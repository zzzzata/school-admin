package io.renren.service;

public interface FileDownLoadViewService {
    void downloadFile(Long teachFileId, Long fileDetailId);

    void viewFile(Long teachFileId, Long fileDetailId);
}
