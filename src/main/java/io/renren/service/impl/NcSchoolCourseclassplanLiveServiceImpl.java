package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.dao.NcSchoolCourseclassplanLiveDao;
import io.renren.entity.NcSchoolCourseclassplanLiveEntity;
import io.renren.service.NcSchoolCourseclassplanLiveService;
import io.renren.utils.Constant;



@Service("ncSchoolCourseclassplanLiveService")
public class NcSchoolCourseclassplanLiveServiceImpl implements NcSchoolCourseclassplanLiveService {
	@Autowired
	private NcSchoolCourseclassplanLiveDao ncSchoolCourseclassplanLiveDao;
	
	@Override
	public NcSchoolCourseclassplanLiveEntity queryObject(Map<String, Object> map){
		return ncSchoolCourseclassplanLiveDao.queryObject(map);
	}
	
	@Override
	public List<NcSchoolCourseclassplanLiveEntity> queryList(Map<String, Object> map){
		return ncSchoolCourseclassplanLiveDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ncSchoolCourseclassplanLiveDao.queryTotal(map);
	}
	
	@Override
	public void save(NcSchoolCourseclassplanLiveEntity ncSchoolCourseclassplanLive){
		ncSchoolCourseclassplanLiveDao.save(ncSchoolCourseclassplanLive);
	}
	
	@Override
	public void update(NcSchoolCourseclassplanLiveEntity ncSchoolCourseclassplanLive){
		ncSchoolCourseclassplanLiveDao.update(ncSchoolCourseclassplanLive);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		ncSchoolCourseclassplanLiveDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		ncSchoolCourseclassplanLiveDao.deleteBatch(map);
	}

    @Override
    public void updateByClassplanId(Map liveParamMap) {
        ncSchoolCourseclassplanLiveDao.updateByClassplanId(liveParamMap);
    }

    @Override
    public boolean isExistByClassplanLiveId(Map paramMap) {
        return ncSchoolCourseclassplanLiveDao.isExistByClassplanLiveId(paramMap) > 0 ? true : false;
    }


}
