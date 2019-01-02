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
		    		<td align="right" width="120px">时间等级：</td>
		    		<td>
		    			<select id="level" name="level" class="select">
		    			    <option value="" <c:if test="${timepara.level == '' }">selected</c:if>>请选择</option>
			                <option value="1" <c:if test="${timepara.level == '1' }">selected</c:if>>稍微延迟(绿色显示)</option>
			                <option value="2" <c:if test="${operator.level == '2' }">selected</c:if>>延迟较多(蓝色显示)</option>
			             	<option value="3" <c:if test="${operator.level == '3' }">selected</c:if>>严重滞后(红色显示)</option>
			             </select>
		    		</td>
					<td align="right" width="130">
						<a href="javascript:queryTimepara();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
        			<!--
		    		<td width="130" align="right">
		    			<input type="button" class="btn-add" value="添加" onclick="addTimepara();"/>
		    		</td>
		    		-->
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "timeparaDG" style="width:auto; height: 400px">
    	</div>
	</div>
	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:updateTimepara();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
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
	
	function initTimeparaDatagrid(){
         $('#timeparaDG').datagrid({
              title: '时间参数列表信息',
              queryParams: paramsJson(),
              url:'timepara/findListJson',
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
					if (row.level == '1'){
						return 'color:green;font-weight:bold;';
					}else if (row.level == '2'){
						return 'color:blue;font-weight:bold;';
					}else if (row.level == '3'){
						return 'color:red;font-weight:bold;';
					}
				},
              columns: [[
                  { field: 'level', title: '时间等级',width:100,align:"center",
                  		    formatter:function(val, row, index){ 
							 	if(val == '1'){
							 		return "稍微延迟(绿色显示)";
							 	}else if(val == '2'){
							 		return "延迟较多(蓝色显示)";
							 	}else if(val == '3'){
							 		return "严重滞后(红色显示)";
							 	}
                  	        },
                  },
                  { field: 'time', title: '时间隔间(小时大于)',width:100,align:"center",},
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		level:$("#level").val(),
	 	};
	 	return json;
	 }
	
	function queryTimepara() {
		$('#timeparaDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateTimepara() {
		
		var selected = $('#timeparaDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
		
	    dialog = $.dialog({
		    title: '时间参数修改',
		    lock: true,
		    width: 1000,
		    height: 500,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    esc: false,
		    cancel:false,
		    content: 'url:timepara/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#timeparaDG').datagrid('reload');// 刷新当前页面
	}
	
	$(function () {
	   //初始化任务单列表
	   initTimeparaDatagrid();
       var returninfo = '${timepara.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>