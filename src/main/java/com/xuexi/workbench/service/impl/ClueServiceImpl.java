package com.xuexi.workbench.service.impl;

import com.xuexi.workbench.domain.Clue;
import com.xuexi.workbench.mapper.ClueMapper;
import com.xuexi.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "ClueServiceImpl")
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Override
    public Integer saveCreateClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public List<Clue> queryAllClue() {
        List<Clue> s=  clueMapper.selectAllClue();
        return s;
    }

    @Override
    public Clue queryClueForDetailById(String id) {
      Clue c = clueMapper.selectClueForDetailById(id);
        System.out.println("查询出来的对象信息是"+c);
        return c;
    }
}
