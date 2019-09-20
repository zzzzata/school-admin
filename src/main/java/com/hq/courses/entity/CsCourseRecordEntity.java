package com.hq.courses.entity;

import io.renren.entity.SysUserEntity;
import io.renren.utils.ShiroUtils;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-19 15:18:43
 */
public class CsCourseRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
    private Long courseRecordId;
	//课程id
	private Long courseId;
	//编号
	private String courseNo;
	//名称
	private String courseName;
	//产品线
	private Long productId;
	//公司
	private Long deptId;
	//启用状态:0.作废;1.正常
	private Integer status;
	//dr
	private Integer dr;
	//备注
	private String remark;
	//更新时间戳
	private Date updateTime;
	//创建时间戳
	private Date createTime;
	//ncId
	private String ncId;
	//蓝鲸ID
	private String ljId;
	//题库Id
	private String tkId;
	//自适应课程；0:否 1:是
	private Integer adaptiveType;
	//审核状态；0:待审核 1:通过
	private Integer auditStatus;
	//课次审核状态；0:待审核 1:通过
	private Integer liveAuditStatus;
	//创建人id
    private Long createUserId;

	/**
	 * 设置：主键
	 */
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	/**
	 * 获取：主键
	 */
	public Long getCourseId() {
		return courseId;
	}
	/**
	 * 设置：编号
	 */
	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}
	/**
	 * 获取：编号
	 */
	public String getCourseNo() {
		return courseNo;
	}
	/**
	 * 设置：名称
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * 获取：名称
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * 设置：产品线
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	/**
	 * 获取：产品线
	 */
	public Long getProductId() {
		return productId;
	}
	/**
	 * 设置：公司
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：公司
	 */
	public Long getDeptId() {
		return deptId;
	}
	/**
	 * 设置：启用状态:0.作废;1.正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：启用状态:0.作废;1.正常
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：dr
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * 获取：dr
	 */
	public Integer getDr() {
		return dr;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
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
	 * 设置：ncId
	 */
	public void setNcId(String ncId) {
		this.ncId = ncId;
	}
	/**
	 * 获取：ncId
	 */
	public String getNcId() {
		return ncId;
	}
	/**
	 * 设置：蓝鲸ID
	 */
	public void setLjId(String ljId) {
		this.ljId = ljId;
	}
	/**
	 * 获取：蓝鲸ID
	 */
	public String getLjId() {
		return ljId;
	}
	/**
	 * 设置：题库Id
	 */
	public void setTkId(String tkId) {
		this.tkId = tkId;
	}
	/**
	 * 获取：题库Id
	 */
	public String getTkId() {
		return tkId;
	}
	/**
	 * 设置：自适应课程；0:否 1:是
	 */
	public void setAdaptiveType(Integer adaptiveType) {
		this.adaptiveType = adaptiveType;
	}
	/**
	 * 获取：自适应课程；0:否 1:是
	 */
	public Integer getAdaptiveType() {
		return adaptiveType;
	}
	/**
	 * 设置：审核状态；0:待审核 1:通过
	 */
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	/**
	 * 获取：审核状态；0:待审核 1:通过
	 */
	public Integer getAuditStatus() {
		return auditStatus;
	}
	/**
	 * 设置：课次审核状态；0:待审核 1:通过
	 */
	public void setLiveAuditStatus(Integer liveAuditStatus) {
		this.liveAuditStatus = liveAuditStatus;
	}
	/**
	 * 获取：课次审核状态；0:待审核 1:通过
	 */
	public Integer getLiveAuditStatus() {
		return liveAuditStatus;
	}

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getCourseRecordId() {
        return courseRecordId;
    }

    public void setCourseRecordId(Long courseRecordId) {
        this.courseRecordId = courseRecordId;
    }

    public static CsCourseRecordEntity getRecordEntityByEntity(CsCourseEntity entity){
	    CsCourseRecordEntity recordEntity = new CsCourseRecordEntity();
	    if (entity != null){
            recordEntity.setAdaptiveType(entity.getAdaptiveType());
            recordEntity.setAuditStatus(entity.getAuditStatus());
            recordEntity.setCourseId(entity.getCourseId());
            recordEntity.setCourseName(entity.getCourseName());
            recordEntity.setCourseNo(entity.getCourseNo());
            recordEntity.setCreateTime(new Date());
            recordEntity.setUpdateTime(new Date());
            recordEntity.setDeptId(entity.getDeptId());
            recordEntity.setDr(entity.getDr());
            recordEntity.setLjId(entity.getLjId());
            recordEntity.setLiveAuditStatus(entity.getLiveAuditStatus());
            recordEntity.setNcId(entity.getNcId());
            SysUserEntity userEntity = ShiroUtils.getUserEntity();
            if (userEntity != null){
                recordEntity.setCreateUserId(userEntity.getUserId());
            }
            recordEntity.setProductId(entity.getProductId());
            recordEntity.setRemark(entity.getRemark());
            recordEntity.setStatus(entity.getStatus());
            recordEntity.setTkId(entity.getTkId());
        }
	    return recordEntity;
    }
}
