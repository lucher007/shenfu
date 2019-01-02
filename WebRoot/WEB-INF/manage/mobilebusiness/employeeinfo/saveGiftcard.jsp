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
	<link rel="stylesheet" href="style/mobilebusiness/form.css">
	<style type="text/css">
	
    </style>
</head>

<body>
<form method="post" id="addForm" name="addForm" class="am-form am-form-horizontal">
	<header data-am-widget="header" class="am-header am-header-default am-header-fixed">
	    <div class="am-header-left am-header-nav">
	       <a href="mobilebusiness/employeeinfoInit" class="">
	           <img class="am-header-icon-custom" src="data:image/svg+xml;charset&#x3D;utf-8,&lt;svg xmlns&#x3D;&quot;http://www.w3.org/2000/svg&quot; viewBox&#x3D;&quot;0 0 12 20&quot;&gt;&lt;path d&#x3D;&quot;M10,0l2,2l-8,8l8,8l-2,2L0,10L10,0z&quot; fill&#x3D;&quot;%23fff&quot;/&gt;&lt;/svg&gt;" alt=""/>
	           <span class="am-header-nav-title">返回</span>
	       </a>
	    </div>
	    <h1 class="am-header-title">优惠卡生成</h1>
	</header>
	<div style="height: 15px;"></div>
	<div style="padding-bottom: 50px; ">
		
		 <div class="am-form-group">
	      	 <label for="modelcode"  class="am-form-label am-u-sm-3">产品型号</label>
	      	 <div class="am-u-sm-9">
	      	 	 <select  id="modelcode" name="modelcode"  class="am-u-sm-10" >
	      	 	 	 <option value="">请选择</option>
			         <c:forEach items="${productmodelList}" var="productmodel">  
		               <option value="${productmodel.modelcode}" <c:if test="${giftcard.modelcode eq productmodel.modelcode}">selected</c:if>>${productmodel.modelname}----最高优惠金额:${productmodel.discountgain}</option>
			         </c:forEach> 
			     </select>
			     <span class="am-form-caret"></span>
	      	 </div>
	    </div>

		<hr data-am-widget="divider" style="margin: 1.5rem auto;"  class="am-divider am-divider-default" />
		<div class="am-form-group">
			<label for="talkernumber" class="am-form-label am-u-sm-3" style="text-align: right;">优惠金额</label>
			<div class="am-form-content am-u-sm-9">
				<input type="text" id="amount" name="amount" value="${giftcard.amount}" placeholder="请输入优惠金额" class="am-form-field" required
				onkeyup="checkNum(this)" onkeypress="checkNum(this)" onblur="checkNum(this)" onafterpaste="this.value=this.value.replace(/\D/g,'')"
				/>
			</div>
		</div>
		<hr data-am-widget="divider" style="margin: 1.5rem auto;"  class="am-divider am-divider-default" />
		<div class="am-form-group">
			<label for="starttime" class="am-form-label am-u-sm-4" style="text-align: right;">有效开始日期</label>
			<div class="am-form-content am-u-sm-8">
				<input type="text" id="starttime" name="starttime" value="${giftcard.starttime}" class="am-form-field" placeholder="请输入有效开始日期" data-am-datepicker readonly required />
			</div>
		</div>
		<hr data-am-widget="divider" style="margin: 1.5rem auto;"  class="am-divider am-divider-default" />
		<div class="am-form-group">
			<label for="endtime" class="am-form-label am-u-sm-4" style="text-align: right;">有效结束日期</label>
			<div class="am-form-content am-u-sm-8">
				<input type="text" id="endtime" name="endtime" value="${giftcard.endtime}" class="am-form-field" placeholder="请输入有效结束日期"  data-am-datepicker readonly required/>
			</div>
		</div>
		<hr data-am-widget="divider" style="margin: 1.5rem auto;"  class="am-divider am-divider-default" />
		
		<c:if test="${giftcard.returninfo ne null && giftcard.returninfo ne ''}">
			<div id="returninfo"  class="am-alert am-alert-success" data-am-alert >
		 		 <p>${giftcard.returninfo}</p>
			</div>
		<hr data-am-widget="divider"style="margin: 1.5rem auto;"  class="am-divider am-divider-default" /> 
		</c:if>  
	</div>
	
	<div class="info-btn">
			<button type="button" class="am-btn am-alert am-btn-block" style="height: 50px;" onclick="javascript:saveGiftcard();">提交</button>
	</div>
	
	<!-- 保存确认框 -->
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
	  <div class="am-modal-dialog">
	    <div class="am-modal-bd">
	     	 确定操作？
	    </div>
	    <div class="am-modal-footer">
	      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
	      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
	    </div>
	  </div>
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
	
	//设置一个对象来控制是否进入AJAX过程
	var post_flag = false; 
	function saveGiftcard() {
		if ($("#modelcode") != undefined && ($("#modelcode").val() == "" || $("#modelcode").val() == null )) {
			$.dialog.tips("请选择产品型号", 2, "alert.gif", function() {
				$("#modelcode").focus();
			});
			return false;
		}
		if ($("#amount") != undefined && ($("#amount").val() == "" || $("#amount").val() == null )) {
			$("#amount").focus();
			$.dialog.tips("请输入优惠金额", 2, "alert.gif");
			return false;
		}
		
		if ($("#starttime") != undefined && ($("#starttime").val() == "" || $("#starttime").val() == null )) {
			$("#starttime").focus();
			$.dialog.tips("请输入有效开始日期", 2, "alert.gif");
			return false;
		}
		if ($("#endtime") != undefined && ($("#endtime").val() == "" || $("#endtime").val() == null )) {
			$("#endtime").focus();
			$.dialog.tips("请输入有效结束日期", 2, "alert.gif");
			return false;
		}
		
		if($("#starttime").val() > $("#endtime").val()){
			$("#starttime").focus();
			$("#endtime").focus();
			$.dialog.tips("有效开始日期不能大于有效结束日期", 2, "alert.gif");
			return false;
		}
		
		$('#my-confirm').modal({
		      relatedTarget: this,
		      onConfirm: function(e) {
			    	//如果正在提交则直接返回，停止执行
			  	    if(post_flag){
			  	    	$.dialog.tips("本次操作已经提交，请勿重复提交", 1, 'alert.gif');
			  	    	return; 
			  	    }
			  	    //标记当前状态为正在提交状态
			  	    post_flag = true;
			  	  	$("<div class='loadingWrap'></div>").appendTo("body");  
			  	  	
			  	  	//提交
			  	 	$("#addForm").attr("action", "mobilebusiness/saveGiftcard?rid="+Math.random());
					$("#addForm").submit();
		      },
		      onCancel: function(e) {
		       	
		      }
	    });
	}

	$(function () {
	      var returninfo = '${giftcard.returninfo}';
	      if (returninfo != '') {
	          $.dialog.tips(returninfo, 1, 'alert.gif');
	      }
	  });
	
	function rebackActivityinfo() {
		$("#addForm").attr("action", "mobilebusiness/activityinfoInit?rid="+Math.random());
		$("#addForm").submit();
	}
	</script>
</body>
</html>
