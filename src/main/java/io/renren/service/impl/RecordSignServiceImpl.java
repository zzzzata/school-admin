package io.renren.service.impl;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import io.renren.entity.*;
import io.renren.pojo.RecordSignPOJO;
import io.renren.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.CourseAbnormalOrderDao;
import io.renren.dao.RecordSignDao;
import io.renren.pojo.CourseAbnormalOrderPOJO;
import io.renren.pojo.RecordSignPOJO;
import io.renren.service.RecordSignService;

import io.renren.utils.DateUtils;

@Service
public class RecordSignServiceImpl implements RecordSignService {
    @Autowired
    private RecordSignDao recordSignDao;
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CourseAbnormalOrderDao courseAbnormalOrderDao;
    //回访老师
    private final long RETURN_TYPE_OLDER = 1L;
    //回访超期
    private final long RETURN_TYPE_CHAOQI = 2L;
    //新生-最新已回访
    private final long RETURN_TYPE_NEWRETURN = 5L;
    //新生-全部已回访
    private final long RETURN_TYPE_ALLRETURN = 6L;


    @Override
    public void saveRecordSign(RecordSignEntity e) {
        try {

            recordSignDao.save(e);
            logger.info("RecordSignEntity   saved successfully.RecordSignEntity={} ", e);
        } catch (Exception es) {
            logger.error("RecordSignEntity save has Error.RecordSignEntity={},error_message={}", e, es);

        }
    }

    @Override
    public List<RecordSignEntity> queryList(Map<String, Object> queryMap) {

        return recordSignDao.queryList(queryMap);

    }

    @Override
    public void upDateRecordSign(RecordSignEntity e) {
        try {

            recordSignDao.update(e);
            logger.info("RecordSignEntity   updated successfully.RecordSignEntity={} ", e);
        } catch (Exception es) {
            logger.error("RecordSignEntity updated has Error.RecordSignEntity={},error_message={}", e, es);

        }

    }

    @Override
    public OutputStream RecordSignExport(List<Long> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void RecordSignCheck(RecordInfoEntity r, OrderMessageConsumerEntity order, MallOrderEntity m) {
        //1.先根据订单消息和学员档案组装成报读信息
        RecordSignEntity s = new RecordSignEntity(order, m);
        if (s.getUserId() == null) {
            s.setUserId(r.getUserId());
        }
        s.setRecordId(r.getRecordId());
	
	/*	if (s.getNcId()==null) {
			logger.error ("RecordSignEntity  check ncid is null! vo is {}", s);
			
			return ;
		}*/


        boolean hasUpdate = false;//用于判断是否更新了,如果没有更新的则后面会新增

        //2.检测用ncID是否能匹配 如果能匹配的则修改

        if (s.getNcId() != null) {
            Map<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.put("ncId", s.getNcId());
            List<RecordSignEntity> s_list = this.queryList(queryMap);
            if (s_list != null && s_list.size() > 0) {
                for (RecordSignEntity old : s_list) {
                    if (old.getNcId().equals(s.getNcId())) {
                        old.signUpdate(old, s, m);
                        this.upDateRecordSign(old);
                        hasUpdate = true;
                    }

                }


            }

        }
        //3. 如果按ncId都取不到的 则改为按orderId来判断
        if (!hasUpdate) {


            if (s.getOrderId() != null && s.getOrderId() > 0) {
                Map<String, Object> ordermap = new HashMap<String, Object>();
                ordermap.put("orderId", s.getOrderId());
                ordermap.put("dr", 0); //只取dr=0的订单
                List<RecordSignEntity> orderList = this.queryList(ordermap);
                if (orderList != null && orderList.size() > 0) {
                    for (RecordSignEntity ss : orderList) {
                        if (ss.getNcId() == null && ss.getOrderId().equals(s.getOrderId()) && ss.getUserId().equals(s.getUserId())) {
                            ss.setNcId(s.getNcId());
                            this.upDateRecordSign(ss);
                            hasUpdate = true;

                        }
                    }
                }


            }


        }
        //4.都取不到的则新增
        if (!hasUpdate) {
            //要有订单号的才会生成报读信息
            if (s.getOrderId() != null && s.getOrderId().longValue() > 0) {
                System.out.println(s.toString());
                this.saveRecordSign(s);
            }

        }


    }

    @Override
    public List<RecordSignPOJO> queryPOJOList(Map<String, Object> queryMap) {
        if (queryMap.get("returnType") != null || queryMap.get("expectTime") != null) {
            if (queryMap.get("returnType") != null && RETURN_TYPE_OLDER == (long) queryMap.get("returnType")) {
                //当前时间距离最后一次回访超过30天
                queryMap.put("lastReturnTime", DateUtils.getDateBefore(new Date(), 30));
            }
            if (queryMap.get("returnType") != null && RETURN_TYPE_CHAOQI == (long) queryMap.get("returnType")) {
                queryMap.put("nowTime", DateUtils.format(new Date()));
            }
            queryMap.put("lastReturnTime", DateUtils.getDateBefore(new Date(), 30));
            return recordSignDao.queryPOJOListByReturnVisit(queryMap);
        }
        return recordSignDao.queryPOJOList(queryMap);
    }

    @Override
    public int queryPOJOTotal(Map<String, Object> queryMap) {

        if (queryMap.get("returnType") != null || queryMap.get("expectTime") != null) {
            if (queryMap.get("returnType") != null && RETURN_TYPE_OLDER == (long) queryMap.get("returnType")) {
                //当前时间距离最后一次回访超过30天
                queryMap.put("lastReturnTime", DateUtils.getDateBefore(new Date(), 30));
            }
            if (queryMap.get("returnType") != null && RETURN_TYPE_CHAOQI == (long) queryMap.get("returnType")) {
                queryMap.put("nowTime", DateUtils.format(new Date()));
            }
            queryMap.put("lastReturnTime", DateUtils.getDateBefore(new Date(), 30));
            return recordSignDao.queryPOJOTotalByReturnVisit(queryMap);
        }
        return recordSignDao.queryPOJOTotal(queryMap);
    }

    @Override
    public void updateFollowStatus(RecordSignEntity recordSignEntity) {
        recordSignDao.updateFollowStatus(recordSignEntity);
    }

    @Override
    public void updateReturnTime(RecordSignEntity recordSignEntity) {
        recordSignDao.updateReturnTime(recordSignEntity);
    }


    @Override
    public RecordSignEntity findRecordSignById(Long recordSignId) {
        return recordSignDao.findRecordSignById(recordSignId);

    }

    @Override
    public void updateSaveReturnTime(Map map) {
        recordSignDao.updateSaveReturnTime(map);
    }


    public int getRecordSignStatus(RecordSignEntity s) {

        if (s.getStatus() == 3) {
            return 3;
        } else {
            Map<String, Object> queryMap = new HashMap<String, Object>();

            queryMap.put("orderId", s.getOrderId());
            queryMap.put("abnormalType", 1);
            try {
                CourseAbnormalOrderPOJO p = courseAbnormalOrderDao.verifyStatus(queryMap);
                if (p != null) {
                    logger.info("RecordSignEntity  check status is Abnormal,set RecordSign's status to 2 .RecordSignEntity is {} ", s);
                    return 2;
                }
            } catch (Exception e) {
                logger.error("RecordSignEntity  check status has Errors .RecordSignEntity is {},error's message is{} ", s, e);

            }


        }

        return 1;//默认返回1为在读

    }

    @Override
    public int queryTotal(Map<String, Object> queryMap) {
        // TODO Auto-generated method stub
        return recordSignDao.queryTotal(queryMap);
    }

}
