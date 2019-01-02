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
<link type="text/css" rel="stylesheet" href="js/plugin/poshytip/tip-yellowsimple/tip-yellowsimple.css">
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
            <table style="width:100%">
		    	<tr>
		    		<td height="26"  align="right">查询开始时间：</td>
                    <td>
                    	<input type="text" id="begintime" name="begintime"   value="${operatorlog.begintime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150">
                    </td>
                    <td align="right">查询结束时间：</td>
                    <td>
                    	<input type="text" id="endtime" name="endtime"  value="${operatorlog.endtime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'begintime\');}'});" class="Wdate inp w150">
					</td>
					<td align="right" width="130">
        				<input type="button" class="btn-sch" value="查询" onclick="queryOperatorlog();"/>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "operatorlogDG" style="width:auto; height: 400px">
    	</div>
	</div>
    </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initOperatorlogDatagrid(){
         $('#operatorlogDG').datagrid({
              title: '操作员日志信息',
              queryParams: paramsJson(),
              url:'operatorlog/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              columns: [[
                  { field: 'operatorcode', title: '操作员编号',width:40,align:"center",},
                  { field: 'operatorname', title: '操作员姓名',width:40,align:"center",},
                  { field: 'addtime', title: '操作时间',width:60,align:"center",},
                  { field: 'content', title: '操作内容',width:300,align:"center",
                	  formatter: function (value) {
			              return "<span title='" + value + "'>" + value + "</span>";
			          },
                  },
              ]]
          });
	}
    
    function operatorformatter(value,row,index){
	 	return row.operator.employee.employeename;
	}
	 
	 function addtimeformatter(value,row,index){
	 	 return value.substring(0,19);
	 }
    
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		begintime:$("#begintime").val(),
	 		endtime:$("#endtime").val()
	 	};
	 	
	 	return json;
	 }
	
	function queryOperatorlog() {
		$('#operatorlogDG').datagrid('reload',paramsJson());
	}
	
	$(function () {
	   
	   //初始化材料列表
	   initOperatorlogDatagrid();
		
       var returninfo = '${operatorlog.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
</script>
</body>
</html>