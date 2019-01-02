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
		    			<input type="text"  class="inp w200" name="ordercode" id="ordercode" value="${usercontract.ordercode }" readonly="readonly" style="background:#eeeeee;">
		    		</td>
		    		
					<td align="right" width="130">
						<a href="javascript:queryUsercontract();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
	    <div id = "usercontractDG" style="width:100%; height: 400px"></div>
	    <div id="tools">
			<div style="margin-bottom:5px">
				<a href="javascript:addUsercontract();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
				<a id = "lookUsercontract" href="javascript:lookUsercontract();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">合同查看</a>
				<a href="javascript:deleteUsercontract();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
			</div>
		</div>
	</div>
	<div id="imageinfo"></div>
	<div id="ft" style="padding:5px;">
		<cite> 
			<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
	    </cite><span class="red">${usercontract.returninfo}</span>
	</div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initUsercontractDatagrid(){
         $('#usercontractDG').datagrid({
              title: '合同列表信息',
              queryParams: paramsJson(),
              url:'usercontract/findListJson',
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
                  { field: 'id', title: '图片预览',width:300,align:"center",formatter:imgFormatter},
                  { field: 'addtime', title: '上传时间',width:100,align:"center",
                  	        formatter:function(val, row, index){ 
							 	return val==null?val:val.substring(0,19);
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
	
	//图片添加路径  
	function imgFormatter1(value,row,index){  
	     if('' != value && null != value){  
	     	var strs = new Array(); //定义一数组   
	     	if(value.substr(value.length-1,1)==","){  
	         	value=value.substr(0,value.length-1)  
	     	}  
	     	strs = value.split(","); //字符分割
	     	var rvalue ="";            
	     	for (i=0;i<strs.length ;i++ ){   
	         	rvalue += "<img onclick=download(\""+strs[i]+"\") style='width:66px; height:60px;margin-left:3px;' src='<%=path%>" + strs[i] + "' title='点击查看图片'/>";  
	        }   
	     	return rvalue;        
	    }  
	}  
	
	//图片添加路径  
	function imgFormatter(value,row,index){
	     if('' != value && null != value){  
	     	var rvalue = "<img onclick=showImg(\""+value+"\") style='width:66px; height:60px;margin-left:3px;' src='usercontract/lookUsercontract?id=" + value + "' title='点击查看图片'/>";  
	     	return rvalue;        
	    }  
	}  
	
	function showImg(img) {
		var url = "usercontract/lookUsercontract?id=" + img;
		 window.open(url);
	}   
	
	function queryUsercontract() {
		$('#usercontractDG').datagrid('reload',paramsJson());
	}
	 
	function addUsercontract() {	
	    dialog = $.dialog({
		    title: '合同添加',
		    lock: true,
		    width: 850,
		    height: 550,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:usercontract/addInit?rid='+Math.random()+'&ordercode='+$("#ordercode").val()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#usercontractDG').datagrid('reload');// 刷新当前页面
	}
	
	
	/**
	*删除
	*/
	function deleteUsercontract(id){
		
		var selected = $('#usercontractDG').datagrid("getSelected");
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
			var url="usercontract/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 2, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#usercontractDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化任务单列表
	   initUsercontractDatagrid();
		
       var returninfo = '${usercontract.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
    function lookUsercontract() {	
	    
    	var selected = $('#usercontractDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
        
        var url = "usercontract/lookUsercontract?id="+id;
        window.open(url);
	 }
    
    function goback(){
    	parent.closeTabSelected();
		//parent.closeDialog();
  	}
    
</script>
</body>
</html>