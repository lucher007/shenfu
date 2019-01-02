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
		    			<input type="text"  class="inp w200" name="username" id="username" value="${sale.username }">
		    		</td>
		    		<td align="right" width="10%">电话号码：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${sale.phone }">
		    		</td>
		    		<td align="right" width="120px">销售单号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="saleno" id="saleno" value="${sale.saleno }">
		    		</td>
		    		<td width="130" align="right">
		    			<input type="button" class="btn-add" value="添加" onclick="addSale();"/>
		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">安装地址：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="address" id="address" value="${user.address }">
		    		</td>
		    		<td align="right">状态：</td>
        			<td colspan="3">
        				<select id="status" name="status" class="select" onchange="querySale();">
			                   <option value=""  <c:if test="${user.status == ''}">selected</c:if>>请选择</option>
			                   <option value="0" <c:if test="${user.status == '0' }">selected</c:if>>洽谈中</option>
			                   <option value="1" <c:if test="${user.status == '1' }">selected</c:if>>洽谈完成</option>
			                   <option value="2" <c:if test="${user.status == '2' }">selected</c:if>>生成任务单</option>
			             	   <option value="3" <c:if test="${user.status == '3' }">selected</c:if>>洽谈失败</option>
			             	   <option value="4" <c:if test="${user.status == '4' }">selected</c:if>>销售失败</option>
			             	   <option value="5" <c:if test="${user.status == '5' }">selected</c:if>>销售完成</option>
			             </select>
        			</td>
					<td align="right" width="130">
        				<input type="button" class="btn-sch" value="查询" onclick="querySale();"/>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "saleDG" style="width:auto; height: 400px">
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
	
	function initSaleDatagrid(){
         $('#saleDG').datagrid({
              title: '销售单列表信息',
              queryParams: paramsJson(),
              url:'sale/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'saleno', title: '销售单号',width:80,align:"center"},
                  { field: 'username', title: '客户姓名',width:60,align:"center",},
                  { field: 'phone', title: '电话号码',width:80,align:"center",},
                  { field: 'address', title: '安装地址',width:200,align:"center",
	                	    formatter: function (value) {
			                    return "<span title='" + value + "'>" + value + "</span>";
			                },
                  },
                  { field: 'addtime', title: '添加时间',width:100,align:"center",
                  	        formatter:function(val, row, index){ 
							 	return val==null?val:val.substring(0,19);
                  	        },
                  },
                  { field: 'salername', title: '销售人员',width:60,align:"center",},
                  { field: 'status', title: '状态',width:60,align:"center",
                  		    formatter:function(val, row, index){ 
							 	if(val == '0'){
							 		return "洽谈中";
							 	}else if(val == '1'){
							 		return "洽谈完成";
							 	}else if(val == '2'){
							 		return "生成任务单";
							 	}else if(val == '3'){
							 		return "洽谈失败";
							 	}else if(val == '4'){
							 		return "销售失败";
							 	}else if(val == '5'){
							 		return "销售完成";
							 	}
                  	        },
                  },
                  { field: 'id', title: '操作' ,width:80,align:"center",
                  		    formatter:function(val, row, index){ 
							 	var id = row.id;
							 	var content = row.content;
							 	var saleno = row.saleno;
							 	if(row.status == '0' || row.status == '1'){
							 		var updateContent = '<a class="btn-edit" href="javascript:updateSale('+ id +');">修改</a>';
								 	var deleteContent = '<a class="btn-del" href="javascript:deleteSale('+ id +');" >删除</a>';
							        return  updateContent + deleteContent;
							 	}else if(row.status == '3'){
							 		var updateContent = '<a class="btn-look" href="javascript:lockContent(\''+ content +'\');">失败原因</a>';
	    							return  updateContent;
							 	}else{
							 		var updateContent = '<a class="btn-look" href="javascript:lockUserbusiness(\''+ saleno +'\');">进度查看</a>';
	    							return  updateContent;
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
	 		saleno:$("#saleno").val(),
	 		address:$("#address").val(),
	 		status:$("#status").val(),
	 	};
	 	return json;
	 }
	
	function querySale() {
		$('#saleDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateSale(id) {
	    dialog = $.dialog({
		    title: '销售单修改',
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
		    content: 'url:sale/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addSale() {	
	    dialog = $.dialog({
		    title: '销售单添加',
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
		    content: 'url:sale/addInit?rid='+Math.random()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#saleDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteSale(id){
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="sale/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#saleDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化销售单列表
	   initSaleDatagrid();
		
       var returninfo = '${sale.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
    
    /**
	*删除
	*/
	function completeSale(id){
		
		$.dialog.confirm('确认是否生成任务单？', function(){ 
			var data = {
					     id: id
					   };
			var url="sale/saveComplete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#saleDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
    
	function lockContent(content) {
	    dialog = $.dialog({
		    title: '洽谈失败原因',
		    lock: true,
		    width: 500,
		    height: 50,
		    top: 0,
		    drag: false,
		    resize: false,
		    content:content,
		});
	 }
     
	
	function lockUserbusiness(saleno) {
	    dialog = $.dialog({
		    title: '安装进度详情',
		    lock: true,
		    width: 1000,
		    height: 580,
		    top: 0,
		    drag: false,
		    resize: false,
		    content: 'url:userbusiness/findByList?rid='+ Math.random()+'&saleno='+saleno
		});
	 }
</script>
</body>
</html>