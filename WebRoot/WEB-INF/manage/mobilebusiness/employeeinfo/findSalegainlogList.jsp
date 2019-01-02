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
	<link rel="stylesheet" href="style/mobilebusiness/style.css">
	<link rel="stylesheet" href="style/mobilebusiness/admin.css">
	<link rel="stylesheet" href="shopping/AmazeUI-2.4.2/assets/css/amazeui.min.css">
	<link rel="stylesheet" href="shopping/AmazeUI-2.4.2/assets/css/app.css">
	<link rel="stylesheet" href="js/dist/dropload.css">
	<style type="text/css">
	.my-header1-fixed {
		    position: fixed;
		    top: 50px;
		    left: 0;
		    right: 0;
		    width: 100%;
		    z-index: 1010;
	}
    </style>
	
</head>

<body>
	<form class="am-form am-form-horizontal" id="addForm" method="post" >
	<header data-am-widget="header" class="am-header am-header-default am-header-fixed">
	       <div class="am-header-left am-header-nav">
	          <a href="mobilebusiness/employeeinfoInit" class="">
	              <img class="am-header-icon-custom" src="data:image/svg+xml;charset&#x3D;utf-8,&lt;svg xmlns&#x3D;&quot;http://www.w3.org/2000/svg&quot; viewBox&#x3D;&quot;0 0 12 20&quot;&gt;&lt;path d&#x3D;&quot;M10,0l2,2l-8,8l8,8l-2,2L0,10L10,0z&quot; fill&#x3D;&quot;%23fff&quot;/&gt;&lt;/svg&gt;" alt=""/>
	              <span class="am-header-nav-title">返回 </span>
	          </a>
	       </div>
	       <h1 class="am-header-title">提成领取记录</h1>
	</header>
	<div class="content">
    	<table class="am-table am-table-bordered">
    		 <thead>
		        <tr class="am-primary">
		            <th width="50%">提成金额</th>
		            <th width="50%">领取时间</th>
		        </tr>
		    </thead>
		   <tbody class="am-data"></tbody>
   		</table>
		<div class="dropload-down">
			<div class="dropload-refresh">↑上拉加载更多</div>
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
	<script type="text/javascript" src="js/dist/dropload.min.js"></script>
	<script type="text/javascript">
	$(function(){
	    var page_0 = 1;//TAB_0查询页码
	    var rows = 6;//每页显示行数
	    // dropload
	    var dropload = $('#content').dropload({
	        scrollArea : window,
	        loadDownFn : function(me){
	            // 加载数据
            	//请求参数
            	var data = {
            					page:page_0,  //页码
            					rows:rows,    //每页显示行数
						   };
                $.ajax({
                    type: 'GET',
                    url: 'mobilebusiness/getSalegainlogListJSON',
                    data: data, //可选参数
                    dataType: 'json',
                    success: function(data){
                        var result = '';
                        page_0++;
                        var listdata = data.salegainloglist;
                        if(listdata.length > 0){
                        	for(var i = 0; i < listdata.length; i++){
                            	 result +=   '<tr class="lb-list">'
                            	         +     '<td>'+ listdata[i].gainmoney + '<input type="hidden" value="'+ listdata[i].gainlogcode+'">' + '</td>'
                            	         +     '<td>'+ listdata[i].gaintime +'</td>'
	                                     +   '</tr>';
                            }
                            // 为了测试，延迟1秒加载
                            setTimeout(function(){
                            	 //刷新加载数据列表
                            	 $('.am-data').append(result);
                            	 //判断是否是最后一页，如果是最后一页，显示无数据
                            	 if(listdata.length < rows){//表示最后一页了
 	                            	// 数据加载完
 	                                //tab1LoadEnd = true;
 	                                // 锁定
 	                                me.lock();
 	                                // 无数据
 	                                me.noData();
 	                            }
                            	//每次数据加载完，必须重置
                                 me.resetload();
                            	 
                                 $(".lb-list").unbind("click");//清除table的所有click事件
                                 $('.lb-list').on('click', function() {
                                	 var gainlogcode = $(this).find("input[type=hidden]").val();
                                	 //根据领取记录查询提成领取明细
                                	 findSalegaininfoListByGainlogcode(gainlogcode);
     		                     });
                            	
                            },100);
                            
                        }else{
                        	// 数据加载完
                               //tab1LoadEnd = true;
                               // 锁定
                               me.lock();
                               // 无数据
                               me.noData();
                               me.resetload();
                        }
                    },
                    error: function(xhr, type){
                        //alert('Ajax error!');
                        // 即使加载出错，也得重置
                        me.resetload();
                    },
                    
                });
	        }
	    });
	});
	
	//根据领取记录查询提成领取明细
	function findSalegaininfoListByGainlogcode(gainlogcode) {
			$("#addForm").attr("action", "mobilebusiness/findSalegaininfoListByGainlogcode?rid="+Math.random()+"&gainlogcode="+gainlogcode);
			$("#addForm").submit();
	}
	
	</script>
</body>
</html>
