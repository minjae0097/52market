<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>알바</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/KJY.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/alba_fav.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<img src="${pageContext.request.contextPath}/images/알바배너.jpg" width="960">
	<div class="content-main">
		<h2>알바 최신글</h2>
		<form id="search_form" action="list.do" method="get">
			<ul>
				<li>
			<ul class="search">
				<li>
				<input type="button" value="필터">
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>제목+내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>		
			</li>
		</ul>
		<div class="image-space">
			<c:forEach var="alba" items="${albaList}">
				<div class="horizontal-area99">
					<a href="${pageContext.request.contextPath}/alba/detailAlba.do?alba_num=${alba.alba_num}">
					<img src="${pageContext.request.contextPath}/upload/${alba.alba_photo}">
					<span>${alba.alba_title}</span>
					<span>${alba.alba_address1}</span>
					<br>
					<span>조회 ${alba.alba_hit}</span>
					<span>관심 ${alba.alba_fav}</span>
					</a>
				</div>
			</c:forEach>
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
			<div class="align-center">${page}</div>
		</div>
		</form>
	</div>
	<div class="align-right">
		<c:if test="${user_auth==3}">
		<input type="button" value="글쓰기" onclick="location.href='insertAlbaForm.do'">
		</c:if>
	</div>
</div>
</body>
</html>