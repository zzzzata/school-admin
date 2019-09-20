package io.renren.service.impl;

import io.renren.dao.FileDownLoadViewDao;
import io.renren.service.FileDownLoadViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fileDownLoadViewService")
public class FileDownLoadViewServiceImpl implements FileDownLoadViewService {

    @Autowired
    private FileDownLoadViewDao fileDownLoadViewDao;

    @Override
    public void downloadFile(Long teachFileId, Long fileDetailId) {
        //交付件下载次数增加1
        fileDownLoadViewDao.downloadTeachFile(teachFileId);
        //学员课程详细资料下载次数增加1
        fileDownLoadViewDao.downloadFileDetail(fileDetailId);

    }

    @Override
    public void viewFile(Long teachFileId, Long fileDetailId) {
        //交付件查看次数增加1
        fileDownLoadViewDao.viewTeachFile(teachFileId);
        //学员课程详细资料查看次数增加1
        fileDownLoadViewDao.viewFileDetail(fileDetailId);
    }
}
