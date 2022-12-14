package com.xuexi.workbench.web.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.xuexi.commons.contants.Contants;
import com.xuexi.commons.domain.ReturnObject;
import com.xuexi.commons.utils.DateUtils;
import com.xuexi.commons.utils.UUID;
import com.xuexi.settings.domain.DicValue;
import com.xuexi.settings.domain.User;
import com.xuexi.settings.service.DicValueService;
import com.xuexi.settings.service.UserService;
import com.xuexi.workbench.domain.Activity;
import com.xuexi.workbench.domain.Clue;
import com.xuexi.workbench.domain.ClueActivityRelation;
import com.xuexi.workbench.domain.ClueRemark;
import com.xuexi.workbench.service.ActivityService;
import com.xuexi.workbench.service.ClueActivityRelationService;
import com.xuexi.workbench.service.ClueRemarkService;
import com.xuexi.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class ClueController {

    @Resource
    private DicValueService dicValueService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClueService clueService;

    @Autowired
    private ClueRemarkService clueRemarkService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/workbench/clue/index.do")
    public String idnex(HttpServletRequest httpServletRequest){

        List<User> userList = userService.QueryAllUser();
        List<DicValue> appellationlist = dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStatelist = dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourcelist = dicValueService.queryDicValueByTypeCode("source");
        httpServletRequest.setAttribute("userList",userList);
        httpServletRequest.setAttribute("appellationlist",appellationlist);
        httpServletRequest.setAttribute("clueStatelist",clueStatelist);
        httpServletRequest.setAttribute("sourcelist",sourcelist);
        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/saveCreateClue.do")
    @ResponseBody
    public Object saveCreateClue(Clue clue, HttpSession session){

        ReturnObject returnObject = new ReturnObject();
        //????????????
        clue.setId(UUID.getuuid());
        clue.setCreateTime(DateUtils.formateDateTime(new Date()));
        User user = (User) session.getAttribute(Contants.SESSION_USER_KEY);
        clue.setCreateBy(user.getId());

        try{
            //??????service??????????????????
            Integer result = clueService.saveCreateClue(clue);
            if (result>0){
                returnObject.setCode(Contants.code.SUCCESS);
                returnObject.setRetDate(clue);
            }else {
                returnObject.setCode(Contants.code.FAIL);
                returnObject.setMessage("????????????????????????????????????????????????");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.code.FAIL);
            returnObject.setMessage("????????????????????????????????????????????????");
        }

        return returnObject;
    }

    //???????????????????????????????????????????????????????????????????????????????????????
    @RequestMapping("/workbench/clue/queryAllClue.do")
    @ResponseBody
    public Object queryAllClue(){
        ReturnObject returnObject = new ReturnObject();
        List<Clue> clueList = clueService.queryAllClue();
        returnObject.setRetDate(clueList);
        return returnObject;
    }

    @RequestMapping("/workbench/clue/detailClue.do")
    public String detailClue(String id,HttpServletRequest httpServletRequest){
        System.out.println("id?????????----------------------------->"+id);

        //??????service?????????????????????????????????
        Clue clue = clueService.queryClueForDetailById(id);
        List<ClueRemark> clueRemark = clueRemarkService.queryClueRemarkForDetailByClueId(id);
        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);

        //???????????????????????????????????????????????????????????????
        httpServletRequest.setAttribute("clue",clue);
        httpServletRequest.setAttribute("clueRemark",clueRemark);
        httpServletRequest.setAttribute("activityList",activityList);
        for(Activity s: activityList){
            System.out.println("activityList???????????????------------------???"+s.getName());
        }

        return "workbench/clue/detail";

    }


    @RequestMapping("/workbench/clue/queryActivityForDeTailByNameClueId.do")
    @ResponseBody
    public Object queryActivityForDeTailByNameClueId(String activityName,String clueId){
        System.out.println("activityName--------------???"+activityName);
        System.out.println("clueId----------------->"+clueId);
        Map<String,Object> map  = new TreeMap<>();
         map.put("activityName",activityName);
         map.put("clueId",clueId);

         //??????service??????????????????????????????
         List<Activity> activityList = activityService.queryActivityForDetailByNameClueId(map);
        System.out.println("??????????????????------------------???"+activityList);

         //???????????????????????????json
        return activityList;  //?????????activitylist?????????????????????????????????ajax???????????????????????????    ???[]??? ??????????????????
    }

    @RequestMapping("/workbench/clue/saveBund.do")
    @ResponseBody
    public Object saveBund(String[] activityId,String clueId){

        System.out.println("activityIds:------------>"+activityId);
        ClueActivityRelation clueActivityRelation = null;
        List<ClueActivityRelation> relationList = new ArrayList<>();
        for (int i = 0; i <activityId.length;i++){
            clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setActivityId(activityId[i]);
            clueActivityRelation.setClueId(clueId);
            clueActivityRelation.setId(UUID.getuuid());
            System.out.println(clueActivityRelation.getActivityId()+clueActivityRelation.getClueId());
            relationList.add(clueActivityRelation);
        }

        ReturnObject returnObject = new ReturnObject();
        try {
            //??????service?????????????????????????????????????????????????????????
            Integer result = clueActivityRelationService.saveCreateClueActivityRelationByList(relationList);
           if (result>0){
               returnObject.setCode(Contants.code.SUCCESS);
               List<Activity> activityList = activityService.queryActivityForDetailByIds(activityId);
               returnObject.setRetDate(activityList);
           }else {
               returnObject.setCode(Contants.code.FAIL);
               returnObject.setMessage("??????????????????????????????");
           }
        } catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.code.FAIL);
            returnObject.setMessage("??????????????????????????????");
        }

        return returnObject;
    }

    @RequestMapping("/workbench/clue/saveUnbund.do")
    @ResponseBody
    public  Object saveUnbund(ClueActivityRelation clueActivityRelation){

        ReturnObject returnObject = new ReturnObject();
        try {
            //??????service???????????????????????????????????????????????????
            Integer result = clueActivityRelationService.deleteClueActivityRelationByClueIdActivityId(clueActivityRelation);
            if (result >  0){
                returnObject.setCode(Contants.code.SUCCESS);
            }else {
                returnObject.setCode(Contants.code.FAIL);
                returnObject.setMessage("????????????????????????");
            }
        } catch(Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.code.FAIL);
            returnObject.setMessage("????????????????????????");
        }

        return returnObject;
    }
}
