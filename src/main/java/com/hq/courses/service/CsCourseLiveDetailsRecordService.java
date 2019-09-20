package com.hq.courses.service;


import com.hq.courses.entity.CsCourseLiveDetailsEntity;
import com.hq.courses.entity.CsCourseLiveDetailsRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 课次
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-22 11:43:43
 */
public interface CsCourseLiveDetailsRecordService {
	
		
	CsCourseLiveDetailsRecordEntity queryObject(Map<String, Object> map);
	
	List<CsCourseLiveDetailsRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

	/**
	 * 新增
	 * @param liveId 课次id
	 * @param updatePerson 修改人
	 */
	void save(Long liveId,Long updatePerson);
	
	void update(CsCourseLiveDetailsRecordEntity csCourseLiveDetailsRecord);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);
	
		
		
}
