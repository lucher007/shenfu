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
		    		<td align="right" width="120px">产品型号编码：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="modelcode" id="modelcode" value="${productmodel.modelcode }">
		    		</td>
		    		<td align="right" width="120px">产品型号名称：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="modelname" id="modelname" value="${productmodel.modelname }">
		    		</td>
					<td align="right" width="130">
						<a href="javascript:queryProductmodel();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="lst-box">
    	<div id = "productmodelDG" style="width:auto; height: 400px"></div>
    	<div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:addProductmodel();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
			<a href="javascript:updateProductmodel();" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">修改</a>
			<a href="javascript:deleteProductmodel();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
		</div>
	</div>
	</div>
    </div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initProductmodelDatagrid(){
         $('#productmodelDG').datagrid({
              title: '产品型号信息',
              queryParams: paramsJson(),
              url:'productmodel/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              fitColumns:true,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              toolbar:'#tools',
              loadMsg:'正在加载数据......',
              columns: [[
			      { field: 'modelcode', title: '型号编码',width:100,align:"center"},
                  { field: 'modelname', title: '型号名称',width:100,align:"center"},
                  { field: 'price', title: '原价',width:100,align:"center"},
                  { field: 'sellprice', title: '出售价格',width:100,align:"center"},
                  <%--
                  { field: 'maxprice', title: '最大浮动价格',width:100,align:"center"},
                  { field: 'minprice', title: '最小浮动价格',width:100,align:"center"},
                  --%>
                  { field: 'discountgain', title: '优惠权限金额',width:100,align:"center"},
                  { field: 'fixedgain', title: '固定返利金额',width:100,align:"center"},
                  { field: 'managergain', title: '销售管家提成',width:100,align:"center"},
                  { field: 'starttime', title: '有效开始时间',width:100,align:"center"},
                  { field: 'endtime', title: '有效结束时间',width:100,align:"center"},
                  { field: 'statusname', title: '状态',width:100,align:"center"},
                  { field: 'content', title: '简介',width:100,align:"center",
                	  formatter: function (value) {
				        	return "<span title='" + value + "' class='tip'>" + value + "</span>";
				        },
                  },
              ]],
              onLoadSuccess:function(data){  
                  $(".tip").tooltip({  
                      onShow: function(){  
                          $(this).tooltip('tip').css({   
                              width:'300',          
                              boxShadow: '1px 1px 3px #292929'                          
                          });  
                      }  
                  });  
               }  
          });
	}
	
	//将表单数据转为json
	function paramsJson(){
	 	var json = {
	 		modelcode:$("#modelcode").val(),
	 		modelname:$("#modelname").val(),
	 	};
	 	return json;
	}
	
	function queryProductmodel() {
		$('#productmodelDG').datagrid('reload',paramsJson());
	}
	
	var dialog;
	function updateProductmodel() {
		
		var selected = $('#productmodelDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        id = selected.id;
		
	    dialog = $.dialog({
		    title: '产品型号修改',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:productmodel/updateInit?rid='+ Math.random()+'&id='+id
		});
	 }
	 
	 function addProductmodel() {	
	    dialog = $.dialog({
		    title: '产品型号添加',
		    lock: true,
		    width: 1100,
		    height: 650,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:productmodel/addInit?rid='+Math.random()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#productmodelDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteProductmodel(){
		
		var selected = $('#productmodelDG').datagrid("getSelected");
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
			var url="productmodel/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 2, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#productmodelDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化产品列表
	   initProductmodelDatagrid();
		
       var returninfo = '${productmodel.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryProductmodel();
	    }  
	});
	
</script>
</body>
</html>