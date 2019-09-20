package io.renren.service.impl;

import com.alibaba.fastjson.JSON;
import io.renren.dao.StatisticsTeacherDao;
import io.renren.pojo.course.SubjectStatisticsPOJO;
import io.renren.service.StatisticsTeacherService;
import io.renren.service.SysTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: chenda
 * @Date: 2019/7/25 09:48
 * @Description:
 */
@Service
public class StatisticsTeacherServiceImpl implements StatisticsTeacherService {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("#{application['pom.bi.teacher.url']}")
    private String uriString;

    @Value("#{application['pom.bi.tiku.url']}")
    private String tikuString;

    @Autowired
    private SysTeamService sysTeamService;


    @Autowired
    private StatisticsTeacherDao statisticsTeacherDao;

    private DecimalFormat df = new DecimalFormat("0.00");


    @Override
    public List<SubjectStatisticsPOJO> getDataFromRemote(Map<String, Object> param) {
        if(param.get("groupByField").equals("") || param.get("groupByField")==null){
            return null;
        }
        Long teamId = null;
        if(param.get("teamId")!=null && !param.get("teamId").equals("")){
            teamId =  Long.valueOf((String)param.get("teamId"));
        }
        ArrayList<Long> teamIdList = new ArrayList<>() ;
        List<Long> tempTeamIdList = new ArrayList<>();
        if(teamId!=null){
            teamIdList.add(teamId);
            tempTeamIdList.add(teamId);
            Map<String,Object> map = new HashMap<>();
            map.put("teamIdList",teamIdList);
            teamIdList  = sysTeamService.getChildrenIdList(map);
            while(teamIdList.size()>0){
                map.put("teamIdList",teamIdList);
                tempTeamIdList.addAll(teamIdList);
                teamIdList =  sysTeamService.getChildrenIdList(map);
            }
        }
        Map<String, Object> body = new HashMap<>();
        List<SubjectStatisticsPOJO> tempList = new ArrayList<>();
        for (Iterator<String> iterator = param.keySet().iterator(); iterator.hasNext(); ) {
            String tempKey = iterator.next();
            if (param.get(tempKey) instanceof String && param.get(tempKey).equals("")) {
                iterator.remove();
            }
        }
        ArrayList<Object> userList = new ArrayList<>();
        ResponseEntity<Map> responseEntity = getDataFromRemote(uriString, param, HttpMethod.POST);
        body = responseEntity.getBody();
        userList = (ArrayList) body.get("data");

        for (Iterator<Object> iterator = userList.iterator(); iterator.hasNext(); ) {
            SubjectStatisticsPOJO user = JSON.parseObject(JSON.toJSONString(iterator.next()), SubjectStatisticsPOJO.class);
            SubjectStatisticsPOJO tempUser = statisticsTeacherDao.getNamesByIds(user);
            if (tempUser == null) {
                iterator.remove();
            }else {
                if(teamId!=null){
                    //过滤掉不符合团队条件的
                    Long teamIdTemp = tempUser.getTeamId();
                    if(!tempTeamIdList.contains(teamIdTemp)){
                        iterator.remove();
                        continue;
                    }
                }
                user.setTeamId(tempUser.getTeamId());
                user.setTeamName(tempUser.getTeamName());
                user.setNickName(tempUser.getNickName());
                user.setClassplanName(tempUser.getClassplanName());
                user.setCourseName(tempUser.getCourseName());
                user.setClassName(tempUser.getClassName());
                tempList.add(user);
            }
        }
        return tempList;
    }

    @Override
    public Map<String, Object> OrganizeData(List<SubjectStatisticsPOJO> userList) {
        if(userList==null || userList.size()==0){
            return new HashMap<>();
        }
        String tikuuri = tikuString;
        //筛选出没有做题数据的记录并去重
        List<SubjectStatisticsPOJO> tikuUserList = userList.stream().filter(a->a.getPlanHomewordCount()==0
                && a.getActualHomewordCount()==0 && a.getRightHomewordCount()==0).
                collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(SubjectStatisticsPOJO::getSysUserId))), ArrayList::new));
        //筛选出非智适应的记录
        for(Iterator<SubjectStatisticsPOJO> iterator = tikuUserList.iterator();iterator.hasNext();){
            SubjectStatisticsPOJO pojo = iterator.next();
            int examStageIdCount = statisticsTeacherDao.countWisedomCourse(pojo.getClassplanId());
            if(examStageIdCount>0){
                iterator.remove();
            }
        }
        List<SubjectStatisticsPOJO> tikuReturnList;
        String tikuteacherId = "";
        int listLength = tikuUserList.size();
        //调题库接口,每次传10个老师的数据
        //判断数组可以截取几次
        int num  = getForeachNum(listLength);
        List<SubjectStatisticsPOJO> tikuSubList = new ArrayList<>();
        for(int i=1;i<=num;i++){
            int index = (i-1)*10;
            int tail = i*10;
            //判断是否为最后一次循环
            if(i==num){
                //截取的是视图,且含头不含尾
                tikuSubList = tikuUserList.subList(index,tikuUserList.size());
            }else{
                tikuSubList = tikuUserList.subList(index,tail);
            }

            for(SubjectStatisticsPOJO pojo : tikuSubList){
                tikuteacherId = tikuteacherId +pojo.getSysUserId()+",";
            }
            tikuteacherId = tikuteacherId.substring(0,tikuteacherId.length()-1);
            tikuuri = tikuuri +"?teacherids="+tikuteacherId;
            ResponseEntity<Map> responseEntity  = getDataFromRemote(tikuuri,new HashMap<>(),HttpMethod.POST);
            Map<String, Object> body = responseEntity.getBody();
            tikuReturnList = (ArrayList) body.get("data");
            for(Iterator<SubjectStatisticsPOJO> iterator = tikuReturnList.iterator(); iterator.hasNext();){
                SubjectStatisticsPOJO user = JSON.parseObject(JSON.toJSONString(iterator.next()), SubjectStatisticsPOJO.class);
                for(SubjectStatisticsPOJO pojo :userList){
                    if(pojo.getSysUserId().longValue()==user.getSysUserId().longValue() && pojo.getClassplanId().equals(user.getClassplanId())){
                        pojo.setPlanHomewordCount(user.getPlanHomewordCount());
                        pojo.setRightHomewordCount(user.getRightHomewordCount());
                        pojo.setActualHomewordCount(user.getActualHomewordCount());
                    }
                }
            }
        }
        //根据sysuserid和排课计划进行排序
        userList = userList.stream().sorted(Comparator.comparing(SubjectStatisticsPOJO::getTeamId).
                thenComparing(SubjectStatisticsPOJO::getSysUserId).
                thenComparing(SubjectStatisticsPOJO::getClassplanId)).collect(Collectors.toList());
        //根据团队进行分组
        Map<Long, List<SubjectStatisticsPOJO>> groupByTeamIdData = userList.parallelStream().
                collect(Collectors.groupingBy(SubjectStatisticsPOJO::getTeamId));

        //根据班主任进行分组
        Map<Long, List<SubjectStatisticsPOJO>> groupBySysUserIdData = userList.parallelStream().
                collect(Collectors.groupingBy(SubjectStatisticsPOJO::getSysUserId));


        Map<Long, List<String>> teamCountData = staticsData(groupByTeamIdData);
        Map<Long, List<String>> teacherCountData = staticsData(groupBySysUserIdData);

        for (SubjectStatisticsPOJO pojo : userList) {
            Long teamId = pojo.getTeamId();
            Long teacherId = pojo.getSysUserId();
            //直播出勤率
            String livePer = pojo.getFullDuration() == 0 ? "0.00%" : df.format(pojo.getLiveDuration().doubleValue() * 100d / pojo.getFullDuration().doubleValue()) + "%";
            //总出勤率
            String attendPer = pojo.getFullDuration() == 0 ? "0.00%" : df.format(pojo.getWatchDuration().doubleValue() * 100d / pojo.getFullDuration().doubleValue()) + "%";
            //录播出勤率
            String recordPer = pojo.getMustRecordDuration() == 0 ? "0.00%" : df.format(pojo.getRecordDuration().doubleValue() * 100d / pojo.getMustRecordDuration().doubleValue()) + "%";
            //最大出勤率
            String maxAttendPer = pojo.getMustMaxAttendDuration() == 0 ? "0.00%" : df.format(pojo.getMaxAttendDuration().doubleValue() * 100d / pojo.getMustMaxAttendDuration().doubleValue()) + "%";
            //作业达标率
            String homeWorkPer = pojo.getPlanHomewordCount() == 0 ? "0.00%" : df.format(pojo.getRightHomewordCount().doubleValue() * 100d / pojo.getPlanHomewordCount().doubleValue()) + "%";
            //完成率
            String actualPer = pojo.getPlanHomewordCount() == 0 ? "0.00%" : df.format(pojo.getActualHomewordCount().doubleValue() * 100d / pojo.getPlanHomewordCount().doubleValue()) + "%";

            pojo.setLivePer(livePer);
            pojo.setAttendPer(attendPer);
            pojo.setRecordPer(recordPer);
            pojo.setMaxAttendPer(maxAttendPer);
            pojo.setWorkRate(homeWorkPer);
            pojo.setCompleteRate(actualPer);
            List<String> teamResult = teamCountData.get(teamId);
            pojo.setTeamLivePer(teamResult.get(0));
            pojo.setTeamAttendPer(teamResult.get(1));
            pojo.setTeamRecordPer(teamResult.get(2));
            pojo.setTeamMaxAttendPer(teamResult.get(3));
            pojo.setTeamWorkRate(teamResult.get(4));
            pojo.setTeamCompleteRate(teamResult.get(5));
            List<String> teacherResult = teacherCountData.get(teacherId);
            pojo.setTeacherLivePer(teacherResult.get(0));
            pojo.setTeacherAttendPer(teacherResult.get(1));
            pojo.setTeacherRecordPer(teacherResult.get(2));
            pojo.setTeacherMaxAttendPer(teacherResult.get(3));
            pojo.setTeacherWorkRate(teacherResult.get(4));
            pojo.setTeacherCompleteRate(teacherResult.get(5));
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("data", userList);
        return resultMap;
    }


    public Map<Long, List<String>> staticsData(Map<Long, List<SubjectStatisticsPOJO>> dataSource) {
        Map<Long, List<String>> countData = new HashMap<>();

        for (Iterator<Long> iterator = dataSource.keySet().iterator(); iterator.hasNext(); ) {
            Long tempId = iterator.next();
            Long liveDurationTime = 0l;
            Long fullDurationTime = 0l;
            Long watchDurationTime = 0l;
            Long recordDuratiionTime = 0l;
            Long mustRecordDurationTime = 0l;
            Long maxAttendDurationTime = 0l;
            Long mustMaxAttendDurationTime = 0l;
            Integer rightHomewordCount = 0;
            Integer planHomewordCount = 0;
            Integer actualHomewordCount = 0;
            List<SubjectStatisticsPOJO> tempPojoList = dataSource.get(tempId);
            for (SubjectStatisticsPOJO pojo : tempPojoList) {
                liveDurationTime = liveDurationTime + pojo.getLiveDuration();
                fullDurationTime = fullDurationTime + pojo.getFullDuration();
                watchDurationTime = watchDurationTime + pojo.getWatchDuration();
                recordDuratiionTime = recordDuratiionTime + pojo.getRecordDuration();
                mustRecordDurationTime = mustRecordDurationTime + pojo.getMustRecordDuration();
                maxAttendDurationTime = maxAttendDurationTime + pojo.getMaxAttendDuration();
                mustMaxAttendDurationTime = mustMaxAttendDurationTime + pojo.getMustMaxAttendDuration();
                rightHomewordCount = rightHomewordCount + pojo.getRightHomewordCount();
                planHomewordCount = planHomewordCount + pojo.getPlanHomewordCount();
                actualHomewordCount = actualHomewordCount + pojo.getActualHomewordCount();
            }

            //直播出勤率
            String livePer = fullDurationTime == 0 ? "0.00%" : df.format(liveDurationTime.doubleValue() * 100d / fullDurationTime.doubleValue()) + "%";
            //总出勤率
            String watchPer = fullDurationTime == 0 ? "0.00%" : df.format(watchDurationTime.doubleValue() * 100d / fullDurationTime.doubleValue()) + "%";
            //录播出勤率
            String recordPer = mustRecordDurationTime == 0 ? "0.00%" : df.format(recordDuratiionTime.doubleValue() * 100d / mustRecordDurationTime.doubleValue()) + "%";
            //最大出勤率
            String maxAttendPer = mustMaxAttendDurationTime == 0 ? "0.00%" : df.format(maxAttendDurationTime.doubleValue() * 100d / mustMaxAttendDurationTime.doubleValue()) + "%";
            //作业达标率
            String homeWorkPer = planHomewordCount == 0 ? "0.00%" : df.format(rightHomewordCount.doubleValue() * 100d / planHomewordCount.doubleValue()) + "%";
            //完成率
            String actualPer = planHomewordCount == 0 ? "0.00%" : df.format(actualHomewordCount.doubleValue() * 100d / planHomewordCount.doubleValue()) + "%";

            List<String> perData = Arrays.asList(livePer, watchPer, recordPer, maxAttendPer, homeWorkPer, actualPer);

            countData.put(tempId, perData);
        }
        return countData;
    }



    public ResponseEntity<Map> getDataFromRemote(String uriAddress, Map<String, Object> param, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        URI uri = null;
        try {
            uri = new URI(uriAddress);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(param), headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(uri, method, entity, Map.class);
        return responseEntity;
    }


    public int getForeachNum(int listLength){
        int num = listLength/10;
        int remainder = listLength % 10;
        if(num ==0 && remainder >0){
            num=1;
        }else if(num >0 && remainder>0){
            num++;
        }
        return num;
    }



}
