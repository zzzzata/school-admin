package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 学员考期表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-13 09:18:16
 */
public class ExaminationDateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//用户id
	private Long userId;
	//订单id
	private Long orderId;
	//课程id
	private Long courseId;
	//学习计划详情id
	private Long userplanClassDetailId;
	//学习计划id
	private Long userplanClassId;
	//学员规划id
	private Long userPlanId;
	//学习状态:0 在读 1休学 2失联
	private Long learnState;
	//考试状态0-- 1报考 2弃考 3免考
	private Long examState;
	//报考时间id
	private Long examScheduleId;
	//删除
	private Long dr;
	//创建时间戳
	private Date createTime;
	//更新时间戳
	private Date updateTime;

	/**
	 * 设置：编号
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：编号
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：订单id
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：订单id
	 */
	public Long getOrderId() {
		return orderId;
	}
	/**
	 * 设置：课程id
	 */
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	/**
	 * 获取：课程id
	 */
	public Long getCourseId() {
		return courseId;
	}
	/**
	 * 设置：学习计划详情id
	 */
	public void setUserplanClassDetailId(Long userplanClassDetailId) {
		this.userplanClassDetailId = userplanClassDetailId;
	}
	/**
	 * 获取：学习计划详情id
	 */
	public Long getUserplanClassDetailId() {
		return userplanClassDetailId;
	}
	/**
	 * 设置：学习计划id
	 */
	public void setUserplanClassId(Long userplanClassId) {
		this.userplanClassId = userplanClassId;
	}
	/**
	 * 获取：学习计划id
	 */
	public Long getUserplanClassId() {
		return userplanClassId;
	}
	/**
	 * 设置：学员规划id
	 */
	public void setUserPlanId(Long userPlanId) {
		this.userPlanId = userPlanId;
	}
	/**
	 * 获取：学员规划id
	 */
	public Long getUserPlanId() {
		return userPlanId;
	}
	/**
	 * 设置：学习状态:0 在读 1休学 2失联
	 */
	public void setLearnState(Long learnState) {
		this.learnState = learnState;
	}
	/**
	 * 获取：学习状态:0 在读 1休学 2失联
	 */
	public Long getLearnState() {
		return learnState;
	}
	/**
	 * 设置：考试状态0-- 1报考 2弃考 3免考
	 */
	public void setExamState(Long examState) {
		this.examState = examState;
	}
	/**
	 * 获取：考试状态0-- 1报考 2弃考 3免考
	 */
	public Long getExamState() {
		return examState;
	}
	/**
	 * 设置：报考时间id
	 */
	public void setExamScheduleId(Long examScheduleId) {
		this.examScheduleId = examScheduleId;
	}
	/**
	 * 获取：报考时间id
	 */
	public Long getExamScheduleId() {
		return examScheduleId;
	}
	/**
	 * 设置：删除
	 */
	public void setDr(Long dr) {
		this.dr = dr;
	}
	/**
	 * 获取：删除
	 */
	public Long getDr() {
		return dr;
	}
	/**
	 * 设置：创建时间戳
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间戳
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新时间戳
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间戳
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
