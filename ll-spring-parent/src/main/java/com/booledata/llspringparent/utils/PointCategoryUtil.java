package com.booledata.llspringparent.utils;

import com.booledata.llspringparent.model.springPoint.SpringPointInfo;
import com.booledata.llspringparent.utils.enums.PointCategory;

import java.util.ArrayList;
import java.util.List;

public class PointCategoryUtil {


    public SpringPointInfo selectPointCategory(SpringPointInfo springPointInfo, boolean b) {

        String codeNumber = springPointInfo.getCodeNumber();


//        String substring = codeNumber.substring(0, 1);



        if (codeNumber.contains("S") || codeNumber.contains("s")) {
            if (b) {
                springPointInfo.setPointCategory(PointCategory.S.getValue());
                return springPointInfo;
            } else {
                springPointInfo.setPointCategory(PointCategory.SCANCEL.getValue());
                return springPointInfo;
            }

        } else if (codeNumber.contains("D") || codeNumber.contains("d")) {

            //判断是否是施工中地热井
            boolean reach = getReach(springPointInfo);
            if (b){
                springPointInfo.setPointCategory(PointCategory.DR.getValue());
                return springPointInfo;
            }else if(reach){
                springPointInfo.setPointCategory(PointCategory.SGZRKSZK.getValue());
                return springPointInfo;
            }else {
                springPointInfo.setPointCategory(PointCategory.DRCANCEL.getValue());
                return springPointInfo;
            }
        } else {
            springPointInfo.setPointCategory(PointCategory.NCANCEL.getValue());
            return springPointInfo;
        }

    }


    public boolean getReach(SpringPointInfo springPointInfo) {
        List<Double> standValue = new ArrayList<>();
       Integer  sumStand = 0;
        standValue.add(springPointInfo.getDissolvedSolids());
        standValue.add(springPointInfo.getCo2());
        standValue.add(springPointInfo.getHydrothion());
        standValue.add(springPointInfo.getHsio());
        standValue.add(springPointInfo.getHbo2());
        standValue.add(springPointInfo.getBr2());
        standValue.add(springPointInfo.getI2());
        standValue.add(springPointInfo.getFe());
        standValue.add(springPointInfo.getAsa());
        standValue.add(springPointInfo.getRn());


        for (Double aDouble : standValue) {
            if (aDouble==0){
                sumStand++;
                if (sumStand>=10){
                    return true;
                }
            }else{
                return false;
            }
        }
        return false;
    }
}
