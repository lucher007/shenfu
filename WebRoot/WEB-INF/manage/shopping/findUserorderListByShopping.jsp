<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
	<base href="<%=basePath%>" />
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">
	<title>个人订购信息</title>
	<link type="text/css" rel="stylesheet"  href="shopping/AmazeUI-2.4.2/assets/css/admin.css" >
	<link type="text/css" rel="stylesheet"  href="shopping/AmazeUI-2.4.2/assets/css/amazeui.css" >
	<link type="text/css" rel="stylesheet"  href="shopping/css/personal.css" >
	<link type="text/css" rel="stylesheet"  href="shopping/css/infstyle.css" >
	<script type="text/javascript" src="shopping/AmazeUI-2.4.2/assets/js/jquery.min.js" ></script>
	<script type="text/javascript" src="shopping/AmazeUI-2.4.2/assets/js/amazeui.js" ></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
</head>

<body>
	<div class="info-main">
		<form class="am-form am-form-horizontal" id="addForm" method="post">
		    <input type="hidden" id="salercode" name="salercode" value="${Saler.employeecode}">
		    <div class="am-form-group">
				<label for="username" class="am-form-label">姓名</label> 
				<div class="am-form-content">
					<input type="tel" id="username" name="username" value="${Usershopping.username}" class="am-form-field" required/>
				</div>
			</div>
		    <div class="am-form-group">
				<label for="user-phone" class="am-form-label">电话号码</label> 
				<div class="am-form-content">
					<input type="tel" id="phone" name="phone" value="${Usershopping.phone}" class="am-form-field" required/>
				</div>
			</div>
		    
		    <!-- 未处理的-还在体验阶段 -->
		    <h3>还未生成订单列表</h3>
		    <table class="am-table am-table-bordered am-table-striped am-table-hover">
		    	<thead >
			        <tr>
			            <th>申请日期</th>
			            <th>处理状态</th>
			        </tr>
			    </thead>
			    <tbody>
	   			 	<c:if test="${not empty userList}">
				    	<c:forEach items="${userList}" var="user">  
				    		<tr>
				    			<td>
				    				${fn:substring(user.addtime, 0, 19)}
				    			</td>
				    			<td>
				    				${user.statusname}
				    			</td>
				    		</tr>
				    	</c:forEach>
			    	</c:if>
			    </tbody>
			</table>
		   
		   <!-- 已经生成订单 -->
		   <h3>已经购买订单列表</h3>
		   <table class="am-table am-table-bordered am-table-striped am-table-hover ">
   				<thead>
			        <tr>
			        	<th>产品型号</th>
			            <th>产品颜色</th>
			            <th>订单状态</th>
			        </tr>
			    </thead>
			    <tbody>
		   			<c:if test="${not empty userorderList}">
				    	<c:forEach items="${userorderList}" var="userorder">  
				    		<tr>
				    			<td>
				    				${userorder.modelname}
				    			</td>
				    			<td>
				    				${userorder.productcolorname}
				    			</td>
				    			<td>
				    				${userorder.statusname}
				    			</td>
				    		</tr>
				    	</c:forEach>
				    </c:if>
			    </tbody>
			</table>
		    
			<div style="height: 15px;"></div>
			<div class="info-btn">
				<button type="button" class="am-btn  am-alert-secondary am-btn-block" style="height: 40px;"  onclick="javascript:rebackMain();">返回到主界面</button>
			</div>
				
			<c:if test="${user.returninfo ne null && user.returninfo ne ''}">
				<div id="returninfo"  class="am-alert am-alert-success" data-am-alert >
				  <p>${user.returninfo}</p>
				</div>
			</c:if>
			
			<!-- 保存确认框 -->
			<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
			  <div class="am-modal-dialog">
			    <div class="am-modal-hd">申请体验</div>
			    <div class="am-modal-bd">
			     	 确定操作？
			    </div>
			    <div class="am-modal-footer">
			      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
			      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
			    </div>
			  </div>
			</div>
			
		</form>
	</div>
	<script type="text/javascript">
	
	function checkVal() {
		
		if ($("#username") != undefined && ($("#username").val() == "" || $("#username").val() == null )) {
			$("#username").focus();
			return false;
		}
		
		if ($("#phone") != undefined && ($("#phone").val() == "" || $("#phone").val() == null )) {
			$("#phone").focus();
			return false;
		}
		
		if ($("#address") != undefined && ($("#address").val() == "" || $("#address").val() == null )) {
			$("#address").focus();
			return false;
		}
		return true;
	}
	
	function rebackMain() {
		$("#addForm").attr("action", "sale/saleExtendInit?rid="+Math.random()+"&salercode="+$("#salercode").val());
		$("#addForm").submit();
	}
	
	$(function () {
	       var returninfo = '${user.returninfo}';
	       if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	       }
	 });
	
	</script>
</body>
</html>
