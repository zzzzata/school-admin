package io.renren.dao;

import io.renren.pojo.AttendRecordViewPOJO;
import io.renren.pojo.RecordLogDetailsPOJO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AttendRecordViewDao {
    List<AttendRecordViewPOJO> queryListPOJO(Map<String, Object> map);

	List<RecordLogDetailsPOJO> queryLogPolyvDetailsByUserIdAndRecordId(@Param("userId")Long userId, @Param("recordId")Long recordId);

	List<RecordLogDetailsPOJO> queryRecordCourseCallbackDetailByUserIdAndRecordId(@Param("userId")Long userId, @Param("recordId")Long recordId);
}
