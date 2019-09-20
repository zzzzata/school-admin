package io.renren.controller.manage;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.doc.ParamKey;
import io.renren.common.doc.SysLog;
import io.renren.controller.AbstractController;
import io.renren.entity.manage.TeacherTipAuthority;
import io.renren.service.SysUserService;
import io.renren.service.ask.TeacherAskTipService;
import io.renren.utils.ExcelReaderJXL;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.http.HttpClientUtil4_3;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教师抢答、提问权限管理
 * Created by Administrator on 2017/12/7 0007.
 *
 * @author Evan
 */
@Controller
@RequestMapping("/teacherTipAuthority")
public class TeacherTipAuthorityController extends AbstractController {

    @Value("#{application['kuaida.api']}")
    private String KUAIDA_API;

    @Resource
    private SysUserService sysUserService;
    @Resource
    private TeacherAskTipService teacherAskTipService;

    /**
     * 教师抢答权限列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("teacherTipAuthority:list")
    public R list(HttpServletRequest request) throws Exception {
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile", "").trim();
        int answerPermission = ServletRequestUtils.getIntParameter(request, "answerPermission", -1);
        String childTipIds = ServletRequestUtils.getStringParameter(request,"childTipIds","");
        int currPage = ServletRequestUtils.getIntParameter(request, ParamKey.In.PAGE, 1);
        int pageSize = ServletRequestUtils.getIntParameter(request, ParamKey.In.LIMIT, ParamKey.In.DEFAULT_MAX_LIMIT);

        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("answerPermission", answerPermission + "");
        params.put("currPage", currPage + "");
        params.put("pageSize", pageSize + "");
        params.put("childTipIds",childTipIds);

        String result = HttpClientUtil4_3.post(KUAIDA_API + "/api/teacherTipAuthority/getTeacherList", params, null);
        logger.info("url：" + KUAIDA_API + "/api/teacherTipAuthority/getTeacherList");
        logger.info("params：" + params.toString());
        logger.info("result：" + result);
        int total = 0;
        List<TeacherTipAuthority> list = null;
        if (StringUtils.isNotBlank(result)) {
            JSONObject object = JSONObject.parseObject(result);
            if (null != object && 1 == object.getIntValue("code")) {
                JSONObject data = object.getJSONObject("data");
                total = data.getIntValue("total");
                list = JSONObject.parseArray(data.getString("list"), TeacherTipAuthority.class);
            }
        }
        PageUtils pageUtil = new PageUtils(list, total, pageSize, currPage);
        return R.ok().put(pageUtil);
    }


    /**
     * 修改教师抢答标签权限
     */
    @SysLog("修改教师抢答标签权限")
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("teacherTipAuthority:update")
    public R update(@RequestBody String teacherTipAuthority) throws IOException {

        long userId = getUserId();
        String result = HttpClientUtil4_3.postStr(KUAIDA_API + "/api/teacherTipAuthority/updateUserTip?userId=" + userId, teacherTipAuthority, null);
        logger.info("url：" + KUAIDA_API + "/api/teacherTipAuthority/updateUserTip");
        logger.info("params：" + teacherTipAuthority);
        logger.info("result：" + result);
        return R.ok(result);
    }

    /**
     * 班主任提问权限管理列表
     *
     * @param nickName      教师名称
     * @param mobile        手机号码
     * @param unlimitedAsk 无限制权限状态 0 未开通 1 开通
     * @param currentPage   当前页
     * @param pageSize      每页数据量
     * @author chen xin yu
     * @date 2019-04-30 14:51
     */
    @ResponseBody
    @RequestMapping("/classTeacherAskList")
    @RequiresPermissions("teacherTipAuthority:list")
    public R classTeacherAskList(@RequestParam(value = ParamKey.In.PAGE, defaultValue = "1") Integer currentPage,
                                 @RequestParam(value = ParamKey.In.LIMIT, defaultValue = "30") Integer pageSize,
                                 @RequestParam(value = "nickName", required = false) String nickName,
                                 @RequestParam(value = "mobile", required = false) String mobile,
                                 @RequestParam(value = "unlimitedAsk", required = false) Integer unlimitedAsk) {
        try {
            PageUtils result = teacherAskTipService.getClassTeacherAskList(currentPage, pageSize, nickName, mobile, unlimitedAsk);
            return R.ok().put(result);
        } catch (Exception e) {
            logger.error("TeacherTipAuthorityController:classTeacherAskList--error", e);
            return R.error();
        }
    }

    /**
     * 更新班主任提问权限及标签
     * @param id                       教师提问标签权限id （为空新增，不为空更新）
     * @param tipIds                   提问标签id串
     * @param userId                  用户id
     * @param unlimitedAsk            无限制提问权限 0 关闭 1 开启
     * @author chen xin yu
     * @date 2019-05-05 16:41
     */
    @ResponseBody
    @RequestMapping("/updateClassTeacherAskAuthority")
    @RequiresPermissions("teacherTipAuthority:update")
    public R updateClassTeacherAskAuthority(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "tipIds", required = false) String tipIds,
                                            @RequestParam(value = "userId") Long userId, @RequestParam("unlimitedAsk") Integer unlimitedAsk) {
        try {
            return teacherAskTipService.updateClassTeacherAskAuthority(id, userId, unlimitedAsk, tipIds);
        } catch (Exception e) {
            logger.error("TeacherTipAuthorityCo ntroller:updateClassTeacherAskAuthority--error", e);
            return R.error();
        }
    }

    /**
     * 班主任提问权限 导入模板
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping("/exportTeacherAskAuthorityTemplate")
    public void   exportTeacherAskAuthorityTemplate (HttpServletRequest request, HttpServletResponse response) {
        StringBuffer sbf = new StringBuffer();
        //表头
        sbf.append("0,0,0,0,用户id&0,1,0,0,教师姓名&0,2,0,0,无限次提问权限（开或者关）&0,3,0,0,问题标签ID（多个以/隔开）");
        //示例1
        sbf.append("&1,0,0,0,1319&1,1,0,0,张三&1,2,0,0,开&1,3,0,0,20000198/10000199");
        sbf.append("&2,0,0,0,1330&2,1,0,0,李四&2,2,0,0,关&2,3,0,0,10000200/1000");
        try {
            String cellsStr = new String(sbf.toString().getBytes("GBK"), "GBK");
            String[] cells = cellsStr.split("&");
            String filename = "班主任提问权限导入模板-" + Calendar.getInstance().getTimeInMillis() + ".xls";
            filename = new String(filename.getBytes("GBK"), "iso-8859-1");
            response.setContentType("text/html; charset=UTF-8");
            response.addHeader("Content-Disposition", (new StringBuilder())
                    .append("attachment;filename=")
                    .append(filename+";").toString());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            ExcelReaderJXL.exportToJxlExcel(filename, "班主任提问权限", cells, toClient);
            toClient.flush();
            toClient.close();
        }catch(Exception es) {
            logger.error("exportExcelCourseExamRecordTemplate has erros,message is {}",es);
        }
    }

    /**
     * Excel批量导入班主任提问权限
     */
    @ResponseBody
    @RequestMapping(value = "/importData", method = RequestMethod.POST)
    @Transactional
    public R importData(HttpServletRequest request) {
        MultipartHttpServletRequest mpReq = (MultipartHttpServletRequest) request;
        MultipartFile file = mpReq.getFile("file_data");
        int result = 0;
        try {
            result = teacherAskTipService.importData(file);
        } catch (Exception e) {
            e.printStackTrace();
            return R.ok(e.getMessage());
        }
        return R.ok("成功导入 " + result + " 条数据");
    }


}
