package io.renren.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import io.renren.entity.NcSchoolUserclassplanEntity;
import io.renren.service.NcSchoolUserclassplanService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;


/**
 * 接收NC学员--排课关联信息表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-28 16:31:02
 */
@Controller
@RequestMapping("ncschooluserclassplan")
public class NcSchoolUserclassplanController extends AbstractController {
	@Autowired
	private NcSchoolUserclassplanService ncSchoolUserclassplanService;
	
	@RequestMapping("/ncschooluserclassplan.html")
	public String list(){
		return "ncschooluserclassplan/ncschooluserclassplan.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("ncschooluserclassplan:list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		stringQuery(map,request,"mobile");
		stringQuery(map,request,"ncUserClassplanId");
		//查询列表数据
		List<NcSchoolUserclassplanEntity> ncSchoolUserclassplanList = ncSchoolUserclassplanService.queryList(map);
		int total = ncSchoolUserclassplanService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(ncSchoolUserclassplanList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("ncschooluserclassplan:info")
	public R info(@PathVariable("id") Long id , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		longQuery(map, request, "id");
		NcSchoolUserclassplanEntity ncSchoolUserclassplan = ncSchoolUserclassplanService.queryObject(map);
		return R.ok().put(ncSchoolUserclassplan);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	//@RequiresPermissions("ncschooluserclassplan:save")
	public R save(@RequestBody NcSchoolUserclassplanEntity ncSchoolUserclassplan , HttpServletRequest request){
		//保存
		ncSchoolUserclassplanService.save(ncSchoolUserclassplan);	
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	//@RequiresPermissions("ncschooluserclassplan:update")
	public R update(@RequestBody NcSchoolUserclassplanEntity ncSchoolUserclassplan , HttpServletRequest request){
		//修改
		ncSchoolUserclassplanService.update(ncSchoolUserclassplan);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	//@RequiresPermissions("ncschooluserclassplan:delete")
	public R delete(@RequestBody Long[] ids , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",ids);
		ncSchoolUserclassplanService.deleteBatch(map);	
		return R.ok();
	}
	
}
