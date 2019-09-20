package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.dao.AdaptiveGoodsDao;
import io.renren.entity.AdaptiveGoodsEntity;
import io.renren.service.AdaptiveGoodsService;
import io.renren.utils.Constant;



@Service("adaptiveGoodsService")
public class AdaptiveGoodsServiceImpl implements AdaptiveGoodsService {
	@Autowired
	private AdaptiveGoodsDao adaptiveGoodsDao;
	
	@Override
	public AdaptiveGoodsEntity queryObject(Map<String, Object> map){
		return adaptiveGoodsDao.queryObject(map);
	}
	
	@Override
	public List<AdaptiveGoodsEntity> queryList(Map<String, Object> map){
		return adaptiveGoodsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return adaptiveGoodsDao.queryTotal(map);
	}
	
	@Override
	public void save(AdaptiveGoodsEntity adaptiveGoods){
		adaptiveGoodsDao.save(adaptiveGoods);
	}
	
	@Override
	public void update(AdaptiveGoodsEntity adaptiveGoods){
		adaptiveGoodsDao.update(adaptiveGoods);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		adaptiveGoodsDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		adaptiveGoodsDao.deleteBatch(map);
	}
	
	
}
