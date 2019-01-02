<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0"/>

<link rel="stylesheet" type="text/css" href="style/easyui/easyui.css">
<link type="text/css" rel="stylesheet" href="style/upload/uploadpricture.css" >
<link rel="stylesheet" type="text/css" href="style/upload/wap.css" />

<script type="text/javascript" src="js/common/jquery.js"></script>
<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common/easyui-lang-zh_CN.js"></script>
	
<script src="style/upload/imgUp.js"></script>


<style type="text/css">

h2{
	margin: 0;
	padding: 0;
}
input.b{

font-size: 20px;
width:100%;
height:50px;
color: #fff;
background: #256ebb;
}


.default-img {
    width: 30%;
    height: width;
    
}
.ok{width:100%;position:fixed;bottom: 0px;}
            
.header{background:#226ab6;display:block;position:fixed; z-index: 200; font-size:16px;height:50px;line-height:50px;width: 100%;}

.header .back{position:absolute;padding:0 10px;height:28px;margin:10px 10px;line-height:28px;font-size:14px;background-color:#226ab6;border-radius:3px;width:30px;color:#fff}

.logo{color:#fff;font-size:20px;}

.top1 {
	display: block;
	width: 100%;
	margin-top: 50px;
	margin-bottom: 50px;
}
/*遮罩层样式*/
.mask{
	z-index: 500;
	display: none;
	position: fixed;
	top: 0px;
	left: 0px;
	width: 100%;
	height: 100%;
	background: rgba(0,0,0,.4);
}
.mask .mask-content{
	 width: 80%;
	 position: absolute;
	 top: 50%;
	 left: 10%;
	 background: white;
	 text-align: center;
}
.mask .mask-content .del-p{
	color: #555;
	height: 94px;
	line-height: 94px;
	font-size: 18px;
	border-bottom: 1px solid #D1D1D1;
}

.mask-content .check-p span{
	padding:5px 0px 5px 0px;
	width:49%;
	display:inline-block;
	text-align: center;
	color:#d4361d ;
	font-size: 18px;
}
.check-p .del-com{
	border-right: 1px solid #D1D1D1;
}

.fl {
	float: left;
}

.clear:after {
	content: '';
	display: block;
	clear: both;
}
</style>
</head>

<body>
<div class="header" >
	<div class="back" onclick="javascript:history.go(-1);"><h2>＜</h2></div>
    <div class="logo" align="center">上传照片</div>
</div>
<div class="top1" >
	<form id="clientAddForm" name="clientAddForm" class="itemForm"
		method="post" >
		<p >为了匹配合适的锁体，需要参考您家的门板数据</p>							
		<div >
			<p >例:门的厚度</p>
			<img src="style/upload/add.png" class="default-img">
			<p >请上传门的厚度</p>
			<div class="z_photo upimg-div clear" id="z_photo1">
				 <section class="z_file fl">
					<img src="style/upload/add.png" class="add-img">
					<input type="file" name="file1" id="file1" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" multiple />
				 </section>
		 	</div>

		</div>

		<div >
			<p >例:衬板长度</p>
			<img src="style/upload/add.png" class="default-img">
			<p >请上传衬板长度</p>
			<div class="z_photo upimg-div clear"  id="z_photo2">

				 <section class="z_file fl">
					<img src="style/upload/add.png" class="add-img">
					<input type="file" name="file2" id="file2" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp"  multiple/>
				 </section>
		 	</div>

		</div>

		<div >

			<p >例:衬板宽度</p>
			<img src="style/upload/add.png" class="default-img">
			<p >请上传衬板宽度</p>
			<div class="z_photo upimg-div clear" id="z_photo3">

				 <section class="z_file fl">
					<img src="style/upload/add.png" class="add-img">
					<input type="file" name="file3" id="file3" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp"  multiple/>
				 </section>
		 	</div>

		</div>



	</form>
	<form class="mask works-mask">
		<div class="mask-content">
			<p class="del-p ">您确定要删除作品图片吗？</p>
			<p class="check-p"><span class="del-com wsdel-ok">确定</span><span class="wsdel-no">取消</span></p>
		</div>
</form>

</div>
<div class="ok">
<input type="button" class="b" id="registsubmit" value="确认" onclick="UPLOAD.reg();"/>
</div>
</body>
<script type="text/javascript">
var ele=[];
var name="";
//提交表单
var UPLOAD = {
	param : {
		//系统的url
		surl : "<%=basePath%>client"
	},
	
	doSubmit : function() {

		//ajax的post方式提交表单
		//$("#itemAddForm").serialize()将表单序列号为key-value形式的字符串

        var xhr = new XMLHttpRequest();
        var formData = new FormData();

        for(var i=0, f; f=ele[i]; i++){
            formData.append('files', f);
            name=name+f.name+",";
        }
        formData.append("phone",'1234567');
        
         
        
        console.log('1',ele);
        console.log('2',formData);
        //alert(name);
        alert(ele.length);
		alert(formData);
		 $.ajax({  
	          url: "<%=basePath%>web/upload",  
	          type: 'POST',  
	          data: formData,  
	          async: false,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          success: function (returndata) {  
	              alert(returndata);  
	          },  
	          error: function (returndata) {  
	              alert(returndata);  
	          }  
	     });  
	},
	login : function() {
		location.href = "<%=basePath%>client/success";
		return false;
	},
	reg : function() {
			this.doSubmit();
	}
}; 

</script>
</html>
