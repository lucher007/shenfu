<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<style type="text/css">
.easyui-panel table tr {
	height: 25px;
}
</style>
</head>
<body>
<div id="body">
	<form method="post" id="addForm" name="addForm">
		<input type="hidden" name="id" id="id" value="${userorder.userorder.id}"/>
		<input type="hidden" name="operatetime" id="operatetime" value="${userorder.userorder.operatetime}"/>
		<input type="hidden" name="filingflag" id="filingflag" value="${userorder.userorder.filingflag}"/>
    	<div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
			<div class="easyui-panel" title="订单信息" style="width:100%;">
				<table style="width:100%">
					<tr>
						<td align="right">订单号：</td>
						<td >
							<input type="text" class="inp w200" name="ordercode" id="ordercode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.ordercode }">
						</td>
						<td align="right">状态：</td>
						<td >
						   <input type="text" class="inp w200" name="statusname" id="statusname" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.statusname}">
						</td>
						<td align="right">任务单号：</td>
						<td >
							<input type="text" class="inp w200" name="taskcode" id="taskcode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.taskcode }">
						</td>
					</tr>
					<tr>
						<td align="right">客户编号：</td>
						<td>
							<input type="text" class="inp w200" name="usercode" id="usercode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.usercode}"><span class="red">*</span>
						</td>
						<td align="right">客户姓名：</td>
						<td>
							<input type="text" class="inp w200" name="username" id="username" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.username}"><span class="red">*</span>
						</td>
						<td align="right">联系电话：</td>
						<td>
							<input type="text" class="inp" name="phone" id="phone" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.phone}"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">客户来源：</td>
						<td>
							<input type="text" class="inp" name="source" id="source" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.sourcename}"> <span class="red">*</span>
							<span class="red">*</span>
						</td>
						<td align="right">上门类型：</td>
						<td>
							<input type="text" class="inp" name="visittype" id="visittype" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.visittypename}"> <span class="red">*</span>
							<span class="red">*</span>
						</td>
						<td align="right">销售员姓名：</td>
						<td>
							<input type="text" class="inp" name="salername" id="salername" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.saler.employeename}"> <span class="red">*</span>
						</td>
					</tr>
					<!-- 
					<tr>
						<td align="right">上门人员编号：</td>
						<td>
							<input type="text" class="inp" name="visitorcode" id="visitorcode" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.visitor.employeecode}"> <span class="red">*</span>
						</td>
						<td align="right">上门人员姓名：</td>
						<td>
							<input type="text" class="inp" name="visitorname" id="visitorname" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.visitor.employeename}"> <span class="red">*</span>
						</td>
						<td align="right">上门人员电话：</td>
						<td>
							<input type="text" class="inp" name="visitorphone" id="visitorphone" readonly="readonly" style="background:#eeeeee;" value="${userorder.userorder.visitor.phone}"> <span class="red">*</span>
						</td>
					</tr>
					 -->
					<tr>
						<td align="right">客户地址：</td>
						<td colspan="3"><input type="text" class="inp" style="width: 400px;" name="address" id="address" value="${userorder.userorder.address}"> <span class="red">*</span></td>
					</tr>
				</table>
			</div>
			
			<div class="easyui-panel" title="产品信息" style="width:100%;">
				<table style="width:100%">
			  		<tr>
						<td align="right">产品型号：</td>
						<td >
							<input type="text" class="inp" id="modelcode" name="modelcode" value="${userorder.userorder.modelcode}" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">型号名称：</td>
						<td >
							<input type="text" class="inp" id="modelname" name="modelname"  value="${userorder.userorder.modelname}" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
					    <td align="right">产品颜色：</td>
						<td >
							<input id="productcolor" class="inp"  name="productcolor" value="${userorder.userorder.productcolorname}" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						 <td align="right">是否带机械锁心：</td>
						 <td >
						 	<input id="lockcoreflag" class="inp"  name="lockcoreflag" value="${userorder.userorder.lockcoreflagname}" readonly="readonly" style="background:#eeeeee;"><span class="red">机械锁心，需要额外收费500元</span></td>
						 </td>
					</tr>
					 <tr>
						<td align="right">产品价格：</td>
						<td >
							<input type="text" class="inp" id="productfee" name="productfee"  value="${userorder.userorder.productfee}" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
				   </tr>
				   <tr>
						<td align="right">订单总价：</td>
						<td >
							<input type="text" class="inp" id="totalmoney" name="totalmoney"  value="${userorder.userorder.totalmoney}" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
				   </tr>
				   <tr>
						<td align="right">优惠金额：</td>
						<td >
							<input type="text" class="inp" id="totalmoney" name="totalmoney"  value="${userorder.userorder.discountfee}" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
				   </tr>
				   <tr>
						<td align="right">优惠码：</td>
						<td >
							<input type="text" class="inp" id="giftcardno" name="giftcardno" value="${userorder.giftcardno}" style="width: 200px;" maxlength="22"> <span class="red">*</span>
						</td>
					</tr>
			  	</table>
			</div>
		</div>
		<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveUseGiftcard();" class="easyui-linkbutton" iconCls="icon-save">确认使用优惠码</a>
				    </cite><span class="red">${userorder.returninfo}</span>
			</div>
	</form>		
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  $(function () {
	     //initProductmodelrefDatagrid();
	     var returninfo = '${userorder.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 2, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
	  parent.closeTabSelected();
	//parent.closeDialog();
  }
	
  function checkVal() {
	  if ($("#giftcardno") != undefined && ($("#giftcardno").val() == "" || $("#giftcardno").val() == null )) {
			$.dialog.tips("请输入优惠码", 1, "alert.gif", function() {
				$("#giftcardno").focus();
			});
			return false;
		}
		return true;
	}
	
  function saveUseGiftcard() {
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
		    	$("#addForm").attr("action", "userorder/saveUseGiftcard?rid="+Math.random());
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
</script>
</body>
</html>
