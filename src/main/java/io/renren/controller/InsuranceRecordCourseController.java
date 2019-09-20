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

import io.renren.entity.InsuranceRecordCourseEntity;
import io.renren.service.InsuranceRecordCourseService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;


/**
 * 
 * 
 * @author linchaokai
 * @email hq@hq.com
 * @date 2018-11-15 09:12:37
 */
@Controller
@RequestMapping("insurancerecordcourse")
public class InsuranceRecordCourseController extends AbstractController {
	@Autowired
	private InsuranceRecordCourseService insuranceRecordCourseService;

	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		longQuery(map, request, "insuranceRecordId");
		//查询列表数据
		List<InsuranceRecordCourseEntity> insuranceRecordCourseList = insuranceRecordCourseService.queryList(map);
		int total = insuranceRecordCourseList.size();
		PageUtils pageUtil = new PageUtils(insuranceRecordCourseList, total , request);
		return R.ok().put(pageUtil);
	}
}
