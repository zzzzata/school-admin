package io.renren.pojo.course;

/**
 * @Auther: chenda
 * @Date: 2019/7/27 09:42
 * @Description:
 */
public class SubjectStatisticsPOJO {

    //团队id
    private Long teamId;

    //团队名称
    private String teamName;

    //班级id
    private Long classId;

    //班级名称
    private String className;

    //班主任id
    private Long sysUserId;

    //班主任姓名
    private String nickName;

    //课程id
    private Long courseId;

    //课程名称
    private String courseName;

    //排课id
    private String classplanId;

    //排课名称
    private String classplanName;

    //直播-直播观看时长/ms
    private Long liveDuration=0l;

    //直播-直播+直播回放观看时长/ms
    private  Long watchDuration=0l;

    //直播-应观看时长/ms
    private Long fullDuration=0l;

    //直播关联录播-录播课次观看时长/ms
    private Long recordDuration=0l;

    //直播关联录播-录播课次应观看时长/ms
    private Long mustRecordDuration=0l;

    //实际最大观看时长/ms
    private Long maxAttendDuration=0l;

    //最大应观看时长/ms
    private Long mustMaxAttendDuration=0l;

    //计划做题数
    private Integer planHomewordCount=0;

    //实际做题数
    private Integer actualHomewordCount=0;

    //正确做题数
    private Integer rightHomewordCount=0;

    //学员人次
    private Integer studentCount=0;

    //学员直播出勤率
    private String livePer;

    //学员总出勤率
    private String attendPer;

    //学员录播出勤率
    private String recordPer;


    //学员最大出勤率
    private String maxAttendPer;

    //学员作业达标率
    private String workRate;

    //学员作业完成率
    private  String completeRate;



    //团队直播出勤率
    private String teamLivePer;


    //团队总出勤率
    private String teamAttendPer;


    //团队录播出勤率
    private String teamRecordPer;


    //团队最大出勤率
    private String teamMaxAttendPer;


    //团队作业达标率
    private String teamWorkRate;


    //团队完成率
    private String teamCompleteRate;




    //班主任直播出勤率
    private String teacherLivePer;


    //班主任总出勤率
    private String teacherAttendPer;


    //班主任录播出勤率
    private String teacherRecordPer;


    //班主任最大出勤率
    private String teacherMaxAttendPer;


    //班主任作业达标率
    private String teacherWorkRate;


    //班主任完成率
    private String teacherCompleteRate;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassplanId() {
        return classplanId;
    }

    public void setClassplanId(String classplanId) {
        this.classplanId = classplanId;
    }

    public String getClassplanName() {
        return classplanName;
    }

    public void setClassplanName(String classplanName) {
        this.classplanName = classplanName;
    }

    public Long getLiveDuration() {
        return liveDuration;
    }

    public void setLiveDuration(Long liveDuration) {
        this.liveDuration = liveDuration;
    }

    public Long getWatchDuration() {
        return watchDuration;
    }

    public void setWatchDuration(Long watchDuration) {
        this.watchDuration = watchDuration;
    }

    public Long getFullDuration() {
        return fullDuration;
    }

    public void setFullDuration(Long fullDuration) {
        this.fullDuration = fullDuration;
    }

    public Long getRecordDuration() {
        return recordDuration;
    }

    public void setRecordDuration(Long recordDuration) {
        this.recordDuration = recordDuration;
    }

    public Long getMustRecordDuration() {
        return mustRecordDuration;
    }

    public void setMustRecordDuration(Long mustRecordDuration) {
        this.mustRecordDuration = mustRecordDuration;
    }

    public Long getMaxAttendDuration() {
        return maxAttendDuration;
    }

    public void setMaxAttendDuration(Long maxAttendDuration) {
        this.maxAttendDuration = maxAttendDuration;
    }

    public Long getMustMaxAttendDuration() {
        return mustMaxAttendDuration;
    }

    public void setMustMaxAttendDuration(Long mustMaxAttendDuration) {
        this.mustMaxAttendDuration = mustMaxAttendDuration;
    }

    public Integer getPlanHomewordCount() {
        return planHomewordCount;
    }

    public void setPlanHomewordCount(Integer planHomewordCount) {
        this.planHomewordCount = planHomewordCount;
    }

    public Integer getActualHomewordCount() {
        return actualHomewordCount;
    }

    public void setActualHomewordCount(Integer actualHomewordCount) {
        this.actualHomewordCount = actualHomewordCount;
    }

    public Integer getRightHomewordCount() {
        return rightHomewordCount;
    }

    public void setRightHomewordCount(Integer rightHomewordCount) {
        this.rightHomewordCount = rightHomewordCount;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public String getLivePer() {
        return livePer;
    }

    public void setLivePer(String livePer) {
        this.livePer = livePer;
    }

    public String getAttendPer() {
        return attendPer;
    }

    public void setAttendPer(String attendPer) {
        this.attendPer = attendPer;
    }

    public String getRecordPer() {
        return recordPer;
    }

    public void setRecordPer(String recordPer) {
        this.recordPer = recordPer;
    }

    public String getMaxAttendPer() {
        return maxAttendPer;
    }

    public void setMaxAttendPer(String maxAttendPer) {
        this.maxAttendPer = maxAttendPer;
    }

    public String getWorkRate() {
        return workRate;
    }

    public void setWorkRate(String workRate) {
        this.workRate = workRate;
    }

    public String getCompleteRate() {
        return completeRate;
    }

    public void setCompleteRate(String completeRate) {
        this.completeRate = completeRate;
    }

    public String getTeamLivePer() {
        return teamLivePer;
    }

    public void setTeamLivePer(String teamLivePer) {
        this.teamLivePer = teamLivePer;
    }

    public String getTeamAttendPer() {
        return teamAttendPer;
    }

    public void setTeamAttendPer(String teamAttendPer) {
        this.teamAttendPer = teamAttendPer;
    }

    public String getTeamRecordPer() {
        return teamRecordPer;
    }

    public void setTeamRecordPer(String teamRecordPer) {
        this.teamRecordPer = teamRecordPer;
    }

    public String getTeamMaxAttendPer() {
        return teamMaxAttendPer;
    }

    public void setTeamMaxAttendPer(String teamMaxAttendPer) {
        this.teamMaxAttendPer = teamMaxAttendPer;
    }

    public String getTeamWorkRate() {
        return teamWorkRate;
    }

    public void setTeamWorkRate(String teamWorkRate) {
        this.teamWorkRate = teamWorkRate;
    }

    public String getTeamCompleteRate() {
        return teamCompleteRate;
    }

    public void setTeamCompleteRate(String teamCompleteRate) {
        this.teamCompleteRate = teamCompleteRate;
    }

    public String getTeacherLivePer() {
        return teacherLivePer;
    }

    public void setTeacherLivePer(String teacherLivePer) {
        this.teacherLivePer = teacherLivePer;
    }

    public String getTeacherAttendPer() {
        return teacherAttendPer;
    }

    public void setTeacherAttendPer(String teacherAttendPer) {
        this.teacherAttendPer = teacherAttendPer;
    }

    public String getTeacherRecordPer() {
        return teacherRecordPer;
    }

    public void setTeacherRecordPer(String teacherRecordPer) {
        this.teacherRecordPer = teacherRecordPer;
    }

    public String getTeacherMaxAttendPer() {
        return teacherMaxAttendPer;
    }

    public void setTeacherMaxAttendPer(String teacherMaxAttendPer) {
        this.teacherMaxAttendPer = teacherMaxAttendPer;
    }

    public String getTeacherWorkRate() {
        return teacherWorkRate;
    }

    public void setTeacherWorkRate(String teacherWorkRate) {
        this.teacherWorkRate = teacherWorkRate;
    }

    public String getTeacherCompleteRate() {
        return teacherCompleteRate;
    }

    public void setTeacherCompleteRate(String teacherCompleteRate) {
        this.teacherCompleteRate = teacherCompleteRate;
    }
}
