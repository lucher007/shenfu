<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link type="text/css" rel="stylesheet" href="<%=basePath%>main/plugin/uploadify/uploadify.css" />
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
			<div class="easyui-panel" title="生产组装信息" style="width:100%;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="30" align="right" width="10%">材料编码：</td>
						<td width="20%">
							<input id="materialcode" name="materialcode" class="easyui-combogrid"> <span class="red">*</span>
						</td> 
						<td align="right" width="10%">材料名称：</td>
						<td width="15%">
							<input type="text" class="inp" name="materialname" id="materialname" readonly="readonly" style="background:#eeeeee;"> <span class="red">*</span>
						</td>
						<td align="right" width="10%">生产数量：</td>
						<td width="15%">
							<input type="text"  class="easyui-numberbox" name="amount" id="amount" 
							maxlength="8"  data-options="min:1,max:100,value:1,onChange: function (a, b) { queryBom(); }" 
							> <span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">生产负责人编号：</td>
						<td width="15%">
							<input type="text" class="inp w200" name="producercode" id="producercode" readonly="readonly" style="background:#eeeeee;" value="${installinfo.producercode }">
							<a href="javascript:chooseEmployee();" class="easyui-linkbutton" iconCls="icon-search" style="width:80px">请选择</a>
						</td>
						<td align="right" width="10%">生产负责人姓名：</td>
						<td width="15%" colspan="3">
							<input type="text" class="inp w200" name="producername" id="producername" readonly="readonly" style="background:#eeeeee;" value="${installinfo.producername }"><span class="red">*</span>
						</td>
					</tr>
					<tr>
						<td align="right" width="10%">材料版本：</td>
						<td width="15%">
							<input type="text" class="inp" style="width: 200px" name="materialversion" id="materialversion" value="${installinfo.materialversion}">
						</td>
					</tr>
					<tr>
						<td align="right">组装信息：</td>
			            <td colspan="5"> 
							<textarea id="installinformation" name="installinformation" style="width:550px; height:50px;"
		                       onKeyDown="checkLength('installinformation',100)" onKeyUp="checkLength('installinformation',100)">${installinfo.installinformation}</textarea>
		            		<span class="red">还可以输入<span id="validNuminstallinformation">100</span>字</span>
		               </td>
					</tr>
				</table>
			</div>
			
			<div id = "bomDG" style="width:100%; height: 500px;" data-options="footer:'#ft'">
				<input type="hidden" name="componentJson" id="componentJson" >
			</div>
				
			<div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
						<a href="javascript:saveInstallinfo();" class="easyui-linkbutton" iconCls="icon-save" style="width:80px">确定生产</a>
				    </cite><span class="red">${installinfo.returninfo}</span>
			</div>
		</form>		
	</div>

    <script type="text/javascript" src="js/common/jquery.js"></script>
    <script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript">

  $(function () {
         initBomDatagrid();
	     var returninfo = '${installinfo.returninfo}';
	     if (returninfo != '') {
	        	$.dialog.tips(returninfo, 1, 'alert.gif');
	     }
	   
  });
  	  
  function goback(){
	  parent.closeTabSelected();
  }
	
  function checkVal() {
		
		if ($('#materialcode').combogrid('getValue')=='') {
			$('#materialcode').combogrid('textbox').focus();  
			$.dialog.tips("请选择材料编号", 2, 'alert.gif');
			return false;
	    }
		
		if ($("#amount") != undefined && ($("#amount").val() == "" || $("#amount").val() == null )) {
			$("#amount").focus();
			$.dialog.tips("请输入生产数量", 2, 'alert.gif');
			return false;
		}
		
		if ($("#producercode") != undefined && ($("#producercode").val() == "" || $("#producercode").val() == null )) {
			$("#producercode").focus();
			$.dialog.tips("请选择生产负责人", 2, 'alert.gif');
			return false;
		}
		
		//获取型号所关联的材料信息
	    var allrows  = $('#bomDG').datagrid('getRows');
        if(allrows  == null  || allrows  == ''){
        	$('#materialcode').next('span').find('input').focus();
        	$.dialog.tips("此材料未设置Bom清单", 2, 'alert.gif');
			return false;
        }
  		for(var i=0;i<allrows.length;i++){
  			if(allrows[i].batchno == null || allrows[i].batchno == '' || allrows[i].result != '1'){
  				$.dialog.tips("请选择元器件批次号！", 2, 'alert.gif');
  				$('#bomDG').datagrid('selectRow',i);
  				//$('#usermaterialDG').datagrid('beginEdit', i);
		        //var ed = $(this).datagrid('getEditor', {index:i,field:materialidentfy});
		        //获取编辑单元格对象
		        //var editorObject = $(ed.target);
		        //光标指向该编辑框
		        //editorObject.focus();
		        return false;
  			}
  		}
        
	    //将row转换成json字符串
	 	var event = JSON.stringify(allrows);
	 	//中文进行编码转换，防止乱码
		var o = JSON.parse(event); 
		//将Json字符串赋值给event
		eval("var event = '"+JSON.stringify(o)+"';"); 
		
		$("#componentJson").val(event);
		
		return true;
	}
	
  function saveInstallinfo() {
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
		    	$("#addForm").attr("action", "installinfo/save?rid="+Math.random());
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
	} 
  
 
    function initBomDatagrid(){
        $('#bomDG').datagrid({
            title: 'BOM清单',
            queryParams: paramsJson(),
            url:'installinfo/findBomListJson',
            pagination: false,
            singleSelect: true,
            scrollbar:true,
            rownumbers:true,
            //checkOnSelect: false,// 如果为true，当用户点击行的时候该复选框就会被选中或取消选中。 如果为false，当用户仅在点击该复选框的时候才会呗选中或取消。
            //pageSize: 10,
            //pageList: [10,30,50], 
            fitColumns:true,
            //idField:'id',   //idField 属性已设置，数据网格（DataGrid）的选择集合将在不同的页面保持。
            loadMsg:'loading...',
            onClickCell: onClickCell,
            onLoadSuccess:initresult,
            columns: [[
                       { field: 'componentcode', title: '元器件编码',width:40,align:"center"},
                       { field: 'componentname', title: '元器件名称',width:100,align:"center"},
                       { field: 'componentmodel', title: '元器件规格',width:100,align:"center"},
                       { field: 'amount', title: '单件需量',width:30,align:"center"},
                       { field: 'totalamount', title: '总需求量',width:30,align:"center"},
                       { field: 'batchno', title: '元器件批次号',width:100,align:"center",
                       	  editor:{
		                       		type:'combobox',
									options:{
										valueField:'id',
										textField:'text',
										method:'get',
										url:'installinfo/getComponentdepotComboBoxJson',
									},
    		                  },
                       },
                       { field: 'depotamount', title: '库存量',width:30,align:"center",
                     	  editor:{
		                       		type:'textbox',
  		                  },
                       },
                       { field: 'depotrackcode', title: '仓库存放位置',width:50,align:"center",
                      	  editor:{
 		                       		type:'textbox',
   		                  },
                        },
                       { field: 'result', title: '核对结果',width:40,align:"center",
                     	  formatter:function(value, row, index){  
                     		  return '<a href="#" onclick="return false;" id="opera_'+index+'" class="easyui-linkbutton" ></a>';  
                     	  }
                       },
                   ]],
                   onBeginEdit :function(index,row){
                	   var ed = $('#bomDG').datagrid('getEditor', {index:index,field:'batchno'});
                	   //重新加载下拉框的数据
                	   $(ed.target).combobox('reload', {componentcode:row.componentcode,depotstatus:'1'});//如果datagrid数据源列名称不是component_id记得修改这里
                	   $(ed.target).combobox({
               	        onChange:function(newvalue,oldvalue){
               	        	var textvalue = $(ed.target).combobox('getText');
               	        	var depotamount = "";
               	        	var depotrackcode = "";
               	        	if(textvalue != null && textvalue != ""){
               	        		var textvalueArr = textvalue.split("_");
               	        		depotamount = textvalueArr[1];
               	        		depotrackcode = textvalueArr[2];
               	        	}
               	        	
               	        	//赋值给库存量
               	        	var ed_depotamount = $('#bomDG').datagrid('getEditor', {index:index,field:'depotamount'});
               	        	$(ed_depotamount.target).textbox("setValue", depotamount);
               	            //赋值给库仓库存放位置
               	        	var ed_depotrackcode = $('#bomDG').datagrid('getEditor', {index:index,field:'depotrackcode'});
               	        	$(ed_depotrackcode.target).textbox("setValue", depotrackcode);
               	        }
               	    });
                   },  
        });
     }
    
      //将表单数据转为json
  	function paramsJson(){
  	 	var materialcode = $('#materialcode').combogrid('getValue');
  	 	var json = '';
  	 	if(materialcode != null && materialcode != ''){
  	 		json = {
  	 			materialcode:materialcode,
  	 			amount:$('#amount').val(),
  		 	};
  	 	}else{//默认不查询材料信息，故参数乱设置
  	 		
  	 		json = {
  	 			materialcode:'-111',
  		 	};
  	 	}
  	 	return json;
  	 }
      
      
     //单元格编辑，按回车键
	 function onClickCell(index, field){
		 //不是点击批次号单元格，不进入修改模式
		 if(field != 'batchno' && field != 'batchno' ){
			 return;
		 }
		
	     $(this).datagrid('beginEdit', index);
         var ed = $(this).datagrid('getEditor', {index:index,field:field});
         
         if(ed != null){
      	     //获取编辑单元格对象
            var editorObject = $(ed.target);
           //光标指向该编辑框
           editorObject.focus();
 		
 		   //绑定回车事件
          	var currentEdatagrid = $(this);  
      	    $('.datagrid-editable .textbox,.datagrid-editable .datagrid-editable-input,.datagrid-editable .textbox-text').bind('keydown', function(e){  
      	    	 
      	    	  var code = e.keyCode || e.which;  
      	    	   
                    if(code == 13){
                 	    
                    	 //获取输入的材料标识
                        var batchno = editorObject.combogrid('getValue');
                	   
                        //验证输入的材料标识正确性，包括是否已经出库
                        var rows = $('#bomDG').datagrid("getRows");
				        var data = {          
			        			          componentcode:rows[index].componentcode,
			        			          componentname:rows[index].componentname,
			        			         componentmodel:rows[index].componentmodel,
			        			            inoutamount:rows[index].totalamount,
			        			                batchno:batchno,
								   };
						var url="installinfo/checkComponentdepotamount?rid="+Math.random();
						$.getJSON(url,data,function(jsonMsg){
							var allrows = $('#bomDG').datagrid("getRows"); // 这段代码是// 对某个单元格赋值
							if(jsonMsg.flag == '1'){ //成功
					            allrows[index].result= '1';
					            //allrows[index].inouterphone= jsonMsg.inouterphone;
					            // 刷新该行, 只有刷新了才有效果
								//$(this).datagrid('refreshRow', index);
								
								//如果该行处于"行编辑"状态, 如果直接调用"refreshRow"方法. 会报data is undefined这个错; 
								//需要先调用"endEdit", 再调用"refreshRow", 最后调用"selectRow"和"beginEdit"这两个方法便可了;
			                       			//$('#usermaterialDG').datagrid('endEdit', index).datagrid('refreshRow', index).datagrid('selectRow', index).datagrid(
			                            	//	'beginEdit', index);
                                $(currentEdatagrid).datagrid('acceptChanges');  
                       			$(currentEdatagrid).datagrid('endEdit', index); 
                       			//var bt = $("a[name='opera_" + index + "']");  
                       			$("#opera_" + index).linkbutton({plain:true,iconCls:'icon-ok'});
							 }else{
								allrows[index].result= '0';
								//var bt = $("a[name='opera_" + index + "']");  
								$("#opera_" + index).linkbutton({plain:true,iconCls:'icon-no'});   
								$.dialog.tips(jsonMsg.returninfo, 2, 'alert.gif');
							 }
					    });
                         //$(currentEdatagrid).datagrid('acceptChanges');  
                       	 //$(currentEdatagrid).datagrid('endEdit', index); 
                  }; 	 
             });  
        }
	}
     
	//获取材料信息
	var wireRod;  
    $.ajax({
        url: 'material/getMaterialCombogridJson?rid='+Math.random()+'&materialsource=0',
        type: "post",  
        dataType: "json",  
        success: function (result) {
            wireRod = result;
        }  
     });  
     
	 $('#materialcode').combogrid({
	        panelWidth: 700,
			idField: 'materialcode',
			textField: 'materialcode',
			//url: 'material/getProductCombogridJson?rid='+Math.random(),
			method: 'get',
			fitColumns: true,
			columns: [[
				{ field: 'materialcode', title: '材料编号',width:150,align:"center"},
				{ field: 'materialname', title: '材料名称',width:150,align:"center"},
	            { field: 'materialsourcename', title: '材料来源',width:100,align:"center"},
			]],
			onShowPanel:function () {  
	            $(this).combogrid('grid').datagrid('loadData', wireRod);  
	        },
	        onChange: function (q){ 
	            doSearch(q,wireRod,['materialcode','materialname','typecode','typename','materialsourcename'],$(this));  
	        },
			onBeforeSelect: function (rowIndex, rowData){  
				if("0" == rowData.materialsource){
					//$.dialog.tips("只能选择外购材料入库", 2, 'alert.gif');
					//return false;
				}
			},
			onSelect: function (rowIndex, rowData){  
					$("#materialname").val(rowData.materialname);  
					//刷新BOM清单
					$('#bomDG').datagrid('reload',{
						materialcode:$('#materialcode').combogrid('getValue'),
						amount:$('#amount').val(),
					});
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
			$("#producercode").val(jsonObject.employeecode);
			$("#producername").val(jsonObject.employeename);
		}
		
		function cleanEmployee() {
			  $('#producercode').val("");
			  $('#producername').val("");
		}	
		
		
		function checkLength(object, maxlength) {
		    var obj = $('#' + object),
		        value = $.trim(obj.val());
		    if (value.length > maxlength) {
		      obj.val(value.substr(0, maxlength));
		    } else {
		      $('#validNum' + object).html(maxlength - value.length);
		    }
		 }
		
		function queryBom() {
			$('#bomDG').datagrid('reload',paramsJson());
		}
		
		//初始化图标
		 function initresult(data){
		    if(data){  
		        var rows=data.rows;  
		        for(var i=0; i<rows.length; i++){  
		            var batchno=rows[i].batchno;  
		            if(batchno == null || batchno == ''){
		            	var bt = $("#opera_" + i);  
			            bt.linkbutton({  
			                plain:true,  
			                iconCls:'icon-no'  
			            });  
		            }else{
		            	var bt = $("#opera_" + i);  
			            bt.linkbutton({  
			                plain:true,  
			                iconCls:'icon-ok'  
			            });  
		            }
		        }  
		    }  
		}  
</script>
</body>
</html>
