package io.renren.entity;

import java.text.DecimalFormat;
import java.util.Date;
/**
 * 商品-投保金额-产品编码档案
 * @author lintf 
 *
 */
public class InsuranceInfoEntity {

	private Long insuranceInfoId;//商品-投保金额-产品编码档案ID
	private Double tuitionFee;//学费金额
	private Double premium;//投保金额   
	private String productCode;//誉好产品编码
	private String projectCode;//项目编码
	private String projectName;//项目名称
	private Integer insuranceType;//0全保1单科 

	private Integer dr=0;//是否删除 0不删除 1是删除
	private Date creationTime ;
	private Date ts ;
 
	private String insuranceTypeName;//0全保1单科 
	private Double compensationAmount;//赔付金额
	
	
	private String compensationAmountName;//用于界面显示的赔付金额
	
	
	
	public InsuranceInfoEntity() {
		this.projectCode="zikao";
		this.projectName="自考";
		this.insuranceType=0;
	}
	
	
	
	
	
	
	
	
	public Long getInsuranceInfoId() {
		return insuranceInfoId;
	}
	public void setInsuranceInfoId(Long insuranceInfoId) {
		this.insuranceInfoId = insuranceInfoId;
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
	public Integer getInsuranceType() {
		return insuranceType;
	}
	public void setInsuranceType(Integer insuranceType) {
		this.insuranceType = insuranceType;
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
	//0全保1单科 
	public String getInsuranceTypeName() {
		
			if (this.insuranceType==0) {
				return "全保";
			}else if (this.insuranceType==1 ) {
				return "单科";
			} 
			return "未知";
	}
	public void setInsuranceTypeName(String insuranceTypeName) {
		this.insuranceTypeName = insuranceTypeName;
	}








	public Double getCompensationAmount() {
		return compensationAmount;
	}








	public void setCompensationAmount(Double compensationAmount) {
		this.compensationAmount = compensationAmount;
	}








	public String getCompensationAmountName() {
		
		if (this.getInsuranceType()==0) {
			return "-";
		}else {
			if (this.getCompensationAmount()!=null) {
			DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
			 return decimalFormat.format( this.getCompensationAmount()) ;	 
			}
			return "0";
		}
		 
	}








	public void setCompensationAmountName(String compensationAmountName) {
		this.compensationAmountName = compensationAmountName;
	}






 
	
}
