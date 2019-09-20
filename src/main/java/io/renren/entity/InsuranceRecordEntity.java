package io.renren.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *  投保记录单主表
 * @author lintf 
 *
 */
public class InsuranceRecordEntity {
	private Long insuranceRecordId;//主键
	private Long orderId;//订单ID
	private String channelCode;//渠道编码
	private Long sourceOrderId;//来源订单id
	private Long userId;//蓝鲸学员id
	private String ncId;//NC订单主键
	private String ncCode;//NC报名表号
	private String ncUserId;//NC学员id
	private String ncCommodityId;//NC报读班型id
	private String ncCommodityName;//nc报读班型名称
	private String ncRecordName;//nc学历名称
	private Integer ncRecordCode;//nc学历编号
	private String idType;//证件类型
	private String idNumber;//证件号码
	private String majorCode;//NC专业代码
	private String majorName;//NC专业名称
	private Integer subjectQty;//课程科目数量
	private Double tuitionFee;//学费金额
	private Double premium;//投保金额  
	private Double compensationAmount;//赔付金额
	private Long examArea;//考试地区
	private String  examDate;//考期（YYYY-MM)
	private Integer insuranceStatus;// 投保状态 (0为未投保;1投保成功;2是投保失败,3.签约成功)
	private String yhOrderCode;//团单订单号
	private String policyNo;//保单号
	private Date effectDate;//保险生效日期
	private Date expireDate;//保险截止日期

	private String productCode;//誉好产品编码
	private String projectCode;//项目编码
	
	private String projectName;//项目名称
	private Long insuranceInfoId;//商品-投保金额-产品编码档案ID
	private Integer insuranceType;//0全保1单科 
	private Integer dr;//是否删除 0不删除 1是删除
	private Date creationTime ;
	private Date sendTime;//用于定时发送时取的可发送时间
	private Date ts ;
	private Long syncTime;//nc同步时间
	private Integer totalDays;//保险要求天数
	private Integer watchDays;//已达标天数

	private String attendanceDays;//已考勤的天数（最近10天）
	
	private Integer contractStatus;//电子协议状态(0-未签订 1-已签订 2-驳回)	 
	private Long contractId;//在线协议id				 
	private Long templateId;//模板id					 
	private Long signerId;//在线用户pk					 
	private Long productId;//产品线id					 
	private Date logUpdateTime;
	
	
	
	
	
	
	
	private String isInsurant;//是投保（Y是） 
	
	private List<InsuranceRecordCourseEntity> insuranceRecordCourse= new ArrayList<InsuranceRecordCourseEntity>();//这个是子表的
	private boolean iscleanCouse=false;//用于判断是否要清掉子表的
	private Integer passStatus = 0;// 是否通过
	//通过时间
	private Date passTime ;
	
	
	public  InsuranceRecordEntity() {
		this.insuranceStatus=0;  
		this.idType="1170010001";
		 this.channelCode="hq";
		 this.totalDays=0;
		 this.watchDays=0;
		 this.passStatus=0;
	}
	
	
	
	


	/**
	 * 从消息体和订单和编号档案来组成新的投保记录
	 * @param message
	 * @param order
	 * @param info
	 */
	public InsuranceRecordEntity(OrderMessageConsumerEntity message,MallOrderEntity order,InsuranceInfoEntity info,List<InsuranceRecordCourseEntity> detail) {
		 this.channelCode="hq";
		//this.creationTime 
		this.dr=0;
		//this.effectDate 
		 this.examArea =order.getAreaId();
		this.examDate=message.getExamDate(); 
		//this.expireDate 
		this.idNumber= message.getidCard();
		 this.idType="1170010001";
		 this.totalDays=0;
		 this.watchDays=0;
		this.insuranceStatus=0;  
		this.majorCode=message.getZyId();
		this.majorName=message.getZy();
		this.ncCode= order.getNcCode();
		this.ncCommodityId=message.getClass_id();
		this.ncCommodityName=message.getClass_name();
		this.ncId=message.getNc_id();
		this.ncRecordCode=message.getRecordCode();
		this.ncRecordName=message.getRecord();
		this.ncUserId=message.getNc_user_id();
		this.userId=order.getUserId();
		this.orderId=order.getOrderId();
		this.sourceOrderId =order.getOrderId();
		//this.tuitionFee=( message.getInsuranceCost()==null?0L:Double.valueOf(message.getInsuranceCost()));//学费金额从NC中取
		this.productId=order.getProductId();
		//this.policyNo 
		//这个是从info中取的数据
		this.compensationAmount=0.0;
		setInsuranceInfo(info);//
		this.subjectQty =0; 
		
		
		//这个是从子表中取数据的  如果 子表的有有效时长的
		if (detail!=null&&detail.size()>0) {
			int totalday=0;
			
			
			for (InsuranceRecordCourseEntity d:detail) {
				d.setUserId(order.getUserId());
				d.setExamDate(this.examDate); 
				totalday=	totalday+(d.getSubjectHour()==null?0:d.getSubjectHour().intValue()) ;
				 this.insuranceRecordCourse.add(d);
			}
			
			this.totalDays=totalday/2;
			this.subjectQty =detail.size(); 
		}else {//如果没有子表的 当成投保失败
			this.insuranceStatus=2;  
		}
		if (this.insuranceType!=null&&this.insuranceType==0) {
			this.totalDays=200;
		}
	 this.syncTime=  message.getSyn_time()==null?1L:Long.valueOf( message.getSyn_time() );
		
		
		
		
		//this.yhOrderCode 

		
		
	}
 public void setInsuranceInfo (InsuranceInfoEntity info) {
   if (info!=null) { 
	 this.insuranceType =info.getInsuranceType();
	 this.insuranceInfoId  =info.getInsuranceInfoId();
    	this.premium=info.getPremium();
	this.productCode= info.getProductCode();
	this.projectCode = info.getProjectCode();
	this.projectName =info.getProjectName();
	this.tuitionFee=info.getTuitionFee()==null?0L:info.getTuitionFee();//改为从info中取学费金额
	this.compensationAmount=info.getCompensationAmount()==null?0.0:info.getCompensationAmount();
	}
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






	public Long getTemplateId() {
		return templateId;
	}






	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}






	public Long getSignerId() {
		return signerId;
	}






	public void setSignerId(Long signerId) {
		this.signerId = signerId;
	}






	public Long getProductId() {
		return productId;
	}






	public void setProductId(Long productId) {
		this.productId = productId;
	}






	public Long getSyncTime() {
		return syncTime;
	}



	public void setSyncTime(Long syncTime) {
		this.syncTime = syncTime;
	}


	
	
	
	
	public Date getSendTime() {
		return sendTime;
	}



	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	
	
	public String getIsInsurant() {
		return isInsurant;
	}
	public void setIsInsurant(String isInsurant) {
		this.isInsurant = isInsurant;
	}
	
	
	
	
	
	

	public List<InsuranceRecordCourseEntity> getInsuranceRecordCourse() {
		return insuranceRecordCourse;
	}
	public void setInsuranceRecordCourse(List<InsuranceRecordCourseEntity> insuranceRecordCourse) {
		this.insuranceRecordCourse = insuranceRecordCourse;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public Date getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public String getYhOrderCode() {
		return yhOrderCode;
	}
	public void setYhOrderCode(String yhOrderCode) {
		this.yhOrderCode = yhOrderCode;
	}
	public Integer getInsuranceStatus() {
		return insuranceStatus;
	}
	public void setInsuranceStatus(Integer insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
	}
	
	public Long getInsuranceRecordId() {
		return insuranceRecordId;
	}
	public void setInsuranceRecordId(Long insuranceRecordId) {
		this.insuranceRecordId = insuranceRecordId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getSourceOrderId() {
		return sourceOrderId;
	}
	public void setSourceOrderId(Long sourceOrderId) {
		this.sourceOrderId = sourceOrderId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getNcId() {
		return ncId;
	}
	public void setNcId(String ncId) {
		this.ncId = ncId;
	}
	public String getNcCode() {
		return ncCode;
	}
	public void setNcCode(String ncCode) {
		this.ncCode = ncCode;
	}
	public String getNcUserId() {
		return ncUserId;
	}
	public void setNcUserId(String ncUserId) {
		this.ncUserId = ncUserId;
	}
	public String getNcCommodityId() {
		return ncCommodityId;
	}
	public void setNcCommodityId(String ncCommodityId) {
		this.ncCommodityId = ncCommodityId;
	}
	public String getNcCommodityName() {
		return ncCommodityName;
	}
	public void setNcCommodityName(String ncCommodityName) {
		this.ncCommodityName = ncCommodityName;
	}
	public String getNcRecordName() {
		return ncRecordName;
	}
	public void setNcRecordName(String ncRecordName) {
		this.ncRecordName = ncRecordName;
	}
	public Integer getNcRecordCode() {
		return ncRecordCode;
	}
	public void setNcRecordCode(Integer ncRecordCode) {
		this.ncRecordCode = ncRecordCode;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getMajorCode() {
		return majorCode;
	}
	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public Integer getSubjectQty() {
		return subjectQty;
	}
	public void setSubjectQty(Integer subjectQty) {
		this.subjectQty = subjectQty;
	}
	public Double getTuitionFee() {
		return tuitionFee;
	}
	public void setTuitionFee(Double tuitionFee) {
		this.tuitionFee = tuitionFee;
	}
	public Double getPremium() {
		return premium;
	}
	public void setPremium(Double premium) {
		this.premium = premium;
	}
	 
	public Long getExamArea() {
		return examArea;
	}
	public void setExamArea(Long examArea) {
		this.examArea = examArea;
	}
	 
	
	
	
	
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getInsuranceInfoId() {
		return insuranceInfoId;
	}
	public void setInsuranceInfoId(Long insuranceInfoId) {
		this.insuranceInfoId = insuranceInfoId;
	}
	public Integer getInsuranceType() {
		return insuranceType;
	}
	public void setInsuranceType(Integer insuranceType) {
		this.insuranceType = insuranceType;
	}
	 
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public Integer getDr() {
		return dr;
	}
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
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


	
	
	
	
	
	
	

	public String getAttendanceDays() {
		return attendanceDays;
	}






	public void setAttendanceDays(String attendanceDays) {
		this.attendanceDays = attendanceDays;
	}






	public Double getCompensationAmount() {
		return compensationAmount;
	}






	public void setCompensationAmount(Double compensationAmount) {
		this.compensationAmount = compensationAmount;
	}






	public Integer getPassStatus() {
		return passStatus;
	}






	public void setPassStatus(Integer passStatus) {
		this.passStatus = passStatus;
	}






	public Date getPassTime() {
		return passTime;
	}






	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}






	//重新检测生成新的数据 ts有变更  或者不是投保 或者是手动推送的
	public boolean updateEntity(MallOrderEntity order, OrderMessageConsumerEntity message) {
	 
		
		if (Long.valueOf( message.getSyn_time())>this.getSyncTime()||"N".equals( message.getIsInsurant())||"3".equals( message.getHandType())) {
			
			if ("N".equals( message.getIsInsurant())){ //如果当前的是不保险的 则要删除
				this.dr=1;
			}else {
				this.dr=0;
			}
			this.examDate=message.getExamDate();  
			this.idNumber= message.getidCard(); 
			this.majorCode=message.getZyId();
			this.majorName=message.getZy();
			this.ncCode= order.getNcCode();
			this.ncCommodityId=message.getClass_id();
			this.ncCommodityName=message.getClass_name();
			this.ncId=message.getNc_id();
			this.ncRecordCode=message.getRecordCode();
			this.ncRecordName=message.getRecord();
			this.ncUserId=message.getNc_user_id();
			this.userId=order.getUserId();
			this.orderId=order.getOrderId();
			this.sourceOrderId =order.getOrderId();
			//this.tuitionFee=( message.getInsuranceCost() ==null?0L:Double.valueOf(message.getInsuranceCost()));//学费金额从NC中取  2019年3月8日取消这个取法换为从产品表中取
			this.syncTime=Long.valueOf( message.getSyn_time());
			return true;
		}
		
		return false;
	}






	public boolean isIscleanCouse() {
		return iscleanCouse;
	}






	public void setIscleanCouse(boolean iscleanCouse) {
		this.iscleanCouse = iscleanCouse;
	}






	public void clearCourse() {
		this.iscleanCouse=true;
		 if (insuranceRecordCourse==null||insuranceRecordCourse.size()==0) {
			 return;
		 }
		 for (InsuranceRecordCourseEntity in:this.insuranceRecordCourse) {
			 in.setDr(1);
		 }
		this.subjectQty=0;
	}

	public Date getLogUpdateTime() {
		return logUpdateTime;
	}

	public void setLogUpdateTime(Date logUpdateTime) {
		this.logUpdateTime = logUpdateTime;
	}
}
