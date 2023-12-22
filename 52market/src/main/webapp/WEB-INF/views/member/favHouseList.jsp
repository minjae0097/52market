<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>부동산 판매목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>부동산 관심목록</h2>
		<hr size="1" width="100%">
		<form id="search_form" action="favHouseList.do" method="get">
			<ul class="search">
				<li>
				<input type="button" value="필터"  id = "show_filter">
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" 
					                              value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
				
			</ul>
			</form>
			<c:if test="${empty detail}">
			<div>
			관심목록이 없습니다.
			</div>
			</c:if>
			<c:if test="${!empty detail}">
			<table>
			<tr>
				<th>번호</th>
				<th>제목</th>
			</tr>
			<c:forEach var="houseList" items="${detail}">
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
			<hr size="1" width="100%">
			<div class="align-center">${page}</div>
			</c:if>
	</div>
</div>
</body>
</html>