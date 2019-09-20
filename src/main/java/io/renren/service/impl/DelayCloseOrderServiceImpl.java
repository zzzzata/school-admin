package io.renren.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.DelayCloseOrderDao;
import io.renren.dao.InsuranceRecordDao;
import io.renren.entity.DelayCloseOrderEntity;
import io.renren.entity.InsuranceInfoEntity;
import io.renren.entity.MallOrderEntity;
import io.renren.entity.OrderMessageConsumerEntity;
import io.renren.service.DelayCloseOrderService;
import io.renren.service.InsuranceInfoService;

@Service
public class DelayCloseOrderServiceImpl implements DelayCloseOrderService {

    @Autowired
    private DelayCloseOrderDao delayCloseOrderDao;

    @Override
    public void save(DelayCloseOrderEntity e) {
        e.setCreationTime(new Date().getTime());
        delayCloseOrderDao.save(e);
    }

    @Override
    public void update(DelayCloseOrderEntity e) {
        delayCloseOrderDao.update(e);

    }

    @Override
    public List<DelayCloseOrderEntity> queryList(Map<String, Object> queryMap) {
        return delayCloseOrderDao.queryList(queryMap);
    }

    @Override
    public void DelayCloseOrderDeleteByNCid(String ncid, Integer closeStatus) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("ncId", ncid);
        queryMap.put("closeStatus", 0);
        List<DelayCloseOrderEntity> dcList = this.queryList(queryMap);
        if (dcList != null && dcList.size() > 0) {
            for (DelayCloseOrderEntity dc : dcList) {
                dc.setCloseStatus(closeStatus);
                dc.setActionTime(new Date().getTime());
                this.update(dc);
            }
        }

    }


    @Override
    public void DelayCloseOrderList(List<MallOrderEntity> mallOrderList, OrderMessageConsumerEntity orderMessageConsumerEntity) {

        for (MallOrderEntity order : mallOrderList) {
            Map<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.put("orderId", order.getOrderId());
            queryMap.put("closeStatus", 0);
            queryMap.put("dr", 0);
            List<DelayCloseOrderEntity> dcList = this.queryList(queryMap);
            if (dcList != null && dcList.size() > 0) {
                return;
            } else {
                DelayCloseOrderEntity dcNew = new DelayCloseOrderEntity(order);
                dcNew.setSourceType(orderMessageConsumerEntity.getSourceType());
                dcNew.setMobile(orderMessageConsumerEntity.getPhone());
                this.save(dcNew);
            }


        }


    }

    @Override
    public void DelayCloseOrderDeleteByOrderId(Long orderId, Integer closeStatus) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("orderId", orderId);
        queryMap.put("closeStatus", 0);
        List<DelayCloseOrderEntity> dcList = this.queryList(queryMap);
        if (dcList != null && dcList.size() > 0) {
            for (DelayCloseOrderEntity dc : dcList) {
                dc.setCloseStatus(closeStatus);
                dc.setActionTime(new Date().getTime());
                this.update(dc);
            }
        }


    }


}
