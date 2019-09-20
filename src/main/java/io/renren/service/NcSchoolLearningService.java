package io.renren.service;

import io.renren.entity.NcSchoolLearningEntity;
import io.renren.pojo.NcSchoolLearningDetailPOJO;

import java.util.List;
import java.util.Map;

/**
 * 线下学习计划
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-15 14:22:36
 */
public interface NcSchoolLearningService {
	
		
	NcSchoolLearningEntity queryObject(Map<String, Object> map);
	
	List<NcSchoolLearningEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(NcSchoolLearningEntity ncSchoolLearning);
	
	void update(NcSchoolLearningEntity ncSchoolLearning);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);


    List<NcSchoolLearningDetailPOJO> queryDetail(Map<String, Object> map);

    int queryDetailTotal(Map<String, Object> map);
}
