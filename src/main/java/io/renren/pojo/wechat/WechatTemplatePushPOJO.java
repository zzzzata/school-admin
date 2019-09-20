package io.renren.pojo.wechat;

import java.io.Serializable;
import java.util.Map;

/**
 *  微信推送消息组装消息实体类
 * Created by DL on 2019/6/3.
 */
public class WechatTemplatePushPOJO implements Serializable {
    //接收用户(openid)
    private String touser;
    //模板消息id
    private String template_id;
    //模板跳转链接（海外帐号没有跳转能力）
    private String url;
    //跳小程序所需数据，不需跳小程序可不用传该数据
    private Map<String,Object> miniprogram;
    //模板内容数据
    private Map<String,Object> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(Map<String, Object> miniprogram) {
        this.miniprogram = miniprogram;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
