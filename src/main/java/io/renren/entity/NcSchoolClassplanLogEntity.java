package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 同步NC排课信息错误日记记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-02-18 09:11:41
 */
public class NcSchoolClassplanLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//错误日志类型: 1:排课,2课次,3学员信息
	private Integer type;
	//队列内容
	private String jsonContent;
	//创建时间
	private Date createTime;
	//错误内容
    private String cause;

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
	 * 设置：错误日志类型: 1:排课,2课次,3学员信息
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：错误日志类型: 1:排课,2课次,3学员信息
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：队列内容
	 */
	public void setJsonContent(String jsonContent) {
		this.jsonContent = jsonContent;
	}
	/**
	 * 获取：队列内容
	 */
	public String getJsonContent() {
		return jsonContent;
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

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
