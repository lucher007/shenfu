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
	<form action="" method="post" id="addForm" name="addForm">
   		<div class="seh-box">
        	<input type="hidden" id="jumpurl" name = "jumpurl" value="autoBatchAdd">
            <table width="100%">
		    	<tr>
		    		<td align="right" width="10%">客户姓名：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="username" id="username" value="${user.username }">
		    		</td>
		    		<td align="right" width="10%">电话号码：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${user.phone }">
		    		</td>
		    		<td align="right" width="10%">来源：</td>
		    		<td width="20%">
		    			<select id="source" name="source" class="select" onchange="queryUser();">
		    				<option value=""  <c:if test="${user.source == ''}">selected</c:if>>请选择</option>
							<option value="0" <c:if test="${user.source == '0'}">selected</c:if>>销售</option>
							<option value="1" <c:if test="${user.source == '1'}">selected</c:if>>400客服</option>
						</select>
		    		</td>
		    		<td align="right" width="130">
		    			<a href="javascript:queryUser();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">安装地址：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="address" id="address" value="${user.address }">
		    		</td>
		    		<td align="right">状态：</td>
        			<td >
        				<select id="status" name="status" class="select" onchange="queryUser();">
			                  <!-- 
			                   <option value=""  <c:if test="${user.status == ''}">selected</c:if>>请选择</option>
			                    -->
			                   <option value="0" <c:if test="${user.status == '0' }">selected</c:if>>未处理</option>
			                   <!--
			                   <option value="1" <c:if test="${user.status == '1' }">selected</c:if>>已派销售单</option>
			                   <option value="2" <c:if test="${user.status == '2' }">selected</c:if>>已派任务单</option>
			             	  -->
			             </select>
        			</td>
        			<td align="right">上门类型：</td>
        			<td >
        				<select id="visittype" name="visittype" class="select" onchange="queryUser();">
			                   <option value="1" <c:if test="${user.visittype == '1' }">selected</c:if>>销售员亲自讲解测量</option>
			             </select>
        			</td>
		    	</tr>
   		    </table>
    </div>
    <div id = "userDG" style="width:100%; height: 400px"></div>
    <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:saveTask('add');" class="easyui-linkbutton" iconCls="icon-ok"  plain="true">转成任务单</a>
		</div>
	</div>
	<div class="form-box">
		<div class="fb-bom">
			<cite> 
				<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
			</cite> 
			<span class="red">${usertask.returninfo}</span>
		</div>
	</div>
</form>
</div>    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initUserDatagrid(){
         $('#userDG').datagrid({
              title: '潜在客户列表信息',
              queryParams: paramsJson(),
              url:'user/findListJson',
              pagination: true,
              singleSelect: false,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              //fitColumns:true,
              idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              toolbar:'#tools',
              frozenColumns:[[
                            { field: 'id', title: '全选',checkbox:true,align:"center",width:100 },
      	                    { field: 'usercode', title: '客户编号',width:150,align:"center"},
      	                    { field: 'username', title: '客户姓名',width:150,align:"center"},
      	                    { field: 'phone', title: '联系电话',width:100,align:"center"},       
                       ]],
                    columns: [[
      	                    { field: 'visittypename', title: '上门类型',width:150,align:"center"},
      		                { field: 'sourcename', title: '客户来源',width:150,align:"center"},
      		                { field: 'salercode', title: '销售员编号',width:150,align:"center"},
      		                { field: 'salername', title: '销售员姓名',width:150,align:"center"},
      		                { field: 'statusname', title: '状态',width:150,align:"center"},
      		                { field: 'addtime', title: '添加时间',width:150,align:"center"},
      		                { field: 'address', title: '安装地址',width:300,align:"center",
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
		 		source:$("#source").val(),
		 		address:$("#address").val(),
		 		status:$("#status").val(),
		 		visittype:$("#visittype").val(),
	 	};
	 	return json;
	 }
	
	function queryUser() {
		$('#userDG').datagrid('reload',paramsJson());
		$('#userDG').datagrid('clearChecked');//清空选中的值
	}
	
	function selectUser(index){
	    //先选中该行
	 	$("#userDG").datagrid('selectRow',index);
	 	
	 	//获取选中的行
	 	var row = $("#userDG").datagrid('getSelected');
	 	
	 	//将row转换成json字符串
	 	var event = JSON.stringify(row);
	 	//中文进行编码转换，防止乱码
		var o = JSON.parse(event); 
		//将Json字符串赋值给event
		eval("var event = '"+JSON.stringify(o)+"';"); 
		
		parent.closeUserDialog(event);
     }
	
	$(function () {
	   
	   //初始化潜在客户列表
	   initUserDatagrid();
		
       var returninfo = '${user.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	
	var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '员工信息',
			lock : true,
			width : 900,
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
	    
	    $("#visitorcode").val(jsonObject.employeecode);
		$("#visitorname").val(jsonObject.employeename);
	}
	
	function cleanEmployee() {
		  $('#visitorcode').val("");
		  $('#visitorname').val("");
	}
	
	function goback(){
		parent.closeDialog();
	}
	
	function saveTask() {
		
		 //获取所有选择的产品
	     var rowsSelected = $("#userDG").datagrid("getChecked");
		 
	     if(rowsSelected == null  || rowsSelected == ''){
        	 $.dialog.tips('请选择某一条记录', 2, 'alert.gif');
		     return;
        }
		
		 //获取选择的对象
	    var usercodearr = [];
	    for(var i=0; i<rowsSelected.length; i++){
	    	usercodearr.push(rowsSelected[i].usercode);
			
	    }
	    var usercodes = usercodearr.join(',');
	    
	    $.dialog({
		    title: '提交',
		    lock: true,
		    background: '#000', /* 背景色 */
		    opacity: 0.5,       /* 透明度 */
		    content: '是否确定执行？',
		    icon: 'alert.gif',
		    ok: function () {
		    	$("#addForm").attr("action", "task/save?usercodes="+usercodes+"&rid="+Math.random());
				$("#addForm").submit();
		        /* 这里要注意多层锁屏一定要加parent参数 */
		        $.dialog({
		        	lock: true,
		            title: '提示',
		        	content: '执行中，请等待......', 
		        	max: false,
				    min: false,
				    cancel:false,
		        	icon: 'loading.gif',
		        });
		        return false;
		    },
		    okVal: '确定',
		    cancel: true,
		    cancelVal:'取消'
		});
	}
</script>
</body>
</html>