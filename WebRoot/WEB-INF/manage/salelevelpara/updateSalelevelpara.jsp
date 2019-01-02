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
  <style>
     .form-box table tr {
      height: 30px;
    }
  </style>
</head>

<body>
<div id="body">
  <form action="" method="post" id="updateform" name="updateform">
    <input type="hidden" name="id" id="id" value="${salelevelpara.salelevelpara.id}"/>
    <div class="form-box">
         <div class="easyui-panel" title="销售等级信息" style="width:100%;">
			<table style="width:100%;border:0; cellpadding:0; cellspacing:0">
				<tr>
					<td align="right" width="10%">销售等级：</td>
					<td style="width: 25%; font-weight: bold;">
						<input type="text" name="level" id="level" class="inp" value="${salelevelpara.salelevelpara.level}">
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">最小对应积分（包含等于）：</td>
					<td style="width: 25%;">
						<input type="text" name="minscore" id="minscore" class="inp" maxlength="9" value="${salelevelpara.salelevelpara.minscore}" onkeyup="checkNum(this)" onkeypress="checkNum(this)" onblur="checkNum(this)" onafterpaste="this.value=this.value.replace(/\D/g,'')">
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">最大对应积分（不包含等于）：</td>
					<td style="width: 25%;">
						<input type="text" name="maxscore" id="maxscore" class="inp" maxlength="9" value="${salelevelpara.salelevelpara.maxscore}" onkeyup="checkNum(this)" onkeypress="checkNum(this)" onblur="checkNum(this)" onafterpaste="this.value=this.value.replace(/\D/g,'')">
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">对应等级每天减少量：</td>
					<td style="width: 25%;">
						<input type="text" name="reduce" id="reduce" class="inp" maxlength="9" value="${salelevelpara.salelevelpara.reduce}" onkeyup="checkNum(this)" onkeypress="checkNum(this)" onblur="checkNum(this)" onafterpaste="this.value=this.value.replace(/\D/g,'')">
					</td>
				</tr>
				 <tr>
		            <td align="right">状态：</td>
		            <td>
		             	<select id="state" name="state" class="select">
							<option value="0" <c:if test="${salelevelpara.salelevelpara.state == '0'}">selected</c:if>>无效</option>
							<option value="1" <c:if test="${salelevelpara.salelevelpara.state == '1'}">selected</c:if>>有效</option>
						</select>
		            </td>
		         </tr>
			</table>
		</div>
         
	    <div class="fb-bom">
	        <cite>
	            <input type="button" class="btn-back" value="返回" onclick="goBack()" >
	            <input type="button" class="btn-save" value="保存" onclick="updateSalelevel();" id="btnfinish">
	        </cite>
	        <span class="red">${salelevelpara.returninfo }</span>
	     </div>
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

  function updateSalelevel() {
      if ($("#level") != undefined && ($("#level").val() == "" || $("#level").val() == null )) {
			$.dialog.tips("请输入销售等级", 1, "alert.gif", function() {
				$("#level").focus();
			});
			return false;
	  }
      
      if ($("#minscore") != undefined && ($("#minscore").val() == "" || $("#minscore").val() == null )) {
			$.dialog.tips("请输入最小对应积分", 1, "alert.gif", function() {
				$("#minscore").focus();
			});
			return false;
	  }
      
      if ($("#maxscore") != undefined && ($("#maxscore").val() == "" || $("#maxscore").val() == null )) {
			$.dialog.tips("请输入最大对应积分", 1, "alert.gif", function() {
				$("#maxscore").focus();
			});
			return false;
	  }
      
      if ($("#reduce") != undefined && ($("#reduce").val() == "" || $("#reduce").val() == null )) {
			$.dialog.tips("请输入对应等级每天减少量", 1, "alert.gif", function() {
				$("#reduce").focus();
			});
			return false;
	  }
      
      $('#updateform').attr('action', 'salelevelpara/update');
      $("#updateform").submit();
  }
  
  function goBack() {
     parent.closeDialog();
  }
  
  $(function () {
      var returninfo = '${salelevelpara.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
  });
  
</script>
</body>
</html>
