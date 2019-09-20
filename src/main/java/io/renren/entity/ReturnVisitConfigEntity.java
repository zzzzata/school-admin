package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-17 17:19:52
 */
public class ReturnVisitConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//编号
	private Long id;
	//产品线
	private Long productId;
	//产品线名称
	private String productName;
	//生成频率天数
	private Integer returnNum;
	//删除
	private Integer dr;
	//创建时间
	private Date createTime;
	//更新人(id)
	private Long updatePerson;
	//更新时间
	private Date updateTime;
	//更新人名字
	private String updatePersonName;

	public String getUpdatePersonName() {
		return updatePersonName;
	}

	public void setUpdatePersonName(String updatePersonName) {
		this.updatePersonName = updatePersonName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(Long updatePerson) {
		this.updatePerson = updatePerson;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

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
	/**
	 * 设置：产品线
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	/**
	 * 获取：产品线
	 */
	public Long getProductId() {
		return productId;
	}
	/**
	 * 设置：生成频率天数
	 */
	public void setReturnNum(Integer returnNum) {
		this.returnNum = returnNum;
	}
	/**
	 * 获取：生成频率天数
	 */
	public Integer getReturnNum() {
		return returnNum;
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
}
