package io.renren.entity.courseliverelaterecord;

import java.io.Serializable;
import java.util.Date;

/**
 * 直播课次与录播章节对照关系
 * @author chen xin yu
 * @date 2019-06-18 11:25
 */
public class CourseLiveRelateRecordEntity  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    private Long id;
    /**课程id*/
    private Long courseId;
    /**直播课次id*/
    private String liveId;
    /**录播课次id （章或者节）多个前后逗号隔开*/
    private Long recordId;
    /**状态：1 启用 0 停用*/
    private Integer state;
    /**1 删除 0 正常*/
    private Integer dr;
    /**创建人id*/
    private Long createUserId;
    /**创建时间*/
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public CourseLiveRelateRecordEntity() {}

    public CourseLiveRelateRecordEntity(Long id, Long courseId, String liveId, Long recordId, Integer state, Integer dr) {
        this.id = id;
        this.courseId = courseId;
        this.liveId = liveId;
        this.recordId = recordId;
        this.state = state;
        this.dr = dr;
    }
}
