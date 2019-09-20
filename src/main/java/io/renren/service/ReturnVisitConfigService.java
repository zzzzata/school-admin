package io.renren.service;

import io.renren.entity.ReturnVisitConfigEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-25 10:54:11
 */
public interface ReturnVisitConfigService {
	
		
	ReturnVisitConfigEntity queryObject(Map<String, Object> map);
	
	List<ReturnVisitConfigEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ReturnVisitConfigEntity returnVisitConfig);
	
	void update(ReturnVisitConfigEntity returnVisitConfig);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);
	
		
		
}
