package io.renren.service.ask;

import io.renren.utils.PageUtils;
import io.renren.utils.R;

/**
 * 会答无限次提问课程管理Service
 * @author chen xin yu
 * @date 2019-05-07 14:42
 */
public interface UnlimitedAskCourseService {

    /**
     * 会答无限次提问课程集合
     * @param currentPage   当前页
     * @param pageSize      每页数据量
     * @param courseId      课程id
     * @param courseName    课程名称
     * @return PageUtils
     * @author chen xin yu
     * @date 2019-05-07 14:46
     */
    PageUtils getUnlimitedAskCourseList(Integer currentPage, Integer pageSize, String courseId, String courseName);

    /**
     * 新增、更新、逻辑删除无限制提问班级信息
     * @param id
     * @param courseId      NC课程id
     * @param courseName   课程名称
     * @param dr           删除标识 0 正常 1 删除
     * @return
     */
    R saveOrUpdateUnlimitedAskCourse(String id,String courseId,String courseName,Integer dr);
}
