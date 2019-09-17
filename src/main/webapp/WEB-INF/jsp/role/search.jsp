<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <table id="table">
    <tr>
      <td >
          <form class="layui-form" action="${pageContext.request.contextPath }/role/list" method="post">
			 	 <div class="layui-inline">
					<div class="search-name">角色名称:</div>
					<div class="layui-input-inline">
					  <input type="text"  class="layui-input" name="roleName" value="${param.roleName }" placeholder="请输入角色名称">
					</div>
				 </div>
			     <div class="layui-inline">
					<div class="layui-input-inline">
					  <button class="layui-btn layui-btn-normal layui-btn-sm" type="submit">
					  <i class="layui-icon">&#xe615;</i>查询</button>
					</div>
				 </div>
		   </form>
      </td>
    </tr>
  </table>
