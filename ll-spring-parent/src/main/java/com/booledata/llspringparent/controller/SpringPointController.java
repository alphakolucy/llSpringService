package com.booledata.llspringparent.controller;

import com.booledata.llspringparent.api.drawMap.SpringPointControllerApi;
import com.booledata.llspringparent.common.PageChange;
import com.booledata.llspringparent.dao.SpringPointRepository;
import com.booledata.llspringparent.model.springPoint.SpringPointInfo;
import com.booledata.llspringparent.model.springPoint.response.SpringPointResult;
import com.booledata.llspringparent.service.SpringPicService;
import com.booledata.llspringparent.service.SpringTypeService;
import com.booledata.llspringparent.utils.*;
import com.booledata.llspringparent.utils.enums.OutputState;
import com.booledata.llspringparent.utils.tools.ObjectCopyUtil;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author xlr
 * @description 温泉点
 * @date 2019/10/12
 **/
@RestController
@RequestMapping("/springPoint")
@Transactional(propagation = Propagation.SUPPORTS)
public class SpringPointController implements SpringPointControllerApi {

    @Autowired
    private SpringPointRepository springPointRepository;

    @Autowired
    private SpringPicService springPicService;


    @Autowired
    private SpringTypeService springTypeService;


    private static ArrayList<Integer> spState = new ArrayList<Integer>() {{
        add(10001);
        add(10002);
        add(10003);
        add(10004);
        add(10005);
        add(10006);
    }};

    @Override
    @GetMapping(produces = "application/json", value = "/findAllPage")
    public SpringPointResult findAllPage(SpringPointInfo entity, @PageableDefault(sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        HttpStatusContent status = null;





        //添加匹配规则  模糊查询 codeNumber address+
        SpringPointResult springPointResult = new SpringPointResult();
//        if (pageable.getPageSize()==0){
//
//        }
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("codeNumber", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("address", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pointCategory",ExampleMatcher.GenericPropertyMatchers.contains());

        Example<SpringPointInfo> example = Example.of(entity, matcher);

        Page<SpringPointInfo> springPoints = this.springPointRepository.findAll(example, pageable);
        return new SpringPointResult(springPoints,200);
    }

    @Override
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> findAll() {

        HttpStatusContent status = null;
        try {
            List<SpringPointInfo> all = this.springPointRepository.findAll();
            return new ResponseEntity<List<SpringPointInfo>>(all, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            status = new HttpStatusContent(OutputState.FAIL, "数据不存在！");
            return new ResponseEntity<>(status, HttpStatus.OK);
        }

    }

    @Override
    @GetMapping(produces = "application/json", value = "/{id}")
    public ResponseEntity<?> getOne(@PathVariable(value = "id") String id) {
        HttpStatusContent status = null;
        SpringPointInfo entity = this.springPointRepository.findOne(id);
        if (entity == null) {
            status = new HttpStatusContent(OutputState.FAIL, "数据不存在！");
            return new ResponseEntity<HttpStatusContent>(status, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }


    @Override
    @PostMapping("/addPoint")
    public ResponseEntity<?> addPoint(SpringPointInfo entity) {
        HttpStatusContent status = null;
        PointCategoryUtil pointCategoryUtil = new PointCategoryUtil();

        //验证实体必填字段
        Map<String, StringBuffer> validate = ValidatorUtil.validate(entity);
        if (validate != null) {
            status = new HttpStatusContent(OutputState.FAIL, ValidatorUtil.getErrorMsg(validate));
            return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        SpringPointInfo springPointInfo = pointCategoryUtil.selectPointCategory(entity);
        entity = springPointRepository.save(springPointInfo);
        if (springPointInfo == null) {
            status = new HttpStatusContent(OutputState.FAIL, "添加失败，请稍后重试！");
            return new ResponseEntity<HttpStatusContent>(status, HttpStatus.INTERNAL_SERVER_ERROR);
        }

//        //存入url
//        if (spState.contains(picState)) {
//            if (picState.equals(spState.get(5))) {
//                springPicService.saveFile(file, request, picState, entity);
//            } else {
//                springPicService.saveImg(file, type, request, picState, entity);
//            }
//        }
        String success = springTypeService.saveType(springPointInfo);
        status = new HttpStatusContent(OutputState.SUCCESS);
        return new ResponseEntity<>(springPointInfo, HttpStatus.OK);
    }

    @Override
    @PutMapping(produces = "application/json", value = "/{id}")
    public ResponseEntity<?> updatePoint(@PathVariable(value = "id") String id, @PathVariable(value = "id") SpringPointInfo entity) {
        HttpStatusContent status = null;


        System.out.println("entityId" + entity.getId());
        SpringPointInfo springPointInfo = new SpringPointInfo();
        springPointInfo = springPointRepository.findOne(entity.getId());

        if (springPointInfo == null) {
            System.out.println("no exit!");
            status = new HttpStatusContent(OutputState.FAIL, "数据不存在！");
            return new ResponseEntity<HttpStatusContent>(status, HttpStatus.NOT_FOUND);
        }


        //同一类实体之间的属性复制
        ObjectCopyUtil<SpringPointInfo> uUtil = new ObjectCopyUtil<>();
        uUtil.copyProperties(entity, springPointInfo);

        entity = this.springPointRepository.save(springPointInfo);
        if (entity == null) {
            status = new HttpStatusContent(OutputState.FAIL, "修改失败，请稍后重试！");
            return new ResponseEntity<HttpStatusContent>(status, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        status = new HttpStatusContent(OutputState.SUCCESS);
        return new ResponseEntity<HttpStatusContent>(status, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("deletePoint")
    public ResponseEntity<?> deletePoint(String id) {
        HttpStatusContent status = null;
        springPointRepository.delete(id);
        status = new HttpStatusContent(OutputState.SUCCESS);
        return new ResponseEntity<HttpStatusContent>(status, HttpStatus.OK);
    }

//    @Override
//    @PutMapping("updatePoint1")
//    public SpringPointResult updatePoint(SpringPointInfo springPointInfo) {
//        return null;
//    }
}
