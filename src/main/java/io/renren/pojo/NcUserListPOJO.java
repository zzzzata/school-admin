package io.renren.pojo;

import java.io.Serializable;

/**
 * Created by DL on 2019/3/26.
 */
public class NcUserListPOJO implements Serializable {
    //学员姓名
    private String userName;
    //学员手机
    private String mobile;
    //学习计划单号
    private String learningNo;
    //入课时间
    private String timeStamp;
    //报读班型名称
    private String ncClassType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLearningNo() {
        return learningNo;
    }

    public void setLearningNo(String learningNo) {
        this.learningNo = learningNo;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNcClassType() {
        return ncClassType;
    }

    public void setNcClassType(String ncClassType) {
        this.ncClassType = ncClassType;
    }

    @Override
    public String toString() {
        return "NcUserListPOJO{" +
                "userName='" + userName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", learningNo='" + learningNo + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", ncClassType='" + ncClassType + '\'' +
                '}';
    }

}
