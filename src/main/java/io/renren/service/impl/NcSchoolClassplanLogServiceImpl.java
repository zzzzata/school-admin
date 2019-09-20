package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.dao.NcSchoolClassplanLogDao;
import io.renren.entity.NcSchoolClassplanLogEntity;
import io.renren.service.NcSchoolClassplanLogService;
import io.renren.utils.Constant;



@Service("ncSchoolClassplanLogService")
public class NcSchoolClassplanLogServiceImpl implements NcSchoolClassplanLogService {
	@Autowired
	private NcSchoolClassplanLogDao ncSchoolClassplanLogDao;
	
	@Override
	public NcSchoolClassplanLogEntity queryObject(Map<String, Object> map){
		return ncSchoolClassplanLogDao.queryObject(map);
	}
	
	@Override
	public List<NcSchoolClassplanLogEntity> queryList(Map<String, Object> map){
		return ncSchoolClassplanLogDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ncSchoolClassplanLogDao.queryTotal(map);
	}
	
	@Override
	public void save(NcSchoolClassplanLogEntity ncSchoolClassplanLog){
		ncSchoolClassplanLogDao.save(ncSchoolClassplanLog);
	}
	
	@Override
	public void update(NcSchoolClassplanLogEntity ncSchoolClassplanLog){
		ncSchoolClassplanLogDao.update(ncSchoolClassplanLog);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		ncSchoolClassplanLogDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		ncSchoolClassplanLogDao.deleteBatch(map);
	}
	
	
}
