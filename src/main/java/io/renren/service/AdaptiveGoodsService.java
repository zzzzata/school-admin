package io.renren.service;

import io.renren.entity.AdaptiveGoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * 自适应推送商品表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-29 10:07:14
 */
public interface AdaptiveGoodsService {
	
		
	AdaptiveGoodsEntity queryObject(Map<String, Object> map);
	
	List<AdaptiveGoodsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AdaptiveGoodsEntity adaptiveGoods);
	
	void update(AdaptiveGoodsEntity adaptiveGoods);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);
	
		
		
}
