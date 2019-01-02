<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		<form method="post" id="addForm" name="addForm">
			<div class="easyui-panel" title="材料生产信息" style="width:100%;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">生产批次号：</td>
						<td>
							<input type="text" class="inp" name="batchno" id="batchno" readonly="readonly" style="background:#eeeeee;width: 200px;" value="${installmaterial.installmaterial.batchno }">  <span class="red">*</span>
						</td> 
						<td align="right">材料SN码：</td>
						<td>
							<input type="text" class="inp" name="materialidentfy" id="materialidentfy" readonly="readonly" style="background:#eeeeee;width: 200px;" value="${installmaterial.installmaterial.materialidentfy }">  <span class="red">*</span>
						</td> 
					</tr>
					<tr>
						<td align="right">材料编码：</td>
						<td>
							<input type="text" class="inp" name="materialcode" id="materialcode" readonly="readonly" style="background:#eeeeee;width: 200px;" value="${installmaterial.installmaterial.materialcode }">  <span class="red">*</span>
						</td> 
						<td height="30" align="right">材料名称：</td>
						<td >
							<input type="text" class="inp" name="materialname" id="materialname" readonly="readonly" style="background:#eeeeee;width: 200px;" value="${installmaterial.installmaterial.materialname }"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td height="30"align="right">入库数量：</td>
						<td >
							<input type="text" class="inp" name="depotamount" id="depotamount" readonly="readonly" style="background:#eeeeee;width: 200px;" value="${installmaterial.installmaterial.depotamount }"> <span class="red">*</span>
						</td>
						<td align="right">入库负责人编号：</td>
						<td >
							<input type="text" class="inp w200" name="inoutercode" id="inoutercode" readonly="readonly" style="background:#eeeeee;" value="${installmaterial.inoutercode }">
							<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						</td>
					</tr>
					<tr>
						<td align="right" >入库负责人姓名：</td>
						<td >
							<input type="text" class="inp w200" name="inoutername" id="inoutername" readonly="readonly" style="background:#eeeeee;" value="${installmaterial.inoutername }"><span class="red">*</span>
						</td>
						<td height="30" align="right">存放仓库位置：</td>
						<td >
							<input id="depotrackcode" name="depotrackcode"> <span class="red">*</span>
						</td>
					</tr>
				</table>
			</div>
			
			<!--材料检测内容 -->
			<div class="easyui-panel" title="材料检测内容" style="width:100%;" data-options="footer:'#ft'">
				<table style="width:100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="hightemp">
						<td align="right">高温老练：</td>
						<td >
							<select id="hightemp" name="hightemp" class="select">
								<option value="1" <c:if test="${materialcheck.hightemp == '1'}">selected</c:if>>合格</option>
								<option value="0" <c:if test="${materialcheck.hightemp == '0'}">selected</c:if>>不合格</option>
							</select>
						</td>
					</tr>
					<tr class="currentvoltage">
						<td align="right">电流电压是否正常：</td>
						<td >
							<select id="currentvoltage" name="currentvoltage" class="select">
								<option value="1" <c:if test="${materialcheck.currentvoltage == '1'}">selected</c:if>>合格</option>
								<option value="0" <c:if test="${materialcheck.currentvoltage == '0'}">selected</c:if>>不合格</option>
							</select>
						</td>
					</tr>
					<tr class="displayscreen">
						<td align="right">显示屏是否正常点亮：</td>
						<td >
							<select id="displayscreen" name="displayscreen" class="select">
								<option value="1" <c:if test="${materialcheck.displayscreen == '1'}">selected</c:if>>合格</option>
								<option value="0" <c:if test="${materialcheck.displayscreen == '0'}">selected</c:if>>不合格</option>
							</select>
						</td>
					</tr>
					<tr class="touchscreen">
						<td align="right">触屏是否灵敏：</td>
						<td >
							<select id="touchscreen" name="touchscreen" class="select">
								<option value="1" <c:if test="${materialcheck.touchscreen == '1'}">selected</c:if>>合格</option>
								<option value="0" <c:if test="${materialcheck.touchscreen == '0'}">selected</c:if>>不合格</option>
							</select>
						</td>
					</tr>
					<tr class="messageconnect">
						<td align="right">通讯是否连接：</td>
						<td >
							<select id="messageconnect" name="messageconnect" class="select">
								<option value="1" <c:if test="${materialcheck.messageconnect == '1'}">selected</c:if>>合格</option>
								<option value="0" <c:if test="${materialcheck.messageconnect == '0'}">selected</c:if>>不合格</option>
							</select>
						</td>
					</tr>
					<tr class="openclosedoor">
						<td align="right">开关门是否顺畅：</td>
						<td >
							<select id="openclosedoor" name="openclosedoor" class="select">
								<option value="1" <c:if test="${materialcheck.openclosedoor == '1'}">selected</c:if>>合格</option>
								<option value="0" <c:if test="${materialcheck.openclosedoor == '0'}">selected</c:if>>不合格</option>
							</select>
						</td>
					</tr>
					<tr class="keystrokesound">
						<td align="right">按键声音是否正常：</td>
						<td >
							<select id="keystrokesound" name="keystrokesound" class="select">
								<option value="1" <c:if test="${materialcheck.keystrokesound == '1'}">selected</c:if>>合格</option>
								<option value="0" <c:if test="${materialcheck.keystrokesound == '0'}">selected</c:if>>不合格</option>
							</select>
						</td>
					</tr>
					<tr class="fingerprint">
						<td align="right">内外指纹是否灵敏：</td>
						<td >
							<select id="fingerprint" name="fingerprint" class="select">
								<option value="1" <c:if test="${materialcheck.fingerprint == '1'}">selected</c:if>>合格</option>
								<option value="0" <c:if test="${materialcheck.fingerprint == '0'}">selected</c:if>>不合格</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">检测备注：</td>
						<td>
							<textarea id="checkinfo" name="checkinfo" style="width:400px; height:30px;">${materialcheck.checkinfo}</textarea>
						</td>
					</tr>
					<tr>
						<td align="right">材料检测状态：</td>
						<td >
							<select id="checkstatus" name="checkstatus" class="select">
								<option value="1" <c:if test="${materialcheck.checkstatus == '1'}">selected</c:if>>合格入库</option>
								<option value="0" <c:if test="${materialcheck.checkstatus == '0'}">selected</c:if>>不合格入库</option>
							</select>
						</td>
					</tr>
			  	</table>
			</div>
			
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveIndepot();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">材料入库</a>
				    </cite><span class="red">${installmaterial.returninfo}</span>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
    <script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript">
	
	function checkVal() {
		if ($("#materialcode") != undefined && ($("#materialcode").val() == "" || $("#materialcode").val() == null )) {
			$.dialog.tips("请选择入库材料编号", 1, "alert.gif", function() {
				$('#materialcode').focus();
			});
			return false;
		}
		
		if ($("#materialname") != undefined && ($("#materialname").val() == "" || $("#materialname").val() == null )) {
			$.dialog.tips("请选择入库材料名称", 1, "alert.gif", function() {
				$('#materialcode').focus();
			});
			return false;
		}

		if ($("#depotamount") != undefined && ($("#depotamount").val() == "" || $("#depotamount").val() == null )) {
			$.dialog.tips("请输入入库数量", 1, "alert.gif", function() {
				$("#depotamount").focus();
			});
			return false;
		}
		
		if ($("#inoutercode") != undefined && ($("#inoutercode").val() == "" || $("#inoutercode").val() == null )) {
			$.dialog.tips("请入库负责人", 1, "alert.gif", function() {
				$("#inputercode").focus();
			});
			return false;
		}
		
		if ($("#depotrackcode").combobox('getValue') == "") {
			$.dialog.tips("请选择货柜存放位置", 1, "alert.gif", function() {
				$("#depotrackcode").focus();
			});
			return false;
		}
		
		return true;
	}
	
	function saveIndepot() {
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
		    	$("#addForm").attr("action", "installmaterial/saveIndepot?rid="+Math.random());
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
		
		//$("#addForm").attr("action", "installmaterial/saveIndepot");
	    //$("#addForm").submit();
	}

	
	$(function () {
		//屏蔽页面不需要的检测
		changeMaterialcheckInfo();
		
       var returninfo = '${installmaterial.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
	
	var employeeDialog;
	function chooseEmployee() {
		employeeDialog = $.dialog({
			title : '装维员工查询',
			lock : true,
			width : 1100,
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
		$("#inoutercode").val(jsonObject.employeecode);
		$("#inoutername").val(jsonObject.employeename);
	}
	
	function cleanEmployee() {
		  $('#producercode').val("");
		  $('#producername').val("");
	}
	
	//加载材料列表
	$('#depotrackcode').combobox({    
	    url:'depotrack/getDepotrackinfoComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${installmaterial.depotrackcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
              
        } 
	}); 
	
	function goback(){
		parent.closeTabSelected();
	}
	
	function changeMaterialcheckInfo(){
    	var materialcode =  $("#materialcode").val();
    	if(materialcode=="0063"){ //按键板检测
    		$(".hightemp").show(); //高温老练
    		$(".currentvoltage").show();//电流电压是否正常
    		$(".keystrokesound").show(); //按键声音是否正常
    		
    		$(".displayscreen").hide(); //显示屏是否正常点亮
    		$(".touchscreen").hide();   //触屏是否灵敏
    		$(".messageconnect").hide(); //通讯是否连接
    		$(".openclosedoor").hide();  //开关门是否顺畅
    		$(".fingerprint").hide();    //内外指纹是否灵敏
    	}else if(materialcode=="0066"){//网络级别，请选择网络和营业厅
    		$(".hightemp").show(); //高温老练
    		$(".currentvoltage").show();//电流电压是否正常
    		$(".displayscreen").show(); //显示屏是否正常点亮
    		$(".touchscreen").show();   //触屏是否灵敏
    		$(".messageconnect").show(); //通讯是否连接
    		$(".openclosedoor").show();  //开关门是否顺畅
    		$(".keystrokesound").show(); //按键声音是否正常
    		$(".fingerprint").show();    //内外指纹是否灵敏
    	}else if(materialcode=="0067"){
    		$(".hightemp").show(); //高温老练
    		$(".currentvoltage").show();//电流电压是否正常
    		$(".openclosedoor").show();  //开关门是否顺畅
    		$(".keystrokesound").show(); //按键声音是否正常
    		
    		$(".displayscreen").hide(); //显示屏是否正常点亮
    		$(".touchscreen").hide();   //触屏是否灵敏
    		$(".messageconnect").hide(); //通讯是否连接
    		$(".fingerprint").hide();    //内外指纹是否灵敏
    	}
    }
	
</script>
</body>
</html>
