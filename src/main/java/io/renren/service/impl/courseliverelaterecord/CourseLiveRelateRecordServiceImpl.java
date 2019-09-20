package io.renren.service.impl.courseliverelaterecord;

import io.renren.common.doc.ParamKey;
import io.renren.dao.courseliverelaterecord.CourseLiveRelateRecordDao;
import io.renren.entity.SysUserEntity;
import io.renren.entity.courseliverelaterecord.CourseLiveRelateRecordEntity;
import io.renren.pojo.courseliverelaterecord.CourseLiveRelateRecordPOJO;
import io.renren.service.courseliverelaterecord.CourseLiveRelateRecordService;
import io.renren.utils.ExcelReaderJXL;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 直播课次与录播章节对照关系 Service 实现类
 *
 * @author chen xin yu
 * @date 2019-06-18 11:48
 */
@Service
public class CourseLiveRelateRecordServiceImpl implements CourseLiveRelateRecordService {

    @Resource
    private CourseLiveRelateRecordDao courseLiveRelateRecordDao;

    @Override
    public PageUtils getCourseLiveRelateRecordList(Integer currentPage, Integer pageSize, Long courseId, String liveName, String recordName, Integer state) {
        Map<String, Object> params = new HashMap<>();
        //课程id
        if (!ObjectUtils.isEmpty(courseId)) {
            params.put("courseId", courseId);
        }
        //直播课次名称
        if (StringUtils.isNotBlank(liveName)) {
            params.put("liveName", liveName);
        }
        //录播课次名称
        if (StringUtils.isNotBlank(recordName)) {
            params.put("recordName", recordName);
        }
        //状态 0 停用 1 开启
        if (!ObjectUtils.isEmpty(state)) {
            params.put("state", state);
        }
        //设置分页参数
        if (!ObjectUtils.isEmpty(pageSize)) {
            params.put(ParamKey.In.LIMIT, pageSize);
        }
        if (!ObjectUtils.isEmpty(currentPage)) {
            params.put(ParamKey.In.OFFSET, (currentPage - 1) * pageSize);
        }
        params.put("dr", 0);
        List<CourseLiveRelateRecordPOJO> courseLiveRelateRecordList = courseLiveRelateRecordDao.queryCourseLiveRelateRecordGroupByLive(params);
        //查询数据总量
        Integer total = courseLiveRelateRecordDao.courseLiveRelateRecordListTotal(params);
        return new PageUtils(courseLiveRelateRecordList, total, pageSize, currentPage);
    }

    @Override
    public R saveOrUpdateCourseLiveRelateRecord(CourseLiveRelateRecordEntity courseLiveRelateRecord, String recordIds) {
        //更新的录播id集合
        List<Long> updateRecordIdList = new ArrayList<>();
        //新增的录播id集合
        List<Long> addRecordIdList;
        //删除的录播id集合
        List<Long> delRecordIdList = new ArrayList<>();
        if (StringUtils.isEmpty(courseLiveRelateRecord.getLiveId())) {
            R.error("请选择直播课次");
        }
        if (StringUtils.isEmpty(recordIds)) {
            R.error("请选择录播章节");
        }
        //录播课程id集合
        List<Long> recordIdList = Arrays.stream(recordIds.split(",")).map(e -> Long.parseLong(e)).collect(Collectors.toList());
        //根据直播id查询数据库中的记录，包含删除和未删除的
        Map<String, Object> params = new HashMap<>();
        params.put("liveId", courseLiveRelateRecord.getLiveId());
        List<CourseLiveRelateRecordPOJO> courseLiveRelateRecordList = courseLiveRelateRecordDao.queryCourseLiveRelateRecordList(params);
        if (!ObjectUtils.isEmpty(courseLiveRelateRecordList)) {
            //数据库中已存在的录播记录
            List<Long> recordIdInDBList = courseLiveRelateRecordList.stream().map(e -> e.getRecordId()).collect(Collectors.toList());
            recordIdInDBList.forEach(e -> {
                //已包含的，更新删除状态和启用状态
                if (recordIdList.contains(e)) {
                    updateRecordIdList.add(e);
                } else {
                    //未包含的，说明是需要删除的录播记录
                    delRecordIdList.add(e);
                }
            });
        }
        addRecordIdList = new ArrayList<>(recordIdList);
        addRecordIdList.removeAll(updateRecordIdList);
        addRecordIdList.removeAll(delRecordIdList);
        if (!ObjectUtils.isEmpty(updateRecordIdList)) {
            String updateRecordIds = updateRecordIdList.stream().map(e -> String.valueOf(e)).collect(Collectors.joining("','", "'", "'"));
            updateOrDelete(courseLiveRelateRecord.getLiveId(), updateRecordIds, 0, courseLiveRelateRecord.getState());
        }
        if (!ObjectUtils.isEmpty(delRecordIdList)) {
            String delRecordIds = delRecordIdList.stream().map(e -> String.valueOf(e)).collect(Collectors.joining("','", "'", "'"));
            updateOrDelete(courseLiveRelateRecord.getLiveId(), delRecordIds, 1, courseLiveRelateRecord.getState());
        }
        if (!ObjectUtils.isEmpty(addRecordIdList)) {
            //当前操作用户
            SysUserEntity currentUser = ShiroUtils.getUserEntity();
            Long createUserId = currentUser.getUserId();
            Date createTime = new Date();
            addRecordIdList.forEach(e -> {
                CourseLiveRelateRecordEntity _courseLiveRelateRecord = new CourseLiveRelateRecordEntity();
                _courseLiveRelateRecord.setCourseId(courseLiveRelateRecord.getCourseId());
                _courseLiveRelateRecord.setRecordId(e);
                _courseLiveRelateRecord.setLiveId(courseLiveRelateRecord.getLiveId());
                _courseLiveRelateRecord.setDr(0);
                _courseLiveRelateRecord.setState(courseLiveRelateRecord.getState());
                _courseLiveRelateRecord.setCreateUserId(createUserId);
                _courseLiveRelateRecord.setCreateTime(createTime);
                courseLiveRelateRecordDao.save(_courseLiveRelateRecord);
            });
        }
        return R.ok();
    }

    @Override
    public R saveOrUpdateState(String liveIds, Integer dr, Integer state) {
        List<String> liveIdList = Arrays.stream(liveIds.split(",")).collect(Collectors.toList());
        liveIdList.forEach(liveId -> updateOrDelete(liveId, null, dr, state));
        return R.ok();
    }

    @Override
    public int importData(MultipartFile file) throws Exception {
        FileInputStream fio = (FileInputStream) file.getInputStream();
        List<String[]> dataList = ExcelReaderJXL.readExcel(fio);
        int num = 0;
        //当前操作用户
        SysUserEntity currentUser = ShiroUtils.getUserEntity();
        for (int i = 1; i < dataList.size(); i++) {
            String msg = "第" + i + "行，";
            String[] array = dataList.get(i);
            String courseId = array[0];
            String liveId = array[2];
            String recordId = array[4];
            if (StringUtils.isBlank(courseId) || courseId.trim().length() == 0) {
                throw new Exception(msg + "课程id不能为空");
            }
            if (StringUtils.isBlank(liveId) || liveId.trim().length() == 0) {
                throw new Exception(msg + "直播id不能为空");
            }
            if (StringUtils.isBlank(recordId) || recordId.trim().length() == 0) {
                throw new Exception(msg + "录播id不能为空");
            }
            courseId = courseId.trim();
            liveId = liveId.trim();
            recordId = recordId.trim();
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("liveId", liveId);
            queryMap.put("recordId", recordId);
            List<CourseLiveRelateRecordPOJO> courseLiveRelateRecordList = courseLiveRelateRecordDao.queryCourseLiveRelateRecordList(queryMap);
            if (!ObjectUtils.isEmpty(courseLiveRelateRecordList)) {
                courseLiveRelateRecordList.forEach(e -> {
                    updateOrDelete(e.getLiveId(), null, 0, 1);
                });
            } else {
                CourseLiveRelateRecordEntity _courseLiveRelateRecord = new CourseLiveRelateRecordEntity();
                _courseLiveRelateRecord.setCourseId(Long.valueOf(courseId));
                _courseLiveRelateRecord.setRecordId(Long.valueOf(recordId));
                _courseLiveRelateRecord.setLiveId(liveId);
                _courseLiveRelateRecord.setDr(0);
                _courseLiveRelateRecord.setState(1);
                _courseLiveRelateRecord.setCreateUserId(currentUser.getUserId());
                _courseLiveRelateRecord.setCreateTime(new Date());
                courseLiveRelateRecordDao.save(_courseLiveRelateRecord);
            }
            num++;
        }
        return num;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        //获取数据
        List<CourseLiveRelateRecordPOJO> recordList = courseLiveRelateRecordDao.queryCourseLiveRelateRecordList(new HashMap<> ());
        String[] title = {"所属课程","课次名称","绑定录播课名称","状态"};
        //excel标题
        //excel文件名
        String fileName = "直播课次录播章节关联关系.xls";
        //sheet名
        String sheetName = "直播课次录播章节关联关系";
        String[][] content = new String[1000][1000];
        for (int i = 0; i < recordList.size(); i++) {
            content[i] = new String[title.length];
            CourseLiveRelateRecordPOJO recordPOJO = recordList.get(i);
            content[i][0] = String.valueOf(recordPOJO.getCourseName());
            content[i][1] = String.valueOf(recordPOJO.getLiveName());
            content[i][2] = String.valueOf(recordPOJO.getRecordName());
            content[i][3] = recordPOJO.getState().equals(1) ? "启用" : "停用";
        }
        //创建HSSFWorkbook
        XSSFWorkbook wb = getHSSFWorkbook(sheetName, title, content, null);
        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量更新或删除 直播课次与录播章节对照关系
     *
     * @param liveId
     * @param recordIds
     * @param dr
     * @param state
     */
    private void updateOrDelete(String liveId, String recordIds, Integer dr, Integer state) {
        StringBuilder whereSql = new StringBuilder(" `live_id` = '" + liveId + "'");
        if (StringUtils.isNotBlank(recordIds)) {
            whereSql.append(" AND `record_id` IN (" + recordIds + ") ");
        }
        Map<String, Object> updateParams = new HashMap<>();
        updateParams.put("dr", dr);
        updateParams.put("state", state);
        courseLiveRelateRecordDao.updateBySql(updateParams, whereSql.toString());
    }

    /**
     * 导出Excel
     * @param sheetName
     * @param title
     * @param values
     * @param wb
     * @return
     */
    private static XSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, XSSFWorkbook wb) {
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new XSSFWorkbook();
        }
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        XSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(HorizontalAlignment.CENTER);
        //声明列对象
        XSSFCell cell = null;
        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    /**
     * 发送响应流方法
     * @param response
     * @param fileName
     */
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
