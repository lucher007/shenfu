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
.easyui-panel table tr {
	height: 30px;
}
</style>
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm">
			<div class="easyui-panel" title="类别信息" style="width:100%;" data-options="footer:'#ft'">
					<table width="100%">
						<tr>
							<td height="30" width="15%" align="right">类别编码：</td>
							<td width="25%"><input type="text" class="inp" name="typecode" id="typecode" value="${producttype.typecode }"> <span class="red">*</span></td>
						</tr>
						<tr>
							<td height="30" width="15%" align="right">类别名称：</td>
							<td width="25%"><input type="text" class="inp" name="typename" id="typename" value="${producttype.typename }"> <span class="red">*</span></td>
						</tr>
					</table>
				</div>
				<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveProducttype();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
				    </cite><span class="red">${producttype.returninfo}</span>
				</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript">
	
	function checkVal() {
		if ($("#typecode") != undefined && ($("#typecode").val() == "" || $("#typecode").val() == null )) {
			$.dialog.tips("请输入类别编码", 1, "alert.gif", function() {
				$("#typecode").focus();
			});
			return false;
		}
		
		if ($("#typename") != undefined && ($("#typename").val() == "" || $("#typename").val() == null )) {
			$.dialog.tips("请输入类别名称", 1, "alert.gif", function() {
				$("#typename").focus();
			});
			return false;
		}
		
		return true;
	}
	
	function saveProducttype() {
		if (!checkVal()) {
			return;
		}
		$("#addForm").attr("action", "producttype/save");
	    $("#addForm").submit();
	}
	
	function goback(){
		parent.closeDialog();
	}
	
	$(function () {
       var returninfo = '${producttype.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });

</script>
</body>
</html>
