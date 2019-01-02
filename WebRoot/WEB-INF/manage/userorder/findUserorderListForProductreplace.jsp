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
        	<!--转为订单产品更换查询的条件， 不是新生成的订单 -->
            <input type="hidden" name="queryForReplace" id="queryForReplace" value="1"> 
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
		    		<td align="center" width="130" colspan="2">
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
			                <option value="1" <c:if test="${userorder.status == '1' }">selected</c:if>>已打包</option>
			                <option value="2" <c:if test="${userorder.status == '2' }">selected</c:if>>已发货</option>
			                <option value="3" <c:if test="${userorder.status == '3' }">selected</c:if>>已派工</option>
			             	<option value="4" <c:if test="${userorder.status == '4' }">selected</c:if>>派工接收</option>
			             	<option value="5" <c:if test="${userorder.status == '5' }">selected</c:if>>安装完成</option>
			             	<option value="6" <c:if test="${userorder.status == '6' }">selected</c:if>>审核结单</option>
			             	<option value="10" <c:if test="${userorder.status == '10' }">selected</c:if>>安装失败</option>
			             </select>
		    		</td>
		    		<td align="right" >来源：</td>
        			<td>
        				<select id="source" name="source" class="select" onchange="queryUserorder();">
			                   <option value=""  <c:if test="${userorder.status == ''}">selected</c:if>>请选择</option>
			                   <option value="0" <c:if test="${userorder.status == '0' }">selected</c:if>>销售APP</option>
			                   <option value="1" <c:if test="${userorder.status == '1' }">selected</c:if>>400客服</option>
			                   <option value="2" <c:if test="${userorder.status == '2' }">selected</c:if>>小区驻点</option>
			             </select>
        			</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "userorderDG" style="width:auto; height: 450px"></div>
    <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:saveUserproductreplace();" class="easyui-linkbutton" iconCls="icon-tip"  plain="true">订单产品更换</a>
			<a href="javascript:saveUserproductChangeVersion();" class="easyui-linkbutton" iconCls="icon-reload"  plain="true">产品软件升级</a>
			<a href="javascript:lookUserorder();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看订单详情</a>
			<a href="javascript:lookUsercontract();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看合同信息</a>
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
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              toolbar:'#tools',
              frozenColumns:[[
                              { field: 'ordercode', title: '订单编号',width:150,align:"center",sortable:"true",},
                              { field: 'username', title: '客户姓名',width:150,align:"center",sortable:"true",},
                              { field: 'phone', title: '联系电话',width:150,align:"center",},      
                         ]],
               columns: [[
							  { field: 'sourcename', title: '客户来源',width:150,align:"center",},
							  { field: 'visittypename', title: '上门类型',width:200,align:"center",},
							  { field: 'address', title: '安装地址',width:350,align:"center",
							  	    formatter: function (value) {
							              return "<span title='" + value + "'>" + value + "</span>";
							          },
							  },
							  { field: 'firstarrivalflagname', title: '首付金额标识',width:150,align:"center",},
                              { field: 'paytypename', title: '支付类型',width:150,align:"center",},
							  { field: 'firstpayment', title: '首付金额',width:150,align:"center",},
							  { field: 'finalpayment', title: '尾款金额',width:150,align:"center",},
							  { field: 'finalarrivalflagname', title: '尾款金额标识',width:150,align:"center",},
							  { field: 'productfee', title: '产品费用',width:150,align:"center",},
							  { field: 'otherfee', title: '其他费用',width:150,align:"center",},
							  { field: 'totalmoney', title: '总金额',width:150,align:"center",},
							  { field: 'addtime', title: '添加时间',width:150,align:"center",sortable:"true",},
							  { field: 'visittypename', title: '上门类型',width:200,align:"center",},
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
		 		orderno:$("#orderno").val(),
		 		address:$("#address").val(),
		 		status:$("#status").val(),
		 		source:$("#source").val(),
		 		queryForReplace:$("#queryForReplace").val(),
	 	};
	 	return json;
	 }
	
	function queryUserorder() {
		$('#userorderDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function saveUserproductreplace() {
		
		var selected = $('#userorderDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
 		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
		
        parent.addTab('订单产品确认更换','userorder/saveUserproductreplaceInit?rid='+ Math.random()+'&id='+id);
        
	    //dialog = $.dialog({
		//   title: '订单产品确认',
		///    lock: true,
		//    width: 1100,
		//    height: 700,
		//    top: 0,
		//    drag: false,
		//    resize: false,
		//    max: false,
		//    min: false,
		//    content: 'url:userorder/updateUserproductconfirmInit?rid='+ Math.random()+'&id='+id
		//});
	 }
	
	function saveUserproductChangeVersion() {
		
		var selected = $('#userorderDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
 		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
		
        parent.addTab('产品软件升级','userorder/saveUserproductChangeVersionInit?rid='+ Math.random()+'&id='+id);
        
	    //dialog = $.dialog({
		//   title: '订单产品确认',
		///    lock: true,
		//    width: 1100,
		//    height: 700,
		//    top: 0,
		//    drag: false,
		//    resize: false,
		//    max: false,
		//    min: false,
		//    content: 'url:userorder/updateUserproductconfirmInit?rid='+ Math.random()+'&id='+id
		//});
	}
  
	function closeDialog(){
		dialog.close();
		$('#userorderDG').datagrid('reload');// 刷新当前页面
	}
	
	$(function () {
	   //初始化订单列表
	   initUserorderDatagrid();
		
       var returninfo = '${userorder.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
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
       
	    //dialog = $.dialog({
		//    title: '合同信息',
		////    lock: true,
		//    width: 1100,
		//    height: 650,
		//    top: 0,
		//    drag: false,
		//    resize: false,
		//    max: false,
		//    min: false,
		 //   content: 'url:usercontract/findByList?rid='+ Math.random()+'&ordercode='+ordercode
		//});
	 } 
	 
    function lookUserorder() {
    	
    	var selected = $('#userorderDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
 		     return;
        }
        //获取需要修改项的ID
        id = selected.id;
    	
        parent.addTab('订单信息','userorder/lookInit?rid='+ Math.random()+'&id='+id);
        
	    //dialog = $.dialog({
		//    title: '订单信息',
		//    lock: true,
		//    width: 1100,
		//    height: 650,
		//    top: 0,
		//    drag: false,
		//    resize: false,
		//    max: false,
		//    min: false,
		//    content: 'url:userorder/lookInit?rid='+ Math.random()+'&id='+id
		//});
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