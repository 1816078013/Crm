package com.xuexi.workbench.service;

import com.xuexi.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkService {
    List<ClueRemark> queryClueRemarkForDetailByClueId(String id);
}
