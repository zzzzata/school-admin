package io.renren.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.hq.common.idgen.IdWorkerSequence;

import io.renren.constant.DateTimeConstant;
import io.renren.dao.CourseClassplanDao;
import io.renren.dao.TeachClassplanBaseFilesDao;
import io.renren.dao.TeachClassplanFilesDao;
import io.renren.dao.TeachClassplanLivesSysuserRefDao;
import io.renren.dao.TeachClassplanSysuserRefDao;
import io.renren.dao.TeachFileDao;
import io.renren.entity.CourseClassplanEntity;
import io.renren.entity.CourseClassplanLivesEntity;
import io.renren.entity.CourseLiveDetailsEntity;
import io.renren.entity.CoursesEntity;
import io.renren.entity.MallLiveRoomEntity;
import io.renren.entity.MallStudioEntity;
import io.renren.entity.SysUserEntity;
import io.renren.entity.TeachClassplanBaseFilesEntity;
import io.renren.entity.TeachClassplanFilesEntity;
import io.renren.entity.TeachClassplanLivesSysuserRefEntity;
import io.renren.entity.TeachClassplanStudentfileEntity;
import io.renren.entity.TeachClassplanSysuserRefEntity;
import io.renren.entity.TeachFileEntity;
import io.renren.entity.TimeTableDetailEntity;
import io.renren.pojo.classplan.ClassplanLivePOJO;
import io.renren.pojo.classplan.ClassplanPOJO;
import io.renren.service.CourseClassplanChangeRecordService;
import io.renren.service.CourseClassplanLivesService;
import io.renren.service.CourseClassplanService;
import io.renren.service.CourseLiveDetailsService;
import io.renren.service.CoursesService;
import io.renren.service.MallGoodsDetailsService;
import io.renren.service.MallGoodsInfoService;
import io.renren.service.MallLiveRoomService;
import io.renren.service.MallStudioService;
import io.renren.service.SysUserService;
import io.renren.service.TeachClassplanStudentfileService;
import io.renren.service.TimeTableDetailService;
import io.renren.service.manage.MessageProductor2KuaidaCourseClassplanService;
import io.renren.utils.ClassTypeUtils;
import io.renren.utils.ClassplanLiveException;
import io.renren.utils.Constant;
import io.renren.utils.DateUtils;
import io.renren.utils.ShiroUtils;
import io.renren.utils.UUIDUtils;


@Transactional(readOnly = true)
@Service("courseClassplanService")
public class CourseClassplanServiceImpl implements CourseClassplanService {
    @Autowired
    private CourseClassplanDao courseClassplanDao;
    @Autowired
    private CourseClassplanLivesService courseClassplanLivesService;
    @Autowired
    private CourseLiveDetailsService courseLiveDetailsService;
    @Autowired
    private TimeTableDetailService timeTableDetailService;
    @Autowired
    private MallLiveRoomService mallLiveRoomService;
    @Autowired
    private MallStudioService mallStudioService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CoursesService coursesService;
    @Autowired
    private MallGoodsInfoService mallGoodsInfoService;
    @Autowired
    private MallGoodsDetailsService mallGoodsDetailsService;
    @Autowired
    private MessageProductor2KuaidaCourseClassplanService msgPro2KdCourseClassplanService;
    @Autowired
    private TeachClassplanStudentfileService teachClassplanStudentfileService;


    //添加助教，20181211 by mumu
    @Autowired
    TeachClassplanSysuserRefDao teachClassplanSysuserRefDao;
    @Autowired
    TeachClassplanLivesSysuserRefDao teachClassplanLivesSysuserRefDao;

//	@Override
//	public CourseClassplanEntity queryObject(Map<String, Object> map){
//		return courseClassplanDao.queryObject(map);
//	}

    @Autowired
    private CourseClassplanChangeRecordService courseClassplanChangeRecordService;

    @Autowired
    private TeachClassplanBaseFilesDao teachClassplanBaseFilesDao;

    @Autowired
    private TeachClassplanFilesDao teachClassplanFilesDao;

    @Autowired
    private TeachFileDao teachFileDao;

    @Override
    public ClassplanPOJO queryObject1(Map<String, Object> map) {
        ClassplanPOJO o = courseClassplanDao.queryObject1(map);
        //添加助教，20181211 by mumu
        Map p = new HashMap();
        p.put("courseClassplanId",o.getClassplanId());
        List<TeachClassplanSysuserRefEntity> list = teachClassplanSysuserRefDao.queryList(p);
        if(list.size()>0){
            Map asst = new JSONObject();
            String atids = "";
            for(TeachClassplanSysuserRefEntity  i : list){
                asst.put(String.valueOf(i.getAssistantTeacherId()),i.getTeacherName());
                atids+= i.getAssistantTeacherId()+",";
            }
            atids.substring(0,atids.length()-1);
            o.setAsst(((JSONObject) asst).toJSONString());
            o.setAtids(atids);
        }
        StringBuilder materialIds = new StringBuilder();
        List<TeachClassplanStudentfileEntity> teachClassplanStudentfile = teachClassplanStudentfileService.get(o.getClassplanId());
        for (int j = 0;j <teachClassplanStudentfile.size();j ++ ){
            materialIds.append(teachClassplanStudentfile.get(j).getTeachStudentFileId());
            if (j != teachClassplanStudentfile.size() - 1){
                materialIds.append(",");
            }
        }
        o.setMaterialIds(materialIds.toString());

        return o;
    }

    @Override
    public List<CourseClassplanEntity> queryList(Map<String, Object> map) {
        List<CourseClassplanEntity> courseClassplanList = courseClassplanDao.queryList(map);
        return courseClassplanList;
    }



    public List<ClassplanPOJO> queryListClassplanPOJO(Map<String, Object> map) {
        List<ClassplanPOJO> courseClassplanList = courseClassplanDao.queryListClassplanPOJO(map);
        for (int i = 0;i< courseClassplanList.size();i ++){
            StringBuilder materialIds = new StringBuilder();
            List<TeachClassplanStudentfileEntity> teachClassplanStudentfile = teachClassplanStudentfileService.get(courseClassplanList.get(i).getClassplanId());
            for (int j = 0;j <teachClassplanStudentfile.size();j ++ ){
                materialIds.append(teachClassplanStudentfile.get(j).getTeachStudentFileId());
                if (j != teachClassplanStudentfile.size() - 1){
                    materialIds.append(",");
                }
            }
            courseClassplanList.get(i).setMaterialIds(materialIds.toString());
        }
        return courseClassplanList;
    }


    public List<Map<String, Object>> queryListMap(Map<String, Object> map) {
        return courseClassplanDao.queryListMap(map);
    }


    @Override
    public int queryTotal(Map<String, Object> map) {
        return courseClassplanDao.queryTotal(map);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void save(ClassplanPOJO courseClassplan) throws ClassplanLiveException {
        // 2018-05-19 去掉排课冲突校验
        //排课冲突信息抛出
//		String errMsg = checkLive(courseClassplan);
//		if(StringUtils.isNotBlank(errMsg)){
//			throw new ClassplanLiveException(errMsg);
//		}
        //审核初始值
        courseClassplan.setIsAudited(2);
        //dr
        courseClassplan.setDr(0);
        //创建时间
        courseClassplan.setCreationTime(new Date());
        //修改时间
        courseClassplan.setModifiedTime(courseClassplan.getCreationTime());
        //en
        CourseClassplanEntity en = ClassplanPOJO.getEntity(courseClassplan);
        //生成ID
        en.setClassplanId(UUIDUtils.formatter());
//		en.setClassplanId(UUID.randomUUID().toString());


        //保存主表
        courseClassplanDao.save(en);
        //保存中间表teach_classplan_studentfile
        TeachClassplanStudentfileEntity teachClassplanStudentfile = new TeachClassplanStudentfileEntity();
        String materialIds = en.getMaterialIds();
        if (!"".equals(materialIds)){
            String[] split = materialIds.split(",");
            for (String materialId : split) {
                teachClassplanStudentfile.setId(System.currentTimeMillis());
                teachClassplanStudentfile.setClassplanId(en.getClassplanId());
                teachClassplanStudentfile.setTeachStudentFileId(Long.valueOf(materialId));
                teachClassplanStudentfile.setSysUserId(ShiroUtils.getUserId());
                teachClassplanStudentfileService.save(teachClassplanStudentfile);
            }
        }
        //排课发送到MQ
        msgPro2KdCourseClassplanService.pushToMessageQueue(en);

        //子表
        List<ClassplanLivePOJO> detailList = courseClassplan.getDetailList();
        //子表保存
        if (null != detailList && detailList.size() > 0) {
            for (int i = 0; i < detailList.size(); i++) {
                //pojo
                ClassplanLivePOJO clp = detailList.get(i);
                //entity
                CourseClassplanLivesEntity ccle = ClassplanLivePOJO.getEntity(clp);
                //set classplanLiveName
                ccle.setClassplanLiveName(clp.getClassplanLiveName());
                //school_id
                ccle.setSchoolId(en.getSchoolId());
                //主表pk
                ccle.setClassplanId(en.getClassplanId());
                //开课时间
                ccle.setStartTime(clp.getStartTime());
                ccle.setEndTime(clp.getEndTime());
                //直播室
                ccle.setStudioId(clp.getStudioId());
                //直播间
                ccle.setLiveroomId(clp.getLiveroomId());
                //授课老师
                ccle.setTeacherId(clp.getTeacherId());
                //创建人
                ccle.setCreatePerson(en.getCreator());
                //修改人
                ccle.setModifyPerson(ccle.getCreatePerson());
                //课程pk
                ccle.setCourseId(en.getCourseId());
                //排序
                ccle.setOrderNum(i);

                ccle.setDayTime(DateUtils.dateClear(ccle.getStartTime()));
                //上传文件的文件名
             /*   ccle.setFileName(clp.getFileName());
                //上期复习文件的文件名
                ccle.setReviewName(clp.getReviewName());
                //本期预习文件的文件名
                ccle.setPrepareName(clp.getPrepareName());
                //课堂资料文件的文件名
                ccle.setCoursewareName(clp.getCoursewareName());*/
//				ccle.setClassplanLiveTimeDetail(classplanLiveTimeDetailFormate(ccle.getStartTime(), ccle.getEndTime()));
                if(null == ccle.getType() || ccle.getType() == 1){
                	ccle.setType(1);
                	ccle.setRecordId(null);
                	ccle.setGoodId(null);
                }

                //保存子表
                courseClassplanLivesService.save(ccle);

                //处理助教老师，20181211 by mumu
                clp.setClassplanLiveId(ccle.getClassplanLiveId());

                //把基础课次资料保存到课次资料
                saveTeachFile(clp, ccle);
            }

            //处理助教老师，20181211 by mumu
            courseClassplan.setClassplanId(en.getClassplanId());
            saveAsst(courseClassplan,detailList,true);
        }
    }

    public void saveAsst(ClassplanPOJO courseClassplan,List<ClassplanLivePOJO> detailList,boolean isSave){
        if(!isSave){//更新操作清除旧数据
            Map p = new HashMap();
            p.put("courseClassplanId",courseClassplan.getClassplanId());
            List<TeachClassplanSysuserRefEntity> prl = teachClassplanSysuserRefDao.queryList(p);
            if(prl.size()>0){
                for(TeachClassplanSysuserRefEntity i : prl){
                    teachClassplanSysuserRefDao.delete(i);
                }
            }
            for(ClassplanLivePOJO o : detailList){
                Map p2 = new HashMap();
                p2.put("courseClassplanLivesId",o.getClassplanLiveId());
                List<TeachClassplanLivesSysuserRefEntity> orl = teachClassplanLivesSysuserRefDao.queryList(p2);
                if(orl.size()>0){
                    for(TeachClassplanLivesSysuserRefEntity i : orl){
                        teachClassplanLivesSysuserRefDao.delete(i);
                    }
                }
            }
        }
        //处理排课计划助教老师
        List<TeachClassplanSysuserRefEntity> cpl = new ArrayList<TeachClassplanSysuserRefEntity>();
        if(StringUtils.isNotBlank(courseClassplan.getAtids())){
            String[] ids = courseClassplan.getAtids().split(",");
            for(String id : ids){
                TeachClassplanSysuserRefEntity pt
                        = new TeachClassplanSysuserRefEntity(
                                courseClassplan.getClassplanId(),
                                Long.valueOf(id),
                                courseClassplan.getCreator()
                );
                cpl.add(pt);
            }
            if(cpl.size()>0){
                teachClassplanSysuserRefDao.saveBatch(cpl);
            }
        }
        //处理课次助教老师
        for(ClassplanLivePOJO o : detailList){
            List<TeachClassplanLivesSysuserRefEntity> pll = new ArrayList<TeachClassplanLivesSysuserRefEntity>();
            if(StringUtils.isNotBlank(o.getAtids())){
                String[] ids = o.getAtids().split(",");
                for(String id : ids){
                    TeachClassplanLivesSysuserRefEntity pt
                            = new TeachClassplanLivesSysuserRefEntity(
                            o.getClassplanLiveId(),
                            Long.valueOf(id),
                            courseClassplan.getCreator()
                    );
                    pll.add(pt);
                }
            }else {//课次没有自定义的助教取课程配置的
                for(TeachClassplanSysuserRefEntity tid : cpl){
                    TeachClassplanLivesSysuserRefEntity pt
                            = new TeachClassplanLivesSysuserRefEntity(
                            o.getClassplanLiveId(),
                            tid.getAssistantTeacherId(),
                            courseClassplan.getCreator()
                    );
                    pll.add(pt);
                }
            }
            if(pll.size()>0){
                teachClassplanLivesSysuserRefDao.saveBatch(pll);
            }
        }
    }

    public String checkLive(ClassplanPOJO courseClassplan) {
        StringBuffer stb = new StringBuffer();
        if (null == courseClassplan || null == courseClassplan.getCheckType() || !courseClassplan.getCheckType()) {
            return stb.toString();
        }
        // 子表
        List<ClassplanLivePOJO> detailList = courseClassplan.getDetailList();
        // 子表保存
        if (null != detailList && detailList.size() > 0) {
            for (int i = 0, j = 0; i < detailList.size(); i++) {
                // pojo
                ClassplanLivePOJO clp = detailList.get(i);
                // entity
                CourseClassplanLivesEntity ccle = ClassplanLivePOJO.getEntity(clp);
                // school_id
                ccle.setSchoolId(courseClassplan.getSchoolId());
                // 主表pk
                ccle.setClassplanId(courseClassplan.getClassplanId());
                // 开课时间
                ccle.setStartTime(clp.getStartTime());
                ccle.setEndTime(clp.getEndTime());
                // 直播室
                ccle.setStudioId(clp.getStudioId());
                // 直播间
                ccle.setLiveroomId(clp.getLiveroomId());
                // 授课老师
                ccle.setTeacherId(clp.getTeacherId());
                // 课程pk
                ccle.setCourseId(courseClassplan.getCourseId());

                ccle.setDayTime(DateUtils.dateClear(ccle.getStartTime()));

                //校验授课老师冲突
                Map<String, Object> checkTeacherMap = this.courseClassplanLivesService.checkTeacher(ccle);
                if (null != checkTeacherMap && null != checkTeacherMap.get("classplanLiveId")) {
                    String errorMsg = ++j + ".授课老师时间冲突！" + checkTeacherMap.get("dayTime") + " "
                            + DateTimeConstant.TIME_BUCKET[(Integer) checkTeacherMap.get("timeBucket")] + " 的"
                            + ccle.getClassplanLiveName() + "与排课计划【" + checkTeacherMap.get("classplanName") + "】的直播课【"
                            + checkTeacherMap.get("classplanLiveName") + "】冲突！";
//					throw new ClassplanLiveException(errorMsg);
                    stb.append(errorMsg + "<br/>");
                }
                //校验直播室冲突
                Map<String, Object> checkStudioMap = this.courseClassplanLivesService.checkStudio(ccle);
                if (null != checkStudioMap && null != checkStudioMap.get("classplanLiveId")) {
                    String errorMsg = ++j + ".直播室冲突！" + checkStudioMap.get("dayTime") + " "
                            + DateTimeConstant.TIME_BUCKET[(Integer) checkStudioMap.get("timeBucket")] + " 的"
                            + ccle.getClassplanLiveName() + "与排课计划【" + checkStudioMap.get("classplanName") + "】的直播课【"
                            + checkStudioMap.get("classplanLiveName") + "】冲突！";
//					throw new ClassplanLiveException(errorMsg);
                    stb.append(errorMsg + "<br/>");
                }
                //校验直播间冲突
                Map<String, Object> checkLiveRoomMap = this.courseClassplanLivesService.checkLiveRoom(ccle);
                if (null != checkLiveRoomMap && null != checkLiveRoomMap.get("classplanLiveId")) {
                    String errorMsg = ++j + ".直播间冲突！" + checkLiveRoomMap.get("dayTime") + " "
                            + DateTimeConstant.TIME_BUCKET[(Integer) checkLiveRoomMap.get("timeBucket")] + " 的"
                            + ccle.getClassplanLiveName() + "与排课计划【" + checkLiveRoomMap.get("classplanName") + "】的直播课【"
                            + checkLiveRoomMap.get("classplanLiveName") + "】冲突！";
//					throw new ClassplanLiveException(errorMsg);
                    stb.append(errorMsg + "<br/>");
                }
                //校验同一课程多次排课冲突
                Map<String, Object> checkCourseLiveMap = this.courseClassplanLivesService.checkCourseLive(ccle);
                if (null != checkCourseLiveMap && null != checkCourseLiveMap.get("classplanLiveId")) {
                    String errorMsg = ++j + ".同一课程下的排课课次冲突！" + checkCourseLiveMap.get("dayTime") + " "
                            + DateTimeConstant.TIME_BUCKET[(Integer) checkCourseLiveMap.get("timeBucket")] + " 的"
                            + ccle.getClassplanLiveName() + "与排课计划【" + checkCourseLiveMap.get("classplanName") + "】的直播课【"
                            + checkCourseLiveMap.get("classplanLiveName") + "】冲突！";
//					throw new ClassplanLiveException(errorMsg);
                    stb.append(errorMsg + "<br/>");
                }

                //TODO 校验同一商品下的不同课程的排课课次冲突
                //*******************************************************************************//
                //通过排课id获取课程信息
                CoursesEntity coursesEntity = new CoursesEntity();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("courseId", ccle.getCourseId());
                map.put("schoolId", ccle.getSchoolId());
                coursesEntity = coursesService.queryObject(map);

                /*List<String> classplanLiveIds = new ArrayList<String>();*/


                if (coursesEntity.getCourseEq() == 0) {//课程不可冲突
                    //确定该课程在哪些商品下
                    List<Long> goodsInfoIds = goodsInfoListByCourseId(ccle.getCourseId(), ccle.getSchoolId());
                    //遍历商品
                    for (Long goodsInfoId : goodsInfoIds) {
                        //确定某个商品下的课程
                        List<Long> courseIds = courseListByGoodsInfoId(goodsInfoId, ccle.getSchoolId());
                        for (Long courseId : courseIds) {
							/*//确定某个课程排课下的课次
							List<String> courseLiveIds = courseLiveListByCourseId(courseId, ccle.getSchoolId());
							if(!courseLiveIds.isEmpty()){
								for (String courseLiveId : courseLiveIds) {
									//将包含该课程的所有商品下的所有课程的排课下的课次放入List集合
									classplanLiveIds.add(courseLiveId);
								}
							}*/
                            ccle.setCourseId(courseId);
                            Map<String, Object> checkOneCommodityCourseLiveMap = this.courseClassplanLivesService.checkOneCommodityCourseLive(ccle);
                            if (null != checkOneCommodityCourseLiveMap && null != checkOneCommodityCourseLiveMap.get("classplanLiveId")) {
                                String errorMsg = ++j + ".同一商品下的不同课程的排课课次冲突！" + checkOneCommodityCourseLiveMap.get("dayTime") + " "
                                        + DateTimeConstant.TIME_BUCKET[(Integer) checkOneCommodityCourseLiveMap.get("timeBucket")] + " 的"
                                        + ccle.getClassplanLiveName() + "与排课计划【" + checkOneCommodityCourseLiveMap.get("classplanName") + "】的直播课【"
                                        + checkOneCommodityCourseLiveMap.get("classplanLiveName") + "】冲突！";
                                //			throw new ClassplanLiveException(errorMsg);
                                stb.append(errorMsg + "<br/>");
                            }
                        }
                    }
					/*if(!classplanLiveIds.isEmpty()){
						String[] classplanLiveIdArray = StringUtils.strip(classplanLiveIds.toString(), "[]").split(",");
						Map<String, Object> checkOneCommodityCourseLiveMap = this.courseClassplanLivesService.checkOneCommodityCourseLive(ccle, classplanLiveIdArray);
						if(null != checkOneCommodityCourseLiveMap && null != checkOneCommodityCourseLiveMap.get("classplanLiveId")){
							String errorMsg = ++j+".同一商品下的不同课程的课次冲突！"+ checkOneCommodityCourseLiveMap.get("dayTime") + " " 
									+ DateTimeConstant.TIME_BUCKET[(Integer)checkOneCommodityCourseLiveMap.get("timeBucket")] + " 的"
									+ ccle.getClassplanLiveName() + "与排课计划【" + checkOneCommodityCourseLiveMap.get("classplanName") + "】的直播课【" 
									+ checkOneCommodityCourseLiveMap.get("classplanLiveName") + "】冲突！";
				//			throw new ClassplanLiveException(errorMsg);
							stb.append(errorMsg + "<br/>");		
						}
					}*/
                }
                if (coursesEntity.getCourseEq() == 1) {//课程可冲突
                    //确定该课程在哪些商品下
                    List<Long> goodsInfoIds = goodsInfoListByCourseId(ccle.getCourseId(), ccle.getSchoolId());
                    //遍历商品
                    for (Long goodsInfoId : goodsInfoIds) {
                        //确定某个商品下的课程
                        List<Long> courseIds = courseListByGoodsInfoId(goodsInfoId, ccle.getSchoolId());
                        for (Long courseId : courseIds) {
                            map.put("courseId", courseId);
                            map.put("schoolId", ccle.getSchoolId());
                            coursesEntity = coursesService.queryObject(map);
                            if (coursesEntity.getCourseEq() == 0) {
                                ccle.setCourseId(courseId);
                                Map<String, Object> checkOneCommodityCourseLiveMap = this.courseClassplanLivesService.checkOneCommodityCourseLive(ccle);
                                if (null != checkOneCommodityCourseLiveMap && null != checkOneCommodityCourseLiveMap.get("classplanLiveId")) {
                                    String errorMsg = ++j + ".同一商品下的不同课程的课次冲突！" + checkOneCommodityCourseLiveMap.get("dayTime") + " "
                                            + DateTimeConstant.TIME_BUCKET[(Integer) checkOneCommodityCourseLiveMap.get("timeBucket")] + " 的"
                                            + ccle.getClassplanLiveName() + "与排课计划【" + checkOneCommodityCourseLiveMap.get("classplanName") + "】的直播课【"
                                            + checkOneCommodityCourseLiveMap.get("classplanLiveName") + "】冲突！";
                                    //			throw new ClassplanLiveException(errorMsg);
                                    stb.append(errorMsg + "<br/>");
                                }
                            }
                        }
                    }
                }


            }
        }
        return stb.toString();
    }

    public String checkLive2(ClassplanPOJO courseClassplan) {
        StringBuffer stb = new StringBuffer();
        //校验同一商品下的不同课程的课次冲突
        return null;

    }


    //修改资料库
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateMaterial(ClassplanPOJO courseClassplan) {
        if (null != courseClassplan) {
            //资料库ID
            Long materialId = courseClassplan.getMaterialId();
            //资料库ids
            String materialIds = courseClassplan.getMaterialIds();
            //平台ID
            String schoolId = courseClassplan.getSchoolId();
            //排课ID
            String classplanId = courseClassplan.getClassplanId();

            if (StringUtils.isNotBlank(schoolId) && StringUtils.isNotBlank(classplanId)) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("materialId", materialId);
                map.put("materialIds", materialIds);
                map.put("schoolId", schoolId);
                map.put("classplanId", classplanId);
                this.courseClassplanDao.updateMaterial(map);
            }
        }

    }
    /**
     * 修改资料库关联 改动的
     * @param courseClassplan
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateMaterialData(ClassplanPOJO courseClassplan) {
//        CourseClassplanEntity courseClassplanEntity = new CourseClassplanEntity();
//        courseClassplanEntity.setClassplanId(courseClassplan.getClassplanId());
//        courseClassplanEntity.setMaterialIds(courseClassplan.getMaterialIds());
//        courseClassplanEntity.setModifiedTime(new Date());
//        courseClassplanEntity.setModifier(ShiroUtils.getUserId());
//        courseClassplanDao.updateMaterialData(courseClassplanEntity);
        //修改排课学员课程资料关系表
        //先删除中间表更新
        teachClassplanStudentfileService.delete(courseClassplan.getClassplanId());
        String materialIds1 = courseClassplan.getMaterialIds();
        if (!"".equals(materialIds1)){
            String[] materialIds = materialIds1.split(",");
            TeachClassplanStudentfileEntity teachClassplanStudentfil = new TeachClassplanStudentfileEntity();
            //删除之后再从新保存关系
            for (String materialId : materialIds) {
                teachClassplanStudentfil.setId(System.currentTimeMillis());
                teachClassplanStudentfil.setClassplanId(courseClassplan.getClassplanId());
                teachClassplanStudentfil.setTeachStudentFileId(Long.valueOf(materialId));
                teachClassplanStudentfil.setSysUserId(ShiroUtils.getUserId());
                teachClassplanStudentfileService.save(teachClassplanStudentfil);
            }
        }
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void update(ClassplanPOJO courseClassplan) {
        //排课冲突信息抛出   2018-05-19 去掉排课冲突校验
		/*String errMsg = checkLive(courseClassplan);
		if(StringUtils.isNotBlank(errMsg)){
			throw new ClassplanLiveException(errMsg);
		}*/
        //修改时间
        courseClassplan.setModifiedTime(new Date());
        //en
        CourseClassplanEntity en = ClassplanPOJO.getEntity(courseClassplan);
        //保存主表修改
        courseClassplanDao.update(en);
        //更新排课学员课程资料关系表
        //先根据classplanId删除中间表的数据
        teachClassplanStudentfileService.delete(courseClassplan.getClassplanId());
        //删除之后再重新保存
        TeachClassplanStudentfileEntity teachClassplanStudentfile = new TeachClassplanStudentfileEntity();
        String ids = courseClassplan.getMaterialIds();
        if (!"".equals(ids)){
            String[] materialIds = ids.split(",");
            for (String materialId : materialIds) {
                teachClassplanStudentfile.setId(System.currentTimeMillis());
                teachClassplanStudentfile.setClassplanId(courseClassplan.getClassplanId());
                teachClassplanStudentfile.setTeachStudentFileId(Long.valueOf(materialId));
                teachClassplanStudentfile.setSysUserId(ShiroUtils.getUserId());
                teachClassplanStudentfileService.save(teachClassplanStudentfile);
            }
        }




        //排课发送到MQ
        msgPro2KdCourseClassplanService.pushToMessageQueue(en);
        //遍历子表
        List<ClassplanLivePOJO> detatilList = courseClassplan.getDetailList();
        //用于存放被删除的子表id集合
        List<String> delIds = new ArrayList<String>();
        if (null != detatilList && detatilList.size() > 0) {
            for (int i = 0; i < detatilList.size(); i++) {
                //pojo
                ClassplanLivePOJO clp = detatilList.get(i);
                //entity
                CourseClassplanLivesEntity ccle = ClassplanLivePOJO.getEntity(clp);
                //pk
                ccle.setClassplanId(en.getClassplanId());
                //schoolId
                ccle.setSchoolId(en.getSchoolId());
                //创建人
                ccle.setCreatePerson(en.getCreator());
                //修改人
                ccle.setModifyPerson(en.getModifier());
                //course_id
                ccle.setCourseId(en.getCourseId());
                //上传的文件名
                ccle.setFileName(clp.getFileName());
                //上期复习文件名
                ccle.setReviewName(clp.getReviewName());
                //本期预习文件名
                ccle.setPrepareName(clp.getPrepareName());
                //课程资料文件名
                ccle.setCoursewareName(clp.getCoursewareName());
                //ccle.setClassplanLiveId(UUID.randomUUID().toString());
                //排序
                ccle.setOrderNum(i);
                ccle.setDayTime(DateUtils.dateClear(ccle.getStartTime()));
                if(ccle.getType() == 1L){
                	ccle.setRecordId(null);
                	ccle.setGoodId(null);
                }
                if(StringUtils.isBlank(ccle.getClassplanLiveId())){//保存
                    courseClassplanLivesService.save(ccle);
                }else{//更新
                    //判断课次是否有变动再更新
                    if (checkClassLiveDetail(ccle)) {
                        courseClassplanLivesService.update(ccle);
                    }
                }
                delIds.add(ccle.getClassplanLiveId());

                //处理助教老师，20181211 by mumu
                clp.setClassplanLiveId(ccle.getClassplanLiveId());
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("classplanLiveIds", delIds);
        map.put("classplanId", en.getClassplanId());
//		System.out.println("==========删除了：" + map);
        courseClassplanLivesService.deleteBatchNotIn(map);

        //处理助教老师，20181211 by mumu
        saveAsst(courseClassplan,detatilList,false);
    }

    @Override
    public void update(CourseClassplanEntity en, List<CourseClassplanLivesEntity> detatilList) {
        //保存主表修改
        courseClassplanDao.update(en);
        //用于存放被删除的子表id集合
        List<String> delIds = new ArrayList<String>();
        if (null != detatilList && detatilList.size() > 0) {
            for (int i = 0; i < detatilList.size(); i++) {
                //entity
                CourseClassplanLivesEntity ccle = detatilList.get(i);
                if (StringUtils.isBlank(ccle.getClassplanLiveId())) {//保存
                    courseClassplanLivesService.save(ccle);
                } else {//更新
                    //判断课次是否有变动再更新
                    if (checkClassLiveDetail(ccle)) {
                        ccle.setReview(null);
                        ccle.setPrepare(null);
                        ccle.setCourseware(null);
                        ccle.setFileUrl(null);
                        courseClassplanLivesService.update(ccle);
                    }
                }
                delIds.add(ccle.getClassplanLiveId());
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("classplanLiveIds", delIds);
        map.put("classplanId", en.getClassplanId());
        courseClassplanLivesService.deleteBatchNotIn(map);
    }

    private boolean checkClassLiveDetail(CourseClassplanLivesEntity ccle) {
        boolean flag = false;
        Map<String, Object> map = new HashMap<>();
        map.put("classplanLiveId", ccle.getClassplanLiveId());
        CourseClassplanLivesEntity oldEntity = courseClassplanLivesService.queryObject(map);
        //直播课名称
        if (!ccle.getClassplanLiveName().equals(oldEntity.getClassplanLiveName())) {
            flag = true;
        }
        //即将开始时间
        if (!ccle.getReadyTime().equals(oldEntity.getReadyTime())) {
            flag = true;
        }
        //开始时间
        if (!ccle.getStartTime().equals(oldEntity.getStartTime())) {
            flag = true;
        }
        //结束时间
        if (!ccle.getEndTime().equals(oldEntity.getEndTime())) {
            flag = true;
        }
        //进入直播结束时间
        if (!ccle.getCloseTime().equals(oldEntity.getCloseTime())) {
            flag = true;
        }
        //上课时段
        if (!ccle.getTimeBucket().equals(oldEntity.getTimeBucket())) {
            flag = true;
        }
        //直播室
        if (ccle.getStudioId() == null && oldEntity.getStudioId() == null) {

        } else if (!ccle.getStudioId().equals(oldEntity.getStudioId())) {
            flag = true;
        }
        //直播间
        if (ccle.getLiveroomId() == null && oldEntity.getLiveroomId() == null) {

        } else if (!ccle.getLiveroomId().equals(oldEntity.getLiveroomId())) {
            flag = true;
        }
        //回放地址(同为空,同为不空且相同,不修改)
        boolean backUrlFlag = StringUtils.isBlank(ccle.getBackUrl()) && StringUtils.isBlank(oldEntity.getBackUrl()) ||
                StringUtils.isNotBlank(ccle.getBackUrl()) && StringUtils.isNotBlank(oldEntity.getBackUrl()) && ccle.getBackUrl().equals(oldEntity.getBackUrl());
        if (!backUrlFlag) {
            flag = true;
        }
        //授课老师
        if (!ccle.getTeacherId().equals(oldEntity.getTeacherId())) {
            flag = true;
        }
        //文件上传(同为空,同为不空且相同,不修改)
        boolean fileUrlFlag = StringUtils.isBlank(ccle.getFileUrl()) && StringUtils.isBlank(oldEntity.getFileUrl()) ||
                StringUtils.isNotBlank(ccle.getFileUrl()) && StringUtils.isNotBlank(oldEntity.getFileUrl()) && ccle.getFileUrl().equals(oldEntity.getFileUrl());
        if (!fileUrlFlag) {
            flag = true;
        }
        //考勤
        if (ccle.getAttendance() != (oldEntity.getAttendance())) {
            flag = true;
        }


        //app4.0.1撤销内容
        //上期复习(同为空,同为不空且相同,不修改)
        boolean reviewFlag = StringUtils.isBlank(ccle.getReview()) && StringUtils.isBlank(oldEntity.getReview()) ||
                StringUtils.isNotBlank(ccle.getReview()) && StringUtils.isNotBlank(oldEntity.getReview()) && ccle.getReview().equals(oldEntity.getReview());
        if (!reviewFlag) {
            flag = true;
        }
        //本次预习(同为空,同为不空且相同,不修改)
        boolean prepareFlag = StringUtils.isBlank(ccle.getPrepare()) && StringUtils.isBlank(oldEntity.getPrepare()) ||
                StringUtils.isNotBlank(ccle.getPrepare()) && StringUtils.isNotBlank(oldEntity.getPrepare()) && ccle.getPrepare().equals(oldEntity.getPrepare());
        if (!prepareFlag) {
            flag = true;
        }
        //课堂资料(同为空,同为不空且相同,不修改)
        boolean coursewareFlag = StringUtils.isBlank(ccle.getCourseware()) && StringUtils.isBlank(oldEntity.getCourseware()) ||
                StringUtils.isNotBlank(ccle.getCourseware()) && StringUtils.isNotBlank(oldEntity.getCourseware()) && ccle.getCourseware().equals(oldEntity.getCourseware());
        if (!coursewareFlag) {
            flag = true;
        }
        //阶段
        if (StringUtils.isBlank(ccle.getExamStageId()) && StringUtils.isBlank(oldEntity.getExamStageId())) {

        } else if (!ccle.getExamStageId().equals(oldEntity.getExamStageId())) {
            flag = true;
        }

        // 禁言
        if (ccle.getBanSpeaking() != (oldEntity.getBanSpeaking())) {
            flag = true;
        }
        // 禁止问答
        if (ccle.getBanAsking() != (oldEntity.getBanAsking())) {
            flag = true;
        }
        // 隐藏讨论模块
        if (ccle.getHideDiscussion() != (oldEntity.getHideDiscussion())) {
            flag = true;
        }
        // 隐藏问答模块
        if (ccle.getHideAsking() != (oldEntity.getHideAsking())) {
            flag = true;
        }
        // 是否挂录播课程
        if (ccle.getType() != oldEntity.getType()) {
        	flag = true;
        }

        //班型权限
        if (StringUtils.isBlank(ccle.getLiveClassTypeIds()) && StringUtils.isBlank(oldEntity.getLiveClassTypeIds())) {

        } else if (!("," + ccle.getLiveClassTypeIds() + ",").equals(oldEntity.getLiveClassTypeIds())) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void delete(Long classplanId) {
        courseClassplanDao.delete(classplanId);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteBatch(Map<String, Object> map) {
        courseClassplanDao.deleteBatch(map);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void pause(String[] classplanIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", classplanIds);
        map.put("status", Constant.Status.PAUSE.getValue());
        courseClassplanDao.updateBatch(map);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void resume(String[] classplanIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", classplanIds);
        map.put("status", Constant.Status.RESUME.getValue());
        courseClassplanDao.updateBatch(map);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void end(String[] classplanIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", classplanIds);
        map.put("status", Constant.Status.END.getValue());
        courseClassplanDao.updateBatch(map);
    }

    @Override
    public List<Map<String, Object>> addItem(Map<String, Object> map) {
        List<Map<String, Object>> list = new ArrayList<>();
        //将主表中相同的字段传递给子表
        Long classplanId = (Long) map.get("classplanId");//主表排课计划id
        Long courseId = (Long) map.get("courseId");//主表课程id
        Long timetableId = (Long) map.get("timetableId");//主表上课时点id
        String startTime = (String) map.get("startTime");//主表开课日期
        Long studioId = (Long) map.get("studioId");//主表直播室id
        Long liveRoomId = (Long) map.get("liveRoomId");//主表直播间id
        Long teacherId = (Long) map.get("teacherId");//主表授课老师id
        String schoolId = (String) map.get("schoolId");//主表schoolId

        String readyTimeStr = (String) map.get("readyTime");//即将开始时间
        int readyTime = Integer.parseInt(readyTimeStr);//即将开始时间转为int类型
        String closeTimeStr = (String) map.get("closeTime");//进入直播间结束时间
        int closeTime = Integer.parseInt(closeTimeStr);//进入直播间结束时间转为int类型
        Integer type = 1;//是否挂录播课程 1：否  2：是

        //课程子表
        Map<String, Object> corseQueryMap = new HashMap<>();
        corseQueryMap.put("schoolId", schoolId);
        corseQueryMap.put("courseId", courseId);
        List<CourseLiveDetailsEntity> courseLiveDetailsEntities = courseLiveDetailsService.queryListByCouresId(corseQueryMap);
        //判断该课程的子表list集合是否为空
        if (null != courseLiveDetailsEntities && !courseLiveDetailsEntities.isEmpty()) {
            List<TimeTableDetailEntity> tableDetailEntities = timeTableDetailService.queryObject(timetableId);
            //查询直播室名称
            Map<String, Object> studioMap = new HashMap<String, Object>();
            studioMap.put("schoolId", schoolId);
            studioMap.put("studioId", studioId);
            MallStudioEntity mallStudioEntity = this.mallStudioService.queryObject(studioMap);
            String studioName = "";
            if (null != studioName) studioName = mallStudioEntity.getStudioName();

            //查询直播间名称
            Map<String, Object> liveRoomSMap = new HashMap<String, Object>();
            liveRoomSMap.put("schoolId", schoolId);
            liveRoomSMap.put("liveRoomId", liveRoomId);
            MallLiveRoomEntity mallLiveRoomEntity = this.mallLiveRoomService.queryObject(liveRoomSMap);
            String liveRoomName = "";
            if (null != liveRoomName) liveRoomName = mallLiveRoomEntity.getLiveRoomName();

            //查询授课老师
            Map<String, Object> teacherMap = new HashMap<String, Object>();
            teacherMap.put("teacherId", teacherId);
            teacherMap.put("schoolId", schoolId);
            SysUserEntity sysUserEntity = this.sysUserService.queryTeacherById(teacherMap);
            String teacherName = "";
            if (null != teacherName) teacherName = sysUserEntity.getNickName();

            for (int i = 0; i < courseLiveDetailsEntities.size(); i++) {
                CourseLiveDetailsEntity courseLiveDetailsEntity = courseLiveDetailsEntities.get(i);
                Map<String, Object> liveMap = new HashMap<>();
                liveMap.put("courseLiveDetailId", courseLiveDetailsEntity.getLiveId());//课程子表直播课次id
                liveMap.put("classplanLiveName", courseLiveDetailsEntity.getLiveName());//直播课程名称
                liveMap.put("liveClassTypeIds", courseLiveDetailsEntity.getLiveClassTypeIds());//班型权限
                //加载上期复习,阶段等字段
                liveMap.put("reviewName",courseLiveDetailsEntity.getReviewName());
                liveMap.put("prepareName",courseLiveDetailsEntity.getPrepareName());
                liveMap.put("coursewareName",courseLiveDetailsEntity.getCoursewareName());
                liveMap.put("review",courseLiveDetailsEntity.getReviewUrl());
                liveMap.put("prepare",courseLiveDetailsEntity.getPrepareUrl());
                liveMap.put("review", courseLiveDetailsEntity.getReviewUrl());
                liveMap.put("prepare", courseLiveDetailsEntity.getPrepareUrl());
                liveMap.put("courseware", courseLiveDetailsEntity.getCoursewareUrl());
                liveMap.put("examStageId", courseLiveDetailsEntity.getExamStageId());
                liveMap.put("phaseName", courseLiveDetailsEntity.getExamStageName());
                liveMap.put("courseware",courseLiveDetailsEntity.getCoursewareUrl());
                liveMap.put("examStageId",courseLiveDetailsEntity.getExamStageId());
                liveMap.put("phaseName",courseLiveDetailsEntity.getExamStageName());
                liveMap.put("studioId", studioId);//直播室PK
                liveMap.put("studioName", studioName);//直播室
                liveMap.put("liveroomId", liveRoomId);//直播间PK
                liveMap.put("liveRoomName", liveRoomName);//直播间
                liveMap.put("teacherId", teacherId);//授课老师PK
                liveMap.put("teacherName", teacherName);//授课老师
                liveMap.put("classplanId", classplanId);//主表id

                liveMap.put("classplanLiveTimeDetail",null);
                liveMap.put("startTime", null);
                liveMap.put("endTime", null);
                liveMap.put("timeBucket", null);//时段0.上午;1.下午;2.晚上
                //TODO
                liveMap.put("readyTime", null);
                liveMap.put("closeTime", null);
                liveMap.put("type", type);

                list.add(liveMap);

            }
            //判断上课时点子表list集合是否为空
            if(null != tableDetailEntities && !tableDetailEntities.isEmpty()){
                List<Map<String, Object>> dateList = new ArrayList<>(courseLiveDetailsEntities.size());
                Date today = DateUtils.parse(startTime);//将主表开课日期转换成Date类型
                today = DateUtils.getDateBefore(today, 1);//将Date类型的开课日期减一天
                do {
                    today = DateUtils.getDateAfter(today, 1);//将Date类型的开课日期加一天
                    for(TimeTableDetailEntity timeTableDetailEntity : tableDetailEntities){
                        if(timeTableDetailEntity.getWeek() == DateUtils.getWeek(today)){//判断排课主表的开课日期对应的星期是否与遍历后的上课时点子表的上课时间（星期）对应
                            Map<String, Object> dateMap = new HashMap<String, Object>();
                            dateMap.put("date", today);//上课时间（星期）
                            dateMap.put("data", timeTableDetailEntity);//上课时点子表
                            dateList.add(dateMap);
                        }
                    }
                } while (dateList.size() < list.size());

                for(int i = 0 ; i< list.size() ;i++){
                    Map<String, Object> liveMap = list.get(i);
                    Map<String, Object> dateMap = dateList.get(i);
                    Date date = (Date) dateMap.get("date");
                    TimeTableDetailEntity timeTableDetailEntity = (TimeTableDetailEntity) dateMap.get("data");
                    Integer startHHMM[] = timetableTimeFormate(timeTableDetailEntity.getStartTime());//将开始时间String类型（HH:mm）按“:”拆分
                    Integer endHHMM[] = timetableTimeFormate(timeTableDetailEntity.getEndTime());//将结束时间String类型（HH:mm）按“:”拆分

                    Date startDate = DateUtils.setHour(startHHMM[0], DateUtils.setMinute(startHHMM[1], date));//将开始时间String类型（HH:mm）转为Date类型（yyyy-MM-dd HH:mm）
                    Date endDate = DateUtils.setHour(endHHMM[0], DateUtils.setMinute(endHHMM[1], date));//将结束时间String类型（HH:mm）转为Date类型（yyyy-MM-dd HH:mm）
                    Date readyDate = DateUtils.addMinute(-readyTime, startDate);//
                    Date closeDate = DateUtils.addMinute(closeTime, endDate);
//					liveMap.put("classplanLiveTimeDetail",classplanLiveTimeDetailFormate(startDate, endDate));
                    liveMap.put("readyTime", readyDate);//即将开始时间
                    liveMap.put("closeTime", closeDate);//进入直播间结束时间
                    liveMap.put("startTime", startDate);//直播开始时间
                    liveMap.put("endTime", endDate);//直播结束时间
                    liveMap.put("timeBucket", timeTableDetailEntity.getTimeBucket());//时段0.上午;1.下午;2.晚上
                    liveMap.put("timeBucketName", DateTimeConstant.TIME_BUCKET[timeTableDetailEntity.getTimeBucket()]);//时段0.上午;1.下午;2.晚上
                }
            }

        }

        return list;
    }
    
    @Override
    public List<Map<String, Object>> autoLoad(Map<String, Object> map) {
        List<Map<String, Object>> list = new ArrayList<>();
        //将主表中相同的字段传递给子表
        String classplanId = (String) map.get("classplanId");//主表排课计划id
        Long timetableId = (Long) map.get("timetableId");//主表上课时点id
        String startTime = (String) map.get("startTime");//主表开课日期
        Date chooseTime = DateUtils.parse(startTime);
        Long studioId = (Long) map.get("studioId");//主表直播室id
        Long liveRoomId = (Long) map.get("liveRoomId");//主表直播间id
        Long teacherId = (Long) map.get("teacherId");//主表授课老师id
        String schoolId = (String) map.get("schoolId");//主表schoolId

        String readyTimeStr = (String) map.get("readyTime");//即将开始时间
        int readyTime = Integer.parseInt(readyTimeStr);//即将开始时间转为int类型
        String closeTimeStr = (String) map.get("closeTime");//进入直播间结束时间
        int closeTime = Integer.parseInt(closeTimeStr);//进入直播间结束时间转为int类型
        String classplanLiveId = (String) map.get("classplanLiveId");//选中课次
        
        Integer type = 1;//是否挂录播课程 1：否  2：是
        List<CourseClassplanLivesEntity> lives = courseClassplanLivesService.queryListByClassplanId(classplanId);
        
        List<TimeTableDetailEntity> tableDetailEntities = timeTableDetailService.queryObject(timetableId);
        //查询直播室名称
        Map<String, Object> studioMap = new HashMap<String, Object>();
        studioMap.put("schoolId", schoolId);
        studioMap.put("studioId", studioId);
        MallStudioEntity mallStudioEntity = this.mallStudioService.queryObject(studioMap);
        String studioName = "";
        if (null != studioName) studioName = mallStudioEntity.getStudioName();

        //查询直播间名称
        Map<String, Object> liveRoomSMap = new HashMap<String, Object>();
        liveRoomSMap.put("schoolId", schoolId);
        liveRoomSMap.put("liveRoomId", liveRoomId);
        MallLiveRoomEntity mallLiveRoomEntity = this.mallLiveRoomService.queryObject(liveRoomSMap);
        String liveRoomName = "";
        if (null != liveRoomName) liveRoomName = mallLiveRoomEntity.getLiveRoomName();

        //查询授课老师
        Map<String, Object> teacherMap = new HashMap<String, Object>();
        teacherMap.put("teacherId", teacherId);
        teacherMap.put("schoolId", schoolId);
        SysUserEntity sysUserEntity = this.sysUserService.queryTeacherById(teacherMap);
        String teacherName = "";
        if (null != teacherName) teacherName = sysUserEntity.getNickName();

        for (int i = 0; i < lives.size(); i++) {
        	CourseClassplanLivesEntity live = lives.get(i);
            Map<String, Object> liveMap = new HashMap<>();
            
            liveMap.put("classplanId", live.getClassplanId());
            liveMap.put("classplanLiveId", live.getClassplanLiveId());
            liveMap.put("classplanLiveName", live.getClassplanLiveName());//直播课程名称
            liveMap.put("liveClassTypeIds", ClassTypeUtils.out(live.getLiveClassTypeIds()));//班型权限
            liveMap.put("reviewName",live.getReviewName());
            liveMap.put("prepareName",live.getPrepareName());
            liveMap.put("coursewareName",live.getCoursewareName());
            liveMap.put("review",live.getReview());
            liveMap.put("prepare",live.getPrepare());
            liveMap.put("courseware", live.getCourseware());
            liveMap.put("examStageId", live.getExamStageId());
            liveMap.put("phaseName", live.getPhaseName());
            liveMap.put("studioId", studioId);//直播室PK
            liveMap.put("studioName", studioName);//直播室
            liveMap.put("liveroomId", liveRoomId);//直播间PK
            liveMap.put("liveRoomName", liveRoomName);//直播间
            liveMap.put("teacherId", teacherId);//授课老师PK
            liveMap.put("teacherName", teacherName);//授课老师
            liveMap.put("classplanId", classplanId);//主表id
            liveMap.put("attendance", live.getAttendance());
            
            liveMap.put("genseeLiveId", mallLiveRoomEntity.getGenseeLiveId());
            liveMap.put("genseeLiveNum", mallLiveRoomEntity.getGenseeLiveNum());
              
            liveMap.put("classplanLiveTimeDetail",live.getClassplanLiveTimeDetail());
            liveMap.put("backUrl", live.getBackUrl());
            liveMap.put("backId", live.getBackId());
            liveMap.put("backType", live.getBackType());
            
            liveMap.put("startTime", live.getStartTime());
            liveMap.put("endTime", live.getEndTime());
            liveMap.put("timeBucket", live.getTimeBucket());//时段0.上午;1.下午;2.晚上
            //TODO
            liveMap.put("readyTime", live.getReadyTime());
            liveMap.put("closeTime", live.getCloseTime());
            liveMap.put("type", type);
            liveMap.put("fileName", live.getFileName());
            liveMap.put("fileUrl", live.getFileUrl());
            liveMap.put("asst", live.getAssistantTeacherIds());
            liveMap.put("assts", live.getAssistantTeacherIds());
            liveMap.put("banSpeaking", live.getBanSpeaking());
            liveMap.put("banAsking", live.getBanAsking());
            liveMap.put("hideDiscussion", live.getHideDiscussion());
            liveMap.put("hideAsking", live.getHideAsking());

            list.add(liveMap);

        }
        //判断上课时点子表list集合是否为空
        if(null != tableDetailEntities && !tableDetailEntities.isEmpty()){
            List<Map<String, Object>> dateList = new ArrayList<>(lives.size());
            Date today = DateUtils.parse(startTime);//将主表开课日期转换成Date类型
            while(dateList.size() < list.size()) {
                today = DateUtils.getDateAfter(today, 1);//将Date类型的开课日期加一天
            	for(TimeTableDetailEntity timeTableDetailEntity : tableDetailEntities){
                    if(timeTableDetailEntity.getWeek() == DateUtils.getWeek(today)){//判断排课主表的开课日期对应的星期是否与遍历后的上课时点子表的上课时间（星期）对应
                        Map<String, Object> dateMap = new HashMap<String, Object>();
                        dateMap.put("date", today);//上课时间（星期）
                        dateMap.put("data", timeTableDetailEntity);//上课时点子表
                        dateList.add(dateMap);
                    }
                }
            }

            for(int i = 0 , j = 0; i< list.size() && j< dateList.size() ;i++){
                Map<String, Object> liveMap = list.get(i);
                Map<String, Object> dateMap = dateList.get(j);
                 
            	Date time = (Date)liveMap.get("startTime");
                //选择的直播课的开始时间之前的直播课都跳过,不处理
                if(StringUtils.isNotBlank(startTime)) {
                	if( time.before(chooseTime) || liveMap.get("classplanLiveId").equals(classplanLiveId)) {
                		continue;
                	}
                } 
                //当前时间大于直播开始时间,跳过,不处理
                if(new Date().after(time)){
                	continue;
                }
                
                Date date = (Date) dateMap.get("date");
                TimeTableDetailEntity timeTableDetailEntity = (TimeTableDetailEntity) dateMap.get("data");
                Integer startHHMM[] = timetableTimeFormate(timeTableDetailEntity.getStartTime());//将开始时间String类型（HH:mm）按“:”拆分
                Integer endHHMM[] = timetableTimeFormate(timeTableDetailEntity.getEndTime());//将结束时间String类型（HH:mm）按“:”拆分

                Date startDate = DateUtils.setHour(startHHMM[0], DateUtils.setMinute(startHHMM[1], date));//将开始时间String类型（HH:mm）转为Date类型（yyyy-MM-dd HH:mm）
                Date endDate = DateUtils.setHour(endHHMM[0], DateUtils.setMinute(endHHMM[1], date));//将结束时间String类型（HH:mm）转为Date类型（yyyy-MM-dd HH:mm）
                Date readyDate = DateUtils.addMinute(-readyTime, startDate);//
                Date closeDate = DateUtils.addMinute(closeTime, endDate);
//					liveMap.put("classplanLiveTimeDetail",classplanLiveTimeDetailFormate(startDate, endDate));
                liveMap.put("readyTime", readyDate);//即将开始时间
                liveMap.put("closeTime", closeDate);//进入直播间结束时间
                liveMap.put("startTime", startDate);//直播开始时间
                liveMap.put("endTime", endDate);//直播结束时间
                liveMap.put("timeBucket", timeTableDetailEntity.getTimeBucket());//时段0.上午;1.下午;2.晚上
                liveMap.put("timeBucketName", DateTimeConstant.TIME_BUCKET[timeTableDetailEntity.getTimeBucket()]);//时段0.上午;1.下午;2.晚上
                j++;
            }
        }

        return list;
    }

//	public static String classplanLiveTimeDetailFormate(Date sdate , Date eDate){
//		String formate = "[开始时间]-[结束时间]";
//		String startFormate = "yyyy-MM-dd HH:mm" ;
//		String endFormate = "HH:mm" ;
//		
//		return formate.replace("[开始时间]", DateUtils.format(sdate , startFormate)).replace("[结束时间]", DateUtils.format(sdate , endFormate));
//	}

    public static Integer[] timetableTimeFormate(String time) {
        Integer[] times = {0, 0};
        if (StringUtils.isNotBlank(time)) {
            String s[] = time.split(":");
            times[0] = Integer.valueOf(s[0]);
            times[1] = Integer.valueOf(s[1]);
        }
        return times;
    }

    /**
     * 根据学员规划子表id查询排课计划下拉列表
     */
    @Override
    public List<Map<String, Object>> queryListByUserplanDetailId(Map<String, Object> map) {
        return this.courseClassplanDao.queryListByUserplanDetailId(map);
    }

    /**
     * 审核通过
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void accept(String classplanId, Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("classplanId", classplanId);
        map.put("isAudited", Constant.Status.RESUME.getValue());
        courseClassplanDao.audited(map);
        courseClassplanChangeRecordService.saveAuait(classplanId, userId);
    }

    /**
     * 审核未过
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void reject(String classplanId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("classplanId", classplanId);
        map.put("isAudited", Constant.Status.PAUSE.getValue());
        courseClassplanDao.audited(map);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void unAudit(String classplanId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("classplanId", classplanId);
        map.put("isAudited", Constant.Status.END.getValue());
        courseClassplanDao.audited(map);
    }


    @Override
    public int checkClassType(long id) {
        return this.courseClassplanDao.checkClassType(id);
    }

    @Override
    public List<CourseClassplanEntity> queryList1(Map<String, Object> map) {
        return this.courseClassplanDao.queryList1(map);
    }

    @Override
    public int queryTotal1(Map<String, Object> map) {
        return this.courseClassplanDao.queryTotal1(map);
    }

    @Override
    public List<Object> queryUserList(Map<String, Object> map) {
        return this.courseClassplanDao.queryUserList(map);
    }

    @Override
    public int queryUserListTotal(Map<String, Object> map) {
        return this.courseClassplanDao.queryUserListTotal(map);
    }

    @Override
    public int findCId(Map<String, Object> map) {
        return this.courseClassplanDao.findCId(map);
    }


    /**
     * 根据课程id确定该课程在哪些商品下
     *
     * @param courseId 课程id
     * @param schoolId 平台id
     * @return
     */
    private List<Long> goodsInfoListByCourseId(Long courseId, String schoolId) {
        List<Long> goodsInfoList = null;
        goodsInfoList = mallGoodsDetailsService.getGoods(courseId, schoolId);
        return goodsInfoList;
    }

    /**
     * 根据商品id获取该商品下的课程
     *
     * @param goodsInfoId 商品id
     * @param schoolId    平台id
     * @return
     */
    private List<Long> courseListByGoodsInfoId(Long goodsInfoId, String schoolId) {
        List<Long> courseList = null;
        courseList = mallGoodsDetailsService.getCourses(goodsInfoId, schoolId);
        return courseList;
    }

    /**
     * 根据课程id获取该课程下的课次
     *
     * @param courseId 课程id
     * @param schoolId 平台id
     * @return
     */
    private List<String> courseLiveListByCourseId(Long courseId, String schoolId) {
        List<String> courseLiveList = null;
        courseLiveList = courseClassplanLivesService.getCourseLives(courseId, schoolId);
        return courseLiveList;
    }
//	public static void main(String[] args) {
//		System.out.println(classplanLiveTimeDetailFormate(new Date(), new Date()));;
//	}

    @Override
    public List<Map<String, Object>> queryClassPlanForQueue(Map<String, Object> map) {
        List<Map<String, Object>> list = courseClassplanDao.queryListMap(map);

//		List<Map<String, Object>> list = this.courseUserplanDetailDao.courseListByUserPlanId(map);
        String schoolId = (String) map.get("schoolId");
        String millisecond = (String) map.get("millisecond");
        if (null != list && list.size() > 0) {
            for (Map<String, Object> map2 : list) {
                Map<String, Object> mapp = new HashMap<String, Object>();
                String id = (String) map2.get("id");
                mapp.put("id", id);
                mapp.put("schoolId", schoolId);
                mapp.put("millisecond", millisecond);
                Map<String, Object> map3 = this.courseClassplanDao.queryOtherById(mapp);
                if (null != map3) {
                    map2.putAll(map3);
                }
            }
        }
        return list;
    }

    /**
     * 查询排课 根据排课ID
     */
    @Override
    public CourseClassplanEntity queryObjectByClassplanId(String classplanId) {
        return this.courseClassplanDao.queryObjectByClassplanId(classplanId);
    }

    /**
     * 把基础课次资料保存到课次资料
     * @param clp 基础课次
     * @param ccle 课次
     */
    private void saveTeachFile(ClassplanLivePOJO clp, CourseClassplanLivesEntity ccle) {

        List<TeachClassplanBaseFilesEntity> baseFilesList = teachClassplanBaseFilesDao.queryListByCourseLiveDetailsId(clp.getCourseLiveDetailId());
        if (baseFilesList.size() > 0) {
            baseFilesList.forEach(item -> {
                //生成文件的fileKey
                String fileKey = createUuid();
                //保存课次资料
                TeachClassplanFilesEntity teachClassplanFilesEntity = new TeachClassplanFilesEntity();
                teachClassplanFilesEntity.setId(IdWorkerSequence.nextId());
                teachClassplanFilesEntity.setProductId(item.getProductId());
                teachClassplanFilesEntity.setName(item.getName());
                teachClassplanFilesEntity.setStageCode(item.getStageCode());
                teachClassplanFilesEntity.setFileKey(fileKey);
                teachClassplanFilesEntity.setClassplanId(ccle.getClassplanId());
                teachClassplanFilesEntity.setCourseClassplanLivesId(ccle.getClassplanLiveId());
                teachClassplanFilesEntity.setCourseId(ccle.getCourseId());
                teachClassplanFilesEntity.setCreateTime(new Date());
                teachClassplanFilesEntity.setSysUserId(ShiroUtils.getUserId());
                teachClassplanFilesEntity.setStatus(1);
                teachClassplanFilesDao.save(teachClassplanFilesEntity);
                //查询文件
                List<TeachFileEntity> fileList = teachFileDao.queryListByFileKey(item.getFileKey());
                //保存文件
                if (fileList.size() > 0) {
                    fileList.forEach(file -> {
                        TeachFileEntity teachFileEntity = new TeachFileEntity();
                        BeanUtils.copyProperties(file, teachFileEntity);
                        teachFileEntity.setFileKey(fileKey);
                        teachFileEntity.setId(IdWorkerSequence.nextId());
                        teachFileEntity.setCreateTime(new Date());
                        teachFileEntity.setSysUserId(ShiroUtils.getUserId());
                        teachFileDao.save(teachFileEntity);
                    });
                }
            });
        }
    }

    /**
     * 生成统一的uuid
     * @return
     */
    public static String createUuid(){
        return UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
    }

	@Override
	public ClassplanPOJO queryObjectByName(String classplanName) {
		// TODO Auto-generated method stub
		return courseClassplanDao.queryObjectByName(classplanName);
	}

}
