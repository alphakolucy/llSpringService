package com.booledata.llspringparent.utils.enums;


import com.booledata.llspringparent.utils.EmptyUtil;

/**
* @author xlr
* @description 温泉点种类
* @date 2019/10/15
**/
public enum PointCategory {
    //未处理  已处理  已完成  已取消
    S(30001, "天然温泉"),
    DR(30002, "地热井"),


    CANCEL(-30000,"温泉点编号有误");

    private Integer value;
    private String txt;

    PointCategory(Integer v, String txt) {
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
        for (PointCategory state : values()) {
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
        for (PointCategory e : PointCategory.values()) {
            if (e.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}