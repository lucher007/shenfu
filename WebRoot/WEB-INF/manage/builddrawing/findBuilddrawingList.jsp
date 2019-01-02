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
            <input type="hidden" name="taskid" id="taskid" value="${builddrawing.taskid}"/>
            <table width="100%">
		    	<tr>
		    	    <td align="right" width="120px">任务单号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="taskno" id="taskno" value="${builddrawing.taskno }" readonly="readonly" style="background:#eeeeee;">
		    		</td>
		    		
		    		<td align="right" width="130">
        				<input type="button" class="btn-back" value="返回" onclick="goback();" />
   		    		</td>
					<td align="right" width="130">
        				<input type="button" class="btn-sch" value="查询" onclick="queryBuilddrawing();"/>
   		    		</td>
        			
		    		<td width="130" align="right">
		    			<input type="button" class="btn-add" value="添加" onclick="addBuilddrawing();"/>
		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "builddrawingDG" style="width:100%; height: 400px">
    	</div>
	</div>
    </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initBuilddrawingDatagrid(){
         $('#builddrawingDG').datagrid({
              title: '任务单单列表信息',
              queryParams: paramsJson(),
              url:'builddrawing/findListJson',
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
                  { field: 'filename', title: '文件名称',width:100,align:"center",},
                  { field: 'addtime', title: '上传时间',width:100,align:"center",
                  	        formatter:function(val, row, index){ 
							 	return val==null?val:val.substring(0,19);
                  	        },
                  },
                  { field: 'id', title: '操作' ,width:100,align:"center",
                  		   formatter:function(val, row, index){ 
							 	var id = row.id;
							 	var updateContent = '<a class="btn-look" target="_blank" href="builddrawing/lookBuilddrawing?id='+ id +'">查看</a>';
							 	var deleteContent = '<a class="btn-del" href="javascript:deleteBuilddrawing('+ id +');" >删除</a>';
						        return  updateContent + deleteContent;
	                  	    },
                  },
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		taskid:$("#taskid").val(),
	 		taskno:$("#taskno").val(),
	 	};
	 	return json;
	 }
	
	function queryBuilddrawing() {
		$('#builddrawingDG').datagrid('reload',paramsJson());
	}
	 
	function addBuilddrawing() {	
	    dialog = $.dialog({
		    title: '施工图添加',
		    lock: true,
		    width: 800,
		    height: 500,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    esc: false,
		    cancel:false,
		    content: 'url:builddrawing/addInit?rid='+Math.random()+'&taskid='+$("#taskid").val()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#builddrawingDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteBuilddrawing(id){
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="builddrawing/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#builddrawingDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化任务单单列表
	   initBuilddrawingDatagrid();
		
       var returninfo = '${builddrawing.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
    function lookBuilddrawing(taskid) {	
	    dialog = $.dialog({
		    title: '施工图查询',
		    lock: true,
		    width: 900,
		    height: 560,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    esc: false,
		    cancel:false,
		    content: 'url:builddrawing/addInit?rid='+Math.random()+'&taskid='+taskid
		});
	 }
    
    function goback(){
		parent.closeDialog();
  	}
    
</script>
</body>
</html>