<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반회원 지원서 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="content-main">
		<h2>지원서 목록(일반 회원)</h2>
		<div class="list-space align-right">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
		<c:if test="${count ==0}">
		<div class="result-display">
			표시할 목록이 없습니다.
		</div>
		</c:if>
		<c:if test="${count>0}">
		<table>
			<tr>
				<th>알바 글 제목</th>
				<th>지원서 파일</th>
				<th>지원일</th>
			</tr>
			<c:forEach var="alba" items="${list}">
			<tr>
				<td><a href="detailAlba.do?alba_num=${alba.alba_num}">${alba.alba_title}</a></td>
				<td><a href="${pageContext.request.contextPath}/upload/${alba.alba_filename}">${alba.alba_filename}</a></td>
				<td>${alba.aplist_reg_date}</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		</c:if>
	</div>
</div>
</body>
</html>