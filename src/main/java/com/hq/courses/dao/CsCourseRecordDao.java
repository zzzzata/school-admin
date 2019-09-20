package com.hq.courses.dao;

import com.hq.courses.entity.CsCourseRecordEntity;
import com.hq.courses.pojo.CsCourseRecordPOJO;
import io.renren.dao.BaseMDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-19 15:18:43
 */
@Repository
public interface CsCourseRecordDao extends BaseMDao<CsCourseRecordEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

}
