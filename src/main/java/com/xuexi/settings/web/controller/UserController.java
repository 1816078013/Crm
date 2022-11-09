package com.xuexi.settings.web.controller;

import com.xuexi.commons.contants.Contants;
import com.xuexi.commons.domain.ReturnObject;
import com.xuexi.commons.utils.DateUtils;
import com.xuexi.settings.domain.User;
import com.xuexi.settings.service.UserService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/settings/qx/user/tologin.do")
    public ModelAndView UserLogin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settings/qx/user/login");
        return  modelAndView ;
    }

    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public  Object login(HttpServletRequest request, HttpServletResponse response){
        System.out.println("当前的IP是---------------------->"+request.getRemoteAddr());
        String act = request.getParameter("loginact");
        String pwd = request.getParameter("loginpwd");
        String stmdl = request.getParameter("isrempwd");
      /*  System.out.println("你的母亲走了后台....."+act);
        System.out.println("你的母亲走了后台....."+pwd);*/
        //封装参数`
        Map<String,Object> map = new HashMap<>();
        map.put("dlzh",act);
        map.put("dlmm",pwd);
        //调用service层的方法，查询用户
        User user = userService.queryUserByLoginActAndLoginPwd(map);
        //System.out.println("后台已接收...");
        ReturnObject returnObject = new ReturnObject();
        if (user == null) {
            //用户名和密码错误
            //returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setCode(Contants.code.FAIL);
            returnObject.setMessage("对不起，您的账号和密码有误");
        }else {
            String gq = user.getExpireTime();
            String xzsj = DateUtils.formateDateTime(new Date());
            //System.out.println("现在时间："+xzsj);
            if (xzsj.compareTo(user.getExpireTime())>0){
                //账号时间过期了
                //returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setCode(Contants.code.FAIL);
                returnObject.setMessage("对不起，你的账号时间已到期");
            }else if (user.getLockState().equals("0")){
                //账号被锁定
                //returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setCode(Contants.code.FAIL);
                returnObject.setMessage("对不起，你的账号已经被锁定");
            }else  if (!user.getAllowIps().contains(request.getRemoteAddr())){
                //ip受到限制
                //returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setCode(Contants.code.FAIL);
                returnObject.setMessage("对不起，你当前登录的ip受到限制");
            }else {
                //登录成功
                //returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setCode(Contants.code.SUCCESS);

                //把用户信息存储到session作用域中，让其他的地方。静态资源页面能够拿数据使用
                HttpSession session = request.getSession();
                session.setAttribute(Contants.SESSION_USER_KEY,user);

                Cookie zhanghao = null;
                Cookie mima = null;
                //判断用户是否选择了十天免登录这个按钮
                if ("true".equals(stmdl)){
                    //程序走到这里，就说明用户选择了十天免登录的功能，就写一个cookie，
                    // cookie里面存储着用户的账号和密码，方便下次免登录的时候直接填写，
                    zhanghao = new Cookie("zhanghao",user.getLoginAct());
                    mima = new Cookie("mima",user.getLoginPwd());
                    //设置两个cookie存活的最大时间
                    zhanghao.setMaxAge(60*60*24*10);
                    mima.setMaxAge(60*60*24*10);
                    //信息存储好之后，讲cookie存储到响应包里，返还给用户
                    response.addCookie(zhanghao);
                    response.addCookie(mima);
                }else {
                    //把没过期的cookie删掉
                    //你要删什么cookie，你就写什么cookie，因为如果key一样，他就会把原有的cookie覆盖掉
                    zhanghao = new Cookie("zhanghao","");
                    mima = new Cookie("mima","");
                    zhanghao.setMaxAge(0);
                    mima.setMaxAge(0);
                    response.addCookie(zhanghao);
                    response.addCookie(mima);
                }
            }
        }
        return returnObject;
    }


    @RequestMapping("/settings/qx/user/loginout.do")
    public String  Logout(HttpServletRequest request,HttpServletResponse response){
        //清空cookie
        Cookie zhanghao = null;
        Cookie mima = null;
        zhanghao = new Cookie("zhanghao","");
        mima = new Cookie("mima","");
        zhanghao.setMaxAge(0);
        mima.setMaxAge(0);
        response.addCookie(zhanghao);
        response.addCookie(mima);

        //销毁session
        HttpSession session = request.getSession();
        session.invalidate();
        return "settings/qx/user/login";

        //也可以这么写
        //return "redirect:/";
    }
}
