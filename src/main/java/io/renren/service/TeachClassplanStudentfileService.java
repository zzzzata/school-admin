package io.renren.service;

import io.renren.entity.TeachClassplanStudentfileEntity;

import java.util.List;

/**
 * <p>
 * 排课学员课程资料关系表 服务类
 * </p>
 *
 * @author hzr
 * @since 2018-12-27
 */
public interface TeachClassplanStudentfileService {
    /**
     * 保存中间表数据
     * @param teachClassplanStudentfile
     */
    void save(TeachClassplanStudentfileEntity teachClassplanStudentfile);

    /**
     * 根据classplanId删除中间表的数据
     * @param classplanId id
     */
    void delete(String classplanId);

    /**
     * 根据changeId查询数据
     * @param classplanId
     * @return
     */
    List<TeachClassplanStudentfileEntity> get(String classplanId);
}
