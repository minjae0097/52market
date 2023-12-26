<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CMJ.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>최근 판매목록</h2>
		<div>
			<h3>중고거래</h3>
			<div class="align-right">
				<a href="sellProductList.do">상세보기</a>
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
				<th>채팅</th>
			</tr>
			<c:forEach var="product" items="${productList}">
			<tr>
				<td>${product.product_num}</td>
				<td>
				<ul>
					<li><a href="${pageContext.request.contextPath}/product/productDetail.do?product_num=${product.product_num}">${product.product_name}</a></li>
				</ul>
				</td>
				<td style="width: 20%">
					<button onclick="location.href='${pageContext.request.contextPath}/chatting/chattingListForSellerProduct.do?product_num=${product.product_num}'">채팅목록</button>
				</td>
			</tr>
			</c:forEach>
			</table>
			</c:if>
		</div>
		<div>
			<h3>중고차</h3>
			<div class="align-right">
				<a href="sellCarList.do">상세보기</a>
			</div>
			<c:if test="${empty carList}">
			<div>
			표시할 게시물이 없습니다.
			</div>
			</c:if>
			<c:if test="${!empty carList}">
			<table>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>채팅</th>
			</tr>
			<c:forEach var="car" items="${carList}">
			<tr>
				<td>${car.carlist_num}</td>
				<td>
				<ul>
					<li><a href="${pageContext.request.contextPath}/car/detailCar.do?carlist_num=${car.carlist_num}">${car.car_title}</a></li>
				</ul>
				</td>
				<td style="width: 20%">
					<button onclick="location.href='${pageContext.request.contextPath}/chatting/chattingListForSellerCar.do?carlist_num=${car.carlist_num}'">채팅목록</button>
					<c:if test="${car.chatcnt>0}"><span>(NEW)</span></c:if>
				</td>
			</tr>
			</c:forEach>
			</table>
			</c:if>
		</div>
		<div>
			<h3>부동산 직거래</h3>
			<div class="align-right">
				<a href="sellHouseList.do">상세보기</a>
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
				<th>채팅</th>
			</tr>
			<c:forEach var="house" items="${houseList}">
			<tr>
				<td>${house.house_num}</td>
				<td>
				<ul>
					<li><a href="${pageContext.request.contextPath}/house/detailHouse.do?house_num=${house.house_num}">${house.house_title}</a></li>
				</ul>
				</td>
				<td style="width: 20%">
					<button onclick="location.href='${pageContext.request.contextPath}/chatting/chattingListForSellerHouse.do?house_num=${house.house_num}'">채팅목록</button>
				</td>
			</tr>
			</c:forEach>
			</table>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>