<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<title></title>
<link type="text/css" rel="stylesheet" href="style/user.css">
<link rel="stylesheet" type="text/css" href="style/easyui/easyui.css">
<link rel="stylesheet" type="text/css" href="main/plugin/easyui/themes/icon.css">
<style type="text/css">
.easyui-panel table tr {
	height: 30px;
}
</style>
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm">
			<div class="easyui-panel" title="小区信息" style="width:100%;" data-options="footer:'#ft'">
				<table style="width:100%">
					<tr>
						<td align="right">小区名称：</td>
						<td >
							<input type="text" class="inp" style="width: 250px;" name="cellname" id="cellname" value="${cell.cellname }"><span class="red">*</span>
						</td>
					</tr>
					<tr >
		            <td align="right">楼栋：</td>
		            <td>
						<input type="text" class="inp" name="building" id="building" style="width:250px;" value="${cell.building }">
					</td>
				  </tr>
				  <tr >
		            <td align="right">楼层：</td>
		            <td>
						<input type="text" class="inp" name="floor" id="floor" style="width:250px;" value="${cell.floor }">
					</td>
				  </tr>
				  <tr >
		            <td align="right">总户数：</td>
		            <td>
						<input type="text" class="inp easyui-numberbox" name="totalhouse" id="totalhouse" style="width:250px;" value="${cell.totalhouse }">
					</td>
				  </tr>
				  <tr >
		            <td align="right">户型：</td>
		            <td>
						<input type="text" class="inp" name="layout" id="layout" style="width:250px;" value="${cell.layout }">
					</td>
				  </tr>
					<tr>
						<td align="right">安防级别：</td>
						<td >
							<select id="safelevel" name="safelevel" class="select">
							    <option value="" <c:if test="${cell.safelevel == null || cell.safelevel == ''}">selected</c:if>>未知</option>
								<option value="1" <c:if test="${cell.safelevel == '1'}">selected</c:if>>高级</option>
								<option value="2" <c:if test="${cell.safelevel == '2'}">selected</c:if>>中级</option>
								<option value="3" <c:if test="${cell.safelevel == '3'}">selected</c:if>>低级</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">小区地址：</td>
						<td ><input type="text" class="inp" style="width: 450px;" name="address" id="address" value="${cell.address }"><span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">开盘时间：</td>
						<td >
							<input type="text" id="opentime" name="opentime"   value="${cell.opentime }"
                    			onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});" class="Wdate inp w150">
						</td>
					</tr>
					<tr>
						<td align="right">交房时间：</td>
						<td>
							<input type="text" id="handtime" name="handtime"   value="${cell.handtime }"
                    			onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});" class="Wdate inp w150">
						</td>
					</tr>
					<tr>
						<td align="right">开发商：</td>
						<td >
							<input type="text" class="inp" style="width: 250px;"  name="developer" id="developer" value="${cell.developer }"> 
						</td>
					</tr>
					<tr>
						<td align="right">小区广告：</td>
						<td >
							<input type="text" class="inp" style="width: 250px;"  name="advertisement" id="advertisement" value="${cell.advertisement }"> 
						</td>
					</tr>
					<tr>
						<td align="right">物业：</td>
						<td >
							<input type="text" class="inp" style="width: 250px;"  name="property" id="property" value="${cell.property }"> 
						</td>
					</tr>
					<tr>
						<td align="right">物业是否允许驻点：</td>
						<td >
							<select id="allowstation" name="allowstation" class="select">
							    <option value="1" <c:if test="${cell.allowstation == '1'}">selected</c:if>>允许驻点</option>
							    <option value="0" <c:if test="${cell.allowstation == '0'}">selected</c:if>>不允许驻点</option>
							    <option value="2" <c:if test="${cell.allowstation == '2'}">selected</c:if>>未知</option>
							</select>
						</td>
					</tr>
					<!--
					<tr>
						<td align="right">区域地址：</td>
						<td >
							<div id="location" class="citys">
								<select name="province" class="select"></select>
			                    <select name="city" class="select"></select>
			                    <select name="area" class="select"></select> 
		                    </div>
						</td>
					</tr>
					<tr>
						<td align="right">经度：</td>
						<td >
			            	<input type="text" class="inp" name="longitude" id="longitude" maxlength="6" readonly="readonly" style="background:#eeeeee;" value="${cell.longitude}">
						</td>
			          </tr>
			          <tr>
						<td align="right">纬度：</td>
						<td>
			            	<input type="text" class="inp"  name="latitude" id="latitude" maxlength="6" readonly="readonly" style="background:#eeeeee;" value="${cell.latitude}">
			            	<input type="button" value="选取经纬度" id="lonlat" onclick="openMap()" style="padding:3px;">
						</td>
						<td align="left">
							
						</td>
			          </tr>
					<tr>
					  <td>
					  </td>
					</tr>
					  -->
				</table>
			</div>
			<div id="ft" style="padding:5px;">
				<cite> 
					<a href="javascript:goBack();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
					<a href="javascript:saveCell();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
			    </cite><span class="red">${cell.returninfo}</span>
		    </div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
	<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/areacode/jquery.citys.js"></script>
	<script type="text/javascript">
	$('#location').citys({
		//dataUrl:'js/areacode/areaInfo.json',  //数据库地址
	    required:false,
	    nodata:'disabled',
	    onChange:function(data){
	        var text = data['direct']?'(直辖市)':'';
	        $('#address').val(data['province']+text+data['city']+data['area']);
	    }
	});
	
   
	
	function checkVal() {
		if ($("#cellname") != undefined && ($("#cellname").val() == "" || $("#cellname").val() == null )) {
			$.dialog.tips("请输入小区名称", 1, "alert.gif", function() {
				$("#cellname").focus();
			});
			return false;
		}
		
		if ($("#address") != undefined && ($("#address").val() == "" || $("#address").val() == null)) {
			$.dialog.tips("请输入小区地址", 1, "alert.gif", function() {
				$("#address").focus();
			});
			return false;
		}
		return true;
	}
	
	function saveCell() {
		if (!checkVal()) {
			return;
		}
		$("#addForm").attr("action", "cell/save");
	    $("#addForm").submit();
	}
	
	function goBack(){
		parent.closeDialog();
	}
	
	$(function () {
       var returninfo = '${cell.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
    function openMap(){
    
    	if ($("#address") != undefined && ($("#address").val() == "" || $("#address").val() == null)) {
			$.dialog.tips("请输入小区地址", 1, "alert.gif", function() {
				$("#address").focus();
			});
			return false;
		}	
    	
		//$("#lonlat").attr("disabled","disabled");
		//创建地址解析器
		var myGeo = new BMap.Geocoder();
		//解析详细地址
		myGeo.getPoint($("#address").val(),function(point){
			if(point){
				$("#longitude").val(point.lng);
				$("#latitude").val(point.lat);			
			}
			t1 = $.dialog({
				title:'标注所在区域',
				id:'lonlat',
				lock:true,
				padding:0,
				content: '<iframe frameborder="0" scrolling="no" name="tFrame" id="tFrame" width="950" height="450" src="'+"map/mapBaseChoice?ischoice="+Math.random()+'"></iframe>',
				cancelVal: "确定",
				cancel:function(){}
			});
		},$("#province").val());
	}
	
	function closeMap(s){
		$("#lonlat").removeAttr("disabled");
		$("#longitude").val(s[1]);
		$("#latitude").val(s[2]);
	}
    
    
</script>
</body>
</html>
