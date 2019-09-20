package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 线下学习计划
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-27 11:39:22
 */
public class NcSchoolLearningEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long learningId;
	//线下学习计划编码
	private String learningNo;
	//蓝鲸学员id
	private Long userId;
	//学员手机号码
	private String mobile;
	//部门id
	private Long deptId;
	//nc班型名称
	private String ncClassType;
	//是否删除: 0正常1删除
	private Integer dr;
	//创建时间
	private Date createTime;
	//
	private Date ts;
	//nc校区id
    private String ncSchoolId;
    //nc校区名称
    private String ncSchoolName;
    //学员名称
    private String userName;
    //蓝鲸部门名称
    private String deptName;
    //nc学员排课关联表pk
    private String ncUserClassplanId;

	/**
	 * 设置：
	 */
	public void setLearningId(Long learningId) {
		this.learningId = learningId;
	}
	/**
	 * 获取：
	 */
	public Long getLearningId() {
		return learningId;
	}
	/**
	 * 设置：线下学习计划编码
	 */
	public void setLearningNo(String learningNo) {
		this.learningNo = learningNo;
	}
	/**
	 * 获取：线下学习计划编码
	 */
	public String getLearningNo() {
		return learningNo;
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
	 * 设置：部门id
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：部门id
	 */
	public Long getDeptId() {
		return deptId;
	}
	/**
	 * 设置：nc班型名称
	 */
	public void setNcClassType(String ncClassType) {
		this.ncClassType = ncClassType;
	}
	/**
	 * 获取：nc班型名称
	 */
	public String getNcClassType() {
		return ncClassType;
	}
	/**
	 * 设置：是否删除: 0正常1删除
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * 获取：是否删除: 0正常1删除
	 */
	public Integer getDr() {
		return dr;
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
	 * 设置：
	 */
	public void setTs(Date ts) {
		this.ts = ts;
	}
	/**
	 * 获取：
	 */
	public Date getTs() {
		return ts;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getNcUserClassplanId() {
        return ncUserClassplanId;
    }

    public void setNcUserClassplanId(String ncUserClassplanId) {
        this.ncUserClassplanId = ncUserClassplanId;
    }
}
