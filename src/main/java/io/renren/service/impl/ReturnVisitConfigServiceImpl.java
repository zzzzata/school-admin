package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.dao.ReturnVisitConfigDao;
import io.renren.entity.ReturnVisitConfigEntity;
import io.renren.service.ReturnVisitConfigService;
import io.renren.utils.Constant;



@Service("returnVisitConfigService")
public class ReturnVisitConfigServiceImpl implements ReturnVisitConfigService {
	@Autowired
	private ReturnVisitConfigDao returnVisitConfigDao;
	
	@Override
	public ReturnVisitConfigEntity queryObject(Map<String, Object> map){
		return returnVisitConfigDao.queryObject(map);
	}
	
	@Override
	public List<ReturnVisitConfigEntity> queryList(Map<String, Object> map){
		return returnVisitConfigDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return returnVisitConfigDao.queryTotal(map);
	}
	
	@Override
	public void save(ReturnVisitConfigEntity returnVisitConfig){
		returnVisitConfigDao.save(returnVisitConfig);
	}
	
	@Override
	public void update(ReturnVisitConfigEntity returnVisitConfig){
		returnVisitConfigDao.update(returnVisitConfig);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		returnVisitConfigDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		returnVisitConfigDao.deleteBatch(map);
	}
	
	
}
