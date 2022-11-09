<%--
  Created by IntelliJ IDEA.
  User: 18160
  Date: 2022/5/1
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"
            +request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <%--jquery的库--%>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"> </script>
    <%--bootstrap框架--%>
    <link rel="stylesheet" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css"/>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"/></script>
    <%--bs_pagination插件，分页插件--%>
    <link rel="stylesheet" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css" />
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"/></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"/></script>
    <title>演示boot_strap  中  bs_pagintion插件的使用</title>
    
    <script type="text/javascript">
        $(function () {
            $("#demo_pag1").bs_pagination({
                currentPage:1,               //当前页的页号   相当于pageNo
                rowsPerPage:10,             //每页显示的记录数   相当于pageSize
                totalRows:1000,              //总记录条数
                totalPages:100,              //总页数，必填参数

                visiblePageLinks:10,         //最多可以显示的卡片数量

                showGoToPage: true,         //是否显示“跳转到"多少页部分，true为显示，false为不显示
                showRowsPerPage: true,       //是否显示“每页显示多少条数据” 部分，true为显示，false为不显示
                showRowsInfo: true,          //是否显示记录的信息，true为显示，false为不显示


                onChangePage:function (event,pageobj){    //用户每次切换页号的时候，都会自动的执行这个函数
                    //js代码....              //每次返回切换页号之后的pageNo和pageSize
                    alert("当前页："+pageobj.currentPage);
                    alert("每页的记录条数："+pageobj.rowsPerPage);
                }
            });
        })
    </script>
</head>
<body>
         <div id="demo_pag1">
             
         </div>
</body>
</html>
