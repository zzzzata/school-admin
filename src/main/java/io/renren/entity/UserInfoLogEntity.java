package io.renren.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import io.renren.pojo.NcUserInfoMessagePOJO;
import io.renren.utils.ClassTypeUtils;
  

/**
 * 用户中心变更信息错误表存
 * @author lintf
 */
public class UserInfoLogEntity {
	/**
	 * 正常
	 */
	public final static Integer OK=0;
	public final static Integer OK_change=4;

	public final static Integer OK_change_MOBILE=5;
	public final static Integer OK_change_NCID=8;
	
	/**
	 * 一条结果有误的
	 */
	public final static Integer ONE_RESULT_ERROR=101;
	/**
	 * 多条结果有误的
	 */
	public final static Integer MANY_RESULT_ERROR=102;
	/**
	 * NC中的userid与现在的不一样的
	 */
	public final static Integer  USERID_NOT_THE_SAME_ERROR=103;
	/**
	 * 初始化时ncid不一致时 
	 */
	public final static Integer  USERID_INIT_NCID_NOT_THE_SAME_ERROR=104;
	public final static Integer  USERID_INIT_MOBILE_NOT_IN_NC_ERROR=105;
	//
	private Long id;
	//
	private Date createtime;
	//用户手机
	private String userMobileStr;
	//用户id
	private String  userIdStr;
	private String  ncUserIdStr;
	 
	//推送内容
	private String pushJson;
	//备注
	private String remark;
	//推送过来的userid
	private Long MessageUserId;
	//推送过来的电话
	private String MessageUserMobile;
	//推送过来的NcId
	private String MessageNcUserId;
	

	private List<String> ncUserIdList;
	
	private List<String> userMobileList;
	//用户id
	private List<Long>  userIdList;
	 
	private Integer type=0;
	
	
	
	public UserInfoLogEntity() {}
	
	 
	
	public UserInfoLogEntity(NcUserInfoMessagePOJO m, List<UsersEntity> userList) {
		
		this.MessageNcUserId=m.getNcUserId();
		this.MessageUserId=m.getUserId();
		this.MessageUserMobile=m.getMobile();
		this.userMobileList= new ArrayList<String>();
		this.userIdList= new ArrayList<Long>();
		this.ncUserIdList=new ArrayList<String>();
		UsersEntity u= new UsersEntity(m);
		
		
		
		userList.add(u);
		for ( UsersEntity ue:userList) {
			 if (ue.getChangeMobile()==1) {
				 this.type=UserInfoLogEntity.OK_change_MOBILE;
			 }
			if (ue.getNcId()!=null) {
				this.ncUserIdStr=(this.ncUserIdStr==null?"":this.ncUserIdStr+",")+ue.getNcId();
			}
			if (ue.getMobile()!=null) {
				this.userMobileStr=(this.userMobileStr==null?"":this.userMobileStr+",")+ue.getMobile();
			}
			if (ue.getUserId()!=null) {
				this.userIdStr=(this.userIdStr==null?"":this.userIdStr+",")+ue.getUserId();
			}
			
			
			this.userMobileList.add(ue.getMobile());
			this.userIdList.add(ue.getUserId()==null?0L:ue.getUserId());
			this.ncUserIdList.add(ue.getNcId()==null?"null":ue.getNcId());
		}
		Gson gson=new Gson();
		 String json = gson.toJson(userList).toString();
		 this.pushJson=json;
		 this.createtime= new Date();
		
	}
	
	
	
	
	
	
	
	
	
	
	 
	public Long getMessageUserId() {
		return MessageUserId;
	}





	public void setMessageUserId(Long messageUserId) {
		MessageUserId = messageUserId;
	}





	public String getMessageUserMobile() {
		return MessageUserMobile;
	}





	public void setMessageUserMobile(String MessageUserMobile) {
		this.MessageUserMobile = MessageUserMobile;
	}





	public String getMessageNcUserId() {
		return MessageNcUserId;
	}





	public void setMessageNcUserId(String messageNcUserId) {
		MessageNcUserId = messageNcUserId;
	}





	public List<String> getNcUserIdList() {
		return ClassTypeUtils.StringToStringList(this.ncUserIdStr);
	}





	public void setNcUserIdList(List<String> ncUserIdList) {
		this.ncUserIdList = ncUserIdList;
	}





	public List<String> getUserMobileList() {
		
		
		return ClassTypeUtils.StringToStringList(this.userMobileStr);
	}
	public void setUserMobileList(List<String> userMobileList) {
		this.userMobileList = userMobileList;
	}
	public List<Long> getUserIdList() {

		return ClassTypeUtils.StringToLongList(this.userIdStr);
	}
	public void setUserIdList(List<Long> userIdList) {
		this.userIdList = userIdList;
	}
	
	
	
	
	public String getNcUserIdStr() {
		return ncUserIdStr;
	}





	public void setNcUserIdStr(String ncUserIdStr) {
		this.ncUserIdStr = ncUserIdStr;
	}





	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getUserMobileStr() {
		return userMobileStr;
	}
	public void setUserMobileStr(String userMobileStr) {
		this.userMobileStr = userMobileStr;
	}
	public String getUserIdStr() {
		return userIdStr;
	}
	public void setUserIdStr(String userIdStr) {
		this.userIdStr = userIdStr;
	}
	public String getPushJson() {
		return pushJson;
	}
	public void setPushJson(String pushJson) {
		this.pushJson = pushJson;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}





	public Integer getType() {
		return type;
	}





	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	
	
}
