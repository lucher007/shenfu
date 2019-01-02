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
<link rel="stylesheet" href="main/plugin/easyui/themes/default/easyui.css"/>
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
			                   <option value="1" <c:if test="${user.status == '1' }">selected</c:if>>已接收</option>
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
    	<div id = "taskDG" style="width:auto; height: 400px">
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
	
	function initTaskDatagrid(){
         $('#taskDG').datagrid({
              title: '任务单列表信息',
              queryParams: paramsJson(),
              url:'task/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'taskno', title: '任务单号',width:100,align:"center"},
                  { field: 'username', title: '客户姓名',width:100,align:"center",},
                  { field: 'phone', title: '联系电话',width:100,align:"center",},
                  { field: 'address', title: '安装地址',width:150,align:"center",
                	  	formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                },
                  },
                  { field: 'saleno', title: '销售单号',width:100,align:"center",},
                  { field: 'salername', title: '销售人员',width:80,align:"center",},
                  { field: 'researchername', title: '勘察人员',width:100,align:"center",},
                  { field: '1', title: '手绘图' ,width:100,align:"center",
	          		    formatter:function(val, row, index){ 
							 	var id = row.id;
							 	var taskno = row.taskno;
							    var updateContent = '<a class="btn-test" href="javascript:lookHanddrawing('+ id +','+ taskno +');">查看</a>';
								return  updateContent;
	          	        },
	              },
                  { field: 'id1', title: '施工图' ,width:100,align:"center",
	            		    formatter:function(val, row, index){ 
							 	var id = row.id;
							 	var taskno = row.taskno;
							    var updateContent = '<a class="btn-test" href="javascript:lookBuilddrawing('+ id +','+ taskno +');">上传</a>';
								return  updateContent;
	            	        },
	              },
                  { field: '2', title: '操作' ,width:100,align:"center",
                  		    formatter:function(val, row, index){ 
							 	var id = row.id;
							 	var taskno = row.taskno;
							    var updateContent = '<a class="btn-test" href="javascript:updateForBuilddrawing('+ id +','+ taskno +');">完成施工图</a>';
								return  updateContent;
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
	
	$(function () {
	   
	   //初始化任务单列表
	   initTaskDatagrid();
		
       var returninfo = '${task.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
    
    function lookHanddrawing(taskid,taskno) {	
	    dialog = $.dialog({
		    title: '手绘图信息',
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
		    content: 'url:handdrawing/findByList?rid='+Math.random()+'&taskid='+taskid+'&taskno='+taskno
		});
	 }
	 
	 function lookBuilddrawing(taskid,taskno) {	
	    dialog = $.dialog({
		    title: '施工图信息',
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
		    content: 'url:builddrawing/findByList?rid='+Math.random()+'&taskid='+taskid+'&taskno='+taskno
		});
	 }
    
	 /**
		*任务单接收
		*/
		function updateForBuilddrawing(id){
			
			$.dialog.confirm('确认完成施工图上传？', function(){ 
				var data = {
						     id: id
						   };
				var url="task/updateForBuilddrawing?rid="+Math.random();
				$.getJSON(url,data,function(jsonMsg){
					$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
					if(jsonMsg.flag=="1"){
						$('#taskDG').datagrid('reload');// 刷新当前页面
					}else{
					}
				});
			}, function(){
				
				});
			
		}
	 
    function closeDialog(){
		dialog.close();
		$('#taskDG').datagrid('reload');// 刷新当前页面
	}
    
</script>
</body>
</html>