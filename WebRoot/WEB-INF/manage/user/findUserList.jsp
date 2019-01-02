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
		    		<td width="10%">
		    			<input type="text"  class="inp w200" name="username" id="username" value="${user.username }">
		    		</td>
		    		<td align="right" width="10%">电话号码：</td>
		    		<td width="10%">
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${user.phone }">
		    		</td>
		    		<td align="right" width="10%">来源：</td>
		    		<td width="10%">
		    			<select id="source" name="source" class="select" onchange="queryUser();">
		    				<option value=""  <c:if test="${user.source == ''}">selected</c:if>>请选择</option>
							<option value="0" <c:if test="${user.source == '0'}">selected</c:if>>销售APP</option>
							<option value="1" <c:if test="${user.source == '1'}">selected</c:if>>400客服</option>
							<option value="2" <c:if test="${user.source == '2'}">selected</c:if>>小区驻点</option>
						</select>
		    		</td>
		    		<td align="right" width="10%">上门类型：</td>
		    		<td width="10%">
		    			<select id="visittype" name="visittype" class="select" onchange="queryUser();">
		    				<option value=""  <c:if test="${user.visittype == ''}">selected</c:if>>请选择</option>
							<option value="0" <c:if test="${user.visittype == '0'}">selected</c:if>>公司派人讲解测量</option>
							<option value="1" <c:if test="${user.visittype == '1'}">selected</c:if>>亲自讲解测量</option>
							<option value="2" <c:if test="${user.visittype == '2'}">selected</c:if>>已讲解，公司派人测量</option>
						</select>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">安装地址：</td>
		    		<td width="10%">
		    			<input type="text"  class="inp w200" name="address" id="address" value="${user.address }">
		    		</td>
		    		<td align="right">状态：</td>
        			<td >
        				<select id="status" name="status" class="select" onchange="queryUser();">
			                   <option value=""  <c:if test="${user.status == ''}">selected</c:if>>请选择</option>
			                   <option value="0" <c:if test="${user.status == '0' }">selected</c:if>>未处理</option>
			                   <option value="1" <c:if test="${user.status == '1' }">selected</c:if>>安装进行中</option>
			                   <option value="2" <c:if test="${user.status == '2' }">selected</c:if>>已安装完成</option>
			             </select>
        			</td>
					<td align="right">销售员姓名：</td>
					<td colspan="2">
						<input type="hidden" class="inp w200" name="salercode" id="salercode" readonly="readonly" style="background:#eeeeee;" value="${user.salercode }">
						<input type="text" class="inp w200" name="salername" id="salername" readonly="readonly" style="background:#eeeeee;" value="${user.salername }">
						<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						<a href="javascript:cleanEmployee();" class="easyui-linkbutton" iconCls="icon-undo" style="width:80px">清除</a>
					</td>
					<td align="center">
		    			<a href="javascript:queryUser();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
   	<div id = "userDG" style="width:auto; height: 450px"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addUser();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateUser();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteUser();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
			<a href="javascript:addUserorder();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加订单</a>
			<!--
			<a href="javascript:lookUserdoor();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看门锁图片</a>
			  -->
		</div>
	</div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initUserDatagrid(){
         $('#userDG').datagrid({
              title: '列表信息',
              queryParams: paramsJson(),
              url:'user/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              //fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              toolbar:'#tools',
              frozenColumns:[[
      	                    { field: 'usercode', title: '客户编号',width:150,align:"center"},
      	                    { field: 'username', title: '客户姓名',width:150,align:"center"},
      	                    { field: 'phone', title: '联系电话',width:100,align:"center"},       
                       ]],
                    columns: [[
      	                    { field: 'visittypename', title: '上门类型',width:200,align:"center"},
      		                { field: 'sourcename', title: '客户来源',width:150,align:"center"},
      		                { field: 'salercode', title: '销售员编号',width:150,align:"center"},
      		                { field: 'salername', title: '销售员姓名',width:150,align:"center"},
      		                { field: 'salerphone', title: '销售员电话',width:150,align:"center"},
      		                { field: 'statusname', title: '状态',width:150,align:"center"},
      		                { field: 'addtime', title: '添加时间',width:150,align:"center"},
      		                { field: 'address', title: '安装地址',width:300,align:"center",
                        			formatter: function (value) {
	      					        	return "<span title='" + value + "'>" + value + "</span>";
	      					        },
                          	},
                          	{ field: 'dealresult', title: '备注信息',width:300,align:"center",
                    			formatter: function (value) {
      					        	return "<span title='" + value + "'>" + value + "</span>";
      					        },
                      		},
                    	]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		username:$("#username").val(),
	 		phone:$("#phone").val(),
	 		source:$("#source").val(),
	 		visittype:$("#visittype").val(),
	 		address:$("#address").val(),
	 		status:$("#status").val(),
	 		salercode:$("#salercode").val(),
	 	};
	 	return json;
	 }
	
	function queryUser() {
		$('#userDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateUser() {
		
		var selected = $('#userDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
        var url = 'user/updateInit?rid='+ Math.random()+'&id='+id
        parent.addTab('客户修改',url);
	   
	 }
	 
	 function addUser() {	
		 
		parent.addTab('客户添加','user/addInit?rid='+Math.random());
		 
	    //dialog = $.dialog({
		//    title: '客户添加',
		//    lock: true,
		//    width: 1100,
		//    height: 650,
		//    top: 0,
		//    drag: false,
		//    resize: false,
		//    max: false,
		//    min: false,
		//    content: 'url:user/addInit?rid='+Math.random()
		//});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#userDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteUser(){
		
		var selected = $('#userDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
		
		//获取状态
        var status = selected.status;
        if(status != '0'){
        	$.dialog.tips('此客户已经开始安装，不能删除', 2, 'alert.gif');
		    return;
        }
		
        //获取需要修改项的ID
        var id = selected.id;
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="user/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#userDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化潜在客户列表
	   initUserDatagrid();
		
       var returninfo = '${user.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	
	var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '上级销售员查询',
			lock : true,
			width : 900,
			height : 480,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:employee/findSalerListDialog?rid='+Math.random()
		});
	}
	
	function closeEmployeeDialog(jsonStr) {
		employeeDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
	    
	    $("#salercode").val(jsonObject.employeecode);
		$("#salername").val(jsonObject.employeename);
	}
	
	function cleanEmployee() {
		  $('#salercode').val("");
		  $('#salername').val("");
	  }
	
	//合同查看
	function lookUserdoor() {
	   var selected = $('#userDG').datagrid("getSelected");
       if(selected == null){
  	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
       }
       //获取需要修改项的ID
       var usercode = selected.usercode;
		
       parent.addTab('门锁图片','userdoor/findByList?rid='+ Math.random()+'&usercode='+usercode);
       
	   //dialog = $.dialog({
	//    title: '门锁信息',
	//	    lock: true,
	//	    width: 1100,
	//	    height: 650,
	//	    top: 0,
	//	    drag: false,
	//	    resize: false,
	//	    max: false,
	//	    min: false,
	//	    content: 'url:userdoor/findByList?rid='+ Math.random()+'&usercode='+usercode
	//	});
	   
	} 
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryUser();
	    }  
	});
	
	
	function addUserorder() {	
		 
		var selected = $('#userDG').datagrid("getSelected");
        if(selected == null){
  	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var usercode = selected.usercode;
        
		parent.addTab('添加订单','user/addUserorderInit?rid='+Math.random()+'&usercode='+usercode);
		 
	 }
	
</script>
</body>
</html>