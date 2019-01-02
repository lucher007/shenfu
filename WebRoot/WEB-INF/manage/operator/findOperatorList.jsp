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
		    		<td align="right" width="120px">登录名称：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="loginname" id="loginname" value="${operator.loginname }">
		    		</td>
					<td align="right">状态：</td>
        			<td>
        				<select id="status" name="status" class="select" onchange="queryOperator();">
			                   <option value="1" <c:if test="${operator.status == '1' }">selected</c:if>>有效</option>
			                   <option value="0" <c:if test="${operator.status == '0' }">selected</c:if>>无效</option>
			             </select>
        			</td>
        			<td align="right">
        				<a href="javascript:queryOperator();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "operatorDG" style="width:auto; height: 400px"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addOperator();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateOperator();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteOperator();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
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
	
	function initOperatorDatagrid(){
         $('#operatorDG').datagrid({
              title: '操作员列表信息',
              queryParams: paramsJson(),
              url:'operator/findListJson',
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
                  { field: 'loginname', title: '登录名称',width:100,align:"center"},
                  { field: 'employeename', title: '员工姓名',width:100,align:"center",},
                  { field: 'employeecode', title: '员工编号',width:100,align:"center",},
                  { field: 'phone', title: '电话',width:100,align:"center",},
                  { field: 'statusname', title: '状态',width:100,align:"center",},
                  { field: 'addtime', title: '添加时间',width:100,align:"center",},
                  { field: 'operatortypename', title: '操作员类型',width:100,align:"center",},
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		loginname:$("#loginname").val(),
	 		status:$("#status").val(),
	 	};
	 	return json;
	 }
	
	function queryOperator() {
		$('#operatorDG').datagrid('reload',{
				loginname:$("#loginname").val(),
	 			status:$("#status").val(),
			});	
	}
	
	var dialog;
	function updateOperator() {
		
		var selected = $('#operatorDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
		
        //获取销售员的ID
        id = selected.id;
		
	    dialog = $.dialog({
		    title: '操作员修改',
		    lock: true,
		    width: 1100,
		    height: 560,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:operator/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addOperator() {	
	    dialog = $.dialog({
		    title: '操作员添加',
		    lock: true,
		    width: 1100,
		    height: 600,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:operator/addInit?rid='+Math.random()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#operatorDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteOperator(){
		
		var selected = $('#operatorDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
		
        //获取销售员的ID
       id = selected.id;
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="operator/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 2, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#operatorDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化操作员列表
	   initOperatorDatagrid();
		
       var returninfo = '${operator.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>