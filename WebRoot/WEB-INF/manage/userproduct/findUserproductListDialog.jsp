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
    <div class="seh-box">
        <form action="" method="post" id="searchForm" name="searchForm">
            <table width="100%">
		    	<tr>
		    		<td align="right" width="120px">产品标识：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="productidentfy" id="productidentfy" value="${userproduct.productidentfy }">
		    		</td>
					<td align="right" width="130">
        				<input type="button" class="btn-sch" value="查询" onclick="queryUserproduct();"/>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "userproductDG" style="width:100%; height: 280px">
    	</div>
	</div>
    </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initUserproductDatagrid(){
         $('#userproductDG').datagrid({
              title: '潜在客户列表信息',
              queryParams: paramsJson(),
              url:'userproduct/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'productidentfy', title: '产品标识',width:100,align:"center"},
                  { field: 'username', title: '客户姓名',width:100,align:"center",},
                  { field: 'phone', title: '联系电话',width:100,align:"center",},
                  { field: 'usersex', title: '客户性别',width:100,align:"center",
                  		    formatter:function(val, row, index){ 
							 	if(val == '0'){
							 		return "女";
							 	}else if(val == '1'){
							 		return "男";
							 	}
                  	        },
                  },
                  { field: 'source', title: '客户来源',width:100,align:"center",
                  		    formatter:function(val, row, index){ 
							 	if(val == '0'){
							 		return "销售";
							 	}else if(val == '1'){
							 		return "400电话";
							 	}
                  	        },
                  },
                  { field: 'address', title: '安装地址',width:100,align:"center",},
                  { field: 'remark', title: '选择' ,width:100,align:"center",
                  		    formatter:function(val, row, index){ 
							 	var formatter = '<a class="btn-look" href="javascript:selectUserproduct('+ index +')">选择</a>';
								return formatter;
	                  	    },
                  },
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		userproductname:$("#userproductname").val(),
	 	};
	 	return json;
	 }
	
	function queryUserproduct() {
		$('#userproductDG').datagrid('reload',paramsJson());
	}
	
	function selectUserproduct(index){
	 	//先选中该行
	 	$("#userproductDG").datagrid('selectRow',index);
	 	
	 	//获取选中的行
	 	var row = $("#userproductDG").datagrid('getSelected');
	 	
	 	//将row转换成json字符串
	 	var event = JSON.stringify(row);
	 	//中文进行编码转换，防止乱码
		var o = JSON.parse(event); 
		//将Json字符串赋值给event
		eval("var event = '"+JSON.stringify(o)+"';"); 
		parent.closeUserproductDialog(event);
     }
	
	
	$(function () {
	   
	   //初始化潜在客户列表
	   initUserproductDatagrid();
		
       var returninfo = '${userproduct.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>