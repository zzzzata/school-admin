package com.hq.courses.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hq.courses.entity.CsCourseLiveDetailsEntity;
import com.hq.courses.service.CsCourseLiveDetailsRecordService;
import com.hq.courses.service.CsCourseLiveDetailsService;
import io.renren.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;


/**
 * 课次
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-15 11:08:20
 */
@Controller
@RequestMapping("cscourselivedetails")
public class CsCourseLiveDetailsController extends AbstractController {
	@Autowired
	private CsCourseLiveDetailsService csCourseLiveDetailsService;
	@Autowired
	private CsCourseLiveDetailsRecordService csCourseLiveDetailsRecordService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
//	@RequiresPermissions("cscourselivedetails:list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		longQuery(map, request, "courseId");
		//查询列表数据
		List<CsCourseLiveDetailsEntity> csCourseLiveDetailsList = csCourseLiveDetailsService.queryList(map);
		int total = csCourseLiveDetailsService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(csCourseLiveDetailsList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{liveId}")
//	@RequiresPermissions("cscourselivedetails:info")
	public R info(@PathVariable("liveId") Long liveId , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("liveId",liveId);
		CsCourseLiveDetailsEntity csCourseLiveDetails = csCourseLiveDetailsService.queryObject(map);
		return R.ok().put(csCourseLiveDetails);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
//	@RequiresPermissions("cscourselivedetails:save")
	public R save(@RequestBody CsCourseLiveDetailsEntity csCourseLiveDetails , HttpServletRequest request){
		csCourseLiveDetails.setCreatePerson(getUserId());
		//保存
		csCourseLiveDetailsService.save(csCourseLiveDetails);	
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
//	@RequiresPermissions("cscourselivedetails:update")
	public R update(@RequestBody CsCourseLiveDetailsEntity csCourseLiveDetails , HttpServletRequest request){
		//修改
		csCourseLiveDetailsService.update(csCourseLiveDetails);
		csCourseLiveDetailsRecordService.save(csCourseLiveDetails.getLiveId(),getUserId());
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
//	@RequiresPermissions("cscourselivedetails:delete")
	public R delete(@RequestBody Long[] liveIds , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",liveIds);
		csCourseLiveDetailsRecordService.save(liveIds[0],getUserId());
		csCourseLiveDetailsService.deleteBatch(map);
		return R.ok();
	}
	
}
