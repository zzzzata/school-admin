package com.hq.courses.service;


import com.hq.courses.entity.CsChapterRecordEntity;
import com.hq.courses.pojo.CsChapterRecordPOJO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-19 15:18:38
 */
public interface CsChapterRecordService {
	
		
	CsChapterRecordEntity queryObject(Map<String, Object> map);
	
	List<CsChapterRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CsChapterRecordEntity csChapterRecord);
	
	void update(CsChapterRecordEntity csChapterRecord);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);


	//删除/更新前保存好之前的记录
    void saveOldChapter(Long chapterId);

    int queryPojoTotal(Map<String, Object> map);

    List<CsChapterRecordPOJO> queryPojoList(Map<String, Object> map);
}
