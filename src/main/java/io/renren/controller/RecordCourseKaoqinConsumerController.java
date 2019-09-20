package io.renren.controller;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import io.renren.entity.CoursesEntity;
import io.renren.entity.RecordCourseCallbackDetailEntity;
import io.renren.pojo.log.ReplayCallbackPOJO;
import io.renren.service.CoursesService;
import io.renren.service.RecordCourseCallbackService;
import io.renren.service.SysProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 录播课程考勤数据消费者（从MQ把考勤明细写入数据库）
 * @author lihaifei
 */
@Controller
public class RecordCourseKaoqinConsumerController implements ChannelAwareMessageListener {

    protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	@Autowired
    private RecordCourseCallbackService recordCourseCallbackService;
	@Autowired
	private SysProductService sysProductService;
    @Autowired
    private CoursesService coursesService;


	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		TransactionStatus status = null;
        ReplayCallbackPOJO callback = null;
		//开启事务
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 事物隔离级别，开启新事务
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // 获得事务状态
		status = transactionManager.getTransaction(def);
		String body = null;
		try {
			body = new String(message.getBody(), "UTF-8");
			Gson gson = new Gson();
			callback = gson.fromJson(body, ReplayCallbackPOJO.class);
			if(null != callback) {
                //是否离线  0离线(缓存)  1回放回调
                //type:时间的类型 0进入 1退出 2离线(有进入和退出时间)
                //根据章节id查询课程id
                Integer recordId = Integer.parseInt(callback.getVideoId());//章节id
                Long courseId = recordCourseCallbackService.queryCourseIdByRecordId(recordId);

                //根据课程id查询课程对应的productId
                Map<String,Object> courseIdParam= new HashMap<String,Object>();
                courseIdParam.put("courseId", courseId);
                CoursesEntity courseEntity = coursesService.queryObjectbyCourseId(courseIdParam);
                if (courseEntity == null){
                    logger.error("course is not exists. courseId={}",courseId);
                    throw new RuntimeException("course is not exists. courseId="+courseId);
                }
                Long productId = courseEntity.getProductId();
                //根据productId查询系数
                Map<String,Object> productIdParam= new HashMap<String,Object>();
                productIdParam.put("productId", productId);
                Float recordCoefficient = sysProductService.queryObject(productIdParam).getRecordEfficient();

                if (callback.getType() == 2) {
                    //2离线(有进入和退出时间) 直接保存
                    RecordCourseCallbackDetailEntity entity = new RecordCourseCallbackDetailEntity();
                    entity.setCreateTime(new Date());
                    entity.setIsOfflive(callback.getIsOfflive());
                    entity.setJoinTime(callback.getJoinTime());
                    entity.setLeaveTime(callback.getLeaveTime());
                    entity.setUserId(callback.getUserId());
                    entity.setVideoEndTime(callback.getVideoEndTime());
                    entity.setRecordId(Long.parseLong(callback.getVideoId()));
					entity.setCourseId(courseEntity.getCourseId());
                    entity.setVideoId(callback.getVideoId());
                    entity.setVideoStartTime(callback.getVideoStartTime());
                    entity.setVideoTotalTime(callback.getVideoTotalTime());
                    entity.setProductId(productId);
                    entity.setRecordCoefficient(recordCoefficient);
                    recordCourseCallbackService.saveRecordCallbackDetail(entity);
                } else {
                    logger.error("参数错误，录播课程仅支持记录离线考勤明细，暂不支持在线观看等其他方式。ReplayCallbackPOJO={}",callback);
                }
            }
		} catch (Exception e) {
			try {
				e.printStackTrace();
				transactionManager.rollback(status);
				logger.error("queue RecordCourseKaoqinConsumerController error : {}",e);
			} catch (Exception e1) {
				e1.printStackTrace();
				//如果保存报错日志出错finally不会被执行，需要在此确认消费消息
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				logger.error("queue RecordCourseKaoqinConsumerController catch error : {}",e1);
			}
		} finally {
			try {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				// 提交事务
				if(!status.isCompleted()){
					transactionManager.commit(status);
				}
			} catch (Exception e) {
				e.printStackTrace();
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				logger.error("queue RecordCourseKaoqinConsumerController finally error : {}",e);
			}
		}
	}
}
