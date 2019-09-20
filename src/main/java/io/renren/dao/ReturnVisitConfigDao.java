package io.renren.dao;

import io.renren.entity.ReturnVisitConfigEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-17 17:19:52
 */
public interface ReturnVisitConfigDao extends BaseMDao<ReturnVisitConfigEntity> {
	/**
	 * 批量更新状态
	 */
	int update(Map<String, Object> map);

	/**
	 * 通过报读主键查询
	 * @param recordSignId
	 * @return
	 */
	List<ReturnVisitConfigEntity> queryListBySign(@Param(value="recordSignId")Long recordSignId);

	/**
	 * 列表
	 *
	 * @param map
	 * @return
	 */
	@Override
	List<ReturnVisitConfigEntity> queryList(Map<String, Object> map);

	@Override
	ReturnVisitConfigEntity queryObject(Map<String, Object> map);


}
