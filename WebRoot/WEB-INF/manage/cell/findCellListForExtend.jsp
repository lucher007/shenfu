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
		    		<td align="right">发布状态：</td>
		    		<td>
		    			<select id="extendflag" name="extendflag" class="select" onchange="queryCell();">
		    			    <option value="0" <c:if test="${cell.extendflag == '0' }">selected</c:if>>未发布</option>
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
						<a href="javascript:extendCell();" class="easyui-linkbutton" style="width:120px">发布</a> 
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
              fitColumns:true,
              idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
				  { field: 'id', title: '全选',checkbox:true,align:"center",width:100,},       
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
                  { field: 'building', title: '楼栋',width:100,align:"center",
                	  formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                },
                  },
                  { field: 'price', title: '单价',width:100,align:"center"},
                  { field: 'floor', title: '楼层信息',width:100,align:"center",
                	  formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                },
                  },
                  { field: 'totalhouse', title: '总户数',width:100,align:"center",
                	  formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                },  
                  },
                  { field: 'layout', title: '户型',width:100,align:"center",
                	  formatter: function (value) {
		                    return "<span title='" + value + "'>" + value + "</span>";
		                },  
                  },
                  { field: 'safelevel', title: '安全等级',width:60,align:"center",
                  			formatter:function(val, row, index){ 
							 	if(val == '1'){
							 		return "高级";
							 	}else if(val == '2'){
							 		return "中级";
							 	}else if(val == '3'){
							 		return "低级";
							 	}else{
							 		return "未知";
							 	}
                  	        },
                  },
                  { field: 'opentime', title: '开盘时间',width:80,align:"center",
	          	        formatter:function(val, row, index){ 
							 	return val==null?val:val.substring(0,10);
	          	        },
		          },
		         { field: 'handtime', title: '交房时间',width:80,align:"center",
	          	        formatter:function(val, row, index){ 
							 	return val==null?val:val.substring(0,10);
	          	        },
			      },
                  { field: 'extendflag', title: '是否发布',width:60,align:"center",
            			formatter:function(val, row, index){ 
						 	if(val == '0'){
						 		return "未发布";
						 	}else if(val == '1'){
						 		return "已发布";
						 	}
            	        },
            	  },
            	  { field: 'acceptername', title: '接收人',width:60,align:"center",},
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
	
	function getChecked(){
		var ids = [];
		var rows = $('#cellDG').datagrid('getChecked');
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].id);
		}
		$("#ids").val(ids.join(','));
	}
	
	/**
	*删除
	*/
	function extendCell(){
		
		getChecked();

		if ($("#ids").val() == "") {
			$.dialog.tips(
					'请选择需要发布的小区', 1,
					'alert.gif');
			return;
		}
		
		$.dialog.confirm('确认是否发布？', function(){ 
			var data = {
					     ids: $("#ids").val(),
					   };
			var url="cell/saveExtendCell?rid="+Math.random();
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
    
</script>
</body>
</html>