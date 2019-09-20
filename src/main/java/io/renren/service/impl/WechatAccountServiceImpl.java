package io.renren.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.pojo.wechat.WechatTemplatePOJO;
import io.renren.utils.MD5Util;
import io.renren.utils.http.HttpClientUtil4_3;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import io.renren.dao.WechatAccountDao;
import io.renren.entity.WechatAccountEntity;
import io.renren.service.WechatAccountService;
import io.renren.utils.Constant;



@Service("wechatAccountService")
public class WechatAccountServiceImpl implements WechatAccountService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("#{application['pom.wechat.service.domain']}")
    private String  wechatServiceDomain = "";

	@Autowired
	private WechatAccountDao wechatAccountDao;
	
	@Override
	public WechatAccountEntity queryObject(Map<String, Object> map){
		return wechatAccountDao.queryObject(map);
	}
	
	@Override
	public List<WechatAccountEntity> queryList(Map<String, Object> map){
        List<WechatAccountEntity> list = wechatAccountDao.queryList(map);
        if (list != null && list.size() > 0){
            for (WechatAccountEntity wechatAccountEntity : list) {
                String appSecret = wechatAccountEntity.getAppSecret();
                if (StringUtils.isNotBlank(appSecret) && appSecret.length() > 5){
                appSecret = appSecret.substring(0,5)+"******"+appSecret.substring(appSecret.length()-5,appSecret.length());
                wechatAccountEntity.setAppSecret(appSecret);
                }
            }
        }
		return list;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return wechatAccountDao.queryTotal(map);
	}
	
	@Override
	public void save(WechatAccountEntity wechatAccount){
		wechatAccountDao.save(wechatAccount);
	}
	
	@Override
	public void update(WechatAccountEntity wechatAccount){
		wechatAccountDao.update(wechatAccount);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		wechatAccountDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		wechatAccountDao.deleteBatch(map);
	}

    @Override
    public  List<WechatTemplatePOJO> queryTemplateList(Map<String, Object> map) {
        List<WechatTemplatePOJO> list = new ArrayList<>();
        String appid = (String) map.get("appid");
        String signStr = "appid="+appid;
        String sign = MD5Util.encrypt(signStr,MD5Util.getKey());
        String templateListUrl = wechatServiceDomain+"/wechat/server/msgTemplate/getAllTemplate?appid="+appid+"&sign="+sign;
        logger.info("获取微信模板消息列表url:{}",templateListUrl);
        try {
            String result = HttpClientUtil4_3.get(templateListUrl, new HashMap<String, String>());
            logger.info("获取微信模板消息列表返回内容result:{}",result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (200 ==jsonObject.getIntValue("code")){
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray template_list = data.getJSONArray("template_list");
                if (template_list != null) {
                    list = JSONArray.parseArray(template_list.toJSONString(), WechatTemplatePOJO.class);
                    return list;
                }
                logger.info("微信模板消息列表为空template_list:{}",template_list);
            }
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取微信模板消息列表报错:{}",e.toString());
        }
        return new ArrayList<>();
    }


}
