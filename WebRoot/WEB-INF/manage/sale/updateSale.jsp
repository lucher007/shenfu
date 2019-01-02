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
    <input type="hidden" name="id" id="id" value="${sale.sale.id}"/>
    <input type="hidden" name="operatorid" id="operatorid" value="${sale.sale.operatorid}"/>
    <div class="form-box">
         <div class="easyui-panel" title="订户信息" style="width:100%;">
			<table style="width:100%;border:0; cellpadding:0; cellspacing:0">
				<tr>
					<td align="right" width="10%">客户姓名：</td>
					<td width="25%">
						<input type="text" class="inp w200" name="username" id="username" readonly="readonly" style="background:#eeeeee;" value="${sale.sale.username}">
						<input type="hidden" class="inp w200" name="userid" id="userid" value="${sale.sale.userid}">
						<input  type="button" class="btn-add"  id="btnfinish" value="请选择" onclick="chooseSale();">
					</td>
				</tr>
				<tr>
					<td align="right">联系电话：</td>
					<td><input type="text" class="inp" name="phone" id="phone" readonly="readonly" style="background:#eeeeee;" value="${sale.sale.phone}"> <span class="red">*</span></td>
				</tr>
				<tr>
					<td align="right">客户性别：</td>
					<td>
						<select id="usersex" name="usersex" class="select">
							<option value="0" <c:if test="${sale.sale.usersex == '0'}">selected</c:if>>女</option>
							<option value="1" <c:if test="${sale.sale.usersex == '1'}">selected</c:if>>男</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">客户来源：</td>
					<td>
						<select id="source" name="source" class="select">
							<option value="0" <c:if test="${sale.sale.source == '0'}">selected</c:if>>销售</option>
							<option value="1" <c:if test="${sale.sale.source == '1'}">selected</c:if>>400电话</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">客户地址：</td>
					<td><input type="text" class="inp" style="width: 400px;" name="address" id="address" value="${sale.sale.address}"> <span class="red">*</span></td>
				</tr>
			</table>
		</div>
		
		<div class="easyui-panel" title="销售单信息" style="width:100%;">
			<table style="width:100%;border:0; cellpadding:0; cellspacing:0">
				<tr>
					<td align="right" width="10%">销售单号：</td>
					<td style="width: 25%; font-weight: bold;">
						${sale.sale.saleno}
						<input type="hidden" name="saleno" id="saleno" value="${sale.sale.saleno}">
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">销售人员姓名：</td>
					<td width="25%">
						<input type="text" class="inp w200" name="salername" id="salername" readonly="readonly" style="background:#eeeeee;" value="${sale.sale.saler.employeename}">
						<input type="hidden" class="inp w200" name="salerid" id="salerid" value="${sale.sale.saler.id}">
						<input  type="button" class="btn-add"  id="btnfinish" value="请选择" onclick="chooseEmployee();">
					</td>
				</tr>
				<tr>
					<td align="right">联系电话：</td>
					<td><input type="text" class="inp" name="salerphone" id="salerphone" readonly="readonly" style="background:#eeeeee;" value="${sale.sale.saler.phone}"> <span class="red">*</span></td>
				</tr>
				<tr>
					<td align="right">状态：</td>
					<td>
						<select id="status" name="status" class="select" onchange="changeStatus();">
							<option value="0" <c:if test="${sale.sale.status == '0'}">selected</c:if>>洽谈中</option>
							<option value="1" <c:if test="${sale.sale.status == '1'}">selected</c:if>>洽谈完成</option>
							<option value="3" <c:if test="${sale.sale.status == '3'}">selected</c:if>>洽谈失败</option>
						</select>
					</td>
				</tr>
				<tr id="contentTr" style="display:none">
					<td align="right">失败原因：</td>
		            <td > 
						<textarea id="content" name="content" style="width:500px; height:30px;"
	                       onKeyDown="checkLength('content',100)" onKeyUp="checkLength('content',100)">${sale.sale.content}</textarea>
	            		<span class="red">还可以输入<span id="validNumcontent">100</span>字</span>
	               </td>
				</tr>
			</table>
		</div>
     
	    <div class="fb-bom">
	        <cite>
	            <input type="button" class="btn-back" value="返回" onclick="goBack()" >
	            <input type="button" class="btn-save" value="保存" onclick="updateSale();" id="btnfinish">
	        </cite>
	        <span class="red">${user.returninfo }</span>
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

  function updateSale() {
      if ($("#userid") != undefined && ($("#userid").val() == "" || $("#userid").val() == null )) {
			$.dialog.tips("请输入客户姓名", 1, "alert.gif", function() {
				$("#username").focus();
			});
			return false;
		}
		if ($("#salerid") != undefined && ($("#salerid").val() == "" || $("#salerid").val() == null )) {
			$.dialog.tips("请输入销售人员", 1, "alert.gif", function() {
				$("#salername").focus();
			});
			return false;
		}
      
      $('#updateform').attr('action', 'sale/update');
      $("#updateform").submit();
  }
  
  function goBack() {
     parent.closeDialog();
  }
  
  $(function () {
	  changeStatus();
      var returninfo = '${sale.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
  });
  
  var userDialog;
	function chooseSale() {
		userDialog = $.dialog({
			title : '客户查询',
			lock : true,
			width : 900,
			height : 500,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:user/findSaleListDialog?rid='+Math.random()
		});
	}

	function closeSaleDialog(jsonStr) {
		userDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
		$("#userid").val(jsonObject.id);
		$("#username").val(jsonObject.username);
		$("#phone").val(jsonObject.phone);
		$("#usersex").val(jsonObject.usersex);
		$("#source").val(jsonObject.source);
		$("#address").val(jsonObject.address);
	}
    
    var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '员工查询',
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
		$("#salerid").val(jsonObject.id);
		$("#salername").val(jsonObject.employeename);
		$("#salerphone").val(jsonObject.phone);
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
	
	//判断销售单状态
	function changeStatus(){
		if($("#status").val()=="3"){
		    $("#contentTr").show();
		}else{
		    $("#contentTr").hide();
		}
	}
</script>
</body>
</html>
