<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ApplyList</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript">
	window.onload = function(){
		let myForm = document.getElementById('search_form');
		//이벤트 연결
		myForm.onsubmit = function(){
			let keyword = document.getElementById('keyword');
			if(keyword.value.trim()==''){
				alert('검색어를 입력하세요');
				keyword.value='';
				keyword.focus();
				return false;
			}
		};
	};
</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<div class="align-center">
				<a href="${pageContext.request.contextPath}/alba/applyList.do">
					<img src="${pageContext.request.contextPath}/images/알바배너.jpg" width="900" onclick="location.href='${pageContext.request.contextPath}/alba/applyList.do'">
				</a>
			</div>
			<form class="align-right" id="search_form" action="applyList.do" method="get">
				<ul>
					<li>
						<select name="alba_category">
							<option value="1" <c:if test="${param.alba_category==1}">selected</c:if>>글 제목</option>
						</select>	
						<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
						<input type="submit" value="검색">
					</li>
					<li>
					</li>
				</ul>
			</form>
			<div>
				<input type="button" value="마이페이지" onclick="location.href='${pageContext.request.contextPath}/board/board_myPage.do'">
			</div>
			<c:if test="${count==0}">
				<div class="result-display">
					표시할 게시물이 없습니다.
				</div>
			</c:if>
			<c:if test="${count > 0}">
				<table>
					<tr>
						<th>카테고리</th>
						<th>제목</th>
						<th>내용</th>
						<th>위치</th>
						<th>조회수</th>
					</tr>
					<c:forEach var="board" items="${list}">
						<tr>
							<td class="align-center">
								<c:if test="${board.board_category==1}">동네생활</c:if>
							    <c:if test="${board.board_category==2}">동네백과</c:if>
							    <c:if test="${board.board_category==3}">동네맛집</c:if>
							    <c:if test="${board.board_category==4}">동네소식</c:if>
							</td>
							<td class="align-center"><a href="detail.do?board_num=${board.board_num}">${board.board_title}</a></td>
							<td class="align-center">${board.board_content}</td>
							<td class="align-center">${board.short_address}</td>
							<td class="align-center">${board.board_hit}</td>
						</tr>
					</c:forEach>
				</table>
				<hr size="1" width="100%">
				<div class="align-center">${page}</div>
			</c:if>
			<div class="list-space align-right">
				<input type="button" value="글쓰기" onclick="location.href='writeForm.do'"
					<c:if test="${empty user_num}">disabled="disabled"</c:if>
				>
			</div>
		</div>
	</div>
</body>
</html>