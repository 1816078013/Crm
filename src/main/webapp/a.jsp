<%--
  Created by IntelliJ IDEA.
  User: 18160
  Date: 2022/4/28
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"
            +request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <%--下面的框架都是基于jQuery实现的，所以要先引入jQuery--%>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <%--光引入下面的插件是不够的，还要引入bootstrap框架，下面的插件都是基于bootstrap框架来实现的，不引入框架，下面的插件没法使用--%>
    <link rel="stylesheet" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css">
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <%--引入bootstrap插件--%>
    <link rel="stylesheet" href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <title>演示日历插件</title>
</head>
<script type="text/javascript">
    $(function (){
        //当容器加载之后，开始执行这段代码
        $("#myDate").datetimepicker({
            language:'zh-CN',   //语言
            format:"yyyy-MM-dd",  //日期的格式，将来希望你前端响应到后端的数据是什么格式的
            minView:'month',   //可以选择的最小视图
            initialDate:new Date(),  //初始化显示的日期
            autoclose:true,  //设置选择完日期或者时间之后，是否自动关闭日历
            todayBtn:true,  //设置是否显示“今天”这个按钮，true为显示，false为不显示
            clearBtn:true,   //设置清空，清空掉你所选择的所有日期信息
        });
    })
</script>
<body>
<input type="text" id="myDate" readonly >
<input type="button" id="btn" value="清空">
</body>
</html>
