<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title><spring:message code="page.title" /></title>
  <link rel="stylesheet" href="<%=path%>/main/plugin/easyui/themes/default/easyui.css"/>
  <style type="text/css">
   .tilte {
      width: 100%;
      padding-top:10px;
      background-repeat: repeat;
      font-family: 'Microsoft YaHei';
      font-size: 30px;
      text-align: center;
      color: #000000;
      background-image: url('<%=path%>/main/images/title1.jpg');
      background-size:cover;
    }
    
   .nav {
      width: 100%;
      height: 100%;
      text-align: center;
      line-height: 25px;
      background-repeat: repeat;
      font-family: 'Microsoft YaHei';
      font-size: 15px;
      color: #000000;
      background-image: url('<%=path%>/main/images/nav.png');
      background-size:cover;
      
    }
    
   .footer {
      width: 100%;
      height: 100%;
      text-align: center;
      line-height: 35px;
      background-repeat: repeat;
      font-family: 'Microsoft YaHei';
      font-size: 15px;
      color: #ffffff;
      padding-top: 20px;
      background-image: url('<%=path%>/main/images/foot_bg.jpg');
      background-size:cover;
    }
    
    a {
      font-family: 'Microsoft YaHei';
      font-size: 15px;
      color: #fff;
      text-decoration: none;
    }
    
   *{margin: 0;padding: 0}
	body{font-size: 12px;font-family: "宋体","微软雅黑";}
	ul,li{list-style: none;}
	a:link,a:visited{text-decoration: none;}
    
    .list{width: 100%;border-bottom:solid 1px #316a91;margin:0px auto 0 auto;}
	.list ul li{background-color:#6196bb; border:solid 1px #316a91; border-bottom:0;}
	.list ul li a{padding-left: 10px;color: #fff; font-size:12px; display: block; font-weight:bold; height:30px;line-height: 30px;position: relative;}
	.list ul li .inactive{ background:url('<%=path%>/main/images/off.png') no-repeat 184px center;}
	.list ul li .inactives{ background:url('<%=path%>/main/images/on.png') no-repeat 184px center;} 
	
	.list ul li ul{display: none;}
	.list ul li ul li { border-left:0; border-right:0; background-color:#d6e6f1; border-color:#6196bb;}
	.list ul li ul li ul{display: none;}
	.list ul li ul li a{color:#316a91; padding-left:20px;}
	.list ul li ul li ul li { background-color:#d6e6f1; border-color:#6196bb; }
	.last{ background-color:#d6e6f1; border-color:#6196bb; }
	.list ul li ul li ul li a{ color:#316a91; padding-left:30px;}
  </style>
</head>
<body class="easyui-layout" data-options="fit:true">
<!-- header -->
<div data-options="region:'north',border:false" style="overflow:hidden; height:120px;">
  <div>
	  <div class="tilte" id="tilte" style="height:80px;">
		  	<span style="text-align:center; display:block;color: #ffffff;">申亚科技管理系统</span>
	  </div>
	  <div class="nav" id="nav" style="height:25px;">
	    <span style="font-weight: bold;  left:20px; position: absolute;">
	      	登陆名称：<a href="javascript:userInfo();">${Operator.loginname}</a>
	    </span>
		<span style="right:20px;  position: absolute; color: #fff">
	          <a href="javascript:logout();">退出系统</a>
	          <span>|</span>
	          <a href="javascript:helpInfo();">系统帮助</a>
	    </span>
	    </div>
  </div>
</div>

<!-- 左边树形结构 -->
<div data-options="region:'west',border:true" style="width:200px;background-repeat:repeat;">
  <div class="list">
	<ul class="yiji">
	    <c:forEach items="${menulist}" var="data" varStatus="vs">
	    	<li>	
	    		<a href="#" class="inactive">${data.menuname}</a>
	    		<ul style="display: none">
	    		 <c:forEach items="${data.secondmenulist}" var="seconddata" varStatus="vs">
	    		 	<li><a href="#" name="${seconddata.menuname}" url="${seconddata.menuurl}" class="secondmenu">${seconddata.menuname}</a></li>
	    		 </c:forEach>
	    		</ul>
	    	</li>
	    </c:forEach>
	</ul>
  </div>
</div>

<!-- 右边列表内容 -->
<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
	<div id="tabs" class="easyui-tabs" data-options="region:'center',border:false,fit:true">
	</div>
</div>

<!-- footer -->
<div data-options="region:'south',border:false"
     style="overflow:hidden;height:50px;">
  <div class="footer">
    <span> <strong>${initParam.copyright }</strong></span>
  </div>
</div>
  <script type="text/javascript" src="<%=path%>/main/plugin/jquery/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="<%=path%>/main/plugin/easyui/js/easyui.js"></script>
  <script type="text/javascript" src="<%=path%>/js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
  <script type="text/javascript">
	$(document).ready(function() {
	    
	    //点击一级菜单展开二级菜单样式
		$(".inactive").click(function(){
			if($(this).siblings('ul').css('display')=='none'){
				$(this).parent('li').siblings('li').removeClass('inactives');
				$(this).addClass('inactives');
				$(this).siblings('ul').slideDown(100).children('li');
			    $(this).parent('li').siblings('li').children('a').removeClass('inactives');
			    $(this).parent('li').siblings('li').children('ul').slideUp(100);
			}else{
				//控制自身变成+号
				$(this).removeClass('inactives');
				//控制自身菜单下子菜单隐藏
				$(this).siblings('ul').slideUp(100);
				//控制自身子菜单变成+号
				$(this).siblings('ul').children('li').children('ul').parent('li').children('a').addClass('inactives');
				//控制自身菜单下子菜单隐藏
				$(this).siblings('ul').children('li').children('ul').slideUp(100);
				//控制同级菜单只保持一个是展开的（-号显示）
				$(this).siblings('ul').children('li').children('a').removeClass('inactives');
			}
		});
		
		//点击二级菜单跳转
		$(".secondmenu").click(function(){
			var name = $(this).attr("name");
			var url = $(this).attr("url");
			if(url!=null && url != ''){
				reflashTab(name,url);
			}
		});
		
	});
	
	/**
	 *退出系统
	 */
	function logout() {
		$.dialog.confirm('确认是否退出？', function(){ 
				window.top.location.href = '<%=request.getContextPath()%>/operator/logout';
			}, function(){
						});				
	  
	}
	
	var helpInfoDialog;
	function helpInfo() {
	  helpInfoDialog = $.dialog({
	    title: '系统帮助',
	    lock: true,
	    width: 800,
	    height: 200,
	    drag: false,
	    resize: false,
	    max: false,
	    min: false,
	    content: 'url:<%=request.getContextPath()%>/operator/helpInfoInit?t=' + new Date().getTime()
	  });
	}
	
	
	
var userInfoDialog;
function userInfo() {
	
  var id = '${Operator.id}';
	
  userInfoDialog = $.dialog({
    title: '操作员修改',
    lock: true,
    width: 800,
    height: 400,
    drag: false,
    resize: false,
    max: false,
    min: false,
    content: 'url:<%=request.getContextPath()%>/menu/updateInit?t=' + new Date().getTime() + '&id=' + id
  });
}

//新增Tab模式
function addTab(title, url){
	if ($('#tabs').tabs('exists', title)){
		$('#tabs').tabs('select', title);
		//刷新数据
		var selTab = $('#tabs').tabs('getSelected');
        var url = $(selTab.panel('options').content).attr('src');     
        $('#tabs').tabs('update', {
            tab: selTab,
            options: {
                content:createFrame(url)
            }
        })
		
	} else {
		var content = '<iframe scrolling="auto" frameborder="0"  src="<%=request.getContextPath()%>/'+url+'" style="width:100%;height:100%;"></iframe>';
		$('#tabs').tabs('add',{
			//closable:true,//Tab可以关闭
			title:title,
			content:content,
			closable:true
		});
	}
}

//刷新Tab模式
function reflashTab(title, url){
	
	closeAllTabs();
	
	var currTab =  $('#tabs').tabs('getSelected'); //获得当前tab
	if(currTab == null){//当前没有Tab,新增一个
		addTab(title, url);
	}else{//当前有tab,修改当前的Tab
		$('#tabs').tabs('update', {
      		tab : currTab,
        	options : {
        		title:title,
      			content : '<iframe scrolling="auto" frameborder="0"  src="<%=request.getContextPath()%>/'+url+'" style="width:100%;height:100%;"></iframe>'
      		}
     	});
	}
}

//关闭所有Tab模式
function closeAllTabs(){
	$(".tabs li").each(function(index, obj) {  
        //获取所有可关闭的选项卡  
		 $("#tabs").tabs('close',index);//关闭对应index的tabs 
    });  
}

//关闭指定Tab模式
function closeTab(title){
	if ($('#tabs').tabs('exists', title)) {  
        $('#tabs').tabs('close', title);  
    } 
}

//关闭指定Tab模式
function closeTabSelected(){
	var currTab =  $('#tabs').tabs('getSelected'); //获得当前选择tab
	if(currTab != null){//当前没有Tab,新增一个
		 var index = $('#tabs').tabs('getTabIndex',currTab);//获取当前选中tabs的index  
		 $('#tabs').tabs('close', index);  
	}
}


function closeDialog(){
	userInfoDialog.close();
}
	
//选择时刷新	
$('#tabs').tabs({
	onSelect:function(title,index){
	var select=$('#tabs').tabs('getTab',index);
	var url=$(select.panel('options').content).attr('src');
	$('#tabs').tabs("update",{
		tab:select,
		options:{
			title:title, 
	        //href:URL, // 使用href会导致页面加载两次，所以使用content代替  
	        content:"<iframe scrolling='auto' frameborder='0'" +
			"src='" + url + "' style='width:100%;height:100%;'></iframe>"
		}
	})
	}
})
</script>
</body>
</html>
