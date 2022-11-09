package com.xuexi.workbench.web.controller;

import com.xuexi.commons.contants.Contants;
import com.xuexi.commons.domain.ReturnObject;
import com.xuexi.commons.utils.DateUtils;
import com.xuexi.commons.utils.UUID;
import com.xuexi.settings.domain.User;
import com.xuexi.workbench.domain.ActivityRemark;
import com.xuexi.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

import static com.xuexi.commons.contants.Contants.EDIT_FLAG_YES;
import static com.xuexi.commons.contants.Contants.SESSION_USER_KEY;

@Controller
public class ActivityRemarkController {

    @Autowired
    private ActivityRemarkService activityRemarkService;


    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    @ResponseBody
    public Object saveCreateActivityRemark(ActivityRemark activityRemark, HttpSession session){  //这里直接定义一个实体类的参数，前台传过来的参数，
                                                                                                 // 都是这个实体类里面的属性，所以传过来的是，就已经被这个实体类封装好了

        //封装参数
        activityRemark.setId(UUID.getuuid());
        activityRemark.setCreateTime(DateUtils.formateDateTime(new Date()));
        User user = (User) session.getAttribute(SESSION_USER_KEY);
        activityRemark.setCreateBy(user.getId());
        activityRemark.setEditFlag(Contants.EDIT_FLAG_NO);

        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层，保存创建的市场活动
            Integer result = activityRemarkService.saveCreateActivityRemark(activityRemark);
            if (result>0) {
                returnObject.setCode(Contants.code.SUCCESS);
                returnObject.setRetDate(activityRemark);
            }else {
                returnObject.setCode(Contants.code.FAIL);
                returnObject.setMessage("对不起，后台出现异常，保存失败");
                return  returnObject;
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.code.FAIL);
            returnObject.setMessage("对不起，后台出现异常，保存失败");
            return  returnObject;
        }

        System.out.println("--------》"+activityRemark.getNoteContent());
        return returnObject;
    }

    @RequestMapping("/workbench/activity/deleteActivityRemarkById.do")
    @ResponseBody
    public Object deleteActivityRemarkById(String id){

        ReturnObject returnObject = new ReturnObject();

        try {
            //调用service层方法，删除备注
            Integer resdult = activityRemarkService.deleteActivityRemarkById(id);
            if (resdult>0) {
                returnObject.setCode(Contants.code.SUCCESS);
            }else {
                returnObject.setCode(Contants.code.FAIL);
                returnObject.setMessage("对不起，后台繁忙，轻稍后重试");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.code.FAIL);
            returnObject.setMessage("对不起，后台繁忙，轻稍后重试");
        }
        return returnObject;
    }


    @RequestMapping("/workbench/activity/saveEditActivityRemark.do")
    @ResponseBody
    public Object saveEditActivityRemark(ActivityRemark activityRemark,HttpSession httpSession){
        User user = (User) httpSession.getAttribute(SESSION_USER_KEY);
        ReturnObject returnObject = new ReturnObject();

        //对这些参数进行二次封装
        activityRemark.setEditBy(user.getId());
        activityRemark.setEditTime(DateUtils.formateDateTime(new Date()));
        activityRemark.setEditFlag(EDIT_FLAG_YES);
        try {
            Integer result = activityRemarkService.saveEditActivityRemark(activityRemark);
            System.out.println("----------------->"+result);
            if (result>0){
                returnObject.setCode(Contants.code.SUCCESS);
                returnObject.setRetDate(activityRemark);
            }else {
                returnObject.setCode(Contants.code.FAIL);
                returnObject.setMessage("对不起，后台系统繁忙");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.code.FAIL);
            returnObject.setMessage("对不起，后台系统繁忙");
        }

        return returnObject;
    }
}
