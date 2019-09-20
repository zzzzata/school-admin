package io.renren.service.impl;

import io.renren.entity.NcSchoolLearningDetailEntity;
import io.renren.entity.NcSchoolLearningEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.dao.NcSchoolUserclassplanDao;
import io.renren.entity.NcSchoolUserclassplanEntity;
import io.renren.service.NcSchoolUserclassplanService;


@Service("ncSchoolUserclassplanService")
public class NcSchoolUserclassplanServiceImpl implements NcSchoolUserclassplanService {
	@Autowired
	private NcSchoolUserclassplanDao ncSchoolUserclassplanDao;
	
	@Override
	public NcSchoolUserclassplanEntity queryObject(Map<String, Object> map){
		return ncSchoolUserclassplanDao.queryObject(map);
	}
	
	@Override
	public List<NcSchoolUserclassplanEntity> queryList(Map<String, Object> map){
		return ncSchoolUserclassplanDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ncSchoolUserclassplanDao.queryTotal(map);
	}
	
	@Override
	public void save(NcSchoolUserclassplanEntity ncSchoolUserclassplan){
		ncSchoolUserclassplanDao.save(ncSchoolUserclassplan);
	}
	
	@Override
	public void update(NcSchoolUserclassplanEntity ncSchoolUserclassplan){
		ncSchoolUserclassplanDao.update(ncSchoolUserclassplan);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		ncSchoolUserclassplanDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		ncSchoolUserclassplanDao.deleteBatch(map);
	}

    @Override
    public boolean isHandle(String ncUserClassplanId, Date ncModifiedTime) {
	    Map parmMap = new HashMap();
	    parmMap.put("ncUserClassplanId",ncUserClassplanId);
        int total =  ncSchoolUserclassplanDao.userClassplanTotal(parmMap);
        if (total == 0 ){
            return true;
        }
        parmMap.put("ncModifiedTime",ncModifiedTime);
        int timeTotal = ncSchoolUserclassplanDao.userClassplanTotal(parmMap);
        if (timeTotal > 0){
           return true;
        }
        return false;
    }

    @Override
    public void saveLearning(NcSchoolLearningEntity learningEntity) {
        ncSchoolUserclassplanDao.saveLearning(learningEntity);
    }

    @Override
    public void saveLearningDetail(NcSchoolLearningDetailEntity detailEntity) {
        ncSchoolUserclassplanDao.saveLearningDetail(detailEntity);
    }

    @Override
    public void updateLearningAndDetail(String ncUserClassplanId) {
        List<NcSchoolLearningEntity> detailEntityList = ncSchoolUserclassplanDao.queryLearningDetail(ncUserClassplanId);
        if (detailEntityList != null && detailEntityList.size() > 0){
            for (NcSchoolLearningEntity detailEntity : detailEntityList) {
                ncSchoolUserclassplanDao.updateLearningDr(detailEntity.getLearningId());
                ncSchoolUserclassplanDao.updateLearningDetailDr(detailEntity.getLearningId());
            }
        }
    }

    @Override
    public boolean isExistByNcUserClassplanId(String ncUserClassplanId) {
        return ncSchoolUserclassplanDao.isExistByNcUserClassplanId(ncUserClassplanId) > 0 ? true : false;
    }

    @Override
    public boolean queryAdaptiveCourse(String classplanId) {
        return ncSchoolUserclassplanDao.queryAdaptiveCourse(classplanId) > 0 ? true:false;
    }
}
