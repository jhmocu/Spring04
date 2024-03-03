package com.mokcoding.ex04.persistence;

import java.util.List;

import com.mokcoding.ex04.domain.Attach;

public interface AttachMapper {
    int insert(Attach attach);
    Attach selectByAttachId(int attachId);
    List<Integer> selectIdList();
    int update(Attach attach);
    int delete(int attachId);
}
