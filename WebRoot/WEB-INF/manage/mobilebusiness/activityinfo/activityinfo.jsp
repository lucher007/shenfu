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
	<header data-am-widget="header" class="am-header am-header-default my-header am-header-fixed" style="background-color: #0e90d2;">
		       <h1 class="am-header-title" id="head_title">活动</h1>
		       <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
	</header>
	<div class="tab  my-header1-fixed">
	    <a href="javascript:;" class="item cur">消息</a>
	    <a href="javascript:;" class="item">扫楼</a>
	</div>
	<div style="height: 30px;"></div>
	<div class="content">
	    <div class="lists">
	    	<ul class="am-list" style="margin-left:10px;margin-right:10px;  font-size: 15px; color: #333333;"></ul>
	    </div>
	    <div class="lists">
	    	<ul class="am-list" style="margin-left:10px;margin-right:10px;  font-size: 15px; color: #333333;"></ul>
	    </div>
		<div class="dropload-down">
			<div class="dropload-refresh">↑上拉加载更多</div>
		</div>
	</div>
	<!--底部-->
    <div data-am-widget="navbar" class="am-navbar am-cf my-nav-footer " id="">
      <ul class="am-navbar-nav am-cf am-avg-sm-4 my-footer-ul">
        <li>
          <a href="mobilebusiness/activityinfoInit" class="" style="background-color: black;">
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
          <a href="mobilebusiness/employeeinfoInit" class="">
            <span class=" am-icon-user"></span>
            <span class="am-navbar-label">我的</span>
          </a>
        </li>
      </ul>
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
	<script type="text/javascript" src="js/dist/dropload.min.js"></script>
	<script type="text/javascript">
	$(function(){
	    var itemIndex = 0;
	    var tab1LoadEnd = false; //Tab1数据是否加载结束
	    var tab2LoadEnd = false; //Tab2数据是否加载结束
	    
	    //读取缓存历史TAB选择记录（进入页面，返回的时候调到）
	    if(location.hash){
	    	var tabid= location.hash;
		    var hashStr = location.hash.replace("#","");
		    itemIndex = hashStr;
		    $('.item').eq(itemIndex).addClass('cur').siblings('.item').removeClass('cur');
	        $('.lists').eq(itemIndex).show().siblings('.lists').hide();
        }
	    
	    // tab
	    $('.tab .item').on('click',function(){
	        var $this = $(this);
	        itemIndex = $this.index();
	        $('.item').eq(itemIndex).addClass('cur').siblings('.item').removeClass('cur');
	        $('.lists').eq(itemIndex).show().siblings('.lists').hide();

	        location.hash = itemIndex;
	        
	        // 如果选中菜单一
	        if(itemIndex == '0'){
	            // 如果数据没有加载完
	            if(!tab1LoadEnd){
	                // 解锁
	                dropload.unlock();
	                dropload.noData(false);
	            }else{
	                // 锁定
	                dropload.lock('down');
	                dropload.noData();
	            }
	        // 如果选中菜单二
	        }else if(itemIndex == '1'){
	            if(!tab2LoadEnd){
	                // 解锁
	                dropload.unlock();
	                dropload.noData(false);
	            }else{
	                // 锁定
	                dropload.lock('down');
	                dropload.noData();
	            }
	        }
	        // 重置
	        dropload.resetload();
	    });
	    
	    var page_0 = 1;//TAB_0查询页码
	    var page_1 = 1;//TAB_1查询页码
	    var rows = 6;//每页显示行数
	    // dropload
	    var dropload = $('#content').dropload({
	    	
	        scrollArea : window,
	        loadDownFn : function(me){
	            // 加载菜单一的数据
	            if(itemIndex == '0'){
	            	//请求参数
	            	var data = {
	            					page:page_0,  //页码
	            					rows:rows,  //每页显示行数
							   };
	                $.ajax({
	                    type: 'GET',
	                    url: 'mobilebusiness/findActivityListJSON',
	                    data: data, //可选参数
	                    dataType: 'json',
	                    success: function(data){
	                        var result = '';
	                        page_0++;
	                        if(data.lists.length > 0){
	                        	for(var i = 0; i < data.lists.length; i++){
	                            	 result +=   '<li>'
	                                     +'<a href="mobilebusiness/lookActivity?id='+ data.lists[i].id + '" class="am-list-item-hd" style="font-size:15px;">'+data.lists[i].title+'</a>'
	                                     +'<div class="am-text-truncate">'+data.lists[i].content+'</div>'
	                                 	+'</li>';
	                            }
	                            // 为了测试，延迟1秒加载
	                            setTimeout(function(){
	                            	 //刷新加载数据列表
	                            	 $('.am-list').eq(0).append(result);
	                            	 //判断是否是最后一页，如果是最后一页，显示无数据
	                            	 if(data.lists.length < rows){//表示最后一页了
	 	                            	// 数据加载完
	 	                                //tab1LoadEnd = true;
	 	                                // 锁定
	 	                                me.lock();
	 	                                // 无数据
	 	                                me.noData();
	 	                            }
	                            	//每次数据加载完，必须重置
	                                 me.resetload();
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
	                    }
	                });
	            // 加载菜单二的数据
	            }else if(itemIndex == '1'){
	            	//请求参数
	            	var data = {
	            					page:page_1,  //页码
	            					rows:rows,  //每页显示行数
	            			  acceptflag:0,     //未接收的扫楼信息
							   };
	            	$.ajax({
	                    type: 'GET',
	                    url: 'mobilebusiness/findCellextendListJSON',
	                    data: data, //可选参数
	                    dataType: 'json',
	                    success: function(data){
	                        var result = '';
	                        page_1++;
	                        if(data.lists.length > 0){
	                        	for(var i = 0; i < data.lists.length; i++){
	                            	 result +=   '<li>'
	                                     +'<a href="mobilebusiness/buyCellextendInit?id=' + data.lists[i].id + '" class="am-list-item-hd" style="font-size:15px;">'+data.lists[i].cellname+'</a>'
	                                     +'<div class="am-text-truncate">'+data.lists[i].address+'</div>'
	                                 	+'</li>';
	                            }
	                            // 为了测试，延迟1秒加载
	                            setTimeout(function(){
	                            	 //刷新加载数据列表
	                            	 $('.am-list').eq(1).append(result);
	                            	 //判断是否是最后一页，如果是最后一页，显示无数据
	                            	 if(data.lists.length < rows){//表示最后一页了
	 	                            	// 数据加载完
	 	                                tab2LoadEnd = true;
	 	                                // 锁定
	 	                                me.lock();
	 	                                // 无数据
	 	                                me.noData();
	 	                            }
	                            	//每次数据加载完，必须重置
	                                 me.resetload();
	                            },100);
	                           
	                        }else{
	                        	// 数据加载完
                                tab2LoadEnd = true;
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
	                    }
	                });
	            }
	        }
	    });
	});
	
	function showFooterNav(){
		$("#footNav").toggle();
	}
	</script>
</body>
</html>
