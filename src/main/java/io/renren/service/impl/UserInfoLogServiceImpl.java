package io.renren.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import io.renren.dao.UserInfoLogDao;
import io.renren.entity.UserInfoLogEntity;
import io.renren.entity.UsersEntity;
import io.renren.service.UserInfoLogService;
@Service("userInfoLogService")
public class UserInfoLogServiceImpl implements UserInfoLogService {

	   protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserInfoLogDao userInfoLogDao;
	@Override
	public void userUpdateLog(Map<String, Object> oldUserMap, Map<String, Object> newUserMap) {
	 
		userInfoLogSave(oldUserMap,newUserMap,1);
	}

	@Override
	public void userInsertLog(UsersEntity user) {
		userInfoLogSave(null,user.toSSOMap(),0);
		
	}
	 
	public  void  userInfoLogSave(Map<String,Object> oldMap,Map<String,Object> newMap,Integer type)
	{
		try {
		UserInfoLogEntity u= new UserInfoLogEntity();
		if (newMap!=null&&newMap.get("userId")!=null) {
			u.setUserIdStr(newMap.get("userId").toString()); //由于是蓝鲸后台操作的 所以只有一个userid
			if (type==1) {//修改的时候进入判断

				String oldMobile= oldMap.get("mobile")==null?"~": oldMap.get("mobile").toString();
				String newMobile=newMap.get("mobile")==null?"~": newMap.get("mobile").toString();
				 if (!oldMobile.equals(newMobile)) {
					u.setType(55);;//55带表修改了号码
					u.setUserMobileStr(oldMobile+","+newMobile);
				}else {
					u.setUserMobileStr( newMobile);
					u.setType(54);;
				}
			}else if (type==0){
				u.setType(50);//是新增加的用户
			}
			
			 Gson gson = new Gson();
			 List<Map<String,Object>> userList= new ArrayList<Map<String,Object>>();
			 if (oldMap!=null&&oldMap.size()>0) {
				 userList.add(oldMap);
			 }			 
			u.setPushJson( gson.toJson(userList).toString());
			 
		   u.setCreatetime( new Date());
		   userInfoLogDao.save(u);
		}else {
		    logger.error("保存蓝鲸前台修改用户出错！,取到的用户信息为空:{}.",newMap);
		}
		
		}catch (Exception es ) {
			logger.error("保存蓝鲸前台修改用户出错！旧用户：{}，新用户：{}，修改类型:{}.",oldMap,newMap,type);
		}
		
	}
	
	
	
	
}
