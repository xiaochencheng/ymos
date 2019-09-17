<%@ include file="common/common.jsp"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>os后台管理系统</title>
    <meta charset="utf-8">
    <meta  name="renderer" content="webkit">

    <style type="text/css">
        .mobileMenu{
            display: none;
        }
        @media only screen and (min-width: 100px) and (max-width: 640px) {
            .mobileMenu{
                display: block;
                position: absolute;
                top:0;
                left: 0px;
            }
            .layui-logo{
                display: none;
            }
            .layui-nav-img{
                display: none;
            }

        }
        .layui-layout-right{
            position:absolute;
            right:0;
        }

    </style>
</head>
<script type="text/javascript">
    function setIframeHeight(iframe) {
        if (iframe) {
            $('#Main').attr('height',600);
            var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
            if (iframeWin.document.body) {
                iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
            }
        }
    };
    window.onload=function () {
        setIframeHeight(document.getElementById('iframe'));
    }

</script>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"><a href="${pageContext.request.contextPath}/home"><img src="${pageContext.request.contextPath}/jsandcss/images/ous.jpg" width="180"/></a></div>
        <ul class="layui-nav mobileMenu">
            <li class="layui-nav-item slide-sidebar" lay-unselect>
                <a href="javascript:toggleMenu();" class="icon-font" id="left"> <i class="layui-icon" style="font-size: 25px;" title="侧边伸缩">&#xe668;</i></a>
                <a href="javascript:toggleMenu();" class="icon-font" id="right"><i class="layui-icon layui-icon-shrink-right" style="font-size: 25px;" title="侧边伸缩">&#xe668;</i></a>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="${pageContext.request.contextPath}/jsandcss/images/people.png" class="layui-nav-img">
                    ${user}
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="${pageContext.request.contextPath }/user/information" target="main">基本资料</a></dd>
                    <dd><a href="${pageContext.request.contextPath }/user/updatePassword" target="main">修改密码</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/logout">退出</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black" width="20%" top="60px" overflow-x="hidden" position="absolute" left="0" top="0" bottom="0">
        <div class="layui-side-scroll" width="100%" height="100%" overflow-x=hidden position="relative">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree"  lay-filter="test" width="100%" padding="0px">
                <c:forEach items="${ datas['0'] }" var="menu">
                    <li class="layui-nav-item">
                        <a class="" href="javascript:;">${menu.name }</a>
                        <c:forEach items="${datas[menu.id]}" var="sub_menu">
                            <dl class="layui-nav-child home-child">
                                <dd class="loca"><a href="${pageContext.request.contextPath}${sub_menu.url}" target="main">${sub_menu.name }</a></dd>
                            </dl>
                        </c:forEach>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <div id="content">
            <div class="main_box">
                <iframe  onload="setIframeHeight(this)"  name="main" id="Main"  frameborder="no" scrolling="auto"  width="100%" height="806"></iframe>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var stone="stone";
    window.onload = function () {
        setIframeHeight(document.getElementById('external-frame'));
        $('#right').hide();
        $('.layui-layout-left span.layui-nav-bar').hide();//隐藏侧边伸缩图标的下划线
    }
    $(function(){
        $(".layui-nav-tree").children("li:first").addClass('layui-nav-itemed');//展开第一个垂直菜单
        $(".home-child").children("dd:first").addClass('layui-this');//第一个垂直菜单下的第一个子菜单被选中
        var firstA=$(".home-child").children("dd:first").children("a:first");
        firstA.attr("id", "firstAId");
        document.getElementById("firstAId").click();//默认打开第一个垂直菜单下的第一个子菜单的链接
    })
    var flag=false;
    function toggleMenu(){
        if(flag){
            $('#left').hide();
            $('#right').show();
            $('.layui-body').animate({left: '0px'});
            $('.layui-side').animate({width: '0px'});
            flag=false;
        }else{

            $('#right').hide();
            $('#left').show();
            $('.layui-body').animate({left: '20%',width:"80%",position:"relative"});
            flag=true;
        }
    }
    function IsPC() {
        var userAgentInfo = navigator.userAgent;
        var Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
        var flag = true;
        for (var v = 0; v < Agents.length; v++) {
            if (userAgentInfo.indexOf(Agents[v]) > 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }
    //默认关闭菜单栏
    // if(!IsPC()){
    //     $(".loca").click(function(){
    //         autoCloseMenu();
    //     });    }else{
    //
    // }
    //选择页面时  自动关闭菜单栏
    function autoCloseMenu(){
        toggleMenu();
        var location=$(this).attr("tag");
        console.log(location);
    }

</script>
</body>
</html>