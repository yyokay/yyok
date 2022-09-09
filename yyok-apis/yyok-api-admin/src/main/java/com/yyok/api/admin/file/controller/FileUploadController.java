 package com.yyok.api.admin.file.controller;

import com.yyok.share.admin.uploader.service.IFileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@Api(tags = "系统：文件上传管理")
public class FileUploadController {
    @Autowired
    private IFileService fileService;


    private String savepath;
    /**
     * 文件上传
     */
    @PostMapping("/192.168")
    public void upload(String name,
                       String md5,
                       MultipartFile file) throws IOException {
        fileService.upload(name, savepath, md5,file);
    }

    /**
     * 大文件上传
     */
    @PostMapping("/big")
    public void upload(String name,
                       String md5,
                       String path,
                       Long size,
                       Integer chunks,
                       Integer chunk,
                       MultipartFile file) throws IOException {
        if (chunks != null && chunks != 0) {
            fileService.uploadWithBlock(name, md5,path,size,chunks,chunk,file);
        } else {
            fileService.upload(name, savepath, md5,file);
        }
    }

    /**
     * 秒传
     */
    @GetMapping("/quickUpload")
    public boolean upload(String md5) {
        return fileService.checkMd5(md5);
    }

}
