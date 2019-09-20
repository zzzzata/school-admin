package io.renren.dao;

import io.renren.entity.TeachStudentFilesDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学员课程资料明细 dao
 * @author chenlinfeng
 * @since 2018-12-25
 */
public interface TeachStudentFileDetailDao extends BaseMDao<TeachStudentFilesDetailEntity> {

	List<TeachStudentFilesDetailEntity> selectMaterialListByCourseId(@Param("classtypeId") Long classtypeId);
}
