package io.renren.pojo;

import java.util.Date;

public class ReturnVisitPOJO {
    private static final long serialVersionUID = 1L;

    //编号
    private Long id;
    //回访人
    private Long createperson;
    //预计回访时间
    private String expectTime;
    //回访时间
    private String returnTime;
    //回访状态
    private Integer returnStatus;
    //回访内容
    private String returnContent;
    //报读主键
    private Long recordSignId;
    //删除
    private Integer dr;
    //回访人
    private String createPersonName;
    //学员号码
    private String mobile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateperson() {
        return createperson;
    }

    public void setCreateperson(Long createperson) {
        this.createperson = createperson;
    }

    public String getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getReturnContent() {
        return returnContent;
    }

    public void setReturnContent(String returnContent) {
        this.returnContent = returnContent;
    }

    public Long getRecordSignId() {
        return recordSignId;
    }

    public void setRecordSignId(Long recordSignId) {
        this.recordSignId = recordSignId;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public String getCreatePersonName() {
        return createPersonName;
    }

    public void setCreatePersonName(String createPersonName) {
        this.createPersonName = createPersonName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
