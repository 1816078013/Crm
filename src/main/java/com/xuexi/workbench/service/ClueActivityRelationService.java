package com.xuexi.workbench.service;

import com.xuexi.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {

    Integer saveCreateClueActivityRelationByList(List<ClueActivityRelation> activityRelationList);

    Integer deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation clueActivityRelation);
}
