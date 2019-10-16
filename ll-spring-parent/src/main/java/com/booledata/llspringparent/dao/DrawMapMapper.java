package com.booledata.llspringparent.dao;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author xlr
* @description 底图
* @date 2019/9/29  
**/
@Mapper
public interface DrawMapMapper{

    List<List<Object>> getMapData();
}
