<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세보기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CMJ.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-detail">
		<img src="${pageContext.request.contextPath}/upload/${detail.car_image}" width="600">
	<div class="horizontal-area">
		<c:if test="${!empty seller.mem_photo}">
		<img src="${pageContext.request.contextPath}/upload/${seller.mem_photo}" width="30" height="30" class="my-photo">
		</c:if>
		<c:if test="${empty seller.mem_photo}">
		<img src="${pageContext.request.contextPath}/images/face.png" width="30" height="30" class="my-photo">
		</c:if>
		<span>${detail.mem_nickname}</span>
		<button onclick="location.href='updateCarForm.do?carlist_num=${list.carlist_num}'">수정</button>
		<button>삭제</button>
	</div>
	<hr width="100%" size="1">
	<div>
		<span>${detail.car_title} . 
		<c:if test="${detail.car_price%10000==0}"><fmt:formatNumber value="${detail.car_price/10000}"/>만원</c:if>
		<c:if test="${detail.car_price%10000!=0}"><fmt:formatNumber value="${detail.car_price}"/>원</c:if> . 
		<c:if test="${detail.car_distance>=10000}">  <fmt:formatNumber value="${detail.car_distance/10000}" pattern="#.#" />만km</c:if>
		<c:if test="${detail.car_distance<10000 }"><fmt:formatNumber value="${detail.car_distance}"/>km</c:if>
		
		</span>
	</div>
	<div>
	<h1>정보</h1>
		<ul>
			<li>차종 : ${detail.car_type}</li>
			<li>연식 : ${detail.car_model_year}</li>
			<li>주행거리 : <fmt:formatNumber value="${detail.car_distance}"/>km</li>
			<li>연료 : ${detail.car_fuel}</li>
			<li>자동/수동 : ${detail.car_transmission}</li>
		</ul>
	<h1>소개</h1>
		<ul>
			<li>
			<span>${list.carlist_content}</span>
			</li>
		</ul>
	<h1>위치</h1>
	<jsp:include page="/map/showMap.jsp"/>
	</div>
		</div>
</div>
</body>
</html>