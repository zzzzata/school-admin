package io.renren.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 录播课程离线考勤明细数据
 */
public class RecordCourseCallbackDetailEntity  implements Serializable {

    //主键
    private Long recordCallbackDetailId;

    //录播课程章节id
    private Long recordId;

    //录播课程id
    private Long courseId;

    //录播视频id
    private String videoId;

    //学员id
    private Long userId;

    //观看视频-加入时间（毫秒)
    private Long joinTime;

    //观看视频-离开时间
    private Long leaveTime;

    //观看记录同步到服务端的时间
    private Date createTime;

    //视频进度开始时间
    private Long videoStartTime;

    //视频进度结束时间
    private Long videoEndTime;

    //视频总时长
    private Long videoTotalTime;

    //是否离线(缓存):0离线1回放回调
    private Integer isOfflive;

    //产品线
    private Long productId;

    //录播考勤系数
    private Float recordCoefficient;

    public Long getRecordCallbackDetailId() {
        return recordCallbackDetailId;
    }

    public void setRecordCallbackDetailId(Long recordCallbackDetailId) {
        this.recordCallbackDetailId = recordCallbackDetailId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId == null ? null : videoId.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Long joinTime) {
        this.joinTime = joinTime;
    }

    public Long getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Long leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getVideoStartTime() {
        return videoStartTime;
    }

    public void setVideoStartTime(Long videoStartTime) {
        this.videoStartTime = videoStartTime;
    }

    public Long getVideoEndTime() {
        return videoEndTime;
    }

    public void setVideoEndTime(Long videoEndTime) {
        this.videoEndTime = videoEndTime;
    }

    public Long getVideoTotalTime() {
        return videoTotalTime;
    }

    public void setVideoTotalTime(Long videoTotalTime) {
        this.videoTotalTime = videoTotalTime;
    }

    public Integer getIsOfflive() {
        return isOfflive;
    }

    public void setIsOfflive(Integer isOfflive) {
        this.isOfflive = isOfflive;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Float getRecordCoefficient() {
        return recordCoefficient;
    }

    public void setRecordCoefficient(Float recordCoefficient) {
        this.recordCoefficient = recordCoefficient;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}