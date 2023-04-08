package com.wj.file.controller.admin;


import cn.hutool.core.util.IdUtil;
import com.wj.server.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/admin/upload")
public class UploadController {
    private  static final Logger LOG = LoggerFactory.getLogger(UploadController .class);
    public final static String BUSINESS_NAME = "文件上传";

    @RequestMapping("/uploadImage")
    public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
        LOG.info("file{}",file);
        LOG.info("file==1<>>" + file.getOriginalFilename());
        LOG.info("file==size<>>" + file.getSize());
        ResponseDto responseDto = new ResponseDto();
        String fileName = file.getOriginalFilename();
        String key = IdUtil.randomUUID();
        String fullPath = "/Users/beginner/work/workspace/ideaworkspace/wjcloud/uploadfile/teacher/" + key +"_" + fileName;
        File dest = new File(fullPath);
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        return responseDto;
    }

}
