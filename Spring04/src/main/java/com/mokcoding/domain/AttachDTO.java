package com.mokcoding.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter 
@Setter
@ToString 
public class AttachDTO {
	private String memberId;
	private String attachPath;
	private MultipartFile file;
	
}
