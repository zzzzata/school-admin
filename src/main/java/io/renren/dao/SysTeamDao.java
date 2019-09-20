package io.renren.dao;

import io.renren.entity.SysTeamEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hewanxian
 * @version 1.0
 * @date 2019/6/11 15:59
 */
public interface SysTeamDao extends BaseDao<SysTeamEntity> {

    Map queryById(Long id);

    List<SysTeamEntity> queryListByParentId(Long parentId);

    String getParList(Map<String,Object> map);

    ArrayList<Long> getChildrenIdList(Map<String,Object> map);

}
