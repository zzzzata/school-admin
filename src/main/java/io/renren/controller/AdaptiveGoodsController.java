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

import io.renren.entity.AdaptiveGoodsEntity;
import io.renren.service.AdaptiveGoodsService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;


/**
 * 自适应推送商品表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-29 10:07:14
 */
@Controller
@RequestMapping("adaptivegoods")
public class AdaptiveGoodsController extends AbstractController {
	@Autowired
	private AdaptiveGoodsService adaptiveGoodsService;
	
	@RequestMapping("/adaptivegoods.html")
	public String list(){
		return "adaptivegoods/adaptivegoods.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		stringQuery(map,request,"goodName");
		//查询列表数据
		List<AdaptiveGoodsEntity> adaptiveGoodsList = adaptiveGoodsService.queryList(map);
		int total = adaptiveGoodsService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(adaptiveGoodsList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id , HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		map.put("id",id);
		AdaptiveGoodsEntity adaptiveGoods = adaptiveGoodsService.queryObject(map);
		return R.ok().put(adaptiveGoods);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody AdaptiveGoodsEntity adaptiveGoods , HttpServletRequest request){

		//保存
		adaptiveGoodsService.save(adaptiveGoods);	
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody AdaptiveGoodsEntity adaptiveGoods , HttpServletRequest request){

		//修改
		adaptiveGoodsService.update(adaptiveGoods);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("adaptivegoods:delete")
	public R delete(@RequestBody Long[] ids , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",ids);
		adaptiveGoodsService.deleteBatch(map);	
		return R.ok();
	}
	
}
