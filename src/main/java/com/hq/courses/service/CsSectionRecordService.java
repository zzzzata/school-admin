package com.hq.courses.service;


import com.hq.courses.entity.CsSectionRecordEntity;
import com.hq.courses.pojo.CsSectionRecordPOJO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-19 15:18:57
 */
public interface CsSectionRecordService {
	
		
	CsSectionRecordEntity queryObject(Map<String, Object> map);
	
	List<CsSectionRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CsSectionRecordEntity csSectionRecord);
	
	void update(CsSectionRecordEntity csSectionRecord);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);

    //更新/删除前保存好之前的内容
    void saveOldSection(Long sectionId);

    int queryPojoTotal(Map<String, Object> map);

    List<CsSectionRecordPOJO> queryPojoList(Map<String, Object> map);
}
