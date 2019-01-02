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
	          <a href="mobilebusiness/saleinfoInit" class="">
	              <img class="am-header-icon-custom" src="data:image/svg+xml;charset&#x3D;utf-8,&lt;svg xmlns&#x3D;&quot;http://www.w3.org/2000/svg&quot; viewBox&#x3D;&quot;0 0 12 20&quot;&gt;&lt;path d&#x3D;&quot;M10,0l2,2l-8,8l8,8l-2,2L0,10L10,0z&quot; fill&#x3D;&quot;%23fff&quot;/&gt;&lt;/svg&gt;" alt=""/>
	              <span class="am-header-nav-title">返回 </span>
	          </a>
	       </div>
	       <h1 class="am-header-title">任务单管理</h1>
	       <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
	</header>
	
	<div class="tab my-header1-fixed">
	    <a href="javascript:;" class="item cur">待处理</a>
	    <a href="javascript:;" class="item">已处理</a>
	</div>
	<div style="height: 30px;"></div>
	<div class="content">
		<div style="height: 30px;"></div>
	    <div class="lists">
	    	<table class="am-table am-table-bordered">
	    		 <thead>
			        <tr class="am-primary">
			            <th width="30%">姓名</th>
			            <th width="30%">电话</th>
			            <th width="40%">地址</th>
			        </tr>
			    </thead>
			   <tbody class="am-data"></tbody>
	   		</table>
	    </div>
	    <div class="lists">
	    	<table class="am-table am-table-bordered">
	    		 <thead>
			        <tr class="am-primary">
			            <th width="25%">姓名</th>
			            <th width="25%">电话</th>
			            <th width="25%">地址</th>
			            <th width="25%">订单进程</th>
			        </tr>
			    </thead>
			   <tbody class="am-data"></tbody>
	   		</table>
	    </div>
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
	    var itemIndex = 0;
	    var tab1LoadEnd = false; //Tab1数据是否加载结束
	    var tab2LoadEnd = false; //Tab2数据是否加载结束
	    
	    //默认加载第一个tab
	    $('.item').eq(itemIndex).addClass('cur').siblings('.item').removeClass('cur');
        $('.lists').eq(itemIndex).show().siblings('.lists').hide();
	    
	    // tab
	    $('.tab .item').on('click',function(){
	        var $this = $(this);
	        itemIndex = $this.index();
	        $('.item').eq(itemIndex).addClass('cur').siblings('.item').removeClass('cur');
	        $('.lists').eq(itemIndex).show().siblings('.lists').hide();

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
	            					querystateflag:0,     //未处理的任务单信息
							   };
	                $.ajax({
	                    type: 'GET',
	                    url: 'mobilebusiness/findUsertaskListJSON',
	                    data: data, //可选参数
	                    dataType: 'json',
	                    success: function(data){
	                        var result = '';
	                        page_0++;
	                        var listdata = data.usertaskList;
	                        if(listdata.length > 0){
	                        	for(var i = 0; i < listdata.length; i++){
	                            	 result +=   '<tr class="lb-list">'
	                            	         +     '<td>'+ listdata[i].username + '<input type="hidden" value="'+ listdata[i].taskcode+'">' + '</td>'
	                            	         +     '<td>'+ listdata[i].phone +'</td>'
	                            	         +     '<td>'+ listdata[i].address +'</td>'
		                                     +   '</tr>';
	                            }
	                            // 为了测试，延迟1秒加载
	                            setTimeout(function(){
	                            	 //刷新加载数据列表
	                            	 $('.am-data').eq(0).append(result);
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
	                                	 var taskcode = $(this).find("input[type=hidden]").val();
	                                	 //查看订单详情
	                                	 lookUsertask('ing',taskcode);
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
	                
	            // 加载菜单二的数据
	            }else if(itemIndex == '1'){
	            	//请求参数
	            	var data = {
	            					page:page_1,  //页码
	            					rows:rows,  //每页显示行数
	            					querystateflag:1,     //已处理的任务单信息
							   };
	            	$.ajax({
	                    type: 'GET',
	                    url: 'mobilebusiness/findUsertaskListJSON',
	                    data: data, //可选参数
	                    dataType: 'json',
	                    success: function(data){
	                        var result = '';
	                        page_1++;
	                        var listdata = data.usertaskList;
	                        if(listdata.length > 0){
	                        	for(var i = 0; i < listdata.length; i++){
	                            	 result +=   '<tr class="lb-list">'
	                            	         +     '<td>'+listdata[i].username + '<input type="hidden" value="'+ listdata[i].ordercode+'">' + '</td>'
	                            	         +     '<td>'+ listdata[i].phone +'</td>'
	                            	         +     '<td>'+ listdata[i].address +'</td>'
	                            	         +     '<td>'+ listdata[i].statusname +'</td>'
		                                     +   '</tr>';
	                            }
	                            // 为了测试，延迟1秒加载
	                            setTimeout(function(){
	                            	 //刷新加载数据列表
	                            	 $('.am-data').eq(1).append(result);
	                            	 //判断是否是最后一页，如果是最后一页，显示无数据
	                            	 if(listdata.length < rows){//表示最后一页了
	 	                            	// 数据加载完
	 	                                tab2LoadEnd = true;
	 	                                // 锁定
	 	                                me.lock();
	 	                                // 无数据
	 	                                me.noData();
	 	                            }
	                            	//每次数据加载完，必须重置
	                                 me.resetload();
	                            	
	                                 $(".lb-list").unbind("click");//清除table的所有click事件
	                            	//重新绑定每行点击事件
	                                 $('.lb-list').on('click', function() {
	                                	 var ordercode = $(this).find("input[type=hidden]").val();
	                                	 //查看订单详情
	                                	 lookUsertask('ed',ordercode);
	     		                     });
	                            	
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
	
	function lookUsertask(type,value) {
		if(type == 'ing'){
			$("#addForm").attr("action", "mobilebusiness/lookUsertask_ing?rid="+Math.random()+"&taskcode="+value);
			$("#addForm").submit();
		}else if(type == 'ed'){
			$("#addForm").attr("action", "mobilebusiness/lookUsertask_ed?rid="+Math.random()+"&ordercode="+value);
			$("#addForm").submit();
		}
	}
	
	</script>
</body>
</html>