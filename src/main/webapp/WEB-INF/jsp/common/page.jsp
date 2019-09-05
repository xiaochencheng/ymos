<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	a{height: 19px;}
	span input{height: 19px;}
	#pageNum{width: 20px;}
</style>
<c:if test="${paginator.hasData }">
	<form id="paginator_form" method="post" style="display: none;">
		<c:forEach items="${paginator.query.qstrs }" var="qstrs">
			<input name="${qstrs.name }" value="${qstrs.value }" type="hidden"/>
		</c:forEach>
	</form>
	<div  id="paginator" class="zhsppagination">
		<ul>
			<c:if test="${paginator.needTotal}">
				<li><span>共 <em>${paginator.total}</em> 条/${paginator.totalPage}&nbsp;页</span></li>
			</c:if>
			<c:choose>
				<c:when test="${paginator.hasPrePage}">
					<li><a href="javascript:gotoPage(${paginator.page-1})">上一页</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:void(0)"  class="layui-disabled">上一页</a></li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${paginator.page <=5 }">
					<c:forEach var="curPage" begin="1" end="${paginator.page-1}">
						<li><a href="javascript:gotoPage(${curPage})">${curPage}</a></li>
					</c:forEach>
					<li ><a href="javascript:void(0)" class="active">${paginator.page}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:gotoPage(1)">1</a></li>
					<li><a href="javascript:gotoPage(2)">2</a></li>
					<li><a href="javascript:void(0)">...</a></li>
					<c:forEach var="curPage" begin="${paginator.page-2}" end="${paginator.page-1}">
						<li><a href="javascript:gotoPage(${curPage})">${curPage}</a></li>
					</c:forEach>
					<li ><a href="javascript:void(0)" class="active">${paginator.page}</a></li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${paginator.hasNextPage}">
					<li><a href="javascript:gotoPage(${paginator.page+1})">${paginator.page+1}</a></li>
					<li><a href="javascript:void(0)">...</a></li>
					<li><a href="javascript:gotoPage(${paginator.page+1})">下一页</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:void(0)"  class="layui-disabled">下一页</a></li>
				</c:otherwise>
			</c:choose>
			<c:if test="${not empty param.jump}">
				<li>
				<span>
					<input id="pageNum" type="text" value="${paginator.page}">
				</span>
				</li>
				<li>
					<a href="javascript:var pageNum=document.getElementById('pageNum').value;if(pageNum!=''){gotoPage(pageNum)}">Go!</a>
				</li>
			</c:if>
		</ul>
	</div>
	<script>
        $(".paginator").css({"margin-left":"0rem"});
        var paginator_form=document.getElementById("paginator_form");
        function gotoPage(page){
            paginator_form.action="${pageContext.request.contextPath}/${paginator.path}${param.noSlash!=null?'':'/'}"+page;
            paginator_form.submit();
        }
	</script>
</c:if>