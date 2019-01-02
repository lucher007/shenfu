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
<base href="<%=basePath%>"/>
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
            <input type="hidden" name="status" id="status" value="0"/>
            <table width="100%">
		    	<tr>
		    		<td align="right" width="120px">工单号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="dispatchno" id="dispatchno" value="${dispatch.dispatchno }">
		    		</td>
					<td align="right" width="130">
        				<input type="button" class="btn-sch" value="查询" onclick="queryDispatch();"/>
   		    		</td>
        			
		    		<td width="130" align="right">
		    			<input type="button" class="btn-add" value="添加" onclick="addDispatch();"/>
		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "dispatchDG" style="width:auto; height: 400px">
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
	
	function initDispatchDatagrid(){
         $('#dispatchDG').datagrid({
              title: '工单列表信息',
              queryParams: paramsJson(),
              url:'dispatch/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'dispatchno', title: '工单号',width:100,align:"center"},
                  { field: 'username', title: '客户姓名',width:100,align:"center"},
                  { field: 'phone', title: '联系电话',width:100,align:"center"},
                  { field: 'addtime', title: '添加时间',width:100,align:"center",
                  	        formatter:function(val, row, index){ 
							 	return val==null?val:val.substring(0,19);
                  	        },
                  },
                  { field: 'status', title: '状态',width:100,align:"center",
                  		    formatter:function(val, row, index){ 
							 	if(val == '0'){
							 		return "未派单";
							 	}else if(val == '1'){
							 		return "已派单";
							 	}else if(val == '2'){
							 		return "处理中";
							 	}else if(val == '3'){
							 		return "已处理";
							 	}else if(val == '4'){
							 		return "处理失败";
							 	}else if(val == '5'){
							 		return "审核通过";
							 	}
                  	        },
                  },
                  { field: 'installername', title: '安装人',width:100,align:"center"},
                  { field: 'content', title: '安装内容',width:100,align:"center",
                  	        formatter: function (value) {
			                    return "<span title='" + value + "'>" + value + "</span>";
			                },
                  },
                  { field: 'dealresult', title: '处理结果',width:100,align:"center",
                  	        formatter: function (value) {
			                    return "<span title='" + value + "'>" + value + "</span>";
			                },
                  },
                  { field: 'checkresult', title: '审核意见',width:100,align:"center",
                  	        formatter: function (value) {
			                    return "<span title='" + value + "'>" + value + "</span>";
			                },
                  },
                  { field: 'id', title: '操作' ,width:100,align:"center",
                  		    formatter:function(val, row, index){ 
							 	var id = row.id;
							 	if(row.status == '0'){
								 	var updateContent = '<a class="btn-edit" href="javascript:assignDispatch('+ id +');">派工</a>';
							        return  updateContent;
							    }else{
							 		return "";
							 	}
	                  	    },
                  },
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		dispatchno:$("#dispatchno").val(),
	 		status:$("#status").val(),
	 	};
	 	return json;
	 }
	
	function queryDispatch() {
		$('#dispatchDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function assignDispatch(id) {
	    dialog = $.dialog({
		    title: '工单派工',
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
		    content: 'url:dispatch/assignInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addDispatch() {	
	    dialog = $.dialog({
		    title: '工单添加',
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
		    content: 'url:dispatch/addInit?rid='+Math.random()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#dispatchDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteDispatch(id){
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="dispatch/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#dispatchDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化派工单号列表
	   initDispatchDatagrid();
		
       var returninfo = '${dispatch.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>