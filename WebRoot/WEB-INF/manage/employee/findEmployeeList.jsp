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
		    		<td align="right" width="10%">员工姓名：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="employeename" id="employeename" value="${employee.employeename }">
		    		</td>
		    		<td align="right" width="10%">电话号码：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${employee.phone }">
		    		</td>
		    		<td align="right" width="10%">部门：</td>
		    		<td width="20%">
		    			<select id="department" name="department" class="select" onchange="queryEmployee();">
		    				<option value=""  <c:if test="${employee.department == ''}">selected</c:if>>请选择</option>
							<option value="0" <c:if test="${employee.department == '0'}">selected</c:if>>管理部</option>
							<option value="1" <c:if test="${employee.department == '1'}">selected</c:if>>系统部</option>
							<option value="2" <c:if test="${employee.department == '2'}">selected</c:if>>销售部</option>
							<option value="3" <c:if test="${employee.department == '3'}">selected</c:if>>装维部</option>
							<option value="4" <c:if test="${employee.department == '4'}">selected</c:if>>生产部</option>
							<option value="5" <c:if test="${employee.department == '5'}">selected</c:if>>400客服</option>
							<option value="6" <c:if test="${employee.department == '6'}">selected</c:if>>财务部</option>
						</select>
		    		</td>
		    		<td width="130" align="right">
		    			<a href="javascript:queryEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">员工编号：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="employeecode" id="employeecode" value="${employee.employeecode }">
		    		</td>
        			<td align="right">居住地址：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="address" id="address" value="${employee.address }">
		    		</td>
		    		<td align="right">状态：</td>
        			<td>
        				<select id="status" name="status" class="select" onchange="queryEmployee();">
			                   <option value="1" <c:if test="${employeename.status == '1' }">selected</c:if>>在职</option>
			                   <option value="0" <c:if test="${employeename.status == '0' }">selected</c:if>>离职</option>
			             </select>
        			</td>
					<td align="right" width="130">
   		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">推广码：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="extendcode" id="extendcode" value="${employee.extendcode }">
		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "employeeDG" style="width:100%; height: 450px"></div>
    <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addEmployee();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateEmployee();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteEmployee();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
		</div>
	</div>
    </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initEmployeeDatagrid(){
         $('#employeeDG').datagrid({
              title: '列表信息',
              queryParams: paramsJson(),
              url:'employee/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              toolbar:'#tools',
              //fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              frozenColumns:[[
                              { field: 'employeecode', title: '工号',width:150, align:"center"},
                              { field: 'employeename', title: '姓名',width:150,align:"center"},
                              { field: 'phone', title: '电话号码',width:150, align:"center"},
                      	]],
              columns: [[
                  { field: 'extendcode', title: '推广码',width:150,align:"center"},
				  { field: 'departmentname', title: '部门',width:150,align:"center"},
                  { field: 'identification', title: '身份证号',width:150,align:"center"},
                  { field: 'managername', title: '销售管家姓名',width:150,align:"center"},
                  { field: 'parentworkname', title: '上级销售员',width:150,align:"center"},
                  { field: 'address', title: '地址',width:250,align:"center",
                	       formatter: function (val, row, index) {
                	    	   if(val != null){
                	    		   return "<span title='" + val + "'>" + val + "</span>";
                	    	   }
			               }
                  },
                  { field: 'employeetypename', title: '员工类型',width:150,align:"center"},
                  { field: 'bankcardname', title: '银行名称',width:150,align:"center"},
                  { field: 'bankcardno', title: '银行卡号',width:150,align:"center"},
                  { field: 'employeesexname', title: '性别',width:150,align:"center"},
                  { field: 'birthday', title: '员工生日',width:150,align:"center"},
                  { field: 'addtime', title: '添加日期',width:150,align:"center"},
                  { field: 'statusname', title: '状态',width:150,align:"center",},
              ]],
          });
	}
	
	 function operateformatter(value,row,index){
		 var id = row.id;
		 	var updateContent = '<a class="btn-edit" href="javascript:updateEmployee('+ id +');">修改</a>';
		 	var deleteContent = '<a class="btn-del" href="javascript:deleteEmployee('+ id +');" >删除</a>';
	        return  updateContent + deleteContent;
	 }
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		employeename:$("#employeename").val(),
	 		phone:$("#phone").val(),
	 		department:$("#department").val(),
	 		employeecode:$("#employeecode").val(),
	 		address:$("#address").val(),
	 		status:$("#status").val(),
	 		extendcode:$("#extendcode").val(),
	 	};
	 	return json;
	 }
	
	function queryEmployee() {
		$('#employeeDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateEmployee() {
		
		var selected = $('#employeeDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
		
        //获取销售员的ID
       id = selected.id;
		
	    dialog = $.dialog({
		    title: '员工修改',
		    lock: true,
		    width: 1000,
		    height: 500,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:employee/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addEmployee() {	
	    dialog = $.dialog({
		    title: '员工添加',
		    lock: true,
		    width: 1000,
		    height: 500,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:employee/addInit?rid='+Math.random()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#employeeDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteEmployee(){
		
		var selected = $('#employeeDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
		
        //获取销售员的ID
       id = selected.id;
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="employee/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#employeeDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化员工列表
	   initEmployeeDatagrid();
		
       var returninfo = '${employee.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   initEmployeeDatagrid();
	    }  
	});
	
</script>
</body>
</html>