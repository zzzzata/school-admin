package io.renren.dao;

import io.renren.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:33
 */
public interface SysRoleDao extends BaseDao<SysRoleEntity> {

    List<Map<String,Object>> queryRoleName(Long[] roleIds);
}
