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
		    		<td align="right" width="10%">客户姓名：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="username" id="username" value="${task.username }">
		    		</td>
		    		<td align="right" width="10%">电话号码：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${task.phone }">
		    		</td>
		    		<td align="right" width="120px">任务单号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="taskno" id="taskno" value="${task.taskno }">
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">安装地址：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="address" id="address" value="${user.address }">
		    		</td>
		    		<td align="right">状态：</td>
        			<td>
        				<select id="status" name="status" class="select" onchange="queryTask();">
			                   <option value="3" <c:if test="${user.status == '3' }">selected</c:if>>完成职工图</option>
			             </select>
        			</td>
        			<td align="right">来源：</td>
        			<td>
        				<select id="source" name="source" class="select" onchange="queryTask();">
			                   <option value=""  <c:if test="${user.status == ''}">selected</c:if>>请选择</option>
			                   <option value="0" <c:if test="${user.status == '0' }">selected</c:if>>销售</option>
			                   <option value="1" <c:if test="${user.status == '1' }">selected</c:if>>400客服</option>
			             </select>
        			</td>
					<td align="right" width="130">
        				<input type="button" class="btn-sch" value="查询" onclick="queryTask();"/>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "taskDG" style="width:100%; height: 280px">
    	</div>
	</div>
    </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initTaskDatagrid(){
         $('#taskDG').datagrid({
              title: '潜在客户列表信息',
              queryParams: paramsJson(),
              url:'task/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              //fitColumns:true,
              idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'taskno', title: '任务单号',width:80,align:"center"},
                  { field: 'username', title: '客户姓名',width:60,align:"center",},
                  { field: 'phone', title: '联系电话',width:60,align:"center",},
                  { field: 'address', title: '安装地址',width:200,align:"center",
                	    formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                },
                  },
                  { field: 'addtime', title: '添加时间',width:100,align:"center",
                  	        formatter:function(val, row, index){ 
							 	return val==null?val:val.substring(0,19);
                  	        },
                  },
                  { field: 'researchername', title: '勘察人员',width:60,align:"center",},
                  { field: 'remark', title: '选择' ,width:60,align:"center",
                  		    formatter:function(val, row, index){ 
							 	var formatter = '<a class="btn-look" href="javascript:selectTask('+ index +')">选择</a>';
								return formatter;
	                  	    },
                  },
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 			username:$("#username").val(),
		 		phone:$("#phone").val(),
		 		taskno:$("#taskno").val(),
		 		address:$("#address").val(),
		 		status:$("#status").val(),
		 		source:$("#source").val(),
	 	};
	 	return json;
	 }
	
	function queryTask() {
		$('#taskDG').datagrid('reload',paramsJson());
	}
	
	function selectTask(index){
	 	//先选中该行
	 	$("#taskDG").datagrid('selectRow',index);
	 	
	 	//获取选中的行
	 	var row = $("#taskDG").datagrid('getSelected');
	 	
	 	//将row转换成json字符串
	 	var event = JSON.stringify(row);
	 	//中文进行编码转换，防止乱码
		var o = JSON.parse(event); 
		//将Json字符串赋值给event
		eval("var event = '"+JSON.stringify(o)+"';"); 
		parent.closeTaskDialog(event);
     }
	
	
	$(function () {
	   
	   //初始化潜在客户列表
	   initTaskDatagrid();
		
       var returninfo = '${task.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>