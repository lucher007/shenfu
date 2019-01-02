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
		<input type="hidden" name="id" id="id" value="${cellextend.cellextend.id}"/>
		<input type="hidden" name="extendcode" id="extendcode" value="${cellextend.cellextend.extendcode}"/>
		<header data-am-widget="header" class="am-header am-header-default am-header-fixed">
		       <div class="am-header-left am-header-nav">
		          <a href="mobilebusiness/findMyCellextendList" class="">
		              <img class="am-header-icon-custom" src="data:image/svg+xml;charset&#x3D;utf-8,&lt;svg xmlns&#x3D;&quot;http://www.w3.org/2000/svg&quot; viewBox&#x3D;&quot;0 0 12 20&quot;&gt;&lt;path d&#x3D;&quot;M10,0l2,2l-8,8l8,8l-2,2L0,10L10,0z&quot; fill&#x3D;&quot;%23fff&quot;/&gt;&lt;/svg&gt;" alt=""/>
		              <span class="am-header-nav-title">返回 </span>
		          </a>
		       </div>
		       <h1 class="am-header-title">楼盘详情</h1>
		</header>
		<div style="padding-bottom: 50px; ">
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="phone" class="am-form-label am-u-sm-3">楼盘名称</label> 
				<div class="am-form-content am-u-sm-9" align="right">
					${cellextend.cellextend.cellname}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-3">总户数</label>
				<div class="am-form-content am-u-sm-9" align="right">
					${cellextend.cellextend.totalhouse}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-3">入住率</label>
				<div class="am-form-content am-u-sm-9" align="right">
					${cellextend.cellextend.cell.userate}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-3">每户单价</label>
				<div class="am-form-content am-u-sm-9" align="right">
					${cellextend.cellextend.price}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-3">扫楼总价</label>
				<div class="am-form-content am-u-sm-9" align="right">
					${cellextend.cellextend.totalmoney}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-3">楼栋数</label>
				<div class="am-form-content am-u-sm-9" align="right">
					${cellextend.cellextend.cell.building}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-3">高低层</label>
				<div class="am-form-content am-u-sm-9" align="right">
					${cellextend.cellextend.cell.highlow}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-6">物业公司</label>
				<div class="am-form-content am-u-sm-6" align="right">
					${cellextend.cellextend.cell.property}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-6">交房时间</label>
				<div class="am-form-content am-u-sm-6" align="right">
					${cellextend.cellextend.cell.handtime==null?"":fn:substring(cellextend.cellextend.cell.handtime, 0, 10)}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-6">驻点参考报价</label>
				<div class="am-form-content am-u-sm-6" align="right">
					
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-3">备注信息</label>
				<div class="am-form-content am-u-sm-9" align="right">
					${cellextend.cellextend.remark}
				</div>
			</div>
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<div class="am-form-group">
				<label for="address" class="am-form-label am-u-sm-3">楼盘地址</label>
				<div class="am-form-content am-u-sm-9" align="right">
					${cellextend.cellextend.address}
				</div>
			</div>
			<!-- 是否已支付 -->
			<c:if test="${cellextend.cellextend.payflag eq '1'}">
				<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
				<div class="am-form-group">
					<label for="address" class="am-form-label am-u-sm-3">支付时间</label>
					<div class="am-form-content am-u-sm-9" align="right">
						${cellextend.cellextend.paytime==null?"":fn:substring(cellextend.cellextend.paytime, 0, 19)}
					</div>
				</div>
			</c:if>  
			<!-- 是否已申请驻点 -->
			<c:if test="${cellextend.cellextend.stationflag eq '1'}">
				<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
				<div class="am-form-group" onclick="javascript:lookCellstation();">
					<label for="address" class="am-form-label am-u-sm-6">申请驻点信息</label>
					<div class="am-form-content am-u-sm-6" align="right">
						点击查看&nbsp;&nbsp;<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
					</div>
				</div>
			</c:if>  
			
			<div class="info-btn my-foot2-fixed">
				<table class="b" >
        			<tr>
	        			<td>
	        				<button id="pay" type="button" class="b"  onclick="javascript:savePaymentInfo();">立即支付</button>
	        			</td>
	        			<td>
	        				<button id="station" type="button" class="b"   onclick="javascript:saveCellstation();">申请驻点</button>
	        			</td>
				   </tr>
			   </table>
			</div>
			
			<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
			<c:if test="${cellextend.returninfo ne null && cellextend.returninfo ne ''}">
				<div id="returninfo"  class="am-alert am-alert-success" data-am-alert >
			 		 <p>${cellextend.returninfo}</p>
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
		$(function(){
			if('${cellextend.cellextend.payflag}'=='0'){
				$("#station").attr('disabled',true); 
				$("#station").removeClass("b"); 
				$("#station").addClass("c");
			}else if('${cellextend.cellextend.stationflag}'=='0'){
				$("#pay").attr('disabled',true); 
				$("#pay").removeClass("b"); 
				$("#pay").addClass("c");
			}else{
				$("#pay").attr('disabled',true); 
				$("#pay").removeClass("b"); 
				$("#pay").addClass("c");
				$("#station").attr('disabled',true); 
				$("#station").removeClass("b"); 
				$("#station").addClass("c");
			}		
		});
		
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
		
	</script>
</body>
</html>
