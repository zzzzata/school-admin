package io.renren.dao;

import io.renren.entity.MallGoodsDetailsEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品表格详细信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-29 10:11:55
 */
public interface MallGoodsDetailsDao extends BaseDao<MallGoodsDetailsEntity> {
    /**
     * 批量更新状态
     */
    int updateBatch(Map<String, Object> map);

    /**
     * 查询商品所销售的地区
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> queryAreaByGoodId(Map<String, Object> map);

    List<Map<String, Object>> queryListMap(Map<String, Object> map);

    public void deleteBatchNotIn(Map<String, Object> map);

    /**
     * 根据课程id确定该课程在哪些商品下
     *
     * @param courseId 课程id
     * @param schoolId 平台id
     * @return
     */
    List<Long> getGoods(
            @Param(value = "courseId") Long courseId,
            @Param(value = "schoolId") String schoolId);

    /**
     * 根据商品id获取该商品下的课程
     *
     * @param goodsInfoId
     * @param schoolId
     * @return
     */
    List<Long> getCourses(
            @Param(value = "goodsInfoId") Long goodsInfoId,
            @Param(value = "schoolId") String schoolId);

    long getAreaIdByGoodsId(Map<String, Object> map);

    /**
     * 查询商品某一个省份下的课程
     *
     * @param map schoolId:校区PK id:商品ID areaId:省份ID
     * @return
     */
    List<MallGoodsDetailsEntity> selectAreaCouresList(Map<String, Object> map);

    //查询某商品的某省份的科目是否已经存在
    int hasMallGoodsDetail(@Param("mallGoodsId") Long mallGoodsId, @Param("mallAreaId") Long mallAreaId, @Param("courseId") Long courseId);


    /**
     * 删除课程
     * @param id
     * @return
     */
    int deleteCourse(
            @Param(value = "id") Long id);

	void insuranceAction(@Param(value = "type") int type,  @Param(value = "ids") List<Long> ids);

	List<MallGoodsDetailsEntity> queryListByGoodId(Long goodId);
}
