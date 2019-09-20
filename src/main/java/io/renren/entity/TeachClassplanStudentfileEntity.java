package io.renren.entity;


import java.util.Date;

/**
 * <p>
 * 排课学员课程资料关系表
 * </p>
 *
 * @author hzr
 * @since 2018-12-27
 */

public class TeachClassplanStudentfileEntity {


    /**
     * id
     */
    private Long id;

    /**
     * 排课计划PK
     */
    private String classplanId;

    /**
     * 学员课程资料PK
     */
    private Long teachStudentFileId;

    /**
     * 创建者
     */
    private Long sysUserId;

    /**
     * 最近修改用户
     */
    private Long modifySysUserId;

    /**
     * 最近修改时间
     */
    private Date modifyTime;

    public void setId(Long id) {
        this.id = id;
    }

    public void setClassplanId(String classplanId) {
        this.classplanId = classplanId;
    }

    public void setTeachStudentFileId(Long teachStudentFileId) {
        this.teachStudentFileId = teachStudentFileId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public void setModifySysUserId(Long modifySysUserId) {
        this.modifySysUserId = modifySysUserId;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public String getClassplanId() {
        return classplanId;
    }

    public Long getTeachStudentFileId() {
        return teachStudentFileId;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public Long getModifySysUserId() {
        return modifySysUserId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }
}
