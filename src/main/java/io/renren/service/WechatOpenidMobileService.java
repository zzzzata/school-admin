package io.renren.service;

import io.renren.entity.WechatOpenidMobileEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户绑定微信openId记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-25 16:09:15
 */
public interface WechatOpenidMobileService {
	
		
	WechatOpenidMobileEntity queryObject(Map<String, Object> map);
	
	List<WechatOpenidMobileEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WechatOpenidMobileEntity wechatOpenidMobile);
	
	void update(WechatOpenidMobileEntity wechatOpenidMobile);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);

    /**
     * 查询排课学员列表数量
     * @param map
     * @return
     */
    int queryTotalByClassplanId(Map<String, Object> map);

    /**
     *查询排课学员列表数据
     * @param map
     * @return
     */
    List<WechatOpenidMobileEntity> queryListByClassplanId(Map<String, Object> map);

    /**
     *查询班级学员列表数量
     * @param map
     * @return
     */
    int queryTotalByClassId(Map<String, Object> map);

    /**
     *查询排课学员列表数据
     * @param map
     * @return
     */
    List<WechatOpenidMobileEntity> queryListByClassId(Map<String, Object> map);
}
