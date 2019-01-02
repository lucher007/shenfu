<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page   import= "com.sykj.shenfu.common.loginencryption.* "%>
<%@ page   import= "java.security.interfaces.RSAPrivateKey "%>
<%@ page   import= "java.security.interfaces.RSAPublicKey "%>
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
	<link rel="stylesheet" href="style/mobilebusiness/style.css">
	<style type="text/css">
	
    </style>
	
</head>

<body>
	<header data-am-widget="header" class="am-header am-header-default my-header am-header-fixed" style="background-color: #0e90d2;">
		       <h1 class="am-header-title" id="head_title">销售</h1>
	</header>
	
	<div id="widget-list">
		<ul class="am-list m-widget-list">
	 	     <li>
	 	     	<a href="mobilebusiness/addUserorderInit" data-rel="订单录入">
	 	     		<span class="widget-name">&nbsp;&nbsp;&nbsp;&nbsp;订单录入</span>
	 	     		<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
	 	     	</a>
	 	     </li>
	 	     <li>
	 	     	<a href="mobilebusiness/manageUserorder" data-rel="订单管理">
	 	     		<span class="widget-name">&nbsp;&nbsp;&nbsp;&nbsp;订单管理</span>
	 	     		<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
	 	     	</a>
	 	     </li>
	 	     <li>
	 	     	<a href="mobilebusiness/manageUsertask" data-rel="任务单管理">
	 	     		<span class="widget-name">&nbsp;&nbsp;&nbsp;&nbsp;任务单管理</span>
	 	     		<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
	 	     	</a>
	 	     </li>
		</ul>
	</div>
	
	<!--底部-->
    <div data-am-widget="navbar" class="am-navbar am-cf my-nav-footer " id="">
      <ul class="am-navbar-nav am-cf am-avg-sm-4 my-footer-ul">
        <li>
          <a href="mobilebusiness/activityinfoInit" class="">
            <span class="am-icon-archive"></span>
            <span class="am-navbar-label">活动</span>
          </a>
        </li>
        <li>
          <a href="mobilebusiness/saleinfoInit" class="" style="background-color: black;">
            <span class="am-icon-money"></span>
            <span class="am-navbar-label">销售</span>
          </a>
        </li>
        <li>
          <a href="mobilebusiness/teaminfoInit" class="">
            <span class="am-icon-user-plus"></span>
            <span class="am-navbar-label">团队</span>
          </a>
        </li>
       <li>
          <a href="mobilebusiness/employeeinfoInit" class="">
            <span class=" am-icon-user"></span>
            <span class="am-navbar-label">我的</span>
          </a>
        </li>
      </ul>
    </div>
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
	function showFooterNav(){
		alert('a');
		$("#footNav").toggle();
	}
	</script>
</body>
</html>
