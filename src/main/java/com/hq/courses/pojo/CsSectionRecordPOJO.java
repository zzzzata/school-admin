package com.hq.courses.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DL on 2019/4/24.
 */
public class CsSectionRecordPOJO implements Serializable {

    //
    private Long sectionRecordId;
    //节ID
    private Long sectionId;
    //名称
    private String sectionName;
    //编码
    private String sectionNo;
    //课程PK
    private Long courseId;
    //课程名称
    private String courseName;
    //创建人id
    private Long userId;
    //创建人
    private String userName;
    //更新时间戳
    private Date updateTime;

    public Long getSectionRecordId() {
        return sectionRecordId;
    }

    public void setSectionRecordId(Long sectionRecordId) {
        this.sectionRecordId = sectionRecordId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionNo() {
        return sectionNo;
    }

    public void setSectionNo(String sectionNo) {
        this.sectionNo = sectionNo;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
