package io.renren.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class HangjiaCommodityPOJO {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 蓝鲸课程id
     */
    private Long courseId;

    /**
     * 商品名称 商品名称
     */
    private String name;

    /**
     * 封面图片链接
     */
    private String coverPicture;

    /**
     * 视频地址或7张图片（图片地址逗号隔开存储）
     */
    private String dynamicCover;

    /**
     * 动态封面类型（1：多张图片；2：视频）
     */
    private Integer dynamicCoverType;

    /**
     * 商品类目id
     */
    private Long goodsCategoryId;

    /**
     * 商品类型 商品类型（1:录播;2:直播或其它）
     */
    private Integer type;

    /**
     * 价格 商品价格
     */
    private BigDecimal price;

    /**
     * 商品有效期单位：月（-1：无限）
     */
    private Integer validityDate;

    /**
     * 商品简介（支持富文本）
     */
    private String introduction;

    /**
     * 发布状态（2：未发布；1：上架；0：下架；4：停售）
     */
    private Integer issueStatus;


    /**
     * 是否删除（0：未删除；1：已删除）
     */
    private Boolean isDelete;

    /**
     * 创建人
     */
    private Long creatorId;

    /**
     * 上下架时间
     */
    private Date issueTime;


    /**
     * 创建时间 创建时间
     */
    private Date createTime;

    /**
     * 更新时间 更新时间
     */
    private Date updateTime;

    /**
     * 商品项目类型 （1：行家;2：子墨学院）
     */
    private Integer projectType;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getDynamicCover() {
        return dynamicCover;
    }

    public void setDynamicCover(String dynamicCover) {
        this.dynamicCover = dynamicCover;
    }

    public Integer getDynamicCoverType() {
        return dynamicCoverType;
    }

    public void setDynamicCoverType(Integer dynamicCoverType) {
        this.dynamicCoverType = dynamicCoverType;
    }

    public Long getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(Long goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(Integer issueStatus) {
        this.issueStatus = issueStatus;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Integer validityDate) {
        this.validityDate = validityDate;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    @Override
    public String toString() {
        return "HangjiaCommodityPOJO{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", name='" + name + '\'' +
                ", coverPicture='" + coverPicture + '\'' +
                ", dynamicCover='" + dynamicCover + '\'' +
                ", dynamicCoverType=" + dynamicCoverType +
                ", goodsCategoryId=" + goodsCategoryId +
                ", type=" + type +
                ", price=" + price +
                ", validityDate=" + validityDate +
                ", introduction='" + introduction + '\'' +
                ", issueStatus=" + issueStatus +
                ", isDelete=" + isDelete +
                ", creatorId=" + creatorId +
                ", issueTime=" + issueTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", projectType=" + projectType +
                '}';
    }
}
