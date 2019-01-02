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
<style type="text/css">
	#body table tr {
      height: 30px;
    }
</style>

</head>

<body>
	<div id="body">
    <div class="lst-box">
    	<div id = "statisticsDG" style="width:auto; height: 400px">
    	</div>
	</div>
    </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initStatisticsDatagrid(){
         $('#statisticsDG').datagrid({
              title: '材料供需统计',
              queryParams: paramsJson(),
              url:'statistics/findMaterialBalanceStatJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'materialcode', title: '材料编码',width:100,align:"center"},
                  { field: 'materialname', title: '材料名称',width:200,align:"center",},
                  { field: 'orderamount', title: '订单需求量',width:200,align:"center",},
                  { field: 'depotamount', title: '材料库存量',width:200,align:"center",},
                  { field: 'purchaseamount', title: '采购单量',width:200,align:"center",
                	  formatter: function (value) {
                		   if(value == null || value ==''){
                			   return 0;
                		   }else{
                			   return value;
                		   }
		              },
                  },
              ]]
          });
	}

	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 	};
	 	
	 	return json;
	 }
	
	function queryStatistics() {
		$('#statisticsDG').datagrid('reload',paramsJson());
	}
	
	$(function () {
	   
	   //初始化小区列表
	   initStatisticsDatagrid();
		
       var returninfo = '${statistics.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>