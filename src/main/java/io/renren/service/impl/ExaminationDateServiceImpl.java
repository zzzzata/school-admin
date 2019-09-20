package io.renren.service.impl;

import io.renren.pojo.ExaminationDatePOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.dao.ExaminationDateDao;
import io.renren.entity.ExaminationDateEntity;
import io.renren.service.ExaminationDateService;
import io.renren.utils.Constant;



@Service("examinationDateService")
public class ExaminationDateServiceImpl implements ExaminationDateService {
	@Autowired
	private ExaminationDateDao examinationDateDao;
	
	@Override
	public ExaminationDateEntity queryObject(Map<String, Object> map){
		return examinationDateDao.queryObject(map);
	}
	
	@Override
	public List<ExaminationDatePOJO> queryList(Map<String, Object> map){
		return examinationDateDao.queryListPOJO(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return examinationDateDao.queryTotal(map);
	}
	
	@Override
	public void save(ExaminationDateEntity examinationDate){
		examinationDateDao.save(examinationDate);
	}
	
	@Override
	public void update(ExaminationDateEntity examinationDate){
		examinationDateDao.update(examinationDate);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		examinationDateDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		examinationDateDao.deleteBatch(map);
	}
	
	
}
