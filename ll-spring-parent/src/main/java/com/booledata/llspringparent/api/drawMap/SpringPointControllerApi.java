package com.booledata.llspringparent.api.drawMap;


//import com.booledata.llspringparent.model.springPoint.SpringPoint;
//import com.booledata.llspringparent.model.springPoint.response.SpringPointResult;
import com.booledata.llspringparent.model.springPoint.SpringPoint;
import com.booledata.llspringparent.model.springPoint.response.SpringPointResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Administrator
* @description 温泉点管理
* @date 2019/9/27
**/
@Api(value = "温泉点管理",tags = {"温泉点管理接口"})
public interface SpringPointControllerApi {



    @ApiOperation(value = "获取温泉点列表(分页）") //Page<SpringPoint>
    ResponseEntity<?> findAllPage(SpringPoint springPoint, @PageableDefault(value = 10, sort = {"createTime"}, direction = Sort.Direction.DESC)Pageable pageable);

    @ApiOperation(value = "获取温泉点列表")
    ResponseEntity<?> findAll();



    @ApiOperation(value = "查询温泉点")
    ResponseEntity<?> getOne(String id);


    @ApiOperation(value = "添加温泉点")
    ResponseEntity<?> addPoint(SpringPoint entity);

    @ApiOperation(value = "修改温泉点")
    ResponseEntity<?> updatePoint( String id,SpringPoint entity);

    @ApiOperation(value = "删除温泉点")
    ResponseEntity<?> deletePoint(String pointId);

//    @ApiOperation(value = "更新温泉点")
//    SpringPointResult updatePoint(SpringPoint springPoint);


}
