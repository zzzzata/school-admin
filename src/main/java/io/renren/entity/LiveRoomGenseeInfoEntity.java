package io.renren.entity;

import java.util.Date;

/**
 * 直播间展示互动站点信息表
 * @author ouchujian
 */
public class LiveRoomGenseeInfoEntity {
	
	private Long id;
	//展视互动账号
	private String genseeLoginname; 
	//展视互动密码
	private String genseePassword; 
	//展视互动回放记录地址
	private String genseeWebcastVodLog; 
	//展视互动直播记录
	private String genseeWebcastLogUrl; 
	//展视互动域名
	private String genseeDomain; 
	//备注
	private String remark;
	//创建日期
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGenseeLoginname() {
		return genseeLoginname;
	}
	public void setGenseeLoginname(String genseeLoginname) {
		this.genseeLoginname = genseeLoginname;
	}
	public String getGenseePassword() {
		return genseePassword;
	}
	public void setGenseePassword(String genseePassword) {
		this.genseePassword = genseePassword;
	}
	public String getGenseeWebcastVodLog() {
		return genseeWebcastVodLog;
	}
	public void setGenseeWebcastVodLog(String genseeWebcastVodLog) {
		this.genseeWebcastVodLog = genseeWebcastVodLog;
	}
	public String getGenseeWebcastLogUrl() {
		return genseeWebcastLogUrl;
	}
	public void setGenseeWebcastLogUrl(String genseeWebcastLogUrl) {
		this.genseeWebcastLogUrl = genseeWebcastLogUrl;
	}
	public String getGenseeDomain() {
		return genseeDomain;
	}
	public void setGenseeDomain(String genseeDomain) {
		this.genseeDomain = genseeDomain;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
