package io.renren.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import io.renren.entity.CourseClassplanLivesEntity;
import io.renren.entity.CourseUserplanDetailEntity;
import io.renren.entity.CourseUserplanEntity;
import io.renren.entity.MallGoodsInfoEntity;
import io.renren.entity.MallOrderEntity;
import io.renren.entity.UsersEntity;

public interface CourseClassplanCheckoutDao {

	UsersEntity getUserByPhone(String phoneNum);

	MallOrderEntity getOrderByNo(String orderNo);

	MallGoodsInfoEntity getGoodById(Long goodId);

	CourseClassplanLivesEntity getClassplanLiveById(String classplanliveId);

	CourseUserplanEntity getUserplanByOrderNo(String orderNo);

	List<CourseUserplanDetailEntity> getUserplanDetailsByUserPlanId(Long userPlanId);

	Map<String, Object> getUserplanDetailsByMap(Map<String, Object> mapp);

}
