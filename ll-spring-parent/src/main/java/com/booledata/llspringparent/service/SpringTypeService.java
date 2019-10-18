package com.booledata.llspringparent.service;

import com.booledata.llspringparent.dao.SpringTypeRepository;
import com.booledata.llspringparent.model.springPoint.SpringPoint;
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


    public String saveType(SpringPoint springPoint) {


//
        ArrayList arrayList = new ArrayList();
        //温泉类型标准值（大于）
        double[] arr = {1000, 5, 500, 10, 2, 0.7, 50, 110, 35, 25};
        String type = "";
        SpringPointType springPointType = new SpringPointType();
        if (springPoint.getDissolvedSolids() > arr[0]) {
            type += PointType.KQS.getTxt() + ",";
        }
        if (springPoint.getI2() > arr[1]) {
//            springPointType.setPointType(PointType.I.getTxt());
            type += PointType.I.getTxt() + ",";

        }
        if (springPoint.getCo2() > arr[2]) {
//            springPointType.setPointType(PointType.CO2.getTxt());
            type += PointType.CO2.getTxt() + ",";
        }
        if (springPoint.getFe() > arr[3]) {
//            springPointType.setPointType(PointType.FE.getTxt());
            type += PointType.FE.getTxt() + ",";
        }
        if (springPoint.getHydrothion() > arr[4]) {
//            springPointType.setPointType(PointType.H2S.getTxt());
            type += PointType.H2S.getTxt() + ",";
        }
        if (springPoint.getAsa() > arr[5]) {
//            springPointType.setPointType(PointType.AS.getTxt());
            type += PointType.AS.getTxt() + ",";
        }
        if (springPoint.getHsio() > arr[6]) {
//            springPointType.setPointType(PointType.H2SIO3.getTxt());
            type += PointType.H2SIO3.getTxt() + ",";
        }
        if (springPoint.getRn() > arr[7]) {
//            springPointType.setPointType(PointType.RN.getTxt());
            type += PointType.RN.getTxt() + ",";
        }
        if (springPoint.getHbo2() > arr[8]) {
//            springPointType.setPointType(PointType.HBO2.getTxt());
            type += PointType.HBO2.getTxt() + ",";
        }
        if (springPoint.getBr2() > arr[9]) {
//            springPointType.setPointType(PointType.BR.getTxt());
            type += PointType.BR.getTxt() + ",";
        }

        springPointType.setSpringPoint(springPoint);
        springPointType.setPointType(type);
        //传入id
//        String id = springPoint.getId();
//        springPointType.setId(id);
        springTypeRepository.save(springPointType);

//        }

        return "success";
    }
}
