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
					<tr>
						<td align="right">员工编号：</td>
						<td>
							<input type="text" class="inp w200" name="employeecode" id="employeecode" readonly="readonly" style="background:#eeeeee;" value="${operator.employeecode }">
							<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
						</td>
					</tr>
					<tr>
						<td align="right">员工姓名：</td>
						<td>
							<input type="text" class="inp w200" name="employeename" id="employeename" readonly="readonly" style="background:#eeeeee;" value="${operator.employee.employeename }">
						</td>
					</tr>
					<tr>
						<td align="right">登录名称：</td>
						<td><input type="text" class="inp" name="loginname" id="loginname" value="${operator.loginname }"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">登录密码：</td>
						<td><input type="password" class="inp" name="password" id="password" value="${operator.password }"> <span class="red">*</span></td>
					</tr>	
					<tr>
						<td align="right">确认密码：</td>
						<td><input type="password" class="inp" name="confirmpassword" id="confirmpassword" value="${operator.confirmpassword }"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">所属角色：</td>
						<td >
							<select id="roleid" name="roleid" class="select">
								<c:forEach items="${operator.rolemap}" var="roleMap" varStatus="s">
									<option value="${roleMap.key}" <c:if test="${roleMap.key == operator.roleid}">selected</c:if>>${roleMap.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveOperator();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
				    </cite><span class="red">${operator.returninfo}</span>
			</div>
			
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript">
	
	function checkVal() {
		if ($("#employeecode") != undefined && ($("#employeecode").val() == "" || $("#employeecode").val() == null )) {
			$.dialog.tips("请选择员工", 1, "alert.gif", function() {
				$("#employeecode").focus();
			});
			return false;
		}
		
		if ($("#loginname") != undefined && ($("#loginname").val() == "" || $("#loginname").val() == null )) {
			$.dialog.tips("请输入登录名称", 1, "alert.gif", function() {
				$("#loginname").focus();
			});
			return false;
		}
		
		if ($("#password") != undefined && ($("#password").val() == "" || $("#password").val() == null)) {
			$.dialog.tips("请输入登录密码", 1, "alert.gif", function() {
				$("#password").focus();
			});
			return false;
		}
		
		if ($("#confirmpassword") != undefined && ($("#confirmpassword").val() == "" || $("#confirmpassword").val() == null)) {
			$.dialog.tips("请输入确认密码", 1, "alert.gif", function() {
				$("#confirmpassword").focus();
			});
			return false;
		} else if ($("#password").val() != $("#confirmpassword").val()) {
			$.dialog.tips("俩次输入密码不一样", 1, "alert.gif", function() {
				$("#confirmpassword").focus();
			});
			return false;
		}
		
		return true;
	}
	
	function saveOperator() {
		if (!checkVal()) {
			return;
		}
		
		$.dialog({
		    title: '提交',
		    lock: true,
		    background: '#000', /* 背景色 */
		    opacity: 0.5,       /* 透明度 */
		    content: '是否确定执行？',
		    icon: 'alert.gif',
		    ok: function () {
		    	$("#addForm").attr("action", "operator/save?rid="+Math.random());
				$("#addForm").submit();
		        /* 这里要注意多层锁屏一定要加parent参数 */
		        $.dialog({
		        	lock: true,
		            title: '提示',
		        	content: '执行中，请等待......', 
		        	max: false,
				    min: false,
				    cancel:false,
		        	icon: 'loading.gif',
		        });
		        return false;
		    },
		    okVal: '确定',
		    cancel: true,
		    cancelVal:'取消'
		});
		
	}
	
	function goback(){
		parent.closeDialog();
	}
	
	$(function () {
       var returninfo = '${operator.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
    
    var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '员工查询',
			lock : true,
			width : 1100,
			height : 500,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:employee/findEmployeeListDialog?rid='+Math.random()
		});
	}

	function closeEmployeeDialog(jsonStr) {
		employeeDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
	    
		$("#employeecode").val(jsonObject.employeecode);
		$("#employeename").val(jsonObject.employeename);
		$("#loginname").val(jsonObject.employeename);
	}
    
</script>
</body>
</html>
