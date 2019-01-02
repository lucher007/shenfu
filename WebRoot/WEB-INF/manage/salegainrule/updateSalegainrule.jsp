<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
  <style>
    .easyui-panel table tr {
		height: 30px;
	}
  </style>
</head>

<body>
<div id="body">
  <form action="" method="post" id="updateform" name="updateform">
    <input type="hidden" name="id" id="id" value="${salegainrule.salegainrule.id}"/>
   		  <div class="easyui-panel" title="提成规则信息" style="width:100%;" data-options="footer:'#ft'">
	       <table width="100%" >
	          <tr>
			      <td height="30" align="right">提成类型：</td>
				  <td >
					   <select id="gaintype" name="gaintype" class="select">
							<option value="0" <c:if test="${salegainrule.salegainrule.gaintype == '0'}">selected</c:if>>销售提成</option>
							<option value="1" <c:if test="${salegainrule.salegainrule.gaintype == '1'}">selected</c:if>>安装提成</option>
						</select>
				   </td>
			  </tr>
	          <tr>
	            <td align="right">提成项编码：</td>
	            <td>
					<input type="text" class="inp" name="gaincode" id="gaincode" readonly="readonly" style="background:#eeeeee;" value="${salegainrule.salegainrule.gaincode }">
				</td>
	          </tr>
	          <tr>
	            <td align="right">提成项名称：</td>
	            <td>
					<input type="text" class="inp" name="gainname" id="gainname" value="${salegainrule.salegainrule.gainname }">
				</td>
	          </tr>
	          <tr>
					 <td height="30" align="right">提成类型：</td>
					 <td >
						 <select id="gainform" name="gainform" class="select">
								<option value="0" <c:if test="${salegainrule.salegainrule.gainform == '0'}">selected</c:if>>百分比</option>
								<option value="1" <c:if test="${salegainrule.salegainrule.gainform == '1'}">selected</c:if>>绝对数值</option>
						</select>
					 </td>
			  </tr>
	          <tr>
				<td align="right">提成百分比(%)：</td>
	          	<td>
					<input type="text" class="inp easyui-numberbox" name="gainrate" id="gainrate" value="${salegainrule.salegainrule.gainrate }">
				</td>
			  </tr>
			  
			  <tr>
					 <td height="30" width="15%" align="right">状态：</td>
					 <td width="25%">
						 <select id="status" name="status" class="select">
								<option value="0" <c:if test="${salegainrule.salegainrule.status == '0'}">selected</c:if>>无效</option>
								<option value="1" <c:if test="${salegainrule.salegainrule.status == '1'}">selected</c:if>>有效</option>
						</select>
					 </td>
			  </tr>
			  
	        </table>
      </div>
      <div id="ft" style="padding:5px;">
		<cite> 
			<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
			<a href="javascript:updateSalegainrule();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
		</cite>
	    <span class="red">${salegainrule.returninfo}</span>
	  </div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  function updateSalegainrule() {
	  
	  if ($("#gaincode") != undefined && ($("#gaincode").val() == "" || $("#gaincode").val() == null )) {
			$.dialog.tips("请输入提成项编码", 1, "alert.gif", function() {
				$("#gaincode").focus();
			});
			return false;
		}
		
		if ($("#gainname") != undefined && ($("#gainname").val() == "" || $("#gainname").val() == null )) {
			$.dialog.tips("请输入提成项名称", 1, "alert.gif", function() {
				$("#gainname").focus();
			});
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
		    	$("#updateform").attr("action", "salegainrule/update?rid="+Math.random());
				$("#updateform").submit();
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
  
  function goback() {
     parent.closeDialog();
  }
  
  $(function () {
      var returninfo = '${salegainrule.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 2, 'alert.gif');
      }
  });
  
</script>
</body>
</html>
