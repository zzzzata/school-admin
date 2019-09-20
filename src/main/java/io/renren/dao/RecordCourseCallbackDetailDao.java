package io.renren.dao;

import io.renren.entity.RecordCourseCallbackDetailEntity;

public interface RecordCourseCallbackDetailDao {

    int insert(RecordCourseCallbackDetailEntity record);

    Long queryCourseIdByRecordId(Integer recordId);

}