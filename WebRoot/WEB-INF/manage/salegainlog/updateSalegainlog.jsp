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
    <input type="hidden" name="id" id="id" value="${salegainlog.salegainlog.id}"/>
    <div class="easyui-panel" title="提现记录信息" style="width:100%;" data-options="footer:'#ft'">
       <table style="width:100%">
          <tr>
	            <td align="right">提现编号：</td>
	            <td>
					<input type="text" class="inp" name="gainlogcode" id="gainlogcode" value="${salegainlog.salegainlog.gainlogcode }" readonly="readonly" style="background:#eeeeee;">
				</td>
	       </tr>
	      <tr>
	            <td align="right">员工编号：</td>
	            <td>
					<input type="text" class="inp" name="gainercode" id="gainercode" value="${salegainlog.salegainlog.gainercode }" readonly="readonly" style="background:#eeeeee;">
				</td>
	       </tr>
	       <tr>
	            <td align="right">员工姓名：</td>
	            <td>
					<input type="text" class="inp" name="gainername" id="gainername" value="${salegainlog.salegainlog.gainer.employeename }" readonly="readonly" style="background:#eeeeee;">
				</td>
	       </tr>
	       <tr>
				<td align="right">联系电话：</td>
	            <td>
					<input type="text" class="inp" name="gainerphone" id="gainerphone" value="${salegainlog.salegainlog.gainer.phone }" readonly="readonly" style="background:#eeeeee;">
				</td>
			</tr>
			<tr>
				<td align="right">提现状态：</td>
				<td>
				    <select id="status" name="status" class="select">
						<option value="0" <c:if test="${salegainlog.salegainlog.status == '0'}">selected</c:if>>未到账</option>
						<option value="1" <c:if test="${salegainlog.salegainlog.status == '1'}">selected</c:if>>已到账</option>
					</select>
				</td>
			</tr>
			<tr style="height: 10px">
				<td align="right" width="15%">提现信息：</td>
				<td width="85%">
					 <textarea id="gaincontent" name="gaincontent" style="width:450px; height:60px;"
                      			onKeyDown="checkLength('gaincontent',300)" onKeyUp="checkLength('gaincontent',300)">${salegainlog.salegainlog.gaincontent}</textarea>
           		  		<span class="red">还可以输入<span id="validNumgaincontent">300</span>字</span>
				<td>
			</tr>
			<tr style="height: 10px">
				<td>
				<td>
			</tr>
        </table>
      </div>
      
     <div id="ft" style="padding:5px;">
	        <cite>
	        	<a href="javascript:goBack();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
	      		<a href="javascript:updateSalegainlog();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
	        </cite> <span class="red">${salegainlog.returninfo }</span>
     </div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

  function updateSalegainlog() {
      $('#updateform').attr('action', 'salegainlog/update');
      $("#updateform").submit();
  }
  
  function goBack() {
     parent.closeDialog();
  }
  
  $(function () {
      var returninfo = '${salegainlog.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
  });
  
</script>
</body>
</html>
