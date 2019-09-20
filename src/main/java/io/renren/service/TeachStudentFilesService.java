package io.renren.service;

import io.renren.entity.TeachStudentFilesEntity;

import java.util.List;

/**
 * 学员课程资料 服务实现类
 * @author chenlinfeng
 * @since 2018-12-25
 */
public interface TeachStudentFilesService {
    /**
     * 根据id获取 学员课程资料数据
     * @param materialId
     * @return
     */
    TeachStudentFilesEntity selectById(Long materialId);
    /**
     * 根据课程的id去查询相应的资料库资料,注意:是从新表查询的
     * 从teach_student_files查询
     * @param courseId  查看的id
     * @return  返回资料的列表
     */
    List<TeachStudentFilesEntity> selectMaterialListByCourseId(Long courseId);

    /**
     * 获取所有的资料库资料
     * 从teach_student_files查询
     * @return  返回资料的列表
     */
    List<TeachStudentFilesEntity> getList();

}
