package com.booledata.llspringparent.controller;

import com.alibaba.fastjson.JSONObject;
import com.booledata.llspringparent.api.drawMap.DrawMapControllerApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/drawMap")

public class DrawMapController implements DrawMapControllerApi {

//    @Autowired
//    DrawMapService drwaMapService;


    @Override
    @GetMapping(value = "getMap")
    @ApiOperation("底图数据")
    public JSONObject getMapData() {
        return null;
    }
}
