<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
<div class="container">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<div class="align-center">
			<a href="${pageContext.request.contextPath}/product/list.do">
				<img src="${pageContext.request.contextPath}/images/중고거래배너.png" width="900">
			</a>
		</div>
		<div class="align-center">
			<a href="${pageContext.request.contextPath}/board/list.do">
				<img src="${pageContext.request.contextPath}/images/동네생활.png" width="900">
			</a>
		</div>
		<div class="align-center">
			<a href="${pageContext.request.contextPath}/alba/list.do">
				<img src="${pageContext.request.contextPath}/images/알바배너.png" width="900">
			</a>
		</div>
		<div class="align-center">
			<a href="${pageContext.request.contextPath}/house/list.do">
				<img src="${pageContext.request.contextPath}/images/house_title.png" width="900">
			</a>
		</div>
		<div class="align-center">
			<a href="${pageContext.request.contextPath}/car/list.do">
				<img src="${pageContext.request.contextPath}/images/중고차배너.png" width="900">
			</a>
		</div>
	</div>
</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>







