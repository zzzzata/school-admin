package com.hq.courses.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.hq.courses.entity.CsKnowledgeRecordEntity;
import com.hq.courses.pojo.CsKnowledgeRecordPOJO;
import com.hq.courses.service.CsKnowledgeRecordService;
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
 * @date 2019-04-19 15:18:47
 */
@Controller
@RequestMapping("csknowledgerecord")
public class CsKnowledgeRecordController extends AbstractController {
	@Autowired
	private CsKnowledgeRecordService csKnowledgeRecordService;
	
	@RequestMapping("/csknowledgerecord.html")
	public String list(){
		return "csknowledgerecord/csknowledgerecord.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(HttpServletRequest request){
        List<CsKnowledgeRecordPOJO> csKnowledgeRecordList = new ArrayList<>();
		Map<String, Object> map = getMapPage(request);
		stringQuery(map,request,"courseName");
		stringQuery(map,request,"knowledgeNo");
		stringQuery(map,request,"knowledgeName");
		//查询列表数据
		int total = csKnowledgeRecordService.queryPojoTotal(map);
		if (total > 0){
            csKnowledgeRecordList = csKnowledgeRecordService.queryPojoList(map);
        }
		PageUtils pageUtil = new PageUtils(csKnowledgeRecordList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{knowledgeId}")
	public R info(@PathVariable("knowledgeId") Long knowledgeId , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		longQuery(map, request, "knowledgeId");
		CsKnowledgeRecordEntity csKnowledgeRecord = csKnowledgeRecordService.queryObject(map);
		return R.ok().put(csKnowledgeRecord);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody CsKnowledgeRecordEntity csKnowledgeRecord , HttpServletRequest request){
		//保存
		csKnowledgeRecordService.save(csKnowledgeRecord);	
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody CsKnowledgeRecordEntity csKnowledgeRecord , HttpServletRequest request){

		//修改
		csKnowledgeRecordService.update(csKnowledgeRecord);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] knowledgeIds , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",knowledgeIds);
		csKnowledgeRecordService.deleteBatch(map);	
		return R.ok();
	}
	
}
