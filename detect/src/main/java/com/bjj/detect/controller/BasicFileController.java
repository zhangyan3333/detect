package com.bjj.detect.controller;

import com.bjj.detect.entity.DetectRecord;
import com.bjj.detect.entity.PgCertificate;
import com.bjj.detect.entity.PgRecord;
import com.bjj.detect.entity.VO.DetectRecordVO;
import com.bjj.detect.service.FilesService;
import com.syzx.framework.controller.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(value = "/exportDetectRecord")
    public ApiResult<String> exportDetectRecord(@RequestBody DetectRecordVO record) {
        return new ApiResult(0, "导出成功", this.fileService.exportDetectRecord(record,0));
    }

    @PostMapping(value = "/exportDetectResult")
    public ApiResult<String> exportDetectResult(@RequestBody PgCertificate certificate) {
        return new ApiResult(0, "导出成功", this.fileService.exportDetectRecord(certificate,certificate.getExportState()));
    }
}
