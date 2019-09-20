package io.renren.pojo;

import java.util.Date;

/**
 * Created by DL on 2019/4/15.
 */
public class NcSchoolLearningDetailPOJO {
    //id
    private  Long learningDetailId;
    //nc课程id
    private String courseId;
    //nc课程名称
    private String courseName;
    //线下排课id
    private String classplanId;
    //线下排课名称
    private String classplanName;
    //学员入课时间
    private Date timeStamp;

    public Long getLearningDetailId() {
        return learningDetailId;
    }

    public void setLearningDetailId(Long learningDetailId) {
        this.learningDetailId = learningDetailId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassplanId() {
        return classplanId;
    }

    public void setClassplanId(String classplanId) {
        this.classplanId = classplanId;
    }

    public String getClassplanName() {
        return classplanName;
    }

    public void setClassplanName(String classplanName) {
        this.classplanName = classplanName;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
