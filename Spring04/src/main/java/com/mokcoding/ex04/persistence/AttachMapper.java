package com.mokcoding.ex04.persistence;

import com.mokcoding.ex04.domain.Attach;

public interface AttachMapper {
    int insert(Attach attach);
    Attach selectByAttachId(int attachId);
    int update(Attach attach);
    int delete(int attachId);
}
