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
    <div class="easyui-panel" title="查询条件" style="width:auto;">
         <form action="" method="post" id="searchForm" name="searchForm">
         <input type="hidden"  name="ids" id="ids">
            <table style="width: 100%">
		    	<tr>
		    	    <td align="right">小区名称：</td>
		    		<td>
		    			<input type="text"  class="inp" name="cellname" id="cellname" value="${cellextend.cellname }">
		    		</td>
		    		<td align="right">小区地址：</td>
		    		<td>
		    			<input type="text"  class="inp" name="address" id="address" value="${cellextend.address }">
		    		</td>
		    		<td align="right">
						<a href="javascript:queryCellextend();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
				</tr>
				<tr>
		    	    <td align="right">是否接收：</td>
		    		<td>
		    			<select id="acceptflag" name="acceptflag" class="select" onchange="queryCellextend();">
		    			    <option value=""  <c:if test="${cellextend.acceptflag == ''}">selected</c:if>>请选择</option>
		    			    <option value="0" <c:if test="${cellextend.acceptflag == '0' }">selected</c:if>>未接收</option>
			                <option value="1" <c:if test="${cellextend.acceptflag == '1' }">selected</c:if>>已接收</option>
			             </select>
		    		</td>
		    		<td align="right">是否支付：</td>
		    		<td>
		    			<select id="payflag" name="payflag" class="select" onchange="queryCellextend();">
		    				<option value=""  <c:if test="${cellextend.payflag == ''}">selected</c:if>>请选择</option>
		    			    <option value="0" <c:if test="${cellextend.payflag == '0' }">selected</c:if>>未支付</option>
			                <option value="1" <c:if test="${cellextend.payflag == '1' }">selected</c:if>>已支付</option>
			            </select>
		    		</td>
				</tr>
   		    </table>
   		</form>
    </div>
    <div id = "cellextendDG" style="width:auto; height: 400px"></div>
	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addCellextend();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">增加小区发布</a>
			<a href="javascript:updateCellextendprice();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">扫楼费用修改</a>
			<a href="javascript:deleteCellextend();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
		</div>
	</div>
    </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initCellextendDatagrid(){
         $('#cellextendDG').datagrid({
              title: '扫楼发布列表信息',
              queryParams: paramsJson(),
              url:'cellextend/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              toolbar:'#tools',
              columns: [[
                  { field: 'extendcode', title: '发布编号',width:100,align:"center",},
                  { field: 'cellname', title: '小区名称',width:100,align:"center",
                	  formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                },
                  },
                  { field: 'address', title: '小区地址',width:200,align:"center",
                	  formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                },
                  },
                  { field: 'totalhouse', title: '总户数',width:100,align:"center",},
                  { field: 'price', title: '每户单价(元/户)',width:100,align:"center",},
                  { field: 'totalmoney', title: '扫楼总价(元)',width:100,align:"center",},
                  { field: 'acceptercode', title: '接收人编号',width:100,align:"center",},
                  { field: 'acceptername', title: '接收人姓名',width:100,align:"center",},
                  { field: 'payflagname', title: '支付状态',width:100,align:"center",},
                  { field: 'stationflagname', title: '是否申请驻点',width:100,align:"center",},
              ]]
          });
	}

	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		cellname:$.trim($("#cellname").val()),
	 		address:$.trim($("#address").val()),
	 		acceptflag:$.trim($("#acceptflag").val()),
	 		payflag:$.trim($("#payflag").val()),
	 	};
	 	return json;
	 }
	
	function queryCellextend() {
		$('#cellextendDG').datagrid('reload',paramsJson());
	}
	
	 
	function addCellextend() {
		 parent.addTab('小区发布添加','cellextend/addInit?rid='+ Math.random());
	}
	
	function updateCellextendprice() {
		var selected = $('#cellextendDG').datagrid("getSelected");
        if(selected == null){
  	     	$.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        var payflag = selected.payflag;
        if(payflag != '0'){
        	$.dialog.tips('该记录已经支付成功了，不能修改扫楼费用', 2, 'alert.gif');
		     return;
        }
        
        //获取需要修改项的ID
        var id = selected.id;
		 parent.addTab('扫楼费用修改','cellextend/updateInit?rid='+ Math.random()+'&id='+id);
	}
    
	function closeDialog(){
		dialog.close();
		$('#cellextendDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteCellextend(){
		
		var selected = $('#cellextendDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要删除的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
        
        //接收人
        var acceptercode = selected.acceptercode;
        if(acceptercode != null && acceptercode != ''){
        	$.dialog.tips('请小区发布已经接收，不能删除', 2, 'alert.gif');
		     return;
        }
        
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="cellextend/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#cellextendDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化小区列表
	   initCellextendDatagrid();
		
       var returninfo = '${cellextend.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	/**
	*批量删除
	*/
	function deleteBatch(){
		
		getChecked();

		if ($("#ids").val() == "") {
			$.dialog.tips(
					'请选择需要删除的小区', 1,
					'alert.gif');
			return;
		}
		
		$.dialog.confirm('确认是否批量删除？', function(){ 
			var data = {
					     ids: $("#ids").val(),
					   };
			var url="cell/deleteBatch?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#cellextendDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	
	function getChecked(){
		var ids = [];
		var rows = $('#cellextendDG').datagrid('getChecked');
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].id);
		}
		$("#ids").val(ids.join(','));
	}
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryCellextend();
	    }  
	});
</script>
</body>
</html>