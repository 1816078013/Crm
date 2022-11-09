package com.xuexi.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class indexController {
    /*
    理论上，给controller方法分配url：http://127.0.0.1：8080/crm/
    但是为了简便，前面的http://127.0.0.1:8080/crm就可以省去，用/代替,
    /就代表了绝对路径，正好/也代表了 “所有” 的意思。

   ************************************************
    这里说一下，关于这个路径中，斜线的问题
    整个下午，我都在想，能不能用网站上的地址来替换掉这个斜线，因为这个斜线代表的就是网站的地址，也是整个项目的根路径
    结论是，不可以用网站地址去替换，只能用斜线来表示，视频里的大老师也说了，写网站地址是错误的，
    因为还没有写处理请求的方法的时候，网站的默认访问路径是http://127.0.0.1：8080/crm/，这个是默认的欢迎文件的资源
    也是整个项目的起点，所有的其他请求资源都要在http://127.0.0.1：8080/crm/的  这个    最后一个  “/”   开始
    去请求访问，所以，这里就用这最后一个斜线去代替了请求映射，避免了很多请求中重复的代码

    而且，springmvc框架也有规定，前面的那些重复的请求地址   ：http://127.0.0.1：8080/crm
    必须省略，带上反而就会报错
     */


    @RequestMapping("/")
    public ModelAndView index(){
        //请求转发操作
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
