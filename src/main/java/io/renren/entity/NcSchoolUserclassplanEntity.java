package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 接收NC学员--排课关联信息表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-26 16:54:16
 */
public class NcSchoolUserclassplanEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//蓝鲸学员id
	private Long userId;
	//学员手机号码
	private String mobile;
	//nc学员名称
	private String userName;
	//nc排课id
	private String courseclassplanId;
	//nc排课入课时间
	private Date timeStamp;
	//是否开通学习计划: 0关1开
	private Integer flag;
	//nc修改排课时间
	private Date ncModifiedTime;
	//创建时间
	private Date createTime;
	//nc班型名称
    private String ncClassType;
    //nc校区id
    private String ncSchoolId;
    //nc校区名称
    private String ncSchoolName;
    //蓝鲸部门id
    private Long deptId;
    //nc学员关联表(c表)pk
    private String ncUserClassplanId;
    //是否是自适应课程
    private int isAdaptiveCourse;

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
	 * 设置：蓝鲸学员id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：蓝鲸学员id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：学员手机号码
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：学员手机号码
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：nc学员名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：nc学员名称
	 */
	public String getUserName() {
		return userName;
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
	 * 设置：nc排课入课时间
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * 获取：nc排课入课时间
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}
	/**
	 * 设置：是否开通学习计划: 0关1开
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	/**
	 * 获取：是否开通学习计划: 0关1开
	 */
	public Integer getFlag() {
		return flag;
	}
	/**
	 * 设置：nc修改排课时间
	 */
	public void setNcModifiedTime(Date ncModifiedTime) {
		this.ncModifiedTime = ncModifiedTime;
	}
	/**
	 * 获取：nc修改排课时间
	 */
	public Date getNcModifiedTime() {
		return ncModifiedTime;
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

    public String getNcClassType() {
        return ncClassType;
    }

    public void setNcClassType(String ncClassType) {
        this.ncClassType = ncClassType;
    }

    public String getNcSchoolId() {
        return ncSchoolId;
    }

    public void setNcSchoolId(String ncSchoolId) {
        this.ncSchoolId = ncSchoolId;
    }

    public String getNcSchoolName() {
        return ncSchoolName;
    }

    public void setNcSchoolName(String ncSchoolName) {
        this.ncSchoolName = ncSchoolName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getNcUserClassplanId() {
        return ncUserClassplanId;
    }

    public void setNcUserClassplanId(String ncUserClassplanId) {
        this.ncUserClassplanId = ncUserClassplanId;
    }

    public int getIsAdaptiveCourse() {
        return isAdaptiveCourse;
    }

    public void setIsAdaptiveCourse(int isAdaptiveCourse) {
        this.isAdaptiveCourse = isAdaptiveCourse;
    }
}
