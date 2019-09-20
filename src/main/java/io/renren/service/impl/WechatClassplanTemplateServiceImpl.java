package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.dao.WechatClassplanTemplateDao;
import io.renren.entity.WechatClassplanTemplateEntity;
import io.renren.service.WechatClassplanTemplateService;
import io.renren.utils.Constant;



@Service("wechatClassplanTemplateService")
public class WechatClassplanTemplateServiceImpl implements WechatClassplanTemplateService {
	@Autowired
	private WechatClassplanTemplateDao wechatClassplanTemplateDao;
	
	@Override
	public WechatClassplanTemplateEntity queryObject(Map<String, Object> map){
		return wechatClassplanTemplateDao.queryObject(map);
	}
	
	@Override
	public List<WechatClassplanTemplateEntity> queryList(Map<String, Object> map){
		return wechatClassplanTemplateDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return wechatClassplanTemplateDao.queryTotal(map);
	}
	
	@Override
	public void save(WechatClassplanTemplateEntity wechatClassplanTemplate) throws Exception {
	    //判断同一个排课是否已经存在
        Map<String,Object> map = new HashMap<>();
        map.put("id",wechatClassplanTemplate.getId());
        map.put("classplanId",wechatClassplanTemplate.getClassplanId());
        map.put("appid",wechatClassplanTemplate.getAppid());
        map.put("templateId",wechatClassplanTemplate.getTemplateId());
        map.put("type",0);
        boolean flag = wechatClassplanTemplateDao.isExistByClassplanId(map) > 0 ? true:false;
        if (!flag){
            wechatClassplanTemplate.setContent(wechatClassplanTemplate.getContent().replaceAll("\r|\n", ""));
            wechatClassplanTemplateDao.save(wechatClassplanTemplate);
        }else {
            throw new Exception("该排课已经存在");
        }
	}

	@Override
	public void update(WechatClassplanTemplateEntity wechatClassplanTemplate) throws Exception {
	    //判断同一个排课是否已经存在
        Map<String,Object> map = new HashMap<>();
        map.put("id",wechatClassplanTemplate.getId());
        map.put("classplanId",wechatClassplanTemplate.getClassplanId());
        map.put("appid",wechatClassplanTemplate.getAppid());
        map.put("templateId",wechatClassplanTemplate.getTemplateId());
        map.put("type",0);
        WechatClassplanTemplateEntity oldEntity = wechatClassplanTemplateDao.queryObject(map);
        int count = wechatClassplanTemplateDao.isExistByClassplanId(map);
        if (!oldEntity.getClassplanId().equals(wechatClassplanTemplate.getClassplanId()) && count > 0) {
            throw new Exception("该排课已经存在");
        }else {
            wechatClassplanTemplateDao.update(wechatClassplanTemplate);
        }
	}
	
	@Override
	public void delete(Map<String, Object> map){
		wechatClassplanTemplateDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		wechatClassplanTemplateDao.deleteBatch(map);
	}
	
	
}
