package io.renren.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 学员使用的课件资料
 * </p>
 *
 * @author hzr
 * @since 2018-11-30
 */
public class TeachClassplanBaseFilesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 资料名称
     */
    private String name;

    /**
     * 交付件关联KEY
     */
    private String fileKey;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * -1为删除，0为下架，1为上架
     */
    private Integer status;

    /**
     * 创建者
     */
    private Long sysUserId;

    /**
     * 阶段
     */
    private Integer stageCode;

    /**
     * 直播基础课次PK
     */
    private Long courseLiveDetailsId;

    /**
     * 产品线PK
     */
    private Long productId;

    /**
     * 专业PK
     */
    private Long mallProfessionId;

    /**
     * 课程PK
     */
    private Long courseId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Integer getStageCode() {
        return stageCode;
    }

    public void setStageCode(Integer stageCode) {
        this.stageCode = stageCode;
    }

    public Long getCourseLiveDetailsId() {
        return courseLiveDetailsId;
    }

    public void setCourseLiveDetailsId(Long courseLiveDetailsId) {
        this.courseLiveDetailsId = courseLiveDetailsId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getMallProfessionId() {
        return mallProfessionId;
    }

    public void setMallProfessionId(Long mallProfessionId) {
        this.mallProfessionId = mallProfessionId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "TeachClassplanBaseFilesEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fileKey='" + fileKey + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                ", sysUserId=" + sysUserId +
                ", stageCode=" + stageCode +
                ", courseLiveDetailsId=" + courseLiveDetailsId +
                ", productId=" + productId +
                ", mallProfessionId=" + mallProfessionId +
                ", courseId=" + courseId +
                '}';
    }
}
