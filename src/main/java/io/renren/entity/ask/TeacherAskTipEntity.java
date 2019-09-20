package io.renren.entity.ask;

import java.io.Serializable;
import java.util.Date;

/**
 * 教师提问标签权限
 * @author chen xin yu
 * @date 2019-04-30 17:01
 */
public class TeacherAskTipEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**用户id*/
    private Long userId;
    /**提问标签id串，逗号隔开*/
    private String tipIds;
    /**无限次提问权限 0 关闭 1 开启 默认只有一次提问权限*/
    private Integer unlimitedAsk;
    /**创建人名称*/
    private String createUserName;
    /**创建人id*/
    private Long createUserId;
    /**创建时间*/
    private Date createTime;
    /**更新人名称*/
    private String updateUserName;
    /**更新人id*/
    private Long updateUserId;
    /**更新时间*/
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTipIds() {
        return tipIds;
    }

    public void setTipIds(String tipIds) {
        this.tipIds = tipIds;
    }

    public Integer getUnlimitedAsk() {
        return unlimitedAsk;
    }

    public void setUnlimitedAsk(Integer unlimitedAsk) {
        this.unlimitedAsk = unlimitedAsk;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
