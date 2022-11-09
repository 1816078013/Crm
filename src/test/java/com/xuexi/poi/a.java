package com.xuexi.poi;

import com.xuexi.workbench.domain.Clue;
import com.xuexi.workbench.mapper.ClueMapper;
import com.xuexi.workbench.service.ClueService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class a {

    @Autowired
    private ClueService clueService;

    @Test
    public void test1(){
        List<Clue> clueList = clueService.queryAllClue();
        for (Clue c: clueList) {
            System.out.println("对象是："+c.getFullname());
        }
    }
}
