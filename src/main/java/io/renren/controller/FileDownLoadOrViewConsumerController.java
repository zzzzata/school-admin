package io.renren.controller;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import io.renren.pojo.FilePOJO;
import io.renren.service.FileDownLoadViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FileDownLoadOrViewConsumerController implements ChannelAwareMessageListener {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FileDownLoadViewService fileDownLoadViewService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        FilePOJO filePOJO = null;
        String body = null;
        try {
            body = new String(message.getBody(), "UTF-8");
            Gson gson = new Gson();
            filePOJO = gson.fromJson(body, FilePOJO.class);
            if (null != filePOJO) {
                int type = filePOJO.getType();
                Long teachFileId = filePOJO.getTeachfileId();
                Long fileDetailId = filePOJO.getFileDetailId();
                //下载类型，文件下载次数加1
                if (type == 1) {
                    fileDownLoadViewService.downloadFile(teachFileId, fileDetailId);
                    logger.info("文件下载次数增加成功");
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    //查看类型，文件查看次数加1
                } else if (type == 0) {
                    fileDownLoadViewService.viewFile(teachFileId, fileDetailId);
                    logger.info("文件查看次数增加成功");
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                }
            }
        } catch (Exception e) {
            logger.error("文件下载查看次数增加队列消费失败！");
            throw new RuntimeException("文件下载查看次数增加队列消费失败！");
        }
    }
}
