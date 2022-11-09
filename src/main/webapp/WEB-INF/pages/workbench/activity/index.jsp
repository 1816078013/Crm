<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
   String basePath=request.getScheme()+"://"+request.getServerName()+":"
		   +request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<%--bs_pagination插件，分页插件--%>
<link rel="stylesheet" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css"/>
<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"/></script>
<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"/></script>
<script type="text/javascript">

	$(function(){
		//给创建按钮绑定单击事件
		$("#createActivityBtn").click(function () {
			//初始化工作
			$("#createActivityForm")[0].reset();
			//弹出创建市场活动的模态窗口
			$("#createActivityModal").modal("show");
			//$("input[name='abc']").xxx
			$(".mydate").datetimepicker({
				language:'zh-CN',   //语言
				format:"yyyy-MM-dd",  //日期的格式，将来希望你前端响应到后端的数据是什么格式的
				minView:'month',   //可以选择的最小视图
				initialDate:new Date(),  //初始化显示的日期
				autoclose:true, //设置选择完日期或者时间之后，是否自动关闭日历
				todayBtn:true,  //设置是否显示“今天”这个按钮，true为显示，false为不显示
				clearBtn:true,   //设置清空，清空掉你所选择的所有日期信息
			});
		})

		$("#SaveCreateActivityBtn").click(function (){
            var owner = $("#create-marketActivityOwner").val();
			alert("所有者是"+owner);
            var name = $.trim($("#create-marketActivityName").val());
            var startDate =  $("#create-startDate").val();
            var endDate = $("#create-endDate").val();
            var cost = $("#create-cost").val();
            var description = $.trim($("#create-description").val());
            //表单验证
            if (owner == ""){
                alert("抱歉，所有者不能为空");
                retrun;
            }
            if (name== ""){
                alert("抱歉，必须给市场活动取一个名字！！");
                retrun;
            }
            if (startDate != "" && endDate != ""){
				//在js当中，比较任何数据都可以用   ">"   "<"   "="  来进行，字符串也不例外，因为js是弱类型的语言
				if (startDate > endDate){
					alert("结束日期不能比开始日期大");
					return;
				}
            }
			//正则表达式，判断用户输入的金额数是否合法
			var regexp = /^(([1-9]\d*)|0)$/;
			if(!regexp.test(cost)){
				alert("成本只能是非负整数");
				return;
			}
			$.ajax({
				url:'workbench/activity/saveCreateActivity.do',
				data:{
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description,
				},
				type:'post',
				dataType:'json',
				success:function (data){
					if (data.code=="SUCCESS"){
						//关闭模态窗口
						$("#createActivityModal").modal("hide");
						//创建成功之后,关闭模态窗口,刷新市场活动列，显示第一页数据，保持每页显示条数不变
						queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption','option_name'));
					}else {
						//创建失败,提示信息创建失败,模态窗口不关闭,市场活动列表也不刷新
						alert(data.message);
						$("#createActivityModal").modal("hide");//这行代码其实可以不写，习惯使然罢了
					}
				}
			});
		});


        queryActivityByConditionForPage(1,10);


        //给"查询"按钮添加单击事件
        $("#search").click(function (){
            queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption','option_name'));
        })

        //给全选按钮添加单击事件
        $("#chckAll").click(function (){
            //如果“全选”按钮是选中状态，则列表中所有CheckBox都是选中状态
			/*if(this.checked == true){
				$("#tBody input[type='checkbox']").prop("checked",true);
			}else{
				$("#tBody input[type='checkbox']").prop("checked",false);
			}*/
			$("#tBody input[type='checkbox']").prop("checked",this.checked);
            //$("#tBody input[type='checkbox']"):这段代码的意思是，获取id为 “tBody”父标签下，所有的（子）input标签中，type类型为checkbox的标签
        });

		/*var obj = $("#tBody input[type='checkbox']");
		for(var i = 0; i = obj.length;i++){
			obj.length[i].click(function () {
				alert("1");
			});
		}*/
		/*$("#tBody input[type='checkbox']").click(function () {
			alert("1");
			//如果列表中所有的checkbox都是选中状态，那么全选按钮也要选中
			//$("#chckAll").prop("checkbox",false);

			var zong = $("#tBody input[type='checkbox']").size();
			var xuanzhong = $("#tBody input[type='checkbox']:checked").size();
			if ( zong == xuanzhong){
				$("#chckAll").prop("checked",true);
			} else {
				//如果列表中的checkbox哪怕有一个没选中，那么“全选”按钮也应该取消选中
				$("#chckAll").prop("checked",false);
			}
		});*/

		$("#tBody").on("click","input[type='checkbox']",function () {
			alert("4")
			var zong = $("#tBody input[type='checkbox']").size();
			var xuanzhong = $("#tBody input[type='checkbox']:checked").size();
			if (zong == xuanzhong){
				$("#chckAll").prop("checked",true);
			}else {
				$("#chckAll").prop("checked",false);
			}
		})
		//给删除按钮添加单击事件
		$("#deleteActivityBtn").click(function (){
			//获取参数
			//获取所有列表中被选中chcekbox
			var checkAll = $("#tBody input[type='checkbox']:checked")
			if(checkAll.size() == 0){
				alert("至少选择一个");
				return;
			}
            alert("------------>"+checkAll.val())
			if(window.confirm("确定要删除吗？")){
				var ids = "";
				$.each(checkAll,function (index,obj){
					alert("值是："+obj.value);
					ids +="id="+obj.value+"&";
				})
				ids =  ids.substr(0,ids.length-1);
				alert("拼接好的数组："+ids)
				$.ajax({
					url:"workbench/activity/deleteActivityIds.do",
					data:ids, //如果你这里的data数据被封装成了一个数组对象的话，那么你controller层里对于接受的变量，一定是这个数组中的某一个属性
					type:"post",
					dataType:"json",
					success:function (data) {
						if (data.code == "SUCCESS"){
							//刷新市场活动列表，显示第一页数据，保持每页显示条数不变
							queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption','option_name'))
						}
						else {
							alert(data.message);
						}
					}
				})
			}
		});
        
        //给修改按钮添加单击事件
        $("#editActivityBtn").click(function () {
            //手收集参数
            //获取列表中选中的chckbox
            var checkIds = $("#tBody input[type='checkbox']:checked");
            if (checkIds.size() == 0){
                alert("至少选择一个吧，亲");
                return;
            }else if (checkIds.size() > 1){
                alert("只能选择一个哦");
                return;
            }
            var id = checkIds.val();
            alert("值是："+checkIds.val())
            /*var id = checkIds.val();
            var id = checkIds.get(0).value;   <------------这三种方式都可以获取value属性值
            var id = checkIds[0].value;*/
          /*  $.each(checkIds,function (index,obj){
                alert("另一种方式获取的值："+obj.value); ，<-------这种可以循环数组，获取其中dom对象的值
            })*/
           /* $.each(checkIds,function (index,obj){
                var jqr = $(obj);
                alert("另一种方式："+jqr.val());
            })*/
            //发送请求
            $.ajax({
                url:'workbench/activity/queryActivityById.do',
                type:'post',
                dataType:'json',
                data:{
                    id:id
                },

                success:function (data) {
                    //吧查出来的数据，渲染到各个模块中
                    $("#edit-id").val(data.id);
                    $("#edit-marketActivityOwner").val(data.owner);
                    $("#edit-marketActivityName").val(data.name);
                    $("#edit-startTime").val(data.startDate);
                    $("#edit-endTime").val(data.endDate);
                    $("#edit-cost").val(data.cost);
                    $("#edit-description").val(data.description);
                    //弹出模态窗口
                    $("#editActivityModal").modal("show");
					$("#gengxin").click(function (){
						var id = $("#edit-id").val();
						//alert("id是："+id);
						var owner = $("#edit-marketActivityOwner").val();
						//alert("所有者："+owner)
						var name = $("#edit-marketActivityName").val();
						//alert("名称"+name);
						var startDate = $("#edit-startTime").val();
						//alert("开始时间"+startDate);
						var endDate = $("#edit-endTime").val();
						//alert("结束时间"+endDate);
						var cost = $("#edit-cost").val();
						//alert("成本"+cost);
						var description = $("#edit-description").val();
						//alert("描述"+description);
						$.ajax({
							url:'workbench/activity/updateActivityById.do',
							type:'post',
							dataType:"json",
							data:{
								id:id,
								owner:owner,
								name:name,
								startDate:startDate,
								endDate:endDate,
								cost:cost,
								description:description
							},
							success:function (data1) {
								if (data1.code == "SUCCESS"){
									alert(data1.code);
									alert("更新成功");
								}else {
									alert(data1.message);
									return;
								};
								queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption','option_name'));
							},
						});
					})
                }
            });
        });
		//给下载按钮添加单击事件
		$("#exportActivityAllBtn").click(function (){
			/*alert("22");*/
			window.location.href="workbench/activity/exportAllActivitys.do";
		})

		//给导入市场活动的导入按钮添加单击事件
		$("#importActivityBtn").click(function () {
			//收集参数,这里的参数是一个文件
			var activityFileName = $("#activityFile").val();
			var suffix = activityFileName.substr(activityFileName.lastIndexOf(".")+1).toLocaleLowerCase();
			if (suffix != "xls"){
				alert("只支持excel文件");
				return;
			}
			//下面这个吧jQuery对象转换成dom对象后，dom对象有个属性，files，这个属性里面就保存了你传上来文件值
			var activityFile = $("#activityFile")[0].files[0];
			if (activityFile.size>1024*1024*5){
				alert("文件格式过大，请重新选择");
				return;
			}


			//FORMDATA是ajax给我们提供的接口，可以模拟键值对后台提交参数
			//FORMDATA最大的优势是，不仅能提交文本数据，还能提交二进制数据
			var formdata = new FormData();
			formdata.append("myFile",activityFile);
			formdata.append("username","王爱笑");
			//发送请求
			$.ajax({
				url:"workbench/activity/importActivity.do",
				data:formdata,
				processData:false,//设置ajax想后台提交参数的时候，是否把参数统一转换成字符串，默认是true，true--是，false--不是
				contentType:false,//设置ajax想后台提交参数的时候，是否把参数统一按urlencoded编码，默认是true，true--是，false--不是
				type:"post",
				dataType:'json',
				success:function (data) {
					if (data.code=="SUCCESS"){
						alert("导入成功"+data.retDate+"条记录");
						$("#importActivityModal").modal("hide");
						queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption','option_name'));
					}else {
						alert(data.message);
					}
				}
			})
		})
	});
	function queryActivityByConditionForPage(pageNo,pageSize){
        //收集参数
        var name = $("#query-name").val();
        var owner = $("#query-owner").val();
        var startDate = $("#query_startDate").val();
        var endDate = $("#query_endDate").val();
        //var pageNo = pageNo;
        //var pageSize = pageSize;
        //发送请求
        $.ajax({
            url:"workbench/activity/queryActivityByConditionForPage.do",
            data: {
                name:name,
                owner:owner,
                startDate:startDate,
                endDate:endDate,
                pageNo:pageNo,
                pageSize:pageSize
            },
            dataType: "json",
            type: "post",
            success:function (data){
                //显示市场活动的总条数
                //$("#totalRowsB").text(data.result);
                //显示市场活动的列表
                var htmlStr = "";
                //便利activitylist，拼接所有行数据
                $.each(data.activityList,function (indedx,obj){
                    htmlStr += "<tr class=\"+active+\">";
                    /*htmlStr += "<td><input type=\"checkbox\" value=\""+obj.id+"\"/></td>";*/
                    htmlStr+="<td><input type=\"checkbox\" value=\""+obj.id+"\"/></td>";
                    htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id="+obj.id+"'\">"+obj.name+"</a></td>";
                    htmlStr+="<td>"+obj.owner+"</td>";
                    htmlStr+="<td>"+obj.startDate+"</td>";
                    htmlStr+="<td>"+obj.endDate+"</td>";
                    htmlStr+="</tr>";
                });
                $("#tBody").html(htmlStr);

				$("#chckAll").prop("checked",false);

				//计算总页数
				var totalPages;
				if (data.result%pageSize==0){
					totalPages = data.result/pageSize;
				}else if(data.result%pageSize != 0){            //parseInt()  js函数中，把一个数字的小数部分去掉，保留整数部分的函数
					totalPages = parseInt(data.result/pageSize+1);
				}

                //对容器调用bs_pagenation工具函数，显示翻页信息
                $("#demo_pag1").bs_pagination({
                    currentPage:pageNo,               //当前页的页号   相当于pageNo
                    rowsPerPage:pageSize,             //每页显示的记录数   相当于pageSize
                    totalRows:data.result,              //总记录条数
                    totalPages:totalPages,              //总页数，必填参数

                    visiblePageLinks:10,         //最多可以显示的卡片数量

                    showGoToPage: true,         //是否显示“跳转到"多少页部分，true为显示，false为不显示
                    showRowsPerPage: true,       //是否显示“每页显示多少条数据” 部分，true为显示，false为不显示
                    showRowsInfo: true,          //是否显示记录的信息，true为显示，false为不显示


                    onChangePage:function (event,pageobj){    //用户每次切换页号的时候，都会自动的执行这个函数
                        //js代码....              //每次返回切换页号之后的pageNo和pageSize
                        //alert("当前页："+pageobj.currentPage);
                        //alert("每页的记录条数："+pageobj.rowsPerPage);
						queryActivityByConditionForPage(pageobj.currentPage,pageobj.rowsPerPage);
                    }
                });
            }
        })
    }
</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="createActivityForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								 <c:forEach items="${user}" var="u">
									 <option value="${u.id}">${u.name}</option>
								 </c:forEach>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" name="abc" id="create-startDate" readonly>
							</div>
							<label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" name="abc" id="create-endDate" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="SaveCreateActivityBtn" >保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					 <input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${user}" var="u">
										<option value="${u.id}"> ${u.name} </option>
									</c:forEach>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startTime" value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endTime" value="2020-10-20">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="gengxin" data-dismiss="modal">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="query-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="query_startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="query_endDate">
				    </div>
				  </div>
				  
				  <button type="button"  id="search" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createActivityBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editActivityBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteActivityBtn" ><span class="glyphicon glyphicon-minus" ></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input id="chckAll"  type="checkbox" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="tBody">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
                <div id="demo_pag1">

				</div>
			</div>
			<%--<div style="height: 50px; position: relative;top: 30px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b id="totalRowsB">50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>
			</div>--%>
			
		</div>
		
	</div>
</body>
</html>