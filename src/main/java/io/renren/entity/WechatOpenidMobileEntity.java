package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户绑定微信openId记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-25 16:09:15
 */
public class WechatOpenidMobileEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//微信公众号id
	private String appid;
	//微信openId
	private String openid;
	//用户手机
	private String mobile;
	//学员微信id
	private String wechatId;
	//学员微信昵称
	private String wechatNickname;
	//蓝鲸用户id
	private Long userId;
	//创建时间
	private Date createTime;
	//蓝鲸用户名称
    private String userName;
    //微信公众号名称
    private String wechatTitle;

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
	 * 设置：微信公众号id
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}
	/**
	 * 获取：微信公众号id
	 */
	public String getAppid() {
		return appid;
	}
	/**
	 * 设置：微信openId
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：微信openId
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * 设置：用户手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：用户手机
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：学员微信id
	 */
	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	/**
	 * 获取：学员微信id
	 */
	public String getWechatId() {
		return wechatId;
	}
	/**
	 * 设置：学员微信昵称
	 */
	public void setWechatNickname(String wechatNickname) {
		this.wechatNickname = wechatNickname;
	}
	/**
	 * 获取：学员微信昵称
	 */
	public String getWechatNickname() {
		return wechatNickname;
	}
	/**
	 * 设置：蓝鲸用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：蓝鲸用户id
	 */
	public Long getUserId() {
		return userId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWechatTitle() {
        return wechatTitle;
    }

    public void setWechatTitle(String wechatTitle) {
        this.wechatTitle = wechatTitle;
    }
}
