package io.renren.pojo.ask;

import io.renren.entity.ask.TeacherAskTipEntity;

/**
 * 教师提问标签权限 PO类
 * @author chen xin yu
 * @date 2019-05-05 09:39
 */
public class TeacherAskTipPOJO extends TeacherAskTipEntity {

    /** 教师名称 */
    private String nickName;
    /**手机号码*/
    private String mobile;
    /**父级标签名称串*/
    private String parentTipNames;
    /**子级标签名称串*/
    private String childTipNames;

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

    public String getParentTipNames() {
        return parentTipNames;
    }

    public void setParentTipNames(String parentTipNames) {
        this.parentTipNames = parentTipNames;
    }

    public String getChildTipNames() {
        return childTipNames;
    }

    public void setChildTipNames(String childTipNames) {
        this.childTipNames = childTipNames;
    }
}
