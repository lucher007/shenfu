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
  	<input type="hidden" name="id" id="id" value="${helpinfo.helpinfo.id}"/>
    <input type="hidden" name="status" id="status" value="${helpinfo.helpinfo.status}"/>
    <input type="hidden" name="addtime" id="addtime" value="${helpinfo.helpinfo.addtime}"/>
    <div class="easyui-panel" title="帮助信息" style="width:100%;" data-options="footer:'#ft'">	
        <table style="width:100%">
          <tr >
            <td align="right">类型：</td>
            <td>
				<select id="type" name="type" class="select">
					<option value="0" <c:if test="${helpinfo.helpinfo.type == '0'}">selected</c:if>>行动力</option>
				</select>
			</td>
		  </tr>
		  <tr>
			<td align="right">显示顺序：</td>
			<td>
				<input type="text" class="inp easyui-numberbox" name="showorder" id="showorder" value="${helpinfo.helpinfo.showorder }" maxlength="8">
			</td> 
		 </tr>
          <tr>
			<td align="right">问题：</td>
            <td>
            	 <textarea id="question" name="question" style="width:550px; height:60px;"
		                       onKeyDown="checkLength('question',300)" onKeyUp="checkLength('question',300)">${helpinfo.helpinfo.question}</textarea>
		            		<span class="red">还可以输入<span id="validNumquestion">300</span>字</span><span class="red">*</span>
            </td>
		   </tr>
		  <tr>
			<td align="right">答案：</td>
            <td>
             	<textarea id="answer" name="answer" style="width:550px; height:60px;"
		                       onKeyDown="checkLength('answer',300)" onKeyUp="checkLength('answer',300)">${helpinfo.helpinfo.answer}</textarea>
		            		<span class="red">还可以输入<span id="validNumanswer">300</span>字</span><span class="red">*</span>
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
				<a href="javascript:updateHelpinfo();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
		    </cite><span class="red">${helpinfo.returninfo}</span>
	</div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" charset="utf-8" src="js/common/helpinfoChoose.js"></script>
<script type="text/javascript" src="js/comtools.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  function updateHelpinfo() {
	  if ($('#question').val() == '') {
	      $.dialog.tips('请输入问题', 1, 'alert.gif');
	      $('#question').focus();
	      return;
	    }
	    
	    if ($('#answer').val() == '') {
	      $.dialog.tips('请输入答案', 1, 'alert.gif');
	      $('#answer').focus();
	      return;
	    }
      $('#updateform').attr('action', 'helpinfo/update');
      $("#updateform").submit();
  }
  
  function goback() {
	  parent.closeDialog();
  }
  
  
  $(function () {
      var returninfo = '${helpinfo.returninfo}';
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
