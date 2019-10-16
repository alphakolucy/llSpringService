package com.booledata.llspringparent.controller;


import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.booledata.llspringparent.api.drawMap.UploadControllerApi;
import com.booledata.llspringparent.model.Image;
import com.booledata.llspringparent.model.UploadStatus;
import com.booledata.llspringparent.utils.ApplicationConfig;
import com.booledata.llspringparent.utils.FileUtil;
import com.booledata.llspringparent.utils.HttpStatusContent;
import com.booledata.llspringparent.utils.enums.OutputState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class UploadController implements UploadControllerApi {


    private static final String PATH = "E:\\dev\\project\\llSpringService\\ll-spring-parent\\src\\main\\resources\\static"; //上传路径


    //处理图片上传
    @Override
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public ResponseEntity<?> uploadImgDif(@RequestParam("file") MultipartFile file, @RequestParam(value = "filetype", defaultValue = "") String type,
                                          HttpServletRequest request) {

        HttpStatusContent status;

        String contentType = file.getContentType();
        String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        String fileName = UUID.randomUUID().toString() + '.' + prefix;
        Image img = new Image();
        img.setImageName(fileName);
        img.setImageType(contentType);

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
            status = new HttpStatusContent(OutputState.SUCCESS);
            img.setImageName(path + fileName);
            img.setImageUrl(ApplicationConfig.newInstance().getBaseUrl() + img.getImageName());
            return new ResponseEntity<Image>(img, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            status = new HttpStatusContent(OutputState.FAIL);//未找到记录
            return new ResponseEntity<HttpStatusContent>(status, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        HttpStatusContent status;

        String contentType = file.getContentType();
        String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        String fileName = UUID.randomUUID().toString() + '.' + prefix;
        Image img = new Image();
        img.setImageName(fileName);
        img.setImageType(contentType);

        try {
            FileUtil.uploadFile(file.getBytes(), PATH + "/file/", img.getImageName());
            status = new HttpStatusContent(OutputState.SUCCESS);
            img.setImageName(ApplicationConfig.newInstance().getBaseUrl() + "/file/" + fileName);


            UploadStatus uploadStatus = new UploadStatus();
            uploadStatus.setStatus("done");
            uploadStatus.setName("/file/" + fileName);
            uploadStatus.setSize(file.getSize());
            uploadStatus.setDownloadURL(img.getImageName());
            uploadStatus.setFileURL(img.getImageName());
            uploadStatus.setImgURL(img.getImageName());

            return new ResponseEntity<UploadStatus>(uploadStatus, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            status = new HttpStatusContent(OutputState.FAIL);//未找到记录
            return new ResponseEntity<HttpStatusContent>(status, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
