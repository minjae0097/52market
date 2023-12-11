<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고차 직거래</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>등록 매물</h2>
		<div class="image-space">
			<c:forEach var="car" items="${carList}">
				<div class="horizontal-area">
					<a href="${pageContext.request.contextPath}/car/detailCar.do?carlist_num=${car.carlist_num}">
						<img src="${pageContext.request.contextPath}/upload/${car.car_image}">
						<span>${car.car_title}</span>
						<br>
						<b><fmt:formatNumber value="${car.car_price}"/>원</b>
					</a>
				</div>
			</c:forEach>
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
		</div>
	</div>
	<div class="align-right">
		<input type="button" value="글쓰기" onclick="location.href='insertCarForm.do'">
	</div>
</div>
</body>
</html>