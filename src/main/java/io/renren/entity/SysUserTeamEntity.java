package io.renren.entity;

import java.io.Serializable;

/**
 * 用户与团队对应关系表
 * @author hewanxian
 * @version 1.0
 * @date 2019/6/14 16:44
 */
public class SysUserTeamEntity implements Serializable {

    private static final long serialVersionUID = -1484460985259614174L;

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 团队id
     */
    private Long teamId;

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

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
