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
    <input type="hidden" name="id" id="id" value="${operator.operator.id}"/>
    <input type="hidden" name="operatortype" id="operatortype" value="${operator.operator.operatortype}"/>
   	<div class="easyui-panel" title="操作员修改" style="width:100%;" style="width:100%;" data-options="footer:'#ft'">
       <table style="width:100%">
          <tr>
            <td align="right">操作员编号：</td>
			<td style="font-weight: bold;">
				<input type="text" class="inp w200" name="employeecode" id="employeecode" readonly="readonly" style="background:#eeeeee;" value="${operator.operator.employeecode }">
				<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
			</td>
          </tr>
          <tr>
            <td align="right">操作员姓名：</td>
			<td style="font-weight: bold;">
				<input type="text" class="inp w200" name="employeename" id="employeename" readonly="readonly" style="background:#eeeeee;" value="${operator.operator.employee.employeename }">
			</td>
          </tr>
          <tr>
            <td align="right">登录名称：</td>
            <td >
            	<input type="text" class="inp" name="loginname" id="loginname" value="${operator.operator.loginname }"> <span class="red">*</span>
            </td>
          </tr>
		  <tr>
          	<td align="right">状态：</td>
          	<td>
			  <select name="status" id="state" class="select">
              	  <option value="1" <c:if test="${operator.operator.status == '1' }">selected</c:if>>有效</option>
                  <option value="0" <c:if test="${operator.operator.status == '0' }">selected</c:if>>无效</option>
              </select>
			</td>
		  </tr>
		  <tr>
			 <td align="right">所属角色：</td>
           	 <td >
            	<select id="roleid" name="roleid" class="select">
                <c:forEach items="${operator.rolemap}" var="roleMap" varStatus="s">
                  <option value="${roleMap.key}" <c:if test="${roleMap.key == operator.roleid}">selected</c:if>>${roleMap.value}</option>
                </c:forEach>
              </select>
            </td>
		  </tr>
		  <tr>
	 		<td align="right"></td>
          	<td>
          		<a class="btn-edit" href="javascript:updatePasswordInit();">密码修改</a>
			</td>
		  </tr>
      </table>
    </div>
    <div id="ft" style="padding:5px;">
			<cite> 
				<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
				<a href="javascript:updateOperator();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
		    </cite><span class="red">${operator.returninfo}</span>
	</div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  function updateOperator() {
  	  if ($("#loginname") != undefined && ($("#loginname").val() == "" || $("#loginname").val() == null )) {
			$.dialog.tips("请输入登录名称", 1, "alert.gif", function() {
				$("#loginname").focus();
			});
			return false;
		}
      $('#updateform').attr('action', 'operator/update');
      $("#updateform").submit();
  }
  
  function goback() {
     parent.closeDialog();
  }
  
  function updatePasswordInit() {
      $("#updateform").attr("action", "operator/updatePasswordInit");
      $("#updateform").submit();
  }
  
  var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '员工查询',
			lock : true,
			width : 1100,
			height : 500,
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
	    
		$("#employeecode").val(jsonObject.employeecode);
		$("#employeename").val(jsonObject.employeename);
		$("#loginname").val(jsonObject.employeename);
	}
  
  $(function () {
      var returninfo = '${operator.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
  });
</script>
</body>
</html>
