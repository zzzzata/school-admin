package io.renren.pojo.wechat;

import java.io.Serializable;

/**
 * 微信推送--上课提醒模板内容参数数据
 * Created by DL on 2019/6/3.
 */
public class WechatTemplateDataValuePOJO implements Serializable{
    //参数内容
    private String value;
    //字体颜色,不填默认黑色
    private String color;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
