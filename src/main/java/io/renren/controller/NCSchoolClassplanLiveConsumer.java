package io.renren.controller;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import io.renren.entity.NcSchoolClassplanLogEntity;
import io.renren.entity.NcSchoolCourseclassplanEntity;
import io.renren.entity.NcSchoolCourseclassplanLiveEntity;
import io.renren.service.NcSchoolClassplanLogService;
import io.renren.service.NcSchoolCourseclassplanLiveService;
import io.renren.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DL on 2019/2/18.
 */
@Component
public class NCSchoolClassplanLiveConsumer implements ChannelAwareMessageListener {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 时间转换格式
     */
    private final static String PATTERN = "yyyy-MM-dd HH:mm:ss";
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private NcSchoolClassplanLogService logService;
    @Autowired
    private NcSchoolCourseclassplanLiveService liveService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        logger.error("nc open ncSchoolClassplanLive message" + " [x] Received '" + new String(message.getBody(), "UTF-8") + "'");
        NcSchoolCourseclassplanLiveEntity entity = new NcSchoolCourseclassplanLiveEntity();
        TransactionStatus status = null;//初始化状态
        /**
         * 开启事务
         */
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务
        status = transactionManager.getTransaction(def); // 获得事务状态
        String body = new String(message.getBody(), "UTF-8");//获取消息体
        try {
            Gson gson = new Gson();
            entity = gson.fromJson(body, NcSchoolCourseclassplanLiveEntity.class);//将消息体转成map
            Map paramMap = new HashMap();
            paramMap.put("courseclassplanLiveId",entity.getCourseclassplanLiveId());
            boolean saveFlag = liveService.isExistByClassplanLiveId(paramMap);
            paramMap.put("ncModifiedTime",entity.getNcModifiedTime());
            boolean updateFlag = liveService.isExistByClassplanLiveId(paramMap);
            if (!saveFlag){
                entity.setCreateTime(new Date());
                entity.setModifiedTime(new Date());
                Date endTime = DateUtils.addMinute(120, DateUtils.parse(entity.getStartTime(),PATTERN));
                entity.setEndTime(DateUtils.format(endTime,PATTERN));
                liveService.save(entity);
            }
            if (updateFlag){
                Date endTime = DateUtils.addMinute(120, DateUtils.parse(entity.getStartTime(),PATTERN));
                entity.setEndTime(DateUtils.format(endTime,PATTERN));
                entity.setModifiedTime(new Date());
                liveService.update(entity);
            }
        } catch (Exception e) {
            try {
                transactionManager.rollback(status);//回滚
                NcSchoolClassplanLogEntity logEntity = new NcSchoolClassplanLogEntity();
                logEntity.setCreateTime(new Date());
                logEntity.setJsonContent(body);
                //错误日志类型: 1:排课,2课次,3学员信息
                logEntity.setType(2);
                //logEntity.setCause(e.toString());
                logService.save(logEntity);
                e.printStackTrace();
                logger.error("NCSchoolClassplanLiveConsumer onMessage error Cause by:{}, message body=[{}]", e.toString(),body);
            } catch (Exception e2) {
                e2.printStackTrace();
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } finally{
            try {
                transactionManager.commit(status);// 提交事务
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e) {
                logger.error("finally,事务提交出错! exception message:{}, message body=[{}]", e.toString(),body);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        }
    }

}
