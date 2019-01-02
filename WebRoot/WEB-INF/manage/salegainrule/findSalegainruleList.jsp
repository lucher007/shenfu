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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<title></title>
<link type="text/css" rel="stylesheet" href="style/user.css">
<link type="text/css" rel="stylesheet" href="js/plugin/poshytip/tip-yellowsimple/tip-yellowsimple.css">
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
					<td align="right">提成项编码：</td>
					<td >
						<input type="text"  class="inp w200" name="gaincode" id="gaincode" value="${salegainrule.gaincode }">
					</td>
					<td align="right" >提成项名称：</td>
					<td >
						<input type="text"  class="inp w200" name="gainname" id="gainname" value="${salegainrule.gainname }">
					</td>
					<td height="30" align="right">提成类型：</td>
					<td >
						   <select id="gaintype" name="gaintype" class="select" onchange="querySalegainrule();">
						   		<option value="" <c:if test="${salegainrule.gaintype == ''}">selected</c:if>>请选择</option>
								<option value="0" <c:if test="${salegainrule.gaintype == '0'}">selected</c:if>>销售提成</option>
								<option value="1" <c:if test="${salegainrule.gaintype == '1'}">selected</c:if>>安装提成</option>
							</select>
					</td>
					<td height="30" align="right">状态：</td>
					<td >
						   <select id="status" name="status" class="select" onchange="querySalegainrule();">
						   		<option value="1" <c:if test="${salegainrule.status == '1'}">selected</c:if>>有效</option>
						   		<option value="" <c:if test="${salegainrule.status == ''}">selected</c:if>>请选择</option>
								<option value="0" <c:if test="${salegainrule.status == '0'}">selected</c:if>>无效</option>
								
							</select>
					</td>
					<td align="right">
						<a href="javascript:querySalegainrule();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
					</td>
				</tr>
    	    </table>
   		</form>
    </div>
   	<div id = "salegainruleDG" style="width:auto; height: auto;"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addSalegainrule();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateSalegainrule();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteSalegainrule();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
		</div>
	</div>
   </div>
    
<script type="text/javascript" language="javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" language="javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" language="javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" language="javascript" src="js/form.js"></script>
<script type="text/javascript">
   
function initSalegainruleDatagrid(){
    $('#salegainruleDG').datagrid({
         title: '提成规则列表信息',
         queryParams: paramsJson(),
         url:'salegainrule/findListJson',
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
		     { field: 'gaintypename', title: '提成类型',width:100,align:"center"},
             { field: 'gaincode', title: '提成项编码',width:100,align:"center"},
             { field: 'gainname', title: '提成项名称',width:100,align:"center" },
             { field: 'gainformname', title: '提成方式',width:100,align:"center" },
             { field: 'gainrate', title: '提成值',width:100,align:"center" },
         ]]
     });
}
   
//将表单数据转为json
function paramsJson(){
	var json = {
		gaincode:$("#gaincode").val(),
		gainname:$("#gainname").val(),
		gaintype:$("#gaintype").val(),
		status:$("#status").val(),
	};
	return json;
}  
   

    //查询
	function querySalegainrule(){
		$('#salegainruleDG').datagrid('reload',paramsJson());
	}	
    
    
	var dialog;
	
	function addSalegainrule() {
	    dialog = $.dialog({
		    title: '提成规则添加',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:salegainrule/addInit?rid='+Math.random()
		});
	 }
	
	function updateSalegainrule() {
		
		var selected = $('#salegainruleDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
		
	    dialog = $.dialog({
		    title: '提成规则修改',
		    lock: true,
		    width: 1100,
		    height: 600,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:salegainrule/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#salegainruleDG').datagrid('reload');// 刷新当前页面
	}
	
	/**
	*删除
	*/
	function deleteSalegainrule(id){
		
		var selected = $('#salegainruleDG').datagrid("getSelected");
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
			var url="salegainrule/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 2, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#salegainruleDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
		initSalegainruleDatagrid();
        var returninfo = '${salegainrule.returninfo}';
        if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
        }
    });
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   querySalegainrule();
	    }  
	});
</script>
</body>
</html>

