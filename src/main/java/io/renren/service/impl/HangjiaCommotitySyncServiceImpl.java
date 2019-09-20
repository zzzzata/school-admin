package io.renren.service.impl;

import io.renren.dao.HangjiaCommoditySyncDao;
import io.renren.entity.MallGoodsDetailsEntity;
import io.renren.entity.MallGoodsInfoEntity;
import io.renren.pojo.HangjiaCommodityPOJO;
import io.renren.service.HangjiaCommoditySyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service("hangjiaCommoditySyncService")
public class HangjiaCommotitySyncServiceImpl implements HangjiaCommoditySyncService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private HangjiaCommoditySyncDao hangjiaCommoditySyncDao;

    @Override
    public int addOrUpdateCommodity(HangjiaCommodityPOJO commodityPOJO, Integer syncStatus) {

        logger.info("开始从行家后台同步新增商品到蓝鲸后台!");
        TransactionStatus status = null;
        // 开启事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 事物隔离级别，开启新事务
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        //获得事务状态
        status = transactionManager.getTransaction(def);
        MallGoodsInfoEntity mallgoods = new MallGoodsInfoEntity();

        try {
            //产品线ID
            mallgoods.setProductId(10L);
            //商品Id
            mallgoods.setHjGoodsId(commodityPOJO.getId());
            //商品名称
            System.out.println(commodityPOJO.getName());
            mallgoods.setName(commodityPOJO.getName());
            //大图
            mallgoods.setOriginPath(commodityPOJO.getCoverPicture());
            //小图
            mallgoods.setThumbPath(commodityPOJO.getCoverPicture());
            //商品类目ID
            mallgoods.setGoodsCategoryId(commodityPOJO.getGoodsCategoryId());
            //商品类型，观看类型:直播+录播:0,直播:1,录播:2
            mallgoods.setWatchType(commodityPOJO.getType());
            //价格
            mallgoods.setOriginalPrice(commodityPOJO.getPrice().doubleValue());
            //商品有效期
            mallgoods.setDayValidity(commodityPOJO.getValidityDate().longValue());
            //商品简介
            mallgoods.setGoodRecomment(commodityPOJO.getIntroduction());
            //商品发布状态
            mallgoods.setStatus(commodityPOJO.getIssueStatus());
            boolean isDelete = commodityPOJO.getDelete();
            if (isDelete) {
                //商品状态为已删除
                mallgoods.setDr(1);
            } else {
                //商品状态为未删除
                mallgoods.setDr(0);
            }
            //创建人
            mallgoods.setCreatePerson(commodityPOJO.getCreatorId());
            //创建时间
            mallgoods.setCreateTime(commodityPOJO.getCreateTime());
            //修改时间
            mallgoods.setModifyTime(commodityPOJO.getUpdateTime());
            //商品项目类型 （1：行家;2：子墨学院）推过来的默认给1
            mallgoods.setProjectType(commodityPOJO.getProjectType() == null?1:commodityPOJO.getProjectType());
            //以下字段暂时使用默认的
            mallgoods.setPresentPrice(commodityPOJO.getPrice().doubleValue());
            mallgoods.setClassTypeId(0L);
            mallgoods.setProfessionId(0L);
            mallgoods.setLevelId(0L);
            mallgoods.setIsAudited(1);
            mallgoods.setSchoolId("hj");
            mallgoods.setOnlyOne(0);
            //如果同步的状态是1的话，那么就是新增商品
            logger.info("存入的参数为：" + mallgoods.toString());
            int flag = 0;
            if (syncStatus == 1) {
                MallGoodsInfoEntity hangjiaCommodity = hangjiaCommoditySyncDao.selectGoods(commodityPOJO.getId());
                if (hangjiaCommodity == null) {
                    flag = hangjiaCommoditySyncDao.addGoods(mallgoods);
                }
                //插入商品表成功
                if (flag == 1) {
                    logger.info("从行家后台同步商品到蓝鲸的表MallGoodsInfo成功，商品的ID是：" + commodityPOJO.getId());
                    //设置商品的详情表
                    MallGoodsDetailsEntity goodsDetails = new MallGoodsDetailsEntity();
                    //设置商品ID
                    goodsDetails.setHjGoodsId(commodityPOJO.getId());
                    //设置课程ID
                    goodsDetails.setCourseId(commodityPOJO.getCourseId());
                    //设置商品详情的状态值
                    goodsDetails.setDr(0);
                    //以下字段暂时使用默认的
                    goodsDetails.setMallAreaId(24L);
                    goodsDetails.setIsSubstitute(0);
                    goodsDetails.setIsSubstituted(0);
                    goodsDetails.setSchoolId("hj");
                    goodsDetails.setOrderNum(0);
                    goodsDetails.setIsUnitedExam(0);
                    goodsDetails.setIsSuitable(0);
                    goodsDetails.setMallGoodsId(-1L);
                    hangjiaCommoditySyncDao.addGoodsDetail(goodsDetails);
                    logger.info("从行家后台同步商品到蓝鲸的商品详情表成功，商品的ID是：" + commodityPOJO.getId());
                }
                //如果同步的状态是2的话，那么就是更新商品
            } else if (syncStatus == 2) {
                flag = hangjiaCommoditySyncDao.updateGoods(mallgoods);
                //插入商品表成功
                if (flag == 1) {
                    logger.info("从行家后台同步商品到蓝鲸的表MallGoodsInfo成功，商品的ID是：" + commodityPOJO.getId());
                    //设置商品的详情表
                    MallGoodsDetailsEntity goodsDetails = new MallGoodsDetailsEntity();
                    //设置商品ID
                    goodsDetails.setHjGoodsId(commodityPOJO.getId());
                    //设置课程ID
                    goodsDetails.setCourseId(commodityPOJO.getCourseId());
                    //设置商品详情的状态值
                    goodsDetails.setDr(0);
                    //以下字段暂时使用默认的
                    goodsDetails.setMallAreaId(24L);
                    goodsDetails.setIsSubstitute(0);
                    goodsDetails.setIsSubstituted(0);
                    goodsDetails.setSchoolId("hj");
                    goodsDetails.setOrderNum(0);
                    goodsDetails.setIsUnitedExam(0);
                    goodsDetails.setIsSuitable(0);
                    hangjiaCommoditySyncDao.updateGoodsDetail(goodsDetails);
                    logger.info("从行家后台同步商品到蓝鲸的商品详情表成功，商品的ID是：" + commodityPOJO.getId());
                }
            }
        } catch (Exception e1) {
            // 回滚
            transactionManager.rollback(status);
            logger.error("从行家后台同步商品到蓝鲸失败，商品的ID是：" + commodityPOJO.getId(), "错误信息:" + e1.getMessage());
        } finally {
            try {
                logger.info("从行家后台同步商品到蓝鲸的商品详情表成功，商品的ID是：" + commodityPOJO.getId());
                // 提交事务
                transactionManager.commit(status);
                return 1;
            } catch (Exception e2) {
                logger.error("事务提交出错，商品的ID是：" + commodityPOJO.getId() + "错误信息:" + e2.getMessage());
                return 0;
            }
        }
    }


    @Override
    public void delCommodity(String goodsId) {
        logger.info("开始从行家后台同步新增商品到蓝鲸后台!");
        TransactionStatus status = null;
        // 开启事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 事物隔离级别，开启新事务
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        //获得事务状态
        status = transactionManager.getTransaction(def);
        try {
            //判断
            if (goodsId != null) {
                String[] goodsIds = goodsId.split(",");
                for (String goodId : goodsIds) {
                    //判断是否存在这个商品
                    MallGoodsInfoEntity mallGoods = hangjiaCommoditySyncDao.selectGoods(Long.valueOf(goodId));
                    if (mallGoods != null) {
                        //把商品的dr设置为1
                        int flag = hangjiaCommoditySyncDao.delGoods(Long.valueOf(goodId));
                        if (flag == 1) {
                            //判断是否存在这个商品的详情
                            MallGoodsDetailsEntity mallGoodsDetail = hangjiaCommoditySyncDao.selectGoodsDetails(Long.valueOf(goodId));
                            if (mallGoodsDetail != null) {
                                //把商品详情中的dr设置为1
                                hangjiaCommoditySyncDao.delGoodsDetail(Long.valueOf(goodId));
                                logger.info("从行家后台同步新增商品到蓝鲸后台完成!");
                            }
                        }
                    }
                }

            }

        } catch (Exception e1) {
            // 回滚
            transactionManager.rollback(status);
            logger.error("从行家后台同步商品到蓝鲸失败，商品的ID是：" + goodsId, "错误信息:" + e1.getMessage());
        } finally {
            try {
                logger.info("从行家后台同步商品到蓝鲸的商品详情表成功，商品的ID是：" + goodsId);
                // 提交事务
                transactionManager.commit(status);
            } catch (Exception e2) {
                logger.error("事务提交出错，商品的ID是：" + goodsId + "错误信息:" + e2.getMessage());
            }
        }
    }


    @Override
    public void updateCommodityStatus(String goodsId, Integer issueStatus) {

        //判断
        if (goodsId != null) {
            String[] goodsIds = goodsId.split(",");
            for (String goodId : goodsIds) {

                //判断是否存在这个商品
                MallGoodsInfoEntity mallGoods = hangjiaCommoditySyncDao.selectGoods(Long.valueOf(goodId));
                if (mallGoods != null) {
                    hangjiaCommoditySyncDao.updateGoodsStatus(Long.valueOf(goodId));
                }
            }

        }
    }


    @Override
    public void updateGoodsCategoryId(String goodsId, Integer goodsCategoryId) {
        try {
            String[] goodIds = null;
            goodIds = goodsId.split(",");
            for (int i = 0; i < goodIds.length; i++) {
                MallGoodsInfoEntity mallGoodsInfo = hangjiaCommoditySyncDao.selectGoods(Long.valueOf(goodIds[i]));
                if (mallGoodsInfo != null) {
                    hangjiaCommoditySyncDao.updateGoodsCategoryId(mallGoodsInfo.getId(), goodsCategoryId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}