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
	
	table.u {
		list-style-type:none;
		font-size: 20px;
		}
	input{ 
		border:0;
		font-size: 20px;
		 }
		 
	.button{ 
		font-size: 20px;
		background:blue;
		 }
		 
	table.b{
		list-style-type:none;
		font-size: 20px;
		width:100%;
		height:100%;
		}
	button.b{
		list-style-type:none;
		font-size: 20px;
		width:100%;
		height:100%;
		color: #fff;
		background: #0e90d2;
		}
		
	button.c{
		list-style-type:none;
		font-size: 20px;
		width:100%;
		height:100%;
		color: #fff;
		background: #c9c9cb;
		}
		
	/*沉底按钮*/
	.my-foot2-fixed {
		    position: fixed;
		    bottom: 0;
		    left: 0;
		    right: 0;
		    width: 100%;
		    z-index: 1010;
		    height: 50px; 
		    background: white; 
		    color: white;
			}
    </style>
</head>

<body>
	<form method="post" id="addForm" name="addForm" >
		<input type="hidden" name="id" id="id" value="${cellstation.cellstation.id}"/>
		<input type="hidden" name="extendcode" id="extendcode" value="${cellstation.cellstation.extendcode}"/>
		<input type="hidden" name="stationcode" id="stationcode" value="${cellstation.cellstation.stationcode}"/>
		<header data-am-widget="header" class="am-header am-header-default am-header-fixed">
		       <div class="am-header-left am-header-nav">
		          <a href="mobilebusiness/lookCellextend?extendcode=${cellstation.cellstation.extendcode}" class="">
		              <img class="am-header-icon-custom" src="data:image/svg+xml;charset&#x3D;utf-8,&lt;svg xmlns&#x3D;&quot;http://www.w3.org/2000/svg&quot; viewBox&#x3D;&quot;0 0 12 20&quot;&gt;&lt;path d&#x3D;&quot;M10,0l2,2l-8,8l8,8l-2,2L0,10L10,0z&quot; fill&#x3D;&quot;%23fff&quot;/&gt;&lt;/svg&gt;" alt=""/>
		              <span class="am-header-nav-title">返回 </span>
		          </a>
		       </div>
		       <h1 class="am-header-title">申请驻点信息</h1>
		</header>
		<div style="padding-bottom: 50px; ">
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="phone" class="am-form-label am-u-sm-3">楼盘名称</label> 
				<div class="am-form-content am-u-sm-9" align="right">
					${cellstation.cellstation.cellname}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-3">地址</label>
				<div class="am-form-content am-u-sm-9" align="right">
					${cellstation.cellstation.address}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-3">申请时间</label>
				<div class="am-form-content am-u-sm-9" align="right">
					${cellstation.cellstation.addtime==null?"":fn:substring(cellstation.cellstation.addtime, 0, 19)}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-3">开始时间</label>
				<div class="am-form-content am-u-sm-9" align="right">
					${cellstation.cellstation.starttime==null?"":fn:substring(cellstation.cellstation.starttime, 0, 10)}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-3">结束时间</label>
				<div class="am-form-content am-u-sm-9" align="right">
					${cellstation.cellstation.endtime==null?"":fn:substring(cellstation.cellstation.endtime, 0, 10)}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-6">驻点人员数量</label>
				<div class="am-form-content am-u-sm-6" align="right">
					${cellstation.cellstation.talkernumber}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group" onclick="javascript:findEmployeeListByStationcode();">
				<label for="address" class="am-form-label am-u-sm-6">驻点人员信息</label>
				<div class="am-form-content am-u-sm-6" align="right">
					点击查看&nbsp;&nbsp;<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-6">驻点订单信息</label>
				<div class="am-form-content am-u-sm-6" align="right">
					点击查看&nbsp;&nbsp;<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
				</div>
			</div>
			
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<c:if test="${cellstation.returninfo ne null && cellstation.returninfo ne ''}">
				<div id="returninfo"  class="am-alert am-alert-success" data-am-alert >
			 		 <p>${cellstation.returninfo}</p>
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
		
		function savePaymentInfo(payitem) {
			var extendcode = $("#extendcode").val(); 
			$("#addForm").attr("action", "mobilebusiness/savePaymentInfoForCellextendInit?rid="+Math.random());
			$("#addForm").submit();
		}
		
		function saveCellstation() {
			var extendcode = $("#extendcode").val(); 
			$("#addForm").attr("action", "mobilebusiness/saveCellstationInit?rid="+Math.random());
			$("#addForm").submit();
		}
		
		function lookCellstation() {
			var extendcode = $("#extendcode").val(); 
			$("#addForm").attr("action", "mobilebusiness/lookCellstation?rid="+Math.random());
			$("#addForm").submit();
		}
		
		function findEmployeeListByStationcode() {
			var extendcode = $("#extendcode").val(); 
			$("#addForm").attr("action", "mobilebusiness/findEmployeeListByStationcode?rid="+Math.random());
			$("#addForm").submit();
		}
		
	</script>
</body>
</html>
