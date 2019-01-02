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
     #body table tr {
		height: 30px;
	}
  </style>
</head>

<body>
<div id="body">
  <form action="" method="post" id="updateform" name="updateform">
    <input type="hidden" name="id" id="id" value="${timepara.timepara.id}"/>
    	<div class="easyui-panel" title="时间参数信息" style="width:100%;" data-options="footer:'#ft'">		
			<table style="width:100%;border:0; cellpadding:0; cellspacing:0">
				<tr>
					<td align="right" width="10%">时间等级：</td>
					<td style="width: 25%; font-weight: bold;">
						<input type="hidden" name="level" id="level" class="inp" value="${timepara.timepara.level}">
						<c:if test="${timepara.timepara.level == 1}">
							<span class="green">${timepara.timepara.levelname}</span>
						</c:if>
						<c:if test="${timepara.timepara.level == 2}">
							<span class="blue">${timepara.timepara.levelname}</span>
						</c:if>
						<c:if test="${timepara.timepara.level == 3}">
							<span class="red">${timepara.timepara.levelname}</span>
						</c:if>
						
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">时间间隔(小时)：</td>
					<td style="width: 25%;">
						<input type="text" name="time" id="time" class="inp" value="${timepara.timepara.time}">
					</td>
				</tr>
			</table>
		</div>
        <div id="ft" style="padding:5px;">
				<cite> 
					<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
					<a href="javascript:updateTimepara();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
			    </cite><span class="red">${timepara.returninfo}</span>
		</div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  function updateTimepara() {
      if ($("#time") != undefined && ($("#time").val() == "" || $("#time").val() == null )) {
			$.dialog.tips("请输入间隔时间", 1, "alert.gif", function() {
				$("#time").focus();
			});
			return false;
		}
      
      $('#updateform').attr('action', 'timepara/update');
      $("#updateform").submit();
  }
  
  function goback() {
     parent.closeDialog();
  }
  
  $(function () {
      var returninfo = '${timepara.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
  });
  
</script>
</body>
</html>
