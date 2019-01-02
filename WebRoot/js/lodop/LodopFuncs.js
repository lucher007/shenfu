﻿var CreatedOKLodop7766=null;

//====判断是否需要安装CLodop云打印服务器:====
function needCLodop(){
    try{
	var ua=navigator.userAgent;
	if (ua.match(/Windows\sPhone/i) !=null) return true;
	if (ua.match(/iPhone|iPod/i) != null) return true;
	if (ua.match(/Android/i) != null) return true;
	if (ua.match(/Edge\D?\d+/i) != null) return true;
	if (ua.match(/QQBrowser/i) != null) return false;
	var verTrident=ua.match(/Trident\D?\d+/i);
	var verIE=ua.match(/MSIE\D?\d+/i);
	var verOPR=ua.match(/OPR\D?\d+/i);
	var verFF=ua.match(/Firefox\D?\d+/i);
	var x64=ua.match(/x64/i);
	if ((verTrident==null)&&(verIE==null)&&(x64!==null)) 
		return true; else
	if ( verFF !== null) {
		verFF = verFF[0].match(/\d+/);
		if ( verFF[0] >= 42 ) return true;
	} else 
	if ( verOPR !== null) {
		verOPR = verOPR[0].match(/\d+/);
		if ( verOPR[0] >= 32 ) return true;
	} else 
	if ((verTrident==null)&&(verIE==null)) {
		var verChrome=ua.match(/Chrome\D?\d+/i);		
		if ( verChrome !== null ) {
			verChrome = verChrome[0].match(/\d+/);
			if (verChrome[0]>=42) return true;
		};
	};
        return false;
    } catch(err) {return true;};
};

//====页面引用CLodop云打印必须的JS文件：====
if (needCLodop()) {
	var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement;
	var oscript = document.createElement("script");
	oscript.src ="http://localhost:8000/CLodopfuncs.js?priority=1";
	head.insertBefore( oscript,head.firstChild );
	//本机云打印的后补端口8001：
	oscript = document.createElement("script");
	oscript.src ="http://localhost:8001/CLodopfuncs.js?priority=2";
	head.insertBefore( oscript,head.firstChild );
};

//====获取LODOP对象的主过程：====



function getLodop(oOBJECT,oEMBED){
	var realpath = 'print/printPluginInstall';
	var strHtmInstall="<br><font color='#FF00FF'>"+'打印控件未安装!点击这里'+"<a href='" + realpath + "?plugin_version=install_lodop32.exe" + "' target='_self'>"+'执行安装'+"</a>,"+'然后请刷新页面或重新进入'+"</font>";
    var strHtmUpdate="<br><font color='#FF00FF'>"+'打印控件需要升级!点击这里'+"<a href='" + realpath + "?plugin_version=install_lodop32.exe" + "' target='_self'>"+'执行升级'+"</a>,"+'然后请刷新页面或重新进入'+"</font>";
    var strHtm64_Install="<br><font color='#FF00FF'>"+'打印控件未安装!点击这里'+"<a href='" + realpath + "?plugin_version=install_lodop64.exe" + "' target='_self'>"+'执行安装'+"</a>,"+'然后请刷新页面或重新进入'+"</font>";
    var strHtm64_Update="<br><font color='#FF00FF'>"+'打印控件需要升级!点击这里'+"<a href='" + realpath + "?plugin_version=install_lodop64.exe" + "' target='_self'>"+'执行升级'+"</a>,"+'然后请刷新页面或重新进入'+"</font>";
    var strHtmFireFox="<br><br><font color='#FF00FF'>（请先卸载旧版本控件）</font>";
    var strHtmChrome="<br><br><font color='#FF00FF'>(请重新安装）</font>";
    var strCLodopInstall="<br><font color='#FF00FF'>"+'打印控件未安装!点击这里'+"<a href='" + realpath + "?plugin_version=CLodop_Setup_for_Win32NT.exe" + "' target='_self'>"+'执行安装'+"</a>,"+'然后请刷新页面或重新进入'+"</font>";
    var strCLodopUpdate="<br><font color='#FF00FF'>"+'打印控件需要升级!点击这里'+"<a href='" + realpath + "?plugin_version=CLodop_Setup_for_Win32NT.exe" + "' target='_self'>"+'执行升级'+"</a>,"+'然后请刷新页面或重新进入'+"</font>";
    var LODOP;
    try{
        var isIE = (navigator.userAgent.indexOf('MSIE')>=0) || (navigator.userAgent.indexOf('Trident')>=0);
        if (needCLodop()) {
            try{ LODOP=getCLodop();} catch(err) {};
	    if (!LODOP && document.readyState!=="complete") {alert('打印控件没准备好，请稍后再试'); return;};
            if (!LODOP) {
		 if (isIE) document.write(strCLodopInstall); else
		 document.documentElement.innerHTML=strCLodopInstall+document.documentElement.innerHTML;
                 return;
            } else {

	         if (CLODOP.CVERSION<"2.0.6.8") { 
			if (isIE) document.write(strCLodopUpdate); else
			document.documentElement.innerHTML=strCLodopUpdate+document.documentElement.innerHTML;
		 };
		 if (oEMBED && oEMBED.parentNode) oEMBED.parentNode.removeChild(oEMBED);
		 if (oOBJECT && oOBJECT.parentNode) oOBJECT.parentNode.removeChild(oOBJECT);	
	    };
        } else {
            var is64IE  = isIE && (navigator.userAgent.indexOf('x64')>=0);
            //=====如果页面有Lodop就直接使用，没有则新建:==========
            if (oOBJECT!=undefined || oEMBED!=undefined) {
                if (isIE) LODOP=oOBJECT; else  LODOP=oEMBED;
            } else if (CreatedOKLodop7766==null){
                LODOP=document.createElement("object");
                LODOP.setAttribute("width",0);
                LODOP.setAttribute("height",0);
                LODOP.setAttribute("style","position:absolute;left:0px;top:-100px;width:0px;height:0px;");
                if (isIE) LODOP.setAttribute("classid","clsid:2105C259-1E0C-4534-8141-A753534CB4CA");
                else LODOP.setAttribute("type","application/x-print-lodop");
                document.documentElement.appendChild(LODOP);
                CreatedOKLodop7766=LODOP;
             } else LODOP=CreatedOKLodop7766;
            //=====Lodop插件未安装时提示下载地址:==========
            if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
                 if (navigator.userAgent.indexOf('Chrome')>=0)
                     document.documentElement.innerHTML=strHtmChrome+document.documentElement.innerHTML;
                 if (navigator.userAgent.indexOf('Firefox')>=0)
                     document.documentElement.innerHTML=strHtmFireFox+document.documentElement.innerHTML;
                 if (is64IE) document.write(strHtm64_Install); else
                 if (isIE)   document.write(strHtmInstall);    else
                     document.documentElement.innerHTML=strHtmInstall+document.documentElement.innerHTML;
                 return LODOP;
            };
        };
        if (LODOP.VERSION<"6.2.0.8") {
            if (needCLodop())
            document.documentElement.innerHTML=strCLodopUpdate+document.documentElement.innerHTML; else
            if (is64IE) document.write(strHtm64_Update); else
            if (isIE) document.write(strHtmUpdate); else
            document.documentElement.innerHTML=strHtmUpdate+document.documentElement.innerHTML;
            return LODOP;
        };
        //===如下空白位置适合调用统一功能(如注册语句、语言选择等):===

        //===========================================================
        return LODOP;
    } catch(err) {alert('加载Lodop打印控件出错'+":"+err);};
};
