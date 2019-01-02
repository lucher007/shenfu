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
		    <input type="hidden" name="id" id="id" value="${produceproduct.produceproduct.id}">
			<div class="easyui-panel" title="订单信息" style="width:100%;">
				<table style="width:100%">
					<tr>
						<td height="30" align="right" width="10%">产品SN码：</td>
						<td width="20%">
							<input type="text" class="inp"  id="productidentfy" name="productidentfy" readonly="readonly" style="background:#eeeeee;" value="${produceproduct.produceproduct.productidentfy}"> <span class="red">*</span>
						</td> 
					</tr>
					<tr>
						<td align="right" width="10%">产品编码：</td>
						<td width="15%">
							<input type="text" class="inp" name="productcode" id="productcode" readonly="readonly" style="background:#eeeeee;" value="${produceproduct.produceproduct.productcode}"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">产品名称：</td>
						<td width="15%">
							<input type="text" class="inp" name="productname" id="productname" readonly="readonly" style="background:#eeeeee;" value="${produceproduct.produceproduct.productname}"> <span class="red">*</span>
						</td>
					</tr>
				</table>
			</div>
			
			<div id = "bomDG" style="width:100%; height:auto;" data-options="footer:'#ft'">
				<input type="hidden" name="materialJson" id="materialJson" >
    		</div>
	    		
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
					<!-- 
					    <input type="button" class="btn-save" value="保存" onclick="saveDelivery();"/>
				     -->
				    </cite><span class="red">${produceproduct.returninfo}</span>
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
  		 initBomDatagrid();
	     var returninfo = '${produceproduct.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
	  parent.closeTabSelected();
  }
	
	
  function saveDelivery() {
		
		//获取所有产品的json信息
		var allrows = $('#bomDG').datagrid("getRows");
		
		//将row转换成json字符串
	 	var event = JSON.stringify(allrows);
	 	//中文进行编码转换，防止乱码
		var o = JSON.parse(event); 
		//将Json字符串赋值给event
		eval("var event = '"+JSON.stringify(o)+"';"); 
		
		$("#materialJson").val(event);
		
		//alert($("#materialJson").val());
		
		//return false;
		
		$("#addForm").attr("action", "produceproduct/saveDeliveryUserorder");
	    $("#addForm").submit();
	}  
     
    function initBomDatagrid(){
         $('#bomDG').datagrid({
              title: '生产组装信息',
              queryParams: paramsJson(),
              url:'produceproduct/findProducematerailJson',
              pagination: false,
              singleSelect: true,
              scrollbar:true,
              //pageSize: 10,
              //pageList: [10,30,50], 
              fitColumns:true,
              rownumbers:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              //onClickCell: onClickCell,
              columns: [[
                         { field: 'materialcode', title: '材料编码',width:40,align:"center"},
                         { field: 'materialname', title: '材料名称',width:100,align:"center"},
                         { field: 'amount', title: '单件需量',width:30,align:"center"},
                         { field: 'batchno', title: '材料批次号',width:100,align:"center"},
                         { field: 'materialidentfy', title: '材料SN码',width:100,align:"center"},
                         { field: 'depotrackcode', title: '仓库存放位置',width:50,align:"center"},
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var productidentfy = $("#productidentfy").val();
	 	var json = '';
	 	if(productidentfy != null && $("#productidentfy").val() != ''){
	 		json = {
	 				productidentfy:productidentfy,
		 	};
	 	}else{//默认不查询产品信息，故参数乱设置
	 		json = {
	 				productidentfy:'-100null',
		 	};
	 	}
	 	return json;
	 }
	 
</script>
</body>
</html>
