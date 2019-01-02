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
		    <input type="hidden" name="id" id="id" value="${installinfo.installinfo.id}">
			<div class="easyui-panel" title="组装信息" style="width:100%;">
				<table style="width:100%">
					<tr>
						<td height="30" align="right" width="10%">材料编码：</td>
						<td width="20%">
							<input type="text" class="inp"  id="materialcode" name="materialcode" readonly="readonly" style="background:#eeeeee;" value="${installinfo.installinfo.materialcode}"> <span class="red">*</span>
						</td> 
						<td align="right" width="10%">产品名称：</td>
						<td width="15%">
							<input type="text" class="inp" name="materialname" id="materialname" readonly="readonly" style="background:#eeeeee;" value="${installinfo.installinfo.materialname}"> <span class="red">*</span>
						</td>
						<td align="right" width="10%">生产数量：</td>
						<td width="15%">
							<input type="text" class="inp" name="amount" id="amount" readonly="readonly" style="background:#eeeeee;" value="${installinfo.installinfo.amount}"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">生产批次号：</td>
						<td width="15%">
							<input type="text" class="inp w200" name="producecode" id="producecode" readonly="readonly" style="background:#eeeeee;" value="${installinfo.installinfo.producecode }">
						</td>
						<td align="right" width="10%">生产负责人编号：</td>
						<td width="15%">
							<input type="text" class="inp w200" name="producercode" id="producercode" readonly="readonly" style="background:#eeeeee;" value="${installinfo.installinfo.producercode }">
						</td>
						<td align="right" width="10%">生产负责人姓名：</td>
						<td width="15%" colspan="3">
							<input type="text" class="inp w200" name="producername" id="producername" readonly="readonly" style="background:#eeeeee;" value="${installinfo.installinfo.producername }"><span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">组装信息：</td>
			            <td colspan="5"> 
							<textarea id="installinformation" name="installinformation" style="width:550px; height:50px;">${installinfo.installinfo.installinformation}</textarea>
		               </td>
					</tr>
				</table>
			</div>
			
			<div id = "bomDG" style="width:100%; height:auto;" data-options="footer:'#ft'">
				<input type="hidden" name="componentJson" id="componentJson" >
    		</div>
	    		
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
					<!-- 
					    <input type="button" class="btn-save" value="保存" onclick="saveDelivery();"/>
				     -->
				    </cite><span class="red">${installinfo.returninfo}</span>
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
	     var returninfo = '${installinfo.returninfo}';
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
		
		$("#componentJson").val(event);
		
		//alert($("#componentJson").val());
		
		//return false;
		
		$("#addForm").attr("action", "installinfo/saveDeliveryUserorder");
	    $("#addForm").submit();
	}  
     
    function initBomDatagrid(){
         $('#bomDG').datagrid({
              title: '生产组装信息',
              queryParams: paramsJson(),
              url:'installinfo/findInstallcomponentJson',
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
                         { field: 'componentcode', title: '元器件编码',width:40,align:"center"},
                         { field: 'componentname', title: '元器件名称',width:100,align:"center"},
                         { field: 'componentmodel', title: '元器件规格',width:100,align:"center"},
                         { field: 'amount', title: '单件需量',width:30,align:"center"},
                         { field: 'batchno', title: '批次号',width:100,align:"center"},
                         { field: 'totalamount', title: '总需求量',width:30,align:"center"},
                         { field: 'depotrackcode', title: '仓库存放位置',width:50,align:"center"},
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var producecode = $("#producecode").val();
	 	var json = '';
	 	if(producecode != null && $("#producecode").val() != ''){
	 		json = {
	 				producecode:producecode,
		 	};
	 	}else{//默认不查询产品信息，故参数乱设置
	 		json = {
	 				producecode:'-100null',
		 	};
	 	}
	 	return json;
	 }
	 
</script>
</body>
</html>
