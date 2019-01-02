<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link type="text/css" rel="stylesheet" href="<%=basePath%>main/plugin/uploadify/uploadify.css" />
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
			<input type="hidden" name="id" id="id" value="${componentdepot.componentdepot.id}">
			<div class="easyui-panel" title="元器件库存信息" style="width:100%;" data-options="footer:'#ft'">
				<table style="width:100%">
					<tr>
						<td align="right">元器件批次号：</td>
						<td>
							<input type="text" class="inp w200" name="batchno" id="batchno" readonly="readonly" style="background:#eeeeee;" value="${componentdepot.componentdepot.batchno }">
						</td>
					</tr>
					<tr>
						<td align="right">元器件编码：</td>
						<td><input type="text" class="inp" name="componentcode" id="componentcode" readonly="readonly" style="background:#eeeeee;" value="${componentdepot.componentdepot.componentcode }"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">元器件封装：</td>
						<td><input type="text" class="inp" name="componentname" id="componentname" readonly="readonly" style="background:#eeeeee;" value="${componentdepot.componentdepot.componentname }"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">元器件规格：</td>
						<td><input type="text" class="inp" name="componentmodel" id="componentmodel" readonly="readonly" style="background:#eeeeee;" value="${componentdepot.componentdepot.componentmodel }"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">存放货柜位置：</td>
						<td><input type="text" class="inp" name="depotrackcode" id="depotrackcode" readonly="readonly" style="background:#eeeeee;" value="${componentdepot.componentdepot.depotrackcode }"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">库存量：</td>
						<td><input type="text" class="inp" name="depotamount" id="depotamount" readonly="readonly" style="background:#eeeeee;" value="${componentdepot.componentdepot.depotamount }"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td height="30"align="right">入库数量：</td>
						<td >
							<input type="text"  class="easyui-numberbox" name="inoutamount" id="inoutamount" style="width: 200px;"
							maxlength="8"  data-options="min:1,value:1" value="${componentdepot.inoutamount}"
							> <span class="red">*</span>
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
				</table>
			</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveOutdepot();" class="easyui-linkbutton" iconCls="icon-save">元器件入库</a>
				    </cite><span class="red">${componentdepot.returninfo}</span>
			</div>
			
		</form>		
	</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>main/plugin/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript">

  $(function () {
	     var returninfo = '${componentdepot.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
		parent.closeDialog();
  }
	
  function checkVal() {
	    
	    if ($("#inoutamount") != undefined && ($("#inoutamount").val() == "" || $("#inoutamount").val() == null )) {
			$("#inoutamount").focus();
			$.dialog.tips("请输入入库数量", 2, "alert.gif");
			return false;
		}
	    
	    if ($("#inoutercode") != undefined && ($("#inoutercode").val() == "" || $("#inoutercode").val() == null )) {
			$.dialog.tips("请入库负责人", 1, "alert.gif", function() {
				$("#inputercode").focus();
			});
			return false;
		}
	    
	    //字符串转整数类型
	    //var depotamount = parseInt($("#depotamount").val());
	    //var inoutamount = parseInt($("#inoutamount").val());
	    //if (inoutamount > depotamount) {
	    //	$("#inoutamount").focus();
		//	$.dialog.tips("出库数量不能超过库存量", 2, "alert.gif");
		//	return false;
	    //}
	    
	    //if ($("#inoutercode") != undefined && ($("#inoutercode").val() == "" || $("#inoutercode").val() == null )) {
		//	$("#inoutercode").focus();
		//	$.dialog.tips("请选择领取人信息", 2, "alert.gif");
		//	return false;
		//}
	    
		return true;
	}
	
  function saveOutdepot() {
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
		    	$("#addForm").attr("action", "componentdepot/updateDepotamount?rid="+Math.random());
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
		
		//$("#addForm").attr("action", "componentdepot/saveOutdepot");
	    //$("#addForm").submit();
	} 
    
    
  var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '员工查询',
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
</script>
</body>
</html>
