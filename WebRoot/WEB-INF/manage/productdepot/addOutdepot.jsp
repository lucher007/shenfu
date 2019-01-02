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
			<input type="hidden" name="id" id="id" value="${productdepot.productdepot.id}">
			<div class="easyui-panel" title="产品出库信息" style="width:100%;" data-options="footer:'#ft'">
				<table style="width:100%">
					<tr>
						<td align="right">产品标识：</td>
						<td>
							<input type="text" class="inp w200" name="productidentfy" id="productidentfy" readonly="readonly" style="background:#eeeeee;" value="${productdepot.productdepot.productidentfy }">
						</td>
					</tr>
					<tr>
						<td align="right">产品编码：</td>
						<td><input type="text" class="inp" name="productcode" id="productcode" readonly="readonly" style="background:#eeeeee;" value="${productdepot.productdepot.productcode }"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">产品名称：</td>
						<td><input type="text" class="inp" name="productname" id="productname" readonly="readonly" style="background:#eeeeee;" value="${productdepot.productdepot.productname }"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">存放货柜位置：</td>
						<td><input type="text" class="inp" name="depotrackcode" id="depotrackcode" readonly="readonly" style="background:#eeeeee;" value="${productdepot.productdepot.depotrackcode }"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">库存量：</td>
						<td><input type="text" class="inp" name="depotamount" id="depotamount" readonly="readonly" style="background:#eeeeee;" value="${productdepot.productdepot.depotamount }"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td height="30"align="right">出库数量：</td>
						<td >
							<input type="text"  class="easyui-numberbox" name="inoutamount" id="inoutamount" style="width: 200px;"
							maxlength="8"  data-options="min:1,value:1" value="${productdepot.inoutamount}"
							> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">领取人编号：</td>
						<td>
							<input type="text" class="inp w200" name="inoutercode" id="inoutercode" readonly="readonly" style="background:#eeeeee;width: 200px;" value="${materialdepot.inoutercode }"><span class="red">*</span>
							<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
							<!--
							<a href="javascript:cleanEmployee();" class="easyui-linkbutton" iconCls="icon-undo" style="width:80px">清除</a>
							  -->
						</td>
					</tr>
					<tr>
						<td align="right">领取人姓名：</td>
						<td>
							<input type="text" class="inp w200" name="inoutername" id="inoutername" readonly="readonly" style="background:#eeeeee;width: 200px;" value="${materialdepot.inoutername }"><span class="red">*</span>
						</td>
					</tr>
				</table>
			</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveOutdepot();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">产品出库</a>
				    </cite><span class="red">${productdepot.returninfo}</span>
			</div>
			
		</form>		
	</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>main/plugin/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript">

  $(function () {
	     var returninfo = '${productdepot.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
	  parent.closeTabSelected();
		//parent.closeDialog();
  }
	
  function checkVal() {
	    
	    if ($("#inoutamount") != undefined && ($("#inoutamount").val() == "" || $("#inoutamount").val() == null )) {
			$("#inoutamount").focus();
			$.dialog.tips("请输入出库数量", 2, "alert.gif");
			return false;
		}
	    
	    //字符串转浮点类型
	    var depotamount = parseInt($("#depotamount").val());
	    var inoutamount = parseInt($("#inoutamount").val());
	    if (inoutamount > depotamount) {
	    	$("#inoutamount").focus();
			$.dialog.tips("出库数量不能超过库存量", 2, "alert.gif");
			return false;
	    }
	    
	    if ($("#inoutercode") != undefined && ($("#inoutercode").val() == "" || $("#inoutercode").val() == null )) {
			$("#inoutercode").focus();
			$.dialog.tips("请选择领取人信息", 2, "alert.gif");
			return false;
		}
	    
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
		    	$("#addForm").attr("action", "productdepot/saveOutdepot?rid="+Math.random());
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
		
		//$("#addForm").attr("action", "productdepot/saveOutdepot");
	    //$("#addForm").submit();
	} 
    
    
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
		  $('#inoutercode').val("");
		  $('#inoutername').val("");
	}
</script>
</body>
</html>
