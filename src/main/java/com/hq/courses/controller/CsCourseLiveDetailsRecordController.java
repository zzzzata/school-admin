package com.hq.courses.controller;

import com.hq.courses.entity.CsCourseLiveDetailsRecordEntity;
import com.hq.courses.service.CsCourseLiveDetailsRecordService;
import io.renren.controller.AbstractController;
import io.renren.utils.PMapUtils;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 课次
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-22 11:43:43
 */
@Controller
@RequestMapping("cscourselivedetailsrecord")
public class CsCourseLiveDetailsRecordController extends AbstractController {
	@Autowired
	private CsCourseLiveDetailsRecordService csCourseLiveDetailsRecordService;

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params , HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		map.put("courseId",PMapUtils.getLong(params, "courseId"));
		map.put("liveId",PMapUtils.getLong(params, "liveId"));
		map.put("liveName",PMapUtils.getString(params, "liveName"));
		//查询列表数据
		List<CsCourseLiveDetailsRecordEntity> csCourseLiveDetailsRecordList = csCourseLiveDetailsRecordService.queryList(map);
		int total = csCourseLiveDetailsRecordService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(csCourseLiveDetailsRecordList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{liveRecordId}")
	@RequiresPermissions("cscourselivedetailsrecord:info")
	public R info(@PathVariable("liveRecordId") Long liveRecordId , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		longQuery(map, request, "liveRecordId");
		CsCourseLiveDetailsRecordEntity csCourseLiveDetailsRecord = csCourseLiveDetailsRecordService.queryObject(map);
		return R.ok().put(csCourseLiveDetailsRecord);
	}

	
}
