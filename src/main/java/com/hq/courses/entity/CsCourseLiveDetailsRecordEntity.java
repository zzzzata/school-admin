package com.hq.courses.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 课次变更记录
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-22 11:43:43
 */
public class CsCourseLiveDetailsRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Long liveRecordId;
	//原编号
	private Long liveId;
	//课次名称
	private String liveName;
	//上期复习文件
	private String reviewName;
	//上期复习文件地址
	private String reviewUrl;
	//本次预习文件
	private String prepareName;
	//本次预习文件地址
	private String prepareUrl;
	//课堂资料文件
	private String coursewareName;
	//课堂资料地址
	private String coursewareUrl;
	//阶段id
	private Long phaseId;
	//阶段名称
	private String phaseName;
	//创建时间
	private Date createTime;
	//创建人
	private Long createPerson;
	//修改时间
	private Date updateTime;
	//修改人
	private Long updatePerson;
	//删除 0：正常，1：删除
	private Integer dr;
	//序号
	private Integer orderNum;
	//课程id
	private Long courseId;
	//同步状态，0：未同步；1：已同步
	private Integer syncStatus;
	//修改人
	private String nickName;

	/**
	 * 设置：主键
	 */
	public void setLiveRecordId(Long liveRecordId) {
		this.liveRecordId = liveRecordId;
	}
	/**
	 * 获取：主键
	 */
	public Long getLiveRecordId() {
		return liveRecordId;
	}
	/**
	 * 设置：原编号
	 */
	public void setLiveId(Long liveId) {
		this.liveId = liveId;
	}
	/**
	 * 获取：原编号
	 */
	public Long getLiveId() {
		return liveId;
	}
	/**
	 * 设置：课次名称
	 */
	public void setLiveName(String liveName) {
		this.liveName = liveName;
	}
	/**
	 * 获取：课次名称
	 */
	public String getLiveName() {
		return liveName;
	}
	/**
	 * 设置：上期复习文件
	 */
	public void setReviewName(String reviewName) {
		this.reviewName = reviewName;
	}
	/**
	 * 获取：上期复习文件
	 */
	public String getReviewName() {
		return reviewName;
	}
	/**
	 * 设置：上期复习文件地址
	 */
	public void setReviewUrl(String reviewUrl) {
		this.reviewUrl = reviewUrl;
	}
	/**
	 * 获取：上期复习文件地址
	 */
	public String getReviewUrl() {
		return reviewUrl;
	}
	/**
	 * 设置：本次预习文件
	 */
	public void setPrepareName(String prepareName) {
		this.prepareName = prepareName;
	}
	/**
	 * 获取：本次预习文件
	 */
	public String getPrepareName() {
		return prepareName;
	}
	/**
	 * 设置：本次预习文件地址
	 */
	public void setPrepareUrl(String prepareUrl) {
		this.prepareUrl = prepareUrl;
	}
	/**
	 * 获取：本次预习文件地址
	 */
	public String getPrepareUrl() {
		return prepareUrl;
	}
	/**
	 * 设置：课堂资料文件
	 */
	public void setCoursewareName(String coursewareName) {
		this.coursewareName = coursewareName;
	}
	/**
	 * 获取：课堂资料文件
	 */
	public String getCoursewareName() {
		return coursewareName;
	}
	/**
	 * 设置：课堂资料地址
	 */
	public void setCoursewareUrl(String coursewareUrl) {
		this.coursewareUrl = coursewareUrl;
	}
	/**
	 * 获取：课堂资料地址
	 */
	public String getCoursewareUrl() {
		return coursewareUrl;
	}
	/**
	 * 设置：阶段id
	 */
	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}
	/**
	 * 获取：阶段id
	 */
	public Long getPhaseId() {
		return phaseId;
	}
	/**
	 * 设置：阶段名称
	 */
	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}
	/**
	 * 获取：阶段名称
	 */
	public String getPhaseName() {
		return phaseName;
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
	 * 设置：创建人
	 */
	public void setCreatePerson(Long createPerson) {
		this.createPerson = createPerson;
	}
	/**
	 * 获取：创建人
	 */
	public Long getCreatePerson() {
		return createPerson;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：修改人
	 */
	public void setUpdatePerson(Long updatePerson) {
		this.updatePerson = updatePerson;
	}
	/**
	 * 获取：修改人
	 */
	public Long getUpdatePerson() {
		return updatePerson;
	}
	/**
	 * 设置：删除 0：正常，1：删除
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * 获取：删除 0：正常，1：删除
	 */
	public Integer getDr() {
		return dr;
	}
	/**
	 * 设置：序号
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：序号
	 */
	public Integer getOrderNum() {
		return orderNum;
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
	 * 设置：同步状态，0：未同步；1：已同步
	 */
	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}
	/**
	 * 获取：同步状态，0：未同步；1：已同步
	 */
	public Integer getSyncStatus() {
		return syncStatus;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
