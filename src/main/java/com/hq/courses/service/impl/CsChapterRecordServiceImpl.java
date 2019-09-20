package com.hq.courses.service.impl;

import com.hq.courses.dao.CsChapterDao;
import com.hq.courses.dao.CsChapterRecordDao;
import com.hq.courses.entity.CsChapterEntity;
import com.hq.courses.entity.CsChapterRecordEntity;
import com.hq.courses.pojo.CsChapterRecordPOJO;
import com.hq.courses.service.CsChapterRecordService;
import io.renren.entity.SysUserEntity;
import io.renren.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




@Service("csChapterRecordService")
public class CsChapterRecordServiceImpl implements CsChapterRecordService {
	@Autowired
	private CsChapterRecordDao csChapterRecordDao;
	@Autowired
    private CsChapterDao csChapterDao;
    @Autowired
    private SysUserService sysUserService;
	
	@Override
	public CsChapterRecordEntity queryObject(Map<String, Object> map){
		return csChapterRecordDao.queryObject(map);
	}

    @Override
    public List<CsChapterRecordEntity> queryList(Map<String, Object> map) {
        return csChapterRecordDao.queryList(map);
    }

    @Override
	public List<CsChapterRecordPOJO> queryPojoList(Map<String, Object> map){
        List<CsChapterRecordPOJO> pojoList = csChapterRecordDao.queryPojoList(map);
        if (pojoList !=null && pojoList.size() > 0){
            for (CsChapterRecordPOJO csChapterRecordPOJO : pojoList) {
                Map<String, Object> queryProductMap = new HashMap<>();
                queryProductMap.put("teacherId",csChapterRecordPOJO.getUserId());
                SysUserEntity sysUserEntity = sysUserService.queryTeacherById(queryProductMap);
                if (sysUserEntity != null){
                    csChapterRecordPOJO.setUserName(sysUserEntity.getNickName());
                }
            }
        }
        return pojoList;
	}

    @Override
    public int queryPojoTotal(Map<String, Object> map) {
        return csChapterRecordDao.queryPojoTotal(map);
    }

	@Override
	public int queryTotal(Map<String, Object> map){
		return csChapterRecordDao.queryTotal(map);
	}
	
	@Override
	public void save(CsChapterRecordEntity csChapterRecord){
		csChapterRecordDao.save(csChapterRecord);
	}
	
	@Override
	public void update(CsChapterRecordEntity csChapterRecord){
		csChapterRecordDao.update(csChapterRecord);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		csChapterRecordDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		csChapterRecordDao.deleteBatch(map);
	}

    @Override
    public void saveOldChapter(Long chapterId) {
        CsChapterEntity csChapterEntity = csChapterDao.queryObject(chapterId);
        CsChapterRecordEntity recordEntity = CsChapterRecordEntity.getRecordEntityByEntity(csChapterEntity);
        csChapterRecordDao.save(recordEntity);
    }


}
