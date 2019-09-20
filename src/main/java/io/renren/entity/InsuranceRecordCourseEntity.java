package io.renren.entity;

import java.util.Date;
/**
 * 投保记录单课程子表
 * @author lintf
 *
 */
public class InsuranceRecordCourseEntity {
	private Long id;//子表主键
	private Long insuranceRecordId;//主表主键
	private Long userId;//学员ID
	private String subjectInfos;//科目信息
	private String subjectCode;//科目编码
	private String subjectName;//科目名称
	private Double subjectHour;//课程标准课时
	private String examDate;//考期（YYYY-MM）
	private Integer dr;//是否删除 0不删除 1是删除
	private Date creationTime ;
	private Date ts ;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	public Long getInsuranceRecordId() {
		return insuranceRecordId;
	}
	public void setInsuranceRecordId(Long insuranceRecordId) {
		this.insuranceRecordId = insuranceRecordId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSubjectInfos() {
		return subjectInfos;
	}
	public void setSubjectInfos(String subjectInfos) {
		this.subjectInfos = subjectInfos;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Double getSubjectHour() {
		return subjectHour;
	}
	public void setSubjectHour(Double subjectHour) {
		this.subjectHour = subjectHour;
	}
	public Integer getDr() {
		return dr;
	}
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	} 
	
	public void InsuranceRecordCourseEntity( ) { 
		this.dr=0;
		this.subjectHour=0.0;
	}
	
	
	public void updateEntity(InsuranceRecordCourseEntity in) {
		if (this.id!=null&&in!=null) {
			 
			this.examDate=in.getExamDate();			 
			//this.insuranceRecordId =in.getInsuranceRecordId();
			this.subjectCode =in.getSubjectCode();
			this.subjectHour=in.getSubjectHour()==null?0:in.getSubjectHour();
			this.subjectInfos=in.getSubjectInfos();
			this.subjectName =in.getSubjectName();			 
			this.userId=in.getUserId();
		}
		
	}
	
	
}
