package com.hq.courses.service.impl;

import com.hq.courses.dao.CsSectionDao;
import com.hq.courses.dao.CsSectionRecordDao;
import com.hq.courses.entity.CsSectionRecordEntity;
import com.hq.courses.pojo.CsSectionPOJO;
import com.hq.courses.pojo.CsSectionRecordPOJO;
import com.hq.courses.service.CsSectionRecordService;
import io.renren.entity.SysUserEntity;
import io.renren.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("csSectionRecordService")
public class CsSectionRecordServiceImpl implements CsSectionRecordService {
	@Autowired
	private CsSectionRecordDao csSectionRecordDao;
	@Autowired
	private CsSectionDao csSectiondDao;
	@Autowired
    private SysUserService sysUserService;
	
	@Override
	public CsSectionRecordEntity queryObject(Map<String, Object> map){
		return csSectionRecordDao.queryObject(map);
	}
	
	@Override
	public List<CsSectionRecordEntity> queryList(Map<String, Object> map){
		return csSectionRecordDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return csSectionRecordDao.queryTotal(map);
	}
	
	@Override
	public void save(CsSectionRecordEntity csSectionRecord){
		csSectionRecordDao.save(csSectionRecord);
	}
	
	@Override
	public void update(CsSectionRecordEntity csSectionRecord){
		csSectionRecordDao.update(csSectionRecord);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		csSectionRecordDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		csSectionRecordDao.deleteBatch(map);
	}

    @Override
    public void saveOldSection(Long sectionId) {
        CsSectionPOJO csSectionPOJO = csSectiondDao.queryObject(sectionId);
        CsSectionRecordEntity recordEntity = CsSectionRecordEntity.getRecordEntityByPOJO(csSectionPOJO);
        csSectionRecordDao.save(recordEntity);
    }

    @Override
    public int queryPojoTotal(Map<String, Object> map) {
        return csSectionRecordDao.queryPojoTotal(map);
    }

    @Override
    public List<CsSectionRecordPOJO> queryPojoList(Map<String, Object> map) {
        List<CsSectionRecordPOJO> pojoList = csSectionRecordDao.queryPojoList(map);
        if (pojoList != null && pojoList.size() > 0){
            for (CsSectionRecordPOJO csSectionRecordPOJO : pojoList) {
                Map<String, Object> queryProductMap = new HashMap<>();
                queryProductMap.put("teacherId",csSectionRecordPOJO.getUserId());
                SysUserEntity sysUserEntity = sysUserService.queryTeacherById(queryProductMap);
                if (sysUserEntity != null){
                    csSectionRecordPOJO.setUserName(sysUserEntity.getNickName());
                }
            }
        }
        return pojoList;
    }


}
