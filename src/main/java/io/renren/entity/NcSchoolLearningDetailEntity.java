package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 线下学习计划详情
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-27 11:39:26
 */
public class NcSchoolLearningDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long learningDetailId;
	//线下学习计划id
	private Long learningId;
	//学员id
	private Long userId;
	//学员手机号码
	private String mobile;
	//nc排课计划id
	private String courseclassplanId;
	//排课入课时间
	private Date timeStamp;
	//是否删除: 0正常1删除
	private Integer dr;
	//创建时间
	private Date createTime;
	//
	private Date ts;
	//Nc学员名称
    private String userName;

	/**
	 * 设置：
	 */
	public void setLearningDetailId(Long learningDetailId) {
		this.learningDetailId = learningDetailId;
	}
	/**
	 * 获取：
	 */
	public Long getLearningDetailId() {
		return learningDetailId;
	}
	/**
	 * 设置：线下学习计划id
	 */
	public void setLearningId(Long learningId) {
		this.learningId = learningId;
	}
	/**
	 * 获取：线下学习计划id
	 */
	public Long getLearningId() {
		return learningId;
	}
	/**
	 * 设置：学员id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：学员id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：学员手机号码
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：学员手机号码
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：nc排课计划id
	 */
	public void setCourseclassplanId(String courseclassplanId) {
		this.courseclassplanId = courseclassplanId;
	}
	/**
	 * 获取：nc排课计划id
	 */
	public String getCourseclassplanId() {
		return courseclassplanId;
	}
	/**
	 * 设置：排课入课时间
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * 获取：排课入课时间
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}
	/**
	 * 设置：是否删除: 0正常1删除
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * 获取：是否删除: 0正常1删除
	 */
	public Integer getDr() {
		return dr;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setTs(Date ts) {
		this.ts = ts;
	}
	/**
	 * 获取：
	 */
	public Date getTs() {
		return ts;
	}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
