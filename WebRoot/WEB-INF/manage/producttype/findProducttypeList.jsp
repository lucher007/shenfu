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
		    		<td align="right" width="120px">产品类别编码：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="typecode" id="typecode" value="${producttype.typecode }">
		    		</td>
		    		<td align="right" width="120px">产品类别名称：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="typename" id="typename" value="${producttype.typename }">
		    		</td>
		    		<td align="right" width="130">
						<a href="javascript:queryProducttype();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "producttypeDG" style="width:auto; height:auto;"></div>
    <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addProducttype();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateProducttype();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteProducttype();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
		</div>
	</div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initProducttypeDatagrid(){
         $('#producttypeDG').datagrid({
              title: '产品类别信息',
              queryParams: paramsJson(),
              url:'producttype/findListJson',
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
                  { field: 'typecode', title: '类别编码',width:100,align:"center"},
                  { field: 'typename', title: '类别名称',width:100,align:"center"},
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		typecode:$("#typecode").val(),
	 		typename:$("#typename").val(),
	 	};
	 	
	 	return json;
	 }
	
	function queryProducttype() {
		$('#producttypeDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateProducttype() {
		var selected = $('#producttypeDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        id = selected.id;
		
	    dialog = $.dialog({
		    title: '产品类别修改',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:producttype/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addProducttype() {	
	    dialog = $.dialog({
		    title: '产品类别添加',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    esc: false,
		    cancel:false,
		    content: 'url:producttype/addInit?rid='+Math.random()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#producttypeDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteProducttype(id){
		
		var selected = $('#producttypeDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        id = selected.id;
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="producttype/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 2, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#producttypeDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化产品类别列表
	   initProducttypeDatagrid();
		
       var returninfo = '${producttype.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryProducttype();
	    }  
	});
	
</script>
</body>
</html>