package io.renren.controller.manage;

import io.renren.common.doc.ParamKey;
import io.renren.controller.AbstractController;
import io.renren.service.ask.UnlimitedAskCourseService;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 会答无限次提问课程管理Controller
 * @author chen xin yu
 * @date 2019-05-07 14:37
 */
@Controller
public class UnlimitedAskCourseController extends AbstractController {

    @Resource
    private UnlimitedAskCourseService unlimitedAskCourseService;

    /**
     * 会答无限次提问课程集合
     *
     * @param courseId        课程ID
     * @param courseName     课程名称
     * @param currentPage    当前页
     * @param pageSize      每页数据量
     * @author chen xin yu
     * @date 2019-04-30 14:51
     */
    @ResponseBody
    @RequestMapping("/unlimitedAskCourse/list")
    @RequiresPermissions("teacherTipAuthority:list")
    public R unlimitedAskCourseList(@RequestParam(value = ParamKey.In.PAGE, defaultValue = "1") Integer currentPage,
                                 @RequestParam(value = ParamKey.In.LIMIT, defaultValue = "30") Integer pageSize,
                                 @RequestParam(value = "courseId", required = false) String courseId,
                                 @RequestParam(value = "courseName", required = false) String courseName) {
        try {
            PageUtils result = unlimitedAskCourseService.getUnlimitedAskCourseList(currentPage, pageSize, courseId, courseName);
            return R.ok().put(result);
        } catch (Exception e) {
            logger.error("UnlimitedAskCourseController:unlimitedAskCourseList--error", e);
            return R.error();
        }
    }

    /**
     * 新增、更新、逻辑删除无限制提问班级信息
     * @param id
     * @param courseId      NC课程id
     * @param courseName    课程名称
     * @param dr             删除标识 0 正常 1 删除
     * @author chen xin yu
     * @date 2019-05-07 17:11
     */
    @ResponseBody
    @RequestMapping("/unlimitedAskCourse/addOrUpdate")
    @RequiresPermissions("teacherTipAuthority:update")
    public R saveOrUpdateUnlimitedAskCourse(
            @RequestParam(value = "id",required = false)String id,
            @RequestParam(value = "courseId") String courseId,
            @RequestParam(value = "courseName") String courseName,
            @RequestParam(value = "dr",required = false) Integer dr) {
        try {
            return unlimitedAskCourseService.saveOrUpdateUnlimitedAskCourse(id,courseId,courseName,dr);
        } catch (Exception e) {
            logger.error("UnlimitedAskCourseController:saveOrUpdateUnlimitedAskCourse--error", e);
            return R.error();
        }

    }
}
