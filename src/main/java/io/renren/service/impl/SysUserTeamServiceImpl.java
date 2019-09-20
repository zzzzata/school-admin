package io.renren.service.impl;

import io.renren.dao.SysTeamDao;
import io.renren.dao.SysUserTeamDao;
import io.renren.entity.SysUserEntity;
import io.renren.entity.SysUserTeamEntity;
import io.renren.service.SysUserTeamService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hewanxian
 * @version 1.0
 * @date 2019/6/15 9:23
 */
@Service("sysUserTeamService")
public class SysUserTeamServiceImpl implements SysUserTeamService {

    @Autowired
    private SysUserTeamDao sysUserTeamDao;

    @Autowired
    private SysTeamDao sysTeamDao;

    @Override
    public List<SysUserTeamEntity> queryList(Map<String, Object> map) {
        List<SysUserTeamEntity> list = sysUserTeamDao.queryList(map);
        return list;
    }

    @Override
    public void save(SysUserTeamEntity sysUserTeamEntity) {
        sysUserTeamDao.save(sysUserTeamEntity);
    }

    @Override
    public void update(SysUserTeamEntity sysUserTeamEntity) {
        sysUserTeamDao.update(sysUserTeamEntity);
    }

    @Override
    public void deleteByUserId(Long userId) {
        sysUserTeamDao.deleteByUserId(userId);
    }

    @Override
    public List<SysUserTeamEntity> queryTeamByUser(SysUserEntity userEntity) {
        Map qm = new HashMap();
        qm.put("userId",userEntity.getUserId());
        List<SysUserTeamEntity> entityList = sysUserTeamDao.queryList(qm);
        List<Long> idList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for(SysUserTeamEntity s:entityList){
            idList.add(s.getTeamId());
            nameList.add(sysTeamDao.queryObject(s.getTeamId()).getName());
        }
        userEntity.setTeamIdList(idList);
        userEntity.setTeamNameList(nameList);
        userEntity.setTeamTempList(entityList);
        return entityList;
    }

    @Override
    @Transactional
    public void updateByUser(SysUserEntity user) {
        sysUserTeamDao.deleteByUserId(user.getUserId());
        saveByUser(user);
    }

    @Override
    @Transactional
    public void saveByUser(SysUserEntity user) {
        List<Long> idList = user.getTeamIdList();
        SysUserTeamEntity entity = new SysUserTeamEntity();
        entity.setUserId(user.getUserId());
        for(Long l :idList){
            entity.setTeamId(l);
            sysUserTeamDao.save(entity);
        }
    }

    @Override
    public int queryTotal(Map<String, Object> map){
        return sysUserTeamDao.queryTotal(map);
    }

    @Override
    public List<SysUserTeamEntity> queryTeamList(Map<String, Object> map) {
        return sysUserTeamDao.queryTeamList(map);
    }

}
