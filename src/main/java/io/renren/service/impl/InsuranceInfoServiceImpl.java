package io.renren.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.InsuranceInfoDao;
import io.renren.dao.InsuranceRecordDao;
import io.renren.entity.InsuranceInfoEntity;
import io.renren.service.InsuranceInfoService;
@Service
public class InsuranceInfoServiceImpl implements InsuranceInfoService {

	@Autowired
	private InsuranceInfoDao insuranceInfoDao;

	@Override
	public void saveInsuranceInfo(InsuranceInfoEntity e) {
		insuranceInfoDao.save(e);
	}

	@Override
	public void updateInsuranceInfo(InsuranceInfoEntity e) {
		insuranceInfoDao.update (e);

	}

	@Override
	public List<InsuranceInfoEntity> queryList(Map<String, Object> queryMap) {
		return insuranceInfoDao.queryList(queryMap);
	}

	@Override
	public InsuranceInfoEntity queryObject(Long id) {
		return insuranceInfoDao.queryObject(id);
	}


	@Override
	public InsuranceInfoEntity queryByMallGoodsId(Long goodsId) {
		return insuranceInfoDao.queryByMallGoodsId(goodsId);
	}

	
	
	
	@Override
	public int delete(Long id) {
		return insuranceInfoDao.delete(id);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return insuranceInfoDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(Long[] insuranceInfoIds) {
		insuranceInfoDao.deleteBatch(insuranceInfoIds);
		
	}
	@Override
	public int checkInsuranceInfoExist(Long insuranceInfoId) {
		return insuranceInfoDao.checkInsuranceInfoExist(insuranceInfoId);
	}


	
}
