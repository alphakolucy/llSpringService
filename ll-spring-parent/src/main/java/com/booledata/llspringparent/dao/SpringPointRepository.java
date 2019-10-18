package com.booledata.llspringparent.dao;

import com.booledata.llspringparent.model.springPoint.SpringPoint;
import com.booledata.llspringparent.service.wisely.WiselyRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
* @author Administrator
* @date 2019/10/11
**/
@Repository
public interface SpringPointRepository extends WiselyRepository<SpringPoint,String> {




    @Query(nativeQuery = true,value = "select * from spring_point where code_number = ?1")
    SpringPoint getSpringPointByCodeNumber(String codeNumber);
}

