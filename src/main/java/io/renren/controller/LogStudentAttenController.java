package io.renren.controller;

import io.renren.common.doc.ParamKey;
import io.renren.pojo.log.LogStudentAttendPOJO;
import io.renren.pojo.log.LogStudentAttentLiveLogDetails;
import io.renren.service.LogStudentAttenService;
import io.renren.service.LogStudentAttendService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.http.HttpClientUtil4_3;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.sf.json.JSONArray.toList;
import io.renren.entity.SysUserEntity;
import io.renren.pojo.log.LiveAttendPOJO;
import io.renren.pojo.log.LogStudentAttendPOJO;
import io.renren.service.LogStudentAttenService;
import io.renren.service.LogStudentAttendService;
import io.renren.utils.AttendanceMethodUtils;
import io.renren.utils.PageUtils;
import io.renren.utils.R;

/**
 * 考勤统计
 * 班主任角色观看
 * 每个学员
 * @author shihongjie
 * @date 2017-08-23
 */
@Controller
@RequestMapping("logStudentAtten")
public class LogStudentAttenController extends AbstractController {
	@Autowired
	private LogStudentAttendService logStudentAttendService;
	@Autowired
	private LogStudentAttenService logStudentAttenService;

	@Value("#{application['bi.search.domain']}")
	private String BI_SEARCH_DOMAIN;

	/**
	 * 考勤统计
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(HttpServletRequest request){
		
		Long classTeacherId = null;//班主任
		Long classId = null;//班级PK
		
		Long areaId = null;//省份PK
		Long deptId = null;//部门PK
		String statusId = null;
		String date = null;
		try {
			classTeacherId = ServletRequestUtils.getLongParameter(request, "classTeacherId", 0);
			classId = ServletRequestUtils.getLongParameter(request, "classId", 0);
			
			areaId = ServletRequestUtils.getLongParameter(request, "areaId", 0);
			deptId = ServletRequestUtils.getLongParameter(request, "deptId", 0);
			statusId = ServletRequestUtils.getStringParameter(request, "statusId");
			
			date = ServletRequestUtils.getStringParameter(request, "date");
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		if(StringUtils.isBlank(date)){
			return R.error("请选择日期！");
		}

		int currPage = ServletRequestUtils.getIntParameter(request,  ParamKey.In.PAGE, 1);
		int pageSize = ServletRequestUtils.getIntParameter(request,  ParamKey.In.LIMIT, ParamKey.In.DEFAULT_MAX_LIMIT);

		//查询列表数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("classTeacherId", classTeacherId);
		map.put("areaId", areaId);
		map.put("classId", classId);
		map.put("deptId", deptId);
		map.put("statusId", statusId);
		map.put("startDate", date+" 00:00:00");
		map.put("endDate", date+" 23:59:59");
		map.put("offset",(currPage-1)*(pageSize-1));
		map.put("limit",pageSize-1);
        //普通用户无权限查看其它人的学员
        //SysUserEntity user = getUser();
        //if(!AttendanceMethodUtils.isAdmin(user)) {
            //map.put("classOwner", user.getUserId());
        //}
		List<LogStudentAttendPOJO> logStudentAttenList = logStudentAttendService.queryLiveAttendList(map);
		int total = 0;

		PageUtils pageUtil = new PageUtils(logStudentAttenList,total,pageSize,currPage);
		return R.ok().put(pageUtil);
	}
	
	@ResponseBody
	@RequestMapping("/listByMobile")
	public R listByMobile(HttpServletRequest request){
		String mobile = null; 
		
		String startDate = null;
		String endDate = null;
		try {
			mobile = ServletRequestUtils.getStringParameter(request, "mobile");
			startDate = ServletRequestUtils.getStringParameter(request, "startDate");
			endDate = ServletRequestUtils.getStringParameter(request, "endDate");
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		if(StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)){
			return R.error("请选择日期！");
		}

		int currPage = ServletRequestUtils.getIntParameter(request,  ParamKey.In.PAGE, 1);
		int pageSize = ServletRequestUtils.getIntParameter(request,  ParamKey.In.LIMIT, ParamKey.In.DEFAULT_MAX_LIMIT);

		//根据学院手机号查询列表数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile", mobile);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("offset",(currPage-1)*(pageSize-1));
		map.put("limit",pageSize-1);
        //普通用户无权限查看其它人的学员
        //SysUserEntity user = getUser();
        //if(!AttendanceMethodUtils.isAdmin(user)) {
            //map.put("classOwner", user.getUserId());
        //}
		List<LogStudentAttendPOJO> logStudentAttenList = logStudentAttendService.queryLiveAttendList(map);
		int total = 0;
		PageUtils pageUtil = new PageUtils(logStudentAttenList,total,pageSize,currPage);
		return R.ok().put(pageUtil);
	}
	
	@ResponseBody
	@RequestMapping("/listByRequirement")
	public R listByRequirement(HttpServletRequest request){
		
		Long classTeacherId = null;//班主任
		Long classId = null;//班级PK
		
		Long areaId = null;//省份PK
		Long deptId = null;//部门PK
		String statusId = null;
		String startDate = null;
		String endDate = null;
		try {
			classTeacherId = ServletRequestUtils.getLongParameter(request, "classTeacherId", 0);
			classId = ServletRequestUtils.getLongParameter(request, "classId", 0);
			
			areaId = ServletRequestUtils.getLongParameter(request, "areaId", 0);
			deptId = ServletRequestUtils.getLongParameter(request, "deptId", 0);
			statusId = ServletRequestUtils.getStringParameter(request, "statusId");
			
			startDate = ServletRequestUtils.getStringParameter(request, "startDate");
			endDate = ServletRequestUtils.getStringParameter(request, "endDate");
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		if(StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)){
			return R.error("请选择日期！");
		}

		int currPage = ServletRequestUtils.getIntParameter(request,  ParamKey.In.PAGE, 1);
		int pageSize = ServletRequestUtils.getIntParameter(request,  ParamKey.In.LIMIT, ParamKey.In.DEFAULT_MAX_LIMIT);

		//查询列表数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("classTeacherId", classTeacherId);
		map.put("areaId", areaId);
		map.put("classId", classId);
		map.put("deptId", deptId);
		map.put("statusId", statusId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("offset",(currPage-1)*(pageSize-1));
		map.put("limit",pageSize-1);

		List<LogStudentAttendPOJO> logStudentAttenList = logStudentAttendService.queryLiveAttendList(map);
		int total = 0;
		PageUtils pageUtil = new PageUtils(logStudentAttenList,total,pageSize,currPage);
		return R.ok().put(pageUtil);
	}
	
	/*
	@Description:查看学员进入直播间日志
	@Author:DL
	@Date:17:30 2017/11/22
	@params:
	 * @param null
	*/
	@RequestMapping(value = "logDetails")
    @ResponseBody
    public R logDetalis(HttpServletRequest request){
        Long userplanId = null;//学员规划
        String classplanLiveId = null;//直播课次ID
        Long userId = null;//学员ID
        String startDateString = null;//起始时间
        String endDateString = null;//结束时间
        userplanId = ServletRequestUtils.getLongParameter(request, "userplanId", 0);
        userId = ServletRequestUtils.getLongParameter(request, "userId", 0);
        classplanLiveId = ServletRequestUtils.getStringParameter(request, "classplanLiveId", null);
        int queryType = ServletRequestUtils.getIntParameter(request, "queryType", 1);
        try {
             startDateString = ServletRequestUtils.getStringParameter(request, "startDate");
             endDateString = ServletRequestUtils.getStringParameter(request, "endDate");
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }
        if(userplanId == 0 || userId == 0 || null == classplanLiveId){
            return R.error("请选择一条有效信息!");
        }
        List<LogStudentAttentLiveLogDetails> logDetailsList = new ArrayList<>();
        //按学员规划查询
        if (queryType == 1){
            logDetailsList = logStudentAttenService.queryLogStudentAttenLiveLogDetailsByUserplanId(userplanId,startDateString,endDateString);
            //按直播课次查询
        }else {
            logDetailsList = logStudentAttenService.queryLogStudentAttenLiveLogDetails(userId, classplanLiveId, userplanId, startDateString, endDateString);
        }
        //查询列表数据
        int total = 0;
        if(null != logDetailsList && !logDetailsList.isEmpty()){
            total = logDetailsList.size();
        }
        PageUtils pageUtil = new PageUtils(logDetailsList, total, request);
        return R.ok().put(pageUtil);
    }
}
