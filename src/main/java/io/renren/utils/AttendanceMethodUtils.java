package io.renren.utils;

import java.util.List;

import io.renren.entity.SysUserEntity;
import io.renren.service.SysConfigService;
import io.renren.service.SysUserRoleService;

public class AttendanceMethodUtils {
	
    private static SysConfigService sysConfigService;
    
    private static  SysUserRoleService sysUserRoleService;
    
    static Byte[] obj = new Byte[1];
    
    final static String KEY_ROLE = "attendance_role";
    
	/**
	 * 如果用户拥有管理员或主管权限，则具有所有权限
	 * @return true
	 */
	public static boolean isAdmin(SysUserEntity user) {
		if(sysConfigService == null) {
			synchronized (obj) {
				if(sysConfigService == null) {
					sysConfigService = SpringContextUtils.getBean("sysConfigService",SysConfigService.class);
					sysUserRoleService = SpringContextUtils.getBean("sysUserRoleService",SysUserRoleService.class);
				}
			}
		}
		//admin不需要判断
		if(user == null || Constant.SUPER_ADMIN_USER_ID.equals(user.getUserId())) {
			return true;
		}
		List<Long> roleList = sysUserRoleService.queryRoleIdList(user.getUserId());
		//读取配置表的角色
		String rolesStr = sysConfigService.getValue(KEY_ROLE, "");
		//加入超级管理员的roleId
		rolesStr = Constant.SUPER_ADMIN_ROLE_ID + "," + rolesStr;
		String[] roles = rolesStr.split(",");
		for(String role : roles) {
			//正则表达式判断roleId是否为正整数
			if(MatcherUtil.matcher(role, MatcherUtil.POSITIVE_INTEGER)) {
				if(roleList.contains(Long.valueOf(role))) {
					return true;
				}
			}
		}
		return false;
	}
}
