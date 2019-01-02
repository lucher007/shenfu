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
			<div class="easyui-panel" title="产品信息" style="width:100%;" data-options="footer:'#ft'">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">产品编码：</td>
						<td>
							<input id="productcode" name="productcode" class="easyui-combogrid" style="width: 200px;"> <span class="red">*</span>
						</td> 
					</tr>
					<tr>
						<td height="30" align="right">产品名称：</td>
						<td >
							<input type="text" class="inp" name="productname" id="productname" readonly="readonly" style="background:#eeeeee;width: 200px;"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td height="30"align="right">入库数量：</td>
						<td >
							<input type="text"  class="easyui-numberbox" name="depotamount" id="depotamount" style="width: 200px;"
							maxlength="8"  data-options="min:1,value:1"
							> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">入库负责人编号：</td>
						<td width="25%">
							<input type="text" class="inp w200" name="inoutercode" id="inoutercode" readonly="readonly" style="background:#eeeeee;" value="${materialdepot.inoutercode }">
							<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">入库负责人姓名：</td>
						<td width="25%">
							<input type="text" class="inp w200" name="inoutername" id="inoutername" readonly="readonly" style="background:#eeeeee;" value="${materialdepot.inoutername }"><span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td height="30" width="15%" align="right">存放仓库位置：</td>
						<td width="25%">
							<input id="depotrackcode" name="depotrackcode"> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td height="30" width="15%" align="right">供应商简称：</td>
						<td width="25%">
							<input id="suppliername" name="suppliername" style="width: 400px;"> 
						</td>
					</tr>
					<tr>
						<td height="30" width="15%" align="right">产品软件版本：</td>
						<td width="25%">
							<input class="inp" id="productversion" name="productversion" style="width: 400px;"> <span class="red">*</span>
						</td>
					</tr>
				</table>
			</div>
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveIndepot();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">产品入库</a>
				    </cite><span class="red">${productdepot.returninfo}</span>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
    <script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript">
	
	function checkVal() {
		if ($('#productcode').combogrid('getValue') == "") {
			$.dialog.tips("请选择入库产品编号", 1, "alert.gif", function() {
				$('#productcode').next('span').find('input').focus();
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
		
		if ($('#depotrackcode').combobox('getValue') == "") {
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
		    	$("#addForm").attr("action", "productdepot/saveIndepot?rid="+Math.random());
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
		
		//$("#addForm").attr("action", "productdepot/saveIndepot");
	    //$("#addForm").submit();
	}

	
	$(function () {
       var returninfo = '${productdepot.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
	
	
	//获取产品信息
	var wireRod;  
    $.ajax({
        url: 'product/getProductCombogridJson?rid='+Math.random(),
        type: "post",  
        dataType: "json",  
        success: function (result) {
            wireRod = result;
        }  
     });  
	
	
	$('#productcode').combogrid({
        panelWidth: 700,
		idField: 'productcode',
		textField: 'productcode',
		//url: 'product/getProductCombogridJson?rid='+Math.random(),
		method: 'get',
		fitColumns: true,
		columns: [[
			{ field: 'productcode', title: '产品编号',width:150,align:"center"},
			{ field: 'productname', title: '产品名称',width:150,align:"center"},
            { field: 'typecode', title: '产品类型',width:150,align:"center"},
            { field: 'typename', title: '类型名称',width:150,align:"center"},
            { field: 'productsourcename', title: '产品来源',width:100,align:"center"},
		]],
		onShowPanel:function () {  
            $(this).combogrid('grid').datagrid('loadData', wireRod);  
        },
        onChange: function (q){ 
            doSearch(q,wireRod,['productcode','productname','typecode','typename','productsourcename'],$(this));  
        },
		onBeforeSelect: function (rowIndex, rowData){  
			//if("0" == rowData.productsource){
			//	$.dialog.tips("只能选择外购产品入库", 2, 'alert.gif');
			//	return false;
			//}
		},
		onSelect: function (rowIndex, rowData){  
				$("#productname").val(rowData.productname);  
		 } 
   });
	
	//q为用户输入，data为远程加载的全部数据项，searchList是需要进行模糊搜索的列名的数组，ele是combogrid对象  
	//doSearch的思想其实就是，进入方法时将combogrid加载的数据清空，如果用户输入为空则加载全部的数据，输入不为空  
	//则对每一个数据项做匹配，将匹配到的数据项加入rows数组，相当于重组数据项，只保留符合筛选条件的数据项，  
	//如果筛选后没有数据，则combogrid加载空，有数据则重新加载重组的数据项  
	function doSearch(q,data,searchList,ele){  
        ele.combogrid('grid').datagrid('loadData', []);  
        if(q == ""){  
            ele.combogrid('grid').datagrid('loadData', data);  
            return;  
        }  
        var rows = [];  
        $.each(data,function(i,obj){  
            for(var p in searchList){  
                var v = obj[searchList[p]];  
                if (!!v && v.toString().indexOf(q) >= 0){  
                    rows.push(obj);  
                    break;  
                }  
            }  
        });  
        if(rows.length == 0){  
            ele.combogrid('grid').datagrid('loadData', []);  
            return;  
        }  
        ele.combogrid('grid').datagrid('loadData', rows);  
    }  
	
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
	    	$(this).combobox("setValue", '${materialdepot.depotrackcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
              
        } 
	}); 
	
	//加载列表
	$('#suppliername').combobox({    
	    url:'supplier/getSupplierComboBoxJson',    
	    valueField:'id',    
	    textField:'text',
	    //数据执行之后才加载
	    onLoadSuccess:function(node, data){
	    	//默认选择
	    	$(this).combobox("setValue", '${materialdepot.suppliername}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
              
        } 
	}); 
	
	function goback(){
		parent.closeTabSelected();
	}
</script>
</body>
</html>
