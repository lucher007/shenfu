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
	<link rel="stylesheet" href="shopping/AmazeUI-2.4.2/assets/css/app.css">
	<link rel="stylesheet" href="style/mobilebusiness/validateCss.css">
</head>

<body>
	<div>
		<form class="am-form am-form-horizontal" data-am-validator id="addForm" method="post" value="">
		    <input type="hidden" id="encrypedPwd" name="encrypedPwd">
		    <header data-am-widget="header" class="am-header am-header-default my-header">
		       <h1 class="am-header-title">员工登录</h1>
		    </header>
		    <div style="height: 15px;"></div>
            <div class="am-form-group ">
            	<div class="field am-input-group am-input-group-primary">
            		<span class="am-input-group-label"><i class="am-icon-phone am-icon-fw"></i></span>
			      	<input type="tel" name="phone" id="phone" value="${requestScope.phone}" placeholder="请输入您的电话号码" data-validate="required:账号不能为空">
			     </div>
	        </div>
	       
	        <div class="am-form-group ">
			      <div class="field am-input-group am-input-group-primary">
			      	<span class="am-input-group-label"><i class="am-icon-lock am-icon-fw"></i></span>
			      	<input type="password" name="password" id="password" value="${requestScope.password}" placeholder="请输入您的登录密码" data-validate="required:密码不能为空">
			      </div>
	        </div>
	      
	        <div class="am-form-group">
			      <div class="field am-input-group am-input-group-primary">
			      	<span class="am-input-group-label"><i class="am-icon-get-pocket am-icon-fw"></i></span>
			      	<input type="text" name="logincode" id="logincode" value="${requestScope.logincode}" placeholder="请输入验证码" data-validate="required:验证码不能为空">
			      	<span class="am-input-group-btn">
			      		 <img src="<%=basePath %>images" id="checkcode" id="checkcode"  name="checkcode" style="width: 100px;height: 30px;" />
			      	</span>
			      </div>
	        </div>
            <small class="tooltips" style="color: red;" <c:if test="${requestScope.errorFlags== null || !requestScope.errorFlags}">style="display:none;"</c:if> >${requestScope.message }</small>
            <div style="height: 15px;"></div>
            <p class="am-text-center">
            	<button type="button" class="am-btn am-btn-primary am-radius am-btn-block" style="height: 50px;" onclick="doLoginAjax()">立即登录</button>
            </p>
		    
		    <footer data-am-widget="footer" class="am-footer am-footer-default" data-am-footer="{  }">
		        <hr data-am-widget="divider" style="" class="am-divider am-divider-default"/>
		      <div class="am-footer-miscs ">
		        <p>Copyright©2016 www.shenyatech.com</p>
		        <p>蜀ICP备81002229号</p>
		      </div>
		    </footer>
		</form>
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
	<script type="text/javascript" src="js/mobilebusiness/validateJs.js"></script>
	<script type="text/javascript" src="js/loginencryption/security.js" ></script> 
	<script type="text/javascript">
		//刷新验证码
		$("#checkcode").click(function(){
		 	var imgsrc=$(this).attr("src");
			$(this).attr("src",imgsrc+"?requestFlag="+new Date().getTime());
		});
	
		 <%  
	     HashMap<String, Object> map = RSAUtils.getKeys();    
	     //生成公钥和私钥    
	     RSAPublicKey publicKey = (RSAPublicKey) map.get("public");    
	     RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");  
	       
	     session.setAttribute("privateKey", privateKey);//私钥保存在session中，用于解密  
	       
	     //公钥信息保存在页面，用于加密  
	     String publicKeyExponent = publicKey.getPublicExponent().toString(16);  
	     String publicKeyModulus = publicKey.getModulus().toString(16);  
	     request.setAttribute("publicKeyExponent", publicKeyExponent);  
	     request.setAttribute("publicKeyModulus", publicKeyModulus);  
	   %>  
		
		function doLoginAjax(){
			
			var phone = $.trim($("#phone").val());
			var password = $.trim($("#password").val());
			var logincode = $.trim($("#logincode").val());
			
			RSAUtils.setMaxDigits(200);  
	        //setMaxDigits(256);  
	        var key = new RSAUtils.getKeyPair("${publicKeyExponent}", "", "${publicKeyModulus}");  
	        var encrypedPwd = RSAUtils.encryptedString(key,password); 
	        $("#encrypedPwd").val(encrypedPwd);
	        
	        $("#addForm").attr("action", "mobilebusiness/login?rid="+Math.random());
			$("#addForm").submit();
		}
		
	</script>
</body>
</html>
