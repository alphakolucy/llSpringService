package com.booledata.llspringparent.model.springPoint;

//import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
//import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

//import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Generated;

import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* @author xlr
* @date 2019/9/27
**/

@Entity
@Table(name = "spring_point")
@Data
public class SpringPoint implements Serializable {




    private static final long serialVersionUID = -8908592090826465419L;



//    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @JoinColumn(name = "pic_url",referencedColumnName = "codeNumber", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
//    private SpringPointPic springPointPicList;  //url

    @Id
    @GeneratedValue(generator = "identifier", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "identifier", strategy = "uuid")
    @Column(nullable = false, length = 32, unique = true)
    private String id;


    @Column(columnDefinition = "datetime default now()")
    @Generated(GenerationTime.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

    //编号
    @NotNull(message = "codeNumber不能为空")
    @Column(nullable = false, length = 20,unique = true)
    private String codeNumber;


    //位置名称
//    @NotBlank(message = "address不能为空")
    @Column(nullable = false, length = 50)
    private String address;
    //x轴
//    @NotNull(message = "x不能为空")
    @Column(nullable = false, length = 20)
    private double x;
    //y轴
    @Column(nullable = false, length = 20)
    private double y;
    //z轴
    @Column(nullable = false, length = 20)
    private double z;
    //孔深
    @Column(nullable = false, length = 20)
    private double holeDepth;
    //ph值
    @Column(nullable = false, length = 11)
    private String ph;
    //水温
    @Column(nullable = false, length = 11)
    private String waterTemperature;
    //涌水量
    @Column(nullable = false, length = 11)
    private String waterInflow;
    //开孔/出露层位
    @Column(nullable = false, length = 11)
    private String trepanning;
    //出水段
    @Column(nullable = false, length = 11)
    private String waterOutlet;
    //溶解性总固体
    @Column(nullable = false, length = 20)
    private double dissolvedSolids;
    //二氧化碳
    @Column(nullable = false, length = 20)
    private double co2;
    //总硫化氢
    @Column(nullable = false, length = 20)
    private double hydrothion;
    //偏硅酸
    @Column(nullable = false, length = 20)
    private double hsio;
    //偏硼酸
    @Column(nullable = false, length = 20)
    private double hbo2;
    //溴
    @Column(nullable = false, length = 20)
    private double br2;
    //碘
    @Column(nullable = false, length = 20)
    private double i2;
    //总铁
    @Column(nullable = false, length = 20)
    private double fe;
    // 砷
    @Column(nullable = false, length = 20)
    private double asa;
    //氡
    @Column(nullable = false, length = 20)
    private double rn;
    //水化学类型
    @Column(nullable = false, length = 20)
    private String hydrochemicalType;

    //温泉点类型：理疗温泉  地热井
    @Column( length = 10)
    private Integer pointCategory;



}


