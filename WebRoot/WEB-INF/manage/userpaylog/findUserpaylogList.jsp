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
		    		<td align="right" width="10%">客户姓名：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="username" id="username" value="${userpaylog.username }">
		    		</td>
		    		<td align="right" width="10%">电话号码：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${userpaylog.phone }">
		    		</td>
		    		<td align="right" width="10%">来源：</td>
		    		<td width="20%">
		    			<select id="source" name="source" class="select" onchange="queryUser();">
		    				<option value=""  <c:if test="${userpaylog.source == ''}">selected</c:if>>请选择</option>
							<option value="0" <c:if test="${userpaylog.source == '0'}">selected</c:if>>销售</option>
							<option value="1" <c:if test="${userpaylog.source == '1'}">selected</c:if>>400客服</option>
						</select>
		    		</td>
		    		<td align="center">
						<a href="javascript:queryUserpaylog();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
					</td>
		    	</tr>
				<tr>
					<td align="right">查询开始时间：</td>
					<td>
                 		<input type="text" readonly="readonly" id="beginpaytime" name="beginpaytime" value="" onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150" />
					</td>
					<td align="right">查询结束时间：</td>
					<td>
                 		<input type="text" readonly="readonly" id="endpaytime" name="endpaytime" value="" onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150" />
					</td>
					<td align="right" >支付方式：</td>
					<td colspan="2">
						<select id="paytype" name="paytype" class="select" onchange="queryUserpaylog();">
							<option value="" <c:if test="${userpaylog.paytype == '' }">selected</c:if>>请选择</option>
							<option value="0" <c:if test="${userpaylog.paytype == '0' }">selected</c:if>>现金</option>
			                <option value="1" <c:if test="${userpaylog.paytype == '1' }">selected</c:if>>微信在线</option>
			                <option value="2" <c:if test="${userpaylog.paytype == '2' }">selected</c:if>>支付宝在线</option>
			                <option value="3" <c:if test="${userpaylog.paytype == '3' }">selected</c:if>>微信静态码</option>
			                <option value="4" <c:if test="${userpaylog.paytype == '4' }">selected</c:if>>支付宝静态码</option>
			                <option value="5" <c:if test="${userpaylog.paytype == '5' }">selected</c:if>>POS机刷卡</option>
			             </select>
					</td>
				</tr>
    	    </table>
   		</form>
    </div>
   	<div id = "userpaylogDG" style="width:auto;"></div>
   	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:checkUserpaylog();" class="easyui-linkbutton" iconCls="icon-tip"  plain="true">审核到账</a>
			<!--
			<a href="javascript:addUserpaylog();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateUserpaylog();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteUserpaylog();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
		      -->
		</div>
	</div>
	<div class="pop-box" id="pop-div">
			<table width="400" border="0" cellpadding="0" cellspacing="0">
	          <tr>
	            <td height="30" width="30%" align="right">支付总金额：</td>
	            <td width="60%">
	            	<input type="text" id="paytotalmoney" name="paytotalmoney" class="inp" readonly="readonly" style="background:#eeeeee;" ><span class="red">*</span>
	            </td>
	          </tr>
		</table>
	</div>
</div>
    
<script type="text/javascript" language="javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" language="javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
   
function initUserpaylogDatagrid(){
    $('#userpaylogDG').datagrid({
         title: '提成规则列表信息',
         queryParams: paramsJson(),
         url:'userpaylog/findListJson',
         pagination: true,
         singleSelect: false,
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
							{ field: 'id', title: '<spring:message code="page.select.all"/>',checkbox:true,align:"center",width:40,
								  formatter: function(val, row, index){
							    	  return row.id;
								  }, 
							}, 
							{ field: 'paytypename', title: '支付方式',width:150,align:"center"},
							{ field: 'paymoney', title: '支付金额',width:150,align:"center",
								styler: function (value) {
										  return "background-color:yellow;color:red;";
								  },	
							},
							{ field: 'payitemname', title: '支付项',width:150,align:"center"},
                       ]],
          columns: [[
							{ field: 'username', title: '客户姓名',width:150,align:"center"},
							{ field: 'phone', title: '客户电话',width:150,align:"center"},
							{ field: 'receivercode', title: '收款人编号',width:150,align:"center"},
							{ field: 'receivername', title: '收款人姓名',width:150,align:"center"},
							{ field: 'receiverphone', title: '收款人电话',width:150,align:"center"},
							{ field: 'ordercode', title: '订单编号',width:150,align:"center"},
						    { field: 'sourcename', title: '客户来源',width:150,align:"center",},
						    { field: 'address', title: '安装地址',width:350,align:"center",
						  	    formatter: function (value) {
						  	    	if(value != null){
						  	    		return "<span title='" + value + "'>" + value + "</span>";
						  	    	}
						  	    },
						  },
						  { field: 'paytime', title: '支付时间',width:150,align:"center",},
         ]]
     });
}
   
//将表单数据转为json
function paramsJson(){
	var json = {
			username:$("#username").val(),
			phone:$("#phone").val(),
			source:$("#source").val(),
			beginpaytime:$("#beginpaytime").val(),
			endpaytime:$("#endpaytime").val(),
			paytype:$("#paytype").val(),
	};
	return json;
}  
   

    //查询
	function queryUserpaylog(){
		$('#userpaylogDG').datagrid('reload',paramsJson());
		$('#userpaylogDG').datagrid('clearChecked');//清空选中的值
	}	
    
    
	var dialog;
	function addUserpaylog() {
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
		    content: 'url:userpaylog/addInit?rid='+Math.random()
		});
	}
	
	function updateUserpaylog() {
		
		var selected = $('#userpaylogDG').datagrid("getSelected");
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
		    content: 'url:userpaylog/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#userpaylogDG').datagrid('reload');// 刷新当前页面
		$('#userpaylogDG').datagrid('clearChecked');//清空选中的值
	}
	
	/**
	*删除
	*/
	function checkUserpaylog(){
		
		var selected = $('#userpaylogDG').datagrid("getChecked");
        if(selected == null || selected == ""){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取选择的项
        var ids = [];
        var totalpaymoney = 0;
        
		var rows = $('#userpaylogDG').datagrid("getChecked");
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].id);
			totalpaymoney = totalpaymoney + parseInt(rows[i].paymoney);
		}
        
		var checkedids = ids.join(',');
		
		//弹出框显示金额
		dialog = $.dialog({
		    title: '审核总金额',
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
			   			updateCheckstatus(checkedids);
			   		},
		   	okVal:'保存',
		   	cancel:function(){/* cardDialog.close(); */},
		   	cancelVal:'取消'
	   });
		
		//赋值
		$('#paytotalmoney').val(totalpaymoney);
		
	}
	
	function updateCheckstatus(checkedids){
		
		if($('#paytotalmoney').val() == '') {
	      	$.dialog.tips('支付金额不能为空', 1, 'alert.gif');
	        return false;
	    }
		
		var data = {
					 ids: checkedids,
				   };
		var url="userpaylog/updateCheckstatus?rid="+Math.random();
		$.getJSON(url,data,function(jsonMsg){
			 $.dialog.tips(jsonMsg.result, 3, 'alert.gif');
			 if(jsonMsg.status=="1"){
				$('#userpaylogDG').datagrid('reload');// 刷新当前页面
				$('#userpaylogDG').datagrid('clearChecked');//清空选中的值
				//parent.closeDialog();
			 }
		});
    }
	
	$(function () {
		initUserpaylogDatagrid();
        var returninfo = '${userpaylog.returninfo}';
        if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
        }
    });
	
	var userDialog;
	function chooseUser() {
		userDialog = $.dialog({
			title : '员工查询',
			lock : true,
			width : 1100,
			height : 650,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:user/findUserListDialog?rid='+Math.random()
		});
	}

	function closeUserDialog(jsonStr) {
		userDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
	    
		$("#usercode").val(jsonObject.usercode);
		$("#username").val(jsonObject.username);
		
		queryUserpaylog();
	}
	
	function cleanUser() {
		  $('#usercode').val("");
		  $('#username').val("");
		  
		  queryUserpaylog();
	}
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryUserpaylog();
	    }  
	});
</script>
</body>
</html>

