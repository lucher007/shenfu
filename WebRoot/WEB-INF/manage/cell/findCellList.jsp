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
    <div class="easyui-panel" title="查询条件" style="width:auto;">
         <form action="" method="post" id="searchForm" name="searchForm">
         <input type="hidden"  name="ids" id="ids">
            <table style="width: 100%">
		    	<tr>
		    	    <td align="right">小区名称：</td>
		    		<td>
		    			<input type="text"  class="inp" name="cellname" id="cellname" value="${cell.cellname }">
		    		</td>
		    		<td align="right">小区地址：</td>
		    		<td>
		    			<input type="text"  class="inp" name="address" id="address" value="${cell.address }">
		    		</td>
		    		<!--
		    		<td align="right">开发商：</td>
		    		<td>
		    			<input type="text"  class="inp" name="developer" id="developer" value="${cell.developer }">
		    		</td>
		    		  -->
		    		<td align="right">发布状态：</td>
		    		<td>
		    			<select id="extendflag" name="extendflag" class="select" onchange="queryCell();">
		    			    <option value=""  <c:if test="${cell.extendflag == ''}">selected</c:if>>请选择</option>
		    			    <option value="0" <c:if test="${cell.extendflag == '0' }">selected</c:if>>未发布</option>
			                <option value="1" <c:if test="${cell.extendflag == '1' }">selected</c:if>>已发布</option>
			             </select>
		    		</td>
				</tr>
				<tr>
		    	   <td align="right">开盘时间：从</td>
                   <td colspan="3">
                    	<input type="text" id="beginopentime" name="beginopentime"   value="${cell.beginopentime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150">
                    	到
                    	<input type="text" id="endopentime" name="endopentime"  value="${cell.endopentime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginopentime\');}'});" class="Wdate inp w150">
					</td>
					<td height="26"  align="right">开盘时间：从</td>
                    <td colspan="3">
                    	<input type="text" id="beginhandtime" name="beginhandtime"   value="${cell.beginhandtime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150">
                    	到
                    	<input type="text" id="endhandtime" name="endhandtime" value="${cell.endhandtime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginhandtime\');}'});" class="Wdate inp w150">
					</td>
				</tr>
				<tr>
					<td colspan="8" align="center">
						<a href="javascript:queryCell();" class="easyui-linkbutton" style="width:120px">查询</a>                                                                                                                                                                                  
						<a href="javascript:addCell();" class="easyui-linkbutton" style="width:120px">添加</a> 
						<a href="javascript:addCellByImportfile();" class="easyui-linkbutton" style="width:120px">文件导入</a> 
						<a href="javascript:deleteBatch();" class="easyui-linkbutton" style="width:120px">批量删除</a> 
					</td>				
				</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "cellDG" style="width:auto; height: 400px">
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
	
	function initCellDatagrid(){
         $('#cellDG').datagrid({
              title: '小区列表信息',
              queryParams: paramsJson(),
              url:'cell/findListJson',
              pagination: true,
              singleSelect: false,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              //fitColumns:true,
              idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              frozenColumns:[[
                              { field: 'id1', title: '全选',checkbox:true,align:"center",width:100,
    							  formatter: function(val, row, index){
    				                    return row.id;
    				                }, 
    						  },   
    						  { field: 'cellcode', title: '小区编号',width:120,align:"center",},
    		                  { field: 'cellname', title: '小区名称',width:200,align:"center",
    		                	  formatter: function (value) {
    				                    return "<span title='" + value + "'>" + value + "</span>";
    				                },
    		                  },
                         ]],
              columns: [[
		                  { field: 'address', title: '小区地址',width:300,align:"center",
		                	  formatter: function (value) {
				                    return "<span title='" + value + "'>" + value + "</span>";
				                },
		                  },
		                  { field: 'property', title: '物业',width:200,align:"center",},
		                  { field: 'advertisement', title: '小区广告',width:200,align:"center",},
		                  { field: 'allowstationname', title: '物业是否允许驻点',width:150,align:"center",},
		                  { field: 'building', title: '楼栋',width:80,align:"center",},
		                  { field: 'floor', title: '楼层信息',width:80,align:"center",},
		                  { field: 'totalhouse', title: '总户数',width:80,align:"center",},
		                  { field: 'layout', title: '户型',width:150,align:"center",},
		                  { field: 'safelevelname', title: '安防级别',width:80,align:"center",},
		                  { field: 'opentime', title: '开盘时间',width:80,align:"center",},
		            	  { field: 'handtime', title: '交房时间',width:80,align:"center",},
		            	  { field: 'extendflagname', title: '发布状态',width:80,align:"center",},
		                  { field: 'id', title: '操作' ,width:100,align:"center",
		                  			formatter:function(val, row, index){ 
									 	var id = row.id;
									 	var updateContent = '<a class="btn-edit" href="javascript:updateCell('+ id +');">修改</a>';
									 	var deleteContent = '<a class="btn-del" href="javascript:deleteCell('+ id +');" >删除</a>';
								        return  updateContent + deleteContent;
		                  	        },
		                  },
              ]]
          });
	}

	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		cellname:$.trim($("#cellname").val()),
	 		address:$.trim($("#address").val()),
	 		extendflag:$("#extendflag").val(),
	 		beginopentime:$("#beginopentime").val(),
	 		endopentime:$("#endopentime").val(),
	 		beginhandtime:$("#beginhandtime").val(),
	 		endhandtime:$("#endhandtime").val(),
	 	};
	 	return json;
	 }
	
	function queryCell() {
		$('#cellDG').datagrid('reload',paramsJson());
		$('#cellDG').datagrid('clearChecked');//清空选中的值
	}
	
	var dialog;
	function updateCell(id) {
	    dialog = $.dialog({
		    title: '小区修改',
		    lock: true,
		    width: 1000,
		    height: 550,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:cell/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addCell() {	
	    dialog = $.dialog({
		    title: '小区添加',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:cell/addInit?rid='+Math.random()
		});
	 }
    
	 function addCellByImportfile() {	
		    dialog = $.dialog({
			    title: '小区文件导入',
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
			    content: 'url:cell/addByImportfileInit?rid='+Math.random()
			});
	}
	 
	function closeDialog(){
		dialog.close();
		$('#cellDG').datagrid('reload');// 刷新当前页面
		$('#cellDG').datagrid('clearChecked');//清空选中的值
	}
	
	
	/**
	*删除
	*/
	function deleteCell(id){
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="cell/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#cellDG').datagrid('reload');// 刷新当前页面
					$('#cellDG').datagrid('clearChecked');//清空选中的值
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化小区列表
	   initCellDatagrid();
		
       var returninfo = '${cell.returninfo}';
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
					$('#cellDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	
	function getChecked(){
		var ids = [];
		var rows = $('#cellDG').datagrid('getChecked');
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].id);
		}
		$("#ids").val(ids.join(','));
	}
</script>
</body>
</html>