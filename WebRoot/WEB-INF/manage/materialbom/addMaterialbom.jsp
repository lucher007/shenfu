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
    body { padding: 10px 0; }
		.easyui-panel table { width: 100%; }
		.easyui-panel table tr td { height: 30px; width: expression((cellIndex%2==0)?"15%":"25%"); text-align: expression((cellIndex%2==0)?"right":"left"); }
		.easyui-panel table tr td:nth-child(odd) { width: 15%; text-align: right; }
		.easyui-panel table tr td:nth-child(even) { width: 25%; text-align: left; }
		.select { width: 158px; border:solid #3b98c6 1px; }
		.inp { width: 150px; }
  </style>
</head>

<body>
<div id="body">
  <form action="bom!save" method="post" id="addForm" name="addForm">
    <div class="easyui-panel" style="width:100%;" title="BOM清单添加" data-options="footer:'#ft'">
        <table>
          <!--
          <tr >
            <td align="right">父节点：</td>
            <td >
			   <input type="text" class="inp" name="parentbomname" id="parentbomname" readonly="readonly" style="background:#eeeeee; width: 250px;" value="${materialbom.parentbom.materialname}">
               <input type="hidden" id="pid" name="pid" value="${materialbom.parentbom.id }">
            </td>
          </tr>
           -->
           <tr>
          	<td align="right">材料编号：</td>
			<td>
			   <input type="text" class="inp" name="materialcode" id="materialcode" readonly="readonly" style="background:#eeeeee; width: 250px;" value="${materialbom.material.materialcode}">
			</td>
		  </tr>
          <tr>
          	<td align="right">材料名称：</td>
			<td>
			   <input type="text" class="inp" name="materialname" id="materialname" readonly="readonly" style="background:#eeeeee; width: 250px;" value="${materialbom.material.materialname }">
			</td>
		  </tr>
          <tr>
            <td align="right">元器件名称：</td>
            <td>
               <input id="componentcode" name="componentcode" style="width: 250px;"> <span class="red">*</span>
            </td>
          </tr>
          <tr>
			<td align="right">数量：</td>
			<td>
				<input type="text" class="inp easyui-numberbox" name="amount" id="amount" value="${materialbom.amount}" > <span class="red">*</span>
			</td> 
		  </tr>
        </table>
      </div>
	  <div id="ft" style="padding:5px;">
			<cite> 
       			<a href="javascript:saveBom();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
		    </cite><span class="red">${materialbom.returninfo}</span>
	  </div>  
  </form>
</div>
<script type="text/javascript" language="javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" language="javascript" src="js/form.js"></script>
<script language="javascript" type="text/javascript" src="js/comtools.js"></script>
<script language="javascript" type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript">

  //判断是否为数字
  function checkNumberChar(ob) {
    if (/^\d+$/.test(ob)) {
      return true;
    } else {
      return false;
    }
  }

  function saveBom() {
	  if ($('#componentcode').combobox('getValue') == "") {
			$.dialog.tips("请选择元器件", 1, "alert.gif", function() {
				$('#componentcode').next('span').find('input').focus();
			});
			return false;
		}
	    
	    if ($('#amount').val() == '') {
	       $.dialog.tips('请选择元器件数量', 1, 'alert.gif');
	       $('#amount').focus();
	       return;
	    }
	    
	    var amountInt = parseInt($("#amount").val());
	    if (amountInt <= 0) {
	    	$("#amount").focus();
			$.dialog.tips("数量输入不正确", 2, "alert.gif");
			return false;
	    }
	    
	    $("#addForm").attr("action", "materialbom/save");
	    $("#addForm").submit();
  }
  
  function goBack() {
      $("#addForm").attr("action", "materialbom/findByList?modelid=");
      $("#addForm").submit();
  }
  
  $(function () {
       var returninfo = '${materialbom.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
       
       var flag = '${materialbom.flag}';
       if (flag == '1') {//添加成功
	      var newNode = {
	          id: '${materialbom.id}',
	         pId: '${materialbom.pid}',
	     bomcode: '${materialbom.bomcode}',
	        name: '${materialbom.componentname}_${materialbom.componentmodel}(${materialbom.componentcode})_${materialbom.amount}',
	        path: 'materialbom/updateInit?id=${materialbom.id}',
	    isParent: true,
	        type: 2
	      };
	      parent.addNode(newNode);
	   }
  });
  
   //加载元器件列表
	$('#componentcode').combobox({
	    url:'component/getComponentComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${materialbom.componentcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
            
      } 
	}); 
  
</script>
</body>
</html>
