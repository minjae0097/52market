<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래 채팅</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>중고거래 채팅</h2>
		<hr size="1" width="100%">
			<c:if test="${empty list}">
				<div>
				채팅목록이 없습니다.
				</div>
			</c:if>
			<c:if test="${!empty list}">
				<table>
				<c:forEach var="list" items="${list}">
				<tr>
					<td>${list.chatroom_num}</td>
					<td><a href="chatDetailProduct.do?chatroom_num=${list.chatroom_num}">${list.mem_nickname}님(${list.cnt})과 채팅하기</a></td>
				</tr>
				</c:forEach>
				</table>
			</c:if>
	</div>
</div>
</body>
</html>