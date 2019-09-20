package io.renren.controller;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import io.renren.entity.*;
import io.renren.rest.persistent.KGS;
import io.renren.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import javax.print.attribute.standard.MediaSize;
import java.security.cert.TrustAnchor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DL on 2019/3/26.
 */
@Component
public class NcSchoolUserclassplanConsumer implements ChannelAwareMessageListener {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String HEAD = "NC_XXJH_";
    private static final String NCSCHOOL_ADAPTIVECOURSE = "ncSchool_adaptiveCourse";

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private NcSchoolClassplanLogService logService;
    @Autowired
    private NcSchoolUserclassplanService ncSchoolUserclassplanService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysConfigService sysConfigService;
    @Resource
    KGS ncLearningKGS;



    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        logger.error("nc open ncSchoolUserclassplan message" + " [x] Received '" + new String(message.getBody(), "UTF-8") + "'");
        NcSchoolUserclassplanEntity entity = new NcSchoolUserclassplanEntity();
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
            entity = gson.fromJson(body, NcSchoolUserclassplanEntity.class);//将消息体转成map

            synchronized (entity.getNcUserClassplanId()) {
                boolean isHandleFlag = ncSchoolUserclassplanService.isHandle(entity.getNcUserClassplanId(),entity.getNcModifiedTime());
                //判断是否启用面授自适应课程表
                boolean isAdaptiveCourse = true;
                String ncschool_adaptivecourse = sysConfigService.getValue("NCSCHOOL_ADAPTIVECOURSE", "0");
                if ("1".equals(ncschool_adaptivecourse)){
                    //判断面授自适应表中是否有该课程
                    isAdaptiveCourse = ncSchoolUserclassplanService.queryAdaptiveCourse(entity.getCourseclassplanId());
                }
                //1.保存进库
                entity.setCreateTime(new Date());
                entity.setIsAdaptiveCourse(isAdaptiveCourse == true ? 1 : 0);
                ncSchoolUserclassplanService.save(entity);
                //2.生成学习计划和学习计划详情
                if (isHandleFlag && isAdaptiveCourse){
                    handleUserClassplan(entity);
                }
            }
        } catch (Exception e) {
            try {
                transactionManager.rollback(status);//回滚
                NcSchoolClassplanLogEntity logEntity = new NcSchoolClassplanLogEntity();
                logEntity.setCreateTime(new Date());
                logEntity.setJsonContent(body);
                //错误日志类型: 1:排课,2课次,3学员信息
                logEntity.setType(3);
                logService.save(logEntity);
                e.printStackTrace();
                logger.error("ncSchoolUserclassplan onMessage error Cause by:{}, message body=[{}]", e.toString(),body);
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

    private void handleUserClassplan(NcSchoolUserclassplanEntity entity) {
        //开通
        if (entity.getFlag() == 1){
            //判断是否已经存在
            boolean isExist = ncSchoolUserclassplanService.isExistByNcUserClassplanId(entity.getNcUserClassplanId());
            if (!isExist){
                saveLearningAndDetail(entity);
            }
            //关闭
        }else {
            ncSchoolUserclassplanService.updateLearningAndDetail(entity.getNcUserClassplanId());
        }
    }

    private void saveLearningAndDetail(NcSchoolUserclassplanEntity entity) {
        //根据nc_school_pk（对应部门表的nc_id）查找对应部门
        SysDeptEntity deptEntity = this.sysDeptService.queryObjectByNcId(entity.getNcSchoolId());
        //查询蓝鲸学员id
        Map map = new HashMap();
        map.put("mobilePhoneNo",entity.getMobile());
        Long userId=usersService.getUserIdByMobilePhoneNo(map);
        NcSchoolLearningEntity learningEntity = new NcSchoolLearningEntity();
        learningEntity.setMobile(entity.getMobile());
        learningEntity.setCreateTime(new Date());
        learningEntity.setDr(1);
        learningEntity.setNcClassType(entity.getNcClassType());
        learningEntity.setLearningNo(HEAD + ncLearningKGS.nextId());
        learningEntity.setNcSchoolId(entity.getNcSchoolId());
        learningEntity.setNcSchoolName(entity.getNcSchoolName());
        learningEntity.setNcUserClassplanId(entity.getNcUserClassplanId());
        learningEntity.setUserName(entity.getUserName());
        if (entity.getUserId() != null && entity.getUserId() > 0){
            learningEntity.setUserId(entity.getUserId());
        }else {
            learningEntity.setUserId(userId);
        }
        if (deptEntity != null){
            learningEntity.setDeptId(deptEntity.getDeptId());
        }else {
            logger.info("接收nc线下排课 NCSchoolClassplanConsumer,deptEntity为空,ncSchoolId={}",entity.getNcSchoolId());
        }
        ncSchoolUserclassplanService.saveLearning(learningEntity);
        NcSchoolLearningDetailEntity detailEntity = new NcSchoolLearningDetailEntity();
        detailEntity.setCourseclassplanId(entity.getCourseclassplanId());
        detailEntity.setCreateTime(new Date());
        detailEntity.setDr(1);
        detailEntity.setLearningId(learningEntity.getLearningId());
        detailEntity.setMobile(entity.getMobile());
        detailEntity.setTimeStamp(entity.getTimeStamp());
       detailEntity.setUserName(entity.getUserName());
        if (entity.getUserId() != null && entity.getUserId() > 0){
            detailEntity.setUserId(entity.getUserId());
        }else {
            detailEntity.setUserId(userId);
        }
        ncSchoolUserclassplanService.saveLearningDetail(detailEntity);
    }
}
