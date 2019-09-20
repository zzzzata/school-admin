package io.renren.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.LiveRoomGenseeInfoDao;
import io.renren.entity.LiveRoomGenseeInfoEntity;
import io.renren.service.LiveRoomGenseeInfoService;

@Service("liveRoomGenseeInfoService")
public class LiveRoomGenseeInfoServiceImpl implements LiveRoomGenseeInfoService {

	@Autowired
	LiveRoomGenseeInfoDao liveRoomGenseeInfoDao;
	
	@Override
	public List<LiveRoomGenseeInfoEntity> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return liveRoomGenseeInfoDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return liveRoomGenseeInfoDao.queryTotal(map);
	}

	@Override
	public LiveRoomGenseeInfoEntity queryObjectById(Long id) {
		// TODO Auto-generated method stub
		return liveRoomGenseeInfoDao.queryObjectById(id);
	}

}
