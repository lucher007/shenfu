<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
  <style>
     .fb-con table tr {
      height: 30px;
    }
  </style>
</head>

<body>
<div id="body">
  <form action="" method="post" id="updateform" name="updateform">
       <input type="hidden" name="id" id="id" value="${productmodel.productmodel.id}"/>
    
   	   <div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
	       <div class="easyui-panel" title="产品型号信息" style="width:100%;">
		       <table width="100%" >
				<tr>
					<td height="30" width="15%" align="right">型号编码：</td>
					<td width="25%"><input type="text" class="easyui-textbox" name="modelcode" id="modelcode" value="${productmodel.productmodel.modelcode }"> <span class="red">*</span></td>
				</tr>
				<tr>
					<td height="30" width="15%" align="right">型号名称：</td>
					<td width="25%"><input type="text" style="width:300px"  class="easyui-textbox" name="modelname" id="modelname" value="${productmodel.productmodel.modelname }"> <span class="red">*</span></td>
				</tr>
				<tr>
					<td align="right">产品价格：</td>
					<td>
						<input type="text" class="inp easyui-numberbox" name="price" id="price"  value="${productmodel.productmodel.price }" maxlength="8"> <span class="red">*</span>
					</td> 
				</tr>
				<tr>
					<td align="right">销售价格：</td>
					<td>
						<input type="text" class="inp easyui-numberbox" name="sellprice" id="sellprice"  value="${productmodel.productmodel.sellprice }" maxlength="8"> <span class="red">*</span>
					</td> 
				</tr>
				<!--
				<tr>
					<td align="right">最大浮动价格：</td>
					<td>
						<input type="text" class="inp easyui-numberbox" name="maxprice" id="maxprice"  value="${productmodel.productmodel.maxprice }" maxlength="8"> <span class="red">*</span>
					</td> 
				</tr>
				<tr>
					<td align="right">最小浮动价格：</td>
					<td>
						<input type="text" class="inp easyui-numberbox" name="minprice" id="minprice"  value="${productmodel.productmodel.minprice }" maxlength="8"> <span class="red">*</span>
					</td> 
				</tr>
				-->
				
				<tr>
					<td align="right">优惠权限金额：</td>
					<td>
						<input type="text" class="inp easyui-numberbox" name="discountgain" id="discountgain"  value="${productmodel.productmodel.discountgain }" maxlength="8"> <span class="red">*</span>
					</td> 
				</tr>
				
				<tr>
					<td align="right">固定返利金额：</td>
					<td>
						<input type="text" class="inp easyui-numberbox" name="fixedgain" id="fixedgain"  value="${productmodel.productmodel.fixedgain }" maxlength="8"> <span class="red">*</span>
					</td> 
				</tr>
				
				<tr>
					<td align="right">销售管家提成：</td>
					<td>
						<input type="text" class="inp easyui-numberbox" name="managergain" id="managergain"  value="${productmodel.productmodel.managergain }" maxlength="8"> <span class="red">*</span>
					</td> 
				</tr>
				
				<tr>
					<td align="right">有效开始时间：</td>
					<td>
						<input type="text" id="starttime" name="starttime" value="${fn:substring(productmodel.productmodel.starttime, 0, 19)} " style="width: 200px;"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});" class="Wdate inp">
					</td> 
				</tr>
				<tr>
					<td align="right">有效结束时间：</td>
					<td>
						<input type="text" id="endtime" name="endtime" value="${fn:substring(productmodel.productmodel.endtime, 0, 19)}" style="width: 200px;"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});" class="Wdate inp">
					</td> 
				</tr>
				<tr>
					<td align="right">状态：</td>
					<td>
						<select id="status" name="status" class="easyui-combobox" style="width:143px;">
							<option value="0" <c:if test="${productmodel.productmodel.status == '0' }">selected</c:if>>未发布</option>
		    			    <option value="1" <c:if test="${productmodel.productmodel.status == '1' }">selected</c:if>>正式发布</option>
			            </select>
					</td> 
				</tr>
				<tr>
					<td align="right" width="10%">简介：</td>
		            <td width="25%"> 
						<textarea id="content" name="content" style="width:450px; height:50px;"
	                       onKeyDown="checkLength('content',100)" onKeyUp="checkLength('content',100)">${productmodel.productmodel.content}</textarea>
	            		<span class="red">还可以输入<span id="validNumcontent">100</span>字</span>
	               </td>
				</tr>
			</table>
		</div>
		<div id = "producttypeDG" style="width:100%; height:auto;">
		   <input type="hidden"name="producttypeJson" id="producttypeJson" >
  	    </div>
    </div>
    <div id="ft" style="padding:5px;">
		<cite> 
			<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
			<a href="javascript:updateProductmodel();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
		</cite>
	    <span class="red">${productmodel.returninfo}</span>
	</div>
	</form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="<%=basePath%>/js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  function updateProductmodel() {
		if ($("#modelcode") != undefined && ($("#modelcode").val() == "" || $("#modelcode").val() == null )) {
			$.dialog.tips("请输入型号编码", 1, "alert.gif", function() {
				$("#modelcode").focus();
			});
			return false;
		}
		if ($("#modelname") != undefined && ($("#modelname").val() == "" || $("#modelname").val() == null )) {
			$.dialog.tips("请输入型号名称", 1, "alert.gif", function() {
				$("#modelname").focus();
			});
			return false;
		}
		
		if ($("#price") != undefined && ($("#price").val() == "" || $("#price").val() == null )) {
			$.dialog.tips("请输入原价格", 1, "alert.gif", function() {
				$("#price").focus();
			});
			return false;
		}
		
		if ($("#sellprice") != undefined && ($("#sellprice").val() == "" || $("#sellprice").val() == null )) {
			$.dialog.tips("请输入销售价格", 1, "alert.gif", function() {
				$("#sellprice").focus();
			});
			return false;
		}
		
		<%--
		if ($("#maxprice") != undefined && ($("#maxprice").val() == "" || $("#maxprice").val() == null )) {
			$.dialog.tips("请输入最大浮动价格", 1, "alert.gif", function() {
				$("#maxprice").focus();
			});
			return false;
		}
		
		if ($("#minprice") != undefined && ($("#minprice").val() == "" || $("#minprice").val() == null )) {
			$.dialog.tips("请输入最小浮动价格", 1, "alert.gif", function() {
				$("#minprice").focus();
			});
			return false;
		}
		--%>
		
		if ($("#discountgain") != undefined && ($("#discountgain").val() == "" || $("#discountgain").val() == null )) {
			$.dialog.tips("请输入优惠权限金额", 1, "alert.gif", function() {
				$("#discountgain").focus();
			});
			return false;
		}
		
		if ($("#fixedgain") != undefined && ($("#fixedgain").val() == "" || $("#fixedgain").val() == null )) {
			$.dialog.tips("请输入固定返利金额", 1, "alert.gif", function() {
				$("#fixedgain").focus();
			});
			return false;
		}
		
		if ($("#managergain") != undefined && ($("#managergain").val() == "" || $("#managergain").val() == null )) {
			$.dialog.tips("请输入销售管家提成", 1, "alert.gif", function() {
				$("#managergain").focus();
			});
			return false;
		}
		
		if ($("#starttime") != undefined && ($("#starttime").val() == "" || $("#starttime").val() == null )) {
			$.dialog.tips("请输入有效开始时间", 1, "alert.gif", function() {
				$("#starttime").focus();
			});
			return false;
		}
		if ($("#endtime") != undefined && ($("#endtime").val() == "" || $("#modelname").val() == null )) {
			$.dialog.tips("请输入有效结束时间", 1, "alert.gif", function() {
				$("#endtime").focus();
			});
			return false;
		}
		
		//获取所有选择的产品
	    var rowsSelected = $('#producttypeDG').datagrid('getChecked');
        if(rowsSelected == null  || rowsSelected == ''){
        	 $.dialog.tips('请选择关联的产品类别', 1, 'alert.gif');
		      return;
        }
		
        //将row转换成json字符串
	 	var event = JSON.stringify(rowsSelected);
	 	//中文进行编码转换，防止乱码
		var o = JSON.parse(event); 
		//将Json字符串赋值给event
		eval("var event = '"+JSON.stringify(o)+"';"); 
		
		$("#producttypeJson").val(event);
		
		$.dialog({
		    title: '提交',
		    lock: true,
		    background: '#000', /* 背景色 */
		    opacity: 0.5,       /* 透明度 */
		    content: '是否确定执行？',
		    icon: 'alert.gif',
		    ok: function () {
		    	$("#updateform").attr("action", "productmodel/update?rid="+Math.random());
				$("#updateform").submit();
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
  
  function goback() {
     parent.closeDialog();
  }
  
  $(function () {
	  initProducttypeDatagrid();
      var returninfo = '${productmodel.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
  });
 
  function initProducttypeDatagrid(){
      $('#producttypeDG').datagrid({
          title: '产品信息',
          queryParams: paramsJson(),
          url:'productmodel/findProductmoderefListJson',
          pagination: false,
          singleSelect: false,
          scrollbar:true,
          checkOnSelect: false,// 如果为true，当用户点击行的时候该复选框就会被选中或取消选中。 如果为false，当用户仅在点击该复选框的时候才会呗选中或取消。
          //pageSize: 10,
          //pageList: [10,30,50], 
          fitColumns:true,
          idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
          loadMsg:'loading...',
          onClickCell: onClickCell,
          columns: [[
					  { field: 'id', title: '<spring:message code="page.select.all"/>',checkbox:true,align:"center",width:40,
						  formatter: function(val, row, index){
					          return row.id;
					      }, 
					  },   
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
                 ]],
	       onLoadSuccess:function(data){  
	        	    var rowData = data.rows;    
	        	    $.each(rowData,function(index,row){//遍历JSON     
	        	        if(row.checkedFlag == '1'){ //如果数据行为已选中则选中改行   
	        	            $("#producttypeDG").datagrid("checkRow", index);  
	        	            //$("#productDG").datagrid("selectRecord", row.id);  
	        	        }  
	        	           
	        	    });  
	        },  
	        onBeginEdit :function(index,row){
         	   var ed = $('#producttypeDG').datagrid('getEditor', {index:index,field:'productcode'});
         	   //重新加载下拉框的数据
         	   
         	   $(ed.target).combobox('reload', {typecode:row.typecode});//如果datagrid数据源列名称不是material_id记得修改这里
         	   $(ed.target).combobox('select', {typecode:row.productcode});//如果datagrid数据源列名称不是material_id记得修改这里
         	   
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
         	        	var ed_productname = $('#producttypeDG').datagrid('getEditor', {index:index,field:'productname'});
         	        	
         	        	$(ed_productname.target).textbox("setValue", productname);
         	        }
         	    });
            },  
      });
   }
  
    //将表单数据转为json
	function paramsJson(){
	 	var modelcode = '${productmodel.productmodel.modelcode}';
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
