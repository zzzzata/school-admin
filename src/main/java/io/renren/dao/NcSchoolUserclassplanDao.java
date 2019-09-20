package io.renren.dao;

import io.renren.entity.NcSchoolLearningDetailEntity;
import io.renren.entity.NcSchoolLearningEntity;
import io.renren.entity.NcSchoolUserclassplanEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 接收NC学员--排课关联信息表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-26 16:54:16
 */
@Repository
public interface NcSchoolUserclassplanDao extends BaseMDao<NcSchoolUserclassplanEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

    int userClassplanTotal(Map parmMap);

    void saveLearning(NcSchoolLearningEntity learningEntity);

    void saveLearningDetail(NcSchoolLearningDetailEntity detailEntity);

    List<NcSchoolLearningEntity> queryLearningDetail(@Param("ncUserClassplanId") String ncUserClassplanId);

    void updateLearningDr(Long learningId);

    void updateLearningDetailDr(Long learningId);

    int isExistByNcUserClassplanId(String ncUserClassplanId);

    int queryAdaptiveCourse(String classplanId);
}
