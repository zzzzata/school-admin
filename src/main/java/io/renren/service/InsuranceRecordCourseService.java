package io.renren.service;
import java.util.List;
import java.util.Map;

import io.renren.entity.InsuranceRecordCourseEntity;
import io.renren.entity.InsuranceRecordEntity;
import io.renren.entity.MallOrderEntity;
import io.renren.entity.OrderMessageConsumerEntity;
import io.renren.pojo.MallGoodsDetailsPOJO;
import io.renren.utils.R;

public interface InsuranceRecordCourseService {
	
	void saveInsuranceRecordCourse(InsuranceRecordCourseEntity e);

	void updateInsuranceRecordCourse(InsuranceRecordCourseEntity e);
	List<InsuranceRecordCourseEntity> queryList(Map<String,Object> queryMap); 

	InsuranceRecordCourseEntity queryObject(Long id);

	List<InsuranceRecordCourseEntity> queryGooodsCourse(Long areaId, Long goodsId, Long insuranceInfoId);
  
	/**
	 * 投保课程对比
	 * @param detail 当前的课程
	 * @param courseDetail 现在能取到的商品省份对应下的课程
	 * @return
	 */
	List<InsuranceRecordCourseEntity> InsuranceRecordCourseUpdateCheck(List<InsuranceRecordCourseEntity> detail,
			List<InsuranceRecordCourseEntity> courseDetail);
	/**
	 * 根据主键id设置子表dr=1
	 * @param insuranceRecordId
	 */
	void updateDrByinsuranceRecordId(Long insuranceRecordId);

	/**
	 * 根据条件查询投保的课程数量
	 * @param areaId
	 * @param goodsId
	 * @param insuranceInfoId
	 * @return
	 */
	List<Map<String, Object>> countGooodsCourseByArea(Long areaId, Long goodsId, Long insuranceInfoId);
	/**
	 * controller中的设置商品子表中的是否投保
	 * @param mallGoodsDetailsList 从前端传回来的商品子表
	 * @param type 1是投保 0是取消投保
	 * @return
	 */
	R insuranceActionUpdate(List<MallGoodsDetailsPOJO> mallGoodsDetailsList, int type);

}
