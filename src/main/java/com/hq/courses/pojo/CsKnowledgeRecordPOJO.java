package com.hq.courses.pojo;

import java.io.Serializable;
import java.security.SecureRandom;

/**
 * Created by DL on 2019/4/24.
 */
public class CsKnowledgeRecordPOJO implements Serializable {
    //
    private Long knowledgeRecordId;
    //z知识点id
    private Long knowledgeId;
    //名称
    private String knowledgeName;
    //编码
    private String knowledgeNo;
    //考点:1.正常;2.考点
    private Integer keyPoint;
    //难度PK，请参考静态变量表
    private Integer levelId;
    //所属节PK
    private Long sectionId;
    //所属节编码
    private String sectionNo;
    //所属节名称
    private String sectionName;
    //课程PK
    private Long courseId;
    //课程名称
    private String courseName;
    //创建者id
    private Long userId;
    //创建者名称
    private String userName;
    //创建时间
    private String updateTime;

    public Long getKnowledgeRecordId() {
        return knowledgeRecordId;
    }

    public void setKnowledgeRecordId(Long knowledgeRecordId) {
        this.knowledgeRecordId = knowledgeRecordId;
    }

    public Long getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getKnowledgeName() {
        return knowledgeName;
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName;
    }

    public String getKnowledgeNo() {
        return knowledgeNo;
    }

    public void setKnowledgeNo(String knowledgeNo) {
        this.knowledgeNo = knowledgeNo;
    }

    public Integer getKeyPoint() {
        return keyPoint;
    }

    public void setKeyPoint(Integer keyPoint) {
        this.keyPoint = keyPoint;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionNo() {
        return sectionNo;
    }

    public void setSectionNo(String sectionNo) {
        this.sectionNo = sectionNo;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
