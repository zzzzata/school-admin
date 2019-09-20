package io.renren.controller;

import io.renren.common.doc.SysLog;
import io.renren.entity.ReturnVisitConfigEntity;
import io.renren.service.ReturnVisitConfigService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-25 10:54:11
 */
@Controller
@RequestMapping("returnvisitconfig")
public class ReturnVisitConfigController extends AbstractController {
    @Autowired
    private ReturnVisitConfigService returnVisitConfigService;


    /**
     * 已有回访日期列表
     */
    List<ReturnVisitConfigEntity> returnVisitConfigList;

    @RequestMapping("/returnvisitconfig.html")
    public String list() {
        return "returnvisitconfig/returnvisitconfig.html";
    }

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("returnvisitconfig:list")
    public R list(HttpServletRequest request) {
        Map<String, Object> map = getMapPage(request);
        //查询列表数据
        returnVisitConfigList = returnVisitConfigService.queryList(map);
        int total = returnVisitConfigService.queryTotal(map);
        PageUtils pageUtil = new PageUtils(returnVisitConfigList, total, request);
        return R.ok().put(pageUtil);
    }


    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("returnvisitconfig:info")
    public R info(@PathVariable("id") Long id, HttpServletRequest request) {
        Map<String, Object> map = getMap(request);
        longQuery(map, request, "id");
        map.put("id", id);
        ReturnVisitConfigEntity returnVisitConfig = returnVisitConfigService.queryObject(map);
        return R.ok().put(returnVisitConfig);
    }

    /**
     * 保存
     */
    @SysLog("保存回访日期配置")
    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions("returnvisitconfig:save")
    public R save(@RequestBody ReturnVisitConfigEntity returnVisitConfig, HttpServletRequest request) {
        //默认状态
        returnVisitConfig.setDr(0);
        //产品线
        returnVisitConfig.setProductId(returnVisitConfig.getProductId());
        //生成频率天数
        returnVisitConfig.setReturnNum(returnVisitConfig.getReturnNum());
        //创建用户
        returnVisitConfig.setUpdatePerson(getUserId());
        //创建时间
        returnVisitConfig.setCreateTime(new Date());
        //更新时间
        returnVisitConfig.setUpdateTime(new Date());

        //是否存在相同记录
        if (isExist(returnVisitConfig, returnVisitConfigList)) {
            return R.error(-1, "已存在相同的产品线名称和天数");
        }
        //保存
        returnVisitConfigService.save(returnVisitConfig);
        return R.ok();
    }

    /**
     * 判断是否有相同记录
     *
     * @param returnVisitConfig
     * @param returnVisitConfigList
     * @return
     */
    public boolean isExist(ReturnVisitConfigEntity returnVisitConfig, List<ReturnVisitConfigEntity> returnVisitConfigList){
        for (int i = 0; i < returnVisitConfigList.size(); i++) {
            //不能与已有的产品线名称和频率天数相同
            if (returnVisitConfigList.get(i).getReturnNum().equals(returnVisitConfig.getReturnNum()) &&
                    returnVisitConfigList.get(i).getProductName().equals(returnVisitConfig.getProductName())) {
                return true;
            }
        }
        return false;
    }



    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("returnvisitconfig:update")
    public R update(@RequestBody ReturnVisitConfigEntity returnVisitConfig, HttpServletRequest request) {
        //生成频率天数
        returnVisitConfig.setReturnNum(returnVisitConfig.getReturnNum());
        //修改用户
        returnVisitConfig.setUpdatePerson(getUserId());
        //修改时间
        returnVisitConfig.setUpdateTime(new Date());

        //是否存在相同记录
        if (isExist(returnVisitConfig, returnVisitConfigList)) {
            return R.error(-1, "已存在相同的产品线名称和天数");
        }
        //修改
        returnVisitConfigService.update(returnVisitConfig);
        return R.ok();
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions("returnvisitconfig:delete")
    public R delete(@RequestBody Long[] ids, HttpServletRequest request) {
        Map<String, Object> map = getMap(request);
        map.put("ids", ids);
        returnVisitConfigService.deleteBatch(map);
        return R.ok();
    }

}
