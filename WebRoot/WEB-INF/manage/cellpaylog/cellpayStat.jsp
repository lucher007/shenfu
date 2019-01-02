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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<title></title>
<link type="text/css" rel="stylesheet" href="style/user.css">
<link type="text/css" rel="stylesheet" href="js/plugin/poshytip/tip-yellowsimple/tip-yellowsimple.css">
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
				<tr height="30px">
					<td align="right">查询开始时间：</td>
					<td>
                 		<input type="text" readonly="readonly" id="beginchecktime" name="beginchecktime" value="" onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150" />
					</td>
					<td align="right">查询结束时间：</td>
					<td>
                 		<input type="text" readonly="readonly" id="endchecktime" name="endchecktime" value="" onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150" />
					</td>
					<td align="right">支付方式：</td>
					<td>
					    <select id="paytype" name="paytype" class="select" onchange="queryUserpayarrival();">
						 	   <option value="">请选择</option>
			                   <option value="0">现金</option>
			                   <option value="1">微信在线</option>
			                   <option value="2">支付宝在线</option>
			                   <option value="3">微信静态码</option>
			                   <option value="4">支付宝静态码</option>
			             </select>
					</td>
		    		<td align="center">
		    			<a href="javascript:queryUserpayarrival();" class="easyui-linkbutton" iconCls="icon-search" style="width:100px">查询</a>
						<!-- 
						<a href="javascript:saveDownloadFile();" style="width:120px" class="easyui-linkbutton">导出</a>
						 -->
		    		</td>
				</tr>
    	    </table>
   		</form>
    </div>
    
	<div id="cellpay" style="width:auto;"></div>
	
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">
	function initCellpay(){
	    $('#cellpay').datagrid({
	         title: '小区支付统计报表',
	         url:'cellpaylog/cellpayStatJson',
	         queryParams: paramsJson(),
	         singleSelect: true,
	         scrollbar:true,
	         pagination: false,
	         fitColumns:true,
	         rownumbers:true,
	         showFooter: true,
	         idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
	         loadMsg:'loading...',
	         columns: [[
	             { field: 'paytypename', title: '支付类型',align:"center",width:100},
	             { field: 'totalcount', title: '次数',align:"center",width:100},
	        	 { field: 'totalmoney', title: '金额',align:"center",width:100},
	         ]]
	     });
	}
	
	//将表单数据转为json
	function paramsJson(){
		var json = {
				beginchecktime:$("#beginchecktime").val(),
				endchecktime:$("#endchecktime").val(),
				paytype:$("#paytype").val(),
		};
		
		return json;
	}
    
	function saveDownloadFile(){
		$.dialog.confirmMultiLanguage('<spring:message code="page.confirm.execution"/>?', 
 		        '<spring:message code="page.confirm"/>',
 				'<spring:message code="page.cancel"/>',
 		        function(){ 
 					$("#searchForm").attr("action", "cellpaylog/exportBusinessExcel");
					$("#searchForm").submit();
 				}, function(){
 							});
	}
	
	function queryUserpayarrival(){
		$('#cellpay').datagrid('reload',paramsJson());
	}
	
	$(function () {
       //初始化参数
	  initCellpay();
       
       var returninfo = '${cellpaylog.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryUserpayarrival();
	    }  
	});
	
</script>
</body>
</html>

