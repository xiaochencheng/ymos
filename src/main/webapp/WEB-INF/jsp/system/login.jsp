<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib uri="/WEB-INF/tlds/cc-fn.tld" prefix="fn2" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>os管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsandcss/js/login/css/demo.css" />
    <!--必要样式-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsandcss/js/login/css/component.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsandcss/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/jsandcss/js/layer/layer.js"></script>
    <style>
        .title{
            font-size: 1.75em;
            padding-left: 90px;
            height: 55px;
        }
        #username{
            color: #FFFFFF !important
        }
        #passwd{
            color: #FFFFFF !important;
            position:absolute;
            z-index:100;
        }
        .demo-1 .large-header {
            background-image: url('${pageContext.request.contextPath}/jsandcss/images/10.jpg');
        }
        .u_user{
            background-image: url('${pageContext.request.contextPath}/jsandcss/images/login_ico.png');
        }
        .us_uer{
            background-image: url('${pageContext.request.contextPath}/jsandcss/images/login_ico.png');
        }
        .submit{
            width: 330px;
            height: 46px;padding-top: 12px;
            color: #FFFFFF
        }
        #message{
            float:left;
        }
        /* 浏览器自动保存用户名和密码的背景颜色设置为透明 */
        input:-webkit-autofill , textarea:-webkit-autofill, select:-webkit-autofill {
            -webkit-text-fill-color: #ededed !important;
            -webkit-box-shadow: 0 0 0px 1000px transparent  inset !important;
            background-color:transparent;
            background-image: none;
            transition: background-color 50000s ease-in-out 0s;
        }
    </style>
    <script>
        function checkLogin() {
            var username = $('[name="username"]').val();
            var passwd = $('[name="passwd"]').val();
            if (username == "") {
                layer.tips('请您输入账号后再登陆', '#username', {
                    tips: [3, '#3595CC'],
                    time: 3000
                });
                return false;
            }
            if (passwd == "") {
                layer.tips('请您输入密码后再登陆', '#passwd', {
                    tips: [3, '#3595CC'],
                    time: 3000
                });
                return false;
            }
        }
        $(function(){
            $('#username').keyup(function(){
                $('#message').html('');
            })

            $('#passwd').keyup(function(){
                $('#message').html('');
            })
        })
    </script>
</head>
<body >
<div class="container demo-1">
    <div class="content">
        <div id="large-header" class="large-header">
            <canvas id="demo-canvas"></canvas>
            <div class="logo_box">
                <div class="title">os管理系统</div>
                <form onsubmit="return checkLogin()" id="loginForm"  action="${pageContext.request.contextPath}/login" method="post">
                    <div class="input_outer">
                        <span class="u_user"></span>
                        <input name="username" class="text" id="username"  type="text" placeholder="请输入账户" value='${form.username }'>
                        <div id="message">${message }</div>
                    </div>
                    <div class="input_outer">
                        <span class="us_uer"></span>
                        <input name="passwd" class="text"  id="passwd"  type="password" placeholder="请输入密码" value='${form.passwd }'>
                    </div>
                    <div class="mb2">
                        <button class="btn-block act-but submit" type="submit">登陆</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/jsandcss/js/login/js/TweenLite.min.js"></script>
<script src="${pageContext.request.contextPath}/jsandcss/js/login/js/EasePack.min.js"></script>
<script src="${pageContext.request.contextPath}/jsandcss/js/login/js/rAF.js"></script>
<script src="${pageContext.request.contextPath}/jsandcss/js/login/js/demo-1.js"></script>
</body>
</html>