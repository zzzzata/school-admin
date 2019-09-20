package io.renren.dao;

import org.springframework.stereotype.Repository;

import io.renren.entity.AppUserChannelsEntity;
 

/**
 * 
 * @author Created by LiuHai 2018/02/05
 *
 */ 
public interface AppUserChannelsDao {

	int save(AppUserChannelsEntity entity);

}
