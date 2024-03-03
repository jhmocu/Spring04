package com.mokcoding.ex04.service;

import java.util.List;

import com.mokcoding.ex04.domain.AttachDTO;

public interface AttachService {
	
    int createAttach(AttachDTO attachDTO);

    AttachDTO getAttachById(int attachId);
    
    List<Integer> getAllId();

    int updateAttach(AttachDTO attachDTO);
    
    int deleteAttach(int attachId);

}
