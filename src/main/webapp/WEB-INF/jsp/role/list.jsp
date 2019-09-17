 <%@ include file="../common/common.jsp"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
<div class="mainbox">    
    <div >
      <div >
        <fieldset class="layui-elem-field fieldset">
            <legend>角色管理</legend>
            <div class="layui-field-box">
				<div>
				   <c:import url="search.jsp">
						<c:param name="action" value="${pageContext.request.contextPath}role/list"/>
				   </c:import>
				</div>
			 <form class="layui-form">
			    <table style="width: 100%" class="layui-table" lay-size="sm">
			    <colgroup>
					 <col width="80">
				</colgroup> 
			      <thead>
			        <tr>
			          <th><input type="checkbox"  lay-skin="primary" lay-filter="allChoose"/></th>
			          <th>名称</th>
			          <th>描述</th>
			          <th>创建人</th>
			          <th>创建日期</th>
			        </tr>
			      </thead>
			      <tbody>
			       <c:forEach items="${datas}" var="obj">
			       		<tr>
			       		  <td><input type="checkbox" lay-skin="primary" lay-filter="itemChoose" id="ids" name="ids" value="${obj.id }"  /></td>
				          <td>${obj.name}</td>
				          <td>${obj.description}</td>
				          <td>${obj.username}</td>
				          <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${obj.createDate }"  /></td>
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
			 <c:param name="create">${pageContext.request.contextPath }/role/create</c:param>
			 <c:param name="modify">${pageContext.request.contextPath }/role/modify</c:param>
			 <c:param name="delete">${pageContext.request.contextPath }/role/delete</c:param>
			 <c:param name="deleteMsg">确认删除角色？</c:param>
		  </c:import>
		  <c:import url="../common/page.jsp">
			 <c:param name="jump">true</c:param>
		  </c:import>
	</div>
   </div>
</body>
<script>
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
        $("body").css({"position":"absolute",left:"0px"});
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