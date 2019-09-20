package io.renren.service;

import io.renren.entity.AppUserChannelsEntity;

/**
 * 
 * @author Created by LiuHai 2018/02/05
 *
 */
public interface AppUserChannelsService {

	void save(AppUserChannelsEntity entity);
	/**
	 * 将用户绑定到public和本身的渠道，并且发送到消息系统
	 * @param userId
	 * @return
	 */
	int userInfoBindPublicChannel(long userId);

}
