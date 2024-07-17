package com.bjj.detect.service;

import com.bjj.detect.entity.PgRecord;
import org.springframework.web.multipart.MultipartFile;

public interface FilesService {

	String saveBasicFile(MultipartFile file);

	String saveFaceFile(MultipartFile file);

	boolean deleteFile(String filePath);

	String exportDetectRecord(Object o,int index);
}
