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
		    		<td height="30" width="15%" align="right">材料名称：</td>
					<td width="25%">
						<input id="materialcode" name="materialcode">
					</td>
		    		<td align="right" width="120px">库存量状态：</td>
		    		<td>
		    			<select id="depotstatus" name="depotstatus" class="select" onchange="queryMaterialdepot();">
		    				<option value="1" <c:if test="${materialdepot.depotstatus == '1' }">selected</c:if>>有库存</option>
		    			    <option value=""  <c:if test="${materialdepot.depotstatus == '' }">selected</c:if>>请选择</option>
		    			    <option value="0" <c:if test="${materialdepot.depotstatus == '0' }">selected</c:if>>无库存</option>
			             </select>
		    		</td>
		    		<!-- 
		    		<td align="right" width="120px">报警量显示：</td>
		    		<td>
		    			<select id="depotamountlevel" name="depotamountlevel" class="select" onchange="queryMaterialdepot();">
		    			    <option value=""  <c:if test="${materialdepot.depotamountlevel == '' }">selected</c:if>>请选择</option>
		    			    <option value="0" <c:if test="${materialdepot.depotamountlevel == '0' }">selected</c:if>>库存量正常(白色显示)</option>
			                <option value="1" <c:if test="${materialdepot.depotamountlevel == '1' }">selected</c:if>>库存量不足(蓝色显示)</option>
			                <option value="2" <c:if test="${materialdepot.depotamountlevel == '2' }">selected</c:if>>无库存量(红色显示)</option>
			             </select>
		    		</td>
		    		 -->
					<td align="right" width="130">
						<a href="javascript:queryMaterialdepot();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
		    	<tr>
		    		<td height="30" width="15%" align="right">材料批次号：</td>
					<td width="25%">
						<input id="batchno" name="batchno" class="inp w200" value="${materialdepot.batchno }">
					</td>
					<td height="30" width="15%" align="right">材料SN码：</td>
					<td width="25%">
						<input id="materialidentfy" name="materialidentfy" class="inp w200" value="${materialdepot.materialidentfy }">
					</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
   	<div id = "materialdepotDG" style="width:100%; height: auto;"></div>
    <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addDepotinput();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">新批次入库</a>
			<a href="javascript:addDepotout();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">材料手动出库</a>
			<a href="javascript:updateDepotamount();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">同批次手动入库</a>
			<a href="javascript:printBarcode();" class="easyui-linkbutton" iconCls="icon-print"  plain="true">打印条码</a>
			<a href="javascript:updateMaterialstatus();" class="easyui-linkbutton" iconCls="icon-print"  plain="true">修改材料信息</a>
			<!--
			<a href="javascript:deleteDepot();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
			  -->
		</div>
	</div>
	<!-- 材料检测信息 -->
	<div id="materialcheckList" style="width:auto; height: auto;"></div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="<%=basePath%>js/lodop/LodopFuncs.js"></script>
<script type="text/javascript">
	
	function initMaterialdepotDatagrid(){
         $('#materialdepotDG').datagrid({
              title: '材料库存信息',
              queryParams: paramsJson(),
              url:'materialdepot/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              toolbar:'#tools',
              onClickRow: onClickRow,
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
				  { field: 'materialcode', title: '材料编号',width:100,align:"center",sortable:"true",sortable:"true",},
                  { field: 'materialname', title: '材料名称',width:100,align:"center"},
                  { field: 'batchno', title: '批次号',width:100,align:"center",sortable:"true"},
                  { field: 'materialidentfy', title: 'SN码',width:120,align:"center",sortable:"true"},
                  { field: 'depotstatusname', title: '库存状态',width:100,align:"center"},
                  { field: 'materialstatusname', title: '材料状态',width:100,align:"center"},
                  { field: 'depotamount', title: '库存量',width:100,align:"center"},
                  { field: 'materialsourcename', title: '材料来源',width:100,align:"center"},
                  { field: 'depotrackcode', title: '存放货柜位置',width:100,align:"center"},
                  { field: 'inoutername', title: '入库负责人',width:100,align:"center"},
                  { field: 'suppliername', title: '供应商简称',width:100,align:"center"},
                  { field: 'addtime', title: '入库时间',width:100,align:"center",sortable:"true"},
              ]],
			onLoadSuccess:function(data){  
 	        	 //默认选择第一条
 	        	 $('#materialdepotDG').datagrid("selectRow", 0);
 	        	 //加载第一条材料的检测信息
 	        	 initMaterialcheckList();
 	         },
          });
	}
	
	function materialunitformatter(value,row,index){
	 	return row.material.materialunit;
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		materialcode:$('#materialcode').combobox('getValue'),
	 		depotstatus:$("#depotstatus").val(),
	 		batchno:$("#batchno").val(),
	 		materialidentfy:$("#materialidentfy").val(),
	 		//depotamountlevel:$("#depotamountlevel").val(),
	 	};
	 	
	 	return json;
	 }
	
	function queryMaterialdepot() {
		$('#materialdepotDG').datagrid('reload',paramsJson());
	}
	
	
	function addDepotinput() {
	    dialog = $.dialog({
		    title: '材料手动入库',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:materialdepot/indepot?rid='+Math.random()
		});
	 }
	
	/**
	*出库
	*/
	function addDepotout(){
		
		var selected = $('#materialdepotDG').datagrid("getSelected");
        if(selected == null){
   	         $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
		
        //库存状态
        var depotstatus = selected.depotstatus;
        if(depotstatus == '0'){
        	$.dialog.tips('该材料没有库存，不能手动出库', 2, 'alert.gif');
		     return;
        }
        
		dialog = $.dialog({
		    title: '材料出库',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:materialdepot/outdepot?rid='+Math.random() + '&id=' +id
		});
	}
	
	/**
	*手动修改库存量
	*/
	function updateDepotamount(){
		
		var selected = $('#materialdepotDG').datagrid("getSelected");
        if(selected == null){
   	         $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
		
        //库存状态
        //var depotstatus = selected.depotstatus;
        //if(depotstatus == '0'){
        //	$.dialog.tips('该材料没有库存，不能手动出库', 2, 'alert.gif');
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
		    content: 'url:materialdepot/updateDepotamountInit?rid='+Math.random() + '&id=' +id
		});
	}
	
	function closeDialog(){
		dialog.close();
		$('#materialdepotDG').datagrid('reload');// 刷新当前页面
	}
	
	$(function () {
	   
	   //初始化材料列表
	   initMaterialdepotDatagrid();
		
       var returninfo = '${materialdepot.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	//加载材料列表
	$('#materialcode').combobox({    
	    url:'material/getMaterialComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${materialdepot.materialcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
	    	queryMaterialdepot(); 
        } 
	}); 
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryMaterialdepot();
	    }  
	});
	
	function printBarcode() {
		var selected = $('#materialdepotDG').datagrid("getSelected");
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
	
	//点击表格某一条
	function onClickRow(index, data){
		var materialidentfy = data.materialidentfy;
		if(materialidentfy == null){
    		materialidentfy = -100;
    	}
		
		$('#materialcheckList').datagrid('reload',{
			materialidentfy:materialidentfy,
		});	
	} 
	
	 //查询材料检测
	function initMaterialcheckList(){
	    $('#materialcheckList').datagrid({
	    	title: '材料检测信息',
	         queryParams: materialcheckListparamsJson(),
	         url:'materialcheck/findListJson',
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
					{ field: 'batchno', title: '批次号',width:150,align:"center",},
					{ field: 'hightempname', title: '高温老练',width:100,align:"center",},
					{ field: 'currentvoltagename', title: '电流电压是否正常',width:150,align:"center",},
					{ field: 'displayscreenname', title: '显示屏是否正常点亮',width:150,align:"center"},
					{ field: 'touchscreenname', title: '触屏是否灵敏',width:150,align:"center"},
					{ field: 'messageconnectname', title: '通讯是否连接',width:150,align:"center"},
					{ field: 'openclosedoorname', title: '开关门是否顺畅',width:150,align:"center"},
					{ field: 'keystrokesoundname', title: '按键声音是否正常',width:150,align:"center"},
					{ field: 'fingerprintname', title: '内外指纹是否灵敏',width:150,align:"center"},
					{ field: 'checktime', title: '检验时间',width:150,align:"center"},
					{ field: 'checkinfo', title: '检验备注',width:350,align:"center",
				  	    formatter: function (value) {
				              return "<span title='" + value + "'>" + value + "</span>";
				          },
				    },
	         ]]
	     });
	}
 
    //将表单数据转为json
	function materialcheckListparamsJson(){
    	//获取选中的智能卡
		var selected = $('#materialdepotDG').datagrid('getSelected');
    	var materialidentfy = selected.materialidentfy;
    	if(materialidentfy == undefined){
    		materialidentfy = -100;
    	}
		var json = {
				materialidentfy:materialidentfy,
		};
		return json;
	}
    
	/**
	* 修改材料信息
	*/
	function updateMaterialstatus(){
		
		var selected = $('#materialdepotDG').datagrid("getSelected");
        if(selected == null){
   	         $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
        
        parent.addTab('修改材料信息','materialdepot/updateMaterialstatusInit?rid='+Math.random() + '&id=' +id);
	}
	
</script>
</body>
</html>