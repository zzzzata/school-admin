package com.hq.courses.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DL on 2019/4/23.
 */
public class CsChapterRecordPOJO implements Serializable {

    //
    private Long chapterRecordId;
    //章id
    private Long chapterId;
    //章节名称
    private String chapterName;
    //章节编码
    private String chapterNo;
    //课程PK
    private Long courseId;
    //课程名称
    private String courseName;
    //修改人id
    private Long userId;
    //修改人
    private String userName;
    //修改时间
    private Date updateTime;

    public Long getChapterRecordId() {
        return chapterRecordId;
    }

    public void setChapterRecordId(Long chapterRecordId) {
        this.chapterRecordId = chapterRecordId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(String chapterNo) {
        this.chapterNo = chapterNo;
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
