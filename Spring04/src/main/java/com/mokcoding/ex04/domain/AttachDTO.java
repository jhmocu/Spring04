package com.mokcoding.ex04.domain;

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
	private int attachId;
	private int boardId;
	private String attachPath;
	private String attachRealName;
	private String attachChgName;
	private String attachExtension;
	private Date attachDateCreated;
	private MultipartFile file;
	
}
