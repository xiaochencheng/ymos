<%@ include file="../common/common.jsp"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <style>
        @media only screen and (max-width: 500px) {
            body{
                font-size: 1.0rem;
            }
            .layui-field-box{
                width: 400px;!important
            }
            .layui-elem-field{
                transform: scale(.8);
                position:absolute;
                top: -30px;
                left:-50px;
                overflow: hidden;
            }
        }
    </style>
</head>
<body>
<div>
    <c:import url="../common/errors.jsp"></c:import>
    <fieldset class="layui-elem-field fieldset">
        <legend>修改密码</legend>
        <div class="layui-field-box">
            <form class="layui-form layui-form-pane"   method="post">
                <div class="layui-form-item">
                    <label class="layui-form-label">当前密码:</label>
                    <div class="layui-input-inline">
                        <input type="password" id="password" name="password"  placeholder="请输入当前密码"  lay-verify="password"  class="layui-input create-input">
                        <label class="error" id="passwordLabel"></label>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">新密码:</label>
                    <div class="layui-input-inline">
                        <input type="password"  id="newPassword" name="newPassword" value="${newPassword}" placeholder="请输入新密码"  lay-verify="newPassword"  class="layui-input create-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">确认新密码:</label>
                    <div class="layui-input-inline">
                        <input type="password"  id="confirm_newPassword" name="confirm_newPassword" value="${newPassword}" placeholder="请输入确认新密码"  lay-verify="confirm_newPassword"  class="layui-input create-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">操作:</label>
                    <div class="layui-input-inline" style="margin-left: 10px;margin-top: 5px">
                        <button class="layui-btn  layui-btn-normal layui-btn-sm dw-dailog" id="updatePassword"  lay-submit="" lay-filter="updatePassword">
                            <i class="layui-icon">&#xe654;</i>保存
                        </button>
                        <button class="layui-btn  layui-btn-warm layui-btn-sm dw-dailog"  type="button" onclick="javascript:history.go(-1)">
                            <i class="layui-icon">&#x1006;</i>取消
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </fieldset>
</div>
</body>
<script type="text/javascript">
    $('#password').blur(function(){
        var password=$('#password').val();
        if(password.trim()!=""){
            $.post('${pageContext.request.contextPath}/user/checkPassword',{"password":password},function(reply){
                if(reply.flag && reply.prompt!=""){
                    $('#passwordLabel').html(reply.prompt);
                    $('#updatePassword').addClass("layui-btn-disabled");
                    $('#updatePassword').attr("disabled",true);
                }else if(reply.flag && reply.prompt==""){
                    $('#passwordLabel').html(reply.prompt);
                    $('#updatePassword').removeClass("layui-btn-disabled");
                    $('#updatePassword').attr("disabled",false);
                }else{
                    layer.msg(reply.prompt);
                }
            });
        }
    })

    layui.use('form', function(){
        var form = layui.form;
        var password='';
        var newPassword='';
        form.verify({
            password: function(value, item){
                if(value=='' && value.trim()==''){
                    return '当前密码不能为空';
                }
                password=value;
            },
            newPassword: function(value, item){
                if(value=='' && value.trim()==''){
                    return '新密码不能为空';
                }
                newPassword=value;
            },
            confirm_newPassword: function(value, item){
                if(value=='' && value.trim()==''){
                    return '确认新密码不能为空';
                }
                if(newPassword==password){
                    return '新密码不能与当前密码相同';
                }
                if(value!=newPassword){
                    return '两次密码不一致,请重新输入';
                }
            }
        });
        form.on('submit(updatePassword)', function (data) {
            $.post('${pageContext.request.contextPath}/user/updatePassword',{"newPassword":newPassword},function(reply){
                if(reply.flag){
                    layer.confirm('密码修改成功，请重新登陆！', { btn: ['确认'],icon: 1, title:'信息',cancel : function(index){
                            parent.location.href="${pageContext.request.contextPath }/logout";
                            layer.close(index);
                        }
                    }, function(index){
                        parent.location.href="${pageContext.request.contextPath }/logout";
                        layer.close(index);
                    });
                }
            });
            return false;
        });
    });

    $('#password').keyup(function(){
        $('#passwordLabel').html('');
    })

</script>
</html>