package io.renren.utils;

import com.alibaba.fastjson.JSONObject;
import io.renren.pojo.wechat.WechatTemplateDataValuePOJO;
import io.renren.pojo.wechat.WechatTemplatePushPOJO;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * 微信推送消息工具类,组装不同消息模板的消息内容
 * Created by DL on 2019/6/3.
 */
public class WechatTemplatePushUtil {

    /**
     *  上课提醒模板,该模板有3个参数
     * @param templateId 模板id
     * @param localKeyword1 第一个参数
     * @param localKeyword2 第二个参数
     * @param localKeyword3 第三个参数
     * @param localKeyword4 第四个参数
     * @return
     */
    public static String getShangkeTemplateMsg(String templateId,String localKeyword1,String localKeyword2,String localKeyword3,String localKeyword4){
        WechatTemplatePushPOJO pushPOJO = new WechatTemplatePushPOJO();
        pushPOJO.setTouser("");
        pushPOJO.setTemplate_id(templateId);
        Map<String,Object> map = new HashedMap();

        WechatTemplateDataValuePOJO first = new WechatTemplateDataValuePOJO();
        first.setValue(localKeyword1);
        first.setColor("#173177");

        WechatTemplateDataValuePOJO keyword1 = new WechatTemplateDataValuePOJO();
        keyword1.setValue(localKeyword2);
        keyword1.setColor("#173177");

        WechatTemplateDataValuePOJO keyword2 = new WechatTemplateDataValuePOJO();
        keyword2.setValue(localKeyword3);
        keyword2.setColor("#173177");

        WechatTemplateDataValuePOJO remark = new WechatTemplateDataValuePOJO();
        remark.setValue(localKeyword4);
        remark.setColor("#173177");

        map.put("first",first);
        map.put("keyword1",keyword1);
        map.put("keyword2",keyword2);
        map.put("remark",remark);
        pushPOJO.setData(map);
        String pushJson = JSONObject.toJSONString(pushPOJO);
        return pushJson;
    }

    public static void main(String[] args) {
        String string = WechatTemplatePushUtil.getShangkeTemplateMsg("12343455","111","222","333","444");
        System.out.println(string+"========");
    }
}
