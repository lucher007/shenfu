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
		    			<input type="text"  class="inp w200" name="producecode" id="producecode" value="${installmaterial.producecode }">
		    		</td>
		    		<td align="right" >材料编号：</td>
		    		<td >
		    			<input type="text"  class="inp w200" name="materialcode" id="materialcode" value="${installmaterial.materialcode }">
		    		</td>
		    		<td align="right" >材料SN码：</td>
		    		<td >
		    			<input type="text"  class="inp w200" name="materialidentfy" id="materialidentfy" value="${installmaterial.materialidentfy }">
		    		</td>
		    		<td align="right" >材料状态：</td>
		    		<td >
		    			<select id="materialstatus" name="materialstatus" class="select" onchange="queryInstallmaterial();">
							<option value="1" <c:if test="${installmaterial.materialstatus == '1'}">selected</c:if>>组装中</option>
							<option value="" <c:if test="${installmaterial.materialstatus == ''}">selected</c:if>>请选择</option>
							<!-- 
							<option value="2" <c:if test="${installmaterial.materialstatus == '2'}">selected</c:if>>组装完成</option>
							 -->
							<option value="3" <c:if test="${installmaterial.materialstatus == '3'}">selected</c:if>>已经入库</option>
						</select>
		    		</td>
		    		<td align="center" colspan="2">
		    			<a href="javascript:queryInstallmaterial();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
   <div id = "installmaterialDG" style="width:100%; height: 500px;"></div>
   <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:indepot();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">组装入库</a>
			<a href="javascript:lookInstallmaterial();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看材料详情</a>
			<a href="javascript:printBarcode();" class="easyui-linkbutton" iconCls="icon-print"  plain="true">打印条码</a>
			<!--
			<a href="javascript:updateInstallmaterial();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
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
	
	function initInstallmaterialDatagrid(){
         $('#installmaterialDG').datagrid({
              title: '列表信息',
              queryParams: paramsJson(),
              url:'installmaterial/findListJson',
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
							  { field: 'materialidentfy', title: '材料SN码',width:150,align:"center",sortable:"true"},
							  { field: 'materialcode', title: '材料编号',width:150,align:"center",sortable:"true"},
							  { field: 'materialname', title: '材料名称',width:150,align:"center",},
							  { field: 'materialstatusname', title: '状态',width:250,align:"center",},
							  { field: 'materialversion', title: '材料软件版本',width:250,align:"center",},
							  { field: 'installinformation', title: '组装信息',width:550,align:"center",
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
	 			materialcode:$("#materialcode").val(),
	 			materialidentfy:$("#materialidentfy").val(),
	 			materialstatus:$("#materialstatus").val(),
	 	};
	 	return json;
	 }
	
	function queryInstallmaterial() {
		$('#installmaterialDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	
	function lookInstallmaterial() {
		var selected = $('#installmaterialDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var materialidentfy = selected.materialidentfy;
		
        parent.addTab('查看材料详情','installmaterial/lookInit?rid='+ Math.random()+'&materialidentfy='+materialidentfy);
        
	 }
	
	function indepot() {
		
		var selected = $('#installmaterialDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        
        //获取需要修改项的ID
        var materialidentfy = selected.materialidentfy;
		
        parent.addTab('组装入库','installmaterial/indepotInit?rid='+ Math.random()+'&materialidentfy='+materialidentfy);
        
	 }
	 
	function closeDialog(){
		dialog.close();
		$('#installmaterialDG').datagrid('reload');// 刷新当前页面
	}
	
	$(function () {
	   
	   //初始化订单列表
	   initInstallmaterialDatagrid();
		
       var returninfo = '${installmaterial.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
	
	function printBarcode() {
		var selected = $('#installmaterialDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var materialidentfy = selected.materialidentfy;
		
        //开始打印
	    LODOP = getLodop();
	    LODOP.SET_PRINT_PAGESIZE (0,40+"mm", 30+"mm","");  
	    
	    //LODOP.ADD_PRINT_BARCODE(25,20,150,70,"128Auto",18050309010008);
        
	    LODOP.ADD_PRINT_BARCODE(25,20,120,70,"128Auto",materialidentfy);
	    
	    LODOP.PREVIEW();
	    //LODOP.PRINT();
	    
	 }
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryInstallmaterial();
	    }  
	});
</script>
</body>
</html>