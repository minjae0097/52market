<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BoardList</title>
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
			<h2>게시판 목록</h2>
			<form id="search_form" action="list.do" method="get">
				<ul class="align-right">
					<li>
						<select name="category">
							<option>동네생활</option>
							<option>동네백과</option>
							<option>동네맛집</option>
							<option>동네소식</option>
							<option>취미생활</option>
							<option>해주세요</option>
							<option>동네질문</option>
							<option>생활정보</option>
						</select>
						<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
						<input type="submit" value="검색">
					</li>
				</ul>
			</form>
			<c:if test="${count==0}">
				<div class="result-display">
					표시할 게시물이 없습니다.
				</div>
			</c:if>
			<%-- <c:if test="${count >0}"> --%>
				<table>
					<tr>
						<th>글번호</th>
						<td>${board.board_num}</td>
					</tr>	
					<tr>
						<th>제목</th>
						<td><a href="detail.do?board_num=${board.board_num}">${board.board_title}</a></td>
					</tr>	
					<tr>
						<th>내용</th>
						<td>${board.board_content}</td>
					</tr>	
					<tr>
						<th>위치</th>
						<td>${board.board_address1}</td>
					</tr>	
					<tr>
						<th>작성일</th>
						<td>${board.board_regdate}</td>
					</tr>	
					<tr>
						<th>조회수</th>
						<td>${board.board_hit}</td>
					</tr>	
				</table>
					
				<div>
				<div class="align-right">
					<input type="button" value="글쓰기" onclick="location.href='writeForm.do'">
				</div>
				<div class="align-center">${page}</div>			
			<%-- </c:if> --%>
		</div>
	</div>
	</div>
</body>
</html>