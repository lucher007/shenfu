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
<style type="text/css">
.fb-con table tr {
	height: 30px;
}
</style>
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm">
			<div class="form-box">
				<div class="fb-tit">产品颜色增加</div>
				<div class="fb-con">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="30" width="15%" align="right">颜色名称：</td>
							<td width="25%"><input type="text" class="inp" name="color" id="color" value="${productcolor.color }"> <span class="red">*</span></td>
						</tr>
					</table>
				</div>
				<div class="fb-bom">
					<cite> <input type="button" class="btn-back" value="返回" onclick="goback();" />
					 <input type="button" class="btn-save" value="保存" onclick="saveProductcolor();"
					/> </cite> <span class="red">${productcolor.returninfo}</span>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/comtools.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript">
	
	function checkVal() {
		if ($("#color") != undefined && ($("#color").val() == "" || $("#color").val() == null )) {
			$.dialog.tips("请输入产品颜色", 1, "alert.gif", function() {
				$("#color").focus();
			});
			return false;
		}
		
		return true;
	}
	
	function saveProductcolor() {
		if (!checkVal()) {
			return;
		}
		$("#addForm").attr("action", "productcolor/save");
	    $("#addForm").submit();
	}
	
	function goback(){
		parent.closeDialog();
	}
	
	$(function () {
       var returninfo = '${productcolor.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });

</script>
</body>
</html>
