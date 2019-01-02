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
			<input type="hidden" id="jumpurl" name = "jumpurl" value="add">
				<div class="easyui-panel" title="任务单信息" style="width:100%;" data-options="footer:'#ft'">
					<div class="easyui-panel" style="width:100%;">
						<table style="width:100%;border:0; cellpadding:0; cellspacing:0">
							<tr>
								<td align="right" width="10%">客户编号：</td>
								<td width="25%">
									<input type="text" class="inp w200" name="usercode" id="usercode" readonly="readonly" style="background:#eeeeee;" >
									<a href="javascript:chooseUser();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
								</td>
							</tr>
							<tr>
								<td align="right" width="10%">客户姓名：</td>
								<td width="25%">
									<input type="text" class="inp w200" name="username" id="username" readonly="readonly" style="background:#eeeeee;" ><span class="red">*</span>
								</td>
							</tr>
							<tr>
								<td align="right">联系电话：</td>
								<td><input type="text" class="inp" name="phone" id="phone" readonly="readonly" style="background:#eeeeee;" > <span class="red">*</span></td>
							</tr>
							<tr>
								<td align="right">客户来源：</td>
								<td>
									<select id="source" name="source" class="select">
					    				<option value=""  <c:if test="${usertask.source == ''}">selected</c:if>>请选择</option>
										<option value="0" <c:if test="${usertask.source == '0'}">selected</c:if>>销售</option>
										<option value="1" <c:if test="${usertask.source == '1'}">selected</c:if>>400客服</option>
									</select>
								</td>
							</tr>
							<tr>
								<td align="right">客户地址：</td>
								<td><input type="text" class="inp" style="width: 400px;" name="address" id="address">
							</tr>
							<tr>
								<td align="right">上门类型：</td>
								<td>
									<select id="visittype" name="visittype" class="select" style="width: 200px;">
					    				<option value=""  <c:if test="${usertask.visittype == ''}">selected</c:if>>请选择</option>
										<option value="0" <c:if test="${usertask.visittype == '0'}">selected</c:if>>公司派人推销测量</option>
										<option value="1" <c:if test="${usertask.visittype == '1'}">selected</c:if>>销售员亲自推销测量</option>
										<option value="2" <c:if test="${usertask.visittype == '2'}">selected</c:if>>已推销，公司派人只需测量</option>
									</select>
								</td>
							</tr>
						</table>
					</div>
					
					<div class="easyui-panel" title="上门人员信息" style="width:100%;">
						<table style="width:100%;border:0; cellpadding:0; cellspacing:0">
							<tr>
								<td align="right" width="10%">上门人员编号：</td>
								<td width="25%">
									<input type="text" class="inp w200" name="visitorcode" id="visitorcode" readonly="readonly" style="background:#eeeeee;" value="${usertask.visitorcode }">
									<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
								</td>
							</tr>
							<tr>
								<td align="right" width="10%">上门人员姓名：</td>
								<td width="25%">
									<input type="text" class="inp w200" name="visitorname" id="visitorname" readonly="readonly" style="background:#eeeeee;" value="${usertask.visitorname }"><span class="red">*</span>
								</td>
							</tr>
							<tr>
								<td align="right">联系电话：</td>
								<td><input type="text" class="inp" name="visitorphone" id="visitorphone" readonly="readonly" style="background:#eeeeee;" value="${usertask.visitorphone }"> <span class="red">*</span></td>
							</tr>
						</table>
					</div>
				</div>
				<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveUser();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
				    </cite><span class="red">${usertask.returninfo}</span>
			    </div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/comtools.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript">
	
	function checkVal() {
		if ($("#usercode") != undefined && ($("#usercode").val() == "" || $("#usercode").val() == null )) {
			$.dialog.tips("请选择客户", 1, "alert.gif", function() {
				$("#usercode").focus();
			});
			return false;
		}
		if ($("#visitorcode") != undefined && ($("#visitorcode").val() == "" || $("#visitorcode").val() == null )) {
			$.dialog.tips("请选择上门人员", 1, "alert.gif", function() {
				$("#visitorcode").focus();
			});
			return false;
		}
		return true;
	}
	
	function saveUser() {
		if (!checkVal()) {
			return;
		}
		
		var usercode = $("#usercode").val();
		
		$.dialog({
		    title: '提交',
		    lock: true,
		    background: '#000', /* 背景色 */
		    opacity: 0.5,       /* 透明度 */
		    content: '是否确定执行？',
		    icon: 'alert.gif',
		    ok: function () {
		    	$("#addForm").attr("action", "task/save?usercodes="+usercode+"&rid="+Math.random());
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
       var returninfo = '${usertask.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 2, 'alert.gif');
       }
    });
    
    var userDialog;
	function chooseUser() {
		userDialog = $.dialog({
			title : '客户信息',
			lock : true,
			width : 1000,
			height : 550,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:user/findUserListDialog?rid='+Math.random()
		});
	}

	function closeUserDialog(jsonStr) {
		userDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
		$("#usercode").val(jsonObject.usercode);
		$("#username").val(jsonObject.username);
		$("#phone").val(jsonObject.phone);
		//$("#usersex").val(jsonObject.usersex);
		$("#source").val(jsonObject.source);
		$("#address").val(jsonObject.address);
		$("#visittype").val(jsonObject.visittype);
		//销售员上门
		if("1" == jsonObject.visittype){
			$("#visitorcode").val(jsonObject.salercode);
			$("#visitorname").val(jsonObject.salername);
			$("#visitorphone").val(jsonObject.salerphone);
		}else{
			$("#visitorcode").val("");
			$("#visitorname").val("");
			$("#visitorphone").val("");
		}
	}
    
    var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '上门人员查询',
			lock : true,
			width : 900,
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
		$("#visitorcode").val(jsonObject.employeecode);
		$("#visitorname").val(jsonObject.employeename);
		$("#visitorphone").val(jsonObject.phone);
	}
    
</script>
</body>
</html>
