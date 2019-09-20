package io.renren.dao;

import io.renren.entity.SysUserTeamEntity;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * @author hewanxian
 * @version 1.0
 * @date 2019/6/14 17:10
 */
public interface SysUserTeamDao extends BaseMDao<SysUserTeamEntity> {

    void deleteByUserId(Long userId);

    List<SysUserTeamEntity> queryTeamList(Map<String, Object> map);
}

