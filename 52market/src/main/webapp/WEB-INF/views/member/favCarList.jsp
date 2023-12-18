<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고차 관심목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>중고차 관심목록</h2>
		<hr size="1" width="100%">
			<c:if test="${empty detail}">
			<div>
			관심목록이 없습니다.
			</div>
			</c:if>
			<c:if test="${!empty detail}">
			<c:forEach var="detail" items="${detail}">
			<div>
				<ul>
					<li>${detail.car_title}</li>
				</ul>
			</div>
			</c:forEach>
			<div class="align-center">${page}</div>
			</c:if>
	</div>
</div>
</body>
</html>