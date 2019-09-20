package io.renren.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.renren.dao.CourseClassplanChangeRecordDao;
import io.renren.entity.*;
import io.renren.pojo.classplan.ClassplanLivePOJO;
import io.renren.pojo.classplan.ClassplanPOJO;
import io.renren.service.*;
import io.renren.utils.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Transactional(readOnly = true)
@Service("courseClassplanChangeRecordService")
public class CourseClassplanChangeRecordServiceImpl implements CourseClassplanChangeRecordService {
    @Autowired
    private CourseClassplanChangeRecordDao courseClassplanChangeRecordDao;
    @Autowired
    private CourseClassplanLivesService courseClassplanLivesService;
    @Autowired
    private TeachClassplanStudentfileService teachClassplanStudentfileService;
    @Autowired
    private CourseClassplanLivesChangeRecordService courseClassplanLivesChangeRecordService;
    @Autowired
    private CourseClassplanService courseClassplanService;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveApply(ClassplanPOJO courseClassplan) {
        deleteOldObject(courseClassplan.getClassplanId());
        //修改时间
        courseClassplan.setModifiedTime(new Date());
        CourseClassplanChangeRecordEntity entity = getEntityByPoJO(courseClassplan);
        //修改排课学员课程资料关系表
        //先删除中间表更新
        teachClassplanStudentfileService.delete(courseClassplan.getClassplanId());
        String materialIds1 = courseClassplan.getMaterialIds();
        if (!"".equals(materialIds1)){
            String[] materialIds = materialIds1.split(",");
            TeachClassplanStudentfileEntity teachClassplanStudentfile = new TeachClassplanStudentfileEntity();
            //删除之后再从新保存关系
            for (String materialId : materialIds) {
                teachClassplanStudentfile.setId(System.currentTimeMillis());
                teachClassplanStudentfile.setClassplanId(courseClassplan.getClassplanId());
                teachClassplanStudentfile.setTeachStudentFileId(Long.valueOf(materialId));
                teachClassplanStudentfile.setSysUserId(ShiroUtils.getUserId());
                teachClassplanStudentfileService.save(teachClassplanStudentfile);
            }
        }
        //遍历子表
        List<ClassplanLivePOJO> detatilList = courseClassplan.getDetailList();
        List<CourseClassplanLivesChangeRecordEntity> livesEntityList = getLivesEntityList(detatilList, courseClassplan);
        save(entity, livesEntityList);

    }


    @Override
    public void saveAuait(String courseClassplanId, Long userId) {
        CourseClassplanEntity courseClassplanEntity = courseClassplanService.queryObjectByClassplanId(courseClassplanId);
        CourseClassplanChangeRecordEntity entity = getEntityByEntity(courseClassplanEntity);
        entity.setAuditor(userId);
        entity.setAuditTime(new Date());
        List<CourseClassplanLivesChangeRecordEntity> livesEntityList = courseClassplanLivesService.queryEntityListByClassplanId(courseClassplanId);
        save(entity, livesEntityList);
    }

    public void save(CourseClassplanChangeRecordEntity entity, List<CourseClassplanLivesChangeRecordEntity> livesEntityList) throws ClassplanLiveException {
        int versionNo = findCId(entity.getClassplanId());
        entity.setVersionNo(versionNo);
        //保存主表
        courseClassplanChangeRecordDao.save(entity);
        Long changeId = entity.getChangeId();
        //子表保存
        if (null != livesEntityList && livesEntityList.size() > 0) {
            for (int i = 0; i < livesEntityList.size(); i++) {
                CourseClassplanLivesChangeRecordEntity liveEntity = livesEntityList.get(i);
                liveEntity.setVersionNo(versionNo);
                liveEntity.setCreationTime(new Date());
                //保存子表
                courseClassplanLivesChangeRecordService.save(liveEntity);
            }
        }
    }

    /**
     * 审核通过
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void accept(String classplanId, int versionNo, Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("classplanId", classplanId);
        map.put("isAudited", Constant.Status.RESUME.getValue());
        map.put("versionNo", versionNo);
        map.put("userId", userId);
        map.put("auditTime", new Date());
        CourseClassplanEntity en = queryObjectByClassplanId(classplanId, versionNo);
        List<CourseClassplanLivesEntity> detatilList = courseClassplanLivesChangeRecordService.queryEntityList(classplanId, versionNo);
        en.setIsAudited((int) Constant.Status.RESUME.getValue());
        en.setRemark(null);
        courseClassplanService.update(en, detatilList);
        courseClassplanChangeRecordDao.audited(map);

        //处理助教老师
        ClassplanPOJO courseClassplan = new ClassplanPOJO();
        courseClassplan.setClassplanId(en.getClassplanId());
        courseClassplan.setAtids(en.getAssistantTeacherIds());
        courseClassplan.setCreator(en.getCreator());
        List<ClassplanLivePOJO> list = new ArrayList<ClassplanLivePOJO>();
        for(CourseClassplanLivesEntity o : detatilList){
            ClassplanLivePOJO pojo = new ClassplanLivePOJO();
            pojo.setClassplanLiveId(o.getClassplanLiveId());
            pojo.setAtids(o.getAssistantTeacherIds());
            pojo.setCreatePerson(o.getCreatePerson());
            list.add(pojo);
        }
        courseClassplanService.saveAsst(courseClassplan,list,false);
    }

    /**
     * 审核未过
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void reject(String classplanId, int versionNo, Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("classplanId", classplanId);
        map.put("isAudited", Constant.Status.PAUSE.getValue());
        map.put("versionNo", versionNo);
        map.put("userId", userId);
        map.put("auditTime", new Date());
        courseClassplanChangeRecordDao.audited(map);
    }

    @Override
    public int findCId(String classplanId) {
        return courseClassplanChangeRecordDao.findCId(classplanId) + 1;
    }

    @Override
    public CourseClassplanEntity queryObjectByClassplanId(String classplanId, int versionNo) {
        return courseClassplanChangeRecordDao.queryObjectByClassplanId(classplanId, versionNo);
    }


    @Override
    public List<CourseClassplanChangeRecordEntity> queryList(Map<String, Object> map) {
        return courseClassplanChangeRecordDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return courseClassplanChangeRecordDao.queryTotal(map);
    }

    @Override
    public CourseClassplanChangeRecordEntity queryObject1(Map<String, Object> map1) {
        CourseClassplanChangeRecordEntity o = courseClassplanChangeRecordDao.queryObject1(map1);
        if(StringUtils.isNotBlank(o.getAssistantTeacherIds())){
            List<Map> list = courseClassplanChangeRecordDao
                    .queryAssistantTeacherList(o.getAssistantTeacherIds().split(","));
            if(list.size()>0){
                Map asst = new JSONObject();
                for(Map  i : list){
                    asst.put(MapUtils.getString(i,"id"),MapUtils.getString(i,"name"));
                }
                o.setAsst(((JSONObject) asst).toJSONString());
            }
        }
        return o;
    }

    @Override
    public void deleteOldObject(String classplanId) {
        courseClassplanChangeRecordDao.deleteOldObject(classplanId);
    }


    /**
     * 通过排行计划entity得到CourseClassplanChangeRecordEntity
     *
     * @param courseClassplanEntity
     * @return
     */
    public CourseClassplanChangeRecordEntity getEntityByEntity(CourseClassplanEntity courseClassplanEntity) {
        CourseClassplanChangeRecordEntity entity = new CourseClassplanChangeRecordEntity();
        if (courseClassplanEntity != null) {
            entity.setClassplanId(courseClassplanEntity.getClassplanId());
            entity.setClassplanName(courseClassplanEntity.getClassplanName());
            entity.setCourseId(courseClassplanEntity.getCourseId());
            entity.setClassplanLiveDetail(courseClassplanEntity.getClassplanLiveDetail());
            entity.setRemark(courseClassplanEntity.getRemark());
            entity.setStartTime(courseClassplanEntity.getStartTime());
            entity.setTeacherId(courseClassplanEntity.getTeacherId());
            entity.setTimetableId(courseClassplanEntity.getTimetableId());
            entity.setLiveRoomId(courseClassplanEntity.getLiveRoomId());
            entity.setStudioId(courseClassplanEntity.getStudioId());
            entity.setCreator(courseClassplanEntity.getCreator());
            entity.setCreationTime(courseClassplanEntity.getCreationTime());
            entity.setModifier(courseClassplanEntity.getModifier());
            entity.setModifiedTime(courseClassplanEntity.getModifiedTime());
            entity.setSchoolId(courseClassplanEntity.getSchoolId());
            entity.setDr(courseClassplanEntity.getDr());
            entity.setStatus(courseClassplanEntity.getStatus());
            entity.setIsAudited(1);//通过
            entity.setIsOpen(courseClassplanEntity.getIsOpen());
            entity.setMaterialId(courseClassplanEntity.getMaterialId());
            entity.setTs(courseClassplanEntity.getTs());
            entity.setProductId(courseClassplanEntity.getProductId());
            entity.setRecordType(0);
        }
        return entity;
    }

    /**
     * 通过排课计划pojo得到CourseClassplanChangeRecordEntity
     *
     * @param pojo
     * @return
     */
    public CourseClassplanChangeRecordEntity getEntityByPoJO(ClassplanPOJO pojo) {
        CourseClassplanChangeRecordEntity en = new CourseClassplanChangeRecordEntity();
        if (pojo != null) {
            en.setClassplanId(pojo.getClassplanId());
            en.setClassplanName(pojo.getClassplanName());
            en.setCourseId(pojo.getCourseId());
            en.setClassplanLiveDetail(pojo.getClassplanLiveDetail());
            en.setRemark(pojo.getRemark());
            en.setStartTime(pojo.getStartTime());
            en.setTeacherId(pojo.getTeacherId());
            en.setTimetableId(pojo.getTimetableId());
            en.setLiveRoomId(pojo.getLiveRoomId());
            en.setStudioId(pojo.getStudioId());
            en.setCreator(pojo.getCreator());
            en.setCreationTime(pojo.getCreationTime());
            en.setModifier(pojo.getModifier());
            en.setModifiedTime(pojo.getModifiedTime());
            en.setSchoolId(pojo.getSchoolId());
            en.setDr(pojo.getDr());
            en.setStatus(pojo.getStatus());
            en.setIsAudited(pojo.getIsAudited());
            en.setIsAudited(2);//待审核
            en.setIsOpen(pojo.getIsOpen());
            en.setMaterialId(pojo.getMaterialId());
            en.setTs(pojo.getTs());
            en.setProductId(pojo.getProductId());
            en.setRecordType(1);
            en.setAssistantTeacherIds(pojo.getAtids());
        }
        return en;
    }

    List<CourseClassplanLivesChangeRecordEntity> getLivesEntityList(List<ClassplanLivePOJO> detatilList, ClassplanPOJO courseClassplan) {
        List<CourseClassplanLivesChangeRecordEntity> list = null;
        if (detatilList != null && detatilList.size() > 0) {
            list = new ArrayList<CourseClassplanLivesChangeRecordEntity>();
            for (int i = 0; i < detatilList.size(); i++) {
                ClassplanLivePOJO pojo = detatilList.get(i);
                CourseClassplanLivesChangeRecordEntity en = new CourseClassplanLivesChangeRecordEntity();
                if (pojo != null) {
                    en.setClassplanLiveId(pojo.getClassplanLiveId());
                    en.setClassplanId(pojo.getClassplanId());
                    en.setClassplanLiveName(pojo.getClassplanLiveName());
                    en.setClassplanLiveTimeDetail(pojo.getClassplanLiveTimeDetail());
                    en.setDayTime(pojo.getDayTime());
                    en.setStartTime(pojo.getStartTime());
                    en.setEndTime(pojo.getEndTime());
                    en.setTimeBucket(pojo.getTimeBucket());
                    en.setLiveroomId(pojo.getLiveroomId());
                    en.setStudioId(pojo.getStudioId());
                    en.setBackUrl(pojo.getBackUrl());
                    BackUtils.setBackId(en);
                    en.setTeacherId(pojo.getTeacherId());
                    en.setLiveClassTypeIds(pojo.getLiveClassTypeIds());
                    en.setOrderNum(pojo.getOrderNum());
                    en.setCourseLiveDetailId(pojo.getCourseLiveDetailId());
                    en.setCreatePerson(pojo.getCreatePerson());
                    en.setCreationTime(pojo.getCreationTime());
                    en.setModifyPerson(pojo.getModifyPerson());
                    en.setModifiedTime(pojo.getModifiedTime());
                    en.setSchoolId(pojo.getSchoolId());
                    en.setCourseId(pojo.getCourseId());
                    en.setDr(0);
                    en.setFileUrl(pojo.getFileUrl());
                    en.setReadyTime(pojo.getReadyTime());
                    en.setCloseTime(pojo.getCloseTime());
                    en.setReview(pojo.getReview());
                    en.setPrepare(pojo.getPrepare());
                    en.setCourseware(pojo.getCourseware());
                    en.setPracticeStageId(pojo.getPracticeStageId());
                    en.setExamStageId(pojo.getExamStageId());
                    en.setPhaseName(pojo.getPhaseName());
                    en.setBanSpeaking(pojo.getBanSpeaking());
                    en.setBanAsking(pojo.getBanAsking());
                    en.setHideDiscussion(pojo.getHideDiscussion());
                    en.setHideAsking(pojo.getHideAsking());
                    en.setAttendance(pojo.getAttendance());
                    //pk
                    en.setClassplanId(courseClassplan.getClassplanId());
                    //schoolId
                    en.setSchoolId(courseClassplan.getSchoolId());
                    //创建人
                    en.setCreatePerson(courseClassplan.getCreator());
                    //修改人
                    en.setModifyPerson(courseClassplan.getModifier());
                    //course_id
                    en.setCourseId(courseClassplan.getCourseId());
                    //排序
                    en.setOrderNum(i);
                    en.setDayTime(DateUtils.dateClear(en.getStartTime()));
                    if(pojo.getAtids()==null|| "".equals(pojo.getAtids())){
                        en.setAssistantTeacherIds(courseClassplan.getAtids());
                    }else {
                        en.setAssistantTeacherIds(pojo.getAtids());
                    }
                    en.setType(pojo.getType());
                    if(en.getType() == 2L){
                    	en.setGoodId(pojo.getGoodId());
                    	en.setRecordId(pojo.getRecordId());
                    }

                    list.add(en);
                }
            }
        }
        return list;
    }
}
