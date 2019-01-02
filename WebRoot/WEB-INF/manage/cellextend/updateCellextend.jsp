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
    <input type="hidden" name="id" id="id" value="${cellextend.cellextend.id}"/>
    <input type="hidden" name="extendcode" id="extendcode" value="${cellextend.cellextend.extendcode}"/>
    <div class="easyui-panel" title="小区扫楼价格" style="width:100%;" data-options="footer:'#ft'">
        <table style="width:100%">
          <tr>
            <td align="right">小区名称：</td>
            <td>
				<input type="text" class="inp" name="cellname" id="cellname" value="${cellextend.cellextend.cellname }" readonly="readonly" style="background:#eeeeee;width:250px;">
				<span class="red">*</span>
			</td>
		  </tr>
		  <tr>
            <td align="right">小区地址：</td>
            <td>
				<input type="text" class="inp" name="cellname" id="cellname" value="${cellextend.cellextend.address }" readonly="readonly" style="background:#eeeeee;width:450px;">
				<span class="red">*</span>
			</td>
		  </tr>
		  <tr >
            <td align="right">总户数：</td>
            <td>
				<input type="text" class="inp" name="totalhouse" id="totalhouse" value="${cellextend.cellextend.totalhouse }" readonly="readonly" style="background:#eeeeee;width:250px;">
			</td>
		  </tr>
		  <tr>
			<td align="right">扫楼每户单价(分)：</td>
          	<td>
				<input type="text" class="inp" name="price" id="price" value="${cellextend.cellextend.price }" readonly="readonly" style="background:#eeeeee;width:250px;">
			</td>
		  </tr>
		  <tr>
			<td align="right">扫楼总价：</td>
			<td >
				<input type="text" class="inp easyui-numberbox" style="width: 250px;" name="totalmoney" id="totalmoney" value="${cellextend.cellextend.totalmoney }"> 
			</td>
		  </tr>
      </table>
    </div>
    <div id="ft" style="padding:5px;">
		<cite> 
			<a href="javascript:goBack();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
			<a href="javascript:updateCellextend();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
	    </cite><span class="red">${cellextend.returninfo}</span>
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
  
  
  function updateCellextend() {
	  if ($("#totalmoney") != undefined && ($("#totalmoney").val() == "" || $("#totalmoney").val() == null )) {
		  $("#totalmoney").focus();
		  $.dialog.tips("请输入小区扫楼总价", 2, "alert.gif");
		  return false;
	  }
      $('#updateform').attr('action', 'cellextend/update');
      $("#updateform").submit();
  }
  
  function goBack() {
	 parent.closeTabSelected();
     //parent.closeDialog();
  }
  
  $(function () {
      var returninfo = '${cellextend.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
  });
  
</script>
</body>
</html>
