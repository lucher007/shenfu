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
		    		<td align="right" width="120px">状态：</td>
		    		<td>
		    			<select id="state" name="state" class="select" onchange="querySalelevelpara();">
		    			    <option value="" <c:if test="${salelevelpara.level == '' }">selected</c:if>>请选择</option>
			                <option value="0" <c:if test="${salelevelpara.level == '0' }">selected</c:if>>无效</option>
			                <option value="1" <c:if test="${salelevelpara.level == '1' }">selected</c:if>>有效</option>
			             </select>
		    		</td>
					<td align="right" width="130">
        				<input type="button" class="btn-sch" value="查询" onclick="querySalelevelpara();"/>
   		    		</td>
        			<!--
		    		<td width="130" align="right">
		    			<input type="button" class="btn-add" value="添加" onclick="addSalelevelpara();"/>
		    		</td>
		    		-->
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "salelevelparaDG" style="width:auto; height: 400px">
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
	
	function initSalelevelparaDatagrid(){
         $('#salelevelparaDG').datagrid({
              title: '时间参数列表信息',
              queryParams: paramsJson(),
              url:'salelevelpara/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'level', title: '销售等级',width:100,align:"center",},
                  { field: 'minscore', title: '最小对应积分（包含等于）',width:100,align:"center",},
                  { field: 'maxscore', title: '最大对应积分（不包含等于）',width:100,align:"center",},
                  { field: 'reduce', title: '对应等级每天减少量',width:100,align:"center",},
                  { field: 'state', title: '状态',width:100,align:"center",
                	  	formatter:function(val, row, index){ 
						 	if(val=='0'){
						 		return "无效";
						 	}else if (val=='1'){
						 		return "有效";
						 	}
                	    },
                  },
                  { field: 'id', title: '操作' ,width:100,align:"center",
                  		    formatter:function(val, row, index){ 
							 	var id = row.id;
						 		var updateContent = '<a class="btn-edit" href="javascript:updateSalelevelpara('+ id +');">修改</a>';
						        return  updateContent;
	                  	    },
                  },
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 			state:$("#state").val(),
	 	};
	 	return json;
	 }
	
	function querySalelevelpara() {
		$('#salelevelparaDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateSalelevelpara(id) {
	    dialog = $.dialog({
		    title: '销售等级参数修改',
		    lock: true,
		    width: 1000,
		    height: 500,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    esc: false,
		    cancel:false,
		    content: 'url:salelevelpara/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#salelevelparaDG').datagrid('reload');// 刷新当前页面
	}
	
	$(function () {
	   //初始化任务单列表
	   initSalelevelparaDatagrid();
       var returninfo = '${salelevelpara.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>