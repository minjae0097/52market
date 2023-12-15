<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>알바</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/KJY.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<img src="${pageContext.request.contextPath}/images/알바배너.jpg" width="960">
	<div class="content-main">
		<h2>알바 최신글</h2>
		<div class="image-space">
			<c:forEach var="alba" items="${albaList}">
				<div class="horizontal-area99">
					<a href="${pageContext.request.contextPath}/alba/detailAlba.do?alba_num=${alba.alba_num}">
					<img src="${pageContext.request.contextPath}/upload/${alba.alba_photo}">
					<span>${alba.alba_title}</span>
					<span>${alba.alba_address1}</span>
					<br>
					<span>조회 ${alba.alba_hit}</span>
					</a>
				</div>
			</c:forEach>
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
		</div>
	</div>
	<div class="align-right">
		<input type="button" value="글쓰기" onclick="location.href='insertAlbaForm.do'"
			<c:if test="${empty user_num}">disabled="disabled"</c:if>
		>
	</div>
</div>
</body>
</html>