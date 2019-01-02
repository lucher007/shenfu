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
			             </select>
        			</td>
					<td align="right" width="130">
						<a href="javascript:queryEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
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
    	<div id = "employeeDG" style="width:100%; height: auto; ">
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
              title: '产品列表信息',
              queryParams: paramsJson(),
              url:'employee/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              //fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              frozenColumns:[[
                              { field: 'id', title: '操作' ,align:"center",width:80,formatter:operateformatter},
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
 			employeename:$("#employeename").val(),
	 		phone:$("#phone").val(),
	 		department:$("#department").val(),
	 		employeesex:$("#employeesex").val(),
	 		address:$("#address").val(),
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
    
</script>
</body>
</html>