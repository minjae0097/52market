<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h4>중고 물품</h4>
		<div class="image-space">
			<c:forEach var="product" items="${product}">
				<div class="horizontal-area">
					<a href="${pageContext.request.contextPath}/product/detail.do?product_num=${product.product_num}">
						<img src="${pageContext.request.contextPath}/upload/${product.product_image}">
						<span>${product.product_title}</span>
						<br>
						<b><fmt:formatNumber value="${product.product_price}"/>원</b>
					</a>
				</div>
			</c:forEach>
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
		</div>
	</div>
	<div class="align-right">
		<input type="button" value="글쓰기" onclick="location.href='productWriteForm.do'">
	</div>
</div>
</body>