package io.renren.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TeachStudentFilesEntity {
    /**
     * ID
     */
    private String id;

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

    /**
     * 商品PK
     */
    private Long mallGoodsInfoId;

    /**
     * 班型PK值存储方式如：,1,2,3,4,
     */
    private String mallClassTypeId;

    /**
     * 资料名称
     */
    private String name;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
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
     * 最近修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date lastModifyTime;

    /**
     * 最近修改用户
     */
    private Long modifySysUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setMallProfessionId(Long mallProfessionId) {
        this.mallProfessionId = mallProfessionId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setMallGoodsInfoId(Long mallGoodsInfoId) {
        this.mallGoodsInfoId = mallGoodsInfoId;
    }

    public void setMallClassTypeId(String mallClassTypeId) {
        this.mallClassTypeId = mallClassTypeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public void setModifySysUserId(Long modifySysUserId) {
        this.modifySysUserId = modifySysUserId;
    }



    public Long getProductId() {
        return productId;
    }

    public Long getMallProfessionId() {
        return mallProfessionId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getMallGoodsInfoId() {
        return mallGoodsInfoId;
    }

    public String getMallClassTypeId() {
        return mallClassTypeId;
    }

    public String getName() {
        return name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public Long getModifySysUserId() {
        return modifySysUserId;
    }
}
