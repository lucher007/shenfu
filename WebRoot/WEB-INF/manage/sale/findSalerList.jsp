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
        	<input type="hidden" name="department" id="department" value="2"/>
            <table width="100%">
		    	<tr>
		    		<td align="right" width="10%">工号：</td>
		    		<td width="10%">
		    			<input type="text"  class="inp w200" name="employeecode" id="employeecode" value="${employee.employeecode }">
		    		</td>
		    		<td align="right" width="10%">员工姓名：</td>
		    		<td width="10%">
		    			<input type="text"  class="inp w200" name="employeename" id="employeename" value="${employee.employeename }">
		    		</td>
		    		<td align="right" width="10%">电话号码：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${employee.phone }">
		    		</td>
		    		<td align="center">
						<a href="javascript:queryEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
		    	<tr>
        			<td align="right" width="10%">工作类型：</td>
		    		<td width="10%">
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
        			<td align="right" width="10%">上级销售员：</td>
		    		<td width="20%" colspan="2">
		    			<input type="hidden" class="inp w200" name="parentemployeecode" id="parentemployeecode" readonly="readonly" style="background:#eeeeee;" value="${user.parentemployeecode }">
						<input type="text" class="inp w200" name="parentemployeename" id="parentemployeename" readonly="readonly" style="background:#eeeeee;" value="${user.parentemployeename }">
		    			<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						<a href="javascript:cleanEmployee();" class="easyui-linkbutton" iconCls="icon-undo" style="width:80px">清除</a>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">推广码：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="extendcode" id="extendcode" value="${employee.extendcode }">
		    		</td>
		    		<td align="right" width="10%">充值VIP等级：</td>
		    		<td width="10%">
		    			<input name="rechargevipcode" id="rechargevipcode" >
		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
   	<div id = "employeeDG" style="width:100%;"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addEmployee();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateEmployee();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteEmployee();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
			<a href="javascript:saleExtend();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">销售推广</a>
			<a href="javascript:mobilebuniss();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">移动商务平台</a>
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
              title: '销售员列表信息',
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
                              { field: 'employeecode', title: '工号',width:150, align:"center",sortable:"true"},
                              { field: 'employeename', title: '姓名',width:150,align:"center"},
                              { field: 'phone', title: '电话号码',width:150, align:"center",sortable:"true"},
                      	]],
              columns: [[
				  { field: 'extendcode', title: '推广码',width:150,align:"center"},
				  { field: 'managername', title: '销售管家',width:150,align:"center"},
                  { field: 'rechargevipcode', title: '充值VIP等级',width:150,align:"center"},
                  { field: 'identification', title: '身份证号',width:150,align:"center"},
                  { field: 'departmentname', title: '部门',width:150,align:"center"},
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
                  { field: 'addtime', title: '添加日期',width:150,align:"center",sortable:"true"},
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
	 		department:$("#department").val(),
	 		employeecode:$("#employeecode").val(),
	 		employeename:$("#employeename").val(),
	 		phone:$("#phone").val(),
	 		rechargevipcode:$('#rechargevipcode').combobox('getValue'),
	 		parentemployeecode:$("#parentemployeecode").val(),
	 		employeetype:$("#employeetype").val(),
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
    	     $.dialog.tips('请选择需要修改的销售员', 2, 'alert.gif');
		     return;
         }
		
         //获取销售员的ID
        id = selected.id;
         
	    dialog = $.dialog({
		    title: '销售员修改',
		    lock: true,
		    width: 1000,
		    height: 580,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    esc: false,
		    cancel:false,
		    content: 'url:employee/updateInit?rid='+ Math.random()+'&id='+id+'&jumpurl=forSaler'
		});
	 }
	 
	 function addEmployee() {	
	    dialog = $.dialog({
		    title: '销售员添加',
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
		    content: 'url:employee/addSalerInit?rid='+Math.random()+'&jumpurl=forSaler'
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
   	     $.dialog.tips('请选择需要删除的销售员', 2, 'alert.gif');
		     return;
        }
		
        //获取销售员的ID
       id = selected.id;
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="employee/delete?rid="+Math.random()+'&jumpurl=forSaler';
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 2, 'alert.gif');
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
    
	var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '上级销售员查询',
			lock : true,
			width : 1000,
			height : 480,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:employee/findSalerListDialog?rid='+Math.random()
		});
	}
	
	function closeEmployeeDialog(jsonStr) {
		employeeDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
	    
	    $("#parentemployeecode").val(jsonObject.employeecode);
		$("#parentemployeename").val(jsonObject.employeename);
	}
	
	function cleanEmployee() {
		  $('#parentemployeecode').val("");
		  $('#parentemployeename').val("");
	  }
	
	//加载充值VIP
	$('#rechargevipcode').combobox({    
	    url:'rechargevip/getRechargevipComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${employee.rechargevipcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
	    	queryEmployee();
        } 
	}); 
	
	
	function saleExtend() {
		var selected = $('#employeeDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的销售员', 2, 'alert.gif');
		     return;
        }
        var salercode = selected.employeecode;
        var url = "<%=request.getContextPath()%>/sale/saleExtendInit?rid="+ Math.random()+"&salercode="+salercode;
        window.open(url);
	}
	
	function mobilebuniss() {
        var url = "<%=request.getContextPath()%>/mobilebusiness/initLogin";
        window.open(url);
	}
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryEmployee();
	    }  
	});
</script>
</body>
</html>