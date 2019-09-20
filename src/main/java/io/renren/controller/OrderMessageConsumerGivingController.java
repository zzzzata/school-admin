package io.renren.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import io.renren.entity.*;
import io.renren.service.*;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import io.renren.pojo.GivingCoursesPOJO;
import io.renren.pojo.order.OrderPOJO;
import io.renren.rest.persistent.KGS;

import io.renren.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;



@Controller
public class OrderMessageConsumerGivingController implements ChannelAwareMessageListener {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static final String MD5_KEY = "%^\\$AF>.12*******zK";
	@Resource
	KGS orderNoKGS;
	@Resource
	KGS userKGS;
	private static final String ORDERNO_HEAD = "nc_";
	@Autowired
	private MallOrderService mallOrderService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private MallGoodsInfoService mallGoodsInfoService;
	@Autowired
	private MallAreaService mallAreaService;
	@Autowired
	private MallClassService mallClassService;
	@Autowired
	private MallOrderSyncService mallOrderSyncService;
	@Autowired
	private GivingCoursesService givingCoursesService;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	@Autowired
	private SysDeptService sysDeptService;


	/** 默认密码 hqzk123456 */
	private static String PSW = "1c3f360330c442c3cc62d1608fe7a3a3";
	@Value("#{application['user.psw']}")
	private void setPSW(String str){
		PSW = str;
	}

	/** 默认头像 */
	private static String PIC = "http://alifile.hqjy.com/hq/Avatar.png";

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		logger.info("收到-赠送课程-队列信息" + " [x] Received '" + new String(message.getBody(), "UTF-8") + "'");
		TransactionStatus status = null;
		UsersEntity user=null;
		OrderMessageConsumerGivingEntity order = null;
		//开启事务
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务
		status = transactionManager.getTransaction(def); // 获得事务状态

		String body = null;
		try {
			body = new String(message.getBody(), "UTF-8");
			Gson gson = new Gson();
			order = gson.fromJson(body, OrderMessageConsumerGivingEntity.class);
			if (1 == order.getSpec_status()) {
				//2019-05-23 李海飞临时屏蔽  start
				/*if (order.getUserId()==null||order.getUserId()==0) {
		        	  //nc中的订单没有带userid的不再创建订单
					 saveErr( body,order,9);//错误类型 1:商品不存在 2：商品对应省份不存在3：没有该班级存在4：系统异常 5:无业务线 6:校区不存在;9userid不存在
		            logger.error("赠送订单同步NC报名单异常,userId为空: phone={},code={},### mallOrderSync={}", order.getPhone(),order.getCode(),  order);
		            return;
		        }*/
				//2019-05-23 李海飞临时屏蔽  end
				// 不存在相同的报名表
				if (0 == mallOrderService.queryOrderExist(null, order.getCode(), order.getOrder_no())) {
//					if (StringUtils.isBlank(order.getOrder_no())) {
//						order.setOrder_no(ORDERNO_HEAD + orderNoKGS.nextId());
//					}
					Map<String, Object> queryGivingMap = new HashMap<>();
					queryGivingMap.put("ncCommodityId", order.getNc_commodity_id());
					queryGivingMap.put("givingType", 0); //只查询赠送类型为1的
					// 根据nc_commodity_id查找对照表
					List<GivingCoursesPOJO> givingCoursesList = givingCoursesService.queryPojoList(queryGivingMap);
					if (null != givingCoursesList && givingCoursesList.size() != 0) {
						for (GivingCoursesPOJO givingCoursesPOJO : givingCoursesList) {
							order.setOrder_no(ORDERNO_HEAD + orderNoKGS.nextId());
							Map<String, Object> queryGoodMap = new HashMap<>();
							queryGoodMap.put("id", givingCoursesPOJO.getMallGoodsId());
							// 根据nc_commodity_id查找对应商品
							MallGoodsInfoEntity goodsInfo = mallGoodsInfoService.queryObject(queryGoodMap);
							SysDeptEntity deptEntity = this.sysDeptService.queryObjectByNcId(order.getNc_school_pk());
							if (null != goodsInfo) {// 商品存在
								Map<String, Object> queryAreaMap = new HashMap<>();
								queryAreaMap.put("schoolId", goodsInfo.getSchoolId());
								queryAreaMap.put("ncId", order.getProvince_pk());
								List<MallAreaEntity> queryAreaIdByZKAreaId = mallAreaService.queryList(queryAreaMap);
								if (null != queryAreaIdByZKAreaId && queryAreaIdByZKAreaId.size() != 0) {// 省份存在
									Long areaId = queryAreaIdByZKAreaId.get(0).getAreaId();
									// 查询默认班级
									// ==>根据省份（area_id），专业（profession_id），学历（level_id）查找对应班级ID
									Map<String, Object> classMap = new HashMap<>();
									classMap.put("areaId", areaId);
									classMap.put("professionId", goodsInfo.getProfessionId());
									classMap.put("levelId", goodsInfo.getLevelId());
									MallClassEntity mallClass = mallClassService.queryClassId(classMap);
									// 班级ID
									Long classId = null;
									if (mallClass != null) {// 班级存在
										classId = mallClass.getClassId();
									}
									// if(mallClass != null){//班级存在
									Map<String, Object> checkUserMap = new HashMap<>();
									checkUserMap.put("schoolId", goodsInfo.getSchoolId());
									checkUserMap.put("mobile", order.getPhone());
									Long user_id = null;
									// 判断号码是否存在
									if (!this.usersService.checkMobile(checkUserMap)) {// 用户不存在
										//李海飞2019-05-23 取消屏蔽  START
										// 如果是生成订单就插入用户信息
										user = new UsersEntity();
										user_id = (long) userKGS.nextId();
										user.setUserId(user_id);
										user.setNcId(order.getNc_user_id());//nc中用户的pk
										user.setSchoolId(goodsInfo.getSchoolId());
										user.setMobile(order.getPhone());
										user.setNickName(order.getUser_name());
										user.setRealName(order.getUser_name());
										user.setPic(PIC);
										user.setSex(1);
										user.setEmail(null);
										user.setPassword(PSW);
										user.setCreator(null);
										user.setModifiedTime(new Date());
										user.setCreationTime(new Date());
										user.setModifier(null);
										user.setModifiedTime(null);
										user.setLastLoginIp(null);
										user.setLastLoginTime(null);
										user.setRemake("同步NC赠送订单时生成用户");
										logger.error("赠送订单同步NC报名单生成用户: phone={},code={}", order.getPhone(),order.getCode());
										usersService.save(user);
										//李海飞2019-05-23 取消屏蔽  END
										 /*logger.error("赠送订单同步NC报名单异常,userId为空: phone={},code={},### mallOrderSync={}", order.getPhone(),order.getCode(),  order);
								            return;*/
									} else {// 用户存在
										Map<String, Object> usersMap = new HashMap<>();
										usersMap.put("mobile", order.getPhone());
										// 判断用户是否存在
										user_id = Long.valueOf(usersService.queryUserId(usersMap));
									}

									// 创建订单对象
									OrderPOJO mallOrder = new OrderPOJO();
									// 用户ID
									mallOrder.setUserId(user_id);
									// 班型
									mallOrder.setClassTypeId(goodsInfo.getClassTypeId());
									// 班级
									mallOrder.setClassId(classId);
									// 支付状态
									mallOrder.setPayStatus(2);
									// 訂單號
									mallOrder.setOrderNo(order.getOrder_no());
									// 商品名称
									mallOrder.setCommodityName(goodsInfo.getName());
									// 商品ID
									mallOrder.setCommodityId(goodsInfo.getId());
									// 订单名称
									mallOrder.setOrderName(goodsInfo.getName());
									// 订单描述
									mallOrder.setOrderDescribe(goodsInfo.getName());
									// 订单总额
									mallOrder.setTotalMoney(goodsInfo.getOriginalPrice());
									// 备注
									mallOrder.setRemark(null);
									// 平台IP
									mallOrder.setSchoolId(goodsInfo.getSchoolId());
									// 支付金額
									mallOrder.setPayMoney(goodsInfo.getOriginalPrice());
									// 支付時間
									mallOrder.setPayTime(DateUtils.getDate(order.getTs(), 1));
									// 优惠金额
									mallOrder.setFavorableMoney(0.0);
									// 优惠券ID
									mallOrder.setDiscountId(null);
									// 第三方支付回调时间
									mallOrder.setPayCallblackTime(new Date());
									// 第三方支付回调信息
									mallOrder.setPayCallblackMsg(null);
									// 支付宝交易号
									mallOrder.setAlipayTradeNo(null);
									// 支付IP
									mallOrder.setPayip(null);
									// 生成支付地址
									mallOrder.setPayUrl(null);
									// 商品图片
									mallOrder.setPic(goodsInfo.getOriginPath());
									// 商品小图
									mallOrder.setSpic(goodsInfo.getThumbPath());
									// dr 0.正常 1.删除
									mallOrder.setDr(0);
									// 用户操作状态 0.正常 1.取消 2.删除
									mallOrder.setUstatus(0);
									// 微信开放ID
									mallOrder.setWxOpenId(null);
									// 0.未选择 1.支付宝 2.微信
									mallOrder.setPayType(0);
									// 银行编码
									mallOrder.setBankCode(null);
									// 银行名称
									mallOrder.setBankName(null);
									// 創建用戶
									mallOrder.setCreator(null);
									// 创建时间
									mallOrder.setCreationTime(DateUtils.getDate(order.getCreate_time(), 1));
									// 最近修改用户
									mallOrder.setModifier(null);
									// 最近修改时间
									mallOrder.setModifiedTime(null);
									//// 来源 0.线上;1.NC;2測試
									mallOrder.setSourceType(1);
									// NCID
									mallOrder.setNcId(order.getNc_id());
									// 省份ID
									mallOrder.setAreaId(areaId);
									// nc同步code值
									mallOrder.setNcCode(order.getCode());
									// nc同步时间
									Date date = DateUtils.getDate(Long.valueOf(order.getSyn_time()), 1);
									mallOrder.setSynTime(date);
									// 层次ID
									mallOrder.setLevelId(goodsInfo.getLevelId());
									// 专业ID
									mallOrder.setProfessionId(goodsInfo.getProfessionId());
									//订单有效期 date_validity
									mallOrder.setDateValidity(DateUtils.orderDateValiditySync(DateUtils.getDate(Long.valueOf(order.getTs()), 1), goodsInfo.getDayValidity()));
									// 产品线ID
									mallOrder.setProductId(order.getProductType());
									//设置部门
                                    Long deptId = deptEntity.getDeptId()==null?1L:deptEntity.getDeptId();
									if(deptEntity != null){
										deptId = deptEntity.getDeptId()==null?1L:deptEntity.getDeptId();
									}
									mallOrder.setDeptId(deptId);

									//根据商品id查询订单表是否有记录
									List<MallOrderEntity> mallOrderListTemp = mallOrderService.queryListByCommodityIdAndUserIdOrNcId(givingCoursesPOJO.getMallGoodsId(), user_id,null);
									if(mallOrderListTemp != null && mallOrderListTemp.size()==0){
										// 保存
										mallOrderService.saveGiveOrder(mallOrder);
									}else{

									}

									// }else{//班型=null
									// saveErr(body, order, 3);
									// }
								} else {// 省份不存在
									saveErr(body, order, 2);
								}
							} else {// 商品不存在
								saveErr(body, order, 1);
							}
						}//一个队列赠送多个商品
					} else {// 商品对照不存在
						saveErr(body, order, 1);
					}
				} else {// 订单存在

				}
			} else {// 不做处理

			}
		} catch (Exception e) {
			try {
				transactionManager.rollback(status);
				// 记录报错日志
				saveErr(body, order, 4);
				e.printStackTrace();
			} catch (Exception e1) {
				//如果保存报错日志出错finally不会被执行，需要在此确认消费消息
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			}
		} finally {
			try {
				// 提交事务
				transactionManager.commit(status);
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				//System.out.println("over!");
			} catch (Exception e) {
				saveErr(body, order, 4);
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			}
		}
	}
	/**
	 * 保存错误信息
	 * @param json	json
	 * @param ms	转换对象
	 * @param type	错误类型 1:商品不存在 2：商品对应省份不存在3：没有该班级存在4：系统异常
	 */
	private void saveErr(String json , OrderMessageConsumerGivingEntity ms , int type){
		MallOrderSyncEntity entity = new MallOrderSyncEntity();
		entity.setCreateTime(new Date());
		entity.setErrType(type);
		entity.setNcJson(json);
		try {
			if(null != ms){
				entity.setNcId(ms.getNc_id());
				entity.setCreateTime(DateUtils.getDate(ms.getCreate_time(),1));
				entity.setNcClassTypeId(ms.getClass_type_id());
				entity.setMobile(ms.getPhone());
				entity.setState(0);
				entity.setSynTime(DateUtils.getDate(ms.getSyn_time(),1));
				entity.setNcCode(ms.getCode());;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}finally{
			mallOrderSyncService.save(entity);
		}
	}
}
