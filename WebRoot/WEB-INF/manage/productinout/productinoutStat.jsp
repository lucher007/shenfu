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
		    		<td align="right" width="10%">产品编码：</td>
		    		<td>
		    			<input id="productcode" name="productcode" style="width: 200px;"> 
		    		</td>
		    		<td align="right" width="15%">类型：</td>
		    		<td>
		    			<select id="type" name="type" class="select" onchange="queryProductinout();">
		    			    <option value="" <c:if test="${productinout.type == '' }">selected</c:if>>请选择</option>
		    			 	<option value="0" <c:if test="${productinout.type == '0' }">selected</c:if>>入库</option>
			                <option value="1" <c:if test="${productinout.type == '1' }">selected</c:if>>出库</option>
			            </select>
		    		</td>
		    		<td height="26"  align="right" width="10%">查询开始时间：</td>
                    <td width="15%">
                    	<input type="text" id="begintime" name="begintime"   value="${productinout.begintime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150">
                    </td>
                    <td align="right" width="10%">查询结束时间：</td>
                    <td width="15%">
                    	<input type="text" id="endtime" name="endtime"  value="${productinout.endtime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'begintime\');}'});" class="Wdate inp w150">
					</td>
					<td align="right" width="130">
						<a href="javascript:queryProductinout();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "productinoutDG" style="width:100%; height: 500px"></div>
    <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:saveExportExcel();" class="easyui-linkbutton" iconCls="icon-reload"  plain="true">导出Excel</a>
			<!-- 
			<a href="javascript:printProductinout();" class="easyui-linkbutton" iconCls="icon-print"  plain="true">在线打印</a>
			 -->
		</div>
	</div>
	<div id="printInfo" style="display: none;"></div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="<%=basePath%>js/lodop/LodopFuncs.js"></script>
<script type="text/javascript">
	
	function initProductinoutDatagrid(){
         $('#productinoutDG').datagrid({
              title: '产品出入库信息',
              queryParams: paramsJson(),
              url:'productinout/productinoutStatJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              toolbar:'#tools',
              //onClickRow: onClickRow,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              rowStyler: function(index,row){
	         	    if (row.type == '0'){
	         	    	return 'color:blue;font-weight:bold;';
					}else if (row.type == '1'){
						return 'color:red;font-weight:bold;';
					}else{
						return '';
					}
			  },
              columns: [[
				  { field: 'productcode', title: '产品编码',width:100,align:"center",sortable:"true"},
                  { field: 'productname', title: '产品名称',width:100,align:"center"},
                  { field: 'typename', title: '出入库类型',width:100,align:"center",},
                  { field: 'totalinoutamount', title: '总数量',width:100,align:"center"},
                 
              ]]
          });
	}
    
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		productcode:$('#productcode').combogrid('getValue'),
	 		type:$("#type").val(),
	 		begintime:$("#begintime").val(),
	 		endtime:$("#endtime").val()
	 	};
	 	
	 	return json;
	 }
	
	function queryProductinout() {
		$('#productinoutDG').datagrid('reload',paramsJson());
	}
	
	$(function () {
	   
	   //初始化产品列表
	   initProductinoutDatagrid();
		
       var returninfo = '${productinout.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
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
	    	$(this).combobox("setValue", '${productinout.productcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
	    	queryProductinout();
        } 
	}); 
	
	function saveExportExcel(){
		$.dialog.confirm('确认是否导出？', function(){ 
			$("#searchForm").attr("action", "productinout/productinoutStatExportExcel");
			$("#searchForm").submit();
		}, function(){
			
			});
	}
	
	//点击表格某一条
	function onClickRow(index, data){
		var id = data.id;
		
		var data = {
		     id: id,
		     tag: 'printInfo'
		};
		var url = 'productinout/getPrintInfo .' + data.tag;
		$('#printInfo').load(url, data, function () {
			
		});
	} 
	
	function printProductinout() {
		var selected = $('#productinoutDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        //var id = selected.id;
        
        //开始打印
	    LODOP = getLodop();
	    //LODOP.SET_PRINT_PAGESIZE (0,40+"mm", 30+"mm","");  
	    
	    LODOP.PRINT_INIT("");	
	    
		//LODOP.ADD_PRINT_TABLE(80,55,600,"100%",document.getElementById("printInfo").innerHTML);	  
	    
	    LODOP.ADD_PRINT_BARCODE(25,20,150,70,"128Auto",18050309010008);
        
	    //LODOP.ADD_PRINT_BARCODE(25,20,120,70,"128Auto",batchno);
	    
	    LODOP.PREVIEW();
	    //LODOP.PRINT();
	    
	 }
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryProductinout();
	    }  
	});
	
</script>
</body>
</html>