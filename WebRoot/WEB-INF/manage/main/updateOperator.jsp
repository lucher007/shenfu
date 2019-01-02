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
  <style>
    .fb-con table tr {
      height: 30px;
    }
  </style>
</head>

<body>
<div id="body">
  <form action="" method="post" id="updateform" name="updateform">
    <input type="hidden" name="id" id="id" value="${operator.operator.id}"/>
    <input type="hidden" name="employeecode" id="employeecode" value="${operator.operator.employeecode}"/>
    <input type="hidden" name="operatortype" id="operatortype" value="${operator.operator.operatortype}"/>
    <input type="hidden" name="status" id="status" value="${operator.operator.status}"/>
    <div class="form-box">
      <div class="fb-tit">操作员修改</div>
      <div class="fb-con">
       <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right">操作员编号：</td>
			<td style="font-weight: bold;">
				${operator.operator.employeecode }
			</td>
          </tr>
          <tr>
            <td align="right">操作员姓名：</td>
			<td style="font-weight: bold;">
				${operator.operator.employee.employeename }
			</td>
          </tr>
          <tr>
            <td align="right">登录名称：</td>
            <td >
            	<input type="text" class="inp" name="loginname" id="loginname" value="${operator.operator.loginname }"> <span class="red">*</span>
            </td>
          </tr>
		  <tr>
			<td align="right"></td>
          	<td>
          		<a class="btn-edit" href="javascript:updatePasswordInit();">密码修改</a>
			</td>
		  </tr> 
        </table>
      </div>
      <div class="fb-bom">
        <cite>
        	<input type="button" class="btn-back" value="返回" onclick="goBack()" >
            <input type="button" class="btn-save" value="保存" onclick="updateOperator();" id="btnfinish">
        </cite>
        <span class="red">${operator.returninfo }</span>
      </div>
    </div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript">

  function updateOperator() {
	  if ($("#loginname") != undefined && ($("#loginname").val() == "" || $("#loginname").val() == null )) {
			$.dialog.tips("请输入登录名称", 1, "alert.gif", function() {
				$("#loginname").focus();
			});
			return false;
		}
      $('#updateform').attr('action', 'menu/update');
      $("#updateform").submit();
  }
  
  function updatePasswordInit() {
      $("#updateform").attr("action", "menu/updatePasswordInit");
      $("#updateform").submit();
  }
  
  $(function () {
      var returninfo = '${operator.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
  });
  
  function goBack() {
     parent.closeDialog();
  }
</script>
</body>
</html>
