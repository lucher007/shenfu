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
		    		<td align="right" width="120px">维修单号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="repairno" id="repairno" value="${userrepair.repairno }">
		    		</td>
					<td align="right" width="130">
        				<input type="button" class="btn-sch" value="查询" onclick="queryUserrepair();"/>
   		    		</td>
        			
		    		<td width="130" align="right">
		    			<input type="button" class="btn-add" value="添加" onclick="addUserrepair();"/>
		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "userrepairDG" style="width:auto; height: 400px">
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
	
	function initUserrepairDatagrid(){
         $('#userrepairDG').datagrid({
              title: '维修单列表信息',
              queryParams: paramsJson(),
              url:'userrepair/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'repairno', title: '维修单号',width:100,align:"center"},
                  { field: 'username', title: '客户姓名',width:100,align:"center",},
                  { field: 'usersex', title: '客户性别',width:100,align:"center",
                  		    formatter:function(val, row, index){ 
							 	if(val == '0'){
							 		return "女";
							 	}else if(val == '1'){
							 		return "男";
							 	}
                  	        },
                  },
                  { field: 'phone', title: '联系电话',width:100,align:"center",},
                  { field: 'addtime', title: '添加时间',width:100,align:"center",
                  	        formatter:function(val, row, index){ 
							 	return val==null?val:val.substring(0,19);
                  	        },
                  },
                  { field: 'productidentfy', title: '产品标识',width:100,align:"center"},
                  { field: 'content', title: '维修内容',width:100,align:"center"},
                  { field: 'status', title: '状态',width:100,align:"center",
                  		    formatter:function(val, row, index){ 
							 	if(val == '0'){
							 		return "未受理";
							 	}else if(val == '1'){
							 		return "受理中";
							 	}else if(val == '2'){
							 		return "已处理";
							 	}else if(val == '3'){
							 		return "处理失败";
							 	}else if(val == '4'){
							 		return "结单";
							 	}
                  	        },
                  },
                  { field: 'id', title: '操作' ,width:100,align:"center",
                  		    formatter:function(val, row, index){ 
							 	var id = row.id;
							 	//var updateContent = '<a class="btn-edit" href="javascript:updateUserrepair('+ id +');">修改</a>';
							 	var deleteContent = '<a class="btn-del" href="javascript:deleteUserrepair('+ id +');" >删除</a>';
						        return  deleteContent;
	                  	    },
                  },
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		repairno:$("#repairno").val(),
	 	};
	 	return json;
	 }
	
	function queryUserrepair() {
		$('#userrepairDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateUserrepair(id) {
	    dialog = $.dialog({
		    title: '维修单修改',
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
		    content: 'url:userrepair/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addUserrepair() {	
	    dialog = $.dialog({
		    title: '维修单添加',
		    lock: true,
		    width: 1000,
		    height: 600,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    esc: false,
		    cancel:false,
		    content: 'url:userrepair/addInit?rid='+Math.random()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#userrepairDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteUserrepair(id){
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="userrepair/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#userrepairDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化维修单列表
	   initUserrepairDatagrid();
		
       var returninfo = '${userrepair.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>