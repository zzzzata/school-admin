package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 推送模板消息排课关联表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-10 09:35:41
 */
public class WechatClassplanTemplateEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//排课id
	private String classplanId;
	//微信推送消息模板id
	private String templateId;
	//消息模板名称
	private String templateName;
	//模板类型(用于选择不同的推送模型): 1上课提醒(默认,暂时只有这种)
	private Integer templateType;
	//微信公众号id
	private String appid;
	//产品线id
	private Long productId;
	//是否有效 0有效1删除
	private Integer dr;
	//创建时间
	private Date createTime;
	//创建用户
	private Long createUser;
	//排课名称
    private String classplanName;
    //产品线名称
    private String productName;
    //创建用户
    private String createUserName;
    //微信公众号title
    private String wechatTitle;
    //修改时间
    private Date updateTime;
    //修改用户id
    private Long updateUser;
    //修改用户名称
    private String updateUserName;
    //微信模板内容
    private String content;

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
	 * 设置：排课id
	 */
	public void setClassplanId(String classplanId) {
		this.classplanId = classplanId;
	}
	/**
	 * 获取：排课id
	 */
	public String getClassplanId() {
		return classplanId;
	}
	/**
	 * 设置：微信推送消息模板id
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	/**
	 * 获取：微信推送消息模板id
	 */
	public String getTemplateId() {
		return templateId;
	}
	/**
	 * 设置：消息模板名称
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	/**
	 * 获取：消息模板名称
	 */
	public String getTemplateName() {
		return templateName;
	}
	/**
	 * 设置：模板类型(用于选择不同的推送模型): 1上课提醒(默认,暂时只有这种)
	 */
	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}
	/**
	 * 获取：模板类型(用于选择不同的推送模型): 1上课提醒(默认,暂时只有这种)
	 */
	public Integer getTemplateType() {
		return templateType;
	}
	/**
	 * 设置：微信公众号id
	 */
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
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

    public String getClassplanName() {
        return classplanName;
    }

    public void setClassplanName(String classplanName) {
        this.classplanName = classplanName;
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

    public String getWechatTitle() {
        return wechatTitle;
    }

    public void setWechatTitle(String wechatTitle) {
        this.wechatTitle = wechatTitle;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
