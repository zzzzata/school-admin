package io.renren.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import io.renren.entity.CoursesEntity;
import io.renren.entity.InsuranceRecordEntity;
import io.renren.entity.RecordInfoEntity;
import io.renren.pojo.SelectionItem;
import io.renren.pojo.course.CoursesPOJO;
/**
 * 保险档案
 * @author lintf 
 *
 */
public interface InsuranceRecordDao extends BaseDao<InsuranceRecordEntity> {
    List queryListPOJO(Map<String, Object> map);

    int queryPOJOTotal(Map<String, Object> map);

	Date getSendTime(@Param("today")String today, @Param("userId")Long userId);

    /**
     * 驳回协议状态
     * @param e
     */
    void updateByContract(InsuranceRecordEntity e);
    /**
     * 根据订单order_id删除保险档案(dr=1)
     * @param ids
     */
    void  deleteInsuranceRecordByOrderId(Long[] ids);

    /**
     * 通过保单
     * @param ids
     */
    void  passAction(@Param("passTime")Date passTime,@Param("ids")Long[] ids);

    /**
     * 不通过保单
     * @param ids
     */
    void  passCancel(@Param("ids")Long[] ids);
    /**
     * 检测该学员已通过保险信息的数量
     * @param id
     */
    int checkCount(@Param("id")Long id);

    /**
     * 保险记录变更时保存原来记录到日志表
     * @param e
     */
    void saveInsuranceLog(InsuranceRecordEntity e);
}
