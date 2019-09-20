package io.renren.dao;

import io.renren.entity.NcSchoolLearningEntity;
import io.renren.pojo.NcSchoolLearningDetailPOJO;

import java.util.List;
import java.util.Map;

/**
 * 线下学习计划
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-15 14:22:36
 */
public interface NcSchoolLearningDao extends BaseMDao<NcSchoolLearningEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

    List<NcSchoolLearningDetailPOJO> queryDetail(Map<String, Object> map);

    int queryDetailTotal(Map<String, Object> map);
}
