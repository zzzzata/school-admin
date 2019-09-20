package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 商品表格详细信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-29 10:11:55
 */
public class MallGoodsDetailsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Long id;
	//商品ID
	private Long mallGoodsId;
	//省份
	private Long mallAreaId;
	//课程ID
	private Long courseId;
	//被替代课程
	private Integer isSubstituted;
	//代替课程
	private Integer isSubstitute;
	//是否统考
	private Integer isUnitedExam;
	//专业不对口课程
	private Integer isSuitable;
	//平台ID
	private String schoolId;
	//排序
	private Integer orderNum;
	//dr
	private Integer dr;
	//课时 （保险单科的用到）
	private Double subjectHour;
	//是否投保的科目  0 不是 1是
	private Integer insuranceCourseStatus;
	//行家商品ID
	private Long hjGoodsId;

	public Long getHjGoodsId() {
		return hjGoodsId;
	}

	public void setHjGoodsId(Long hjGoodsId) {
		this.hjGoodsId = hjGoodsId;
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
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 设置：主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：商品ID
	 */
	public void setMallGoodsId(Long mallGoodsId) {
		this.mallGoodsId = mallGoodsId;
	}
	/**
	 * 获取：商品ID
	 */
	public Long getMallGoodsId() {
		return mallGoodsId;
	}
	/**
	 * 设置：省份
	 */
	public void setMallAreaId(Long mallAreaId) {
		this.mallAreaId = mallAreaId;
	}
	/**
	 * 获取：省份
	 */
	public Long getMallAreaId() {
		return mallAreaId;
	}
	/**
	 * 设置：课程ID
	 */
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	/**
	 * 获取：课程ID
	 */
	public Long getCourseId() {
		return courseId;
	}
	/**
	 * 设置：被替代课程
	 */
	public void setIsSubstituted(Integer isSubstituted) {
		this.isSubstituted = isSubstituted;
	}
	/**
	 * 获取：被替代课程
	 */
	public Integer getIsSubstituted() {
		return isSubstituted;
	}
	/**
	 * 设置：代替课程
	 */
	public void setIsSubstitute(Integer isSubstitute) {
		this.isSubstitute = isSubstitute;
	}
	/**
	 * 获取：代替课程
	 */
	public Integer getIsSubstitute() {
		return isSubstitute;
	}
	/**
	 * 设置：是否统考
	 */
	public void setIsUnitedExam(Integer isUnitedExam) {
		this.isUnitedExam = isUnitedExam;
	}
	/**
	 * 获取：是否统考
	 */
	public Integer getIsUnitedExam() {
		return isUnitedExam;
	}
	/**
	 * 设置：专业不对口课程
	 */
	public void setIsSuitable(Integer isSuitable) {
		this.isSuitable = isSuitable;
	}
	/**
	 * 获取：专业不对口课程
	 */
	public Integer getIsSuitable() {
		return isSuitable;
	}
	
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	
	
	 
	public Integer getInsuranceCourseStatus() {
		return insuranceCourseStatus;
	}
	public void setInsuranceCourseStatus(Integer insuranceCourseStatus) {
		this.insuranceCourseStatus = insuranceCourseStatus;
	}
	@Override
	public String toString() {
		return "MallGoodsDetailsEntity [id=" + id + ", mallGoodsId=" + mallGoodsId + ", mallAreaId=" + mallAreaId + ", courseId=" + courseId
				+ ", isSubstituted=" + isSubstituted + ", isSubstitute=" + isSubstitute + ", isUnitedExam=" + isUnitedExam + ", isSuitable="
				+ isSuitable + ", schoolId=" + schoolId + "]";
	}
	
}
