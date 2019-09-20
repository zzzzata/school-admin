package io.renren.dao;

import io.renren.entity.WechatTemplateMsgEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
 * 微信推送消息记录表
 * 
 * @author hq
 * @email hq@hq.com
 * @date 2019-05-29 10:54:49
 */
@Repository
public interface WechatTemplateMsgDao extends BaseMDao<WechatTemplateMsgEntity> {
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

    /**
     * 删除消息记录
     * @param id  记录id
     * @param updateUser 删除者
     * @param date 删除时间
     */
    void delete(@Param("id") long id, @Param("updateUser")Long updateUser, @Param("updateTime")Date date);

    /**
     * 修改发送状态
     * @param id
     */
    void updateSendStatus(Long id);

    /**
     * 修改消息发送量
     * @param id
     * @param size
     */
    void updateSendCount(@Param("id") Long id, @Param("sendCount")int sendCount);

    /**
     * 获取发送内容
     * @param msgNo
     * @return
     */
    String queryContentById(long msgNo);
}
