package io.renren.service;
import java.util.List;
import java.util.Map;

import io.renren.entity.InsuranceInfoEntity;

public interface InsuranceInfoService {
	
	void saveInsuranceInfo(InsuranceInfoEntity e);

	void updateInsuranceInfo(InsuranceInfoEntity e);
	
	List<InsuranceInfoEntity> queryList(Map<String,Object> queryMap);
 

	InsuranceInfoEntity queryObject(Long id);

	
	
	
	
	int delete(Long id);

	int queryTotal(Map<String, Object> map);
	/**
	 * 根据商品id取得产品编号档案
	 * @param goodsId
	 * @return
	 */
	InsuranceInfoEntity queryByMallGoodsId(Long goodsId);
	
	/**
	 * 删除
	 * @param orderIds
	 */
	void deleteBatch(Long[] insuranceInfoIds);

	int checkInsuranceInfoExist(Long insuranceInfoId); 

}
