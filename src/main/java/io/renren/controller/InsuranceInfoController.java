package io.renren.controller;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.renren.common.doc.ParamKey;
import io.renren.common.doc.SysLog;
import io.renren.entity.CourseExamRecordDetailEntity;
import io.renren.entity.CourseExamRecordEntity;
import io.renren.entity.InsuranceInfoEntity;
import io.renren.entity.RecordInfoEntity;
import io.renren.rest.persistent.KGS;
import io.renren.service.CourseExamRecordDetailService;
import io.renren.service.CourseExamRecordService;
import io.renren.service.CourseUserplanDetailService;
import io.renren.service.CourseUserplanService;
import io.renren.service.CoursesService;
import io.renren.service.InsuranceInfoService;
import io.renren.service.MallAreaService;
import io.renren.service.MallGoodsInfoService;
import io.renren.service.MallOrderService;
import io.renren.service.RecordInfoService;
import io.renren.service.SysProductService;
import io.renren.service.UsersService;
import io.renren.utils.ExcelReaderJXL;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;
import net.sf.json.JSONObject;

 /**
  * 学员档案-基础信息
  * @author lintf 
  *
  */
@Controller
@RequestMapping("insurance/insuranceInfo")
public class InsuranceInfoController extends AbstractController {
	 
	@Autowired
	private InsuranceInfoService insuranceInfoService; 
	/**
	 * 根据id取得数据
	 *@param recordInfoId
	 *@param request
	 *@return
	 * @author lintf
	 * 2018年8月13日
	 */
	@ResponseBody
	@RequestMapping("/info/{infoId}")
	public R info(@PathVariable("infoId") Long infoId, HttpServletRequest request) {	
		
		 
		 InsuranceInfoEntity  info = insuranceInfoService.queryObject(infoId);
		return R.ok().put("insuranceInfo", info);
	} 
	
	
	/**
	 * 更新基础信息表
	 */
	@SysLog("页面更新商品-投保金额-产品编码档案")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("insurance:insuranceInfo:update")
	public R  update(@RequestBody InsuranceInfoEntity e) {
		if (e.getProductCode()==null||e.getTuitionFee()==null||e.getPremium()==null||e.getInsuranceType()==null) {
			return R.error("誉好产品编码、学费金额、投保金额、投保类型不能为空！");
		}
		
		 
		
		// e.setModifyPerson(getUserId());
		 insuranceInfoService.updateInsuranceInfo(e);
		return R.ok();
		
	} 
	
	/**
	 * 新增加
	 */
	@SysLog("页面新增加商品-投保金额-产品编码档案")
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("insurance:insuranceInfo:save")
	public R  save(@RequestBody InsuranceInfoEntity e) {
		
		if (e.getProductCode()==null||e.getTuitionFee()==null||e.getPremium()==null||e.getInsuranceType()==null) {
			return R.error("誉好产品编码、学费金额、投保金额、投保类型不能为空！");
		}
		
		 
		
		
		// e.setModifyPerson(getUserId());
		e.setDr(0);
		 insuranceInfoService.saveInsuranceInfo(e);
		return R.ok();
		
	} 
	
	/**
	 * 删除
	 */
	@SysLog("页面删除商品-投保金额-产品编码档案")
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("insurance:insuranceInfo:delete")
	public R delete(@RequestBody Long[] insuranceInfoIds){
		 for (Long l:insuranceInfoIds) {

				int num=insuranceInfoService.checkInsuranceInfoExist(l);
				if ( num>0) {
					return R.error(l+" 已被商品档案引用，不允许删除！");
				}
		 }
		
		
		
		insuranceInfoService.deleteBatch(insuranceInfoIds);
		return R.ok();
	}
	
	
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping(value="/list",produces="text/html;charset=UTF-8")
   @RequiresPermissions("insurance:insuranceInfo:list")
	public R list( HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		stringQuery(map, request, "tuitionFee");//学费金额
		stringQuery(map, request, "premium");//投保金额   
		stringQuery(map, request, "compensationAmount");//赔付金额   		
		stringQuery(map, request, "productCode"); //誉好产品编码
		stringQuery(map, request, "insuranceType"); //0全保1单科 
		stringQuery(map, request, "layType"); //Y则是查的弹框 
		longQuery(map, request, "insuranceInfoId");  //商品-投保金额-产品编码档案ID
		
		 
	 
		List<InsuranceInfoEntity> infoList = insuranceInfoService.queryList(map);
		int total = insuranceInfoService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(infoList, total , request);
		if (map.get("layType")!=null&&"Y".equals( map.get("layType").toString())) {
			return R.ok().put(ParamKey.Out.data, pageUtil);
		}else {

			return R.ok().put(  pageUtil);
		}
		
		
		
		
	}
	
	public static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
  }
	 
}
