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
			<div class="easyui-panel" title="产品生产信息" style="width:100%;" data-options="footer:'#ft'">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">产品SN码：</td>
						<td>
							<input type="text" class="inp" name="productidentfy" id="productidentfy" readonly="readonly" style="background:#eeeeee;width: 200px;" value="${produceproduct.produceproduct.productidentfy }">  <span class="red">*</span>
						</td> 
					</tr>
					<tr>
						<td align="right">产品编码：</td>
						<td>
							<input type="text" class="inp" name="productcode" id="productcode" readonly="readonly" style="background:#eeeeee;width: 200px;" value="${produceproduct.produceproduct.productcode }">  <span class="red">*</span>
						</td> 
					</tr>
					<tr>
						<td height="30" align="right">产品名称：</td>
						<td >
							<input type="text" class="inp" name="productname" id="productname" readonly="readonly" style="background:#eeeeee;width: 200px;" value="${produceproduct.produceproduct.productname }"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td height="30"align="right">入库数量：</td>
						<td >
							<input type="text" class="inp" name="depotamount" id="depotamount" readonly="readonly" style="background:#eeeeee;width: 200px;" value="${produceproduct.produceproduct.depotamount }"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">入库负责人编号：</td>
						<td width="25%">
							<input type="text" class="inp w200" name="inoutercode" id="inoutercode" readonly="readonly" style="background:#eeeeee;" value="${produceproduct.inoutercode }">
							<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">入库负责人姓名：</td>
						<td width="25%">
							<input type="text" class="inp w200" name="inoutername" id="inoutername" readonly="readonly" style="background:#eeeeee;" value="${produceproduct.inoutername }"><span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td height="30" width="15%" align="right">存放仓库位置：</td>
						<td width="25%">
							<input id="depotrackcode" name="depotrackcode"> <span class="red">*</span>
						</td>
					</tr>
				</table>
			</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveIndepot();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">产品入库</a>
				    </cite><span class="red">${produceproduct.returninfo}</span>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
    <script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript">
	
	function checkVal() {
		if ($("#productcode") != undefined && ($("#productcode").val() == "" || $("#productcode").val() == null )) {
			$.dialog.tips("请选择入库产品编号", 1, "alert.gif", function() {
				$('#productcode').focus();
			});
			return false;
		}
		
		if ($("#productname") != undefined && ($("#productname").val() == "" || $("#productname").val() == null )) {
			$.dialog.tips("请选择入库产品名称", 1, "alert.gif", function() {
				$('#productcode').focus();
			});
			return false;
		}

		if ($("#depotamount") != undefined && ($("#depotamount").val() == "" || $("#depotamount").val() == null )) {
			$.dialog.tips("请输入入库数量", 1, "alert.gif", function() {
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
		    	$("#addForm").attr("action", "produceproduct/saveIndepot?rid="+Math.random());
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
		
		//$("#addForm").attr("action", "produceproduct/saveIndepot");
	    //$("#addForm").submit();
	}

	
	$(function () {
       var returninfo = '${produceproduct.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
	
	var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '装维员工查询',
			lock : true,
			width : 1100,
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
	
	function cleanEmployee() {
		  $('#producercode').val("");
		  $('#producername').val("");
	}
	
	//加载材料列表
	$('#depotrackcode').combobox({    
	    url:'depotrack/getDepotrackinfoComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${produceproduct.depotrackcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
              
        } 
	}); 
	
	function goback(){
		parent.closeTabSelected();
	}
</script>
</body>
</html>
