package io.renren.service;

import io.renren.entity.GoodsPaperEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品-题库试卷对照表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-28 11:39:28
 */
public interface GoodsPaperService {
	
		
	GoodsPaperEntity queryObject(Map<String, Object> map);
	
	List<GoodsPaperEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(GoodsPaperEntity goodsPaper);
	
	void update(GoodsPaperEntity goodsPaper);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);

	int getCountByGoodIdAndPaperId(Long goodId, Long paperId);
	
		
		
}
