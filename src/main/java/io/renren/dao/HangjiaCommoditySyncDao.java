package io.renren.dao;

import io.renren.entity.MallGoodsDetailsEntity;
import io.renren.entity.MallGoodsInfoEntity;
import org.apache.ibatis.annotations.Param;

public interface HangjiaCommoditySyncDao {
    //同步新增商品
    int addGoods(MallGoodsInfoEntity mallGoods);

    //同步更新商品
    int updateGoods(MallGoodsInfoEntity mallGoods);

    //同步新增商品详情
    int addGoodsDetail(MallGoodsDetailsEntity mallGoodsDetails);

    //同步更新的商品详情
    int updateGoodsDetail(MallGoodsDetailsEntity mallGoodsDetails);

    //同步删除商品
    int delGoods(@Param("goodsId") Long goodsId);

    //同步删除商品详情
    int delGoodsDetail(@Param("goodsId") Long goodsId);

    //查找商品
    MallGoodsInfoEntity selectGoods(@Param("goodsId") Long goodsId);

    //查找商品详情
    MallGoodsDetailsEntity selectGoodsDetails(@Param("goodsId") Long goodsId);

    //更新商品的上下架状态
    int updateGoodsStatus(@Param("goodsId") Long goodsId);


    //更新商品的类目ID
    int updateGoodsCategoryId(@Param("id") Long goodsId, @Param("goodsCategoryId") Integer goodsCategoryId);


}
