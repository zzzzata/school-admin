package io.renren.enums;

 /**
  * 投保记录日志类型
  * @author lintf
  *
  */
public enum InsuranceLogTypeEnum { 
	
	InsuranceRecordProcessError(0,"订单生成投保记录时出现错误"),
	 InsuranceRecordChange(1,"投保记录变更"),
	 InsuranceRecordChangeInfo(2,"投保记录变更投保产品信息"),
	 InsuranceRecordChangeFromDelectedRecord(3,"投保记录从已经删除的变更为正常"),
	 InsuranceRecordChangeDisableWithPassStatus(4,"审批通的投保记录不能变更"),  
	OrderNotFullPlayNotDeleteInsuranceRecord(5,"没有交齐款删除已经生成的投保记录"),
	OrderNotFullPlayNotCreateInsuranceRecord(6,"没有交齐款不会生成投保记录"),
	OrderNotFullPlayNotDeleteInsuranceRecordDisableWithPassStatus(7,"审批通的过没有交齐款不会删除已经生成的投保记录"), 
	InsuranceRecordNGoodsinfoNotExistNotChangeRecord(8,"投保的商品不存在投保记录不变更"),
	InsuranceRecordNotActionNotChangeRecord(9,"商品不投保了 投保记录变更"),
	InsuranceRecordPassNotChangeRecord(10,"状态为通过的投保记录不能修投保记录"),
	 InsuranceRecordPassNotChangeInfo(11,"状态为通过的投保记录不能修改修投保记录的产品档案"),
	CloseOrderDelinsuranceRecordSuccess(12, "订单关闭时删除投保记录成功"),
	CloseOrderDelinsuranceRecordFail(13, "订单关闭时删除投保记录失败"),
	/**
	 * 生成投保记录中的课程为空
	 */
	CreateInsuranceRecordWithNoCourse(14, "生成投保记录中的课程为空"),
	/**
	 * 商品没有投保
	 */
	CreateInsuranceRecordFailWithNoInfoId(15, "商品没有投保"),
	/**
	 * 删除的投保记录恢复并将通过改为未通过
	 */
	 InsuranceRecordDisablePassStatus(16, "删除的投保记录恢复并将通过改为未通过"),
	 /**
	  * 没有交齐款删除已经生成的投保记录
	  */
	OrderNotFullPlayNotDeleteInsuranceRecordWithPassStatus(17,"没有交齐款删除已经生成的状态为通过的投保记录");
    private int value;

    private String text;

    InsuranceLogTypeEnum(int type, String name) {
        this.value = type;
        this.text = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // 普通方法
    public static String getText(int index) {
        for (InsuranceLogTypeEnum c : InsuranceLogTypeEnum.values()) {
            if (c.getValue() == index) {
                return c.text;
            }
        }
        return null;
    }
}
