package com.xuexi.workbench.service.impl;

import com.xuexi.workbench.domain.ClueRemark;
import com.xuexi.workbench.mapper.ClueRemarkMapper;
import com.xuexi.workbench.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;


    @Override
    public List<ClueRemark> queryClueRemarkForDetailByClueId(String id) {
        return clueRemarkMapper.selectClueRemarkForDetailByClueId(id);
    }
}
