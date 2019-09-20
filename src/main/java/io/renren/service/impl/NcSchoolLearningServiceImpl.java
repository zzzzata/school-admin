package io.renren.service.impl;

import io.renren.pojo.NcSchoolLearningDetailPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.dao.NcSchoolLearningDao;
import io.renren.entity.NcSchoolLearningEntity;
import io.renren.service.NcSchoolLearningService;
import io.renren.utils.Constant;



@Service("ncSchoolLearningService")
public class NcSchoolLearningServiceImpl implements NcSchoolLearningService {
	@Autowired
	private NcSchoolLearningDao ncSchoolLearningDao;
	
	@Override
	public NcSchoolLearningEntity queryObject(Map<String, Object> map){
		return ncSchoolLearningDao.queryObject(map);
	}
	
	@Override
	public List<NcSchoolLearningEntity> queryList(Map<String, Object> map){
		return ncSchoolLearningDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ncSchoolLearningDao.queryTotal(map);
	}
	
	@Override
	public void save(NcSchoolLearningEntity ncSchoolLearning){
		ncSchoolLearningDao.save(ncSchoolLearning);
	}
	
	@Override
	public void update(NcSchoolLearningEntity ncSchoolLearning){
		ncSchoolLearningDao.update(ncSchoolLearning);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		ncSchoolLearningDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		ncSchoolLearningDao.deleteBatch(map);
	}

    @Override
    public List<NcSchoolLearningDetailPOJO> queryDetail(Map<String, Object> map) {
        return ncSchoolLearningDao.queryDetail(map);
    }

    @Override
    public int queryDetailTotal(Map<String, Object> map) {
        return ncSchoolLearningDao.queryDetailTotal(map);
    }


}
