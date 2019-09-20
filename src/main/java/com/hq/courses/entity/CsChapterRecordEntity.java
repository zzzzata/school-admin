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
 * @date 2019-04-19 15:18:38
 */
public class CsChapterRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
    private Long chapterRecordId;
	//章id
	private Long chapterId;
	//章节名称
	private String chapterName;
	//章节编码
	private String chapterNo;
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
	//创建者id
    private Long createUserId;

	/**
	 * 设置：自增id
	 */
	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}
	/**
	 * 获取：自增id
	 */
	public Long getChapterId() {
		return chapterId;
	}
	/**
	 * 设置：章节名称
	 */
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	/**
	 * 获取：章节名称
	 */
	public String getChapterName() {
		return chapterName;
	}
	/**
	 * 设置：章节编码
	 */
	public void setChapterNo(String chapterNo) {
		this.chapterNo = chapterNo;
	}
	/**
	 * 获取：章节编码
	 */
	public String getChapterNo() {
		return chapterNo;
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

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }


    public Long getChapterRecordId() {
        return chapterRecordId;
    }

    public void setChapterRecordId(Long chapterRecordId) {
        this.chapterRecordId = chapterRecordId;
    }

    public static CsChapterRecordEntity getRecordEntityByEntity(CsChapterEntity entity){
	    CsChapterRecordEntity recordEntity = new CsChapterRecordEntity();
	    if (entity != null){
	        recordEntity.setChapterId(entity.getChapterId());
	        recordEntity.setChapterName(entity.getChapterName());
	        recordEntity.setChapterNo(entity.getChapterNo());
	        recordEntity.setCourseId(entity.getCourseId());
	        recordEntity.setCreateTime(new Date());
	        recordEntity.setUpdateTime(new Date());
            SysUserEntity userEntity =  ShiroUtils.getUserEntity();
            if (userEntity != null){
                recordEntity.setCreateUserId(userEntity.getUserId());
            }
	        recordEntity.setDr(entity.getDr());
            recordEntity.setOrderNum(entity.getOrderNum());
        }
	    return recordEntity;
    }
}
