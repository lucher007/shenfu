<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<link rel="stylesheet" type="text/css" href="main/plugin/easyui/themes/icon.css">
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm">
		    <input type="hidden" name="id" id="id" value="${purchase.purchase.id}">
		    <input type="hidden" name="purchaseid" id="purchaseid" value="${purchase.purchase.id}">
			<div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
				<div class="easyui-panel" title="供应商信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">供应商简称：</td>
							<td colspan="5">
								<input type="text" class="inp" style="width: 300px;" name="suppliername" id="suppliername" value="${purchase.purchase.suppliername }"> <span class="red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">供应商信息：</td>
				            <td> 
								<textarea id="content" name="content" style="width:550px; height:50px;"
			                       onKeyDown="checkLength('content',100)" onKeyUp="checkLength('content',100)"> ${purchase.purchase.supplierinfo }</textarea>
			            		<span class="red">还可以输入<span id="validNum_content">100</span>字</span>
			               </td>
						</tr>
					</table>
				</div>
				
				<div id = "purchasedetailDG" style="width:100%; height:auto;">
					<input type="hidden"name="purchasedetailJson" id="purchasedetailJson" >
	    		</div>
	    		
				<div class="easyui-panel" title="快递单信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">总金额：</td>
							<td>
								<input type="text" class="inp" name="totalmoney" id="totalmoney" readonly="readonly" style="background:#eeeeee;" value="${purchase.purchase.totalmoney}">
							</td>
							<td align="right">预计发货时间：</td>
							<td>
								<input type="text" name="plandelivertime" id="plandelivertime" class="Wdate inp w150" value="${fn:substring(purchase.purchase.plandelivertime, 0, 10)}"> <span class="red">*</span>
						    </td>
							<td align="right">预计到货信息：</td>
							<td>
								<input type="text" name="planarrivaltime" id="planarrivaltime" class="Wdate inp w150" value="${fn:substring(purchase.purchase.planarrivaltime, 0, 10)}"> <span class="red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">票据类型：</td>
							<td >
								<select id="billtype" name="billtype" class="select">
									<option value="1" <c:if test="${purchase.purchase.billtype == '1'}">selected</c:if>>开票</option>
									<option value="0" <c:if test="${purchase.purchase.billtype == '0'}">selected</c:if>>不开票</option>
								</select>
							</td>
							<td align="right">票据状态：</td>
							<td >
								<select id="billstatus" name="billstatus" class="select">
								    <option value="0" <c:if test="${purchase.purchase.billstatus == '0'}">selected</c:if>>票据未到</option>
									<option value="1" <c:if test="${purchase.purchase.billstatus == '1'}">selected</c:if>>票据已到</option>
								</select>
							</td>
							<td align="right">票据号码：</td>
							<td >
								<input type="text" class="inp" name="invoicecode" id="invoicecode" value="${purchase.invoicecode }">
							</td>
						</tr>
						<tr>
						  <td></td>
						</tr>
					</table>
				</div>
			</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
				    </cite><span class="red">${purchase.returninfo}</span>
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
  		 initPurchasedetailDatagrid();
	     var returninfo = '${userorder.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
		parent.closeDialog();
  }
	
  
    function initPurchasedetailDatagrid(){
         $('#purchasedetailDG').datagrid({
              title: '采购物料信息',
              queryParams: paramsJson(),
              url:'purchasedetail/findListJson',
              pagination: false,
              singleSelect: true,
              scrollbar:true,
              //pageSize: 10,
              //pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              //onClickCell: onClickCell,
              columns: [[
                  { field: 'typename', title: '物品类型',width:100,align:"center"},
				  { field: 'materialcode', title: '物品编号',width:100,align:"center"},
                  { field: 'materialname', title: '物品名称',width:150,align:"center",},
                  { field: 'model', title: '物品型号',width:60,align:"center",},
                  { field: 'price', title: '物料单价',width:60,align:"center",},
                  { field: 'amount', title: '物料数量',width:60,align:"center",},
                  { field: 'money', title: '物料总价',width:60,align:"center",},
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var purchaseid = $("#purchaseid").val();
	 	var json = '';
	 	if(purchaseid != null && purchaseid != ''){
	 		json = {
	 				purchaseid:purchaseid,
		 	};
	 	}else{//默认不查询产品信息，故参数乱设置
	 		json = {
	 				purchaseid:'-100null',
		 	};
	 	}
	 	
	 	return json;
	 }
	 
	 function checkLength(object, maxlength) {
		    var obj = $('#' + object),
		        value = $.trim(obj.val());
		    if (value.length > maxlength) {
		      obj.val(value.substr(0, maxlength));
		    } else {
		      $('#validNum_' + object).html(maxlength - value.length);
		    }
		 }
</script>
</body>
</html>
