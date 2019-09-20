package io.renren.service;

import io.renren.entity.SysTeamEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hewanxian
 * @version 1.0
 * @date 2019/6/11 15:54
 */
public interface SysTeamService {

    Object queryById(Long id);

    SysTeamEntity queryObject(Long id);

    List<SysTeamEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SysTeamEntity sysTeam);

    void update(SysTeamEntity sysTeam);

    void delete(Map<String, Object> map);

    void deleteBatch(Map<String, Object> map);

    void updateTreeByStatus(Long id,int status);

    Integer getLevel(Long id);


    ArrayList<Long> getChildrenIdList(Map<String, Object> map);
}
