package com.hq.courses.entity;

import com.hq.courses.pojo.CsSectionPOJO;
import io.renren.entity.SysUserEntity;
import io.renren.utils.ShiroUtils;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-19 15:18:57
 */
public class CsSectionRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
    private Long sectionRecordId;
	//节ID
	private Long sectionId;
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
	//章PK
	private Long chapterId;
	//创建者
    private Long createUserId;

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
	/**
	 * 设置：章PK
	 */
	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}
	/**
	 * 获取：章PK
	 */
	public Long getChapterId() {
		return chapterId;
	}

    public Long getSectionRecordId() {
        return sectionRecordId;
    }

    public void setSectionRecordId(Long sectionRecordId) {
        this.sectionRecordId = sectionRecordId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public static CsSectionRecordEntity getRecordEntityByPOJO(CsSectionPOJO pojo){
	    CsSectionRecordEntity recordEntity = new CsSectionRecordEntity();
	    if (pojo != null){
	        recordEntity.setChapterId(pojo.getChapterId());
	        recordEntity.setCourseId(pojo.getCourseId());
	        recordEntity.setCreateTime(new Date());
	        recordEntity.setUpdateTime(new Date());
            SysUserEntity userEntity = ShiroUtils.getUserEntity();
            if(userEntity != null){
                recordEntity.setCreateUserId(userEntity.getUserId());
            }
            recordEntity.setDr(pojo.getDr());
            recordEntity.setOrderNum(pojo.getOrderNum());
            recordEntity.setSectionId(pojo.getSectionId());
            recordEntity.setSectionName(pojo.getSectionName());
            recordEntity.setSectionNo(pojo.getSectionNo());
        }
	    return recordEntity;
    }
}
