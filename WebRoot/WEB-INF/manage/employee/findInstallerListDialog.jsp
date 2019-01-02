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
							<option value="3" <c:if test="${employee.department == '3'}">selected</c:if>>装维部</option>
						</select>
		    		</td>
		    		<td align="right" width="130">
        				<a href="javascript:queryEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">上级编号：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="parentemployeecode" id="parentemployeecode" value="${employee.parentemployeecode }">
		    		</td>
        			<td align="right" width="10%">工作类型：</td>
		    		<td width="20%">
		    			<select id="employeetype" name="employeetype" class="select" onchange="queryEmployee();">
		    				 <option value="" <c:if test="${employee.employeetype == '' }">selected</c:if>>请选择</option>
		    				<option value="0" <c:if test="${employee.employeetype == '0'}">selected</c:if>>兼职</option>
							<option value="1" <c:if test="${employee.employeetype == '1'}">selected</c:if>>全职</option>
						</select>
		    		</td>
		    		<td align="right">状态：</td>
        			<td>
        				<select id="status" name="status" class="select" onchange="queryEmployee();">
			                   <option value="1" <c:if test="${employeename.status == '1' }">selected</c:if>>在职</option>
			                   <option value="0" <c:if test="${employeename.status == '0' }">selected</c:if>>离职</option>
			             </select>
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
    <div class="lst-box">
    	<div id = "employeeDG" style="width:100%; height: 300px;">
    	</div>
	</div>
    </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
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
              pageSize: 5,
              pageList: [5,10,20], 
              //fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              frozenColumns:[[
                              { field: 'id', title: '操作' ,width:80, align:"center",formatter:operateformatter},
                              { field: 'employeecode', title: '工号',width:150, align:"center"},
                              { field: 'employeename', title: '姓名',width:150, align:"center"},
                              { field: 'phone', title: '电话号码', width:150, align:"center"},
                      	]],
              columns: [[
                  { field: 'extendcode', title: '推广码',width:150,align:"center"},
				  { field: 'departmentname', title: '部门',width:150, align:"center"},
                  { field: 'identification', title: '身份证号',width:150, align:"center"},
                  { field: 'parentworkname', title: '上级销售员',width:150, align:"center"},
                  { field: 'address', title: '地址',width:150, align:"center",
                	       formatter: function (val, row, index) {
                	    	   if(val != null){
                	    		   return "<span title='" + val + "'>" + val + "</span>";
                	    	   }
			               }
                  },
                  { field: 'employeetypename', title: '员工类型',width:150, align:"center"},
                  { field: 'bankcardname', title: '银行名称',width:150, align:"center"},
                  { field: 'bankcardno', title: '银行卡号',width:150, align:"center"},
                  { field: 'employeesexname', title: '性别',width:150, align:"center"},
                  { field: 'birthday', title: '员工生日',width:150, align:"center"},
                  { field: 'addtime', title: '添加日期',width:150, align:"center"},
                  { field: 'statusname', title: '状态',width:150, align:"center",},
              ]],
          });
	}
   
	 function operateformatter(value,row,index){
	 
	 	var formatter = '<a class="btn-look" href="javascript:selectUser('+ index +')">选择</a>';
		return formatter;
	 }
	
	 function selectUser(value){
	    
	    //先选中该行
	 	$("#employeeDG").datagrid('selectRow',value);
	 	
	 	//获取选中的行
	 	var row = $("#employeeDG").datagrid('getSelected');
	 	
	 	var event = JSON.stringify(row);
		var o = JSON.parse(event); 
		//将Json字符串赋值给event
		eval("var event = '"+JSON.stringify(o)+"';"); 
		
	 	
		parent.closeEmployeeDialog(event);
     }
	 
	//将表单数据转为json
	function paramsJson(){
	 	var json = {
	 		department:$("#department").val(),
	 		employeename:$("#employeename").val(),
	 		employeename:$("#employeename").val(),
	 		phone:$("#phone").val(),
	 		parentemployeename:$("#parentemployeename").val(),
	 		employeetype:$("#employeetype").val(),
	 		status:$("#status").val(),
	 		extendcode:$("#extendcode").val(),
	 	};
	 	return json;
	 }

	function queryEmployee() {
		$('#employeeDG').datagrid('reload',paramsJson());	
	}
	
	$(function () {
	   
	   //初始化员工列表
	   initEmployeeDatagrid();
		
       var returninfo = '${employee.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	function checkVal() {
		if ($("#userid") != undefined && ($("#userid").val() == "" || $("#userid").val() == null )) {
			$.dialog.tips("请输入客户姓名", 1, "alert.gif", function() {
				$("#username").focus();
			});
			return false;
		}
		if ($("#researcherid") != undefined && ($("#researcherid").val() == "" || $("#researcherid").val() == null )) {
			$.dialog.tips("请输入勘察人员", 1, "alert.gif", function() {
				$("#researchername").focus();
			});
			return false;
		}
		return true;
	}
	
	function saveTask() {
		if (!checkVal()) {
			return;
		}
		$("#addForm").attr("action", "task/save");
	    $("#addForm").submit();
	}
	
</script>
</body>
</html>