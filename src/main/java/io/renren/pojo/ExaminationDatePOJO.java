package io.renren.pojo;


import io.renren.enums.CourseAbormalTypeEnum;
import io.renren.enums.ExamStateEnum;
import io.renren.utils.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * 学员考期表
 *
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-13 09:18:16
 */
public class ExaminationDatePOJO {
    private static final long serialVersionUID = 1L;

    //用户id
    private Long userId;
    //订单id
    private Long orderId;
    //课程id
    private Long courseId;
    //课程编码
    private String courseNo;
    //课程名称
    private String courseName;
    //学习状态:0 在读 1休学 2失联
    private Long learnState;
    //考试状态0-- 1报考 2弃考 3免考
    private Long examState;
    //报考时间id
    private Long examScheduleId;
    //支付时间
    private Date payTime;
    //nc同步code值
    private String ncCode;
    //省份
    private String areaName;
    //层次
    private String levelName;
    //班级
    private String className;
    //班型
    private String classtypeName;
    //用户昵称
    private String nickName;
    //手机号码
    private String mobile;
    //身份证
    private String idCard;

    //专业
    private String professionName;
    //产品线PK
    private String productName;
    //部门PK
    private String deptName;

    //班主任
    private String teacherName;

    //考期
    private String scheduleDate;

    private String learnStateStr;

    private String examStateStr;

    private String payTimeStr;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getLearnState() {
        return learnState;
    }

    public void setLearnState(Long learnState) {
        this.learnState = learnState;
    }

    public Long getExamState() {
        return examState;
    }

    public void setExamState(Long examState) {
        this.examState = examState;
    }

    public Long getExamScheduleId() {
        return examScheduleId;
    }

    public void setExamScheduleId(Long examScheduleId) {
        this.examScheduleId = examScheduleId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayTimeStr() {
        return DateUtils.format(payTime);
    }

    public void setPayTimeStr(String payTimeStr) {
        this.payTimeStr = payTimeStr;
    }

    public String getNcCode() {
        return ncCode;
    }

    public void setNcCode(String ncCode) {
        this.ncCode = ncCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClasstypeName() {
        return classtypeName;
    }

    public void setClasstypeName(String classtypeName) {
        this.classtypeName = classtypeName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getLearnStateStr() {
        return CourseAbormalTypeEnum.getE(learnState.intValue()).getText();
    }

    public void setLearnStateStr(String learnStateStr) {
        this.learnStateStr = learnStateStr;
    }

    public String getExamStateStr() {
        return ExamStateEnum.getE(examState.intValue()).getText();
    }

    public void setExamStateStr(String examStateStr) {
        this.examStateStr = examStateStr;
    }
}
