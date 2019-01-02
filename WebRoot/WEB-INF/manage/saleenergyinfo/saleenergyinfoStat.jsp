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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<title></title>
<link type="text/css" rel="stylesheet" href="style/user.css">
<link type="text/css" rel="stylesheet" href="js/plugin/poshytip/tip-yellowsimple/tip-yellowsimple.css">
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
    <div class="seh-box">
        <form action="" method="post" id="searchForm" name="searchForm">
            <input type="hidden" name="saleenergyinfoStatJson" id="saleenergyinfoStatJson" >
            <table width="100%">
				<tr>
					<td align="right" width="5%">员工姓名：</td>
					<td width="35%">
						<input type="text"  class="inp w150" name="gainercode" id="gainercode" readonly="readonly" style="background:#eeeeee;" >
						<input type="text"  class="inp w150" name="gainername" id="gainername" readonly="readonly" style="background:#eeeeee;" >
						<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						<a href="javascript:cleanEmployee();" class="easyui-linkbutton" iconCls="icon-undo" style="width:80px">清除</a>
					</td>
					<td align="right" width="5%">兑换状态：</td>
					<td width="15%">
						<select id="status" name="status" class="select" onchange="querySalegaininfo();">
							<option value="0" <c:if test="${saleenergyinfo.status == '0' }">selected</c:if>>未兑换</option>
		    			    <option value="" <c:if test="${saleenergyinfo.status == '' }">selected</c:if>>请选择</option>
			                <option value="1" <c:if test="${saleenergyinfo.status == '1' }">selected</c:if>>已兑换</option>
			            </select>
					</td>
					<td height="26"  align="right" width="5%">开始时间：</td>
                    <td width="15%">
                    	<input type="text" id="begintime" name="begintime"   value="${saleenergyinfo.begintime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w100">
                    </td>
                    <td align="right" width="5%">结束时间：</td>
                    <td width="15%">
                    	<input type="text" id="endtime" name="endtime"  value="${saleenergyinfo.endtime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'begintime\');}'});" class="Wdate inp w100">
					</td>
					<td align="right">
						<a href="javascript:querySalegaininfo();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
					</td>
				</tr>
    	    </table>
   		</form>
    </div>
   	<div id = "saleenergyinfoStatDG" style="width:auto;">	</div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:saveEnergyExchangeToGain();" class="easyui-linkbutton" iconCls="icon-tip"  plain="true">兑换行动力</a>
			<a href="javascript:saveExportExcel();" class="easyui-linkbutton" iconCls="icon-reload"  plain="true">导出Excel</a>
			<!--
			<a href="javascript:printMaterialinout();" class="easyui-linkbutton" iconCls="icon-print"  plain="true">在线打印</a>
			-->
		</div>
	</div>
	
	<div id="saleenergyinfoList" style="width:auto; height: auto;"></div>
	
	<div id="printInfo" style="display: none;"></div>
	
	<div class="pop-box" id="pop-div">
			<table width="400" border="0" cellpadding="0" cellspacing="0">
	          <tr>
	            <td height="30" width="30%" align="right">可提取总金额：</td>
	            <td width="60%">
	            	<input type="text" id="gaintotalmoney" name="gaintotalmoney" class="inp" readonly="readonly" style="background:#eeeeee;" ><span class="red">*</span>
	            </td>
	          </tr>
		    </table>
	</div>
</div>
    
<script type="text/javascript" language="javascript" src="js/form.js"></script>
<script type="text/javascript" language="javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" language="javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" language="javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>js/lodop/LodopFuncs.js"></script>
<script type="text/javascript">
   
	function initSalegaininfoDatagrid(){
	    $('#saleenergyinfoStatDG').datagrid({
	         title: '提成列表信息',
	         queryParams: paramsJson(),
	         url:'saleenergyinfo/saleenergyinfoStatJson',
	         pagination: true,
	         singleSelect: false,
	         scrollbar:true,
	         pageSize: 10,
	         pageList: [10,30,50], 
	         fitColumns:true,
	         checkOnSelect: false,// 如果为true，当用户点击行的时候该复选框就会被选中或取消选中。 如果为false，当用户仅在点击该复选框的时候才会呗选中或取消。
	         idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
	         loadMsg:'正在加载数据......',
	         toolbar:'#tools',
	         onClickRow: onClickRow,
	         columns: [[
							{ field: 'id', title: '全选',checkbox:true,align:"center",width:100,
								  formatter: function(val, row, index){
							        return row.id;
							    }, 
							},
							{ field: 'gainercode', title: '提成人编号',width:150,align:"center"},
							{ field: 'gainername', title: '提成人姓名',width:150,align:"center"},
							{ field: 'gainerphone', title: '提成人电话',width:150,align:"center"},
							{ field: 'energyscore', title: '本次兑换行动力',width:150,align:"center",},
	         ]],
	         onLoadSuccess:function(data){  
 	        	 //默认选择第一条
 	        	 $('#saleenergyinfoStatDG').datagrid("selectRow", 0);
 	        	 //加载第一条驻点的工作人员信息
 	        	 initSalegaininfolist();
 	         },
	     });
	}
	   
	//将表单数据转为json
	function paramsJson(){
		var json = {
			gainercode:$("#gainercode").val(),
			status:$("#status").val(),
			begintime:$("#begintime").val(),
	 		endtime:$("#endtime").val()
		};
		return json;
	}  
	   
    //查询
	function querySalegaininfo(){
		$('#saleenergyinfoStatDG').datagrid('reload',paramsJson());
		$('#saleenergyinfoStatDG').datagrid('clearChecked');//清空选中的值
	}	
    
	//点击表格某一条
	function onClickRow(index, data){
		var ids = data.ids;
		
		$('#saleenergyinfoList').datagrid('reload',{
			ids:ids,
		});	
	} 
    
    //查询汇总明细
	function initSalegaininfolist(){
	    $('#saleenergyinfoList').datagrid({
	    	title: '提成列表信息',
	         queryParams: saleenergyinfoListparamsJson(),
	         url:'saleenergyinfo/findListJson',
	         pagination: true,
	         singleSelect: true,
	         scrollbar:true,
	         pageSize: 10,
	         pageList: [10,30,50], 
	         //fitColumns:true,
	         checkOnSelect: true,// 如果为true，当用户点击行的时候该复选框就会被选中或取消选中。 如果为false，当用户仅在点击该复选框的时候才会呗选中或取消。
	         idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
	         loadMsg:'正在加载数据......',
	         showFooter: true,
	         //toolbar:'#tools',
	         frozenColumns:[[
								{ field: 'gainername', title: '提成人姓名',width:150,align:"center"},
								{ field: 'gainerphone', title: '提成人电话',width:150,align:"center"},
								{ field: 'gainname', title: '提成项',width:150,align:"center"},
								{ field: 'energyscore', title: '行动力积分',width:150,align:"center",
									styler: function (value) {
											  return "background-color:yellow;color:red;";
									  },	
								},
	                    ]],
	          columns: [[
								{ field: 'addtime', title: '添加时间',width:150,align:"center"},
								{ field: 'statusname', title: '行动力状态',width:150,align:"center",},
								{ field: 'gainrate', title: '兑换提成比例',width:150,align:"center"},
								{ field: 'gainmoney', title: '兑换提成金额',width:150,align:"center"},
								{ field: 'exchangetime', title: '兑换提成时间',width:150,align:"center",},
								{ field: 'gainercode', title: '提成人编号',width:150,align:"center"},
								{ field: 'ordercode', title: '订单编号',width:150,align:"center"},
								{ field: 'username', title: '客户姓名',width:150,align:"center",},
								{ field: 'phone', title: '联系电话',width:150,align:"center",},
							    { field: 'sourcename', title: '客户来源',width:150,align:"center",},
							    { field: 'address', title: '安装地址',width:350,align:"center",
							  	    formatter: function (value) {
							  	    	if(value != null){
							  	    		return "<span title='" + value + "'>" + value + "</span>";
							  	    	}
							  	    },
							    },
							    { field: 'visittypename', title: '上门类型',width:150,align:"center",},
	         ]]
	     });
	}
 
    //将表单数据转为json
	function saleenergyinfoListparamsJson(){
    	//获取选中的智能卡
		var selected = $('#saleenergyinfoStatDG').datagrid('getSelected');
    	var ids = selected.ids;
    	if(ids == undefined){
    		ids = -100;
    	}
		var json = {
				ids:ids,
		};
		return json;
	}
	
	/**
	*提取提成
	*/
	function saveEnergyExchangeToGain(){
		//获取所有选择的产品
	    var rowsSelected = $('#saleenergyinfoStatDG').datagrid('getChecked');
		
        if(rowsSelected == null  || rowsSelected == ''){
        	 $.dialog.tips('<spring:message code="请选择需要兑换行动力的员工"/>', 2, 'alert.gif');
		      return;
        }
		
		//将row转换成json字符串
	 	var event = JSON.stringify(rowsSelected);
	 	//中文进行编码转换，防止乱码
		var o = JSON.parse(event); 
		//将Json字符串赋值给event
		eval("var event = '"+JSON.stringify(o)+"';"); 
		
		//alert(event);
		
		//赋值
		$("#saleenergyinfoStatJson").val(event);
		
		$.dialog({
		    title: '提交',
		    lock: true,
		    background: '#000', /* 背景色 */
		    opacity: 0.5,       /* 透明度 */
		    content: '是否确定执行？',
		    icon: 'alert.gif',
		    ok: function () {
		    	$("#searchForm").attr("action", "saleenergyinfo/saveEnergyExchangeToGain?rid="+Math.random());
				$("#searchForm").submit();
		        /* 这里要注意多层锁屏一定要加parent参数 */
		        $.dialog({
		        	lock: true,
		            title: '提示',
		        	content: '执行中，请等待......', 
		        	max: false,
				    min: false,
				    cancel:false,
		        	icon: 'loading.gif',
		        });
		        return false;
		    },
		    okVal: '确定',
		    cancel: true,
		    cancelVal:'取消'
		});
	}
	
	$(function () {
		initSalegaininfoDatagrid();
        var returninfo = '${saleenergyinfo.returninfo}';
        if (returninfo != '') {
        	$.dialog.tips(returninfo, 3, 'alert.gif');
        }
    });
	
	var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '员工查询',
			lock : true,
			width : 1100,
			height : 650,
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
	    
		$("#gainercode").val(jsonObject.employeecode);
		$("#gainername").val(jsonObject.employeename);
		//$("#salescore").val(jsonObject.salescore);
		querySalegaininfo();
	}
	
	function cleanEmployee() {
		  $('#gainercode').val("");
		  $('#gainername').val("");
		  //$("#salescore").val("");
		  querySalegaininfo();
	}
	
	
	function onkeyupNum(obj){
		obj.value=$.trim(obj.value);//去空格			
		obj.value=obj.value.replace(/\D/g,'');//保留数字
				
		if(obj.value == 'undefined'){
			obj.value='';
		}
			
		//大于0
		if(obj.value <1 && obj.value.length==1) {
			obj.value = "";
		}	
	}
	
	function saveExportExcel(){
		$.dialog.confirm('确认是否导出？', function(){ 
			$("#searchForm").attr("action", "saleenergyinfo/saleenergyinfoStatExportExcel");
			$("#searchForm").submit();
		}, function(){
			
			});
	}
</script>
</body>
</html>

