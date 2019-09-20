package io.renren.service;

import io.renren.entity.WechatTemplateMsgEntity;
import io.renren.pojo.wechat.WechatMsgDetailPOJO;

import java.util.List;
import java.util.Map;

/**
 * 微信推送消息记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-29 10:54:49
 */
public interface WechatTemplateMsgService {
	
		
	WechatTemplateMsgEntity queryObject(Map<String, Object> map);
	
	List<WechatTemplateMsgEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WechatTemplateMsgEntity wechatTemplateMsg);
	
	void update(WechatTemplateMsgEntity wechatTemplateMsg);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);


    /**
     * 删除待发送消息
     * @param id 消息记录id
     */
    String deleteMsg(long id);

    /**
     * 获取消息接收人详情
     * @param map
     * @return
     */
    List<WechatMsgDetailPOJO> wechatMsgDetailList(Map<String, Object> map);

    /**
     * 获取发送消息总数量
     * @param map
     * @return
     */
    WechatMsgDetailPOJO wechatMsgCount(Map<String, Object> map);
}
