<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<img src="${pageContext.request.contextPath}/images/house_title.png" width="960">
	<div class="content-main">
		<form id="search_form" action="list.do" method="get">
			<div>
				<b>부동산 직거래 게시물</b>
			</div>
			<ul class="search">		
				<li>
					<input type="button" value="필터">
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>
			<div class="align-right">
				<input type="checkbox">판매중인 글만 보기
			</div>
			
			<div class="content-main">
				<h4>등록 매물</h4>
				<div class="image-space">
					<c:forEach var="house" items="${houseList}">
						<div class="horizontal-area">
							<a href="${pageContext.request.contextPath}/house/detailHouse.do?house_num=${house.house_num}">
								<img src="${pageContext.request.contextPath}/upload/${house.house_photo1}">
								<span>${house.house_title}</span>
								<br>
								${house.mem_nickname}<b><fmt:formatNumber value="${house.house_price}"/>원</b>
							</a>
						</div>
					</c:forEach>
					<div class="float-clear">
						<hr width="100%" size="1" noshade="noshade">
					</div>
				</div>
			</div>
			<div class="align-center">${page}</div>
			<div class="align-right">
			<input type="button" value="글쓰기" onclick="location.href='insertHouseForm.do'"
					<c:if test="${empty user_num}">disabled="disabled"</c:if>
				><!-- 로그인이 되어있지 않은면 비활성화 됨(disabled="disabled") -->
			</div>
		</form>	
	</div>
</div>
</body>
</html>