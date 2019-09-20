package io.renren.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 商品-题库试卷对照表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-03-28 11:39:28
 */
public class GoodsPaperEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Long id;
	//商品id
	private Long goodId;
	//题库试卷id
	private Long paperId;
	//题库试卷名称
	private String paperName;
	//创建时间
	private Date createdTime;
	//修改时间
	private Date updatedTime;
	//创建者id
	private Long creator;
	//修改者id
	private Long updater;
	//软删除0：未删除 1：删除
	private Integer dr;
	
	//商品名称
	private String goodName;
	//创建人
	private String creatorName;
	//修改人
	private String updaterName;

	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getUpdaterName() {
		return updaterName;
	}
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}
	/**
	 * 设置：主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：商品id
	 */
	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}
	/**
	 * 获取：商品id
	 */
	public Long getGoodId() {
		return goodId;
	}
	/**
	 * 设置：题库试卷id
	 */
	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}
	/**
	 * 获取：题库试卷id
	 */
	public Long getPaperId() {
		return paperId;
	}
	/**
	 * 设置：题库试卷名称
	 */
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	/**
	 * 获取：题库试卷名称
	 */
	public String getPaperName() {
		return paperName;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreatedTime() {
		return createdTime;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdatedTime() {
		return updatedTime;
	}
	/**
	 * 设置：创建者id
	 */
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	/**
	 * 获取：创建者id
	 */
	public Long getCreator() {
		return creator;
	}
	/**
	 * 设置：修改者id
	 */
	public void setUpdater(Long updater) {
		this.updater = updater;
	}
	/**
	 * 获取：修改者id
	 */
	public Long getUpdater() {
		return updater;
	}
	/**
	 * 设置：软删除0：未删除 1：删除
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * 获取：软删除0：未删除 1：删除
	 */
	public Integer getDr() {
		return dr;
	}
	@Override
	public String toString() {
		return "GoodsPaperEntity [id=" + id + ", goodId=" + goodId + ", paperId=" + paperId + ", paperName=" + paperName
				+ ", createdTime=" + createdTime + ", updatedTime=" + updatedTime + ", creator=" + creator
				+ ", updater=" + updater + ", dr=" + dr + ", goodName=" + goodName + ", creatorName=" + creatorName
				+ ", updaterName=" + updaterName + "]";
	}
}
