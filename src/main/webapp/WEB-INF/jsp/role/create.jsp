<%@ include file="../common/common.jsp"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
            top: -40px;
            left:-50px;
            overflow: hidden;
        }
    }
</style>
<body>
<div>
<c:import url="../common/errors.jsp"></c:import>
        <fieldset class="layui-elem-field">
            <legend>${empty form.id?'新增角色':'修改角色'}</legend>
            <div class="layui-field-box">
                <form class="layui-form layui-form-pane" name="myform" id="roleForm" action="${pageContext.request.contextPath }/role/save?queryStr${queryStr}" method="post">
                	<input type="hidden" value="${form.id }" name="id">
                    <div class="layui-form-item">
                        <label class="layui-form-label">角色名称:</label>
                        <div class="layui-input-inline">
                        <input type="text" name="name" id="name" value="${form.name}"  placeholder="请输入角色名称"  lay-verify="name"  class="layui-input create-input">
                        <label class="error" id="nameLabel"></label>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">数据权限:</label>
                        <div class="layui-input-inline">
                        <input type="text" name="sql" id="sql" value="${form.sql}"  placeholder="请输入数据权限"  class="layui-input create-input">
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text">
                        <label class="layui-form-label" id="create-textarea">描述:</label>
                        <div class="layui-input-block">
                        <textarea name="description" id="description" class="layui-textarea">${form.description}</textarea>
                        </div>
                    </div>  
                    <div class="layui-form-item"> 
                        <label class="layui-form-label">操作:</label>
                         <div class="layui-input-inline" id="create-button">
                        <button class="layui-btn  layui-btn-normal layui-btn-sm dw-dailog" id="btnId"  lay-submit="">
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
<script>
var id='${form.id}';
//当鼠标失去焦点的时候，判断用户名是否存在
$('#name').blur(function(){
	 var name=$('#name').val();
	 if(name.trim()!=''){
		 $.post('${pageContext.request.contextPath}/role/checkName',{'name':name,"id":id},function(reply){
			 if(reply.flag && reply.prompt!=""){
				 	$('#nameLabel').html(reply.prompt);
					$('#btnId').addClass("layui-btn-disabled"); 
					$('#btnId').attr("disabled",true);
			 }else if(reply.flag && reply.prompt==""){
					$('#nameLabel').html(reply.prompt);
					$('#btnId').removeClass("layui-btn-disabled"); 
					$('#btnId').attr("disabled",false);
			 }else{
					layer.msg(reply.prompt);
			 }
		 })
	 }
})
layui.use('form', function(){
  var form = layui.form;
  form.verify({
	  name: function(value, item){ 
		  if(value=='' && value.trim()==''){
	  			return '角色名不能为空';
	  		}
	  }
	});  
});

</script>