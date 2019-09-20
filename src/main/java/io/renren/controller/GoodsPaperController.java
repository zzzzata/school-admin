package io.renren.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.renren.entity.GoodsPaperEntity;
import io.renren.service.GoodsPaperService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;


/**
 * 商品-题库试卷对照表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-28 11:39:28
 */
@Controller
@RequestMapping("goodspaper")
public class GoodsPaperController extends AbstractController {
	@Autowired
	private GoodsPaperService goodsPaperService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("goodspaper:list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		stringQuery(map, request, "goodName");
		stringQuery(map, request, "paperName");
		//查询列表数据
		List<GoodsPaperEntity> goodsPaperList = goodsPaperService.queryList(map);
		int total = goodsPaperService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(goodsPaperList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("goodspaper:info")
	public R info(@PathVariable("id") Long id , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("id", id);
		GoodsPaperEntity goodsPaper = goodsPaperService.queryObject(map);
		return R.ok().put(goodsPaper);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("goodspaper:save")
	public R save(@RequestBody GoodsPaperEntity goodsPaper , HttpServletRequest request){
		if(null == goodsPaper.getGoodId()){
			return R.error("商品不能为空！！！");
		}
		if(null == goodsPaper.getPaperId() || null == goodsPaper.getPaperName()){
			return R.error("题库试卷不能为空！！！");
		}
		//校验是否存在
		int exist = goodsPaperService.getCountByGoodIdAndPaperId(goodsPaper.getGoodId(), goodsPaper.getPaperId());
		if(exist > 0){
			return R.error("所选的商品对应的题库试卷已经存在，不需要新增！！！");
		}
		//保存
		goodsPaper.setDr(0);
		goodsPaper.setCreatedTime(new Date());
		goodsPaper.setUpdatedTime(goodsPaper.getCreatedTime());
		goodsPaper.setCreator(getUserId());
		goodsPaper.setUpdater(goodsPaper.getCreator());
		goodsPaperService.save(goodsPaper);	
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("goodspaper:update")
	public R update(@RequestBody GoodsPaperEntity goodsPaper , HttpServletRequest request){
		if(null == goodsPaper.getGoodId()){
			return R.error("商品不能为空！！！");
		}
		if(null == goodsPaper.getPaperId() || null == goodsPaper.getPaperName()){
			return R.error("题库试卷不能为空！！！");
		}
		//校验是否存在
		int exist = goodsPaperService.getCountByGoodIdAndPaperId(goodsPaper.getGoodId(), goodsPaper.getPaperId());
		if(exist > 0){
			return R.error("所选的商品对应的题库试卷已经存在，不需要修改！！！");
		}
		//修改
		goodsPaper.setUpdater(getUserId());
		goodsPaper.setUpdatedTime(new Date());
		goodsPaperService.update(goodsPaper);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("goodspaper:delete")
	public R delete(@RequestBody Long[] ids , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",ids);
		goodsPaperService.deleteBatch(map);	
		return R.ok();
	}
	
}
