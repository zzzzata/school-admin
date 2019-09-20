package io.renren.service;

import io.renren.entity.NcSchoolCourseclassplanEntity;
import io.renren.entity.NcSchoolCourseclassplanLiveEntity;
import io.renren.pojo.NcUserListPOJO;

import java.util.List;
import java.util.Map;

/**
 * NC线下排课信息表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-02-18 09:12:11
 */
public interface NcSchoolCourseclassplanService {
	
		
	NcSchoolCourseclassplanEntity queryObject(Map<String, Object> map);
	
	List<NcSchoolCourseclassplanEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(NcSchoolCourseclassplanEntity ncSchoolCourseclassplan);
	
	void update(NcSchoolCourseclassplanEntity ncSchoolCourseclassplan);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);

    //排课信息是否已经存在
    boolean isExistByClassplanId(Map paramMap);

    //查询排课子表内容
    List<NcSchoolCourseclassplanLiveEntity> queryDetailList(Map<String, Object> map);

    //查询排课子表数量
    int queryDetailTotal(Map<String, Object> map);

    //查询学员列表
    List<NcUserListPOJO> queryUserList(Map<String, Object> map);

    //查询学员列表数量
    int queryUserListTotal(Map<String, Object> map);
}
