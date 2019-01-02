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
<link type="text/css" rel="stylesheet" href="picUpload/jquery-ui/jquery-ui.min.css" media="screen" />
<link type="text/css" rel="stylesheet" href="picUpload/js/jquery.ui.plupload/css/jquery.ui.plupload.css" media="screen" />
<style type="text/css">
body{background:#fff;}
#uploader{max-width:800px;margin:50px auto;}
</style>
<!--[if lte IE 7]>
    <style>
        #main-menu {
            position: absolute;
            position: fixed;	
        }
        
        .menu-item {
            float: left;	
        }
        
        .span4 {
            float: left;	
        }
        
        .clearfix {
            clear: both;	
        }
        
        .btn {
            zoom: 1;
        }
    </style>
<![endif]-->
<!--[if lt IE 9]>
<script src="picUpload/js/html5shiv.js"></script>
<![endif]-->
</head>
<body>
		<div class="form-box">
			<form method="post"  enctype="multipart/form-data" id="addForm" name="addForm" >
			<div class="fb-con">
				<div style="width:750px; height:300px; margin:10px auto;"> 
					<span class="red" id="msg"></span>
			        <div id="uploader" style="margin:10px auto;">  
			            <p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者不支持 HTML5</p>  
			        </div> 
			        <input id="taskid" name="taskid" type="hidden" value="${handdrawing.taskid}"/> 
				      <cite><input type="button" class="btn-back" value="返回" onclick="goback();" /></cite>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/common/jquery.js"></script>
	<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
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
	
	$(function () {
	   //初始化文件上传插件
	   plupload();
       var returninfo = '${handdrawing.returninfo}';
       if (returninfo != '') {
        	$.dialog.tips(returninfo, 1, 'alert.gif');
       }
    });
    
    //加载上传图片插件
    function plupload(){
     	var taskid = $("#taskid").val();
     	
		$("#uploader").plupload({
			runtimes : 'html5,flash,silverlight,html4',//上传插件初始化选用哪种方式的优先级顺序，如果第一个初始化失败就走第二个，依次类推
			url : '<%=basePath%>handdrawing/saveHanddrawingInfo',
			 // 参数  
            multipart_params: {	
            					'taskid': taskid, 
                              },
            file_data_name: 'uploadfile',
			//max_file_size : '5m',//最大文件大小
			//max_file_count: 3,//最大文件数量
			resize : {//按比例生成缩略图
				width : 250, 
				height : 300, 
				quality : 90,
				crop: true 
			},
// 			filters : [
// 				{title : "图片文件", extensions : "jpg,gif,png"}//限制上传文件格式
// 			],
			rename: true,
			sortable: true,
			dragdrop: true,
			init:{
				'Error':function(up, err) {
					$("#msg").html(err);
				},
				'FileUploaded': function(uploader,file,responseObject){ 
				    var msg = file.name + " 已经上传成功！";
				    $('#msg').html(msg);
                },
				'UploadComplete': function(up,files){ 
					//$('picspan').innerHTML="您选择的文件已经全部上传，总计共"+files.length+"个文件";
                },
			},
			views: {
				list: true,
				thumbs: true, // Show thumbs
				active: 'thumbs'
			},
			flash_swf_url : 'picUpload/js/Moxie.swf',
			silverlight_xap_url : 'picUpload/js/Moxie.xap'
		});

	}
    
</script>
</body>
</html>
