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
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">安装地址：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="address" id="address" value="${user.address }">
		    		</td>
		    		<td align="right">状态：</td>
        			<td width="20%">
        				<select id="status" name="status" class="select" onchange="queryUser();">
			                   <option value=""  <c:if test="${user.status == ''}">selected</c:if>>请选择</option>
			                   <option value="0" <c:if test="${user.status == '0' }">selected</c:if>>未处理</option>
			                   <option value="1" <c:if test="${user.status == '1' }">selected</c:if>>安装进行中</option>
			                   <option value="2" <c:if test="${user.status == '2' }">selected</c:if>>已安装完成</option>
			             </select>
        			</td>
					<td colspan="2">
						<a href="javascript:queryUser();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "userDG" style="width:100%; height: 400px"></div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
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
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              //fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              frozenColumns:[[
								{ field: 'id', title: '操作', width:80, align:"center",
									formatter:function(val, row, index){ 
									 	var formatter = '<a class="btn-look" href="javascript:selectUser('+ index +')">选择</a>';
										return formatter;
			                  	    },
								},
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
	 	};
	 	return json;
	 }
	
	function queryUser() {
		$('#userDG').datagrid('reload',paramsJson());
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
    
</script>
</body>
</html>