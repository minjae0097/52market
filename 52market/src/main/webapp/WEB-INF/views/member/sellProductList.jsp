<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래 판매목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CMJ.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>중고거래 판매목록</h2>
		<hr size="1" width="100%">
		
			<c:if test="${empty productList}">
			<div>
			판매목록이 없습니다.
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
				<td>
					<button onclick="location.href='${pageContext.request.contextPath}/chatting/chattingListForSellerProduct.do?product_num=${product.product_num}'">채팅목록</button>
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