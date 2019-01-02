<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
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
		<form method="post" id="addForm" name="addForm">
			<div class="easyui-panel" style="width:100%;" title="元器件入库" data-options="footer:'#ft'">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="30" width="15%" align="right">元器件名称：</td>
						<td width="25%">
							<input id="componentcode" name="componentcode" > <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">入库量：</td>
						<td>
							<input type="text" class="inp easyui-numberbox" name="depotamount" id="depotamount" value="${componentdepot.depotamount}" /> <span class="red">*</span>
						</td> 
					</tr>
					<tr>
						<td align="right" width="10%">入库负责人编号：</td>
						<td width="25%">
							<input type="text" class="inp w200" name="inoutercode" id="inoutercode" readonly="readonly" style="background:#eeeeee;" value="${componentdepot.inoutercode }">
							<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">入库负责人姓名：</td>
						<td width="25%">
							<input type="text" class="inp w200" name="inoutername" id="inoutername" readonly="readonly" style="background:#eeeeee;" value="${componentdepot.inoutername }"><span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td height="30" width="15%" align="right">存放仓库位置：</td>
						<td width="25%">
							<input id="depotrackcode" name="depotrackcode"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td height="30" width="15%" align="right">供应商简称：</td>
						<td width="25%">
							<input id="suppliername" name="suppliername" style="width: 400px;"> <span class="red">*</span>
						</td>
					</tr>
				</table>
			</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
	        			<a href="javascript:saveIndepot();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
				    </cite><span class="red">${componentdepot.returninfo}</span>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/comtools.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript">
	
	function checkVal() {
		
		if ($('#componentcode').combobox('getValue') == "") {
			$.dialog.tips("请选择入库元器件", 1, "alert.gif", function() {
				$("#componentcode").focus();
			});
			return false;
		}
		
		if ($("#depotamount") != undefined && ($("#depotamount").val() == "" || $("#depotamount").val() == null )) {
			$.dialog.tips("请输入元器件入库量", 1, "alert.gif", function() {
				$("#depotamount").focus();
			});
			return false;
		}
		
		if ($("#inoutercode") != undefined && ($("#inoutercode").val() == "" || $("#inoutercode").val() == null )) {
			$.dialog.tips("请入库负责人", 1, "alert.gif", function() {
				$("#inputercode").focus();
			});
			return false;
		}
		
		if ($('#depotrackcode').combobox('getValue') == "") {
			$.dialog.tips("请选择货柜存放位置", 1, "alert.gif", function() {
				$("#depotrackcode").focus();
			});
			return false;
		}
		
		return true;
	}
	
	function saveIndepot() {
		if (!checkVal()) {
			return;
		}
		
		
		$.dialog({
		    title: '提交',
		    lock: true,
		    background: '#000', /* 背景色 */
		    opacity: 0.5,       /* 透明度 */
		    content: '是否确定执行？',
		    icon: 'alert.gif',
		    ok: function () {
		    	$("#addForm").attr("action", "componentdepot/saveIndepot?rid="+Math.random());
				$("#addForm").submit();
		        /* 这里要注意多层锁屏一定要加parent参数 */
		        $.dialog({
		        	lock: true,
		            title: '提示',
		        	content: '执行中，请等待......', 
		        	max: false,
				    min: false,
				    cancel:false,
		        	icon: 'loading.gif',
		        });
		        return false;
		    },
		    okVal: '确定',
		    cancel: true,
		    cancelVal:'取消'
		});
		
		//$("#addForm").attr("action", "componentdepot/saveIndepot");
	    //$("#addForm").submit();
	}

	
	$(function () {
       var returninfo = '${componentdepot.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
	
	//加载元器件列表
	$('#componentcode').combobox({    
	    url:'component/getComponentComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${componentdepot.componentcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
              
        } 
	}); 
	
	//加载元器件列表
	$('#depotrackcode').combobox({    
	    url:'depotrack/getDepotrackinfoComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${componentdepot.depotrackcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
              
        } 
	}); 
	
	 var employeeDialog;
		function chooseEmployee() {
			employeeDialog = $.dialog({
				title : '员工查询',
				lock : true,
				width : 1000,
				height : 600,
				top : 0,
				drag : false,
				resize : false,
				max : false,
				min : false,
				content : 'url:employee/findEmployeeListDialog?rid='+Math.random()
			});
		}

		function closeEmployeeDialog(jsonStr) {
			employeeDialog.close();
			//将json字符串转换成json对象
		    var jsonObject =  eval("(" + jsonStr +")");
			$("#inoutercode").val(jsonObject.employeecode);
			$("#inoutername").val(jsonObject.employeename);
		}
	
		
		//加载列表
		$('#suppliername').combobox({    
		    url:'supplier/getSupplierComboBoxJson',    
		    valueField:'id',    
		    textField:'text',
		    //数据执行之后才加载
		    onLoadSuccess:function(node, data){
		    	//默认选择
		    	$(this).combobox("setValue", '${componentdepot.suppliername}');
		    },
		    //绑定onchanger事件
		    onChange:function(){  
	              
	        } 
		}); 
		
		function goback(){
			parent.closeDialog();
	  }
</script>
</body>
</html>
