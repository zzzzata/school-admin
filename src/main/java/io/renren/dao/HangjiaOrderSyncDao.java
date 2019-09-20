package io.renren.dao;

import io.renren.entity.MallOrderEntity;
import org.apache.ibatis.annotations.Param;

public interface HangjiaOrderSyncDao {
    //同步新增订单
    int addOrder(MallOrderEntity mallOrder);

    //同步更新订单
    int updateOrder(MallOrderEntity mallOrder);

    //同步订单
    int updateOrderRefundsStatus(MallOrderEntity mallOrder);

    Long getOrderId(@Param("orderCode") String orderCode);

    MallOrderEntity getMallOrder(@Param("userId") Long userId, @Param("commodityId") Long commodityId, @Param("date") String date);

    MallOrderEntity selectMallOrder(@Param("userId") Long userId, @Param("commodityId") Long commodityId, @Param("orderCode") String orderCode);
}
