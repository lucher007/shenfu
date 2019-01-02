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
		    		<td align="right">客户姓名：</td>
		    		<td >
		    			<input type="text"  class="inp w200" name="username" id="username" value="${userorder.username }">
		    		</td>
		    		<td align="right" >电话号码：</td>
		    		<td >
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${userorder.phone }">
		    		</td>
		    		<td align="right" >订单号：</td>
		    		<td >
		    			<input type="text"  class="inp w200" name="ordercode" id="ordercode" value="${userorder.ordercode }">
		    		</td>
		    		<td align="center" colspan="2">
		    			<a href="javascript:queryUserorder();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right">安装地址：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="address" id="address" value="${userorder.address }">
		    		</td>
		    		<td align="right">状态：</td>
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
		    		</td>
		    		<td align="right">来源：</td>
        			<td colspan="3">
        				<select id="source" name="source" class="select" onchange="queryUserorder();">
			                   <option value=""  <c:if test="${userorder.status == ''}">selected</c:if>>请选择</option>
			                   <option value="0" <c:if test="${userorder.status == '0' }">selected</c:if>>销售</option>
			                   <option value="1" <c:if test="${userorder.status == '1' }">selected</c:if>>400客服</option>
			             </select>
        			</td>
		    	</tr>
		    	<tr>
		    		<td align="right" >是否归档：</td>
		    		<td >
		    			<select id="filingflag" name="filingflag" class="select" onchange="queryUserorder();">
		    			    <option value="0" <c:if test="${userorder.filingflag == '0' }">selected</c:if>>未归档</option>
			                <option value="1" <c:if test="${userorder.filingflag == '1' }">selected</c:if>>已归档</option>
			             </select>
		    		</td>
		    		<td align="right" >操作时效性：</td>
		    		<td >
		    			<select id="timelevel" name="timelevel" class="select" onchange="queryUserorder();">
		    			    <option value=""  <c:if test="${userorder.timelevel == '' }">selected</c:if>>请选择</option>
		    			    <option value="0" <c:if test="${userorder.timelevel == '0' }">selected</c:if>>正常(白色显示)</option>
			                <option value="1" <c:if test="${userorder.timelevel == '1' }">selected</c:if>>稍微延迟(绿色显示)</option>
			                <option value="2" <c:if test="${userorder.timelevel == '2' }">selected</c:if>>延迟较多(蓝色显示)</option>
			             	<option value="3" <c:if test="${userorder.timelevel == '3' }">selected</c:if>>严重滞后(红色显示)</option>
			             </select>
		    		</td>
		    		<td align="right" >首付款状态：</td>
        			<td >
        				<select id="firstarrivalflag" name="firstarrivalflag" class="select" onchange="queryUserorder();">
			                   <option value=""  <c:if test="${userorder.firstarrivalflag == ''}">selected</c:if>>请选择</option>
			                   <option value="0" <c:if test="${userorder.firstarrivalflag == '0' }">selected</c:if>>未支付</option>
			                   <option value="1" <c:if test="${userorder.firstarrivalflag == '1' }">selected</c:if>>已支付</option>
			                   <option value="2" <c:if test="${userorder.firstarrivalflag == '2' }">selected</c:if>>确认到账</option>
			             </select>
        			</td>
        			<td align="right" >尾款状态：</td>
        			<td >
        				<select id="finalarrivalflag" name="finalarrivalflag" class="select" onchange="queryUserorder();">
			                   <option value=""  <c:if test="${userorder.finalarrivalflag == ''}">selected</c:if>>请选择</option>
			                   <option value="0" <c:if test="${userorder.finalarrivalflag == '0' }">selected</c:if>>未支付</option>
			                   <option value="1" <c:if test="${userorder.finalarrivalflag == '1' }">selected</c:if>>已支付</option>
			                   <option value="2" <c:if test="${userorder.finalarrivalflag == '2' }">selected</c:if>>确认到账</option>
			             </select>
        			</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
   <div id = "userorderDG" style="width:auto; height: auto;"></div>
   <div id="tools">
		<div style="margin-bottom:5px">
		     <!-- 
			<a href="javascript:updateUserorder();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			 -->
			<a href="javascript:lookUserorder();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看订单详情</a>
			<a href="javascript:lookUsercontract();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看合同信息</a>
			<a href="javascript:lookUserdelivery();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看订单快递信息</a>
		</div>
	</div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
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
              toolbar:'#tools',
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              rowStyler: function(index,row){
					if (row.timelevel == '1'){
						return 'color:green;font-weight:bold;';
					}else if (row.timelevel == '2'){
						return 'color:blue;font-weight:bold;';
					}else if (row.timelevel == '3'){
						return 'color:red;font-weight:bold;';
					}else{
						return '';
					}
				},
			   frozenColumns:[[
                              { field: 'ordercode', title: '订单编号',width:150,align:"center",sortable:"true"},
                              { field: 'username', title: '客户姓名',width:150,align:"center",},
                              { field: 'phone', title: '联系电话',width:150,align:"center",sortable:"true"},  
                              { field: 'statusname', title: '状态',width:150,align:"center",},
                         ]],
               columns: [[
							  { field: 'sourcename', title: '客户来源',width:150,align:"center",},
							  { field: 'address', title: '安装地址',width:350,align:"center",
							  	    formatter: function (value) {
							              return "<span title='" + value + "'>" + value + "</span>";
							          },
							  },
							  { field: 'firstpayment', title: '首付金额',width:150,align:"center",},
							  { field: 'firstarrivalflagname', title: '首付金额标识',width:150,align:"center",},
							  { field: 'finalpayment', title: '尾款金额',width:150,align:"center",},
							  { field: 'finalarrivalflagname', title: '尾款金额标识',width:150,align:"center",},
							  { field: 'productfee', title: '产品费用',width:150,align:"center",},
							  { field: 'otherfee', title: '其他费用',width:150,align:"center",},
							  { field: 'totalmoney', title: '总金额',width:150,align:"center",},
							  { field: 'addtime', title: '添加时间',width:150,align:"center",sortable:"true"},
							  { field: 'visittypename', title: '上门类型',width:150,align:"center",},
							  { field: 'salername', title: '销售员姓名',width:150,align:"center",},
							  { field: 'talkername', title: '讲解员姓名',width:150,align:"center",},
			                  { field: 'visitorname', title: '上门员姓名',width:150,align:"center",},
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
	 		filingflag:$("#filingflag").val(),
	 		timelevel:$("#timelevel").val(),
	 		firstarrivalflag:$("#firstarrivalflag").val(),
	 		finalarrivalflag:$("#finalarrivalflag").val(),
	 	};
	 	return json;
	 }
	
	function queryUserorder() {
		$('#userorderDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	
	function lookUserorder() {
		var selected = $('#userorderDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        id = selected.id;
		
        parent.addTab('订单信息','userorder/lookInit?rid='+ Math.random()+'&id='+id);
        
	 }
	
	function updateUserorder() {
		
		var selected = $('#userorderDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        id = selected.id;
		
	    dialog = $.dialog({
		    title: '订单修改',
		    lock: true,
		    width: 1100,
		    height: 650,
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
     
	 //合同查看
	 function lookUsercontract() {
		var selected = $('#userorderDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        ordercode = selected.ordercode;
		
        parent.addTab('合同信息','usercontract/findByList?rid='+ Math.random()+'&ordercode='+ordercode);
        
	 } 
	 
	 
	//快递查看
	 function lookUserdelivery() {
		var selected = $('#userorderDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        ordercode = selected.ordercode;
        
        parent.addTab('快递信息','userdelivery/findByList?rid='+ Math.random()+'&ordercode='+ordercode);
	 }  
	 
	function closeDialog(){
		dialog.close();
		$('#userorderDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteUserorder(){
		
		var selected = $('#userorderDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        id = selected.id;
		
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
    
    function uploadUsercontract(orderid,ordercode) {	
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
		    content: 'url:usercontract/findByList?rid='+Math.random()+'&orderid='+orderid+'&ordercode='+ordercode
		});
	 }
    
    function uploadUserdelivery(deliveryno) {	
	    dialog = $.dialog({
		    title: '快递信息',
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
		    content: 'url:userdelivery/findUserdeliveryInfo?rid='+Math.random()+'&deliveryno='+deliveryno
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
	 
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryUserorder();
	    }  
	});
</script>
</body>
</html>