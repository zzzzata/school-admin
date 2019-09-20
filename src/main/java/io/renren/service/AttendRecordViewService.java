package io.renren.service;

import io.renren.pojo.AttendRecordViewPOJO;
import io.renren.pojo.RecordLogDetailsPOJO;

import java.util.List;
import java.util.Map;

public interface AttendRecordViewService {

    List<AttendRecordViewPOJO> queryListPOJO(Map<String, Object> map);

    /**
     * 查询录播考勤明细
     * @param userId 用户id
     * @param recordId 录播课id
     * @return
     */
	List<RecordLogDetailsPOJO> queryLogDetailsByUserIdAndRecordId(Long userId, Long recordId);

}
