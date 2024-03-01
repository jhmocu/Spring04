package com.mokcoding.ex04.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mokcoding.ex04.domain.Attach;
import com.mokcoding.ex04.domain.AttachDTO;
import com.mokcoding.ex04.persistence.AttachMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AttachServiceImple implements AttachService {

	@Autowired
    private AttachMapper attachMapper;


    @Override
    public int createAttach(AttachDTO attach) {
    	log.info("createAttach");
        return attachMapper.insert(toEntity(attach));
    }

    @Override
    public AttachDTO getAttachById(int attachId) {
    	log.info("getAttachById()");
        return toDto(attachMapper.selectByAttachId(attachId));
    }

    @Override
    public int updateAttach(AttachDTO attach) {
    	log.info("updateAttach()");
        return attachMapper.update(toEntity(attach));
    }

    @Override
    public int deleteAttach(int attachId) {
    	log.info("deleteAttach()");
        return attachMapper.delete(attachId);
    }
    
    // AttachDTO를 Attach로 변환하는 메서드
    private Attach toEntity(AttachDTO attachDto) {
        Attach attach = new Attach();
        attach.setAttachId(attachDto.getAttachId());
        attach.setBoardId(attachDto.getBoardId());
        attach.setAttachPath(attachDto.getAttachPath());
        attach.setAttachRealName(attachDto.getAttachRealName());
        attach.setAttachChgName(attachDto.getAttachChgName());
        attach.setAttachExtension(attachDto.getAttachExtension());
        attach.setAttachDateCreated(attachDto.getAttachDateCreated());
        // file 필드는 Attach 엔티티에 없으므로 무시됩니다.
        return attach;
    }

    // Attach를 AttachDTO로 변환하는 메서드
    private AttachDTO toDto(Attach attach) {
        AttachDTO attachDto = new AttachDTO();
        attachDto.setAttachId(attach.getAttachId());
        attachDto.setBoardId(attach.getBoardId());
        attachDto.setAttachPath(attach.getAttachPath());
        attachDto.setAttachRealName(attach.getAttachRealName());
        attachDto.setAttachChgName(attach.getAttachChgName());
        attachDto.setAttachExtension(attach.getAttachExtension());
        attachDto.setAttachDateCreated(attach.getAttachDateCreated());
        // AttachDTO의 file 필드는 Attach 엔티티에 없으므로 무시됩니다.
        return attachDto;
    }
}
