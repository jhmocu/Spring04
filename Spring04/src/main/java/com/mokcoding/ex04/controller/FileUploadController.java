package com.mokcoding.ex04.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mokcoding.ex04.util.FileUploadUtil;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class FileUploadController {

	@Autowired
	private String uploadPath;
	
	@GetMapping("/upload")
	public void uploadGET() {
		log.info("uploadGET()");
	} // end uploadGET()
	
	// 단일 파일 업로드 수신 및 파일 저장
	@PostMapping("/upload")
	public void uploadPOST(MultipartFile file) {
		log.info("uploadPost()");
		log.info("파일 이름 : " + file.getOriginalFilename());
		log.info("파일 크기 : " + file.getSize());
		
		// File 객체에 파일 경로 및 파일명 설정
		File savedFile = new File(uploadPath, file.getOriginalFilename());
		try {
			file.transferTo(savedFile); // 실제 경로에 파일 저장
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	} // end uploadPOST()
	
	// 다중 파일 업로드 수신 및 파일들 저장
	@PostMapping("/uploads")
	public void uploadsPost(MultipartFile[] files) { // 배열에 파일들 적용
		for(MultipartFile file : files) {
			log.info(file.getOriginalFilename());
			File savedFile = new File(uploadPath, file.getOriginalFilename());
			try {
				file.transferTo(savedFile); // 실제 경로에 파일 저장
			} catch (Exception e) {
				log.error(e.getMessage());
			} 
		}
	} // end uploadsPost()
	
	@GetMapping("/upload-ajax")
	public void uploadAjaxGET() {
		log.info("uploadAjaxGET() 호출");
	}
	
	@PostMapping("/upload-ajax")
	@ResponseBody
	public ResponseEntity<String> uploadAjaxPOST(MultipartFile[] files) {
		log.info("uploadAjaxPOST() 호출");
		
		String result = null; // result : 파일 경로 및 썸네일 이미지 이름
		
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
		
	}
	
	@GetMapping("/display")
	public ResponseEntity<byte[]> display(String fileName) {
		log.info("display() 호출");
		
		ResponseEntity<byte[]> entity = null;

		
		return entity;
		
	}
	
} // end FileUploadController













