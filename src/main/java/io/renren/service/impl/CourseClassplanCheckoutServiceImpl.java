package io.renren.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestUtils;

import io.renren.dao.CourseClassplanCheckoutDao;
import io.renren.entity.CourseClassplanLivesEntity;
import io.renren.entity.CourseUserplanDetailEntity;
import io.renren.entity.CourseUserplanEntity;
import io.renren.entity.MallGoodsInfoEntity;
import io.renren.entity.MallOrderEntity;
import io.renren.entity.UsersEntity;
import io.renren.service.CourseClassplanCheckoutService;
import io.renren.utils.RRException;
@Service("courseClassplanCheckoutService")
public class CourseClassplanCheckoutServiceImpl implements CourseClassplanCheckoutService {

	@Autowired 
	private CourseClassplanCheckoutDao courseClassplanCheckoutDao;
	
	@Override
	public String checkout(String phoneNum, String orderNo, Long goodId, String classplanliveId) {
		//TODO 输入条件前期校验
		UsersEntity user = courseClassplanCheckoutDao.getUserByPhone(phoneNum);
		if(null == user){
			throw new RRException("输入的手机号不存在！请到【学员档案】检查该用户是否存在！");
		}
		MallOrderEntity order = courseClassplanCheckoutDao.getOrderByNo(orderNo);
		if(null == order){
			throw new RRException("输入的订单号不存在！请到【订单档案】检查该订单是否存在！");
		}
		if(user.getUserId().longValue() != order.getUserId().longValue()){
			throw new RRException("输入的订单号对应的手机号与输入的手机号不符！请到【订单档案】检查该订单对应的手机号是否正确！");
		}
		if(goodId.longValue() != order.getCommodityId().longValue()){
			throw new RRException("输入的订单号对应的商品id与输入的商品id不符！请到【订单档案】检查该订单对应的商品id是否正确！");
		}
		MallGoodsInfoEntity good = courseClassplanCheckoutDao.getGoodById(goodId);
		if(null == good){
			throw new RRException("输入的商品id不存在！请到【商品档案】检查该商品是否存在或是否审核通过！");
		}
		CourseUserplanEntity userplan = courseClassplanCheckoutDao.getUserplanByOrderNo(orderNo);
		if(null == userplan){
			throw new RRException("输入的订单号对应的学员规划未生成！请到【学员规划档案】检查该订单对应的学员规划是否生成！");
		}
		CourseClassplanLivesEntity classplanLive = courseClassplanCheckoutDao.getClassplanLiveById(classplanliveId);
		if(null == classplanLive){
			throw new RRException("输入的直播课次名称不存在！请到【直播明细档案】检查该直播课次是否存在！");
		}
		String[] strClassTypeIds = classplanLive.getLiveClassTypeIds().split(",");
		boolean flag = Arrays.asList(strClassTypeIds).contains(order.getClassTypeId().toString());
		if(!flag){
			throw new RRException("输入的直播课次名称对应的班型权限不包含输入订单号对应的班型！请分别到【排课计划档案】和【订单档案】检查该直播课次的班型权限是否包含订单对应的班型！");
		}

		//TODO 校验学员规划
		List<CourseUserplanDetailEntity> userplanDetails = courseClassplanCheckoutDao.getUserplanDetailsByUserPlanId(userplan.getUserPlanId());
		Map<String, Object> mapp=new HashMap<String, Object>();
		if(null != userplanDetails && userplanDetails.size() > 0){
			for (CourseUserplanDetailEntity userplanDetail : userplanDetails) {
				mapp.put("userplanDetailId", userplanDetail.getUserplanDetailId());
				mapp.put("classplanId", classplanLive.getClassplanId());
				mapp.put("courseId", classplanLive.getCourseId());
				Map<String, Object> map = courseClassplanCheckoutDao.getUserplanDetailsByMap(mapp);
				if(null != map){
					return "恭喜，您有该节输入的直播课次的观看权限，若登陆学习中心无显示所输入的直播课次，请联系管理员处理！！！";
				}
			}
		}
		
		return "输入的直播课次名称对应的排课计划和输入的订单号对应的学员规划没有生成相应的学习计划！请到【学习计划档案】生成相应课程的学习计划！！！";
	}

}
