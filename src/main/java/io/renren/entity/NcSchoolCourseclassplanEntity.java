package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * NC线下排课信息表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-02-18 09:12:11
 */
public class NcSchoolCourseclassplanEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//nc排课id
	private String courseclassplanId;
	//nc排课名称
	private String courseclassplanName;
	//nc校区id
	private String ncSchoolId;
	//nc校区名称
	private String ncSchoolName;
	//nc课程id
	private String courseId;
	//nc课城名称
	private String courseName;
	//nc上课时点(上午下午晚上)
	private String scheduleTime;
	//nc排课类型(正常排课,加课)
	private String classplanType;
	//nc排课开课日期
	private String dateTime;
	//授课老师id
	private String courseTeacherId;
	//nc授课老师名称
	private String courseTeacherName;
	//nc考核老师id
	private String auditTeacherId;
	//nc考核老师名称
	private String auditTeacherName;
	//nc排课状态: 0正常,1结课
	private Integer status;
	//创建时间
	private Date createTime;
	//修改时间
	private Date modifiedTime;
	//是否删除标志: 0有效 1删除
	private Integer dr;
	//nc修改排课数据时间
	private String ncModifiedTime;
	//nc授课老师用户编码
    private String courseTeacherUsercode;
    //nc考核老师用户编码
    private String auditTeacherUsercode;
    //产品线id
    private Long productId;
    //nc排课编码
    private String courseclassplanCode;
    //nc班级id
    private String ncClassId;
    //nc班级名称
    private String ncClassName;
    //产品线名称
    private String productName;
    //排课子表数量
    private int classplanLiveNum;
    //学生数量
    private int studentNum;
    //蓝鲸部门id
    private Long deptId;
    //nc课程编码
    private String courseCode;
    //自适应课程id
    private Long courseFk;

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
	 * 设置：nc排课名称
	 */
	public void setCourseclassplanName(String courseclassplanName) {
		this.courseclassplanName = courseclassplanName;
	}
	/**
	 * 获取：nc排课名称
	 */
	public String getCourseclassplanName() {
		return courseclassplanName;
	}
	/**
	 * 设置：nc校区id
	 */
	public void setNcSchoolId(String ncSchoolId) {
		this.ncSchoolId = ncSchoolId;
	}
	/**
	 * 获取：nc校区id
	 */
	public String getNcSchoolId() {
		return ncSchoolId;
	}
	/**
	 * 设置：nc校区名称
	 */
	public void setNcSchoolName(String ncSchoolName) {
		this.ncSchoolName = ncSchoolName;
	}
	/**
	 * 获取：nc校区名称
	 */
	public String getNcSchoolName() {
		return ncSchoolName;
	}
	/**
	 * 设置：nc课程id
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	/**
	 * 获取：nc课程id
	 */
	public String getCourseId() {
		return courseId;
	}
	/**
	 * 设置：nc课城名称
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * 获取：nc课城名称
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * 设置：nc上课时点(上午下午晚上)
	 */
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	/**
	 * 获取：nc上课时点(上午下午晚上)
	 */
	public String getScheduleTime() {
		return scheduleTime;
	}
	/**
	 * 设置：nc排课类型(正常排课,加课)
	 */
	public void setClassplanType(String classplanType) {
		this.classplanType = classplanType;
	}
	/**
	 * 获取：nc排课类型(正常排课,加课)
	 */
	public String getClassplanType() {
		return classplanType;
	}
	/**
	 * 设置：nc排课开课日期
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	/**
	 * 获取：nc排课开课日期
	 */
	public String getDateTime() {
		return dateTime;
	}
	/**
	 * 设置：授课老师id
	 */
	public void setCourseTeacherId(String courseTeacherId) {
		this.courseTeacherId = courseTeacherId;
	}
	/**
	 * 获取：授课老师id
	 */
	public String getCourseTeacherId() {
		return courseTeacherId;
	}
	/**
	 * 设置：nc授课老师名称
	 */
	public void setCourseTeacherName(String courseTeacherName) {
		this.courseTeacherName = courseTeacherName;
	}
	/**
	 * 获取：nc授课老师名称
	 */
	public String getCourseTeacherName() {
		return courseTeacherName;
	}
	/**
	 * 设置：nc考核老师id
	 */
	public void setAuditTeacherId(String auditTeacherId) {
		this.auditTeacherId = auditTeacherId;
	}
	/**
	 * 获取：nc考核老师id
	 */
	public String getAuditTeacherId() {
		return auditTeacherId;
	}
	/**
	 * 设置：nc考核老师名称
	 */
	public void setAuditTeacherName(String auditTeacherName) {
		this.auditTeacherName = auditTeacherName;
	}
	/**
	 * 获取：nc考核老师名称
	 */
	public String getAuditTeacherName() {
		return auditTeacherName;
	}
	/**
	 * 设置：nc排课状态: 0正常,1结课
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：nc排课状态: 0正常,1结课
	 */
	public Integer getStatus() {
		return status;
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
	 * 设置：是否删除标志: 0有效 1删除
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * 获取：是否删除标志: 0有效 1删除
	 */
	public Integer getDr() {
		return dr;
	}
	/**
	 * 设置：nc修改排课数据时间
	 */
	public void setNcModifiedTime(String ncModifiedTime) {
		this.ncModifiedTime = ncModifiedTime;
	}
	/**
	 * 获取：nc修改排课数据时间
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

    public String getAuditTeacherUsercode() {
        return auditTeacherUsercode;
    }

    public void setAuditTeacherUsercode(String auditTeacherUsercode) {
        this.auditTeacherUsercode = auditTeacherUsercode;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCourseclassplanCode() {
        return courseclassplanCode;
    }

    public void setCourseclassplanCode(String courseclassplanCode) {
        this.courseclassplanCode = courseclassplanCode;
    }

    public String getNcClassId() {
        return ncClassId;
    }

    public void setNcClassId(String ncClassId) {
        this.ncClassId = ncClassId;
    }

    public String getNcClassName() {
        return ncClassName;
    }

    public void setNcClassName(String ncClassName) {
        this.ncClassName = ncClassName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getClassplanLiveNum() {
        return classplanLiveNum;
    }

    public void setClassplanLiveNum(int classplanLiveNum) {
        this.classplanLiveNum = classplanLiveNum;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Long getCourseFk() {
        return courseFk;
    }

    public void setCourseFk(Long courseFk) {
        this.courseFk = courseFk;
    }
}
