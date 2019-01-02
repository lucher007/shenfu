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
      height: 50px;
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
		    		
					<td align="right" width="130">
						<a href="javascript:queryUserdelivery();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
	    <div id = "userdeliveryDG" style="width:100%; height: 400px"></div>
	    <div id="tools">
			<div style="margin-bottom:5px">
			   <!-- 
				<a href="javascript:addUserdelivery();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
				<a href="javascript:deleteUserdelivery();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
			   -->
				<a id = "lookUserdelivery" href="javascript:lookUserdelivery();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">查看快递跟踪信息</a>
			</div>
		</div>
	</div>
	<div id="ft" style="padding:5px;">
		<cite> 
			<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
	    </cite><span class="red">${userdelivery.returninfo}</span>
	</div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initUserdeliveryDatagrid(){
         $('#userdeliveryDG').datagrid({
              title: '快递列表信息',
              queryParams: paramsJson(),
              url:'userdelivery/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              toolbar:'#tools',
              columns: [[
                  { field: 'ordercode', title: '订单号',width:100,align:"center"},
                  { field: 'deliverytime', title: '发货时间',width:100,align:"center",},
                  { field: 'deliveryname', title: '快递名称',width:100,align:"center",},
                  { field: 'deliverycode', title: '快递单号',width:100,align:"center",},
                  { field: 'deliverercode', title: '送货人编号',width:100,align:"center",},
                  { field: 'deliverername', title: '送货人姓名',width:100,align:"center",},
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
	 	};
	 	return json;
	 }
	
	function queryUserdelivery() {
		$('#userdeliveryDG').datagrid('reload',paramsJson());
	}
	 
	function addUserdelivery() {	
	    dialog = $.dialog({
		    title: '合同添加',
		    lock: true,
		    width: 1000,
		    height: 750,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:userdelivery/addInit?rid='+Math.random()+'&ordercode='+$("#ordercode").val()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#userdeliveryDG').datagrid('reload');// 刷新当前页面
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
    
    
    function goback(){
    	parent.closeTabSelected();
		//parent.closeDialog();
  	}
    
</script>
</body>
</html>