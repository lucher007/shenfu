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
	<link rel="stylesheet" href="style/mobilebusiness/form.css">
	<style type="text/css">
			.footer-nav{position:absolute; background-color:yellow;top:50px; z-index:10000;width:140px; right:-8px; display:none}
	        .footer-nav span{ line-height:45px; border-top: solid 1px #dedede;border-left: solid 1px #dedede;border-right: solid 1px #dedede; padding:0px 10px; background-color:white; font-size: 12px;}
		    .footer-nav span:before{ color:#aaa}
		    .footer-nav span a{ height:36px; line-height:45px; color:#333333 !important; display:inline; margin-left:5px}
    </style>
	
</head>

<body>
	<form method="post" id="addForm" name="addForm" >
	<header data-am-widget="header" class="am-header am-header-default my-header am-header-fixed" style="background-color: #0e90d2;">
		 <h1 class="am-header-title" id="head_title">我的</h1>
		 <div class="am-header-right am-header-nav">
	          <a href="javascript:;" onClick="showFooterNav();" class="">
	              <span class="am-header-nav-title">设置</span><i class="am-header-icon am-icon-bars am-icon-sm"></i>
	          </a>
	          <div class="footer-nav" id="footNav" style="">
	          	<span class="am-icon-user-secret"><a href="mobilebusiness/updateApppasswordInit">修改密码</a></span>
	            <span class="am-icon-suitcase"><a href="mobilebusiness/findParentSalerInfo">引入人信息</a></span>
	            <span class="am-icon-book"><a href="mobilebusiness/findEmployeeInfo">个人信息</a></span>
	            <span class="am-icon-key"><a href="mobilebusiness/findEmployeeExtendcode">我的推广码</a></span>
	            <!--
	            <span class="am-icon-chain"><a href="mobilebusiness/extendUserorderUrl">下单链接分享</a></span>
	              -->
	            <span class="am-icon-qrcode"><a href="mobilebusiness/findMySaleQrcode">我的二维码</a></span>
	            <span class="am-icon-lock"><a href="mobilebusiness/dispatchinfo">我的工单</a></span>
	            <span class="am-icon-power-off"><a href="mobilebusiness/logout">账号退出</a></span>
	          </div>
	     </div>
	</header>
	<div style="background-color: #0e90d2;width: 100%;height:50px;" >
         <table style="color: white;">
           <tr>
           	 <td style="width: 20px;"></td>
           	 <td >
           	 	<img src="images/mobilebusiness/logo.png" alt=""  width="40" height="40" />
           	 </td>
           	 <td style="width: 20px;"></td>
           	 <td>
           	 	 <span>${MobileOperator.employeename}</span> <br>
         		 <span>${MobileOperator.phone}</span>
           	 </td>
           </tr>
         </table>
	</div>
	
	<div style="padding-bottom: 50px; ">
		<hr data-am-widget="divider" style="margin: 1.5rem auto;" class="am-divider am-divider-default" />
		<div class="am-form-group" onclick="javascript:findSalegaininfoList();">
			<label for="" class="am-form-label am-u-sm-6"><img src="images/mobilebusiness/pay_new.png" alt=""  width="30" height="30" />&nbsp;&nbsp;未领提成</label>
			<div class="am-form-content am-u-sm-6" align="right">
				${gaintotalmoney}
				&nbsp;&nbsp;<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
			</div>
		</div>
		
		<hr data-am-widget="divider" style="margin: 1.5rem auto;" class="am-divider am-divider-default" />
		<div class="am-form-group" onclick="javascript:findSalegainlogList();">
			<label for="" class="am-form-label am-u-sm-6"><img src="images/mobilebusiness/pay_new.png" alt=""  width="30" height="30" />&nbsp;&nbsp;提成领取记录</label>
			<div class="am-form-content am-u-sm-6" align="right">
			&nbsp;&nbsp;<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
			</div>
		</div>
		
		<hr data-am-widget="divider" style="margin: 1.5rem auto;" class="am-divider am-divider-default" />
		<div class="am-form-group" onclick="javascript:addGiftcardInit();">
			<label for="" class="am-form-label am-u-sm-6"><img src="images/mobilebusiness/pay_new.png" alt=""  width="30" height="30" />&nbsp;&nbsp;生成优惠卡</label>
			<div class="am-form-content am-u-sm-6" align="right">
			&nbsp;&nbsp;<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
			</div>
		</div>
		
		<!-- 
		<hr data-am-widget="divider" style="margin: 1.5rem auto;" class="am-divider am-divider-default" />
		<div class="am-form-group" onclick="javascript:findSaleenergyinfoList('0');">
			<label for="" class="am-form-label am-u-sm-7"><img src="images/mobilebusiness/pay_new.png" alt=""  width="30" height="30" />&nbsp;&nbsp;未兑换行动力奖励</label>
			<div class="am-form-content am-u-sm-5" align="right">
			${saleenergy_unexchange_gain}
			&nbsp;&nbsp;<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
			</div>
		</div>
		
		<hr data-am-widget="divider" style="margin: 1.5rem auto;" class="am-divider am-divider-default" />
		<div class="am-form-group" onclick="javascript:findSaleenergyinfoList('1');">
			<label for="" class="am-form-label am-u-sm-7"><img src="images/mobilebusiness/pay_new.png" alt=""  width="30" height="30" />&nbsp;&nbsp;已兑换行动力奖励</label>
			<div class="am-form-content am-u-sm-5" align="right">
			${saleenergy_exchangeed_gain}
			&nbsp;&nbsp;<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
			</div>
		</div>
		
		<hr data-am-widget="divider" style="margin: 1.5rem auto;" class="am-divider am-divider-default" />
		<div class="am-form-group" onclick="javascript:findEnergyHelpInfoList();">
			<label for="address" class="am-form-label am-u-sm-6"><img src="images/mobilebusiness/rule_new.png" alt=""  width="30" height="30" />&nbsp;&nbsp;行动力规则</label>
			<div class="am-form-content am-u-sm-6" align="right">
				<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
			</div>
		</div>
		
		<hr data-am-widget="divider" style="margin: 1.5rem auto;" class="am-divider am-divider-default" />
		<div class="am-form-group" onclick="javascript:findMyCellextendList();">
			<label for="address" class="am-form-label am-u-sm-6"><img src="images/mobilebusiness/home3.png" alt=""  width="28" height="28" />&nbsp;&nbsp;我的楼盘</label>
			<div class="am-form-content am-u-sm-6" align="right">
				<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
			</div>
		</div>
		
		<hr data-am-widget="divider" style="margin: 1.5rem auto;" class="am-divider am-divider-default" />
		<div class="am-form-group" onclick="javascript:updateUserdoordata();">
			<label for="address" class="am-form-label am-u-sm-6"><img src="images/mobilebusiness/map-marker.png" alt=""  width="28" height="28" />&nbsp;&nbsp;我的驻点</label>
			<div class="am-form-content am-u-sm-6" align="right">
				<span class="am-icon-angle-double-right am-icon-sm am-fr am-margin-right"></span>
			</div>
		</div>
		
		
		<hr data-am-widget="divider" style="margin: 1.5rem auto;" class="am-divider am-divider-default" />
		<div class="am-form-group" onclick="javascript:updateUserdoordata();">
			<table style="width: 100%; " >
				<tr>
					<td style="border: 1px solid #bbbbbb;  border-left: 0px;border-top: 0px;border-bottom: 0px;width: 33.3%; text-align: center">
						 <span>上上月行动力</span> <br>
         		 		 <span>${saleenergy_pre_two}</span>
					</td>
					<td style="border: 1px solid #bbbbbb;  border-left: 0px;border-top: 0px;border-bottom: 0px;width: 33.3%; text-align: center">
						 <span>上月行动力</span> <br>
         		 		 <span>${saleenergy_pre_one}</span>
					</td>
					<td style="text-align: center;width: 33.3%;">
						 <span>本月行动力</span> <br>
         		 		 <span>${saleenergy_current}</span>
					</td>
				</tr>
			</table>
		</div>
		 -->
		<hr data-am-widget="divider" style="margin: 1.5rem auto;" class="am-divider am-divider-default" />
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
	          <a href="mobilebusiness/saleinfoInit" class="">
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
	          <a href="mobilebusiness/employeeinfoInit" class="" style="background-color: black;">
	            <span class=" am-icon-user"></span>
	            <span class="am-navbar-label">我的</span>
	          </a>
	        </li>
	      </ul>
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
	function showFooterNav(){
		$("#footNav").toggle();
	}
	
	//未领提成记录查询
	function findSalegaininfoList() {
		$("#addForm").attr("action", "mobilebusiness/findSalegaininfoList?rid="+Math.random());
		$("#addForm").submit();
	}
	
	//提成领取记录查询
	function findSalegainlogList() {
		$("#addForm").attr("action", "mobilebusiness/findSalegainlogList?rid="+Math.random());
		$("#addForm").submit();
	}
	
	//行动力分查询
	function findSaleenergyinfoList(status) {
		$("#addForm").attr("action", "mobilebusiness/findSaleenergyinfoList?rid="+Math.random()+"&status="+status);
		$("#addForm").submit();
	}
	
	//行动力规则查询
	function findEnergyHelpInfoList(status) {
		$("#addForm").attr("action", "mobilebusiness/findEnergyHelpInfoList?rid="+Math.random());
		$("#addForm").submit();
	}
	
	//未领提成记录查询
	function findMyCellextendList() {
		$("#addForm").attr("action", "mobilebusiness/findMyCellextendList?rid="+Math.random());
		$("#addForm").submit();
	}
	
	//生成礼品卡初始化方法
	function addGiftcardInit() {
		$("#addForm").attr("action", "mobilebusiness/addGiftcardInit?rid="+Math.random());
		$("#addForm").submit();
	}
	</script>
</body>
</html>
