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
    <input type="hidden" name="id" id="id" value="${material.material.id}"/>
    <div class="easyui-panel" title="材料信息" style="width:100%;" data-options="footer:'#ft'">
       <table width="100%">
          <tr>
            <td align="right">材料编号：</td>
            <td>
				<input type="text" class="inp" name="materialcode" id="materialcode" value="${material.material.materialcode }">
			</td>
          </tr>
          <tr>
            <td align="right">材料名称：</td>
            <td>
				<input type="text" class="inp" name="materialname" id="materialname" value="${material.material.materialname }">
			</td>
          </tr>
          <tr>
			<td align="right">材料计量单位：</td>
          	<td>
				<input type="text" class="inp" name="materialunit" id="materialunit" value="${material.material.materialunit }">
			</td>
		  </tr>
		  <tr>
			<td align="right">材料来源：</td>
			<td>
				<select id="materialsource" name="materialsource" class="select">
					<option value="0" <c:if test="${material.material.materialsource == '0'}">selected</c:if>>自产</option>
					<option value="1" <c:if test="${material.material.materialsource == '1'}">selected</c:if>>外购</option>
				</select>
			</td>
		  </tr>
		  <tr>
				<td align="right">报警量：</td>
				<td><input type="text" class="inp easyui-numberbox" name="depotamount" id="depotamount" value="${material.material.depotamount }" ></td>
		  </tr>
        </table>
      </div>
      
      <div id="ft" style="padding:5px;">
				<cite> 
					<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
					<a href="javascript:updateMaterial();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
			    </cite><span class="red">${material.returninfo}</span>
	  </div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  function updateMaterial() {
	  if ($("#materialcode") != undefined && ($("#materialcode").val() == "" || $("#materialcode").val() == null )) {
			$.dialog.tips("请输入材料编号", 1, "alert.gif", function() {
				$("#materialcode").focus();
			});
			return false;
		}
		if ($("#materialname") != undefined && ($("#materialname").val() == "" || $("#materialname").val() == null )) {
			$.dialog.tips("请输入材料名称", 1, "alert.gif", function() {
				$("#materialname").focus();
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
		    	$("#updateform").attr("action", "material/update?rid="+Math.random());
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
      var returninfo = '${material.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
  });
  
/////////////////////对必要的输入框进行数字合法验证,只能输入小数点后三位//////////////////////////
	function onkeypressCheck(obj){
		if (!obj.value.match(/^[1-9]?\d*?\.?\d*?$/)) {
			obj.value = obj.t_value;
		} else {
			obj.t_value = obj.value;
		}
		if (obj.value.match(/^(?:[1-9]?\d+(?:\.\d+)?)?$/)) {
			obj.o_value = obj.value;
		}
		if(obj.value.match(/^\d+\.\d{3}?$/)){
		   obj.value = obj.value.substr(0,obj.value.length-1);
		}
	}
	
	function onkeyupCheck(obj){
		if (!obj.value.match(/^[1-9]?\d*?\.?\d*?$/)) {
				obj.value = obj.t_value;
			} else {
				obj.t_value = obj.value;
			}
			if (obj.value.match(/^(?:[1-9]?\d+(?:\.\d+)?)?$/)) {
				obj.o_value = obj.value;
			}
			if (obj.value.match(/^\.$/)) {
				obj.value = "";
			}
			if (obj.value.match(/^\-$/)) {
				obj.value = "";
			}
			if (obj.value.match(/^00+/)) {
				obj.value = "";
			}
			if (obj.value.match(/^0\.00/)) {
				obj.value = "";
			}
			if (obj.value.match(/^0[1-9]/)) {
				obj.value = "";
			}
			if(obj.value.match(/^\d+\.\d{3}?$/)){
				obj.value = obj.value.substr(0,obj.value.length-1);
			} 
			if(obj.value == 'undefined'){
				obj.value='';
			}
			
	}
	
	function onkeyblurCheck(obj){
		if(obj.value==0){
			//obj.value='';
		}
		if(obj.value==''){
			obj.value = 0;
		}
		if (!obj.value.match(/^(?:[1-9]?\d+(?:\.\d+)?|\.\d*?)?$/)) {
			obj.value = obj.o_value;
		}else {
			if (obj.value.match(/^\.\d+$/)) {
				obj.value = 0 + obj.value;
			}
			obj.o_value = obj.value;
		}
		if(!obj.value.match(/^\d+(\.\d{3})?$/)){
			obj.value = obj.value.substr(0,obj.value.indexOf(".")+3);
		} 
		
	}
</script>
</body>
</html>
