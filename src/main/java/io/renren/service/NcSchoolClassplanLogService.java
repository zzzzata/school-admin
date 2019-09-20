package io.renren.service;

import io.renren.entity.NcSchoolClassplanLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 同步NC排课信息错误日记记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-02-18 09:11:41
 */
public interface NcSchoolClassplanLogService {
	
		
	NcSchoolClassplanLogEntity queryObject(Map<String, Object> map);
	
	List<NcSchoolClassplanLogEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(NcSchoolClassplanLogEntity ncSchoolClassplanLog);
	
	void update(NcSchoolClassplanLogEntity ncSchoolClassplanLog);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);
	
		
		
}
