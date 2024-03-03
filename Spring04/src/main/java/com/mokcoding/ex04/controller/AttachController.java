package com.mokcoding.ex04.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mokcoding.ex04.domain.AttachDTO;
import com.mokcoding.ex04.service.AttachService;
import com.mokcoding.ex04.util.FileUploadUtil;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class AttachController {

    @Autowired
    private String uploadPath;

    @Autowired
    private AttachService attachService;

    // 첨부 파일 업로드 페이지 이동(GET)
    @GetMapping("/register")
    public void registerGET() {
        log.info("registerGET()");
    } // end registerGET()

    // 첨부 파일 업로드 처리(POST)
    @PostMapping("/attach")
    public String attachPOST(AttachDTO attachDTO) {
        log.info("attachPost()");
        log.info("attachDTO = " + attachDTO);
        MultipartFile file = attachDTO.getFile();
        
        // UUID 생성
        String uuid = UUID.randomUUID().toString();
        // 파일 저장
        FileUploadUtil.saveFile(uploadPath, file, uuid);

        // 파일 경로 설정
        attachDTO.setAttachPath(FileUploadUtil.makeDatePath());
        // 파일 실제 이름 설정
        attachDTO.setAttachRealName(FileUploadUtil.subStrName(file.getOriginalFilename()));
        // 파일 변경 이름(UUID) 설정
        attachDTO.setAttachChgName(uuid);
        // 파일 확장자 설정
        attachDTO.setAttachExtension(FileUploadUtil.subStrExtension(file.getOriginalFilename()));
        // DB에 첨부 파일 정보 저장
        log.info(attachService.createAttach(attachDTO) + "행 등록") ;

        return "redirect:/list";
    } // end attachPOST()
    
    // 첨부 파일 목록 조회(GET)
    @GetMapping("/list")
    public void list(Model model) {
        // 첨부 파일 목록을 Model에 추가하여 전달
        model.addAttribute("idList", attachService.getAllId());
        log.info("list()");
    }

    // 첨부 파일 상세 정보 조회(GET)
    @GetMapping("/detail")
    public void detail(int attachId, Model model) {
        log.info("detail()");
        log.info("attachId : " + attachId);
        // 첨부 파일 ID로 상세 정보 조회
        AttachDTO attachDTO = attachService.getAttachById(attachId);
        // 조회된 상세 정보를 Model에 추가하여 전달
        model.addAttribute("attachDTO", attachDTO);
    } // end detail()
    

    // 첨부 파일 다운로드(GET)
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> download(int attachId) throws IOException {
        log.info("download()");
        
        // attachId로 상세 정보 조회
        AttachDTO attachDTO = attachService.getAttachById(attachId);
        String attachPath = attachDTO.getAttachPath();
        String attachChgName = attachDTO.getAttachChgName();
        String attachExtension = attachDTO.getAttachExtension();
        String attachRealName = attachDTO.getAttachRealName();
        
        // 서버에 저장된 파일 정보 생성
        String resourcePath = uploadPath + File.separator + attachPath + File.separator + attachChgName;
        // 파일 리소스 생성
        Resource resource = new FileSystemResource(resourcePath);
        // 다운로드할 파일 이름을 헤더에 설정
        HttpHeaders headers = new HttpHeaders();
        String attachName = new String(attachRealName.getBytes("UTF-8"), "ISO-8859-1");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachName + "." + attachExtension);

        // 파일을 클라이언트로 전송
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    } // end download()
    
    // 첨부 파일 수정 페이지 이동(GET)
    @GetMapping("/modify")
    public void modifyGET(@ModelAttribute("attachId") int attachId) {
        log.info("modify()");
        log.info("attachId : " + attachId);
        
    } // end modifyGET()
    
    
    // 첨부 파일 수정 기능(POST)
    @PostMapping("/modify")
    public String modifyPOST(int attachId, MultipartFile file) {
    	log.info("modifyPOST()");
    	log.info("attachId = " + attachId);
    	
    	// attach 테이블 조회
    	AttachDTO attachDTO = attachService.getAttachById(attachId);
    	
    	// 기존 파일 삭제
    	FileUploadUtil.deleteFile(uploadPath, attachDTO.getAttachPath(), attachDTO.getAttachChgName());
    	  
    	/* Attach 데이터 수정 */
        // UUID 생성
        String uuid = UUID.randomUUID().toString();
        // 파일 저장
        FileUploadUtil.saveFile(uploadPath, file, uuid);

        // 파일 경로 설정
        attachDTO.setAttachPath(FileUploadUtil.makeDatePath());
        // 파일 실제 이름 설정
        attachDTO.setAttachRealName(FileUploadUtil.subStrName(file.getOriginalFilename()));
        // 파일 변경 이름(UUID) 설정
        attachDTO.setAttachChgName(uuid);
        // 파일 확장자 설정
        attachDTO.setAttachExtension(FileUploadUtil.subStrExtension(file.getOriginalFilename()));
        
        attachDTO.setAttachId(attachId);
        // DB에 첨부 파일 정보 저장
        log.info(attachService.updateAttach(attachDTO) + "행 수정") ;
    	
    	return "redirect:/list";
    }
    
    // 첨부 파일 삭제 기능(POST)
    @PostMapping("/delete")
    public String delete(int attachId) {
    	log.info("delete()");
    	log.info("attachId = " + attachId);
    	
    	AttachDTO attachDTO = attachService.getAttachById(attachId);
    	
    	FileUploadUtil.deleteFile(uploadPath, attachDTO.getAttachPath(), attachDTO.getAttachChgName());
    	
    	log.info(attachService.deleteAttach(attachId) + "행 삭제");
    	
    	return "redirect:/list";
    }
    

} // end FileUploadController
