package io.renren.service;

import io.renren.entity.UsersEntity;
import io.renren.entity.manage.Users;

import java.util.List;
import java.util.Map;

/**
 * 前台用户表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-27 10:26:24
 */
public interface UsersService {
	
	UsersEntity queryObject(Long userId);
	
	List<UsersEntity> queryList(Map<String, Object> map);
	//查询用户信息显示到浮沉中
	List<UsersEntity> queryLayList(Map<String,Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	Integer queryUserId(Map<String,Object> map);
	
	void save(UsersEntity users);
	
	void update(UsersEntity users);
	
	void delete(Long userId);
	
	void deleteBatch(Long[] userIds);
	
	void pause(Long[] userIds);
	
	void resume(Long[] userIds);
	
	/**
	 * 手机号码是否存在
	 * @param users
	 * @return
	 */
	boolean checkMobile(Map<String, Object> map);
	
	boolean mobileExist(Map<String, Object> map);
	
	long getUserIdByMobilePhoneNo(Map<String, Object> map);

	String getNickNameByMobilePhoneNo(Map<String, Object> map);

	int countUserIdByMobilePhoneNo(Map<String, Object> map);
	
	void restPsw(String psw , Long userId);

	/**
	 * 根据学员的手机号更新姓名
	 * @param phone
	 * @param user_name
	 */
	void updateNameByPhone(String phone, String user_name);

	int checkUser(String mobile);

	Users findByMobile(String mobile);
	/**
	 * 随机生成密码 如getRandomPass(4,4)则是 4位字母4位数字 ：GcNx8822
	 * @param strLen 大小写字母位数
	 * @param numLen 数字位数
	 * @return
	 */
	String getRandomPass(Integer strLen, Integer numLen);
	/**
	 * 密码转为MD5加密
	 * @param password
	 * @return
	 */
	String getPasswordToMD5(String password);
	/**
	 * 遍历列表取得最近登陆的时间 
	 * @param userList
	 */
	void setLastLoginTime(List<UsersEntity> userList);
	 
	void updateByUserId(UsersEntity users);
}
