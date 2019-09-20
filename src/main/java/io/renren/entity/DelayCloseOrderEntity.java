package io.renren.entity;
/**
 * 延期关订单
 * @author lintf 
 *
 */
public class DelayCloseOrderEntity {
	private Long id;//主键
	private Long orderId;//订单id
	private String ncId;//报名表id
	private String ncCode;//报名表号
	private String mobile;//电话号
	private Long userId;//用户id
	private Long creationTime;//创建时间
	private Integer closeStatus;//状态   0：未关闭 1 已经关闭 
	private Long actionTime;//关闭时的执行的时间;
	private Integer sourceType;//来源 0 无 1.nc删除订单 2.NC退费订单 3.贷款过期 
	private Integer dr;//1 删除 0没删除
	
	
	public DelayCloseOrderEntity( ) { 
	}
	
	
	public DelayCloseOrderEntity(MallOrderEntity order) {
		this.dr=0;
		this.closeStatus=0;
		this.orderId=order.getOrderId();
		this.ncId=order.getNcId();
		this.ncCode=order.getNcCode();
		this.userId=order.getUserId();
		
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}
	public Integer getCloseStatus() {
		return closeStatus;
	}
	public void setCloseStatus(Integer closeStatus) {
		this.closeStatus = closeStatus;
	}
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	 
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getActionTime() {
		return actionTime;
	}
	public void setActionTime(Long actionTime) {
		this.actionTime = actionTime;
	}
	public Integer getDr() {
		return dr;
	}
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	
	

}
