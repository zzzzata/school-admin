package io.renren.service;

import io.renren.entity.RecordSignEntity;
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
public interface ReturnVisitService {
	
		
	ReturnVisitEntity queryObject(Map<String, Object> map);
	
	List<ReturnVisitEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void saveBySign(RecordSignEntity recordSignEntity);

	void save(ReturnVisitEntity returnVisit);

	void update(ReturnVisitEntity returnVisit);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);

	ReturnVisitPOJO queryPOJOObject(Map<String, Object> map);

	List<ReturnVisitPOJO> queryPOJOList(Map<String, Object> map);

	/**
	 * 该记录是否是最后一条并且有预计回访时间
	 *
	 * @param recordSignId
	 */
	Long queryLast(Long recordSignId);


	/**
	 * 查询该学员是否已联系
	 *
	 * @param recordSignId
	 * @return
	 */
	Integer checkIsContact(Long recordSignId);

}
