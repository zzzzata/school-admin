package io.renren.service;

import java.util.Map;

import io.renren.entity.UsersEntity;
 

/**
 * 用于保存蓝鲸后台修改用户时的日志
 * @author lintf	
 *
 */
public interface UserInfoLogService {

	/**
	 * 修改用户时保存的日志
	 * @param oldUserMap
	 * @param newUserMap
	 */
	void userUpdateLog(Map<String, Object> oldUserMap, Map<String, Object> newUserMap);
	/**
	 * 创建用户时保存的日志
	 * @param user
	 */
	void userInsertLog( UsersEntity user); 
}
