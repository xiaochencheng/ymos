 <%@ include file="../common/common.jsp"%>
 <%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" href="${pageContext.request.contextPath}/jsandcss/layui/layui/css/formSelects-v4.css">
 <style>
.layui-edge{ left: 267px;} 
.xm-select-parent{width: 290px;}
.xm-select-title{width: 288px;}
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
        top: -40px;
        left:-50px;
        overflow: hidden;
    }
}
</style>
<div> 
<c:import url="../common/errors.jsp"></c:import>
        <fieldset class="layui-elem-field">
            <legend>
   			<c:if test="${empty token }">${empty form.id?'新增用户':'修改用户'}</c:if>
            <c:if test="${not empty token }">基本资料</c:if>
            </legend>
            <div class="layui-field-box">
                <form class="layui-form layui-form-pane" action="${pageContext.request.contextPath }/userProxy/save?queryStr${queryStr}" id="userForm" method="post">
                <input type="hidden" value="${form.id }" name="id">
                   <div class="layui-form-item">
                        <label class="layui-form-label">账号:</label>
                        <div class="layui-input-inline">
                        <input type="text" name="username" id="username" value="${form.username}" lay-verify="username"  placeholder="请输入账号名称"  class="layui-input create-input">
                        <label  class="error" id="usernameLabel"></label>
                        </div>
                    </div>
                    <div class="layui-form-item password">
                        <label class="layui-form-label">密码:</label>
                        <div class="layui-input-inline">
                        <input type="text" name="passwd" id="passwd"  value="${form.passwd}" lay-verify="passwd"   placeholder="请输入密码"  class="layui-input create-input" >
                        <label  class="error" id="passwdLabel"></label>
                        </div>
                    </div> 
                   <div class="layui-form-item">
                        <label class="layui-form-label">邮箱:</label> 
                        <div class="layui-input-inline">
                        <input type="text" name="email"  value="${form.email }"  placeholder="请输入邮箱" lay-verify="email" class="layui-input create-input" >
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">手机:</label>
                        <div class="layui-input-inline">
                        <input type="text" name="mobile" id="mobile"  value="${form.mobile }"  placeholder="请输入手机号码" lay-verify="mobile"  class="layui-input create-input" >
                        </div>
                    </div>
                   <div class="layui-form-item">
                        <label class="layui-form-label">性别:</label>
                        <div class="layui-input-block create-radio"  id="gender">
                        <input type="radio" name="gender"  title="女"  value="0" <c:if test="${form.gender eq 0}"> checked</c:if> checked>
                        <input type="radio" name="gender"  title="男" value="1" <c:if test="${form.gender eq 1}"> checked</c:if> >
                        <input type="radio" name="gender"  title="保密" value="2" <c:if test="${form.gender eq 2}"> checked</c:if>>
                        </div>
                    </div>  
                     <div class="layui-form-item">
                        <label class="layui-form-label">状态:</label>
                        <div class="layui-input-block create-radio"   id="status">
                        <input type="radio" name="status" value="0" title="禁用"  <c:if test="${form.status eq 0}"> checked</c:if> checked>
                        <input type="radio" name="status" value="1" title="可用"  <c:if test="${form.status eq 1}"> checked</c:if>>
                        </div>
                    </div> 
                     <div class="layui-form-item"> 
                        <label class="layui-form-label">操作:</label>
                         <div class="layui-input-inline" id="create-button">
                        <button class="layui-btn  layui-btn-normal layui-btn-sm dw-dailog" id="btnId"  lay-submit="" lay-filter="userForm">
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
 <script>
 var token="${token}";
 var isProxyChannel="${isProxyChannel}";
 if(token!="" || isProxyChannel!=""){
	 $('#role').prop("disabled",true);
 }
 layui.config({ 
     base: '${pageContext.request.contextPath}/jsandcss/layui/layui/'
	    }).extend({
	        formSelects: 'formSelects-v4'
	    });
 var id='${form.id}';
 if(id!=""){
	 $('#username').prop("disabled",true);
	 $('#username').addClass('layui-disabled'); 
 }
 //当鼠标失去焦点的时候，判断用户名是否存在
 <%--$('#username').blur(function(){--%>
	 <%--var username=$('#username').val();--%>
	 <%--if(username.trim()!=''){--%>
		 <%--$.post('${pageContext.request.contextPath}/user/checkUsername',{'username':username,"id":id},function(reply){--%>
			 <%--if(reply.flag && reply.prompt!=""){--%>
				 	<%--$('#usernameLabel').html(reply.prompt);--%>
					<%--$('#btnId').addClass("layui-btn-disabled");--%>
					<%--$('#btnId').attr("disabled",true);--%>
			 <%--}else if(reply.flag && reply.prompt==""){--%>
					<%--$('#usernameLabel').html(reply.prompt);--%>
					<%--$('#btnId').removeClass("layui-btn-disabled");--%>
					<%--$('#btnId').attr("disabled",false);--%>
			 <%--}else{--%>
					<%--layer.msg(reply.prompt);--%>
			 <%--}--%>
		 <%--})--%>
	 <%--}--%>
 <%--})--%>

 
 
 //如果是修改操作，密码输入框就隐藏
 if(id!=''){
	 $('.password').hide();
 }
layui.use('form', function(){
  var form = layui.form;
  var username='';
  form.verify({
	  username: function(value, item){ //value：表单的值、item：表单的DOM对象
		  if(value=='' && value.trim()==''){
	  			return '账号不能为空';
	  		}
	  		username=value;
	  },passwd:function(value, item){ 
		  if(value=='' && value.trim()==''){
	  			return '密码不能为空';
	  		}
	  	  if(value==username){ 
	  		 return '密码不能与帐号相同';
	  	  }
	  },email:function(value, item){
		  if(value.trim()!='' && !/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(value)){
			  return '邮箱格式不正确';
		  }
	  } ,mobile:function(value, item){
		  if(value.trim()!='' && !/^1\d{10}$/.test(value)){
			  return '请输入正确的手机号';
		  }
	  },role:function(value, item){ 
		  if(value==''){
	  			return '至少选择一个角色';
	  		}
	  }  
	});   
  $('#passwd').keyup(function(){
	  $('#passwdLabel').html('');
  })
   $('#username').keyup(function(){
	  $('#usernameLabel').html('');
  })
});

</script>
