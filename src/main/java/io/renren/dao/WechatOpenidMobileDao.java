package io.renren.dao;

import io.renren.entity.WechatOpenidMobileEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户绑定微信openId记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-25 16:09:15
 */
@Repository
public interface WechatOpenidMobileDao extends BaseMDao<WechatOpenidMobileEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

    int queryTotalByClassplanId(Map<String, Object> map);

    List<WechatOpenidMobileEntity> queryListByClassplanId(Map<String, Object> map);

    int queryTotalByClassId(Map<String, Object> map);

    List<WechatOpenidMobileEntity> queryListByClassId(Map<String, Object> map);

    /**
     * 根据手机号码查询用户openid
     * @param paramMap
     * @return
     */
    List<WechatOpenidMobileEntity> queryListByMobleList(Map<String, Object> paramMap);

    /**
     * 根据openid获取用户手机
     * @param openid
     * @return
     */
    String queryMobileByOpenid(String openid);
}
