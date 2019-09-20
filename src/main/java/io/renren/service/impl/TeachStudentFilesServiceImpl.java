package io.renren.service.impl;

import io.renren.dao.TeachStudentFilesDao;
import io.renren.entity.TeachStudentFilesEntity;
import io.renren.service.TeachStudentFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学员课程资料明细 服务实现类
 * @author chenlinfeng
 * @since 2018-12-25
 */
@Service
public class TeachStudentFilesServiceImpl implements TeachStudentFilesService {
    @Autowired
    private TeachStudentFilesDao teachStudentFilesDao;

    /**
     * 根据id获取数据
     * @param materialId
     * @return
     */
    public TeachStudentFilesEntity selectById(Long materialId) {

        return teachStudentFilesDao.select(materialId);
    }

    /**
     * 通过课程id获取资料库文件数据
     * @param courseId
     * @return
     */
    public List<TeachStudentFilesEntity> selectMaterialListByCourseId(Long courseId) {
        List<TeachStudentFilesEntity> list = teachStudentFilesDao.selectMaterialListByCourseId(courseId);

        return list;
    }


    /**
     * 获取所有的资料库资料
     * 从teach_student_files查询
     * @return  返回资料的列表
     */
    public List<TeachStudentFilesEntity> getList() {
        List<TeachStudentFilesEntity> list = teachStudentFilesDao.getList();
        return list;
    }
}
