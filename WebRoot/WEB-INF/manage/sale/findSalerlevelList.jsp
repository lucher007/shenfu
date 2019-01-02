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
							<option value="2" <c:if test="${employee.department == '2'}">selected</c:if>>销售部</option>
						</select>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">性别：</td>
        			<td>
        				<select id="employeesex" name="employeesex" class="select" onchange="queryEmployee();">
        					   <option value="" <c:if test="${employeename.employeesex == '' }">selected</c:if>>请选择</option>
			                   <option value="1" <c:if test="${employeename.employeesex == '1' }">selected</c:if>>男</option>
			                   <option value="0" <c:if test="${employeename.employeesex == '0' }">selected</c:if>>女</option>
			             </select>
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
        				<input type="button" class="btn-sch" value="查询" onclick="queryEmployee();"/>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "employeeDG" style="width:auto; height: 400px">
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
              title: '销售分红统计',
              queryParams: paramsJson(),
              url:'employee/findSalelevelListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'employeename', title: '姓名',width:60,align:"center"},
                  { field: 'phone', title: '电话号码',width:80,align:"center"},
                  { field: 'status', title: '状态',width:60,align:"center",formatter:statusformatter},
                  { field: 'salescore', title: '销售积分',width:150,align:"center"},
                  { field: 'level', title: '分红等级',width:150,align:"center"},
                  
              ]]
          });
	}
    
    function employeesexformatter(value,row,index){
	 	var id = row.id;
	 	if(value == '0'){
	 		return "女";
	 	}else if(value == '1'){
	 		return "男";
	 	}
	 }
	 
	 function statusformatter(value,row,index){
	 	var id = row.id;
	 	if(value == '0'){
	 		return "离职";
	 	}else if(value == '1'){
	 		return "在职";
	 	}
	 }
	 
	 function birthdayformatter(value,row,index){
	 	return value.substring(0,19);
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
	 		employeesex:$("#employeesex").val(),
	 		address:$("#address").val(),
	 		status:$("#status").val()
	 	};
	 	return json;
	 }
	
	function queryEmployee() {
		$('#employeeDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateEmployee(id) {
	    dialog = $.dialog({
		    title: '员工修改',
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
		    content: 'url:employee/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addEmployee() {	
	    dialog = $.dialog({
		    title: '员工添加',
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
	function deleteEmployee(id){
		
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
    
</script>
</body>
</html>