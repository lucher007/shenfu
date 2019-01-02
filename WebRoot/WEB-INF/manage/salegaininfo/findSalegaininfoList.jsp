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
					<td align="right" >提成员工姓名：</td>
					<td >
						<input type="text"  class="inp w200" name="gainercode" id="gainercode" readonly="readonly" style="background:#eeeeee;" >
						<input type="text"  class="inp w200" name="gainername" id="gainername" readonly="readonly" style="background:#eeeeee;" >
						<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						<a href="javascript:cleanEmployee();" class="easyui-linkbutton" iconCls="icon-undo" style="width:80px">清除</a>
					</td>
					<td align="right">总提成金额：</td>
					<td >
						<input type="text" class="inp w200" name="salescore" id="salescore" readonly="readonly" style="background:#eeeeee;" >
					</td>
					<td align="right">
						<a href="javascript:querySalegaininfo();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
					</td>
				</tr>
    	    </table>
   		</form>
    </div>
   	<div id = "salegaininfoDG" style="width:auto;"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<!--
			<a href="javascript:takeGaininfo();" class="easyui-linkbutton" iconCls="icon-tip"  plain="true">提成提取</a>
			
			<a href="javascript:addSalegaininfo();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateSalegaininfo();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteSalegaininfo();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
		      -->
		</div>
	</div>
	<div class="pop-box" id="pop-div">
			<table width="400" border="0" cellpadding="0" cellspacing="0">
	          <tr>
	            <td height="30" width="30%" align="right">可提取总金额：</td>
	            <td width="60%">
	            	<input type="text" id="gaintotalmoney" name="gaintotalmoney" class="inp" readonly="readonly" style="background:#eeeeee;" ><span class="red">*</span>
	            </td>
	          </tr>
	          <tr>
	            <td height="30" width="30%" align="right">本次提取金额：</td>
	            <td width="60%">
	            	<input type="text" id="takeoutmoney" name="takeoutmoney" class="inp" onkeyup="onkeyupNum(this);" onkeypress="onkeyupNum(this);" onblur="onkeyupNum(this);" onpaste="return false"><span class="red">*</span>
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

<script type="text/javascript">
   
function initSalegaininfoDatagrid(){
    $('#salegaininfoDG').datagrid({
         title: '提成列表信息',
         queryParams: paramsJson(),
         url:'salegaininfo/findListJson',
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
         toolbar:'#tools',
         frozenColumns:[[
							{ field: 'gainername', title: '提成人姓名',width:150,align:"center"},
							{ field: 'gainerphone', title: '提成人电话',width:150,align:"center"},
							{ field: 'gainname', title: '提成项',width:150,align:"center"},
							{ field: 'gainmoney', title: '提成金额',width:150,align:"center",
								styler: function (value) {
										  return "background-color:yellow;color:red;";
								  },	
							},
							{ field: 'addtime', title: '生成时间',width:150,align:"center"},
                    ]],
          columns: [[
							{ field: 'gainercode', title: '提成人编号',width:150,align:"center"},
							{ field: 'ordercode', title: '订单编号',width:150,align:"center"},
							{ field: 'username', title: '客户姓名',width:150,align:"center",},
							{ field: 'phone', title: '联系电话',width:150,align:"center",},
							{ field: 'statusname', title: '状态',width:150,align:"center",},
						  { field: 'sourcename', title: '客户来源',width:150,align:"center",},
						  { field: 'address', title: '安装地址',width:350,align:"center",
						  	    formatter: function (value) {
						  	    	if(value != null){
						  	    		return "<span title='" + value + "'>" + value + "</span>";
						  	    	}
						  	    },
						  },
						  { field: 'visittypename', title: '上门类型',width:150,align:"center",},
						  { field: 'taskcode', title: '任务单号',width:150,align:"center",},
		                  { field: 'salername', title: '销售员姓名',width:150,align:"center",},
		                  { field: 'visitorname', title: '上门人姓名',width:150,align:"center",},
						  { field: 'totalmoney', title: '总金额',width:150,align:"center",},
						  { field: 'gainrate', title: '提成百分比(%)',width:150,align:"center"},
		                  { field: 'gaintime', title: '提取时间',width:150,align:"center",},
         ]]
     });
}
   
//将表单数据转为json
function paramsJson(){
	var json = {
		gainercode:$("#gainercode").val(),
		status:$("#status").val(),
	};
	return json;
}  
   

    //查询
	function querySalegaininfo(){
		$('#salegaininfoDG').datagrid('reload',paramsJson());
		$('#salegaininfoDG').datagrid('clearChecked');//清空选中的值
	}	
    
    
	var dialog;
	function addSalegaininfo() {
	    dialog = $.dialog({
		    title: '提成规则添加',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:salegaininfo/addInit?rid='+Math.random()
		});
	 }
	
	function updateSalegaininfo() {
		
		var selected = $('#salegaininfoDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        var id = selected.id;
		
	    dialog = $.dialog({
		    title: '提成规则修改',
		    lock: true,
		    width: 1100,
		    height: 600,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:salegaininfo/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#salegaininfoDG').datagrid('reload');// 刷新当前页面
		$('#salegaininfoDG').datagrid('clearChecked');//清空选中的值
	}
	
	/**
	*删除
	*/
	function takeGaininfo(id){
		
		var gainercode = $('#gainercode').val();
		if(gainercode == null || gainercode == ""){
   	     $.dialog.tips('请选择提取人', 2, 'alert.gif');
		     return;
        }
		
		var data = {
						gainercode: gainercode
				   };
		var url="salegaininfo/getGaintotalmoneyForSalegaininfo?rid="+Math.random();
		$.getJSON(url,data,function(jsonMsg){
			if(jsonMsg.flag=="1"){
					$('#gaintotalmoney').val(jsonMsg.gaintotalmoney);
					$('#takeoutmoney').val(jsonMsg.gaintotalmoney);
			}else{
				$.dialog.tips(jsonMsg.result, 2, 'alert.gif');
				return false;
			}
		});
		
		//弹出框显示金额
		dialog = $.dialog({
		    title: '提成金额',
		    lock: true,
		    width: 400,
		    height:140,
		    top: 200,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: $("#pop-div").html(),
		   	ok: function(){
		   		   if($('#gaintotalmoney').val() == '' || $('#gaintotalmoney').val() == '0') {
				      	$.dialog.tips('提成总金额为0，不能提取', 1, 'alert.gif');
				        return false;
				    }
		    	    
		   			updateStatus($("#gainmoney").val());
		   		},
		   	okVal:'保存',
		   	cancel:function(){/* cardDialog.close(); */},
		   	cancelVal:'取消'
	   });
	}
	
	function updateStatus(gainmoney){
		var data = {
						  gainercode: $('#gainercode').val(),
							     gainmoney: $('#gainmoney').val()
				   };
		var url="salegaininfo/updateStatus?rid="+Math.random();
		$.getJSON(url,data,function(jsonMsg){
			 $.dialog.tips(jsonMsg.result, 3, 'alert.gif');
			 if(jsonMsg.status=="1"){
				$('#salegaininfoDG').datagrid('reload');// 刷新当前页面
				$('#salegaininfoDG').datagrid('clearChecked');//清空选中的值
				//parent.closeDialog();
			 }
		});
    }
	
	$(function () {
		initSalegaininfoDatagrid();
        var returninfo = '${salegaininfo.returninfo}';
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
		$("#salescore").val(jsonObject.salescore);
		querySalegaininfo();
	}
	
	function cleanEmployee() {
		  $('#gainercode').val("");
		  $('#gainername').val("");
		  $("#salescore").val("");
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
</script>
</body>
</html>

