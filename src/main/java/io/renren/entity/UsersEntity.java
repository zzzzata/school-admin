package io.renren.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.renren.pojo.NcUserInfoMessagePOJO;
import io.renren.utils.Constant;

/**
 * 前台用户表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-27 10:26:24
 */
public class UsersEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// ID
	private Long userId;
	// 平台ID
	private String schoolId;
	// 是否删除 0.未删除 1.删除 用于软删除
	private Integer dr = Constant.DR.NORMAL.getValue();
	// 手机号码(登录账号)
	private String mobile;
	// 用户昵称
	private String nickName;
	// 头像地址
	private String pic;
	// 状态 0：禁用 1：正常
	private Integer status = (int) Constant.Status.RESUME.getValue();
	// 性别0女，1男，2保密
	private Integer sex;
	// 登录密码
	private String password;
	// 创建用户
	private Long creator;
	// 创建时间
	private Date creationTime;
	// 修改用户
	private Long modifier;
	// 修改时间
	private Date modifiedTime;
	// 最近登录时间
	private Date lastLoginTime;
	// 最近登录IP
	private String lastLoginIp;
	// 来源 0.正常注册;1.后台注册;2.NC导入
	private Integer channel;
	// 备注
	private String remake;
	// 性别名称
	private String sexName;
	// 邮箱
	private String email;
	// 平台ID
	private Long deptId;
	// nc_id
	private String ncId;
	// 真实姓名
	private String realName;
	// 身份证
	private String idCard;

	private Long activityId;

	private String channelType;

	// 真实的密码（只传递不存库）
	private String truePassWord;

	// 以下是不保存到库中的字段
	private Integer ChangeMobile = 0;// 0是没有改变电话 1是修改了号码

	private String asePwStr;// 通过ACE对称加密后的密码
	private String schoolPk;// NC那边的校区主键

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Long getModifier() {
		return modifier;
	}

	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getRemake() {
		return remake;
	}

	public void setRemake(String remake) {
		this.remake = remake;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getNcId() {
		return ncId;
	}

	public void setNcId(String ncId) {
		this.ncId = ncId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getTruePassWord() {
		return truePassWord;
	}

	public void setTruePassWord(String truePassWord) {
		this.truePassWord = truePassWord;
	}

	public Integer getChangeMobile() {
		return ChangeMobile;
	}

	public void setChangeMobile(Integer changeMobile) {
		ChangeMobile = changeMobile;
	}

	public String getAsePwStr() {
		return asePwStr;
	}

	public void setAsePwStr(String asePwStr) {
		this.asePwStr = asePwStr;
	}

	@Override
	public String toString() {
		return "UsersEntity [userId=" + userId + ", schoolId=" + schoolId + ", dr=" + dr + ", mobile=" + mobile
				+ ", nickName=" + nickName + ", pic=" + pic + ", status=" + status + ", sex=" + sex + ", password="
				+ password + ", creator=" + creator + ", creationTime=" + creationTime + ", modifier=" + modifier
				+ ", modifiedTime=" + modifiedTime + ", lastLoginTime=" + lastLoginTime + ", lastLoginIp=" + lastLoginIp
				+ ", channel=" + channel + ", remake=" + remake + ", sexName=" + sexName + ", email=" + email
				+ ", deptId=" + deptId + ", ncId=" + ncId + "]";
	}

	public UsersEntity() {

	}

	public UsersEntity(NcUserInfoMessagePOJO m) {
		this.nickName = m.getUserName();
		this.realName = m.getUserName();
		this.mobile = m.getMobile();
		this.ncId = m.getNcUserId();
		this.sex = m.getSex();
		this.email = m.getEmail();
		this.idCard = m.getIdCard();
		this.schoolPk = m.getSchoolId();
	}

	// 用于发送到队列中用的map，与 SSO的对齐，但是如果有空的则也要有字段
	public Map<String, Object> toSSOMap() {

		Map<String, Object> map = new HashMap<String, Object>();
		if (this.userId == null || this.mobile == null) {
			return map;
		}
		map.put("userId", this.userId == null ? 0 : this.userId);
		map.put("mobile", this.mobile == null ? 0 : this.mobile);
		map.put("nickName", this.nickName == null ? "" : this.nickName);
		map.put("avatar", this.pic == null ? "" : this.pic);
		map.put("status", this.status == null ? 1 : this.status);
		map.put("sex", this.sex == null ? 2 : this.sex);
		map.put("email", this.email == null ? "" : this.email);
		map.put("passWord", this.password == null ? "" : this.password);
		map.put("dr", this.dr == null ? 0 : this.dr);
		map.put("channel", this.channel == null ? 0 : this.channel);
		map.put("schoolId", this.schoolId == null ? 0 : this.schoolId);
		// map.put("unionId", this.unionId==null?0:this.unionId);
		map.put("idCard", this.idCard == null ? 0 : this.idCard);
		map.put("channel", this.channel == null ? 0 : this.channel);
		map.put("name", this.realName == null ? "" : this.getRealName());
		map.put("msgType", 0);
		map.put("ncId", this.getNcId() == null ? "" : this.getNcId());

		return map;

	}
}
