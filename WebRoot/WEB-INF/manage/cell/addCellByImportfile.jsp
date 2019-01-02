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
<style type="text/css">
.easyui-panel table tr {
	height: 30px;
}
</style>
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm" enctype="multipart/form-data">
			<div class="easyui-panel" title="小区信息" style="width:100%;">
				<table style="width:100%">
					<tr>
						<td width="15%" align="right">文件路径：</td>
						<td>
							<input type="text" id="txt" name="txt" readonly="readonly" style="width: 230px" class="inp"/> 
							<input type="button" style="width:70px;height:23px;" name="selectbutton" id="selectbutton" value="请选择"  onclick="uploadfile.click()"/> 
							<input type="file" name="uploadfile" id="uploadfile" onchange="txt.value=this.value" style="position:absolute;width:100px;height:23px;filter:alpha(opacity=0);-moz-opacity:0;opacity:0;margin-left:-103px;"/>
						</td>
					</tr>
					<tr>
					  <td>
					  </td>
					</tr>
				</table>
			</div>
			<div class="form-box">
				<div class="fb-bom">
					<cite> 
						<span>
							<a href="cell/downloadTemplate" class="btn-print">模板下载</a>
						</span> 
					 <input type="button" class="btn-back" value="返回" onclick="goBack();" />
					 <input type="button" class="btn-save" value="保存" onclick="saveCell();"
					/> </cite><span class="red">${cell.returninfo}</span>
				</div>
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
		
		$.dialog({
		    title: '小区信息文件批量导入',
		    lock: true,
		    background: '#000', /* 背景色 */
		    opacity: 0.5,       /* 透明度 */
		    content: '是否确认文件导入?',
		    icon: 'alert.gif',
		    ok: function () {
		    	    $("#addForm").attr("action", "cell/saveByImportFile"+"?rid="+Math.random());
					$("#addForm").submit();
		        /* 这里要注意多层锁屏一定要加parent参数 */
		        $.dialog({
		        	lock: true,
		            title: '文件导入中',
		        	content: '后台代码正在执行中，请等候......', 
		        	max: false,
				    min: false,
				    cancel:false,
		        	icon: 'loading.gif',
		        });
		        return false;
		    },
		    okVal: '确认',
		    cancel: true,
		    cancelVal:'取消'
		});
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
