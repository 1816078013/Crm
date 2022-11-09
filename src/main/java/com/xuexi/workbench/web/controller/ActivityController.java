package com.xuexi.workbench.web.controller;

import com.xuexi.commons.contants.Contants;
import com.xuexi.commons.domain.ReturnObject;
import com.xuexi.commons.domain.a;
import com.xuexi.commons.utils.DateUtils;
import com.xuexi.commons.utils.HSSFUtils;
import com.xuexi.commons.utils.UUID;
import com.xuexi.settings.domain.User;
import com.xuexi.settings.service.UserService;
import com.xuexi.workbench.domain.Activity;
import com.xuexi.workbench.domain.ActivityRemark;
import com.xuexi.workbench.service.ActivityRemarkService;
import com.xuexi.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

import static com.xuexi.commons.contants.Contants.SESSION_USER_KEY;

@Controller
public class ActivityController {
    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String  index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //调用service层方法，来查询所有的用户
        List<User> users = userService.QueryAllUser();
        //把数据保存到request作用域当中
        request.setAttribute("user",users);
        //请求转发
        //这个下面的写法，是我自己慢慢摸索出来的
        request.getRequestDispatcher("/WEB-INF/pages/workbench/activity/index.jsp").forward(request,response);
        return  "";
        //这个是老师的写法
        //return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    @ResponseBody
    public Object saveCreateActivity(Activity activity, HttpSession session){
        //对前台传过来的参数进行二次封装，因为创建时间，创建的用户和ID这三个字段是前台没有的，我们需要自己封装到实体类当中
        activity.setId(UUID.getuuid());
        activity.setCreateTime(DateUtils.formateDateTime(new Date()));
        User user = (User) session.getAttribute(SESSION_USER_KEY);
        String userid  = user.getId();
        activity.setCreateBy(userid);

        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service方法，保存创建的市场活动
            Integer results = activityService.saveCreateActivity(activity);
          if (results == 1 ){
              returnObject.setCode(Contants.code.SUCCESS);
          }else {
              returnObject.setCode(Contants.code.FAIL);
              returnObject.setMessage("系统繁忙，请稍后重试...");
          }
        } catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.code.FAIL);
            returnObject.setMessage("后台出现错误...请等待");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    @ResponseBody
    public Object queryActivityByConditionForPage(String name,String owner,String startDate,
                                                  String endDate,Integer pageNo,Integer pageSize){
        //封装参数
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);

        //调用service层的方法，查询两个数据
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        Integer result  = activityService.queryCountOfActivityByCondition(map);
        //根据查询结果，生成响应信息
        a a1 = new a();
        a1.setActivityList(activityList);
        a1.setResult(result);
        return a1;
       /* Map<String,Object> retmap = new HashMap<>();
        retmap.put("activityList",activityList);
        retmap.put("result",result);
        return retmap;*/
    }

    @RequestMapping("/workbench/activity/deleteActivityIds.do")
    @ResponseBody
    public Object  deleteActivityIds(String[] id){//由于前将数据拼接成了一个数组，所以你这里获取的一定是数组中某个属性的具体值
                                                  // 前端：ids = {id=xxx&id=xxx&id=xxx&},所以你这里就是里面的具体的值：id了
        System.out.println("id========>"+id);
        //调用service层的方法，来删除数据
        //但是有可能会爆异常，所以我们这里用try    。。。catch  卡住
        ReturnObject returnObject = new ReturnObject();
        try {
            Integer numbers = activityService.deleteActivityByIds(id);
            if (numbers > 0){
                returnObject.setCode(Contants.code.SUCCESS);
            }else {
                returnObject.setCode(Contants.code.FAIL);
                returnObject.setMessage("后台程序出现异常...请等待处理....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.code.FAIL);
            returnObject.setMessage("后台程序出现异常...请等待处理....");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    @ResponseBody
    public Object queryActivityById(String id){
        //调用service层方法，查询市场活动
        Activity activity = activityService.queryActivityById(id);
        //根据查询街而过，返回响应信息
        return activity;
    }

    @RequestMapping("/workbench/activity/updateActivityById.do")
    @ResponseBody
    public Object updateActivityById(HttpServletRequest httpServletRequest,HttpSession session){
        User user = (User) session.getAttribute(SESSION_USER_KEY);
        String id = httpServletRequest.getParameter("id");
        System.out.println("id是：----------------------------->"+id);
        String owner = httpServletRequest.getParameter("owner");
        String name = httpServletRequest.getParameter("name");
        String startDate = httpServletRequest.getParameter("startDate");
        String endDate = httpServletRequest.getParameter("endDate");
        String cost = httpServletRequest.getParameter("cost");
        String description = httpServletRequest.getParameter("description");
        String editby = user.getName();
        String edittime = DateUtils.formateDateTime(new Date());
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("owner",owner);
        map.put("name",name);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("cost",cost);
        map.put("description",description);
        map.put("editby",editby);
        map.put("edittime",edittime);
        Integer result = activityService.updateActivityById(map);
        ReturnObject returnObject = new ReturnObject();
        if (result ==1){
            returnObject.setCode(Contants.code.SUCCESS);
        }else {
            returnObject.setCode(Contants.code.FAIL);
            returnObject.setMessage("对不起，后台繁忙，轻稍后重新尝试");
        }
        System.out.println("----------->"+returnObject.getCode());
        return returnObject;
    }

    @RequestMapping("/workbench/activity/fileDowLoad.do")
    @ResponseBody
    public void filedowload(HttpServletResponse httpServletResponse) throws IOException {
        //设置响应类型
        httpServletResponse.setContentType("application/octet-stream;charset=UTF-8");
        //获取输出流
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();


        //浏览器接受到响应信息之后，默认情况下，直接在显示窗口中打开响应信息，即使打不开，也会调用应用程序打开，只有实在打不开，才会激活文件下载
        //可以设置响应头信息，使浏览器接收到响应信息之后，直接激活下载窗口，即使能打开也不去打开他
        httpServletResponse.addHeader("Content-Disposition", "attachment;filename=studentList.xls");

        //先读取excel文件（InputStream），在输出到浏览器（OutPutStream）
        FileInputStream fileInputStream = new FileInputStream("G:\\CRM客户关系管理系统SSM版\\serverDir\\studentList.xls");
        byte[] b = new byte[256];
        int length = 0;
        while ((length = fileInputStream.read(b))!= -1){
          /*  fileInputStream.read(b);*/
            servletOutputStream.write(b,0,length);
        }
        fileInputStream.close();
        servletOutputStream.flush();
        servletOutputStream.close();
    }


    @RequestMapping("/workbench/activity/exportAllActivitys.do")
    public void exportAllActivitys(HttpServletResponse httpServletResponse) throws Exception{

        //调用service层的方法，去查询所有的市场活动
        List<Activity> activityList = activityService.queryAllActivitys();

        //创建excel文件，并且吧activityList写入到excel文件中
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet("市场活动列表");
        HSSFRow row = sheet.createRow(0);//行号，从0开始，依次增加
        //使用row创建HSSFCell对象，对应row中的列
        HSSFCell cell = row.createCell(0);//列的编号，从0开始，依次增加
        //当文件创建好了，页页创建好了，行和列都准备好了，就可以往里面写数据了
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("成本");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");

        Activity activity = null;
        if (activityList.size()>0 && activityList!= null) {
            for (int i = 0; i < activityList.size(); i++) {
                activity = activityList.get(i);

                //每遍历出一个activity，就射鞥成一个新的行
                row = sheet.createRow(i + 1);
                //每遍历出一个activity，就创建出11个列，用来装里面的数据
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }

        //根据workbook对象，生成excel文件
        /*OutputStream outputStream = new FileOutputStream("G:\\CRM客户关系管理系统SSM版\\serverDir\\activitys.xls");
        hssfWorkbook.write(outputStream);*/
        //关闭资源
       /* outputStream.flush();
        outputStream.close();*/
       /* hssfWorkbook.close();//到目前为止，文件只是被阿帕奇的poi插件生成了一个excel文件，还没有下载，所以接下来还要下载*/

        //把生成的excel文件下载到用户的电脑
        httpServletResponse.setContentType("application/octet-stream;charset=utf-8");
        httpServletResponse.addHeader("Content-Disposition", "attachment;filename=activitys.xls");
        OutputStream outputStream1 = httpServletResponse.getOutputStream();
        /*FileInputStream fileInputStream = new FileInputStream("G:\\CRM客户关系管理系统SSM版\\serverDir\\activitys.xls");
        byte[] buff = new byte[256];
        int length = 0;
        while ((length = fileInputStream.read(buff)) != -1){
            outputStream1.write(buff,0,length);
        }*/
        /*fileInputStream.close();*/

        /**
         * 上面注释掉的代码经过了改造，取消了从内存中生成一个excel表格到磁盘，还取消了从磁盘读数据到内存中
         * 恰巧，从内存到磁盘，再从磁盘到内存都是非常消耗时间的，所以就取消了这一步的操作，直接从内存中拿数据给另一个内存
         * 而且，巧了，  HSSFWorkbook这个类中，就有一个方法是用来在内存中传输数据的  write();
         */
        hssfWorkbook.write(outputStream1);
        outputStream1.flush();
        outputStream1.close();
    }

    /**
     * //MultipartFile:SpringMVC中提供的，专门接受文件的参数，当有文件上传的时候，会自动封装到这个参数当中
     * @param username
     * @param MyFile
     * @return
     */
    @RequestMapping("/workbench/activity/fileUpload.do")
    @ResponseBody
    public Object fileUpload(String username, MultipartFile MyFile) throws Exception {
        //吧文本数据打印到控制台
        System.out.println("username:"+username);
        //把文件在服务器指定的目录中，生成一个同样的文件
        //G:\CRM客户关系管理系统SSM版\serverDir,这个路径必须创建好
        String name = MyFile.getOriginalFilename();
        File file = new File("G:\\CRM客户关系管理系统SSM版\\serverDir\\"+name);
        MyFile.transferTo(file);

        ReturnObject returnObject = new ReturnObject();
        returnObject.setMessage("上传成功");
        returnObject.setCode(Contants.code.SUCCESS);
        return returnObject;
    }

    @RequestMapping("/workbench/activity/importActivity.do")
    @ResponseBody
    public Object importActivity(MultipartFile myFile,HttpSession session){
        System.out.println("controller进来了");
        ReturnObject returnObject = new ReturnObject();
        Integer result = null;
        try {
            //把接受到的excel文件，写入到磁盘中
            /*String name = myFile.getOriginalFilename();
            File file = new File("G:\\CRM客户关系管理系统SSM版\\serverDir\\"+name);
            myFile.transferTo(file);*/

            //解析excel文件，获取文件中的数据，并封装成activityList
            //InputStream inputStream = new FileInputStream("G:\\CRM客户关系管理系统SSM版\\serverDir\\"+name);
            InputStream inputStream = myFile.getInputStream();
            HSSFWorkbook wb = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            User user = (User) session.getAttribute(SESSION_USER_KEY);
            List<Activity> activityList = new ArrayList<>();
            for (int i = 1;i<=sheet.getLastRowNum();i++) {
                row = sheet.getRow(i);

                activity = new Activity();
                activity.setId(UUID.getuuid());
                activity.setOwner(user.getId());
                activity.setCreateTime(DateUtils.formateDateTime(new Date()));
                activity.setCreateBy(user.getId());

                for (int s = 0; s < row.getLastCellNum(); s++) {
                    cell = row.getCell(s);

                    String cellvalue = HSSFUtils.getCellValueStr(cell);
                    if (s == 0){
                        activity.setName(cellvalue);
                    }else if (s == 1){
                        activity.setStartDate(cellvalue);
                    }else if (s == 2){
                        activity.setEndDate(cellvalue);
                    }else if (s == 3){
                        activity.setCost(cellvalue);
                    }else if (s == 4){
                        activity.setDescription(cellvalue);
                    }
                }
                //没循环一次，就把一个activity对象放到List集合中
                activityList.add(activity);
            }
             result = activityService.saveCreateByList(activityList);
        } catch (IOException e) {
            e.printStackTrace();
            returnObject.setCode(Contants.code.FAIL);
            returnObject.setMessage("对不起，导入失败");
        }
        returnObject.setCode(Contants.code.SUCCESS);
        returnObject.setMessage("导入成功");
        System.out.println("改动了"+result+"条记录");
        returnObject.setRetDate(result);
        System.out.println("controller出去");
        return returnObject;
    }

    @RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id,HttpServletRequest httpServletRequest){
        System.out.println("id是------------->"+id);
        Activity activity = activityService.queryActivityForDetailById(id);
        List<ActivityRemark> activityRemarkList = activityRemarkService.qureyActivityRemarkForDetailByActivityId(id);
        for (int i = 0;i <activityRemarkList.size();i++){
            System.out.println("数据是-------------》"+activityRemarkList);
        }
        httpServletRequest.setAttribute("activity",activity);
        httpServletRequest.setAttribute("activityRemarkList",activityRemarkList);
        System.out.println("点击后走没走");


        return "workbench/activity/detail";
    }
}