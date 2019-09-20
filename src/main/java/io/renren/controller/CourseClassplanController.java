package io.renren.controller;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.renren.common.doc.ParamKey;
import io.renren.common.doc.SysLog;
import io.renren.entity.MallClassTypeEntity;
import io.renren.entity.MallLiveRoomEntity;
import io.renren.entity.MallStudioEntity;
import io.renren.entity.SysUserEntity;
import io.renren.pojo.classplan.ClassplanLivePOJO;
import io.renren.pojo.classplan.ClassplanPOJO;
import io.renren.service.CourseClassplanChangeRecordService;
import io.renren.service.CourseClassplanLivesService;
import io.renren.service.CourseClassplanService;
import io.renren.service.MallClassTypeService;
import io.renren.service.MallLiveRoomService;
import io.renren.service.MallStudioService;
import io.renren.service.SysCheckQuoteService;
import io.renren.service.SysUserService;
import io.renren.service.TeachStudentFilesService;
import io.renren.utils.ClassplanLiveException;
import io.renren.utils.ExcelReaderJXL;
import io.renren.utils.PageUtils;
import io.renren.utils.QuoteConstant;
import io.renren.utils.R;
import io.renren.utils.SchoolIdUtils;


/**
 * 排课计划表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-06 10:45:58
 */
@Controller
@RequestMapping("/course/classplan")
public class CourseClassplanController extends AbstractController {
    @Autowired
    private CourseClassplanService courseClassplanService;
    @Autowired
    private CourseClassplanLivesService courseClassplanLivesService;
    @Autowired
    private SysCheckQuoteService sysCheckQuoteService;
    @Autowired
    private CourseClassplanChangeRecordService courseClassplanChangeRecordService;

    @Autowired
    private TeachStudentFilesService teachStudentFilesService;
    @Autowired
    private MallLiveRoomService mallLiveRoomService;
    @Autowired
    private SysUserService sysUserService;
	@Autowired
	private MallClassTypeService mallClassTypeService;
	@Autowired
	private MallStudioService mallStudioService;

	/*@Autowired
	private MessageProductorService messageProductorCourseClassplanServiceImpl;
	*/
    /**
     * 根据学员规划子表id查询排课计划下拉列表
     */
    @ResponseBody
    @RequestMapping("/classplanList")
    public R coursepalnList(HttpServletRequest request){
        Map<String, Object> map = getMapPage(request);
        map.put("schoolId", SchoolIdUtils.getSchoolId(request));
        longQuery(map, request, "userplanDetailId");
        return R.ok().put(courseClassplanService.queryListByUserplanDetailId(map));
    }

    /**
     * 主表列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("course:classplan:list")
    public R list(String classplanName, Integer page,  Integer limit ,HttpServletRequest request){
//	    List<Map<String , Object>> map1=queryClassPlanForQueue();
        String schoolId = SchoolIdUtils.getSchoolId(request);
        Map<String, Object> map = new HashMap<>();
        map.put("classplanName", classplanName);
        map.put("schoolId", schoolId);
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);
        //查询列表数据
//        List<CourseClassplanEntity> courseClassplanList = courseClassplanService.queryList(map);
//		List<CourseClassplanEntity> courseClassplanList = messageProductorCourseClassplanServiceImpl.queryList(map);


        List<ClassplanPOJO> courseClassplanList = courseClassplanService.queryListClassplanPOJO(map);
        int total = courseClassplanService.queryTotal(map);
        PageUtils pageUtil = new PageUtils(courseClassplanList, total, limit, page);
        return R.ok().put("page", pageUtil);

    }
    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info/{classplanId}")
    @RequiresPermissions("course:classplan:info")
    public R info(@PathVariable("classplanId") String classplanId,HttpServletRequest request){
        String schoolId = SchoolIdUtils.getSchoolId(request);
        //查询主表
        Map<String , Object> map1 = new HashMap<String , Object>();
        map1.put("classplanId", classplanId);
        map1.put("schoolId", schoolId);
        ClassplanPOJO courseClassplan = courseClassplanService.queryObject1(map1);
        //查询子表
        Map<String , Object> map2 = new HashMap<String , Object>();
        map2.put("classplanId", classplanId);
        map2.put("schoolId", schoolId);
        List<ClassplanLivePOJO> detailList = courseClassplanLivesService.queryPojoList(map2);
        List materialList = teachStudentFilesService.selectMaterialListByCourseId(courseClassplan.getCourseId());
        return R.ok().put("courseClassplan", courseClassplan).put("detailList", detailList).put("materialList",materialList);
    }

    /**
     * 根据排课计划id查询学员列表
     * @throws ServletRequestBindingException
     */
    @ResponseBody
    @RequestMapping("/userList")
    public R userList(HttpServletRequest request) throws ServletRequestBindingException{
        Map<String, Object> map = getMapPage(request);
        String  classplanId = ServletRequestUtils.getStringParameter(request, "classplanId");
        if(StringUtils.isBlank(classplanId)){
            return R.error("请选择排课！");
        }
        map.put("classplanId", classplanId);
        map.put("schoolId", SchoolIdUtils.getSchoolId(request));
        stringQuery(map, request, "userName");
        stringQuery(map, request, "mobile");
        stringQuery(map, request, "className");
        List<Object> list = this.courseClassplanService.queryUserList(map);
        int total = this.courseClassplanService.queryUserListTotal(map);
        PageUtils pageUtil = new PageUtils(list, total, request);
        return R.ok().put(ParamKey.Out.data, pageUtil);
    } 
    
	/**
	 * EXCEL批量导入
	 * 
	 * @throws Exception
	 */
	@SysLog("EXCEL批量导入")
	@ResponseBody
	@RequestMapping("/importLivesTemplate")
	public R importLivesTemplate(HttpServletRequest request) throws Exception {

		MultipartHttpServletRequest mpReq = (MultipartHttpServletRequest) request;
		MultipartFile file = mpReq.getFile("file_data");
		
		if(file.getSize()<=0) {
			return R.error("请选择Excel文件");
		}
		
		FileInputStream fio = (FileInputStream) file.getInputStream();
		List<String[]> dataList = ExcelReaderJXL.readExcelSDF(fio,"yyyy-MM-dd HH:mm:ss");

		if(dataList.size() == 0) {
			return R.error("格式不正确");
		}
		List<Map<String,Object>> result = new ArrayList<>();
		int num = 1;
		if(dataList.size() > 1) {
			try {
				List<String> repeatLiveNameList = new ArrayList<>();
				for(int i = 1; i < dataList.size(); i++) {
					num++;
					Map<String,Object> map = new HashMap<>();
					String[] rows = dataList.get(i);
					if(rows.length != 11) {
						throw new RuntimeException("文档列数不正确");
					}
					
					//去除空格
					for(int j = 0 ; j< rows.length ; j++) {
						if(StringUtils.isNotBlank(rows[j])) {
							rows[j] = rows[j].trim();
						}
					}
					
					String classplanLiveName = rows[0];
					String readyTime = rows[1];
					String startTime = rows[2];
					String endTime = rows[3];
					String closeTime = rows[4];
					String timeBucketName = rows[5];
					String attendanceName = rows[6];
					String studioName = rows[7];
					String liveRoomName = rows[8];
					String mobile = rows[9];
					String liveClassTypeNames = rows[10];
	
					if(StringUtils.isBlank(classplanLiveName)) {
						throw new RuntimeException("直播课次名字不能为空");
					}
					if(StringUtils.isBlank(readyTime)) {
						throw new RuntimeException("即将开始时间不能为空");
					}
					if(StringUtils.isBlank(startTime)) {
						throw new RuntimeException("开始时间不能为空");
					}
					if(StringUtils.isBlank(endTime)) {
						throw new RuntimeException("结束时间不能为空");
					}
					if(StringUtils.isBlank(closeTime)) {
						throw new RuntimeException("进入直播间结束时间不能为空");
					}
					if(StringUtils.isBlank(timeBucketName)) {
						throw new RuntimeException("上课时段不能为空");
					}
					if(StringUtils.isBlank(attendanceName)) {
						throw new RuntimeException("考勤不能为空");
					}
					if(StringUtils.isBlank(studioName)) {
						throw new RuntimeException("直播室不能为空");
					}
					if(StringUtils.isBlank(liveRoomName)) {
						throw new RuntimeException("直播间不能为空");
					}
					if(StringUtils.isBlank(mobile)) {
						throw new RuntimeException("授课老师号码不能为空");
					}
					if(StringUtils.isBlank(liveClassTypeNames)) {
						throw new RuntimeException("班型权限不能为空");
					}
					map.put("classplanLiveName", classplanLiveName);
					
					if(repeatLiveNameList.contains(classplanLiveName)) {
						throw new RuntimeException("直播课次名称不能重复");
					}else {
						repeatLiveNameList.add(classplanLiveName);
					}
					
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date readyDate = sdf.parse(readyTime);
						Date startDate = sdf.parse(startTime);
						Date endDate = sdf.parse(endTime);
						Date closeDate = sdf.parse(closeTime);
						
						if(readyDate.after(readyDate)) {
							throw new RuntimeException("即将开始时间不能大于开始时间");
						}
						if(startDate.after(endDate)) {
							throw new RuntimeException("开始时间不能大于结束时间");
						}
						if(startDate.after(closeDate)) {
							throw new RuntimeException("开始时间不能大于进入直播间结束时间");
						}

						map.put("readyTime", readyTime);
						map.put("startTime", startTime);
						map.put("endTime", endTime);
						map.put("closeTime", closeTime);
					}catch (Exception e) {
						if(e instanceof ParseException) {
							throw new RuntimeException("时间格式错误，标准样例：2019-01-01 00:00:00");
						}else {
							throw new RuntimeException(e.getMessage());
						}
					}
					
					//上课时段
					Integer timeBucket = 0;
					switch (timeBucketName) {
					case "上午": timeBucket = 0 ;break;
					case "下午": timeBucket = 1 ;break;
					case "晚上": timeBucket = 2 ;break;
					default: throw new RuntimeException("上课时段错误(只能输入：上午/下午/晚上)");
					} 
					map.put("timeBucket", timeBucket); //上课时段
					
					//考勤
					Integer attendance = 0;
					switch (attendanceName) {
					case "是": attendance = 0 ;break;
					case "否": attendance = -1 ;break;
					default: throw new RuntimeException("考勤输入出错(只能输入：是/否)");
					} 
					map.put("attendance", attendance); //是否考勤
					 
					//直播室
					Map<String,Object> studioParams = new HashMap<String,Object>();
					studioParams.put("studioName", studioName);
					List<MallStudioEntity> studioList = mallStudioService.queryList(studioParams);
					if(studioList.size() != 1) {
						throw new RuntimeException("找不到直播室");
					}
					map.put("studioName", studioName); //直播间名称
					map.put("studioId", studioList.get(0).getStudioId()); //直播间ID
					
					//直播间
					Map<String,Object> liveParams = new HashMap<String,Object>();
					liveParams.put("liveRoomName", liveRoomName);
					List<MallLiveRoomEntity> liveRoom = mallLiveRoomService.queryList(liveParams);
					map.put("liveRoomName", liveRoomName); //直播间
					if(liveRoom.size() != 1) {
						throw new RuntimeException("找不到直播间");
					}
					map.put("liveroomName", liveRoomName);
					map.put("liveroomId", liveRoom.get(0).getLiveRoomId()); //直播间ID
					map.put("genseeLiveId", liveRoom.get(0).getGenseeLiveId());
					map.put("genseeLiveNum", liveRoom.get(0).getGenseeLiveNum());
					
					//老师
					Map<String,Object> teacherParams = new HashMap<String,Object>();
					teacherParams.put("mobile", mobile);
					List<SysUserEntity> user = sysUserService.queryList(teacherParams);
					if(user.size() != 1) {
						throw new RuntimeException("找不到老师");
					}
					map.put("teacherName", user.get(0).getNickName()); 
					map.put("teacherId", user.get(0).getUserId());  
					map.put("type", 1);
					
					String[] liveClassTypeNamesArray = liveClassTypeNames.split(",");
					String liveClassTypeIds = "";
					for(String liveClassTypeName : liveClassTypeNamesArray) {
						MallClassTypeEntity classType = mallClassTypeService.queryClassId(liveClassTypeName);
						if(classType == null) {
							throw new RuntimeException("找不到班型权限");
						}
						liveClassTypeIds += classType.getClasstypeId() + ",";
						
					} 
					map.put("liveClassTypeNames", liveClassTypeNames);
					map.put("liveClassTypeIds", liveClassTypeIds);
					result.add(map);
				}
			}catch (Exception e) {
				return R.error("第" + num + "行出错，" + e.getMessage());
			}
		}
		return R.ok().put("list",result);
	}
    
	/**
	 * 列表
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/exportLivesTemplate")
	public void exportLivesTemplate(HttpServletRequest request, HttpServletResponse res)
			throws IOException {
		
		String arrStr = "0,0,0,0,直播名称&0,1,0,0,即将开始时间&0,2,0,0,开始时间&0,3,0,0,结束时间&0,4,0,0,进入直播间结束时间&0,5,0,0,上课时段&0,6,0,0,考勤&0,7,0,0,直播室&0,8,0,0,直播间&0,9,0,0,授课老师手机号码&0,10,0,0,班型权限";
        String title = "排课计划详情";
		String cellsStr = new String(arrStr.getBytes("GBK"), "GBK");
		String[] cells = cellsStr.split("&");
		String filename = title+"导入模板-" + Calendar.getInstance().getTimeInMillis() + ".xls";
		filename = new String(filename.getBytes("GBK"), "iso-8859-1");
		res.setContentType("text/html; charset=UTF-8");
		res.addHeader("Content-Disposition",
				(new StringBuilder()).append("attachment;filename=").append(filename + ";").toString());
		OutputStream toClient = new BufferedOutputStream(res.getOutputStream());
		ExcelReaderJXL.exportToJxlExcel1(filename, title, cells, toClient);
		toClient.flush();
		toClient.close();
		/* return R.ok(); */
	}


    /**
     * 保存
     */
    @SysLog("保存排课计划")
    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions("course:classplan:save")
    public R save(@RequestBody ClassplanPOJO courseClassplan, HttpServletRequest request){
        if(StringUtils.isBlank(courseClassplan.getClassplanName())){
            return R.error("[排课计划名称]不能为空！！！");
        }
        if(StringUtils.isBlank(""+courseClassplan.getProductId())){
            return R.error("[产品线]不能为空！！！");
        }
        if(StringUtils.isBlank(""+courseClassplan.getCourseId())){
            return R.error("[课程名称]不能为空！！！");
        }
		/*if(StringUtils.isBlank(courseClassplan.getClassplanLiveDetail())){
			return R.error("[时间说明]不能为空！！！");
		}*/
        if(StringUtils.isNotBlank(courseClassplan.getClassplanLiveDetail()) && courseClassplan.getClassplanLiveDetail().length() > 100){
            return R.error("[时间说明]不能超过100个字！！！");
        }
//		if(StringUtils.isBlank(""+courseClassplan.getMaterialId())){
//			return R.error("[资料库]不能为空！！！");
//		}
        if(StringUtils.isBlank(""+courseClassplan.getStartTime())){
            return R.error("[开课日期]不能为空！！！");
        }
//		if(StringUtils.isBlank(""+courseClassplan.getTimetableId())){
//			return R.error("[上课时点]不能为空！！！");
//		}
//		if(StringUtils.isBlank(""+courseClassplan.getStudioId())){
//			return R.error("[直播室]不能为空！！！");
//		}
//		if(StringUtils.isBlank(""+courseClassplan.getLiveRoomId())){
//			return R.error("[直播间]不能为空！！！");
//		}
        if(StringUtils.isBlank(""+courseClassplan.getTeacherId())){
            return R.error("[授课老师]不能为空！！！");
        }
        if(StringUtils.isNotBlank(courseClassplan.getRemark()) &&courseClassplan.getRemark().length() > 50){
            return R.error("[备注信息]不能超过50个字！！！");
        }
        String schoolId = SchoolIdUtils.getSchoolId(request);
        //平台ID
        courseClassplan.setSchoolId(schoolId);
        //创建人
        courseClassplan.setCreator(getUserId());
        //修改人
        courseClassplan.setModifier(courseClassplan.getCreator());
        
        boolean isPass = checkRepeatDetail(courseClassplan);
        if(!isPass) {
        	return R.error("请检查直播课次是否有重复");
        }
        
        //保存主表
        try {
            courseClassplanService.save(courseClassplan);
            return R.ok();
        } catch (ClassplanLiveException e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 加载生成子表
     */
    @ResponseBody
    @RequestMapping("/initItem")
    public R initItem(HttpServletRequest request) throws ServletRequestBindingException{
        Map<String , Object> map = getMap(request);
        Long courseId = ServletRequestUtils.getLongParameter(request , "courseId" , 0);//课程id
        String startTime = ServletRequestUtils.getStringParameter(request , "startTime");//开课日期
        Long timetableId = ServletRequestUtils.getLongParameter(request , "timetableId" , 0);//上课时段id
        Long studioId = ServletRequestUtils.getLongParameter(request , "studioId" , 0);//直播室id
        Long liveRoomId = ServletRequestUtils.getLongParameter(request , "liveRoomId" , 0);//直播间id
        Long teacherId = ServletRequestUtils.getLongParameter(request , "teacherId" , 0);//授课老师id

        String readyTime = ServletRequestUtils.getStringParameter(request , "readyTime");//即将开始时间
        String closeTime = ServletRequestUtils.getStringParameter(request , "closeTime");//进入直播间结束时间

        // 非空校验
        if(timetableId == 0){
            return R.error("请输入上课时点");
        }
        if(StringUtils.isBlank(startTime)){
            return R.error("请输入开课日期");
        }
        if(courseId == 0){
            return R.error("请输入课程");
        }
//		if(studioId == 0){
//			return R.error("请输入直播室");
//		}
        if(liveRoomId == 0){
            return R.error("请输入直播间");
        }
        if(teacherId == 0){
            return R.error("请输入授课老师");
        }
        map.put("timetableId", timetableId);
        map.put("startTime", startTime);
        map.put("courseId", courseId);
        map.put("studioId", studioId);
        map.put("liveRoomId", liveRoomId);
        map.put("teacherId", teacherId);

        map.put("readyTime", readyTime);
        map.put("closeTime", closeTime);

        List<Map<String, Object>> list = courseClassplanService.addItem(map);
        PageUtils pageUtil = new PageUtils(list, list.size(), request);
        return R.ok().put("page", pageUtil);
    }
    
    
    /**
     * 加载生成子表
     */
    @ResponseBody
    @RequestMapping(value = "/autoLoad", method = RequestMethod.GET)
    public R autoLoad(HttpServletRequest request) throws ServletRequestBindingException{
        Map<String , Object> map = getMap(request);
        Long courseId = ServletRequestUtils.getLongParameter(request , "courseId" , 0);//课程id
        String startTime = ServletRequestUtils.getStringParameter(request , "startTime");//开课日期
        Long timetableId = ServletRequestUtils.getLongParameter(request , "timetableId" , 0);//上课时段id
        Long studioId = ServletRequestUtils.getLongParameter(request , "studioId" , 0);//直播室id
        Long liveRoomId = ServletRequestUtils.getLongParameter(request , "liveRoomId" , 0);//直播间id
        Long teacherId = ServletRequestUtils.getLongParameter(request , "teacherId" , 0);//授课老师id

        String readyTime = ServletRequestUtils.getStringParameter(request , "readyTime");//即将开始时间
        String closeTime = ServletRequestUtils.getStringParameter(request , "closeTime");//进入直播间结束时间
        
        String classplanId = ServletRequestUtils.getStringParameter(request , "classplanId");
        String classplanLiveId = ServletRequestUtils.getStringParameter(request , "classplanLiveId");
        
        // 非空校验
        if(timetableId == 0){
            return R.error("请输入上课时点");
        }
        if(StringUtils.isBlank(startTime)){
            return R.error("请输入开课日期");
        }
        if(courseId == 0){
            return R.error("请输入课程");
        }
//		if(studioId == 0){
//			return R.error("请输入直播室");
//		}
        if(liveRoomId == 0){
            return R.error("请输入直播间");
        }
        if(teacherId == 0){
            return R.error("请输入授课老师");
        }
        map.put("timetableId", timetableId);
        map.put("startTime", startTime);
        map.put("courseId", courseId);
        map.put("studioId", studioId);
        map.put("liveRoomId", liveRoomId);
        map.put("teacherId", teacherId);

        map.put("readyTime", readyTime);
        map.put("closeTime", closeTime);
        map.put("classplanId", classplanId);
        map.put("classplanLiveId", classplanLiveId);

        List<Map<String, Object>> list = courseClassplanService.autoLoad(map);
        PageUtils pageUtil = new PageUtils(list, list.size(), request);
        return R.ok().put("page", pageUtil);
    }

    @ResponseBody
    @RequestMapping("/checkItem")
    public R checkItem(@RequestBody ClassplanPOJO courseClassplan, HttpServletRequest request){
        if(StringUtils.isBlank(courseClassplan.getClassplanName())){
            return R.error("[排课计划名称]不能为空");
        }
        String schoolId = SchoolIdUtils.getSchoolId(request);
        //平台ID
        courseClassplan.setSchoolId(schoolId);
        //保存主表
		/*String msg= courseClassplanService.checkLive(courseClassplan);
		if(StringUtils.isNotBlank(msg)){
			return R.error(msg);	
		}*/
        return R.ok();
    }
    
    private boolean checkRepeatDetail(ClassplanPOJO courseClassplan) {
        //子表
        List<ClassplanLivePOJO> detailList = courseClassplan.getDetailList();
        
        if(detailList != null) {
	        //检测直播课次重复
	        for( int i = 0 ; i < detailList.size() - 1 ; i++ ){
	            for( int j = detailList.size() - 1; j > i; j-- ){       
	                 if(detailList.get(j).getClassplanLiveName().equals(detailList.get(i).getClassplanLiveName())
	                		 || detailList.get(j).getStartTime().equals(detailList.get(i).getStartTime())
	                		 || detailList.get(j).getCloseTime().equals(detailList.get(i).getCloseTime())
	                		 || detailList.get(j).getEndTime().equals(detailList.get(i).getEndTime())
	                		 || detailList.get(j).getReadyTime().equals(detailList.get(i).getReadyTime())){       
	                	 return false;
	                 }        
	            }        
	        } 
        }
        return true;
    }

    /**
     * 修改
     */
    @SysLog("修改排课计划")
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("course:classplan:update")
    public R update(@RequestBody ClassplanPOJO courseClassplan){
        if(StringUtils.isBlank(courseClassplan.getClassplanName())){
            return R.error("[排课计划名称]不能为空！！！");
        }
        if(StringUtils.isBlank(""+courseClassplan.getProductId())){
            return R.error("[产品线]不能为空！！！");
        }
        if(StringUtils.isBlank(""+courseClassplan.getCourseId())){
            return R.error("[课程名称]不能为空！！！");
        }
		/*if(StringUtils.isBlank(courseClassplan.getClassplanLiveDetail())){
			return R.error("[时间说明]不能为空！！！");
		}*/
        if(StringUtils.isNotBlank(courseClassplan.getClassplanLiveDetail()) && courseClassplan.getClassplanLiveDetail().length() > 100){
            return R.error("[时间说明]不能超过100个字！！！");
        }
//		if(StringUtils.isBlank(""+courseClassplan.getMaterialId())){
//			return R.error("[资料库]不能为空！！！");
//		}
        if(StringUtils.isBlank(""+courseClassplan.getStartTime())){
            return R.error("[开课日期]不能为空！！！");
        }
//		if(StringUtils.isBlank(""+courseClassplan.getTimetableId())){
//			return R.error("[上课时点]不能为空！！！");
//		}
//		if(StringUtils.isBlank(""+courseClassplan.getStudioId())){
//			return R.error("[直播室]不能为空！！！");
//		}
//		if(StringUtils.isBlank(""+courseClassplan.getLiveRoomId())){
//			return R.error("[直播间]不能为空！！！");
//		}
        if(StringUtils.isBlank(""+courseClassplan.getTeacherId())){
            return R.error("[授课老师]不能为空！！！");
        }
        if(StringUtils.isNotBlank(courseClassplan.getRemark()) && courseClassplan.getRemark().length() > 50){
            return R.error("[备注信息]不能超过50个字！！！");
        }
        courseClassplan.setModifier(getUserId());
        
        boolean isPass = checkRepeatDetail(courseClassplan);
        if(!isPass) {
        	return R.error("请检查直播课次是否有重复");
        }
        
        //保存主表
        try {
            courseClassplanService.update(courseClassplan);
            return R.ok();
        } catch (ClassplanLiveException e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 修改
     */
    @SysLog("变更申请排课计划")
    @ResponseBody
    @RequestMapping("/updateApply")
    @RequiresPermissions("course:classplan:update")
    public R updateApply(@RequestBody ClassplanPOJO courseClassplan){
        if(StringUtils.isBlank(courseClassplan.getClassplanName())){
            return R.error("[排课计划名称]不能为空！！！");
        }
        if(StringUtils.isBlank(""+courseClassplan.getProductId())){
            return R.error("[产品线]不能为空！！！");
        }
        if(StringUtils.isBlank(""+courseClassplan.getCourseId())){
            return R.error("[课程名称]不能为空！！！");
        }
        if(StringUtils.isNotBlank(courseClassplan.getClassplanLiveDetail()) && courseClassplan.getClassplanLiveDetail().length() > 100){
            return R.error("[时间说明]不能超过100个字！！！");
        }
        if(StringUtils.isBlank(""+courseClassplan.getStartTime())){
            return R.error("[开课日期]不能为空！！！");
        }
        if(StringUtils.isBlank(""+courseClassplan.getTeacherId())){
            return R.error("[授课老师]不能为空！！！");
        }
        if(StringUtils.isNotBlank(courseClassplan.getRemark()) && courseClassplan.getRemark().length() > 50){
            return R.error("[备注信息]不能超过50个字！！！");
        }
        courseClassplan.setModifier(getUserId());
        
        boolean isPass = checkRepeatDetail(courseClassplan);
        if(!isPass) {
        	return R.error("请检查直播课次是否有重复");
        }
        
        //保存主表
        try {
            courseClassplanChangeRecordService.saveApply(courseClassplan);
            return R.ok();
        } catch (ClassplanLiveException e) {
            logger.error("e.getMessageis{ }",e);
            return R.error(e.getMessage());
        }
    }

    /**
     * 删除
     */
    @SysLog("删除排课计划")
    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions("course:classplan:delete")
    public R delete(@RequestBody String[] classplanIds, HttpServletRequest request){
        Map<String, Object> map = getMap(request);
        for (String classplanId : classplanIds) {
            map.put("value", classplanId);
            if(sysCheckQuoteService.checkQuote(map , QuoteConstant.course_userplan_class_detail_classplan_id)){
                return R.error("删除失败！学习计划详情有引用！！！");
            }
        }
        map.put("ids",classplanIds);
        courseClassplanLivesService.deleteBatch(map);
        courseClassplanService.deleteBatch(map);
        return R.ok();
    }

    /**
     * 作废
     */
    @SysLog("作废排课计划")
    @ResponseBody
    @RequestMapping("/pause")
    @RequiresPermissions("course:classplan:pause")
    public R pause(@RequestBody String[] classplanIds){
        courseClassplanService.pause(classplanIds);
        return R.ok();
    }

    /**
     * 正常
     */
    @SysLog("启用排课计划")
    @ResponseBody
    @RequestMapping("/resume")
    @RequiresPermissions("course:classplan:resume")
    public R resume(@RequestBody String[] classplanIds){
        courseClassplanService.resume(classplanIds);
        return R.ok();
    }

    /**
     * 结课
     */
    @SysLog("结课排课计划")
    @ResponseBody
    @RequestMapping("/end")
    @RequiresPermissions("course:classplan:end")
    public R end(@RequestBody String[] classplanIds){
        courseClassplanService.end(classplanIds);
        return R.ok();
    }

    /**
     * 审核通过
     * */
    @SysLog("审核通过排课计划")
    @ResponseBody
    @RequestMapping("/accept")
    //@RequiresPermissions("course:classplan:accept")
    public R accept(@RequestBody String  classplanId){
        Long userId = getUserId();
        courseClassplanService.accept(classplanId,userId);
        return R.ok();
    }
    /**
     * 审核未过
     * */
    @SysLog("审核未过排课计划")
    @ResponseBody
    @RequestMapping("/reject")
    //@RequiresPermissions("course:classplan:reject")
    public R reject(@RequestBody String classplanId){
        courseClassplanService.reject(classplanId);
        return R.ok();
    }
    /**
     * 反审核修改状态为待审核
     * */
    @SysLog("反审核排课计划")
    @ResponseBody
    @RequestMapping("/waitAudit")
    //@RequiresPermissions("course:classplan:reject")
    public R waitAudit(@RequestBody String classplanId){
        courseClassplanService.unAudit(classplanId);
        return R.ok();
    }

    //修改资料库关联
    @SysLog("修改资料库关联")
    @ResponseBody
    @RequestMapping("/updateMaterial")
    @RequiresPermissions("course:classplan:update")
    public R updateMaterial(@RequestBody ClassplanPOJO courseClassplan){
        this.courseClassplanService.updateMaterial(courseClassplan);
        return R.ok();
    }

    /**
     * 修改资料库关联 改动的
     * @param courseClassplan
     * @return
     */
    @SysLog("修改资料库关联")
    @ResponseBody
    @RequestMapping("/updateMaterialData")
    public R updateMaterialData(@RequestBody ClassplanPOJO courseClassplan){
        this.courseClassplanService.updateMaterialData(courseClassplan);
        return R.ok();
    }


/*//	为排课计划准备推送数据  
	public List<Map<String , Object>> queryClassPlanForQueue() {

//		Map<String , Object> map = getMap(request);
		Map<String , Object> map = new HashMap<>();
		map.put("schoolId", "test");
//		SysCheckQuoteService sysCheckQuote=new SysCheckQuoteServiceImpl();
		String millisecond=sysCheckQuoteService.syncDate(map, SyncDateConstant.course_classplan); 
	    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//	    long millisecond1= format.parse(millisecond).getTime();
	    map.put("millisecond", millisecond);
		List<Map<String , Object>> list=this.courseClassplanService.queryClassPlanForQueue(map);
		Map<String, Object> syncMap=new HashMap<>();
		for (Map<String, Object> map2 : list) {
			   java.sql.Timestamp timestamp=(Timestamp) map2.get("ts");
//			   String ts1 =  map2.get("ts"); 
			   long millisecond1= timestamp.getTime();
			   if(millisecond1!=0){
				if(millisecond1>millisecond){
					syncMap.putAll(map2);
				}
			   }
		}
		return list;
	}*/

}
