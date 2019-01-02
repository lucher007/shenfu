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
    <div class="easyui-panel" title="查询条件" style="width:100%;">
         <form action="" method="post" id="searchForm" name="searchForm">
         	<input type="hidden"  name="ids" id="ids">
         	<input type="hidden"  name="stationcode" id="stationcode" value="${cellstationemployee.stationcode}">
         	<input type="hidden"  name="employeetype" id="employeetype" value="${cellstationemployee.employeetype}">
            <table style="width: 100%">
		    	<tr>
		    		<td align="right" width="8%">员工姓名：</td>
		    		<td width="15%">
		    			<input type="text"  class="inp w200" name="employeename" id="employeename" value="${employee.employeename }">
		    		</td>
		    		<td align="right" width="8%">电话号码：</td>
		    		<td width="15%">
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${employee.phone }">
		    		</td>
		    		<td align="right" width="8%">部门：</td>
		    		<td width="15%">
		    		   <!-- 
		    		    <c:choose>
		    		      <c:when test="${cellstationemployee.employeetype eq 'talker'}">
			    		      <select id="department" name="department" class="select" onchange="queryEmployee();">
			    		      	<option value="2" <c:if test="${employee.department == '2'}">selected</c:if>>销售部</option>
			    		      </select>
		    		      </c:when>
		    		      <c:when test="${cellstationemployee.employeetype eq 'visitor'}">
		    		      	<select id="department" name="department" class="select" onchange="queryEmployee();">
		    		      		<option value="3" <c:if test="${employee.department == '3'}">selected</c:if>>装维部</option>
		    		      	</select>
		    		      </c:when>
		    		     </c:choose>
		    		     -->
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
		    		<td align="right" width="8%">状态：</td>
        			<td width="15%">
        				<select id="status" name="status" class="select" onchange="queryEmployee();">
			                   <option value="1" <c:if test="${employeename.status == '1' }">selected</c:if>>在职</option>
			                   <option value="0" <c:if test="${employeename.status == '0' }">selected</c:if>>离职</option>
			             </select>
        			</td>
		    	</tr>
				<tr>
					<td colspan="8" align="center">
						<a href="javascript:queryEmployee();" class="easyui-linkbutton" style="width:120px">查询</a>                                                                                                                                                                                  
						<a href="javascript:addCellstationemployee();" class="easyui-linkbutton" style="width:120px">添加驻点人员</a> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
					</td>				
				</tr>
   		    </table>
   		</form>
    </div>
    <div id = "employeeDG" style="width:100%; height: 410px"></div>
    </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
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
	         singleSelect: false,
	         scrollbar:true,
	         pageSize: 10,
	         pageList: [10,30,50], 
	         //fitColumns:true,
	         //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
	         loadMsg:'正在加载数据......',
	         frozenColumns:[[
	                         { field: 'id', title: '全选',checkbox:true,align:"center",width:100,},
	                         { field: 'employeecode', title: '工号',width:150, align:"center"},
	                         { field: 'employeename', title: '姓名',width:150, align:"center"},
	                         { field: 'phone', title: '电话号码', width:150, align:"center"},
	                 	]],
	         columns: [[
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
	
	//将表单数据转为json
	function paramsJson(){
	 	var json = {
	 		employeename:$("#employeename").val(),
	 		phone:$("#phone").val(),
	 		department:$("#department").val(),
	 		status:$("#status").val()
	 	};
	 	return json;
    }
	
	function queryEmployee() {
		$('#employeeDG').datagrid('reload',paramsJson());	
	}
	
	function getChecked(){
		var ids = [];
		var rows = $('#employeeDG').datagrid('getChecked');
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].id);
		}
		$("#ids").val(ids.join(','));
	}
	
	/**
	*删除
	*/
	function addCellstationemployee(){
		
		getChecked();

		if ($("#ids").val() == "") {
			$.dialog.tips(
					'请选择员工', 2,
					'alert.gif');
			return;
		}
		
		$.dialog.confirm('确认是否添加？', function(){ 
			var data = {
					             ids: $("#ids").val(),
					     stationcode: $("#stationcode").val(),
					     employeetype: $("#employeetype").val(),
					   };
			var url="cellstationemployee/saveCellstationemployee?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 3, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#employeeDG').datagrid('reload');// 刷新当前页面
					$('#employeeDG').datagrid('clearChecked');//清空选中的值
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化小区列表
	   initEmployeeDatagrid();
		
       var returninfo = '${cellstationemployee.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	 function goback(){
		parent.closeDialog();
	 }
	
</script>
</body>
</html>