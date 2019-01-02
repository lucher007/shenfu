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
    <div class="easyui-panel" title="查询条件" style="width:auto;">
         <form action="" method="post" id="searchForm" name="searchForm">
         <input type="hidden"  name="ids" id="ids">
            <table style="width: 100%">
		    	<tr>
		    		<td align="right">驻点编号：</td>
		    		<td>
		    			<input type="text"  class="inp" name="stationcode" id="stationcode" value="${cellstation.stationcode }">
		    		</td>
		    	    <td align="right">小区名称：</td>
		    		<td>
		    			<input type="text"  class="inp" name="cellname" id="cellname" value="${cellstation.cellname }">
		    		</td>
		    		<td align="right">小区地址：</td>
		    		<td>
		    			<input type="text"  class="inp" name="address" id="address" value="${cellstation.address }">
		    		</td>
		    		<td align="right">
						<a href="javascript:queryCellstation();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
				</tr>
   		    </table>
   		</form>
    </div>
    <div id = "cellstationDG" style="width:auto; height: auto;"></div>
	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addCellstationemployee('talker');" class="easyui-linkbutton" iconCls="icon-add"  plain="true">分配驻点人员</a>
			<!-- 
			<a href="javascript:addCellstationemployee('visitor');" class="easyui-linkbutton" iconCls="icon-add"  plain="true">分配测量人员</a>
			 -->
			<a href="javascript:deleteCellstation();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
		</div>
	</div>
	
	<div id="cellstationemployeelist" style="width:auto; height: auto;"></div>
	<div id="cellstationemployeetools">
		<div style="margin-bottom:5px">
			<a href="javascript:deleteCellstationemployee();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
		</div>
	</div>
	 
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initCellstationDatagrid(){
         $('#cellstationDG').datagrid({
              title: '扫楼发布列表信息',
              queryParams: paramsJson(),
              url:'cellstation/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 5,
              pageList: [5,10,30], 
              fitColumns:true,
              idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              toolbar:'#tools',
              onClickRow: onClickRow,
              columns: [[
                  { field: 'stationcode', title: '驻点编号',width:100,align:"center",},
                  { field: 'cellcode', title: '小区编号',width:100,align:"center",},
                  { field: 'cellname', title: '小区名称',width:100,align:"center",
                	  formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                },
                  },
                  { field: 'address', title: '小区地址',width:200,align:"center",
                	  formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                },
                  },
                  { field: 'totalhouse', title: '总户数',width:80,align:"center",},
                  { field: 'totalmoney', title: '扫楼总价(元)',width:80,align:"center",},
                  { field: 'acceptercode', title: '接收人编号',width:100,align:"center",},
                  { field: 'acceptername', title: '接收人姓名',width:100,align:"center",},
                  { field: 'starttime', title: '开始时间',width:80,align:"center",},
                  { field: 'endtime', title: '结束时间',width:80,align:"center",},
                  { field: 'talkernumber', title: '申请讲解人数',width:80,align:"center",},
                  { field: 'visitornumber', title: '申请测量人数',width:80,align:"center",},
                  { field: 'addtime', title: '申请时间',width:110,align:"center",},
              ]],
             onLoadSuccess:function(data){  
 	        	 //默认选择第一条
 	        	 $('#cellstationDG').datagrid("selectRow", 0);
 	        	 //加载第一条驻点的工作人员信息
 	        	 initCellstationemployeelist();
 	         },
          });
	}

	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		stationcode:$.trim($("#stationcode").val()),
	 		cellname:$.trim($("#cellname").val()),
	 		address:$.trim($("#address").val()),
	 	};
	 	return json;
	 }
	
	function queryCellstation() {
		$('#cellstationDG').datagrid('reload',paramsJson());
	}
	
	//点击表格某一条
	function onClickRow(index, data){
		var stationcode = data.stationcode;
		$('#cellstationemployeelist').datagrid('reload',{
			stationcode:stationcode,
		});	
	} 
	
	function initCellstationemployeelist(){
	    $('#cellstationemployeelist').datagrid({
	         title: '驻点工作人员信息',
	         url:'cellstationemployee/findListJson',
	         queryParams: cellstationemployeeparamsJson(),
	         pageSize:5,
             singleSelect: true,
             pageList: [5,25,50], 
	         scrollbar:true,
	         pagination: true,
	         fitColumns:true,
	         rownumbers:true,
	         toolbar:'#cellstationemployeetools',
	         idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
	         loadMsg:'loading...',
	         columns: [[
	             { field: 'employeecode', title: '员工编号',align:"center",width:100},
	        	 { field: 'employeename', title: '员工姓名',align:"center",width:100,},
	        	 { field: 'phone', title: '联系电话',align:"center",width:100,},
	             { field: 'employeetypename', title: '员工类型',align:"center",width:100,}
	         ]],
	     });
	}
 
    //将表单数据转为json
	function cellstationemployeeparamsJson(){
    	//获取选中的智能卡
		var selected = $('#cellstationDG').datagrid('getSelected');
    	var stationcode = selected.stationcode;
		var json = {
				stationcode:stationcode,
		};
		return json;
	}
	
	function addCellstationemployee(employeetype) {
		
		var selected = $('#cellstationDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
        
        //获取需要修改项的ID
        var stationcode = selected.stationcode;
		
	    dialog = $.dialog({
		    title: '驻点人员添加',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:cellstationemployee/addCellstationemployeeInit?rid='+Math.random()+'&stationcode='+stationcode+'&employeetype='+employeetype
		});
	 }
    
	function closeDialog(){
		dialog.close();
		$('#cellstationDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteCellstation(){
		
		var selected = $('#cellstationDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要删除的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="cellstation/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#cellstationDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	/**
	*删除
	*/
	function deleteCellstationemployee(){
		
		var selected = $('#cellstationemployeelist').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要删除的驻点工作人员', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="cellstationemployee/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#cellstationemployeelist').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化小区列表
	   initCellstationDatagrid();
		
       var returninfo = '${cellstation.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	/**
	*批量删除
	*/
	function deleteBatch(){
		
		getChecked();

		if ($("#ids").val() == "") {
			$.dialog.tips(
					'请选择需要删除的小区', 1,
					'alert.gif');
			return;
		}
		
		$.dialog.confirm('确认是否批量删除？', function(){ 
			var data = {
					     ids: $("#ids").val(),
					   };
			var url="cell/deleteBatch?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#cellstationDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	
	function getChecked(){
		var ids = [];
		var rows = $('#cellstationDG').datagrid('getChecked');
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].id);
		}
		$("#ids").val(ids.join(','));
	}
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryCellstation();
	    }  
	});
</script>
</body>
</html>