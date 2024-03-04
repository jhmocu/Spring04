package com.mokcoding.ex04.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mokcoding.ex04.domain.Attach;

@Mapper
public interface AttachMapper {
    int insert(Attach attach);
    Attach selectByAttachId(int attachId);
    List<Integer> selectIdList();
    int update(Attach attach);
    int delete(int attachId);
}
