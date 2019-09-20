package io.renren.service.ask;

import io.renren.utils.PageUtils;
import io.renren.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 会答教师提问标签权限Service
 *
 * @author chen xin yu
 * @date 2019-04-30 17:10
 */
public interface TeacherAskTipService {
    /**
     * 班主任提问权限管理列表
     *
     * @param nickName      教师名称
     * @param mobile        手机号码
     * @param unlimitedAsk  无限次权限状态 0 未开通 1 开通
     * @param currentPage   当前页
     * @param pageSize      每页数据
     * @return PageUtils
     * @author chen xin yu
     * @date 2019-04-30 17:31
     */
    PageUtils getClassTeacherAskList(Integer currentPage, Integer pageSize, String nickName, String mobile, Integer unlimitedAsk);

    /**
     * 更新班主任提问权限及标签
     * @param id              教师提问标签权限id （为空新增，不为空更新）
     * @param tipIds          提问标签id串
     * @param userId         用户id
     * @param unlimitedAsk   无限制提问权限 0 关闭 1 开启
     * @return Map<String, Object>
     * @author chen xin yu
     * @date 2019-05-05 16:41
     */
    R updateClassTeacherAskAuthority(Long id, Long userId,Integer unlimitedAsk, String tipIds);


    int importData(MultipartFile file) throws Exception;
}
