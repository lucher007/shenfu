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
	height: 25px;
}
</style>
</head>
<body>
<div id="body">
	<form method="post" id="addForm" name="addForm">
		<input type="hidden" name="id" id="id" value="${userorder.userorder.id}"/>
		<input type="hidden" name="operatetime" id="operatetime" value="${userorder.userorder.operatetime}"/>
		<input type="hidden" name="filingflag" id="filingflag" value="${userorder.userorder.filingflag}"/>
    	<div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
			<div class="easyui-panel" title="订单信息" style="width:100%;">
				<table style="width:100%">
					<tr>
						<td align="right">订单号：</td>
						<td >
							<input type="text" class="inp w200" name="ordercode" id="ordercode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.ordercode }">
						</td>
						<td align="right">状态：</td>
						<td >
						   <input type="text" class="inp w200" name="statusname" id="statusname" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.statusname}">
						</td>
						<td align="right">任务单号：</td>
						<td >
							<input type="text" class="inp w200" name="taskcode" id="taskcode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.taskcode }">
						</td>
					</tr>
					<tr>
						<td align="right">客户编号：</td>
						<td>
							<input type="text" class="inp w200" name="usercode" id="usercode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.usercode}"><span class="red">*</span>
						</td>
						<td align="right">客户姓名：</td>
						<td>
							<input type="text" class="inp w200" name="username" id="username" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.username}"><span class="red">*</span>
						</td>
						<td align="right">联系电话：</td>
						<td>
							<input type="text" class="inp" name="phone" id="phone" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.phone}"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">客户来源：</td>
						<td>
							<input type="text" class="inp" name="source" id="source" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.sourcename}"> <span class="red">*</span>
							<span class="red">*</span>
						</td>
						<td align="right">上门类型：</td>
						<td>
							<input type="text" class="inp" name="visittype" id="visittype" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.visittypename}"> <span class="red">*</span>
							<span class="red">*</span>
						</td>
						<td align="right">销售员姓名：</td>
						<td>
							<input type="text" class="inp" name="salername" id="salername" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.saler.employeename}"> <span class="red">*</span>
						</td>
					</tr>
					<!-- 
					<tr>
						<td align="right">上门人员编号：</td>
						<td>
							<input type="text" class="inp" name="visitorcode" id="visitorcode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.visitor.employeecode}"> <span class="red">*</span>
						</td>
						<td align="right">上门人员姓名：</td>
						<td>
							<input type="text" class="inp" name="visitorname" id="visitorname" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.visitor.employeename}"> <span class="red">*</span>
						</td>
						<td align="right">上门人员电话：</td>
						<td>
							<input type="text" class="inp" name="visitorphone" id="visitorphone" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.visitor.phone}"> <span class="red">*</span>
						</td>
					</tr>
					 -->
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
							<input type="text" class="inp" id="modelcode" name="modelcode" value="${userorder.userorder.modelcode}" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">型号名称：</td>
						<td >
							<input type="text" class="inp" id="modelname" name="modelname"  value="${userorder.userorder.modelname}" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
					    <td align="right">产品颜色：</td>
						<td >
							<input id="productcolor" class="inp"  name="productcolor" value="${userorder.userorder.productcolorname}" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						 <td align="right">是否带机械锁心：</td>
						 <td >
						 	<input id="lockcoreflag" class="inp"  name="lockcoreflag" value="${userorder.userorder.lockcoreflagname}" readonly="readonly" style="background:#eeeeee;"><span class="red">机械锁心，需要额外收费500元</span></td>
						 </td>
					</tr>
				    <tr>
						<td align="right">产品销售原价：</td>
						<td >
							<input type="text" class="inp" id="productfee" name="productfee"  value="${userorder.userorder.productfee}" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
				    </tr>
				    <tr>
						<td align="right">优惠价格：</td>
						<td >
							<input type="text" class="inp" id="discountfee" name="discountfee"  value="${userorder.userorder.discountfee}" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
				    </tr>
				    <tr>
						<td align="right">订单金额：</td>
						<td >
							<input type="text" class="inp" id="totalmoney" name="totalmoney"  value="${userorder.userorder.totalmoney}" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
				     </tr>
				     <tr>
						<td align="right">订单详情：</td>
						<td >
							<textarea id="orderinfo" name="orderinfo" style="width:550px; height:50px;">${userorder.userorder.orderinfo}</textarea>
						</td>
					 </tr>
			  	</table>
			</div>
			<div id="productDiv" style="width:1040px;display: none">
				<div id = "productmodelrefDG" style="width:100%; height: auto;">
					<input type="hidden"name="productJson" id="productJson" >
				</div>
			</div>
		</div>
		<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:updateOrderinfo();" class="easyui-linkbutton" iconCls="icon-save">修改订单金额</a>
				    </cite><span class="red">${userorder.returninfo}</span>
			</div>
	</form>		
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  $(function () {
	     //initProductmodelrefDatagrid();
	     var returninfo = '${userorder.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 2, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
	  parent.closeTabSelected();
	//parent.closeDialog();
  }
	
  function checkVal() {
	  if ($("#orderinfo") != undefined && ($("#orderinfo").val() == "" || $("#orderinfo").val() == null )) {
			$.dialog.tips("请输入订单详情", 1, "alert.gif", function() {
				$("#orderinfo").focus();
			});
			return false;
		}
		return true;
	}
	
  function updateOrderinfo() {
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
		    	$("#addForm").attr("action", "userorder/updateOrderinfo?rid="+Math.random());
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
    	 var modelcodeObject = $('#modelcode');
         var productcolorObject = $('#productcolor');
         var amountObject = $('#amount');
         var priceObject = $('#price');
         
		  var modelcode = $('#modelcode').combogrid('getValue');
		  var productcolor = productcolorObject.val();
		  var amount = amountObject.val();
		  var price = priceObject.val();
		  
		  if (modelcode == undefined || modelcode == '') {
		    $.dialog.tips("请输入产品型号", 1, "alert.gif");
		    modelcodeObject.focus();
		    return false;
		  }
		  if (productcolor == undefined || productcolor == '') {
		    $.dialog.tips("请选择产品颜色", 1, "alert.gif");
		    return false;
		  }
		  if (price == undefined || price == '') {
			    $.dialog.tips("请输入产品价格", 1, "alert.gif");
			    return false;
		  }
		  if (amount == undefined || amount == '') {
		    $.dialog.tips("请选择产品个数", 1, "alert.gif");
		    return false;
		  }
		  
		 var grid=$("#modelcode").combogrid("grid");//获取表格对象 
 		 var row = grid.datagrid('getSelected');//获取行数据 
		 
 		 var modelcode = row.modelcode;
 		 var modelname = row.modelname;
 		 var productcode = row.productcode;
		 var productname = row.productname;
		 var productcolor = row.productcolor;
		 var productunit = row.productunit;
		 var price = row.price;
		 
		 var table = modelcodeObject.parents('table');
		  
		 for(var i = 0;i < parseInt(amount);i++){
			
			  table.append('<tr class="count">'
			  	  + '     <td align="center" ><input type="text" class="inp" name="modelcode" readonly="readonly" style="background:#eeeeee;" value="' + modelcode + '"/></td>'
			  	  + '     <td align="center" ><input type="text" class="inp" name="modelname" readonly="readonly" style="background:#eeeeee;" value="' + modelname + '"/></td>'
			  	  + '     <td align="center" ><input type="text" class="inp" name="productcode" readonly="readonly" style="background:#eeeeee;" value="' + productcode + '"/></td>'
			  	  + '     <td align="center" >'
			  	  + '           <input type="hidden" class="inp" name="productunit" readonly="readonly" style="background:#eeeeee;" value="' + productunit + '"/>'
			  	  + '           <input type="text" class="inp" name="productname" readonly="readonly" style="background:#eeeeee;" value="' + productname + '"/>'
			  	  + '     </td>'
			  	  + '     <td align="center" ><input type="text" class="inp" name="productcolor" readonly="readonly" style="background:#eeeeee;" value="' + productcolor + '"/></td>'
			  	  + '     <td align="center" ><input type="text" class="inp" name="price" readonly="readonly" style="background:#eeeeee;" value="' + price + '"/></td>'
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
                { field: 'price', title: '原价格',width:100,align:"center"},
                { field: 'sellprice', title: '优惠价格',width:100,align:"center"},
			]],
			onSelect: function (rowIndex, rowData){  
				$("#modelid").val(rowData.id); 
				$("#modelcode").val(rowData.modelcode); 
				$("#productname").val(rowData.productname);  
				$("#price").val(rowData.price);  
		  	}
    });
	
    function initProductmodelrefDatagrid(){
        $('#productmodelrefDG').datagrid({
            title: '型号包含的产品信息',
            queryParams: paramsJson(),
            url:'productmodel/findVolidProducttypeOfModerefedListJson',
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
  	 	var modelcode = $('#modelcode').combogrid('getValue');
  	 	var json = '';
  	 	if(modelcode != null && modelcode != ''){
  	 		json = {
  	 			modelcode:modelcode,
  		 	};
  	 	}else{//默认不查询产品信息，故参数乱设置
  	 		
  	 		json = {
  	 			modelcode:'-111',
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
