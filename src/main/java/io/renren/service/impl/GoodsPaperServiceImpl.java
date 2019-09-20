package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.dao.GoodsPaperDao;
import io.renren.entity.GoodsPaperEntity;
import io.renren.service.GoodsPaperService;
import io.renren.utils.Constant;



@Service("goodsPaperService")
public class GoodsPaperServiceImpl implements GoodsPaperService {
	@Autowired
	private GoodsPaperDao goodsPaperDao;
	
	@Override
	public GoodsPaperEntity queryObject(Map<String, Object> map){
		return goodsPaperDao.queryObject(map);
	}
	
	@Override
	public List<GoodsPaperEntity> queryList(Map<String, Object> map){
		return goodsPaperDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return goodsPaperDao.queryTotal(map);
	}
	
	@Override
	public void save(GoodsPaperEntity goodsPaper){
		goodsPaperDao.save(goodsPaper);
	}
	
	@Override
	public void update(GoodsPaperEntity goodsPaper){
		goodsPaperDao.update(goodsPaper);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		goodsPaperDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		goodsPaperDao.deleteBatch(map);
	}

	@Override
	public int getCountByGoodIdAndPaperId(Long goodId, Long paperId) {
		return goodsPaperDao.getCountByGoodIdAndPaperId(goodId, paperId);
	}
	
	
}
