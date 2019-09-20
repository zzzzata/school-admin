package io.renren.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
 

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;

import io.renren.utils.http.HttpClientUtil;
import io.renren.utils.http.HttpClientUtil4_3; 

public class SendChannelIdUtil {

	//{"code":200,"message":"操作成功","data":{"count":1}}

    public static String sendChannelId(String msgHost,Long userId , String channelId , Integer type) throws IOException{
 
        Map<String , String> map = new HashMap<>(); 
        JSONObject user = new JSONObject();
        user.put("channel", channelId);
        user.put("type", type);
        
        JSONArray userArray=new JSONArray();
        userArray.add(user);
        
        map.put("user_id", ""+userId);
    	map.put("groups", userArray.toString());
    	
      return  HttpClientUtil.getInstance().sendHttpPost(msgHost+"/group/updateMsgGroup", map) ;
         
    
    }
    public static void main(String[] args) throws IOException {
    	SendChannelIdUtil.sendChannelId("http://10.0.99.25:2814", 12342342L, "public", 1);
	}
}
