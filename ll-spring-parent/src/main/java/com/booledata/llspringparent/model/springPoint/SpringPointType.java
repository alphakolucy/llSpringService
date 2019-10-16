package com.booledata.llspringparent.model.springPoint;


import com.booledata.llspringparent.utils.enums.PointType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "spring_pointtype")
public class SpringPointType implements Serializable {

    //所属温泉点
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "point_id")
    private SpringPoint springPoint;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable=false,length=32, unique = true)
    private  String id;

    @NotNull(message = "温泉点类型不能为空")
    @Column(nullable = false,length=32)
    private String pointType;


    @Column(columnDefinition = "datetime default now()")
    @Generated(GenerationTime.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

//    //处理状态txt
//    public String getPointTypeTxt(){
//        if(this.getPointType()!=null){
//            return PointType.getTxtByValue(this.getPointType());
//        }
//        return null;
//    }


}
