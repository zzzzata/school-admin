package io.renren.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 交付件
 * </p>
 *
 * @author hzr
 * @since 2018-11-27
 */
public class TeachFileEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片描述
     */
    private String descs;

    /**
     * UUID
     */
    private String fileKey;

    /**
     * URL地址
     */
    private String url;

    /**
     * 文件后缀名
     */
    private String suffix;

    /**
     * PPT为10、文档为20、视频为30、音频为40、图片为50
     */
    private Integer type;

    /**
     * 大小
     */
    private Long size;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    /**
     * -1为删除状态，0为初始状态，1为正常状态，默认为1
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

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
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

    public Long getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(Long downloadNum) {
        this.downloadNum = downloadNum;
    }

    public Long getViewNum() {
        return viewNum;
    }

    public void setViewNum(Long viewNum) {
        this.viewNum = viewNum;
    }

    @Override
    public String toString() {
        return "TeachFileEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", descs='" + descs + '\'' +
                ", fileKey='" + fileKey + '\'' +
                ", url='" + url + '\'' +
                ", suffix='" + suffix + '\'' +
                ", type=" + type +
                ", size=" + size +
                ", createTime=" + createTime +
                ", status=" + status +
                ", sysUserId=" + sysUserId +
                ", downloadNum=" + downloadNum +
                ", viewNum=" + viewNum +
                '}';
    }
}
