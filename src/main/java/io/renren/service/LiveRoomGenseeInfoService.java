package io.renren.service;

import java.util.List;
import java.util.Map;

import io.renren.entity.LiveRoomGenseeInfoEntity;

public interface LiveRoomGenseeInfoService {
	List<LiveRoomGenseeInfoEntity> queryList(Map<String,Object> map);
	LiveRoomGenseeInfoEntity queryObjectById(Long id);
	int queryTotal(Map<String,Object> map);
}
