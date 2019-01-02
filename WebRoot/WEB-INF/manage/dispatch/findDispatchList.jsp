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
		    		<td align="right" width="10%">客户姓名：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="username" id="username" value="${userdispatch.username }">
		    		</td>
		    		<td align="right" width="10%">电话号码：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${userdispatch.phone }">
		    		</td>
		    		<td align="right" width="120px">工单号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="dispatchcode" id="dispatchcode" value="${userdispatch.dispatchcode }">
		    		</td>
		    		<td align="center" width="10%">
		    			<a href="javascript:queryDispatch();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">安装地址：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="address" id="address" value="${userdispatch.address }">
		    		</td>
		    		<td align="right" width="120px">状态：</td>
		    		<td>
		    			<select id="status" name="status" class="select" onchange="queryDispatch();">
			                <option value="" <c:if test="${userdispatch.status == '' }">selected</c:if>>请选择</option>
			                <option value="1" <c:if test="${userdispatch.status == '1' }">selected</c:if>>已派工</option>
			                <option value="2" <c:if test="${userdispatch.status == '2' }">selected</c:if>>处理中</option>
			                <option value="3" <c:if test="${userdispatch.status == '3' }">selected</c:if>>处理成功</option>
			             	<option value="4" <c:if test="${userdispatch.status == '4' }">selected</c:if>>处理失败</option>
			             	<option value="5" <c:if test="${userdispatch.status == '5' }">selected</c:if>>审核通过</option>
			             </select>
		    		</td>
		    		<td align="right" width="120px">订单号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="ordercode" id="ordercode" value="${userdispatch.ordercode }">
		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "dispatchDG" style="width:auto; height: 450px"></div>
    <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:lookUserorder();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看订单详情</a>
			<a href="javascript:lookDispatchfile();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看现场安装图片</a>
			<a href="javascript:updateDispatch();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改工单</a>
			<a href="javascript:deleteDispatch();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除工单</a>
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
              url:'userdispatch/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              //fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              toolbar:'#tools',
              frozenColumns:[[
								{ field: 'dispatchcode', title: '工单号',width:150,align:"center"},
        	                    { field: 'installercode', title: '安装人编号',width:150,align:"center"},
								{ field: 'installername', title: '安装人姓名',width:150,align:"center"},
                         ]],
              columns: [[
								
								{ field: 'ordercode', title: '订单号',width:150,align:"center"},
								{ field: 'usercode', title: '客户编号',width:150,align:"center"},
        	                    { field: 'username', title: '客户姓名',width:150,align:"center"},
        	                    { field: 'phone', title: '联系电话',width:100,align:"center"},     
			                    { field: 'content', title: '安装内容',width:150,align:"center",
			                  	        formatter: function (value) {
						                    return "<span title='" + value + "'>" + value + "</span>";
						                },
			                    },
			                    { field: 'statusname', title: '状态',width:150,align:"center",},
			                    { field: 'dealresult', title: '处理结果',width:150,align:"center",
			                  	        formatter: function (value) {
						                    return "<span title='" + value + "'>" + value + "</span>";
						                },
			                    },
			                    { field: 'checkstatusname', title: '审核状态',width:150,align:"center",},
			                    { field: 'checkresult', title: '审核意见',width:150,align:"center",
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
 			username:$("#username").val(),
	 		phone:$("#phone").val(),
	 		address:$("#address").val(),
	 		status:$("#status").val(),
 	 		ordercode:$("#ordercode").val(),
	 		dispatchcode:$("#dispatchcode").val(),
	 	};
	 	return json;
	 }
	
	function queryDispatch() {
		$('#dispatchDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateDispatch(id) {
		
		var selected = $('#dispatchDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
		
        //判断是否能修改
        var status = selected.status;
        if("1" != status){
        	$.dialog.tips('此工单已经处理，不能修改', 2, 'alert.gif');
		     return;
        }
        
	    dialog = $.dialog({
		    title: '工单修改',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:userdispatch/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addDispatch() {	
	    dialog = $.dialog({
		    title: '工单添加',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    esc: false,
		    cancel:false,
		    content: 'url:userdispatch/addInit?rid='+Math.random()
		});
	 }
   
	function lookUserorder() {
		
		var selected = $('#dispatchDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var orderid = selected.orderid;
		
	    dialog = $.dialog({
		    title: '订单信息',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:userorder/lookInit?rid='+ Math.random()+'&id='+orderid
		});
	 }
    
	 //安装图片查看
	 function lookDispatchfile() {
		var selected = $('#dispatchDG').datagrid("getSelected");
       if(selected == null){
  	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
       }
       //获取需要修改项的ID
       var dispatchcode = selected.dispatchcode;
		
	    dialog = $.dialog({
		    title: '安装图片信息',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:userdispatchfile/findByList?rid='+ Math.random()+'&dispatchcode='+dispatchcode
		});
	} 
	
	function closeDialog(){
		dialog.close();
		$('#dispatchDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteDispatch(){
		
		var selected = $('#dispatchDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
        
		//判断是否能修改
        var status = selected.status;
        if("1" != status){
        	$.dialog.tips('此工单已经处理，不能删除', 2, 'alert.gif');
		     return;
        }
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="userdispatch/delete?rid="+Math.random();
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
		
       var returninfo = '${userdispatch.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryDispatch();
	    }  
	});
	
</script>
</body>
</html>