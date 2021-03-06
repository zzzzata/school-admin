package com.hq.adaptive.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 节档案
 * 
 * @author shihongjie
 * @email shihongjie@hengqijy.com
 * @date 2017-11-22 16:59:19
 */
public class AdlSectionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Long sectionId;
	//节id
	private Long chapterId;
	//名称
	private String sectionName;
	//编码
	private String sectionNo;
	//课程PK
	private Long courseId;
	//更新时间戳
	private Date updateTime;
	//创建时间戳
	private Date createTime;
	//删除标记 0.正常; 1.删除
	private Integer dr;
	//排序
	private Integer orderNum;
	//更新时间戳
	private Date syncTime;

	@Override
	public String toString() {
		return "AdlSectionEntity{" +
				"sectionId=" + sectionId +
				", chapterId=" + chapterId +
				", sectionName='" + sectionName + '\'' +
				", sectionNo='" + sectionNo + '\'' +
				", courseId=" + courseId +
				", updateTime=" + updateTime +
				", createTime=" + createTime +
				", dr=" + dr +
				", orderNum=" + orderNum +
				", syncTime=" + syncTime +
				'}';
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	/**
	 * 设置：ID
	 */
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	/**
	 * 获取：ID
	 */
	public Long getSectionId() {
		return sectionId;
	}
	/**
	 * 设置：名称
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	/**
	 * 获取：名称
	 */
	public String getSectionName() {
		return sectionName;
	}
	/**
	 * 设置：编码
	 */
	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}
	/**
	 * 获取：编码
	 */
	public String getSectionNo() {
		return sectionNo;
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	/**
	 * 设置：课程PK
	 */
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	/**
	 * 获取：课程PK
	 */
	public Long getCourseId() {
		return courseId;
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
	 * 设置：排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}
}
