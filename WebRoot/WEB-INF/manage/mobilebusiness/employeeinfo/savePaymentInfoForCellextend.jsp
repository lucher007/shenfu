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
	<style type="text/css">
	
    </style>
</head>

<body>
	<form method="post" id="addForm" name="addForm">
		<input type="hidden" name="extendcode" id="extendcode" value="${cellpaylog.extendcode}"/>
		<header data-am-widget="header" class="am-header am-header-default am-header-fixed">
		       <div class="am-header-left am-header-nav">
		          <a href="mobilebusiness/lookCellextend?extendcode=${cellpaylog.extendcode}" class="">
		              <img class="am-header-icon-custom" src="data:image/svg+xml;charset&#x3D;utf-8,&lt;svg xmlns&#x3D;&quot;http://www.w3.org/2000/svg&quot; viewBox&#x3D;&quot;0 0 12 20&quot;&gt;&lt;path d&#x3D;&quot;M10,0l2,2l-8,8l8,8l-2,2L0,10L10,0z&quot; fill&#x3D;&quot;%23fff&quot;/&gt;&lt;/svg&gt;" alt=""/>
		              <span class="am-header-nav-title">返回 </span>
		          </a>
		       </div>
		       <h1 class="am-header-title">支付详情</h1>
		       <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
		</header>
		<div style="height: 15px;"></div>
		<div style="padding-bottom: 50px; ">
			<div class="am-form-group">
				<label for="username" class="am-form-label am-u-sm-3" >支付项目</label>
				<div class="am-form-content am-u-sm-9" align="right">
					小区扫楼支付
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="phone" class="am-form-label am-u-sm-3">支付金额</label> 
				<div class="am-form-content am-u-sm-9" align="right">
					<input type="hidden" name="payitem" id="payitem" value="${cellpaylog.paymoney}"/>
					<span style="color: red">￥${cellpaylog.paymoney}</span>
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="username" class="am-form-label am-u-sm-3">支付方式</label>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
						<label class="am-radio"> 
							<div for="phone" class="am-form-label am-u-sm-9" >
								<img alt=""  width="30" height="30" src="images/mobilebusiness/weixin_new.png">
								<span>微信在线支付</span>
							</div> 
							<div class="am-form-content am-u-sm-3" >
								<input type="radio" name="paytype" value="1" data-am-ucheck>
							</div>
						</label>
					</div>
			
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label class="am-radio"> 
					<div for="phone" class="am-form-label am-u-sm-9" >
						<img alt=""  width="30" height="30" src="images/mobilebusiness/weixin_code.png">
						<span>微信扫码支付</span>
					</div> 
					<div class="am-form-content am-u-sm-3" >
						<input type="radio" name="paytype" value="3" data-am-ucheck>
					</div>
				</label>
			</div>
			
			<div class="info-btn">
				<button type="button" class="my-foot1-fixed" onclick="javascript:savePaymentInfo();">立即支付</button>
			</div>
		
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<c:if test="${cellpaylog.returninfo ne null && cellpaylog.returninfo ne ''}">
				<div id="returninfo"  class="am-alert am-alert-success" data-am-alert >
			 		 <p>${cellpaylog.returninfo}</p>
				</div>
				<hr data-am-widget="divider" style="" class="am-divider am-divider-default" /> 
			</c:if>
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
	<script type="text/javascript">
		function savePaymentInfo() {
			var paytype = $('input[name="paytype"]:checked').val();
			if(paytype == null || paytype == ''){
				$.dialog.tips("请选择支付方式", 2, "alert.gif");
				return false;
			}
			
			alert(paytype);
			//$("#addForm").attr("action", "mobilebusiness/updateUserdoorInit?rid="+Math.random()+"&ordercode="+ordercode);
			//$("#addForm").submit();
		}
	</script>
</body>
</html>
