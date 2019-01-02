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
		    		<td align="right" width="120px">订单号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="orderno" id="orderno" value="${userorder.orderno }">
		    		</td>
					<td align="right" width="130">
        				<input type="button" class="btn-sch" value="查询" onclick="queryUserorder();"/>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "userorderDG" style="width:auto; height: 280px">
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
	
	function initUserorderDatagrid(){
         $('#userorderDG').datagrid({
              title: '订单列表信息',
              queryParams: paramsJson(),
              url:'userorder/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'orderno', title: '订单号',width:100,align:"center"},
                  { field: 'username', title: '客户姓名',width:100,align:"center",
                  			formatter:function(val, row, index){ 
							 	return row.user.username;
                  	        },
                  },
                  { field: 'address', title: '安装地址',width:100,align:"center",
                  			formatter:function(val, row, index){ 
							 	return row.user.address;
                  	        },
                  },
                  { field: 'phone', title: '联系电话',width:100,align:"center",
                  			formatter:function(val, row, index){ 
							 	return row.user.phone;
                  	        },
                  },
                  { field: 'addtime', title: '添加时间',width:100,align:"center",
                  	        formatter:function(val, row, index){ 
							 	return val==null?val:val.substring(0,19);
                  	        },
                  },
                  { field: 'employeename', title: '销售人员',width:100,align:"center",
                  			formatter:function(val, row, index){ 
							 	return row.sale.employeename;
                  	        },
                  },
                  { field: 'remark', title: '选择' ,width:100,align:"center",
                  		    formatter:function(val, row, index){ 
							 	var info = row.id 
							 	         + "-&-" +row.orderno
							 	         + "-&-" +row.userid
							 			 + "-&-" +row.user.username
							 	         + "-&-" +row.user.phone
							 	         + "-&-" +row.user.address
							 	         + "-&-" +row.sale.employeename
							 	         + "-&-" +row.sale.phone
							 	         + "-&-" +row.productidentfy
							 	         + "-&-" +row.productdepot.productname
							 	         + "-&-" +row.totalmoney
							 	         + "-&-" +row.paidmoney
							 	         + "-&-" +row.paytype
							 	         + "-&-" +row.unpaidmoney;
							 	var formatter = '<a class="btn-look" href="javascript:selectUserorder(\''+ info +'\')">选择</a>';
								return formatter;
	                  	    },
                  },
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		orderno:$("#orderno").val(),
	 	};
	 	return json;
	 }
	
	function queryUserorder() {
		$('#userorderDG').datagrid('reload',paramsJson());
	}
	
	function selectUserorder(value){
	 	var selArray=value.split("-&-");
		parent.closeOrderDialog(selArray);
     }
	
	
	$(function () {
	   
	   //初始化潜在客户列表
	   initUserorderDatagrid();
		
       var returninfo = '${userorder.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>