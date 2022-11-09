<%--
  Created by IntelliJ IDEA.
  User: 18160
  Date: 2022/5/16
  Time: 22:21
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
    <title>演示文件上传</title>
</head>
<body>
<%--文件上传的表单，必须要满足三个条件：
      1，表单组件标签只能用：<input type="file">   只能用这个
            以前学到的表单标签有 <input  type="  button|submit|text|radio|chekbox|password">   <seelect> 等
             <input type="text|password|radio|checkbox|hidden|button|submit|reset|file">
                      <select>,<textarea>

      2.请求方式只能是post：
          get：参数通过请求头提交到后塔台，参数放在URL后面，用？和&符号隔开，
          并且只能向后台提交文本数据，对参数长度有限制，参数卸载地址栏后面，数据安全有风险，但是效率高
          post：参数放在请求体中提交到后台，技能提交文件数据，又能提交二进制数据，
          理论上对参数长度有没有限制，并且参数放在请求体中，没有数据安全的风险，但是效率相对较低


      3.表单的编码格式只能用：multipart/form-data
      根据HTTP协议的规定，浏览器每次向后台提交数据，都会对参数进行统一编码，默认采用的格式是urlencodeed，这种编码格式只能对文本数据进行解析，
      例如字符串，布尔，数值等类型的数据
      浏览器每次向后台提交数据，都会首先把所有的参数转换成字符串，然后统一对这些数据进行urlencoded编码；
      文件上传的表单，他的编码格式只能用multipart/form-data，设置编码格式：enctype="multipart/form-data"
      --%>
<form action="workbench/activity/fileUpload.do" method="post"  enctype="multipart/form-data">
    <input type="file" name="MyFile"   > <br>
    <input type="text" name="usernName" > <br>
    <input type="submit" value="上传">
</form>
</body>
</html>
