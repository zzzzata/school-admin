package io.renren.pojo;

import java.util.Date;

/**
 * 用于从消息队列中取得nc用户信息的
 * @author lintf 
 *
 */
public class NcUserInfoMessagePOJO {
	//{"ncUserId":"0001DS100000000SSL7P","syncTime":1544006399000,"idCard":"512501197203035172","sex":1,"schoolId":"0001A510000000000L8L","mobile":"15611160001","userName":"小雨1","userId":"0"}

	private String userName;
	private Integer sex;
	private String ncUserId;
	private Long userId;
	private String mobile;
	private Long syncTime;
	    
	private String email; 
	private String schoolId;
 
	private String idCard; 

	
	
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getNcUserId() {
		return ncUserId;
	}
	public void setNcUserId(String ncUserId) {
		this.ncUserId = ncUserId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getSyncTime() {
		return syncTime;
	}
	public void setSyncTime(Long syncTime) {
		this.syncTime = syncTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
 
	
	
	
	
}
