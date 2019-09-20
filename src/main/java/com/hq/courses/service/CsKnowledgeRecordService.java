package com.hq.courses.service;


import com.hq.courses.entity.CsKnowledgeRecordEntity;
import com.hq.courses.pojo.CsKnowledgeRecordPOJO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-19 15:18:47
 */
public interface CsKnowledgeRecordService {
	
		
	CsKnowledgeRecordEntity queryObject(Map<String, Object> map);
	
	List<CsKnowledgeRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CsKnowledgeRecordEntity csKnowledgeRecord);
	
	void update(CsKnowledgeRecordEntity csKnowledgeRecord);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);


	//更新/删除前保存好之前的记录
    void saveOldKnowledge(Long knowledgeId);

    int queryPojoTotal(Map<String, Object> map);

    List<CsKnowledgeRecordPOJO> queryPojoList(Map<String, Object> map);
}
