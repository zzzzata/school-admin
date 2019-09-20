package io.renren.dao;

import org.apache.ibatis.annotations.Param;

public interface FileDownLoadViewDao {
    Integer downloadTeachFile(@Param("teachFileId") Long teachFileId);

    Integer downloadFileDetail(@Param("fileDetailId") Long teachFileId);

    Integer viewTeachFile(@Param("teachFileId") Long teachFileId);

    Integer viewFileDetail(@Param("fileDetailId") Long teachFileId);
}
