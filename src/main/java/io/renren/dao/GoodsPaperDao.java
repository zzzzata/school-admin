package io.renren.dao;

import io.renren.entity.GoodsPaperEntity;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 商品-题库试卷对照表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-28 11:39:28
 */
public interface GoodsPaperDao extends BaseMDao<GoodsPaperEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

	int getCountByGoodIdAndPaperId(@Param("goodId")Long goodId, @Param("paperId")Long paperId);
}
