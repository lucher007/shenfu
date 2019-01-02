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
		    		<td align="right" width="120px">产品编码：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="productcode" id="productcode" value="${product.productcode }">
		    		</td>
		    		<td align="right" width="120px">产品名称：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="productname" id="productname" value="${product.productname }">
		    		</td>
		    		<td align="right" width="120px">产品类别编码：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="typecode" id="typecode" value="${product.typecode }">
		    		</td>
		    		<td align="right" width="120px">产品类别名称：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="typename" id="typename" value="${product.typename }">
		    		</td>
		    		<td align="right" width="130">
						<a href="javascript:queryProduct();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "productDG" style="width:auto; height: 450px; "></div>
    <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addProduct();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateProduct();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteProduct();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
			<a href="javascript:printBarcode();" class="easyui-linkbutton" iconCls="icon-print"  plain="true">打印条码</a>
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
	
	function initProductDatagrid(){
         $('#productDG').datagrid({
              title: '产品列表信息',
              queryParams: paramsJson(),
              url:'product/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              toolbar:'#tools',
              columns: [[
                  { field: 'productcode', title: '产品编码',width:100,align:"center",sortable:"true",},
                  { field: 'id', title: '条形码',width:100,align:"center",
                	  formatter:function (value,row,index) {
                		  var codevalue = row.productcode;
                		  var content = '<img style="vertical-align:middle;" src="<%=request.getContextPath()%>/BarCodeServlet?msg='+codevalue+'&barH=10&barXD=0.4&codeType=code128"/>';
                	      return  content;
			          },
                  },
                  { field: 'productname', title: '产品名称',width:100,align:"center"},
                  { field: 'typename', title: '类别名称',width:100,align:"center"},
                  { field: 'typecode', title: '类别编号',width:100,align:"center"},
                  { field: 'price', title: '价格',width:100,align:"center"},
                  { field: 'productunit', title: '产品计算单位',width:100,align:"center"},
                  { field: 'productsource', title: '产品来源',width:100,align:"center",formatter:productsourceformatter},
              ]]
          });
	}
    
    function productsourceformatter(value,row,index){
	 	var id = row.id;
	 	if(value == '0'){
	 		return "自产";
	 	}else if(value == '1'){
	 		return "外购";
	 	}
	 }
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		productcode:$("#productcode").val(),
	 		productname:$("#productname").val(),
	 		typecode:$("#typecode").val(),
	 		typename:$("#typename").val(),
	 	};
	 	
	 	return json;
	 }
	
	function queryProduct() {
		$('#productDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateProduct() {
		var selected = $('#productDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        id = selected.id;
		
	    dialog = $.dialog({
		    title: '产品修改',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:product/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addProduct() {
	    dialog = $.dialog({
		    title: '产品添加',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:product/addInit?rid='+Math.random()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#productDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteProduct(id){
		
		var selected = $('#productDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        id = selected.id;
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="product/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 2, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#productDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化产品列表
	   initProductDatagrid();
		
       var returninfo = '${product.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	function printBarcode() {
		var selected = $('#productDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var productcode = selected.productcode;
		
        //开始打印
	    LODOP = getLodop();
	    LODOP.SET_PRINT_PAGESIZE (0,40+"mm", 30+"mm","");  
	    
	    //LODOP.ADD_PRINT_BARCODE(25,20,150,70,"128Auto",18050309010008);
        
	    LODOP.ADD_PRINT_BARCODE(25,20,120,70,"128Auto",productcode);
	    
	    LODOP.PREVIEW();
	    //LODOP.PRINT();
	    
	}
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryProduct();
	    }  
	});
	
	
	
</script>
</body>
</html>