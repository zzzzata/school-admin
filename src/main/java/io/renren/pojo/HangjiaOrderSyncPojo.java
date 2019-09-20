package io.renren.pojo;

public class HangjiaOrderSyncPojo {

    private static final long serialVersionUID = 1L;

    //同步的状态值
    private Integer syncStatus;
    //订单编号
    private String orderCode;
    //实付金额
    private Double price;
    //支付方式
    private Integer paymentType;
    //支付来源
    private Integer source;
    //创建时间
    private Long createTime;
    //修改时间
    private Long updateTime;
    //商品ID
    private Long goodsId;
    //商品名称
    private String goodsName;
    //用户ID
    private Long studentId;
    //状态 0：待支付，1：已支付，2：交易完成，3：交易关闭
    private Integer status;
    //商品图片
    private String goodsImage;
    //退款状态
    private Integer refundStatus;
    //课程有效期
    private Integer validityDate;
    //是否免费
    private Integer isFree;
    //删除状态
    private Integer deleteStatus;

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

    public int getIsFree() {
        return isFree;
    }

    public void setIsFree(int isFree) {
        this.isFree = isFree;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Integer validityDate) {
        this.validityDate = validityDate;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
