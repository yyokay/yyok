package com.yyok.share.admin.uploader.service.impl;

import com.yyok.share.admin.uploader.domain.File;
import com.yyok.share.admin.uploader.service.IFileService;
import com.yyok.share.framework.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

import static com.yyok.share.admin.uploader.utils.UploadUtils.*;

/**
 * 文件上传服务
 */
@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private IFileService fileService;


    /**
     * 上传文件
     * @param md5
     * @param file
     */
    @Override
    public void upload(String name,
                       String md5,
                       String pathstr,
                       MultipartFile file) throws IOException {
        String path = pathstr + "/" + name;
        FileUtil.write(path, file.getInputStream());
        fileService.save(new File(name, md5, path, new Date()));
    }



    /**
     * 分块上传文件
     * @param md5
     * @param size
     * @param chunks
     * @param chunk
     * @param file
     * @throws IOException
     */
    public void uploadWithBlock(String name,
                                String md5,
                                String path,
                                Long size,
                                Integer chunks,
                                Integer chunk,
                                MultipartFile file) throws IOException {
        String fileName = getFileName(md5, chunks);
        Object UploadConfig;
        FileUtil.writeWithBlok(path + fileName, size, file.getInputStream(), file.getSize(), chunks, chunk);
        addChunk(md5,chunk);
        if (isUploaded(md5)) {
            removeKey(md5);
            fileService.save(new File(name, md5,path + fileName, new Date()));
        }
    }

    /**
     * 检查Md5判断文件是否已上传
     * @param md5
     * @return
     */
    @Override
    public boolean checkMd5(String md5) {
        File file = new File();
        file.setMd5(md5);
        boolean bl = fileService.getByFile(file);
        return  bl;
    }

    @Override
    public boolean getByFile(File file) {
        return false;
    }

    @Override
    public void save(File file) {

    }
}
