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
<link type="text/css" rel="stylesheet" href="style/easyui/easyui.css">
<style type="text/css">
.easyui-panel table tr {
	height: 20px;
}
</style>
</head>
<body>
<div id="body">
	<form method="post" id="addForm" name="addForm">
		<input type="hidden" name="id" id="id" value="${userorder.userorder.id}"/>
    	<input type="hidden" name="operatorid" id="operatorid" value="${userorder.userorder.operatorid}"/>
    	<div class="form-box">
			 <div class="easyui-panel" title="订单信息" style="width:100%;">
				<table style="width:100%">
					<tr>
						<td align="right" width="10%">订单号：</td>
						<td style="width: 25%; font-weight: bold;">
							${userorder.userorder.orderno}
							<input type="hidden" name="orderno" id="orderno" value="${userorder.userorder.orderno}">
						</td>
					</tr>
					<tr>
						<td align="right">状态：</td>
						<td style="width: 25%; font-weight: bold;">
						   <c:if test="${userorder.userorder.status == '0'}">未处理</c:if>
						   <c:if test="${userorder.userorder.status == '1'}">已派工</c:if>
						   <c:if test="${userorder.userorder.status == '2'}">已结单</c:if>
						   <c:if test="${userorder.userorder.status == '3'}">已作废</c:if>
						   <input type="hidden" name="status" id="status" value="${userorder.userorder.status}">
						</td>
					</tr>
				</table>
			</div>
			
			
			<div class="easyui-panel" title="任务单信息" style="width:100%;">
				<table style="width:100%">
					<tr>
						<td align="right">任务单号：</td>
						<td colspan="3">
							<input type="text" class="inp w200" name="taskno" id="taskno" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.taskno }">
							<input type="hidden" class="inp w200" name="taskid" id="taskid" value="${userorder.userorder.taskid}">
							<input  type="button" class="btn-add"  id="btnfinish" value="请选择" onclick="chooseTask();">
						</td>
					</tr>
					<tr>
						<td align="right">客户姓名：</td>
						<td>
							<input type="hidden" class="inp w200" name="userid" id="userid" value="${userorder.userorder.userid}">
							<input type="text" class="inp" name="username" id="username" value="${userorder.userorder.username}"> <span class="red">*</span>
						</td>
						<td align="right">联系电话：</td>
						<td><input type="text" class="inp" name="phone" id="phone" value="${userorder.userorder.phone}"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">客户性别：</td>
						<td>
							<select id="usersex" name="usersex" class="select">
								<option value="1" <c:if test="${userorder.userorder.usersex == '1'}">selected</c:if>>男</option>
								<option value="0" <c:if test="${userorder.userorder.usersex == '0'}">selected</c:if>>女</option>
							</select>
							<span class="red">*</span>
						</td>
						<td align="right">客户来源：</td>
							<td>
								<select id="source" name="source" class="select">
									<option value="0" <c:if test="${userorder.userorder.source == '0'}">selected</c:if>>销售</option>
									<option value="1" <c:if test="${userorder.userorder.source == '1'}">selected</c:if>>400电话</option>
								</select>
								<span class="red">*</span>
							</td>
					</tr>
					<tr>
						<td align="right">勘察人员：</td>
						<td>
							<input type="hidden" class="inp w200" name="researcherid" id="researcherid" value="${userorder.userorder.researcher.id}">
							<input type="text" class="inp" name="researchername" id="researchername" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.researcher.employeename}"> <span class="red">*</span></td>
						<td align="right">销售人员电话：</td>
						<td><input type="text" class="inp" name="researcherphone" id="researcherphone" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.researcher.phone}"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">客户地址：</td>
						<td colspan="3"><input type="text" class="inp" style="width: 400px;" name="address" id="address" value="${userorder.userorder.address}"> <span class="red">*</span></td>
					</tr>
				</table>
			</div>
			
			<div class="easyui-panel" title="产品信息" style="width:100%;">
				<table style="width:100%">
			  		<tr>
						<td align="right">产品型号：</td>
						<td >
						    <!--
							<select id="model"  class="select" >
								<c:forEach items="${userorder.productmodelList}" var="productmodel" varStatus="s">
									<option value="${productmodel.model}">${productmodel.model}</option>
								</c:forEach><span class="red">*</span>
							</select>
							  -->
							<input id="model"  class="easyui-combogrid"> 
						</td>
						<td align="right">产品名称：</td>
						<td >
							<input type="text" class="inp" id="productname"  value="${productmodel.productname }" onkeypress="onkeypressCheck(this);" onkeyup="onkeyupCheck(this);" onblur="onkeyblurCheck(this);" maxlength="10">
						</td>
						<td align="right">产品单价：</td>
						<td >
							<input type="text" class="inp" id="price"  value="${productmodel.price }" onkeypress="onkeypressCheck(this);" onkeyup="onkeyupCheck(this);" onblur="onkeyblurCheck(this);" maxlength="10"> 
						</td>  
				   </tr>
				   <tr>
				   		<td align="right">产品颜色：</td>
						<td >
							<select id="color" class="select">
								<c:forEach items="${userorder.productcolorList}" var="productcolor" varStatus="s">
									<option value="${productcolor.color}">${productcolor.color}</option>
								</c:forEach>
							</select>
						</td> 
						<td align="right">销售价格：</td>
						<td >
							<input type="text" class="inp" id="saleprice"  value="${productmodel.price }" onkeypress="onkeypressCheck(this);" onkeyup="onkeyupCheck(this);" onblur="onkeyblurCheck(this);" maxlength="10">
						</td> 
						<td align="right">销售数量：</td>
						<td >
							<input type="text" class="inp" id="amount" 
			            	maxlength="1" 
			            	onkeyup="checkNum(this)" onkeypress="checkNum(this)" onblur="checkNum(this)"
			            	onafterpaste="this.value=this.value.replace(/\D/g,'')" >
						</td> 
					</tr>
					<tr>
		  				<th align="center" colspan="4">
		  					<a id="addProductinfo" href="javascript:void(0)" class="blue" onClick="addProductinfo()" style="font-weight: bold;">+添加产品信息</a>
		  				</th>
		  			</tr>
			  		<tr>
			  			<th align="center">产品型号</th>
		  				<th align="center">产品名称</th>
		  				<th align="center">计量单位</th>
		  				<th align="center">产品单价</th>
		  				<th align="center">产品颜色</th>
		  				<th align="center">销售价格</th>
		  			</tr>
			  		<c:forEach items="${userorder.userorder.userproductList }" var="dataList">
				  		<tr class="count">
				  			<td align="center"><input type="text" class="inp readonly" name="model" readonly="readonly" style="background:#eeeeee;" value="${dataList.model }"/></td>
				  			<td align="center"><input type="text" class="inp readonly" name="productname" readonly="readonly" style="background:#eeeeee;" value="${dataList.productname }" ></td>
				  			<td align="center"><input type="text" class="inp readonly" name="productunit" readonly="readonly" style="width:80px;background:#eeeeee;" value="${dataList.productunit }" ></td>
				  			<td align="center"><input type="text" class="inp readonly" name="price" readonly="readonly" style="width:80px;background:#eeeeee;" value="${dataList.price }" ></td>
				  			<td align="center"><input type="text" class="inp readonly" name="color" readonly="readonly" style="width:80px;background:#eeeeee;"value="${dataList.color }"/></td>
				  			<td align="center"><input type="text" class="inp readonly" name="saleprice" readonly="readonly" style="width:80px;background:#eeeeee;" value="${dataList.saleprice }"/></td>
				  			<td align="center"><a class="red" href="javascript:void(0);" onclick="deleteTr(this)">删除</a></td>
				  		</tr>
			  		</c:forEach>
			  	</table>
			</div>
			
			<div class="easyui-panel" title="支付信息" style="width:100%;">
				<table style="width:100%">
					<tr>
						<td align="right">总金额：</td>
						<td><input type="text" class="inp" name="totalmoney" id="totalmoney" value="${userorder.userorder.totalmoney}"><span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">首付金额：</td>
						<td><input type="text" class="inp" name="firstpayment" id="firstpayment" value="${userorder.userorder.firstpayment}"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">尾款金额：</td>
						<td><input type="text" class="inp" name="finalpayment" id="finalpayment" value="${userorder.userorder.finalpayment}"> <span class="red">*</span></td>
					</tr>
					<tr>
					  <td></td>
					</tr>
				</table>
			</div>
			
			<div class="fb-bom">
				<cite> <input type="button" class="btn-back" value="返回" onclick="goback();" />
				 <input type="button" class="btn-save" value="保存" onclick="updateUserorder();"
				/> </cite><span class="red">${userorder.returninfo}</span>
			</div>
		</div>
	</form>		
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  $(function () {
	     var returninfo = '${userorder.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
		parent.closeDialog();
  }
	
  function checkVal() {
		if ($("#taskid") != undefined && ($("#taskid").val() == "" || $("#taskid").val() == null )) {
			$.dialog.tips("请选择任务单号", 1, "alert.gif", function() {
				$("#taskno").focus();
			});
			return false;
		}
		
		if ($("#totalmoney") != undefined && ($("#totalmoney").val() == "" || $("#totalmoney").val() == null )) {
			$.dialog.tips("请输入总金额", 1, "alert.gif", function() {
				$("#totalmoney").focus();
			});
			return false;
		}
		
		if ($("#firstpayment") != undefined && ($("#firstpayment").val() == "" || $("#firstpayment").val() == null )) {
			$.dialog.tips("请输入首付金额", 1, "alert.gif", function() {
				$("#firstpayment").focus();
			});
			return false;
		}
		
		if ($("#finalpayment") != undefined && ($("#finalpayment").val() == "" || $("#finalpayment").val() == null )) {
			$.dialog.tips("请输入尾款金额", 1, "alert.gif", function() {
				$("#finalpayment").focus();
			});
			return false;
		}
		
		return true;
	}
	
  function updateUserorder() {
		if (!checkVal()) {
			return;
		}
		$("#addForm").attr("action", "userorder/update");
	    $("#addForm").submit();
	}  
  
   var taskDialog;
	function chooseTask() {
		taskDialog = $.dialog({
			title : '任务单查询',
			lock : true,
			width : 900,
			height : 450,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:task/findTaskListDialog?rid='+Math.random()
		});
	}

	function closeTaskDialog(jsonStr) {
		taskDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
		$("#taskid").val(jsonObject.id);
		$("#taskno").val(jsonObject.taskno);
		$("#userid").val(jsonObject.userid);
		$("#username").val(jsonObject.username);
		$("#phone").val(jsonObject.phone);
		$("#usersex").val(jsonObject.usersex);
		$("#source").val(jsonObject.source);
		$("#address").val(jsonObject.address);
		$("#researcherid").val(jsonObject.researcherid);
		$("#researchername").val(jsonObject.researchername);
		$("#researcherphone").val(jsonObject.researcherphone);
	}
    
     function addProductinfo() {
          var modelObject = $('#model');
          var colorObject = $('#color');
          var amountObject = $('#amount');
          var salepriceObject = $('#saleprice');
          
		  var model = $('#model').combogrid('getValue');
		  var color = colorObject.val();
		  var amount = amountObject.val();
		  var saleprice = salepriceObject.val();
		  
		  if (model == undefined || model == '') {
		    $.dialog.tips("请输入产品型号", 1, "alert.gif");
		    modelObject.focus();
		    return false;
		  }
		  if (color == undefined || color == '') {
		    $.dialog.tips("请选择产品颜色", 1, "alert.gif");
		    return false;
		  }
		  if (amount == undefined || amount == '') {
		    $.dialog.tips("请选择产品个数", 1, "alert.gif");
		    return false;
		  }
		  if (saleprice == undefined || saleprice == '') {
		    $.dialog.tips("请输入销售价格", 1, "alert.gif");
		    return false;
		  }
		  
		  
		 var grid=$("#model").combogrid("grid");//获取表格对象 
  		 var row = grid.datagrid('getSelected');//获取行数据 
		 
		 var productname = row.productname;
		 var productunit = row.productunit;
		 var price = row.price;
		 
		  
		  var table = modelObject.parents('table');
		  
		  for(var i = 0;i < parseInt(amount);i++){
			
			  table.append('<tr class="count">'
			  	  + '     <td align="center" ><input type="text" class="inp" name="model" readonly="readonly" style="background:#eeeeee;" value="' + model + '"/></td>'
			  	  + '     <td align="center" ><input type="text" class="inp" name="productname" readonly="readonly" style="background:#eeeeee;" value="' + productname + '"/></td>'
			  	  + '     <td align="center" ><input type="text" class="inp" name="productunit" readonly="readonly" style="width:80px;background:#eeeeee;" value="' + productunit + '"/></td>'
			  	  + '     <td align="center" ><input type="text" class="inp" name="price" readonly="readonly" style="width:80px;background:#eeeeee;" value="' + price + '"/></td>'
			      + '     <td align="center" ><input type="text" class="inp" name="color" readonly="readonly" style="width:80px;background:#eeeeee;" value="' + color + '"/></td>'
			      + '     <td align="center"><input type="text" class="inp" name="saleprice" readonly="readonly" style="width:80px;background:#eeeeee;" value="' + saleprice + '"/></td>'
			      + '     <td align="center"><a class="red" href="javascript:void(0);" onclick="deleteTr(this)">删除</a></td>'
			      + '    </tr>'
		      );
		   }
		  //amountObject.val('');
		  //colorObject.val('');
		  //modelObject.val('');
	}
    
    function deleteTr(delObj) {
		  $(delObj).parents('tr').remove();
		  //parent.descriptorDialog.size(600, $('body').height() + 20);
	}
    
    
    $('#model').combogrid({
            panelWidth: 500,
			idField: 'model',
			textField: 'model',
			url: 'productmodel/getProductmodelCombogridJson',
			method: 'get',
			fitColumns: true,
			columns: [[
				{ field: 'model', title: '产品型号',width:100,align:"center"},
                { field: 'productname', title: '产品名称',width:100,align:"center"},
                { field: 'productunit', title: '计量单位',width:100,align:"center"},
                { field: 'price', title: '产品价格',width:100,align:"center"},
			]],
			onSelect: function (rowIndex, rowData){  
				$("#productname").val(rowData.productname);  
				$("#price").val(rowData.price);  
		  	}
       });
	  
</script>
</body>
</html>
