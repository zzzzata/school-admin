package io.renren.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.renren.entity.NcSchoolCourseclassplanLiveEntity;
import io.renren.pojo.NcUserListPOJO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import io.renren.entity.NcSchoolCourseclassplanEntity;
import io.renren.service.NcSchoolCourseclassplanService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;


/**
 * NC线下排课信息表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-25 15:53:42
 */
@Controller
@RequestMapping("ncschoolcourseclassplan")
public class NcSchoolCourseclassplanController extends AbstractController {
	@Autowired
	private NcSchoolCourseclassplanService ncSchoolCourseclassplanService;
	
	@RequestMapping("/ncschoolcourseclassplan.html")
	public String list(){
		return "ncschoolcourseclassplan/ncschoolcourseclassplan.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("ncschoolcourseclassplan:list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
        String classplanName = ServletRequestUtils.getStringParameter(request, "classplanName", "");
        String courseName = ServletRequestUtils.getStringParameter(request, "courseName", "");
        String schoolName = ServletRequestUtils.getStringParameter(request, "schoolName", "");
        String courseTeacherName = ServletRequestUtils.getStringParameter(request, "courseTeacherName", "");
        String startTime = ServletRequestUtils.getStringParameter(request, "starTime", "");
        String endTime = ServletRequestUtils.getStringParameter(request, "endTime", "");
        map.put("classplanName",classplanName);
        map.put("courseName",courseName);
        map.put("schoolName",schoolName);
        map.put("courseTeacherName",courseTeacherName);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        //查询列表数据
		List<NcSchoolCourseclassplanEntity> ncSchoolCourseclassplanList = ncSchoolCourseclassplanService.queryList(map);
		int total = ncSchoolCourseclassplanService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(ncSchoolCourseclassplanList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	/**
	 * 排课子表
	 */
	@ResponseBody
	@RequestMapping("/classplanDetail")
	//@RequiresPermissions("ncschoolcourseclassplan:list")
	public R detaiList(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
        String classplanId = ServletRequestUtils.getStringParameter(request, "classplanId", "");
        if (StringUtils.isBlank(classplanId)){
            return R.error("请选择排课！");
        }
        map.put("classplanId",classplanId);
        //查询列表数据
		List<NcSchoolCourseclassplanLiveEntity> liveList = ncSchoolCourseclassplanService.queryDetailList(map);
		int total = ncSchoolCourseclassplanService.queryDetailTotal(map);
		PageUtils pageUtil = new PageUtils(liveList, total , request);
		return R.ok().put(pageUtil);
	}

	/**
	 * 排课下的学员列表
	 */
	@ResponseBody
	@RequestMapping("/userList")
	//@RequiresPermissions("ncschoolcourseclassplan:list")
	public R userList(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
        String classplanId = ServletRequestUtils.getStringParameter(request, "classplanId", "");
        String userName = ServletRequestUtils.getStringParameter(request, "userName", "");
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile", "");
        if (StringUtils.isBlank(classplanId)){
            return R.error("请选择排课！");
        }
        map.put("classplanId",classplanId);
        map.put("userName",userName);
        map.put("mobile",mobile);
        //查询列表数据
		List<NcUserListPOJO> liveList = ncSchoolCourseclassplanService.queryUserList(map);
		int total = ncSchoolCourseclassplanService.queryUserListTotal(map);
		PageUtils pageUtil = new PageUtils(liveList, total , request);
		return R.ok().put(pageUtil);
	}


	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("ncschoolcourseclassplan:info")
	public R info(@PathVariable("id") Long id , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		longQuery(map, request, "id");
		NcSchoolCourseclassplanEntity ncSchoolCourseclassplan = ncSchoolCourseclassplanService.queryObject(map);
		return R.ok().put(ncSchoolCourseclassplan);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	//@RequiresPermissions("ncschoolcourseclassplan:save")
	public R save(@RequestBody NcSchoolCourseclassplanEntity ncSchoolCourseclassplan , HttpServletRequest request){
		//默认状态
	    ncSchoolCourseclassplan.setStatus(1);

		//保存
		ncSchoolCourseclassplanService.save(ncSchoolCourseclassplan);	
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	//@RequiresPermissions("ncschoolcourseclassplan:update")
	public R update(@RequestBody NcSchoolCourseclassplanEntity ncSchoolCourseclassplan , HttpServletRequest request){

		//修改时间
		ncSchoolCourseclassplan.setModifiedTime(new Date());
		//修改
		ncSchoolCourseclassplanService.update(ncSchoolCourseclassplan);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	//@RequiresPermissions("ncschoolcourseclassplan:delete")
	public R delete(@RequestBody Long[] ids , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",ids);
		ncSchoolCourseclassplanService.deleteBatch(map);	
		return R.ok();
	}
	
}
