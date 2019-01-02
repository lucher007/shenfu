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
<link rel="stylesheet" type="text/css" href="main/plugin/easyui/themes/icon.css">
<style type="text/css">
.easyui-panel table tr {
	height: 30px;
}
</style>
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm">
			<div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
				<div class="easyui-panel" title="供应商信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">供应商简称：</td>
							<td colspan="5">
								<input type="text" class="inp" style="width: 300px;" name="suppliername" id="suppliername" value="${purchase.suppliername }"> <span class="red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">供应商信息：</td>
				            <td> 
								<textarea id="supplierinfo" name="supplierinfo" style="width:550px; height:50px;"
			                       onKeyDown="checkLength('supplierinfo',100)" onKeyUp="checkLength('supplierinfo',100)"></textarea>
			            		<span class="red">还可以输入<span id="validNum_supplierinfo">100</span>字</span>
			               </td>
						</tr>
					</table>
				</div>
				
				<div class="easyui-panel" title="物料信息" style="width:100%;">
					<table style="width:100%">
				  		<tr>
							<td align="right">物料名称：</td>
							<td >
								<input id="materialname"  class="easyui-combogrid"> <span class="red">*</span>
							</td>
							<td align="right">物料编号：</td>
							<td >
								<input type="text" class="inp" id="materialcode" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
							</td>
							<td align="right">计量单位：</td>
							<td >
								<input type="text" class="inp" id="materialunit" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
							</td>  
					   </tr>
					   
					   <tr>
							<td align="right">物料单价：</td>
							<td >
								<input type="text" class="inp" id="price" onkeypress="onkeypressCheck(this);" onkeyup="onkeyupCheck(this);" onblur="onkeyblurCheck(this);" maxlength="10"> <span class="red">*</span>
							</td> 
							<td align="right">物料数量：</td>
							<td >
								<input type="text" class="inp " id="amount" onkeypress="onkeypressCheck(this);" onkeyup="onkeyupCheck(this);" onblur="onkeyblurCheck(this);" maxlength="10"> <span class="red">*</span>
							</td> 
							<td align="right">物料金额：</td>
							<td >
								<input type="text" class="inp" id="money" onkeypress="onkeypressCheck(this);" onkeyup="onkeyupCheck(this);" onblur="onkeyblurCheck(this);" maxlength="10"> <span class="red">*</span>
							</td> 
						</tr>
						<tr>
			  				<th align="center" colspan="6">
			  					<a id="addMaterialinfo" href="javascript:void(0)" class="blue" onClick="addMaterialinfo('1')" style="font-weight: bold;">+添加物料信息</a>
			  				</th>
			  			</tr>
				  		<tr>
				  		    <th align="center">物品类型</th>
				  			<th align="center">物品编号</th>
				  			<th align="center">物品名称</th>
			  				<th align="center">规格</th>
			  				<th align="center">采购单价</th>
			  				<th align="center">采购数量</th>
			  				<th align="center">物品总价</th>
			  			</tr>
				  	</table>
				</div>
				
				<div class="easyui-panel" title="采购单信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">总金额：</td>
							<td><input type="text" class="inp" name="totalmoney" id="totalmoney" onkeypress="onkeypressCheck(this);" onkeyup="onkeyupCheck(this);" onblur="onkeyblurCheck(this);" maxlength="10"> <span class="red">*</span></td>
							<td align="right">预计发货日期：</td>
							<td><input type="text" name="plandelivertime" id="plandelivertime"
							onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150"> <span class="red">*</span></td>
							<td align="right">预计到货日期：</td>
							<td><input type="text" name="planarrivaltime" id="planarrivaltime"
							onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'plandelivertime\');}'});" class="Wdate inp w150"> <span class="red">*</span></td>
						</tr>
						<tr>
							<td align="right">票据类型：</td>
							<td >
								<select id="billtype" name="billtype" class="select">
									<option value="1" <c:if test="${purchase.billtype == '1'}">selected</c:if>>开票</option>
									<option value="0" <c:if test="${purchase.billtype == '0'}">selected</c:if>>不开票</option>
								</select>
							</td>
							<td align="right">票据状态：</td>
							<td >
								<select id="billstatus" name="billstatus" class="select">
								    <option value="0" <c:if test="${purchase.billstatus == '0'}">selected</c:if>>票据未到</option>
									<option value="1" <c:if test="${purchase.billstatus == '1'}">selected</c:if>>票据已到</option>
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
						<a href="javascript:savePurchase();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
				    </cite><span class="red">${purchase.returninfo}</span>
			</div>
		</form>		
	</div>

    <script type="text/javascript" src="js/common/jquery.js"></script>
    <script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript">

  
  $(function () {
         //initmaterialname();
	     var returninfo = '${purchase.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
		parent.closeDialog();
  }
	
  function checkVal() {
		if ($("#suppliername") != undefined && ($("#suppliername").val() == "" || $("#suppliername").val() == null )) {
			$.dialog.tips("请输入供应商名称", 1, "alert.gif", function() {
				$("#suppliername").focus();
			});
			return false;
		}
		
		if ($("input[name='materialname']").val() == undefined) {
			$.dialog.tips("请添加物料信息", 1, "alert.gif", function() {
				$('#materialname').next('span').find('input').focus();
			});
			return false;
		}
		
		if ($("#totalmoney") != undefined && ($("#totalmoney").val() == "" || $("#totalmoney").val() == null )) {
			$.dialog.tips("请输入总金额", 1, "alert.gif", function() {
				$("#totalmoney").focus();
			});
			return false;
		}
		
		if ($("#plandelivertime") != undefined && ($("#plandelivertime").val() == "" || $("#plandelivertime").val() == null )) {
			$.dialog.tips("请输入预计发货日期", 1, "alert.gif", function() {
				$("#plandelivertime").focus();
			});
			return false;
		}
		
		if ($("#planarrivaltime") != undefined && ($("#planarrivaltime").val() == "" || $("#planarrivaltime").val() == null )) {
			$.dialog.tips("请输入预计到货日期", 1, "alert.gif", function() {
				$("#planarrivaltime").focus();
			});
			return false;
		}
		
		return true;
	}
	
    function savePurchase() {
		if (!checkVal()) {
			return;
		}
		
		$.dialog({
		    title: '提交',
		    lock: true,
		    background: '#000', /* 背景色 */
		    opacity: 0.5,       /* 透明度 */
		    content: '是否确定执行？',
		    icon: 'alert.gif',
		    ok: function () {
		    	$("#addForm").attr("action", "purchase/save?rid="+Math.random());
				$("#addForm").submit();
		        /* 这里要注意多层锁屏一定要加parent参数 */
		        $.dialog({
		        	lock: true,
		            title: '提示',
		        	content: '执行中，请等待......', 
		        	max: false,
				    min: false,
				    cancel:false,
		        	icon: 'loading.gif',
		        });
		        return false;
		    },
		    okVal: '确定',
		    cancel: true,
		    cancelVal:'取消'
		});
	}  
  
    var supplierDialog;
	function chooseSupplier() {
		supplierDialog = $.dialog({
			title : '供应商查询',
			lock : true,
			width : 1000,
			height : 550,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:supplier/findSupplierListDialog?rid='+Math.random()
		});
	}

	function closeSupplierDialog(jsonStr) {
		supplierDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
		$("#supplierid").val(jsonObject.id);
		$("#suppliername").val(jsonObject.name);
		$("#supplierfullname").val(jsonObject.fullname);
		$("#contactname").val(jsonObject.contactname);
		$("#contactphone").val(jsonObject.contactphone);
		$("#address").val(jsonObject.address);
	}
    
    function addMaterialinfo() {
          var materialname = $('#materialname').combogrid('getValue');
          if (materialname == undefined || materialname == '') {
  		    $.dialog.tips("请选择物料名称", 1, "alert.gif");
  		    $('#materialname').next('span').find('input').focus();
  		    return false;
  		  }
          
		  var price = $('#price').val();
		  var amount = $('#amount').val();
		  var money = $('#money').val();
		  if (price == undefined || price == '') {
		    $.dialog.tips("请输入物料单价", 1, "alert.gif");
		    $("#price").focus();
		    return false;
		  }
		  if (amount == undefined || amount == '') {
		    $.dialog.tips("请输入物料数量", 1, "alert.gif");
		    $("#amount").focus();
		    return false;
		  }
		  if (money == undefined || money == '') {
		    $.dialog.tips("请输入物料金额", 1, "alert.gif");
		    $("#money").focus();
		    return false;
		  }
		  
		  //获取选中的数据
		  var grid=$("#materialname").combogrid("grid");//获取表格对象 
  		  var row = grid.datagrid('getSelected');//获取行数据 
  		  var materialname = row.materialname;
		  var materialcode = row.materialcode;
		  var model = '';
		  
		  
		  //获取列表的对象
		  var materialnameObject = $('#materialname');
		  var table = materialnameObject.parents('table');
		  
		  table.append('<tr class="count">'
			  + '     <td align="center" ><input type="hidden" name="type" value="1"/><input type="text" class="inp" name="typename" readonly="readonly" style="width:80px;background:#eeeeee;" value="物料"/></td>'
		  	  + '     <td align="center" ><input type="text" class="inp" name="materialcode" readonly="readonly" style="width:80px;background:#eeeeee;" value="' + materialcode + '"/></td>'
		  	  + '     <td align="center" ><input type="text" class="inp" name="materialname" readonly="readonly" style="width:150px;background:#eeeeee;" value="' + materialname + '"/></td>'
		  	  + '     <td align="center" ><input type="text" class="inp" name="model" readonly="readonly" style="width:150px;background:#eeeeee;" value="' + model + '"/></td>'
		  	  + '     <td align="center" ><input type="text" class="inp" name="price" readonly="readonly" style="width:80px;background:#eeeeee;" value="' + price + '"/></td>'
		  	  + '     <td align="center" ><input type="text" class="inp" name="amount" readonly="readonly" style="width:80px;background:#eeeeee;" value="' + amount + '"/></td>'
		      + '     <td align="center"><input type="text" class="inp" name="money" readonly="readonly" style="width:80px;background:#eeeeee;" value="' + money + '"/></td>'
		      + '     <td align="center"><a class="red" href="javascript:void(0);" onclick="deleteTr(this)">删除</a></td>'
		      + '    </tr>'
	      );
		   
		  //amountObject.val('');
		  //materialcodeObject.val('');
		  //materialnameObject.val('');
	}
    
    function deleteTr(delObj) {
		  $(delObj).parents('tr').remove();
		  //parent.descriptorDialog.size(600, $('body').height() + 20);
	}
    
    $('#materialname').combogrid({
            panelWidth: 500,
			idField: 'materialname',
			textField: 'materialname',
			url: 'material/getMaterialCombogridJson?rid='+Math.random(),
			method: 'get',
			fitColumns: true,
			columns: [[
				{ field: 'materialname', title: '物料名称',width:100,align:"center"},
                { field: 'materialcode', title: '物料编号',width:100,align:"center"},
                { field: 'materialunit', title: '计量单位',width:100,align:"center"},
			]],
			onSelect: function (rowIndex, rowData){  
							$("#materialcode").val(rowData.materialcode);  
							$("#materialunit").val(rowData.materialunit);  
					  }  
       });
	  
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
