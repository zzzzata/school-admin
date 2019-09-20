package io.renren.service;

import io.renren.pojo.HangjiaCommodityPOJO;

public interface HangjiaCommoditySyncService {
    int addOrUpdateCommodity(HangjiaCommodityPOJO CommodityPOJO, Integer syncStatus);

    void delCommodity(String goodsId);

    //更新商品的上下架状态
    void updateCommodityStatus(String goodsId, Integer issueStatus);

    //批量更新商品的类目ID
    void updateGoodsCategoryId(String goodsId, Integer goodsCategoryId);

}
