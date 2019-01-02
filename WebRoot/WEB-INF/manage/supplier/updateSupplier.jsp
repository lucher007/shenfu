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
     #body table tr {
      height: 30px;
    }
  </style>
</head>

<body>
<div id="body">
  <form action="" method="post" id="updateform" name="updateform">
    <input type="hidden" name="id" id="id" value="${supplier.supplier.id}"/>
    <div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
        <table style="width:100%">
          <tr >
            <td align="right">供应商简称：</td>
            <td>
				<input type="text" class="inp" name="name" id="name" value="${supplier.supplier.name }">
			</td>
		  </tr>
		  <tr>
            <td align="right">供应商全称：</td>
          	<td>
				<input type="text" class="inp" style="width:250px;" name="fullname" id="fullname" value="${supplier.supplier.fullname }">
			</td>
          </tr>
          <tr>
			<td align="right">供应商地址：</td>
          	<td>
				<input type="text" class="inp" style="width:250px;" name="address" id="address" value="${supplier.supplier.address }">
			</td>
		   </tr>
		  <tr>
            <td align="right">供应商联系电话：</td>
          	<td>
				<input type="text" class="inp" name="phone" id="phone" value="${supplier.supplier.phone}">
			</td>
		  </tr>
          <tr>
		  	<td align="right">供应商联系人：</td>
          	<td>
				<input type="text" class="inp" name="contactname" id="contactname" value="${supplier.supplier.contactname }">
			</td>
		  </tr>
		  <tr>
			<td align="right">联系人电话：</td>
          	<td>
				<input type="text" class="inp" name="contactphone" id="contactphone" value="${supplier.supplier.contactphone }">
			</td>
		  </tr>
          <tr>
          	<td align="right">联系人邮件：</td>
          	<td>
				<input type="text" class="inp" style="width:250px;" name="contactmail" id="contactmail" value="${supplier.supplier.contactmail }">
			</td>
		  </tr>
		  <tr>
          	<td align="right">状态：</td>
          	<td>
			  <select name="status" id="state" class="select">
              	  <option value="1" <c:if test="${supplier.supplier.status == '1' }">selected</c:if>>有效</option>
                  <option value="0" <c:if test="${supplier.supplier.status == '0' }">selected</c:if>>无效</option>
              </select>
			</td>
		  </tr>
		  <tr>
	  		<td align="right">信息介绍：</td>
            <td colspan="5"> 
				<textarea id="introduceinfo" name="introduceinfo" style="width:550px; height:50px;"
				onKeyDown="checkLength('introduceinfo',300)" onKeyUp="checkLength('introduceinfo',300)">${supplier.supplier.introduceinfo}</textarea>
            	<span class="red">还可以输入<span id="validNumintroduceinfo">300</span>字</span>
            </td>
		  </tr>
      </table>
    </div>
    <div id="ft" style="padding:5px;">
				<cite> 
					<a href="javascript:goBack();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
      				<a href="javascript:updateSupplier();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
			    </cite><span class="red">${supplier.returninfo}</span>
		 </div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  function updateSupplier() {
      $('#updateform').attr('action', 'supplier/update');
      $("#updateform").submit();
  }
  
  function goBack() {
     parent.closeDialog();
  }
  
  $(function () {
	  checkLength('introduceinfo',300);
      var returninfo = '${supplier.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
  });
  
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
