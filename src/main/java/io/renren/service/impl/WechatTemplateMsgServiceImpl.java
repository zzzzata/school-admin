package io.renren.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hq.common.idgen.IdWorkerSequence;
import io.renren.dao.WechatOpenidMobileDao;
import io.renren.dao.WechatTemplateMsgDao;
import io.renren.entity.WechatOpenidMobileEntity;
import io.renren.entity.WechatTemplateMsgEntity;
import io.renren.pojo.wechat.WechatMsgDetailPOJO;
import io.renren.service.WechatTemplateMsgService;
import io.renren.utils.DateUtils;
import io.renren.utils.ShiroUtils;
import io.renren.utils.WechatTemplatePushUtil;
import io.renren.utils.http.HttpClientUtil4_3;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("wechatTemplateMsgService")
public class WechatTemplateMsgServiceImpl implements WechatTemplateMsgService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("#{application['pom.msg.domain']}")
    private String MSG_DOMAIN;

	@Autowired
	private WechatTemplateMsgDao wechatTemplateMsgDao;
    @Autowired
    private WechatOpenidMobileDao wechatOpenidMobileDao;
	
	@Override
	public WechatTemplateMsgEntity queryObject(Map<String, Object> map){
		return wechatTemplateMsgDao.queryObject(map);
	}
	
	@Override
	public List<WechatTemplateMsgEntity> queryList(Map<String, Object> map){
		return wechatTemplateMsgDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return wechatTemplateMsgDao.queryTotal(map);
	}
	
	@Override
	public void save(WechatTemplateMsgEntity wechatTemplateMsg){
	    //处理生成的id过长问题(大于17位前端精度丢失)
        String idStr = String.valueOf(IdWorkerSequence.nextId());
        long id = Long.valueOf(idStr.substring(2,idStr.length()));
        wechatTemplateMsg.setId(id);
        //处理消息内容的换行符
        wechatTemplateMsg.setSendContent(wechatTemplateMsg.getSendContent().replaceAll("\r|\n", ""));
	    if (wechatTemplateMsg.getSendTimeType() == 1 ){
	        wechatTemplateMsg.setSendTimeValue(new Date());
        }
        wechatTemplateMsg.setCreateUser(ShiroUtils.getUserId());
	    wechatTemplateMsg.setUpdateUser(ShiroUtils.getUserId());
	    wechatTemplateMsg.setUpdateTime(new Date());
	    wechatTemplateMsg.setCreateTime(new Date());
		wechatTemplateMsgDao.save(wechatTemplateMsg);
		//发送时间类型 -1暂不发送1立即发送2定时发送
		if(wechatTemplateMsg.getSendTimeType() > 0){
            String errorMsg = sendMsg(wechatTemplateMsg);
            if (StringUtils.isNotBlank(errorMsg)){
                //修改发送状态为失败
                wechatTemplateMsgDao.updateSendStatus(wechatTemplateMsg.getId());
            }
        }


    }

    private String sendMsg(WechatTemplateMsgEntity wechatTemplateMsg) {
        try {
            //调用消息系统发送(可以根据templateType调用不同的消息模板,目前暂时只有上课提醒模板templateType=1)
            String pushJson = WechatTemplatePushUtil.getShangkeTemplateMsg(wechatTemplateMsg.getTemplateId(),wechatTemplateMsg.getKeyWord1(),
                    wechatTemplateMsg.getKeyWord2(),wechatTemplateMsg.getKeyWord3(),wechatTemplateMsg.getKeyWord4());
            //发送时间
            String sendTime = DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss");
            if (wechatTemplateMsg.getSendTimeType() == 2 ){
                sendTime = DateUtils.format(wechatTemplateMsg.getSendTimeValue(),"yyyy-MM-dd HH:mm:ss");
            }
            //接收人的openid
            List<String> openidList = new ArrayList<>();
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("appid",wechatTemplateMsg.getAppid());
            if (wechatTemplateMsg.getSendObjType() == 1){
                //排课
                paramMap.put("classplanId",wechatTemplateMsg.getSendObjValue());
                List<WechatOpenidMobileEntity> wechatOpenidMobileList = wechatOpenidMobileDao.queryListByClassplanId(paramMap);
                openidList = handleOpenidMobileList(wechatOpenidMobileList);
            }else if (wechatTemplateMsg.getSendObjType() == 2){
                //班级
                paramMap.put("classId",wechatTemplateMsg.getSendObjValue());
                List<WechatOpenidMobileEntity> wechatOpenidMobileList = wechatOpenidMobileDao.queryListByClassId(paramMap);
                openidList = handleOpenidMobileList(wechatOpenidMobileList);
            }else if (wechatTemplateMsg.getSendObjType() == 3){
                //手机号码
                String mobileStr = wechatTemplateMsg.getSendObjValue();
                if (StringUtils.isNotBlank(mobileStr)){
                    String[] split = mobileStr.split(",");
                    if (null != split && split.length > 0){
                        paramMap.put("mobileList",split);
                        List<WechatOpenidMobileEntity> wechatOpenidMobileList = wechatOpenidMobileDao.queryListByMobleList(paramMap);
                            openidList = handleOpenidMobileList(wechatOpenidMobileList);
                        }
                    }
            }
            String sendUrl = MSG_DOMAIN+"/wechat/sendMsg";
            Map<String,String> sendParamMap = new HashMap<>();
            sendParamMap.put("msgNo",wechatTemplateMsg.getId()+"");
            sendParamMap.put("productId",wechatTemplateMsg.getProductId()+"");
            sendParamMap.put("appid",wechatTemplateMsg.getAppid());
            sendParamMap.put("openidList",String.join(",",openidList));
            sendParamMap.put("msgContent",pushJson);
            sendParamMap.put("sendTime",sendTime);
            logger.info("发送微信模板消息,调用消息url:{},params:{}",sendUrl,sendParamMap.toString());
            String sendResult = HttpClientUtil4_3.sendHttpPost(sendUrl, sendParamMap);
            JSONObject jsonObject = JSONObject.parseObject(sendResult);
            if (jsonObject.getIntValue("code") == 200){
                //修改发送量
                wechatTemplateMsgDao.updateSendCount(wechatTemplateMsg.getId(),openidList.size());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送微信模板消息,调用消息系统失败,cause:{}",e.toString());
            return "发送失败";
        }
        return "发送失败";
    }

    private List<String> handleOpenidMobileList(List<WechatOpenidMobileEntity> wechatOpenidMobileList) {
        List<String> openidList = new ArrayList<>();
        if (wechatOpenidMobileList != null && wechatOpenidMobileList.size() > 0){
            for (WechatOpenidMobileEntity wechatOpenidMobileEntity : wechatOpenidMobileList) {
                if (StringUtils.isNotBlank(wechatOpenidMobileEntity.getOpenid())){
                    openidList.add(wechatOpenidMobileEntity.getOpenid());
                }
            }
        }
        return openidList;
    }

    @Override
	public void update(WechatTemplateMsgEntity wechatTemplateMsg){
		wechatTemplateMsgDao.update(wechatTemplateMsg);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		wechatTemplateMsgDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		wechatTemplateMsgDao.deleteBatch(map);
	}

    @Override
    public String deleteMsg(long id) {
        try {
            //调用消息系统删除
            String deleteUrl = MSG_DOMAIN+"/wechat/cancelSendMsg";
            Map<String,String> deleteMap = new HashMap<>();
            deleteMap.put("msgNo",id+"");
            logger.info("删除消息url:{},params:{}",deleteUrl,deleteMap.toString());
            String deleteResult = HttpClientUtil4_3.sendHttpPost(deleteUrl, deleteMap);
            JSONObject jsonObject = JSONObject.parseObject(deleteResult);
            if (jsonObject.getIntValue("code") == 200){
                Long updateUser = ShiroUtils.getUserId();
                wechatTemplateMsgDao.delete(id,updateUser,new Date());
                return null;
            }
            return "消息系统删除失败";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除消息失败,cause:{}",e.toString());
            return "消息系统删除失败";
        }
    }

    @Override
    public List<WechatMsgDetailPOJO> wechatMsgDetailList(Map<String, Object> map) {
        try {
            List<WechatMsgDetailPOJO> resultList = new ArrayList<>();
            String detailUrl = MSG_DOMAIN+"/wechat/getStatusByMsgNo";
            String msgNo = String.valueOf(map.get("id"));
            String offset = "0";
            String limit = "5000";
            offset = String.valueOf( map.get("offset"));
            limit = String.valueOf( map.get("limit"));
            Map<String,String> detailMap = new HashMap<>();
            detailMap.put("msgNo",msgNo);
            detailMap.put("offset",offset);
            detailMap.put("limit",limit);
            logger.info("获取发送消息详情url:{},params:{}",detailUrl,detailMap.toString());
            String detailResult = HttpClientUtil4_3.sendHttpPost(detailUrl, detailMap);
            JSONObject jsonObject = JSONObject.parseObject(detailResult);
            logger.info("获取发送消息详情消息系统返回内容:{}",jsonObject);
            if (jsonObject.getIntValue("code") == 200){
                resultList = JSONArray.parseArray(jsonObject.getString("data"),WechatMsgDetailPOJO.class);
                if (resultList.size() > 0 ){
                    for (WechatMsgDetailPOJO wechatMsgDetailPOJO : resultList) {
                        String  sendContent = wechatTemplateMsgDao.queryContentById(wechatMsgDetailPOJO.getMsgNo());
                        String mobile = wechatOpenidMobileDao.queryMobileByOpenid(wechatMsgDetailPOJO.getOpenid());
                        wechatMsgDetailPOJO.setMsgContent(sendContent);
                        wechatMsgDetailPOJO.setMobile(mobile);
                    }
                }
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取发送消息详情失败,cause:{}",e.toString());
            return new ArrayList<>();
        }
    }

    @Override
    public WechatMsgDetailPOJO wechatMsgCount(Map<String, Object> map) {
        try {
            WechatMsgDetailPOJO pojo = new WechatMsgDetailPOJO();
            String detailUrl = MSG_DOMAIN+"/wechat/getStatusCount";
            String msgNo = String.valueOf(map.get("id"));
            Map<String,String> detailMap = new HashMap<>();
            detailMap.put("msgNo",msgNo);
            logger.info("获取发送消息数量url:{},params:{}",detailUrl,detailMap.toString());
            String detailResult = HttpClientUtil4_3.sendHttpPost(detailUrl, detailMap);
            JSONObject jsonObject = JSONObject.parseObject(detailResult);
            logger.info("获取发送数量消息系统返回内容:{}",jsonObject);
            if (jsonObject.getIntValue("code") == 200){
                pojo = JSONObject.parseObject(jsonObject.getString("data"),WechatMsgDetailPOJO.class);
            }
            return pojo;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取发送消息数量详情失败,cause:{}",e.toString());
            return new WechatMsgDetailPOJO();
        }
    }
}
