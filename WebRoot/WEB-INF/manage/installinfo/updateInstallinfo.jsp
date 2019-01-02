<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<title></title>
<link type="text/css" rel="stylesheet" href="style/user.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>main/plugin/uploadify/uploadify.css" />
<link rel="stylesheet" type="text/css" href="style/easyui/easyui.css">
<link rel="stylesheet" type="text/css" href="main/plugin/easyui/themes/icon.css">
<style type="text/css">
.easyui-panel table tr {
	height: 20px;
}
</style>
</head>
<body>
<div id="body">
	<form method="post" id="addForm" name="addForm">
		<input type="hidden" name="id" id="id" value="${installinfo.installinfo.id}"/>
		<input type="hidden" name="operatorcode" id="operatorcode" value="${installinfo.installinfo.operatorcode}"/>
    	<div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
			<div class="easyui-panel" title="组装信息" style="width:100%;">
				<table style="width:100%">
					<tr>
						<td align="right" width="10%">生产批次号：</td>
						<td width="15%">
							<input type="text" class="inp w200" name="producecode" id="producecode" readonly="readonly" style="background:#eeeeee;" value="${installinfo.installinfo.producecode }">
						</td>
						<td height="30" align="right" width="10%">材料编码：</td>
						<td width="20%">
							<input type="text" class="inp"  id="materialcode" name="materialcode" readonly="readonly" style="background:#eeeeee;" value="${installinfo.installinfo.materialcode}"> <span class="red">*</span>
						</td> 
					</tr>
					<tr>
						<td align="right" width="10%">材料名称：</td>
						<td width="15%">
							<input type="text" class="inp" name="materialname" id="materialname" readonly="readonly" style="background:#eeeeee;" value="${installinfo.installinfo.materialname}"> <span class="red">*</span>
						</td>
						<td align="right" width="10%">生产数量：</td>
						<td width="15%">
							<input type="text" class="inp" name="amount" id="amount" readonly="readonly" style="background:#eeeeee;" value="${installinfo.installinfo.amount}"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">生产负责人编号：</td>
						<td width="15%">
							<input type="text" class="inp w200" name="producercode" id="producercode" readonly="readonly" style="background:#eeeeee;" value="${installinfo.installinfo.producercode }">
							<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						</td>
						<td align="right" width="10%">生产负责人姓名：</td>
						<td width="15%" colspan="3">
							<input type="text" class="inp w200" name="producername" id="producername" readonly="readonly" style="background:#eeeeee;" value="${installinfo.installinfo.producername }"><span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">产品版本：</td>
						<td width="15%">
							<input type="text" class="inp" style="width: 200px" name="materialversion" id="materialversion" value="${installinfo.installinfo.materialversion}">
						</td>
					</tr>
					<tr>
						<td align="right">组装信息：</td>
			            <td colspan="5"> 
							<textarea id="installinformation" name="installinformation" style="width:550px; height:50px;">${installinfo.installinfo.installinformation}</textarea>
		               </td>
					</tr>
				</table>
			</div>
		</div>
		<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:updateInstallinfo();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
				    </cite><span class="red">${installinfo.returninfo}</span>
			</div>
	</form>		
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  $(function () {
	     var returninfo = '${installinfo.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
	  parent.closeTabSelected();
  }
	
  function checkVal() {
	    if ($("#producercode") != undefined && ($("#producercode").val() == "" || $("#producercode").val() == null )) {
			$("#producercode").focus();
			$.dialog.tips("请选择生产负责人", 2, 'alert.gif');
			return false;
		}
		
		return true;
	}
	
  function updateInstallinfo() {
		if (!checkVal()) {
			return;
		}
		
		$.dialog({
		    title: '提交',
		    lock: true,
		    background: '#000', /* 背景色 */
		    opacity: 0.5,       /* 透明度 */
		    content: '是否确定执行？',
		    icon: 'alert.gif',
		    ok: function () {
		    	$("#addForm").attr("action", "installinfo/update?rid="+Math.random());
				$("#addForm").submit();
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
  
  var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '装维员工查询',
			lock : true,
			width : 1100,
			height : 600,
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
		$("#producercode").val(jsonObject.employeecode);
		$("#producername").val(jsonObject.employeename);
	}
	
	function cleanEmployee() {
		  $('#producercode').val("");
		  $('#producername').val("");
	}	
    
</script>
</body>
</html>
