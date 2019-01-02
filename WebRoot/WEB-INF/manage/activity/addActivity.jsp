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
  <form action="activity!save" method="post" id="addForm" name="addForm">
    <div class="easyui-panel" title="活动消息" style="width:100%;" data-options="footer:'#ft'">	
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="30" width="15%" align="right">活动编码：</td>
            <td width="25%">
            	<input type="text" class="inp" name="activitycode" id="activitycode" value="${activity.activitycode }">
            </td>
          </tr>
          <tr>
            <td height="30" width="15%" align="right">标题：</td>
            <td width="25%">
            	<input type="text" class="inp"  style="width: 400px;'" name="title" id="title" value="${activity.title }">
            </td>
          </tr>
          <tr>
            <td align="right">内容：</td>
            <td>
               <textarea id="content" name="content" style="width:550px; height:80px;"
		                       onKeyDown="checkLength('content',300)" onKeyUp="checkLength('content',300)">${activity.content}</textarea>
		            		<span class="red">还可以输入<span id="validNumcontent">300</span>字</span>
            </td>
          </tr>
        </table>
      </div>
      <div id="ft" style="padding:5px;">
			<cite> 
				<a href="javascript:goBack();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
				<a href="javascript:saveActivity();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
		    </cite><span class="red">${activity.returninfo}</span>
	</div>
  </form>
</div>
<script type="text/javascript" language="javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" language="javascript" src="js/form.js"></script>
<script language="javascript" type="text/javascript" src="js/comtools.js"></script>
<script language="javascript" type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  //判断是否为数字
  function checkNumberChar(ob) {
    if (/^\d+$/.test(ob)) {
      return true;
    } else {
      return false;
    }
  }

  function saveActivity() {
	    if ($('#activitycode').val() == '') {
	      $.dialog.tips('请输入标题', 2, 'alert.gif');
	      $('#activitycode').focus();
	      return;
	    }
	   
	    if ($('#title').val() == '') {
	      $.dialog.tips('请输入标题', 2, 'alert.gif');
	      $('#title').focus();
	      return;
	    }
	    
	    if ($('#content').val() == '') {
	      $.dialog.tips('请输入内容', 2, 'alert.gif');
	      $('#content').focus();
	      return;
	    }
	   
	    $("#addForm").attr("action", "activity/save");
	    $("#addForm").submit();
  }
  
  function goBack() {
	  parent.closeDialog();
  }
  
  $(function () {
       var returninfo = '${activity.returninfo}';
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
