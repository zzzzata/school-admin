package io.renren.entity;

import java.util.Date;

/**
 * User: mumu
 * Date: 2018/12/11
 * Time: 16:16
 */
public class TeachClassplanLivesSysuserRefEntity {

    private String courseClassplanLivesId;
    private long assistantTeacherId;
    private long sysUserId;
    private Date createTime;

    private String teacherName;

    public TeachClassplanLivesSysuserRefEntity(){

    }

    public TeachClassplanLivesSysuserRefEntity(
            String courseClassplanLivesId,
            long assistantTeacherId,
            long sysUserId){
        this.sysUserId = sysUserId;
        this.courseClassplanLivesId = courseClassplanLivesId;
        this.assistantTeacherId = assistantTeacherId;
    }

    public String getCourseClassplanLivesId() {
        return courseClassplanLivesId;
    }

    public void setCourseClassplanLivesId(String courseClassplanLivesId) {
        this.courseClassplanLivesId = courseClassplanLivesId;
    }

    public long getAssistantTeacherId() {
        return assistantTeacherId;
    }

    public void setAssistantTeacherId(long assistantTeacherId) {
        this.assistantTeacherId = assistantTeacherId;
    }

    public long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
