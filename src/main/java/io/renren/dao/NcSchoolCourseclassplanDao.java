package io.renren.dao;

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
public interface NcSchoolCourseclassplanDao extends BaseMDao<NcSchoolCourseclassplanEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

	//检验排课信息是否已经存在
    int isExistByClassplanId(Map paramMap);

    List<NcSchoolCourseclassplanLiveEntity> queryDetailList(Map<String, Object> map);

    int queryDetailTotal(Map<String, Object> map);

    List<NcUserListPOJO> queryUserList(Map<String, Object> map);

    int queryUserListTotal(Map<String, Object> map);
}
