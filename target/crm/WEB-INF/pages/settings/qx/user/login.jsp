<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String basePath=request.getScheme()+"://"+request.getServerName()+":"
			+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<%--
   这个base标签的意思是：不管你下面的资源从哪里开始找，巴拉巴拉啥的，都要从我这个base标签后面的路径开始找！
   --%>
<head>
	<base href="<%=basePath%>" >
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
        //这个是jQuery的入口函数
		$(function () {
			//给整个浏览器窗口添加键盘按下事件
			$(window).keydown(function (e) {
				if (e.keyCode == 13) {
					$("#denglu").click();
				}
			});
			//给登录按钮绑定单击事件，当按钮发生点击的时候，自动执行后面的函数
			$("#denglu").click(function () {
				var zhanghao = $.trim($("#zhanghao").val());
				var mima = $.trim($("#mima").val());
				var st = $("#mdl").prop("checked");
				//表单验证
				if (zhanghao == "") {
					alert("您的账号不能位空");
					return;
				} else if (mima == "") {
					alert("您的密码不能为空");
					return;
				}
				//显示正在验证
				//$("#msg").text("正在验证，请稍后....");
				$.ajax({
					url: "settings/qx/user/login.do",
					data: {
						loginact: zhanghao,
						loginpwd: mima,
						isrempwd: st,
					},
					type: "post",
					dataType: "json",
					success: function (data) {
						if (data.code == "SUCCESS") {
							window.location.href = "workbench/index.do";
						} else {
							$("#msg").html(data.message);
						}
					},
					beforeSend: function () {  //当ajax向后台发送请求之前，会自动执行本函数
						//该函数的返回值能够觉得ajax是否真正的向后台发送请求
						//如果该函数返回true，则ajax会真正的向后台发送请求，
						// 否则，如果该函数返回false，，则ajax会放弃向后台发送请求
						//下面的这个内容，和上面做验证的内容是一样的，写在下面也是可以的，个人习惯不一样，所以习惯上面的方式，把下面先注释
						/*if ( zhanghao == ""){
							alert("您的账号不能位空");
							return   false;
						} else if (mima == "") {
							alert("您的密码不能为空");
							return   false;
						}*/
						$("#msg").text("正在验证，请稍后....");
						return true;
					}
				})
			})
		});
	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/半魅惑.jpg" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2019&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" id="zhanghao" value="${cookie.zhanghao.value}" placeholder="用户名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" id="mima" value="${cookie.mima.value}" placeholder="密码">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<label>
							<c:if test="${ not empty cookie.zhanghao and not empty cookie.mima}">
								<input id="mdl" type="checkbox" checked>
							</c:if>
							<c:if test="${  empty cookie.zhanghao or  empty cookie.mima}">
								<input id="mdl" type="checkbox" >
							</c:if>
							 十天内免登录
						</label>
						&nbsp;&nbsp;
						<font color="red"><span id="msg"></span></font>
					</div>
					<button type="button" class="btn btn-primary btn-lg btn-block" id="denglu" style="width: 350px; position: relative;top: 45px;">登你妈的录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>