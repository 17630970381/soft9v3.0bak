package com.cqupt.software_9.service.Adapter;

import com.cqupt.software_9.common.UploadResult;
import com.cqupt.software_9.service.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public abstract class FileServiceAdapter implements FileService {


    @Override
    public UploadResult fileUpload(MultipartFile file, String newName, String disease) throws IOException {
        return null;
    }

    @Override
    public UploadResult fileUpload(MultipartFile file, String modelname, String diseasename, String publisher, Integer uid) throws IOException {
        return null;
    }

    @Override
    public UploadResult creatUpTable(MultipartFile file, String newName, String disease, String user, Integer uid, String chinesename) {
        return null;
    }

    //在线训练
    public abstract UploadResult fileUpload(MultipartFile file, String modelname, String diseasename, String publisher, String uid) throws IOException;
}

