<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	.errors{
		color:red;
		font-size:1.25rem;
		padding-bottom: 10px;
	}
</style>
<ul id="errors" class="errors">
	<c:if test="${errors!=null}">
		<c:forEach var="error" items="${errors.allErrors}">
			<li>${error.defaultMessage}</li>
		</c:forEach>
	</c:if>
</ul>
<c:if test="${msg!=null}">
	<ul id="msg">
		<li>${msg}</li>
	</ul>
</c:if>