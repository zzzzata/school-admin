package io.renren.dao;

import io.renren.entity.TeachStudentFilesDetailEntity;
import io.renren.entity.TeachStudentFilesEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学员课程资料明细 dao
 * @author chenlinfeng
 * @since 2018-12-25
 */
public interface TeachStudentFilesDao extends BaseMDao<TeachStudentFilesDetailEntity> {

    List<TeachStudentFilesEntity> selectMaterialListByCourseId(@Param("courseId") Long courseId);
    List<TeachStudentFilesEntity> getList();

    TeachStudentFilesEntity select(@Param("materialId") Long materialId);
}
