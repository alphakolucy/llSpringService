package com.booledata.llspringparent.utils;

import com.booledata.llspringparent.model.springPoint.SpringPointInfo;
import com.booledata.llspringparent.utils.enums.PointCategory;

public class PointCategoryUtil {


    public SpringPointInfo selectPointCategory(SpringPointInfo springPointInfo){

        String codeNumber = springPointInfo.getCodeNumber();

        String substring = codeNumber.substring(0, 1);
        if ("S".equals(substring)|| "s".equals(substring)){
            springPointInfo.setPointCategory(PointCategory.S.getValue());
            return springPointInfo;
        }else if ("D".equals(substring)||"d".equals(substring)){
            springPointInfo.setPointCategory(PointCategory.DR.getValue());
            return springPointInfo;
        }else {
            springPointInfo.setPointCategory(PointCategory.CANCEL.getValue());
            return springPointInfo;
        }

    }
}
