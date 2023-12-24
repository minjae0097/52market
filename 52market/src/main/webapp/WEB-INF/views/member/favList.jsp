<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관심목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>관심목록</h2>
		<div>
			<h3>중고거래</h3>
			<div class="align-right">
				<a href="favProductList.do">상세보기</a>
			</div>
			<c:if test="${empty productList}">
			<div>
			표시할 게시물이 없습니다.
			</div>
			</c:if>
			<c:if test="${!empty productList}">
			<c:forEach var="product" items="${productList}">
			<div>
				<ul>
					<li><a href="${pageContext.request.contextPath}/product/productDetail.do?product_num=${product.product_num}">${product.product_name}</a></li>
				</ul>
			</div>
			</c:forEach>
			</c:if>
		</div>
		<hr size="1" width="100%">
		<div>
			<h3>중고차</h3>
			<div class="align-right">
				<a href="favCarList.do">상세보기</a>
			</div>
			<c:if test="${empty carList}">
			<div>
			표시할 게시물이 없습니다.
			</div>
			</c:if>
			<c:if test="${!empty carList}">
			<c:forEach var="car" items="${carList}">
			<div>
				<ul>
					<li><a href="${pageContext.request.contextPath}/car/detailCar.do?carlist_num=${car.carlist_num}">${car.car_title}</a></li>
				</ul>
			</div>
			</c:forEach>
			</c:if>
		</div>
		<hr size="1" width="100%">
		<div>
			<h3>부동산 직거래</h3>
			<div class="align-right">
				<a href="favHouseList.do">상세보기</a>
			</div>
			<c:if test="${empty houseList}">
			<div>
			표시할 게시물이 없습니다.
			</div>
			</c:if>
			<c:if test="${!empty houseList}">
			<c:forEach var="house" items="${houseList}">
			<div>
				<ul>
					<li><a href="${pageContext.request.contextPath}/house/detailHouse.do?house_num=${house.house_num}">${house.house_title}</a></li>
				</ul>
			</div>
			</c:forEach>
			</c:if>
		</div>
		<hr size="1" width="100%">
	</div>
</div>
</body>
</html>