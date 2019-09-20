package io.renren.service.impl;

import io.renren.dao.ColdStartingDao;
import io.renren.dao.SysProductDao;
import io.renren.entity.ColdStartingEntity;
import io.renren.pojo.ColdStartingPOJO;
import io.renren.service.ColdStartingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;





@Service("coldStartingService")
public class ColdStartingServiceImpl implements ColdStartingService {
	@Autowired
	private ColdStartingDao coldStartingDao;
	@Autowired
	private SysProductDao sysProductDao;
	
	@Override
	public ColdStartingEntity queryObject(Long id){
		return coldStartingDao.queryObject(id);
	}
	
	@Override
	public List<ColdStartingEntity> queryList(Map<String, Object> map){
		return coldStartingDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return coldStartingDao.queryTotal(map);
	}
	
	@Override
	public void save(ColdStartingEntity coldStarting){
		coldStartingDao.save(coldStarting);
	}
	
	@Override
	public void update(ColdStartingEntity coldStarting){
		coldStartingDao.update(coldStarting);
	}
	
	@Override
	public void delete(Long id){
		coldStartingDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		coldStartingDao.deleteBatch(ids);
	}

    @Override
    public void resume(Long[] ids) {
        Map<String,Object> map = new HashMap();
        map.put("list",ids);
        coldStartingDao.resume(map);
    }

    @Override
    public void pause(Long[] ids) {
        Map<String,Object> map = new HashMap();
        map.put("list",ids);
        coldStartingDao.pause(map);
    }

	@Override
	public List<ColdStartingPOJO> queryPojoList(Map<String, Object> map) {
		List<ColdStartingPOJO> resultList = this.coldStartingDao.queryPojoList(map);
		return resultList;
	}

	@Override
	public ColdStartingPOJO queryPojo(Long id) {
		return coldStartingDao.queryPojo(id);
	}

}
