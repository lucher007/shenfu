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
		    		<td align="right">元器件编号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="componentcode" id="componentcode" value="${component.componentcode }">
		    		</td>
		    		<td align="right">元器件封装：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="componentname" id="componentname" value="${component.componentname }">
		    		</td>
		    		<td align="right">元器件规格：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="componentmodel" id="componentmodel" value="${component.componentmodel }">
		    		</td>
					<td align="right">
						<a href="javascript:queryComponent();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "componentDG" style="width:auto; height: 450px"></div>
	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addComponent();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateComponent();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteComponent();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
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
	
	function initComponentDatagrid(){
         $('#componentDG').datagrid({
              title: '元器件列表信息',
              queryParams: paramsJson(),
              url:'component/findListJson',
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
			      { field: 'componentcode', title: '元器件编号',width:100,align:"center",sortable:"true"},
                  { field: 'componentname', title: '元器件封装',width:100,align:"center"},
                  { field: 'componentmodel', title: '元器件规格',width:100,align:"center"},
                  { field: 'componentunit', title: '元器件计算单位',width:100,align:"center" },
                  { field: 'depotamount', title: '报警量',width:100,align:"center" },
              ]]
          });
	}
    
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		componentcode:$("#componentcode").val(),
	 		componentname:$("#componentname").val(),
	 		componentmodel:$("#componentmodel").val(),
	 	};
	 	
	 	return json;
	 }
	
	function queryComponent() {
	 	$('#componentDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateComponent() {
		
		var selected = $('#componentDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
		
	    dialog = $.dialog({
		    title: '元器件修改',
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
		    content: 'url:component/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addComponent() {
	    dialog = $.dialog({
		    title: '元器件添加',
		    lock: true,
		    width: 1100,
		    height: 600,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:component/addInit?rid='+Math.random()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#componentDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteComponent(){
		
		var selected = $('#componentDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="component/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#componentDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化元器件列表
	   initComponentDatagrid();
		
       var returninfo = '${component.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
	
	function printBarcode() {
		var selected = $('#componentDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var componentcode = selected.componentcode;
		
        //开始打印
	    LODOP = getLodop();
	    LODOP.SET_PRINT_PAGESIZE (0,40+"mm", 30+"mm","");  
	    
	    //LODOP.ADD_PRINT_BARCODE(25,20,150,70,"128Auto",18050309010008);
        
	    LODOP.ADD_PRINT_BARCODE(25,20,120,70,"128Auto",componentcode);
	    
	    LODOP.PREVIEW();
	    //LODOP.PRINT();
	    
	 }
	
</script>
</body>
</html>