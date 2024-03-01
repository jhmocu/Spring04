package com.mokcoding.ex04.service;

import com.mokcoding.ex04.domain.AttachDTO;

public interface AttachService {
	
    int createAttach(AttachDTO attach);

    AttachDTO getAttachById(int attachId);

    int updateAttach(AttachDTO attach);
    
    int deleteAttach(int attachId);

}
