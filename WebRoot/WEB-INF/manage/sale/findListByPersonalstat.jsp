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
		    <table style="width:100%">
		    	<tr>
		    		<td align="right">销售人员：</td>
		    		<td>
						<input type="text" class="inp" name="salername" id="salername" readonly="readonly" style="background:#eeeeee;" value="${sale.salername}">
						<input type="hidden" class="inp" name="salerid" id="salerid" value="${sale.salerid}">
						<input  type="button" class="btn-add"  id="btnfinish" value="请选择" onclick="chooseEmployee();">
					</td>
		    		<td align="right">查询年份：</td>
                    <td>
                    	<input type="text" name="opertime" id="opertime" value="${fn:substring(sale.opertime,0,4)}" class="Wdate inp w120" 
						onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy'});" class="Wdate inp w150" />
                    </td>
					<td align="right" >
        				<input type="button" class="btn-sch" value="查询" onclick="checkForm();"/>
   		    		</td>
		    	</tr>
   		    </table>
   		 <div id="container" style="height: 400px"></div>
    </form>
    </div>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="js/highcharts/highcharts-3d.js"></script>
<script type="text/javascript" src="js/highcharts/exporting.js"></script>
<script type="text/javascript" src="js/highcharts/highcharts-zh_CN.js"></script>
<script type="text/javascript" src="js/highcharts/dark-unica.js"></script>
<script type="text/javascript">

function initPersonalstatData(){
	$('#container').highcharts({
        chart: {
            type: 'column',
            margin: 75,
            options3d: {
                enabled: true,
                alpha: 10,
                beta: 25,
                depth: 70
            }
        },
        title: {
            text: '个人销售量统计'
        },
        subtitle: {
            text: ''
        },
        plotOptions: {
            column: {
                depth: 25
            }
        },
        xAxis: {
            categories: Highcharts.getOptions().lang.shortMonths
        },
        yAxis: {
            title: {
                text: null
            }
        },
        series: [{
            name: '销售量',
            data: [
            		parseInt('${sale.personaltotalmoneymap["01"]}'),
            		parseInt('${sale.personaltotalmoneymap["02"]}'),
            		parseInt('${sale.personaltotalmoneymap["03"]}'),
            		parseInt('${sale.personaltotalmoneymap["04"]}'),
            		parseInt('${sale.personaltotalmoneymap["05"]}'),
            		parseInt('${sale.personaltotalmoneymap["06"]}'),
            		parseInt('${sale.personaltotalmoneymap["07"]}'),
            		parseInt('${sale.personaltotalmoneymap["08"]}'),
            		parseInt('${sale.personaltotalmoneymap["09"]}'),
            		parseInt('${sale.personaltotalmoneymap["10"]}'),
            		parseInt('${sale.personaltotalmoneymap["11"]}'),
            		parseInt('${sale.personaltotalmoneymap["12"]}'),
            	 ]
        }]
    });
} 

$(function () {
	initPersonalstatData();
	var returninfo = '${sale.returninfo}';
    if (returninfo != '') {
     	$.dialog.tips(returninfo, 1, 'alert.gif');
    }
});

function checkForm(){
	$("#searchForm").attr("action", "sale/findListByPersonalstat");
	$("#searchForm").submit();
}
    
var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '销售查询',
			lock : true,
			width : 900,
			height : 500,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:employee/findEmployeeListDialog?rid='+Math.random()
		});
	}

	function closeEmployeeDialog(jsonStr) {
		employeeDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
		$("#salerid").val(jsonObject.id);
		$("#salername").val(jsonObject.employeename);
	}
</script>
</body>
</html>