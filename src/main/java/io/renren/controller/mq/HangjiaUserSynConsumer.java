package io.renren.controller.mq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

import io.renren.entity.UsersEntity;
import io.renren.entity.manage.*;
import io.renren.service.UsersService;
import io.renren.service.manage.AppAccountService;
import io.renren.service.manage.AppCommodityService;
import io.renren.service.manage.AppService;
import io.renren.service.manage.UserAppService;
import io.renren.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static io.renren.utils.RedisUtil.getJedis;

/**
 * NC报名消息队列消费者
 * 
 * @author hq
 */
public class HangjiaUserSynConsumer implements ChannelAwareMessageListener {

	private static final Logger LOG = LoggerFactory.getLogger(HangjiaUserSynConsumer.class);

	@Autowired
	private UsersService usersService;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		channel.basicQos(0, 1, false);
		LOG.info((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())) + "	收到队列信息" + " [x] Received '"
				+ message + "'");

		TransactionStatus status = null;
		try {
			String body = new String(message.getBody(), "UTF-8");
			JSONObject json = JSONObject.parseObject(body);
			String entity = json.getString("entity");
			if (!"users".equals(entity)) {
				return;
			}
			UsersEntity users = json.getObject("after", UsersEntity.class);
			String type = json.getString("type");
			// 开启事务
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务
			status = transactionManager.getTransaction(def); // 获得事务状态
			if ("I".equalsIgnoreCase(type)) {
				usersService.save(users);
			}
			if ("U".equalsIgnoreCase(type)) {
				usersService.update(users);
			}
			if ("D".equalsIgnoreCase(type)) {
				users = json.getObject("before", UsersEntity.class);
				usersService.delete(users.getUserId());
			}
			// 提交事务
			transactionManager.commit(status);
		} catch (Exception e) {
			// 回滚
			transactionManager.rollback(status);
			System.out.println(e.toString());
			// 记录报错日志
			LOG.error("MessageConsumer error", e);
		} finally {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}

	}

}
