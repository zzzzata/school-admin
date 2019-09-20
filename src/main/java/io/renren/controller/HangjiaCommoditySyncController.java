package io.renren.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.renren.entity.MallOrderEntity;
import io.renren.pojo.HangjiaCommodityPOJO;
import io.renren.pojo.HangjiaOrderSyncPojo;
import io.renren.service.HangjiaCommoditySyncService;
import io.renren.service.HangjiaOrderSyncService;
import io.renren.utils.DateUtils;
import io.renren.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("hangjiaSync")
public class HangjiaCommoditySyncController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    HangjiaCommoditySyncService hangjiaCommoditySyncService;
    @Autowired
    HangjiaOrderSyncService hangjiaOrderSyncService;


    @ResponseBody
    @RequestMapping(value = "/addOrUpdateCommodity", method = RequestMethod.POST)
    public R commoditySync(@RequestBody HashMap<String, Object> map) {

        Integer syncStatus = (Integer) map.get("syncStatus");
        JSONObject jo = (JSONObject) map.get("commoditySync");
        HangjiaCommodityPOJO commodity = JSON.parseObject(JSON.toJSONString(jo), HangjiaCommodityPOJO.class);
        logger.info("请求的参数为：" + commodity.toString());
        //syncStatus:1是新增商品  syncStatus:2是更新商品
        //同步商品
        int flag = hangjiaCommoditySyncService.addOrUpdateCommodity(commodity, syncStatus);
        if (flag == 1) {
            return R.ok("从行家后台同步商品到蓝鲸成功!,商品Id是" + commodity.getId());
        }
        return R.error(500, "未知错误,状态码status的值为" + syncStatus + "商品Id为：" + commodity.getId());
    }


    @ResponseBody
    @RequestMapping(value = "/delOrUpdateCommodity", method = RequestMethod.POST)
    public R delOrUpdateCommodity(@RequestBody HashMap<String, Object> map) {
        //syncStatus    删除商品:1     下架或者上架商品:2
        Integer syncStatus = (Integer) map.get("syncStatus");
        String goodsIds = (String) map.get("goodsIds");
        //issueStatus  0：下架 1：上架  2：未发布  4：停售
        Integer issueStatus = (Integer) map.get("issueStatus");
        //删除商品
        if (syncStatus == 1) {
            if (goodsIds != null) {
                hangjiaCommoditySyncService.delCommodity(goodsIds);
                logger.info("删除商品从行家同步到蓝鲸成功！" + "商品ID:" + goodsIds);
                return R.ok("删除商品从行家后台同步到蓝鲸成功");
            } else {
                logger.error("传入的商品Id为空！" + "商品ID:" + goodsIds);
                return R.error(500, "传入的商品Id为空！");
            }
        }
        //下架或者上架商品
        if (syncStatus == 2) {
            if (goodsIds != null) {
                hangjiaCommoditySyncService.updateCommodityStatus(goodsIds, issueStatus);
                logger.info("更新商品的发布状态从行家同步到蓝鲸成功！" + "商品ID:" + goodsIds + "发布状态:" + issueStatus);
                return R.ok("更新商品从行家后台同步到蓝鲸成功");
            } else {
                logger.error("传入的商品Id为空！" + "商品ID:" + goodsIds);
                return R.error(500, "传入的商品Id为空" + syncStatus + "商品Id为：" + goodsIds);
            }
        }
        return R.error(500, "未知错误,状态码syncStatus的值为" + syncStatus + "商品Id为：" + goodsIds);
    }

    @ResponseBody
    @RequestMapping(value = "/syncFreeOrder", method = RequestMethod.POST)
    public R syncFreeOrder(@RequestBody HashMap<String, Object> map2) {
        try {
            JSONObject map = (JSONObject) JSON.toJSON(map2);
            logger.info("map======>" + map);
            JSONObject json = (JSONObject) map.get("hangjiaOrderSyncPojo");
            HangjiaOrderSyncPojo hangjiaOrder = JSON.toJavaObject(json, HangjiaOrderSyncPojo.class);
            Long userId = hangjiaOrder.getStudentId();
            Long commodityId = hangjiaOrder.getGoodsId();
            int isFree = hangjiaOrder.getIsFree();
            //判断是否为付费订单
            if (1 == isFree) {
                String date = DateUtils.getStringDate();
                MallOrderEntity mallOrder1 = hangjiaOrderSyncService.getMallOrder(userId, commodityId, date);
                HashMap<String, Object> orderIdMap = new HashMap<>();
                if (null == mallOrder1) {
                    logger.info("开始新增付费的订单!" + hangjiaOrder.getOrderCode());
                    int flag = hangjiaOrderSyncService.addOrder(hangjiaOrder);
                    if (1 == flag) {
                        if (null != hangjiaOrder.getOrderCode()) {
                            MallOrderEntity mallOrder2 = hangjiaOrderSyncService.getMallOrder(userId, commodityId, date);
                            logger.info("付费的订单新增成功！" + hangjiaOrder.getOrderCode() + ",orderId==>" + mallOrder2.getOrderId());
                            orderIdMap.put("orderId", mallOrder2.getOrderId());
                            return R.ok(orderIdMap);
                        } else {
                            logger.error("传入的OrderCode为:" + hangjiaOrder.getOrderCode());
                            return R.error("传入的OrderCode为null");
                        }
                    } else {
                        logger.error("付费订单插入失败！" + mallOrder1.getOrderNo() + ",flag=" + flag);
                        return R.error("付费订单插入失败！");
                    }
                } else {
                    orderIdMap.put("orderId", mallOrder1.getOrderId());
                    logger.info("返回的订单ID为" + mallOrder1.getOrderId());
                    return R.ok(orderIdMap);
                }
            } else {
                //判断是否为用户的免费订单
                String date = DateUtils.getStringDate();
                MallOrderEntity mallOrder1 = hangjiaOrderSyncService.getMallOrder(userId, commodityId, date);
                if (mallOrder1 == null) {
                    synchronized (this) {
                        mallOrder1 = hangjiaOrderSyncService.getMallOrder(userId, commodityId, date);
                        if (mallOrder1 == null) {
                            int flag = hangjiaOrderSyncService.addOrder(hangjiaOrder);
                            if (1 == flag) {
                                if (null != hangjiaOrder.getOrderCode()) {
                                    MallOrderEntity mallOrder2 = hangjiaOrderSyncService.getMallOrder(userId, commodityId, date);
                                    HashMap<String, Object> orderIdMap = new HashMap<>();
                                    logger.info("免费的订单新增成功！" + hangjiaOrder.getOrderCode() + ",orderId==>" + mallOrder2.getOrderId());
                                    orderIdMap.put("orderId", mallOrder2.getOrderId());
                                    return R.ok(orderIdMap);
                                } else {
                                    logger.error("传入的OrderCode为:" + hangjiaOrder.getOrderCode());
                                    return R.error("传入的OrderCode为null");
                                }
                            } else {
                                logger.error("免费订单插入失败！" + mallOrder1.getOrderNo() + ",flag=" + flag);
                                return R.error("免费订单插入失败！");
                            }
                        } else {
                            HashMap<String, Object> orderIdMap = new HashMap<>();
                            orderIdMap.put("orderId", mallOrder1.getOrderId());
                            logger.info("返回的订单ID为" + mallOrder1.getOrderId());
                            return R.ok(orderIdMap);
                        }
                    }
                } else {
                    HashMap<String, Object> orderIdMap = new HashMap<>();
                    orderIdMap.put("orderId", mallOrder1.getOrderId());
                    logger.info("返回的订单ID为" + mallOrder1.getOrderId());
                    return R.ok(orderIdMap);
                }
            }
        } catch (Exception e) {
            logger.error("其他异常,map=" + map2, e);
            throw new RuntimeException("其他异常");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/updateGoodsCategoryId", method = RequestMethod.POST)
    public R updateGoodsCategoryId(@RequestBody HashMap<String, Object> map) {
        //类目ID
        Integer goodsCategoryId = (Integer) map.get("goodsCategoryId");
        String goodsIds = (String) map.get("goodsIdList");
        if (goodsIds == null && goodsCategoryId == 0) {
            return R.error("传入的参数为空!");
        }
        try {
            hangjiaCommoditySyncService.updateGoodsCategoryId(goodsIds, goodsCategoryId);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("出现未知异常");
        }
    }
}



