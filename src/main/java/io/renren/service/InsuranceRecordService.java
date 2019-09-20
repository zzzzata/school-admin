package io.renren.service;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.renren.entity.InsuranceRecordEntity;
import io.renren.entity.MallGoodsInfoEntity;
import io.renren.entity.MallOrderEntity;
import io.renren.entity.OrderMessageConsumerEntity;

public interface InsuranceRecordService {
	
	void saveInsuranceRecord(InsuranceRecordEntity e);

	void updateInsuranceRecord(InsuranceRecordEntity e);
	List<InsuranceRecordEntity> queryList(Map<String,Object> queryMap);
 

	InsuranceRecordEntity queryObject(Long id);
	/**
	 * 用于生成订单时的判断
	 * @param order
	 * @param body
     * @param goodinfoMap 当前订单中所用到的全部商品
	 * @param isGroupGood 是否组合班型
	 * @return
	 */
	boolean insuranceRecoredProcess(MallOrderEntity order, OrderMessageConsumerEntity body,  Map<Long, MallGoodsInfoEntity> goodinfoMap,boolean isGroupGood);
	 

	List queryListPOJO(Map<String, Object> map);

	int queryPOJOTotal(Map<String, Object> map); 
	/**
	 * 
	 * @param recordList
	 * @param order
	 * @param body
	 * @return
	 */
	boolean InsuranceRecordUpdateCheck(List<InsuranceRecordEntity> recordList, MallOrderEntity order,
			OrderMessageConsumerEntity body, Map<Long, MallGoodsInfoEntity> goodinfoMap);
	/**
	 * 取得发送时间   （为了确保一天只能发一个人的，所以如果一天有生成同一个人多条保险订单的 将时间往后移）<p>
	 * 取前当前学员最后一条保险订单的时间 如果大于等于当天的 则为这个时间加1天
	 * @param userId
	 * @return
	 */
	Date getSendTime(Long userId);
	/**
	 * 根据NCid和原来的班型取得源订单
	 * @param ncId
	 * @param ncCommodityId
	 * @return
	 */
	Long getSourceOrderId(String ncId, String ncCommodityId);


	void updateByContract(InsuranceRecordEntity e);
	/**
	 * 根据订单号删除投保记录
	 * @param orderIds
	 * @return
	 */
	boolean insuranceRecoredDeleteByOrderIds(Long[] orderIds); 
 	void passAction(Long[] ids);

	void passCancel(Long[] ids);

	void insuranceRecoredDeleteByOrderId(Long orderId);
	/**
	 *  用于生成订单时的判断 加入了组合班型的判断
	 * @param body
	 * @param orderListTemp
	 * @param isGroupGood
	 * @return
	 */
	boolean checkInsuranceRecoredByOrderList(OrderMessageConsumerEntity body, List<MallOrderEntity> orderListTemp,Map<Long, MallGoodsInfoEntity> goodinfoMap,
			boolean isGroupGood);

	/**
	 * 检测该学员已通过保险信息的数量
	 * @param id
	 */
	int checkCount(Long id);

	/**
	 * 保存原来记录
	 * @param e
	 */
	void saveInsuranceLog(InsuranceRecordEntity e);
}
