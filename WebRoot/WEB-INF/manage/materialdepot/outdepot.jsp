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
<link rel="stylesheet" href="main/plugin/easyui/themes/default/easyui.css"/>
<style type="text/css">
.fb-con table tr {
	height: 30px;
}
</style>
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm">
			<div class="form-box">
				<div class="fb-tit">材料出库</div>
				<div class="fb-con">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="30" width="15%" align="right">材料名称：</td>
							<td width="25%">
								<input id="materialcode" name="materialcode"> <span class="red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">出库量：</td>
							<td>
								<input type="text" class="inp" name="depotamount" id="depotamount" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"> <span class="red">*</span>
							</td> 
						</tr>
					</table>
				</div>
				<div class="fb-bom">
					<cite>
					  <input type="button" class="btn-save" value="保存" onclick="saveOutdepot();"/>
				    </cite> 
				    <span class="red">${materialdepot.returninfo}</span>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/comtools.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript" src="main/plugin/easyui/js/easyui.js"></script>
	<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
	
	function checkVal() {
		
		if ($('#materialid').combobox('getValue') == "") {
			$.dialog.tips("请选择出库材料", 1, "alert.gif", function() {
				$("#materialid").focus();
			});
			return false;
		}
		
		if ($("#depotamount") != undefined && ($("#depotamount").val() == "" || $("#depotamount").val() == null )) {
			$.dialog.tips("请输入材料出库量", 1, "alert.gif", function() {
				$("#depotamount").focus();
			});
			return false;
		}
		
		return true;
	}
	
	function saveOutdepot() {
		if (!checkVal()) {
			return;
		}
		$("#addForm").attr("action", "materialdepot/saveOutdepot");
	    $("#addForm").submit();
	}

	
	$(function () {
       var returninfo = '${materialdepot.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
	
	//加载材料列表
	$('#materialcode').combobox({    
	    url:'material/getMaterialComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${materialdepot.materialcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
              
        } 
	}); 
	
</script>
</body>
</html>
