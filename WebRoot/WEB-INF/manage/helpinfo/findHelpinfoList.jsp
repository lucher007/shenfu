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
					<td align="right" width="120px">类型：</td>
					<td width="130px">
						<select id="type" name="type" class="select" onchange="queryHelpinfo();">
		    				<option value=""  <c:if test="${helpinfo.type == ''}">selected</c:if>>请选择</option>
							<option value="0" <c:if test="${helpinfo.type == '0'}">selected</c:if>>行动力</option>
						</select>
					</td>
					<td align="right">
						<a href="javascript:queryHelpinfo();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
					</td>
				</tr>
    	    </table>
   		</form>
    </div>
   	<div id = "helpinfoDG" style="width:auto; height: 400px"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addHelpinfo();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateHelpinfo();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteHelpinfo();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
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
   
	function initHelpinfoDatagrid(){
	    $('#helpinfoDG').datagrid({
	         title: '帮助信息列表信息',
	         queryParams: paramsJson(),
	         url:'helpinfo/findListJson',
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
	             { field: 'typename', title: '类型',width:100,align:"center"},
	             { field: 'showorder', title: '显示顺序',width:100,align:"center"},
	             { field: 'question', title: '参数名称',width:300,align:"center",
	            	 formatter: function (value) {
			              return "<span title='" + value + "'>" + value + "</span>";
			          },
	             },
	             { field: 'answer', title: '参数值',width:300,align:"center",
	            	 formatter: function (value) {
			              return "<span title='" + value + "'>" + value + "</span>";
			          },
	             },
	         ]]
	     });
	}
	   
	//将表单数据转为json
	function paramsJson(){
		var json = {
				type:$("#type").val(),
		};
		return json;
	}  
   

    //查询
	function queryHelpinfo(){
		$('#helpinfoDG').datagrid('reload',paramsJson());
	}	
    
	var dialog;
	function updateHelpinfo() {
		
		var selected = $('#helpinfoDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
		
	    dialog = $.dialog({
		    title: '帮助信息修改',
		    lock: true,
		    width: 1100,
		    height: 600,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:helpinfo/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	
     function addHelpinfo() {
	    dialog = $.dialog({
		    title: '帮助信息添加',
		    lock: true,
		    width: 1100,
		    height: 600,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:helpinfo/addInit?rid='+ Math.random()
		});
	 }
     

	function closeDialog(){
		dialog.close();
		$('#helpinfoDG').datagrid('reload');// 刷新当前页面
	}
	
	/**
	*删除
	*/
	function deleteHelpinfo(){
		
		var selected = $('#helpinfoDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
		
        //获取需要修改项的ID
        var id = selected.id;
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="helpinfo/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#helpinfoDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
		initHelpinfoDatagrid();
        var returninfo = '${helpinfo.returninfo}';
        if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
        }
    });
</script>
</body>
</html>

