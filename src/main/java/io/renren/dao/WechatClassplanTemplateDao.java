package io.renren.dao;

import io.renren.entity.WechatClassplanTemplateEntity;
import java.util.Map;

/**
 * 推送模板消息排课关联表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-10 09:35:41
 */
public interface WechatClassplanTemplateDao extends BaseMDao<WechatClassplanTemplateEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

    /**
     * 新增或修改时判断同一个排课是否已经存在
     * @param map
     * @return
     */
    int isExistByClassplanId(Map<String, Object> map);
}
