package io.renren.service.impl;

import io.renren.dao.CourseClassplanChangeRecordDao;
import io.renren.dao.CourseClassplanLivesChangeRecordDao;
import io.renren.dao.CourseClassplanLivesDao;
import io.renren.entity.*;
import io.renren.pojo.classplan.ClassplanLivePOJO;
import io.renren.service.*;
import io.renren.utils.*;
import io.renren.utils.http.HttpClientUtil4_3;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("courseClassplanLivesChangeRecordService")
public class CourseClassplanLivesChangeRecordServiceImpl implements CourseClassplanLivesChangeRecordService {
	@Autowired
	private CourseClassplanLivesChangeRecordDao courseClassplanLivesChangeRecordDao;
	@Autowired
	private CourseClassplanChangeRecordDao courseClassplanChangeRecordDao;

	@Override
	public void save(CourseClassplanLivesChangeRecordEntity courseClassplanLives){
		courseClassplanLivesChangeRecordDao.save(courseClassplanLives);
	}

	/**
	 * 根据排课计划id查询直播明细
	 * @param classplanId
	 * @param versionNo
	 * @return
	 */
	@Override
	public List<CourseClassplanLivesEntity> queryEntityList(String classplanId, int versionNo) {
		return courseClassplanLivesChangeRecordDao.queryEntityList(classplanId,versionNo);
	}

    @Override
    public List<CourseClassplanLivesChangeRecordEntity> queryPojoList(Map<String, Object> map2) {
        List<CourseClassplanLivesChangeRecordEntity> pll = courseClassplanLivesChangeRecordDao.queryPojoList(map2);
        if(null != pll && pll.size() >0){
            for (CourseClassplanLivesChangeRecordEntity classplanLivePOJO : pll) {
                classplanLivePOJO.setLiveClassTypeIds(ClassTypeUtils.out(classplanLivePOJO.getLiveClassTypeIds()));
            }
        }
        if(pll.size()>0){
        	for(CourseClassplanLivesChangeRecordEntity o : pll){
				if(StringUtils.isNotBlank(o.getAssistantTeacherIds())){
					List<Map> list = courseClassplanChangeRecordDao
							.queryAssistantTeacherList(o.getAssistantTeacherIds().split(","));
					if(list.size()>0){
						Map asst = new com.alibaba.fastjson.JSONObject();
						for(Map  i : list){
							asst.put(MapUtils.getString(i,"id"),MapUtils.getString(i,"name"));
						}
						o.setAsst(((com.alibaba.fastjson.JSONObject) asst).toJSONString());
					}
				}
			}
		}
        return pll;
    }

}
