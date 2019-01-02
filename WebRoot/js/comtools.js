

/**
*页面跳转
*/
function goToURL(url) {
	window.location.href = url;
}
var bool1 = false;//全局变量存放文本框是否验证通过
var bool2 = false;//全局变量存放药材框是否验证通过
/**
 *新增页面项未输入提示
 */
function onBlurCheck(name) {
	//获取页面输入框
	var inputVal = $.trim($("input[name="+name+"]").val());
	//获取对应的提示span提示信息
	var spanVal = $("label[id^=" + name + "]");
	
 	//判断输入框是否填写
	if (inputVal == null || inputVal == "") {
		spanVal.attr("class","errico");
		return false;
	} else {
		spanVal.attr("class","okico");
		return true;
	}
}
 ////验证文本框是否是正数////
//onkeypress 
function isN(obj) {
	if (!obj.value.match(/^[1-9]?\\d*?\\.?\\d*?$/)) {
		obj.value = obj.t_value;
	} else {
		obj.t_value = obj.value;
	}
	if (obj.value.match(/^(?:[1-9]?\\d+(?:\\.\\d+)?)?$/)) {
		obj.o_value = obj.value;
	}
}
//onkeyup
function isU(obj) {
	if (!obj.value.match(/^[1-9]?\\d*?\\.?\\d*?$/)) {
		obj.value = obj.t_value;
	} else {
		obj.t_value = obj.value;
	}
	if (obj.value.match(/^(?:[1-9]?\\d+(?:\\.\\d+)?)?$/)) {
		obj.o_value = obj.value;
	}
	if (obj.value.match(/^\\.$/)) {
		obj.value = "";
	}
	if (obj.value.match(/^\\-$/)) {
		obj.value = "";
	}
	if (obj.value.match(/^00+/)) {
		obj.value = "";
	}
	if (obj.value.match(/^0\\.00/)) {
		obj.value = "";
	}
	if (obj.value.match(/^0[1-9]/)) {
		obj.value = "";
	}
}
///onblur
function isB(obj) {
	if (obj.value.match(/^0/)) {
		obj.value = "";
	}
	if (!obj.value.match(/^(?:[1-9]?\\d+(?:\\.\\d+)?|\\.\\d*?)?$/)) {
		obj.value = obj.o_value;
	} else {
		if (obj.value.match(/^\\.\\d+$/)) {
			obj.value = 0 + obj.value;
		}
		obj.o_value = obj.value;
	}
}

//////////////////////////////
//验证输入框是否为空
function isNullIpu(name)
{
	var inputVal = $.trim($("input[name="+name+"]").val());
	if(inputVal == null || inputVal == ""){
		return false;
	}else
	return true;
}

function isStrIpuabc(name)
{
	var inputVal = $.trim($("input[name="+name+"]").val());
	return /[\'\"\~\`\_\!\@\#\$\%\^\&\*\|\(\)\{\}\[\]\-\+\=\;\:\?\<\>\,\.\/\\]/.test(inputVal);
}
function checkload(name){
	var spanVal = $("label[id^="+name+"]");
	spanVal.attr("class","loadico");
	return false;
}
function checkerror(name){
	var spanVal = $("label[id^="+name+"]");
	spanVal.attr("class","errico");
	return false;
}
function checksuccess(name){
	var spanVal = $("label[id^="+name+"]");
	spanVal.attr("class","okico");
	return true;
}
////验证文本框是否是正数////
function isNatural(obj){

}
//////////////////////////////

////验证电子邮箱////
function isEmail(obj){
	if(!obj.value.match(/^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$/))obj.value=obj.o_value;
	obj.o_value=obj.value;
}
//////////////////////////////
//验证输入框是否为空
function isNullIpu(name)
{
	var inputVal = jQuery.trim($("input[name="+name+"]").val());
	if(inputVal == null || inputVal == ""){
		$("#"+name).focus();
		return false;
	}else{
		return true;
	}
}

//分包加、减、乘、除*********************************************

//加
function addtr(arg1,arg2){    
        var r1,r2,m;    
        try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}    
        try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}    
        m=Math.pow(10,Math.max(r1,r2));    
        return (arg1*m+arg2*m)/m;    
    }  
//减
function subtr(arg1,arg2){
	var r1,r2,m,n;
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	m=Math.pow(10,Math.max(r1,r2));
	n=(r1>=r2)?r1:r2;
	return ((arg1*m-arg2*m)/m).toFixed(n);
}
//乘 
function multiply(arg1,arg2){    
    arg1=String(arg1);var i=arg1.length-arg1.indexOf(".")-1;i=(i>=arg1.length)?0:i;    
    arg2=String(arg2);var j=arg2.length-arg2.indexOf(".")-1;j=(j>=arg2.length)?0:j;    
    return arg1.replace(".","")*arg2.replace(".","")/Math.pow(10,i+j);    
}
//除  
function division(arg1,arg2){    
    var t1=0,t2=0,r1,r2;    
    try{t1=arg1.toString().split(".")[1].length}catch(e){}    
    try{t2=arg2.toString().split(".")[1].length}catch(e){}    
    with(Math){    
        r1=Number(arg1.toString().replace(".",""));    
        r2=Number(arg2.toString().replace(".",""));     
        return (r1/r2)*pow(10,t2-t1);    
    }    
}


function openMap(){
	    
	    //alert('a');
	
		$("#lonlat").attr("disabled","disabled");
		//创建地址解析器
		var myGeo = new BMap.Geocoder();
		//解析详细地址
		myGeo.getPoint($("#registeraddress").val(),function(point){
			if(point){
				$("#longitude").val(point.lng);
				$("#latitude").val(point.lat);			
			}
			t1 = $.dialog({
			title:'标注所在区域',
			id:'lonlat',
			lock:true,
			padding:0,
			content: '<iframe frameborder="0" scrolling="no" name="tFrame" id="tFrame" width="600" height="400" src="'+"map/mapBaseChoice?ischoice="+Math.random()+'"></iframe>',
			cancelVal: "确定",
			cancel:function(){}
		});
		},$("#province").val());
	}
	
function closeMap(s){
			$("#lonlat").removeAttr("disabled");
			$("#longitude").val(s[1]);
			$("#latitude").val(s[2]);
}
	
/**
*打开市场楼层图
*/
var sa="";
var t1;
function openMarketMap(cpccorp){
	t1 = $.dialog({
		title:'绑定商铺位置',
		id:'lonlat',
		lock:true,
		padding:0,
		content: '<iframe frameborder="0" scrolling="no" name="MarketMapFrame" id="MarketMapFrame" width="748" height="441" src="'+"marketMap!findMarketMapByCpc.shtml?cpccorp="+cpccorp+"&ischoice="+Math.random()+'"></iframe>',
		button: [{
			id:'bind',
	        name: '开始绑定',
	        callback: function () {
	        	bindButton("1");
				return false;
	        },
	        focus: true
	    },{
	        name: '确定',
	        callback: function () {
	        	if(sa.length>0){
					$("#partion").val(sa[0]);
					$("#coordinate").val(sa[1]);
				}
	        },
	        focus: true
	    },{
	        name: '市场全景图',
	        callback: function () {
		        $.dialog({
					title:'市场全景图',
					id:'panorama',
					lock:true,
					padding:0,
					content: '<iframe frameborder="0" scrolling="no" name="tFrame" id="tFrame" width="748" height="500" src="'+"marketMap!findFloorByCpc.shtml?cpccorp="+cpccorp+"&ischoice="+Math.random()+'"></iframe>',
					parent:this,
					max:false,
					min:false
				});
				return false;
	        },
	        focus: true
	    }]
	});
}

function bindButton(str){
	var obj = $(".ui_buttons input").eq(0);
	if(obj.val().indexOf("开始")>-1&&str!=""){
		obj.val('取消绑定');
		$("#MarketMapFrame")[0].contentWindow.bindPoint();
	}else{
		obj.val('开始绑定');
		$("#MarketMapFrame")[0].contentWindow.unBindPoint();
	}
}
/**
*关闭市场楼层图
*/
function closeMarketMap(s){
	sa=s;
}



//Textarea-光标定位到最后
function locateTextarea(obj){ 
	var aCtrl = document.getElementById(obj); 
	var   o=aCtrl.createTextRange();    
	o.move("character",aCtrl.value.length);    
	o.select();    		
} 

function subTextarea(obj,maxLen){
	var flag=false;  
    var curLength=$("#"+obj).val().length;   
    if(curLength>maxLen){  
        //var num=$("#"+obj).val().substr(0,maxLen);  
        //$("#"+obj).val(num); 
        flag=true;  
        //$.dialog.tips('输入内容不能超过'+maxLen+'个字符!',2,'alert.gif');  
        //定位光标        
        //locateTextarea(obj); 
    } 
    return flag; 
}  
