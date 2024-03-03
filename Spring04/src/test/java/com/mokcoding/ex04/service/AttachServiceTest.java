package com.mokcoding.ex04.service;

import java.io.File;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mokcoding.ex04.config.RootConfig;
import com.mokcoding.ex04.domain.AttachDTO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) // 스프링 JUnit 실행 클래스 연결
@ContextConfiguration(classes = { RootConfig.class }) // 설정 파일 연결
@Log4j

public class AttachServiceTest {
	
	@Autowired
	private AttachService attachService;
	
	@Test
	public void test() {
		testCreateAttach();
		// testGetAttach();
		// testUpdateAttach();
		// testDeleteAttach();
	}

	private void testCreateAttach() {
		AttachDTO dto = new AttachDTO();
		dto.setBoardId(2);
		dto.setAttachPath("2024"+ File.separator + "03"+ File.separator + "01");
		UUID uuid = UUID.randomUUID();
		dto.setAttachRealName("test");
		dto.setAttachChgName(uuid.toString());
		dto.setAttachExtension("txt");
		
		log.info(attachService.createAttach(dto) + "행 등록");
	}

	private void testGetAttach() {
		AttachDTO dto = attachService.getAttachById(1);
		log.info(dto);
		
	}

	private void testUpdateAttach() {
		AttachDTO dto = new AttachDTO();
		
		dto.setAttachPath("2024"+ File.separator + "03"+ File.separator + "01");
		UUID uuid = UUID.randomUUID();
		dto.setAttachRealName("update");
		dto.setAttachChgName(uuid.toString());
		dto.setAttachExtension("txt");
		dto.setAttachId(1);
		
		log.info(attachService.updateAttach(dto) + "행 수정");
	}

	private void testDeleteAttach() {
		log.info(attachService.deleteAttach(1) + "행 삭제");
	}
	

} // end ReplyServiceTest
