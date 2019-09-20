package com.hq.courses.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 课程
 * 
 * @author shihongjie
 * @email shihongjie@hengqijy.com
 * @date 2017-11-07 11:46:18
 */
public class CsCourseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//自增id
	private Long courseId;
	//课程名称
	private String courseName;
	//课程编码
	private String courseNo;
	//删除标记 0.正常; 1.删除
	private Integer dr;
	//产品线pk1.自考;2.会计学历;3学来学网;5.会计猎才;6.会计培训
	private Long productId;
	//部门ID
	private Long deptId;
	//更新时间戳
	private Date updateTime;
	//创建时间戳
	private Date createTime;
	//备注
	private String remark;
	//NCID
	private String ncId;
	//蓝鲸ID
	private String ljId;
	//题库ID
	private String tkId;
	//启用状态:0.作废;1.正常
	private Integer status;
	//自适应课程；0:否 1:是
	private Integer adaptiveType;
	//课程审核状态；0:待审核 1:通过
	private Integer auditStatus;
	//课次审核状态；0:待审核 1:通过
	private Integer liveAuditStatus;
	

	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNcId() {
		return ncId;
	}
	public void setNcId(String ncId) {
		this.ncId = ncId;
	}
	public String getLjId() {
		return ljId;
	}
	public void setLjId(String ljId) {
		this.ljId = ljId;
	}
	public String getTkId() {
		return tkId;
	}
	public void setTkId(String tkId) {
		this.tkId = tkId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 设置：自增id
	 */
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	/**
	 * 获取：自增id
	 */
	public Long getCourseId() {
		return courseId;
	}
	/**
	 * 设置：课程名称
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * 获取：课程名称
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * 设置：课程编码
	 */
	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}
	/**
	 * 获取：课程编码
	 */
	public String getCourseNo() {
		return courseNo;
	}
	/**
	 * 设置：删除标记 0.正常; 1.删除
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * 获取：删除标记 0.正常; 1.删除
	 */
	public Integer getDr() {
		return dr;
	}
	/**
	 * 设置：产品线pk1.自考;2.会计学历;3学来学网;5.会计猎才;6.会计培训
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	/**
	 * 获取：产品线pk1.自考;2.会计学历;3学来学网;5.会计猎才;6.会计培训
	 */
	public Long getProductId() {
		return productId;
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

	public Integer getAdaptiveType() {
		return adaptiveType;
	}

	public void setAdaptiveType(Integer adaptiveType) {
		this.adaptiveType = adaptiveType;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getLiveAuditStatus() {
		return liveAuditStatus;
	}

	public void setLiveAuditStatus(Integer liveAuditStatus) {
		this.liveAuditStatus = liveAuditStatus;
	}

	@Override
	public String toString() {
		return "CsCourseEntity [courseId=" + courseId + ", courseName=" + courseName + ", courseNo=" + courseNo
				+ ", dr=" + dr + ", productId=" + productId + ", deptId=" + deptId + ", updateTime=" + updateTime
				+ ", createTime=" + createTime + ", remark=" + remark + ", ncId=" + ncId + ", ljId=" + ljId + ", tkId="
				+ tkId + ", status=" + status + "]";
	}
}
