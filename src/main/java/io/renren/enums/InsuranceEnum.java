package io.renren.enums;

/**
 * @author linchaokai
 * @Description
 * @date 2018/3/19 16:15
 */
public enum InsuranceEnum {
    weitoubao(0,"未投保"),toubaochenggong(1,"已投保"),toubaoshibai(2,"投保失败"),qianyuechenggong(3,"已投保");

    private int value;

    private String text;

    InsuranceEnum(int type, String name) {
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
        for (InsuranceEnum c : InsuranceEnum.values()) {
            if (c.getValue() == index) {
                return c.text;
            }
        }
        return null;
    }
}
