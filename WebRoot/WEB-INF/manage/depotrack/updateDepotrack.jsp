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
     .table tr {
      height: 30px;
    }
  </style>
</head>

<body>
<div id="body">
  <form action="" method="post" id="updateform" name="updateform">
    <input type="hidden" name="id" id="id" value="${depotrack.depotrack.id}"/>
    <div class="easyui-panel" title="货柜信息" style="width:100%;">	
			<table >
				<tr>
					<td height="30" width="15%" align="right">货柜编号：</td>
					<td width="25%"><input type="text" class="inp" name="depotrackcode" id="depotrackcode" value="${depotrack.depotrack.depotrackcode }"> <span class="red">*</span></td>
					<td height="30" width="15%" align="right">货柜名称：</td>
					<td width="25%"><input type="text" class="inp " name="depotrackname" id="depotrackname" value="${depotrack.depotrack.depotrackname }" ><span class="red">*</span></td>
				<tr>
				</tr>
					<td width="15%" align="right">总层数：</td>
					<td width="25%"><input type="text" class="inp easyui-numberbox" name="rownums" id="rownums" value="${depotrack.depotrack.rownums }" maxlength="2" >
					<td align="right">总列数：</td>
					<td width="25%"><input type="text" class="inp easyui-numberbox" name="columnnums" id="columnnums" value="${depotrack.depotrack.columnnums }" maxlength="2" >
					<td><input type="button" value="生成货柜图片" onclick="addDepotrack();" class="btn" id="next"></td>
				</tr>
			</table>
		</div>
		<div class="easyui-panel" title="货柜模拟图" style="width:100%;" data-options="footer:'#ft'">	
			<table width="500" id="depotrackTR" border="0" cellpadding="0" cellspacing="0" style="margin:auto">
                   </table>
		</div>
	    <div id="ft" style="padding:5px;">
				<cite> 
					<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
					<a href="javascript:updateDepotrack();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
			    </cite><span class="red">${depotrack.returninfo}</span>
		</div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript">

function checkVal() {
	
	if($("#depotrackcode").val() == "") {
		$.dialog.tips("货柜编号填写！",1,'alert.gif');
		$("#depotrackcode").focus();
		return;	
	};
	if($("#depotrackname").val() == "") {
		$.dialog.tips("货柜名称必须填写！",1,'alert.gif');
		$("#depotrackname").focus();
		return;	
	};
	if($("#rownums").val() == "") {
		$.dialog.tips("总层数必须填写！",1,'alert.gif');
		$("#rownums").focus();
		return;	
	};
	
	if(rownums<1 || rownums>20){
		 $("#rownums").focus();
		 $.dialog.tips("总层数数在1~20之间！", 2, "alert.gif");
		return;
	}
	
	if($("#columnnums").val() == "") {
		$.dialog.tips("总列数必须填写！",1,'alert.gif');
		$("#columnnum").focus();
		return;	
	};
	
	if(columnnums<1 || columnnums>20){
		 $("#columnnums").focus();
		 $.dialog.tips("总列数数在1~20之间！", 2, "alert.gif");
		return;
	}
	
	//var racknum = $("#racknum").val();
	
	if($("#racknum") != undefined && ($("#racknum").val() == "" || $("#racknum").val() == null)) {
		$.dialog.tips("请生成货柜模拟图！",2,'alert.gif');
		return;	
	};
	
	return true;
}

function updateDepotrack() {
	if (!checkVal()) {
		return;
	}
	
	//先执行下生成模拟图
	addDepotrack();
	
	$.dialog({
	    title: '提交',
	    lock: true,
	    background: '#000', /* 背景色 */
	    opacity: 0.5,       /* 透明度 */
	    content: '是否确定执行？',
	    icon: 'alert.gif',
	    ok: function () {
	    	$("#updateform").attr("action", "depotrack/update?rid="+Math.random());
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

function goback(){
	parent.closeDialog();
}

$(function () {
   var returninfo = '${depotrack.returninfo}';
   if (returninfo != '') {
    	$.dialog.tips(returninfo, 2, 'alert.gif');
   }
  
   if('${depotrack.depotrack.id}' != null &&  '${depotrack.depotrack.id}' != ""){
	   addDepotrack();
   }
   
});


 function addDepotrack(){
     
	 var depotrackcode = $('#depotrackcode').val();
     var depotrackname = $('#depotrackname').val();
	 var rownums = $('#rownums').val();
     var columnnums = $('#columnnums').val();
     
     if(depotrackcode == ''){
		 $("#depotrackcode").focus();
		 $.dialog.tips("请输入货柜编号！", 2, "alert.gif");
		return;
	 }
     
     if(depotrackname == ''){
		 $("#depotrackname").focus();
		 $.dialog.tips("请输入货柜名称！", 2, "alert.gif");
		return;
	 }
     
     if(rownums == ''){
		 $("#rownums").focus();
		 $.dialog.tips("请输入总层数！", 2, "alert.gif");
		return;
	 }
     
     if(rownums<1 || rownums>20){
		 $("#rownums").focus();
		 $.dialog.tips("行列数最大不超过20！", 2, "alert.gif");
		return;
	 }
     
     if(columnnums == ''){
		 $("#columnnums").focus();
		 $.dialog.tips("请输入总列数！", 2, "alert.gif");
		return;
	 }
     
     if(columnnums<1 || columnnums>20){
		 $("#columnnums").focus();
		 $.dialog.tips("总列数最大不超过20！", 2, "alert.gif");
		return;
	 }
     
     var racknum = rownums * columnnums;
     
     var iCnt = 1;
     var para1= 1;
     var para2= 1;
     var HTMLStr = '<input type="hidden" name="racknum" id="racknum" value= "' + racknum + '"/>';
     
     for(var i = rownums; i >= 1; i--){
			HTMLStr += "<tr>";
         for (var j=1; j <= columnnums; j++) {
        	 
             para1 = iCnt;
             para2 = depotrackcode + "-" + i + "-" + j; 
             
             var str = '<td align=center><input type="button" name="strId[#P2]" id="strId[#P2]" value="#P2"> <input type="hidden" name="strList[#P1]" id="strList[#P1]" value="#P2"></td>';
             str = str.replaceAll('#P1',para1,"g");
             str = str.replaceAll('#P2',para2,"g");
				
             HTMLStr += str;						
             iCnt++;	//#P1
         }
         HTMLStr += "</tr>";
     }
     
     
     $('#depotrackTR').html(HTMLStr).css("padding","10px 0");
		$('#depotrackTR td').css("padding","3px 0");
		var wh=500/columnnums-10;
		$('#depotrackTR input').height(wh).width(wh*2.2).css("padding","0");
		resetH();
 }
 
 String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
     /* 
     string：字符串表达式包含要替代的子字符串。
     reallyDo：被搜索的子字符串。
     replaceWith：用于替换的子字符串。
     */
     if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
         return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);
     } else {
         return this.replace(reallyDo, replaceWith);
     }
 }
</script>
</body>
</html>
