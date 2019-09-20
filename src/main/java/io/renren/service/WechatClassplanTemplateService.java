package io.renren.service;

import io.renren.entity.WechatClassplanTemplateEntity;

import java.util.List;
import java.util.Map;

/**
 * 推送模板消息排课关联表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-10 09:35:41
 */
public interface WechatClassplanTemplateService {
	
		
	WechatClassplanTemplateEntity queryObject(Map<String, Object> map);
	
	List<WechatClassplanTemplateEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WechatClassplanTemplateEntity wechatClassplanTemplate) throws Exception;

	void update(WechatClassplanTemplateEntity wechatClassplanTemplate) throws Exception;
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);
	
		
		
}
