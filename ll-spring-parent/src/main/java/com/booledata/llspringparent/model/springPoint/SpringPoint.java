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

/**
* @author xlr
* @date 2019/9/27
**/

@Entity
@Table(name = "spring_point")
@Data
public class SpringPoint implements Serializable {


    private static final long serialVersionUID = -8958111163695414981L;
    @Id
    @GeneratedValue(generator = "identifier",strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "identifier", strategy = "uuid")
    @Column(nullable = false,length=32, unique = true)
    private String id;


    @Column(columnDefinition = "datetime default now()")
    @Generated(GenerationTime.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

    //编号
    @NotNull(message = "codeNumber不能为空")
    @Column(nullable = false, length = 20)
    private String codeNumber;



    //位置名称
    @NotBlank(message = "address不能为空")
    @Column(nullable = false, length = 50)
    private String address;
    //x轴
    @NotNull(message = "x不能为空")
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getHoleDepth() {
        return holeDepth;
    }

    public void setHoleDepth(double holeDepth) {
        this.holeDepth = holeDepth;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(String waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public String getWaterInflow() {
        return waterInflow;
    }

    public void setWaterInflow(String waterInflow) {
        this.waterInflow = waterInflow;
    }

    public String getTrepanning() {
        return trepanning;
    }

    public void setTrepanning(String trepanning) {
        this.trepanning = trepanning;
    }

    public String getWaterOutlet() {
        return waterOutlet;
    }

    public void setWaterOutlet(String waterOutlet) {
        this.waterOutlet = waterOutlet;
    }

    public double getDissolvedSolids() {
        return dissolvedSolids;
    }

    public void setDissolvedSolids(double dissolvedSolids) {
        this.dissolvedSolids = dissolvedSolids;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public double getHydrothion() {
        return hydrothion;
    }

    public void setHydrothion(double hydrothion) {
        this.hydrothion = hydrothion;
    }

    public double getHsio() {
        return hsio;
    }

    public void setHsio(double hsio) {
        this.hsio = hsio;
    }

    public double getHbo2() {
        return hbo2;
    }

    public void setHbo2(double hbo2) {
        this.hbo2 = hbo2;
    }

    public double getBr2() {
        return br2;
    }

    public void setBr2(double br2) {
        this.br2 = br2;
    }

    public double getI2() {
        return i2;
    }

    public void setI2(double i2) {
        this.i2 = i2;
    }

    public double getFe() {
        return fe;
    }

    public void setFe(double fe) {
        this.fe = fe;
    }

    public double getAsa() {
        return asa;
    }

    public void setAsa(double asa) {
        this.asa = asa;
    }

    public double getRn() {
        return rn;
    }

    public void setRn(double rn) {
        this.rn = rn;
    }

    public String getHydrochemicalType() {
        return hydrochemicalType;
    }

    public void setHydrochemicalType(String hydrochemicalType) {
        this.hydrochemicalType = hydrochemicalType;
    }
}
