package io.renren.service.impl;

import io.renren.dao.ReturnVisitConfigDao;
import io.renren.entity.RecordSignEntity;
import io.renren.entity.ReturnVisitConfigEntity;
import io.renren.pojo.ReturnVisitPOJO;
import io.renren.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.renren.dao.ReturnVisitDao;
import io.renren.entity.ReturnVisitEntity;
import io.renren.service.ReturnVisitService;


@Service("returnVisitService")
public class ReturnVisitServiceImpl implements ReturnVisitService {
	@Autowired
	private ReturnVisitDao returnVisitDao;

	@Autowired
	private ReturnVisitConfigDao returnVisitConfigDao;
	
	@Override
	public ReturnVisitEntity queryObject(Map<String, Object> map){
		return returnVisitDao.queryObject(map);
	}
	
	@Override
	public List<ReturnVisitEntity> queryList(Map<String, Object> map){
		return returnVisitDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return returnVisitDao.queryTotal(map);
	}
	
	@Override
	public void saveBySign(RecordSignEntity recordSignEntity){
		List<ReturnVisitConfigEntity> list = returnVisitConfigDao.queryListBySign(recordSignEntity.getRecordSignId());
		if(list != null && list.size() > 0){
			for (ReturnVisitConfigEntity returnVisitConfigEntity:list) {
				ReturnVisitEntity returnVisit = new ReturnVisitEntity();
				returnVisit.setRecordSignId(recordSignEntity.getRecordSignId());
				//预计回访时间：跟进时间+预计回访天数
				Date expectTime = DateUtils.getDateAfter(recordSignEntity.getFollowTime(),returnVisitConfigEntity.getReturnNum());
				returnVisit.setExpectTime(DateUtils.format(expectTime));
				returnVisit.setUserId(recordSignEntity.getUserId());
				returnVisit.setReturnStatus(0);
				returnVisitDao.save(returnVisit);
			}
		}
	}

	@Override
	public void save(ReturnVisitEntity returnVisit) {
		returnVisitDao.save(returnVisit);
	}

	@Override
	public void update(ReturnVisitEntity returnVisit){
		returnVisitDao.update(returnVisit);
	}
	
	@Override
	public void delete(Map<String, Object> map){
		returnVisitDao.delete(map);
	}
	
	@Override
	public void deleteBatch(Map<String, Object> map){
		returnVisitDao.deleteBatch(map);
	}

	@Override
	public ReturnVisitPOJO queryPOJOObject(Map<String, Object> map) {
		return returnVisitDao.queryPOJOObject(map);
	}

	@Override
	public List<ReturnVisitPOJO> queryPOJOList(Map<String, Object> map) {
		return returnVisitDao.queryPOJOList(map);
	}

	@Override
	public Long queryLast(Long recordSignId) {
		return returnVisitDao.queryLast(recordSignId);
	}

	@Override
	public Integer checkIsContact(Long recordSignId) {
		return returnVisitDao.checkIsContact(recordSignId);
	}




}
