package io.renren.controller;

import java.util.*;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import io.renren.entity.WechatOpenidMobileEntity;
import io.renren.service.WechatOpenidMobileService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;


/**
 * 用户绑定微信openId记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-25 16:09:15
 */
@Controller
@RequestMapping("wechatopenidmobile")
public class WechatOpenidMobileController extends AbstractController {

    private static final int queryType_mobile = 1;
    private static final int queryType_classplan = 2;
    private static final int queryType_class = 3;

	@Autowired
	private WechatOpenidMobileService wechatOpenidMobileService;
	
	@RequestMapping("/wechatopenidmobile.html")
	public String list(){
		return "wechatopenidmobile/wechatopenidmobile.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("wechatopenidmobile:list")
	public R list(HttpServletRequest request) {
        int total = 0;
        List<WechatOpenidMobileEntity> wechatOpenidMobileList = new ArrayList<>();
        List<WechatOpenidMobileEntity> resultList = new ArrayList<>();
        Map<String, Object> map = getMapPage(request);
        stringQuery(map, request, "mobile");
        stringQuery(map, request, "classplanId");
        longQuery(map, request, "classId");
        intQuery(map,request,"status");
        //查询维度: 1按手机号码 2按排课 3按班级
        int queryType = ServletRequestUtils.getIntParameter(request, "queryType", 1);
        switch (queryType) {
            case 1: {
                //查询列表数据
                total = wechatOpenidMobileService.queryTotal(map);
                if (total > 0) {
                    wechatOpenidMobileList = wechatOpenidMobileService.queryList(map);
                }
                break;
            }
            case 2: {
                //查询排课学员列表数据
                total = wechatOpenidMobileService.queryTotalByClassplanId(map);
                if (total > 0) {
                    wechatOpenidMobileList = wechatOpenidMobileService.queryListByClassplanId(map);
                }
                break;
            }
            case 3: {
                //查询班级学员列表数据
                total = wechatOpenidMobileService.queryTotalByClassId(map);
                if (total > 0) {
                    wechatOpenidMobileList = wechatOpenidMobileService.queryListByClassId(map);
                }
                break;
            }
            default:
                return R.error("没有找到匹配的数据,请检查查询条件");
        }
        PageUtils pageUtil = new PageUtils(wechatOpenidMobileList, total, request);
        return R.ok().put(pageUtil);
    }
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("wechatopenidmobile:info")
	public R info(@PathVariable("id") Long id , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		longQuery(map, request, "id");
		WechatOpenidMobileEntity wechatOpenidMobile = wechatOpenidMobileService.queryObject(map);
		return R.ok().put(wechatOpenidMobile);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("wechatopenidmobile:save")
	public R save(@RequestBody WechatOpenidMobileEntity wechatOpenidMobile , HttpServletRequest request){
		//保存
		wechatOpenidMobileService.save(wechatOpenidMobile);	
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("wechatopenidmobile:update")
	public R update(@RequestBody WechatOpenidMobileEntity wechatOpenidMobile , HttpServletRequest request){
		//修改
		wechatOpenidMobileService.update(wechatOpenidMobile);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("wechatopenidmobile:delete")
	public R delete(@RequestBody Long[] ids , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",ids);
		wechatOpenidMobileService.deleteBatch(map);	
		return R.ok();
	}
	
}
