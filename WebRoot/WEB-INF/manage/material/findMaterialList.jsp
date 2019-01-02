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
		    		<td align="right">材料编号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="materialcode" id="materialcode" value="${material.materialcode }">
		    		</td>
		    		<td align="right">材料名称：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="materialname" id="materialname" value="${material.materialname }">
		    		</td>
					<td align="right">
						<a href="javascript:queryMaterial();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "materialDG" style="width:auto; height: 450px"></div>
	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addMaterial();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateMaterial();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteMaterial();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
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
	
	function initMaterialDatagrid(){
         $('#materialDG').datagrid({
              title: '材料列表信息',
              queryParams: paramsJson(),
              url:'material/findListJson',
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
			      { field: 'materialcode', title: '材料编号',width:100,align:"center",sortable:"true"},
			      { field: 'materialcode1', title: '条形码',width:100,align:"center",formatter:barcodeformatter},
                  { field: 'materialname', title: '材料名称',width:100,align:"center"},
                  { field: 'materialunit', title: '材料计算单位',width:100,align:"center" },
                  { field: 'depotamount', title: '报警量',width:100,align:"center" },
                  { field: 'materialsource', title: '产品来源',width:100,align:"center",formatter:materialsourceformatter},
              ]]
          });
	}
    
	function materialsourceformatter(value,row,index){
	 	var id = row.id;
	 	if(value == '0'){
	 		return "自产";
	 	}else if(value == '1'){
	 		return "外购";
	 	}
	 }
	
	function barcodeformatter(value,row,index){
	 	var materialcode = row.materialcode;
	 	var content = '<img style="vertical-align:middle;" src="<%=request.getContextPath()%>/BarCodeServlet?msg='+materialcode+'&barH=10&barXD=0.4&codeType=code128"/>';
        return  content;
        	  
	 }
	
	 function materialformatter(value,row,index){
	 	var id = row.id;
	 	var updateContent = '<a class="btn-edit" href="javascript:updateMaterial('+ id +');">修改</a>';
	 	var deleteContent = '<a class="btn-del" href="javascript:deleteMaterial('+ id +');" >删除</a>';
        return  updateContent + deleteContent;
        	  
	 }
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		materialcode:$("#materialcode").val(),
	 		materialname:$("#materialname").val(),
	 	};
	 	
	 	return json;
	 }
	
	function queryMaterial() {
	 	$('#materialDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateMaterial() {
		
		var selected = $('#materialDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
		
	    dialog = $.dialog({
		    title: '材料修改',
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
		    content: 'url:material/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addMaterial() {	
	    dialog = $.dialog({
		    title: '材料添加',
		    lock: true,
		    width: 1100,
		    height: 600,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:material/addInit?rid='+Math.random()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#materialDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteMaterial(){
		
		var selected = $('#materialDG').datagrid("getSelected");
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
			var url="material/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#materialDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化材料列表
	   initMaterialDatagrid();
		
       var returninfo = '${material.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
	
	function printBarcode() {
		var selected = $('#materialDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var materialcode = selected.materialcode;
		
        //开始打印
	    LODOP = getLodop();
	    LODOP.SET_PRINT_PAGESIZE (0,40+"mm", 30+"mm","");  
	    
	    //LODOP.ADD_PRINT_BARCODE(25,20,150,70,"128Auto",18050309010008);
        
	    LODOP.ADD_PRINT_BARCODE(25,20,120,70,"128Auto",materialcode);
	    
	    LODOP.PREVIEW();
	    //LODOP.PRINT();
	    
	 }
	
</script>
</body>
</html>