package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 自适应推送商品表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-29 10:07:14
 */
public class AdaptiveGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//商品名称
	private Long mallGoodId;
	//商品名称
	private String mallGoodName;
	//是否是ios内购商品:0 不是 1是
	private Integer isIapGood;
	//是否有效:0有效1无效
	private Integer dr;
	//产品线
    private Long productId;
    //产品线名称
    private String productName;

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
	 * 设置：商品名称
	 */
	public void setMallGoodId(Long mallGoodId) {
		this.mallGoodId = mallGoodId;
	}
	/**
	 * 获取：商品名称
	 */
	public Long getMallGoodId() {
		return mallGoodId;
	}
	/**
	 * 设置：商品名称
	 */
	public void setMallGoodName(String mallGoodName) {
		this.mallGoodName = mallGoodName;
	}
	/**
	 * 获取：商品名称
	 */
	public String getMallGoodName() {
		return mallGoodName;
	}
	/**
	 * 设置：是否是ios内购商品:0 不是 1是
	 */
	public void setIsIapGood(Integer isIapGood) {
		this.isIapGood = isIapGood;
	}
	/**
	 * 获取：是否是ios内购商品:0 不是 1是
	 */
	public Integer getIsIapGood() {
		return isIapGood;
	}
	/**
	 * 设置：是否有效:0有效1无效
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * 获取：是否有效:0有效1无效
	 */
	public Integer getDr() {
		return dr;
	}

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
