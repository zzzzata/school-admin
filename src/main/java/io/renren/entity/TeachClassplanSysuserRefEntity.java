package io.renren.entity;

import java.util.Date;

/**
 * User: mumu
 * Date: 2018/12/11
 * Time: 16:16
 */
public class TeachClassplanSysuserRefEntity {

    private String courseClassplanId;
    private long assistantTeacherId;
    private long sysUserId;
    private Date createTime;

    private String teacherName;

    public TeachClassplanSysuserRefEntity(){

    }

    public TeachClassplanSysuserRefEntity(
            String courseClassplanId,
            long assistantTeacherId,
            long sysUserId){
        this.sysUserId = sysUserId;
        this.courseClassplanId = courseClassplanId;
        this.assistantTeacherId = assistantTeacherId;
    }

    public String getCourseClassplanId() {
        return courseClassplanId;
    }

    public void setCourseClassplanId(String courseClassplanId) {
        this.courseClassplanId = courseClassplanId;
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
