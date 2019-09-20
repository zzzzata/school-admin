package io.renren.controller;

import io.renren.common.doc.SysLog;
import io.renren.entity.SysTeamEntity;
import io.renren.entity.SysUserEntity;
import io.renren.service.SysTeamService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;
import io.renren.utils.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 团队管理
 * @author hewanxian
 * @version 1.0
 * @date 2019/6/11 15:16
 */
@RestController
@RequestMapping("/systeam")
public class SysTeamController extends AbstractController {

    @Autowired
    private SysTeamService sysTeamService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("systeam:list")
    public R list(HttpServletRequest request){
        Map<String, Object> map = getMapPage(request);
        stringQuery(map, request, "name");
        stringQuery(map, request, "status");
        //查询列表数据
        List<SysTeamEntity> sysTeamList = sysTeamService.queryList(map);
        int total = sysTeamService.queryTotal(map);
        PageUtils pageUtil = new PageUtils(sysTeamList, total , request);
        return R.ok().put(pageUtil);
    }


    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("systeam:info")
    public R info(@PathVariable("id") Long id , HttpServletRequest request){
        if(id == null) return R.error("id不能为空");
        return R.ok().put(sysTeamService.queryById(id));
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions("systeam:save")
    public R save(@RequestBody SysTeamEntity sysTeam , HttpServletRequest request){
        if(StringUtils.isBlank(sysTeam.getName())){
            return R.error("[团队名称]不能为空");
        }
        if(sysTeam.getParentId() == null){
            return R.error("[所属团队]不能为空");
        }
        //保存
        sysTeamService.save(sysTeam);
        return R.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("systeam:update")
    public R update(@RequestBody SysTeamEntity sysTeam , HttpServletRequest request){
        sysTeamService.update(sysTeam);
        return R.ok();
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions("systeam:delete")
    public R delete(@RequestBody Long[] ids , HttpServletRequest request){
        Map<String, Object> map = getMap(request);
        map.put("ids",ids);
        sysTeamService.deleteBatch(map);
        return R.ok();
    }

    /**
     * 启用
     * @param id
     * @return
     */
    @SysLog("启用")
    @ResponseBody
    @RequestMapping("/resume")
    @RequiresPermissions("systeam:resume")
    public R resume(@RequestBody Long id){
        if(id == null) return R.error("id不能为空");
        //先查出其上级是否为启用状态，若是停用状态则不准直接启用
        SysTeamEntity team = sysTeamService.queryObject(id);
        //获取其上级
        Long parentId = team.getParentId();
        SysTeamEntity entity = sysTeamService.queryObject(parentId);
        //判断其上级的状态
        int entityStatus = entity.getStatus();
        if(entityStatus == 0)return R.ok("请先启用上级团队").put("code","1");
        //若上级是启用状态，则可以直接更新启用
        //若上级是启用状态，则可以直接更新启用
        int status = 1;
        sysTeamService.updateTreeByStatus(id,status);
        return R.ok();
    }

    /**
     * 停用
     * @param id
     * @return
     */
    @SysLog("停用")
    @ResponseBody
    @RequestMapping("/pause")
    @RequiresPermissions("systeam:pause")
    public R pause(@RequestBody Long id){
        if(id == null) return R.error("id不能为空");
        int status = 0;
        sysTeamService.updateTreeByStatus(id,status);
        return R.ok();
    }

}
