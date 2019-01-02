<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<link rel="stylesheet" type="text/css" href="style/easyui/easyui.css">
<link rel="stylesheet" type="text/css" href="main/plugin/easyui/themes/icon.css">
<style type="text/css">
.easyui-panel table tr {
	height: 30px;
}
</style>
</head>
<body>
	<div id="body">
		<form method="post" id="addForm" name="addForm">
			<div class="easyui-panel" title="产品信息" style="width:100%;" data-options="footer:'#ft'">
					<table width="100%">
						<tr>
							<td height="30" width="15%" align="right">产品编码：</td>
							<td width="25%"><input type="text" class="inp" name="productcode" id="productcode" value="${product.productcode }"> <span class="red">*</span></td>
						</tr>
						<tr>
							<td height="30" width="15%" align="right">产品名称：</td>
							<td width="25%"><input type="text" class="inp" name="productname" id="productname" value="${product.productname }"> <span class="red">*</span></td>
						</tr>
						<tr>
							 <td align="right">产品类别：</td>
							 <td>
								<input id="typecode" name="typecode"> <span class="red">*</span>
							 </td>
						  </tr>
						<tr>
							<td align="right">产品价格：</td>
							<td>
								<input type="text" class="inp easyui-numberbox" name="price" id="price" value="${product.price }" maxlength="8">
							</td> 
						</tr>
						<tr>
							<td align="right">产品计算单位：</td>
							<td><input type="text" class="inp" name="productunit" id="productunit" value="${product.productunit }"></td>
						</tr>
						<tr>
							<td align="right">产品来源：</td>
							<td>
								<select id="productsource" name="productsource" class="select">
									<option value="0" <c:if test="${product.productsource == '0'}">selected</c:if>>自产</option>
									<option value="1" <c:if test="${product.productsource == '1'}">selected</c:if>>外购</option>
								</select>
							</td>
						</tr>
					</table>
				</div>
				<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveProduct();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
				    </cite><span class="red">${product.returninfo}</span>
				</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript">
	
	function checkVal() {
		if ($("#productcode") != undefined && ($("#productcode").val() == "" || $("#productcode").val() == null )) {
			$.dialog.tips("请输入产品编码", 1, "alert.gif", function() {
				$("#productcode").focus();
			});
			return false;
		}
		
		if ($("#productname") != undefined && ($("#productname").val() == "" || $("#productname").val() == null )) {
			$.dialog.tips("请输入产品名称", 1, "alert.gif", function() {
				$("#productname").focus();
			});
			return false;
		}
		
		return true;
	}
	
	function saveProduct() {
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
		    	$("#addForm").attr("action", "product/save?rid="+Math.random());
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
	
	function goback(){
		parent.closeDialog();
	}
	
	$(function () {
       var returninfo = '${product.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
	
	//加载列表
	$('#typecode').combobox({    
	    url:'producttype/getProducttypeComboBoxJson',    
	    valueField:'typecode',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${product.typecode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
              
        } 
	}); 
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryUser();
	    }  
	});
</script>
</body>
</html>
