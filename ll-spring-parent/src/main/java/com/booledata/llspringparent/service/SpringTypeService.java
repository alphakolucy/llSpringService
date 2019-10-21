package com.booledata.llspringparent.service;

import com.booledata.llspringparent.dao.SpringTypeRepository;
import com.booledata.llspringparent.model.springPoint.SpringPointInfo;
import com.booledata.llspringparent.model.springPoint.SpringPointType;
import com.booledata.llspringparent.utils.enums.PointType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SpringTypeService {

    @Autowired
    private SpringTypeRepository springTypeRepository;




    public String deleteType(String pointId){
        Integer integer = springTypeRepository.deleteByPointId(pointId);

        if (integer>0){
            return "success";
        }else {
            return "error";
        }

    }


    public String saveType(SpringPointInfo springPointInfo) {


//
        ArrayList arrayList = new ArrayList();
        //温泉类型标准值（大于）
        double[] arr = {1000, 5, 500, 10, 2, 0.7, 50, 110, 35, 25};
        String type = "";
        SpringPointType springPointType = new SpringPointType();
        if (springPointInfo.getDissolvedSolids() > arr[0]) {
            type += PointType.KQS.getTxt() + ",";
        }
        if (springPointInfo.getI2() > arr[1]) {
//            springPointType.setPointType(PointType.I.getTxt());
            type += PointType.I.getTxt() + ",";

        }
        if (springPointInfo.getCo2() > arr[2]) {
//            springPointType.setPointType(PointType.CO2.getTxt());
            type += PointType.CO2.getTxt() + ",";
        }
        if (springPointInfo.getFe() > arr[3]) {
//            springPointType.setPointType(PointType.FE.getTxt());
            type += PointType.FE.getTxt() + ",";
        }
        if (springPointInfo.getHydrothion() > arr[4]) {
//            springPointType.setPointType(PointType.H2S.getTxt());
            type += PointType.H2S.getTxt() + ",";
        }
        if (springPointInfo.getAsa() > arr[5]) {
//            springPointType.setPointType(PointType.AS.getTxt());
            type += PointType.AS.getTxt() + ",";
        }
        if (springPointInfo.getHsio() > arr[6]) {
//            springPointType.setPointType(PointType.H2SIO3.getTxt());
            type += PointType.H2SIO3.getTxt() + ",";
        }
        if (springPointInfo.getRn() > arr[7]) {
//            springPointType.setPointType(PointType.RN.getTxt());
            type += PointType.RN.getTxt() + ",";
        }
        if (springPointInfo.getHbo2() > arr[8]) {
//            springPointType.setPointType(PointType.HBO2.getTxt());
            type += PointType.HBO2.getTxt() + ",";
        }
        if (springPointInfo.getBr2() > arr[9]) {
//            springPointType.setPointType(PointType.BR.getTxt());
            type += PointType.BR.getTxt() + ",";
        }

//        springPointType.setSpringPointInfo(springPointInfo);
        springPointType.setPointType(type);
        //传入id
//        String id = springPointInfo.getId();
//        springPointType.setId(id);
        springTypeRepository.save(springPointType);

//        }

        return "success";
    }
}
