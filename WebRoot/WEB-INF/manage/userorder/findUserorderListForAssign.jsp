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
			                <option value="2" <c:if test="${userorder.status == '2' }">selected</c:if>>已发货</option>
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
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "userorderDG" style="width:auto; height:auto;"></div>
    <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:assignUserorder();" class="easyui-linkbutton" iconCls="icon-tip"  plain="true">订单派工</a>
			<a href="javascript:lookUserorder();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看订单详情</a>
			<a href="javascript:lookUsercontract();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看合同信息</a>
			<a href="javascript:lookUserdelivery();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看订单快递信息</a>
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
                              { field: 'statusname', title: '状态',width:150,align:"center",
                            	  styler: function (value) {
                            		  return "background-color:yellow;color:red;";
						          },
                              },
                         ]],
               columns: [[
							  { field: 'sourcename', title: '客户来源',width:150,align:"center",},
							  { field: 'address', title: '安装地址',width:350,align:"center",
							  	    formatter: function (value) {
							              return "<span title='" + value + "'>" + value + "</span>";
							          },
							  },
							  { field: 'paytypename', title: '支付类型',width:150,align:"center",},
							  { field: 'firstpayment', title: '首付金额',width:150,align:"center",},
							  { field: 'finalpayment', title: '尾款金额',width:150,align:"center",},
							  { field: 'finalarrivalflagname', title: '尾款金额标识',width:150,align:"center",},
							  { field: 'productfee', title: '产品费用',width:150,align:"center",},
							  { field: 'otherfee', title: '其他费用',width:150,align:"center",},
							  { field: 'totalmoney', title: '总金额',width:150,align:"center",},
							  { field: 'addtime', title: '添加时间',width:150,align:"center",},
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
	 	};
	 	return json;
	 }
	
	function queryUserorder() {
		$('#userorderDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function assignUserorder() {
		
		var selected = $('#userorderDG').datagrid("getSelected");
	    if(selected == null){
	  	     $.dialog.tips('请选择选项', 2, 'alert.gif');
			     return;
	    }
	    //获取需要修改项的ID
	    id = selected.id;
		
	    dialog = $.dialog({
		    title: '订单派工',
		    lock: true,
		    width: 1100,
		    height: 700,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:userorder/assignInit?rid='+ Math.random()+'&id='+id
		});
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
    
  //快递查看
	 function lookUserdelivery() {
		var selected = $('#userorderDG').datagrid("getSelected");
       if(selected == null){
  	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
       }
       //获取需要修改项的ID
       ordercode = selected.ordercode;
		
	    dialog = $.dialog({
		    title: '快递信息',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:userdelivery/findByList?rid='+ Math.random()+'&ordercode='+ordercode
		});
	 } 
</script>
</body>
</html>