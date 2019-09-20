package io.renren.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;



/**
 * 微信推送消息记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-29 10:54:49
 */
public class WechatTemplateMsgEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//发送的微信服务号
	private String appid;
	//微信模板消息id
	private String templateId;
	//消息模板名称
	private String templateName;
	//模板类型(用于选择不同的推送模型): 1上课提醒(默认,暂时只有这种)
	private Integer templateType;
	//发送内容
	private String sendContent;
	//发送对象类型:  1排课2班级3手机号
	private Integer sendObjType;
	//发送对象内容
	private String sendObjValue;
	//发送时间类型 -1暂不发送1立即发送2定时发送
	private Integer sendTimeType;
	//发送时间
	private Date sendTimeValue;
	//发送状态: 0草稿1已发送2待发送
	private Integer sendStatus;
	//数据是否有效 0有效1删除
	private Integer dr;
	//产品线id
	private Long productId;
	//创建时间
	private Date createTime;
	//创建用户
	private Long createUser;
	//修改时间
	private Date updateTime;
	//修改用户
	private Long updateUser;

	//微信公众号名称
    private String title;
	//创建用户名称
    private String createUserName;
    //修改用户名称
    private String updateUserName;
    //产品线名称
    private String productName;
    //发送量
    private Long sendCount;
    //参数一到四
    private String keyWord1;
    private String keyWord2;
    private String keyWord3;
    private String keyWord4;



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
	 * 设置：发送的微信服务号
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}
	/**
	 * 获取：发送的微信服务号
	 */
	public String getAppid() {
		return appid;
	}
	/**
	 * 设置：微信模板消息id
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	/**
	 * 获取：微信模板消息id
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
	 * 设置：发送内容
	 */
	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}
	/**
	 * 获取：发送内容
	 */
	public String getSendContent() {
		return sendContent;
	}
	/**
	 * 设置：发送对象类型:  1班级2排课3手机号
	 */
	public void setSendObjType(Integer sendObjType) {
		this.sendObjType = sendObjType;
	}
	/**
	 * 获取：发送对象类型:  1班级2排课3手机号
	 */
	public Integer getSendObjType() {
		return sendObjType;
	}
	/**
	 * 设置：发送对象内容
	 */
	public void setSendObjValue(String sendObjValue) {
		this.sendObjValue = sendObjValue;
	}
	/**
	 * 获取：发送对象内容
	 */
	public String getSendObjValue() {
		return sendObjValue;
	}
	/**
	 * 设置：发送时间类型 -1暂不发送1立即发送2定时发送
	 */
	public void setSendTimeType(Integer sendTimeType) {
		this.sendTimeType = sendTimeType;
	}
	/**
	 * 获取：发送时间类型 -1暂不发送1立即发送2定时发送
	 */
	public Integer getSendTimeType() {
		return sendTimeType;
	}
	/**
	 * 设置：发送时间
	 */
	public void setSendTimeValue(Date sendTimeValue) {
		this.sendTimeValue = sendTimeValue;
	}
	/**
	 * 获取：发送时间
	 */
	public Date getSendTimeValue() {
		return sendTimeValue;
	}
	/**
	 * 设置：发送状态: 0草稿1已发送(11已发送,12待发送)2发送失败
	 */
	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}
	/**
	 * 获取：发送状态: 0草稿1已发送2待发送
	 */
	public Integer getSendStatus() {
		return sendStatus;
	}
	/**
	 * 设置：数据是否有效 0有效1删除
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * 获取：数据是否有效 0有效1删除
	 */
	public Integer getDr() {
		return dr;
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
	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：修改用户
	 */
	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * 获取：修改用户
	 */
	public Long getUpdateUser() {
		return updateUser;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getSendCount() {
        return sendCount;
    }

    public void setSendCount(Long sendCount) {
        this.sendCount = sendCount;
    }

    public String getKeyWord1() {
        return keyWord1;
    }

    public void setKeyWord1(String keyWord1) {
        this.keyWord1 = keyWord1;
    }

    public String getKeyWord2() {
        return keyWord2;
    }

    public void setKeyWord2(String keyWord2) {
        this.keyWord2 = keyWord2;
    }

    public String getKeyWord3() {
        return keyWord3;
    }

    public void setKeyWord3(String keyWord3) {
        this.keyWord3 = keyWord3;
    }

    public String getKeyWord4() {
        return keyWord4;
    }

    public void setKeyWord4(String keyWord4) {
        this.keyWord4 = keyWord4;
    }
}
