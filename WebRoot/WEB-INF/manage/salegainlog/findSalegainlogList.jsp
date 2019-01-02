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
            <input type="hidden" id="checkedids" name="checkedids"/>
            <table width="100%">
				<tr>
					<td align="right" width="10%">员工姓名：</td>
					<td width="40%" >
						<input type="text"  class="inp w150" name="gainercode" id="gainercode" readonly="readonly" style="background:#eeeeee;" >
						<input type="text"  class="inp w150" name="gainername" id="gainername" readonly="readonly" style="background:#eeeeee;" >
						<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						<a href="javascript:cleanEmployee();" class="easyui-linkbutton" iconCls="icon-undo" style="width:80px">清除</a>
					</td>
					<td height="26"  align="right" width="10%">开始时间：</td>
                    <td width="20%">
                    	<input type="text" id="begintime" name="begintime"   value="${salegaininfo.begintime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w100">
                    </td>
                    <td align="right" width="10%">结束时间：</td>
                    <td width="20%">
                    	<input type="text" id="endtime" name="endtime"  value="${salegaininfo.endtime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'begintime\');}'});" class="Wdate inp w100">
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">提现信息标志：</td>
					<td width="20%">
						<select id="gaincontentflag" name="gaincontentflag" class="select" onchange="querySalegainlog();">
		    			    <option value="" <c:if test="${salegainlog.gaincontentflag == '' }">selected</c:if>>请选择</option>
							<option value="1" <c:if test="${salegainlog.gaincontentflag == '1' }">selected</c:if>>有提现信息</option>
			            </select>
					</td>
					<td align="right" width="10%">到账状态：</td>
					<td width="20%">
						<select id="status" name="status" class="select" onchange="querySalegainlog();">
		    			    <option value="" <c:if test="${salegainlog.status == '' }">selected</c:if>>请选择</option>
			                <option value="0" <c:if test="${salegainlog.status == '0' }">selected</c:if>>未提取</option>
							<option value="1" <c:if test="${salegainlog.status == '1' }">selected</c:if>>已提取</option>
			            </select>
					</td>
					<td align="right">
						<a href="javascript:querySalegainlog();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
					</td>
				</tr>
    	    </table>
   		</form>
    </div>
   	<div id = "salegainlogDG" style="width:auto;"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
		    <!-- 
			<a href="javascript:updateStatus('0');" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改成未到账</a>
			<a href="javascript:updateStatus('1');" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改成已到账</a>
			 -->
			<a href="javascript:updateSalegainlog();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
		</div>
	</div>
</div>
    
<script type="text/javascript" language="javascript" src="js/form.js"></script>
<script type="text/javascript" language="javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" language="javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" language="javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	function initSalegainlogDatagrid(){
	    $('#salegainlogDG').datagrid({
	         title: '提现列表信息',
	         queryParams: paramsJson(),
	         url:'salegainlog/findListJson',
	         pagination: true,
	         singleSelect: true,
	         scrollbar:true,
	         pageSize: 10,
	         pageList: [10,30,50], 
	         fitColumns:true,
	         checkOnSelect: true,// 如果为true，当用户点击行的时候该复选框就会被选中或取消选中。 如果为false，当用户仅在点击该复选框的时候才会呗选中或取消。
	         idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
	         loadMsg:'正在加载数据......',
	         showFooter: true,
	         toolbar:'#tools',
	         columns: [[
							{ field: 'gainercode', title: '提成人编号',width:150,align:"center"},
							{ field: 'gainername', title: '提成人姓名',width:150,align:"center"},
							{ field: 'gainerphone', title: '提成人电话',width:150,align:"center"},
							{ field: 'gainmoney', title: '提成金额',width:150,align:"center",},
							{ field: 'statusname', title: '到账状态',width:150,align:"center",},
							{ field: 'operatorname', title: '操作员',width:150,align:"center",},
							{ field: 'gaintime', title: '提现时间',width:150,align:"center",},
							{ field: 'gaincontent', title: '提现信息',width:300,align:"center",
								formatter: function (value) {
									if(value != null && value != ''  ){
										return "<span title='" + value + "'>" + value + "</span>";
									}else{
										return '';
									}
      					        },
                      	    },
	         ]]
	     });
	}
	   
	//将表单数据转为json
	function paramsJson(){
		var json = {
			gainercode:$("#gainercode").val(),
			status:$("#status").val(),
			begintime:$("#begintime").val(),
	 		endtime:$("#endtime").val(),
	 		gaincontentflag:$("#gaincontentflag").val(),
		};
		return json;
	}  
   

    //查询
	function querySalegainlog(){
		$('#salegainlogDG').datagrid('reload',paramsJson());
		$('#salegainlogDG').datagrid('clearChecked');//清空选中的值
	}
  
	
	function updateStatus(status){
		var selected = $('#salegainlogDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
        var currentStatus = selected.status;
        if(currentStatus == status){
        	$.dialog.tips("修改状态已经跟当前状态是一样的", 2, 'alert.gif');
			return false;
        }
		
        
        $.dialog.confirm('确认是否修改？', function(){ 
        	var data = {
					  id: id,
					  status: status
			   };
			var url="salegainlog/updateStatus?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				 $.dialog.tips(jsonMsg.result, 3, 'alert.gif');
				 if(jsonMsg.status=="1"){
					$('#salegainlogDG').datagrid('reload');// 刷新当前页面
					$('#salegainlogDG').datagrid('clearChecked');//清空选中的值
					//parent.closeDialog();
				 }
			});
		}, function(){
			
			});
    }
	
	$(function () {
		initSalegainlogDatagrid();
        var returninfo = '${salegainlog.returninfo}';
        if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
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
		querySalegainlog();
	}
	
	function cleanEmployee() {
		  $('#gainercode').val("");
		  $('#gainername').val("");
		  querySalegainlog();
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
	
	var dialog;
	function updateSalegainlog() {
		
		var selected = $('#salegainlogDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
        
        //获取需要修改项的ID
        var id = selected.id;
		
	    dialog = $.dialog({
		    title: '提现记录修改',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:salegainlog/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	
	function closeDialog(){
		dialog.close();
		$('#salegainlogDG').datagrid('reload');// 刷新当前页面
		$('#salegainlogDG').datagrid('clearChecked');//清空选中的值
	}
</script>
</body>
</html>

