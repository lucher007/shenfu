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
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="description" content="">
	<meta name="keywords" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>申亚科技</title>
	<!-- Set render engine for 360 browser -->
	<meta name="renderer" content="webkit">
	<!-- No Baidu Siteapp-->
	<meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="icon" type="image/png" href="shopping/AmazeUI-2.4.2/assets/i/favicon.png">
	<!-- Add to homescreen for Chrome on Android -->
	<meta name="mobile-web-app-capable" content="yes">
	<link rel="icon" sizes="192x192" href="shopping/AmazeUI-2.4.2/assets/i/app-icon72x72@2x.png">
	<!-- Add to homescreen for Safari on iOS -->
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-title" content="Amaze UI"/>
	<link rel="apple-touch-icon-precomposed" href="shopping/AmazeUI-2.4.2/assets/i/app-icon72x72@2x.png">
	<!-- Tile icon for Win8 (144x144 + tile color) -->
	<meta name="msapplication-TileImage" content="shopping/AmazeUI-2.4.2/assets/i/app-icon72x72@2x.png">
	<meta name="msapplication-TileColor" content="#0e90d2">
	<link rel="stylesheet" href="shopping/AmazeUI-2.4.2/assets/css/amazeui.min.css">
	<link rel="stylesheet" href="shopping/AmazeUI-2.4.2/assets/css/app.css">
</head>

<body>
	<form class="am-form am-form-horizontal" id="addForm" method="post" >
	    <input type="hidden" id="visittype" name="visittype" value="1">
	    <input type="hidden" id="source" name="source" value="0">
	    <input type="hidden" id="salercode" name="salercode" value="${Saler.employeecode}">
		
		<header data-am-widget="header" class="am-header am-header-default am-header-fixed">
	       <div class="am-header-left am-header-nav">
	          <a href="javascript:rebackMain();" class="">
	              <img class="am-header-icon-custom" src="data:image/svg+xml;charset&#x3D;utf-8,&lt;svg xmlns&#x3D;&quot;http://www.w3.org/2000/svg&quot; viewBox&#x3D;&quot;0 0 12 20&quot;&gt;&lt;path d&#x3D;&quot;M10,0l2,2l-8,8l8,8l-2,2L0,10L10,0z&quot; fill&#x3D;&quot;%23fff&quot;/&gt;&lt;/svg&gt;" alt=""/>
	              <span class="am-header-nav-title">返回 </span>
	          </a>
	       </div>
	       <h1 class="am-header-title">客户信息</h1>
	  	</header>
	  	<div style="height: 15px;"></div>
	  	<div class="am-form-group">
			<label for="username" class="am-form-label am-u-sm-2" >姓   名</label>
			<div class="am-form-content am-u-sm-10">
				<input type="text" id="username" name="username" value="${user.username}" maxlength="50" placeholder="请输入姓名" class="am-form-field" required/>
			</div>
		</div>
	  	<div class="am-form-group">
			<label for="phone" class="am-form-label am-u-sm-2">电  话</label> 
			<div class="am-form-content am-u-sm-10">
				<input type="tel" id="phone" name="phone" value="${user.phone}" maxlength="11" placeholder="请输入电话号码" class="am-form-field" required/>
			</div>
		</div>
		<div class="am-form-group">
			<label for="address" class="am-form-label am-u-sm-2">地  址</label>
			<div class="am-form-content am-u-sm-10">
				<input type="text" id="address" name="address" value="${user.address}"  placeholder="请输入安装地址" class="am-form-field" required/>
			</div>
		</div>
		<div style="height: 15px;"></div>
		<div class="info-btn">
			<button type="button" class="am-btn am-alert am-btn-block" style="height: 50px;" onclick="javascript:saveUserBuyOrder();">下一步</button>
		</div>
		<div style="height: 15px;"></div>
	</form>
	
	<!--[if (gte IE 9)|!(IE)]><!-->
	<script type="text/javascript" src="shopping/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
	<!--<![endif]-->
	<!--[if lte IE 8 ]>
	<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
	<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
	<![endif]-->
	<script type="text/javascript" src="shopping/AmazeUI-2.4.2/assets/js/amazeui.min.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript">
	
	function checkVal() {
		
		if ($("#username") != undefined && ($("#username").val() == "" || $("#username").val() == null )) {
			$("#username").focus();
			$.dialog.tips("请输入姓名", 1, 'alert.gif');
			return false;
		}
		
		if ($("#phone") != undefined && ($("#phone").val() == "" || $("#phone").val() == null )) {
			$("#phone").focus();
			$.dialog.tips("请输入电话号码", 1, 'alert.gif');
			return false;
		}
		
		if ($("#address") != undefined && ($("#address").val() == "" || $("#address").val() == null )) {
			$("#address").focus();
			$.dialog.tips("请输入安装地址", 1, 'alert.gif');
			return false;
		}
		
		return true;
	}
	
	function saveUserBuyOrder() {
		if (!checkVal()) {
			return;
		}
		$("#addForm").attr("action", "sale/saveUserAddOrderForVisittype1_userinfo?rid="+Math.random());
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
	
	</script>
</body>
</html>
