package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 学习情况
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-16 11:12:00
 */
public class LearningSituationEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Integer id;
	//课程id
	private Integer courseId;
	//订单id
	private Integer orderId;
	//用户id
	private Integer userId;
	//学员规划id
	private Integer userPlanId;
	//参考日期
	private String referenceDate;
	//创建日期
	private String createDate;
	//应出勤时长
	private Integer fullDur;
	//观看时长
	private Integer watchDur;
	//听课完成率
	private Double attendPer;
	//作对题数
	private Integer rightNum;
	//总题数
	private Integer allNum;
	//作业达标率
	private Double jobPer;
	//班主任评价
	private String teacherAssess;
	//校区协助
	private String schoolAssist;
	//0:正常；1:删除
	private Integer dr;

	/**
	 * 设置：编号
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：编号
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：课程id
	 */
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	/**
	 * 获取：课程id
	 */
	public Integer getCourseId() {
		return courseId;
	}
	/**
	 * 设置：订单id
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单id
	 */
	public Integer getOrderId() {
		return orderId;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：学员规划id
	 */
	public void setUserPlanId(Integer userPlanId) {
		this.userPlanId = userPlanId;
	}
	/**
	 * 获取：学员规划id
	 */
	public Integer getUserPlanId() {
		return userPlanId;
	}

	public String getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(String referenceDate) {
		this.referenceDate = referenceDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * 设置：应出勤时长
	 */
	public void setFullDur(Integer fullDur) {
		this.fullDur = fullDur;
	}
	/**
	 * 获取：应出勤时长
	 */
	public Integer getFullDur() {
		return fullDur;
	}
	/**
	 * 设置：观看时长
	 */
	public void setWatchDur(Integer watchDur) {
		this.watchDur = watchDur;
	}
	/**
	 * 获取：观看时长
	 */
	public Integer getWatchDur() {
		return watchDur;
	}
	/**
	 * 设置：听课完成率
	 */
	public void setAttendPer(Double attendPer) {
		this.attendPer = attendPer;
	}
	/**
	 * 获取：听课完成率
	 */
	public Double getAttendPer() {
		return attendPer;
	}
	/**
	 * 设置：作对题数
	 */
	public void setRightNum(Integer rightNum) {
		this.rightNum = rightNum;
	}
	/**
	 * 获取：作对题数
	 */
	public Integer getRightNum() {
		return rightNum;
	}
	/**
	 * 设置：总题数
	 */
	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	/**
	 * 获取：总题数
	 */
	public Integer getAllNum() {
		return allNum;
	}
	/**
	 * 设置：作业达标率
	 */
	public void setJobPer(Double jobPer) {
		this.jobPer = jobPer;
	}
	/**
	 * 获取：作业达标率
	 */
	public Double getJobPer() {
		return jobPer;
	}
	/**
	 * 设置：班主任评价
	 */
	public void setTeacherAssess(String teacherAssess) {
		this.teacherAssess = teacherAssess;
	}
	/**
	 * 获取：班主任评价
	 */
	public String getTeacherAssess() {
		return teacherAssess;
	}
	/**
	 * 设置：校区协助
	 */
	public void setSchoolAssist(String schoolAssist) {
		this.schoolAssist = schoolAssist;
	}
	/**
	 * 获取：校区协助
	 */
	public String getSchoolAssist() {
		return schoolAssist;
	}
	/**
	 * 设置：0:正常；1:删除
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * 获取：0:正常；1:删除
	 */
	public Integer getDr() {
		return dr;
	}
}
