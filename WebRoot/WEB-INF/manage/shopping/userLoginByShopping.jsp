<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
	<base href="<%=basePath%>" />
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">
	<title>客户登录</title>
	<link type="text/css" rel="stylesheet"  href="shopping/AmazeUI-2.4.2/assets/css/admin.css" >
	<link type="text/css" rel="stylesheet"  href="shopping/AmazeUI-2.4.2/assets/css/amazeui.css" >
	<link type="text/css" rel="stylesheet"  href="shopping/css/personal.css" >
	<link type="text/css" rel="stylesheet"  href="shopping/css/infstyle.css" >
	<script type="text/javascript" src="shopping/AmazeUI-2.4.2/assets/js/jquery.min.js" ></script>
	<script type="text/javascript" src="shopping/AmazeUI-2.4.2/assets/js/amazeui.js" ></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
</head>

<body>
	<div class="info-main">
		<form class="am-form am-form-horizontal data-am-validator" id="addForm" method="post">
		    <input type="hidden" id="visittype" name="visittype" value="0">
		    <input type="hidden" id="source" name="source" value="0">
		    <input type="hidden" id="salercode" name="salercode" value="${Saler.employeecode}">
			<!--
			<div class="am-form-group">
				<label for="user-name" class="am-form-label">姓名</label>
				<div class="am-form-content">
					<input type="text" id="username" name="username" minlength="10" placeholder="输入姓名" class="am-form-field" required/>
				</div>
			</div>
			-->
			<div class="am-form-group">
				<label for="user-phone" class="am-form-label">电话号码</label> 
				<div class="am-form-content">
					<input type="tel" id="phone" name="phone" maxlength="11" placeholder="输入电话号码" class="am-form-field" required/>
				</div>
			</div>
			
			<div style="height: 15px;"></div>
			<div class="info-btn">
				<button type="button" class="am-btn am-alert am-btn-block" style="height: 50px;"  onclick="javascript:userLoginByShopping();">登录</button>
			</div>
				
			<div style="height: 15px;"></div>
			<div class="info-btn">
				<button type="button" class="am-btn  am-alert-secondary am-btn-block" style="height: 40px;"  onclick="javascript:rebackMain();">返回到主界面</button>
			</div>	
			<!-- 
			<div style="height: 15px;"></div>
			<div class="info-btn">
				<button type="button" class="am-btn  am-alert-secondary am-btn-block" style="height: 40px;"  onclick="javascript:uploadimage();">测试文件上传</button>
			</div>	
				 -->
			<c:if test="${user.returninfo ne null && user.returninfo ne ''}">
				<div id="returninfo"  class="am-alert am-alert-success" data-am-alert >
				  <p>${user.returninfo}</p>
				</div>
			</c:if>
			
			<div style="height: 15px;">
			</div>
			
			<!-- 保存确认框 -->
			<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
			  <div class="am-modal-dialog">
			    <div class="am-modal-hd">个人登录</div>
			    <div class="am-modal-bd">
			     	 确定操作？
			    </div>
			    <div class="am-modal-footer">
			      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
			      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
			    </div>
			  </div>
			</div>
			
		</form>
	</div>
	<script type="text/javascript">
	
	function checkVal() {
		
		if ($("#phone") != undefined && ($("#phone").val() == "" || $("#phone").val() == null )) {
			$("#phone").focus();
			return false;
		}
		
		return true;
	}
	
	function userLoginByShopping() {
		if (!checkVal()) {
			return;
		}
		
		$("#addForm").attr("action", "sale/userLoginByShopping?rid="+Math.random());
		$("#addForm").submit();
		
	}
	
	$(function () {
	       var returninfo = '${user.returninfo}';
	       if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	       }
	 });
	
	function rebackMain() {
		$("#addForm").attr("action", "sale/saleExtendInit?rid="+Math.random()+"&salercode="+$("#salercode").val());
		$("#addForm").submit();
	}
	
	function uploadimage() {
		$("#addForm").attr("action", "sale/uploadimage");
		$("#addForm").submit();
	}
	
	</script>
</body>
</html>
