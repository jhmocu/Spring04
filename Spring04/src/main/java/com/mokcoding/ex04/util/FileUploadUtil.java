package com.mokcoding.ex04.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import lombok.extern.log4j.Log4j;

@Log4j
public class FileUploadUtil {
	
	// 유니크 파일 이름 생성
	public static String makeUploadFilePath(String uploadPath, String fileName) {
		
		UUID uuid = UUID.randomUUID();
		
		String uniqueName = uuid.toString() + "_" + fileName;
		
		String datePath = makeDatePath(uploadPath);
		return datePath + File.separator + uniqueName;
	}
	
	// 파일이 저장되는 폴더 이름을 날짜 형식(yyyy/MM/dd)으로 생성하기 위한 메서드
	public static String makeDatePath(String uploadPath) {
		Calendar calendar = Calendar.getInstance();
		
		String yearPath = String.valueOf(calendar.get(Calendar.YEAR));
		log.info("yearPath: " + yearPath);
		makeDir(uploadPath, yearPath);
		
		String monthPath = yearPath
				+ File.separator
				+ new DecimalFormat("00")
					.format(calendar.get(Calendar.MONTH) + 1);
		log.info("monthPath: " + monthPath);
		makeDir(uploadPath, monthPath);
		
		String datePath = monthPath
				+ File.separator
				+ new DecimalFormat("00")
					.format(calendar.get(Calendar.DATE));
		log.info("datePath: " + datePath);
		makeDir(uploadPath, datePath);

		return datePath;
	}
	
	// 폴더 생성 메서드
	private static void makeDir(String uploadPath, String path) {
		File dirPath = new File(uploadPath, path);
		if (!dirPath.exists()) {
			dirPath.mkdir();
			log.info(dirPath.getPath() + " successfully created.");
		} else {
			log.info(dirPath.getPath() + " already exists.");
		}
	}
	
}
