//package com.booledata.llspringparent.model.springPoint;
//
//import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
//import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
//
//import javax.persistence.*;
//
//@Table(name = "springType")
//public class springType {
//
//    //温泉类型
//    @OneToMany(mappedBy = "SpringPoint",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @OrderBy(value = "typeId ASC ")
//    @Column(name = "typeId",type = MySqlTypeConstant.INT,length = 50,isKey = true)
//    private Integer typeId;
//    //温泉id
//    @Column(name = "pointId",type = MySqlTypeConstant.INT,length = 50)
//    private Integer pointId;
//
//}
