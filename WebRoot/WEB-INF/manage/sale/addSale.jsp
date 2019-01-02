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
<style type="text/css">
.easyui-panel table tr {
	height: 30px;
}
</style>
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm">
				<div class="fb-con">
					<div class="easyui-panel" title="潜在订户信息" style="width:100%;">
						<table style="width:100%;border:0; cellpadding:0; cellspacing:0">
							<tr>
								<td align="right" width="10%">客户姓名：</td>
								<td width="25%">
									<input type="text" class="inp w200" name="username" id="username" readonly="readonly" style="background:#eeeeee;">
									<input type="hidden" class="inp w200" name="userid" id="userid">
									<input  type="button" class="btn-add"  id="btnfinish" value="请选择" onclick="chooseUser();">
								</td>
							</tr>
							<tr>
								<td align="right">联系电话：</td>
								<td><input type="text" class="inp" name="phone" id="phone" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span></td>
							</tr>
							<tr>
								<td align="right">客户性别：</td>
								<td>
									<select id="usersex" name="usersex" class="select">
										<option value="0" >女</option>
										<option value="1" >男</option>
									</select>
								</td>
							</tr>
							<tr>
								<td align="right">客户来源：</td>
								<td>
									<select id="source" name="source" class="select">
										<option value="0" >销售</option>
										<option value="1" >400电话</option>
									</select>
								</td>
							</tr>
							<tr>
								<td align="right">客户地址：</td>
								<td><input type="text" class="inp" style="width: 400px;" name="address" id="address" > <span class="red">*</span></td>
							</tr>
						</table>
					</div>
					
					<div class="easyui-panel" title="销售人员信息" style="width:100%;">
						<table style="width:100%;border:0; cellpadding:0; cellspacing:0">
							<tr>
								<td align="right" width="10%">销售人员姓名：</td>
								<td width="25%">
									<input type="text" class="inp w200" name="salername" id="salername" readonly="readonly" style="background:#eeeeee;" >
									<input type="hidden" class="inp w200" name="salerid" id="salerid">
									<input  type="button" class="btn-add"  id="btnfinish" value="请选择" onclick="chooseEmployee();">
								</td>
							</tr>
							<tr>
								<td align="right">联系电话：</td>
								<td><input type="text" class="inp" name="salerphone" id="salerphone" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span></td>
							</tr>
						</table>
					</div>
				</div>
				
				<div class="form-box">
					<div class="fb-bom">
						<cite> <input type="button" class="btn-back" value="返回" onclick="goback();" />
						 <input type="button" class="btn-save" value="保存" onclick="saveSale();"
						/> </cite> <span class="red">${sale.returninfo}</span>
				    </div>
				</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/comtools.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript">
	
	function checkVal() {
		if ($("#userid") != undefined && ($("#userid").val() == "" || $("#userid").val() == null )) {
			$.dialog.tips("请选择客户", 1, "alert.gif", function() {
				$("#username").focus();
			});
			return false;
		}
		if ($("#salerid") != undefined && ($("#salerid").val() == "" || $("#salerid").val() == null )) {
			$.dialog.tips("请输入销售人员", 1, "alert.gif", function() {
				$("#salername").focus();
			});
			return false;
		}
		return true;
	}
	
	function saveSale() {
		if (!checkVal()) {
			return;
		}
		$("#addForm").attr("action", "sale/save");
	    $("#addForm").submit();
	}
	
	function goback(){
		parent.closeDialog();
	}
	
	$(function () {
       var returninfo = '${sale.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
    var userDialog;
	function chooseUser() {
		userDialog = $.dialog({
			title : '潜在客户查询',
			lock : true,
			width : 900,
			height : 500,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:user/findUserListDialog?rid='+Math.random()
		});
	}

	function closeUserDialog(jsonStr) {
		userDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
		$("#userid").val(jsonObject.id);
		$("#username").val(jsonObject.username);
		$("#phone").val(jsonObject.phone);
		$("#usersex").val(jsonObject.usersex);
		$("#source").val(jsonObject.source);
		$("#address").val(jsonObject.address);
	}
    
    var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '员工查询',
			lock : true,
			width : 900,
			height : 500,
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
		$("#salerid").val(jsonObject.id);
		$("#salername").val(jsonObject.employeename);
		$("#salerphone").val(jsonObject.phone);
	}
    
    function checkLength(object, maxlength) {
	    var obj = $('#' + object),
	        value = $.trim(obj.val());
	    if (value.length > maxlength) {
	      obj.val(value.substr(0, maxlength));
	    } else {
	      $('#validNum' + object).html(maxlength - value.length);
	    }
	 }
</script>
</body>
</html>
