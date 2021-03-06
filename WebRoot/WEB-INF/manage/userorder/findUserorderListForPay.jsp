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
    <div class="seh-box">
        <form action="" method="post" id="searchForm" name="searchForm">
            <table width="100%">
		    	<tr>
		    		<td align="right" width="10%">客户姓名：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="username" id="username" value="${userorder.username }">
		    		</td>
		    		<td align="right" width="10%">电话号码：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${userorder.phone }">
		    		</td>
		    		<td align="right" width="120px">订单号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="ordercode" id="ordercode" value="${userorder.ordercode }">
		    		</td>
		    		<td align="center" width="130">
						<a href="javascript:queryUserorder();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="10%">安装地址：</td>
		    		<td width="20%">
		    			<input type="text"  class="inp w200" name="address" id="address" value="${userorder.address }">
		    		</td>
		    		<td align="right" width="120px">状态：</td>
		    		<td>
		    			<select id="status" name="status" class="select" onchange="queryUserorder();">
		    			    <option value="" <c:if test="${userorder.status == '' }">selected</c:if>>请选择</option>
		    			    <option value="0" <c:if test="${userorder.status == '0' }">selected</c:if>>新生成订单</option>
			                <option value="1" <c:if test="${userorder.status == '1' }">selected</c:if>>已打包</option>
			                <option value="2" <c:if test="${userorder.status == '2' }">selected</c:if>>已发货</option>
			                <option value="3" <c:if test="${userorder.status == '3' }">selected</c:if>>已派工</option>
			             	<option value="4" <c:if test="${userorder.status == '4' }">selected</c:if>>派工接收</option>
			             	<option value="5" <c:if test="${userorder.status == '5' }">selected</c:if>>安装完成</option>
			             	<option value="6" <c:if test="${userorder.status == '6' }">selected</c:if>>审核结单</option>
			             	<option value="10" <c:if test="${userorder.status == '10' }">selected</c:if>>安装失败</option>
			             </select>
		    		</td>
		    		<td align="right">来源：</td>
        			<td>
        				<select id="source" name="source" class="select" onchange="queryUserorder();">
			                   <option value=""  <c:if test="${userorder.status == ''}">selected</c:if>>请选择</option>
			                   <option value="0" <c:if test="${userorder.status == '0' }">selected</c:if>>销售</option>
			                   <option value="1" <c:if test="${userorder.status == '1' }">selected</c:if>>400客服</option>
			             </select>
        			</td>
					<td align="right" width="130">
        				<input type="button" class="btn-sch" value="查询" onclick="queryUserorder();"/>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "userorderDG" style="width:auto; height: 450px"></div>
    <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:payUserorder('1');" class="easyui-linkbutton" iconCls="icon-ok"  plain="true">支付首付款</a>
			<a href="javascript:payUserorder('2');" class="easyui-linkbutton" iconCls="icon-ok"  plain="true">支付尾款</a>
			<a href="javascript:lookUserorder();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看订单详情</a>
			<a href="javascript:lookUsercontract();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看合同信息</a>
		</div>
	</div>
	
	<div class="pop-box" id="pop-div">
			<table width="400" border="0" cellpadding="0" cellspacing="0">
	          <tr>
	            <td height="30" width="30%" align="right">支付类型：</td>
	            <td width="60%">
	            	<select id="paytype" name="paytype" class="select" onchange="queryUserpaylog();">
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
	</div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
function initUserorderDatagrid(){
    $('#userorderDG').datagrid({
         title: '订单列表信息',
         queryParams: paramsJson(),
         url:'userorder/findListJson',
         pagination: true,
         singleSelect: true,
         scrollbar:true,
         pageSize: 10,
         pageList: [10,30,50], 
         //fitColumns:true,
         //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
         loadMsg:'正在加载数据......',
         toolbar:'#tools',
         frozenColumns:[[
                         { field: 'ordercode', title: '订单编号',width:150,align:"center"},
                         { field: 'username', title: '客户姓名',width:150,align:"center",},
                         { field: 'phone', title: '联系电话',width:150,align:"center",},    
                    ]],
          columns: [[
                         { field: 'firstpayment', title: '首付金额',width:150,align:"center",
                        	 styler: function (value) {
							  		return "background-color:yellow;color:red;";
					         },
                         },
					     { field: 'firstarrivalflagname', title: '首付金额标识',width:150,align:"center",},
					  
						 { field: 'finalpayment', title: '尾款金额',width:150,align:"center",
								      styler: function (value) {
									  		return "background-color:yellow;color:red;";
							    },
						  },
						  { field: 'finalarrivalflagname', title: '尾款金额标识',width:150,align:"center",},
						  { field: 'paytypename', title: '支付类型',width:150,align:"center",},
						  { field: 'sourcename', title: '客户来源',width:150,align:"center",},
						  { field: 'address', title: '安装地址',width:350,align:"center",
						  	    formatter: function (value) {
						              return "<span title='" + value + "'>" + value + "</span>";
						          },
						  },
						  { field: 'productfee', title: '产品费用',width:150,align:"center",},
						  { field: 'otherfee', title: '其他费用',width:150,align:"center",},
						  { field: 'totalmoney', title: '总金额',width:150,align:"center",},
						  { field: 'addtime', title: '添加时间',width:150,align:"center",},
						  { field: 'visittypename', title: '上门类型',width:150,align:"center",},
						  { field: 'salername', title: '销售员姓名',width:150,align:"center",},
						  { field: 'talkername', title: '讲解员姓名',width:150,align:"center",},
		                  { field: 'visitorname', title: '上门员姓名',width:150,align:"center",},
		                  { field: 'statusname', title: '状态',width:150,align:"center",},
		                  { field: 'operatetime', title: '最新修改时间',width:150,align:"center",},
         ]]
     });
}

//将表单数据转为json
function paramsJson(){
	var json = {
			username:$("#username").val(),
	 		phone:$("#phone").val(),
	 		ordercode:$("#ordercode").val(),
	 		address:$("#address").val(),
	 		status:$("#status").val(),
	 		source:$("#source").val(),
	};
	return json;
}

function queryUserorder() {
	$('#userorderDG').datagrid('reload',paramsJson());
}
	
	var dialog;
	function updateUserorder(id) {
	    dialog = $.dialog({
		    title: '订单修改',
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
		    content: 'url:userorder/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addUserorder() {	
	    dialog = $.dialog({
		    title: '订单添加',
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
		    content: 'url:userorder/addInit?rid='+Math.random()
		});
	 }
    
    
	 function lookUserorder() {
		   
		   var selected = $('#userorderDG').datagrid("getSelected");
	       if(selected == null){
	  	     $.dialog.tips('请选择选项', 2, 'alert.gif');
			     return;
	       }
	       //获取需要修改项的ID
	       id = selected.id;
		   
		    dialog = $.dialog({
			    title: '订单信息',
			    lock: true,
			    width: 1100,
			    height: 650,
			    top: 0,
			    drag: false,
			    resize: false,
			    max: false,
			    min: false,
			    content: 'url:userorder/lookInit?rid='+ Math.random()+'&id='+id
			});
		 }
    
  //合同查看
	 function lookUsercontract() {
		var selected = $('#userorderDG').datagrid("getSelected");
       if(selected == null){
  	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
       }
       //获取需要修改项的ID
       ordercode = selected.ordercode;
		
	    dialog = $.dialog({
		    title: '合同信息',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:usercontract/findByList?rid='+ Math.random()+'&ordercode='+ordercode
		});
	 } 
    
	function closeDialog(){
		dialog.close();
		$('#userorderDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteUserorder(id){
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="userorder/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#userorderDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化订单列表
	   initUserorderDatagrid();
		
       var returninfo = '${userorder.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
    function uploadUsercontract(orderid,orderno) {	
	    dialog = $.dialog({
		    title: '合同信息',
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
		    content: 'url:usercontract/findByList?rid='+Math.random()+'&orderid='+orderid+'&orderno='+orderno
		});
	 }
	 
	/**
	*结单
	*/
	function completeUserorder(id){
		
		$.dialog.confirm('确认是否结单？', function(){ 
			var data = {
					     id: id
					   };
			var url="userorder/saveComplete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#userorderDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	/**
	*订单作废
	*/
	function cancelUserorder(id){
		
		$.dialog.confirm('确认是否违约？', function(){ 
			var data = {
					     id: id
					   };
			var url="userorder/saveCancel?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 1, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#userorderDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	 
	 
    /**
	*订单支付
	*/
	function payUserorder(payitem){
		
		var selected = $('#userorderDG').datagrid("getSelected");
        if(selected == null){
   	     	 $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        
        //支付首付款
        if(payitem == '1'){
        	//获取首付款支付标志
            var firstarrivalflag = selected.firstarrivalflag;
        	if(firstarrivalflag != '0'){
        		$.dialog.tips('该订单已经支付首付款，不能重复支付', 2, 'alert.gif');
   		        return;
        	}
        }else if(payitem == '2'){
        	//获取首付款支付标志
            var finalarrivalflag = selected.finalarrivalflag;
        	if(finalarrivalflag != '0'){
        		$.dialog.tips('该订单已经支付尾款，不能重复尾款', 2, 'alert.gif');
   		        return;
        	}
        }
        
        //获取需要修改项的ID
        var id = selected.id;
        
        //弹出框显示金额
		dialog = $.dialog({
		    title: '支付信息',
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
		   		var data = {
					             id:id,
							payitem:payitem,
							paytype:$('#paytype').val(),
						   };
				var url="userorder/savePaymentInfo?rid="+Math.random();
				$.getJSON(url,data,function(jsonMsg){
					$.dialog.tips(jsonMsg.msg, 2, 'alert.gif');
					if(jsonMsg.flag=="1"){
						$('#userorderDG').datagrid('reload');// 刷新当前页面
					}else{
					}
				});
		   	},
		   	okVal:'确认支付',
		   	cancel:function(){/* cardDialog.close(); */},
		   	cancelVal:'取消'
	   });
       
	}
   
</script>
</body>
</html>