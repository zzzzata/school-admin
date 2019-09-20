package io.renren.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TeachStudentFilesDetailEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 资料类型名称
     */
    private String name;

    /**
     * 交付件关联KEY
     */
    private String fileKey;

    /**
     * 学员课程资料PK
     */
    private Long teachStudentFilesId;

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
     * 下载次数
     */
    private Long downloadNum;

    /**
     * 查看次数
     */
    private Long viewNum;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public void setTeachStudentFilesId(Long teachStudentFilesId) {
        this.teachStudentFilesId = teachStudentFilesId;
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

    public void setDownloadNum(Long downloadNum) {
        this.downloadNum = downloadNum;
    }

    public void setViewNum(Long viewNum) {
        this.viewNum = viewNum;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFileKey() {
        return fileKey;
    }

    public Long getTeachStudentFilesId() {
        return teachStudentFilesId;
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

    public Long getDownloadNum() {
        return downloadNum;
    }

    public Long getViewNum() {
        return viewNum;
    }
}
