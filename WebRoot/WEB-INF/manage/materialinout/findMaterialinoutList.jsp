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
    <div class="seh-box">
        <form action="" method="post" id="searchForm" name="searchForm">
            <table width="100%">
		    	<tr>
		    		<td height="30" width="10%" align="right">材料名称：</td>
					<td width="15%">
						<input id="materialcode" name="materialcode">
					</td>
		    		<td align="right" width="10%">类型：</td>
		    		<td width="15%">
		    			<select id="type" name="type" class="select" onchange="queryMaterialinout();">
		    			    <option value="" <c:if test="${materialinout.type == '' }">selected</c:if>>请选择</option>
		    			 	<option value="0" <c:if test="${materialinout.type == '0' }">selected</c:if>>入库</option>
			                <option value="1" <c:if test="${materialinout.type == '1' }">selected</c:if>>出库</option>
			            </select>
		    		</td>
		    		<td height="26"  align="right" width="10%">查询开始时间：</td>
                    <td width="15%">
                    	<input type="text" id="begintime" name="begintime"   value="${materialinout.begintime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd'});" class="Wdate inp w150">
                    </td>
                    <td align="right" width="10%">查询结束时间：</td>
                    <td width="15%">
                    	<input type="text" id="endtime" name="endtime"  value="${materialinout.endtime }"
                    	onclick="WdatePicker({lang:'${locale}',skin:'blueFresh',isShowWeek:true,isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'begintime\');}'});" class="Wdate inp w150">
					</td>
					<td align="right" width="130">
						<a href="javascript:queryMaterialinout();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">查询</a>
   		    		</td>
		    	</tr>
   		    </table>
   		</form>
    </div>
    <div id = "materialinoutDG" style="width:auto; height: auto;"></div>
    <div id="tools">
		<div style="margin-bottom:5px">
			<a href="javascript:saveExportExcel();" class="easyui-linkbutton" iconCls="icon-reload"  plain="true">导出Excel</a>
			<a href="javascript:printMaterialinout();" class="easyui-linkbutton" iconCls="icon-print"  plain="true">在线打印</a>
		</div>
	</div>
	<div id="printInfo" style="display: none;"></div>
</div>
    
<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" language="javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="<%=basePath%>js/lodop/LodopFuncs.js"></script>
<script type="text/javascript">
	
	function initMaterialinoutDatagrid(){
         $('#materialinoutDG').datagrid({
              title: '材料出入库信息',
              queryParams: paramsJson(),
              url:'materialinout/findListJson',
              pagination: true,
              singleSelect: true,
              scrollbar:true,
              pageSize: 10,
              pageList: [10,30,50], 
              toolbar:'#tools',
              fitColumns:true,
              onClickRow: onClickRow,
              //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
              loadMsg:'正在加载数据......',
              rowStyler: function(index,row){
	         	    if (row.type == '0'){
	         	    	return 'color:blue;font-weight:bold;';
					}else if (row.type == '1'){
						return 'color:red;font-weight:bold;';
					}else{
						return '';
					}
			  },
              columns: [[
				  { field: 'materialcode', title: '材料编号',width:100,align:"center",sortable:"true"},
                  { field: 'materialname', title: '材料名称',width:100,align:"center"},
                  { field: 'batchno', title: '批次号',width:100,align:"center",sortable:"true"},
                  { field: 'materialidentfy', title: 'SN码',width:100,align:"center",sortable:"true"},
                  { field: 'typename', title: '类型',width:100,align:"center",},
                  { field: 'inoutamount', title: '出入库量',width:100,align:"center"},
                  { field: 'depotrackcode', title: '存放货柜位置',width:100,align:"center"},
                  { field: 'inoutername', title: '出入库负责人',width:100,align:"center",},
                  { field: 'addtime', title: '操作时间',width:100,align:"center",sortable:"true"},
              ]]
          });
	}
    
    function operatorformatter(value,row,index){
	 	return row.operator.employee.employeename;
	}
    
    function typeformatter(value,row,index){
	 	if(value == '0'){
	 		return "入库";
	 	}else if(value == '1') {
	 		return "出库";
	 	}
	 }
	 
	 function addtimeformatter(value,row,index){
	 	 return value.substring(0,19);
	 }
    
	function materialformatter(value,row,index){
	 	var id = row.id;
	 	var updateContent = '<a class="btn-edit" href="javascript:updateMaterial('+ id +');">修改</a>';
	 	var deleteContent = '<a class="btn-del" href="javascript:deleteMaterial('+ id +');" >删除</a>';
        return  updateContent + deleteContent;
        	  
	 }
	
	//将表单数据转为json
	 function paramsJson(){
	 	var json = {
	 		materialcode:$('#materialcode').combobox('getValue'),
	 		type:$("#type").val(),
	 		begintime:$("#begintime").val(),
	 		endtime:$("#endtime").val()
	 	};
	 	
	 	return json;
	 }
	
	function queryMaterialinout() {
		$('#materialinoutDG').datagrid('reload',paramsJson());
	}
	
	$(function () {
	   
	   //初始化材料列表
	   initMaterialinoutDatagrid();
		
       var returninfo = '${materialinout.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
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
	    	$(this).combobox("setValue", '${materialinout.materialcode}');
	    },
	    //绑定onchanger事件
	    onChange:function(){  
	    	queryMaterialinout(); 
        } 
	}); 
	
	function saveExportExcel(){
		$.dialog.confirm('确认是否导出？', function(){ 
			$("#searchForm").attr("action", "materialinout/findMaterialinoutExportExcel");
			$("#searchForm").submit();
		}, function(){
			
			});
	}
	
	//页面敲击回车键
	$(document).keyup(function (e) {//捕获文档对象的按键弹起事件  
		
	    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了  
	    	//初始化员工列表
	 	   queryMaterialinout();
	    }  
	});
	
	//点击表格某一条
	function onClickRow(index, data){
		var id = data.id;
		
		var data = {
		     id: id,
		     tag: 'printInfo'
		};
		var url = 'materialinout/getPrintInfo .' + data.tag;
		$('#printInfo').load(url, data, function () {
			
		});
	} 
	
	function printMaterialinout() {
		var selected = $('#materialinoutDG').datagrid("getSelected");
        if(selected == null){
   	     $.dialog.tips('请选择需要修改的对象', 2, 'alert.gif');
		     return;
        }
        //获取ID
        //var id = selected.id;
        
        //开始打印
	    LODOP = getLodop();
	    //LODOP.SET_PRINT_PAGESIZE (0,40+"mm", 30+"mm","");  
	    
	    LODOP.PRINT_INIT("");	
	    
		LODOP.ADD_PRINT_TABLE(80,55,600,"100%",document.getElementById("printInfo").innerHTML);	  
	    
	    //LODOP.ADD_PRINT_BARCODE(25,20,150,70,"128Auto",18050309010008);
        
	    //LODOP.ADD_PRINT_BARCODE(25,20,120,70,"128Auto",batchno);
	    
	    LODOP.PREVIEW();
	    //LODOP.PRINT();
	    
	 }
</script>
</body>
</html>