package io.renren.service.impl;


import io.renren.dao.TeachClassplanStudentfileDao;
import io.renren.entity.TeachClassplanStudentfileEntity;
import io.renren.service.TeachClassplanStudentfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 排课学员课程资料关系表 服务实现类
 * </p>
 *
 * @author hzr
 * @since 2018-12-27
 */
@Service
public class TeachClassplanStudentfileServiceImpl  implements TeachClassplanStudentfileService {
    @Autowired
    private TeachClassplanStudentfileDao teachClassplanStudentfileDao;
    /**
     * 保存中间表数据
     * @param teachClassplanStudentfile
     */
    public void save(TeachClassplanStudentfileEntity teachClassplanStudentfile) {
        teachClassplanStudentfileDao.save(teachClassplanStudentfile);
    }

    /**
     * 根据classplanId删除中间表的数据
     * @param classplanId id
     */
    public void delete(String classplanId) {
        teachClassplanStudentfileDao.delect(classplanId);
    }

    /**
     * 根据changeId查询资料库数据
     * @param classplanId
     * @return
     */
    public List<TeachClassplanStudentfileEntity> get(String classplanId) {
        List<TeachClassplanStudentfileEntity> teachClassplanStudentfileEntity =teachClassplanStudentfileDao.get(classplanId);
        return teachClassplanStudentfileEntity;
    }


}
