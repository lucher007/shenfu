<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>选择所在区域</title>
    <title></title>
		<link type="text/css" rel="stylesheet" href="/style/user.css">
    <style type="text/css">
        html
        {
            height: 100%;
        }
        body
        {
            height: 50%;
            margin: 0px;
            padding: 0px;
        }
        #container
        {
            width:950px;
            height:500px;
        }
    </style>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
</head>
<body onload="loand()">
    <div id="container">
    </div>
    <script type="text/javascript">
        function loand() {
        	var selArray=new Array(3);
         	var map = new BMap.Map("container");
         	var lng = 104.083;
         	var lat = 30.686;
         	var plng = parent.document.getElementById("longitude").value;
         	var plat = parent.document.getElementById("latitude").value;
         	var add  = parent.document.getElementById("address").value;
         	if(plng!=""){lng=plng;};
         	if(plat!=""){lat=plat;};
            var point = new BMap.Point(lng, lat); //默认中心点
            var marker = new BMap.Marker(point);
            var opts = {
                width: 250,     // 信息窗口宽度  
                height: 120,     // 信息窗口高度  
                title: "<b class='f8 red'>选择地址</b>"  // 信息窗口标题  
            }
            var infoWindow = new BMap.InfoWindow((add==""?"":"<br>地址："+add+"<br>")+"<br><b class='red'>注：用鼠标拖动红点来标注位置</b>", opts);  // 创建信息窗口对象
            marker.enableDragging(); //启用拖拽         
            map.addControl(new BMap.NavigationControl()); //左上角控件
            map.enableScrollWheelZoom(); //滚动放大
            map.enableKeyboard(); //键盘放大
            map.centerAndZoom(point, 13); //绘制地图
            map.addOverlay(marker); //标记地图
            map.addControl(new BMap.OverviewMapControl());              //添加默认缩略地图控件
			map.addControl(new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_TOP_RIGHT}));   //右上角，打开
            map.openInfoWindow(infoWindow, map.getCenter());// 打开信息窗口      
            marker.addEventListener('dragend', function(e){
				lng = e.point.lng;
				lat = e.point.lat;
				selArray[0]=0;
				selArray[1]=lng;
				selArray[2]=lat;
				parent.closeMap(selArray);
			});
			selArray[0]=0;
			selArray[1]=lng;
			selArray[2]=lat;
			parent.closeMap(selArray);
        }
        
    </script>
</body>
</html> 