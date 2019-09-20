package io.renren.service;

import io.renren.entity.NcSchoolCourseclassplanLiveEntity;

import java.util.List;
import java.util.Map;

/**
 * NC线下排课详情(课次)信息表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-02-18 09:12:30
 */
public interface NcSchoolCourseclassplanLiveService {
	
		
	NcSchoolCourseclassplanLiveEntity queryObject(Map<String, Object> map);
	
	List<NcSchoolCourseclassplanLiveEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(NcSchoolCourseclassplanLiveEntity ncSchoolCourseclassplanLive);
	
	void update(NcSchoolCourseclassplanLiveEntity ncSchoolCourseclassplanLive);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);


    void updateByClassplanId(Map liveParamMap);

    boolean isExistByClassplanLiveId(Map paramMap);
}
