package io.renren.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import io.renren.entity.CoursesEntity;
import io.renren.entity.InsuranceRecordCourseEntity;
import io.renren.entity.RecordInfoEntity;
import io.renren.pojo.SelectionItem;
import io.renren.pojo.course.CoursesPOJO;
/**
 * 投保课程查询
 * @author lintf 
 *
 */
public interface InsuranceRecordCourseDao extends BaseDao<InsuranceRecordCourseEntity> {
	List<InsuranceRecordCourseEntity> queryGooodsCourse(@Param("areaId")Long areaId,@Param("goodsId")Long goodsId,@Param("insuranceInfoId")Long insuranceInfoId);
	
	List<Map<String,Object>> countGooodsCourseByArea(@Param("areaId")Long areaId,@Param("goodsId")Long goodsId,@Param("insuranceInfoId")Long insuranceInfoId);
	void updateDrByinsuranceRecordId  (@Param("insuranceRecordId")Long insuranceRecordId);
 
	
}
