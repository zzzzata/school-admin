package io.renren.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.renren.entity.InsuranceInfoEntity;
import io.renren.entity.MallGoodsDetailsEntity;
import io.renren.entity.MallGoodsInfoEntity;
import io.renren.pojo.MallGoodsDetailsPOJO;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.renren.common.doc.ParamKey;
import io.renren.common.doc.SysLog;
import io.renren.pojo.MallGoodsInfoPOJO;
import io.renren.service.GoodsCoursetkService;
import io.renren.service.InsuranceRecordCourseService;
import io.renren.service.MallGoodsDetailsService;
import io.renren.service.MallGoodsInfoService;
import io.renren.service.SysCheckQuoteService;
import io.renren.utils.PageUtils;
import io.renren.utils.QuoteConstant;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;
import io.renren.utils.http.HttpClientUtil4_3;


/**
 * 商品档案
 * 
 * @author huxuehan
 * @date 2017-03-29 10:11:55
 */
@Controller
@RequestMapping("/mall/mallgoodsinfo")
public class MallGoodsInfoController extends AbstractController{
	@Autowired
	private MallGoodsInfoService mallGoodsInfoService;
	@Autowired
	private MallGoodsDetailsService mallGoodsDetailsService;
	@Autowired
	private SysCheckQuoteService sysCheckQuoteService;
	@Autowired
	private GoodsCoursetkService goodsCoursetkService;
	@Autowired
	private InsuranceRecordCourseService insuranceRecordCourseService;
	
	/**
	 * 商品下拉列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectList")
	public R goodsList(){
		return R.ok().put("data", mallGoodsInfoService.findGoodsList());
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("mallgoodsinfo:list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		longQuery(map, request, "id");
		stringQuery(map, request, "name");
		stringQuery(map, request, "suitableUser");
		stringQuery(map, request, "ncId");
		intQuery(map, request, "status");
		longQuery(map, request, "levelId");
		longQuery(map, request, "professionId");
		longQuery(map, request, "classTypeId");
		longQuery(map, request, "productId");
		//查询列表数据
		List<Map<String, Object>> mallGoodsInfoList = mallGoodsInfoService.queryListMap(map);
		int total = mallGoodsInfoService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(mallGoodsInfoList, total, request);	
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("mallgoodsinfo:info")
	public R info(@PathVariable("id") Long id ,HttpServletRequest request){
		//主表查询条件
		Map<String, Object> goodQueryMap = getMap(request);
		goodQueryMap.put("id", id);
		//子表查询条件
		Map<String, Object> goodDetailQueryMap = getMapPage(request);
		goodDetailQueryMap.put("goodsId", id);
		//TODO
		List<String> codeList = this.goodsCoursetkService.queryCodeListByCommodityId(id);
		String codeResult = this.goodsCoursetkService.tkCourseCode(codeList);
		//主表结果集
		Map<String, Object> goodMap = mallGoodsInfoService.queryMap(goodQueryMap);
		//子表结果集
		List<Map<String, Object>> details = mallGoodsDetailsService.queryListMap(goodDetailQueryMap);
		goodMap.put("details", details);
		goodMap.put("tkCourseCode", codeResult);
		return R.ok().put("mallGoodsInfo", goodMap);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存商品")
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("mallgoodsinfo:save")
	public R save(@RequestBody MallGoodsInfoPOJO MallGoodsInfo,HttpServletRequest request){
		if(StringUtils.isBlank(MallGoodsInfo.getName())){
			return R.error("商品名称必填!");
		}
		
		if(null == MallGoodsInfo.getClassTypeId() || MallGoodsInfo.getClassTypeId() <= 0){
			return R.error("班型必填!");
		}
		if(null == MallGoodsInfo.getPresentPrice()){
			return R.error("售价必填!");
		}
		if (MallGoodsInfo.getPresentPrice() <= 0){
		    return R.error("售价必须大于等于零");
        }
		if(null == MallGoodsInfo.getOriginalPrice() || MallGoodsInfo.getOriginalPrice() <= 0){
			return R.error("原价必填且不可以为负数!");
		}
		if(null == MallGoodsInfo.getProfessionId() || MallGoodsInfo.getProfessionId() <= 0){
			return R.error("专业必填!");
		}
		if(null == MallGoodsInfo.getLevelId() || MallGoodsInfo.getLevelId() <= 0){
			return R.error("层次必填!");
		}
		if(null == MallGoodsInfo.getDayValidity() || MallGoodsInfo.getDayValidity() <= 0){
			return R.error("有效期必填!");
		}
		if(null == MallGoodsInfo.getProductId() || MallGoodsInfo.getProductId() <= 0){
			return R.error("产品线必填!");
		}
		if(StringUtils.isBlank(MallGoodsInfo.getNcId())){
			return R.error("NCID必填!");
		}
        MallGoodsInfoEntity goodsInfoEntity = mallGoodsInfoService.queryGoodsInfoByNcId(MallGoodsInfo.getNcId());
        if (null != goodsInfoEntity){
            return R.error("【"+MallGoodsInfo.getNcId()+"】已经和商品【"+goodsInfoEntity.getName()+"】绑定,同一个NCID只能绑定一个商品");
        }
		if(1 == MallGoodsInfo.getOnlyOne()){
			if(StringUtils.isBlank(MallGoodsInfo.getTkCourseCode())){
				return R.error("题库课程代码必填!");
			}
		}
        if(!StringUtils.isBlank(MallGoodsInfo.getGoodRecomment()) && MallGoodsInfo.getGoodRecomment().length() >100){
            return R.error("商品介绍请控制在100字以内");
        }
        
        if ( null !=MallGoodsInfo.getInsuranceInfoId()&&MallGoodsInfo.getInsuranceInfoId()>0){
        	if(null == MallGoodsInfo.getInsuranceTemplateId()|| MallGoodsInfo.getInsuranceTemplateId() <= 0){
    			return R.error("保险的商品必须要选择保险模板！");
    		}
        }
        
        
        
		//平台id
		MallGoodsInfo.setSchoolId(SchoolIdUtils.getSchoolId(request));
		//创建人
		MallGoodsInfo.setCreatePerson(getUserId());
		//修改人
		MallGoodsInfo.setModifyPerson(MallGoodsInfo.getCreatePerson());
		//保存主表
		mallGoodsInfoService.save(MallGoodsInfo);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改商品")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("mallgoodsinfo:update")
	public R update(@RequestBody MallGoodsInfoPOJO MallGoodsInfo){
		if(StringUtils.isBlank(MallGoodsInfo.getName())){
			return R.error("商品名称必填!");
		}
		
		if(null == MallGoodsInfo.getClassTypeId() || MallGoodsInfo.getClassTypeId() <= 0){
			return R.error("班型必填!");
		}
		if(null == MallGoodsInfo.getPresentPrice() || MallGoodsInfo.getPresentPrice() <= 0){
			return R.error("售价必填且不可以为负数!");
		}
		if(null == MallGoodsInfo.getOriginalPrice() || MallGoodsInfo.getOriginalPrice() <= 0){
			return R.error("原价必填且不可以为负数!");
		}
		if(null == MallGoodsInfo.getProfessionId() || MallGoodsInfo.getProfessionId() <= 0){
			return R.error("专业必填!");
		}
		if(null == MallGoodsInfo.getLevelId() || MallGoodsInfo.getLevelId() <= 0){
			return R.error("层次必填!");
		}
		if(null == MallGoodsInfo.getDayValidity() || MallGoodsInfo.getDayValidity() <= 0){
			return R.error("有效期必填!");
		}
		if(null == MallGoodsInfo.getProductId() || MallGoodsInfo.getProductId() <= 0){
			return R.error("产品线必填!");
		}
		if(StringUtils.isBlank(MallGoodsInfo.getNcId())){
			return R.error("NCID必填!");
		}
		Map<String,Object> map = new HashedMap();
		map.put("id",MallGoodsInfo.getId());
        MallGoodsInfoEntity oldEntity = mallGoodsInfoService.queryGoodsInfoId(map);
        MallGoodsInfoEntity goodsInfoEntity = mallGoodsInfoService.queryGoodsInfoByNcId(MallGoodsInfo.getNcId());
		if (!MallGoodsInfo.getNcId().equals(oldEntity.getNcId()) && null != goodsInfoEntity){
		    return R.error("【"+MallGoodsInfo.getNcId()+"】已经和商品【"+goodsInfoEntity.getName()+"】绑定,同一个NCID只能绑定一个商品");
        }
		if(1 == MallGoodsInfo.getOnlyOne()){
			if(StringUtils.isBlank(MallGoodsInfo.getTkCourseCode())){
				return R.error("题库课程代码必填!");
			}
		}
		//修改人
		MallGoodsInfo.setModifyPerson(getUserId());
		mallGoodsInfoService.update(MallGoodsInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除商品")
	@ResponseBody
	@RequestMapping("/delete/{id}")
	@RequiresPermissions("mallgoodsinfo:delete")
	public R delete(@PathVariable("id") Long id,HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		map.put("value", id);
		ArrayList<String> exceptList = new ArrayList<String>();
		String exceptMsg = "删除失败的具体原因如下:";
		String errMsg="";
		if(sysCheckQuoteService.checkQuote(map , QuoteConstant.mall_order_commodity_id)){
		exceptMsg=" 订单表有引用！";
		exceptList.add(exceptMsg);
       }
		for (int i = 0; i < exceptList.size(); i++)
		{
			errMsg += exceptList.get(i) + "<br>";
		}
		if(errMsg.equals("")){
			mallGoodsInfoService.deleteBatch(map);	
			return R.ok();
		}
		else{
			return R.error(errMsg);
		}
		
	}
	
	/**
	 * 禁用
	 */
	@SysLog("禁用商品")
	@ResponseBody
	@RequestMapping("/pause")
	@RequiresPermissions("mallgoodsinfo:pause")
	public R pause(@RequestBody Long[] ids){
		mallGoodsInfoService.pause(ids);
		return R.ok();
	}
	
	/**
	 * 启用
	 */
	@SysLog("启用商品")
	@ResponseBody
	@RequestMapping("/resume")
	@RequiresPermissions("mallgoodsinfo:resume")
	public R resume(@RequestBody Long[] ids){
		mallGoodsInfoService.resume(ids);
		return R.ok();
	}
	
	/**
	 * 审核通过
	 * */
	@SysLog("审核通过商品")
	@ResponseBody
	@RequestMapping("/accept")
	@RequiresPermissions("mallgoodsinfo:accept")
	public R accept(@RequestBody Long  id){
		mallGoodsInfoService.accept(id);
		return R.ok();
	}
	/**
	 * 审核未过
	 * */
	@SysLog("审核未通过商品")
	@ResponseBody
	@RequestMapping("/reject")
	@RequiresPermissions("mallgoodsinfo:reject")
	public R reject(@RequestBody Long id){
		mallGoodsInfoService.reject(id);
		return R.ok();
	}
	@SysLog("拷贝课程")
	@ResponseBody
	@RequestMapping("/copyArea")
	@RequiresPermissions("mallgoodsinfo:copyArea")
	public R copyArea(
			HttpServletRequest request) throws ServletRequestBindingException{
		Long id = ServletRequestUtils.getLongParameter(request, "id");
		Long areaId1 = ServletRequestUtils.getLongParameter(request, "areaId1");
		Long areaId2 = ServletRequestUtils.getLongParameter(request, "areaId2");
		Long newGoodId = ServletRequestUtils.getLongParameter(request, "newGoodId", id);
		mallGoodsInfoService.copyArea(id , areaId1 , areaId2 , newGoodId, SchoolIdUtils.getSchoolId(request));
		return R.ok();
	}
	
	@SysLog("拷贝商品")
	@ResponseBody
	@RequestMapping("/copyGood")
	@RequiresPermissions("mallgoodsinfo:copyGood")
	public R copyGood(HttpServletRequest request) throws ServletRequestBindingException{
		Long id = ServletRequestUtils.getLongParameter(request, "id");
		String goodName = ServletRequestUtils.getStringParameter(request, "goodName", null);
		String ncid = ServletRequestUtils.getStringParameter(request, "ncid", null);
		if(null == id){
			return R.error("无法获取商品id");
		}
		if(null == goodName){
			return R.error("请输入新的商品名称");
		}
		if(null == ncid){
			return R.error("请输入新的商品ncid");
		}
		List<String> codeList = goodsCoursetkService.queryCodeListByCommodityId(id);
		String tkCodes = goodsCoursetkService.tkCourseCode(codeList);
		mallGoodsInfoService.copyGood(id, goodName, ncid, tkCodes);
		
		return R.ok();
	}
	/**
	 * 通过题库接口，获取题库科目列表
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/getTKCourseList")
	public void getTKCourseList(HttpServletRequest request, HttpServletResponse response) {

		mallGoodsInfoService.getTKCourseList(request, response);

	}
	
	/**
	 * 修改 --仅记录日志，用于排查问题
	 */
	@SysLog("修改商品课程")
	@ResponseBody
	@RequestMapping("/updateCourse")
	@RequiresPermissions("mallgoodsinfo:update")
	public R updateCourse(@RequestBody MallGoodsDetailsPOJO mgdp){
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除商品课程")
	@ResponseBody
	@RequestMapping("/deleteCourse/{id}")
	@RequiresPermissions("mallgoodsinfo:update")
	public R deleteCourse(@PathVariable("id") Long id,HttpServletRequest request){
		mallGoodsDetailsService.deleteCourse(id);
		return R.ok();
	}

	/**
	 * 设置投保课程 --仅记录日志，用于排查问题
	 */
	@SysLog("设置投保课程")
	@ResponseBody
	@RequestMapping("/insuranceAction")
	@RequiresPermissions("mallgoodsinfo:update")
	public R insuranceAction(@RequestBody ArrayList<Long> ids){
		return R.ok();
	}

	/**
	 * 取消投保课程 --仅记录日志，用于排查问题
	 */
	@SysLog("取消投保课程")
	@ResponseBody
	@RequestMapping("/insuranceActionCancel")
	@RequiresPermissions("mallgoodsinfo:update")
	public R insuranceActionCancel(@RequestBody ArrayList<Long> ids){
		return R.ok();
	}
	
	
	
	/**
	 * 清空商品的投保信息
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/clearInsurance")
	public R clearInsurance(@RequestBody Long  id,HttpServletRequest request) {
		mallGoodsInfoService.clearInsurance(id);
		return R.ok();
	}
	/**
	 * 商品中的投保设置弹框时用到的
	 */
	@ResponseBody
	@RequestMapping("/insuranceGoodsDetail")
	public R insuranceGoodsDetail(HttpServletRequest request) { 
		Map<String, Object> map = new HashMap<String, Object>();
		// 子表查询条件
		Map<String, Object> goodDetailQueryMap = getMapPage(request);
		longQuery(map, request, "goodsId");
		longQuery(map, request, "areaId");
		longQuery(map, request, "courseId");
		stringQuery(map, request, "sortableInsurance");//是否按投保排序		
		if (map.get("sortableInsurance")!=null&&"Y".equals(map.get("sortableInsurance"))) {
			map.put("sortableInsurance","Y");
		}else {
			map.put("sortableInsurance",null);
		}
		map.put("notblankCourse", "Y"); // 不显示空名称的 
		map.put("onlyOfficCourse", "Y");//只显示主课的
		
		List<Map<String, Object>> detailList = mallGoodsDetailsService.queryListMap(map);
		int total = detailList.size();//默认显示是5000行所以不用合计来排序了
		PageUtils pageUtil = new PageUtils(detailList, total, request);
		if (goodDetailQueryMap.get("layType") != null && "Y".equals(goodDetailQueryMap.get("layType").toString())) {
			return R.ok().put(ParamKey.Out.data, pageUtil);
		} else {

			return R.ok().put(pageUtil);
		}

	}

	/**
	 * 这个用于修改商品子表中的课程为投保
	 * 
	 * @param request
	 * @return
	 */
	@SysLog("设置投保课程")
	@ResponseBody
	@RequestMapping("/insuranceActionUpdate")
	public R insuranceActionUpdate(@RequestBody List<MallGoodsDetailsPOJO> mallGoodsDetailsList,
			HttpServletRequest request) {
		if (mallGoodsDetailsList == null || mallGoodsDetailsList.size() == 0) {
			return R.error(500, "没有取到勾选的内容，请关掉窗口再打开试一下。");
		}
		return insuranceRecordCourseService.insuranceActionUpdate(mallGoodsDetailsList, 1);
	}

	/**
	 * 这个用于修改商品子表中的课程为取消投保
	 * 
	 * @param ids
	 * @param request
	 * @return
	 */
	@SysLog("取消投保课程")
	@ResponseBody
	@RequestMapping("/insuranceActionUpdateCancel")
	public R insuranceActionUpdateCancel(@RequestBody List<MallGoodsDetailsPOJO> mallGoodsDetailsList,
			HttpServletRequest request) {
		if (mallGoodsDetailsList == null || mallGoodsDetailsList.size() == 0) {
			return R.error(500, "没有取到勾选的内容，请关掉窗口再打开试一下。");
		}
		return insuranceRecordCourseService.insuranceActionUpdate(mallGoodsDetailsList, 0);

	}
	 
	
	
	
	
}
