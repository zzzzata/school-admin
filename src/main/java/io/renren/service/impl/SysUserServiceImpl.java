package io.renren.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hq.common.enumeration.IMIdPrefix;
import io.renren.dao.SysUserDao;
import io.renren.dao.SysUserTeamDao;
import io.renren.dao.SysUserTypeDao;
import io.renren.entity.SysUserEntity;
import io.renren.entity.SysUserTeamEntity;
import io.renren.entity.SysUserTypeEntity;
import io.renren.service.SysUserRoleService;
import io.renren.service.SysUserService;
import io.renren.service.SysUserTeamService;
import io.renren.utils.Constant;
import io.renren.utils.SendUdeskUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysUserTypeDao sysUserTypeDao;

    @Autowired
	private SysUserTeamService sysUserTeamService;

    @Autowired
	private SysUserTeamDao sysUserTeamDao;

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserDao.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserDao.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserDao.queryByUserName(username);
	}

	@Autowired
	private AmqpTemplate amqpTemplate;

	/** 推送后台用户到IM消息队列名 */
	private static String QUEUE_SYS_USER_MESSAGE = "";
	@Value("#{rabbitmq['queue.sys.user.info']}")
	private void setQUEUE_SYS_USER_MESSAGE(String str){
		QUEUE_SYS_USER_MESSAGE = str;
	}



	@Override
	public SysUserEntity queryObject(Long userId) {
        SysUserEntity user = sysUserDao.queryObject(userId);
        if(user!=null){
            Map qp = new HashMap();
            qp.put("sysUserId",user.getUserId());
            List<SysUserTypeEntity> types =  sysUserTypeDao.queryList(qp);
            for(SysUserTypeEntity t : types){
                //讲师为10，助教为20，班主任为30，课程研发人员为40
                switch (t.getSysUserType()){
                    case 10:
                        user.setTeacher(1);
                        break;
                    case 20:
                        user.setAssistantTeacher(1);
                        break;
                    case 30:
                        user.setClassTeacher(1);
                        break;
                }
            }

        }
		return user;
	}

	@Override
	public List<SysUserEntity> queryList(Map<String, Object> map){
        List<SysUserEntity> list = sysUserDao.queryList(map);
        //**添加管理员类型从表处理，mumu 20181205
        for(SysUserEntity user : list){
            Map qp = new HashMap();
            qp.put("sysUserId",user.getUserId());
            List<SysUserTypeEntity> types =  sysUserTypeDao.queryList(qp);
            for(SysUserTypeEntity t : types){
                //讲师为10，助教为20，班主任为30，课程研发人员为40
                switch (t.getSysUserType()){
                    case 10:
                        user.setTeacher(1);
                    break;
                    case 20:
                        user.setAssistantTeacher(1);
                    break;
                    case 30:
                        user.setClassTeacher(1);
                    break;
                }
            }

        }
		return list;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysUserDao.queryTotal(map);
	}

    /**设置管理员类型**/
    private void setUserType(SysUserEntity user,boolean isNew){
        //**添加管理员类型从表处理，mumu 20181205
        List<SysUserTypeEntity> tl= null;
        if(!isNew){//更新操作
            //清空所有用户类型
            Map qp = new HashMap();
            qp.put("sysUserId",user.getUserId());
            tl = sysUserTypeDao.queryList(qp);
            for(SysUserTypeEntity i : tl){
                sysUserTypeDao.delete(i);
            }
        }

        tl = new ArrayList<SysUserTypeEntity>();
        //添加从表，讲师为10，助教为20，班主任为30，课程研发人员为40
        if(user.getTeacher().equals(1)){
            tl.add(new SysUserTypeEntity(user.getUserId(),10));
        }
        if(user.getAssistantTeacher().equals(1)){
            tl.add(new SysUserTypeEntity(user.getUserId(),20));
        }
        if(user.getClassTeacher().equals(1)){
            tl.add(new SysUserTypeEntity(user.getUserId(),30));
        }

        if(tl.size()>0){
            sysUserTypeDao.saveBatch(tl);
        }
        //**添加管理员类型从表处理，mumu 20181205
    }

	@Override
	@Transactional
	public void save(SysUserEntity user) {
        boolean isNew = true;
        if(user.getUserId()!= null && 0 != user.getUserId()){//更新操作
            isNew=false;
        }

		user.setCreateTime(new Date());
		user.setTs(new Date());
		//sha256加密
		user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		
		//如果是班主任
		if(1 == user.getClassTeacher()){
			//发送Udesk创建客服
			if(StringUtils.isNotBlank(user.getMobile())){
				Integer ownerId = SendUdeskUtil.creatUdeskAgent(user.getMobile(), user.getNickName());
				user.setOwnerId(ownerId!= null?ownerId:0);
			}else{
				Integer ownerId = SendUdeskUtil.creatUdeskAgent(user.getUsername(), user.getNickName());
				user.setOwnerId(ownerId!= null?ownerId:0);
			}
		}

        sysUserDao.save(user);

		//保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());

        //**添加管理员类型从表处理，mumu 20181205
        setUserType(user,isNew);

		//推送到Im
		pushToIm(user);

		//保存用户与团队关系
		if(1 == user.getClassTeacher()){
			if(user.getTeamIdList().size()>0){
				sysUserTeamService.saveByUser(user);
			}
		}

	}
	
	/**
	 * 同步服务-批量保存
	 * @param userList
	 */
	@Override
	@Transactional
	public void saveList(List<SysUserEntity> userList){
		if(null != userList && 0 < userList.size()){
			for (SysUserEntity sysUserEntity : userList) {
				Map<String , Object> map = new HashMap<>();
				map.put("username", sysUserEntity.getUsername());
				map.put("mId", sysUserEntity.getmId());
				if(0 == sysUserDao.syncQueryTotal(map)){
					save(sysUserEntity);
				}
			}
		}
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		user.setTs(new Date());
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		}

        //**添加管理员类型从表处理，mumu 20181205
        setUserType(user,false);

		sysUserDao.update(user);
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());

		//保存用户与团队关系
		sysUserTeamService.updateByUser(user);

	}

	@Override
	@Transactional
	public void deleteBatch(Long[] userId) {
		sysUserDao.deleteBatchInUser(userId);
		sysUserDao.deleteBatchInRole(userId);
		//删除用户与团队关联表
		for(Long l :userId){
			sysUserTeamDao.deleteByUserId(l);
		}

	}

	@Override
	public int updatePassword(Long userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysUserDao.updatePassword(map);
	}
	
	@Override
	public List<SysUserEntity> findClassTeacherList(Map<String, Object> map) {
		return sysUserDao.findClassTeacherList(map);
	}

	@Override
	public List<SysUserEntity> findTeacherList(String nickName,Long teacher ,Long classTeacher ,
			Integer offset ,Integer limit,int asst) {
		if(null == teacher){
			teacher = 0l;
		}
		if(null == classTeacher){
			classTeacher = 0l;
		}
		return sysUserDao.findTeacherList(nickName,teacher, classTeacher, offset, limit,asst);
	}
	/**
	 * 授课老师列表
	 * @param teacher =1 授课老师
	 * @param classTeacher =1 班主任
	 * @param asst
     * @return
	 */
	@Override
	public int findTeacherCount(String nickName, Long teacher, Long classTeacher, int asst){
		if(null == teacher){
			teacher = 0l;
		}
		if(null == classTeacher){
			classTeacher = 0l;
		}
		return this.sysUserDao.findTeacherCount(nickName,teacher, classTeacher,asst);
	}

	@Override
	public SysUserEntity queryTeacherById(Map<String, Object> teacherMap) {
		return sysUserDao.queryTeacherById(teacherMap);
	}

	@Override
	public int queryTotalTeacher(Map<String, Object> map) {
		return this.sysUserDao.queryTotalTeacher(map);
	}

	@Override
	public SysUserEntity queryMid(Map<String, Object> map) {
		return this.sysUserDao.queryMid(map);
	}

	@Override
	public void pause(Long[] userIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", userIds);
		map.put("status", Constant.Status.PAUSE.getValue());
		this.sysUserDao.updateBatch(map);
	}

	@Override
	public void resume(Long[] userIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", userIds);
		map.put("status", Constant.Status.RESUME.getValue());
		this.sysUserDao.updateBatch(map);
	}

	@Override
	public SysUserEntity queryMobile(Map<String, Object> map) {
		return this.sysUserDao.queryMobile(map);
	}

	@Override
	public Map<String, Object> queryObjectByClassId(Long classId) {
		return this.sysUserDao.queryObjectByClassId(classId);
	}

	@Override
	public List<SysUserEntity> queryAllAgentTeacher() {
		return this.sysUserDao.queryAllAgentTeacher();
	}

	@Override
	public String queryMobileByUserId(Long userId) {
		return sysUserDao.queryMobileByUserId(userId);
	}


	/**
	 * 推送到im
	 */
	void pushToIm(SysUserEntity user){
		JSONObject json = new JSONObject();
		//lj_js_user.getUserId() im登陆用户名的拼接
		json.put("platformType", IMIdPrefix.教师.prefix());
		json.put("displayName", user.getNickName());
		json.put("platformUsername", user.getUserId() + "");
		amqpTemplate.convertAndSend("mq-exchange", QUEUE_SYS_USER_MESSAGE, json.toString());
	}

	@Override
	public SysUserEntity queryByNickName(String name) {
		// TODO Auto-generated method stub
		return sysUserDao.queryByNickName(name);
	}
}
