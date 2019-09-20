package io.renren.service.courseliverelaterecord;

import io.renren.entity.SysUserEntity;
import io.renren.entity.courseliverelaterecord.CourseLiveRelateRecordEntity;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 直播课次与录播章节对照关系 Service
 * @author chen xin yu
 * @date 2019-06-18 11:48
 */
public interface CourseLiveRelateRecordService {

    /**
     * 直播课次与录播章节对照关系 列表
     * @param currentPage 当前页
     * @param pageSize    每页数据量
     * @param courseId    课程id
     * @param liveName    直播课次名称
     * @param recordName  录播章节名称
     * @param state       0 停用 1 启用
     * @return PageUtils
     * @author chen xin yu
     * @date 2019-06-18 11:51
     */
    PageUtils getCourseLiveRelateRecordList(Integer currentPage, Integer pageSize, Long courseId, String liveName, String recordName, Integer state);

    /**
     * 新增或修改 直播课次与录播章节对照关系
     * @param courseLiveRelateRecord
     * @param recordIds 关联的录播课id
     * @return
     */
    R saveOrUpdateCourseLiveRelateRecord(CourseLiveRelateRecordEntity courseLiveRelateRecord,String recordIds);

    /**
     * 更新状态
     * @param liveIds    直播id，多个逗号隔开
     * @param state     0 禁用 1 启用
     * @param dr        0 正常 1 删除
     * @return
     */
    R saveOrUpdateState(String liveIds, Integer dr, Integer state);

    /**
     * Excel 批量导入
     * @param file
     * @return
     */
    int importData(MultipartFile file) throws Exception;

    /**
     * 导出数据到Excel
     * @param response
     */
    void exportData(HttpServletResponse response);
}
