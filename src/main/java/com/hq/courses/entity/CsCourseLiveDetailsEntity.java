package com.hq.courses.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 课次
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-15 11:08:20
 */
public class CsCourseLiveDetailsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long liveId;
	//课次名称
	private String liveName;
	//上期复习文件
	private String reviewName;
	//期复习文件地址
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


	public Long getLiveId() {
		return liveId;
	}

	public void setLiveId(Long liveId) {
		this.liveId = liveId;
	}

	public String getLiveName() {
		return liveName;
	}

	public void setLiveName(String liveName) {
		this.liveName = liveName;
	}

	public String getReviewName() {
		return reviewName;
	}

	public void setReviewName(String reviewName) {
		this.reviewName = reviewName;
	}

	public String getReviewUrl() {
		return reviewUrl;
	}

	public void setReviewUrl(String reviewUrl) {
		this.reviewUrl = reviewUrl;
	}

	public String getPrepareName() {
		return prepareName;
	}

	public void setPrepareName(String prepareName) {
		this.prepareName = prepareName;
	}

	public String getPrepareUrl() {
		return prepareUrl;
	}

	public void setPrepareUrl(String prepareUrl) {
		this.prepareUrl = prepareUrl;
	}

	public String getCoursewareName() {
		return coursewareName;
	}

	public void setCoursewareName(String coursewareName) {
		this.coursewareName = coursewareName;
	}

	public String getCoursewareUrl() {
		return coursewareUrl;
	}

	public void setCoursewareUrl(String coursewareUrl) {
		this.coursewareUrl = coursewareUrl;
	}

	public Long getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Long phaseId) {
		this.phaseId = phaseId;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(Long createPerson) {
		this.createPerson = createPerson;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(Long updatePerson) {
		this.updatePerson = updatePerson;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
}
