package com.hq.courses.service.impl;

import com.hq.courses.dao.CsKnowledgeDao;
import com.hq.courses.dao.CsKnowledgeRecordDao;
import com.hq.courses.entity.CsKnowledgeEntity;
import com.hq.courses.entity.CsKnowledgeRecordEntity;
import com.hq.courses.pojo.CsKnowledgeRecordPOJO;
import com.hq.courses.service.CsKnowledgeRecordService;
import io.renren.entity.SysUserEntity;
import io.renren.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.Kernel;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.utils.Constant;



@Service("csKnowledgeRecordService")
public class CsKnowledgeRecordServiceImpl implements CsKnowledgeRecordService {
	@Autowired
	private CsKnowledgeRecordDao csKnowledgeRecordDao;
	@Autowired
    private CsKnowledgeDao csKnowledgeDao;
	@Autowired
    private SysUserService sysUserService;
	
	@Override
	public CsKnowledgeRecordEntity queryObject(Map<String, Object> map){
		return csKnowledgeRecordDao.queryObject(map);
	}
	
	@Override
	public List<CsKnowledgeRecordEntity> queryList(Map<String, Object> map){
		return csKnowledgeRecordDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return csKnowledgeRecordDao.queryTotal(map);
	}
	
	@Override
	public void save(CsKnowledgeRecordEntity csKnowledgeRecord){
		csKnowledgeRecordDao.save(csKnowledgeRecord);
	}
	
	@Override
	public void update(CsKnowledgeRecordEntity csKnowledgeRecord){
		csKnowledgeRecordDao.update(csKnowledgeRecord);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		csKnowledgeRecordDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		csKnowledgeRecordDao.deleteBatch(map);
	}

    @Override
    public void saveOldKnowledge(Long knowledgeId) {
        CsKnowledgeEntity csKnowledgeEntity = csKnowledgeDao.queryObject(knowledgeId);
        CsKnowledgeRecordEntity recordEntity = CsKnowledgeRecordEntity.getRecordEntityByEntity(csKnowledgeEntity);
        csKnowledgeRecordDao.save(recordEntity);
    }

    @Override
    public int queryPojoTotal(Map<String, Object> map) {
        return csKnowledgeRecordDao.queryPojoTotal(map);
    }

    @Override
    public List<CsKnowledgeRecordPOJO> queryPojoList(Map<String, Object> map) {
        List<CsKnowledgeRecordPOJO> pojoList = csKnowledgeRecordDao.queryPojoList(map);
        if (pojoList != null && pojoList.size() >0){
            for (CsKnowledgeRecordPOJO csKnowledgeRecordPOJO : pojoList) {
                Map<String, Object> queryProductMap = new HashMap<>();
                queryProductMap.put("teacherId",csKnowledgeRecordPOJO.getUserId());
                SysUserEntity sysUserEntity = sysUserService.queryTeacherById(queryProductMap);
                if (sysUserEntity != null){
                    csKnowledgeRecordPOJO.setUserName(sysUserEntity.getNickName());
                }
            }
        }
        return pojoList;
    }


}
