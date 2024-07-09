package com.bjj.detect.controller;

import com.bjj.detect.service.FilesService;
import com.syzx.framework.controller.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/basicfile")
public class BasicFileController {

    @Autowired
    private FilesService fileService;

    @PostMapping
    public ApiResult<String> upload(@RequestBody MultipartFile file) {
        return new ApiResult(0, "上传成功", this.fileService.saveBasicFile(file));
    }

    @PostMapping(value = "/face")
    public ApiResult<String> uploadFace(@RequestBody MultipartFile file) {
        return new ApiResult(0, "上传成功", this.fileService.saveFaceFile(file));
    }

}