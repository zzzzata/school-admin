package com.hq.courses.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.hq.courses.entity.CsCourseRecordEntity;
import com.hq.courses.pojo.CsCourseRecordPOJO;
import com.hq.courses.service.CsCourseRecordService;
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
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-19 15:18:43
 */
@Controller
@RequestMapping("cscourserecord")
public class CsCourseRecordController extends AbstractController {
	@Autowired
	private CsCourseRecordService csCourseRecordService;
	
	@RequestMapping("/cscourserecord.html")
	public String list(){
		return "cscourserecord/cscourserecord.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(HttpServletRequest request){
        List<CsCourseRecordPOJO> csCourseRecordList = new ArrayList<>();
        Map<String, Object> map = getMapPage(request);
		//查询列表数据
        longQuery(map,request,"courseId");
        stringQuery(map,request,"courseNo");
        stringQuery(map,request,"courseName");
        int total = csCourseRecordService.queryTotal(map);
        if (total > 0){
            csCourseRecordList = csCourseRecordService.queryList(map);
        }
		PageUtils pageUtil = new PageUtils(csCourseRecordList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{courseId}")
	public R info(@PathVariable("courseId") Long courseId , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		longQuery(map, request, "courseId");
		CsCourseRecordEntity csCourseRecord = csCourseRecordService.queryObject(map);
		return R.ok().put(csCourseRecord);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody CsCourseRecordEntity csCourseRecord , HttpServletRequest request){
		//保存
		csCourseRecordService.save(csCourseRecord);	
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody CsCourseRecordEntity csCourseRecord , HttpServletRequest request){
		//修改
		csCourseRecordService.update(csCourseRecord);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] courseIds , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",courseIds);
		csCourseRecordService.deleteBatch(map);	
		return R.ok();
	}
	
}
