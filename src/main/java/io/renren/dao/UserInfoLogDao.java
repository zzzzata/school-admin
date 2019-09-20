package io.renren.dao;

import org.apache.ibatis.annotations.Mapper;

import io.renren.entity.UserInfoLogEntity;
  
/**
 * 用于保存修改用户的日志
 * @author lintf
 *
 */
@Mapper
public interface UserInfoLogDao extends BaseDao<UserInfoLogEntity> {
	
}
