package io.renren.controller;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import io.renren.entity.*;
import io.renren.service.*;
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
public class NCSchoolClassplanConsumer implements ChannelAwareMessageListener {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private NcSchoolCourseclassplanService ncSchoolCourseclassplanService;
    @Autowired
    private NcSchoolCourseclassplanLiveService NcSchoolCourseclassplanLiveService;
    @Autowired
    private NcSchoolClassplanLogService logService;
    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        logger.error("nc open ncSchoolClassplan message" + " [x] Received '" + new String(message.getBody(), "UTF-8") + "'");
        NcSchoolCourseclassplanEntity entity = new NcSchoolCourseclassplanEntity();
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
            entity = gson.fromJson(body, NcSchoolCourseclassplanEntity.class);//将消息体转成map
            Map paramMap = new HashMap();
            paramMap.put("courseclassplanId",entity.getCourseclassplanId());
            boolean saveFlag = ncSchoolCourseclassplanService.isExistByClassplanId(paramMap);
            paramMap.put("ncModifiedTime",entity.getNcModifiedTime());
            boolean updateFlag = ncSchoolCourseclassplanService.isExistByClassplanId(paramMap);
            SysDeptEntity deptEntity = this.sysDeptService.queryObjectByNcId(entity.getNcSchoolId());
            if (deptEntity != null){
                entity.setDeptId(deptEntity.getDeptId());
            }else {
                logger.info("接收nc线下排课 NCSchoolClassplanConsumer,deptEntity为空,ncSchoolId={}",entity.getNcSchoolId());
            }
            if (!saveFlag){
                entity.setCreateTime(new Date());
                entity.setModifiedTime(new Date());
                entity.setCourseclassplanName(entity.getNcClassName());
                entity.setProductId(entity.getProductId() == null ? 7L : entity.getProductId() );
                ncSchoolCourseclassplanService.save(entity);
            }
            if (updateFlag){
                entity.setModifiedTime(new Date());
                entity.setCourseclassplanName(entity.getNcClassName());
                ncSchoolCourseclassplanService.update(entity);
                if (entity.getDr() == 1){
                    Map liveParamMap = new HashMap();
                    liveParamMap.put("courseclassplanId",entity.getCourseclassplanId());
                    liveParamMap.put("dr",1);
                    liveParamMap.put("modifiedTime",new Date());
                    //删除课次内容
                    NcSchoolCourseclassplanLiveService.updateByClassplanId(liveParamMap);
                }
            }
        } catch (Exception e) {
            try {
                transactionManager.rollback(status);//回滚
                NcSchoolClassplanLogEntity logEntity = new NcSchoolClassplanLogEntity();
                logEntity.setCreateTime(new Date());
                logEntity.setJsonContent(body);
                //错误日志类型: 1:排课,2课次,3学员信息
                logEntity.setType(1);
                //logEntity.setCause(e.toString());
                logService.save(logEntity);
                e.printStackTrace();
                logger.error("NCSchoolClassplanConsumer onMessage error Cause by:{}, message body=[{}]", e.toString(),body);
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
