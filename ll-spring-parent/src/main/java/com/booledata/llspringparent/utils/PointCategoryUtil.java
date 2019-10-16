package com.booledata.llspringparent.utils;

import com.booledata.llspringparent.model.springPoint.SpringPoint;
import com.booledata.llspringparent.utils.enums.PointCategory;

public class PointCategoryUtil {


    public SpringPoint selectPointCategory(SpringPoint springPoint){

        String codeNumber = springPoint.getCodeNumber();

        String substring = codeNumber.substring(0, 1);
        if ("S".equals(substring)|| "s".equals(substring)){
            springPoint.setPointCategory(PointCategory.S.getValue());
            return springPoint;
        }else if ("D".equals(substring)||"d".equals(substring)){
            springPoint.setPointCategory(PointCategory.DR.getValue());
            return springPoint;
        }else {
            springPoint.setPointCategory(PointCategory.CANCEL.getValue());
            return springPoint;
        }

    }
}
