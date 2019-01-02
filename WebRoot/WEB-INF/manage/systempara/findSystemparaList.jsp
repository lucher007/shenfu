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
</head>

<body>
	<div id="body">
    <div class="seh-box">
        <form action="" method="post" id="searchForm" name="searchForm">
            <table width="100%">
				<tr>
					<td align="right" width="120px">参数编码：</td>
					<td width="130px">
						<input type="text"  class="inp w200" name="code" id="code" value="${systempara.code }">
					</td>
					<td align="right">
						<a href="javascript:querySystempara();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
					</td>
				</tr>
    	    </table>
   		</form>
    </div>
   	<div id = "systemparaDG" style="width:auto; height: 400px"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:updateSystempara();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
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
   
function initSystemparaDatagrid(){
    $('#systemparaDG').datagrid({
         title: '系统参数列表信息',
         queryParams: paramsJson(),
         url:'systempara/findListJson',
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
             { field: 'code', title: '参数编码',width:100,align:"center"},
             { field: 'name', title: '参数名称',width:100,align:"center" },
             { field: 'value', title: '参数值',width:100,align:"center" },
         ]]
     });
}
   
//将表单数据转为json
function paramsJson(){
	var json = {
		code:$("#code").val(),
	};
	return json;
}  
   

    //查询
	function querySystempara(){
		$('#systemparaDG').datagrid('reload',paramsJson());
	}	
    
    
	var dialog;
	function updateSystempara() {
		
		var selected = $('#systemparaDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
		
	    dialog = $.dialog({
		    title: '系统参数修改',
		    lock: true,
		    width: 1100,
		    height: 600,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    esc: false,
		    cancel:false,
		    content: 'url:systempara/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
     

	function closeDialog(){
		dialog.close();
		$('#systemparaDG').datagrid('reload');// 刷新当前页面
	}
	
	$(function () {
		initSystemparaDatagrid();
        var returninfo = '${systempara.returninfo}';
        if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
        }
    });
</script>
</body>
</html>

