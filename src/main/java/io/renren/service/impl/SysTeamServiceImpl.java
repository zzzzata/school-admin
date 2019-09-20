package io.renren.service.impl;

import io.renren.dao.SysTeamDao;
import io.renren.entity.SysTeamEntity;
import io.renren.service.SysTeamService;
import io.renren.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Date;

/**
 * @author hewanxian
 * @version 1.0
 * @date 2019/6/11 15:56
 */
@Service("sysTeamService")
public class SysTeamServiceImpl implements SysTeamService {

    @Autowired
    private SysTeamDao sysTeamDao;

    @Override
    public Object queryById(Long id){
        return sysTeamDao.queryById(id);
    }

    @Override
    public SysTeamEntity queryObject(Long id){
        return sysTeamDao.queryObject(id);
    }

    @Override
    public List<SysTeamEntity> queryList(Map<String, Object> map){
        return sysTeamDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map){
        return sysTeamDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(SysTeamEntity sysTeam){
        sysTeam.setCreateTime(new Date());
        sysTeam.setUpdateTime(sysTeam.getCreateTime());
        sysTeam.setCreatorId(ShiroUtils.getUserId());
        sysTeam.setEditorId(sysTeam.getCreatorId());
        sysTeamDao.save(sysTeam);
    }

    @Override
    @Transactional
    public void update(SysTeamEntity sysTeam){
        sysTeam.setUpdateTime(new Date());
        sysTeam.setEditorId(ShiroUtils.getUserId());
        sysTeamDao.update(sysTeam);
    }

    @Override
    public void delete(Map<String, Object> map){
        sysTeamDao.delete(map);
    }

    @Override
    public void deleteBatch(Map<String, Object> map){
        sysTeamDao.deleteBatch(map);
    }

    @Override
    @Transactional
    public void updateTreeByStatus(Long id,int status) {
        SysTeamEntity sysTeam = new SysTeamEntity();
        sysTeam.setId(id);
        List<SysTeamEntity> list = new ArrayList<>();
        list.add(sysTeam);
        //查询及其所有的子节点
        List<SysTeamEntity> sysTeamEntityList = this.queryTreeByParentId(id,list);
        //更新列表中的团队状态，0-为停用，1-为启用
        for(SysTeamEntity s:sysTeamEntityList){
            if(s.getId() != null){
                sysTeam.setId(s.getId());
                sysTeam.setStatus(status);
                update(sysTeam);
            }
        }
    }

    /**
     * 查询传入id的所有子节点
     * @param parentId
     * @param list
     * @return
     */
    private List<SysTeamEntity> queryTreeByParentId(Long parentId,List<SysTeamEntity> list){
        if(parentId == null) return list;
        //查询根据parentId获取列表
        List<SysTeamEntity> teamEntityList = sysTeamDao.queryListByParentId(parentId);
        list.addAll(teamEntityList);
        //递归查询，其获取列表的子节点
        for(SysTeamEntity s:teamEntityList){
            queryTreeByParentId(s.getId(),list);
        }
        return list;
    }

    public Integer getLevel(Long rootId){
        int level = 0;
        Map<String,Object> sysTeamMap = sysTeamDao.queryById(rootId);
        while(sysTeamMap!=null && !sysTeamMap.isEmpty()){
            level++;
            sysTeamMap = sysTeamDao.queryById((Long)sysTeamMap.get("parentId"));
        }
        return level;
    }

    @Override
    public ArrayList<Long> getChildrenIdList(Map<String, Object> map) {
        return sysTeamDao.getChildrenIdList(map);
    }

}
