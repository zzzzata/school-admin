package io.renren.controller;

import io.renren.entity.LearningSituationEntity;
import io.renren.pojo.LearningSiuationPOJO;
import io.renren.service.LearningSituationService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.http.HttpClientUtil4_3;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static net.sf.json.JSONArray.toList;


/**
 * 学习情况
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-16 11:12:00
 */
@Controller
@RequestMapping("learningsituation")
public class LearningSituationController extends AbstractController {
	@Autowired
	private LearningSituationService learningSituationService;
	@Value("#{application['bi.search.domain']}")
	private String BI_SEARCH_DOMAIN;
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(HttpServletRequest request){
		Map<String, Object> map = getMapPage(request);
		longQuery(map, request, "recordSignId");
		stringQuery(map, request, "createMonth");
		//查询列表数据
		List<LearningSiuationPOJO>  learningSituationList = null;
		int total = 0;
		JSONObject result = HttpClientUtil4_3.doPost(BI_SEARCH_DOMAIN+"/learningSiuation/list",JSONObject.fromObject(map),null);
		if(null != result && 200 == result.getInt("code")) {
			total = result.getJSONObject("data").getInt("total");
			learningSituationList = toList(result.getJSONObject("data").getJSONArray("list"),new LearningSiuationPOJO(),new JsonConfig());
		}
		PageUtils pageUtil = new PageUtils(learningSituationList, total , request);	
		return R.ok().put(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Long id , HttpServletRequest request){
		Object learningSituation = null;
		JSONObject resultObj = null;
		try {
			String result = HttpClientUtil4_3.get(BI_SEARCH_DOMAIN+"/learningSiuation/info/"+id,null);
			resultObj =  JSONObject.fromObject(result);
			if(null != resultObj && 200 == resultObj.getInt("code")) {
				learningSituation = JSONObject.toBean(resultObj.getJSONObject("data"),LearningSiuationPOJO.class);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok().put(learningSituation);
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update(@RequestBody LearningSituationEntity learningSituation , HttpServletRequest request){
		//修改
		JSONObject result = HttpClientUtil4_3.doPost(BI_SEARCH_DOMAIN+"/learningSiuation/update",JSONObject.fromObject(learningSituation),null);
		if(null != result && 200 == result.getInt("code")) {
			return R.ok();
		}
		return R.error();
	}

	
}
