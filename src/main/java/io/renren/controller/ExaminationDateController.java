package io.renren.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.renren.pojo.ExaminationDatePOJO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import io.renren.entity.ExaminationDateEntity;
import io.renren.service.ExaminationDateService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;


/**
 * 学员考期表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-13 09:18:16
 */
@Controller
@RequestMapping("examinationdate")
public class ExaminationDateController extends AbstractController {
	@Autowired
	private ExaminationDateService examinationDateService;

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
//	@RequiresPermissions("examinationdate:list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		longQuery(map, request, "examScheduleId");
		stringQuery(map, request, "mobile");
		stringQuery(map, request, "nickName");
		stringQuery(map, request, "courseNo");
		longQuery(map, request, "learnState");
		longQuery(map, request, "examState");
		longQuery(map, request, "productId");
		longQuery(map, request, "classId");
		longQuery(map, request, "teacherId");
		longQuery(map, request, "areaId");
		longQuery(map, request, "classTypeId");
		longQuery(map, request, "levelId");
		longQuery(map, request, "areaId");
		longQuery(map, request, "professionId");
		longQuery(map, request, "courseId");
		stringQuery(map, request, "orderStartTime");
		stringQuery(map, request, "orderEndTime");
		//查询列表数据
		List<ExaminationDatePOJO> examinationDateList = examinationDateService.queryList(map);
		int total = examinationDateService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(examinationDateList, total , request);	
		return R.ok().put(pageUtil);
	}

	
}
