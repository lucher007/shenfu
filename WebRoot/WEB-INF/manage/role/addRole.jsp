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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<base href="<%=basePath%>" />
<meta charset="utf-8">
<title></title>
<link type="text/css" rel="stylesheet" href="style/user.css">
<link type="text/css" rel="stylesheet" href="js/plugin/poshytip/tip-yellowsimple/tip-yellowsimple.css">
<link type="text/css" rel="stylesheet" href="js/plugin/treeTable/css/jquery.treetable.css">
<link rel="stylesheet" href="main/plugin/ztree/css/metroStyle/metroStyle.css" />
<link rel="stylesheet" type="text/css" href="style/easyui/easyui.css">
<link rel="stylesheet" type="text/css" href="main/plugin/easyui/themes/icon.css">

<style type="text/css">
#body table tr {
	height: 30px;
}
</style>
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm">
			<div class="easyui-panel" title="操作员信息" style="width:100%;" data-options="footer:'#ft'">
				<table style="width:100%">
					<tr height="30px">
						<td align="right" height="30">角色名称：</td>
						<td align="left"><input type="text" class="inp" name="rolename" id="rolename" />
						</td>
					</tr>

					<tr height="30px">
						<td align="right">角色编码：</td>
						<td align="left"><input type="text" class="inp" name="rolecode" id="rolecode" /></td>
					</tr>

					<tr height="40px">
						<td align="right">角色权限设定：</td>
						<td align="left">
							<div class="easyui-panel" style="width:250px;padding:10px;">
								<ul id="tt" class="easyui-tree" data-options="url:'role/getMenuTreeJson',method:'get',animate:true,checkbox:true"></ul>
							</div>
						</td>
					</tr>
			
				</table>
			</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveRole();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
				    </cite><span class="red">${role.returninfo}</span>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript">
	
		function goback() {
			parent.closeDialog();
		}
		
		function saveRole() {
			var menuids = '';
			var nodes = $('#tt').tree('getChecked');
			for ( var i = 0; i < nodes.length; i++) {
				if (menuids != ''){
					menuids += ',';
				}
				menuids += nodes[i].id;
			}
			
			if ($('#rolename').val() == '') {
			    $.dialog.tips('角色名称不能为空', 1, 'alert.gif');
			    $('#rolename').focus();
		      return;
		    }
			
			if ($('#rolecode').val() == '') {
			    $.dialog.tips('角色编码不能为空', 1, 'alert.gif');
			    $('#rolecode').focus();
		      return;
		    }
		    
			if(menuids.length < 1){
				$.dialog.tips('请选择一条菜单权限', 1, 'alert.gif');
			return; 
			}
			$("#addForm").attr("action", "role/saveRole?menuids=" + menuids).submit();
		}

		$(function() {
			var returninfo = '${role.returninfo}';
			if (returninfo != '') {
				$.dialog.tips(returninfo, 1, 'alert.gif');
			}
		});

	</script>
</body>
</html>
