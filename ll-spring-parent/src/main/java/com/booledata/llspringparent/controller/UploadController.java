package com.booledata.llspringparent.controller;


import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.booledata.llspringparent.api.drawMap.UploadControllerApi;
import com.booledata.llspringparent.dao.SpringPicRepository;
import com.booledata.llspringparent.model.Image;
import com.booledata.llspringparent.model.UploadStatus;
import com.booledata.llspringparent.model.springPoint.SpringPoint;
import com.booledata.llspringparent.model.springPoint.SpringPointPic;
import com.booledata.llspringparent.service.SpringPicService;
import com.booledata.llspringparent.utils.ApplicationConfig;
import com.booledata.llspringparent.utils.FileUtil;
import com.booledata.llspringparent.utils.HttpStatusContent;
import com.booledata.llspringparent.utils.enums.OutputState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
@Transactional(propagation = Propagation.SUPPORTS)
public class UploadController implements UploadControllerApi {





    @Autowired
    private SpringPicService springPicService;

    @Autowired
    private SpringPicRepository springPicRepository;

    //处理图片上传
    @Override
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public ResponseEntity<?> uploadImgDif(@RequestParam("file") MultipartFile file, @RequestParam(value = "filetype", defaultValue = "") String type,
                                          HttpServletRequest request,@RequestParam(value = "PicState", defaultValue = "") Integer picState,String codeNumber,String pid) {

        return springPicService.saveImg(file, type, request, picState, codeNumber,pid);
    }

    @Override
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request,@RequestParam(value = "PicState", defaultValue = "")  Integer picState,String codeNumber,String pid) {
        return springPicService.saveFile(file,request,picState,codeNumber,pid);
    }
}
