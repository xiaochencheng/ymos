<%@ include file="../common/common.jsp"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
                top: -10px;
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
            <legend>重置密码</legend>
            <div class="layui-field-box">
                <form class="layui-form"   method="post">
                    <div class="layui-form-item">
                        <label class="layui-form-label">新密码:</label>
                        <div class="layui-input-inline">
                        <input type="password" id="newPassword" name="newPassword"  placeholder="请输入新前密码"  lay-verify="newPassword"  class="layui-input create-input">
                         <label class="error" id="newPasswordLabel"></label>
                        </div>
                    </div> 
                    <div class="layui-form-item"> 
                       
                         <div class="layui-input-inline" style="margin-left: 110px;margin-top: 5px">
                        <button class="layui-btn  layui-btn-normal layui-btn-sm dw-dailog"   lay-submit="" lay-filter="newPassword">
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
	layui.use('form', function(){
		  var form = layui.form;
		  form.verify({
			  newPassword: function(value, item){ 
				  if(value=='' && value.trim()==''){
			  			return '新密码不能为空';
			  		}
			  }
		  })
		  form.on('submit(newPassword)', function (data) { 
			  var userId='${userId}';
			  var newPassword=$('#newPassword').val();
			   $.post('${pageContext.request.contextPath}/userProxy/resetPasswords',{"passwd":newPassword,"id":userId},function(reply){
					if(reply.flag && reply.prompt!=null){
						$('#newPasswordLabel').html(reply.prompt);
					}else if(reply.flag && reply.prompt==null){
						layer.msg("密码重置成功!",{time:800},function(){ 
							location.href="${pageContext.request.contextPath }/userProxy/list";
					     }) 
					}else{
						layer.msg('操作失败');
					}
				});
			   return false;
		  }); 
		
	});
	$('#newPassword').keyup(function(){
		$('#newPasswordLabel').html('');
	})
</script>
</html>