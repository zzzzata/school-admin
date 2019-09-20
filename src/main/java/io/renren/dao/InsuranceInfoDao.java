package io.renren.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import io.renren.entity.CoursesEntity;
import io.renren.entity.InsuranceInfoEntity;
import io.renren.entity.RecordInfoEntity;
import io.renren.pojo.SelectionItem;
import io.renren.pojo.course.CoursesPOJO;
/**
 * 学员档案-基础信息
 * @author lintf 
 *
 */
public interface InsuranceInfoDao extends BaseDao<InsuranceInfoEntity> {
	
	/**
	 * 根据商品id取得产品编号档案
	 * @param goodsId
	 * @return
	 */
	InsuranceInfoEntity queryByMallGoodsId(@Param("goodsId")Long goodsId); 
	 //判断保险商品
    int checkInsuranceInfoExist(Long insuranceInfoId);
	
}
