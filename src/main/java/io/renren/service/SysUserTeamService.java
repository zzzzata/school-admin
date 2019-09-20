package io.renren.service;

import io.renren.entity.SysUserEntity;
import io.renren.entity.SysUserTeamEntity;

import java.util.List;
import java.util.Map;

/**
 * @author hewanxian
 * @version 1.0
 * @date 2019/6/15 9:21
 */
public interface SysUserTeamService {

    List<SysUserTeamEntity> queryList(Map<String, Object> map);

    void save(SysUserTeamEntity sysUserTeamEntity);

    void update(SysUserTeamEntity sysUserTeamEntity);

    /**
     * 根据userId删除相应用户关联团队
     * @param userId
     */
    void deleteByUserId(Long userId);

    /**
     * 根据user查询其所属团队
     * @param userEntity
     * @return
     */
    List<SysUserTeamEntity> queryTeamByUser(SysUserEntity userEntity);

    /**
     * 根据userId更新，先删除相关userId的数据，再insert
     * @param user
     */
    void updateByUser(SysUserEntity user);

    /**
     * 根据user保存
     * @param user
     */
    void saveByUser(SysUserEntity user);

    int queryTotal(Map<String, Object> map);

    List<SysUserTeamEntity> queryTeamList(Map<String, Object> map);

}
