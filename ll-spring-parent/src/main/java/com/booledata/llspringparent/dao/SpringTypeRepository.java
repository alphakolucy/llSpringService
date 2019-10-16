package com.booledata.llspringparent.dao;

import com.booledata.llspringparent.model.springPoint.SpringPoint;
import com.booledata.llspringparent.model.springPoint.SpringPointType;
import com.booledata.llspringparent.service.wisely.WiselyRepository;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringTypeRepository extends WiselyRepository<SpringPointType,String> {




}
