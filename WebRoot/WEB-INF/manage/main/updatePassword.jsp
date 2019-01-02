<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
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
<style type="text/css">
.fb-con table {
	width: 100%;
}

.fb-con table tr td:first-child {
	height: 30px;
	width: 40%;
	text-align: right;
}

.inp {
	width: 150px;
}
</style>
</head>
<body>
	<div id="body">
		<form action="" method="post" id="searchform" name="searchform">
				<input type="hidden" name="id" id="id" value="${operator.operator.id }" /> 
			<div class="form-box">
				<div class="fb-tit">密码修改</div>
				<div class="fb-con">
					<table>
						<tr>
							<td>旧密码：</td>
							<td><input type="password" class="inp" name="oldPassword" id="oldPassword"> <span class="red">*</span></td>
						</tr>
						
						<tr>
							<td>新密码：</td>
							<td><input type="password" class="inp" name="password" id="password"> <span class="red">*</span></td>
						</tr>
						
						<tr>
							<td>确认密码：</td>
							<td><input type="password" class="inp" name="confirmPassword" id="confirmPassword"> <span class="red">*</span></td>
						</tr>
					
				  </table>
				</div>
				
				<div class="fb-bom">
					<cite> 
					<input type="button" class="btn-back" value="返回" onclick="goBack()"> 
					<input type="button" class="btn-save" value="保存" onclick="updatePassword();"/> 
					</cite> 
					<span class="red">${operator.returninfo }</span>
				</div>
			
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript">
		function updatePassword() {
			if (!checkVal()) {
				return false;
			}

			$("#searchform").attr("action", "menu/updatePassword").submit();
		}

		function checkVal() {
				if ($("#oldPassword") != undefined && $("#oldPassword").val() == "") {
					$.dialog.tips('请输入旧密码', 1, "alert.gif", 
						function() {
							$("#oldPassword").focus();
						});
					return false;
				}
			
			if ($("#password") != undefined && $("#password").val() == "") {
				$.dialog.tips(
						'请输入新密码',1, "alert.gif", function() {
							$("#password").focus();
						});
				return false;
			}

			if ($("#confirmPassword") != undefined && $("#confirmPassword").val() == "") {
				$.dialog.tips('请输入确认密码',1, "alert.gif",
					 function() {
						$("#confirmPassword").focus();
					});
				return false;
			}
			if ($("#password").val() != $("#confirmPassword").val()) {
				$.dialog.tips('俩次输入的密码不一样',1, "alert.gif", 
					function() {
						$("#confirmPassword").focus();
					});
				return false;
			}

			return true;
		}

		$(function() {
			if ("${remark }" == "dialog") {
				$("body").css({
					"width" : "470px",
					"padding" : "0"
				});
				$(".cur-pos").css("display", "none");
				$(".form-box").css("margin-top", "15px");
				$("#goback").on("click", function() {
					closeDialog();
				});
				if ("${operator.returninfo }" != null
						&& "${operator.returninfo }" != "") {
					$.dialog.tips("${operator.returninfo }", 1, "alert.gif");
				}
			} else {
				if ("${operator.returninfo }" != null
						&& "${operator.returninfo }" != "") {
					$.dialog.tips("${operator.returninfo }", 1, "alert.gif");
				}
			}
		});

		function goBack() {
			$("#searchform").attr("action", "menu/updateInit").submit();
		}

		function closeDialog() {
			parent.passwordDialog.close();
		}
	</script>
</body>
</html>
