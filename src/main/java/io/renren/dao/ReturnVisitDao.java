package io.renren.dao;

import io.renren.entity.ReturnVisitEntity;
import io.renren.pojo.ReturnVisitPOJO;

import java.util.List;
import java.util.Map;

/**
 * 回访
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-17 17:20:06
 */
public interface ReturnVisitDao extends BaseMDao<ReturnVisitEntity> {

	ReturnVisitPOJO queryPOJOObject(Map<String, Object> map);

	List<ReturnVisitPOJO> queryPOJOList(Map<String, Object> map);

    Long queryLast(Long recordSignId);

	Integer checkIsContact(Long recordSignId);
}
