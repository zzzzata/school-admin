package io.renren.service;

import io.renren.entity.NcSchoolLearningDetailEntity;
import io.renren.entity.NcSchoolLearningEntity;
import io.renren.entity.NcSchoolUserclassplanEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 接收NC学员--排课关联信息表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-26 16:54:16
 */
public interface NcSchoolUserclassplanService {
	
		
	NcSchoolUserclassplanEntity queryObject(Map<String, Object> map);
	
	List<NcSchoolUserclassplanEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(NcSchoolUserclassplanEntity ncSchoolUserclassplan);
	
	void update(NcSchoolUserclassplanEntity ncSchoolUserclassplan);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);

    //判断是否要处理学习计划
    boolean isHandle(String ncUserClassplanId, Date ncModifiedTime);

    //生成线下学习计划
    void saveLearning(NcSchoolLearningEntity learningEntity);

    //生成线下学习计划详情
    void saveLearningDetail(NcSchoolLearningDetailEntity detailEntity);

    //关闭线下学习计划和学习计划详情
    void updateLearningAndDetail(String ncUserClassplanId);

    //判断学员线下学习计划是否已经生成
    boolean isExistByNcUserClassplanId(String ncUserClassplanId);

    /**
     * 根据排课计划id判断课程是否是自适应课程
     * @param classplanId
     * @return
     */
    boolean queryAdaptiveCourse(String classplanId);
}
