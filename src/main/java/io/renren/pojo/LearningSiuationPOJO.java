package io.renren.pojo;

import io.renren.utils.DateUtils;

import java.util.Date;

public class LearningSiuationPOJO {
    //编号
    private Long id;
    //课程id
    private Long courseId;
    //课程名称
    private String courseName;
    //订单id
    private Long orderId;
    //用户id
    private Long userId;
    //学员规划id
    private Long userPlanId;
    //参考日期
    private String referenceDate;
    //创建日期
    private Date createDate;
    //创建日期
    private String createMonth;
    //听课完成率
    private Double attendPer;
    //作业达标率
    private Double jobPer;
    //班主任评价
    private String teacherAssess;
    //校区协助
    private String schoolAssist;
    //排课计划名称
    private String classplanName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserPlanId() {
        return userPlanId;
    }

    public void setUserPlanId(Long userPlanId) {
        this.userPlanId = userPlanId;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getAttendPer() {
        return attendPer;
    }

    public void setAttendPer(Double attendPer) {
        this.attendPer = attendPer;
    }

    public Double getJobPer() {
        return jobPer;
    }

    public void setJobPer(Double jobPer) {
        this.jobPer = jobPer;
    }

    public String getTeacherAssess() {
        return teacherAssess;
    }

    public void setTeacherAssess(String teacherAssess) {
        this.teacherAssess = teacherAssess;
    }

    public String getSchoolAssist() {
        return schoolAssist;
    }

    public void setSchoolAssist(String schoolAssist) {
        this.schoolAssist = schoolAssist;
    }

    public String getClassplanName() {
        return classplanName;
    }

    public void setClassplanName(String classplanName) {
        this.classplanName = classplanName;
    }

    public String getCreateMonth() {
        return createMonth;
    }

    public void setCreateMonth(String createMonth) {
        this.createMonth = createMonth;
    }
}
