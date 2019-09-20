package io.renren.dao;

import io.renren.pojo.course.SubjectStatisticsPOJO;

/**
 * @Auther: chenda
 * @Date: 2019/7/27 10:24
 * @Description:
 */
public interface StatisticsTeacherDao extends BaseDao<SubjectStatisticsPOJO> {

    SubjectStatisticsPOJO getNamesByIds(SubjectStatisticsPOJO pojo);

    int countWisedomCourse(String classPlanId);
}
