package com.hq.courses.service;


import com.hq.courses.entity.CsCourseRecordEntity;
import com.hq.courses.pojo.CsCourseRecordPOJO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-19 15:18:43
 */
public interface CsCourseRecordService {
	
		
	CsCourseRecordEntity queryObject(Map<String, Object> map);
	
	List<CsCourseRecordPOJO> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CsCourseRecordEntity csCourseRecord);
	
	void update(CsCourseRecordEntity csCourseRecord);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);


	//删除/更新前保存之前内容
    void saveOldCsCourse(Long courseId);
}
