package io.renren.service.impl;

import io.renren.dao.AttendRecordViewDao;
import io.renren.pojo.AttendRecordViewPOJO;
import io.renren.pojo.RecordLogDetailsPOJO;
import io.renren.service.AttendRecordViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("attendRecordViewService")
public class AttendRecordViewServiceImpl implements AttendRecordViewService {


    @Autowired
    private AttendRecordViewDao attendRecordViewDao;

    @Override
    public List<AttendRecordViewPOJO> queryListPOJO(Map<String, Object> map) {
        return attendRecordViewDao.queryListPOJO(map);
    }

	@Override
	public List<RecordLogDetailsPOJO> queryLogDetailsByUserIdAndRecordId(Long userId, Long recordId) {
		//查询录播课程观看明细表
		List<RecordLogDetailsPOJO> list1 = attendRecordViewDao.queryLogPolyvDetailsByUserIdAndRecordId(userId, recordId);
		//查询录播课程离线观看明细表
		List<RecordLogDetailsPOJO> list2 = attendRecordViewDao.queryRecordCourseCallbackDetailByUserIdAndRecordId(userId, recordId);
		
		if(null != list2 && list2.size() > 0){
			list1.addAll(list2);
		}
		return list1;
	}

}
