package io.renren.service;

import io.renren.entity.ExaminationDateEntity;
import io.renren.pojo.ExaminationDatePOJO;

import java.util.List;
import java.util.Map;

/**
 * 学员考期表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-13 09:18:16
 */
public interface ExaminationDateService {
	
		
	ExaminationDateEntity queryObject(Map<String, Object> map);
	
	List<ExaminationDatePOJO> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ExaminationDateEntity examinationDate);
	
	void update(ExaminationDateEntity examinationDate);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);
	
		
		
}
