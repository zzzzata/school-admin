package io.renren.service;

import io.renren.entity.MallOrderEntity;
import io.renren.pojo.HangjiaOrderSyncPojo;

import java.util.HashMap;

public interface HangjiaOrderSyncService {

    int addOrder(HangjiaOrderSyncPojo hangjiaOrderSyncPojo);

    int updateOrder(HangjiaOrderSyncPojo hangjiaOrderSyncPojo);

    int updateOrderRefundStatus(HangjiaOrderSyncPojo hangjiaOrder);

    void pushOrderMsgAck(HashMap<String, Object> map);

    HashMap<String, Object> pushMsg(String OrderCode, Long goodsId, Integer syncStatus, Integer status);

    Long getOrderId(String orderCode);

    MallOrderEntity getMallOrder(Long userId, Long commodityId, String date);
}
