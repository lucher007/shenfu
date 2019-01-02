<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
  <style>
     .table tr {
      height: 30px;
    }
  </style>
</head>

<body>
<div id="body">
  <form action="" method="post" id="updateform" name="updateform">
    <input type="hidden" id="jumpurl" name = "jumpurl" value="forSaler">
    <input type="hidden" name="id" id="id" value="${user.user.id}"/>
    <input type="hidden" name="status" id="status" value="${user.user.status}"/>
    <input type="hidden" name="checkstatus" id="checkstatus" value="${user.user.checkstatus}"/>
    <input type="hidden" name="checktime" id="checktime" value="${user.user.checktime}"/>
    <div class="easyui-panel" title="客户信息" style="width:100%;">
       <table style="width:100%">
	       <tr>
	            <td align="right">客户编号：</td>
	            <td>
					<input type="text" class="inp" name="usercode" id="usercode" value="${user.user.usercode }" readonly="readonly" style="background:#eeeeee;">
				</td>
	       </tr>
	       <tr>
	            <td align="right">客户名称：</td>
	            <td>
					<input type="text" class="inp" name="username" id="username" value="${user.user.username }">
				</td>
	       </tr>
	          <!-- 
	          <tr>
				<td align="right">客户性别：</td>
				<td>
					<select id="usersex" name="usersex" class="select">
						<option value="0" <c:if test="${user.user.usersex == '0'}">selected</c:if>>女</option>
						<option value="1" <c:if test="${user.user.usersex == '1'}">selected</c:if>>男</option>
					</select>
				</td>
			</tr>
			 -->
			<tr>
				<td height="30" width="15%" align="right">联系电话：</td>
				<td width="25%"><input type="text" class="inp" name="phone" id="phone" value="${user.user.phone }" readonly="readonly" style="background:#eeeeee;" maxlength="12" onkeyup="checkNum(this)" onkeypress="checkNum(this)" onblur="checkNum(this)" onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
			</tr>
			<tr>
				<td height="30" width="15%" align="right">来源：</td>
				<td width="25%">
					<input type="hidden" class="inp" name="source" id="source" value="${user.user.source }">
					<input type="text" class="inp" name="sourcename" id="sourcename" value="${user.user.sourcename }" readonly="readonly" style="background:#eeeeee;">
				</td>
			</tr>
			<tr>
				<td height="30" width="15%" align="right">客户地址：</td>
				<td width="25%"><input type="text" class="inp" style="width:350px" name="address" id="address" value="${user.user.address }"></td>
			</tr>
			<tr>
				<td height="30" width="15%" align="right">上门类型：</td>
				<td width="25%">
					 <input type="hidden" class="inp" name="visittype" id="visittype" value="${user.user.visittype }">
					 <input type="text" class="inp" name="visittypename" id="visittypename" value="${user.user.visittypename }" readonly="readonly" style="background:#eeeeee; width: 200px;">
				</td>
			</tr>
			<tr>
				<td align="right">销售员编号：</td>
				<td>
					<input type="text" class="inp w200" name="salercode" id="salercode" readonly="readonly" style="background:#eeeeee;" value="${user.user.salercode }">
					<!-- 
					<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
					<a href="javascript:cleanEmployee();" class="easyui-linkbutton" iconCls="icon-undo" style="width:80px">清除</a>
					 -->
				</td>
			</tr>
			<tr>
				<td align="right">销售员姓名：</td>
				<td>
					<input type="text" class="inp w200" name="salername" id="salername" readonly="readonly" style="background:#eeeeee;" value="${user.user.saler.employeename }">
				</td>
			</tr>
			<tr style="height: 10px">
				<td align="right" width="15%">备注信息：</td>
				<td width="85%">
					 <textarea id="dealresult" name="dealresult" style="width:450px; height:60px;"
                      			onKeyDown="checkLength('dealresult',300)" onKeyUp="checkLength('dealresult',300)">${user.user.dealresult}</textarea>
           		  		<span class="red">还可以输入<span id="validNumdealresult">300</span>字</span>
				<td>
			</tr>
			<tr style="height: 10px">
				<td>
				<td>
			</tr>
        </table>
      </div>
      
      <div class="form-box">
			<div class="fb-bom">
				<cite> 
					<a href="javascript:goBack();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
	      			<a href="javascript:updateUser();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
				</cite> 
				<span class="red">${user.returninfo}</span>
			</div>
	  </div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript">

  function updateUser() {
	  
	  if ($("#username") != undefined && ($("#username").val() == "" || $("#username").val() == null )) {
			$.dialog.tips("请输入客户姓名", 1, "alert.gif", function() {
			$("#username").focus();
		});
		return false;
	  }
	
	  if ($("#phone") != undefined && ($("#phone").val() == "" || $("#phone").val() == null )) {
			$.dialog.tips("请输入电话号码", 1, "alert.gif", function() {
			$("#phone").focus();
		});
		return false;
	  }
      $('#updateform').attr('action', 'user/update');
      $("#updateform").submit();
  }
  
  function goBack() {
     parent.closeDialog();
  }
  
  $(function () {
      var returninfo = '${user.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
  });
  
  var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '上级销售员查询',
			lock : true,
			width : 900,
			height : 480,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:employee/findSalerListDialog?rid='+Math.random()
		});
	}
  
  function closeEmployeeDialog(jsonStr) {
		employeeDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
	    
	    $("#salercode").val(jsonObject.employeecode);
		$("#salername").val(jsonObject.employeename);
	}
	
	function cleanEmployee() {
		  $('#salercode').val("");
		  $('#salername').val("");
	  }
</script>
</body>
</html>
