package io.renren.controller;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import io.renren.entity.*;
import io.renren.entity.manage.StudentCourse;
import io.renren.entity.manage.Users;
import io.renren.rest.persistent.KGS;
import io.renren.service.*;
import io.renren.service.manage.StudentCourseService;
import io.renren.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Controller
public class OrderMessageConsumerController implements ChannelAwareMessageListener {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private static final String MD5_KEY = "%^\\$AF>.12*******zK";
    @Resource
    KGS orderNoKGS;
    @Resource
    KGS userKGS;
    private static final String ORDERNO_HEAD = "nc_";
    @Resource
    public StringRedisTemplate mainRedis;

    @Autowired
    private MallOrderService mallOrderService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private MallGoodsInfoService mallGoodsInfoService;
    @Autowired
    private GivingCoursesService givingCoursesService;

    @Autowired
    private MallAreaService mallAreaService;
    @Autowired
    private MallClassTypeService mallClassTypeService;
    @Autowired
    private MallClassService mallClassService;
    @Autowired
    private MallOrderSyncService mallOrderSyncService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private ClassGoodsDeptsService classGoodsDeptsService;
    @Autowired
    private ConfigGoodsService configGoodsService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MallLevelService mallLevelService;
    @Autowired
    private MallProfessionService mallProfessionService;
    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private DelayCloseOrderService delayCloseOrderService;
    @Autowired
    private RecordInfoService recordInfoService;
    @Autowired
    private ContractRecordService contractRecordService;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    /**
     * 默认密码 hqzk123456
     */
    private static String PSW = "1c3f360330c442c3cc62d1608fe7a3a3";

    /**
     * redis的key  存储需要自动分班的商品NCID
     **/
    private static String NCIDKEY = "order:good4class:";

    @Value("#{application['user.psw']}")
    private void setPSW(String str) {
        PSW = str;
    }

    /**
     * 同步学员信息到Udesk消息队列名
     */
    private static String SYNC_UDESK_MESSAGE = "";

    @Value("#{rabbitmq['customer.sync.udesk']}")
    private void setDIVIDE_CLASS_MESSAGE(String str) {
        SYNC_UDESK_MESSAGE = str;
    }

    @Autowired
    private InsuranceRecordService insuranceRecordService;
    /**
     * 这个是最后传出的订单列表 不管如果是生成过的还是新增加的都在这个里面
     */
    private Map<Long, MallOrderEntity> orderList = new HashMap<Long, MallOrderEntity>();
    /**
     *
     */
    private Map<Long, MallGoodsInfoEntity> goodinfoMap = new HashMap<Long, MallGoodsInfoEntity>();

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        synchronized (this) {
            logger.info("生成订单任务收到队列信息" + " [x] Received '" + new String(message.getBody(), "UTF-8") + "'");
            this.orderList = new HashMap<Long, MallOrderEntity>();
            this.goodinfoMap = new HashMap<Long, MallGoodsInfoEntity>();
            OrderMessageConsumerEntity orderMessageConsumerEntity = null; // 消费者实体类
            MallOrderSyncEntity mallOrderSync = new MallOrderSyncEntity(); // 异常消息实体类
            UsersEntity user = new UsersEntity(); //学员实体类
            TransactionStatus status = null;
            // 开启事务
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务
            status = transactionManager.getTransaction(def); // 获得事务状态
            String body = new String(message.getBody(), "UTF-8");
            try {
                Gson gson = new Gson();
                orderMessageConsumerEntity = gson.fromJson(body, OrderMessageConsumerEntity.class);
                String userPhone = null;
                String commodityNCPK = null;//NC班型PK，对应商品的NC_ID字段
                Boolean iscontractExist = true;//默认是已经生成协议记录
                Integer Vbill_status = null;
                Integer Sign_status = null;
           /*
           0.根据用户手机+商品id+dr=0判断蓝鲸是否存在该订单;
           1.根据vbillstatus字段判断是审核还是未审核状态,
                      -1表示NC订单无效,关闭蓝鲸订单及相关学习计划,
                     1表示取NC订单有效,如果是退学冲减或换班冲减的就关闭;
            2.根据Sign_status字段判断是退学冲减(6)或换班冲减(4),如果是关闭蓝鲸订单及相关学习计划,不是就重新生成订单;
            */
                if (orderMessageConsumerEntity != null) {
                    userPhone = orderMessageConsumerEntity.getPhone();
                    commodityNCPK = orderMessageConsumerEntity.getNc_commodity_id();
                    Vbill_status = orderMessageConsumerEntity.getVbill_status() == null ? 0 : orderMessageConsumerEntity.getVbill_status();
                    Sign_status = orderMessageConsumerEntity.getSign_status() == null ? 0 : orderMessageConsumerEntity.getSign_status();

                }

                if (orderMessageConsumerEntity != null && (Vbill_status == -1 || orderMessageConsumerEntity.getDr() == 1)) {
                    logger.error("query order for close...orderNcId={},userPhone={}, commodityNCPK={}", orderMessageConsumerEntity.getNc_id(), userPhone, commodityNCPK);
                    this.OrderMessageClose(userPhone, commodityNCPK, orderMessageConsumerEntity);
                } else if (orderMessageConsumerEntity != null && Vbill_status == 1 && (Sign_status == 6 || Sign_status == 4)) {
                    logger.error("query order for close...orderNcId={},userPhone={}, commodityNCPK={}", orderMessageConsumerEntity.getNc_id(), userPhone, commodityNCPK);
                    this.OrderMessageClose(userPhone, commodityNCPK, orderMessageConsumerEntity);
                } else {
                    // 生成订单
                    // 判断是否来自自考、会计、学来学往业务线
                    if ("zikao".equals(orderMessageConsumerEntity.getCompany_type()) || "kuaiji".equals(orderMessageConsumerEntity.getCompany_type()) || "xuelxuew".equals(orderMessageConsumerEntity.getCompany_type()) || "qianyinli".equals(orderMessageConsumerEntity.getCompany_type())) {
                        //临时日志--lck
                        this.logger.info("生成订单前订单信息：{}", orderMessageConsumerEntity);
                        //订单处理
                        this.OrderMessageProcess(body, mallOrderSync, user, iscontractExist, userPhone, commodityNCPK, orderMessageConsumerEntity);
                        //临时日志--lck
                        this.logger.info("生成订单后订单信息：{}", orderMessageConsumerEntity);
                        this.orderMessageDone(body, mallOrderSync, user, iscontractExist, userPhone, commodityNCPK, orderMessageConsumerEntity);
                    } else {
                        logger.error(" company_type() is error! message body=[{}]", body);
                        mallOrderSync.setErrType(5);//错误类型 1:商品不存在 2：商品对应省份不存在3：没有该班级存在4：系统异常 5:无业务线6:校区不存在
                        saveMallOrderSync(mallOrderSync, orderMessageConsumerEntity, body);
                        this.CheckRecordInfoNotOrder(orderMessageConsumerEntity, user);
                    }
                }
            } catch (Exception e) {
                try {
                    // 回滚
                    transactionManager.rollback(status);
                    e.printStackTrace();
                    logger.error(" OrderMessageConsumerController onMessage error Cause by:{}, message body=[{}]", e, body);
                    // 记录系统异常报错日志
                    mallOrderSync.setErrType(4);//错误类型 1:商品不存在 2：商品对应省份不存在3：没有该班级存在4：系统异常 5:无业务线6:校区不存在
                    saveMallOrderSync(mallOrderSync, orderMessageConsumerEntity, body);
                } catch (Exception e2) {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    logger.error(" OrderMessageConsumerController onMessage catch error Cause by:{}", e);
                    e2.printStackTrace();
                }
            } finally {
                try {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    // 提交事务
                    transactionManager.commit(status);
                } catch (Exception e) {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    logger.error(" 事务提交出错! exception message:{}, message body=[{}]", e, body);
                    // 记录系统异常报错日志
                    mallOrderSync.setErrType(4);//错误类型 1:商品不存在 2：商品对应省份不存在3：没有该班级存在4：系统异常 5:无业务线6:校区不存在
                    saveMallOrderSync(mallOrderSync, orderMessageConsumerEntity, body);
                }
            }
        }


    }


    /**
     * 这个是订单生成之后的操作方法
     *
     * @param body
     * @param mallOrderSync
     * @param user
     * @param iscontractExist
     * @param userPhone
     * @param commodityNCPK
     * @param orderMessageConsumerEntity
     */
    private void orderMessageDone(String body, MallOrderSyncEntity mallOrderSync, UsersEntity user,
                                  Boolean iscontractExist, String userPhone, String commodityNCPK,
                                  OrderMessageConsumerEntity orderMessageConsumerEntity) {
        try {
            //1.在遍历中加入了生成保险的判断

            //boolean hasInsuranceRecord="N".equals( orderMessageConsumerEntity.getIsInsurant())?true:false;//如果是N的话就不是保险的

            //boolean hasInsuranceRecord=false;
            List<MallOrderEntity> orderListTemp = new ArrayList<MallOrderEntity>();
            //GroupGood 用于判断是否组合班型 如果是组合班型的 只会生成一个保险协议和在线协议
            boolean isGroupGood = givingCoursesService.checkNcCommodity(orderMessageConsumerEntity.getClass_id());


            for (Entry<Long, MallOrderEntity> order : this.orderList.entrySet()) {
                orderListTemp.add(order.getValue());
                // 在没有改为推送到队列的情况下的则需要手动添加代码到这里执行 订单生成或者修改后的处理逻辑
                // 1.如果不是退单的就清掉延期关订单
                if (orderMessageConsumerEntity.getHandType() != null
                        && "1".equals(orderMessageConsumerEntity.getHandType())) {
                    delayCloseOrderService.DelayCloseOrderDeleteByNCid(order.getValue().getNcId(), 2);
                }
                // 2.检测是否要生成或者修改学员管理信息
                this.CheckRecordInfo(orderMessageConsumerEntity, order.getValue());

                // 处理订单生成或者修改后的逻辑结束
            }
            //定义错误出口
            if (orderListTemp == null || orderListTemp.size() == 0) {
                logger.error("处理生成订单后事件出错! 没有取到有生成的订单。", body);
            }


            //3.生成保险 由于保险的有空转为保险又有保险转不保险所以需要全部订单都进入保险的判断,
            insuranceRecordService.checkInsuranceRecoredByOrderList(orderMessageConsumerEntity, orderListTemp, goodinfoMap, isGroupGood);
            //生成在线协议判断 由于需要判断多张订单确保只生成一张协议 所以需要做成列表判断
            //临时日志--lck
            if (orderListTemp != null && orderListTemp.size() > 0) {
                for (MallOrderEntity myorder : orderListTemp) {
                    logger.error("生成协议前的订单信息{},该订单信息生成的订单{}", orderMessageConsumerEntity, myorder);
                }
            }
            this.contractRecordService.checkContractRecordByOrderList(orderMessageConsumerEntity, orderListTemp, isGroupGood);


        } catch (Exception es) {
            logger.error("处理生成订单后事件出错! exception message:{}, message body=[{}]", es, body);
        }
    }


    private UsersEntity getUser(OrderMessageConsumerEntity orderMessageConsumerEntity, SysDeptEntity deptEntity) {
        UsersEntity user = new UsersEntity();
        //根据同步订单的phone判断用户是否存在
        Map<String, Object> cmap = new HashMap<>();
        cmap.put("mobile", orderMessageConsumerEntity.getPhone());
        if (!this.usersService.mobileExist(cmap)) { //用户不存在则创建用户
            user.setUserId((long) userKGS.nextId());
            user.setMobile(orderMessageConsumerEntity.getPhone());
            user.setNickName(orderMessageConsumerEntity.getUser_name());
            user.setRealName(orderMessageConsumerEntity.getUser_name());
            user.setSex(orderMessageConsumerEntity.getSex());
            user.setPic("http://alifile.hqjy.com/hq/Avatar.png");
            user.setEmail(null);
            user.setPassword(PSW);
            user.setCreator(null);
            user.setCreationTime(new Date());
            user.setModifier(null);
            user.setModifiedTime(null);
            user.setLastLoginIp(null);
            user.setLastLoginTime(null);
            user.setRemake("同步NC报名单时生成用户");
            user.setNcId(orderMessageConsumerEntity.getNc_user_id());
            //  部门id 如果不为空的话 则赋得部门id 
            if (deptEntity != null && deptEntity.getDeptId() > 0) {
                user.setDeptId(deptEntity.getDeptId());
            }

            logger.info("create user: {}", user);
            //    try {
            usersService.save(user);
           /* }catch (Exception es){
            	   logger.info("create user error! user: {}",user);
            	   user.setUserId(0L); //如果出错了 那么将userid设置为0
            	   
            	es.printStackTrace();
            } */
        } else {
            try {
                Users tempUser = usersService.findByMobile(orderMessageConsumerEntity.getPhone());
                //如果数据库里的用户名与NC报名单的真实姓名不一致，则把NC报名单的姓名覆盖数据库的用户名
                if (tempUser != null && !tempUser.getNickName().equals(orderMessageConsumerEntity.getUser_name())) {
                    usersService.updateNameByPhone(orderMessageConsumerEntity.getPhone(), orderMessageConsumerEntity.getUser_name());
                }
            } catch (Exception e) {
                logger.error("updateNameByPhone error!! exception={}", e);
                e.printStackTrace();
            }
            Map<String, Object> param = new HashMap<>();
            param.put("mobilePhoneNo", orderMessageConsumerEntity.getPhone());
            long userId = usersService.getUserIdByMobilePhoneNo(param);
            user.setUserId(userId);
        }
/*        if (user.getUserId()!=null&&user.getUserId().intValue()==0) {
        	Map<String, Object> param = new HashMap<>();
            param.put("mobilePhoneNo", orderMessageConsumerEntity.getPhone());
            long userId = usersService.getUserIdByMobilePhoneNo(param);
            user.setUserId(userId);
        }
        */
        return user;
    }

    /**
     * 订单退费
     * 临时将信息处理的抽成方法 后面会统一重构
     *
     * @param body
     * @param mallOrderSync
     * @param user
     * @param iscontractExist
     * @param userPhone
     * @param commodityNCPK
     * @param orderMessageConsumerEntity
     * @author lintf
     * 2018年9月26日
     */
    private void OrderMessageClose(String userPhone, String commodityNCPK, OrderMessageConsumerEntity orderMessageConsumerEntity
    ) {

        String bkCommodityId = null;
        if (orderMessageConsumerEntity.getContractHead() != null && orderMessageConsumerEntity.getContractHead().getBkClassId() != null) {
            bkCommodityId = orderMessageConsumerEntity.getContractHead().getBkClassId();
        }
        //取得全部的组合班型的商品包含dr=1
        List<MallGoodsInfoEntity> goodinfoList = mallGoodsInfoService.groupGoodInfo(commodityNCPK, true, bkCommodityId);
        logger.info("phone={},commodityNCPK={},取得组合班型的有{}个", userPhone, commodityNCPK, goodinfoList.size());
        goodinfoMap = new HashMap<Long, MallGoodsInfoEntity>();
        Map<String, String> ncidMap = new HashMap<String, String>();
        for (MallGoodsInfoEntity g : goodinfoList) {
            if (g.getNcId() != null) {
                ncidMap.put(g.getNcId(), "1");
            }
            goodinfoMap.put(g.getId(), g);
        }
        if (goodinfoMap == null || ncidMap.get(commodityNCPK) == null) {
            //手动加得当前的商品
            MallGoodsInfoEntity m = mallGoodsInfoService.queryGoodsInfoByNcId(commodityNCPK);
            if (m != null && m.getId() != null && m.getId() > 0) {

                goodinfoMap.put(m.getId(), m);
            }
        }
        if (goodinfoMap == null || goodinfoMap.size() == 0) {
            logger.error("phone={},commodityNCPK={},关闭订单出错，根据班型的NCid取不到蓝鲸订单！", userPhone, commodityNCPK);
        }


        for (Entry<Long, MallGoodsInfoEntity> g : goodinfoMap.entrySet()) {

            if (g != null && g.getValue() != null && g.getValue().getNcId() != null && g.getValue().getNcId().trim().length() > 4) {

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("ncId", orderMessageConsumerEntity.getNc_id());
                map.put("commodityId", g.getValue().getId());
                //根据报名表订单ncid和商品id去关订单
                List<MallOrderEntity> list = mallOrderService.queryOrderList(map);
                if (list != null && list.size() > 0) {
                    logger.error("close order 1: orderNcId={},userPhone={}, ncId={};;;orderMessageConsumerEntity={} ",
                            orderMessageConsumerEntity.getNc_id(), userPhone, commodityNCPK, orderMessageConsumerEntity);
                    //关闭订单的业务逻辑：1.保险记录和在线签约的直接就关掉，只有退款来源是1（收款单删除单）的才会进入延期关订单 其他的直接关
                    //确认问题订单并关闭 只有手动推送来源是1或者关订单的类型是1的才关闭


                    //只要handType==1说是有定时服务手动推送的  直接 关订单的 不延迟
                    if (orderMessageConsumerEntity.getHandType() != null && "1".equals(orderMessageConsumerEntity.getHandType())) {
                        logger.error("close order 1:定时任务立即关订单, orderNcId={},userPhone={}   ,goodsid={}. ",
                                orderMessageConsumerEntity.getNc_id(), userPhone, g.getValue().getId());
                        mallOrderService.closeOrderByNC(list, 1);
                        //如果退款来源SourceType不是1 说明不是删除订单的  也直接关闭不延迟
                    } else if (orderMessageConsumerEntity.getSourceType() != null && orderMessageConsumerEntity.getSourceType() != 1) {
                        logger.error("close order 1:正常做了退费的也是立即关订单, orderNcId={},userPhone={}   ,goodsid={}. ",
                                orderMessageConsumerEntity.getNc_id(), userPhone, g.getValue().getId());
                        mallOrderService.closeOrderByNC(list, 1);
                    } else {
                        logger.error("close order 1:迟延关闭订单, orderNcId={},userPhone={}   ,goodsid={}. ",
                                orderMessageConsumerEntity.getNc_id(), userPhone, g.getValue().getId());
                        mallOrderService.closeOrderByNC(list, 0);
                        mallOrderService.closeOrderDelay(list, orderMessageConsumerEntity);
                    }

                    this.CheckRecordInfoList(orderMessageConsumerEntity, list);

                }

            }

        }


        //根据订单ID删除学员报读课程关系
        studentCourseService.deleteByNcId(orderMessageConsumerEntity.getNc_id());
    }


    /**
     * 临时将信息处理的抽成方法 后面会统一重构
     *
     * @param body
     * @param mallOrderSync
     * @param user
     * @param iscontractExist
     * @param userPhone
     * @param commodityNCPK
     * @param orderMessageConsumerEntity
     * @author lintf
     * 2018年9月26日
     */
    private void OrderMessageProcess(String body, MallOrderSyncEntity mallOrderSync,
                                     UsersEntity user, Boolean iscontractExist,
                                     String userPhone,
                                     String commodityNCPK,
                                     OrderMessageConsumerEntity orderMessageConsumerEntity) {

        //根据nc_commodity_id查找对应商品
        //  MallGoodsInfoEntity goodsInfo = mallGoodsInfoService.queryGoodsInfo(orderMessageConsumerEntity.getNc_commodity_id());
        //根据 nc_commodity_id取得组合班型的商品 false为只取dr=0的
        //临时日志--lck
        this.logger.info("OrderMessageProcess生成订单前的订单信息：{}", orderMessageConsumerEntity);

        String bkCommodityId = null;
        if (orderMessageConsumerEntity.getContractHead() != null && orderMessageConsumerEntity.getContractHead().getBkClassId() != null) {
            bkCommodityId = orderMessageConsumerEntity.getContractHead().getBkClassId();
        }

        List<MallGoodsInfoEntity> goodsList = mallGoodsInfoService.groupGoodInfo(orderMessageConsumerEntity.getNc_commodity_id(), false, bkCommodityId);

        //logger.info("sync_rabbit_sign order={} goodsInfo=={}", orderMessageConsumerEntity, goodsInfo == null?"null":(goodsInfo.getId()+"--"+goodsInfo.getName()));
        //根据nc_school_pk（对应部门表的nc_id）查找对应部门
        SysDeptEntity deptEntity = this.sysDeptService.queryObjectByNcId(orderMessageConsumerEntity.getNc_school_pk());


        //1.判断校区是否存在
        if (null == deptEntity) {
            //保存校区不存在异常
            mallOrderSync.setErrType(6);//错误类型 1:商品不存在 2：商品对应省份不存在3：没有该班级存在4：系统异常 5:无业务线 6:校区不存在
            saveMallOrderSync(mallOrderSync, orderMessageConsumerEntity, body);
            logger.error("同步NC报名单异常,校区不存在[School is not exists]: School_code={},School_name={}### mallOrderSync={}", orderMessageConsumerEntity.getSchool_code(), orderMessageConsumerEntity.getSchool_name(), mallOrderSync);
            return;
        }
        //2.处理用户 当用户不存在时创建用户 用户存在时修改用户名称 里面的错误不再捕获,根据手机号查询是否有用户，不存在则创建 2019-05-22 李海飞
        user = this.getUser(orderMessageConsumerEntity, deptEntity);

        if (null == user || user.getUserId() == 0) {
            //保存用户不存在异常
            mallOrderSync.setErrType(10);//错误类型 1:商品不存在 2：商品对应省份不存在3：没有该班级存在4：系统异常 5:无业务线 6:校区不存在;9:userid不存在, 10:用户不存在
            saveMallOrderSync(mallOrderSync, orderMessageConsumerEntity, body);
            logger.error("同步NC报名单异常,报名单userid不存在，并且生成用户不成功[userId is not exists and save user fail]: userId={},### mallOrderSync={}", orderMessageConsumerEntity.getUserId(), mallOrderSync);
            return;
        }
        //3.有用户之后可以调用生成学员课程信息方法
        studentCourseService.insertBatch(user.getUserId(), orderMessageConsumerEntity);


        //4.当取得的商品为空时 保存错误信息并退出

        if (goodsList == null || goodsList.size() == 0) {
            //保存商品不存在异常
            mallOrderSync.setErrType(1);//错误类型 1:商品不存在 2：商品对应省份不存在3：没有该班级存在4：系统异常 5:无业务线6:校区不存在
            saveMallOrderSync(mallOrderSync, orderMessageConsumerEntity, body);
            logger.info("同步NC报名单异常,蓝鲸商品不存在[goodinfo is not exists]: nc_commodity_id={}, class_name={}### mallOrderSync={}", orderMessageConsumerEntity.getNc_commodity_id(), orderMessageConsumerEntity.getClass_name(), mallOrderSync);
            //商品不存在时生成学员档案信息
            this.CheckRecordInfoNotOrder(orderMessageConsumerEntity, user);
        }


        if (goodsList != null && goodsList.size() > 0) {

            for (MallGoodsInfoEntity goodsInfo : goodsList) {
                this.goodinfoMap.put(goodsInfo.getId(), goodsInfo);
                //根据同步订单的商品GoodsId和province_pk查找对应省份
                Map<String, Object> map = new HashMap<>();
                map.put("ncProvinceId", orderMessageConsumerEntity.getProvince_pk());
                //这里转换一下ncid 
                map.put("ncCommodityId", goodsInfo.getNcId());
                Integer areaId = mallAreaService.queryAreaId(map);
                if (areaId == null || areaId < 0) {
                    //保存省份不存在异常
                    mallOrderSync.setErrType(2);//错误类型 1:商品不存在 2：商品对应省份不存在3：没有该班级存在4：系统异常 5:无业务线6:校区不存在
                    saveMallOrderSync(mallOrderSync, orderMessageConsumerEntity, body);
                    logger.error("同步NC报名单异常,省份不存在[area is not exists]: ncProvinceId={},ncCommodityId={}, ### mallOrderSync={}", orderMessageConsumerEntity.getProvince_pk(), orderMessageConsumerEntity.getNc_commodity_id(), mallOrderSync);
                    //增加生成用户时生成学员档案
                    this.CheckRecordInfoNotOrder(orderMessageConsumerEntity, user);
                    continue;
                }


                //增加生成订单判断,如果根据电话号和商品id能查到订单的 并且那个订单的ncid为空的 ,则绑定为该订单 且不会再生成新订单 2018-09-19

                //根据商品id查询订单表是否有记录
                List<MallOrderEntity> mallOrderListTemp = mallOrderService.queryListByCommodityIdAndUserIdOrNcId(goodsInfo.getId(), user.getUserId(), orderMessageConsumerEntity.getNc_id());


                boolean hasOrder = false; //没有开通的订单
                boolean hasOrder_ncid_goodid = false;//根据商品ID和订单id确认是否重单
                if (mallOrderListTemp != null && mallOrderListTemp.size() > 0) {

                    MallOrderEntity result = null;

                    for (MallOrderEntity m : mallOrderListTemp) {

                        if (m.getPayStatus() == 2 && m.getNcId() == null && m.getUserId().equals(user.getUserId())) {

                            result = m; //如果取到没有ncid的则可以关联
                        }
                        if (m.getNcId() != null && m.getNcId().equals(orderMessageConsumerEntity.getNc_id())) {
                            result = null; //如果有一个是关联系的 则不能再绑定
                            hasOrder = true;//如果有一个是联系的话 那么就是已经开通过订单的了
                            // iscontractExist = true;//已经生成过在线协议了
                            //临时日志--lck
                            this.logger.info("生成订单前的订单信息：{},已生成的订单{}", orderMessageConsumerEntity, m);
                            this.orderList.put(m.getOrderId(), m);//这个是已经生成的订单的会重新加到这个里面
                            break;
                        }
                    }


                    if (result != null) {
                        hasOrder = true;
                        //如果不为空,则保存ncid到订单中
                        result.setNcId(orderMessageConsumerEntity.getNc_id());
                        if (orderMessageConsumerEntity.getCode() != null && result.getNcCode() == null) {
                            result.setNcCode(orderMessageConsumerEntity.getCode());
                        }

                        mallOrderService.update(result);
                        //临时日志--lck
                        this.logger.info("生成订单前的订单信息：{},已生成的订单{}", orderMessageConsumerEntity, result);
                        this.orderList.put(result.getOrderId(), result);//这个是已经生成的订单的会重新加到这个里面

                        logger.info("绑定NC来的报名表到蓝鲸平台上没有NCID的订单,ncid为{},mallOrderEntity 为{}", orderMessageConsumerEntity.getNc_id(), result);
                        /*if (!iscontractExist && goodsInfo.getContractTemplateId() != null && result.getSourceType() == 0) {//来源为0的没有生成签约的并且商品中有定义了模板的
                            //如果来源为0的则会变更信息 (支付方式和班型)
                            contractRecordService.ContractRecordChangeInfo(orderMessageConsumerEntity, result);
                            //变更学历为蓝鲸平台的学历
                            this.setOrderMessageRecord(orderMessageConsumerEntity, goodsInfo);
                            //更新或者生成在线签约的
                            this.createContractRecord(orderMessageConsumerEntity, result, goodsInfo.getContractTemplateId());
                            iscontractExist = true;
                        }*/

                    }


                }


                if (hasOrder) { //如果为true则说明是已经开通过订单的 进入重复订单逻辑

                    //根据报名表号已查询到订单，不做新增操作
                    logger.info(" order has existed! nc_code={},phone={},NcCommodityId={},ncCommodityIdName={}", orderMessageConsumerEntity.getCode(), orderMessageConsumerEntity.getPhone(), orderMessageConsumerEntity.getNc_commodity_id(), orderMessageConsumerEntity.getClass_name());

                    continue;
                }

			/*	//再加一个判断 如果是一个报名表订单只能开通一个相同商品的限制
				 int  hasExistByNcidAadGoodsId=mallOrderService.queryOrderExistByNCidAndCommodityId(orderMessageConsumerEntity.getNc_id(), goodsInfo.getId());
			if ( hasExistByNcidAadGoodsId>0) {
				logger.info(" order has existed, maybe mobile chenge,but not create new order! nc_code={},phone={},NcCommodityId={},ncCommodityIdName={}",orderMessageConsumerEntity.getCode(),orderMessageConsumerEntity.getPhone(),orderMessageConsumerEntity.getNc_commodity_id(),orderMessageConsumerEntity.getClass_name());
			    continue;
			}*/

                //如果没有问题的话最终会生成订单

                this.MallOrderProcess(user, goodsInfo, deptEntity, areaId, iscontractExist, orderMessageConsumerEntity);


            }
        }


    }

    private void MallOrderProcess(UsersEntity user,
                                  MallGoodsInfoEntity goodsInfo, SysDeptEntity deptEntity,
                                  Integer areaId,
                                  Boolean iscontractExist,
                                  OrderMessageConsumerEntity orderMessageConsumerEntity) {

        MallOrderEntity mallOrderEntity = new MallOrderEntity(); // 订单实体类


        // 用户id
        mallOrderEntity.setUserId(user.getUserId());

        // 业务线
        mallOrderEntity.setBusinessId(orderMessageConsumerEntity.getCompany_type());
        // 支付状态 0.未支付 1.发起支付 ,2.支付成功
        mallOrderEntity.setPayStatus(2);
        // 订单号
        mallOrderEntity.setOrderNo(ORDERNO_HEAD + orderNoKGS.nextId());
        // 商品名称
        mallOrderEntity.setCommodityName(goodsInfo.getName());
        // 商品ID
        mallOrderEntity.setCommodityId(goodsInfo.getId());
        //商品是否开通题库权限
        mallOrderEntity.setOnlyOne(goodsInfo.getOnlyOne());
        // 订单名称
        mallOrderEntity.setOrderName(goodsInfo.getName());
        // 订单描述
        mallOrderEntity.setOrderDescribe(goodsInfo.getName());
        // 订单总额
        mallOrderEntity.setTotalMoney(Double.valueOf(goodsInfo.getOriginalPrice()));
        // 备注
        mallOrderEntity.setRemark(null);
        // 平台IP
        mallOrderEntity.setSchoolId(goodsInfo.getSchoolId());
        // 支付金额
        mallOrderEntity.setPayMoney(Double.valueOf(goodsInfo.getOriginalPrice()));
        // 支付時間 NC的支付时间--和有效期有关
        mallOrderEntity.setPayTime(DateUtils.getDate(Long.valueOf(orderMessageConsumerEntity.getTs()), 1));
        // 订单有效期 date_validity
        mallOrderEntity.setDateValidity(DateUtils.orderDateValiditySync(DateUtils.getDate(Long.valueOf(orderMessageConsumerEntity.getTs()), 1), goodsInfo.getDayValidity()));

        if (orderMessageConsumerEntity.getItem_type() != null && orderMessageConsumerEntity.getItem_type() == 25) {
            // 来源类型为25的说明是双师的
            mallOrderEntity.setProductId(8L);
        } else {

            // 产品线
            mallOrderEntity.setProductId(orderMessageConsumerEntity.getProduct_type());

        }


        // 优惠金额
        mallOrderEntity.setFavorableMoney(0.0);
        // 优惠券ID
        mallOrderEntity.setDiscountId(null);
        // 第三方支付回调时间
        mallOrderEntity.setPayCallblackTime(new Date());
        // 第三方支付回调信息
        mallOrderEntity.setPayCallblackMsg(null);
        // 支付宝交易号
        mallOrderEntity.setAlipayTradeNo(null);
        // 支付IP
        mallOrderEntity.setPayip(null);
        // 生成支付地址
        mallOrderEntity.setPayUrl(null);
        // 商品图片
        mallOrderEntity.setPic(goodsInfo.getOriginPath());
        // 商品小图
        mallOrderEntity.setSpic(goodsInfo.getThumbPath());
        // dr 0.正常 1.删除
        mallOrderEntity.setDr(0);
        // 用户操作状态 0.正常 1.取消 2.删除
        mallOrderEntity.setUstatus(0);
        // 微信开放ID
        mallOrderEntity.setWxOpenId(null);
        // 0.未选择 1.支付宝 2.微信
        mallOrderEntity.setPayType(0);
        // 银行编码
        mallOrderEntity.setBankCode(null);
        // 银行名称
        mallOrderEntity.setBankName(null);
        // 創建用戶
        mallOrderEntity.setCreator(null);
        // 创建时间
        mallOrderEntity.setCreationTime(new Date());
        // 最近修改用户
        mallOrderEntity.setModifier(null);
        // 最近修改时间
        mallOrderEntity.setModifiedTime(null);
        //// 来源 0.线上;1.NC;2測試
        mallOrderEntity.setSourceType(1);
        // NCID
        mallOrderEntity.setNcId(orderMessageConsumerEntity.getNc_id());
        // 省份ID
        mallOrderEntity.setAreaId(Long.valueOf(areaId));
        // nc同步code值
        mallOrderEntity.setNcCode(orderMessageConsumerEntity.getCode());
        // nc同步时间
        Date date = DateUtils.getDate(Long.valueOf(orderMessageConsumerEntity.getSyn_time()), 1);
        mallOrderEntity.setSynTime(date);
        // 层次ID
        mallOrderEntity.setLevelId(goodsInfo.getLevelId());
        //有保险并且是单科的 才会赋订单是否单科
        if (orderMessageConsumerEntity.getIsInsurant() != null && "Y".equals(orderMessageConsumerEntity.getIsInsurant()) && goodsInfo.getInsuranceType() != null && goodsInfo.getInsuranceType() == 1) {

            mallOrderEntity.setIsOnlyInsurance(1);
        }
        //报名表金额设置为来源为报名表的总应收金额 对应报名表的def11
        mallOrderEntity.setRegMoney(Double.valueOf(orderMessageConsumerEntity.getRegMoney() == null ? "0" : orderMessageConsumerEntity.getRegMoney()));


        Map<String, Object> classMap = new HashMap<>();

//        String aaa = this.mainRedis.opsForValue().get(orderMessageConsumerEntity.getNc_commodity_id());

        if ("kuaiji".equals(orderMessageConsumerEntity.getCompany_type())) {
            Boolean queryResult = queryKValue(orderMessageConsumerEntity.getNc_commodity_id());
            if (queryResult) {
                //根据商品id，部门id查找对应班级对照表的班级ID
                classMap.put("goodId", goodsInfo.getId());
                classMap.put("deptId", deptEntity.getDeptId());
                List<ClassGoodsDeptsEntity> ClassGoodsDeptsList = classGoodsDeptsService.queryClassList(classMap);
                if (null != ClassGoodsDeptsList && !ClassGoodsDeptsList.isEmpty()) {
                    // 班级ID
                    mallOrderEntity.setClassId(ClassGoodsDeptsList.get(0).getClassId());
                }
            } else {
                int configGoodsNum = configGoodsService.queryNumByGoodId(orderMessageConsumerEntity.getNc_commodity_id());
                if (configGoodsNum > 0) {
                    saveKValue(orderMessageConsumerEntity.getNc_commodity_id());
                    //根据商品id，部门id查找对应班级对照表的班级ID
                    classMap.put("goodId", goodsInfo.getId());
                    classMap.put("deptId", deptEntity.getDeptId());
                    List<ClassGoodsDeptsEntity> ClassGoodsDeptsList = classGoodsDeptsService.queryClassList(classMap);
                    if (null != ClassGoodsDeptsList && !ClassGoodsDeptsList.isEmpty()) {
                        // 班级ID
                        mallOrderEntity.setClassId(ClassGoodsDeptsList.get(0).getClassId());
                    }
                } else {
                    //根据省份（area_id），专业（profession_id），学历（level_id）查找对应班级ID
                    classMap.put("areaId", areaId);
                    classMap.put("professionId", goodsInfo.getProfessionId());
                    classMap.put("levelId", goodsInfo.getLevelId());
                    List<MallClassEntity> mallClassList = mallClassService.queryClassList(classMap);
                    if (null != mallClassList && !mallClassList.isEmpty()) {
                        // 班级ID
                        mallOrderEntity.setClassId(mallClassList.get(0).getClassId());
                    }
                }
            }

        } else {
            //根据省份（area_id），专业（profession_id），学历（level_id）查找对应班级ID
            classMap.put("areaId", areaId);
            classMap.put("professionId", goodsInfo.getProfessionId());
            classMap.put("levelId", goodsInfo.getLevelId());
            List<MallClassEntity> mallClassList = mallClassService.queryClassList(classMap);
            if (null != mallClassList && !mallClassList.isEmpty()) {
                // 班级ID
                mallOrderEntity.setClassId(mallClassList.get(0).getClassId());
            }
        }
        // 班型ID
        mallOrderEntity.setClassTypeId(goodsInfo.getClassTypeId());
        // 专业ID
        mallOrderEntity.setProfessionId(goodsInfo.getProfessionId());
        // 状态
        mallOrderEntity.setStatus(1);
        // TODO 部门id
        mallOrderEntity.setDeptId(deptEntity.getDeptId());
        this.mallOrderService.saveOrder(mallOrderEntity);
        //临时日志--lck
        this.logger.error("生成订单前的订单信息：{},新生成的订单{}", orderMessageConsumerEntity, mallOrderEntity);
        this.orderList.put(mallOrderEntity.getOrderId(), mallOrderEntity);//这个是新生成的订单的会重新加到这个里面


        //如果指定了班级就发送同步Udesk消息队列
        if (null != mallOrderEntity.getClassId() && mallOrderEntity.getClassId() > 0) {
            //发送同步Udesk消息队列
            //sendUdeskMq(Long classId,String nick_Name,String phone,String payTime,String province,String level,String schoolName,String profession,String commodity)
            String phone = orderMessageConsumerEntity.getPhone().trim() + "-" + mallOrderEntity.getOrderId();
            sendUdeskMq(
                    mallOrderEntity.getClassId(),
                    orderMessageConsumerEntity.getUser_name(),
                    phone,
                    DateUtils.format(mallOrderEntity.getPayTime(), DateUtils.DATE_PATTERN),
                    orderMessageConsumerEntity.getProvince_name(),

                    mallLevelService.queryObject(goodsInfo.getLevelId()).getLevelName(),
                    orderMessageConsumerEntity.getSchool_name(),
                    mallProfessionService.queryObjectById(goodsInfo.getProfessionId()).getProfessionName(),
                    goodsInfo.getName()
            );
        }

        logger.info("save order: phone={},OrderNo={},CommodityId={},OrderName={}", orderMessageConsumerEntity.getPhone(), mallOrderEntity.getOrderNo(), mallOrderEntity.getCommodityId(), mallOrderEntity.getOrderName());

    }


    /**
     * 当不会生成订单时 使用这个来生成没有订单号的学员档案及报名信息
     *
     * @param orderMessageConsumerEntity
     * @param user
     * @author lintf
     * 2018年8月23日
     */
    private void CheckRecordInfoNotOrder(OrderMessageConsumerEntity orderMessageConsumerEntity, UsersEntity user) {
        MallOrderEntity m = new MallOrderEntity();
        if (user != null && user.getUserId() != null && user.getUserId() > 0) {

        } else {
            Map<String, Object> cmap = new HashMap<>();
            cmap.put("mobile", orderMessageConsumerEntity.getPhone());
            if (!this.usersService.mobileExist(cmap)) { //用户不存在则创建用户
                user.setUserId((long) userKGS.nextId());
                //   user.setSchoolId(goodsInfo.getSchoolId());
                user.setMobile(orderMessageConsumerEntity.getPhone());
                user.setNickName(orderMessageConsumerEntity.getUser_name());
                user.setSex(orderMessageConsumerEntity.getSex());
                user.setPic("http://alifile.hqjy.com/hq/Avatar.png");
                user.setEmail(null);
                user.setPassword(PSW);
                user.setCreator(null);
                user.setCreationTime(new Date());
                user.setModifier(null);
                user.setModifiedTime(null);
                user.setLastLoginIp(null);
                user.setLastLoginTime(null);
                user.setRemake(null);
                user.setNcId(orderMessageConsumerEntity.getNc_user_id());
                // TODO 部门id
                //   user.setDeptId(deptEntity.getDeptId());
                logger.info("create user: {}", user);
                usersService.save(user);
                m.setUserId(user.getUserId());
            } else {
                Map<String, Object> usersMap = new HashMap<String, Object>();
                usersMap.put("mobile", orderMessageConsumerEntity.getPhone());
                int usersId = usersService.queryUserId(usersMap);
                if (usersId > 0) {
                    // 用户id
                    m.setUserId((long) usersId);
                }
            }
        }


        this.CheckRecordInfo(orderMessageConsumerEntity, m);
    }

    private void CheckRecordInfoList(OrderMessageConsumerEntity orderMessageConsumerEntity, List<MallOrderEntity> list) {
        if (list == null) {
            this.CheckRecordInfoNotOrder(orderMessageConsumerEntity, null);
        } else {
            for (MallOrderEntity m : list) {
                this.CheckRecordInfo(orderMessageConsumerEntity, m);
            }

        }


    }

    private void CheckRecordInfo(OrderMessageConsumerEntity orderMessageConsumerEntity,
                                 MallOrderEntity m) {
        try {
            recordInfoService.CheckRecordInfo(m, orderMessageConsumerEntity);
        } catch (Exception e) {
            logger.error("  CheckRecordInfo has error! orderMessageConsumerEntity={},mallOrderEntity={} ,error message ={}",
                    orderMessageConsumerEntity, m, e.getMessage()
            );
        }
    }

    private void saveMallOrderSync(MallOrderSyncEntity mallOrderSync, OrderMessageConsumerEntity orderMessageConsumerEntity, String body) {
        mallOrderSync.setCreateTime(new Date(System.currentTimeMillis()));//创建时间
        mallOrderSync.setNcId(orderMessageConsumerEntity.getNc_id());//NC主键
        mallOrderSync.setNcCreateTime(new Date(orderMessageConsumerEntity.getCreate_time()));//NC创建时间
        mallOrderSync.setSchoolId(null);
        mallOrderSync.setNcClassTypeId(orderMessageConsumerEntity.getClass_type_id());//NC业务平台
        mallOrderSync.setDr(orderMessageConsumerEntity.getDr());
        mallOrderSync.setState(0);//状态 0:已处理 1:未处理 2:过期不处理

        Date syncdate = DateUtils.getDate(Long.valueOf(orderMessageConsumerEntity.getSyn_time()), 1);
        mallOrderSync.setSynTime(syncdate);//NC同步时间

        mallOrderSync.setNcJson(body);//同步信息
        mallOrderSync.setMobile(orderMessageConsumerEntity.getPhone());//nc同步过来的手机号
        mallOrderSync.setNcCode(orderMessageConsumerEntity.getCode());//nc同步过来的报名表号

        this.mallOrderSyncService.save(mallOrderSync);
    }

    /**
     * 日期格式化
     *
     * @param map
     * @param dateKey
     */
    private static void mapDateFormatter(Map<String, Object> map, String dateKey) {
        if (null != map && StringUtils.isNotBlank(dateKey)) {
            Object object = map.get(dateKey);
            if (null != object) {
                try {
                    Date date = (Date) object;
                    String format = DateUtils.format(date, DateUtils.DATE_TIME_PATTERN);
                    map.put(dateKey, format);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String tkCourseCode(List<String> codeList) {
        String result = null;
        if (null != codeList && !codeList.isEmpty()) {
            StringBuffer sbf = new StringBuffer();
            for (String string : codeList) {
                sbf.append(string + ",");
            }
            result = sbf.substring(0, sbf.length() - 1);
        }
        return result;
    }

    //查询redis缓存是否有商品nc_id
    private boolean queryKValue(String commodityNcId) {
        boolean flag = false;
        String KValue = this.mainRedis.opsForValue().get(NCIDKEY + commodityNcId);
        if (null != KValue) {
            flag = true;
        }
        return flag;
    }

    //将商品nc_id存到redis缓存
    private void saveKValue(String commodityNcId) {
        //Long timestamp = new Date().getTime();
        //过期时间为1天
        this.mainRedis.opsForValue().set(NCIDKEY + commodityNcId, "1", 86400000);
    }

    //发送到Udesk消息队列
    private void sendUdeskMq(Long classId,
                             String nick_Name,
                             String phone,
                             String payTime,
                             String province,
                             String level,
                             String schoolName,
                             String profession,
                             String commodity) {
        Gson gson = new Gson();
        Map<String, Object> mapUdesk = new HashMap<>();
        Map<String, Object> map = this.sysUserService.queryObjectByClassId(classId);
        Integer ownerId = null;
        String tags = null;
        String className = null;
        if (null != map) {
            if (null != map.get("ownerId")) {
                ownerId = (Integer) map.get("ownerId");
            }
            if (null != map.get("nickName")) {
                tags = (String) map.get("nickName");
            }
            if (null != map.get("className")) {
                className = (String) map.get("className");
            }
        }
        mapUdesk.put("nick_Name", nick_Name);
        mapUdesk.put("phone", phone);
        mapUdesk.put("tags", tags);
        mapUdesk.put("ownerId", ownerId);
        mapUdesk.put("payTime", payTime);
        mapUdesk.put("province", province);
        mapUdesk.put("level", level);
        mapUdesk.put("className", className);
        mapUdesk.put("schoolName", schoolName);
        mapUdesk.put("profession", profession);
        mapUdesk.put("commodity", commodity);

        String json = gson.toJson(mapUdesk).toString();
        try {
            amqpTemplate.convertAndSend(SYNC_UDESK_MESSAGE, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
