package io.renren.service.impl;

import io.renren.dao.TeachStudentFileDetailDao;
import io.renren.entity.TeachStudentFilesDetailEntity;
import io.renren.service.TeachStudentFilesDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学员课程资料明细 服务实现类
 * @author chenlinfeng
 * @since 2018-12-25
 */
@Service
public class TeachStudentFilesDetailServiceImpl implements TeachStudentFilesDetailService {
    @Autowired
    private TeachStudentFileDetailDao teachStudentFileDetailDao;
    /**
     * 通过课程id获取资料库文件数据
     * @param classtypeId
     * @return
     */
    public List<TeachStudentFilesDetailEntity> selectMaterialListByCourseId(Long classtypeId) {
        List<TeachStudentFilesDetailEntity> list = teachStudentFileDetailDao.selectMaterialListByCourseId(classtypeId);

        return list;
    }
}
