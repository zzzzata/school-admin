package com.hq.courses.dao;

import com.hq.courses.entity.CsKnowledgeRecordEntity;
import com.hq.courses.pojo.CsKnowledgeRecordPOJO;
import io.renren.dao.BaseMDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-19 15:18:47
 */
@Repository
public interface CsKnowledgeRecordDao extends BaseMDao<CsKnowledgeRecordEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

    int queryPojoTotal(Map<String, Object> map);

    List<CsKnowledgeRecordPOJO> queryPojoList(Map<String, Object> map);
}
