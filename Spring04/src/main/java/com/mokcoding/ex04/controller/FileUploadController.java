package com.mokcoding.ex04.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mokcoding.ex04.domain.AttachDTO;
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
		for (MultipartFile file : files) {
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

	// 전송된 파일들을 원본 이미지와 섬네일 이미지로 저장
	@PostMapping("/upload-ajax")
	@ResponseBody
	public ResponseEntity<ArrayList<AttachDTO>> uploadAjaxPOST(MultipartFile[] files) {
		log.info("uploadAjaxPOST() 호출");

		ArrayList<AttachDTO> list = new ArrayList<>();

		for (MultipartFile file : files) {

			// UUID 생성
			String chgName = UUID.randomUUID().toString();
			// 파일 저장
			FileUploadUtil.saveFile(uploadPath, file, chgName);

			String path = FileUploadUtil.makeDatePath();
			String extension = FileUploadUtil.subStrExtension(file.getOriginalFilename());

			FileUploadUtil.createThumbnail(uploadPath, path, chgName, extension);

			AttachDTO attachDTO = new AttachDTO();
			// 파일 경로 설정
			attachDTO.setAttachPath(path);
			// 파일 실제 이름 설정
			attachDTO.setAttachRealName(FileUploadUtil.subStrName(file.getOriginalFilename()));
			// 파일 변경 이름(UUID) 설정
			attachDTO.setAttachChgName(chgName);
			// 파일 확장자 설정
			attachDTO.setAttachExtension(extension);

			list.add(attachDTO);
		}

		return new ResponseEntity<ArrayList<AttachDTO>>(list, HttpStatus.OK);

	}

	@GetMapping("/display")
	public ResponseEntity<byte[]> display(String attachPath, String fileName) {
		log.info("display() 호출");
		log.info(fileName);
		ResponseEntity<byte[]> entity = null;
		try {
			// 파일을 읽어와서 byte 배열로 변환
			String savedPath = uploadPath + File.separator 
					+ attachPath + File.separator + fileName; 
			Path path = Paths.get(savedPath);
			byte[] imageBytes = Files.readAllBytes(path);


			// 이미지의 MIME 타입 확인하여 적절한 Content-Type 지정
			String contentType = Files.probeContentType(path);

			// HTTP 응답에 byte 배열과 Content-Type을 설정하여 전송
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.parseMediaType(contentType));
			entity = new ResponseEntity<byte[]>(imageBytes, httpHeaders, HttpStatus.OK);
		} catch (IOException e) {
			// 파일을 읽는 중에 예외 발생 시 예외 처리
			e.printStackTrace();
			return ResponseEntity.notFound().build(); // 파일을 찾을 수 없음을 클라이언트에게 알림
		}

		return entity;

	}

} // end FileUploadController
