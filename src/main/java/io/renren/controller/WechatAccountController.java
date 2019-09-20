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

import io.renren.entity.WechatAccountEntity;
import io.renren.service.WechatAccountService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;


/**
 * 微信公众号号信息记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-09 10:34:42
 */
@Controller
@RequestMapping("wechataccount")
public class WechatAccountController extends AbstractController {
	@Autowired
	private WechatAccountService wechatAccountService;
	
	@RequestMapping("/wechataccount.html")
	public String list(){
		return "wechataccount/wechataccount.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("wechataccount:list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		stringQuery(map,request,"title");
		stringQuery(map,request,"appid");
		//查询列表数据
		List<WechatAccountEntity> wechatAccountList = wechatAccountService.queryList(map);
		int total = wechatAccountService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(wechatAccountList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("wechataccount:info")
	public R info(@PathVariable("id") Long id , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("id",id);
		WechatAccountEntity wechatAccount = wechatAccountService.queryObject(map);
		return R.ok().put(wechatAccount);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("wechataccount:save")
	public R save(@RequestBody WechatAccountEntity wechatAccount , HttpServletRequest request){
	    wechatAccount.setCreateTime(new Date());
	    wechatAccount.setCreateUser(getUserId());
		//保存
		wechatAccountService.save(wechatAccount);	
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("wechataccount:update")
	public R update(@RequestBody WechatAccountEntity wechatAccount , HttpServletRequest request){
		//修改
		wechatAccountService.update(wechatAccount);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("wechataccount:delete")
	public R delete(@RequestBody Long[] ids , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",ids);
		wechatAccountService.deleteBatch(map);	
		return R.ok();
	}
	
}
