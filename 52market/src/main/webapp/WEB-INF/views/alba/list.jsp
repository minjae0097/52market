<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>알바</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>알바 최신글</h2>
		<div class="photo-space">
			<c:forEach var="alba" items="${alba}">
				<div class="horizontal-area">
					<a href="${pageContext.request.contextPath}/alba/detailAlba.do?alba_num=${alba.alba_num}">
					<img src="${pageContext.request.contextPath}/upload/${alba.alba_photo}">
					<span>${alba.alba_title}</span>
					</a>
				</div>
			</c:forEach>
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
		</div>
	</div>
	<div class="align-right">
		<input type="button" value="글쓰기" onclick="location.href='writeAlbaForm.do'">
	</div>
</div>
</body>
</html>