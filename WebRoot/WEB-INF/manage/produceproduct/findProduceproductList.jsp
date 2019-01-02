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
		    		<td align="right" >生产批次号：</td>
		    		<td >
		    			<input type="text"  class="inp w200" name="producecode" id="producecode" value="${produceproduct.producecode }">
		    		</td>
		    		<td align="right" >产品编号：</td>
		    		<td >
		    			<input type="text"  class="inp w200" name="productcode" id="productcode" value="${produceproduct.productcode }">
		    		</td>
		    		<td align="right" >产品SN码：</td>
		    		<td >
		    			<input type="text"  class="inp w200" name="productidentfy" id="productidentfy" value="${produceproduct.productidentfy }">
		    		</td>
		    		<td align="right" >产品状态：</td>
		    		<td >
		    			<select id="productstatus" name="productstatus" class="select" onchange="queryProduceproduct();">
							<option value="1" <c:if test="${produceproduct.productstatus == '1'}">selected</c:if>>组装中</option>
							<option value="" <c:if test="${produceproduct.productstatus == ''}">selected</c:if>>请选择</option>
							<!-- 
							<option value="2" <c:if test="${produceproduct.productstatus == '2'}">selected</c:if>>组装完成</option>
							 -->
							<option value="3" <c:if test="${produceproduct.productstatus == '3'}">selected</c:if>>已经入库</option>
						</select>
		    		</td>
		    		<td align="center" colspan="2">
		    			<a href="javascript:queryProduceproduct();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
   <div id = "produceproductDG" style="width:100%; height: 500px;"></div>
   <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:indepot();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">组装入库</a>
			<a href="javascript:lookProduceproduct();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看产品详情</a>
			<a href="javascript:printBarcode();" class="easyui-linkbutton" iconCls="icon-print"  plain="true">打印条码</a>
			<!--
			<a href="javascript:updateProduceproduct();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			  -->
		</div>
	</div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="<%=basePath%>js/lodop/LodopFuncs.js"></script>
<script type="text/javascript">
	
	function initProduceproductDatagrid(){
         $('#produceproductDG').datagrid({
              title: '列表信息',
              queryParams: paramsJson(),
              url:'produceproduct/findListJson',
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
							  { field: 'producecode', title: '生产批次号',width:150,align:"center",sortable:"true"},
							  { field: 'productidentfy', title: '产品SN码',width:150,align:"center",sortable:"true"},
							  { field: 'productcode', title: '产品编号',width:150,align:"center",sortable:"true"},
							  { field: 'productname', title: '产品名称',width:150,align:"center",},
							  { field: 'productstatusname', title: '状态',width:250,align:"center",},
							  { field: 'productversion', title: '产品软件版本',width:250,align:"center",},
							  { field: 'installinfo', title: '组装信息',width:550,align:"center",
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
	 			producecode:$("#producecode").val(),
	 			productcode:$("#productcode").val(),
	 			productidentfy:$("#productidentfy").val(),
	 			productstatus:$("#productstatus").val(),
	 	};
	 	return json;
	 }
	
	function queryProduceproduct() {
		$('#produceproductDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	
	function lookProduceproduct() {
		var selected = $('#produceproductDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var productidentfy = selected.productidentfy;
		
        parent.addTab('查看产品详情','produceproduct/lookInit?rid='+ Math.random()+'&productidentfy='+productidentfy);
        
	 }
	
	function indepot() {
		
		var selected = $('#produceproductDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        
        //获取需要修改项的ID
        var productidentfy = selected.productidentfy;
		
        parent.addTab('组装入库','produceproduct/indepotInit?rid='+ Math.random()+'&productidentfy='+productidentfy);
        
	 }
	 
	function closeDialog(){
		dialog.close();
		$('#produceproductDG').datagrid('reload');// 刷新当前页面
	}
	
	$(function () {
	   
	   //初始化订单列表
	   initProduceproductDatagrid();
		
       var returninfo = '${produceproduct.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
	
	function printBarcode() {
		var selected = $('#produceproductDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var productidentfy = selected.productidentfy;
		
        //开始打印
	    LODOP = getLodop();
	    LODOP.SET_PRINT_PAGESIZE (0,40+"mm", 30+"mm","");  
	    
	    //LODOP.ADD_PRINT_BARCODE(25,20,150,70,"128Auto",18050309010008);
        
	    LODOP.ADD_PRINT_BARCODE(25,20,120,70,"128Auto",productidentfy);
	    
	    LODOP.PREVIEW();
	    //LODOP.PRINT();
	    
	 }
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryProduceproduct();
	    }  
	});
</script>
</body>
</html>