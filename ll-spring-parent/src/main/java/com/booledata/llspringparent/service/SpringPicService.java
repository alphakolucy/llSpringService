package com.booledata.llspringparent.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.booledata.llspringparent.api.drawMap.SpringPointControllerApi;
import com.booledata.llspringparent.dao.SpringPicRepository;
import com.booledata.llspringparent.dao.SpringPointRepository;
import com.booledata.llspringparent.model.Image;
import com.booledata.llspringparent.model.springPoint.SpringPointInfo;
import com.booledata.llspringparent.model.springPoint.SpringPointPic;
import com.booledata.llspringparent.model.springPoint.response.RespondSpringPic;
import com.booledata.llspringparent.model.UploadStatus;
import com.booledata.llspringparent.utils.ApplicationConfig;
import com.booledata.llspringparent.utils.FileUtil;
import com.booledata.llspringparent.utils.HttpStatusContent;
import com.booledata.llspringparent.utils.enums.OutputState;
import com.booledata.llspringparent.utils.tools.ObjectCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class SpringPicService {

    private static final String PATH = "E:\\dev\\project\\llSpringService\\ll-spring-parent\\src\\main\\resources\\static"; //上传路径
    @Autowired
    private SpringPicRepository springPicRepository;


    @Autowired
    private SpringPointRepository springPointRepository;

    @Autowired
    private SpringPointControllerApi springPointControllerApi;

    public ResponseEntity<?> saveImg(MultipartFile file, String type,
                                     HttpServletRequest request, Integer picState, String codeNumber, String pid) {
        HttpStatusContent status;

        SpringPointInfo entity = new SpringPointInfo();

        SpringPointPic springPointPic = new SpringPointPic();



        SpringPointInfo springPointInfo = springPointRepository.getSpringPointByCodeNumber(codeNumber);
        List<SpringPointPic> springPointPics = springPointInfo.getSpringPointPics();

        String contentType = file.getContentType();
        String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        String fileName = UUID.randomUUID().toString() + '.' + prefix;
        Image img = new Image();
        img.setImageName(fileName);
        img.setImageType(contentType);


        RespondSpringPic respondSpringPic = new RespondSpringPic();
        System.out.println(img.getImageName());

        String path = "/" + type + "/";
        try {


            if ("idcard".equals(type)) {
                AipOcr client = new AipOcr("10954235", "FkvCfuuwmD7T5iudrnMq438N", "sQw0jMiXpesWM6fHwCqPcEYyR2cGOqrD");
                HashMap<String, String> options = new HashMap<String, String>();
                options.put("detect_direction", "true");
                //            options.put("detect_risk", "false");
                String idCardSide = "front";
                // 参数为本地图片二进制数组
                byte[] imgbyte = file.getBytes();
                JSONObject res = JSONObject.parseObject(client.idcard(imgbyte, idCardSide, options).toString());
                System.out.println(res.getJSONObject("words_result").get("公民身份号码").toString());

                img.setContent(res.getJSONObject("words_result"));
                img.setImageName(res.getJSONObject("words_result").getJSONObject("公民身份号码").get("words").toString() + '.' + prefix);
            }

            FileUtil.uploadFile(file.getBytes(), PATH + path, img.getImageName());
            String ap = PATH + path + img.getImageName();
            status = new HttpStatusContent(OutputState.SUCCESS);
            img.setImageName(path + fileName);
            img.setImageUrl(ApplicationConfig.newInstance().getBaseUrl() + img.getImageName());

            if (pid != null) {
                SpringPointPic one = springPicRepository.findOne(pid);
                if (one != null) {
                    springPointPic.setUrl(img.getImageUrl());
                    ObjectCopyUtil<SpringPointPic> uUtil = new ObjectCopyUtil<>();
                    //同一类实体之间的属性复制
                    uUtil.copyProperties(springPointPic, one);
                    springPointPics.add(one);
                    springPointInfo.setSpringPointPics(springPointPics);


                    //保存点信息
                    springPointRepository.save(springPointInfo);
                    respondSpringPic.setImage(img);
                    respondSpringPic.setSpringPointPic(springPointPic);
                    return new ResponseEntity<RespondSpringPic>(respondSpringPic, HttpStatus.OK);
                }
            }


            springPointInfo = this.springPointRepository.getSpringPointByCodeNumber(codeNumber);


            //同一类实体之间的属性复制
//            ObjectCopyUtil<SpringPointInfo> uUtil = new ObjectCopyUtil<>();
//            uUtil.copyProperties(springPointInfo,entity);
//

            //存入picState codeNumber
            if (picState == null) {
                status = new HttpStatusContent(OutputState.FAIL, "picState为空！");
                return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);
            }


            if (springPointInfo == null) {

                status = new HttpStatusContent(OutputState.FAIL, "没有编号为" + codeNumber + "的温泉点，请先添加温泉点！");
                return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);

            }

            springPointPic.setPicState(picState);
            springPointPic.setUrl(img.getImageUrl());
            springPointPic.setFilePath(ap);

            springPointPics.add(springPointPic);
//            springPointPic = springPicRepository.save(springPointPic);


            springPointInfo.setSpringPointPics(springPointPics);
            springPointRepository.save(springPointInfo);


//


            respondSpringPic.setImage(img);
            respondSpringPic.setSpringPointPic(springPointPic);
            return new ResponseEntity<SpringPointInfo>(springPointInfo, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            status = new HttpStatusContent(OutputState.FAIL);//未找到记录
            return new ResponseEntity<HttpStatusContent>(status, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> saveFile(MultipartFile file, HttpServletRequest request, Integer picState, String codeNumber, String pid) {
        HttpStatusContent status;
        SpringPointInfo entity = new SpringPointInfo();
        SpringPointPic springPointPic = new SpringPointPic();
        SpringPointInfo springPointInfo = springPointRepository.getSpringPointByCodeNumber(codeNumber);
        List<SpringPointPic> springPointPics = springPointInfo.getSpringPointPics();


        String contentType = file.getContentType();
        String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        String fileName = UUID.randomUUID().toString() + '.' + prefix;
        Image img = new Image();
        img.setImageName(fileName);
        img.setImageType(contentType);

        RespondSpringPic respondSpringPic = new RespondSpringPic();


        try {
            FileUtil.uploadFile(file.getBytes(), PATH + "/file/", img.getImageName());
            status = new HttpStatusContent(OutputState.SUCCESS);
            img.setImageName(ApplicationConfig.newInstance().getBaseUrl() + "/file/" + fileName);
            String ap = PATH + "/file/" + fileName;

            UploadStatus uploadStatus = new UploadStatus();
            uploadStatus.setStatus("done");
            uploadStatus.setName("/file/" + fileName);
            uploadStatus.setSize(file.getSize());
            uploadStatus.setDownloadURL(img.getImageName());
            uploadStatus.setFileURL(img.getImageName());
            uploadStatus.setImgURL(img.getImageName());

            if (pid != null) {
                SpringPointPic one = springPicRepository.findOne(pid);
                if (one != null) {
                    springPointPic.setUrl(img.getImageName());
                    ObjectCopyUtil<SpringPointPic> uUtil = new ObjectCopyUtil<>();
                    //同一类实体之间的属性复制
                    uUtil.copyProperties(springPointPic, one);
                    springPointPic = springPicRepository.save(one);
                    springPointPics.add(one);
                    springPointInfo.setSpringPointPics(springPointPics);


                    //保存点信息
                    springPointRepository.save(springPointInfo);

                    respondSpringPic.setImage(img);
                    respondSpringPic.setSpringPointPic(springPointPic);
                    return new ResponseEntity<RespondSpringPic>(respondSpringPic, HttpStatus.OK);
                }
            }


            //同一类实体之间的属性复制
//            ObjectCopyUtil<SpringPointInfo> uUtil = new ObjectCopyUtil<>();
//            uUtil.copyProperties(springPointInfo,entity);
//
//            springPointPic.setSpringPointInfo(springPointInfo);
//            springPointPic.setPicState(picState);
//            springPointPic.setUrl(img.getImageUrl());
//            springPointPic.setSpringPointInfo(springPointInfo);
//            springPointPic.setPicState(picState);
//            springPointPic.setUrl(img.getImageUrl());

            if (picState == null) {
                status = new HttpStatusContent(OutputState.FAIL, "picState为空！");
                return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (springPointInfo == null) {

                status = new HttpStatusContent(OutputState.FAIL, "没有编号为" + codeNumber + "的温泉点，请先添加温泉点！");
                return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);
            }


//            springPointPic.setSpringPointInfo(springPointInfo);
            springPointPic.setPicState(picState);
            springPointPic.setUrl(img.getImageName());
            springPointPic.setFilePath(ap);

            //存入url
//            springPointPic = springPicRepository.save(springPointPic);

            springPointPics.add(springPointPic);
            springPointInfo.setSpringPointPics(springPointPics);


            //保存点信息
            springPointRepository.save(springPointInfo);

            respondSpringPic.setImage(img);
            respondSpringPic.setSpringPointPic(springPointPic);
            return new ResponseEntity<RespondSpringPic>(respondSpringPic, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            status = new HttpStatusContent(OutputState.FAIL);//未找到记录
            return new ResponseEntity<HttpStatusContent>(status, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public void sameCodeStart(MultipartFile file, String codeNumber) {

        HttpStatusContent status;

        SpringPointInfo entity = new SpringPointInfo();

        SpringPointPic springPointPic = new SpringPointPic();
        SpringPointInfo springPointInfo = new SpringPointInfo();

        entity.setCodeNumber(codeNumber);
        String contentType = file.getContentType();
        String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        String fileName = UUID.randomUUID().toString() + '.' + prefix;
        Image img = new Image();
        img.setImageName(fileName);
        img.setImageType(contentType);
    }
}
