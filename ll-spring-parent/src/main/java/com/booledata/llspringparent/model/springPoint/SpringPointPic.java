package com.booledata.llspringparent.model.springPoint;

import com.booledata.llspringparent.utils.enums.PicState;
import com.booledata.llspringparent.utils.enums.PointType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
* @author xlr
* @description 温泉点照片
* @date 2019/10/15
**/
@Entity
@Data
@Table(name = "spring_pointpic")
public class SpringPointPic implements Serializable {


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable=false,length=32, unique = true)
    private String pid;


    @Column(nullable = false, length = 100)
    private String url;


    @Column(nullable = false, length = 20)
    private Integer picState;


    @Column(columnDefinition = "datetime default now()")
    @Generated(GenerationTime.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

    //处理状态txt
    public String getPointTypeTxt(){
        if(this.getPicState()!=null){
            return PointType.getTxtByValue(this.getPicState());
        }
        return null;
    }


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "point_Id",referencedColumnName = "id")
    private SpringPoint springPoint;  //所属点

    public Integer getPicState() {
        return picState;
    }

    public void setPicState(Integer picState) {
        this.picState = picState;
    }

    //申请类型状态txt
    public String getPicStateTxt(){
        if(this.getPicState() != null){
            return PicState.getTxtByValue(this.getPicState());
        }
        return null;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SpringPoint getSpringPoint() {
        return springPoint;
    }

    public void setSpringPoint(SpringPoint springPoint) {
        this.springPoint = springPoint;
    }
}
