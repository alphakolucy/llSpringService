package com.booledata.llspringparent.utils.enums;


import com.booledata.llspringparent.utils.EmptyUtil;

/**
* @author XLR
* @date 2019/10/11
**/
public enum PackageState {
    //图片代码
    SJ(40001, "收集资料"),
    YW(40002, "野外资料"),
    CS(40003, "测试分析报告"),
    CG(40004,"成果报告"),
    ZP(40005,"照片"),
    QY(40006,"企业资料");


    private Integer value;
    private String txt;

    PackageState(Integer v, String txt) {
        this.value = v;
        this.txt = txt;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getTxt() {
        return this.txt;
    }

    public static String getTxtByValue(Integer value) {
        for (PackageState state : values()) {
            if (state.getValue().equals(value)) {
                return state.getTxt();
            }
        }
        return "";
    }

    /**
     * value是否存在此枚举中
     *
     * @param value 枚举value值
     * @return boolean
     */
    public static boolean isExist(Integer value) {
        if (EmptyUtil.isEmpty(value)) {
            return false;
        }
        for (PackageState e : PackageState.values()) {
            if (e.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}