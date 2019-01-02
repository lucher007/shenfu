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
            <table style="width: 100%">
		    	<tr>
		    	    <td align="right">供应商名称：</td>
		    		<td>
		    			<input type="text"  class="inp" name="fullname" id="fullname" value="${supplier.fullname }">
		    		</td>
		    		<td align="right">简称：</td>
		    		<td>
		    			<input type="text"  class="inp" name="name" id="name" value="${supplier.name }">
		    		</td>
		    		<td align="right">联系人：</td>
		    		<td>
		    			<input type="text"  class="inp" name="contactname" id="contactname" value="${supplier.contactname }">
		    		</td>
		    		<td align="right">联系人电话：</td>
		    		<td>
		    			<input type="text"  class="inp" name="contactphone" id="contactphone" value="${supplier.contactphone }">
		    		</td>
		    		<td align="right">状态：</td>
		    		<td>
		    			<select id="status" name="status" class="select" onchange="querySupplier();">
		    				<option value="" <c:if test="${supplier.status == '' }">selected</c:if>>请选择</option>
							<option value="0" <c:if test="${supplier.status == '0' }">selected</c:if>>无效</option>
			                <option value="1" <c:if test="${supplier.status == '1' }">selected</c:if>>有效</option>
			             </select>
		    		</td>
		    		<td align="center">
		    			<a href="javascript:querySupplier();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
				</tr>
   		    </table>
   		</form>
    </div>
   	<div id = "supplierDG" style="width:auto; height: auto;"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addSupplier();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateSupplier();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteSupplier();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
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
	
	function initSupplierDatagrid(){
         $('#supplierDG').datagrid({
              title: '列表信息',
              queryParams: paramsJson(),
              url:'supplier/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              toolbar:'#tools',
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'name', title: '供应商简称',width:100,align:"center"},
                  { field: 'fullname', title: '供应商全称',width:120,align:"center",
                	  formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                }
                  },
                  { field: 'phone', title: '公司电话',width:80,align:"center"},
                  { field: 'contactname', title: '联系人' ,width:80,align:"center"},
                  { field: 'contactphone', title: '联系电话',width:80,align:"center"},
                  { field: 'contactmail', title: '邮箱',width:100,align:"center"},
                  { field: 'address', title: '详细地址',width:120,align:"center",
                	    formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                }
                  },
                  { field: 'introduceinfo', title: '信息介绍',width:120,align:"center",
	 		      	    formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                }
				  },
				  { field: 'statusname', title: '状态' ,width:60,align:"center"},
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 			fullname:$("#fullname").val(),
		 		name:$("#name").val(),
		 		contactname:$("#contactname").val(),
		 		contactphone:$("#contactphone").val(),
		 		status:$("#status").val(),
	 	};
	 	return json;
	 }
	
	function querySupplier() {
		$('#supplierDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateSupplier() {
		
		var selected = $('#supplierDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
		
        //获取销售员的ID
       id = selected.id;
		
	    dialog = $.dialog({
		    title: '供应商修改',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:supplier/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addSupplier() {	
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
		    content: 'url:supplier/addInit?rid='+Math.random()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#supplierDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteSupplier(){
		
		var selected = $('#supplierDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
		
        //获取销售员的ID
       id = selected.id;
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="supplier/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#supplierDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化供应商列表
	   initSupplierDatagrid();
		
       var returninfo = '${supplier.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   querySupplier();
	    }  
	});
	
</script>
</body>
</html>