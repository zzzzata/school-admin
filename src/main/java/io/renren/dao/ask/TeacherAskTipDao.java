package io.renren.dao.ask;

import io.renren.entity.ask.TeacherAskTipEntity;
import io.renren.pojo.ask.TeacherAskTipPOJO;

import java.util.List;
import java.util.Map;

/**
 * 会答教师提问标签权限Dao
 * @author chen xin yu
 * @date 2019-04-30 17:13
 */
public interface TeacherAskTipDao {

    /**
     * 新增班主任提问权限记录
     * @param teacherAskTipEntity
     * @author chen xin yu
     * @date 2019-05-05 17:03
     */
    int insert(TeacherAskTipEntity teacherAskTipEntity);

    /**
     * 更新班主任提问权限记录
     * @param teacherAskTipEntity
     * @return
     */
    int updateById(TeacherAskTipEntity teacherAskTipEntity);

    /**
     * 分页查询班主任提问标签权限集合
     * @param params        查询条件参数
     * @return List<TeacherAskTipPOJO>
     * @author chen xin yu
     * @date 2019-05-05 09:49
     */
    List<TeacherAskTipPOJO> queryTeacherAskTipList(Map params);

    /**
     * 班主任提问标签权限集合数据总量
     * @param params 查询条件参数
     * @return Integer
     */
    Integer teacherAskTipListTotal(Map<String, Object> params);
}
