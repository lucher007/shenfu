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
		    		<td align="right" width="120px">库存状态：</td>
		    		<td>
						<select id="depotstatus" name="depotstatus" class="select" onchange="queryProductdepot();">
							<option value="1" <c:if test="${productdepot.depotstatus == '1'}">selected</c:if>>有库存</option>
						</select>
					</td>
					<td align="right" width="120px">产品状态：</td>
		    		<td>
						<select id="productstatus" name="productstatus" class="select" onchange="queryProductdepot();">
							<option value="1" <c:if test="${productdepot.productstatus == '1'}">selected</c:if>>正常</option>
						</select>
					</td>
					<td align="right">SN/PN：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="productidentfy" id="productidentfy" value="${productdepot.productidentfy }">
		    		</td>
					<td align="center">
						<a href="javascript:queryProductdepot();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">产品类别：</td>
				    <td>
						<input id="typecode" name="typecode"> 
				    </td>
				    <td align="right">产品名称：</td>
					<td colspan="2">
						<input id="productcode" name="productcode" style="width: 200px;"> 
					</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
   	<div id = "productdepotDG" style="width:100%; height: auto;"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:selectProductdepot();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">选择</a>
		</div>
	</div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="<%=basePath%>js/lodop/LodopFuncs.js"></script>
<script type="text/javascript">
	
	function initProductdepotDatagrid(){
         $('#productdepotDG').datagrid({
              title: '产品库存信息',
              queryParams: paramsJson(),
              url:'productdepot/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              //fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              toolbar:'#tools',
              rowStyler: function(index,row){
							if (row.productstatus == '1'){
								return 'color:green;font-weight:bold;height:100';
							}else if (row.productstatus == '2'){
								return 'color:blue;font-weight:bold;height:100';
							}else if (row.productstatus == '3'){
								return 'color:red;font-weight:bold;height:100';
							}
						},
		 frozenColumns:[[
							{ field: 'productcode', title: '产品编码',align:"center",width:150 ,sortable:"true"},
							{ field: 'productname', title: '产品名称',align:"center",width:150},
							{ field: 'productidentfy', title: 'SN/PN',align:"center",width:150,sortable:"true"},
                       ]],
              columns: [[
		                    { field: 'depotamount', title: '库存量',width:150,height:100,align:"center"},
		                    { field: 'depotstatusname', title: '库存状态',width:150,height:100,align:"center"},
		                    { field: 'productversion', title: '产品软件版本',width:150,height:100,align:"center"},
		                    { field: 'depotrackcode', title: '货柜存放位置',width:150,align:"center"},
		                    { field: 'suppliername', title: '供货商简称',width:150,align:"center"},
		                    { field: 'installinfo', title: '组装信息',width:150,align:"center",
		                	  formatter: function (value) {
				                    return "<span title='" + value + "'>" + value + "</span>";
				                }
		                    },
		                    { field: 'addtime', title: '入库时间',width:150,align:"center"},
		                    { field: 'productsourcename', title: '产品来源',width:150,align:"center",},
		                    { field: 'inoutername', title: '负责人姓名',width:150,align:"center"},
		                    { field: 'productstatusname', title: '产品状态',width:150,align:"center",},
		              ]]
          });
	}
	
	function producerformatter(value,row,index){
	 	return row.producer.operatorname;
	}
	
	function productstatusformatter(value,row,index){
		if(value == '1'){
		  	return "有库存";
		}else if(value == '0'){
			return "无库存";
		}
	}
	
	function productsourceformatter(value,row,index){
	 	if(value == '0'){
	 		return "自产";
	 	}else if(value == '1'){
	 		return "外购";
	 	}
	 }
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		typecode:$('#typecode').combobox('getValue'),
	 		productcode:$('#productcode').combobox('getValue'),
	 		depotstatus:$("#depotstatus").val(),
	 		productstatus:$("#productstatus").val(),
	 		productidentfy:$("#productidentfy").val(),
	 	};
	 	
	 	return json;
	 }
	
	function queryProductdepot() {
		$('#productdepotDG').datagrid('reload',paramsJson());
	}
	
	$(function () {
	   
	   //初始化产品列表
	   initProductdepotDatagrid();
		
       var returninfo = '${productdepot.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	
	function selectProductdepot(){
	   
	 	//获取选中的行
	 	var row = $("#productdepotDG").datagrid('getSelected');
	 	
	 	var event = JSON.stringify(row);
		var o = JSON.parse(event); 
		//将Json字符串赋值给event
		eval("var event = '"+JSON.stringify(o)+"';"); 
		
		parent.closeProductdepotDialog(event);
    }
	
	function closeDialog(){
		dialog.close();
		$('#productdepotDG').datagrid('reload');// 刷新当前页面
	}
	
	//加载列表
	$('#typecode').combobox({  
	    url:'producttype/getProducttypeComboBoxJson',    
	    valueField:'typecode',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${productdepot.typecode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
	    	queryProductdepot();
        } 
	}); 
	
	//加载列表
	$('#productcode').combobox({    
	    url:'product/getProductComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${productdepot.productcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
	    	queryProductdepot();
        } 
	}); 
	
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryProductdepot();
	    }  
	});
	
	
</script>
</body>
</html>