package com.booledata.llspringparent.dao;

import com.booledata.llspringparent.model.springPoint.SpringPointPic;
import com.booledata.llspringparent.service.wisely.WiselyRepository;
import org.springframework.stereotype.Repository;

/**
* @author xlr
* @description 温泉点图片
* @date 2019/10/15  
**/
@Repository
public interface SpringPicRepository extends WiselyRepository<SpringPointPic,String> {



}
