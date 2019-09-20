package io.renren.pojo;

import io.renren.enums.InsuranceEnum;
import io.renren.enums.PassStatusEnum;
import io.renren.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;



/**
 *
 *
 * @author linchaokai
 * @email hq@hq.com
 * @date 2018-11-15 09:12:37
 */
public class InsuranceRecordPOJO{
	private Long insuranceRecordId;
	//订单编号
	private String orderNo;
	//蓝鲸学员id
	private String userId;
	//NC报名表号
	private String ncCode;
	//nc报读班型名称
	private String ncCommodityName;
	//nc学历名称
	private String ncRecordName;
	//证件号码
	private String idNumber;
	//NC专业名称
	private String majorName;
	//课程科目数量
	private Integer subjectQty;
	//学费金额
	private Double tuitionFee;
	//投保金额
	private Double premium;
	//考试地区
	private String areaName;
	//0全保1单科
	private Long insuranceType;
	private String insuranceTypeStr;
	//创建时间
	private Date creationTime;
	//投保状态 (0为未投保;1投保成功;2是投保失败,3.签约成功)
	private Integer insuranceStatus;
	private String insuranceStatusStr;
	//团单订单号
	private String yhOrderCode;
	//保单号
	private String policyNo;
	//保险生效日期
	private Date effectDate;
	//保险截止日期
	private Date expireDate;

	private String effectDateStr;

	private String expireDateStr;

	private String nickName;

	private String mobile;
	

	private Integer totalDays;//保险要求天数
	private Integer watchDays;//已达标天数

	private Integer unfinishedDays; //未完成天数=（保险要求天数-已达标天数）
	//协议状态
	private Integer contractStatus;

	//协议id
	private Long contractId;

	//云合同用户id
	private Long signeId;
	//签约时间
	private Date contractTs ;

	//部门PK
	private Long deptId;
	private String deptName;
	private Double compensationAmount;//赔付金额
	//投保日期
	private String attendanceDays;
	/**
	 * 班主任
	 */
	private String teacherNickName;
	//通过
	private Integer passStatus;

	private String passStatusStr;

	//通过时间
	private Date passTime ;
	/**
	 * 设置：
	 */
	public void setInsuranceRecordId(Long insuranceRecordId) {
		this.insuranceRecordId = insuranceRecordId;
	}
	/**
	 * 获取：
	 */
	public Long getInsuranceRecordId() {
		return insuranceRecordId;
	}


	/**
	 * 设置：蓝鲸学员id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：蓝鲸学员id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：NC报名表号
	 */
	public void setNcCode(String ncCode) {
		this.ncCode = ncCode;
	}
	/**
	 * 获取：NC报名表号
	 */
	public String getNcCode() {
		return ncCode;
	}
	/**
	 * 设置：nc报读班型名称
	 */
	public void setNcCommodityName(String ncCommodityName) {
		this.ncCommodityName = ncCommodityName;
	}
	/**
	 * 获取：nc报读班型名称
	 */
	public String getNcCommodityName() {
		return ncCommodityName;
	}
	/**
	 * 设置：nc学历名称
	 */
	public void setNcRecordName(String ncRecordName) {
		this.ncRecordName = ncRecordName;
	}
	/**
	 * 获取：nc学历名称
	 */
	public String getNcRecordName() {
		return ncRecordName;
	}
	/**
	 * 设置：证件号码
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	/**
	 * 获取：证件号码
	 */
	public String getIdNumber() {
		return idNumber;
	}
	/**
	 * 设置：NC专业名称
	 */
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	/**
	 * 获取：NC专业名称
	 */
	public String getMajorName() {
		return majorName;
	}
	/**
	 * 设置：课程科目数量
	 */
	public void setSubjectQty(Integer subjectQty) {
		this.subjectQty = subjectQty;
	}
	/**
	 * 获取：课程科目数量
	 */
	public Integer getSubjectQty() {
		return subjectQty;
	}
	/**
	 * 设置：学费金额
	 */
	public void setTuitionFee(Double tuitionFee) {
		this.tuitionFee = tuitionFee;
	}
	/**
	 * 获取：学费金额
	 */
	public Double getTuitionFee() {
		return tuitionFee;
	}
	/**
	 * 设置：投保金额
	 */
	public void setPremium(Double premium) {
		this.premium = premium;
	}
	/**
	 * 获取：投保金额
	 */
	public Double getPremium() {
		return premium;
	}
	/**
	 * 设置：0全保1单科
	 */
	public void setInsuranceType(Long insuranceType) {
		this.insuranceType = insuranceType;
	}
	/**
	 * 获取：0全保1单科
	 */
	public Long getInsuranceType() {
		return insuranceType;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreationTime() {
		return creationTime;
	}
	/**
	 * 设置：投保状态 (0为未投保;1投保成功;2是投保失败,3.签约成功)
	 */
	public void setInsuranceStatus(Integer insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
	}
	/**
	 * 获取：投保状态 (0为未投保;1投保成功;2是投保失败,3.签约成功)
	 */
	public Integer getInsuranceStatus() {
		return insuranceStatus;
	}
	/**
	 * 设置：团单订单号
	 */
	public void setYhOrderCode(String yhOrderCode) {
		this.yhOrderCode = yhOrderCode;
	}
	/**
	 * 获取：团单订单号
	 */
	public String getYhOrderCode() {
		return yhOrderCode;
	}
	/**
	 * 设置：保单号
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	/**
	 * 获取：保单号
	 */
	public String getPolicyNo() {
		return policyNo;
	}
	/**
	 * 设置：保险生效日期
	 */
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}
	/**
	 * 获取：保险生效日期
	 */
	public Date getEffectDate() {
		return effectDate;
	}
	/**
	 * 设置：保险截止日期
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	/**
	 * 获取：保险截止日期
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getInsuranceTypeStr() {
		if(insuranceType.equals(0L)){
			insuranceTypeStr = "全保";
		}
		if(insuranceType.equals(1L)){
			insuranceTypeStr = "单科";
		}
		return insuranceTypeStr;
	}

	public void setInsuranceTypeStr(String insuranceTypeStr) {
		this.insuranceTypeStr = insuranceTypeStr;
	}

	public String getInsuranceStatusStr() {
		insuranceStatusStr = InsuranceEnum.getText(insuranceStatus);
		return insuranceStatusStr;
	}

	public void setInsuranceStatusStr(String insuranceStatusStr) {
		this.insuranceStatusStr = insuranceStatusStr;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getEffectDateStr() {
		return DateUtils.format(effectDate);
	}

	public void setEffectDateStr(String effectDateStr) {
		this.effectDateStr = effectDateStr;
	}

	public String getExpireDateStr() {
		return DateUtils.format(expireDate);
	}

	public void setExpireDateStr(String expireDateStr) {
		this.expireDateStr = expireDateStr;
	}
	public Integer getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}
	public Integer getWatchDays() {
		return watchDays;
	}
	public void setWatchDays(Integer watchDays) {
		this.watchDays = watchDays;
	}
	public Integer getUnfinishedDays() {
		
		if (this.totalDays==null ) {
			return 0;
		}else if (this.watchDays==null) {
			return this.totalDays;
		}else if (this.watchDays>=this.totalDays) {
			return 0;
		}else {
			return this.totalDays-this.watchDays;
		}
		
		 
	}
	public void setUnfinishedDays(Integer unfinishedDays) {
		this.unfinishedDays = unfinishedDays;
	}


	public Integer getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Long getSigneId() {
		return signeId;
	}

	public void setSigneId(Long signeId) {
		this.signeId = signeId;
	}

	public Date getContractTs() {
		return contractTs;
	}

	public void setContractTs(Date contractTs) {
		this.contractTs = contractTs;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Double getCompensationAmount() {
		if(subjectQty.equals(0)){
			return 0.0;
		}
		return (double)Math.round((tuitionFee / subjectQty * 0.7));
	}
	public void setCompensationAmount(Double compensationAmount) {
		this.compensationAmount = compensationAmount;
	}

	public String getAttendanceDays() {
		return attendanceDays;
	}

	public void setAttendanceDays(String attendanceDays) {
		this.attendanceDays = attendanceDays;
	}

	public String getTeacherNickName() {
		return teacherNickName;
	}

	public void setTeacherNickName(String teacherNickName) {
		this.teacherNickName = teacherNickName;
	}

	public Integer getPassStatus() {
		if(passStatus == null){
			passStatus = 0;
		}
		return passStatus;
	}

	public void setPassStatus(Integer passStatus) {
		this.passStatus = passStatus;
	}

	public String getPassStatusStr() {
		passStatusStr = PassStatusEnum.getText(passStatus.intValue());
		return passStatusStr;
	}

	public void setPassStatusStr(String passStatusStr) {
		this.passStatusStr = passStatusStr;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}
}
