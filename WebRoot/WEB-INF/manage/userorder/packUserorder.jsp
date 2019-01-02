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
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm">
		    <input type="hidden" name="id" id="id" value="${userorder.userorder.id}"/>
			<input type="hidden" name="operatetime" id="operatetime" value="${userorder.userorder.operatetime}"/>
			<input type="hidden" name="filingflag" id="filingflag" value="${userorder.userorder.filingflag}"/>
			<div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
				<div class="easyui-panel" title="订户信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">订单号：</td>
							<td >
								<input type="text" class="inp w200" name="ordercode" id="ordercode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.ordercode}">
								<input type="hidden" name="id" id="id" value="${userorder.userorder.id}">
								<input type="hidden" name="usercode" id="usercode" value="${userorder.userorder.usercode}">
							</td>
							<td align="right">客户姓名：</td>
							<td>
								<input type="text" class="inp" name="username" id="username" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.username}"> <span class="red">*</span>
							</td>
							<td align="right">联系电话：</td>
							<td><input type="text" class="inp" name="phone" id="phone" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.phone}"> <span class="red">*</span></td>
						</tr>
						<tr>
							<td align="right">客户来源：</td>
							<td>
								<select id="source" name="source" class="select">
									<option value="0" <c:if test="${userorder.userorder.source == '0'}">selected</c:if>>销售</option>
									<option value="1" <c:if test="${userorder.userorder.source == '1'}">selected</c:if>>400电话</option>
								</select>
								<span class="red">*</span>
							</td>
							<td align="right">上门人员姓名：</td>
							<td>
								<input type="hidden" class="inp w200" name="visitorcode" id="visitorcode" value="${userorder.userorder.visitorcode}">
								<input type="text" class="inp" name="visitorname" id="visitorname" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.visitor.employeename}">
							</td>
							 <td align="right">上门员电话：</td>
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
				
				<div class="easyui-panel" title="支付信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">总金额：</td>
							<td>
								<input type="text" class="inp" name="totalmoney" id="totalmoney" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.totalmoney}">
							</td>
							<!-- 
							<td align="right">其他费用：</td>
							<td>
								<input type="text" class="inp" name="otherfee" id="otherfee" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.otherfee}">
							</td>
							 -->
							<td align="right">首付金额：</td>
							<td><input type="text" class="inp" name="firstpayment" id="firstpayment" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.firstpayment}">
						    	<span class="red">
						    		${userorder.userorder.firstarrivalflagname}
						    	</span>
						    </td>
							<td align="right">尾款金额：</td>
							<td><input type="text" class="inp" name="finalpayment" id="finalpayment" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.finalpayment}"> 
								<span class="red">
									${userorder.userorder.finalarrivalflagname}
						    	</span>
							</td>
						</tr>
					</table>
				</div>
				
				<div id = "userproductDG" style="width:100%; height:auto;">
					<input type="hidden"name="userproductJson" id="userproductJson" >
	    		</div>
			</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:savePack();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
				    </cite><span class="red">${userorder.returninfo}</span>
			</div>	
		</form>		
	</div>
<script type="text/javascript" language="javascript" src="js/common/jquery.js"></script>
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
  }
	
  function checkVal() {
		if ($("#ordercode") != undefined && ($("#ordercode").val() == "" || $("#ordercode").val() == null )) {
			$.dialog.tips("请选择订单", 1, "alert.gif", function() {
				$("#ordercode").focus();
			});
			return false;
		}
		
		//是否选择了产品标识
		var allrows = $('#userproductDG').datagrid("getRows");
  		for(var i=0;i<allrows.length;i++){
  			if(allrows[i].productidentfy == null || allrows[i].productidentfy == '' || allrows[i].result != '1'){
  				$.dialog.tips("请输入正确的产品唯一标识！", 2, 'alert.gif');
  				$('#userproductDG').datagrid('selectRow',i);
  				//$('#userproductDG').datagrid('beginEdit', i);
		        //var ed = $(this).datagrid('getEditor', {index:i,field:productidentfy});
		        //获取编辑单元格对象
		        //var editorObject = $(ed.target);
		        //光标指向该编辑框
		        //editorObject.focus();
		        return false;
  			}
  		}
		
// 		if ($("#installerid") != undefined && ($("#installerid").val() == "" || $("#installerid").val() == null )) {
// 			$.dialog.tips("请选择安装人", 1, "alert.gif", function() {
// 				$("#installername").focus();
// 			});
// 			return false;
// 		}
//		if ($("#deliverytime") != undefined && ($("#deliverytime").val() == "" || $("#deliverytime").val() == null )) {
//			$.dialog.tips("请选择发货时间", 1, "alert.gif", function() {
//				$("#deliverytime").focus();
//			});
//			return false;
//		}
		return true;
	}
	
  function savePack() {
  
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
		    	$("#addForm").attr("action", "userorder/savePackUserorder?rid="+Math.random());
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
		
		//$("#addForm").attr("action", "userorder/savePackUserorder");
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
		
		$("#installerid").val(jsonObject.id);
		$("#installername").val(jsonObject.employeename);
		$("#installerphone").val(jsonObject.phone);
		
	}
     
     
    function initUserproductDatagrid(){
         $('#userproductDG').datagrid({
              title: '订单产品信息',
              queryParams: paramsJson(),
              url:'userproduct/findListJsonForPack',
              pagination: false,
              singleSelect: true,
              scrollbar:true,
              //pageSize: 10,
              //pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              onClickCell: onClickCell,
              onLoadSuccess:initresult,
              columns: [[
                  { field: 'modelcode', title: '型号编码',width:100,align:"center",},
                  { field: 'modelname', title: '型号名称',width:150,align:"center",},
                  { field: 'productcode', title: '产品编码',width:80,align:"center"},
                  { field: 'productname', title: '产品名称',width:120,align:"center"},
                  { field: 'productidentfy', title: '产品唯一标识',width:150,align:"center",
                	  editor:{
                     		type:'combobox',
							options:{
								valueField:'id',
								textField:'text',
								method:'get',
								url:'productdepot/getProductdepotComboBoxJsonForPack',
							},
	                  },
                  },
                  { field: 'depotamount', title: '库存量',width:50,align:"center",
                 	  editor:{
	                       		type:'textbox',
		                  },
                   },
                  { field: 'depotrackcode', title: '仓库存放位置',width:80,align:"center",
                  	  editor:{
		                       		type:'textbox',
		                  },
                   },
                  { field: 'result', title: '核对结果',width:60,align:"center",
                	  formatter:function(value, row, index){  
                		  return '<a href="#" onclick="return false;" id="opera_'+index+'" class="easyui-linkbutton" ></a>';  
                	  }
                  },
              ]],
              //初始化下拉框的值
              onBeginEdit :function(index,row){
	           	   var ed = $('#userproductDG').datagrid('getEditor', {index:index,field:'productidentfy'});
	           	   //重新加载下拉框的数据
	           	   $(ed.target).combobox('reload', {productcode:row.productcode, depotstatus:'1', productstatus:'1'});//如果datagrid数据源列名称不是material_id记得修改这里
	           	   $(ed.target).combobox({
		           		onLoadSuccess:function(){//默认选中
		       		    	$(ed.target).combobox('select',row.productidentfy);//菜单项可以text或者id
		       		    },
            	        onChange:function(newvalue,oldvalue){
            	        	var textvalue = $(ed.target).combobox('getText');
            	        	var depotamount = "";
            	        	var depotrackcode = "";
            	        	if(textvalue != null && textvalue != ""){
            	        		var textvalueArr = textvalue.split("_");
            	        		depotamount = textvalueArr[1];
            	        		depotrackcode = textvalueArr[2];
            	        	}
            	        	
            	        	//赋值给库存量
            	        	var ed_depotamount = $('#userproductDG').datagrid('getEditor', {index:index,field:'depotamount'});
            	        	$(ed_depotamount.target).textbox("setValue", depotamount);
            	            //赋值给库仓库存放位置
            	        	var ed_depotrackcode = $('#userproductDG').datagrid('getEditor', {index:index,field:'depotrackcode'});
            	        	$(ed_depotrackcode.target).textbox("setValue", depotrackcode);
            	        }
            	   });
              },  
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
	 
	
	//初始化图标
	 function initresult(data){
	    if(data){  
	        var rows=data.rows;  
	        for(var i=0; i<rows.length; i++){  
	            var productidentfy=rows[i].productidentfy;  
	            if(productidentfy == null || productidentfy == ''){
	            	var bt = $("#opera_" + i);  
		            bt.linkbutton({  
		                plain:true,  
		                iconCls:'icon-no'  
		            });  
	            }else{
	            	var bt = $("#opera_" + i);  
		            bt.linkbutton({  
		                plain:true,  
		                iconCls:'icon-ok'  
		            });  
	            }
	        }  
	    }  
	}  
	
	
	//单元格编辑，按回车键
	 function onClickCell(index, field){
		
		//不是点击唯一识别号，不进入修改模式
		if(field != 'productidentfy' ){
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
                        var productidentfy = editorObject.combogrid('getValue');
                	   
                        //验证输入的产品标识正确性，包括是否已经出库
                        var rows = $('#userproductDG').datagrid("getRows");
				        var data = {          
			        			          productcode:rows[index].productcode,
			        			          productname:rows[index].productname,
							     		  productidentfy:productidentfy
								   };
						var url="userdispatch/checkProductidentfy?rid="+Math.random();
						$.getJSON(url,data,function(jsonMsg){
							var allrows = $('#userproductDG').datagrid("getRows"); // 这段代码是// 对某个单元格赋值
							if(jsonMsg.flag == '1'){ //成功
					            allrows[index].result= '1';
					            //allrows[index].inouterphone= jsonMsg.inouterphone;
					            // 刷新该行, 只有刷新了才有效果
								//$(this).datagrid('refreshRow', index);
								
								//如果该行处于"行编辑"状态, 如果直接调用"refreshRow"方法. 会报data is undefined这个错; 
								//需要先调用"endEdit", 再调用"refreshRow", 最后调用"selectRow"和"beginEdit"这两个方法便可了;
			                       			//$('#userproductDG').datagrid('endEdit', index).datagrid('refreshRow', index).datagrid('selectRow', index).datagrid(
			                            	//	'beginEdit', index);
                                $(currentEdatagrid).datagrid('acceptChanges');  
                       			$(currentEdatagrid).datagrid('endEdit', index); 
                       			//var bt = $("a[name='opera_" + index + "']");  
                       			$("#opera_" + index).linkbutton({plain:true,iconCls:'icon-ok'});
							 }else{
								allrows[index].result= '0';
								//var bt = $("a[name='opera_" + index + "']");  
								$("#opera_" + index).linkbutton({plain:true,iconCls:'icon-no'});   
								$.dialog.tips(jsonMsg.returninfo, 2, 'alert.gif');
							 }
					    });
                       
                 }; 	 
            });  
        }
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
