<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
     <input type="hidden" name="id" id="id" value="${usertask.usertask.id}"/>
     <input type="hidden" name="operatorcode" id="operatorcode" value="${usertask.usertask.operatorcode}"/>
     <input type="hidden" name="visitstate" id="visitstate" value="${usertask.usertask.visitstate}"/>
     <input type="hidden" name="intention" id="intention" value="${usertask.usertask.intention}"/>
     <input type="hidden" name="status" id="status" value="${usertask.usertask.status}">
     <input type="hidden" name="salercode" id="salercode" value="${usertask.usertask.salercode}">
   	 <div class="easyui-panel" title="任务单信息" style="width:100%;" data-options="footer:'#ft'">
   	 		<table style="width:100%;border:0; cellpadding:0; cellspacing:0">
				<tr>
					<td align="right" width="10%">任务单号：</td>
					<td width="25%">
						<input type="text" class="inp w200" name="taskcode" id="taskcode" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.taskcode}">
					</td>
					<td align="right" width="10%">状态：</td>
					<td width="25%">
						<input type="hidden"name="status" id="status" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.status}">
						<input type="text" class="inp w200" name="statusname" id="statusname" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.statusname}">
					</td>
				</tr>
				<tr>
					<td align="right" width="10%">客户编号：</td>
					<td width="25%">
						<input type="text" class="inp w200" name="usercode" id="usercode" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.usercode}">
					</td>
					<td align="right" width="10%">客户姓名：</td>
					<td width="25%">
						<input type="text" class="inp w200" name="username" id="username" value="${usertask.usertask.username}"><span class="red">*</span>
					</td>
				</tr>
				<tr>
					<td align="right">联系电话：</td>
					<td><input type="text" class="inp" name="phone" id="phone" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.phone}"> <span class="red">*</span></td>
					<td align="right">客户来源：</td>
					<td>
						<input type="hidden"name="source" id="source" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.source}">
						<input type="text" class="inp w200" name="sourcename" id="sourcename" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.sourcename}">
					</td>
				</tr>
				<tr>
					<td align="right">客户地址：</td>
					<td><input type="text" class="inp" style="width: 400px;" name="address" id="address" value="${usertask.usertask.address}"> <span class="red">*</span></td>
					<td align="right">讲解类型：</td>
					<td>
						<input type="hidden" name="visittype" id="visittype" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.visittype}">
						<input type="text" class="inp" name="visittypename" id="visittypename" readonly="readonly" style="background:#eeeeee; width: 240px;" value="${usertask.usertask.visittypename}">
					</td>
				</tr>
				<tr>
					<td align="right">销售员编号：</td>
					<td><input type="text" class="inp" readonly="readonly" style="background:#eeeeee;" name="salercode" id="salercode" value="${usertask.usertask.salercode}"> <span class="red">*</span></td>
					<td align="right">销售员姓名：</td>
					<td>
						<input type="text" class="inp" name="salername" id="salername" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.saler.employeename}">
					</td>
				</tr>
				<tr>
					<td align="right">上门单类型：</td>
					<td>
						<input type="hidden" name="tasktype" id="tasktype" value="${usertask.usertask.tasktype}"> 
						<input type="text" class="inp" readonly="readonly" style="background:#eeeeee;" name="tasktypename" id="tasktypename" value="${usertask.usertask.tasktypename}"> <span class="red">*</span>
					</td>
				</tr>
			</table>
			
			<div class="easyui-panel" title="上门人员信息" style="width:100%;">
				<table style="width:100%;border:0; cellpadding:0; cellspacing:0">
					<tr class="talkerDiv">
						<td align="right" width="10%">讲解人员编号：</td>
						<td width="25%">
							<input type="text" class="inp w200" name="talkercode" id="talkercode" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.talkercode }">
							<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						</td>
					</tr>
					<tr class="talkerDiv">
						<td align="right" width="10%">讲解人员姓名：</td>
						<td width="25%">
							<input type="text" class="inp w200" name="talkername" id="talkername" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.talker.employeename }"><span class="red">*</span>
						</td>
					</tr>
					<tr class="talkerDiv">
						<td align="right">联系电话：</td>
						<td><input type="text" class="inp" name="talkerphone" id="talkerphone" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.talker.phone }"> <span class="red">*</span></td>
					</tr>
					<tr class="visitorDiv">
						<td align="right" width="10%">上门人员编号：</td>
						<td width="25%">
							<input type="text" class="inp w200" name="visitorcode" id="visitorcode" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.visitorcode }">
							<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						</td>
					</tr >
					<tr class="visitorDiv" >
						<td align="right" width="10%">上门人员姓名：</td>
						<td width="25%">
							<input type="text" class="inp w200" name="visitorname" id="visitorname" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.visitor.employeename }"><span class="red">*</span>
						</td>
					</tr>
					<tr class="visitorDiv">
						<td align="right">联系电话：</td>
						<td><input type="text" class="inp" name="visitorphone" id="visitorphone" readonly="readonly" style="background:#eeeeee;" value="${usertask.usertask.visitor.phone }"> <span class="red">*</span></td>
					</tr>
					<tr>
						<td align="right">上门时间：</td>
						<td>
							<input type="text" id="visittime" name="visittime"  value="${fn:substring(usertask.usertask.visittime, 0, 19)}"
                  	                  onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});" class="Wdate inp w150">
						</td>
					</tr>
				</table>
			</div>
			
			<div id="ft" style="padding:5px;">
				<cite> 
					<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
					<a href="javascript:updateUsertask();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
			    </cite><span class="red">${usertask.returninfo}</span>
		    </div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  function updateUsertask() {
        if ($("#usercode") != undefined && ($("#usercode").val() == "" || $("#usercode").val() == null )) {
			$.dialog.tips("请选择客户信息", 1, "alert.gif", function() {
				$("#usercode").focus();
			});
			return false;
		}
        
        if ($("#address") != undefined && ($("#address").val() == "" || $("#address").val() == null )) {
        	$("#address").focus();
			$.dialog.tips("请输入客户安装地址信息", 1, "alert.gif", function() {
				
			});
			return false;
		}
        
        //任务单类型
	    var tasktype = $('#tasktype').val();
        
		if("0" == tasktype){//测量等你
			if ($("#talkercode") != undefined && ($("#talkercode").val() == "" || $("#talkercode").val() == null )) {
				$("#talkercode").focus();
				$.dialog.tips("请输入讲解人员", 1, "alert.gif", function() {
					
				});
				return false;
			}
		}else{
			if ($("#visitorcode") != undefined && ($("#visitorcode").val() == "" || $("#visitorcode").val() == null )) {
				$("#visitorcode").focus();
				$.dialog.tips("请输入测量人员", 1, "alert.gif", function() {
					
				});
				return false;
			}
		}
        
		
      
		$.dialog({
		    title: '提交',
		    lock: true,
		    background: '#000', /* 背景色 */
		    opacity: 0.5,       /* 透明度 */
		    content: '是否确定执行？',
		    icon: 'alert.gif',
		    ok: function () {
		    	$("#updateform").attr("action", "task/update?rid="+Math.random());
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
	  
	   //初始化显示方式
	   showDivFlag();
	   
	  
      var returninfo = '${usertask.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 2, 'alert.gif');
      }
  });
  
  function showDivFlag() {
		var tasktype = $('#tasktype').val();
		if(tasktype == '0'){ //讲解单
			$(".talkerDiv").show();
			$(".visitorDiv").hide();
		}else{
			$(".talkerDiv").hide();
			$(".visitorDiv").show();
		}
	}
	
  
  var userDialog;
	function chooseUser() {
		userDialog = $.dialog({
			title : '客户查询',
			lock : true,
			width : 900,
			height : 600,
			top : 0,
			drag : false,
			resize : false,
			max : false,
			min : false,
			content : 'url:user/findUserListDialog?rid='+Math.random()
		});
	}

	function closeUserDialog(jsonStr) {
		userDialog.close();
		//将json字符串转换成json对象
	    var jsonObject =  eval("(" + jsonStr +")");
		$("#usercode").val(jsonObject.usercode);
		$("#username").val(jsonObject.username);
		$("#phone").val(jsonObject.phone);
		//$("#usersex").val(jsonObject.usersex);
		$("#source").val(jsonObject.source);
		$("#address").val(jsonObject.address);
		$("#visittype").val(jsonObject.visittype);
		//销售员上门
		if("1" == jsonObject.visittype){
			$("#visitorcode").val(jsonObject.salercode);
			$("#visitorname").val(jsonObject.salername);
			$("#visitorphone").val(jsonObject.salerphone);
		}else{
			$("#visitorcode").val("");
			$("#visitorname").val("");
			$("#visitorphone").val("");
		}
		
	}
    
    var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '员工查询',
			lock : true,
			width : 900,
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
		
	    //任务单类型
	    var tasktype = $('#tasktype').val();
		if("0" == tasktype){
			$("#talkercode").val(jsonObject.employeecode);
			$("#talkername").val(jsonObject.employeename);
			$("#talkerphone").val(jsonObject.phone);
		}else{
			$("#visitorcode").val(jsonObject.employeecode);
			$("#visitorname").val(jsonObject.employeename);
			$("#visitorphone").val(jsonObject.phone);
		}
	}
</script>
</body>
</html>
