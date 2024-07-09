package com.bjj.detect.serviceimpl;

import com.bjj.detect.service.FilesService;
import com.syzx.framework.config.FrameworkConfig;
import com.syzx.framework.exceptions.BusinessException;
import com.syzx.framework.uid.CentralizedUidGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class FilesServiceImpl implements FilesService {

	private static final DateTimeFormatter datePathFormat = DateTimeFormatter.ofPattern("yyyy/MM");

	@Override
	public String saveBasicFile(MultipartFile file) {
		String datePath = LocalDate.now().format(datePathFormat);
		File folder = new File(String.format("%s%s", FrameworkConfig.dataPath, datePath));
		if (!folder.exists() && !folder.mkdirs()) {
			throw new BusinessException(1, "无法创建目录");
		} else {
			String oldName = file.getOriginalFilename();
			String newName = CentralizedUidGenerator.getId() + oldName.substring(oldName.lastIndexOf("."));
			String suffix = oldName.substring(oldName.lastIndexOf(".") + 1);

			try {
				if (    suffix.equals("docx") ||
						suffix.equals("doc") ||
						suffix.equals("xls") ||
						suffix.equals("xlsx") ||
						suffix.equals("pdf") ||
						suffix.equals("png") ||
						suffix.equals("jpg")) {
					file.transferTo(new File(folder, newName));
					return String.format("%s/%s", datePath, newName);
				}else {
					throw new BusinessException(1, "无法上传该类型文件");
				}
			} catch (IOException var7) {
				throw new BusinessException(1, "无法上传文件");
			}
		}
	}

	@Override
	public String saveFaceFile(MultipartFile file) {
		String datePath = LocalDate.now().format(datePathFormat);
		File folder = new File(String.format("%s%s", FrameworkConfig.dataPath, datePath));
		if (!folder.exists() && !folder.mkdirs()) {
			throw new BusinessException(1, "无法创建目录");
		} else {
			String oldName = file.getOriginalFilename();
			String newName = CentralizedUidGenerator.getId() + oldName.substring(oldName.lastIndexOf("."));
			String suffix = oldName.substring(oldName.lastIndexOf(".") + 1);

			try {
				if (
						suffix.equals("png") ||
						suffix.equals("jpg")) {
					file.transferTo(new File(folder, newName));
					return String.format("%s/%s", datePath, newName);
				}else {
					throw new BusinessException(1, "无法上传该类型文件");
				}
			} catch (IOException var7) {
				throw new BusinessException(1, "无法上传文件");
			}
		}
	}


	public boolean deleteFile(String filePath) {
		File file = new File(String.format("%s%s", FrameworkConfig.dataPath, filePath));
		if (file.exists()) {
//			contractFileService.deleteByPath(filePath);
			return file.delete();
		} else {
			throw new BusinessException(1, "指定文件不存在");
		}
	}
}
