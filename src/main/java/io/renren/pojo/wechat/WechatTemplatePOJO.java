package io.renren.pojo.wechat;

import java.io.Serializable;

/**
 * Created by DL on 2019/5/22.
 */
public class WechatTemplatePOJO implements Serializable{
    //模板id
    private String templateId;
    //模板名称
    private String title;
    //模板内容
    private String content;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
