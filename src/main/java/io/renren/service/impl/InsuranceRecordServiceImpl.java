package io.renren.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import io.renren.common.doc.SysLog;
import io.renren.dao.InsuranceRecordDao;
import io.renren.entity.ContractRecord;
import io.renren.entity.InsuranceInfoEntity;
import io.renren.entity.InsuranceRecordCourseEntity;
import io.renren.entity.InsuranceRecordEntity;
import io.renren.entity.MallGoodsInfoEntity;
import io.renren.entity.MallOrderEntity;
import io.renren.entity.OrderMessageConsumerEntity;
import io.renren.entity.SysLogEntity;
import io.renren.entity.UsersEntity;
import io.renren.enums.InsuranceEnum;
import io.renren.enums.InsuranceLogTypeEnum;
import io.renren.pojo.order.OrderPOJO;
import io.renren.service.ContractRecordService;
import io.renren.service.ContractService;
import io.renren.service.InsuranceInfoService;
import io.renren.service.InsuranceRecordCourseService;
import io.renren.service.InsuranceRecordService;
import io.renren.service.MallGoodsInfoService;
import io.renren.service.MallOrderService;
import io.renren.service.SysLogService;
import io.renren.service.UsersService;
import io.renren.utils.DateUtils;
import io.renren.utils.LocationUtil;
@Service("insuranceRecordService")
public class InsuranceRecordServiceImpl implements InsuranceRecordService {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private InsuranceRecordDao insuranceRecordDao;
	@Autowired
	private ContractService contractService;
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private MallOrderService mallOrderService;
	
	@Autowired
	private MallGoodsInfoService mallGoodsInfoService;
	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private ContractRecordService contractRecordService;
@Autowired
private InsuranceInfoService insuranceInfoService;
@Autowired InsuranceRecordCourseService insuranceRecordCourseService;
	@Override
	public void saveInsuranceRecord(InsuranceRecordEntity e) {
		boolean noExamDate=false;
		if (e.getInsuranceType()!=null&&e.getInsuranceType()==0) { //如果是全保的 则不会有考期
			noExamDate=true;
		}
		
		
		Date d = new Date();
		
		e.setCreationTime(d);
		e.setSendTime(getSendTime(e.getUserId()));
		this.setSignerId(e);//用于设置signerid
		insuranceRecordDao.save(e);
		
		 
		
		if (e.getInsuranceRecordCourse()!=null&&e.getInsuranceRecordCourse().size()>0) {
			for (InsuranceRecordCourseEntity detail:e.getInsuranceRecordCourse()) {
				detail.setInsuranceRecordId(e.getInsuranceRecordId());
				if (noExamDate) {
					detail.setExamDate(null);
				}
				insuranceRecordCourseService.saveInsuranceRecordCourse(detail);
			}
		}

	}
	@Override
	public Date getSendTime(Long userId) {
		
		String today=   DateFormatUtils.format(				 new Date(System.currentTimeMillis()) , "yyyy-MM-dd 00:00:00") ;
	Object o=	insuranceRecordDao.getSendTime(today,userId);
		 if (o==null) {
			 return new Date();
		 }else {
			return  DateUtils.getDateBefore((Date)o , -1);
		 }
		 
		
	}
	
	
	
	
	
	@Override
	public void updateInsuranceRecord(InsuranceRecordEntity e) {
		Date ts = new Date();
	
		int subjectQty=0;
		Long inRecordId=e.getInsuranceRecordId();
		Integer dr=e.getDr();
		if (dr==1) {
			e.setPassStatus(0);//如果是删除的 要清掉通过状态
		}
		if (e.isIscleanCouse()) {//如果是要清空子表的则为1
			dr=1;
		}
		boolean noExamDate=false;//用于判断 是否要考期的
		if (e.getInsuranceType()!=null&&e.getInsuranceType()==0) { //如果是全保的 则不会有考期
			noExamDate=true;
		}
		
		
		
		if (e.getInsuranceRecordCourse()!=null&&e.getInsuranceRecordCourse().size()>0) {
			for (InsuranceRecordCourseEntity detail:e.getInsuranceRecordCourse()) {
				detail.setInsuranceRecordId(inRecordId);
				if (dr==1) {
				detail.setDr(dr);
				}else if (detail.getDr()==null) {
					detail.setDr(0);
				}
				
				detail.setTs(ts);
				detail.setUserId(e.getUserId());
				if (!noExamDate) {
					detail.setExamDate(e.getExamDate());
				}
			
				
				if (detail.getId()!=null&&detail.getId()!=null) {					
					
					insuranceRecordCourseService.updateInsuranceRecordCourse (detail);
				}else {
					detail.setCreationTime(ts);
					insuranceRecordCourseService.saveInsuranceRecordCourse  (detail);
				}
				if (detail.getDr()==0) {
					subjectQty++;
				}
				e.setSubjectQty(subjectQty);
				
			}
		}else if (e.isIscleanCouse()) {  //如果 是要删除子表的查出子表并删除 
			// 先取得子表信息
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("insuranceRecordId", e.getInsuranceRecordId());
			List<InsuranceRecordCourseEntity> detailList = insuranceRecordCourseService.queryList(queryMap);
			if (detailList!=null&&detailList.size()>0) {
				for ( InsuranceRecordCourseEntity d:detailList) {
					d.setDr(1);
					insuranceRecordCourseService.updateInsuranceRecordCourse (d);
				}
				
			}
			e.setSubjectQty(0);
		}
		saveInsuranceLog(insuranceRecordDao.queryObject(e.getInsuranceRecordId()));
		insuranceRecordDao.update(e);
	}
	
	@Override
	public boolean  InsuranceRecordUpdateCheck(List<InsuranceRecordEntity> recordList,MallOrderEntity order,OrderMessageConsumerEntity body, Map<Long, MallGoodsInfoEntity> goodinfoMap) {
		for (InsuranceRecordEntity r : recordList) {

			boolean isPass = r.getPassStatus() == 1 ? true : false;
			boolean isdel = r.getDr()==0?false:true; //是删除的			
			boolean isOldVal = false;// 用于判断 是不是旧数据 （以前删除了的投保记录）
			Long sourceOrderId = null;
			if ( r.getDr()!=0&&!r.getOrderId().equals(order.getOrderId())) {// 库中的数据是删除的并且orderid不一致的 则会
				isOldVal = true;
				sourceOrderId = this.getSourceOrderId(body.getSourceNcid(), body.getSourceClassId());

			}

			if (r.getInsuranceStatus() == 0 || r.getInsuranceStatus() == 2) { // 只有是1未投保存 2投保失败才会进入修改判断

				/**
				 * 通过状态为通过的规则：1.没有交齐款或者退款的时，不管是否通过 都要删除，并将状态改为未通过
				 * 2.通过状态时，NC中的变更不能再修改投保记录。
				 * 总起来说就是通过状态的 不能修改 但是能删除 ，删除时要改为款通过，但是保留通过时间。
				 * 
				 * 
				 * 规则：没有交齐款的将删除投保记录和投保课程 ，不管通过状态是否为通过。
				 */
				if (!"Y".equals(body.getPaystatus())) {
					 /*
					if (isPass) {
						this.logger.info("旧订单{}没有交齐款，但投保清单{}是已经审批通过的，所以不能修改。", order.getOrderId(),
								r.getInsuranceRecordId());
						this.InsuranceLog(InsuranceLogTypeEnum.OrderNotFullPlayNotDeleteInsuranceRecordDisableWithPassStatus.getValue(), 
								"旧订单{} ， 投保清单{}", order.getOrderId(),r.getInsuranceRecordId());
						return true;
					}*/
					Map<String, Object> queryMap = new HashMap<String, Object>();
					queryMap.put("insuranceRecordId", r.getInsuranceRecordId());
					queryMap.put("dr", 0);
					List<InsuranceRecordCourseEntity> detail = insuranceRecordCourseService.queryList(queryMap);
					this.logger.info("旧订单{}没有交齐款，投保清单{}和{}个投保课程将删除 ,NC订单：{}。", order.getOrderId(),
							r.getInsuranceRecordId(), detail.size(), body.getSignTag());
					r.setInsuranceRecordCourse(detail);
					r.clearCourse();
					r.setDr(1);
					
				
					if (isPass) {
						this.InsuranceLog(InsuranceLogTypeEnum.OrderNotFullPlayNotDeleteInsuranceRecordWithPassStatus.getValue(), 
								"旧订单{} ，投保清单{}和{}个投保课程 ,NC订单：{}。", order.getOrderId(),
								r.getInsuranceRecordId(), detail.size(), body.getSignTag());
						
					}else {
						this.InsuranceLog(InsuranceLogTypeEnum.OrderNotFullPlayNotDeleteInsuranceRecord.getValue(), 
								"旧订单{} ，投保清单{}和{}个投保课程 ,NC订单：{}。", order.getOrderId(),
								r.getInsuranceRecordId(), detail.size(), body.getSignTag());
					}
					updateInsuranceRecord(r);
					continue;
				}
				//是否交齐款的处理完毕
				
				Long oldOrderId = r.getOrderId();//用于判断学员是否变更了订单 （nc中变更了班型的会新增新的订单，这时要更新为新的投保产品（在下面的变更中判断变更）
				boolean hasCheck = r.updateEntity(order, body); 
				
				/**
				 * 保险产品档案的判断规则  1.如果原来有投保的 现在没有投保的则 什么都不改变 2.如果原有是投保的 现在换了一个产品档案的并且Nc中改了班型的（即orderId有变更） 则更新为新的产品档案
				 */
				MallGoodsInfoEntity goodinfo = goodinfoMap.get(order.getCommodityId());	
				
				/**
				 *  有可能商品为空的 ，商品为空的 则查库
				 */
				if (goodinfo == null) {
					// 获取商品表中的保险协议id
					Map<String, Object> goodsIdMap = new HashMap<String, Object>();
					goodsIdMap.put("id", order.getCommodityId());
					goodinfo = mallGoodsInfoService.queryObject(goodsIdMap);
					if (goodinfo == null) {
						this.logger.info("订单{}没有找到商品{}， 投保清单{}不会变更 。", order.getOrderId(), order.getCommodityId(),
								r.getInsuranceRecordId());
						this.InsuranceLog(
								InsuranceLogTypeEnum.InsuranceRecordNGoodsinfoNotExistNotChangeRecord.getValue(),
								"订单{},到商品{}， 投保清单{},", order.getOrderId(), order.getCommodityId(),
								r.getInsuranceRecordId());
						return true;
					}

				}
				
				
				
				
				 boolean  checkInsuranceInfo=false;// 是否要更新投保记录中的投保产品（即变更保费和学费等等）,默认为不能变更				
				
				//开始比对产品档案  如果id与商品中的产品id不一致的要变更投保记录 ，只有这种情况的才能变更保费
				if (!oldOrderId.equals(r.getOrderId())) {
					checkInsuranceInfo=true;
				//产品id一致的不需要改变	
				} else if (goodinfo!=null&&goodinfo.getInsuranceInfoId()==null||goodinfo.getInsuranceInfoId()==0)	{//现在商品中不投保了
					if (hasCheck) {//以前不投保的 现在投保的 会变更科目为0的 但变更代码不在这里面 
								r.setIscleanCouse(true);
						this.logger.info(" 投保清单{}中的商品{} 已经不投保了，更新科目为0且变更NC信息。",  r.getInsuranceRecordId(), order.getCommodityId() 
								);
						this.InsuranceLog(InsuranceLogTypeEnum.InsuranceRecordNotActionNotChangeRecord.getValue(),
								" 投保清单{},商品{} ",  r.getInsuranceRecordId(), order.getCommodityId() 
								);
					}
					 
				}
					
				if (checkInsuranceInfo ) {	// 但是如果学员的orderid变更，则说明是换了订单了 但同时要判断一下不是旧数据变更的 
					hasCheck=true;
					InsuranceInfoEntity info=insuranceInfoService.queryByMallGoodsId(goodinfo.getId());
					if (info==null) {
						r.clearCourse();
					}else {
						hasCheck =true;
						if (!isPass) {
							this.logger.info(" 订单{}变更产品档案，投保清单{}  ,NC订单：{}。", order.getOrderId(),
									r.getInsuranceRecordId(),  body.getSignTag());
							this.InsuranceLog(InsuranceLogTypeEnum.InsuranceRecordChangeInfo.getValue(),
									"订单{} ,投保清单{} ,NC订单：{} ",  order.getOrderId(), r.getInsuranceRecordId(),  body.getSignTag());
								 
							r.setInsuranceInfo(info);
						}
						
					}
				}
				
				//最终判断更新的

				if (hasCheck || isOldVal) {

					if (isPass) {
						 if (isdel&&r.getDr()==0) { //如果是已经删除的 现在恢复为未删除的 会清掉通过状态,但保留通过时间
							 	r.setPassStatus(0); 
							    this.logger.info("订单{}的投保清单从删除的恢复，但投保清单{}是已经审批通过的，将变更为未通过", order.getOrderId(),
							    		r.getInsuranceRecordId());
								this.InsuranceLog(InsuranceLogTypeEnum.InsuranceRecordDisablePassStatus.getValue(),
										"订单{}，投保记录{}",  order.getOrderId(),
										r.getInsuranceRecordId());
						 }else {
								this.logger.info("从Nc过来的订单{}信息有变更 ，但投保清单{}是已经审批通过的，所以不能修改。", order.getOrderId(),
										r.getInsuranceRecordId());
								this.InsuranceLog(InsuranceLogTypeEnum.InsuranceRecordChangeDisableWithPassStatus.getValue(),
										"订单{}，投保记录{}",  order.getOrderId(),
										r.getInsuranceRecordId());
								return true;
						 }					
					
					}

					// 先取得子表信息
					Map<String, Object> queryMap = new HashMap<String, Object>();
					queryMap.put("insuranceRecordId", r.getInsuranceRecordId());
					List<InsuranceRecordCourseEntity> detail = insuranceRecordCourseService.queryList(queryMap);
					r.setInsuranceRecordCourse(detail);
					List<InsuranceRecordCourseEntity> courseDetail = insuranceRecordCourseService
							.queryGooodsCourse(order.getAreaId(), order.getCommodityId(), r.getInsuranceInfoId());
					if (courseDetail == null || courseDetail.size() == 0) {// 如果都没有主课的 则投保记录将会去掉
						r.setInsuranceStatus(2);// 会将状态改为投保失败
						r.clearCourse();// 清空子表且表头的科目数量为0
						r.setSubjectQty(0);
					} else {
						r.setInsuranceRecordCourse(insuranceRecordCourseService.InsuranceRecordCourseUpdateCheck(detail, courseDetail));
					}
					if (isOldVal) {
						r.setSourceOrderId(sourceOrderId == 0 ? order.getOrderId() : sourceOrderId);
					}
					updateInsuranceRecord(r);

				}
			} else if (isOldVal) { // 如果是旧数据的并且不是投保成功的 则不能修改别的
				r.setDr(0);
				r.setOrderId(order.getOrderId());

				r.setSourceOrderId(sourceOrderId == 0 ? order.getOrderId() : sourceOrderId);
				
				this.InsuranceLog(InsuranceLogTypeEnum.InsuranceRecordChangeFromDelectedRecord.getValue(),
						"订单{} ,投保清单{} ,NC订单：{} ",  order.getOrderId(), r.getInsuranceRecordId(),  body.getSignTag());
				updateInsuranceRecord(r);
			}
		}
		return true;
		 

	}

	@Override
	public List<InsuranceRecordEntity> queryList(Map<String, Object> queryMap) {
		return insuranceRecordDao.queryList(queryMap);
	}
	
	@Override
	public  InsuranceRecordEntity  queryObject(Long id) {
		return  insuranceRecordDao.queryObject(id);
	}
	
	@Override
	public List queryListPOJO(Map<String, Object> map) {
		return insuranceRecordDao.queryListPOJO(map);
	}

	@Override
	public int queryPOJOTotal(Map<String, Object> map) {
		return insuranceRecordDao.queryPOJOTotal(map);
	}

	 @Override
		public boolean  checkInsuranceRecoredByOrderList(OrderMessageConsumerEntity body,List<MallOrderEntity> orderListTemp, Map<Long, MallGoodsInfoEntity> goodinfoMap, boolean isGroupGood) {
			
				boolean hasInsuranceRecord=false;
			
			MallOrderEntity newOrder = new MallOrderEntity ();
				 for ( MallOrderEntity order:orderListTemp) {
					 if (isGroupGood) {
						//获取商品表中的保险协议id 
							Map<String,Object> goodsIdMap= new HashMap<String,Object>();
							goodsIdMap.put("id", order.getCommodityId());
							MallGoodsInfoEntity goods = mallGoodsInfoService.queryObject(goodsIdMap);
							if (goods!=null&&goods.getNcId()!=null&&goods.getNcId().equals(body.getClass_id())) { //如果一样的话则说明是组合班型中的主订单
								newOrder= order;
								break;
							}
							newOrder= order;
					 }else if (!hasInsuranceRecord){					
						 hasInsuranceRecord=insuranceRecoredProcess(order,body,goodinfoMap,isGroupGood);
						 newOrder= order;
					 }
				 
				 
				 }
			
			//如果前面的遍历都没有生成的 则进行这个判断
			if (!hasInsuranceRecord) {
				
				return insuranceRecoredProcess(newOrder,body,goodinfoMap,isGroupGood);
			}
			
			return hasInsuranceRecord;
			
			
		}

	@Override
	public void saveInsuranceLog(InsuranceRecordEntity e) {
		e.setLogUpdateTime(new Date());
		insuranceRecordDao.saveInsuranceLog(e);
	}


	@Override
	public boolean insuranceRecoredProcess(MallOrderEntity order,OrderMessageConsumerEntity body, Map<Long, MallGoodsInfoEntity> goodinfoMap,boolean isGroupGood) {
		
		try {
		Map<String, Object> queryMap= new HashMap<String,Object>(); 
		
		if (order==null||order.getOrderId()==null) {
			this.logger.info("insuranceRecoredProcess:订单为空，无法生成投保记录." );
			return true;
		}
		
		
		queryMap.put("orderId", order.getOrderId());
		
		List<InsuranceRecordEntity> list= queryList(queryMap);
		//加入判断 如果是未投或者投送没有成功的 可以进入修改 
	 
		if (list!=null&&list.size()>0) {
			return InsuranceRecordUpdateCheck(list,order,body,goodinfoMap);//已经有生成过的返回true
		}
		/**
		 * 新增规则2 就是如果新的订单根据NC报名表能找到投保记录(dr in (0,1))的 则进入判断 
		 * 1.如果投保记录是投保成功的 则直接修改dr=0 并修改order_id 和sourceOrderID,不允许修改其他的字段
		 * 2.如果不是投保记录的  则重新变更别的值（sent_time 不允许修改）并将dr=0;
		 */
		Map<String, Object> ncMap= new HashMap<String,Object>();
		
		ncMap.put("notDr", "notDr");
		ncMap.put("ncId", order.getNcId());
		ncMap.put("userId", order.getUserId());
		List<InsuranceRecordEntity> ncList= queryList(ncMap);
		if (list!=null&&ncList.size()>0) {
			return InsuranceRecordUpdateCheck(ncList,order,body,goodinfoMap);//已经有生成过的返回true
		}
		
		
		boolean notInsurance ="N".equals( body.getIsInsurant())?true:false;//如果是N的话就不是保险的 
		
		
		if (notInsurance) { //不是保险的则当成已经生成的 则不会再生成了
			return true;
		}
		
		boolean payStatused ="Y".equals( body.getPaystatus())?true:false;//如果是Y的说明已经交齐款了 
		if (!payStatused) {//没有交齐款的不会生成 且输出日志
			this.logger.info("新订单{}未交齐款不会生成投保记录，报名表信息：{}。",order.getOrderId(),body.getSignTag());	
			this.InsuranceLog(InsuranceLogTypeEnum.OrderNotFullPlayNotCreateInsuranceRecord.getValue(),
					" 订单{},报名表信息：{}", order.getOrderId(),body.getSignTag());	
			return true;
		}
		
		
		InsuranceInfoEntity info= insuranceInfoService.queryByMallGoodsId(order.getCommodityId());
		if (info==null) {//如果是取不到编号的 就当成不用生成
			return true;
		}
		logger.debug("insuranceRecordCourseService:areaId{},goodsId:{},InsuranceInfoEntity:{}",order.getAreaId(),order.getAreaId(), order.getCommodityId(), info.getInsuranceInfoId());
		 List<InsuranceRecordCourseEntity> courseDetail=insuranceRecordCourseService.queryGooodsCourse(order.getAreaId(), order.getCommodityId(), info.getInsuranceInfoId());
		if (courseDetail==null||courseDetail.size()==0) {
            this.logger.info("InsuranceRecordCourse:投保记录取不到子表的主课程,但是会生成：{}订单的投保记录为投保失败。",order.getOrderId()); 
            this.InsuranceLog(InsuranceLogTypeEnum.CreateInsuranceRecordWithNoCourse.getValue(),
					" 订单{},报名表信息：{}", order.getOrderId(),body.getSignTag());	
			//return true;//如果取不到主课的 则不需要生成(已经取消这个)
		}
		 
		//
		
		
		
	 
		 
		InsuranceRecordEntity  e = new InsuranceRecordEntity(body,order,info,courseDetail);
		if (e.getInsuranceType()==1&&body.getSourceClassId()!=null) {
			Long sourceOrderId=this.getSourceOrderId(body.getSourceNcid(), body.getSourceClassId());
			if (sourceOrderId==null||sourceOrderId.intValue()==0) {
				this.logger.info("insuranceRecoredProcess:生成保险档案出错,因为本订单是单科的，并没有找到对应的源订单.orderId :{},sourceNCID:{}",order.getNcId(),body.getSourceClassId());
				return true;
			}
			e.setSourceOrderId(sourceOrderId);
		}
		
		//获取商品表中的保险协议id 
		Map<String,Object> goodsIdMap= new HashMap<String,Object>();
		goodsIdMap.put("id", order.getCommodityId());
		MallGoodsInfoEntity goods = mallGoodsInfoService.queryObject(goodsIdMap);
		
		if (null!=goods&&null!=goods.getInsuranceTemplateId()&&goods.getInsuranceTemplateId()>=0) {
			e.setTemplateId(goods.getInsuranceTemplateId());
		}else {
			this.logger.info("orderId:{}生成投保记录时出错，商品中没有相应的保险协议id,goodsId:{}",order.getOrderId(),order.getCommodityId());
			 this.InsuranceLog(InsuranceLogTypeEnum.CreateInsuranceRecordFailWithNoInfoId.getValue(),
						" 订单{},报名表信息：{},goodsId:{}", order.getOrderId(),body.getSignTag(), order.getCommodityId());	
			return true;//没有的则不生成投保记录
		}
		
		
		logger.debug("saveInsuranceRecord:{}",e);
		saveInsuranceRecord(e);//主子表保存
		}catch (Exception es) {
			es.printStackTrace();
			this.logger.error("insuranceRecoredProcess:生成保险档案出错,原因："+es.getMessage());
			 this.InsuranceLog(InsuranceLogTypeEnum.InsuranceRecordProcessError.getValue(),
						" 错误原因{} ",  es.getMessage());	
		}
		
		
		return true;
		
	}
	
 
	public void setSignerId(InsuranceRecordEntity in) {
		
		if (in==null) return;

		try {
	UsersEntity user = usersService.queryObject(in.getUserId());
	
	

	
	
	
	
	
		
		String phoneNo=user.getMobile();
		String phoneRegion=String.valueOf( LocationUtil.getMobileLocation(user.getMobile()));
		String certifyNum=in.getIdNumber();
		String identityRegion=String.valueOf( LocationUtil.getIdCardLocation(in.getIdNumber()));
		String username=user.getNickName();

		List<ContractRecord> list=	 contractRecordService.queryByIdcard (certifyNum);
		for (ContractRecord c:list) {
			if (c.getMobile().equals(phoneNo)&&c.getRealName().equals(username)&&c.getSignerId()>0) {
				 in.setSignerId(c.getSignerId());
				 return ;
			}
		}
		
		
	    in.setSignerId( contractService.saveUser(username, identityRegion, certifyNum, phoneRegion, phoneNo) );
		}catch(Exception e) {
			logger.error("set signerId has error!, InsuranceRecordEntity is :{},error message is :{}0",in.toString(),e.getMessage());
			in.setSignerId(0l);
		}
		
	}

	@Override
	public Long getSourceOrderId(String ncId,String ncCommodityId )	{
			 
		 
		if (ncId==null||ncCommodityId==null) {
			return 0L;
		}
		MallOrderEntity order= mallOrderService.queryObjectByNcIdAndCommodityId(ncId,  ncCommodityId );
		if (order!=null&&order.getOrderId()!=null&&order.getOrderId().intValue()>0) {
			return order.getOrderId();
		}
 
			 
		return 0L;

		 
		
	}

	@Override
	public void updateByContract(InsuranceRecordEntity e) {
		insuranceRecordDao.updateByContract(e);
	}
	
 
	@Override
    public void insuranceRecoredDeleteByOrderId(Long orderId) {
		 if (orderId != null && orderId > 0) {

	            try {

	                Long[] orderIds = new Long[1];
	                orderIds[0] = orderId;
	                insuranceRecoredDeleteByOrderIds(orderIds);
	                logger.info("closeOrderDelinsuranceRecord ... orderId={}", orderId);
	            } catch (Exception e) {
	                e.printStackTrace();
	                logger.info("closeOrderDelinsuranceRecord has Errors. ... message={}", e);
	            }
	        }
	}

	@Override
	public void passAction( Long[] ids) {
		insuranceRecordDao.passAction(new Date(),ids);
	}

	@Override
	public void passCancel(Long[] ids) {
		insuranceRecordDao.passCancel(ids);
	}

	@Override
	public boolean insuranceRecoredDeleteByOrderIds(Long[] orderIds) {
		 if (orderIds != null && orderIds.length > 0) {

	            try {
	            	 logger.info("closeOrderDelinsuranceRecord ... orderId={}", orderIds);
	            	Map<String, Object> map= new HashMap<String,Object>();
	            	map.put("orderIds", orderIds);
					List<InsuranceRecordEntity> list = insuranceRecordDao.queryList(map);
					
					for (InsuranceRecordEntity l:list) {
						
						if (l!=null&&l.getInsuranceRecordId()!=null) {
							if (l.getInsuranceStatus()==0||l.getInsuranceStatus()==2) {
								saveInsuranceLog(l);
								l.setDr(1);
								insuranceRecordDao.update(l);
								insuranceRecordCourseService.updateDrByinsuranceRecordId(l.getInsuranceRecordId() );
							}else {
								 logger.info("closeOrderDelinsuranceRecord but not delete InsuranceRecordId! orderId={} ,InsuranceRecordId={}", orderIds,l.getInsuranceRecordId());
							}
							
						}
					
					}
	               
	                
	               
	                return true;
	            } catch (Exception e) {
	                e.printStackTrace();
	                logger.info("closeOrderDelinsuranceRecord has Errors. ... message={}", e);
	            }
	        }
		return false;
	}
	/**
	 * 投保日志保存 
	 * @param logType 类型
	 * @param format
	 * @param arguments
	 */
	public void InsuranceLog(Integer logType, String format, Object... arguments) { 
		SysLogEntity sysLogEntity = new SysLogEntity();
		sysLogEntity.setCreateTime(new Date());
		sysLogEntity.setOperation(InsuranceLogTypeEnum.getText(logType));
		sysLogEntity.setMethod(getClass().getName());
		sysLogEntity.setParams(format+new Gson().toJson(arguments));
		sysLogEntity.setExecutionTime(new Date().getTime());
		sysLogService.save(sysLogEntity);
		
	}
	@Override
	public int checkCount(Long id){
		return insuranceRecordDao.checkCount(id);
	}
	

}
