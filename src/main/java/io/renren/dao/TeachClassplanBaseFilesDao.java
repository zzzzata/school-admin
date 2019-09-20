package io.renren.dao;

import io.renren.entity.TeachClassplanBaseFilesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 学员使用的课件资料 Mapper 接口
 * </p>
 *
 * @author hzr
 * @since 2018-11-30
 */
public interface TeachClassplanBaseFilesDao extends BaseDao<TeachClassplanBaseFilesEntity> {

     List<TeachClassplanBaseFilesEntity> queryListByCourseLiveDetailsId(@Param("courseLiveDetailsId") Long courseLiveDetailsId);
}
