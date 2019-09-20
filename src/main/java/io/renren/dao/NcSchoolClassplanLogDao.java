package io.renren.dao;

import io.renren.entity.NcSchoolClassplanLogEntity;
import java.util.Map;

/**
 * 同步NC排课信息错误日记记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-02-18 09:11:41
 */
public interface NcSchoolClassplanLogDao extends BaseMDao<NcSchoolClassplanLogEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
