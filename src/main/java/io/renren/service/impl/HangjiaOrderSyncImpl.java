package io.renren.service.impl;

import com.google.gson.Gson;
import io.renren.dao.HangjiaOrderSyncDao;
import io.renren.entity.MallOrderEntity;
import io.renren.pojo.HangjiaOrderSyncPojo;
import io.renren.rest.persistent.KGS;
import io.renren.service.HangjiaOrderSyncService;
import io.renren.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

@Service("hangjiaOrderSyncService")
public class HangjiaOrderSyncImpl implements HangjiaOrderSyncService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    HangjiaOrderSyncDao hangjiaOrderSyncDao;
    @Resource
    KGS orderNoKGS;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 推送后台用户到IM消息队列名
     */
    private static String HANGJIAORDERMSGACK = "";

    @Value("#{rabbitmq['queue.hangjia.order.msgAck']}")
    private void setHANGJIAORDERMSGACK(String str) {
        HANGJIAORDERMSGACK = str;
    }


    private static final String ORDERNO_HEAD = "hj_";

    //同步新增订单
    public int addOrder(HangjiaOrderSyncPojo hangjiaOrder) {
        MallOrderEntity mallOrder = new MallOrderEntity();
        //免费的订单
        if (hangjiaOrder.getIsFree() == 0) {
            mallOrder.setDr(0);
            //互联网大学的ProductID设置为10
            mallOrder.setProductId(10L);
            //是否免费
            mallOrder.setIsFree(hangjiaOrder.getIsFree());
            //商品ID
            mallOrder.setCommodityId(hangjiaOrder.getGoodsId());
            //商品名称
            mallOrder.setCommodityName(hangjiaOrder.getGoodsName());
            //同步时间
            mallOrder.setSynTime(new Date());
            //用户
            mallOrder.setUserId(hangjiaOrder.getStudentId());
            //订单编号
            mallOrder.setNcId(hangjiaOrder.getOrderCode());
            mallOrder.setPic(hangjiaOrder.getGoodsImage());
            mallOrder.setSpic(hangjiaOrder.getGoodsImage());
            //创建时间
            mallOrder.setCreationTime(DateUtils.getDate(hangjiaOrder.getCreateTime(), 1));
            //其余字段
            mallOrder.setOrderNo(ORDERNO_HEAD + orderNoKGS.nextId());
            mallOrder.setProfessionId(0L);
            mallOrder.setOrderName("行家订单");
            mallOrder.setTotalMoney(0.00);
            mallOrder.setPayStatus(2);
            mallOrder.setUstatus(0);
            mallOrder.setSourceType(0);
            mallOrder.setLevelId(0L);
            mallOrder.setAreaId(24L);
            //课程有效期
            // 订单有效期 date_validity
            Date creatTime = DateUtils.getDate(hangjiaOrder.getCreateTime(), 1);
            //判断订单是否永久有效
            if (-1 == hangjiaOrder.getValidityDate()) {
                mallOrder.setDateValidity(DateUtils.parse("2099-12-31"));
            } else {
                mallOrder.setDateValidity(DateUtils.orderDateValiditySync(DateUtils.getDate(Long.valueOf(creatTime.getTime()), 1), hangjiaOrder.getValidityDate().longValue()));
            }
            int flag = hangjiaOrderSyncDao.addOrder(mallOrder);
            return flag;
            //付费的订单
        } else if (hangjiaOrder.getIsFree() == 1) {
            //订单编号
            mallOrder.setNcId(hangjiaOrder.getOrderCode());
            //订单状态
            mallOrder.setDr(0);
            //互联网大学的ProductID设置为10
            mallOrder.setProductId(10L);
            //学员ID
            mallOrder.setUserId(hangjiaOrder.getStudentId());
            //订单号
            mallOrder.setOrderNo(ORDERNO_HEAD + orderNoKGS.nextId());
            //退款状态
            mallOrder.setRefundStatus(hangjiaOrder.getRefundStatus());
            //是否免费
            mallOrder.setIsFree(hangjiaOrder.getIsFree());
            //商品ID
            mallOrder.setCommodityId(hangjiaOrder.getGoodsId());
            //商品名称
            mallOrder.setCommodityName(hangjiaOrder.getGoodsName());
            //订单状态
            mallOrder.setStatus(hangjiaOrder.getStatus());
            mallOrder.setPic(hangjiaOrder.getGoodsImage());
            mallOrder.setSpic(hangjiaOrder.getGoodsImage());
            mallOrder.setPayMoney(hangjiaOrder.getPrice());
            mallOrder.setPayType(hangjiaOrder.getPaymentType());
            //来源 0.线上 1.NC 2.測試 3.体验 4.PC端  5.APP 6.微信小程序
            mallOrder.setSourceType(hangjiaOrder.getPaymentType());
            mallOrder.setPayStatus(hangjiaOrder.getStatus());
            //创建时间
            mallOrder.setCreationTime(DateUtils.getDate(hangjiaOrder.getCreateTime(), 1));
            //课程有效期
            // 订单有效期 date_validity
            Date creatTime = DateUtils.getDate(hangjiaOrder.getCreateTime(), 1);
            //判断订单是否永久有效
            if (-1 == hangjiaOrder.getValidityDate()) {
                mallOrder.setDateValidity(DateUtils.parse("2099-12-31"));
            } else {
                mallOrder.setDateValidity(DateUtils.orderDateValiditySync(DateUtils.getDate(Long.valueOf(creatTime.getTime()), 1), hangjiaOrder.getValidityDate().longValue()));
            }
            //创建时间
            mallOrder.setCreationTime(creatTime);
            //修改时间
            mallOrder.setModifiedTime(creatTime);
            //同步时间
            mallOrder.setSynTime(new Date());

            //其余字段
            mallOrder.setProfessionId(0L);
            mallOrder.setOrderName("行家订单");
            mallOrder.setUstatus(0);
            mallOrder.setTotalMoney(0.00);
            mallOrder.setLevelId(0L);
            mallOrder.setAreaId(24L);
            int flag = hangjiaOrderSyncDao.addOrder(mallOrder);
            return flag;
        }
        return 0;
    }

    public int updateOrder(HangjiaOrderSyncPojo hangjiaOrder) {

        String orderCode = hangjiaOrder.getOrderCode();
        Long userId = hangjiaOrder.getStudentId();
        Long commodityId = hangjiaOrder.getGoodsId();
        MallOrderEntity mallOrder = hangjiaOrderSyncDao.selectMallOrder(userId, commodityId, orderCode);

        if (mallOrder != null) {
            //免费的订单
            if (0 == mallOrder.getIsFree()) {
                //订单编号
                mallOrder.setNcId(hangjiaOrder.getOrderCode());
                //订单状态
                mallOrder.setDr(0);
                //互联网大学的ProductID设置为10
                mallOrder.setProductId(10L);
                //是否免费
                mallOrder.setIsFree(hangjiaOrder.getIsFree());
                //商品ID
                mallOrder.setCommodityId(hangjiaOrder.getGoodsId());
                //商品名称
                mallOrder.setCommodityName(hangjiaOrder.getGoodsName());
                //修改时间
                mallOrder.setModifiedTime(new Date());
                //同步时间
                mallOrder.setSynTime(new Date());
                //用户
                mallOrder.setUserId(hangjiaOrder.getStudentId());
                mallOrder.setPic(hangjiaOrder.getGoodsImage());
                mallOrder.setSpic(hangjiaOrder.getGoodsImage());
                //其余字段
                mallOrder.setProfessionId(0L);
                mallOrder.setOrderName("行家订单");
                mallOrder.setTotalMoney(0.00);
                mallOrder.setPayStatus(2);
                mallOrder.setUstatus(0);
                mallOrder.setSourceType(0);
                mallOrder.setLevelId(0L);
                Date creatTime = DateUtils.getDate(hangjiaOrder.getCreateTime(), 1);
                //判断订单是否永久有效
                mallOrder.setDateValidity(DateUtils.parse("2099-12-31"));
                int flag = hangjiaOrderSyncDao.updateOrder(mallOrder);
                return flag;
                //付费的订单
            } else if (1 == mallOrder.getIsFree()) {
                //订单编号
                mallOrder.setNcId(hangjiaOrder.getOrderCode());
                //退款状态
                mallOrder.setRefundStatus(hangjiaOrder.getRefundStatus());
                //是否免费
                mallOrder.setIsFree(hangjiaOrder.getIsFree());
                //商品ID
                mallOrder.setCommodityId(hangjiaOrder.getGoodsId());
                //商品名称
                mallOrder.setCommodityName(hangjiaOrder.getGoodsName());
                //订单状态
                mallOrder.setStatus(hangjiaOrder.getStatus());
                mallOrder.setPic(hangjiaOrder.getGoodsImage());
                mallOrder.setSpic(hangjiaOrder.getGoodsImage());
                mallOrder.setPayMoney(hangjiaOrder.getPrice());
                mallOrder.setPayType(hangjiaOrder.getPaymentType());
                //来源 0.线上 1.NC 2.測試 3.体验 4.PC端  5.APP 6.微信小程序
                mallOrder.setSourceType(hangjiaOrder.getPaymentType());
                // 课程有效期
                Date creatTime = DateUtils.getDate(hangjiaOrder.getCreateTime(), 1);
                //判断订单是否永久有效
                if (-1 == hangjiaOrder.getValidityDate()) {
                    mallOrder.setDateValidity(DateUtils.parse("2099-12-31"));
                } else {
                    mallOrder.setDateValidity(DateUtils.orderDateValiditySync(DateUtils.getDate(Long.valueOf(creatTime.getTime()), 1), hangjiaOrder.getValidityDate().longValue()));
                }//修改时间
                mallOrder.setModifiedTime(new Date());
                //同步时间
                mallOrder.setSynTime(new Date());
                //订单的删除状态
                mallOrder.setDr(hangjiaOrder.getDeleteStatus());
                int flag = hangjiaOrderSyncDao.updateOrder(mallOrder);
                return flag;
            }
        }
        return 0;
    }

    public int updateOrderRefundStatus(HangjiaOrderSyncPojo hangjiaOrder) {
        String orderCode = hangjiaOrder.getOrderCode();
        Long userId = hangjiaOrder.getStudentId();
        Long commodityId = hangjiaOrder.getGoodsId();
        MallOrderEntity mallOrder = hangjiaOrderSyncDao.selectMallOrder(userId, commodityId, orderCode);
        if (mallOrder != null) {
            //更新退款订单的状态
            mallOrder.setRefundStatus(hangjiaOrder.getRefundStatus());
            int flag = hangjiaOrderSyncDao.updateOrderRefundsStatus(mallOrder);
            return flag;
        }
        //找不到对应的订单
        return 0;
    }


    @Override
    public void pushOrderMsgAck(HashMap<String, Object> map) {
        Gson gson = new Gson();
        try {
            amqpTemplate.convertAndSend("mq-exchange", HANGJIAORDERMSGACK, gson.toJson(map));
        } catch (Exception e) {
            logger.info("发送确认消息到消息队列！ ");
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<String, Object> pushMsg(String OrderCode, Long goodsId, Integer syncStatus, Integer status) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderCode", OrderCode);
        map.put("goodsId", goodsId);
        map.put("syncStatus", syncStatus);
        map.put("status", status);
        return map;
    }

    @Override
    public Long getOrderId(String orderCode) {
        return hangjiaOrderSyncDao.getOrderId(orderCode);
    }


    @Override
    public MallOrderEntity getMallOrder(Long userId, Long commodityId, String date) {
        return hangjiaOrderSyncDao.getMallOrder(userId, commodityId, date);
    }
}
