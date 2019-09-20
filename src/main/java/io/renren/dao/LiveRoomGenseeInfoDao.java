package io.renren.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import io.renren.entity.LiveRoomGenseeInfoEntity;

public interface LiveRoomGenseeInfoDao {
	List<LiveRoomGenseeInfoEntity> queryList(Map<String,Object> map);
	LiveRoomGenseeInfoEntity queryObjectById(@Param("id")Long id);
	int queryTotal(Map<String,Object> map);
}
