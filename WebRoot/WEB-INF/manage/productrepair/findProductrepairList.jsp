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
		    		<td align="right" width="10%">产品PN/SN：</td>
		    		<td width="10%">
		    			<input type="text"  class="inp w200" name="productidentfy" id="productidentfy" value="${productrepair.productidentfy }">
		    		</td>
					<td align="center">
		    			<a href="javascript:queryProductrepair();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
   	<div id = "productrepairDG" style="width:auto; height: 450px"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<!--
			<a href="javascript:addProductrepair();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateProductrepair();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteProductrepair();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
			
			<a href="javascript:lookProductrepairdoor();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看门锁图片</a>
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
	
	function initProductrepairDatagrid(){
         $('#productrepairDG').datagrid({
              title: '列表信息',
              queryParams: paramsJson(),
              url:'productrepair/findListJson',
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
      	                    { field: 'productidentfy', title: '产品SN码',width:200,align:"center"},
      	                    { field: 'productcode', title: '产品编号',width:200,align:"center"},
      	                    { field: 'productname', title: '产品名称',width:200,align:"center"},
	      		            { field: 'productproblem', title: '出现问题原因',width:300,align:"center",
	                    			formatter: function (value) {
	      					        	return "<span title='" + value + "'>" + value + "</span>";
	      					        },
	                      	},
      		                { field: 'repairinfo', title: '维修信息',width:300,align:"center",
                        			formatter: function (value) {
	      					        	return "<span title='" + value + "'>" + value + "</span>";
	      					        },
                          	},
                          	{ field: 'repairstatusname', title: '维修状态',width:150,align:"center"},
                          	{ field: 'addtime', title: '添加时间',width:150,align:"center"},
                    	]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		productidentfy:$("#productidentfy").val(),
	 	};
	 	return json;
	 }
	
	function queryProductrepair() {
		$('#productrepairDG').datagrid('reload',paramsJson());
	}
	
	$(function () {
		   
		   //初始化产品更换记录列表
		   initProductrepairDatagrid();
			
	       var returninfo = '${productrepair.returninfo}';
	       if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	       }
	    });
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryProductrepair();
	    }  
	});
	
</script>
</body>
</html>