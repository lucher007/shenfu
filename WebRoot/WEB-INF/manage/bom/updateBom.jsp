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
  <form action="" method="post" id="updateform" name="updateform">
    <input type="hidden" name="id" id="id" value="${bom.bom.id}"/>
     <div class="easyui-panel" style="width:100%;" title="BOM清单修改" data-options="footer:'#ft'">
       <table>
          <!--
          <tr>
            <td align="right">父节点：</td>
            <td >
               <input type="text" class="inp" name="parentbomname" id="parentbomname" readonly="readonly" style="background:#eeeeee; width: 250px;" value="${bom.parentbom.materialname}">
               <input type="hidden" id="pid" name="pid" value="${bom.parentbom.id }">
            </td>
          </tr>
             -->
          <tr>
          	<td align="right">产品编号：</td>
			<td>
			   <input type="text" class="inp" name="productcode" id="productcode" readonly="readonly" style="background:#eeeeee; width: 250px;" value="${bom.product.productcode }">
			</td>
		  </tr>
          <tr>
          	<td align="right">产品名称：</td>
			<td>
			   <input type="text" class="inp" name="productname" id="productname" readonly="readonly" style="background:#eeeeee; width: 250px;" value="${bom.product.productname }">
			</td>
		  </tr>
          <tr>
            <td align="right">材料名称：</td>
            <td>
               <input id="materialcode" name="materialcode" style="width: 250px;"> <span class="red">*</span>
            </td>
          </tr>
          <tr>
			<td align="right">数量：</td>
			<td>
				<input type="text" class="inp easyui-numberbox" name="amount" id="amount" value="${bom.bom.amount }" > <span class="red">*</span>
			</td> 
		  </tr>
        </table>
      </div>
	  <div id="ft" style="padding:5px;">
			<cite> 
       			<a href="javascript:updateBom();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">保存</a>
		    </cite><span class="red">${bom.returninfo}</span>
	  </div>  
    </div>
  </form>
</div>
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" charset="utf-8" src="js/common/bomChoose.js"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/comtools.js"></script>
<script type="text/javascript">

  function updateBom() {
	  if ($('#materialcode').combobox('getValue') == "") {
			$.dialog.tips("请选择材料", 1, "alert.gif", function() {
				$('#materialcode').next('span').find('input').focus();
			});
			return false;
		}
	    
	    if ($('#amount').val() == '') {
	      $.dialog.tips('请输入材料数量', 1, 'alert.gif');
	      $('#amount').focus();
	      return;
	    }
      
	    var amountInt = parseInt($("#amount").val());
	    if (amountInt <= 0) {
	    	$("#amount").focus();
			$.dialog.tips("材料数量输入不正确", 2, "alert.gif");
			return false;
	    }
	    
      $('#updateform').attr('action', 'bom/update');
      $("#updateform").submit();
  }
  
  function goBack() {
      $("#updateform").attr("action", "bom/findByList");
      $("#updateform").submit();
  }
  
  
  $(function () {
      var returninfo = '${bom.returninfo}';
      if (returninfo != '') {
          $.dialog.tips(returninfo, 1, 'alert.gif');
      }
      
      var flag = '${bom.flag}';
      if (flag == '1') {//修改成功
	      var node = {
	         id: '${bom.bom.id}',
	        pId: '${bom.bom.pid}',
	    bomcode: '${bom.bom.bomcode}',
	        name: '${bom.bom.materialname}(${bom.bom.materialcode})_${bom.bom.amount}',
	        isParent: true,
	        type: 2
	      };
	      parent.updateNode(node);
	  }
  });
  
   //加载材料列表
	$('#materialcode').combobox({    
	    url:'material/getMaterialComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${bom.bom.materialcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
          
    } 
	}); 
  
</script>
</body>
</html>
