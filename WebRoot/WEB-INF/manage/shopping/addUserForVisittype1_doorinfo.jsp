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
	
	<link rel="stylesheet" href="style/upload/uploadpricture.css" >
	<link rel="stylesheet" href="style/upload/wap.css" />
	<link rel="stylesheet" href="shopping/AmazeUI-2.4.2/assets/css/amazeui.min.css">
	<link rel="stylesheet" href="shopping/AmazeUI-2.4.2/assets/css/app.css">
	
	<style type="text/css">

	h2{
		margin: 0;
		padding: 0;
	}
	input.b{
	
	font-size: 20px;
	width:100%;
	height:50px;
	color: #fff;
	background: #256ebb;
	}
	
	.ok{width:100%;position:fixed;bottom: 0px;}
	
	.default-img {
	    width: 30%;
	    height: width;
	    
	}
	
	.top1 {
		display: block;
		width: 100%;
		margin-top: 50px;
		margin-bottom: 50px;
	}
	/*遮罩层样式*/
	.mask{
		z-index: 500;
		display: none;
		position: fixed;
		top: 0px;
		left: 0px;
		width: 100%;
		height: 100%;
		background: rgba(0,0,0,.4);
	}
	.mask .mask-content{
		 width: 80%;
		 position: absolute;
		 top: 50%;
		 left: 10%;
		 background: white;
		 text-align: center;
	}
	.mask .mask-content .del-p{
		color: #555;
		height: 94px;
		line-height: 94px;
		font-size: 18px;
		border-bottom: 1px solid #D1D1D1;
	}
	
	.mask-content .check-p span{
		padding:5px 0px 5px 0px;
		width:49%;
		display:inline-block;
		text-align: center;
		color:#d4361d ;
		font-size: 18px;
	}
	.check-p .del-com{
		border-right: 1px solid #D1D1D1;
	}
	
	.fl {
		float: left;
	}
	
	.clear:after {
		content: '';
		display: block;
		clear: both;
	}
	
	
	.loading{
		
		 top: 10px;
		
	}
	
	.mask .mask-loading{
		 width: 80%;
		 position: absolute;
		 top: 50%;
		 left: 10%;
		 background: white;
		 text-align: center;
	}
	
	/*loading等待页面*/
	.loadingWrap{
		position:fixed;
		top:0;
		left:0;
		width:100%;
		height:100%;
		z-index:300;
		background-image:url(style/upload/loading.gif);
		background-repeat:no-repeat;
		background-position:center center;
		background-color:#000;
		background-color:rgba(0,0,0,0.5);
		filter:alpha(opacity=50);
	}
	
</style>
	
</head>

<body>
	<form class="am-form am-form-horizontal" id="addForm" method="post">
	    <input type="hidden" id="visittype" name="visittype" value="1">
	    <input type="hidden" id="source" name="source" value="0">
	    <input type="hidden" id="salercode" name="salercode" value="${Saler.employeecode}">
		<input type="hidden" id="username" name="username" value="${user.username}">
	    <input type="hidden" id="phone" name="phone" value="${user.phone}">
	    <input type="hidden" id="address" name="address" value="${user.address}">
	    <input type="hidden" id="modelcode" name="modelcode" value="${user.modelcode}">
	    <input type="hidden" id="productcolor" name="productcolor" value="${user.productcolor}">
	    <input type="hidden" id="lockcoreflag" name="lockcoreflag" value="${user.lockcoreflag}">
	    
	    <header data-am-widget="header" class="am-header am-header-default am-header-fixed">
	       <div class="am-header-left am-header-nav">
	          <a href="javascript:rebackProductinfo();" class="">
	              <img class="am-header-icon-custom" src="data:image/svg+xml;charset&#x3D;utf-8,&lt;svg xmlns&#x3D;&quot;http://www.w3.org/2000/svg&quot; viewBox&#x3D;&quot;0 0 12 20&quot;&gt;&lt;path d&#x3D;&quot;M10,0l2,2l-8,8l8,8l-2,2L0,10L10,0z&quot; fill&#x3D;&quot;%23fff&quot;/&gt;&lt;/svg&gt;" alt=""/>
	              <span class="am-header-nav-title">返回</span>
	          </a>
	       </div>
	       <h1 class="am-header-title">门锁图片</h1>
	  	</header>
		  
		   <figure data-am-widget="figure" class="am am-figure am-figure-default "   data-am-figure="{  pureview: 'true' }" style=" text-align: center;">
		    	<figcaption class="am-figure-capition-btm">门锁衬板长度和宽度模板照片</figcaption>
		   		<img class="am-radius" alt="180*1800" src="images/shopping/door01.jpg?imageView2/0/w/640" width="140" height="140" style="display:inline-block;"/>
		   		<img class="am-radius" alt="200*200" src="images/shopping/door02.jpg?imageView2/0/w/640" width="140" height="140" style="display:inline-block;"/>
 		  </figure>
 		  
		<div >
			<p>请上传俩张照片，包括衬板长度和衬板宽度</p>
			<div class="z_photo upimg-div clear" id="z_photo1">
				 <section class="z_file fl">
					<img src="style/upload/add2.png" class="add-img">
					<input type="file" name="file1" id="file1" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" multiple />
				 </section>
		 	</div>
		</div>
		
	   <span style="color: red" id="msg"></span>
		
	   <div style="height: 15px;"></div>
	   <c:if test="${user.returninfo ne null && user.returninfo ne ''}">
			<div id="returninfo"  class="am-alert am-alert-success" data-am-alert >
			  <p>${user.returninfo}</p>
			</div>
		</c:if>
		
		<!-- 保存确认框 -->
		<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
		  <div class="am-modal-dialog">
		    <div class="am-modal-hd">确认安装</div>
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
	
	<div class="ok">
		<input type="button" class="b" id="registsubmit" value="提交" onclick="javascript:saveUserBuyOrder();"/>
	</div>
	
	<form class="mask works-mask">
		<div class="mask-content">
			<p class="del-p ">您确定要删除上传图片吗？</p>
			<p class="check-p"><span class="del-com wsdel-ok">确定</span><span class="wsdel-no">取消</span></p>
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
	<script type="text/javascript" src="style/upload/imgUp.js"></script>
	<script type="text/javascript">
	
	function checkVal() {
		if (ele.length < 2) {
			$.dialog.tips("请上传门锁衬板的长度和宽度照片", 1, 'alert.gif');
			return false;
		}
		return true;
	}
	
	
	//设置一个对象来控制是否进入AJAX过程
	var post_flag = false; 
	var ele=[];
	function saveUserBuyOrder() {
		if (!checkVal()) {
			return;
		}
		
		//保存确认框
		$('#my-confirm').modal({
		      relatedTarget: this,
		      onConfirm: function(e) { //确认保存
		
				//如果正在提交则直接返回，停止执行
			    //if(post_flag){
			    //	$.dialog.tips("本次操作已经提交，请勿重复提交", 1, 'alert.gif');
			    //	return; 
			    //}
			    //标记当前状态为正在提交状态
			    //post_flag = true;
			    
			    //ajax的post方式提交表单
				//$("#itemAddForm").serialize()将表单序列号为key-value形式的字符串
		        //var xhr = new XMLHttpRequest();
		        var formData = new FormData();
		
		        for(var i=0, f; f=ele[i]; i++){
		            formData.append('files', f);
		            name=name+f.name+",";
		        }
		        //上门类型
		        formData.append("visittype",$("#visittype").val());
		        //提交方式
		        formData.append("source",$("#source").val());
		        //销售员编号
		        formData.append("salercode",$("#salercode").val());
		        //客户姓名
		        formData.append("username",$("#username").val());
		        //联系电话
		        formData.append("phone",$("#phone").val());
		        //安装地址
		        formData.append("address",$("#address").val());
		        //产品型号
		        formData.append("modelcode",$("#modelcode").val());
		      	//产品型号
		        formData.append("productcolor",$("#productcolor").val());
		        //是否带机械锁心lockcoreflag
		      	formData.append("lockcoreflag",$("#lockcoreflag").val());
			    
		      	$("<div class='loadingWrap'></div>").appendTo("body");  
		        
		      	$.ajax({
			          url: "sale/saveUserAddOrderForVisittype1?rid="+Math.random(),  
			          type: 'POST',  
			          data: formData,  
			          async: true,  
			          cache: false,  
			          contentType: false,  
			          processData: false,  
			          beforeSend: function(){
			              $("<div class='loadingWrap'></div>").appendTo("body");  
			          },
			          success: function (returndata) {
			        	  var obj = JSON.parse(returndata); 
			        	  $.dialog.tips(obj.result, 2, 'alert.gif');
			        	  $("#msg").html(obj.result);
			          },  
			          error: function (returndata) { 
			        	  $.dialog.tips("提交失败", 2, 'alert.gif');
			        	  $("#msg").html("提交失败");
			          },
			          complete: function(){  
			              $(".loadingWrap").remove();  
			          }
			     });  
		      },
		      onCancel: function(e) {
		       	
		      }
	    });
	}
	
	$(function () {
	       var returninfo = '${user.returninfo}';
	       if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	       }
	 });
	
	function rebackProductinfo() {
		$("#addForm").attr("action", "sale/saveUserAddOrderForVisittype1_userinfo?rid="+Math.random());
		$("#addForm").submit();
	}
	
	</script>
</body>
</html>
