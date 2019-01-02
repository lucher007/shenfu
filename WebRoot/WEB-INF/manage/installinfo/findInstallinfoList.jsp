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
		    		<td align="right" >生产批次号：</td>
		    		<td >
		    			<input type="text"  class="inp w200" name="producecode" id="producecode" value="${installinfo.producecode }">
		    		</td>
		    		<td align="right" >材料编号：</td>
		    		<td >
		    			<input type="text"  class="inp w200" name="materialcode" id="materialcode" value="${installinfo.materialcode }">
		    		</td>
		    		<td align="right" >材料名称：</td>
		    		<td >
		    			<input type="text"  class="inp w200" name="materialname" id="materialname" value="${installinfo.materialname }">
		    		</td>
		    		<td align="center" colspan="2">
		    			<a href="javascript:queryInstallinfo();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
   <div id = "installinfoDG" style="width:auto; height: auto;"></div>
   <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addInstallinfo();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:lookInstallinfo();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看组装详情</a>
			<a href="javascript:updateInstallinfo();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
		</div>
	</div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initInstallinfoDatagrid(){
         $('#installinfoDG').datagrid({
              title: '列表信息',
              queryParams: paramsJson(),
              url:'installinfo/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              //fitColumns:true,
              toolbar:'#tools',
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
			  frozenColumns:[[
                              { field: 'producecode', title: '生产批次号',width:150,align:"center",sortable:"true"},
                              { field: 'materialcode', title: '材料编号',width:150,align:"center",sortable:"true"},
                              { field: 'materialname', title: '材料名称',width:150,align:"center",},
                         ]],
               columns: [[
                              { field: 'amount', title: '生产数量',width:150,align:"center",},
                              { field: 'materialversion', title: '材料版本',width:150,align:"center",},
							  { field: 'installinformation', title: '组装信息',width:550,align:"center",
							  	    formatter: function (value) {
							              return "<span title='" + value + "'>" + value + "</span>";
							          },
							  },
							  { field: 'producername', title: '生产负责人',width:250,align:"center",},
							  { field: 'addtime', title: '添加时间',width:250,align:"center",sortable:"true"},
              ]]
          });
	}
	
	//将表单数据转为json
	function paramsJson(){
	 	var json = {
	 			producecode:$("#producecode").val(),
	 			materialcode:$("#materialcode").val(),
	 			materialname:$("#materialname").val(),
	 	};
	 	return json;
	 }
	
	function queryInstallinfo() {
		$('#installinfoDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	
	function lookInstallinfo() {
		var selected = $('#installinfoDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        id = selected.id;
		
        parent.addTab('查看生产详情','installinfo/lookInit?rid='+ Math.random()+'&id='+id);
        
	 }
	
	function updateInstallinfo() {
		
		var selected = $('#installinfoDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        
        //获取需要修改项的ID
        var id = selected.id;
		
        parent.addTab('修改生产详情','installinfo/updateInit?rid='+ Math.random()+'&id='+id);
        
	 }
	 
	 function addInstallinfo() {
		 
		 parent.addTab('添加生产组装','installinfo/addInit?rid='+Math.random());
		 
	 }
     
	function closeDialog(){
		dialog.close();
		$('#installinfoDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteInstallinfo(){
		
		var selected = $('#installinfoDG').datagrid("getSelected");
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
			var url="installinfo/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#installinfoDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化订单列表
	   initInstallinfoDatagrid();
		
       var returninfo = '${installinfo.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryInstallinfo();
	    }  
	});
</script>
</body>
</html>