package io.renren.pojo.wechat;

import java.io.Serializable;
import java.util.Map;

/**
 * 查看消息详情实体类
 * Created by DL on 2019/6/3.
 */
public class WechatMsgDetailPOJO implements Serializable{

    //消息记录id
    private long msgNo;
    //接收人openid
    private String openid;
    //接收人手机号码
    private String mobile;
    //发送内容
    private  String sendContent;
    //发送时间
    private String sendTime;
    //发送状态
    private String state;
    //发送内容
    private String msgContent;
    //发送模板id
    private String template_id;
    //发送消息总量
    private int totalCount;
    //成功发送数量
    private int successCount;
    //失败发送量
    private int failedCount;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public long getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(long msgNo) {
        this.msgNo = msgNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }
}
