package io.renren.pojo;

public class FilePOJO {
    //交付件ID
    private Long teachfileId;
    //学员课程资料ID
    private Long fileDetailId;
    //0:查看 1:下载
    private Integer type;

    public Long getTeachfileId() {
        return teachfileId;
    }

    public void setTeachfileId(Long teachfileId) {
        this.teachfileId = teachfileId;
    }

    public Long getFileDetailId() {
        return fileDetailId;
    }

    public void setFileDetailId(Long fileDetailId) {
        this.fileDetailId = fileDetailId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
