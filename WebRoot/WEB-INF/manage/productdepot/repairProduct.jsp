<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm">
		    <input type="hidden" name="id" id="id" value="${productdepot.productdepot.id}">
			<div class="easyui-panel" title="产品信息"  style="width:100%;"  data-options="footer:'#ft'">
				<table style="width:100%">
					<tr>
						<td align="right">产品SN标识：</td>
						<td>
							<input type="text" id="productidentfy" name="productidentfy"  class="inp" readonly="readonly" style="background:#eeeeee;" value="${productdepot.productdepot.productidentfy}">
						</td>
					</tr>
					<tr>
						<td align="right">产品编号：</td>
						<td>
							<input type="text" id="productcode" name="productcode" class="inp" readonly="readonly" style="background:#eeeeee;" value="${productdepot.productdepot.productcode}">
						</td>
					</tr>
					<tr>
						<td align="right">产品名称：</td>
						<td>
							<input type="text" id="productname" name="productname" class="inp" readonly="readonly" style="background:#eeeeee;" value="${productdepot.productdepot.productname}">
						</td>
					</tr>
					<tr>
						<td align="right">出现问题原因：</td>
						<td>
							<textarea id="productproblem" name="productproblem" style="width:400px; height:30px;">${productproblem}</textarea>
						</td>
					</tr>
					<tr>
						<td align="right">维修信息：</td>
						<td>
							<textarea id="repairinfo" name="repairinfo" style="width:400px; height:30px;">${repairinfo}</textarea>
						</td>
					</tr>
					<tr>
						<td align="right">维修状态：</td>
						<td>
							<select id="repairstatus" name="repairstatus" class="select" style="width: 110px;">
								<option value="1">已维修好</option>
								<option value="0">未维修好</option>
							</select>
						</td>
					</tr>
				</table>
			</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveProductrepair();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">产品维修</a>
				    </cite><span class="red">${productdepot.returninfo}</span>
			</div>
		</form>		
	</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>main/plugin/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript">

  $(function () {
	     var returninfo = '${productdepot.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
	  parent.closeTabSelected();
		//parent.closeDialog();
  }
	
  function checkVal() {
	    
		if ($("#repairinfo") != undefined && ($("#repairinfo").val() == "" || $("#repairinfo").val() == null )) {
			$.dialog.tips("请输入维修信息", 1, "alert.gif", function() {
				$("#repairinfo").focus();
			});
			return false;
		}
		
		return true;
	}
	
  function saveProductrepair() {
  
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
		    	$("#addForm").attr("action", "productdepot/saveProductrepair");
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
  
  
	 function checkLength(object, maxlength) {
	    var obj = $('#' + object),
	        value = $.trim(obj.val());
	    if (value.length > maxlength) {
	      obj.val(value.substr(0, maxlength));
	    } else {
	      $('#validNum' + object).html(maxlength - value.length);
	    }
	 }
	 
</script>
</body>
</html>
