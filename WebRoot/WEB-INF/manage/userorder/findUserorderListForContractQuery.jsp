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
		    		<td align="right" width="10%">客户姓名：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="username" id="username" value="${userorder.username }">
		    		</td>
		    		<td align="right" width="10%">电话号码：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${userorder.phone }">
		    		</td>
		    		<td align="right" width="120px">订单号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="orderno" id="orderno" value="${userorder.orderno }">
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">安装地址：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="address" id="address" value="${userorder.address }">
		    		</td>
		    		<td align="right" width="120px">状态：</td>
		    		<td>
		    			<select id="status" name="status" class="select" onchange="queryUserorder();">
		    			    <option value="" <c:if test="${userorder.status == '' }">selected</c:if>>请选择</option>
		    			    <option value="0" <c:if test="${userorder.status == '0' }">selected</c:if>>新生成</option>
			                <option value="1" <c:if test="${userorder.status == '1' }">selected</c:if>>首款到账</option>
			                <option value="2" <c:if test="${userorder.status == '2' }">selected</c:if>>已发货</option>
			             	<option value="3" <c:if test="${userorder.status == '3' }">selected</c:if>>已派工</option>
			             	<option value="4" <c:if test="${userorder.status == '4' }">selected</c:if>>派工接收</option>
			             	<option value="5" <c:if test="${userorder.status == '5' }">selected</c:if>>安装完成</option>
			             	<option value="6" <c:if test="${userorder.status == '6' }">selected</c:if>>尾款到账</option>
			             	<option value="7" <c:if test="${userorder.status == '7' }">selected</c:if>>审核结单</option>
			             </select>
		    		</td>
		    		<td align="right">来源：</td>
        			<td>
        				<select id="source" name="source" class="select" onchange="queryUserorder();">
			                   <option value=""  <c:if test="${userorder.status == ''}">selected</c:if>>请选择</option>
			                   <option value="0" <c:if test="${userorder.status == '0' }">selected</c:if>>销售</option>
			                   <option value="1" <c:if test="${userorder.status == '1' }">selected</c:if>>400客服</option>
			             </select>
        			</td>
					<td align="right" width="130">
        				<input type="button" class="btn-sch" value="查询" onclick="queryUserorder();"/>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "userorderDG"    style="width:auto; height: 400px">
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
                  { field: 'orderno', title: '订单号',width:80,align:"center"},
                  { field: 'username', title: '客户姓名',width:60,align:"center",},
                  { field: 'phone', title: '联系电话',width:100,align:"center",},
                  { field: 'address', title: '安装地址',width:150,align:"center",
                	  	formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                },
                  },
                  
                  { field: 'addtime', title: '添加时间',width:100,align:"center",
                  	        formatter:function(val, row, index){ 
							 	return val==null?val:val.substring(0,19);
                  	        },
                  },
                  { field: 'researchername', title: '勘察人员',width:60,align:"center",},
                  { field: 'id', title: '订单详情' ,width:60,align:"center",
                		    formatter:function(val, row, index){ 
							 	var orderid = row.id;
							 	var lookContent = '<a class="btn-look" href="javascript:lookUserorder('+ orderid +');">查看</a>';
						        return  lookContent;
							    
	                  	    },
                  },
                  { field: 'contractno', title: '合同号' ,width:120,align:"center",
	          		    formatter:function(val, row, index){ 
	          		    	if(val != null && val != ''){
        				    	var uploadContent = '<a class="btn-look" href="usercontract/lookUsercontract?id='+ row.contractid +'">'+val+'</a>';
        				    	return uploadContent;
        				    }
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
		 		orderno:$("#orderno").val(),
		 		address:$("#address").val(),
		 		status:$("#status").val(),
		 		source:$("#source").val(),
	 	};
	 	return json;
	 }
	
	function queryUserorder() {
		$('#userorderDG').datagrid('reload',paramsJson());
	}
  
	function closeDialog(){
		dialog.close();
		$('#userorderDG').datagrid('reload');// 刷新当前页面
	}
	
	$(function () {
	   //初始化订单列表
	   initUserorderDatagrid();
		
       var returninfo = '${userorder.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	function lookUserorder(id) {
	    dialog = $.dialog({
		    title: '订单信息',
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
		    content: 'url:userorder/lookInit?rid='+ Math.random()+'&id='+id
		});
	 }
    
    /**
	*生成合同
	*/
	function createContact(id){
		
		$.dialog.confirm('确认是否生成合同？', function(){ 
			var data = {
					orderid: id
					   };
			var url="usercontract/saveCreateContract?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#userorderDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
    
	/**
	*重新生成合同
	*/
	function recreateContact(id){
		
		$.dialog.confirm('重新生成合同会覆盖旧合同信息，确认是否重新生成合同？', function(){ 
			var data = {
					orderid: id
					   };
			var url="usercontract/saveCreateContract?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#userorderDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
</script>
</body>
</html>