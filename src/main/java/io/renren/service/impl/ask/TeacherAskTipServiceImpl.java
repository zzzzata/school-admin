package io.renren.service.impl.ask;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.doc.ParamKey;
import io.renren.dao.SysUserDao;
import io.renren.dao.ask.TeacherAskTipDao;
import io.renren.entity.AppLabel;
import io.renren.entity.SysUserEntity;
import io.renren.entity.ask.TeacherAskTipEntity;
import io.renren.entity.courseliverelaterecord.CourseLiveRelateRecordEntity;
import io.renren.pojo.ask.TeacherAskTipPOJO;
import io.renren.pojo.courseliverelaterecord.CourseLiveRelateRecordPOJO;
import io.renren.service.AppLabelService;
import io.renren.service.ask.TeacherAskTipService;
import io.renren.utils.ExcelReaderJXL;
import io.renren.utils.PageUtils;
import io.renren.utils.R;
import io.renren.utils.ShiroUtils;
import io.renren.utils.http.HttpClientUtil4_3;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isNumeric;

/**
 * 会答教师提问标签权限Service 实现类
 *
 * @author chen xin yu
 * @date 2019-04-30 17:12
 */
@Service
public class TeacherAskTipServiceImpl implements TeacherAskTipService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TeacherAskTipDao teacherAskTipDao;
    @Resource
    private AppLabelService appLabelService;
    @Resource
    private SysUserDao sysUserDao;

    @Value("#{application['kuaida.api']}")
    private String KUAIDA_API;

    @Override
    public PageUtils getClassTeacherAskList(Integer currentPage, Integer pageSize, String nickName, String mobile, Integer unlimitedAsk) {
        //拼接班主任查询条件
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(nickName)) {
            params.put("nickName", nickName);
        }
        if (StringUtils.isNotBlank(mobile)) {
            params.put("mobile", mobile);
        }
        if (!ObjectUtils.isEmpty(unlimitedAsk)) {
            params.put("unlimitedAsk", unlimitedAsk);
        }
        //设置分页参数
        params.put(ParamKey.In.LIMIT, pageSize);
        params.put(ParamKey.In.OFFSET, (currentPage - 1) * pageSize);
        //分页查询教师提问标签权限集合
        List<TeacherAskTipPOJO> teacherAskTipPOJOList = teacherAskTipDao.queryTeacherAskTipList(params);
        //查询数据总量
        Integer total = teacherAskTipDao.teacherAskTipListTotal(params);
        if (!ObjectUtils.isEmpty(teacherAskTipPOJOList)) {
            //聚合所有教师的标签id串
            String allTipIds = teacherAskTipPOJOList.stream().filter(e -> !ObjectUtils.isEmpty(e.getTipIds())).map(e -> e.getTipIds()).distinct().collect(Collectors.joining(","));
            if (StringUtils.isNotBlank(allTipIds)) {
                //标签id集合
                List<String> allTipIdList = Arrays.stream(allTipIds.split(",")).collect(Collectors.toList());
                //根据标签id查询标签集合
                Map<String, Object> labelParams = new HashMap<>();
                labelParams.put("tipIdList", allTipIdList);
                List<AppLabel> tipList = appLabelService.queryList(labelParams);
                if (!ObjectUtils.isEmpty(tipList)) {
                    //标签id -> 标签对象
                    Map<Long, AppLabel> tipMap = tipList.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
                    //将标签与教师关联
                    teacherAskTipPOJOList.stream().filter(teacher -> StringUtils.isNotBlank(teacher.getTipIds())).forEach(teacher -> {
                        //获取当前教师的标签id集合
                        List<Long> teacherTipIdList = Arrays.stream(teacher.getTipIds().split(",")).map(e -> Long.parseLong(e)).collect(Collectors.toList());
                        List<String> childTipNameList = new ArrayList<>();
                        List<String> parentTipNameList = new ArrayList<>();
                        teacherTipIdList.forEach(tipId -> {
                            if (tipMap.containsKey(tipId)) {
                                AppLabel tip = tipMap.get(tipId);
                                //父级标签
                                if (tip.getParentId().equals(0)) {
                                    if (!parentTipNameList.contains(tip.getLabelName())) {
                                        parentTipNameList.add(tip.getLabelName());
                                    }
                                } else {
                                    //子级标签
                                    childTipNameList.add(tip.getLabelName());
                                    if (StringUtils.isNotBlank(tip.getParentName()) && !parentTipNameList.contains(tip.getParentName())) {
                                        parentTipNameList.add(tip.getParentName());
                                    }
                                }
                            }
                        });
                        //标签名称集合转换为字符串
                        teacher.setChildTipNames(childTipNameList.stream().collect(Collectors.joining(",")));
                        teacher.setParentTipNames(parentTipNameList.stream().collect(Collectors.joining(",")));
                    });
                }
            }
        }
        return new PageUtils(teacherAskTipPOJOList, total, pageSize, currentPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateClassTeacherAskAuthority(Long id, Long userId, Integer unlimitedAsk, String tipIds) {
        String mobile;
        SysUserEntity sysUser = sysUserDao.queryObject(userId);
        if (StringUtils.isNotBlank(sysUser.getMobile())) {
            mobile = sysUser.getMobile();
        } else if (isMobile(sysUser.getUsername())) {
            mobile = sysUser.getUsername();
        } else {
            return R.error("该用户未填写手机号，请先完善信息");
        }

        TeacherAskTipEntity teacherAskTip = new TeacherAskTipEntity();
        teacherAskTip.setId(id);
        teacherAskTip.setUserId(userId);
        teacherAskTip.setUnlimitedAsk(unlimitedAsk);
        teacherAskTip.setTipIds(tipIds);
        //当前操作用户
        SysUserEntity currentUser = ShiroUtils.getUserEntity();
        //更新人
        teacherAskTip.setUpdateUserId(currentUser.getUserId());
        teacherAskTip.setUpdateUserName(currentUser.getNickName());
        teacherAskTip.setUpdateTime(new Date());
        if (ObjectUtils.isEmpty(id)) {
            //创建人
            teacherAskTip.setCreateUserId(currentUser.getUserId());
            teacherAskTip.setCreateUserName(currentUser.getNickName());
            teacherAskTip.setCreateTime(new Date());
            //新增
            teacherAskTipDao.insert(teacherAskTip);
        } else {
            //更新
            teacherAskTipDao.updateById(teacherAskTip);
        }
        //调用会答接口，同步班主任提问权限
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("mobile", mobile);
        paramMap.put("unlimitedAsk", unlimitedAsk.toString());
        paramMap.put("tipIds", tipIds);
        String httpResult = HttpClientUtil4_3.sendHttpPost(KUAIDA_API + "/api/teacherTipAuthority/updateClassTeacherAskAuthority", paramMap);
        if (StringUtils.isNotBlank(httpResult)) {
            JSONObject resultJson = JSONObject.parseObject(httpResult);
            int statusCode = resultJson.getIntValue("code");
            if (statusCode == 1 || statusCode == 200) {
                return R.ok();
            } else {
                logger.error("updateClassTeacherAskAuthority--postReturn:{}", resultJson.toJSONString());
            }
        }
        //会答同步未成功，回滚事务
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        logger.error("updateClassTeacherAskAuthority--同步会答教师提问未成功，事务回滚");
        return R.error();
    }

    @Override
    public int importData(MultipartFile file) throws Exception {
        FileInputStream fio = (FileInputStream) file.getInputStream();
        List<String[]> dataList = ExcelReaderJXL.readExcel(fio);
        int num = 0;
        for (int i = 1; i < dataList.size(); i++) {
            String msg = "第" + i + "行，";
            String[] array = dataList.get(i);
            //用户id
            String userId = array[0];
            //无限次提问权限（开、关）
            String unlimitedAskStr = array[2];
            //标签id串
            String tipIds = array[3];
            if (StringUtils.isBlank(userId) || userId.trim().length() == 0) {
                throw new Exception(msg + "用户id不能为空");
            }
            if (StringUtils.isBlank(tipIds) || tipIds.trim().length() == 0) {
                throw new Exception(msg + "关联标签id不能为空");
            }
            userId = userId.trim();
            tipIds = tipIds.replaceAll("/",",").trim();
            Integer unlimitedAsk = 0;
            if (unlimitedAskStr.contains("开")) {
                unlimitedAsk = 1;
            }
            R result = this.updateClassTeacherAskAuthority(null,Long.valueOf(userId),unlimitedAsk,tipIds);
            if (result.isOK()) {
                num++;
            }
        }
        return num;
    }

    /**
     * 判断是否为手机号码
     *
     * @param str
     * @return
     */
    private static boolean isMobile(String str) {
        if (null != str && isNumeric(str.trim()) && str.trim().length() == 11) {
            return true;
        }
        return false;
    }
}
