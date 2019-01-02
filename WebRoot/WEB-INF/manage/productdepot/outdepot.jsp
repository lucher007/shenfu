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
		    		<td align="right" width="120px">产品名称：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="productname" id="productname" value="${productdepot.productname }">
		    		</td>
		    		<td align="right" width="120px">产品识别号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="productidentfy" id="productidentfy" value="${productdepot.productidentfy }">
		    		</td>
					<td align="right" width="130">
        				<input type="button" class="btn-sch" value="查询" onclick="queryProductdepot();"/>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "productdepotDG" style="width:auto; height: 400px">
    	</div>
	</div>
    </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
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
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              rowStyler: function(index,row){
							if (row.productstatus == '1'){
								return 'color:green;font-weight:bold;';
							}else if (row.productstatus == '2'){
								return 'color:blue;font-weight:bold;';
							}else if (row.productstatus == '3'){
								return 'color:red;font-weight:bold;';
							}
						},
              columns: [[
                  { field: 'productname', title: '产品名称',width:100,align:"center"},
                  { field: 'productidentfy', title: '产品识别号',width:100,align:"center"},
                  { field: 'productstatus', title: '产品状态',width:100,align:"center",formatter:productstatusformatter},
                  { field: 'depotamount', title: '库存量',width:100,align:"center"},
                  { field: 'id', title: '操作' ,width:100,align:"center",formatter:operateformatter},
              ]]
          });
	}
	
	function producerformatter(value,row,index){
	 	return row.producer.operatorname;
	}
	
	function productstatusformatter(value,row,index){
		if(value == '0'){
		  	return "库存";
		}else if(value == '1'){
			return "已出库";
		}else if(value == '2'){
			return "已出售";
		}else if(value == '3'){
			return "已损坏";
		}else if(value == '4'){
			return "维修中";
		}
	}
	
	function productunitformatter(value,row,index){
	 	return row.product.productunit;
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		productname:$("#productname").val(),
	 		productidentfy:$("#productidentfy").val(),
	 	};
	 	
	 	return json;
	 }
	
	function operateformatter(value,row,index){
	    var outdepotContent = "";
	    if(row.productstatus == '0'){//库存
	    	outdepotContent = '<a class="btn-del" href="javascript:addOutdepot(\''+ row.productidentfy +'\');">出库</a>';
	    }
	 	return  outdepotContent;
        	  
	 }
	
	/**
	*出库
	*/
	function addOutdepot(productidentfy){
		dialog = $.dialog({
		    title: '产品出库',
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
		    content: 'url:productdepot/addOutdepotInit?rid='+Math.random() + '&productidentfy=' +productidentfy
		});
		
	}
	
	function closeDialog(){
		dialog.close();
		$('#productdepotDG').datagrid('reload');// 刷新当前页面
	}
	
	
	function queryProductdepot() {
		initProductdepotDatagrid();
	}
	
	$(function () {
	   
	   //初始化产品列表
	   initProductdepotDatagrid();
		
       var returninfo = '${productdepot.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>