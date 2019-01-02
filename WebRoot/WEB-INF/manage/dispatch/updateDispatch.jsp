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
		<input type="hidden" name="id" id="id" value="${userdispatch.userdispatch.id}"/>
		<input type="hidden" name="dispatchcode" id="dispatchcode" value="${userdispatch.userdispatch.dispatchcode}"/>
    	<input type="hidden" name="operatorcode" id="operatorcode" value="${userdispatch.userdispatch.operatorcode}"/>
    	<input type="hidden" name="status" id="status" value="${userdispatch.userdispatch.status}"/>
			<div class="easyui-panel" style="width:100%;"  data-options="footer:'#ft'">
				<div class="easyui-panel" title="订单信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">订单号：</td>
							<td >
								<input type="text" class="inp w200" name="ordercode" id="ordercode" readonly="readonly" style="background:#eeeeee;" value="${userdispatch.userdispatch.userorder.ordercode}">
								<input type="hidden" name="id" id="id" value="${userdispatch.userdispatch.userorder.id}">
								<input type="hidden" name="usercode" id="usercode" value="${userdispatch.userdispatch.userorder.usercode}">
							</td>
							<td align="right">客户姓名：</td>
							<td>
								<input type="text" class="inp" name="username" id="username" readonly="readonly" style="background:#eeeeee;" value="${userdispatch.userdispatch.userorder.username}"> <span class="red">*</span>
							</td>
							<td align="right">联系电话：</td>
							<td><input type="text" class="inp" name="phone" id="phone" readonly="readonly" style="background:#eeeeee;" value="${userdispatch.userdispatch.userorder.phone}"> <span class="red">*</span></td>
						</tr>
						<tr>
							<td align="right">客户来源：</td>
							<td>
								<select id="source" name="source" class="select">
									<option value="0" <c:if test="${userdispatch.userdispatch.userorder.source == '0'}">selected</c:if>>销售</option>
									<option value="1" <c:if test="${userdispatch.userdispatch.userorder.source == '1'}">selected</c:if>>400电话</option>
								</select>
								<span class="red">*</span>
							</td>
							<td align="right">上门人员姓名：</td>
							<td>
								<input type="hidden" class="inp w200" name="visitorcode" id="visitorcode" value="${userdispatch.userdispatch.userorder.visitorcode}">
								<input type="text" class="inp" name="visitorname" id="visitorname" readonly="readonly" style="background:#eeeeee;" value="${userdispatch.userdispatch.userorder.visitor.employeename}">
							</td>
							 <td align="right">上门员电话：</td>
							<td>
								<input type="text" class="inp" name="visitorphone" id="visitorphone" readonly="readonly" style="background:#eeeeee;" value="${userdispatch.userdispatch.userorder.visitor.phone}">
						 	</td>
						</tr>
						<tr>
							<td align="right">客户地址：</td>
							<td colspan="5"><input type="text" class="inp" name="address" id="address" readonly="readonly" style="background:#eeeeee; width: 500px;" value="${userdispatch.userdispatch.userorder.address}"></td>
						</tr>
					</table>
				</div>
				
				<div id = "userproductDG" style="width:100%; height:auto;">
					<input type="hidden"name="userproductJson" id="userproductJson" >
	    		</div>
	    		
				<div class="easyui-panel" title="支付信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">总金额：</td>
							<td>
								<input type="text" class="inp" name="totalmoney" id="totalmoney" readonly="readonly" style="background:#eeeeee;" value="${userdispatch.userdispatch.userorder.totalmoney}">
							</td>
							<td align="right">其他费用：</td>
							<td>
								<input type="text" class="inp" name="otherfee" id="otherfee" readonly="readonly" style="background:#eeeeee;" value="${userdispatch.userdispatch.userorder.otherfee}">
							</td>
							<td align="right">首付金额：</td>
							<td><input type="text" class="inp" name="firstpayment" id="firstpayment" readonly="readonly" style="background:#eeeeee;" value="${userdispatch.userdispatch.userorder.firstpayment}">
						    	<span class="red">
						    		${userdispatch.userdispatch.userorder.firstarrivalflagname}
						    	</span>
						    </td>
							<td align="right">尾款金额：</td>
							<td><input type="text" class="inp" name="finalpayment" id="finalpayment" readonly="readonly" style="background:#eeeeee;" value="${userdispatch.userdispatch.userorder.finalpayment}"> 
								<span class="red">
									${userdispatch.userdispatch.userorder.finalarrivalflagname}
									<c:choose>  
					        			<c:when test="${userdispatch.userdispatch.userorder.finalarrivalflag eq '1'}">已到账</c:when>  
									    <c:otherwise>未到账</c:otherwise>  
									</c:choose>
						    	</span>
							</td>
						</tr>
					</table>
				</div>
				<div class="easyui-panel" title="发货信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">发货时间：</td>
							<td>
								<input type="text" id="deliverytime" name="deliverytime" class="Wdate inp w150" value="${fn:substring(userdispatch.userdispatch.userorder.userdelivery.deliverytime, 0, 10)}">
							</td>
							<td align="right">快递名称：</td>
							<td>
								<input type="text" id="deliveryname" name="deliveryname"  class="inp w150" value="${userdispatch.userdispatch.userorder.userdelivery.deliveryname}">
							</td>
							<td align="right">快递单号：</td>
							<td>
								<input type="text" id="deliverycode" name="deliverycode" class="inp w150" value="${userdispatch.userdispatch.userorder.userdelivery.deliverycode}">
							</td>
						</tr>
					</table>
				</div>
				<div class="easyui-panel" title="安装信息" style="width:100%;">
					<table style="width:100%">
						<tr>
						 <td align="right">安装人员编号：</td>
							<td >
								<input type="text" class="inp" name="installercode" id="installercode" readonly="readonly" style="background:#eeeeee;" value="${userdispatch.userdispatch.installer.employeecode}">
								<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
								<a href="javascript:cleanEmployee();" class="easyui-linkbutton" iconCls="icon-undo" style="width:80px">清除</a>
							</td>
							<td align="right">安装人姓名：</td>
							<td><input type="text" class="inp" name="installername" id="installername" readonly="readonly" style="background:#eeeeee;" value="${userdispatch.userdispatch.installer.employeename}"></td>
							<td align="right">联系电话：</td>
							<td><input type="text" class="inp" name="installerphone" id="installerphone" readonly="readonly" style="background:#eeeeee;" value="${userdispatch.userdispatch.installer.phone}"></td>
							<!--
							<td align="right">安装日期：</td>
							<td>
								<input type="text" id="installtime" name="installtime"
	                    			onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150" value="${dispatch.dispatch.installtime}">
							</td>
							  -->
						</tr>
						<tr>
							<td align="right">工单内容：</td>
				            <td colspan="3"> 
								<textarea id="content" name="content" style="width:550px; height:30px;"
			                       onKeyDown="checkLength('content',100)" onKeyUp="checkLength('content',100)">${userdispatch.userdispatch.content} </textarea>
			            		<span class="red">还可以输入<span id="validNumcontent">100</span>字</span>
			               </td>
						</tr>
						<tr style="height: 10px">
							<td>
							<td>
						</tr>
					</table>
				</div>
			</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:updateDispatch();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
				    </cite><span class="red">${userdispatch.returninfo}</span>
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
  		 initUserproductDatagrid();
	     var returninfo = '${dispatch.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
		parent.closeDialog();
  }
	
  function checkVal() {
		if ($("#ordercode") != undefined && ($("#ordercode").val() == "" || $("#ordercode").val() == null )) {
			$.dialog.tips("请选择订单", 2, "alert.gif", function() {
				$("#ordercode").focus();
			});
			return false;
		}
		
		if ($("#installercode") != undefined && ($("#installercode").val() == "" || $("#installercode").val() == null )) {
			$.dialog.tips("请选择安装人", 1, "alert.gif", function() {
				$("#installercode").focus();
			});
			return false;
		}
// 		if ($("#installtime") != undefined && ($("#installtime").val() == "" || $("#installtime").val() == null )) {
// 			$.dialog.tips("请选择安装时间", 1, "alert.gif", function() {
// 				$("#installtime").focus();
// 			});
// 			return false;
// 		}
		return true;
	}
	
  function updateDispatch() {
  
		if (!checkVal()) {
			return;
		}
		
		//获取所有产品的json信息
		var allrows = $('#userproductDG').datagrid("getRows");
		
		//将row转换成json字符串
	 	var event = JSON.stringify(allrows);
	 	//中文进行编码转换，防止乱码
		var o = JSON.parse(event); 
		//将Json字符串赋值给event
		eval("var event = '"+JSON.stringify(o)+"';"); 
		
		$("#userproductJson").val(event);
		
		//alert($("#userproductJson").val());
		
		//return false;
		
		$.dialog({
		    title: '提交',
		    lock: true,
		    background: '#000', /* 背景色 */
		    opacity: 0.5,       /* 透明度 */
		    content: '是否确定执行？',
		    icon: 'alert.gif',
		    ok: function () {
		    	$("#addForm").attr("action", "userdispatch/update?rid="+Math.random());
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
		
		//$("#addForm").attr("action", "userdispatch/update");
	    //$("#addForm").submit();
	}  
  
   var orderDialog;
	function chooseOrder() {
		orderDialog = $.dialog({
			title : '订单查询',
			lock : true,
			width : 900,
			height : 450,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:userorder/findUserorderListDialog?rid='+Math.random()
		});
	}

	function closeOrderDialog(jsonStr) {
		orderDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
	    
		$("#orderid").val(jsonObject.id);
		$("#orderno").val(jsonObject.orderno);
		$("#userid").val(jsonObject.userid);
		$("#username").val(jsonObject.username);
		$("#phone").val(jsonObject.phone);
		$("#usersex").val(jsonObject.usersex);
		$("#source").val(jsonObject.source);
		$("#address").val(jsonObject.address);
		$("#researcherid").val(jsonObject.researcherid);
		$("#researchername").val(jsonObject.researchername);
		$("#researcherphone").val(jsonObject.researcherphone);
		$("#totalmoney").val(jsonObject.totalmoney);
		$("#firstpayment").val(jsonObject.firstpayment);
		$("#finalpayment").val(jsonObject.finalpayment);
		//加载订单产品信息
		$('#userproductDG').datagrid('reload',paramsJson());
	}
	
	var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '员工查询',
			lock : true,
			width : 900,
			height : 450,
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
		
		$("#installercode").val(jsonObject.employeecode);
		$("#installername").val(jsonObject.employeename);
		$("#installerphone").val(jsonObject.phone);
		
	}
     
	function cleanEmployee() {
		  $('#installercode').val("");
		  $('#installername').val("");
		  $('#installerphone').val("");
	  }
     
     
	 function initUserproductDatagrid(){
         $('#userproductDG').datagrid({
              title: '订单产品信息',
              queryParams: paramsJson(),
              url:'userproduct/findListJson',
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
                  { field: 'modelcode', title: '型号编号',width:150,align:"center",},
                  { field: 'modelname', title: '型号名称',width:150,align:"center",},
                  { field: 'productcode', title: '产品编号',width:100,align:"center"},
                  { field: 'productname', title: '产品名称',width:100,align:"center"},
                  { field: 'productidentfy', title: '产品识别号',width:150,align:"center",editor:'text'},
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var ordercode = $("#ordercode").val();
	 	var json = '';
	 	if(ordercode != null && $("#ordercode").val() != ''){
	 		json = {
	 			ordercode:$("#ordercode").val(),
		 	};
	 	}else{//默认不查询产品信息，故参数乱设置
	 		json = {
	 			ordercode:'-100null',
		 	};
	 	}
	 	return json;
	 }
	
	
	 //单元格编辑，按回车键
	 function onClickCell(index, field){
			
		$(this).datagrid('beginEdit', index);
        var ed = $(this).datagrid('getEditor', {index:index,field:field});
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
                         var productidentfy = editorObject.val();
                         //验证输入的产品标识正确性，包括是否已经出库
                         var rows = $('#userproductDG').datagrid("getRows");
		        var data = {          
		        			          color:rows[index].color,
		        			          model:rows[index].model,
						     productidentfy:productidentfy,
						   };
				var url="userdispatch/checkProductidentfy?rid="+Math.random();
				$.getJSON(url,data,function(jsonMsg){
					if(jsonMsg.flag == '1'){ //成功
						
                        			var allrows = $('#userproductDG').datagrid("getRows"); // 这段代码是// 对某个单元格赋值
			            allrows[index].inoutername= jsonMsg.inoutername;
			            allrows[index].inouterphone= jsonMsg.inouterphone;
			            // 刷新该行, 只有刷新了才有效果
						//$(this).datagrid('refreshRow', index);
						
						//如果该行处于"行编辑"状态, 如果直接调用"refreshRow"方法. 会报data is undefined这个错; 
						//需要先调用"endEdit", 再调用"refreshRow", 最后调用"selectRow"和"beginEdit"这两个方法便可了;
                        			//$('#userproductDG').datagrid('endEdit', index).datagrid('refreshRow', index).datagrid('selectRow', index).datagrid(
                             	//	'beginEdit', index);
                             		
                                 $(currentEdatagrid).datagrid('acceptChanges');  
                        			$(currentEdatagrid).datagrid('endEdit', index); 
					 }else{
						$.dialog.tips(jsonMsg.returninfo, 1, 'alert.gif');
					 }
				 });
                        
                  }; 	 
             });  
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
