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
  <style type="text/css">
	#body table tr {
	      height: 30px;
	    }
  </style>
</head>

<body>
<div id="body">
  <form action="" method="post" id="updateform" name="updateform">
    <input type="hidden" name="id" id="id" value="${systempara.systempara.id}"/>
    <div class="easyui-panel" title="系统参数信息" style="width:100%;" data-options="footer:'#ft'">	
        <table style="width:100%">
          <tr >
            <td align="right">参数编码：</td>
            <td>
				<input type="text"  class="inp" name="code" id="code"  readonly="readonly" style="background:#eeeeee;" value="${systempara.systempara.code }"> <span class="red">*</span>
			</td>
		  </tr>
          <tr>
			<td align="right">参数名称：</td>
            <td>
              <input type="text"  class="inp" name="name" id="name"  readonly="readonly" style="background:#eeeeee;" value="${systempara.systempara.name }"/> <span class="red">*</span>
            </td>
		   </tr>
		  <tr>
			<td align="right">参数值：</td>
            <td>
            	<c:choose>  
				    <c:when test="${systempara.systempara.code eq 'sale_score'}">   
	       				<input type="text" id="value" name="value" class="inp" value="${systempara.systempara.value }" onkeyup="checkNum(this)" onkeypress="checkNum(this)" onblur="checkNum(this)" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				    </c:when> 
				    <c:when test="${systempara.systempara.code eq 'produce_installinfo'}">   
	       				<textarea id="value" name="value" style="width:550px; height:60px;"
		                       onKeyDown="checkLength('value',100)" onKeyUp="checkLength('value',100)">${systempara.systempara.value}</textarea>
		            		<span class="red">还可以输入<span id="validNumvalue">100</span>字</span>
				    </c:when>  
				     <c:when test="${systempara.systempara.code eq 'cell_extend_price'}">   
	       				<input type="text" id="value" name="value" class="inp" value="${systempara.systempara.value }" onkeyup="checkNum(this)" onkeypress="checkNum(this)" onblur="checkNum(this)" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				   	    <span class="red">以分为单位</span>
				    </c:when>     
				    <c:otherwise>
				    	<input type="text" class="inp" style="width:400px;" name="value" id="value" maxlength="50" value="${systempara.systempara.value }">
				    </c:otherwise>  
				 </c:choose>
             	 <span class="red">*</span>
            </td>
		  </tr>
		  <tr>
		    <td>
		    </td>
		  </tr>
      </table>
    </div>
    <div id="ft" style="padding:5px;">
			<cite> 
				<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
				<a href="javascript:updateSystempara();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
		    </cite><span class="red">${systempara.returninfo}</span>
	</div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" charset="utf-8" src="js/common/systemparaChoose.js"></script>
<script type="text/javascript" src="js/comtools.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  function updateSystempara() {
     if ($('#value').val() == '') {
          $.dialog.tips('<spring:message code="systempara.value.empty"/>', 1, 'alert.gif');
          $('#value').focus();
         return;
      }

      $('#updateform').attr('action', 'systempara/update');
      $("#updateform").submit();
  }
  
  function goback() {
	  parent.closeDialog();
  }
  
  
  $(function () {
      var returninfo = '${systempara.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
  });
  
//文本框输入字符长度判断
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
