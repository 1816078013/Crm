package com.xuexi.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WorkBenchIndexController {

    @RequestMapping("/workbench/index.do")
    public ModelAndView workbench(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("workbench/index");
        return modelAndView;
    }
}
