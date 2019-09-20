package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.dao.WechatOpenidMobileDao;
import io.renren.entity.WechatOpenidMobileEntity;
import io.renren.service.WechatOpenidMobileService;
import io.renren.utils.Constant;



@Service("wechatOpenidMobileService")
public class WechatOpenidMobileServiceImpl implements WechatOpenidMobileService {
	@Autowired
	private WechatOpenidMobileDao wechatOpenidMobileDao;
	
	@Override
	public WechatOpenidMobileEntity queryObject(Map<String, Object> map){
		return wechatOpenidMobileDao.queryObject(map);
	}
	
	@Override
	public List<WechatOpenidMobileEntity> queryList(Map<String, Object> map){
		return wechatOpenidMobileDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return wechatOpenidMobileDao.queryTotal(map);
	}
	
	@Override
	public void save(WechatOpenidMobileEntity wechatOpenidMobile){
		wechatOpenidMobileDao.save(wechatOpenidMobile);
	}
	
	@Override
	public void update(WechatOpenidMobileEntity wechatOpenidMobile){
		wechatOpenidMobileDao.update(wechatOpenidMobile);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		wechatOpenidMobileDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		wechatOpenidMobileDao.deleteBatch(map);
	}

    @Override
    public int queryTotalByClassplanId(Map<String, Object> map) {
        return wechatOpenidMobileDao.queryTotalByClassplanId(map);
    }

    @Override
    public List<WechatOpenidMobileEntity> queryListByClassplanId(Map<String, Object> map) {
        return wechatOpenidMobileDao.queryListByClassplanId(map);
    }

    @Override
    public int queryTotalByClassId(Map<String, Object> map) {
        return wechatOpenidMobileDao.queryTotalByClassId(map);
    }

    @Override
    public List<WechatOpenidMobileEntity> queryListByClassId(Map<String, Object> map) {
        return wechatOpenidMobileDao.queryListByClassId(map);
    }


}
