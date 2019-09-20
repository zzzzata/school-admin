package io.renren.service;

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
public interface LearningSituationService {


	LearningSiuationPOJO queryPOJOObject(Map<String, Object> map);

	List<LearningSiuationPOJO> queryPOJOList(Map<String,Object> map);

	int queryPOJOTotal(Map<String,Object> map);
	
	void update(LearningSituationEntity learningSituation);
		
}
