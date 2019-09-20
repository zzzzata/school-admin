package io.renren.controller;

import io.renren.common.doc.SysLog;
import io.renren.entity.RecordInfoEntity;
import io.renren.pojo.RecordSignPOJO;
import io.renren.service.RecordInfoService;
import io.renren.service.RecordSignService;
import io.renren.utils.DateUtils;
import io.renren.utils.ExcelReaderJXL;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 学员档案-基础信息
 *
 * @author lintf
 */
@Controller
@RequestMapping("record/recordInfo")
public class RecordInfoController extends AbstractController {
    /**
     * 导入和导出时用到的字段
     */
    private static final String[] ExcelTitleName = new String[]{"序号", "姓名", "性别", "身份证", "电话号码", "年龄", "学历", "QQ", "是否结婚", "是否生育", "小孩数量(填写数字)", "现工作岗位", "已有证书", "学员目标", "正备考证书", "产品线"};
    private static final String[] ExcelTitleKey = new String[]{"rowNo", "name", "sex", "idCard", "mobile", "age", "record", "qq", "marriageStatus", "fertilityStatus", "chirdCount", "postName", "accountingCertificates", "studentTarget", "certificate", "productId"};
    /**
     * 判断不能为空的项目
     */
    private static final String[] ExcelValueNotnull = new String[]{"name", "idCard", "record", "mobile"};
    @Autowired
    private RecordInfoService recordInfoService;

    @Autowired
    RecordSignService recordSignService;

    /**
     * 根据id取得基础信息
     *
     * @param recordInfoId
     * @param request
     * @return
     * @author lintf
     * 2018年8月13日
     */
    @ResponseBody
    @RequestMapping("/info/{recordId}")
    public R info(@PathVariable("recordId") Long recordInfoId, HttpServletRequest request) {

        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("recordId", recordInfoId);
        RecordInfoEntity recordInfo = recordInfoService.queryObject(queryMap);
        return R.ok().put("recordInfo", recordInfo);
    }


    /**
     * 更新基础信息表
     */
    @SysLog("页面更新学员档案基础信息")
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("record:recordInfo:update")
    public R update(@RequestBody RecordInfoEntity e) {
        e.setModifyPerson(getUserId());
        e.setModifyTime(new Date());
        recordInfoService.upDateRecordInfo(e);
        return R.ok();

    }

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
    @RequiresPermissions("record:recordInfo:list")
    public R list(HttpServletRequest request) {
        Map<String, Object> map = getMapPage(request);
        stringQuery(map, request, "name");
        stringQuery(map, request, "mobile");
        stringQuery(map, request, "idCard");
        longQuery(map, request, "recordId");
        longQuery(map, request, "teacherId");
        longQuery(map, request, "areaId");
        longQuery(map, request, "deptId");
        stringQuery(map, request, "deptIdList");
        longQuery(map, request, "classId");
        //判断是查询全部还是查询多部门
        String deptIdList = (String) map.get("deptIdList");
        if (deptIdList != null && !"".equals(deptIdList.trim())) {
            String[] split = deptIdList.split(",");
            map.put("deptIdList", Arrays.asList(split));
        }
        stringQuery(map, request, "classplanId");
        longQuery(map, request, "followStatus");
        longQuery(map, request, "returnType");
        stringQuery(map, request, "approvalDate");
        stringQuery(map, request, "expectTime");
        stringQuery(map, request, "ncCode");
        stringQuery(map, request, "zy");

        stringQuery(map, request, "courseName");
        stringQuery(map, request, "startRegDate");
        stringQuery(map, request, "endRegDate");
        stringQuery(map, request, "consultant");
        stringQuery(map, request, "startReceiveDate");
        stringQuery(map, request, "endReceiveDate");
        stringQuery(map, request, "classType");
        longQuery(map, request, "dr");
        longQuery(map, request, "productId");
        longQuery(map, request, "cstatus");
        stringQuery(map, request, "isInsurant");


        List<RecordSignPOJO> list = recordSignService.queryPOJOList(map);

        int total = recordSignService.queryPOJOTotal(map);

        PageUtils pageUtil = new PageUtils(list, total, request);
        return R.ok().put(pageUtil);
    }


    /**
     * 学员档案基础信息EXCEL批量导入
     *
     * @throws Exception
     */
    @SysLog("学员档案基础信息EXCEL批量导入")
    @ResponseBody
    @RequestMapping("/getExcelRecordInfoImportData")
    @RequiresPermissions("record:recordInfo:importData")


    public R getRecordInfoImport(HttpServletRequest request) throws Exception {

        MultipartHttpServletRequest mpReq = (MultipartHttpServletRequest) request;
        MultipartFile file = mpReq.getFile("file_data");
        List<RecordInfoEntity> detailList = new ArrayList<RecordInfoEntity>();
        if (ObjectUtils.isEmpty(file) || file.getSize() == 0) {
            return R.ok("请选择文件上传");
        }


        FileInputStream fio = (FileInputStream) file.getInputStream();
        List<String[]> dataList = ExcelReaderJXL.readExcel(fio);

        String exceptMsg = "";
        String notMatchMsg = "";
        String[] header = dataList.get(0);
        ArrayList<String> exceptList = new ArrayList<String>();

        Map<String, String> keyGetName = new HashMap<String, String>();

        // 判断列名是否对应得上

        boolean headerCheck = true;// 当列数和列名是否对得上
        if (header.length < this.ExcelTitleName.length) {
            exceptMsg = "总列数不正确，请核对一下列数；";
            headerCheck = false;
            exceptList.add(exceptMsg);
        } else {
            for (int i = 0; i < header.length; i++) {

                if (i < this.ExcelTitleName.length) {
                    if (!header[i].trim().equals(ExcelTitleName[i])) {
                        exceptMsg = "第" + (i + 1) + "列的数据与模板不一致,应该是" + ExcelTitleName[i] + ",不应该是" + header[i].trim()
                                + ",请认真核对。";
                        exceptList.add(exceptMsg);
                        headerCheck = false;
                    }

                    keyGetName.put(ExcelTitleKey[i], ExcelTitleName[i]);

                }

            }
        }

        // 判断列名完成

        if (headerCheck) {

            for (int i = 1; i < dataList.size(); i++) {
                notMatchMsg = "";
                // 是否有错误 有错误的不导入只加入错误信息 并提示是哪一行的错误
                boolean hasError = false;
                exceptMsg = "";
                Map<String, Object> map = getMapPage(request);
                String[] dataArr = dataList.get(i);
                int count = 0;
                for (int n = 0; n < ExcelTitleKey.length; n++) {
                    if (dataArr[n] == null || "".equals(dataArr[n])) {
                        count++;
                    }
                    map.put(ExcelTitleKey[n], dataArr[n]);

                }
                /**
                 * 如果有一行是全空的则不会再做判断这一行
                 */
                if (count == ExcelTitleKey.length) {
                    continue;
                }


                for (String notNul : this.ExcelValueNotnull) {
                    if (map.get(notNul) == null || map.get(notNul).toString().equals("")) {

                        exceptMsg += keyGetName.get(notNul) + " ";
                        //	exceptList.add(exceptMsg);
                        hasError = true;
                    }
                    if ("mobile".equals(notNul)) {
                        String mobile = map.get(notNul).toString().trim();//.replaceAll("\\D+", "");
                        if (mobile.length() == 11 && mobile.matches("^(1)\\d{10}$")) {
                            map.put(notNul, mobile);
                        } else {
                            notMatchMsg += " 电话号码不合法!";
                            hasError = true;
                        }

                    }
                    if ("idCard".equals(notNul)) {
                        String idcard = map.get(notNul).toString().trim();
                        if (idcard.length() == 18 || idcard.length() == 15) {
                            if (idcard.matches("^\\d{17}([0-9]|x|X){1}$") ||

                                    idcard.matches("^\\d{14}([0-9]|x|X){1}$")) {

                            } else {
                                notMatchMsg += " 身份证不合法!";
                                hasError = true;
                            }


                        } else {


                            if (idcard.matches("^[a-zA-Z][0-9]{9}$") || // 台湾
                                    idcard.matches("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?$") ||// 澳门
                                    idcard.matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$")) // 香港
                            {

                            }
                            {
                                notMatchMsg += " 身份证不合法!";
                                hasError = true;
                            }

                        }

                    }


                }

                // 如果有错误的 则加入提示列表 不加到导入列表
                if (hasError) {
                    exceptMsg = exceptMsg.trim().length() > 0 ? exceptMsg + "字段不能为空。" : "";
                    exceptList.add("第" + new Integer(i + 1).toString() + "行数据：" + exceptMsg + notMatchMsg);
                    continue;
                } else {
                    RecordInfoEntity info = new RecordInfoEntity(map);

                    // 创建用户
                    info.setCreatePerson(getUserId());
                    // 修改用户
                    info.setModifyPerson(getUserId());
                    // 创建时间
                    info.setCreateTime(new Date());
                    // 修改时间
                    info.setModifyTime(new Date());
                    // 默认状态

                    detailList.add(info);
                }

            }

        }
        String errMsgErr = "";
        for (int i = 0; i < exceptList.size(); i++) {
            errMsgErr += exceptList.get(i) + "<br>";
        }
        // 没有错误并的才保存
        if (errMsgErr.equals("") && detailList != null && detailList.size() > 0) {
            String seccMessage = "导入完成:";
            int updateCount = 0;
            int insertCount = 0;
            int failCount = 0;
            for (RecordInfoEntity e : detailList) {

                //更新还是新增是以电话号进行判断  如果已经存在的则更新
                //int result = recordInfoService.upDateOrSaveByMobile(e);

//        2019-08-15 修改成根据userId和productId更新记录，不新增
                int result = recordInfoService.updateByProductId(e);

                if (result == 1) {
                    insertCount++;
                } else if (result == 2) {
                    updateCount++;
                } else if (result == 0) {
                    failCount++;
                }
            }
            return R.ok();
//            return R.ok(seccMessage+"新增:"+insertCount+"条,更新:"+updateCount+"条, 错误:"+failCount+"条");
        } else {
            return R.ok(errMsgErr);
        }

    }

    /**
     * 学员档案基础信息模板导出
     *
     * @param request
     * @param response
     * @author lintf
     * 2018年8月13日
     */
    @ResponseBody
    @RequestMapping("/exportExcelRecordInfoTemplate")
    public void DownloadRecordInfoImportTemplate(HttpServletRequest request, HttpServletResponse response) {
        String[] cells = new String[ExcelTitleName.length];
        for (int i = 0; i < ExcelTitleName.length; i++) {
            cells[i] = "0," + i + ",0,0," + ExcelTitleName[i];
        }
        try {
            String filename = "基础信息导入模板-" + Calendar.getInstance().getTimeInMillis() + ".xls";
            filename = new String(filename.getBytes("GBK"), "iso-8859-1");
            response.setContentType("text/html; charset=UTF-8");
            response.addHeader("Content-Disposition", (new StringBuilder())
                    .append("attachment;filename=")
                    .append(filename + ";").toString());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            ExcelReaderJXL.exportToJxlExcel(filename, "基础信息导入", cells, toClient);
            toClient.flush();
            toClient.close();
        } catch (Exception es) {
            logger.error("exportExcelCourseExamRecordTemplate has erros,message is {}", es);
        }
    }
}
