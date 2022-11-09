package com.xuexi.workbench.service.impl;

import com.xuexi.workbench.domain.ClueActivityRelation;
import com.xuexi.workbench.mapper.ClueActivityRelationMapper;
import com.xuexi.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("ClueActivityRelationServiceImpl")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Override
    public Integer saveCreateClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelationList) {
        return clueActivityRelationMapper.insertClueActivityRelationByList(clueActivityRelationList);
    }

    @Override
    public Integer deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation clueActivityRelation) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueIdActivityId(clueActivityRelation);
    }
}
