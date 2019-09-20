package io.renren.service.impl.ask;

import com.alibaba.fastjson.JSONObject;
import io.renren.entity.SysUserEntity;
import io.renren.service.ask.UnlimitedAskCourseService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.ShiroUtils;
import io.renren.utils.http.HttpClientUtil4_3;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会答无限次提问课程管理Service 实现类
 * @author chen xin yu
 * @date 2019-05-07 14:43
 */
@Service
public class UnlimitedAskCourseServiceImpl implements UnlimitedAskCourseService {

    @Value("#{application['kuaida.api']}")
    private String KUAIDA_API;

    @Override
    public PageUtils getUnlimitedAskCourseList(Integer currentPage, Integer pageSize, String courseId, String courseName) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("currentPage",currentPage.toString());
        paramMap.put("pageSize",pageSize.toString());
        paramMap.put("courseId",courseId);
        paramMap.put("courseName",courseName);
        String httpResult = HttpClientUtil4_3.sendHttpPost(KUAIDA_API+"/api/unlimitedAskCourse/list", paramMap);
        //无限次提问课程集合
        List<Map> unlimitedAskCourseList = new ArrayList<>();
        Integer total = 0;
        if (StringUtils.isNotBlank(httpResult)) {
            JSONObject resultJson = JSONObject.parseObject(httpResult);
            int statusCode = resultJson.getIntValue("code");
            if (statusCode == 1 || statusCode == 200 ) {
                JSONObject dataJson = (JSONObject) resultJson.get("data");
                unlimitedAskCourseList = (List<Map>) dataJson.get("list");
                total = Integer.parseInt(String.valueOf(dataJson.get("total")));
                unlimitedAskCourseList.forEach(e -> {
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    if (!ObjectUtils.isEmpty(e.get("createTime"))) {
                        LocalDateTime createTime = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) e.get("createTime")), ZoneId.systemDefault());
                        e.put("createTime",createTime.format(df));
                    }
                    if (!ObjectUtils.isEmpty(e.get("updateTime"))) {
                        LocalDateTime updateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli((Long) e.get("updateTime")), ZoneId.systemDefault());
                        e.put("updateTime",updateTime.format(df));
                    }
                });
            }
        }
        return new PageUtils(unlimitedAskCourseList, total, pageSize, currentPage);
    }

    @Override
    public R saveOrUpdateUnlimitedAskCourse(String id, String courseId, String courseName, Integer dr) {
        if (ObjectUtils.isEmpty(dr) || dr.equals(0)) {
            if (StringUtils.isBlank(courseId) || StringUtils.isBlank(courseName)) {
                R.error("请完善课程信息");
            }
        }
        //当前操作用户
        SysUserEntity currentUser = ShiroUtils.getUserEntity();
        Map<String,String> params = new HashMap<>();
        if (StringUtils.isNotBlank(id)) {
            params.put("id",id);
        }
        params.put("courseId",courseId);
        params.put("courseName",courseName);
        if (!ObjectUtils.isEmpty(dr)) {
            params.put("dr",String.valueOf(dr));
        }
        if (ObjectUtils.isEmpty(id)) {
            //新增
            params.put("createUserName",currentUser.getNickName());
        }
        params.put("updateUserName",currentUser.getNickName());
        String httpResult = HttpClientUtil4_3.sendHttpPost(KUAIDA_API+"/api/unlimitedAskCourse/addOrUpdate", params);
        if (StringUtils.isNotBlank(httpResult)) {
            JSONObject resultJson = JSONObject.parseObject(httpResult);
            int statusCode = resultJson.getIntValue("code");
            if (statusCode == 1 || statusCode == 200 ) {
                return R.ok();
            } else {
                String errorMessage = StringUtils.isNotBlank(resultJson.getString("msg")) ? resultJson.getString("msg") :resultJson.getString("message");
                return R.error(errorMessage);
            }
        }
        return R.error();
    }
}
