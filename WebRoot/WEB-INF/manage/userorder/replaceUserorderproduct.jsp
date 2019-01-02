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
		    <input type="hidden" name="id" id="id" value="${userorder.userorder.id}">
		    <input type="hidden" name="dealresult" id="dealresult" >
		    <input type="hidden" name="checkresult" id="checkresult" >
			
			<div class="easyui-panel" style="width:100%;"  data-options="footer:'#ft'">
				<div class="easyui-panel" title="订单信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">订单号：</td>
							<td >
								<input type="text" class="inp w200" name="ordercode" id="ordercode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.ordercode}">
								<input type="hidden" name="id" id="id" value="${userorder.userorder.id}">
								<input type="hidden" name="usercode" id="usercode" value="${userorder.userorder.usercode}">
							</td>
							<td align="right">客户编号：</td>
							<td>
								<input type="text" class="inp" name="usercode" id="usercode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.usercode}"> <span class="red">*</span>
							</td>
							<td align="right">客户姓名：</td>
							<td>
								<input type="text" class="inp" name="username" id="username" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.username}"> <span class="red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">联系电话：</td>
							<td><input type="text" class="inp" name="phone" id="phone" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.phone}"> <span class="red">*</span></td>
							<td align="right">客户来源：</td>
							<td>
								<select id="source" name="source" class="select">
									<option value="0" <c:if test="${userorder.userorder.source == '0'}">selected</c:if>>销售</option>
									<option value="1" <c:if test="${userorder.userorder.source == '1'}">selected</c:if>>400电话</option>
								</select>
							</td>
							<td align="right">上门类型：</td>
							<td>
								<select id="visittype" name="visittype" class="select" style="width: 200px;">
									<option value="0" <c:if test="${userorder.userorder.visittype == '0'}">selected</c:if>>公司派人上门讲解测量</option>
									<option value="1" <c:if test="${userorder.userorder.visittype == '1'}">selected</c:if>>亲自上门讲解测量</option>
									<option value="2" <c:if test="${userorder.userorder.visittype == '2'}">selected</c:if>>已讲解，公司派人测量</option>
								</select>
							</td>
						</tr>
						<tr>	
						    <td align="right">销售员编号：</td>
							<td>
								<input type="text" class="inp" name="salercode" id="salercode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.saler.employeecode}">
						 	</td>
							<td align="right">销售员姓名：</td>
							<td>
								<input type="text" class="inp" name="salername" id="salername" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.saler.employeename}">
							</td>
							<td align="right">销售员电话：</td>
							<td>
								<input type="text" class="inp" name="salerphone" id="salerphone" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.saler.phone}">
						 	</td>
						</tr>
						<tr>	
						    <td align="right">讲解员编号：</td>
							<td>
								<input type="text" class="inp" name="talkercode" id="talkercode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.talker.employeecode}">
						 	</td>
							<td align="right">讲解员姓名：</td>
							<td>
								<input type="text" class="inp" name="talkername" id="talkername" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.talker.employeename}">
							</td>
							<td align="right">讲解员电话：</td>
							<td>
								<input type="text" class="inp" name="talkerphone" id="talkerphone" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.talker.phone}">
						 	</td>
						</tr>
						<tr>	
						    <td align="right">测量员编号：</td>
							<td>
								<input type="text" class="inp" name="visitorcode" id="visitorcode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.visitor.employeecode}">
						 	</td>
							<td align="right">测量员姓名：</td>
							<td>
								<input type="text" class="inp" name="visitorname" id="visitorname" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.visitor.employeename}">
							</td>
							<td align="right">测量员电话：</td>
							<td>
								<input type="text" class="inp" name="visitorphone" id="visitorphone" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.visitor.phone}">
						 	</td>
						</tr>
						<tr>
							<td align="right">客户地址：</td>
							<td colspan="5"><input type="text" class="inp" name="address" id="address" readonly="readonly" style="background:#eeeeee; width: 500px;" value="${userorder.userorder.address}"></td>
						</tr>
					</table>
				</div>
				
				<!--
				<div class="easyui-panel" title="支付信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">总金额：</td>
							<td>
								<input type="text" class="inp" name="totalmoney" id="totalmoney" readonly="readonly" style="background:#eeeeee; width: 100px;" value="${userorder.userorder.totalmoney}">
							</td>
							<td align="right">其他费用：</td>
							<td>
								<input type="text" class="inp" name="otherfee" id="otherfee" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.otherfee}">
							</td>
							<td align="right">首付金额：</td>
							<td><input type="text" class="inp" name="firstpayment" id="firstpayment" readonly="readonly" style="background:#eeeeee; width: 100px;" value="${userorder.userorder.firstpayment}">
						    	<span class="red">
						    		${userorder.userorder.firstarrivalflagname}
						    	</span>
						    </td>
							<td align="right">尾款金额：</td>
							<td><input type="text" class="inp" name="finalpayment" id="finalpayment" readonly="readonly" style="background:#eeeeee; width: 100px;" value="${userorder.userorder.finalpayment}"> 
								<span class="red">
									${userorder.userorder.finalarrivalflagname}
						    	</span>
							</td>
							<td align="right">支付类型：</td>
							<td> 
								<select id="paytype" name="paytype" class="select">
									<option value="0" <c:if test="${userorder.userorder.paytype == '0'}">selected</c:if>>支付定金</option>
									<option value="1" <c:if test="${userorder.userorder.paytype == '1'}">selected</c:if>>货到付款</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">产品颜色：</td>
							<td>
								<input type="text" class="inp" name="productcolorname" id="productcolorname" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.productcolorname}">
							</td>
							<td align="right">是否带机械锁心：</td>
							<td> 
								<select id="lockcoreflag" name="lockcoreflag" class="select" style="width: 110px;">
									<option value="0" <c:if test="${userorder.userorder.lockcoreflag == '0'}">selected</c:if>>不需要</option>
									<option value="1" <c:if test="${userorder.userorder.lockcoreflag == '1'}">selected</c:if>>需要</option>
								</select>
							</td>
							<td align="right">是否需要上门测量：</td>
							<td> 
								<select id="visitorflag" name="visitorflag" class="select" style="width: 110px;">
									<option value="0" <c:if test="${userorder.userorder.visitorflag == '0'}">selected</c:if>>不需要</option>
									<option value="1" <c:if test="${userorder.userorder.visitorflag == '1'}">selected</c:if>>需要</option>
								</select>
							</td>
						</tr>
					</table>
				</div>
				  -->
				<div id = "userproductDG" style="width:100%; height:auto;">
					<input type="hidden"name="userproductJson" id="userproductJson" >
	    		</div>
				
				<div class="easyui-panel" title="新产品信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">产品唯一标识：</td>
							<td>
								<input type="text" id="productidentfy" name="productidentfy"  class="inp" readonly="readonly" style="background:#eeeeee;" value="">
								<a href="javascript:chooseProductdepot();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
							</td>
							<td align="right">货柜存放位置：</td>
							<td>
								<input type="text" id="depotrackcode" name="depotrackcode" class="inp" readonly="readonly" style="background:#eeeeee;" value="">
							</td>
							<td align="right">产品编码：</td>
							<td>
								<input type="text" id="productcode" name="productcode" class="inp" readonly="readonly" style="background:#eeeeee;" value="">
							</td>
							<td align="right">产品名称：</td>
							<td>
								<input type="text" id="productname" name="productname" class="inp" readonly="readonly" style="background:#eeeeee;" value="">
							</td>
						</tr>
						<tr>
							<td align="right">产品类别编码：</td>
							<td>
								<input type="text" id="typecode" name="typecode" class="inp" readonly="readonly" style="background:#eeeeee;" value="">
							</td>
							<td align="right">产品类别名称：</td>
							<td>
								<input type="text" id="typename" name="typename"  class="inp" readonly="readonly" style="background:#eeeeee;" value="">
							</td>
							<td align="right">新产品软件版本：</td>
							<td>
								<input type="text" id="newproductversion" name="productversion"  class="inp" readonly="readonly" style="background:#eeeeee;" value="">
							</td>
							<td align="right">旧产品回收状态：</td>
							<td>
								<select id="productstatus" name="productstatus" class="select" style="width: 110px;">
									<option value="1">正常使用</option>
									<option value="2">已损坏</option>
									<option value="3">维修中</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">上门售后时间：</td>
							<td>
								<input type="text" id="visittime" name="visittime"  value="${visittime }"
                    	                  onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});" class="Wdate inp w150">
							</td>
							<td align="right">故障描述：</td>
							<td>
								<textarea id="visitreasons" name="visitreasons" style="width:400px; height:30px;">${visitreasons}</textarea>
							</td>
							
						</tr>
						<!--
						<tr>
							<td align="right">出现问题原因：</td>
							<td>
								<textarea id="oldproductproblem" name="oldproductproblem" style="width:400px; height:30px;">${oldproductproblem}</textarea>
							</td>
							<td align="right">回收备注信息：</td>
							<td>
								<textarea id="rebackremark" name="rebackremark" style="width:400px; height:30px;">${rebackremark}</textarea>
							</td>
						</tr>
						  -->
					</table>
				</div>
			</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveUserproductreplace();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">产品更换</a>
				    </cite><span class="red">${userorder.returninfo}</span>
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
	     var returninfo = '${userorder.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
	  parent.closeTabSelected();
		//parent.closeDialog();
  }
	
  function checkVal() {
	    //是否选择了产品标识
		var selected = $('#userproductDG').datagrid("getSelected");
	    if(selected == null){
	    	$.dialog.tips("请选择需要需要更换的产品", 2, "alert.gif");
			return false;
	    }
	  
		if ($("#productidentfy") != undefined && ($("#productidentfy").val() == "" || $("#productidentfy").val() == null )) {
			$.dialog.tips("请选择新的产品识别号", 1, "alert.gif", function() {
				$("#productidentfy").focus();
			});
			return false;
		}
		
		//选择的产品与更换的产品编号是否一样
		//if(selected.productcode != $("#productcode").val()){
		//	$.dialog.tips("请选择的新产品与替换的旧产品编号不一样", 2, "alert.gif");
		//	return false;
		//}
		
		return true;
	}
	
  function saveUserproductreplace() {
  
		if (!checkVal()) {
			return;
		}
		
		//获取所有产品的json信息
		var allrows = $('#userproductDG').datagrid("getSelected");
		
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
		    	$("#addForm").attr("action", "userorder/saveUserproductreplace");
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
  
	var productdepotDialog;
	function chooseProductdepot() {
		
		 //是否选择了产品标识
		var selected = $('#userproductDG').datagrid("getSelected");
	    if(selected == null){
	    	$.dialog.tips("请选择需要需要更换的产品", 2, "alert.gif");
			return false;
	    }
		
	    var typecode = selected.typecode;
	    var productcode = selected.productcode;
	    
		productdepotDialog = $.dialog({
			title : '产品库存查询',
			lock : true,
			width : 1100,
			height : 650,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:productdepot/findProductdepotListDialog?rid='+Math.random() + '&typecode='+ typecode + '&productcode='+ productcode
		});
	}
	function closeProductdepotDialog(jsonStr) {
		productdepotDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
		$("#productidentfy").val(jsonObject.productidentfy);
		$("#depotrackcode").val(jsonObject.depotrackcode);
		$("#typecode").val(jsonObject.typecode);
		$("#typename").val(jsonObject.typename);
		$("#productcode").val(jsonObject.productcode);
		$("#productname").val(jsonObject.productname);
		$("#newproductversion").val(jsonObject.productversion);
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
                  { field: 'modelcode', title: '型号编号',width:100,align:"center",},
                  { field: 'modelname', title: '型号名称',width:150,align:"center",},
                  { field: 'typecode', title: '类别编码',width:80,align:"center"},
                  { field: 'typename', title: '类别名称',width:80,align:"center"},
                  { field: 'productcode', title: '产品编号',width:80,align:"center"},
                  { field: 'productname', title: '产品名称',width:150,align:"center"},
                  { field: 'productidentfy', title: '产品识别号',width:150,align:"center",editor:'text'},
                  { field: 'productversion', title: '产品软件版本',width:150,align:"center",editor:'text'},
                  { field: 'depotrackcode', title: '货柜存放位置',width:80,align:"center",editor:'text'},
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
