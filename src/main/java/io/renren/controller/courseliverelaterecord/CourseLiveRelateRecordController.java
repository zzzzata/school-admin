package io.renren.controller.courseliverelaterecord;

import io.renren.common.doc.ParamKey;
import io.renren.controller.AbstractController;
import io.renren.entity.courseliverelaterecord.CourseLiveRelateRecordEntity;
import io.renren.service.courseliverelaterecord.CourseLiveRelateRecordService;
import io.renren.utils.ExcelReaderJXL;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * 直播课次与录播章节对照关系Controller
 *
 * @author chen xin yu
 * @date 2019-06-18 11:39
 */
@Controller
@RequestMapping("/courseLiveRelateRecord")
public class CourseLiveRelateRecordController extends AbstractController {

    @Resource
    private CourseLiveRelateRecordService courseLiveRelateRecordService;

    /**
     * 直播课次与录播章节对照关系 列表
     *
     * @param currentPage 当前页
     * @param pageSize    每页数据量
     * @param courseId    课程id
     * @param liveName    直播课次名称
     * @param recordName  录播章节名称
     * @param state       0 停用 1 启用
     * @author chen xin yu
     * @date 2019-06-18 11:47
     */
    @ResponseBody
    @RequestMapping("/list")
    public R courseLiveRelateRecordList(@RequestParam(value = ParamKey.In.PAGE, defaultValue = "1") Integer currentPage,
                                        @RequestParam(value = ParamKey.In.LIMIT, defaultValue = "30") Integer pageSize,
                                        @RequestParam(value = "courseId", required = false) Long courseId,
                                        @RequestParam(value = "liveName", required = false) String liveName,
                                        @RequestParam(value = "recordName", required = false) String recordName,
                                        @RequestParam(value = "state", required = false) Integer state) {

        PageUtils result = courseLiveRelateRecordService.getCourseLiveRelateRecordList(currentPage, pageSize, courseId, liveName, recordName, state);
        return R.ok().put(result);
    }

    /**
     * 更新信息
     *
     * @param courseId  课程id
     * @param liveId    直播id
     * @param recordIds 录播id串
     * @param state     0 禁用 1 启用
     * @param dr        0 正常 1 删除
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public R saveOrUpdateCourseLiveRelateRecord(@RequestParam(value = "courseId", required = false) Long courseId,
                                                @RequestParam(value = "liveId", required = false) String liveId, @RequestParam(value = "recordIds", required = false) String recordIds,
                                                @RequestParam(value = "state", required = false) Integer state, @RequestParam(value = "dr", defaultValue = "0") Integer dr) {
        CourseLiveRelateRecordEntity courseLiveRelateRecord = new CourseLiveRelateRecordEntity(null, courseId, liveId, null, state, dr);
        return courseLiveRelateRecordService.saveOrUpdateCourseLiveRelateRecord(courseLiveRelateRecord, recordIds);
    }

    /**
     * 更新状态
     *
     * @param liveIds 直播id,多个以逗号隔开
     * @param state  0 禁用 1 启用
     * @param dr     0 正常 1 删除
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveOrUpdateState")
    public R saveOrUpdateState(@RequestParam("liveIds") String liveIds, @RequestParam(value = "dr", required = false) Integer dr, @RequestParam(value = "state", required = false) Integer state) {
        return courseLiveRelateRecordService.saveOrUpdateState(liveIds, dr, state);
    }

    /**
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping("/exportCourseLiveRelateRecordTemplate")
    public void   DownloadCourseLiveRelateRecordImportTemplate(HttpServletRequest request,HttpServletResponse response) {
        StringBuffer sbf = new StringBuffer();
        //表头
        sbf.append("0,0,0,0,所属课程ID&0,1,0,0,课程名称&0,2,0,0,直播课次ID&0,3,0,0,直播课次名称&0,4,0,0,绑定录播课ID&0,5,0,0,录播课名称");
        //示例1
        sbf.append("&1,0,0,0,518&1,1,0,0,大学语文&1,2,0,0,016e987b-b3c0-4968-ab33-64e4b5b62491&1,3,0,0,004729大学语文 魔法专项（客观题）&1,4,0,0,015722&1,5,0,0,02018 大学语文第一讲");
        sbf.append("&2,0,0,0,518&2,1,0,0,大学语文&2,2,0,0,016e987b-b3c0-4968-ab33-64e4b5b62491&2,3,0,0,004729大学语文 魔法专项（客观题）&2,4,0,0,015723&2,5,0,0,02018 大学语文第二讲");
        try {
            String cellsStr = new String(sbf.toString().getBytes("GBK"), "GBK");
            String[] cells = cellsStr.split("&");
            String filename = "直播关联录播导入模板-" + Calendar.getInstance().getTimeInMillis() + ".xls";
            filename = new String(filename.getBytes("GBK"), "iso-8859-1");
            response.setContentType("text/html; charset=UTF-8");
            response.addHeader("Content-Disposition", (new StringBuilder())
                    .append("attachment;filename=")
                    .append(filename+";").toString());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            ExcelReaderJXL.exportToJxlExcel(filename, "直播关联录播导入", cells, toClient);
            toClient.flush();
            toClient.close();
        }catch(Exception es) {
            logger.error("exportExcelCourseExamRecordTemplate has erros,message is {}",es);
        }
    }

    /**
     * 导入
     */
    @ResponseBody
    @RequestMapping(value = "/importData", method = RequestMethod.POST)
    @Transactional
    public R importData(HttpServletRequest request) {
        MultipartHttpServletRequest mpReq = (MultipartHttpServletRequest) request;
        MultipartFile file = mpReq.getFile("file_data");
        int result = 0;
        try {
            result = courseLiveRelateRecordService.importData(file);
        } catch (Exception e) {
            e.printStackTrace();
            return R.ok(e.getMessage());
        }
        return R.ok("成功导入 " + result + " 条数据");
    }

    /**
     * 导出
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/exportData")
    @ResponseBody
    public void export(HttpServletResponse response) {
        courseLiveRelateRecordService.exportData(response);
    }
}
