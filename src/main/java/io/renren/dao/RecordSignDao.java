package io.renren.dao;

import java.util.List;
import java.util.Map;

import io.renren.entity.CoursesEntity;
import io.renren.entity.RecordSignEntity;
import io.renren.entity.ReturnVisitEntity;
import io.renren.pojo.RecordSignPOJO;
import io.renren.pojo.SelectionItem;
import io.renren.pojo.course.CoursesPOJO;

/**
 * 学员档案-报读信息
 * @author lintf
 *
 */
public interface RecordSignDao extends BaseDao<RecordSignEntity> {
    List<RecordSignPOJO> queryPOJOList(Map<String,Object> queryMap);

    int queryPOJOTotal(Map<String,Object> queryMap);

    void updateFollowStatus(RecordSignEntity recordSignEntity);

    List<RecordSignPOJO> queryPOJOListByReturnVisit(Map<String,Object> queryMap);

    int queryPOJOTotalByReturnVisit(Map<String,Object> queryMap);

    void updateReturnTime(RecordSignEntity recordSignEntity);


    RecordSignEntity findRecordSignById(Long recordSignId);

    void updateSaveReturnTime(Map map);
}
