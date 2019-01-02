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
	<form class="am-form am-form-horizontal" id="addForm" method="post">
	    <input type="hidden" id="visittype" name="visittype" value="1">
	    <input type="hidden" id="source" name="source" value="0">
	    <input type="hidden" id="salercode" name="salercode" value="${Saler.employeecode}">
	    <input type="hidden" id="username" name="username" value="${user.username}">
	    <input type="hidden" id="phone" name="phone" value="${user.phone}">
	    <input type="hidden" id="address" name="address" value="${user.address}">
		
		<header data-am-widget="header" class="am-header am-header-default am-header-fixed">
	       <div class="am-header-left am-header-nav">
	          <a href="javascript:rebackUserinfo();" class="">
	              <img class="am-header-icon-custom" src="data:image/svg+xml;charset&#x3D;utf-8,&lt;svg xmlns&#x3D;&quot;http://www.w3.org/2000/svg&quot; viewBox&#x3D;&quot;0 0 12 20&quot;&gt;&lt;path d&#x3D;&quot;M10,0l2,2l-8,8l8,8l-2,2L0,10L10,0z&quot; fill&#x3D;&quot;%23fff&quot;/&gt;&lt;/svg&gt;" alt=""/>
	              <span class="am-header-nav-title">返回 </span>
	          </a>
	       </div>
	       <h1 class="am-header-title">产品信息</h1>
	  	</header>
		
		<div class="am-form-group">
			<label for="username" class="am-form-label am-u-sm-3">支付类型</label>
			<label id="username" name="username" class="am-form-label am-u-sm-9" style="text-align: left;">
			   <label  style="color: #226ab6; font-size: 18px; ">货到付款</label>
			</label>
		</div>
		
		 <div class="am-form-group">
	      	 <label for="modelcode"  class="am-form-label am-u-sm-3">产品型号</label>
	      	 <div class="am-u-sm-9">
	      	 	 <select  id="modelcode" name="modelcode"  class="am-u-sm-10" >
	      	 	 	 <option value="">请选择</option>
			         <c:forEach items="${productmodelList}" var="productmodel">  
		               <option value="${productmodel.modelcode}" <c:if test="${user.modelcode eq productmodel.modelcode}">selected</c:if>>${productmodel.modelname}</option>
			         </c:forEach> 
			     </select>
			     <span class="am-form-caret"></span>
	      	 </div>
	    </div>
		
		<div class="am-form-group">
			<label for="productcolor" class="am-form-label am-u-sm-3">产品颜色</label>
			<div class="am-form-content am-u-sm-9">
				<select id="productcolor" name="productcolor">
				  <option value="">请选择</option>
				  <option value="0101" <c:if test="${user.productcolor eq '0101'}">selected</c:if>>摩卡棕</option>
				  <option value="0102" <c:if test="${user.productcolor eq '0102'}">selected</c:if>>石英灰</option>
				  <option value="0103" <c:if test="${user.productcolor eq '0103'}">selected</c:if>>香槟金</option>
				</select>
			</div>
		</div>
		
		<div class="am-form-group" style="display: none">
			<label for="user-name2" class="am-form-label am-u-sm-3">机械锁心</label>
			<div class="am-form-content am-u-sm-9">
					<select id="lockcoreflag" name="lockcoreflag" data-am-selected="{btnWidth: '100%', btnSize: 'lg'}" >
					  <option value="0" <c:if test="${user.lockcoreflag eq '0'}">selected</c:if>>不需要</option>
					  <!-- 
					  <option value="1" <c:if test="${user.lockcoreflag eq '1'}">selected</c:if>>需要</option>
					   -->
					</select>
				<span style="color: #226ab6; text-align: center" >注：选择机械锁心，需额外收费1000元！</span>
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
		
		if ($("#modelcode") != undefined && ($("#modelcode").val() == "" || $("#modelcode").val() == null )) {
			$("#modelcode").focus();
			$.dialog.tips("请选择产品型号", 1, 'alert.gif');
			return false;
		}
		
		if ($("#productcolor") != undefined && ($("#productcolor").val() == "" || $("#productcolor").val() == null )) {
			$("#productcolor").focus();
			$.dialog.tips("请选择产品颜色", 1, 'alert.gif');
			return false;
		}
		
		return true;
	}
	
	function saveUserBuyOrder() {
		if (!checkVal()) {
			return;
		}
		$("#addForm").attr("action", "sale/saveUserAddOrderForVisittype1_productinfo?rid="+Math.random());
		$("#addForm").submit();
	}
	
	$(function () {
	       var returninfo = '${user.returninfo}';
	       if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	       }
	 });
	
	function rebackUserinfo() {
		$("#addForm").attr("action", "sale/userAddOrderInit?rid="+Math.random());
		$("#addForm").submit();
	}
	
	</script>
</body>
</html>
