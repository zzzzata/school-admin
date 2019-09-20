package io.renren.service;

import io.renren.entity.WechatAccountEntity;
import io.renren.pojo.wechat.WechatTemplatePOJO;

import java.util.List;
import java.util.Map;

/**
 * 微信公众号号信息记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-09 10:34:42
 */
public interface WechatAccountService {
	
		
	WechatAccountEntity queryObject(Map<String, Object> map);
	
	List<WechatAccountEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WechatAccountEntity wechatAccount);
	
	void update(WechatAccountEntity wechatAccount);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);

    /**
     * 获取微信推送消息模板
     * @param map
     * @return
     */
    List<WechatTemplatePOJO> queryTemplateList(Map<String, Object> map);
}
