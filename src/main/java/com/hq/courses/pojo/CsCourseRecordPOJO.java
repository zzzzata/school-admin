package com.hq.courses.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DL on 2019/4/23.
 */
public class CsCourseRecordPOJO implements Serializable {

    //
    private Long courseRecordId;
    //课程id
    private Long courseId;
    //编号
    private String courseNo;
    //名称
    private String courseName;
    //公司
    private Long deptId;
    //公司
    private String deptName;
    //产品线
    private Long productId;
    //产品线名称
    private String productName;
    //修改人id
    private Long userId;
    //修改人名称
    private String userName;
    //修改时间
    private Date updateTime;

    public Long getCourseRecordId() {
        return courseRecordId;
    }

    public void setCourseRecordId(Long courseRecordId) {
        this.courseRecordId = courseRecordId;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
