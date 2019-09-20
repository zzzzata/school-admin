package io.renren.dao;

import io.renren.entity.ExaminationDateEntity;
import io.renren.pojo.ExaminationDatePOJO;

import java.util.List;
import java.util.Map;

/**
 * 学员考期表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-13 09:18:16
 */
public interface ExaminationDateDao extends BaseMDao<ExaminationDateEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

	List<ExaminationDatePOJO> queryListPOJO(Map<String, Object> map);
}
