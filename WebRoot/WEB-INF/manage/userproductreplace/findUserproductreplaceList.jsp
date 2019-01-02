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
		    		<td align="right" width="10%">客户编号：</td>
		    		<td width="10%">
		    			<input type="text"  class="inp w200" name="usercode" id="usercode" value="${userproductreplace.usercode }">
		    		</td>
		    		<td align="right" width="10%">订单编号：</td>
		    		<td width="10%">
		    			<input type="text"  class="inp w200" name="ordercode" id="ordercode" value="${userproductreplace.ordercode }">
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">产品PN/SN：</td>
		    		<td width="10%">
		    			<input type="text"  class="inp w200" name="productidentfy" id="productidentfy" value="${userproductreplace.productidentfy }">
		    		</td>
		    		<td align="right">替换类型：</td>
        			<td >
        				<select id="replacetype" name="replacetype" class="select" onchange="queryUserproductreplace();">
			                   <option value=""  <c:if test="${userproductreplace.replacetype == ''}">selected</c:if>>请选择</option>
			                   <option value="0" <c:if test="${userproductreplace.replacetype == '0' }">selected</c:if>>产品更换</option>
			                   <option value="1" <c:if test="${userproductreplace.replacetype == '1' }">selected</c:if>>软件升级</option>
			             </select>
        			</td>
					<td align="center">
		    			<a href="javascript:queryUserproductreplace();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
   	<div id = "userproductreplaceDG" style="width:auto; height: 450px"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<!--
			<a href="javascript:addUserproductreplace();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateUserproductreplace();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteUserproductreplace();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
			
			<a href="javascript:lookUserproductreplacedoor();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看门锁图片</a>
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
	
	function initUserproductreplaceDatagrid(){
         $('#userproductreplaceDG').datagrid({
              title: '列表信息',
              queryParams: paramsJson(),
              url:'userproductreplace/findListJson',
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
      	                    { field: 'ordercode', title: '订单编号',width:200,align:"center"},
      	                    { field: 'replacetypename', title: '更换类型',width:150,align:"center"},
      		                { field: 'visittime', title: '上门售后时间',width:150,align:"center"},
      		                { field: 'visitreasons', title: '故障描述',width:150,align:"center"},
      		                { field: 'productname', title: '产品名称',width:150,align:"center"},
      		                { field: 'oldproductidentfy', title: '原产品PN/SN',width:150,align:"center"},
      		                { field: 'oldproductversion', title: '原产品软件版本',width:150,align:"center"},
      		                { field: 'newproductidentfy', title: '新产品PN/SN',width:150,align:"center"},
    		                { field: 'newproductversion', title: '新产品软件版本',width:150,align:"center"},
      		                { field: 'oldproductstatusname', title: '旧产品回收状态',width:150,align:"center"},
                          	{ field: 'addtime', title: '添加时间',width:150,align:"center"},
                    	]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		usercode:$("#usercode").val(),
	 		ordercode:$("#ordercode").val(),
	 		productidentfy:$("#productidentfy").val(),
	 		replacetype:$("#replacetype").val(),
	 	};
	 	return json;
	 }
	
	function queryUserproductreplace() {
		$('#userproductreplaceDG').datagrid('reload',paramsJson());
	}
	
	$(function () {
		   
		   //初始化产品更换记录列表
		   initUserproductreplaceDatagrid();
			
	       var returninfo = '${userproductreplace.returninfo}';
	       if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	       }
	    });
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryUserproductreplace();
	    }  
	});
	
</script>
</body>
</html>