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
  <form action="" method="post" id="updateform" name="updateform">
    <input type="hidden" name="id" id="id" value="${product.product.id}"/>
    <input type="hidden" name="depotamount" id="depotamount" value="${product.product.depotamount}"/>
   		  <div class="easyui-panel" title="产品信息" style="width:100%;" data-options="footer:'#ft'">
	       <table width="100%" >
	          <tr>
	            <td align="right">产品编码：</td>
	            <td>
					<input type="text" class="inp" name="productcode" id="productcode" value="${product.product.productcode }">
				</td>
	          </tr>
	          <tr>
	            <td align="right">产品名称：</td>
	            <td>
					<input type="text" class="inp" name="productname" id="productname" value="${product.product.productname }">
				</td>
	          </tr>
	          <tr>
				 <td align="right">产品类别：</td>
				 <td>
					<input id="typecode" name="typecode"> <span class="red">*</span>
				 </td>
			  </tr>
			  <tr>
				<td align="right">产品价格：</td>
				<td>
					<input type="text" class="inp easyui-numberbox" name="price" id="price" value="${product.product.price }" maxlength="8">
				</td> 
			  </tr>
	          <tr>
				<td align="right">产品计量单位：</td>
	          	<td>
					<input type="text" class="inp" name="productunit" id="productunit" value="${product.product.productunit }">
				</td>
			  </tr>
			  <tr>
				<td align="right">产品来源：</td>
				<td>
					<select id="productsource" name="productsource" class="select">
						<option value="0" <c:if test="${product.product.productsource == '0'}">selected</c:if>>自产</option>
						<option value="1" <c:if test="${product.product.productsource == '1'}">selected</c:if>>外购</option>
					</select>
				</td>
			 </tr>
	        </table>
      </div>
      <div id="ft" style="padding:5px;">
		<cite> 
			<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
			<a href="javascript:updateProduct();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
		</cite>
	    <span class="red">${product.returninfo}</span>
	  </div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  function updateProduct() {
	  
	  if ($("#productcode") != undefined && ($("#productcode").val() == "" || $("#productcode").val() == null )) {
			$.dialog.tips("请输入产品编码", 1, "alert.gif", function() {
				$("#productcode").focus();
			});
			return false;
		}
		
		if ($("#productname") != undefined && ($("#productname").val() == "" || $("#productname").val() == null )) {
			$.dialog.tips("请输入产品名称", 1, "alert.gif", function() {
				$("#productname").focus();
			});
			return false;
		}
	  
		$.dialog({
		    title: '提交',
		    lock: true,
		    background: '#000', /* 背景色 */
		    opacity: 0.5,       /* 透明度 */
		    content: '是否确定执行？',
		    icon: 'alert.gif',
		    ok: function () {
		    	$("#updateform").attr("action", "product/update?rid="+Math.random());
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
      var returninfo = '${product.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 2, 'alert.gif');
      }
  });
  
   //加载材料列表
   $('#typecode').combobox({    
	    url:'producttype/getProducttypeComboBoxJson',    
	    valueField:'typecode',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${product.product.typecode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
            
      } 
	}); 
</script>
</body>
</html>
