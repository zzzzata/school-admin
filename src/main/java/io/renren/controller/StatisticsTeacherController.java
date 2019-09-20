package io.renren.controller;

import io.renren.pojo.course.SubjectStatisticsPOJO;
import io.renren.service.StatisticsTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Auther: chendongxin
 * @Date: 2019/7/25 09:20
 * @Description:
 */

@RestController
@RequestMapping("/statistics")
public class StatisticsTeacherController extends AbstractController{

    @Autowired
    private StatisticsTeacherService statisticsTeacherService;

    @RequestMapping("/getTeacherStatistics")
    public Map<String,Object> getTeacherStatistics(@RequestParam Map<String,Object> param){
        List<SubjectStatisticsPOJO> userList = statisticsTeacherService.getDataFromRemote(param);
        return statisticsTeacherService.OrganizeData(userList);
    }
}
