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
		<form action="" method="post" id="searchForm" name="searchForm">
			<input type="hidden" name="userid" id="userid" value="${userbusiness.userid}"/>
			<input type="hidden" name="saleno" id="saleno" value="${userbusiness.saleno}"/>
			<input type="hidden" name="taskno" id="taskno" value="${userbusiness.taskno}"/>
			<input type="hidden" name="orderno" id="orderno" value="${userbusiness.orderno}"/>
			<div class="lst-box">
		    	<div id = "userbusinessDG" style="width:100%; height: 400px">
		    	</div>
			</div>
		</form>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initUserbusinessDatagrid(){
         $('#userbusinessDG').datagrid({
              title: '工程进度信息',
              queryParams: paramsJson(),
              url:'userbusiness/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
				  { field: 'addtime', title: '操作时间',width:80,align:"center",
					      formatter:function(val, row, index){ 
						 	return val==null?val:val.substring(0,19);
					      },
				  }, 
                  { field: 'businesstypename', title: '名称',width:80,align:"center"},
                  { field: 'content', title: '内容',width:300,align:"center",
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
	 		userid:$("#userid").val(),
	 		saleno:$("#saleno").val(),
	 		taskno:$("#taskno").val(),
	 		orderno:$("#orderno").val(),
	 	};
	 	return json;
	 }
	
	function queryUserorder() {
		$('#userbusinessDG').datagrid('reload',paramsJson());
	}
	
	$(function () {
	   
	   //初始化订单列表
	   initUserbusinessDatagrid();
		
       var returninfo = '${sale.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
	 
</script>
</body>
</html>