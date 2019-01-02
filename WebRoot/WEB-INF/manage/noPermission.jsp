<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
  	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <base href="<%=basePath%>" />
    <title>登录失效</title>
    <style type="text/css">
    	html,body{
    		margin: 0;
    		padding:0;
    		height:300px;
    	}
    	.container{
    		margin: 0 auto;
    		height: 300px;
    		padding-top:10px;
    		text-indent:10px;
    	}
    </style>
    <script type="text/javascript">     
	function countDown(secs,surl){     
	 	jumpTo.innerText=secs;     
	 	if(--secs>0){     
	     	setTimeout("countDown("+secs+",'"+surl+"')",1000);     
	    }else{       
	     	window.top.location.href=surl;     
	    }  
	 	//window.top.location.href=surl;    
	}     
	</script> 
  </head>
  <body>
  	<div class="container">
    	<span style="color: red;">你的登录已经失效 或 有其他人登录了你的账户 被迫下线,稍后自动跳转到登陆页面: </span><span id="jumpTo" style="color: blue;">5</span>
		<script type="text/javascript">countDown(5,'<%=request.getContextPath()%>/operator/initLogin');</script>
	</div>
  </body>
</html>
