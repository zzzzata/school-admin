package com.hq.courses.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.hq.courses.entity.CsChapterRecordEntity;
import com.hq.courses.pojo.CsChapterRecordPOJO;
import com.hq.courses.service.CsChapterRecordService;
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
 * @date 2019-04-19 15:18:38
 */
@Controller
@RequestMapping("cschapterrecord")
public class CsChapterRecordController extends AbstractController {
	@Autowired
	private CsChapterRecordService csChapterRecordService;
	
	@RequestMapping("/cschapterrecord.html")
	public String list(){
		return "cschapterrecord/cschapterrecord.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(HttpServletRequest request){
        List<CsChapterRecordPOJO> csChapterRecordList = new ArrayList<>();
		Map<String, Object> map = getMapPage(request);
		stringQuery(map,request,"courseName");
		stringQuery(map,request,"chapterNo");
		stringQuery(map,request,"chapterName");
		//查询列表数据
        int total = csChapterRecordService.queryPojoTotal(map);
        if (total > 0){
          csChapterRecordList = csChapterRecordService.queryPojoList(map);
        }
		PageUtils pageUtil = new PageUtils(csChapterRecordList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{chapterId}")
	public R info(@PathVariable("chapterId") Long chapterId , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		longQuery(map, request, "chapterId");
		CsChapterRecordEntity csChapterRecord = csChapterRecordService.queryObject(map);
		return R.ok().put(csChapterRecord);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody CsChapterRecordEntity csChapterRecord , HttpServletRequest request){
		//保存
		csChapterRecordService.save(csChapterRecord);	
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody CsChapterRecordEntity csChapterRecord , HttpServletRequest request){
		//修改
		csChapterRecordService.update(csChapterRecord);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] chapterIds , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",chapterIds);
		csChapterRecordService.deleteBatch(map);	
		return R.ok();
	}
	
}
