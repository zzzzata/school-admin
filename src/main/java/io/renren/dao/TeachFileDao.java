package io.renren.dao;

import io.renren.entity.TeachFileEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 交付件 Mapper 接口
 * </p>
 *
 * @author hzr
 * @since 2018-11-27
 */
@Component
public interface TeachFileDao extends BaseDao<TeachFileEntity> {

    List<TeachFileEntity> queryListByFileKey(@Param("fileKey") String fileKey);

}
