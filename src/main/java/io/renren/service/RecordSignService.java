package io.renren.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import io.renren.entity.*;
import io.renren.pojo.RecordSignPOJO;

public interface RecordSignService {
	void saveRecordSign(RecordSignEntity e);
	List< RecordSignEntity> queryList(Map<String,Object> queryMap);
	int queryTotal(Map<String,Object> queryMap);
	void upDateRecordSign (RecordSignEntity e);
	OutputStream RecordSignExport(List<Long> ids);
	/**
	 * 根据订单消息和学员档案运算报读信息
	 *@param r
	 *@param order
	 * @author lintf
	 * 2018年7月31日
	 */
	void RecordSignCheck(RecordInfoEntity r, OrderMessageConsumerEntity order, MallOrderEntity m);

	List<RecordSignPOJO> queryPOJOList(Map<String,Object> queryMap);

	int queryPOJOTotal(Map<String,Object> queryMap);

	void updateFollowStatus(RecordSignEntity recordSignEntity);

	/**
	 * 更新最后一次回访时间
	 * @param recordSignEntity
	 */
	void updateReturnTime(RecordSignEntity recordSignEntity);


	RecordSignEntity findRecordSignById(Long recordSignId);

	void updateSaveReturnTime(Map map);
}
