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
 <form action="" method="post" id="searchForm" name="searchForm">
	<div id="body">
    <div class="seh-box">
            <table width="100%">
		    	<tr>
		    		<td align="right" width="120px">客户姓名：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="username" id="username" value="${userdoor.userorder.username }" readonly="readonly" style="background:#eeeeee;">
		    		</td>
		    		<td align="right" width="120px">客户编号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="usercode" id="usercode" value="${userdoor.userorder.usercode }" readonly="readonly" style="background:#eeeeee;">
		    		</td>
		    		<td align="right" width="120px">客户电话：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="phone" id="phone" value="${userdoor.userorder.phone }" readonly="readonly" style="background:#eeeeee;">
		    		</td>
		    	    <td align="right" width="120px">订单编号：</td>
		    		<td>
		    			<input type="text"  class="inp w200" name="ordercode" id="ordercode" value="${userdoor.userorder.ordercode }" readonly="readonly" style="background:#eeeeee;">
		    		</td>
					<td align="right" width="130">
						<a href="javascript:queryUserdoor();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
    </div>
    <div class="easyui-panel" style="width:100%;">
	    <div id = "userdoorDG" style="width:100%; height: 400px"></div>
	    <div id="tools">
			<div style="margin-bottom:5px">
				<a href="javascript:addUserdoor();" class="easyui-linkbutton" iconCls="icon-add"  plain="true">添加</a>
				<a id = "lookUserdoor" href="javascript:lookUserdoor();" class="easyui-linkbutton" iconCls="icon-search"  plain="true">门锁查看</a>
				<a href="javascript:deleteUserdoor();" class="easyui-linkbutton" iconCls="icon-cut"  plain="true">删除</a>
			</div>
		</div>
	</div>
	<div id="imageinfo"></div>
	<div class="easyui-panel" title="订单门锁数据" style="width:100%;" data-options="footer:'#ft'">
       <table style="width:100%">
		   <tr>
				<td height="30" width="15%" align="right">锁侧板长度(毫米)：</td>
				<td width="25%">
					<input type="text" class="inp" name="locklength" id="locklength" value="${userdoor.userdoordata.locklength }">
				</td>
			</tr>
			<tr>
				<td height="30" width="15%" align="right">锁侧板宽度(毫米)：</td>
				<td>
					<input type="text" class="inp" name="lockwidth" id="lockwidth" value="${userdoor.userdoordata.lockwidth }">
				</td>
			</tr>
			<tr style="height: 10px">
				<td>
				<td>
			</tr>
        </table>
      </div>
	<div id="ft" style="padding:5px;">
		<cite> 
			<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
			<a href="javascript:saveUserdoordata();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
	    </cite><span class="red">${userdoor.returninfo}</span>
	</div>
</div>
</form>   
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="main/plugin/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript">
	
	function initUserdoorDatagrid(){
         $('#userdoorDG').datagrid({
              title: '门锁列表信息',
              queryParams: paramsJson(),
              url:'userdoor/findListJson',
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
                  { field: 'ordercode', title: '订单编号',width:100,align:"center"},
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
	     	var rvalue = "<img onclick=showImg(\""+value+"\") style='width:66px; height:60px;margin-left:3px;' src='userdoor/lookUserdoor?id=" + value + "' title='点击查看图片'/>";  
	     	return rvalue;        
	    }  
	}  
	
	function showImg(img) {
		var url = "userdoor/lookUserdoor?id=" + img;
		 window.open(url);
	}   
	
	function queryUserdoor() {
		$('#userdoorDG').datagrid('reload',paramsJson());
	}
	 
	function addUserdoor() {	
	    dialog = $.dialog({
		    title: '门锁添加',
		    lock: true,
		    width: 850,
		    height: 550,
		    top: 0,
		    drag: false,
		    resize: false,
		    max: false,
		    min: false,
		    content: 'url:userdoor/addInit?rid='+Math.random()+'&ordercode='+$("#ordercode").val()
		});
	 }
  
	function closeDialog(){
		dialog.close();
		$('#userdoorDG').datagrid('reload');// 刷新当前页面
	}
	
	/**
	*删除
	*/
	function deleteUserdoor(id){
		
		var selected = $('#userdoorDG').datagrid("getSelected");
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
			var url="userdoor/delete?rid="+Math.random();
			$.getJSON(url,data,function(jsonMsg){
				$.dialog.tips(jsonMsg.msg, 2, 'alert.gif');
				if(jsonMsg.flag=="1"){
					$('#userdoorDG').datagrid('reload');// 刷新当前页面
				}else{
				}
			});
		}, function(){
			
			});
		
	}
	
	$(function () {
	   
	   //初始化任务单列表
	   initUserdoorDatagrid();
		
       var returninfo = '${userdoor.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
    function lookUserdoor() {	
	    
    	var selected = $('#userdoorDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的选项', 2, 'alert.gif');
		     return;
        }
        //获取需要修改项的ID
        var id = selected.id;
        
        var url = "userdoor/lookUserdoor?id="+id;
        window.open(url);
	 }
    
    function goback(){
    	parent.closeTabSelected();
		//parent.closeDialog();
  	}
    
    function saveUserdoordata() {
    	if ($("#locklength") != undefined && ($("#locklength").val() == "" || $("#locklength").val() == null )) {
			$("#locklength").focus();
			$.dialog.tips("请输入锁侧板长度", 1, "alert.gif");
			return false;
		}
		
		if ($("#lockwidth") != undefined && ($("#lockwidth").val() == "" || $("#lockwidth").val() == null )) {
			$("#lockwidth").focus();
			$.dialog.tips("请输入锁侧板宽度", 2, "alert.gif");
			return false;
		}
		
		$.dialog({
		    title: '提交',
		    lock: true,
		    background: '#000', /* 背景色 */
		    opacity: 0.5,       /* 透明度 */
		    content: '是否确定执行？',
		    icon: 'alert.gif',
		    ok: function () {
		    	$("#searchForm").attr("action", "userdoor/saveUserdoordata?rid="+Math.random());
				$("#searchForm").submit();
		        /* 这里要注意多层锁屏一定要加parent参数 */
		        $.dialog({
		        	lock: true,
		            title: '提示',
		        	content: '执行中，请等待......', 
		        	max: false,
				    min: false,
				    cancel:false,
		        	icon: 'loading.gif',
		        });
		        return false;
		    },
		    okVal: '确定',
		    cancel: true,
		    cancelVal:'取消'
		});
	}
    
</script>
</body>
</html>