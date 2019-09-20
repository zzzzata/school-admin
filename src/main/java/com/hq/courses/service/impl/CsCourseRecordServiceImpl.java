package com.hq.courses.service.impl;

import com.hq.courses.dao.CsCourseDao;
import com.hq.courses.dao.CsCourseRecordDao;
import com.hq.courses.entity.CsCourseEntity;
import com.hq.courses.entity.CsCourseRecordEntity;
import com.hq.courses.pojo.CsCourseRecordPOJO;
import com.hq.courses.service.CsCourseRecordService;
import io.renren.entity.SysDeptEntity;
import io.renren.entity.SysProductEntity;
import io.renren.entity.SysUserEntity;
import io.renren.service.SysDeptService;
import io.renren.service.SysProductService;
import io.renren.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("csCourseRecordService")
public class CsCourseRecordServiceImpl implements CsCourseRecordService {

	@Autowired
	private CsCourseRecordDao csCourseRecordDao;
    @Autowired
    private CsCourseDao csCourseDao;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysProductService sysProductService;
    @Autowired
    private SysUserService sysUserService;
	
	@Override
	public CsCourseRecordEntity queryObject(Map<String, Object> map){
		return csCourseRecordDao.queryObject(map);
	}
	
	@Override
	public List<CsCourseRecordPOJO> queryList(Map<String, Object> map){
	    List<CsCourseRecordPOJO> pojoList = new ArrayList<>();
        List<CsCourseRecordEntity> entityList = csCourseRecordDao.queryList(map);
        if (entityList != null && entityList.size() > 0){
            for (CsCourseRecordEntity recordEntity : entityList) {
                CsCourseRecordPOJO pojo =getPojoByEntity(recordEntity);
                pojoList.add(pojo);
            }
        }
        return pojoList;
	}

    private CsCourseRecordPOJO getPojoByEntity(CsCourseRecordEntity recordEntity) {
        CsCourseRecordPOJO pojo = new CsCourseRecordPOJO();
        pojo.setCourseId(recordEntity.getCourseId());
        pojo.setCourseName(recordEntity.getCourseName());
        pojo.setCourseNo(recordEntity.getCourseNo());
        pojo.setCourseRecordId(recordEntity.getCourseRecordId());
        //部门
        pojo.setDeptId(recordEntity.getDeptId());
        SysDeptEntity sysDeptEntity = sysDeptService.queryObject(recordEntity.getDeptId());
        if (sysDeptEntity != null){
            pojo.setDeptName(sysDeptEntity.getName());
        }
        //产品线
        pojo.setProductId(recordEntity.getProductId());
        Map<String, Object> queryProductMap = new HashMap<>();
        queryProductMap.put("productId", recordEntity.getProductId());
        SysProductEntity sysProductEntity = this.sysProductService.queryObject(queryProductMap);
        if (sysProductEntity != null){
            pojo.setProductName(sysProductEntity.getProductName());
        }
        pojo.setUpdateTime(recordEntity.getUpdateTime());
        //修改人
        pojo.setUserId(recordEntity.getCreateUserId());
        queryProductMap.put("teacherId",recordEntity.getCreateUserId());
        SysUserEntity sysUserEntity = sysUserService.queryTeacherById(queryProductMap);
        if (sysUserEntity != null){
            pojo.setUserName(sysUserEntity.getNickName());
        }
        return pojo;
    }

    @Override
	public int queryTotal(Map<String, Object> map){
		return csCourseRecordDao.queryTotal(map);
	}
	
	@Override
	public void save(CsCourseRecordEntity csCourseRecord){
		csCourseRecordDao.save(csCourseRecord);
	}
	
	@Override
	public void update(CsCourseRecordEntity csCourseRecord){
		csCourseRecordDao.update(csCourseRecord);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		csCourseRecordDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		csCourseRecordDao.deleteBatch(map);
	}

    @Override
    public void saveOldCsCourse(Long courseId) {
        CsCourseEntity csCourseEntity = csCourseDao.queryObject(courseId);
        CsCourseRecordEntity recordEntityByEntity = CsCourseRecordEntity.getRecordEntityByEntity(csCourseEntity);
        csCourseRecordDao.save(recordEntityByEntity);
    }


}
