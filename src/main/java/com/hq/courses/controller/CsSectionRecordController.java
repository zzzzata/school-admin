package com.hq.courses.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.hq.courses.entity.CsSectionRecordEntity;
import com.hq.courses.pojo.CsSectionRecordPOJO;
import com.hq.courses.service.CsSectionRecordService;
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
 * @date 2019-04-19 15:18:57
 */
@Controller
@RequestMapping("cssectionrecord")
public class CsSectionRecordController extends AbstractController {
	@Autowired
	private CsSectionRecordService csSectionRecordService;
	
	@RequestMapping("/cssectionrecord.html")
	public String list(){
		return "cssectionrecord/cssectionrecord.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(HttpServletRequest request){
        List<CsSectionRecordPOJO>  csSectionRecordList = new ArrayList<>();
		Map<String, Object> map = getMapPage(request);
		stringQuery(map,request,"courseName");
		stringQuery(map,request,"sectionNo");
		stringQuery(map,request,"sectionName");
		//查询列表数据
        int total = csSectionRecordService.queryPojoTotal(map);
        if (total > 0){
            csSectionRecordList = csSectionRecordService.queryPojoList(map);
        }
		PageUtils pageUtil = new PageUtils(csSectionRecordList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{sectionId}")
	public R info(@PathVariable("sectionId") Long sectionId , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		longQuery(map, request, "sectionId");
		CsSectionRecordEntity csSectionRecord = csSectionRecordService.queryObject(map);
		return R.ok().put(csSectionRecord);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody CsSectionRecordEntity csSectionRecord , HttpServletRequest request){
		//保存
		csSectionRecordService.save(csSectionRecord);	
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody CsSectionRecordEntity csSectionRecord , HttpServletRequest request){
		//修改
		csSectionRecordService.update(csSectionRecord);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] sectionIds , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",sectionIds);
		csSectionRecordService.deleteBatch(map);	
		return R.ok();
	}
	
}
