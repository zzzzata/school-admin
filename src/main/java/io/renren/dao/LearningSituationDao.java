package io.renren.dao;

import io.renren.entity.LearningSituationEntity;
import io.renren.pojo.LearningSiuationPOJO;

import java.util.List;
import java.util.Map;

/**
 * 学习情况
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-16 11:12:00
 */
public interface LearningSituationDao extends BaseMDao<LearningSituationEntity> {
	List<LearningSiuationPOJO> queryPOJOList(Map<String,Object> map);

	int queryPOJOTotal(Map<String,Object> map);

	LearningSiuationPOJO queryPOJOObject(Map<String, Object> map);
}
