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
			<div class="easyui-panel" title="充值VIP信息" style="width:100%;" data-options="footer:'#ft'">
					<table width="100%">
						<tr>
							<td height="30" width="15%" align="right">充值VIP编码：</td>
							<td width="25%"><input type="text" class="inp" name="rechargevipcode" id="rechargevipcode" value="${rechargevip.rechargevipcode }"> <span class="red">*</span></td>
						</tr>
						<tr>
							<td height="30" width="15%" align="right">充值VIP名称：</td>
							<td width="25%"><input type="text" class="inp" name="rechargevipname" id="rechargevipname" value="${rechargevip.rechargevipname }"> <span class="red">*</span></td>
						</tr>
						<tr>
							<td align="right">充值VIP金额：</td>
							<td>
								<input type="text" class="inp easyui-numberbox" name="rechargemoney" id="rechargemoney" value="${rechargevip.rechargemoney }" maxlength="8">
							</td> 
						</tr>
						<tr>
							<td align="right">每月提成次数：</td>
							<td>
								<input type="text" class="inp easyui-numberbox" name="gaintimes" id="gaintimes" value="${rechargevip.gaintimes }">
							</td>
						</tr>
					</table>
				</div>
				<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveRechargevip();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
				    </cite><span class="red">${rechargevip.returninfo}</span>
				</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript">
	
	function checkVal() {
		if ($("#rechargevipcode") != undefined && ($("#rechargevipcode").val() == "" || $("#rechargevipcode").val() == null )) {
			$.dialog.tips("请输入充值VIP编码", 1, "alert.gif", function() {
				$("#rechargevipcode").focus();
			});
			return false;
		}
		
		if ($("#rechargevipname") != undefined && ($("#rechargevipname").val() == "" || $("#rechargevipname").val() == null )) {
			$.dialog.tips("请输入充值VIP名称", 1, "alert.gif", function() {
				$("#rechargevipname").focus();
			});
			return false;
		}
		
		return true;
	}
	
	function saveRechargevip() {
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
		    	$("#addForm").attr("action", "rechargevip/save?rid="+Math.random());
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
		
	}
	
	function goback(){
		parent.closeDialog();
	}
	
	$(function () {
       var returninfo = '${rechargevip.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>