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
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<title>申亚科技</title>
	<link  type="text/css" rel="stylesheet" href="shopping/AmazeUI-2.4.2/assets/css/admin.css"  />
	<link  type="text/css" rel="stylesheet" href="shopping/AmazeUI-2.4.2/assets/css/amazeui.css"   />
	<link  type="text/css" rel="stylesheet" href="shopping/basic/css/demo.css"   />
	<link  type="text/css" rel="stylesheet" href="shopping/css/optstyle.css"   />
	<link  type="text/css" rel="stylesheet" href="shopping/css/style.css" />
	
	<script type="text/javascript" src="shopping/basic/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="shopping/basic/js/quick_links.js"></script>
	<script type="text/javascript" src="shopping/AmazeUI-2.4.2/assets/js/amazeui.js"></script>
	<script type="text/javascript" src="shopping/js/jquery.imagezoom.min.js"></script>
	<script type="text/javascript" src="shopping/js/jquery.flexslider.js"></script>
	<script type="text/javascript" src="shopping/js/list.js"></script>
</head>
<body>
		<!--顶部导航条 -->
		<div class="am-container header">
			<ul class="message-r">
				<div class="topMessage my-shangcheng">
					<div class="menu-hd MyShangcheng"><a href="#" target="_top"><i class="am-icon-user am-icon-fw"></i>个人中心</a></div>
				</div>
			</ul>
		</div>
		<div class="clear"></div>
        <b class="line"></b>
        
		<div class="listMain">
				<script type="text/javascript">
					$(function() {});
					$(window).load(function() {
						$('.flexslider').flexslider({
							animation: "slide",
							start: function(slider) {
								$('body').removeClass('loading');
							}
						});
					});
				</script>
				<div class="scoll">
					<section class="slider">
						<div class="flexslider">
							<ul class="slides">
								<li>
									<img src="images/shopping/main_01.png" title="pic" />
								</li>
								<li>
									<img src="images/shopping/main_02.png" title="pic" />
								</li>
							</ul>
						</div>
					</section>
				</div>
				<!--放大镜-->
				<div class="item-inform">
					<div class="clearfixRight">
						<div class="tb-detail-list">
							<div class="tb-detail-price">
							  <!--
								<li class="price iteminfo_price">
									<dt>销售价格：</dt>
									<dd><em>¥</em><b class="sys_item_price">3499</b>  </dd>                                 
								</li>
								
								<li class="price iteminfo_mktprice">
									<dt>价格</dt>
									<dd><em>¥</em><b class="sys_item_mktprice">3499</b></dd>									
								</li>
							  -->
							</div>
							<div class="clear"></div>
							<!--活动	-->
							<div class="shopPromotion gold">
								<div class="hot">
									<dt class="tb-metatit" style="width: 33.33%;text-align:center;">免费上门产品体验</dt>
									<dt class="tb-metatit" style="width: 33.33%;text-align:center;" >货到付款</dt>
									<dt class="tb-metatit" style="width: 33.33%;text-align:center;">免费测量安装</dt>
								</div>
							</div>
						</div>
						<div class="pay">
						    <form id="addForm" method="post">
						    	<input type="hidden" id="salercode" name="salercode" value="${Saler.employeecode}">
							    <!--
							    <li>
							    	<div class="tb-btn">
							   		 	<div id="" title="点此按钮到下一步查看个人订单"  class="menu-hd MyShangcheng"><a href="<%=request.getContextPath()%>/sale/queryUserorderByShopping?salercode=${Saler.employeecode}"><i class="am-icon-user am-icon-fw"></i>个人中心</a></div>
							   		</div>
							    </li>
							      -->
								<li>
									<div class="clearfix tb-btn tb-btn-talker theme-login">
										<a id="" title="点此按钮到下一步确认客户信息" href="<%=request.getContextPath()%>/sale/userAddOrderInit?visittype=0&salercode=${Saler.employeecode}">申请体验</a>
									</div>
								</li>
								<li>
									<div class="clearfix tb-btn tb-btn-install theme-login">
										<a id="" title="点此按钮到下一步确认购买信息" href="<%=request.getContextPath()%>/sale/userAddOrderInit?visittype=1&salercode=${Saler.employeecode}"><i></i>购买安装</a>
									</div>
								</li>
							</form>
						</div>
					</div>
					<div class="clear"></div>
				</div>	
				<div class="introduce">
					<div class="introduceMain">
						<div class="am-tabs" data-am-tabs>
							<div class="am-tabs-bd">
								<div class="am-tab-panel am-fade am-in am-active">
									<div class="details">
										<div class="attr-list-hd after-market-hd">
											<h4>产品细节</h4>
											<div id="" title="点此按钮到下一步查看个人订单"  class="menu-hd MyShangcheng"><a href="<%=request.getContextPath()%>/sale/queryUserorderByShopping?salercode=${Saler.employeecode}">订单查询</a></div>
										</div>
										<div class="twlistNews">
											<img src="images/shopping/productinfo_01.png" />
											<img src="images/shopping/productinfo_02.png" />
											<img src="images/shopping/productinfo_03.png" />
											<img src="images/shopping/productinfo_04.png" />
											<img src="images/shopping/productinfo_05.png" />
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</div>
	</body>

</html>
