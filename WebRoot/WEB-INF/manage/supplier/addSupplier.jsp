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
			<div class="easyui-panel" title="供应商信息" style="width:100%;" data-options="footer:'#ft'">
				<table style="width:100%">
					<tr>
						<td align="right">供应商简称：</td>
						<td ><input type="text" class="inp"  name="name" id="name" value="${supplier.name }"> <span class="red">*</span></td>
					</tr>
	  				<tr>	
						<td align="right">供应商全称：</td>
						<td><input type="text" class="inp" style="width:250px;" name="fullname" id="fullname" value="${supplier.fullname }"> <span class="red">*</span></td> 
					</tr>
					<tr>
						<td align="right">供应商地址：</td>
						<td><input type="text" class="inp" style="width:250px;" name="address" id="address" value="${supplier.address }"></td>
					</tr>
					<tr>
						<td align="right">公司电话：</td>
						<td><input type="text" class="inp" name="phone" id="phone" value="${supplier.phone }"></td>
					</tr>
	  				<tr>	
					    <td align="right">供应商联系人：</td>
						<td><input type="text" class="inp" name="contactname" id="contactname" value="${supplier.contactname }"></td>
					</tr>
					<tr>
						<td align="right">联系人电话：</td>
						<td><input type="text" class="inp" name="contactphone" id="contactphone" value="${supplier.contactphone }"></td>
					</tr>
	  				<tr>	
						<td align="right">联系人邮件：</td>
						<td><input type="text" class="inp" style="width:250px;" name="contactmail" id="contactmail" value="${supplier.contactmail }"></td>
					</tr>
					<tr>
				  		<td align="right">信息介绍：</td>
			            <td colspan="5"> 
							<textarea id="introduceinfo" name="introduceinfo" style="width:550px; height:50px;" 
							onKeyDown="checkLength('introduceinfo',300)" onKeyUp="checkLength('introduceinfo',300)">${supplier.introduceinfo}</textarea>
							<span class="red">还可以输入<span id="validNumintroduceinfo">300</span>字</span>
			            </td>
					</tr>
				</table>
			</div>
			<div id="ft" style="padding:5px;">
				<cite> 
					<a href="javascript:goBack();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
        			<a href="javascript:saveSupplier();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
			    </cite><span class="red">${supplier.returninfo}</span>
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
		if ($("#name") != undefined && ($("#name").val() == "" || $("#name").val() == null )) {
			$.dialog.tips("请输入供应商简称", 1, "alert.gif", function() {
				$("#name").focus();
			});
			return false;
		}
		
		if ($("#fullname") != undefined && ($("#fullname").val() == "" || $("#fullname").val() == null)) {
			$.dialog.tips("请输入供应商全称", 1, "alert.gif", function() {
				$("#fullname").focus();
			});
			return false;
		}
		return true;
	}
	
	function saveSupplier() {
		if (!checkVal()) {
			return;
		}
		$("#addForm").attr("action", "supplier/save");
	    $("#addForm").submit();
	}
	
	function goBack(){
		parent.closeDialog();
	}
	
	$(function () {
	   checkLength('introduceinfo',300);
       var returninfo = '${supplier.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
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
