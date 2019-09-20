package io.renren.enums;

/**
 * @author linchaokai
 * @Description
 * @date 2018/3/19 16:15
 */
public enum PassStatusEnum {
    weitongguo(0,"未通过"),tongguo(1,"通过");

    private int value;

    private String text;

    PassStatusEnum(int type, String name) {
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
        for (PassStatusEnum c : PassStatusEnum.values()) {
            if (c.getValue() == index) {
                return c.text;
            }
        }
        return null;
    }
}
