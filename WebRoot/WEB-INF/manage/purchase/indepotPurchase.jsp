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
				<div id = "purchasedetailDG" style="width:100%; height:auto;">
					<input type="hidden"name="purchasedetailJson" id="purchasedetailJson" >
	    		</div>
			</div>
			<div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
				<div class="easyui-panel" title="出入库信息" style="width:100%;">
					<table style="width:100%">
						<tr>
						<td align="right" width="10%">入库负责人编号：</td>
						<td width="25%">
							<input type="text" class="inp w200" name="inoutercode" id="inoutercode" readonly="readonly" style="background:#eeeeee;" value="${materialdepot.inoutercode }">
							<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">入库负责人姓名：</td>
						<td width="25%">
							<input type="text" class="inp w200" name="inoutername" id="inoutername" readonly="readonly" style="background:#eeeeee;" value="${materialdepot.inoutername }"><span class="red">*</span>
						</td>
					</tr>
					</table>
				</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back">返回</a>
						<a href="javascript:saveIndepot();" class="easyui-linkbutton" iconCls="icon-save">采购单入库</a>
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
 
var employeeDialog;
function chooseEmployee() {
	employeeDialog = $.dialog({
		title : '装维员工查询',
		lock : true,
		width : 1100,
		height : 600,
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
	$("#inoutercode").val(jsonObject.employeecode);
	$("#inoutername").val(jsonObject.employeename);
}
 
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
              onClickCell: onClickCell,
              columns: [[
                  { field: 'typename', title: '物品类型',width:100,align:"center"},
				  { field: 'materialcode', title: '物品编号',width:100,align:"center"},
                  { field: 'materialname', title: '物品名称',width:150,align:"center",},
                  { field: 'model', title: '物品型号',width:60,align:"center",},
                  { field: 'amount', title: '物料数量',width:60,align:"center",},
                  { field: 'depotrackcode', title: '存放货柜位置',width:100,align:"center",
                  	  editor:{
	                       		type:'combobox',
								options:{
									valueField:'id',
									textField:'text',
									method:'get',
									url:'depotrack/getDepotrackinfoComboBoxJson',
								},
		                  },
                  },
              ]],
              onBeginEdit :function(index,row){
	         	   var ed = $('#purchasedetailDG').datagrid('getEditor', {index:index,field:'depotrackcode'});
	         	   //重新加载下拉框的数据
	         	   //$(ed.target).combobox('reload', {typecode:row.typecode});//如果datagrid数据源列名称不是material_id记得修改这里
	         	   //$(ed.target).combobox('select', {typecode:row.productcode});//默认选择
	         	   $(ed.target).combobox({
	         		   onLoadSuccess:function(){//默认选中
		       		    	$(ed.target).combobox('select',row.depotrackcode);//菜单项可以text或者id
		       		   },
	         		   onChange:function(newvalue,oldvalue){
	         	        	var productname = $(ed.target).combobox('getText');
	         	        	var productcode = $(ed.target).combobox('getValue');
	         	        	
	         	        	if(productcode == ''){//未选择
	         	        		productname = '';
	         	        	}
	         	        	//var ed_productname = $('#purchasedetailDG').datagrid('getEditor', {index:index,field:'productname'});
	         	        	//$(ed_productname.target).textbox("setValue", productname);
	         	        }
	         	    });
	            },  
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
	 
	 
	//单元格编辑，按回车键
	 function onClickCell(index, field){
		  
		 //不是点击价单元格，不进入修改模式
		 if(field != 'depotrackcode'){
			 return;
		 }
		
	     $(this).datagrid('beginEdit', index);
         var ed = $(this).datagrid('getEditor', {index:index,field:field});
         
         if(ed != null){
       	//获取编辑单元格对象
             var editorObject = $(ed.target);
            //光标指向该编辑框
            editorObject.focus();
  		
  		   //绑定回车事件
           	var currentEdatagrid = $(this);  
       	    $('.datagrid-editable .textbox,.datagrid-editable .datagrid-editable-input,.datagrid-editable .textbox-text').bind('keydown', function(e){  
       	    	 
       	    	  var code = e.keyCode || e.which;  
       	    	   
                     if(code == 13){
                  	    
                  	   //获取输入的产品标识
                         //var editorContent = editorObject.val();
                  	 
                        	//var allrows = $('#productDG').datagrid("getRows"); // 这段代码是// 对某个单元格赋值
  		           
  		            	// 刷新该行, 只有刷新了才有效果
  						//$(this).datagrid('refreshRow', index);
  					
  						//如果该行处于"行编辑"状态, 如果直接调用"refreshRow"方法. 会报data is undefined这个错; 
  						//需要先调用"endEdit", 再调用"refreshRow", 最后调用"selectRow"和"beginEdit"这两个方法便可了;
                        	//$('#userproductDG').datagrid('endEdit', index).datagrid('refreshRow', index).datagrid('selectRow', index).datagrid(
                          //	'beginEdit', index);
                             		
                            $(currentEdatagrid).datagrid('acceptChanges');  
                        	$(currentEdatagrid).datagrid('endEdit', index); 
  					
                   }; 	 
              });  
         }
	}
	
	function checkVal() {
		if ($("#id") != undefined && ($("#id").val() == "" || $("#id").val() == null )) {
			$.dialog.tips("请重新退出刷新页面", 1, "alert.gif", function() {
				//$("#modelcode").focus();
			});
			return false;
		}
        
		if ($("#inoutercode") != undefined && ($("#inoutercode").val() == "" || $("#inoutercode").val() == null )) {
			$.dialog.tips("请入库负责人", 1, "alert.gif", function() {
				$("#inputercode").focus();
			});
			return false;
		}
		
         //是否选择了产品标识
		var allrows = $('#purchasedetailDG').datagrid("getRows");
  		for(var i=0;i<allrows.length;i++){
  			if(allrows[i].depotrackcode == null || allrows[i].depotrackcode == ''){
  				$.dialog.tips("请选择存放货柜位置！", 2, 'alert.gif');
  				$('#purchasedetailDG').datagrid('selectRow',i);
  				//$('#userproductDG').datagrid('beginEdit', i);
		        //var ed = $(this).datagrid('getEditor', {index:i,field:productidentfy});
		        //获取编辑单元格对象
		        //var editorObject = $(ed.target);
		        //光标指向该编辑框
		        //editorObject.focus();
		        return false;
  			}
  		}
        
        //将row转换成json字符串
	 	var event = JSON.stringify(allrows);
	 	//中文进行编码转换，防止乱码
		var o = JSON.parse(event); 
		//将Json字符串赋值给event
		eval("var event = '"+JSON.stringify(o)+"';"); 
		
		$("#purchasedetailJson").val(event);
        
		return true;
	}
	
	function saveIndepot() {
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
		    	$("#addForm").attr("action", "purchase/saveIndepot?rid="+Math.random());
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
	
	 $(function () {
         //initmaterialname();
	     var returninfo = '${purchase.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
</script>
</body>
</html>
