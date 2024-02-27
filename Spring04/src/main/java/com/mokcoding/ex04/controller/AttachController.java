package com.mokcoding.ex04.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mokcoding.domain.AttachDTO;
import com.mokcoding.ex04.util.FileUploadUtil;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class AttachController {

	@Autowired
	private String uploadPath;
	
	@GetMapping("/attach")
	public void attachGET() {
		log.info("attachGET()");
	} // end uploadGET()
	
	// 단일 파일 업로드 수신 및 파일 저장
	@PostMapping("/attach")
	public String attachPOST(AttachDTO attachDTO, RedirectAttributes reAttr) {
		log.info("attachPost()");
		try {
			String attachPath = FileUploadUtil.makeUploadFilePath(uploadPath, attachDTO.getFile().getOriginalFilename());
			
			File saveFile = new File(uploadPath, attachPath);
			attachDTO.getFile().transferTo(saveFile); 
			reAttr.addFlashAttribute("attachPath", attachPath);
			return "redirect:/result";
		} catch (Exception e) {
			log.error(e.getMessage());
			return "redirect:/attach";
		}
	} // end uploadPOST()
	
	
	@GetMapping("/result")
	public void result(@ModelAttribute("attachPath") String attachPath) {
		log.info("result()");
		log.info("attachPath : " + attachPath);
	}
	
	@GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(String fileName) throws IOException {
        log.info(fileName);
		// 서버에 저장된 파일 경로
        Path filePath = Paths.get(uploadPath, fileName);

        // 파일을 ByteArrayResource로 변환하여 리턴
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));

        // 다운로드할 파일 이름을 헤더에 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        // 파일의 MIME 타입 설정
        String mimeType = Files.probeContentType(filePath);
        MediaType mediaType = MediaType.parseMediaType(mimeType);
        
        // 파일을 클라이언트로 전송
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(mediaType)
                .body(resource);
    }
	
	
} // end FileUploadController













