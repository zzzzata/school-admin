package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 回访
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-17 17:20:06
 */
public class ReturnVisitEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//回访人
	private Long createPerson;
	//预计回访日期
	private String expectTime;
	//回访时间
	private String returnTime;
	//回访状态
	private Integer returnStatus;
	//回访内容
	private String returnContent;
	//报读主键
	private Long recordSignId;
	//删除
	private Integer dr;

	private Long userId;
	/**
	 * 设置：编号
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：编号
	 */
	public Long getId() {
		return id;
	}

	public Long getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(Long createPerson) {
		this.createPerson = createPerson;
	}

	public String getExpectTime() {
		return expectTime;
	}

	public void setExpectTime(String expectTime) {
		this.expectTime = expectTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	/**
	 * 设置：回访状态
	 */
	public void setReturnStatus(Integer returnStatus) {
		this.returnStatus = returnStatus;
	}
	/**
	 * 获取：回访状态
	 */
	public Integer getReturnStatus() {
		return returnStatus;
	}
	/**
	 * 设置：回访内容
	 */
	public void setReturnContent(String returnContent) {
		this.returnContent = returnContent;
	}
	/**
	 * 获取：回访内容
	 */
	public String getReturnContent() {
		return returnContent;
	}
	/**
	 * 设置：报读主键
	 */
	public void setRecordSignId(Long recordSignId) {
		this.recordSignId = recordSignId;
	}
	/**
	 * 获取：报读主键
	 */
	public Long getRecordSignId() {
		return recordSignId;
	}
	/**
	 * 设置：删除
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * 获取：删除
	 */
	public Integer getDr() {
		return dr;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
