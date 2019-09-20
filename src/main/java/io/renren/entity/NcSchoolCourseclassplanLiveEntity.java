package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * NC线下排课详情(课次)信息表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-02-18 09:12:30
 */
public class NcSchoolCourseclassplanLiveEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//nc课次id
	private String courseclassplanLiveId;
	//nc课次名称
	private String courseclassplanLiveName;
	//nc排课id
	private String courseclassplanId;
	//nc课次上课时间
	private String startTime;
	//nc课次结束时间
	private String endTime;
	//nc课次上课时点(上午下午晚上)
	private String scheduleTime;
	//nc授课老师id
	private String courseTeacherId;
	//授课老师名称
	private String courseTeacherName;
	//非自适应课次文件名称
	private String fileName;
	//非自适应课次文件地址
	private String fileUrl;
	//上期复习文件
	private String reviewName;
	//上期复习文件地址
	private String reviewUrl;
	//本次预习文件
	private String prepareName;
	//本次预习文件地址
	private String prepareUrl;
	//课堂资料文件
	private String coursewareName;
	//课堂资料地址
	private String coursewareUrl;
	//阶段id
	private Integer phaseId;
	//阶段名称
	private String phaseName;
	//创建时间
	private Date createTime;
	//修改时间
	private Date modifiedTime;
	//是否删除标志: 0有效,1删除
	private Integer dr;
	//nc修改课次数据时间
	private String ncModifiedTime;
    //nc授课老师用户编码
    private String courseTeacherUsercode;
    //自适应课次Id
    private Long classplanLiveFk;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：nc课次id
	 */
	public void setCourseclassplanLiveId(String courseclassplanLiveId) {
		this.courseclassplanLiveId = courseclassplanLiveId;
	}
	/**
	 * 获取：nc课次id
	 */
	public String getCourseclassplanLiveId() {
		return courseclassplanLiveId;
	}
	/**
	 * 设置：nc课次名称
	 */
	public void setCourseclassplanLiveName(String courseclassplanLiveName) {
		this.courseclassplanLiveName = courseclassplanLiveName;
	}
	/**
	 * 获取：nc课次名称
	 */
	public String getCourseclassplanLiveName() {
		return courseclassplanLiveName;
	}
	/**
	 * 设置：nc排课id
	 */
	public void setCourseclassplanId(String courseclassplanId) {
		this.courseclassplanId = courseclassplanId;
	}
	/**
	 * 获取：nc排课id
	 */
	public String getCourseclassplanId() {
		return courseclassplanId;
	}
	/**
	 * 设置：nc课次上课时间
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：nc课次上课时间
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * 设置：nc课次结束时间
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：nc课次结束时间
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 设置：nc课次上课时点(上午下午晚上)
	 */
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	/**
	 * 获取：nc课次上课时点(上午下午晚上)
	 */
	public String getScheduleTime() {
		return scheduleTime;
	}
	/**
	 * 设置：nc授课老师id
	 */
	public void setCourseTeacherId(String courseTeacherId) {
		this.courseTeacherId = courseTeacherId;
	}
	/**
	 * 获取：nc授课老师id
	 */
	public String getCourseTeacherId() {
		return courseTeacherId;
	}
	/**
	 * 设置：授课老师名称
	 */
	public void setCourseTeacherName(String courseTeacherName) {
		this.courseTeacherName = courseTeacherName;
	}
	/**
	 * 获取：授课老师名称
	 */
	public String getCourseTeacherName() {
		return courseTeacherName;
	}
	/**
	 * 设置：非自适应课次文件名称
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 获取：非自适应课次文件名称
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 设置：非自适应课次文件地址
	 */
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	/**
	 * 获取：非自适应课次文件地址
	 */
	public String getFileUrl() {
		return fileUrl;
	}
	/**
	 * 设置：上期复习文件
	 */
	public void setReviewName(String reviewName) {
		this.reviewName = reviewName;
	}
	/**
	 * 获取：上期复习文件
	 */
	public String getReviewName() {
		return reviewName;
	}
	/**
	 * 设置：上期复习文件地址
	 */
	public void setReviewUrl(String reviewUrl) {
		this.reviewUrl = reviewUrl;
	}
	/**
	 * 获取：上期复习文件地址
	 */
	public String getReviewUrl() {
		return reviewUrl;
	}
	/**
	 * 设置：本次预习文件
	 */
	public void setPrepareName(String prepareName) {
		this.prepareName = prepareName;
	}
	/**
	 * 获取：本次预习文件
	 */
	public String getPrepareName() {
		return prepareName;
	}
	/**
	 * 设置：本次预习文件地址
	 */
	public void setPrepareUrl(String prepareUrl) {
		this.prepareUrl = prepareUrl;
	}
	/**
	 * 获取：本次预习文件地址
	 */
	public String getPrepareUrl() {
		return prepareUrl;
	}
	/**
	 * 设置：课堂资料文件
	 */
	public void setCoursewareName(String coursewareName) {
		this.coursewareName = coursewareName;
	}
	/**
	 * 获取：课堂资料文件
	 */
	public String getCoursewareName() {
		return coursewareName;
	}
	/**
	 * 设置：课堂资料地址
	 */
	public void setCoursewareUrl(String coursewareUrl) {
		this.coursewareUrl = coursewareUrl;
	}
	/**
	 * 获取：课堂资料地址
	 */
	public String getCoursewareUrl() {
		return coursewareUrl;
	}
	/**
	 * 设置：阶段id
	 */
	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}
	/**
	 * 获取：阶段id
	 */
	public Integer getPhaseId() {
		return phaseId;
	}
	/**
	 * 设置：阶段名称
	 */
	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}
	/**
	 * 获取：阶段名称
	 */
	public String getPhaseName() {
		return phaseName;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：修改时间
	 */
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getModifiedTime() {
		return modifiedTime;
	}
	/**
	 * 设置：是否删除标志: 0有效,1删除
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * 获取：是否删除标志: 0有效,1删除
	 */
	public Integer getDr() {
		return dr;
	}
	/**
	 * 设置：nc修改课次数据时间
	 */
	public void setNcModifiedTime(String ncModifiedTime) {
		this.ncModifiedTime = ncModifiedTime;
	}
	/**
	 * 获取：nc修改课次数据时间
	 */
	public String getNcModifiedTime() {
		return ncModifiedTime;
	}

    public String getCourseTeacherUsercode() {
        return courseTeacherUsercode;
    }

    public void setCourseTeacherUsercode(String courseTeacherUsercode) {
        this.courseTeacherUsercode = courseTeacherUsercode;
    }

    public Long getClassplanLiveFk() {
        return classplanLiveFk;
    }

    public void setClassplanLiveFk(Long classplanLiveFk) {
        this.classplanLiveFk = classplanLiveFk;
    }
}
