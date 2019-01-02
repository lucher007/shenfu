<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
				<div class="easyui-panel" title="订户信息" style="width:100%;">	
					<table style="width: 100%">
						<tr>
							<td align="right">客户来源：</td>
							<td>
								<select id="source" name="source" class="select">
									<!--
									<option value="0" <c:if test="${user.source == '0'}">selected</c:if>>销售</option>
									  -->
									<option value="1" <c:if test="${user.source == '1'}">selected</c:if>>400客服</option>
								</select>
							</td>
						</tr>
						<tr>
							<td height="30" width="15%" align="right">客户编号：</td>
							<td width="85%"><input type="text" class="inp" name="usercode" id="usercode" value="${user.user.usercode }" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span></td>
						</tr>
						<tr>
							<td height="30" width="15%" align="right">客户姓名：</td>
							<td width="85%"><input type="text" class="inp" name="username" id="username" value="${user.user.username }"> <span class="red">*</span></td>
						</tr>
						<tr>
							<td height="30" width="15%" align="right">联系电话：</td>
							<td width="85%"><input type="text" class="inp" name="phone" id="phone" value="${user.user.phone }" readonly="readonly" style="background:#eeeeee;">
							<span class="red">*</span></td>
						</tr>
						<tr>
							<td height="30" width="15%" align="right">安装地址：</td>
							<td width="85%">
								<input type="text" class="inp" name="address" id="address" value="${user.user.address }" style="width: 350px;"/>
								<span class="red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">讲解类型：</td>
							<td>
								<select id="visittype" name="visittype" class="select" style="width: 200px;" onchange="showDivFlag();">
									<option value="1" <c:if test="${user.visittype == '1'}">selected</c:if>>亲自讲解</option>
									<option value="0" <c:if test="${user.visittype == '0'}">selected</c:if>>公司派人讲解</option>
								</select>
							</td>
						</tr>
						<tr style="height: 10px">
							<td align="right" width="15%">备注信息：</td>
							<td width="85%">
								 <textarea id="dealresult" name="dealresult" style="width:450px; height:60px;"
		                       			onKeyDown="checkLength('dealresult',300)" onKeyUp="checkLength('dealresult',300)">${user.user.dealresult}</textarea>
		            		  		<span class="red">还可以输入<span id="validNumdealresult">300</span>字</span>
							<td>
						</tr>
						<!--
						<tr>
							<td align="right">销售员编号：</td>
							<td>
								<input type="text" class="inp w200" name="salercode" id="salercode" readonly="readonly" style="background:#eeeeee;" value="${user.salercode }">
								<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
								<a href="javascript:cleanEmployee();" class="easyui-linkbutton" iconCls="icon-undo" style="width:80px">清除</a>
							</td>
						</tr>
						
						<tr>
							<td align="right">销售员姓名：</td>
							<td>
								<input type="text" class="inp w200" name="salername" id="salername" readonly="readonly" style="background:#eeeeee;" value="${user.salername }">
							</td>
						</tr>
						  -->
						<tr style="height: 10px">
							<td>
							<td>
						</tr>
					</table>
				</div>
				
                <!--不需要体验需要显示的内容 -->
				<div id="modelDiv" style="width:100%;">
					<div class="easyui-panel" title="套餐信息" style="width:100%;">
						<table style="width:100%">
					  		<tr>
								<td align="right">产品型号：</td>
								<td >
									<input id="modelcode" name="modelcode" class="easyui-combogrid" style="width: 300px;"> <span class="red">*</span>
								</td>
							</tr>
							<tr>
								<td align="right">型号名称：</td>
								<td >
									<input type="text" class="inp" id="modelname" name="modelname" value="${productmodel.modelname }" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
								</td>
							</tr>
							<tr>
								<td align="right">原价：</td>
								<td >
									<input type="text" class="inp" id="price"  name="price" value="${productmodel.price }" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
								</td>
							</tr>
							<tr>
								<td align="right">促销价：</td>
								<td >
									<input type="text" class="inp" id="sellprice"  name="sellprice" value="${productmodel.sellprice }" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
								</td>
							</tr>
							<tr>
								<td align="right">支付类型：</td>
								<td >
									<select id="paytype" name="paytype" class="select">
										<option value="1" <c:if test="${userorder.paytype == '1'}">selected</c:if>>货到付款</option>
										<option value="0" <c:if test="${userorder.paytype == '0'}">selected</c:if>>支付定金</option>
									</select>
								</td>
							</tr>
					  	</table>
					</div>
				</div>
				
				<div id="otherproduct" style="width:100%;">
					<div class="easyui-panel" title="产品信息" style="width:100%;">
						<table style="width:100%;border:0; cellpadding:0; cellspacing:0">
							<tr>
							    <td align="right">产品颜色：</td>
								<td >
									<input id="productcolor" name="productcolor" class="easyui-combobox"> <span class="red">*</span>
								</td>
							</tr>
							<tr>
								 <td align="right">是否带机械锁心：</td>
								 <td >
									<select id="lockcoreflag" name="lockcoreflag" class="select" style="width: 200px;">
										<option value="0" <c:if test="${user.lockcoreflag == '0'}">selected</c:if>>不需要</option>
										<option value="1" <c:if test="${user.lockcoreflag == '1'}">selected</c:if>>需要</option>
									</select>
									<span class="red">机械锁心，需要额外收费500元</span></td>
								 </td>
							</tr>
						</table>
					</div>
				</div>
				
				
				<div id="visitorDiv" style="width:100%;">
					<div class="easyui-panel" title="上门人员信息" style="width:100%;">
						<table style="width:100%;border:0; cellpadding:0; cellspacing:0">
							<tr>
								<td align="right" width="10%">上门人员编号：</td>
								<td width="25%">
									<input type="text" class="inp w200" name="visitorcode" id="visitorcode" readonly="readonly" style="background:#eeeeee;" value="${usertask.visitorcode }">
									<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
								</td>
							</tr>
							<tr>
								<td align="right" width="10%">上门人员姓名：</td>
								<td width="25%">
									<input type="text" class="inp w200" name="visitorname" id="visitorname" readonly="readonly" style="background:#eeeeee;" value="${usertask.visitorname }"><span class="red">*</span>
								</td>
							</tr>
							<tr>
								<td align="right">联系电话：</td>
								<td><input type="text" class="inp" name="visitorphone" id="visitorphone" readonly="readonly" style="background:#eeeeee;" value="${usertask.visitorphone }"> <span class="red">*</span></td>
							</tr>
							<tr>
								<td align="right">上门时间：</td>
								<td>
									<input type="text" id="visittime" name="visittime"  value="${usertask.visittime }"
                    	                  onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});" class="Wdate inp w150">
								</td>
							</tr>
						</table>
					</div>
				</div>
				
				<div id="productDiv" style="width:100%;display: none">
					<div id = "productmodelrefDG" style="width:100%; height: auto">
						<input type="hidden"name="productJson" id="productJson" >
					</div>
				</div>
				
			</div>
			<div id="ft" style="padding:5px;">
				<cite> 
					<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
       				<a href="javascript:saveUserorder();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
				</cite> <span class="red">${user.returninfo}</span>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/comtools.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript">
	
	function checkVal() {
		if ($("#username") != undefined && ($("#username").val() == "" || $("#username").val() == null )) {
			$("#username").focus();
			$.dialog.tips("请输入客户姓名", 1, "alert.gif");
			return false;
		}
		
		if ($("#phone") != undefined && ($("#phone").val() == "" || $("#phone").val() == null )) {
			$.dialog.tips("请输入电话号码", 2, "alert.gif");
			return false;
		}
		
		if ($("#address") != undefined && ($("#address").val() == "" || $("#address").val() == null )) {
			$("#address").focus();
			$.dialog.tips("请输入安装地址", 2, "alert.gif");
			return false;
		}
		
		var visittype = $('#visittype').val();
		if(visittype == '0'){
			if ($("#visitorcode") != undefined && ($("#visitorcode").val() == "" || $("#visitorcode").val() == null )) {
				$("#visitorcode").focus();
				$.dialog.tips("请选择上门人员", 1, "alert.gif",);
				return false;
			}
		}else if(visittype == '1'){
			if ($('#modelcode').combogrid('getValue')=='') {
				$('#modelcode').combogrid('textbox').focus();  
				$.dialog.tips("请选择产品套餐", 2, 'alert.gif');
				return false;
		    }
			if ($('#productcolor').combobox('getValue')=='') {
				$('#productcolor').combobox('textbox').focus();  
				$.dialog.tips("请选择产品颜色", 2, 'alert.gif');
				return false;
		    }
			
			//获取型号所关联的产品信息
		    //var rows  = $('#productmodelrefDG').datagrid('getRows');
	        //if(rows  == null  || rows  == ''){
	        //	$('#modelcode').next('span').find('input').focus();
	        //	$.dialog.tips("此产品型号未设置产品类别", 2, 'alert.gif');
			//	return false;
	        //}
	        //for(var i=0;i<rows.length;i++){
        	//	if(rows[i].productcode == 'undefined' || rows[i].productcode == null || rows[i].productcode == ''){
	        //		 $('#productmodelrefDG').datagrid('selectRow',i);
	        //		 $.dialog.tips("请选择产品信息", 2, 'alert.gif');
	     	//		 return false;
	        //	 }
	        //}
	        
	        //将row转换成json字符串
		 	//var event = JSON.stringify(rows);
		 	//中文进行编码转换，防止乱码
			//var o = JSON.parse(event); 
			//将Json字符串赋值给event
			//eval("var event = '"+JSON.stringify(o)+"';"); 
			
			//$("#productJson").val(event);
	        
		}else if((visittype == '2')){
			
			if ($("#visitorcode") != undefined && ($("#visitorcode").val() == "" || $("#visitorcode").val() == null )) {
				$("#visitorcode").focus();
				$.dialog.tips("请选择上门人员", 1, "alert.gif",);
				return false;
			}
			
			if ($('#modelcode').combogrid('getValue')=='') {
				$('#modelcode').combogrid('textbox').focus();  
				$.dialog.tips("请选择产品型号", 2, 'alert.gif');
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
	        	if(rows[i].typecode == '01'){
	        		if(rows[i].productcode == 'undefined' || rows[i].productcode == null || rows[i].productcode == ''){
		        		 $('#productmodelrefDG').datagrid('selectRow',i);
		        		 $.dialog.tips("请选择锁壳颜色", 2, 'alert.gif');
		     			 return false;
		        	 }
	        	}
	        }
	        
	        //将row转换成json字符串
		 	var event = JSON.stringify(rows);
		 	//中文进行编码转换，防止乱码
			var o = JSON.parse(event); 
			//将Json字符串赋值给event
			eval("var event = '"+JSON.stringify(o)+"';"); 
			
			$("#productJson").val(event);
		}
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
		    	$("#addForm").attr("action", "user/saveUserorder?rid="+Math.random());
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
	
	function goback(){
		 parent.closeTab('客户添加');
		//parent.closeDialog();
	}
	
	$(function () {
		
	   //初始化产品列表
	   initProductmodelrefDatagrid();
	   
	   //初始化显示方式
	   showDivFlag();
	   
       var returninfo = '${user.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 2, 'alert.gif');
       }
    });
	
	
	function showDivFlag() {
		var visittype = $('#visittype').val();
		if(visittype == '0'){ //公司派人讲解
			cleanEmployee();//清空上门人员
			$("#visitorDiv").show();
			$("#modelDiv").hide();
			$("#otherproduct").hide();
		}else if(visittype == '1'){//亲自讲解
			cleanEmployee();//清空上门人员
			$("#visitorDiv").hide();
			$("#modelDiv").show();
			$("#otherproduct").show();
		}else if(visittype == '2'){
			cleanEmployee();//清空上门人员
			$("#visitorDiv").show();
			$("#modelDiv").show();
			$("#otherproduct").show();
		}
	}
	
	var employeeDialog;
	function chooseEmployee() {
		
		var visittype = $('#visittype').val();
		var url = "";
		if(visittype == '0'){
			url = 'url:employee/findEmployeeListDialog?rid='+Math.random()
		}else if(visittype == '2'){
			url = 'url:employee/findEmployeeListDialog?rid='+Math.random()
		}
		
		employeeDialog = $.dialog({
			title : '上级销售员查询',
			lock : true,
			width : 1000,
			height : 600,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : url
		});
	}
	
	function closeEmployeeDialog(jsonStr) {
		employeeDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
	    
	    $("#visitorcode").val(jsonObject.employeecode);
		$("#visitorname").val(jsonObject.employeename);
		$("#visitorphone").val(jsonObject.phone);
	}
	
	function cleanEmployee() {
		$("#visitorcode").val("");
		$("#visitorname").val("");
		$("#visitorphone").val("");
	}
	
	//加载产品套餐列表
	$('#modelcode').combogrid({
         panelWidth: 850,
			idField: 'modelcode',
			textField: 'modelname',
			url: 'productmodel/getProductmodelCombogridJson?effectivetimeflag=1&rid='+Math.random(),
			method: 'get',
			fitColumns: true,
			columns: [[
				 { field: 'modelcode', title: '型号编码',width:150,align:"center"},
	             { field: 'modelname', title: '型号名称',width:200,align:"center"},
	             { field: 'price', title: '原价',width:100,align:"center"},
	             { field: 'sellprice', title: '优惠价',width:100,align:"center"},
	             { field: 'content', title: '简介',width:400,align:"center",
	             	formatter: function (value) {
				        	return "<span title='" + value + "'>" + value + "</span>";
				        },
	             },
			]],
			onSelect: function (rowIndex, rowData){  
					$("#modelname").val(rowData.modelname); 
					$("#price").val(rowData.price);
					$("#sellprice").val(rowData.sellprice);
					//刷新关联的产品信息
					$('#productmodelrefDG').datagrid('reload',{
						modelcode:$('#modelcode').combogrid('getValue'),
					});
			 }  
    });
	
	//加载产品颜色列表
	$('#productcolor').combobox({    
	    url:'productcolor/getProductcolorComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${user.productcolor}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
              
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
                   onBeginEdit:function(index,row){
                	   var ed = $('#productmodelrefDG').datagrid('getEditor', {index:index,field:'productcode'});
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
  	 
  //文本框输入字符长度判断
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
