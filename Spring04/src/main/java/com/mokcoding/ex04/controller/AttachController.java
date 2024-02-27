package com.mokcoding.ex04.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
    public ResponseEntity<Resource> downloadFile(String fileName) throws IOException {
        log.info(fileName);
		// 서버에 저장된 파일 경로
        Resource resource = new FileSystemResource(uploadPath + fileName);
        // 다운로드할 파일 이름을 헤더에 설정
        HttpHeaders headers = new HttpHeaders();
        String conversionName = new String(resource.getFilename().getBytes("UTF-8"),"ISO-8859-1");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + conversionName);

        
        // 파일을 클라이언트로 전송
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }
	
	
} // end FileUploadController













