package io.renren.service.impl;

import io.renren.dao.RecordCourseCallbackDetailDao;
import io.renren.entity.RecordCourseCallbackDetailEntity;
import io.renren.service.RecordCourseCallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("recordCourseCallbackService")
public class RecordCourseCallbackServiceImpl implements RecordCourseCallbackService {

    @Autowired
    RecordCourseCallbackDetailDao dao;

    @Override
    public void saveRecordCallbackDetail(RecordCourseCallbackDetailEntity entity) {
        dao.insert(entity);
    }

    @Override
    public Long queryCourseIdByRecordId(Integer recordId) {
        return dao.queryCourseIdByRecordId(recordId);
    }
}
