package io.renren.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import io.renren.dao.manage.UsersMapper;
import io.renren.entity.manage.Users;
import io.renren.entity.manage.UsersExample;
import io.renren.rest.persistent.KGS;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import io.renren.common.utils.AESencryptionUtils;
import io.renren.dao.UserInfoLogDao;
import io.renren.dao.UsersDao;
import io.renren.entity.UserInfoLogEntity;
import io.renren.entity.UsersEntity;
import io.renren.service.AppUserChannelsService;
import io.renren.service.UserInfoLogService;
import io.renren.service.UsersService;
import io.renren.utils.Base64;
import io.renren.utils.Constant;
import io.renren.utils.EncryptionUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;
import sun.misc.BASE64Encoder;



@Service("usersService")
public class UsersServiceImpl implements UsersService {
	   protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 默认密码 123456
     */
    private static String PSW = "afa651c0a832371d479f8131271e20cc";
    //private static String PSW = "1c3f360330c442c3cc62d1608fe7a3a3";//默认密码hqzk123456对应的密文
    private static final String MD5_KEY = "%^\\$AF>.12*******zK";

	private static String userspassKey = "";
	@Value("${userspassKey:SFFKWTIwMThOTkhI}")
	private void setUserspassKey(String userspassKey){
		this.userspassKey = userspassKey;
	}
	
	private static String userQueueName ="";
	@Value("${bluewhale.users.sync.new:bluewhale.users.sync.new}")
	private void setuserQueueName(String userQueueName){
		this.userQueueName = userQueueName;
	}
	@Autowired
	private AppUserChannelsService appUserChannelsService;
    @Resource
    KGS userKGS;
    @Autowired
    private AmqpTemplate amqpTemplate;
	@Autowired
	private UsersDao usersDao;
	@Autowired
	private UserInfoLogService userInfoLogService;
	@Autowired
	private UsersMapper usersMapper;

	@Override
	public UsersEntity queryObject(Long userId){
		return usersDao.queryObject(userId);
	}
	
	@Override
	public List<UsersEntity> queryList(Map<String, Object> map){
		return usersDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return usersDao.queryTotal(map);
	}
	
	@Override
	public void save(UsersEntity users){
		users.setDr(0);
		users.setCreationTime(new Date());
		users.setModifiedTime(users.getCreationTime());
		if (users.getTruePassWord()!=null) {
			users.setPassword(getPasswordToMD5(users.getTruePassWord()));//密码加密
		 
		}
		
		usersDao.save(users);
		
		userInfoLogService.userInsertLog(users);//后台新增加用户日志
		
	     SendTONewUser(users);//发送新用户到队列
	     sendToUserTopic(users);//发送到分发队列
	     appUserChannelsService.userInfoBindPublicChannel(users.getUserId());//将用户绑定到public和本身的渠道，并且发送到消息系统


	}
	
	

	@Override
	public void update(UsersEntity users){
		try{
			UsersEntity oldUsers = this.queryObject(users.getUserId());
			if(StringUtils.isBlank(users.getNickName())){
				users.setPassword(null);
			}
			users.setModifiedTime(new Date());
			usersDao.update(users);
			//修改的用户的存日志
			userInfoLogService.userUpdateLog (oldUsers.toSSOMap(), users.toSSOMap());
			sendToUserTopic(users);//发送到分发队列
		}catch (Exception e){
			logger.error("修改学员信息失败:{},学员信息{}",e.getMessage(),users);
		}

	}
	
	

	@Override
	public void delete(Long userId){
		usersDao.delete(userId);
	}
	
	@Override
	public void deleteBatch(Long[] userIds){
		usersDao.deleteBatch(userIds);
	}
	@Override
	public void pause(Long[] userIds){
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("list", userIds);
    	map.put("status", Constant.Status.PAUSE.getValue());
		usersDao.updateBatch(map);
	}
		
	@Override
	public void resume(Long[] userIds){
	    Map<String, Object> map = new HashMap<String, Object>();
    	map.put("list", userIds);
    	map.put("status", Constant.Status.RESUME.getValue());
		usersDao.updateBatch(map);
	}
	@Override
	public boolean checkMobile(Map<String, Object> map) {
		return usersDao.checkMobile(map)>0;
	}
	@Override
	public boolean mobileExist(Map<String, Object> map) {
		return this.usersDao.mobileExist(map) > 0;
	}
	@Override
	public long getUserIdByMobilePhoneNo(Map<String, Object> map) {
		return this.usersDao.getUserIdByMobilePhoneNo(map);
	}

	@Override
	public String getNickNameByMobilePhoneNo(Map<String, Object> map) {
		return this.usersDao.getNickNameByMobilePhoneNo(map);
	}

	@Override
	public int countUserIdByMobilePhoneNo(Map<String, Object> map) {
		return this.usersDao.countUserIdByMobilePhoneNo(map);
	}


	@Override
	public List<UsersEntity> queryLayList(Map<String, Object> map) {
		return usersDao.queryLayList(map);
	}


	@Override
	public Integer queryUserId(Map<String, Object> map) {
		return usersDao.queryUserId(map);
	}

	@Override
	public void restPsw(String psw , Long userId) {
		UsersEntity usersEntity = this.queryObject(userId);
		
		if(null != usersEntity){
			usersEntity.setPassword(psw);
			this.update(usersEntity); 
		}
	}

	@Override
	public void updateNameByPhone(String phone, String user_name) {
		this.usersDao.updateNameByPhone(phone, user_name);
	}

	@Override
	public int checkUser(String mobile) {		
		return this.usersDao.checkUser(mobile);
	}

	@Override
	public Users findByMobile(String mobile) {
		UsersExample example = new UsersExample();
		example.createCriteria().andMobileEqualTo(mobile).andDrEqualTo((byte)0);
		return usersMapper.selectByExampleFetchOne(example);
	}
	@Override
	public String getRandomPass(Integer strLen,Integer numLen) {
		Random ran2 = new Random();
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String num = "1234567890";
		StringBuilder sub= new StringBuilder();
		for (int i=0;i<strLen;i++) {
			int number = ran2.nextInt( base.length() );
			sub.append( base.charAt( number ));
		}
		for (int i=0;i<numLen;i++) {
			int number = ran2.nextInt( num.length() );
			sub.append( num.charAt( number ));
		}
		 return sub.toString();
        }
	@Override
	public String getPasswordToMD5(String password) {

		byte[] b = null; 
		String str64 = null; 
		try {
			b = password.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		str64 = new BASE64Encoder().encode(b); 
		return (EncryptionUtils.md5Hex(str64 + MD5_KEY));
		 
	}
	
	
	private void sendToUserTopic(UsersEntity users) {
      try {
		  Gson gson = new Gson();
		  amqpTemplate.convertAndSend("userinfo-exchange","",gson.toJson(users.toSSOMap()));
		  logger.info("发送用户到分发队列,用户信息:{}",users);
      }catch (Exception es ) {
    	  logger.error("发送用户到分发队列出错,用户信息{}",users);
      }
	}

	private void SendTONewUser(UsersEntity user) {
		Map<String, Object> map= new HashMap<String,Object>();
		try { 
			map = user.toSSOMap();
		map.put("msgType",1); 
		if (user.getTruePassWord()==null&& PSW.equals( user.getPassword())){
		user.setTruePassWord("hqzk123456");
		
		}

		  Gson gson = new Gson();
		  
		map.put("asePwStr", AESencryptionUtils.AESencrypt(user.getTruePassWord(), this.userspassKey) );
		amqpTemplate.convertAndSend(userQueueName,gson.toJson(map)); 
		logger.info("发送新用户到队列:{},电话:{}",user.getUserId(),user.getMobile());
		}catch (Exception es) {
			logger.error("发送新用户到队列出错！{},{}",map,es);
		}
	}
	@Override
	public  void setLastLoginTime(List<UsersEntity> userList) {
		if (userList==null||userList.size()==0) {
			return;
		}else {
			for (int i=0;i<userList.size();i++) {
				if (userList.get(i)!=null&&userList.get(i).getUserId()!=null&&userList.get(i).getUserId()>0) {
					try {
					 Date time = usersDao.queryLastLoginTime(userList.get(i).getUserId());
					 
					userList.get(i).setLastLoginTime(time);
					} catch (Exception es) {
						this.logger.error("用户取得最近登陆时间失败！userId:{},errorMessage:{}.",userList.get(i).getUserId(),es);
					}
				}
			}
		}
	}

	@Override
	public void updateByUserId(UsersEntity users) {
		try{
			UsersEntity oldUsers = this.queryObject(users.getUserId());
			if(StringUtils.isBlank(users.getNickName())){
				users.setPassword(null);
			}
			users.setModifiedTime(new Date());
			usersDao.update(users);
			//修改的用户的存日志
			userInfoLogService.userUpdateLog (oldUsers.toSSOMap(), users.toSSOMap());
			//修改后再重查推送
			UsersEntity usersEntity = usersDao.queryByUserId(users.getUserId());
			sendToUserTopic(usersEntity);//发送到分发队列
		}catch (Exception e){
			logger.error("修改学员信息失败:{},学员信息{}",e.getMessage(),users);
		}
	}


}
