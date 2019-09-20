package io.renren.service.impl;

import io.renren.pojo.LearningSiuationPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.dao.LearningSituationDao;
import io.renren.entity.LearningSituationEntity;
import io.renren.service.LearningSituationService;
import io.renren.utils.Constant;



@Service("learningSituationService")
public class LearningSituationServiceImpl implements LearningSituationService {
	@Autowired
	private LearningSituationDao learningSituationDao;
	
	@Override
	public LearningSiuationPOJO queryPOJOObject(Map<String, Object> map){
		return learningSituationDao.queryPOJOObject(map);
	}

	@Override
	public List<LearningSiuationPOJO> queryPOJOList(Map<String, Object> map) {
		return learningSituationDao.queryPOJOList(map);
	}

	@Override
	public int queryPOJOTotal(Map<String, Object> map) {
		return learningSituationDao.queryPOJOTotal(map);
	}


	@Override
	public void update(LearningSituationEntity learningSituation){
		learningSituationDao.update(learningSituation);
	}
	

	
}
