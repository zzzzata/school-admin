package io.renren.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.renren.dao.AppUserChannelsDao;
import io.renren.entity.AppUserChannelsEntity;
import io.renren.service.AppUserChannelsService;
import io.renren.utils.SendChannelIdUtil;
 

/**
 * 
 * @author Created by LiuHai 2018/02/05
 *
 */
@Service("appUserChannelsService")
public class AppUserChannelsServiceImpl implements AppUserChannelsService {

	   protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AppUserChannelsDao appUserChannelsDao;
	
	@Override
	public void save(AppUserChannelsEntity entity) {
		this.appUserChannelsDao.save(entity);

	}
	 
	
	private static String msgHost = "";
	@Value("${pom.msg.domain}") 
	private void setMsgHost(String msgHost){
		this.msgHost = msgHost;
	}
	
	
	
	
	
	@Override
	public  int   userInfoBindPublicChannel(long userId) { 
		
	   try {
		 
           List<String> list = new ArrayList<String>();
           list.add(String.valueOf(userId));
           list.add("public");
           for (String channel : list) {
               AppUserChannelsEntity entity = new AppUserChannelsEntity();
               entity.setUserId(userId);
               entity.setChannelId(channel);
                save(entity);
               SendChannelIdUtil.sendChannelId(msgHost, userId, channel, 1);
           }
           return 1;
       } catch (Exception e) {

    	 logger.error("绑定用户渠道出错！userId:{},errorMesage:{}",userId,e);
          // e.printStackTrace();
       }
	
	
	return 0;
	
}
}
