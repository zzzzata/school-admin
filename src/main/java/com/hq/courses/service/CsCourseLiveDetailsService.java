package com.hq.courses.service;


import com.hq.courses.entity.CsCourseLiveDetailsEntity;

import java.util.List;
import java.util.Map;

/**
 * 课次
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-15 11:08:20
 */
public interface CsCourseLiveDetailsService {
	
		
	CsCourseLiveDetailsEntity queryObject(Map<String, Object> map);
	
	List<CsCourseLiveDetailsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CsCourseLiveDetailsEntity csCourseLiveDetails);
	
	void update(CsCourseLiveDetailsEntity csCourseLiveDetails);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);
	
	int queryByLiveName(String liveName);
		
}
