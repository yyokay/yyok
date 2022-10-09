 package com.yyok.api.admin.file.controller;

 import com.yyok.share.admin.uploader.service.IFileService;
 import io.swagger.annotations.Api;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;
 import org.springframework.web.multipart.MultipartFile;

 import java.io.IOException;
 import java.util.HashMap;

 @RestController
 @RequestMapping("/api/file/download")
 @Api(tags = "系统：文件下载管理")
 public class FileDownLoadController {
     @Autowired
     private IFileService fileService;


     private String savepath;
     /**
      * 文件下载
      */
     @PostMapping("/download")
     public void download(String name,
                        String md5,
                        MultipartFile file) throws IOException {
         fileService.download(name, null, null,null);
     }

     /**
      * 大文件上传
      */
     @PostMapping("/big")
     public void download(String name,
                        String md5,
                        String path,
                        Long size,
                        Integer chunks,
                        Integer chunk,
                        MultipartFile file) throws IOException {
         if (chunks != null && chunks != 0) {
             fileService.downloadWithBlock(name, null, null,null);
         } else {
             fileService.download(name, null, null,null);
         }
     }

     /**
      * 秒传
      */
     @GetMapping("/quickDownload")
     public boolean upload(String md5) {
         return fileService.checkDownloadMd5(md5);
     }

 }
