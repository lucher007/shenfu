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
		    		<td align="right" width="120px">产品来源：</td>
		    		<td>
						<select id="depotstatus" name="depotstatus" class="select" onchange="queryProductdepot();">
							<!-- 
							<option value="" <c:if test="${productdepot.productstatus == ''}">selected</c:if>>请选择</option>
							 -->
							<option value="1" <c:if test="${productdepot.depotstatus == '1'}">selected</c:if>>有库存</option>
							<option value="" <c:if test="${productdepot.depotstatus == ''}">selected</c:if>>请选择</option>
							<option value="0" <c:if test="${productdepot.depotstatus == '0'}">selected</c:if>>无库存</option>
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
			<a href="javascript:addProductdepot();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">新批次入库</a>
			<a href="javascript:addOutdepot();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">产品手动出库</a>
			<a href="javascript:updateDepotamount();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">同批次手动入库</a>
			<a href="javascript:printBarcode();" class="easyui-linkbutton" iconCls="icon-print"  plain="true">打印条码</a>
			<a href="javascript:updateProductstatus();" class="easyui-linkbutton" iconCls="icon-print"  plain="true">修改产品信息</a>
			<a href="javascript:saveProductrepair();" class="easyui-linkbutton" iconCls="icon-tip"  plain="true">产品维修</a>
		</div>
	</div>
	
	<!-- 产品销售信息 -->
	<div id="userproductList" style="width:auto; height: auto;"></div>
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
              onClickRow: onClickRow,
              rowStyler: function(index,row){
		            	  	if (row.depotamount == 0){
		          	    		return 'color:black;font-weight:bold;';
							}else{
								if (row.productstatus == '1'){
									return 'color:green;font-weight:bold;height:100';
								}else if (row.productstatus == '2'){
									return 'color:blue;font-weight:bold;height:100';
								}else if (row.productstatus == '3'){
									return 'color:red;font-weight:bold;height:100';
								}
							}
						},
		 frozenColumns:[[
							{ field: 'productcode', title: '产品编码',align:"center",width:150 ,sortable:"true"},
							{ field: 'productname', title: '产品名称',align:"center",width:150},
							{ field: 'productidentfy', title: 'SN/PN',align:"center",width:150,sortable:"true"},
                       ]],
              columns: [[
						  { field: 'productversion', title: '产品软件版本',width:150,align:"center",},
		                  { field: 'depotamount', title: '库存量',width:150,height:100,align:"center"},
		                  { field: 'depotstatusname', title: '库存状态',width:150,height:100,align:"center"},
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
		              ]],
				onLoadSuccess:function(data){  
	 	        	 //默认选择第一条
	 	        	 $('#productdepotDG').datagrid("selectRow", 0);
	 	        	 //加载第一条驻点的工作人员信息
	 	        	 initUserproductList();
	 	         },
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
    
	
	function addProductdepot() {
		
		 parent.addTab('新批次入库','productdepot/indepot?rid='+ Math.random());
		
	 }
    
	/**
	*出库
	*/
	function addOutdepot(){
		
		var selected = $('#productdepotDG').datagrid("getSelected");
        if(selected == null){
   	         $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var productidentfy = selected.productidentfy;
		
        //库存状态
        var productstatus = selected.productstatus;
        if(productstatus == '0'){
        	$.dialog.tips('该产品没有库存，不能手动出库', 2, 'alert.gif');
		     return;
        }
        
        parent.addTab('产品手动出库','productdepot/addOutdepotInit?rid='+ Math.random()+ '&productidentfy=' +productidentfy);
		
	}
	
	/**
	*同批次手动入库
	*/
	function updateDepotamount(){
		
		var selected = $('#productdepotDG').datagrid("getSelected");
        if(selected == null){
   	         $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var productidentfy = selected.productidentfy;
		
        //产品来源
        var productsource = selected.productsource;
        if(productsource == '0'){
        	$.dialog.tips('自产的产品具有唯一SN码，不能修改库存量', 2, 'alert.gif');
		     return;
        }
        
        parent.addTab('同批次手动入库','productdepot/updateDepotamountInit?rid='+Math.random() + '&productidentfy=' +productidentfy);
		
	}
	
	/**
	* 修改产品信息
	*/
	function updateProductstatus(){
		
		var selected = $('#productdepotDG').datagrid("getSelected");
        if(selected == null){
   	         $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var productidentfy = selected.productidentfy;
        
        parent.addTab('修改产品信息','productdepot/updateProductstatusInit?rid='+Math.random() + '&productidentfy=' +productidentfy);
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
	    	$(this).combobox("setValue", '${productdepot.typecode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
	    	queryProductdepot();
        } 
	}); 
	
	function printBarcode() {
		var selected = $('#productdepotDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var productidentfy = selected.productidentfy;
		
        //开始打印
	    LODOP = getLodop();
	    LODOP.SET_PRINT_PAGESIZE (0,40+"mm", 30+"mm","");  
	    
	    //LODOP.ADD_PRINT_BARCODE(25,20,150,70,"128Auto",18050309010008);
        
	    LODOP.ADD_PRINT_BARCODE(25,20,120,70,"128Auto",productidentfy);
	    
	    LODOP.PREVIEW();
	    //LODOP.PRINT();
	    
	 }
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryProductdepot();
	    }  
	});
	
	
	//点击表格某一条
	function onClickRow(index, data){
		var productidentfy = data.productidentfy;
		
		$('#userproductList').datagrid('reload',{
			productidentfy:productidentfy,
		});	
	} 
	
	 //查询汇总明细
	function initUserproductList(){
	    $('#userproductList').datagrid({
	    	title: '产品销售信息',
	         queryParams: userproductListparamsJson(),
	         url:'userproduct/findListJson',
	         pagination: true,
	         singleSelect: true,
	         scrollbar:true,
	         pageSize: 10,
	         pageList: [10,30,50], 
	         fitColumns:true,
	         checkOnSelect: true,// 如果为true，当用户点击行的时候该复选框就会被选中或取消选中。 如果为false，当用户仅在点击该复选框的时候才会呗选中或取消。
	         idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
	         loadMsg:'正在加载数据......',
	         //toolbar:'#tools',
	          columns: [[
					{ field: 'usercode', title: '客户编号',width:100,align:"center",},
					{ field: 'username', title: '客户姓名',width:150,align:"center",},
					{ field: 'phone', title: '客户电话',width:80,align:"center"},
					{ field: 'ordercode', title: '订单编号',width:80,align:"center"},
					{ field: 'productversion', title: '客户家产品软件版本',width:80,align:"center"},
					{ field: 'address', title: '安装地址',width:350,align:"center",
				  	    formatter: function (value) {
				              return "<span title='" + value + "'>" + value + "</span>";
				          },
				  },
	         ]]
	     });
	}
 
    //将表单数据转为json
	function userproductListparamsJson(){
    	//获取选中的智能卡
		var selected = $('#productdepotDG').datagrid('getSelected');
    	var productidentfy = selected.productidentfy;
    	if(productidentfy == undefined){
    		productidentfy = -100;
    	}
		var json = {
				productidentfy:productidentfy,
		};
		return json;
	}
	
	 //产品维修
	function saveProductrepair() {
		
		var selected = $('#productdepotDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
 		     return;
        }
        //获取需要修改项的ID
        var productidentfy = selected.productidentfy;
		
        parent.addTab('产品维修','productdepot/saveProductrepairInit?rid='+ Math.random()+'&productidentfy='+productidentfy);
	 }
</script>
</body>
</html>