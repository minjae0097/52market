<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고차 구매목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CMJ.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>중고차 구매목록</h2>
		<hr size="1" width="100%">
		
			<c:if test="${empty carList}">
			<div>
			판매목록이 없습니다.
			</div>
			</c:if>
			<c:if test="${!empty carList}">
			<table>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>채팅</th>
			</tr>
			<c:forEach var="carList" items="${carList}">
			<tr>
				<td>${carList.carlist_num}</td>
				<td>
					<ul>
						<li><a href="${pageContext.request.contextPath}/car/detailCar.do?carlist_num=${carList.carlist_num}">${carList.car_title}</a></li>
					</ul>
				</td>
				<td>
					<button onclick="location.href='${pageContext.request.contextPath}/chatting/chattingListForSellerCar.do?carlist_num=${carList.carlist_num}'">채팅목록</button>
				<td>
			</tr>
			</c:forEach>
			</table>
			<hr size="1" width="100%">
			<div class="align-center">${page}</div>
			</c:if>
	</div>
</div>
</body>
</html>