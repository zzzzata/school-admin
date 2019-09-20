package io.renren.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.renren.common.doc.ParamKey;
import io.renren.pojo.wechat.WechatMsgDetailPOJO;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import io.renren.entity.WechatTemplateMsgEntity;
import io.renren.service.WechatTemplateMsgService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;


/**
 * 微信推送消息记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-29 10:54:49
 */
@Controller
@RequestMapping("wechattemplatemsg")
public class WechatTemplateMsgController extends AbstractController {
	@Autowired
	private WechatTemplateMsgService wechatTemplateMsgService;
	
	@RequestMapping("/wechattemplatemsg.html")
	public String list(){
		return "wechattemplatemsg/wechattemplatemsg.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("wechattemplatemsg:list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		//查询列表数据
        stringQuery(map,request,"startTime");
        stringQuery(map,request,"endTime");
        longQuery(map,request,"productId");
        intQuery(map,request,"sendStatus");
		List<WechatTemplateMsgEntity> wechatTemplateMsgList = wechatTemplateMsgService.queryList(map);
		int total = wechatTemplateMsgService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(wechatTemplateMsgList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("wechattemplatemsg:info")
	public R info(@PathVariable("id") Long id , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		longQuery(map, request, "id");
		WechatTemplateMsgEntity wechatTemplateMsg = wechatTemplateMsgService.queryObject(map);
		return R.ok().put(wechatTemplateMsg);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("wechattemplatemsg:save")
	public R save(@RequestBody WechatTemplateMsgEntity wechatTemplateMsg , HttpServletRequest request){

		//保存
		wechatTemplateMsgService.save(wechatTemplateMsg);	
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("wechattemplatemsg:update")
	public R update(@RequestBody WechatTemplateMsgEntity wechatTemplateMsg , HttpServletRequest request){

		//修改
		wechatTemplateMsgService.update(wechatTemplateMsg);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("wechattemplatemsg:delete")
	public R delete(@RequestBody Long[] ids , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",ids);
		wechatTemplateMsgService.deleteBatch(map);	
		return R.ok();
	}
	/**
	 * 待发送消息删除
	 */
	@ResponseBody
	@RequestMapping("/deleteMsg")
	public R delete( @RequestBody Long id,HttpServletRequest request){
        //long id = ServletRequestUtils.getLongParameter(request, "id", 0L);
        String errorMsg = wechatTemplateMsgService.deleteMsg(id);
        if (StringUtils.isBlank(errorMsg)){
            return R.ok();
        }
        return R.error("删除失败");
    }


	/**
	 * 查询已发送的消息详情
	 */
	@ResponseBody
	@RequestMapping("/wechatMsgDetailList")
	public R wechatMsgDetailList( HttpServletRequest request){
        Map<String, Object> map = getMapPage(request);
        long id = ServletRequestUtils.getLongParameter(request, "id", 0L);
        map.put("id",id);
        List<WechatMsgDetailPOJO> list = wechatTemplateMsgService.wechatMsgDetailList(map);
        PageUtils pageUtil = new PageUtils(list, list.size(), request);
        return R.ok().put(ParamKey.Out.data, pageUtil);
	}

    /**
     * 查询已发送消息的数量
     */
    @ResponseBody
    @RequestMapping("/wechatMsgCount")
    public R wechatMsgCount( HttpServletRequest request){
        Map<String, Object> map = getMapPage(request);
        long id = ServletRequestUtils.getLongParameter(request, "id", 0L);
        map.put("id",id);
//        WechatMsgDetailPOJO pojo = new WechatMsgDetailPOJO();
//        pojo.setTotalCount(22);
//        pojo.setSuccessCount(21);
//        pojo.setFailedCount(1);
        WechatMsgDetailPOJO pojo = wechatTemplateMsgService.wechatMsgCount(map);
        return R.ok().put(ParamKey.Out.data, pojo);
    }


}
