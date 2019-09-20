package io.renren.dao;

import io.renren.entity.WechatAccountEntity;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 微信公众号号信息记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-09 10:34:42
 */
@Repository
public interface WechatAccountDao extends BaseMDao<WechatAccountEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
