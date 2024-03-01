package com.mokcoding.ex04.service;

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
		testGetAttach();
		testUpdateAttach();
		testDeleteAttach();
	}

	private void testCreateAttach() {
		AttachDTO dto = new AttachDTO();
		
		attachService.createAttach(null);
	}

	private void testGetAttach() {
		// TODO Auto-generated method stub
		
	}

	private void testUpdateAttach() {
		// TODO Auto-generated method stub
		
	}

	private void testDeleteAttach() {
		// TODO Auto-generated method stub
		
	}
	

} // end ReplyServiceTest
