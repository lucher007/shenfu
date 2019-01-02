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
		    		<td height="30" width="15%" align="right">元器件名称：</td>
					<td width="25%">
						<input id="componentcode" name="componentcode"> <span class="red">*</span>
					</td>
		    		<td align="right" width="120px">库存量状态：</td>
		    		<td>
		    			<select id="depotstatus" name="depotstatus" class="select" onchange="queryComponentdepot();">
		    				<option value="1" <c:if test="${componentdepot.depotstatus == '1' }">selected</c:if>>有库存</option>
		    			    <option value=""  <c:if test="${componentdepot.depotstatus == '' }">selected</c:if>>请选择</option>
		    			    <option value="0" <c:if test="${componentdepot.depotstatus == '0' }">selected</c:if>>无库存</option>
			             </select>
		    		</td>
		    		<!-- 
		    		<td align="right" width="120px">报警量显示：</td>
		    		<td>
		    			<select id="depotamountlevel" name="depotamountlevel" class="select" onchange="queryComponentdepot();">
		    			    <option value=""  <c:if test="${componentdepot.depotamountlevel == '' }">selected</c:if>>请选择</option>
		    			    <option value="0" <c:if test="${componentdepot.depotamountlevel == '0' }">selected</c:if>>库存量正常(白色显示)</option>
			                <option value="1" <c:if test="${componentdepot.depotamountlevel == '1' }">selected</c:if>>库存量不足(蓝色显示)</option>
			                <option value="2" <c:if test="${componentdepot.depotamountlevel == '2' }">selected</c:if>>无库存量(红色显示)</option>
			             </select>
		    		</td>
		    		 -->
					<td align="right" width="130">
						<a href="javascript:queryComponentdepot();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
   	<div id = "componentdepotDG" style="width:auto; height: 600px;"></div>
    <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addDepotinput();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">新批次入库</a>
			<a href="javascript:addDepotout();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">元器件手动出库</a>
			<a href="javascript:updateDepotamount();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">同批次手动入库</a>
			<a href="javascript:printBarcode();" class="easyui-linkbutton" iconCls="icon-print"  plain="true">打印条码</a>
			<!--
			<a href="javascript:deleteDepot();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
			  -->
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
	
	function initComponentdepotDatagrid(){
         $('#componentdepotDG').datagrid({
              title: '元器件库存信息',
              queryParams: paramsJson(),
              url:'componentdepot/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              toolbar:'#tools',
              rowStyler: function(index,row){
            	    if (row.depotamount == 0){
            	    	return 'color:black;font-weight:bold;';
					}else if (row.depotamount > 0 && row.depotamount <= row.alarmdepotamount){
						return 'color:red;font-weight:bold;';
					}else{
						return 'color:green;font-weight:bold;';
					}
				},
              columns: [[
				  { field: 'componentcode', title: '元器件编号',width:100,align:"center",sortable:"true",sortable:"true",},
                  { field: 'componentname', title: '元器件封装',width:100,align:"center"},
                  { field: 'componentmodel', title: '元器件规格',width:100,align:"center"},
                  { field: 'batchno', title: '批次号',width:100,align:"center",sortable:"true"},
                  { field: 'depotstatusname', title: '库存状态',width:100,align:"center"},
                  { field: 'depotamount', title: '库存量',width:100,align:"center"},
                  { field: 'depotrackcode', title: '存放货柜位置',width:100,align:"center"},
                  { field: 'inoutername', title: '入库负责人',width:100,align:"center"},
                  { field: 'suppliername', title: '供应商简称',width:100,align:"center"},
                  { field: 'addtime', title: '入库时间',width:100,align:"center",sortable:"true"},
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		componentcode:$('#componentcode').combobox('getValue'),
	 		depotstatus:$("#depotstatus").val(),
	 		//depotamountlevel:$("#depotamountlevel").val(),
	 	};
	 	
	 	return json;
	 }
	
	function queryComponentdepot() {
		$('#componentdepotDG').datagrid('reload',paramsJson());
	}
	
	
	function addDepotinput() {
	    dialog = $.dialog({
		    title: '元器件手动入库',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:componentdepot/indepot?rid='+Math.random()
		});
	 }
	
	/**
	*出库
	*/
	function addDepotout(){
		
		var selected = $('#componentdepotDG').datagrid("getSelected");
        if(selected == null){
   	         $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var batchno = selected.batchno;
		
        //库存状态
        var depotstatus = selected.depotstatus;
        if(depotstatus == '0'){
        	$.dialog.tips('该元器件没有库存，不能手动出库', 2, 'alert.gif');
		     return;
        }
        
		dialog = $.dialog({
		    title: '元器件出库',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:componentdepot/outdepot?rid='+Math.random() + '&batchno=' +batchno
		});
	}
	
	/**
	*手动修改库存量
	*/
	function updateDepotamount(){
		
		var selected = $('#componentdepotDG').datagrid("getSelected");
        if(selected == null){
   	         $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var batchno = selected.batchno;
		
        //库存状态
        //var depotstatus = selected.depotstatus;
        //if(depotstatus == '0'){
        //	$.dialog.tips('该元器件没有库存，不能手动出库', 2, 'alert.gif');
		//     return;
        //}
        
		dialog = $.dialog({
		    title: '同批次手动入库',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:componentdepot/updateDepotamountInit?rid='+Math.random() + '&batchno=' +batchno
		});
	}
	
	function closeDialog(){
		dialog.close();
		$('#componentdepotDG').datagrid('reload');// 刷新当前页面
	}
	
	$(function () {
	   
	   //初始化元器件列表
	   initComponentdepotDatagrid();
		
       var returninfo = '${componentdepot.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	//加载元器件列表
	$('#componentcode').combobox({    
	    url:'component/getComponentComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${componentdepot.componentcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
	    	queryComponentdepot(); 
        } 
	}); 
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryComponentdepot();
	    }  
	});
	
	function printBarcode() {
		var selected = $('#componentdepotDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var batchno = selected.batchno;
		
        //开始打印
	    LODOP = getLodop();
	    LODOP.SET_PRINT_PAGESIZE (0,40+"mm", 30+"mm","");  
	    
	    //LODOP.ADD_PRINT_BARCODE(25,20,150,70,"128Auto",18050309010008);
        
	    LODOP.ADD_PRINT_BARCODE(25,20,120,70,"128Auto",batchno);
	    
	    LODOP.PREVIEW();
	    //LODOP.PRINT();
	    
	 }
	
</script>
</body>
</html>