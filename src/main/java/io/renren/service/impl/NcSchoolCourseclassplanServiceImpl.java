package io.renren.service.impl;

import io.renren.entity.NcSchoolCourseclassplanLiveEntity;
import io.renren.pojo.NcUserListPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.NcSchoolCourseclassplanDao;
import io.renren.entity.NcSchoolCourseclassplanEntity;
import io.renren.service.NcSchoolCourseclassplanService;


@Service("ncSchoolCourseclassplanService")
public class NcSchoolCourseclassplanServiceImpl implements NcSchoolCourseclassplanService {
	@Autowired
	private NcSchoolCourseclassplanDao ncSchoolCourseclassplanDao;
	
	@Override
	public NcSchoolCourseclassplanEntity queryObject(Map<String, Object> map){
		return ncSchoolCourseclassplanDao.queryObject(map);
	}
	
	@Override
	public List<NcSchoolCourseclassplanEntity> queryList(Map<String, Object> map){
		return ncSchoolCourseclassplanDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ncSchoolCourseclassplanDao.queryTotal(map);
	}
	
	@Override
	public void save(NcSchoolCourseclassplanEntity ncSchoolCourseclassplan){
		ncSchoolCourseclassplanDao.save(ncSchoolCourseclassplan);
	}
	
	@Override
	public void update(NcSchoolCourseclassplanEntity ncSchoolCourseclassplan){
		ncSchoolCourseclassplanDao.update(ncSchoolCourseclassplan);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		ncSchoolCourseclassplanDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		ncSchoolCourseclassplanDao.deleteBatch(map);
	}

    @Override
    public boolean isExistByClassplanId(Map paramMap) {
        return ncSchoolCourseclassplanDao.isExistByClassplanId(paramMap) > 0 ? true :false;
    }

    @Override
    public List<NcSchoolCourseclassplanLiveEntity> queryDetailList(Map<String, Object> map) {
        return ncSchoolCourseclassplanDao.queryDetailList(map);
    }

    @Override
    public int queryDetailTotal(Map<String, Object> map) {
        return ncSchoolCourseclassplanDao.queryDetailTotal(map);
    }

    @Override
    public List<NcUserListPOJO> queryUserList(Map<String, Object> map) {
        return ncSchoolCourseclassplanDao.queryUserList(map);
    }

    @Override
    public int queryUserListTotal(Map<String, Object> map) {
        return ncSchoolCourseclassplanDao.queryUserListTotal(map);
    }


}
