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
            <input type="hidden" id="checkedids" name="checkedids"/>
            <table width="100%">
				<tr>
		    		<td align="right">支付人员：</td>
		    		<td >
						<input type="text"  class="inp w200" name="payercode" id="payercode" readonly="readonly" style="background:#eeeeee;" value="${cellpaylog.payercode }">
						<input type="text"  class="inp w200" name="payername" id="payername" readonly="readonly" style="background:#eeeeee;" value="${cellpaylog.payername }">
						<a href="javascript:chooseSalerEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						<a href="javascript:cleanSalerEmployee();" class="easyui-linkbutton" iconCls="icon-undo" style="width:80px">清除</a>
					</td>
					<td align="right">查询开始时间：</td>
					<td>
                 		<input type="text" readonly="readonly" id="beginpaytime" name="beginpaytime" value="" onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150" />
					</td>
					<td align="right">查询结束时间：</td>
					<td>
                 		<input type="text" readonly="readonly" id="endpaytime" name="endpaytime" value="" onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150" />
					</td>
					<td align="right" >支付方式：</td>
					<td>
						<select id="paytype" name="paytype" class="select" onchange="queryCellpaylog();">
							<option value="" <c:if test="${cellpaylog.paytype == '' }">selected</c:if>>请选择</option>
							<option value="0" <c:if test="${cellpaylog.paytype == '0' }">selected</c:if>>现金</option>
			                <option value="1" <c:if test="${cellpaylog.paytype == '1' }">selected</c:if>>微信在线</option>
			                <option value="2" <c:if test="${cellpaylog.paytype == '2' }">selected</c:if>>支付宝在线</option>
			                <option value="3" <c:if test="${cellpaylog.paytype == '3' }">selected</c:if>>微信静态码</option>
			                <option value="4" <c:if test="${cellpaylog.paytype == '4' }">selected</c:if>>支付宝静态码</option>
			             </select>
					</td>
		    		<td align="center">
						<a href="javascript:queryCellpaylog();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
					</td>
		    	</tr>
    	    </table>
   		</form>
    </div>
   	<div id = "cellpaylogDG" style="width:auto;"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<!--
			<a href="javascript:checkCellpaylog();" class="easyui-linkbutton" iconCls="icon-tip"  plain="true">审核到账</a>
			<a href="javascript:addCellpaylog();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateCellpaylog();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteCellpaylog();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
		      -->
		</div>
	</div>
	<div class="pop-box" id="pop-div">
			<table width="400" border="0" cellpadding="0" cellspacing="0">
	          <tr>
	            <td height="30" width="30%" align="right">支付总金额：</td>
	            <td width="60%">
	            	<input type="text" id="paytotalmoney" name="paytotalmoney" class="inp" readonly="readonly" style="background:#eeeeee;" ><span class="red">*</span>
	            </td>
	          </tr>
		</table>
	</div>
</div>
    
<script type="text/javascript" language="javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" language="javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
   
function initCellpaylogDatagrid(){
    $('#cellpaylogDG').datagrid({
         title: '扫楼支付列表信息',
         queryParams: paramsJson(),
         url:'cellpaylog/findListJson',
         pagination: true,
         singleSelect: false,
         scrollbar:true,
         pageSize: 10,
         pageList: [10,30,50], 
         //fitColumns:true,
         checkOnSelect: true,// 如果为true，当用户点击行的时候该复选框就会被选中或取消选中。 如果为false，当用户仅在点击该复选框的时候才会呗选中或取消。
         idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
         loadMsg:'正在加载数据......',
         showFooter: true,
         toolbar:'#tools',
         frozenColumns:[[
							{ field: 'payercode', title: '支付人编号',width:150,align:"center"},
							{ field: 'payername', title: '支付人姓名',width:150,align:"center"},
							{ field: 'paytypename', title: '支付方式',width:150,align:"center"},
							{ field: 'paymoney', title: '支付金额',width:150,align:"center",
								styler: function (value) {
										  return "background-color:yellow;color:red;";
								  },	
							},
                       ]],
          columns: [[
							{ field: 'extendcode', title: '小区发布单号',width:150,align:"center"},
							{ field: 'cellcode', title: '小区编号',width:150,align:"center"},
							{ field: 'cellname', title: '小区名称',width:150,align:"center"},
							{ field: 'totalmoney', title: '扫楼总价',width:150,align:"center"},
							{ field: 'address', title: '小区地址',width:350,align:"center",
						  	    formatter: function (value) {
						  	    	if(value != null){
						  	    		return "<span title='" + value + "'>" + value + "</span>";
						  	    	}
						  	    },
						    },
						    { field: 'paytime', title: '支付时间',width:150,align:"center",},
         ]]
     });
}
   
//将表单数据转为json
function paramsJson(){
	var json = {
			payercode:$("#payercode").val(),
			beginpaytime:$("#beginpaytime").val(),
			endpaytime:$("#endpaytime").val(),
			paytype:$("#paytype").val(),
	};
	return json;
}  
   
    //查询
	function queryCellpaylog(){
		$('#cellpaylogDG').datagrid('reload',paramsJson());
		$('#cellpaylogDG').datagrid('clearChecked');//清空选中的值
	}	
	
	$(function () {
		initCellpaylogDatagrid();
        var returninfo = '${cellpaylog.returninfo}';
        if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
        }
    });
	
	var saleremployeeDialog;
	function chooseSalerEmployee() {
		saleremployeeDialog = $.dialog({
			title : '上级销售员查询',
			lock : true,
			width : 1100,
			height : 650,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:employee/findSalerListDialog?rid='+Math.random()
		});
	}

    function closeEmployeeDialog(jsonStr) {
		
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
		$("#payercode").val(jsonObject.employeecode);
		$("#payername").val(jsonObject.employeename);
		saleremployeeDialog.close();
			
		queryCellpaylog();
	}
	
	function cleanSalerEmployee() {
		$("#payercode").val("");
		$("#payername").val("");
		queryCellpaylog();
    }
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryCellpaylog();
	    }  
	});
</script>
</body>
</html>

