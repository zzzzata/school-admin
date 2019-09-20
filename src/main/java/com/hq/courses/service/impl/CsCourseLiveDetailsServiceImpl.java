package com.hq.courses.service.impl;

import com.hq.courses.dao.CsCourseLiveDetailsDao;
import com.hq.courses.entity.CsCourseLiveDetailsEntity;
import com.hq.courses.service.CsCourseLiveDetailsService;
import io.renren.common.validator.Assert;
import io.renren.utils.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.utils.Constant;



@Service("csCourseLiveDetailsService")
public class CsCourseLiveDetailsServiceImpl implements CsCourseLiveDetailsService {
	@Autowired
	private CsCourseLiveDetailsDao csCourseLiveDetailsDao;
	
	@Override
	public CsCourseLiveDetailsEntity queryObject(Map<String, Object> map){
		return csCourseLiveDetailsDao.queryObject(map);
	}
	
	@Override
	public List<CsCourseLiveDetailsEntity> queryList(Map<String, Object> map){
		return csCourseLiveDetailsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return csCourseLiveDetailsDao.queryTotal(map);
	}
	
	@Override
	public void save(CsCourseLiveDetailsEntity csCourseLiveDetails){
		csCourseLiveDetailsDao.save(csCourseLiveDetails);
	}
	
	@Override
	public void update(CsCourseLiveDetailsEntity csCourseLiveDetails){
		csCourseLiveDetailsDao.update(csCourseLiveDetails);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		csCourseLiveDetailsDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		csCourseLiveDetailsDao.deleteBatch(map);
	}

	@Override
	public int queryByLiveName(String liveName) {
		return csCourseLiveDetailsDao.queryByLiveName(liveName);
	}


}
