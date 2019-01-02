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
	<title>购买订单</title>
	<link type="text/css" rel="stylesheet"  href="shopping/AmazeUI-2.4.2/assets/css/admin.css" >
	<link type="text/css" rel="stylesheet"  href="shopping/AmazeUI-2.4.2/assets/css/amazeui.css" >
	<link type="text/css" rel="stylesheet"  href="shopping/css/personal.css" >
	<link type="text/css" rel="stylesheet"  href="shopping/css/infstyle.css" >
	<link href="shopping/basic/css/demo.css" rel="stylesheet" type="text/css" />
	<link href="shopping/css/cartstyle.css" rel="stylesheet" type="text/css" />
	<link href="shopping/css/jsstyle.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="shopping/AmazeUI-2.4.2/assets/js/jquery.min.js" ></script>
	<script type="text/javascript" src="shopping/AmazeUI-2.4.2/assets/js/amazeui.js" ></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
</head>

<body style="padding: 0px;">
	<div class="info-main">
		<form class="am-form am-form-horizontal data-am-validator" id="addForm" method="post" enctype="multipart/form-data" >
		    <input type="hidden" id="visittype" name="visittype" value="1">
		    <input type="hidden" id="source" name="source" value="0">
		    <input type="hidden" id="salercode" name="salercode" value="${Saler.employeecode}">
			
			<div class="am-form-group">
				<label for="username" class="am-form-label">姓名</label>
				<div class="am-form-content">
					<input type="text" id="username" name="username" maxlength="50" value="${user.username}" placeholder="输入姓名" class="am-form-field" required/>
				</div>
			</div>
			<div class="am-form-group">
				<label for="phone" class="am-form-label">电话号码</label> 
				<div class="am-form-content">
					<input type="tel" id="phone" name="phone" maxlength="11" value="${user.phone}" placeholder="输入电话号码" class="am-form-field" required/>
				</div>
			</div>
			<div class="am-form-group">
				<label for="address" class="am-form-label">安装地址</label>
				<div class="am-form-content">
					<input type="text"  id="address" name="address" value="${user.address}" placeholder="输入安装地址" class="am-form-field" required/>
				</div>
			</div>
			<div class="am-form-group">
				<label for="username" class="am-form-label">支付类型</label>
				<div class="am-form-content">
				    <li style="color: red; font-size: 18px;">
				    	货到付款
				    </li>
				</div>
			</div>
			<div class="am-form-group">
				<label for="modelcode" class="am-form-label">产品型号</label>
				<div class="am-form-content">
					<select id="modelcode" name="modelcode" data-am-selected="{btnWidth: '100%', btnSize: 'sm',}" required>
					  	<option value="">请选择</option>
					    <c:forEach items="${productmodelList}" var="productmodel">  
			               <option value="${productmodel.modelcode}" <c:if test="${user.modelcode eq productmodel.modelcode}">selected</c:if>>${productmodel.modelname}、原价:${productmodel.price}、优惠价:${productmodel.sellprice}</option>
				        </c:forEach>  
					</select>
				</div>
			</div>
			
			<div class="am-form-group">
				<label for="productcolor" class="am-form-label">产品颜色</label>
				<div class="am-form-content">
					<select id="productcolor" name="productcolor" data-am-selected="{btnWidth: '50%', btnSize: 'sm', }" required>
					  <option value="">请选择</option>
					  <option value="0101" <c:if test="${user.productcolor eq '0101'}">selected</c:if>>摩卡棕</option>
					  <option value="0102" <c:if test="${user.productcolor eq '0102'}">selected</c:if>>石英灰</option>
					  <option value="0103" <c:if test="${user.productcolor eq '0103'}">selected</c:if>>香槟金</option>
					</select>
				</div>
			</div>
			
			<div class="am-form-group">
				<label for="user-name2" class="am-form-label">机械锁心</label>
				<div class="am-form-content">
						<select id="lockcoreflag" name="lockcoreflag"  data-am-selected="{btnWidth: '30%', btnSize: 'sm',}" required>
						  <option value="0" <c:if test="${user.lockcoreflag eq '0'}">selected</c:if>>不需要</option>
						  <option value="1" <c:if test="${user.lockcoreflag eq '1'}">selected</c:if>>需要</option>
						</select>
					<span style="color: red; text-align: center" >配机械锁心，额外收费500元</span>
				</div>
			</div>
			
			<div class="am-form-group">
				<label for="user-name2" class="am-form-label">门锁图片</label>
				<div class="am-form-content">
					<table>
						<tr>
							<td>
								<div class="am-u-sm-7 am-u-md-4 text-two">
									<div class="outer-con ">
										<img class="am-radius" alt="140*140" src="images/shopping/door_1.jpg" width="140px;" height="180px;" />
										<label for="doc-ipt-file-2">门侧板宽度和锁孔宽度</label>
										<input type="file" id="doc-ipt-file-1" name="uploadfile" >
									</div>
									<div style="height: 10px;"></div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="am-u-sm-7 am-u-md-4 text-two">
									<div class="outer-con ">
										<img class="am-radius" alt="140*140" src="images/shopping/door_2.jpg" width="140px;" height="180px;" />
										<div class="title ">
											<label for="doc-ipt-file-2">门侧板长度</label>
										</div>
										<div class="sub-title ">
											<input type="file" id="doc-ipt-file-2" name="uploadfile">
										</div>
										<div style="height: 10px;"></div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="am-u-sm-3 am-u-md-3 text-two">
									<img class="am-radius" alt="140*140" src="images/shopping/door_3.jpg" width="140" height="180" />
									<div class="outer-con ">
										<div class="title ">
											<label for="doc-ipt-file-3">门衬板宽度</label>
										</div>
										<div class="sub-title ">
											<input type="file" id="doc-ipt-file-3" name="uploadfile">
										</div>
										<div style="height: 10px;"></div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="am-u-sm-3 am-u-md-2 text-two">
									<img class="am-radius" alt="140*140" src="images/shopping/door_4.jpg" width="140" height="180" />
									<div class="outer-con ">
										<div class="title ">
											<label for="doc-ipt-file-4">门衬板长度</label>
										</div>
										<div class="sub-title ">
											<input type="file" id="doc-ipt-file-4" name="uploadfile">
										</div>
									</div>
									<div style="height: 10px;"></div>
								</div>
							</td>
						</tr>
				</table>
			</div>
			
			<div style="height: 15px;"></div>
			<div class="info-btn">
				<button type="button" class="am-btn am-alert am-btn-block" style="height: 50px;" onclick="javascript:saveUserBuyOrder();">确认安装</button>
			</div>
			<div style="height: 15px;"></div>
			<div class="info-btn">
				<button type="button" class="am-btn  am-alert-secondary am-btn-block" style="height: 40px;" onclick="javascript:rebackMain();">返回到主界面</button>
			</div>
			
		   <c:if test="${user.returninfo ne null && user.returninfo ne ''}">
				<div id="returninfo"  class="am-alert am-alert-success" data-am-alert >
				  <p>${user.returninfo}</p>
				</div>
			</c:if>
			
			<div style="height: 15px;">
			</div>
			
			<!-- 保存确认框 -->
			<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
			  <div class="am-modal-dialog">
			    <div class="am-modal-hd">订单购买</div>
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
			$.dialog.tips("请输入姓名", 1, 'alert.gif');
			return false;
		}
		
		if ($("#phone") != undefined && ($("#phone").val() == "" || $("#phone").val() == null )) {
			$("#phone").focus();
			$.dialog.tips("请输入电话号码", 1, 'alert.gif');
			return false;
		}
		
		if ($("#address") != undefined && ($("#address").val() == "" || $("#address").val() == null )) {
			$("#address").focus();
			$.dialog.tips("请输入安装地址", 1, 'alert.gif');
			return false;
		}
		
		if ($("#modelcode") != undefined && ($("#modelcode").val() == "" || $("#modelcode").val() == null )) {
			$.dialog.tips("请选择产品型号", 1, 'alert.gif');
			return false;
		}
		
		if ($("#productcolor") != undefined && ($("#productcolor").val() == "" || $("#productcolor").val() == null )) {
			$.dialog.tips("请选择产品颜色", 1, 'alert.gif');
			return false;
		}
		
		if ($("#doc-ipt-file-1") != undefined && ($("#doc-ipt-file-1").val() == "" || $("#doc-ipt-file-1").val() == null )) {
			$.dialog.tips("请上传门侧板宽度和锁孔宽度", 1, 'alert.gif');
			return false;
		}
		
		if ($("#doc-ipt-file-2") != undefined && ($("#doc-ipt-file-2").val() == "" || $("#doc-ipt-file-2").val() == null )) {
			$.dialog.tips("请上传门侧板长度", 1, 'alert.gif');
			return false;
		}
		
		if ($("#doc-ipt-file-3") != undefined && ($("#doc-ipt-file-3").val() == "" || $("#doc-ipt-file-3").val() == null )) {
			$.dialog.tips("请上传门衬板宽度", 1, 'alert.gif');
			return false;
		}
		
		if ($("#doc-ipt-file-4") != undefined && ($("#doc-ipt-file-4").val() == "" || $("#doc-ipt-file-4").val() == null )) {
			$.dialog.tips("请上传门衬板长度", 1, 'alert.gif');
			return false;
		}
		
		return true;
	}
	
	function saveUserBuyOrder() {
		if (!checkVal()) {
			return;
		}
		$('#my-confirm').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	$("#addForm").attr("action", "sale/saveUserAddOrderForVisittype1?rid="+Math.random());
				$("#addForm").submit();
	        },
	        onCancel: function() {
	         	return false
	        }
	    });
	}
	
	$(function () {
	       var returninfo = '${user.returninfo}';
	       if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	       }
	 });
	
	function rebackMain() {
		$("#addForm").attr("action", "sale/saleExtendInit?rid="+Math.random()+"&salercode="+$("#salercode").val());
		$("#addForm").submit();
	}
	
	</script>
</body>
</html>
