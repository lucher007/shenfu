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
<link type="text/css" rel="stylesheet" href="express/delivery.css">
<link type="text/css" rel="stylesheet" href="express/common.css">
<link rel="stylesheet" type="text/css" href="style/easyui/easyui.css">
<link rel="stylesheet" type="text/css" href="main/plugin/easyui/themes/icon.css">
<link type="text/css" rel="stylesheet" href="picUpload/jquery-ui/jquery-ui.min.css" media="screen" />
<link type="text/css" rel="stylesheet" href="picUpload/js/jquery.ui.plupload/css/jquery.ui.plupload.css" media="screen" />
<style type="text/css">
body{background:#fff;}
#uploader{max-width:800px;margin:50px auto;}

#body table tr {
     height: 40px;
   }
</style>
</head>
<body>
	<div class="container">
		<div class="inner">
			<div class="mod-search">
				<div class="box">
					<form method="POST">
						<input class="input-text" type="text" id="deliverycode" name="deliverycode" value="${userdelivery.deliverycode}" placeholder="输入您要查询的订单号" autocomplete="off"/>
						<input class="input-submit" type="button" onclick="javascript:queryExpress();" value="查询"/>
					</form>
				</div>
                <div class="easyui-panel" style="width:100%;" data-options="footer:'#ft'">
	                <div class="result">
	                    <div class="title" id="expresscompanyinfo"></div>
	                	<ul class="list" id="expressinfo"></ul>
	                </div>
			    <div id="ft" style="padding:5px;">
					<cite> 
						<a href="javascript:goback();" class="easyui-linkbutton" iconCls="icon-back" style="width:80px">返回</a>
				    </cite><span class="red">${userdelivery.returninfo}</span>
			    </div>
			</div>
			<div id="reflashinfo" class="center" style="display: none;"><br/><br/><br/><br/><img src="images/frame/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		</div>
	</div>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="express/expresscompanyinfo.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script type="text/javascript" src="js/form.js"></script>
	<script type="text/javascript" src="js/plugin/lhgdialog/lhgdialog.js?self=true&skin=iblue"></script>
	<script type="text/javascript" src="picUpload/js/jquery-1.8.3.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="picUpload/jquery-ui/jquery-ui.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="picUpload/js/plupload.full.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="picUpload/js/jquery.ui.plupload/jquery.ui.plupload.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="picUpload/js/i18n/zh_CN.js" charset="UTF-8"></script>
	<script type="text/javascript">
	
    function goback(){
		parent.closeDialog();
	}
    
    function queryExpress(){
    	var deliverycode = $("#deliverycode").val();
    	onloadExpressInfo(deliverycode);
    }
    
    function onloadExpressInfo(deliverycode){
    	//清空快递公司信息
		$("#expresscompanyinfo").html("");
		//清空快递信息
		$("#expressinfo").html("");
		
		$("#reflashinfo").show();
		//var number = $("#expressNumber").val();
		 $.ajax({
				type: "POST",
				url: 'userdelivery/queryWuliujilu?tm='+new Date().getTime(),
		    	data: {deliverycode:deliverycode},
				dataType:'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data){
					$("#reflashinfo").hide();
					if("ok" == data.msg){
						var item = jQuery.parseJSON(data.value);
						
						if("ok" == item.message){
							//快递状态
							var state = item.state;
							//快递状态中文
							var statename = getStatsname(state);
							//快递公司KEY
							var companykey = item.com;
							//通过快递公司KYE获取快递公司信息，包括公司名称
							var result = getCompanyInfo(companykey);
							if(result != null){
								$("#expresscompanyinfo").append('快递公司：<span class="name">' + result.name + '</span>物流状态：<span class="status status-6">' + statename + '</span></div>');
							}
							$.each(item.data, function(i, list){
								if(i == 0){
									$("#expressinfo").append('<li class="status status-6"><div class="time">'+list.time+'</div><div class="detail">'+list.context+'</div></li>');
								}else{
									$("#expressinfo").append('<li ><div class="time">'+list.time+'</div><div class="detail">'+list.context+'</div></li>');	
								}
							 }); 
						}else{
							$("#expressinfo").append('<td class="center" colspan="2">'+item.message+'</td>');
						}
					}else{
						$("#expressinfo").append('<td class="center" colspan="2">没有查询到记录</td>');
					}
					$("#reflashinfo").hide();
				}
			});
	}
    
    $(function () {
 	   
 	   //初始化产品列表
 	   queryExpress();
 		
        var returninfo = '${userdelivery.returninfo}';
        if (returninfo != '') {
         	$.dialog.tips(returninfo, 1, 'alert.gif');
        }
     });
    
    //通过快递公司KEY获取公司的信息，包括公司名称
    function getCompanyInfo(companyCode){
   	  var result = {};
   	    for (var i = 0; i < jsoncom.company.length; i++) {
   	      if (companyCode == jsoncom.company[i].number) {
   	        result = jsoncom.company[i];
   	        break;
   	      }
   	    }
   	    return result;
   	}
    
    //通过快递状态key，获取状态值中文
    function getStatsname(state){
    	if(state == '0'){
    		return '在途'; //即货物处于运输过程中；
    	}else if(state == '1'){
    		return '揽件'; //货物已由快递公司揽收并且产生了第一条跟踪信息;
    	}else if(state == '2'){
    		return '疑难'; //货物寄送过程出了问题；
    	}else if(state == '3'){
    		return '签收'; //收件人已签收；
    	}else if(state == '4'){
    		return '退签'; //即货物由于用户拒签、超区等原因退回，而且发件人已经签收；
    	}else if(state == '5'){
    		return '派件'; //即快递正在进行同城派件；
    	}else if(state == '6'){
    		return '退回'; //货物正处于退回发件人的途中；
    	}else{
    		return "";
    	}
    }

</script>
</body>
</html>
