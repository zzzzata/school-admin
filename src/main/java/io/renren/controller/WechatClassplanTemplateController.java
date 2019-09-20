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

import io.renren.entity.WechatClassplanTemplateEntity;
import io.renren.service.WechatClassplanTemplateService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;


/**
 * 推送模板消息排课关联表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-10 09:35:41
 */
@Controller
@RequestMapping("wechatclassplantemplate")
public class WechatClassplanTemplateController extends AbstractController {
	@Autowired
	private WechatClassplanTemplateService wechatClassplanTemplateService;
	
	@RequestMapping("/wechatclassplantemplate.html")
	public String list(){
		return "wechatclassplantemplate/wechatclassplantemplate.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("wechatclassplantemplate:list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		stringQuery(map,request,"classplanName");
		//查询列表数据
		List<WechatClassplanTemplateEntity> wechatClassplanTemplateList = wechatClassplanTemplateService.queryList(map);
		int total = wechatClassplanTemplateService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(wechatClassplanTemplateList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("wechatclassplantemplate:info")
	public R info(@PathVariable("id") Long id , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("id",id);
		WechatClassplanTemplateEntity wechatClassplanTemplate = wechatClassplanTemplateService.queryObject(map);
		return R.ok().put(wechatClassplanTemplate);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("wechatclassplantemplate:save")
	public R save(@RequestBody WechatClassplanTemplateEntity wechatClassplanTemplate , HttpServletRequest request){
        wechatClassplanTemplate.setCreateTime(new Date());
        wechatClassplanTemplate.setUpdateTime(new Date());
        wechatClassplanTemplate.setCreateUser(this.getUserId());
        wechatClassplanTemplate.setUpdateUser(this.getUserId());
		//保存
        try {
            wechatClassplanTemplateService.save(wechatClassplanTemplate);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
        return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("wechatclassplantemplate:update")
	public R update(@RequestBody WechatClassplanTemplateEntity wechatClassplanTemplate , HttpServletRequest request){
	    wechatClassplanTemplate.setUpdateTime(new Date());
	    wechatClassplanTemplate.setUpdateUser(this.getUserId());
		//修改
        try {
            wechatClassplanTemplateService.update(wechatClassplanTemplate);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
        return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("wechatclassplantemplate:delete")
	public R delete(@RequestBody Long[] ids , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",ids);
		wechatClassplanTemplateService.deleteBatch(map);	
		return R.ok();
	}
	
}
