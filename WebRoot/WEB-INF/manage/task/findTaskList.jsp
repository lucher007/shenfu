<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<title></title>
<link type="text/css" rel="stylesheet" href="style/user.css">
<link rel="stylesheet" type="text/css" href="style/easyui/easyui.css">
<link rel="stylesheet" type="text/css" href="main/plugin/easyui/themes/icon.css">
<style type="text/css">
	#body table tr {
      height: 30px;
    }
</style>

</head>

<body>
	<div id="body">
    <div class="seh-box">
        <form action="" method="post" id="searchForm" name="searchForm">
            <table width="100%">
		    	<tr>
		    		<td align="right" width="10%">客户姓名：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="username" id="username" value="${usertask.username }">
		    		</td>
		    		<td align="right" width="10%">电话号码：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${usertask.phone }">
		    		</td>
		    		<td align="right" width="10%">任务单号：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="taskcode" id="taskcode" value="${usertask.taskcode }">
		    		</td>
		    		<td  align="center">
		    			<a href="javascript:queryTask();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">来源：</td>
        			<td width="20%">
        				<select id="source" name="source" class="select" onchange="queryTask();">
			                   <option value=""  <c:if test="${usertask.status == ''}">selected</c:if>>请选择</option>
			                   <option value="0" <c:if test="${usertask.status == '0' }">selected</c:if>>销售</option>
			                   <option value="1" <c:if test="${usertask.status == '1' }">selected</c:if>>400客服</option>
			                   <option value="2" <c:if test="${usertask.status == '2' }">selected</c:if>>小区驻点</option>
			             </select>
        			</td>
		    		<td align="right">状态：</td>
        			<td>
        				<select id="status" name="status" class="select" onchange="queryTask();">
			                   <option value=""  <c:if test="${usertask.status == ''}">selected</c:if>>请选择</option>
			                   <option value="0" <c:if test="${usertask.status == '0' }">selected</c:if>>未处理</option>
			                   <option value="1" <c:if test="${user.status == '1' }">selected</c:if>>已处理</option>
			             </select>
        			</td>
        			<td align="right" width="10%">上门类型：</td>
		    		<td width="20%">
		    			<select id="visittype" name="visittype" class="select" onchange="queryTask();">
		    				<option value=""  <c:if test="${user.visittype == ''}">selected</c:if>>请选择</option>
							<option value="0" <c:if test="${user.visittype == '0'}">selected</c:if>>公司派人讲解测量</option>
							<option value="1" <c:if test="${user.visittype == '1'}">selected</c:if>>亲自讲解测量</option>
							<option value="2" <c:if test="${user.visittype == '2'}">selected</c:if>>已讲解，公司派人测量</option>
						</select>
		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
   	<div id = "taskDG" style="width:auto; height: auto;"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
		    <!-- 
			<a href="javascript:addTask('add');" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			 -->
			<a href="javascript:updateTask();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteTask();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
			<a href="javascript:addTask('batchAdd');" class="easyui-linkbutton" iconCls="icon-add"  plain="true">公司派人讲解</a>
			<!--
			<a href="javascript:addTask('autoBatchAdd');" class="easyui-linkbutton" iconCls="icon-add"  plain="true">亲自讲解测量</a>
			-->
			<a href="javascript:addTask('batchAddToInstaller');" class="easyui-linkbutton" iconCls="icon-add"  plain="true">已讲解，公司派人测量</a>
		</div>
	</div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initTaskDatagrid(){
         $('#taskDG').datagrid({
              title: '任务单列表信息',
              queryParams: paramsJson(),
              url:'task/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              //fitColumns:true,
              toolbar:'#tools',
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              frozenColumns:[[
                              { field: 'taskcode', title: '任务单号',width:150,align:"center",sortable:"true"},
                              { field: 'username', title: '客户姓名',width:150,align:"center",},
                              { field: 'phone', title: '联系电话',width:150,align:"center",sortable:"true"},   
                              { field: 'statusname', title: '状态',width:150,align:"center",}
                         ]],
              columns: [[
						  { field: 'visittime', title: '上门时间',width:180,align:"center",},
						  { field: 'dealdate', title: '处理时间',width:180,align:"center",},
						  { field: 'tasktypename', title: '上门类型',width:180,align:"center",},
						  { field: 'talkername', title: '讲解员姓名',width:180,align:"center",},
						  { field: 'visitorname', title: '测量人姓名',width:150,align:"center",},
		                  { field: 'sourcename', title: '客户来源',width:150,align:"center",},
		                  { field: 'visittypename', title: '讲解类型',width:150,align:"center",},
		                  { field: 'ordercode', title: '订单编号',width:150,align:"center",},
		                  { field: 'address', title: '安装地址',width:350,align:"center",
			                	    formatter: function (value) {
					                    return "<span title='" + value + "'>" + value + "</span>";
					                },
		                  },
		                  { field: 'addtime', title: '添加时间',width:150,align:"center",sortable:"true",sortable:"true"},
		                  { field: 'salercode', title: '销售人工号',width:150,align:"center",},
		                  { field: 'salername', title: '销售人姓名',width:150,align:"center",},
		              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
 			username:$("#username").val(),
	 		phone:$("#phone").val(),
	 		taskcode:$("#taskcode").val(),
	 		//address:$("#address").val(),
	 		status:$("#status").val(),
	 		source:$("#source").val(),
	 		visittype:$("#visittype").val(),
	 	};
	 	return json;
	 }
	
	function queryTask() {
		$('#taskDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateTask() {
		
		var selected = $('#taskDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
        //获取状态
        var status = selected.status;
        if(status != '0'){
        	$.dialog.tips('此任务已经处理，不能修改', 2, 'alert.gif');
		    return;
        }
        
	    dialog = $.dialog({
		    title: '任务单修改',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:task/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addTask(jumprul) {	
	    dialog = $.dialog({
		    title: '任务单添加',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: true,
		    resize: false,
		    max: true,
		    min: true,
		    content: 'url:task/addInit?rid='+Math.random()+'&jumpurl='+jumprul
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#taskDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteTask(){
		
		var selected = $('#taskDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
        
        //获取需要修改项的ID
        var id = selected.id;
        //获取状态
        var status = selected.status;
        if(status != '0'){
        	$.dialog.tips('此任务已经处理，不能删除', 2, 'alert.gif');
		    return;
        }
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="task/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 2, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#taskDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化任务单列表
	   initTaskDatagrid();
		
       var returninfo = '${task.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryTask();
	    }  
	});
</script>
</body>
</html>