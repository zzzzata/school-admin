package io.renren.service;

import io.renren.entity.TeachStudentFilesDetailEntity;

import java.util.List;
/**
 * 学员课程资料明细 服务实现类
 * @author chenlinfeng
 * @since 2018-12-25
 */
public interface TeachStudentFilesDetailService {


    List<TeachStudentFilesDetailEntity> selectMaterialListByCourseId(Long classtypeId);
}
