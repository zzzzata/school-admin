package io.renren.dao.courseliverelaterecord;

import io.renren.dao.BaseDao;
import io.renren.entity.courseliverelaterecord.CourseLiveRelateRecordEntity;
import io.renren.pojo.courseliverelaterecord.CourseLiveRelateRecordPOJO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 直播课次与录播章节对照关系 DAO
 * @author chen xin yu
 * @date 2019-06-18 14:27
 */
public interface CourseLiveRelateRecordDao extends BaseDao<CourseLiveRelateRecordEntity> {

    /**
     * 查询直播课次与录播关联关系，根据直播分组
     * @param params
     * @return
     */
    List<CourseLiveRelateRecordPOJO> queryCourseLiveRelateRecordGroupByLive(Map<String, Object> params);

    /**
     * 查询课次与录播章节对照关系列表
     * @param params
     * @return
     */
    List<CourseLiveRelateRecordPOJO> queryCourseLiveRelateRecordList(Map<String, Object> params);

    /**
     * 查询课次与录播章节对照关系 数据总量
     * @param params
     * @return
     */
    Integer courseLiveRelateRecordListTotal(Map<String, Object> params);

    /**
     * 根据条件sql更新
     * @param params
     * @param whereSql
     */
    void updateBySql(@Param("params") Map<String, Object> params, @Param("whereSql")String whereSql);
}
