<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link type="text/css" rel="stylesheet" href="<%=basePath%>main/plugin/uploadify/uploadify.css" />
<link rel="stylesheet" type="text/css" href="style/easyui/easyui.css">
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm">
			<div class="easyui-panel" title="产品信息" style="width:100%;">
				<table style="width:100%">
					<tr>
						<td align="right">产品标识：</td>
						<td >
							<input type="text" class="inp w200" name="productidentfy" id="productidentfy" readonly="readonly" style="background:#eeeeee;">
							<input  type="button" class="btn-add"  id="btnfinish" value="请选择" onclick="chooseUserproduct();">
						</td>
						<td align="right">订单号：</td>
						<td >
							<input type="text" class="inp w200" name="orderno" id="orderno" readonly="readonly" style="background:#eeeeee;">
							<input type="hidden" name="orderid" id="orderid" >
						</td>
					</tr>
					<tr>
						<td align="right">客户姓名：</td>
						<td>
							<input type="hidden"name="userid" id="userid" >
							<input type="text" class="inp" name="username" id="username" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
						<td align="right">联系电话：</td>
						<td><input type="text" class="inp" name="phone" id="phone" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">客户性别：</td>
						<td>
							<select id="usersex" name="usersex" class="select">
								<option value="1">男</option>
								<option value="0">女</option>
							</select>
							<span class="red">*</span>
						</td>
						<td align="right">客户来源：</td>
							<td>
								<select id="source" name="source" class="select">
									<option value="0">销售</option>
									<option value="1">400电话</option>
								</select>
								<span class="red">*</span>
							</td>
					</tr>
					<tr>
						<td align="right">客户地址：</td>
						<td colspan="3"><input type="text" class="inp" name="address" id="address" readonly="readonly" style="background:#eeeeee; width: 400px;">  <span class="red">*</span></td>
					</tr>
				</table>
			</div>
	
			<div class="easyui-panel" title="维修信息" style="width:100%;">
				<table style="width:100%">
					<tr>
						<td align="right">维修内容：</td>
			            <td> 
							<textarea id="content" name="content" style="width:600px; height:30px;"
		                       onKeyDown="checkLength('content',100)" onKeyUp="checkLength('content',100)">${userrepair.content}</textarea>
		            		<span class="red">还可以输入<span id="validNumcontent">100</span>字</span>
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
					<cite> <input type="button" class="btn-back" value="返回" onclick="goback();" />
					 <input type="button" class="btn-save" value="保存" onclick="saveUserrepair();"
					/> </cite><span class="red">${userrepair.returninfo}</span>
				</div>
			</div>
		</form>		
	</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>main/plugin/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript">

  $(function () {
  		 //initUserproductDatagrid();
	     var returninfo = '${userrepair.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
		parent.closeDialog();
  }
	
  function checkVal() {
		if ($("#productidentfy") != undefined && ($("#productidentfy").val() == "" || $("#productidentfy").val() == null )) {
			$.dialog.tips("请选择产品", 1, "alert.gif", function() {
				$("#productidentfy").focus();
			});
			return false;
		}
		return true;
	}
	
  function saveUserrepair() {
  
		if (!checkVal()) {
			return;
		}
		
		$("#addForm").attr("action", "userrepair/save");
	    $("#addForm").submit();
	}  
  
   var userproductDialog;
	function chooseUserproduct() {
		userproductDialog = $.dialog({
			title : '产品查询',
			lock : true,
			width : 900,
			height : 450,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:userproduct/findUserproductListDialog?rid='+Math.random()
		});
	}

	function closeUserproductDialog(jsonStr) {
		userproductDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
	    
	    $("#productidentfy").val(jsonObject.productidentfy);
		$("#orderid").val(jsonObject.orderid);
		$("#orderno").val(jsonObject.orderno);
		$("#userid").val(jsonObject.userid);
		$("#username").val(jsonObject.username);
		$("#phone").val(jsonObject.phone);
		$("#usersex").val(jsonObject.usersex);
		$("#source").val(jsonObject.source);
		$("#address").val(jsonObject.address);
		//加载产品产品信息
		$('#userproductDG').datagrid('reload',paramsJson());
	}
	 
	function checkLength(object, maxlength) {
	    var obj = $('#' + object),
	        value = $.trim(obj.val());
	    if (value.length > maxlength) {
	      obj.val(value.substr(0, maxlength));
	    } else {
	      $('#validNum' + object).html(maxlength - value.length);
	    }
	 }
	 
</script>
</body>
</html>
