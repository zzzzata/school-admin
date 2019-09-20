package io.renren.dao;

import io.renren.entity.AdaptiveGoodsEntity;
import java.util.Map;

/**
 * 自适应推送商品表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-29 10:07:14
 */
public interface AdaptiveGoodsDao extends BaseMDao<AdaptiveGoodsEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
