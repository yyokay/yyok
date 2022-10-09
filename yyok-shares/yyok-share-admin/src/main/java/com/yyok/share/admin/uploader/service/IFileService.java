package com.yyok.share.admin.uploader.service;

import com.yyok.share.admin.uploader.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface IFileService {

    void upload(String name,
                String md5,
                String path, //save store
                MultipartFile file) throws IOException;

    void uploadWithBlock(String name,
                         String md5,
                         String path,
                         Long size,
                         Integer chunks,
                         Integer chunk,
                         MultipartFile file) throws IOException;

    boolean checkMd5(String md5);

    boolean getByFile(File file);

    void save(File file);

    boolean checkDownloadMd5(String md5);

    void download(String fileName, String []header, HashMap hm, List dataSet);

    void downloadWithBlock(String fileName, String []header, HashMap hm, List dataSet);
}
