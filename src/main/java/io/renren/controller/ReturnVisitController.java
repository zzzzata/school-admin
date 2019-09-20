package io.renren.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.renren.entity.RecordSignEntity;
import io.renren.pojo.RecordSignPOJO;
import io.renren.pojo.ReturnVisitPOJO;
import io.renren.service.RecordSignService;
import io.renren.utils.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import io.renren.entity.ReturnVisitEntity;
import io.renren.service.ReturnVisitService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;


/**
 * 回访
 *
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-17 17:20:06
 */
@Controller
@RequestMapping("returnvisit")
public class ReturnVisitController extends AbstractController {
    @Autowired
    private ReturnVisitService returnVisitService;
    @Autowired
    private RecordSignService recordSignService;

    @RequestMapping("/returnvisit.html")
    public String list() {
        return "returnvisit/returnvisit.html";
    }

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(HttpServletRequest request) {
        Map<String, Object> map = getMapPage(request);
        longQuery(map, request, "recordSignId");
        //查询列表数据
        List<ReturnVisitPOJO> returnVisitList = returnVisitService.queryPOJOList(map);
        int total = returnVisitService.queryTotal(map);
        PageUtils pageUtil = new PageUtils(returnVisitList, total, request);
        return R.ok().put(pageUtil);
    }


    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request) {
        Map<String, Object> map = getMap(request);
        map.put("id", id);
        ReturnVisitPOJO returnVisit = returnVisitService.queryPOJOObject(map);
        return R.ok().put(returnVisit);
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("/save")
    public R save(@RequestBody ReturnVisitEntity returnVisit, HttpServletRequest request) {
        //保存之前检查该学员是否已联系 已联系才允许新增回访记录
        if (!checkIsContact(returnVisit)) {
            return R.error("请先联系学员，再新增回访记录");
        }
        //保存
        returnVisit.setReturnTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        returnVisit.setCreatePerson(getUserId());
        returnVisit.setReturnStatus(1);
        returnVisitService.save(returnVisit);
        //该记录是否是最后一条并且有预计回访时间，是的话更新到record_sign，否则不执行
        try {
            RecordSignEntity RecordSignEntity = recordSignService.findRecordSignById(returnVisit.getRecordSignId());
            Map map = new HashMap();
            map.put("lastReturnTime", new Date());
            map.put("recordSignId", returnVisit.getRecordSignId());
            recordSignService.updateSaveReturnTime(map);
        } catch (Exception e) {
            System.out.println(e);
        }
        return R.ok();
    }

    private boolean checkIsContact(ReturnVisitEntity returnVisit) {
        return returnVisitService.checkIsContact(returnVisit.getRecordSignId()) == 1 ? true : false;
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(@RequestBody ReturnVisitEntity returnVisit, HttpServletRequest request) {
        //修改
        returnVisit.setReturnStatus(1);
        returnVisit.setReturnTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        returnVisit.setCreatePerson(getUserId());
        returnVisitService.update(returnVisit);
        //该记录是否是最后一条并且有预计回访时间，是的话更新到record_sign，否则不执行
        updateReturnTime(returnVisit);

        return R.ok();
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions("returnvisit:delete")
    public R delete(@RequestBody Long[] ids, HttpServletRequest request) {
        Map<String, Object> map = getMap(request);
        map.put("ids", ids);
        returnVisitService.deleteBatch(map);
        return R.ok();
    }

    private void updateReturnTime(ReturnVisitEntity returnVisit) {
        RecordSignEntity recordSignEntity = new RecordSignEntity();
        recordSignEntity.setLastReturnTime(new Date());
        recordSignEntity.setRecordSignId(returnVisit.getRecordSignId());
        //该记录是否是最后一条并且有预计回访时间，是的话更新到record_sign，否则不执行
        Long lastId = returnVisitService.queryLast(returnVisit.getRecordSignId());
        if (lastId.equals(returnVisit.getId())) {
            recordSignService.updateReturnTime(recordSignEntity);
        }
    }
}
