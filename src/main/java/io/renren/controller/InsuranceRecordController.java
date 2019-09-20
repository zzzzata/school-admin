package io.renren.controller;

import io.renren.entity.ContractRecord;
import io.renren.entity.InsuranceRecordEntity;
import io.renren.enums.PassStatusEnum;
import io.renren.pojo.InsuranceRecordPOJO;
import io.renren.service.InsuranceRecordService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2018-11-15 09:12:37
 */
@Controller
@RequestMapping("insurancerecord")
public class InsuranceRecordController extends AbstractController {
	@Autowired
	private InsuranceRecordService insuranceRecordService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
//	@RequiresPermissions("insurancerecord:list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		stringQueryDecodeUTF8(map, request, "orderNo");
		stringQueryDecodeUTF8(map, request, "mobile");
		stringQueryDecodeUTF8(map, request, "nickName");
		stringQueryDecodeUTF8(map, request, "ncCode");
		longQuery(map, request, "insuranceStatus");
        longQuery(map, request, "contractStatus");
		longQuery(map, request, "teacherId");
		longQuery(map, request, "passStatus");
		stringQuery(map, request, "deptIdList");
		//判断是查询全部还是查询多部门
		String deptIdList = (String) map.get("deptIdList");
		if (deptIdList!= null && !"".equals(deptIdList.trim())){
			String[] split = deptIdList.split(",");
			map.put("deptIdList",Arrays.asList(split));
		}
		//查询列表数据
		List<InsuranceRecordPOJO> insuranceRecordList = insuranceRecordService.queryListPOJO(map);
		int total = insuranceRecordService.queryPOJOTotal(map);
		PageUtils pageUtil = new PageUtils(insuranceRecordList, total , request);
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{insuranceRecordId}")
//	@RequiresPermissions("insurancerecord:info")
	public R info(@PathVariable("insuranceRecordId") Long insuranceRecordId , HttpServletRequest request){
		Map<String, Object> map = getMap(request);
		longQuery(map, request, "insuranceRecordId");
//		InsuranceRecordEntity insuranceRecord = insuranceRecordService.queryObject(map);
		return R.ok().put(null);
	}

	@ResponseBody
	@RequestMapping(value = "/updateByContract", method = RequestMethod.POST)
	public R updateByContract(@RequestBody InsuranceRecordEntity entity) {
		insuranceRecordService.updateByContract(entity);
		return R.ok();

	}

    @ResponseBody
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public R pass(@RequestBody Long[] ids) {
		insuranceRecordService.passAction(ids);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/passCancel", method = RequestMethod.POST)
    public R passCancel(@RequestBody Long[] ids) {
		insuranceRecordService.passCancel(ids);
        return R.ok();
    }

	@ResponseBody
	@RequestMapping(value = "/checkCount", method = RequestMethod.POST)
	public R checkCout(@RequestBody Long[] ids) {
		return R.ok().put("count",insuranceRecordService.checkCount(ids[0]));
	}
}
