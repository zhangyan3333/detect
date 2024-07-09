package com.bjj.detect.service;

import org.springframework.web.multipart.MultipartFile;

public interface FilesService {

	String saveBasicFile(MultipartFile file);

	String saveFaceFile(MultipartFile file);

	boolean deleteFile(String filePath);
}
