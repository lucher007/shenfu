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
<link rel="stylesheet" href="main/plugin/easyui/themes/default/easyui.css"/>
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
		    			    <option value="1" <c:if test="${purchase.status == '1' }">selected</c:if>>已到货</option>
			            </select>
					</td>
		    		<td align="right" width="130">
        				<input type="button" class="btn-sch" value="查询" onclick="queryPurchase();"/>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "purchaseDG" style="width:auto; height: 450px"></div>
  </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
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
                  { field: 'actualarrivaltime', title: '到货时间',width:100,align:"center",},
                  { field: 'statusname', title: '状态',width:80,align:"center",},
                  { field: 'id', title: '详情' ,width:100,align:"center",
	            		    formatter:function(val, row, index){ 
							 	var orderid = row.id;
							 	var lookContent = '<a class="btn-look" href="javascript:lookPurchase('+ orderid +');">查看</a>';
						        return  lookContent;
							    
	                	    },
	              },
                  { field: 'id1', title: '操作' ,width:100,align:"center",
            		    formatter:function(val, row, index){ 
						 	var id = row.id;
						 	if(row.status == '1'){
							 	var updateArrival = '<a class="btn-edit" href="javascript:indepotPurchase('+ id +');">确认入库</a>';
						        return  updateArrival;
					        }else{
						 		return "";
						 	}
                	    },
            },
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
	
	function indepotPurchase(id) {
	    dialog = $.dialog({
		    title: '采购单入库信息',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:purchase/indepotPurchaseInit?rid='+ Math.random()+'&id='+id
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#purchaseDG').datagrid('reload');// 刷新当前页面
	}
	
	function lookPurchase(id) {
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
	
	$(function () {
	   //初始化订单列表
	   initPurchaseDatagrid();
       var returninfo = '${purchase.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 2, 'alert.gif');
       }
    });
</script>
</body>
</html>