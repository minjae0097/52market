<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>알바 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<img src="${pageContext.request.contextPath}/upload/${alba.alba_photo}" width="250" height="250" class="albalist-photo">
	</div>
	<div>
		<span>${alba.alba_title}</span>
	</div>
	<div>
	<h3>정보</h3>
		<ul>
			<li>${alba.alba_content1}</li>
			<li>${alba.alba_content2}</li>
			<li>${alba.alba_location}</li>
		</ul>
	</div>
</div>
</body>
</html>