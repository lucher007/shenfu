<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page   import= "com.sykj.shenfu.common.loginencryption.* "%>
<%@ page   import= "java.security.interfaces.RSAPrivateKey "%>
<%@ page   import= "java.security.interfaces.RSAPublicKey "%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="zh-cn"><head>
    <meta name="viewport" content="initial-scale=1, user-scalable=0, minimal-ui" charset="UTF-8">
    <title>tab加载多个数据</title>
    <link rel="stylesheet" href="<%=basePath%>/js/dist/dropload.css">
</head>
<body>
<div class="tab  my-header1-fixed">
    <a href="javascript:;" class="item cur">菜单一</a>
    <a href="javascript:;" class="item">菜单二</a>
</div>
<div class="content">
    <div class="lists"><a class="item opacity" href="#1"><img src="http://d8.yihaodianimg.com/N05/M03/D4/1E/CgQI0lYEBwWAQIvJAAFyostaOYY12501_80x80.jpg" alt=""><h3>Apple 苹果 iPhone 6s Plus (A1699) 16G 玫瑰金色 移动联通电信4G 全网通手机</h3><span class="date">2015-12-01</span></a><a class="item opacity" href="#2"><img src="http://d9.yihaodianimg.com/N07/M00/6B/E6/CgQIz1ZNQWeALuYuAASH8T5MJzY38001_80x80.jpg" alt=""><h3>Letv 乐视1S 乐1S 移动联通双4G 双卡双待 16GB 金</h3><span class="date">2015-12-02</span></a><a class="item opacity" href="#3"><img src="http://d8.yihaodianimg.com/N08/M0A/6B/E1/ChEi1lZhlgSAEES9AAIPUw3996808600_80x80.jpg" alt=""><h3>NOKIA/诺基亚 2610 nokia 2610 经典诺基亚直板手机 学生备用老人手机 诺基亚低端功能机</h3><span class="date">2015-12-03</span></a><a class="item opacity" href="#4"><img src="http://d6.yihaodianimg.com/N09/M00/78/AA/ChEi2VZpRZCAeyz-AAP2yE5HMTQ17001_80x80.jpg" alt=""><h3>华为 Mate8 NXT-TL00 3GB+32GB版 移动定制 月光银</h3><span class="date">2015-12-04</span></a><a class="item opacity" href="#5"><img src="http://d6.yihaodianimg.com/N07/M01/98/48/CgQI0FY3GNuAeKARAARTi1sTc0E99401_80x80.jpg" alt=""><h3>锤子 坚果 32GB 红色 移动联通4G手机 双卡双待</h3><span class="date">2015-12-05</span></a><a class="item opacity" href="#6"><img src="http://d9.yihaodianimg.com/N05/M0A/B6/5A/CgQI0lX45JWANx7NAANWUytnWfY51201_80x80.jpg" alt=""><h3>华为荣耀7i ATH-AL00 3G内存增强版 移动联通电信4G 沙滩金 双卡双待</h3><span class="date">2015-12-06</span></a><a class="item opacity" href="#7"><img src="http://d6.yihaodianimg.com/N08/M08/94/33/ChEi1FX_oHWAWJYUAAL4rKvikTE54401_80x80.jpg" alt=""><h3>小米 红米2A 增强版 白色 16G 移动4G手机 官网版 双卡双待</h3><span class="date">2015-12-07</span></a><a class="item opacity" href="#8"><img src="http://d8.yihaodianimg.com/N06/M06/8A/B9/CgQIzVQj87GAZ2-bAAMyoIZt8v863301_80x80.jpg" alt=""><h3>Samsung 三星 Galaxy Note4 N9108V 移动4G手机 幻影白</h3><span class="date">2015-12-08</span></a><a class="item opacity" href="#9"><img src="http://d8.yihaodianimg.com/N06/M04/DC/24/ChEbu1T4HVeASjwEAAGHikpYgyQ10901_80x80.jpg" alt=""><h3>HTC Desire 826T 魔幻蓝 移动4G手机 双卡双待</h3><span class="date">2015-12-09</span></a><a class="item opacity" href="#10"><img src="http://d8.yihaodianimg.com/N05/M05/BE/72/ChEbulTYbhyAY_DmAAGmsWjdGgY32601_80x80.jpg" alt=""><h3>华为 荣耀畅玩4X Che2-UL00 联通标配版4G手机 双卡双待双通 白色</h3><span class="date">2015-12-10</span></a><a class="item opacity" href="#11"><img src="http://d7.yihaodianimg.com/N05/M0B/89/7D/CgQI01Zb6yGAL2SWAAWXMCmZ7gk97001_80x80.jpg" alt=""><h3>vivo X6A 全网通4G手机 4GB+64GB 双卡双待 金色</h3><span class="date">2015-12-11</span></a><a class="item opacity" href="#12"><img src="http://d9.yihaodianimg.com/N10/M09/69/F7/ChEi3FZf6C-AIC74AAOQYliXK7g41100_80x80.jpg" alt=""><h3>金来 X7 5.5英寸大屏 移动联通双4G手机 智能指纹识别 双卡双待土豪金16GB非合约机官方标配</h3><span class="date">2015-12-12</span></a></div>
    <div class="lists"></div>
<div class="dropload-down"><div class="dropload-refresh">↑上拉加载更多</div></div></div>
<!-- jQuery1.7以上 或者 Zepto 二选一，不要同时都引用 -->
<script type="text/javascript" src="<%=basePath%>shopping/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/dist/dropload.min.js"></script>
<script>
$(function(){
    var itemIndex = 0;
    var tab1LoadEnd = false;
    var tab2LoadEnd = false;
    // tab
    $('.tab .item').on('click',function(){
        var $this = $(this);
        itemIndex = $this.index();
        $this.addClass('cur').siblings('.item').removeClass('cur');
        $('.lists').eq(itemIndex).show().siblings('.lists').hide();

        // 如果选中菜单一
        if(itemIndex == '0'){
            // 如果数据没有加载完
            if(!tab1LoadEnd){
                // 解锁
                dropload.unlock();
                dropload.noData(false);
            }else{
                // 锁定
                dropload.lock('down');
                dropload.noData();
            }
        // 如果选中菜单二
        }else if(itemIndex == '1'){
            if(!tab2LoadEnd){
                // 解锁
                dropload.unlock();
                dropload.noData(false);
            }else{
                // 锁定
                dropload.lock('down');
                dropload.noData();
            }
        }
        // 重置
        dropload.resetload();
    });

    var counter = 0;
    // 每页展示4个
    var num = 4;
    var pageStart = 0,pageEnd = 0;

    // dropload
    var dropload = $('.content').dropload({
        scrollArea : window,
        loadDownFn : function(me){
            // 加载菜单一的数据
            if(itemIndex == '0'){
                $.ajax({
                    type: 'GET',
                    url: 'getSon111',
                    dataType: 'json',
                    success: function(data){
                        var result = '';
                        counter++;
                        pageEnd = num * counter;
                        pageStart = pageEnd - num;

                        if(pageStart <= data.lists.length){
                            for(var i = pageStart; i < pageEnd; i++){
                                result +=   '<a class="item opacity" href="'+data.lists[i].link+'">'
                                                +'<img src="'+data.lists[i].pic+'" alt="">'
                                                +'<h3>'+data.lists[i].title+'</h3>'
                                                +'<span class="date">'+data.lists[i].date+'</span>'
                                            +'</a>';
                                if((i + 1) >= data.lists.length){
                                    // 数据加载完
                                    tab1LoadEnd = true;
                                    // 锁定
                                    me.lock();
                                    // 无数据
                                    me.noData();
                                    break;
                                }
                            }
                            // 为了测试，延迟1秒加载
                            setTimeout(function(){
                                $('.lists').eq(0).append(result);
                                // 每次数据加载完，必须重置
                                me.resetload();
                            },1000);
                        }
                    },
                    error: function(xhr, type){
                        //alert('Ajax error!');
                        // 即使加载出错，也得重置
                        me.resetload();
                    }
                });
            // 加载菜单二的数据
            }else if(itemIndex == '1'){
                $.ajax({
                    type: 'GET',
                    url: 'getSon111',
                    dataType: 'json',
                    success: function(data){
                        var result = '';
                        for(var i = 0; i < data.lists.length; i++){
                            result +=   '<a class="item opacity" href="'+data.lists[i].link+'">'
                                            +'<img src="'+data.lists[i].pic+'" alt="">'
                                            +'<h3>'+data.lists[i].title+'</h3>'
                                            +'<span class="date">'+data.lists[i].date+'</span>'
                                        +'</a>';
                        }
                        // 为了测试，延迟1秒加载
                        setTimeout(function(){
                            $('.lists').eq(1).append(result);
                            // 每次数据加载完，必须重置
                            me.resetload();
                        },1000);
                    },
                    error: function(xhr, type){
                        //alert('Ajax error!');
                        // 即使加载出错，也得重置
                        me.resetload();
                    }
                });
            }
        }
    });
});
</script>

</body></html>