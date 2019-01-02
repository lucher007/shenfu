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
#easyui-panel table tr {
	height: 25px;
}
</style>
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm">
			<div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
				<div class="easyui-panel" title="任务单信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">任务单号：</td>
							<td>
								<input type="text" class="inp w200" name="taskcode" id="taskcode" readonly="readonly" style="background:#eeeeee;" value="${userorder.usertask.taskcode }">
							</td>
							<td align="right">客户来源：</td>
							<td>
								<select id="source" name="source" class="select">
									<option value="0" <c:if test="${userorder.usertask.source == '0'}">selected</c:if>>销售</option>
									<option value="1" <c:if test="${userorder.usertask.source == '1'}">selected</c:if>>400客服</option>
								</select>
							</td>
							<td align="right">订单号：</td>
							<td>
								<input type="text" class="inp w200" name="ordercode" id="ordercode" readonly="readonly" style="background:#eeeeee;" value="${userorder.usertask.ordercode }">
							</td>
						</tr>
						<tr>
							<td align="right">客户编号：</td>
							<td><input type="text" class="inp" name="usercode" id="usercode" readonly="readonly" style="background:#eeeeee;" value="${userorder.usertask.usercode }">
							<td align="right">客户姓名：</td>
							<td>
								<input type="text" class="inp" name="username" id="username" readonly="readonly" style="background:#eeeeee;" value="${userorder.usertask.username }"> 
							</td>
							<td align="right">联系电话：</td>
							<td><input type="text" class="inp" name="phone" id="phone" readonly="readonly" style="background:#eeeeee;" value="${userorder.usertask.phone }"> 
						</tr>
						<tr>
							<td align="right">客户地址：</td>
							<td colspan="3">
								<input type="text" class="inp" name="address" id="address" readonly="readonly" style="background:#eeeeee;width: 500px;" value="${userorder.usertask.address }">
							</td>
							<td align="right">上门类型：</td>
							<td>
								<input type="text" class="inp" name="visittype" id="visittype" readonly="readonly" style="background:#eeeeee;width:200px;" value="${userorder.usertask.visittypename }">
							</td>
						</tr>
						<tr>
							<td align="right">上门人员编号：</td>
							<td><input type="text" class="inp" name="visitorcode" id="visitorcode" readonly="readonly" style="background:#eeeeee;" value="${userorder.usertask.visitor.employeecode }">
							<td align="right">上门人员姓名：</td>
							<td>
								<input type="text" class="inp" name="visitorname" id="visitorname" readonly="readonly" style="background:#eeeeee;" value="${userorder.usertask.visitor.employeename }"> 
							</td>
							<td align="right">上门人员电话：</td>
							<td><input type="text" class="inp" name="visitorphone" id="visitorphone" readonly="readonly" style="background:#eeeeee;" value="${userorder.usertask.visitor.phone }"> </td>
						</tr>
						<c:if test="${userorder.usertask.source eq '0'}">
							<tr>
								<td align="right">销售人员编号：</td>
								<td><input type="text" class="inp" name="salercode" id="salercode" readonly="readonly" style="background:#eeeeee;" value="${userorder.usertask.saler.employeecode }">
								<td align="right">销售人员姓名：</td>
								<td>
									<input type="text" class="inp" name="salername" id="salername" readonly="readonly" style="background:#eeeeee;" value="${userorder.usertask.saler.employeename }"> 
								</td>
								<td align="right">销售人员电话：</td>
								<td><input type="text" class="inp" name="salerphone" id="salerphone" readonly="readonly" style="background:#eeeeee;" value="${userorder.usertask.saler.phone }"> </td>
							</tr>
						</c:if>
					</table>
				</div>
				<div class="easyui-panel" title="订单信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">总金额：</td>
							<td>
								<input type="text" class="inp" name="totalmoney" id="totalmoney" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.totalmoney}">
							</td>
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
				<div class="easyui-panel" title="产品型号信息" style="width:100%;">
					<table style="width:100%">
					     <c:forEach items="${userorder.userorder.userorderinfolist}" var="dataList">
					  		<tr>
								<td align="right">产品型号：</td>
								<td >
									<input type="text" class="inp" id="modelcode" name="modelcode" value="${dataList.modelcode }" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
								</td>
								<td align="right">型号名称：</td>
								<td >
									<input type="text" class="inp" id="modelname" name="modelname" value="${dataList.modelname }" readonly="readonly" style="background:#eeeeee; width: 200px;"> <span class="red">*</span>
								</td>
								<td align="right">价格：</td>
								<td >
									<input type="text" class="inp" id="price"  name="price" value="${dataList.price }" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
								</td>
							</tr>
					    </c:forEach>
				  	</table>
				</div>
				
				<div id = "productmodelrefDG" style="width:100%; height: auto">
					<input type="hidden"name="productJson" id="productJson" >
				</div>
				
				<!--
				<div class="easyui-panel" title="支付信息" style="width:100%;">
					<table style="width:100%">
						<tr>
							<td align="right">其他费用：</td>
							<td><input type="text" class="easyui-numberbox" name=otherfee id="otherfee"  value="${userorder.otherfee }"
							    maxlength="8" data-options="min:0,value:0"
							    ><span class="red">*</span></td>
							    <td align="right">首付金额：</td>
							<td><input type="text" class="easyui-numberbox" name="firstpayment" id="firstpayment"  value="${userorder.firstpayment }"
							     maxlength="8"  data-options="min:0,value:200"
				            	 > <span class="red">*</span></td>
				            <td align="right">支付方式：</td>
				            <td>
				            	<select id="paytype" name="paytype" class="select">
									<option value="0" <c:if test="${userorder.paytype == '0'}">selected</c:if>>现金</option>
									<option value="1" <c:if test="${userorder.paytype == '1'}">selected</c:if>>微信</option>
									<option value="2" <c:if test="${userorder.paytype == '2'}">selected</c:if>>支付宝</option>
								</select>
				            </td>
						</tr>
					</table>
				</div>
			  -->
			</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveUserorder();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">生成订单</a>
				    </cite><span class="red">${userorder.returninfo}</span>
			</div>
		</form>		
	</div>

    <script type="text/javascript" src="js/common/jquery.js"></script>
    <script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript">

  
  $(function () {
         initProductmodelrefDatagrid();
	     var returninfo = '${userorder.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
		parent.closeDialog();
  }
	
  function checkVal() {
		if ($("#taskcode") != undefined && ($("#taskcode").val() == "" || $("#taskcode").val() == null )) {
			$.dialog.tips("请选择任务单号", 1, "alert.gif", function() {
				$("#taskcode").focus();
			});
			return false;
		}
		
		//获取型号所关联的产品信息
	    var rows  = $('#productmodelrefDG').datagrid('getRows');
        if(rows  == null  || rows  == ''){
        	$('#modelcode').next('span').find('input').focus();
        	$.dialog.tips("此产品型号未设置产品类别", 2, 'alert.gif');
			return false;
        }
        
        for(var i=0;i<rows.length;i++){
        	 if(rows[i].productcode == 'undefined' || rows[i].productcode == null || rows[i].productcode == ''){
        		 $('#productmodelrefDG').datagrid('selectRow',i);
        		 $.dialog.tips("未选择产品信息", 2, 'alert.gif');
     			 return false;
        	 }
        }
	    
	    //将row转换成json字符串
	 	var event = JSON.stringify(rows);
	 	//中文进行编码转换，防止乱码
		var o = JSON.parse(event); 
		//将Json字符串赋值给event
		eval("var event = '"+JSON.stringify(o)+"';"); 
		
		$("#productJson").val(event);
		
		return true;
	}
	
  function saveUserorder() {
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
		    	$("#addForm").attr("action", "userorder/saveFill?rid="+Math.random());
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
  
   var taskDialog;
	function chooseTask() {
		taskDialog = $.dialog({
			title : '任务单查询',
			lock : true,
			width : 1000,
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
        
    function initProductmodelrefDatagrid(){
        $('#productmodelrefDG').datagrid({
            title: '型号包含的产品信息',
            queryParams: paramsJson(),
            url:'userproduct/findVolidUserproductListJson',
            pagination: false,
            singleSelect: true,
            scrollbar:true,
            //checkOnSelect: false,// 如果为true，当用户点击行的时候该复选框就会被选中或取消选中。 如果为false，当用户仅在点击该复选框的时候才会呗选中或取消。
            //pageSize: 10,
            //pageList: [10,30,50], 
            fitColumns:true,
            //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
            loadMsg:'loading...',
            onClickCell: onClickCell,
            columns: [[
                       { field: 'typecode', title: '类别编码',width:100,align:"center"},
                       { field: 'typename', title: '类别名称',width:100,align:"center"},
                       { field: 'productcode', title: '产品编号',width:100,align:"center",
                       	  editor:{
		                       		type:'combobox',
									options:{
										valueField:'id',
										textField:'text',
										method:'get',
										url:'product/getProductComboBoxJson',
									},
    		                  },
                       },
                       { field: 'productname', title: '产品名称',width:100,align:"center",
                        	  editor:{
 		                       		type:'textbox',
     		                  },
                        },
                       //{ field: 'productunit', title: '产品计算单位',width:100,align:"center"},
                       //{ field: 'productsource', title: '产品来源',width:100,align:"center",formatter:productsourceformatter},
                   ]],
                   onBeginEdit :function(index,row){
                	   var ed = $('#productmodelrefDG').datagrid('getEditor', {index:index,field:'productcode'});
                	   //重新加载下拉框的数据
                	   $(ed.target).combobox('reload', {typecode:row.typecode});//如果datagrid数据源列名称不是material_id记得修改这里
                	   
                	   $(ed.target).combobox({
                		   onLoadSuccess:function(){//默认选中
	   		       		    	$(ed.target).combobox('select',row.productcode);//菜单项可以text或者id
	   		       		   },
                	        onChange:function(newvalue,oldvalue){
                	        	var productname = $(ed.target).combobox('getText');
                	        	var productcode = $(ed.target).combobox('getValue');
    	         	        	if(productcode == ''){//未选择
    	         	        		productname = '';
    	         	        	}
                	        	var ed_productname = $('#productmodelrefDG').datagrid('getEditor', {index:index,field:'productname'});
                	        	$(ed_productname.target).textbox("setValue", productname);
                	        }
                	    });
                   },  
        });
     }
    
    //将表单数据转为json
  	function paramsJson(){
  	 	var ordercode = $('#ordercode').val();
  	 	
  	 	var json = '';
  	 	if(ordercode != null && ordercode != ''){
  	 		json = {
  	 				ordercode:ordercode,
  		 	};
  	 	}else{//默认不查询产品信息，故参数乱设置
  	 		
  	 		json = {
  	 				ordercode:'-111',
  		 	};
  	 	}
  	 	return json;
  	 }
      
      
     //单元格编辑，按回车键
	 function onClickCell(index, field){
		 //不是点击价单元格，不进入修改模式
		 if(field != 'productcode' && field != 'productname' ){
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
</script>
</body>
</html>
