package io.renren.enums;

public enum ExamStateEnum {
    normal(0,"--"),baokao(1,"报考"),qikao(2,"弃考"),miankao(3,"免考");

    private int value;

    private String text;

    ExamStateEnum(int type, String name) {
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
        for (ExamStateEnum c : ExamStateEnum.values()) {
            if (c.getValue() == index) {
                return c.text;
            }
        }
        return null;
    }
  // 普通方法
    public static Integer getValue(String text) {
        for (ExamStateEnum c : ExamStateEnum.values()) {
            if (c.getText().equals(text)) {
                return c.value;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getE(1));
    }


    // 普通方法
    public static ExamStateEnum getE(String text) {
        for (ExamStateEnum c : ExamStateEnum.values()) {
            if (c.getText().equals(text)) {
                return c;
            }
        }
        return null;
    }

    // 普通方法
    public static ExamStateEnum getE(Integer value) {
        for (ExamStateEnum c : ExamStateEnum.values()) {
            if (c.getValue()==(value.intValue())) {
                return c;
            }
        }
        return null;
    }
}
