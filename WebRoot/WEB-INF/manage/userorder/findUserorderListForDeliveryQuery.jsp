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
		    	    <td align="right" width="120px">订单号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="ordercode" id="ordercode" value="${userdelivery.ordercode }" readonly="readonly" style="background:#eeeeee;">
		    		</td>
		    		
		    		<td height="26"  align="right">开始时间：</td>
                    <td >
                    	<input type="text" id="begintime" name="begintime"   value="${userdelivery.begintime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w100">
                    </td>
   		    		<td align="right" >结束时间：</td>
                    <td >
                    	<input type="text" id="endtime" name="endtime"  value="${userdelivery.endtime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'begintime\');}'});" class="Wdate inp w100">
					</td>
					<td align="right" width="130">
						<a href="javascript:queryUserdelivery();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
		    	<tr>
		    		<td align="right" width="120px">送货单状态：</td>
		    		<td>
		    			<select id="status" name="status" class="select" onchange="queryUserdelivery();">
		    				<option value="" <c:if test="${userdelivery.status == ''}">selected</c:if>>请选择</option>
							<option value="0" <c:if test="${userdelivery.status == '0'}">selected</c:if>>未到货</option>
							<option value="1" <c:if test="${userdelivery.status == '1'}">selected</c:if>>已到货</option>
						</select>
		    		</td>
					<td align="right">送货人姓名：</td>
					<td colspan="3">
						<input type="text"  class="inp w200" name="deliverercode" id="deliverercode" readonly="readonly" style="background:#eeeeee;" >
						<input type="text"  class="inp w200" name="deliverername" id="deliverername" readonly="readonly" style="background:#eeeeee;" >
						<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						<a href="javascript:cleanEmployee();" class="easyui-linkbutton" iconCls="icon-undo" style="width:80px">清除</a>
					</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="easyui-panel" style="width:100%;">
	    <div id = "userdeliveryDG" style="width:100%; height: 450px"></div>
	    <div id="tools">
			<div style="margin-bottom:5px">
				<a href="javascript:updateUserdelivery();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
				<!--
				<a href="javascript:deleteUserdelivery();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
				 -->
				<a id = "lookUserdelivery" href="javascript:lookUserdelivery();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看快递跟踪信息</a>
				<a href="javascript:printUserdelivery();" class="easyui-linkbutton" iconCls="icon-print"  plain="true">在线打印</a>
			</div>
		</div>
	</div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="<%=basePath%>js/lodop/LodopFuncs.js"></script>
<script type="text/javascript">
	
	function initUserdeliveryDatagrid(){
         $('#userdeliveryDG').datagrid({
              title: '发货列表信息',
              queryParams: paramsJson(),
              url:'userdelivery/findListJson',
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
                              { field: 'ordercode', title: '订单编号',width:150,align:"center",sortable:"true"},
                              { field: 'username', title: '客户姓名',width:150,align:"center",},
                              { field: 'deliverercode', title: '送货人编号',width:100,align:"center",},
                              { field: 'deliverername', title: '送货人姓名',width:100,align:"center",},
                         ]],
              columns: [[
                          { field: 'statusname', title: '送货单状态',width:150,align:"center",},
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
						  { field: 'totalmoney', title: '总金额',width:150,align:"center",},
		                  { field: 'deliverytime', title: '发货时间',width:100,align:"center",sortable:"true",},
		                  { field: 'deliveryname', title: '快递名称',width:100,align:"center",},
		                  { field: 'deliverycode', title: '快递单号',width:100,align:"center",},
		                  { field: 'delivererphone', title: '送货人电话',width:100,align:"center",},
		                  { field: 'content', title: '送货详情',width:100,align:"center",
		                	  formatter: function (value) {
					              return "<span title='" + value + "'>" + value + "</span>";
					          },
		                  },
              ]]
          });
	}
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		ordercode:$("#ordercode").val(),
	 		deliverercode:$("#deliverercode").val(),
	 		status:$("#status").val(),
	 		begintime:$("#begintime").val(),
	 		endtime:$("#endtime").val(),
	 	};
	 	return json;
	 }
	
	function queryUserdelivery() {
		$('#userdeliveryDG').datagrid('reload',paramsJson());
	}
	 
	var dialog;
	function updateUserdelivery() {
		
		var selected = $('#userdeliveryDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
        
        //获取需要修改项的ID
        var id = selected.id;
		
	    dialog = $.dialog({
		    title: '送货信息修改',
		    lock: true,
		    width: 1100,
		    height: 700,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:userdelivery/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	
	/**
	*删除
	*/
	function deleteUserdelivery(id){
		
		var selected = $('#userdeliveryDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
		
		$.dialog.confirm('确认是否删除？', function(){ 
			var data = {
					     id: id
					   };
			var url="userdelivery/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 2, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#userdeliveryDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	
	function closeDialog(){
		dialog.close();
		$('#userdeliveryDG').datagrid('reload');// 刷新当前页面
	}
	
	
	$(function () {
	   
	   //初始化任务单列表
	   initUserdeliveryDatagrid();
		
       var returninfo = '${userdelivery.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
    //function lookUserdelivery() {	
    //	var selected = $('#userdeliveryDG').datagrid("getSelected");
    //    if(selected == null){
   	//     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
	//	     return;
    //    }
    //    //获取需要修改项的ID
    //    var deliverycode = selected.deliverycode;
    //    var url = 'userdelivery/findUserdeliveryInfo?rid='+Math.random()+'&deliverycode='+deliverycode
     //   window.open(url);
	// }
    
    function lookUserdelivery() {
    	
    	var selected = $('#userdeliveryDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
    	
        //获取需要修改项的ID
        var deliverycode = selected.deliverycode;
        
	    dialog = $.dialog({
		    title: '快递信息',
		    lock: true,
		    width: 1000,
		    height: 600,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    //esc: false,
		    //cancel:false,
		    content: 'url:userdelivery/findUserdeliveryInfo?rid='+Math.random()+'&deliverycode='+deliverycode
		});
	 }
    
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
	    
		$("#deliverercode").val(jsonObject.employeecode);
		$("#deliverername").val(jsonObject.employeename);
		queryUserdelivery();
	}
	
	function cleanEmployee() {
		  $('#deliverercode').val("");
		  $('#deliverername').val("");
		  queryUserdelivery();
	}
	
	function printUserdelivery() {
		var selected = $('#userdeliveryDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要操作的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        //var id = selected.id;
        //订单编号
        var ordercode = selected.ordercode;
        //订单编号
        var totalmoney = selected.totalmoney;
        //客户编号
        var username = selected.username;
        //客户电话
        var userphone = selected.userphone;
        //客户地址
        var address = selected.address;
        //产品型号
        var productmodel = selected.productmodel;
        //产品颜色
        var productcolor = selected.productcolor;
        //送货人姓名
        var deliverername = selected.deliverername;
        //送货人电话
        var delivererphone = selected.delivererphone;
        
        //开始打印
	    LODOP = getLodop();
	    //LODOP.SET_PRINT_PAGESIZE (0,40+"mm", 30+"mm","");  
	    
	    LODOP.PRINT_INITA(0,0,200,333,"");
	    
	    //以下代码为大标签纸打印效果
	    //LODOP.SET_PRINT_PAGESIZE(1,140+"mm",90+"mm","");
	    //LODOP.ADD_PRINT_TEXT(25,218,431,20,"送货单信息"); 
		//LODOP.ADD_PRINT_TEXT(50,40,100,20, "送货人姓名：");
		//LODOP.ADD_PRINT_TEXT(50,110,400,20, deliverername);
		//LODOP.ADD_PRINT_TEXT(75,40,100,20,"送货人电话：");
		//LODOP.ADD_PRINT_TEXT(75,110,400,20, delivererphone);
		//LODOP.ADD_PRINT_TEXT(100,40,100,20, "订单编号：");
		//LODOP.ADD_PRINT_TEXT(100,110,400,20, ordercode);
		//LODOP.ADD_PRINT_TEXT(125,40,100,20, "产品型号："); 
		//LODOP.ADD_PRINT_TEXT(125,110,400,20, productmodel);
		//LODOP.ADD_PRINT_TEXT(150,40,100,20, "产品颜色：");
		//LODOP.ADD_PRINT_TEXT(150,110,400,20, productcolor);
		//LODOP.ADD_PRINT_TEXT(175,40,100,20, "客户姓名：");
		//LODOP.ADD_PRINT_TEXT(175,110,400,20, username);
		//LODOP.ADD_PRINT_TEXT(200,40,100,20, "客户电话：");
		//LODOP.ADD_PRINT_TEXT(200,110,400,20, userphone);
		//LODOP.ADD_PRINT_TEXT(225,40,100,20, "客户地址：");
		//LODOP.ADD_PRINT_TEXT(225,110,300,20, address);
		
		//小标签纸打印效果（SN条形码纸张）
		LODOP.SET_PRINT_PAGESIZE (0,40+"mm", 30+"mm","");  
		LODOP.SET_PRINT_STYLE("FontSize", 8); //字体大小  
		LODOP.ADD_PRINT_TEXT(10,10,250,10, "送货人:" + deliverername);
		LODOP.ADD_PRINT_TEXT(25,10,250,10, "总金额:" + totalmoney);
		LODOP.ADD_PRINT_TEXT(40,10,250,10, "客户:" + userphone + " " + username);
		LODOP.ADD_PRINT_TEXT(55,10,140,50, "地址:" + address);
		
	    LODOP.PREVIEW();
	    //LODOP.PRINT();
	    
	 }
</script>
</body>
</html>