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
     .fb-con table tr {
      height: 30px;
    }
  </style>
</head>

<body>
<div id="body">
  <form action="" method="post" id="updateform" name="updateform">
    <input type="hidden" name="id" id="id" value="${employee.employee.id}"/>
    <input type="hidden" name="employeecode" id="employeecode" value="${employee.employee.employeecode}"/>
    	<div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
			<div class="easyui-panel" title="员工信息" style="width:100%;">	
	       		<table>
			       <tr>
						<td align="right">员工部门：</td>
						<td>
							<select id="department" name="department" class="select">
								<option value="0" <c:if test="${employee.employee.department == '0'}">selected</c:if>>管理部</option>
								<option value="1" <c:if test="${employee.employee.department == '1'}">selected</c:if>>系统部</option>
								<option value="2" <c:if test="${employee.employee.department == '2'}">selected</c:if>>销售部</option>
								<option value="3" <c:if test="${employee.employee.department == '3'}">selected</c:if>>装维部</option>
								<option value="4" <c:if test="${employee.employee.department == '4'}">selected</c:if>>生产部</option>
								<option value="5" <c:if test="${employee.employee.department == '5'}">selected</c:if>>400客服</option>
								<option value="6" <c:if test="${employee.employee.department == '6'}">selected</c:if>>财务部</option>
							</select>
						</td>
						<td align="right">员工姓名：</td>
			            <td>
							<input type="text" class="inp" name="employeename" id="employeename" value="${employee.employee.employeename }">
						</td>
				  </tr>
		          <tr>
						<td height="30" width="15%" align="right">电话号码：</td>
						<td width="25%"><input type="text" class="inp" name="phone" id="phone" value="${employee.employee.phone }" maxlength="12" onkeyup="checkNum(this)" onkeypress="checkNum(this)" onblur="checkNum(this)" onafterpaste="this.value=this.value.replace(/\D/g,'')"> <span class="red">*固话请添加区号</span></td>
					  	<td align="right">销售员类型：</td>
						<td>
							<select id="employeetype" name="employeetype" class="select">
								<option value="1" <c:if test="${employee.employee.employeetype == '1'}">selected</c:if>>全职</option>
								<option value="0" <c:if test="${employee.employee.employeetype == '0'}">selected</c:if>>兼职</option>
							</select>
						</td>
				  </tr>
		          <tr>
					<td align="right">员工性别：</td>
					<td>
						<select id="employeesex" name="employeesex" class="select">
							<option value="1" <c:if test="${employee.employee.employeesex == '1'}">selected</c:if>>男</option>
							<option value="0" <c:if test="${employee.employee.employeesex == '0'}">selected</c:if>>女</option>
						</select>
					</td>
					<td height="30" width="15%" align="right">员工生日：</td>
					<td width="25%">
						<input type="text" id="birthday" name="birthday"   value="${employee.employee.birthday }"
		                			onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150">
					</td>
				</tr>
				<tr>
					<td height="30" width="15%" align="right">身份证号码：</td>
					<td width="25%"><input type="text" class="inp" name="identification" id="identification" value="${employee.employee.identification }" maxlength="18"></td>
					<td height="30" width="15%" align="right">电子邮件：</td>
					<td width="25%"><input type="text" class="inp" name="email" id="email" style="width:250px;" value="${employee.employee.email }"></td>
				</tr>
				<tr>
					<td height="30" width="15%" align="right">银行名称：</td>
					<td width="25%"><input type="text" class="inp" style="width:200px;" name="bankcardname" id="bankcardname" value="${employee.employee.bankcardname }"></td>
					<td height="30" width="15%" align="right">银行卡号：</td>
					<td width="25%"><input type="text" class="inp" name="bankcardno" id="bankcardno" style="width:200px;" value="${employee.employee.bankcardno }"></td>
				</tr>
				<tr>
					<td height="30" width="15%" align="right">居住地址：</td>
					<td width="25%"><input type="text" class="inp" name="address" id="address" style="width:300px;" value="${employee.employee.address }"></td>
					<td height="30" width="15%" align="right">充值VIP等级：</td>
					<td>
					<input name="rechargevipcode" id="rechargevipcode"> <span class="red">*</span>
				</td>
				</tr>
				<tr>
					<td align="right">上级销售员工号：</td>
					<td>
						<input type="text" class="inp w200" name="parentemployeecode" id="parentemployeecode" readonly="readonly" style="background:#eeeeee;" value="${employee.employee.parentemployeecode }">
						<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
					</td>
					<td align="right">上级销售员姓名：</td>
					<td>
						<input type="text" class="inp w200" name="parentemployeename" id="parentemployeename" readonly="readonly" style="background:#eeeeee;" value="${employee.employee.parentemployee.employeename }">
						<a href="javascript:cleanEmployee();" class="easyui-linkbutton" iconCls="icon-undo" style="width:80px">清除</a>
					</td>
				</tr>
				<tr>
					<td align="right">销售管家编号：</td>
					<td>
						<input type="text" class="inp w200" name="managercode" id="managercode" readonly="readonly" style="background:#eeeeee;" value="${employee.employee.managercode}">
						<a href="javascript:chooseManager();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
					</td>
					<td align="right">销售管家姓名：</td>
					<td>
						<input type="text" class="inp w200" name="managername" id="managername" readonly="readonly" style="background:#eeeeee;" value="${employee.employee.manager.employeename }">
						<a href="javascript:cleanManager();" class="easyui-linkbutton" iconCls="icon-undo" style="width:80px">清除</a>
					</td>
				</tr>
				<tr>
					<td height="30" width="15%" align="right">状态：</td>
					<td width="25%">
						<select id="status" name="status" class="select">
							<option value="1" <c:if test="${employee.employee.status == '1'}">selected</c:if>>在职</option>
							<option value="0" <c:if test="${employee.employee.status == '0'}">selected</c:if>>离职</option>
						</select>
					</td>
					<td height="30" width="15%" align="right">推广码：</td>
					<td width="25%">
						<input type="text" class="inp" readonly="readonly" style="background:#eeeeee;" name="extendcode" id="extendcode" value="${employee.employee.extendcode }">
					</td>
				</tr>
				<tr style="height: 10px">
				 <td>
				 </td>
				</tr>
	        </table>
	      </div>
          <div id="ft" style="padding:5px;">
				<cite> 
					<a href="javascript:goBack();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
      				<a href="javascript:updateEmployee();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
			    </cite><span class="red">${employee.returninfo}</span>
		 </div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript">

  function updateEmployee() {
	  
	  if ($("#employeename") != undefined && ($("#employeename").val() == "" || $("#employeename").val() == null )) {
			$.dialog.tips("请输入员工姓名", 1, "alert.gif", function() {
			$("#employeename").focus();
		});
		return false;
	  }
	
	  if ($("#phone") != undefined && ($("#phone").val() == "" || $("#phone").val() == null )) {
			$.dialog.tips("请输入电话号码", 1, "alert.gif", function() {
			$("#phone").focus();
		});
		return false;
	  }
	  
	  var rechargevipcode = $('#rechargevipcode').combobox('getValue');
	  if (rechargevipcode != undefined && (rechargevipcode == "" || rechargevipcode == null)) {
			$.dialog.tips("请选择充值VIP等级", 1, "alert.gif", function() {
				$("#rechargevipcode").focus();
			});
			return false;
	  }
	  
      $('#updateform').attr('action', 'employee/update');
      $("#updateform").submit();
  }
  
  function goBack() {
     parent.closeDialog();
  }
  
  $(function () {
      var returninfo = '${employee.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
  });
  
  var employeeFlag;
  var employeeDialog;
	function chooseEmployee() {
		employeeFlag = '0';
		employeeDialog = $.dialog({
			title : '上级销售员查询',
			lock : true,
			width : 900,
			height : 480,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:employee/findSalerListDialog?rid='+Math.random()
		});
	}

	function chooseManager() {
		employeeFlag = '1';
		employeeDialog = $.dialog({
			title : '销售管家查询',
			lock : true,
			width : 900,
			height : 480,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:employee/findEmployeeListDialog?rid='+Math.random()
		});
	}
	
	function closeEmployeeDialog(jsonStr) {
		if(employeeFlag == '0'){
			employeeDialog.close();
			//将json字符串转换成json对象
		    var jsonObject =  eval("(" + jsonStr +")");
		    
		    $("#parentemployeecode").val(jsonObject.employeecode);
			$("#parentemployeename").val(jsonObject.employeename);
		}else{
			employeeDialog.close();
			//将json字符串转换成json对象
		    var jsonObject =  eval("(" + jsonStr +")");
		    
		    $("#managercode").val(jsonObject.employeecode);
			$("#managername").val(jsonObject.employeename);
		}
	}
	
	function cleanEmployee() {
		  $('#parentemployeecode').val("");
		  $('#parentemployeename').val("");
	  }
	
	function cleanManager() {
		  $('#managercode').val("");
		  $('#managername').val("");
	 }
  
//加载充值VIP
	$('#rechargevipcode').combobox({    
	    url:'rechargevip/getRechargevipComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${employee.employee.rechargevipcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
            
      } 
	}); 
</script>
</body>
</html>
