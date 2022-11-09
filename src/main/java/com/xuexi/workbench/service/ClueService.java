package com.xuexi.workbench.service;

import com.xuexi.workbench.domain.Clue;

import java.util.List;

public interface ClueService {
    Integer saveCreateClue(Clue clue);

    List<Clue> queryAllClue();

    Clue queryClueForDetailById(String id);
}
