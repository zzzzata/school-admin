package io.renren.dao;

import io.renren.entity.NcSchoolCourseclassplanLiveEntity;
import java.util.Map;

/**
 * NC线下排课详情(课次)信息表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-02-18 09:12:30
 */
public interface NcSchoolCourseclassplanLiveDao extends BaseMDao<NcSchoolCourseclassplanLiveEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

    void updateByClassplanId(Map liveParamMap);

    int isExistByClassplanLiveId(Map paramMap);
}
