package com.hq.courses.service.impl;

import com.hq.courses.dao.CsCourseLiveDetailsRecordDao;
import com.hq.courses.entity.CsCourseLiveDetailsEntity;
import com.hq.courses.entity.CsCourseLiveDetailsRecordEntity;
import com.hq.courses.service.CsCourseLiveDetailsRecordService;
import com.hq.courses.service.CsCourseLiveDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service("csCourseLiveDetailsRecordService")
public class CsCourseLiveDetailsRecordServiceImpl implements CsCourseLiveDetailsRecordService {
	@Autowired
	private CsCourseLiveDetailsRecordDao csCourseLiveDetailsRecordDao;
	@Autowired
	private CsCourseLiveDetailsService csCourseLiveDetailsService;
	
	@Override
	public CsCourseLiveDetailsRecordEntity queryObject(Map<String, Object> map){
		return csCourseLiveDetailsRecordDao.queryObject(map);
	}
	
	@Override
	public List<CsCourseLiveDetailsRecordEntity> queryList(Map<String, Object> map){
		return csCourseLiveDetailsRecordDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return csCourseLiveDetailsRecordDao.queryTotal(map);
	}
	
	@Override
	public void save(Long liveId,Long updatePerson){
		Map<String,Object> map = new HashMap<>();
		map.put("liveId",liveId);
		CsCourseLiveDetailsEntity csCourseLiveDetails = csCourseLiveDetailsService.queryObject(map);
		csCourseLiveDetails.setUpdatePerson(updatePerson);
		csCourseLiveDetailsRecordDao.saveRecord(csCourseLiveDetails);
	}
	
	@Override
	public void update(CsCourseLiveDetailsRecordEntity csCourseLiveDetailsRecord){
		csCourseLiveDetailsRecordDao.update(csCourseLiveDetailsRecord);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		csCourseLiveDetailsRecordDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		csCourseLiveDetailsRecordDao.deleteBatch(map);
	}
	
	
}
