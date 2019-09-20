package io.renren.service;

import io.renren.entity.RecordCourseCallbackDetailEntity;

public interface RecordCourseCallbackService {

	void saveRecordCallbackDetail(RecordCourseCallbackDetailEntity entity);

	Long queryCourseIdByRecordId(Integer recordId);

}
