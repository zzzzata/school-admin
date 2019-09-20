package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 微信公众号号信息记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-09 10:34:42
 */
public class WechatAccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//公众号标题
	private String title;
	//公众号id
	private String appid;
	//公众号密钥
	private String appSecret;
	//产品线id
	private Long productId;
	//备注说明
	private String remark;
	//是否有效 0有效1删除
	private Integer dr;
	//创建时间
	private Date createTime;
	//创建用户
	private Long createUser;
	//产品线名称
    private String productName;
    //创建用户名称
    private String createUserName;

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
	 * 设置：公众号标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：公众号标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：公众号id
	 */
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
	 * 设置：公众号密钥
	 */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	/**
	 * 获取：公众号密钥
	 */
	public String getAppSecret() {
		return appSecret;
	}
	/**
	 * 设置：产品线id
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	/**
	 * 获取：产品线id
	 */
	public Long getProductId() {
		return productId;
	}
	/**
	 * 设置：备注说明
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注说明
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：是否有效 0有效1删除
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * 获取：是否有效 0有效1删除
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
	 * 设置：创建用户
	 */
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：创建用户
	 */
	public Long getCreateUser() {
		return createUser;
	}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
