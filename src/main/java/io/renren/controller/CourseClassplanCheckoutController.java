package io.renren.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.service.CourseClassplanCheckoutService;
import io.renren.utils.R;

/**
 * 排课检测
 * @author liuhai 2019/03/23
 *
 */
@RestController
public class CourseClassplanCheckoutController {
	
	@Autowired
	private CourseClassplanCheckoutService courseClassplanCheckoutService;
	
	@RequestMapping("/course/checkout")
	public R checkout(HttpServletRequest request){
		String phoneNum = ServletRequestUtils.getStringParameter(request, "phoneNum", null);
		String orderNo = ServletRequestUtils.getStringParameter(request, "orderNo", null);
		Long goodId = ServletRequestUtils.getLongParameter(request, "goodId", 0);
		String classplanliveId = ServletRequestUtils.getStringParameter(request, "classplanliveId", null);
		
		String msg = courseClassplanCheckoutService.checkout(phoneNum, orderNo, goodId, classplanliveId);
		
		return R.ok().put(msg);
	}

}
