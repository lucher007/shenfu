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
  <link type="text/css" rel="stylesheet" href="style/userdoordata.css">
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
    <input type="hidden" name="id" id="id" value="${userdoordata.userdoordata.id}"/>
    <div class="easyui-panel" title="订单门锁信息" style="width:100%;">
       <table style="width:100%">
	      <tr>
	            <td align="right">客户编号：</td>
	            <td>
					<input type="text" class="inp" name="usercode" id="usercode" value="${userdoordata.userdoordata.usercode}" readonly="readonly" style="background:#eeeeee;">
				</td>
	       </tr>
	       <tr>
	            <td align="right">订单编号：</td>
	            <td>
					<input type="text" class="inp" name="ordercode" id="ordercode" value="${userdoordata.userdoordata.ordercode }" readonly="readonly" style="background:#eeeeee;">
				</td>
	       </tr>
		   <tr>
				<td height="30" width="15%" align="right">锁侧板长度(毫米)：</td>
				<td width="25%">
					<input type="text" class="inp" name="locklength" id="locklength" value="${userdoordata.userdoordata.locklength }">
				</td>
			</tr>
			<tr>
				<td height="30" width="15%" align="right">锁侧板宽度(毫米)：</td>
				<td>
					<input type="text" class="inp" name="lockwidth" id="lockwidth" value="${userdoordata.userdoordata.lockwidth }">
				</td>
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
	        <span class="red">${userdoordata.returninfo }</span>
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
	  
	  if ($("#userdoordataname") != undefined && ($("#userdoordataname").val() == "" || $("#userdoordataname").val() == null )) {
			$.dialog.tips("请输入客户姓名", 1, "alert.gif", function() {
			$("#userdoordataname").focus();
		});
		return false;
	  }
	
	  if ($("#phone") != undefined && ($("#phone").val() == "" || $("#phone").val() == null )) {
			$.dialog.tips("请输入电话号码", 1, "alert.gif", function() {
			$("#phone").focus();
		});
		return false;
	  }
      $('#updateform').attr('action', 'userdoordata/update');
      $("#updateform").submit();
  }
  
  function goBack() {
	 parent.closeTab('门锁图片');
    // parent.closeDialog();
  }
  
  $(function () {
      var returninfo = '${userdoordata.returninfo}';
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
