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
		    		<td align="right" width="10%">货柜编码：</td>
		    		<td width="10%">
		    			<input type="text"  class="inp w200" name="depotrackcode" id="depotrackcode" value="${depotrack.depotrackcode }">
		    		</td>
		    		<td align="right" width="10%">货柜名称：</td>
		    		<td width="10%">
		    			<input type="text"  class="inp w200" name="depotrackname" id="depotrackname" value="${depotrack.depotrackname }">
		    		</td>
		    		<td align="center">
		    			<a href="javascript:queryDepotrack();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
   	<div id = "depotrackDG" style="width:auto; height: 450px"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addDepotrack();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateDepotrack();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteDepotrack();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
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
	
	function initDepotrackDatagrid(){
         $('#depotrackDG').datagrid({
              title: '列表信息',
              queryParams: paramsJson(),
              url:'depotrack/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              toolbar:'#tools',
              frozenColumns:[[
      	                    { field: 'depotrackcode', title: '货柜编号',width:150,align:"center",sortable:"true"},
      	                    { field: 'depotrackname', title: '货柜名称',width:150,align:"center"},
                       ]],
                    columns: [[
      	                    { field: 'rownums', title: '总层数',width:150,align:"center"},
      		                { field: 'columnnums', title: '总列数',width:150,align:"center"},
      		                { field: 'racknum', title: '仓位数',width:150,align:"center"},
                    	]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 			depotrackcode:$("#depotrackcode").val(),
	 			depotrackname:$("#depotrackname").val(),
	 	};
	 	return json;
	 }
	
	function queryDepotrack() {
		$('#depotrackDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateDepotrack() {
		
		var selected = $('#depotrackDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
		
	    dialog = $.dialog({
		    title: '修改',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:depotrack/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addDepotrack() {	
	    dialog = $.dialog({
		    title: '添加',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:depotrack/addInit?rid='+Math.random()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#depotrackDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteDepotrack(){
		
		var selected = $('#depotrackDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="depotrack/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#depotrackDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化潜在客户列表
	   initDepotrackDatagrid();
		
       var returninfo = '${depotrack.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryDepotrack();
	    }  
	});
</script>
</body>
</html>