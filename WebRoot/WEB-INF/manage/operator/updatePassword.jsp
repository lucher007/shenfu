<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		<form action="" method="post" id="searchform" name="searchform">
		  <input type="hidden" name="id" id="id" value="${operator.operator.id }"/>
		  <input type="hidden" name="loginname" id="loginname" value="${operator.operator.loginname }"/>
          <div class="easyui-panel" title="操作员修改" style="width:100%;" data-options="footer:'#ft'">
		        <table style="width:100%">
				    <tr>
					    <td align="right">新密码：</td>
					    <td>
                           <input type="password" class="inp" name="password" id="password"> <span class="red">*</span>
                        </td>
				    </tr>
					<tr>
					    <td align="right">确认密码：</td>
					    <td>
                  				<input type="password" class="inp" name="confirmPassword" id="confirmPassword"> <span class="red">*</span>
                				</td>
					</tr>
		        </table>
	        </div>
	        <div id="ft" style="padding:5px;">
				<cite> 
					<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
					<a href="javascript:updatePassword();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
			    </cite><span class="red">${operator.returninfo}</span>
			</div>
    </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">
	
	function updatePassword(){
		if (!checkVal()){
			return false;
		}
		var url = "operator/updatePassword";
		if ("${operator.remark}" == "dialog") {
			url += "?remark=dialog";
		}
		$("#searchform").attr("action", url).submit();
	}
	
	function checkVal() {
		if ("${operator.remark}" == "dialog") {
			if ($("#oldPassword") != undefined && $("#oldPassword").val() == "") {
				$.dialog.tips('请输入旧密码', 1, "alert.gif", function() {
					$("#oldPassword").focus();
				});
				return false;
			}
		}
		if($("#password") != undefined && $("#password").val() == ""){
			$.dialog.tips('请输入新密码', 1, "alert.gif", function() {
				$("#password").focus();
			});
			return false;
		}
		
		if($("#confirmPassword") != undefined && $("#confirmPassword").val() == ""){
			$.dialog.tips('请输入确认密码', 1, "alert.gif", function() {
				$("#confirmPassword").focus();
			});
			return false;
		}
		if($("#password").val() != $("#confirmPassword").val()) {
			$.dialog.tips('俩次输入密码不一样', 1, "alert.gif", function() {
				$("#confirmPassword").focus();
			});
			return false;
		}
		
		return true;
	}
	
	$(function() {
		if ("${remark }" == "dialog") {
			$("body").css({ "width": "470px", "padding": "0" });
			$(".cur-pos").css("display", "none");
			$(".form-box").css("margin-top", "15px");
			$("#goback").on("click", function() {
				closeDialog();
			});
			if ("${operator.returninfo }" != null && "${operator.returninfo }" != "") {
				$.dialog.tips("${operator.returninfo }", 1, "alert.gif");
			}
		} else {
			if ("${operator.returninfo }" != null && "${operator.returninfo }" != "") {
				$.dialog.tips("${operator.returninfo }", 1, "alert.gif");
			}
		}
	});
	
	function goback() {
		$("#searchform").attr("action", "operator/updateInit").submit();
	}
	
	function closeDialog(){
		parent.passwordDialog.close();
	}
</script>   
</body>
</html>
