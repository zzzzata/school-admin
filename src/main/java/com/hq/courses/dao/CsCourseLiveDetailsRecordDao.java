package com.hq.courses.dao;

import com.hq.courses.entity.CsCourseLiveDetailsEntity;
import com.hq.courses.entity.CsCourseLiveDetailsRecordEntity;
import io.renren.dao.BaseMDao;

import java.util.Map;

/**
 * 课次
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-22 11:43:43
 */
public interface CsCourseLiveDetailsRecordDao extends BaseMDao<CsCourseLiveDetailsRecordEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

	void saveRecord(CsCourseLiveDetailsEntity t);
}
