<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
					<td align="right" width="10%">角色编码：</td>
					<td width="20%">
						<input type="text" class="inp" name="rolecode" id="rolecode" value="${role.rolecode }">
					</td>
					<td align="right" width="10%">角色名称：</td>
					<td width="20%">
						<input type="text" class="inp" name="rolename" id="rolename" value="${role.rolename }">
					</td>
					<td width="130px" align="right">
						<a href="javascript:queryRole();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="lst-box">
    	<div id = "roleDG" style="width:auto; height: 400px">
    	</div>
	</div>
	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:lookRole();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看</a>
			<a href="javascript:addRole();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateRole();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteRole();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
		</div>
	</div>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	function initRoleDatagrid(){
         $('#roleDG').datagrid({
              title: '权限列表信息',
              queryParams: paramsJson(),
              url:'role/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              toolbar:'#tools',
              columns: [[
                  { field: 'rolecode', title: '角色编码',width:100,align:"center"},
                  { field: 'rolename', title: '角色名称',width:100,align:"center"},
                  { field: 'typename', title: '角色类型',width:100,align:"center"},
              ]]
          });
	}
    
    function typeformatter(value,row,index){
	    var typeContent = '';
	    if(value == '0'){
	    	typeContent = "系统默认角色";
	    }else{
	    	typeContent = "新增角色";
	    }
        return  typeContent;
	 }
    
     	
    	
	function roleEditformatter(value,row,index){
	    var updateContent = '';
	    if('${Operator.loginname}' != 'admin' && row.type == '0'){
	    	updateContent = '<a class="btn-look" href="javascript:checkRole('+row.id+');">查看</a>';
	    }else{
	    	updateContent = '<a class="btn-edit" href="javascript:editRole('+row.id+');">修改</a>';
	    }
        return  updateContent;
	 }
	 
	 function roleDeleteformatter(value,row,index){
	    var deleteContent = '';
	    
	    if(row.type != '0'){
	    	deleteContent = '<a class="btn-del" href="javascript:deleteRole('+row.id+');" >删除</a>';
	    }
        return  deleteContent;
	 }
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		rolecode:$("#rolecode").val(),
	 		rolename:$("#rolename").val()
	 	};
	 	
	 	return json;
	 }
	
	
	function queryRole() {
		$('#roleDG').datagrid('reload',paramsJson());
	}
    
    var dialog;
	function addRole() {	
	    dialog = $.dialog({
		    title: '角色添加',
		    lock: true,
		    width: 1100,
		    height: 600,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:role/addRoleInit?rid='+Math.random()
		});
	 }
	 
	 function updateRole() {
		 
		var selected = $('#roleDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
 		 
        
        var type = selected.type;
        if('${Operator.loginname}' != 'admin' && type == '0'){
        	$.dialog.tips('无权修改系统默认角色', 2, 'alert.gif');
		    return;
	    }
        
       
	   dialog = $.dialog({
			title : '角色修改',
			lock : true,
			width : 1100,
			height : 600,
			top : 0,
			drag : true,
			resize : true,
			max : false,
			min : false,
			content : 'url:role/editRole?rid='+Math.random()+'&id='+id
		});
	}
  
    
    
	var dialog;
	function lookRole() {
		
		var selected = $('#roleDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
        
		dialog = $.dialog({
			title : '角色查看 ',
			lock : true,
			width : 1100,
			height : 600,
			top : 0,
			drag : true,
			resize : true,
			max : false,
			min : false,
			content : 'url:role/checkRole?id='+id+'&rid='+Math.random()
		});
	}
    
	function closeDialog(){
		dialog.close();
		$('#roleDG').datagrid('reload');// 刷新当前页面
	}
	
	
	$(function() {
	
		//初始化角色列表
	    initRoleDatagrid();	
			
		var returninfo = '${role.returninfo}';
		if (returninfo != '') {
			$.dialog.tips(returninfo, 1, 'alert.gif');
		}
	});
	
	/**
	*删除
	*/
	function deleteRole(){
		
		var selected = $('#roleDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="role/delete?rid="+Math.random();
			
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#roleDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
	}
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryRole();
	    }  
	});
</script>
</body>
</html>
