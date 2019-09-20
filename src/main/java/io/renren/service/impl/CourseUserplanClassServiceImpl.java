package io.renren.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.renren.dao.CourseUserplanClassDao;
import io.renren.dao.CourseUserplanClassDetailDao;
import io.renren.dao.CourseUserplanDao;
import io.renren.dao.CourseUserplanDetailDao;
import io.renren.dao.MallExamScheduleDao;
import io.renren.dao.MallOrderDao;
import io.renren.entity.CourseClassplanEntity;
import io.renren.entity.CourseUserplanClassDetailEntity;
import io.renren.entity.CourseUserplanClassEntity;
import io.renren.entity.CourseUserplanDetailEntity;
import io.renren.entity.CourseUserplanEntity;
import io.renren.entity.MallExamScheduleEntity;
import io.renren.entity.MallOrderEntity;
import io.renren.pojo.CourseUserplanClassDetailPOJO;
import io.renren.pojo.CourseUserplanClassPOJO;
import io.renren.rest.persistent.KGS;
import io.renren.service.CourseClassplanService;
import io.renren.service.CourseUserplanClassDetailService;
import io.renren.service.CourseUserplanClassService;
import io.renren.service.CourseUserplanService;
import io.renren.utils.Constant;
import io.renren.utils.UserPlanClassDetailException;

@Service("courseUserplanClassService")
public class CourseUserplanClassServiceImpl implements CourseUserplanClassService {
	@Autowired
	private CourseUserplanClassDao courseUserplanClassDao;
	@Autowired
	private CourseUserplanClassDetailService courseUserplanClassDetailService;
	@Autowired
	private CourseUserplanService courseUserplanService;
	@Autowired
	private CourseUserplanDao courseUserplanDao;
	@Autowired
	private CourseUserplanDetailDao courseUserplanDetailDao;
	@Autowired
	private MallOrderDao mallOrderDao;
	@Autowired
	private CourseClassplanService courseClassplanService;
	@Autowired
	private MallExamScheduleDao mallExamScheduleDao;
	@Autowired
	private CourseUserplanClassDetailDao courseUserplanClassDetailDao;
	
	@Resource
	KGS studyplanKGS;
	private static final String HEAD = "XXJH_";
	
	
	public String getCode(){
		String userplanClassNo = HEAD + studyplanKGS.nextId();
		return userplanClassNo;
	}
	
	@Override
	public CourseUserplanClassEntity queryObject(Map<String, Object> map){
		return courseUserplanClassDao.queryObject(map);
	}
	
	@Override
	public List<CourseUserplanClassEntity> queryList(Map<String, Object> map){
		return courseUserplanClassDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return courseUserplanClassDao.queryTotal(map);
	}
	
	private void saveOrUpdate(CourseUserplanClassPOJO courseUserplanClass) {
		//查询该学员所有学习计划
		List<CourseUserplanClassPOJO> userplanClassList = new ArrayList<>();
		if(courseUserplanClass.getOrderNo() != null) {
			MallOrderEntity orderEntity = mallOrderDao.queryByOrderNo(courseUserplanClass.getOrderNo());
			if(orderEntity != null) { 
				userplanClassList = courseUserplanClassDao.queryByOrderNo(orderEntity.getOrderNo());
				courseUserplanClass.setCommodityId(orderEntity.getCommodityId());
				courseUserplanClass.setSchoolId(orderEntity.getSchoolId());
			}
		} 
		
		courseUserplanClass.setModifyTime(new Date());
		//en
		CourseUserplanClassEntity en = CourseUserplanClassPOJO.getEntity(courseUserplanClass);
		CourseUserplanEntity courseUserplanEntity = this.courseUserplanService.queryObject(en.getUserplanId());
		if(courseUserplanEntity != null){
			en.setDeptId(courseUserplanEntity.getDeptId());
		} 

		//已经有学习计划(相同考试时间)的学员，不再生成新的学习计划，只在旧的学习计划上面追加学习计划详情
		for(CourseUserplanClassPOJO pojo : userplanClassList) {
			if(en.getUserplanId().equals(pojo.getUserplanId()) && en.getExamScheduleId().equals(pojo.getExamScheduleId())) {
				en = CourseUserplanClassPOJO.getEntity(pojo);
			}
		}
		
		en.setDr(0);
		en.setStatus(1);
		en.setRemark("test666");
		  
		//保存/更新主表
		if(en.getUserplanClassId() == null) {
			//学习计划单号
			en.setUserplanClassNo(this.getCode());
			en.setCreateTime(new Date());
			courseUserplanClassDao.save(en);
		}else {
			en.setModifyTime(new Date());
			courseUserplanClassDao.update(en);
		}
		
		//子表
		List<CourseUserplanClassDetailPOJO> detailList = courseUserplanClass.getDetailList();
		
		List<Long> delIds = new ArrayList<Long>();
		//子表保存
		if(null != detailList && detailList.size() > 0){
			for(int i=0;i<detailList.size();i++){
				//pojo
				CourseUserplanClassDetailPOJO cucdp = detailList.get(i);
				//entity
				CourseUserplanClassDetailEntity cucde = CourseUserplanClassDetailPOJO.getEntity(cucdp);
				//set 主表id
				cucde.setUserplanClassId(en.getUserplanClassId());
				//set 学员规划子表id
				cucde.setUserplanDetailId(cucdp.getUserplanDetailId());
				//set 排课计划id
				cucde.setClassplanId(cucdp.getClassplanId());
				//set 入课时间
				cucde.setTimestamp(cucdp.getTimestamp());
				//set 备注
				cucde.setRemark(cucdp.getRemark());
				//set dr
				cucde.setDr(0);
				//set schoolId
				cucde.setSchoolId(en.getSchoolId());
				//排序
				cucde.setOrderNum(i);
				if(null == cucde.getUserplanClassDetailId()){
					courseUserplanClassDetailDao.save(cucde);
				}else{
					courseUserplanClassDetailDao.update(cucde);
				}
				delIds.add(cucde.getUserplanClassDetailId());
			}
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("userplanClassDetailIds", delIds);
			map.put("userplanClassId", en.getUserplanClassId());
			courseUserplanClassDetailService.deleteBatchNotIn(map);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void save(CourseUserplanClassPOJO courseUserplanClass) throws UserPlanClassDetailException{
		if(courseUserplanClass.getExamScheduleId() != null) {
			//查询学习计划
			Map<String,Object> userplanClassParams = new HashMap<>();
			userplanClassParams.put("examScheduleId", courseUserplanClass.getExamScheduleId());
			userplanClassParams.put("userplanId", courseUserplanClass.getUserplanId());
			List<CourseUserplanClassEntity> userplanClassList = courseUserplanClassDao.queryList(userplanClassParams);
			
			if(userplanClassList.size() != 0) {
				throw new UserPlanClassDetailException("学习计划生成失败,相同学员规划,相同考试时间只能生成一个学习计划");
			} 
		}
		CourseUserplanClassEntity en = CourseUserplanClassPOJO.getEntity(courseUserplanClass);
		CourseUserplanEntity courseUserplanEntity = this.courseUserplanService.queryObject(en.getUserplanId());
		if(courseUserplanEntity != null){
			en.setDeptId(courseUserplanEntity.getDeptId());
		} 
		en.setDr(0);
		en.setStatus(1);
		en.setCreateTime(new Date());
		//保存主表
		courseUserplanClassDao.save(en);
		
		//子表
		List<CourseUserplanClassDetailPOJO> detailList = courseUserplanClass.getDetailList();
		//子表保存
		if(null != detailList && detailList.size() > 0){
			for(int i=0;i<detailList.size();i++){
				//pojo
				CourseUserplanClassDetailPOJO cucdp = detailList.get(i);
				//entity
				CourseUserplanClassDetailEntity cucde = CourseUserplanClassDetailPOJO.getEntity(cucdp);
				//set 主表id
				cucde.setUserplanClassId(en.getUserplanClassId());
				//set 学员规划子表id
				cucde.setUserplanDetailId(cucdp.getUserplanDetailId());
				//set 排课计划id
				cucde.setClassplanId(cucdp.getClassplanId());
				//set 入课时间
				cucde.setTimestamp(cucdp.getTimestamp());
				//set 备注
				cucde.setRemark(cucdp.getRemark());
				//set dr
				cucde.setDr(0);
				//set schoolId
				cucde.setSchoolId(en.getSchoolId());
				//排序
				cucde.setOrderNum(i);
				
				//保存子表
				courseUserplanClassDetailService.save(cucde);
			}
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(CourseUserplanClassPOJO courseUserplanClass) throws UserPlanClassDetailException{
		//修改时间
		courseUserplanClass.setModifyTime(new Date());
		//en
		CourseUserplanClassEntity en = CourseUserplanClassPOJO.getEntity(courseUserplanClass);
		CourseUserplanEntity courseUserplanEntity = this.courseUserplanService.queryObject(en.getUserplanId());
		if(courseUserplanEntity != null){
			en.setDeptId(courseUserplanEntity.getDeptId());
		}
		//保存主表修改
		courseUserplanClassDao.update(en);
		//遍历子表
		List<CourseUserplanClassDetailPOJO> detailList = courseUserplanClass.getDetailList();
		
		List<Long> delIds = new ArrayList<Long>();
		if(null != detailList && detailList.size() > 0){
			for(int i=0;i<detailList.size();i++){
				//pojo
				CourseUserplanClassDetailPOJO cucdp = detailList.get(i);
				//entity
				CourseUserplanClassDetailEntity cucde = CourseUserplanClassDetailPOJO.getEntity(cucdp);
				//set 主表id
				cucde.setUserplanClassId(en.getUserplanClassId());
				//set 学员规划子表id
				cucde.setUserplanDetailId(cucdp.getUserplanDetailId());
				//set 排课计划id
				cucde.setClassplanId(cucdp.getClassplanId());
				//set 入课时间
				cucde.setTimestamp(cucdp.getTimestamp());
				//set 备注
				cucde.setRemark(cucdp.getRemark());
				//set dr
				cucde.setDr(0);
				//set schoolId
				cucde.setSchoolId(en.getSchoolId());
				//排序
				cucde.setOrderNum(i);
				if(null == cucde.getUserplanClassDetailId()){
					courseUserplanClassDetailService.save(cucde);
				}else{
					courseUserplanClassDetailService.update(cucde);
				}
				delIds.add(cucde.getUserplanClassDetailId());
			}
		}
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("userplanClassDetailIds", delIds);
		map.put("userplanClassId", en.getUserplanClassId());
		courseUserplanClassDetailService.deleteBatchNotIn(map);
	}
	
	@Override
	public void update(CourseUserplanClassEntity courseUserplanClassEntity) throws UserPlanClassDetailException{
		//保存主表修改
		courseUserplanClassDao.update(courseUserplanClassEntity);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(Map<String, Object> map){
		courseUserplanClassDao.delete(map);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void deleteBatch(Map<String, Object> map){
		courseUserplanClassDetailService.deleteBatch(map);
		courseUserplanClassDao.deleteBatch(map);
	}

	/**
	 * 查询列表
	 */
	@Override
	public List<CourseUserplanClassPOJO> queryPojoList(Map<String, Object> map) {
		return this.courseUserplanClassDao.queryPojoList(map);
	}

	@Override
	public CourseUserplanClassPOJO queryPojoObject(Map<String, Object> map) {
		return this.courseUserplanClassDao.queryPojoObject(map);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void pause(Long[] userplanClassIds) {
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("list", userplanClassIds);
    	map.put("status", Constant.Status.PAUSE.getValue());
    	courseUserplanClassDao.updateBatch(map);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void resume(Long[] userplanClassIds) {
		 Map<String, Object> map = new HashMap<String, Object>();
	     map.put("list", userplanClassIds);
	     map.put("status", Constant.Status.RESUME.getValue());
	     //map.put("modifiedTime", new Date());
	     courseUserplanClassDao.updateBatch(map);
	}

	/**
	 * 审核通过
	 * */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void accept(Long userplanClassId) {
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("userplanClassId", userplanClassId);
	    map.put("status", Constant.Status.RESUME.getValue());
	    courseUserplanClassDao.audited(map);
	}
	
	/**
	 * 审核未过
	 * */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void reject(Long userplanClassId) {
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("userplanClassId", userplanClassId);
	    map.put("status", Constant.Status.PAUSE.getValue());
	    courseUserplanClassDao.audited(map);
	}

	@Override
	public Integer queryUserClassPlanMid(String mId) {
		return courseUserplanClassDao.queryUserClassPlanMid(mId);
	}

    @Override
    public void updateChangeByOrderNo(Long userplanId) {
        courseUserplanClassDao.updateChangeByOrderNo(userplanId);
    }

    @Override
    public List<Long> queryCourseUserplanClass(Long userPlanId) {
        return courseUserplanClassDao.queryCourseUserplanClass(userPlanId);
    }

    @Override
	public synchronized String addBatchByClassplanId(String classplanId , String classId,String schoolId,Long userId) {
		StringBuffer sbf = new StringBuffer();
        String[] classIds = null;
        if (StringUtils.isNotBlank(classId)){
            classIds = classId.split(",");
        }
		if(StringUtils.isNotBlank(classplanId)){
//			this.courseClassplanService.queryObject1(map)
			//排课
			CourseClassplanEntity classplanEntity = this.courseClassplanService.queryObjectByClassplanId(classplanId);
			if(null != classplanEntity){
				//课程ID
				Long courseId = classplanEntity.getCourseId();
//				Map<String, Object> userplanMap = 
				List<Map<String, Object>> list = this.courseUserplanService.queryUserplanInfoNotClassplanByCourseId(courseId,classIds);
				if(null != list && !list.isEmpty()){
					sbf.append("本次处理单据数量:"+list.size());
					for(Map<String, Object> userplanMap : list){
						if(null != userplanMap){
							//学员规划子表ID
							Long userplanDetailId = (Long) userplanMap.get("userplanDetailId");
							//学员规划ID
							Long userplanId = (Long) userplanMap.get("userplanId");
							CourseUserplanClassPOJO courseUserplanClass = new CourseUserplanClassPOJO();
							courseUserplanClass.setUserplanId(userplanId);
							courseUserplanClass.setUserplanClassNo(getCode());
							courseUserplanClass.setExamScheduleId(null);
							courseUserplanClass.setRemark("");
							courseUserplanClass.setSchoolId(schoolId);
							courseUserplanClass.setCreateTime(new Date());
							courseUserplanClass.setModifyTime(new Date());
							courseUserplanClass.setCreatePerson(userId);
							courseUserplanClass.setModifyPerson(userId);
							courseUserplanClass.setDr(0);
							courseUserplanClass.setStatus(1);
							courseUserplanClass.setDeptId((Long)userplanMap.get("deptId"));
							List<CourseUserplanClassDetailPOJO> detailList = new ArrayList<>();
							CourseUserplanClassDetailPOJO classDetailPOJO = new CourseUserplanClassDetailPOJO();
							classDetailPOJO.setUserplanDetailId(userplanDetailId);
							classDetailPOJO.setClassplanId(classplanId);
							classDetailPOJO.setTimestamp(new Date());
							classDetailPOJO.setRemark("");
							classDetailPOJO.setDr(0);
							classDetailPOJO.setSchoolId(schoolId);
							classDetailPOJO.setOrderNum(1);
							courseUserplanClass.setDetailList(detailList);
							detailList.add(classDetailPOJO);
							try {
								this.save(courseUserplanClass);
							} catch (UserPlanClassDetailException e) {
								sbf.append("~学员规划ID:"+userplanId + "学员规划子表ID:"+userplanDetailId+ e.getMessage());
//								e.printStackTrace();
							}
						}
					}
				}else{
					sbf.append("该排课下的学员已经全部生成学习规划！");
				}
				
			}
//			courseUserplanClass.setUserplanId(userplanId);
		}else{
			sbf.append("参数异常!");
		}
		return sbf.toString();
	}

	@Override
	@Transactional
	public String addBatchByClassplanIdAndClassId(Long examScheduleId, String classId, String classplanIds, String schoolId, Long userId) {
		StringBuffer sbf = new StringBuffer();
        String[] classIds = null;
        List<Long> courseList = new ArrayList<>();
        
        if (StringUtils.isNotBlank(classId)){
            classIds = classId.split(",");
        }
		if(StringUtils.isNotBlank(classplanIds)){
			//临时保存courseId 和 classPlanIds的对应关系,方便后面查找
			Map<Long,String> courseMap = new HashMap<>();
			
			String[] classplanIdStr = classplanIds.split(",");
			for(String classplanId : classplanIdStr) {
				//排课
				CourseClassplanEntity classplanEntity = this.courseClassplanService.queryObjectByClassplanId(classplanId);
				if(null == classplanEntity){
					sbf.append(classplanId + "找不到相应排课计划");
				}else {
					courseList.add(classplanEntity.getCourseId());
					courseMap.put(classplanEntity.getCourseId(), classplanId);
				} 
			}
			
			//查询班级所有学员规划
			List<CourseUserplanEntity> userplans = courseUserplanDao.queryUserplanByClassIds(classIds);
			for(CourseUserplanEntity userplan : userplans) {
				//查询该学员规划的所有子表
				Map<String,Object> userplanDetailsParams = new HashMap<>();
				userplanDetailsParams.put("userPlanId", userplan.getUserPlanId());
				userplanDetailsParams.put("courseIds", courseList);
				List<CourseUserplanDetailEntity> userplanDetails = courseUserplanDetailDao.courseUserPlanEntityLisrt(userplanDetailsParams);
				
				//生成本次新增的学员规划
				List<CourseUserplanClassDetailPOJO> newUserplanClassDetails = new ArrayList<>();
				for(CourseUserplanDetailEntity userplanDetail : userplanDetails) {
					CourseUserplanClassDetailPOJO pojo = new CourseUserplanClassDetailPOJO();
					pojo.setClassplanId(courseMap.get(userplanDetail.getCourseId()));
					pojo.setUserplanDetailId(userplanDetail.getUserplanDetailId());
					pojo.setTimestamp(new Date());
					pojo.setRemark("test666");
					newUserplanClassDetails.add(pojo);
				}
				
				//查询学习计划
				Map<String,Object> userplanClassParams = new HashMap<>();
				userplanClassParams.put("examScheduleId", examScheduleId);
				userplanClassParams.put("userplanId", userplan.getUserPlanId());
				List<CourseUserplanClassEntity> userplanClassList = courseUserplanClassDao.queryList(userplanClassParams);
				
				//学习计划pojo
				CourseUserplanClassPOJO userplanClass = null;
				List<CourseUserplanClassDetailPOJO> userplanClassdetails = null;
				if(userplanClassList.size() == 0) {
					userplanClass = new CourseUserplanClassPOJO();
					userplanClassdetails = new ArrayList<>();
					//修改用户
					userplanClass.setCreatePerson(userId);
					userplanClass.setExamScheduleId(examScheduleId);
				}else {
				    userplanClass = CourseUserplanClassPOJO.getPOJO(userplanClassList.get(0));
					Map<String,Object> userplanClassDetailsParams = new HashMap<>();
					userplanClassDetailsParams.put("userplanClassId", userplanClass.getUserplanClassId());
					userplanClassdetails = courseUserplanClassDetailService.queryListByUserplanClassId(userplanClassDetailsParams);
				}

				userplanClass.setOrderNo(userplan.getOrderNo());
				userplanClass.setModifyPerson(userId);
				userplanClass.setUserplanId(userplan.getUserPlanId());
				//学习计划 去重
				//记录完整的去重结果列表
				List<CourseUserplanClassDetailPOJO> tempUserplanClassdetails = new ArrayList<>();
				
				for(CourseUserplanClassDetailPOJO newDetail : newUserplanClassDetails) {
					boolean isReapt = false;
					for(CourseUserplanClassDetailPOJO oldDetail : userplanClassdetails) {
						if(oldDetail.getClassplanId().equals(newDetail.getClassplanId())) {
							isReapt = true;
							break;
						}
					}
					if(!isReapt) {
						tempUserplanClassdetails.add(newDetail);
					}
				}

				userplanClass.setDetailList(tempUserplanClassdetails);
				this.saveOrUpdate(userplanClass);
			}

			sbf.append("成功处理：" + userplans.size() + " 条学员规划信息,并保存或更新相关学习计划");
		}else{
			sbf.append("参数异常!");
		}
		return sbf.toString();
	}
 
	@Override
	@Transactional
	public String importData(List<String[]> dataList,Long sysUserId) {
		int num = 1;
		try {
			for(int i = 1; i < dataList.size(); i++,num++) {
				String[] rows = dataList.get(i);
				
				//去除空格
				for(int j = 0 ; j< rows.length ; j++) {
					if(StringUtils.isNotBlank(rows[j])) {
						rows[j] = rows[j].trim();
					}
				}
				
				String orderNo = rows[0];
				String classplanNameStr = rows[1];
				String examScheduleName = rows[2];
				
				if(StringUtils.isBlank(orderNo)) {
					throw new RuntimeException("订单编号不能为空");
				}
				if(StringUtils.isBlank(classplanNameStr)) {
					throw new RuntimeException("排课计划不能为空");
				}
				if(StringUtils.isBlank(examScheduleName)) {
					throw new RuntimeException("考试时刻不能为空");
				}
				
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("orderNo", orderNo);
				MallOrderEntity mallOrderEntity = mallOrderDao.queryObject(params);
				if(mallOrderEntity == null) {
					throw new RuntimeException("找不到相应订单");
				}
				
				//考试时刻
				Long scheduleId = null;
				if(rows.length >= 3) {
					MallExamScheduleEntity schedule = mallExamScheduleDao.queryObjectByName(examScheduleName);
					if(schedule != null) {
						scheduleId = schedule.getId();
					}
				}

				//生成学员规划
				CourseUserplanEntity courseUserplan = courseUserplanDao.queryUserplanObjectByOrderId(mallOrderEntity.getOrderId());
				if( courseUserplan.getUserPlanId() == null ) {
					courseUserplan = new CourseUserplanEntity();
					courseUserplan.setOrderId(mallOrderEntity.getOrderId());
					courseUserplan.setSchoolId(mallOrderEntity.getSchoolId());
					courseUserplan.setIsMatch(0);
					courseUserplan.setIsRep(0);
					courseUserplan.setClassId(mallOrderEntity.getClassId());
					courseUserplan.setDeptId(mallOrderEntity.getDeptId());
					courseUserplanService.save(courseUserplan);
				} 
				
				//学习计划详情
				List<CourseUserplanClassDetailPOJO> detailList = new ArrayList<>();
				String[] classplanNames = classplanNameStr.split(",");
				for(String classplanName : classplanNames) { 

					Map<String,Object> userplandetailMap = new HashMap<>();
					userplandetailMap.put("userPlanId", courseUserplan.getUserPlanId());
					userplandetailMap.put("classplanName", classplanName);
					List<Map<String, Object>> userplanDetailList = this.courseUserplanDetailDao.queryListByUserplanId(userplandetailMap);
					
					if(userplanDetailList.size() == 0) {
						throw new RuntimeException("该订单所购买的课程与排课计划的课程不一样");
					}
					Map<String, Object> userplanDetail = userplanDetailList.get(0);
					CourseUserplanClassDetailPOJO UserplanClassDetail = new CourseUserplanClassDetailPOJO();
					UserplanClassDetail.setClassplanName(classplanName);
					UserplanClassDetail.setCourseName((String)userplanDetail.get("courseName"));
					UserplanClassDetail.setUserplanDetailId((Long)userplanDetail.get("userplanDetailId"));
					UserplanClassDetail.setClassplanId((String)userplanDetail.get("classplanId"));
					UserplanClassDetail.setSchoolId(mallOrderEntity.getSchoolId());
					UserplanClassDetail.setTimestamp(new Date());
					detailList.add(UserplanClassDetail);
				}
				//学习计划详情
				CourseUserplanClassPOJO courseUserplanClass = new CourseUserplanClassPOJO();
				courseUserplanClass.setDeptId(mallOrderEntity.getDeptId());
				courseUserplanClass.setOrderNo(mallOrderEntity.getOrderNo());
				//平台ID
			    courseUserplanClass.setSchoolId(mallOrderEntity.getSchoolId());
			    //创建用户
				courseUserplanClass.setCreatePerson(sysUserId);
				//修改用户
				courseUserplanClass.setModifyPerson(courseUserplanClass.getCreatePerson());
				courseUserplanClass.setExamScheduleId(scheduleId);
				courseUserplanClass.setUserplanId(courseUserplan.getUserPlanId());
				//学习计划单号
				courseUserplanClass.setUserplanClassNo(this.getCode());
				this.saveOrUpdate(courseUserplanClass);
				 
			}
		}catch (Exception e) {
			throw new RuntimeException("第" + num + "行出错，" + e.getMessage());
		}
		return "导入成功";
	}
	
}
