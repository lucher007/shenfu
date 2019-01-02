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
	<link rel="stylesheet" href="style/mobilebusiness/form.css">
	<link rel="stylesheet" type="text/css" href="style/easyui/easyui.css">
	<style type="text/css">
	
    </style>
</head>

<body>
	<form method="post" id="addForm" name="addForm" class="am-form am-form-horizontal">
		<input type="hidden" name="ordercode" id="ordercode" value="${userorder.userorder.ordercode}"/>
		<input type="hidden" name="jumpurl" id="jumpurl" value="${userorder.jumpurl}"/>
		<header data-am-widget="header" class="am-header am-header-default am-header-fixed">
		       <div class="am-header-left am-header-nav">
		          <a href="${userorder.jumpurl}?ordercode=${userorder.userorder.ordercode}" class="">
		              <img class="am-header-icon-custom" src="data:image/svg+xml;charset&#x3D;utf-8,&lt;svg xmlns&#x3D;&quot;http://www.w3.org/2000/svg&quot; viewBox&#x3D;&quot;0 0 12 20&quot;&gt;&lt;path d&#x3D;&quot;M10,0l2,2l-8,8l8,8l-2,2L0,10L10,0z&quot; fill&#x3D;&quot;%23fff&quot;/&gt;&lt;/svg&gt;" alt=""/>
		              <span class="am-header-nav-title">返回 </span>
		          </a>
		       </div>
		       <h1 class="am-header-title">产品价格</h1>
		</header>
		<div class="am-form-group" >
			<label for="username" class="am-form-label am-u-sm-6" >订单编号：</label>
			<div class="am-form-content am-u-sm-6" align="right">
				${userorder.userorder.ordercode}
			</div>
		</div>
		<div class="am-form-group">
			<label for="username" class="am-form-label am-u-sm-6" >客户姓名：</label>
			<div class="am-form-content am-u-sm-6" align="right">
				${userorder.userorder.username}
			</div>
		</div>
		
		<div class="am-form-group">
			<label for="username" class="am-form-label am-u-sm-6" >客户原价格：</label>
			<div class="am-form-content am-u-sm-6" align="right">
				￥${userorder.userorder.productfee}
			</div>
		</div>
		
		<div class="am-form-group">
			<label for="newproductfee" class="am-form-label am-u-sm-6">产品新价格：</label>
			<div class="am-form-content am-u-sm-6">
				<input type="text" id="newproductfee" name="newproductfee" value="" class="am-form-field" onkeyup="checkNum(this)" onkeypress="checkNum(this)" onblur="checkNum(this)" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
		 	</div>
		</div>
		
		<c:if test="${userorder.returninfo ne null && userorder.returninfo ne ''}">
			<div id="returninfo"  class="am-alert am-alert-success" data-am-alert >
		 		 <p>${userorder.returninfo}</p>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" /> 
		</c:if>  
			
		<div class="info-btn">
			<button type="button" class="am-btn am-alert am-btn-block" style="height: 50px;" onclick="javascript:updateProductfee();">提交</button>
		</div>
		
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
	<script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript">
		 function checkVal() {
		  if ($("#newproductfee") != undefined && ($("#newproductfee").val() == "" || $("#newproductfee").val() == null )) {
				$.dialog.tips("请输入产品新价格", 1, "alert.gif", function() {
					$("#newproductfee").focus();
				});
				return false;
			}
			return true;
		}	
	
		function updateProductfee() {
			
			if (!checkVal()) {
				return;
			}
			
			$("#addForm").attr("action", "mobilebusiness/updateProductfee?rid="+Math.random());
			$("#addForm").submit();
		}
	</script>
</body>
</html>
