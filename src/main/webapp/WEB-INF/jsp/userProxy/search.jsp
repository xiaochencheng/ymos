<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	.layui-input{width: 182px;}
</style>
    <table id="table">
    <tr>  
      <td >
          	<form class="layui-form" action="${pageContext.request.contextPath }/userProxy/list" method="post">
					<div class="layui-inline">
			            <div class="search-name" style="width: 40px">账号:</div>
			            <div class="layui-input-inline search-input-inline">
			              <input type="text" class="layui-input search-input" name="username" value="${param.username }" placeholder="请输入账号名称">
			            </div>
			        </div>
					<div class="layui-inline">
			            <div class="search-name" style="width: 40px">状态:</div>
			            <div class="layui-input-inline search-input-inline"> 
			            <select name="status" id="status" lay-search> 
                            <option value="">请选择用户状态</option>
					         <option value="0">禁用</option>
					        <option value="1">可用</option>
                        </select>  
         			   </div>
         			</div>
					 <div class="layui-inline">
						<div class="layui-input-inline search-input-inline">
						  <button class="layui-btn layui-btn-normal layui-btn-sm" type="submit">
						  <i class="layui-icon">&#xe615;</i>查询</button>
						</div>
					 </div>
		       </form>
        </td>
    </tr>
  </table>
 <script type="text/javascript">
	var status="${param.status}";
	$('#status').find("option[value='"+status+"']").prop("selected","selected");
</script>