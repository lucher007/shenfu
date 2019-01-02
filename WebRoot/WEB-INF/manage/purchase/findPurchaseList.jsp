<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<title></title>
<link type="text/css" rel="stylesheet" href="style/user.css">
<link rel="stylesheet" type="text/css" href="style/easyui/easyui.css">
<link rel="stylesheet" type="text/css" href="main/plugin/easyui/themes/icon.css">
<style type="text/css">
	#body table tr {
      height: 30px;
    }
</style>

</head>

<body>
	<div id="body">
    <div class="seh-box">
        <form action="" method="post" id="searchForm" name="searchForm">
            <table width="100%">
		    	<tr>
		    		<td align="right" width="10%">供应商名称：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="suppliername" id="suppliername" value="${purchase.suppliername }">
		    		</td>
		    		<td align="right" width="120px">采购单号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="purchasecode" id="purchasecode" value="${purchase.purchasecode }">
		    		</td>
		    		<td align="right" width="10%">状态：</td>
					<td width="20%">
						<select id="status" name="status" class="select" onchange="queryPurchase();">
		    			    <option value="" <c:if test="${purchase.status == '' }">selected</c:if>>请选择</option>
		    			    <option value="0" <c:if test="${purchase.status == '0' }">selected</c:if>>未到货</option>
							<option value="1" <c:if test="${purchase.status == '1' }">selected</c:if>>已到货</option>
							<option value="2" <c:if test="${purchase.status == '2' }">selected</c:if>>已审核入库</option>
			            </select>
					</td>
   		    		<td  align="center">
		    			<a href="javascript:queryPurchase();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id="purchaseDG" style="width:auto; height: 450px"></div>
    <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addPurchase();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updatePurchase();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deletePurchase();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
			<a href="javascript:lookPurchase();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看</a>
		</div>
	</div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initPurchaseDatagrid(){
         $('#purchaseDG').datagrid({
              title: '订单列表信息',
              queryParams: paramsJson(),
              url:'purchase/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              toolbar:'#tools',
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'purchasecode', title: '采购单号',width:60,align:"center"},
                  { field: 'suppliername', title: '供应商姓名',width:60,align:"center",},
                  { field: 'totalmoney', title: '总金额',width:60,align:"center",},
                  { field: 'plandelivertime', title: '预计发货日期',width:100,align:"center",},
                  { field: 'planarrivaltime', title: '预计到货日期',width:100,align:"center",},
                  { field: 'statusname', title: '状态',width:80,align:"center",},
                  { field: 'billtypename', title: '是否开票',width:80,align:"center",},
                  { field: 'billstatusname', title: '票据状态',width:80,align:"center",},
                  { field: 'invoicecode', title: '票据号码',width:80,align:"center",},
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 			suppliername:$("#suppliername").val(),
	 			purchasecode:$("#purchasecode").val(),
	 			status:$("#status").val(),
	 	};
	 	return json;
	 }
	
	function queryPurchase() {
		$('#purchaseDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	
	function lookPurchase(id) {
		
		var selected = $('#purchaseDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
		
	    dialog = $.dialog({
		    title: '采购单信息',
		    lock: true,
		    width: 1000,
		    height: 550,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    esc: false,
		    cancel:false,
		    content: 'url:purchase/lookInit?rid='+ Math.random()+'&id='+id
		});
	 }
	
	function updatePurchase(id) {
	    dialog = $.dialog({
		    title: '订单修改',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    esc: false,
		    cancel:false,
		    content: 'url:purchase/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addPurchase() {	
	    dialog = $.dialog({
		    title: '采购单添加',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    esc: false,
		    cancel:false,
		    content: 'url:purchase/addInit?rid='+Math.random()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#purchaseDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deletePurchase(id){
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="purchase/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#purchaseDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化订单列表
	   initPurchaseDatagrid();
		
       var returninfo = '${purchase.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>