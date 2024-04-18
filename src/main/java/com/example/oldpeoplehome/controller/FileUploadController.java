package com.example.oldpeoplehome.controller;

import com.example.oldpeoplehome.pojo.Result;
import com.example.oldpeoplehome.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        //保证文件的名字是唯一的，防止文件覆盖
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String URL = AliOssUtil.uploadFile(filename, file.getInputStream());
        System.out.println(URL);
        return Result.success(URL);
    }
}
