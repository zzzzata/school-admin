package io.renren.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import io.renren.pojo.NcSchoolLearningDetailPOJO;
import io.renren.pojo.NcUserListPOJO;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import io.renren.entity.NcSchoolLearningEntity;
import io.renren.service.NcSchoolLearningService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;


/**
 * 线下学习计划
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-15 14:22:36
 */
@Controller
@RequestMapping("ncschoollearning")
public class NcSchoolLearningController extends AbstractController {
	@Autowired
	private NcSchoolLearningService ncSchoolLearningService;
	
	@RequestMapping("/ncschoollearning.html")
	public String list(){
		return "ncschoollearning/ncschoollearning.html";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		stringQuery(map,request,"learningNo");
		stringQuery(map,request,"userName");
		stringQuery(map,request,"mobile");
		//查询列表数据
		List<NcSchoolLearningEntity> ncSchoolLearningList = ncSchoolLearningService.queryList(map);
		int total = ncSchoolLearningService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(ncSchoolLearningList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{learningId}")
	public R info(@PathVariable("learningId") Long learningId , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		longQuery(map, request, "learningId");
		NcSchoolLearningEntity ncSchoolLearning = ncSchoolLearningService.queryObject(map);
		return R.ok().put(ncSchoolLearning);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	public R save(@RequestBody NcSchoolLearningEntity ncSchoolLearning , HttpServletRequest request){
		//保存
		ncSchoolLearningService.save(ncSchoolLearning);	
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody NcSchoolLearningEntity ncSchoolLearning , HttpServletRequest request){
		//修改
		ncSchoolLearningService.update(ncSchoolLearning);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] learningIds , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("ids",learningIds);
		ncSchoolLearningService.deleteBatch(map);	
		return R.ok();
	}

    /**
     * 学习计划详情
     */
    @ResponseBody
    @RequestMapping("/learningDetail")
    public R learningDetail(HttpServletRequest request){
        Map<String, Object> map = getMapPage(request);
        String learningId = ServletRequestUtils.getStringParameter(request, "learningId", "");
        if (StringUtils.isBlank(learningId)){
            return R.error("请选择有效的学习计划！");
        }
        map.put("learningId",learningId);
        //查询列表数据
        List<NcSchoolLearningDetailPOJO> detailList = new ArrayList<>();
        int total = ncSchoolLearningService.queryDetailTotal(map);
        if (total > 0){
            detailList= ncSchoolLearningService.queryDetail(map);
        }
        PageUtils pageUtil = new PageUtils(detailList, total , request);
        return R.ok().put(pageUtil);
    }
}
