<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세보기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<img src="${pageContext.request.contextPath}/upload/${detail.car_image}" 
				                           width="200" height="200" class="my-photo">
	</div>
	<div>
		<span>${detail.car_title}</span>
	</div>
	<div>
	<h3>정보</h3>
		<ul>
			<li>차종 : ${detail.car_type}</li>
			<li>연식 : ${detail.car_model_year}</li>
			<li>주행거리 : ${detail.car_distance}</li>
			<li>연료 : ${detail.car_fuel}</li>
			<li>자동/수동 : ${detail.car_transmission}</li>
		</ul>
	<h3>소개</h3>
	<span>${list.carlist_content}</span>
	<h3>위치</h3>
	
	</div>
</div>
</body>
</html>