<%@ include file="../common/common.jsp"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
</head>
<body>
<div class="mainbox">
	<div >
		<div >
			<fieldset class="layui-elem-field fieldset">
				<legend>用户管理</legend>
				<div class="layui-field-box">
					<div>
						<c:import url="search.jsp">
							<c:param name="action" value="${pageContext.request.contextPath}/user/list"/>
						</c:import>
					</div>
					<form class="layui-form">
						<table width="100%" class="layui-table" lay-size="sm">
							<colgroup>
								<col width="80">
							</colgroup>
							<thead>
							<tr>
								<th><input type="checkbox" lay-skin="primary" lay-filter="allChoose"/></th>
								<th>帐号</th>
								<th>角色</th>
								<th>邮箱</th>
								<th>手机</th>
								<th>最后登陆时间</th>
								<th>注册日期</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${datas }" var="obj">
								<tr>
									<td><input type="checkbox" lay-skin="primary" lay-filter="itemChoose" id="ids" name="ids"  value="${obj.id }"/></td>
									<td>${obj.username }</td>
									<td>
										<c:forEach items="${obj.roles}" var="role" varStatus="status">
											${role.name}<c:if test="${status.last eq false}">,</c:if></c:forEach>
									</td>
									<td>${obj.email }</td>
									<td>${obj.mobile }</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${obj.lastLoginTime }"  /></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${obj.createDate }"  /></td>
									<td>
										<c:if test="${obj.status ==0}">禁用</c:if>
										<c:if test="${obj.status ==1}">可用</c:if>
									</td>
									<td>
										<a href="javascript:resetPassword('${obj.id }');" class="layui-btn layui-btn-xs layui-btn-danger" target="main">重置密码</a>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</form>
				</div>
			</fieldset>
		</div>
	</div>
	<div class="operator">
		<c:import url="../common/button.jsp">
			<c:param name="create">${pageContext.request.contextPath }/user/create</c:param>
			<c:param name="modify">${pageContext.request.contextPath }/user/modify</c:param>
			<c:param name="delete">${pageContext.request.contextPath }/user/delete</c:param>
			<c:param name="deleteMsg">确认删除用户？</c:param>
		</c:import>
		<c:import url="../common/page.jsp">
			<c:param name="jump">true</c:param>
		</c:import>
	</div>
</div>
</body>
<script type="text/javascript">
    function resetPassword(id){
        location.href="${base}/user/resetPassword?id="+id;
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
    //手机适配
    function mobile(){
        $("body").css({"position":"absolute","left":"0px"});
        $(".layui-elem-field").css({"width":"100%"});
        var operator=$(".operator");
        $(".operator").remove();
        $(".layui-elem-field").append(operator);
    }
    if(!IsPC()){
        mobile();
    }
</script>
</html>