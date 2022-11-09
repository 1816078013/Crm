<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"
            +request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <title>演示文件下载</title>
<script type="text/javascript">
    $(function (){
        //给下载按钮添加单击事件
        //所有文件下载的请求，只能发同步的
        $("#FileDowloadBtn").click(function () {
            window.location.href="workbench/activity/fileDowLoad.do";
        })
    })
</script>
</head>
<body>
<input type="button" value="下载" id="FileDowloadBtn" />
</body>
</html>
