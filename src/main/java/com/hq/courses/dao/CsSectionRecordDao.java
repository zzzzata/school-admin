package com.hq.courses.dao;

import com.hq.courses.entity.CsSectionRecordEntity;
import com.hq.courses.pojo.CsSectionRecordPOJO;
import io.renren.dao.BaseMDao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-19 15:18:57
 */
public interface CsSectionRecordDao extends BaseMDao<CsSectionRecordEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

    int queryPojoTotal(Map<String, Object> map);

    List<CsSectionRecordPOJO> queryPojoList(Map<String, Object> map);
}
