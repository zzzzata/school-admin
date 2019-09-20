package io.renren.service;

import java.util.List;
import java.util.Map;

import io.renren.entity.DelayCloseOrderEntity;
import io.renren.entity.MallOrderEntity;
import io.renren.entity.OrderMessageConsumerEntity;

public interface DelayCloseOrderService {

	void save(DelayCloseOrderEntity e);

	void update(DelayCloseOrderEntity e);

	List<DelayCloseOrderEntity> queryList(Map<String, Object> queryMap);
	/**
	 * 关掉订单时调用 延迟关掉订单 
	 * @param mallOrderList
	 */
	void DelayCloseOrderList (List<MallOrderEntity> mallOrderList, OrderMessageConsumerEntity orderMessageConsumerEntity);
	/***
	 * 退单时删除迟延订单
	 * @param ncid
	 * @return
	 */
	void  DelayCloseOrderDeleteByNCid(String ncid,Integer closeStatus);
	void  DelayCloseOrderDeleteByOrderId(Long orderId,Integer closeStatus);
}
