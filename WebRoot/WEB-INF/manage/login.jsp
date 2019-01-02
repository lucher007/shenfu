<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page   import= "com.sykj.shenfu.common.loginencryption.* "%>
<%@ page   import= "java.security.interfaces.RSAPrivateKey "%>
<%@ page   import= "java.security.interfaces.RSAPublicKey "%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html class="no-js">
<head>
<base href="<%=basePath%>">
<title><spring:message code="page.title" /></title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="renderer" content="webkit">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Cache-Control" content="no-siteapp" />
		<base href="<%=basePath%>">
		<title><spring:message code="page.title" /></title>
		<link type="text/css" rel="stylesheet" href="js/plugin/poshytip/tip-yellowsimple/tip-yellowsimple.css">
		<style type="text/css"> 
		    body{
		    	background:url(images/login_img/login_bg.jpg) no-repeat center center fixed;
		    }
		   
		   .titleClass{ 
			       color:white;
		           font-size: 35px;
		           position: absolute;
		           top: 32%;
		           left:22%;
		           width: 56%;
		           text-align:center;
		    }  
		    
		    .backgroundClass1{
		    	_background:none; 
		        position:absolute;left:45%;top:40%;width:400px;height:220px;
		    }
		   
		    .backgroundClass2{
		    	background:url(images/login_img/login_bg2.png) repeat; 
		        _background:none; 
		        _filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='' , sizingMethod='scale' ); 
		        } 
		    .loginnameClass{
		        margin-left:50px;height:20px;width:200px; background-color:transparent;border:0px; color:white;
		    }
		    .passwordClass{
		        margin-left:50px;height:20px;width:200px; background-color:transparent;border:0px; color:white;
		    }
		    .codeClass{
		        margin-left:50px;height:20px;width:110px; background-color:transparent;border:0px; color:white;
		    }
		    .languageClass{
		        margin-left:50px;height:20px;width:100px; background-color:transparent;border:0px; color:white;
		    }
		    .loginbuttonClass{
		        background:url(images/login_img/login_button.png) repeat;height:25px;width:200px; border:0px; color:white; margin-left:150px;
		        font-size:15px;
		        font-family:'Microsoft Yahei','宋体',Verdana,Arial,Helvetica, sans-serif;
		    }
		</style> 
	</head>
  
<body>
	<form method="post" action="operator/logined" name="dataform" id="dataform">
		<div class="titleClass"><strong>申亚科技管理系统</strong></div>
  		<div class="backgroundClass1" style="display:block;overflow:hidden;">
  			<div class="backgroundClass2" style="position:relative;margin-top:40px;margin-left:100px;width:250px;height:25px;padding-top:-55px;">
  			 	<img src="images/login_img/login_name.png" style="position:absolute;top:20px;margin-left: 12px;margin-top: -20px;"/>
  			 	<input type="text" name="loginname" id="loginname" class="loginnameClass" onkeypress="onKeyInput2(event)" />
  			</div>
  			<div class="backgroundClass2" style="position:relative;margin-top:10px;margin-left:100px;width:250px;height:25px;padding-top:-55px;">
  			 	<img src="images/login_img/login_password.png" style="position:absolute;top:20px;margin-left: 12px;margin-top: -20px;"/>
  			 	<input type="password" name="password" id="password"  class="passwordClass" onkeypress="onKeyInput2(event)" />
  			</div>
  			<c:if test="${captcha_check==1}">
	  			<div class="backgroundClass2" style="position:relative;margin-top:10px;margin-left:100px;width:250px;height:25px;padding-top:-55px;">
	  			 	<img src="images/login_img/login_code.png" style="position:absolute;top:20px;margin-left: 12px;margin-top: -20px;"/>
	  			 	<input type="text" name="logincode" id="logincode"  class="codeClass" onkeypress="onKeyInput2(event)"/>
	  				<img src="<%=basePath %>images" id="checkcode" id="checkcode"  name="checkcode" alt="看不见验证码请点击刷新" style="position:absolute;cursor:pointer;margin-left:5px;top:20px;margin-top: -20px;width: 80px;height: 25px;" />
	  			</div>
  			</c:if>
  			<!-- 
  			<div class="backgroundClass2" style="position:relative;margin-top:10px;margin-left:100px;width:50px;height:25px;padding-top:-55px;">
  			 	<img src="images/login_img/login_language.png" style="position:absolute;top:20px;margin-left: 12px;margin-top: -20px;"/>
  			 	<select id="language" name="language" class="languageClass" onchange="languageChange();">
	                  <option style="color: blue" value="zh_CN" <c:if
                      test="${locale=='zh_CN'}">selected</c:if>>中文</option>
	                  <option style="color: blue" value="en_US" <c:if
                      test="${locale=='en_US'}">selected</c:if>>English</option>
	            </select>
  			</div>
  			 -->
  			<div style="position:relative;margin-top:20px;width:250px;height:35px;padding-top:-55px;border:0px solid #000;">
  			 	<input type="button" value="登录" class="loginbuttonClass" onclick="doLoginAjax()"/>
  			</div>
  		</div>
	</form>
	<script type="text/javascript" language="javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" language="javascript" src="js/plugin/poshytip/jquery.poshytip.min.js"></script>
	<script type="text/javascript" language="javascript" src="js/loginencryption/security.js" ></script> 
	<script type="text/javascript">
	
	//初始化需要判断的项
	$('#loginname,#password,#logincode,#checkcode').blur(function(){checkLogin();}).poshytip({
		className: 'tip-yellowsimple',
		showOn: 'none',
		alignTo: 'target',
		alignX: 'right',
		alignY: 'center',
		offsetX: 5
	});

	
	//刷新验证码
	$("#checkcode").click(function(){
	 	var imgsrc=$(this).attr("src");
		$(this).attr("src",imgsrc+"?requestFlag="+new Date().getTime());
	});
	
	function changeAuthImg(){
		var imgsrc=$("#checkcode").attr("src");
		$("#checkcode").attr("src",imgsrc+"?requestFlag="+new Date().getTime());
	}
	
	function onKeyInput2(e) {
		if (event.keyCode == 13) {
			doLoginAjax();
		}
	}
	
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
		
		
		
		if(checkLogin()){
			var loginname = $.trim($("#loginname").val());
			var password = $.trim($("#password").val());
			var logincode = $.trim($("#logincode").val());
			
			RSAUtils.setMaxDigits(200);  
	        //setMaxDigits(256);  
	        var key = new RSAUtils.getKeyPair("${publicKeyExponent}", "", "${publicKeyModulus}");  
	        var encrypedPwd = RSAUtils.encryptedString(key,password); 
	        
			var data = {
							loginname:loginname,
							 password:encrypedPwd,
							logincode:logincode,
					   }
			var url="<%=request.getContextPath()%>/operator/login?rid="+Math.random();
			$.ajax({
			    type: "POST",
			    url: url,
			    data: data, //可选参数
			    dataType: "json",
			    success: function(jsonMsg){
			    				doLoginReturn(jsonMsg,encrypedPwd);
			    			} //可选参数
			});
			
			//$.getJSON(url,data,function(jsonMsg){
			//	doLoginReturn(jsonMsg);
			//});
		}
	}
	
	function doLoginReturn(jsonMsg,encrypedPwd){
		if(jsonMsg.flag=="loginname_error"){
		    $('#loginname').poshytip('update','<b class="red">'+jsonMsg.error +'</b>').poshytip('show');
		}else if(jsonMsg.flag=="password_error"){
			$('#password').poshytip('update','<b class="red">'+jsonMsg.error+'</b>').poshytip('show');
		}else if(jsonMsg.flag=="logincode_error"){
			$('#checkcode').poshytip('update','<b class="red">'+jsonMsg.error+'</b>').poshytip('show');
			changeAuthImg();
		}else if(jsonMsg.flag==0){
			 //登录成功  
            window.location.href = 'operator/logined';
		}
	}
	
	
	function checkLogin(){  
		var loginname = $.trim($("#loginname").val());
		var password = $.trim($("#password").val());
		var logincode = $.trim($("#logincode").val());
		
		if(loginname==""||loginname.length<1||loginname.length>50){
			$('#loginname').poshytip('update','<b class="red">'+(loginname==''?'请输入登录名称':'登录名称不正确')+'</b>').poshytip('show');
			return false;
		}else{
			$('#loginname').poshytip('hide');
		}
		
		if(password==""||password.length<1||password.length>50){
			$('#password').poshytip('update','<b class="red">'+(password==''?'请输入登录密码':'登录密码不正确')+'</b>').poshytip('show');
			return false;
		}else{
			$('#password').poshytip('hide');
		}
		
		if('${captcha_check}'==1){//表示系统需要用验证码
			if(logincode==""||logincode.length<1||logincode.length>4){
				$('#checkcode').poshytip('update','<b class="red">'+(logincode==''?'请输入验证码':'验证码不正确')+'</b>').poshytip('show');
			return false;
			}else{
				$('#checkcode').poshytip('hide');
			}
		}
		return true;		
	}
	
	function languageChange(){  
	    $("#dataform").attr("action", "operator/changeLanguage?locale="+$("#language").val());
		$("#dataform").submit();
	}

</script>
</body>
</html>