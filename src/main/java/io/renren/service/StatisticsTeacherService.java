package io.renren.service;

import io.renren.pojo.course.SubjectStatisticsPOJO;
import java.util.List;
import java.util.Map;

/**
 * @Auther: chenda
 * @Date: 2019/7/25 09:47
 * @Description:
 */
public interface StatisticsTeacherService {

    List<SubjectStatisticsPOJO> getDataFromRemote(Map<String, Object> param);


    Map<String,Object> OrganizeData(List<SubjectStatisticsPOJO> userList);


}
