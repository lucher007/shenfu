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
<link rel="stylesheet" href="main/plugin/easyui/themes/default/easyui.css"/>
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
		    		<td align="right">查询开始时间：</td>
					<td>
                 		<input type="text" id="querystarttime" name="querystarttime" value="${sale.querystarttime}" onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150" />
					</td>
					<td align="right">查询结束时间：</td>
					<td>
                 		<input type="text" id="queryendtime" name="queryendtime" value="${sale.queryendtime}" onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150" />
					</td>
					<td align="right">
        				<input type="button" class="btn-sch" value="查询" onclick="queryTotalstat();"/>
   		    		</td>
   		    		<td align="right">
        				<input type="button" class="btn-print" value="导出EXL" onclick="saveDownloadFile();"/>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "saleDG" style="width:auto; height: 400px">
    	</div>
	</div>
    </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initSaleDatagrid(){
         $('#saleDG').datagrid({
              title: '销售单列表信息',
              queryParams: paramsJson(),
              url:'sale/findListByTotalstatJson',
              pagination: true,
              singleSelect: true,
              rownumbers:false,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'salername', title: '销售人员',width:100,align:"center"},
                  { field: 'saletotal', title: '销售量',width:100,align:"center",},
                  { field: 'saletotalmoney', title: '销售总金额',width:100,align:"center",},
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		querystarttime:$("#querystarttime").val(),
	 		queryendtime:$("#queryendtime").val(),
	 	};
	 	return json;
	 }
	
	function queryTotalstat() {
		$('#saleDG').datagrid('reload',paramsJson());
	}
	
	$(function () {
	   
	   //初始化销售单列表
	   initSaleDatagrid();
		
       var returninfo = '${sale.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
	
	//导出充值卡
	function saveDownloadFile(){
		$("#searchForm").attr("action", "sale/saveExportSalestatForExcel");
		$("#searchForm").submit();
	}
    
</script>
</body>
</html>