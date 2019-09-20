package io.renren.dao;


import io.renren.entity.TeachClassplanStudentfileEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 排课学员课程资料关系表 Mapper 接口
 * </p>
 *
 * @author hzr
 * @since 2018-12-27
 */
public interface TeachClassplanStudentfileDao{

    void save(TeachClassplanStudentfileEntity teachClassplanStudentfile);

    void delect(String classplanId);


    List<TeachClassplanStudentfileEntity> get(@Param("classplanId") String classplanId);
}
