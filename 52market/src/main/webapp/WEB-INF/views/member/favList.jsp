<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관심목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CMJ.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>최근 관심목록</h2>
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
			<table>
			<tr>
				<th>번호</th>
				<th>제목</th>
			</tr>
			<c:forEach var="product" items="${productList}">
			<tr>
				<td>${product.product_num}</td>
				<td>
					<ul>
						<li><a href="${pageContext.request.contextPath}/product/productDetail.do?product_num=${product.product_num}">${product.product_name}</a></li>
					</ul>
				</td>
			</tr>
			</c:forEach>
			</table>
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
			<div>
			<table>
			<tr>
				<th>번호</th>
				<th>제목</th>
			</tr>
			<c:forEach var="carList" items="${carList}">
			<tr>
				<td>${carList.carlist_num}</td>
				<td>
					<a href="${pageContext.request.contextPath}/car/detailCar.do?carlist_num=${carList.carlist_num}">${carList.car_title}</a>
				</td>
			</tr>
			</c:forEach>
			</table>
			</div>
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
			<table>
			<tr>
				<th>번호</th>
				<th>제목</th>
			</tr>
			<c:forEach var="houseList" items="${houseList}">
			<tr>
				<td>${houseList.house_num}</td>
				<td>
					<ul>
						<li><a href="${pageContext.request.contextPath}/house/detailHouse.do?house_num=${houseList.house_num}">${houseList.house_title}</a></li>
					</ul>
				</td>
			</tr>
			</c:forEach>
			</table>
			</c:if>
		</div>
		<hr size="1" width="100%">
	</div>
</div>
</body>
</html>