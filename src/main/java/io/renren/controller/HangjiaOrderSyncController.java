package io.renren.controller;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import io.renren.entity.MallOrderEntity;
import io.renren.pojo.HangjiaOrderSyncPojo;
import io.renren.service.HangjiaOrderSyncService;
import io.renren.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("hangjiaSync")
public class HangjiaOrderSyncController implements ChannelAwareMessageListener {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    HangjiaOrderSyncService hangjiaOrderSyncService;

    //@Resource
    //public StringRedisTemplate mainRedis;

    //行家课程列表的KEY
    //private static String HANGJIA_COURSE_LIST_HANGJIA_APP = "hangjia_course_list:hangjia_app:";
    //private static String HANGJIA_COURSE_LIST_HANGJIA = "hangjia_course_list:hangjia:";
    //private static String HANGJIA_COURSE_LIST_HANGJIA_H5 = "hangjia_course_list:hangjia_h5:";

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        HangjiaOrderSyncPojo hangjiaOrder = null;
        String body = new String(message.getBody(), "UTF-8");
        logger.info("新增订单开始从行家同步到蓝鲸!,body===>" + body);
        TransactionStatus status = null;
        //开启事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务
        status = transactionManager.getTransaction(def); // 获得事务状态
        HashMap<String, Object> map = new HashMap<>();
        try {
            Gson gson = new Gson();
            hangjiaOrder = gson.fromJson(body, HangjiaOrderSyncPojo.class);
            //syncStatus:1是新增订单  syncStatus:2是更新订单，  syncStatus:3是删除订单
            //同步新增订单
            String date = DateUtils.getStringDate();
            Long userId = hangjiaOrder.getStudentId();
            Long commodityId = hangjiaOrder.getGoodsId();
            if (1 == hangjiaOrder.getSyncStatus()) {
                MallOrderEntity mallorder = hangjiaOrderSyncService.getMallOrder(userId, commodityId, date);
                if (mallorder == null) {
                    int flag = hangjiaOrderSyncService.addOrder(hangjiaOrder);
                    if (flag == 1) {
                        /*logger.info("订单新增成功后，开始删除课程表的缓存。");
                        //订单新增成功后，删除缓存中的key
                        mainRedis.delete(HANGJIA_COURSE_LIST_HANGJIA + hangjiaOrder.getStudentId());
                        mainRedis.delete(HANGJIA_COURSE_LIST_HANGJIA_APP + hangjiaOrder.getStudentId());
                        mainRedis.delete(HANGJIA_COURSE_LIST_HANGJIA_H5 + hangjiaOrder.getStudentId());
                        logger.info("订单新增成功后，删除课程表的缓存完成。");*/
                        //封装消息队列的Map
                        map = hangjiaOrderSyncService.pushMsg(hangjiaOrder.getOrderCode(), hangjiaOrder.getGoodsId(), hangjiaOrder.getSyncStatus(), hangjiaOrder.getStatus());
                        logger.info("新增订单从行家同步到蓝鲸成功！" + "订单编号:" + hangjiaOrder.getOrderCode() +
                                "用户ID:" + hangjiaOrder.getStudentId() + ",订单状态:" + hangjiaOrder.getStatus());
                    } else {
                        //封装消息队列的Map,新增失败的话支付状态就设置为0，待支付。
                        map = hangjiaOrderSyncService.pushMsg(hangjiaOrder.getOrderCode(), hangjiaOrder.getGoodsId(), hangjiaOrder.getSyncStatus(), 0);
                        logger.error("新增订单从行家同步到蓝鲸失败！" + "订单编号:" + hangjiaOrder.getOrderCode() +
                                ",用户ID:" + hangjiaOrder.getStudentId() + ",订单状态:" + hangjiaOrder.getStatus());
                    }
                } else {
                    //封装消息队列的Map
                    map = hangjiaOrderSyncService.pushMsg(mallorder.getNcId(), mallorder.getCommodityId(), hangjiaOrder.getSyncStatus(), mallorder.getStatus());
                    logger.info("该订单已存在！，新增失败！" + "存在的订单编号为:" + hangjiaOrder.getOrderCode() +
                            ",用户ID:" + hangjiaOrder.getStudentId() + ",订单状态:" + hangjiaOrder.getStatus());
                }
                //同步更新订单状态
            } else if (2 == hangjiaOrder.getSyncStatus()) {
                int flag = hangjiaOrderSyncService.updateOrder(hangjiaOrder);
                if (flag == 1) {
                    /*logger.info("订单更新成功后，开始删除课程表的缓存。");
                    //订单更新成功后，删除缓存中的key
                    mainRedis.delete(HANGJIA_COURSE_LIST_HANGJIA + hangjiaOrder.getStudentId());
                    mainRedis.delete(HANGJIA_COURSE_LIST_HANGJIA_APP + hangjiaOrder.getStudentId());
                    mainRedis.delete(HANGJIA_COURSE_LIST_HANGJIA_H5 + hangjiaOrder.getStudentId());
                    logger.info("订单更新成功后，删除课程表的缓存完成。");*/
                    //更新成功就返回从消息队列中获取的值
                    //封装消息队列的Map
                    map = hangjiaOrderSyncService.pushMsg(hangjiaOrder.getOrderCode(), hangjiaOrder.getGoodsId(), hangjiaOrder.getSyncStatus(), hangjiaOrder.getStatus());
                    logger.info("更新订单从行家同步到蓝鲸成功！" + "订单编号:" + hangjiaOrder.getOrderCode()
                            + ",用户ID:" + hangjiaOrder.getStudentId() + ",订单状态:" + hangjiaOrder.getStatus());
                } else {
                    //失败后需要返回数据库中的值
                    MallOrderEntity mallOrder = hangjiaOrderSyncService.getMallOrder(userId, commodityId, date);
                    map = hangjiaOrderSyncService.pushMsg(mallOrder.getNcId(), mallOrder.getCommodityId(), hangjiaOrder.getSyncStatus(), mallOrder.getStatus());
                    logger.error("更新订单从行家同步到蓝鲸失败！" + "订单编号:" + hangjiaOrder.getOrderCode()
                            + ",用户ID:" + hangjiaOrder.getStudentId() + ",订单状态:" + hangjiaOrder.getStatus());
                }
                //同步订单的退款状态
            } else if (3 == hangjiaOrder.getSyncStatus()) {
                int flag = hangjiaOrderSyncService.updateOrderRefundStatus(hangjiaOrder);
                if (flag == 1) {
                   /* logger.info("订单同步成功后，开始删除课程表的缓存。");
                    //订单同步成功后，删除缓存中的key
                    mainRedis.delete(HANGJIA_COURSE_LIST_HANGJIA + hangjiaOrder.getStudentId());
                    mainRedis.delete(HANGJIA_COURSE_LIST_HANGJIA_APP + hangjiaOrder.getStudentId());
                    mainRedis.delete(HANGJIA_COURSE_LIST_HANGJIA_H5 + hangjiaOrder.getStudentId());
                    logger.info("订单同步成功后，删除课程表的缓存完成。");*/
                    map.put("orderCode", hangjiaOrder.getOrderCode());
                    map.put("goodsId", hangjiaOrder.getGoodsId());
                    map.put("syncStatus", hangjiaOrder.getSyncStatus());
                    map.put("refundStatus", hangjiaOrder.getRefundStatus());
                    logger.info("删除订单从行家同步到蓝鲸成功！" + "订单编号:" + hangjiaOrder.getOrderCode()
                            + ",用户ID:" + hangjiaOrder.getStudentId() + ",订单状态:" + hangjiaOrder.getStatus());
                } else {
                    //更新失败的话，返回数据库里的值。
                    MallOrderEntity mallOrder = hangjiaOrderSyncService.getMallOrder(userId, commodityId, date);
                    map.put("orderCode", mallOrder.getNcId());
                    map.put("goodsId", mallOrder.getCommodityId());
                    map.put("syncStatus", hangjiaOrder.getSyncStatus());
                    map.put("refundStatus", mallOrder.getRefundStatus());
                    logger.error("删除订单从行家同步到蓝鲸失败！" + "订单编号:" + hangjiaOrder.getOrderCode()
                            + ",用户ID:" + hangjiaOrder.getStudentId() + ",订单状态:" + hangjiaOrder.getStatus());
                }
            }
        } catch (Exception e) {
            // 回滚
            transactionManager.rollback(status);
            logger.error(" ，出现未知错误。订单号为：" + hangjiaOrder.getOrderCode(), e);
            //消息确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } finally {
            try {
                //提交事务后，增加返回的OrderId
                String date = DateUtils.getStringDate();
                MallOrderEntity mallOrder = hangjiaOrderSyncService.getMallOrder(hangjiaOrder.getStudentId(), hangjiaOrder.getGoodsId(), date);
                if (mallOrder != null) {
                    map.put("orderId", mallOrder.getOrderId());
                }
                //推到确认的订单消息到消息队列
                hangjiaOrderSyncService.pushOrderMsgAck(map);
                logger.info("同步订单从行家同步到蓝鲸成功!订单号为：" + hangjiaOrder.getOrderCode() + ",orderId==>" + mallOrder.getOrderId());
                //消息确认
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                // 提交事务
                transactionManager.commit(status);
            } catch (Exception e) {
                //消息确认
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                // 提交事务
                transactionManager.commit(status);
                logger.error("queue OrderMessageConsumerUpdate error : {}", e);
            }
        }
    }
}
