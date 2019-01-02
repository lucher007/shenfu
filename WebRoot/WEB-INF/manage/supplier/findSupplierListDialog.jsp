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
		    	    <td align="right">供应商名称：</td>
		    		<td>
		    			<input type="text"  class="inp" name="fullname" id="fullname" value="${supplier.fullname }">
		    		</td>
		    		<td align="right">简称：</td>
		    		<td>
		    			<input type="text"  class="inp" name="name" id="name" value="${supplier.name }">
		    		</td>
				</tr>
				<tr>
		    	    <td align="right">联系人：</td>
		    		<td>
		    			<input type="text"  class="inp" name="contactname" id="contactname" value="${supplier.contactname }">
		    		</td>
		    		<td align="right">联系电话：</td>
		    		<td>
		    			<input type="text"  class="inp" name="contactphone" id="contactphone" value="${supplier.contactphone }">
		    		</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<a href="javascript:querySupplier();" class="easyui-linkbutton" style="width:120px">查询</a>                                                                                                                                                                                  
					</td>				
				</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "supplierDG" style="width:100%; height: 280px">
    	</div>
	</div>
    </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initSupplierDatagrid(){
         $('#supplierDG').datagrid({
              title: '潜在客户列表信息',
              queryParams: paramsJson(),
              url:'supplier/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'name', title: '供应商简称',width:100,align:"center"},
                  { field: 'fullname', title: '供应商全称',width:100,align:"center"},
                  { field: 'contactname', title: '联系人' ,width:100,align:"center"},
                  { field: 'contactphone', title: '联系电话',width:100,align:"center"},
                  { field: 'contactmail', title: '邮箱',width:100,align:"center"},
                  { field: 'address', title: '地址',width:200,align:"center",
                	    formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                },
                  },
                  { field: 'remark', title: '选择' ,width:60,align:"center",
                  		    formatter:function(val, row, index){ 
							 	var formatter = '<a class="btn-look" href="javascript:selectSupplier('+ index +')">选择</a>';
								return formatter;
	                  	    },
                  },
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 			fullname:$("#fullname").val(),
		 		name:$("#name").val(),
		 		contactname:$("#contactname").val(),
		 		contactphone:$("#contactphone").val(),
	 	};
	 	return json;
	 }
	
	function querySupplier() {
		$('#supplierDG').datagrid('reload',paramsJson());
	}
	
	function selectSupplier(index){
	 	//先选中该行
	 	$("#supplierDG").datagrid('selectRow',index);
	 	
	 	//获取选中的行
	 	var row = $("#supplierDG").datagrid('getSelected');
	 	
	 	//将row转换成json字符串
	 	var event = JSON.stringify(row);
	 	//中文进行编码转换，防止乱码
		var o = JSON.parse(event); 
		//将Json字符串赋值给event
		eval("var event = '"+JSON.stringify(o)+"';"); 
		parent.closeSupplierDialog(event);
     }
	
	
	$(function () {
	   
	   //初始化潜在客户列表
	   initSupplierDatagrid();
		
       var returninfo = '${supplier.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>