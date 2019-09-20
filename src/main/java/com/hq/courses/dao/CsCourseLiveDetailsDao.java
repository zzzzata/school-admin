package com.hq.courses.dao;

import com.hq.courses.entity.CsCourseLiveDetailsEntity;
import io.renren.dao.BaseMDao;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 课次
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-04-15 11:08:20
 */
public interface CsCourseLiveDetailsDao extends BaseMDao<CsCourseLiveDetailsEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

	int queryByLiveName(@Param(value="liveName")String liveName);
}
