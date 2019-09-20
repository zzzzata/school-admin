package io.renren.entity;

import java.util.Date;

/**
 * User: mumu
 * Date: 2018/12/5
 * Time: 16:16
 */
public class SysUserTypeEntity {
    private long id;
    private long sysUserId;
    private int sysUserType;
    private Date createTime;

    public SysUserTypeEntity(){

    }

    public SysUserTypeEntity(long sysUserId,int sysUserType){
        this.sysUserId = sysUserId;
        this.sysUserType = sysUserType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public int getSysUserType() {
        return sysUserType;
    }

    public void setSysUserType(int sysUserType) {
        this.sysUserType = sysUserType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
